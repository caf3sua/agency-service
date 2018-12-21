package com.baoviet.agency.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.ContactProduct;
import com.baoviet.agency.domain.ContactRelationship;
import com.baoviet.agency.domain.Relationship;
import com.baoviet.agency.dto.AgentReminderDTO;
import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.dto.ContactProductDTO;
import com.baoviet.agency.dto.ContactRelationshipDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgentContactRelationshipRepository;
import com.baoviet.agency.repository.AgentReminderRepository;
import com.baoviet.agency.repository.ContactProductRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.RelationshipRepository;
import com.baoviet.agency.service.AgentContactRelationshipService;
import com.baoviet.agency.service.AgentReminderService;
import com.baoviet.agency.service.ContactProductService;
import com.baoviet.agency.service.ContactService;
import com.baoviet.agency.service.mapper.AgentContactRelationshipMapper;
import com.baoviet.agency.service.mapper.ContactMapper;
import com.baoviet.agency.service.mapper.ContactProductMapper;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ContactCreateVM;
import com.baoviet.agency.web.rest.vm.ContactProductVM;
import com.baoviet.agency.web.rest.vm.ContactRelationshipVM;
import com.baoviet.agency.web.rest.vm.ContactSearchVM;
import com.baoviet.agency.web.rest.vm.ContactUpdateVM;
import com.baoviet.agency.web.rest.vm.ReminderCreateVM;

/**
 * Service Implementation for managing Kcare.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

	@Autowired
	private ContactMapper contactMapper;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private AgentContactRelationshipService contactRelationshipService;

	@Autowired
	private RelationshipRepository relationshipRepository;

	@Autowired
	private ContactProductService contactProductService;

	@Autowired
	private ContactProductRepository contactProductRepository;

	@Autowired
	private ContactProductMapper contactProductMapper;

	@Autowired
	private AgentContactRelationshipRepository contactRelationshipRepository;

	@Autowired
	private AgentContactRelationshipMapper contactRelationshipMapper;

	@Autowired
	AgentReminderService agentReminderService;

	@Autowired
	private AgentReminderRepository agentReminderRepository;

	@Override
	public List<ContactDTO> findByEmail(String email) {
		log.debug("Request to findByEmail : {}", email);
		List<ContactDTO> result = contactMapper.toDto(contactRepository.findByEmail(email));
		return result;
	}

	@Override
	public List<ContactDTO> findByEmailIgnoreCase(String email) {
		log.debug("Request to findByEmailIgnoreCase : {}", email);
		List<ContactDTO> result = contactMapper.toDto(contactRepository.findByEmailIgnoreCase(email));
		return result;
	}

	@Override
	public ContactDTO findOneByUserName(String username) {
		log.debug("Request to findOneByUserName : {}", username);
		return contactMapper.toDto(contactRepository.findOneByUserName(username));
	}

	@Override
	public ContactDTO findOneByContactCodeAndType(String contactCode, String type) {
		log.debug("Request to findOneByContactCodeAndType : contactCode{}, type{}", contactCode, type);
		ContactDTO result = contactMapper.toDto(contactRepository.findOneByContactCodeAndType(contactCode, type));

		if (result != null) {
			// Get listRelationship by contactId
			List<ContactRelationship> listRelationship = contactRelationshipRepository
					.findByContactId(result.getContactId());
			// Get listContactProduct by contactId
			List<ContactProduct> listContactProduct = contactProductRepository.findByContactId(result.getContactId());

			result.setListRelationship(listRelationship);
			result.setListContactProduct(listContactProduct);
		}

		return result;
	}

	@Override
	public List<ContactDTO> findAll() {
		List<Contact> result = contactRepository.findAll();
		List<ContactDTO> resultMapper = contactMapper.toDto(result);
		log.debug("Request to findAll ContactDTO: return List<ContactDTO> {}", resultMapper);
		return resultMapper;
	}

	@Override
	public ContactDTO save(ContactDTO contactDTO) {
		log.debug("Request to save ContactDTO: {}", contactDTO);
		Contact contact = contactMapper.toEntity(contactDTO);
		contact = contactRepository.save(contact);
		return contactMapper.toDto(contact);
	}

	@Override
	@Transactional(readOnly = true)
	public ContactDTO findOne(String contactId, String type) {
		log.debug("Request to findOne ContactDTO: contactId{}, type{}", contactId, type);
		Contact contact = contactRepository.findByContactIdAndType(contactId, type);
		return contactMapper.toDto(contact);
	}

	@Override
	public void delete(String contactId) {
		log.debug("Request to delete Contact : {}", contactId);
		contactRepository.delete(contactId);
	}

	@Override
	public ContactDTO findOneByContactUsername(String email) {
		log.debug("Request to findOneByContactUsername : {}", email);
		return contactMapper.toDto(contactRepository.findOneByContactUsername(email));
	}

	@Override
	public List<ContactDTO> findAllByType(String type) {
		log.debug("Request to findAllByType ContactDTO: {}", type);
		List<ContactDTO> result = contactMapper.toDto(contactRepository.findByType(type));

		// Lopp via result to append listRelationship, listContactProduct
		if (result != null && result.size() > 0) {
			for (ContactDTO item : result) {
				// Get listRelationship by contactId
				List<ContactRelationship> listRelationship = contactRelationshipRepository
						.findByContactId(item.getContactId());
				// Get listContactProduct by contactId
				List<ContactProduct> listContactProduct = contactProductRepository.findByContactId(item.getContactId());

				item.setListRelationship(listRelationship);
				item.setListContactProduct(listContactProduct);
			}
		}

		return result;
	}

	@Override
	public Page<ContactDTO> searchContact(ContactSearchVM contact, String type) {
		log.debug("Request to searchContact: ContactSearchVM{}, type {} : ", contact, type);
		Page<ContactDTO> result = contactRepository.search(contact, type).map(contactMapper::toDto);

		// Lopp via result to append listRelationship, listContactProduct
		if (result != null && result.getContent().size() > 0) {
			for (ContactDTO item : result.getContent()) {
				// Get listRelationship by contactId
				List<ContactRelationship> listRelationship = contactRelationshipRepository
						.findByContactId(item.getContactId());
				// Get listContactProduct by contactId
				List<ContactProduct> listContactProduct = contactProductRepository.findByContactId(item.getContactId());

				item.setListRelationship(listRelationship);
				item.setListContactProduct(listContactProduct);
			}
		}

		return result;
	}

	@Override
	public String generateContactCode(String type) {
		log.debug("Request to generateContactCode: {}", type);
		String contactCode = "";
		// default
		contactCode = type + String.format("%07d", 1);
		try {
			// get count contact
			Contact contact = contactRepository.findFirstByTypeOrderByContactIdDesc(type);
			if (contact != null) {
				if (!StringUtils.isEmpty(contact.getContactCode())) {
					String code = contact.getContactCode();
					String strNumber = code.substring((contact.getContactCode().length() - 7), contact.getContactCode().length());
					if (Integer.parseInt(strNumber) > 0) {
						int num = Integer.parseInt(strNumber) + 1;
						while (num < 999999999) {
							num++;
							contactCode = type + String.format("%07d", num);
							// ktra xem contactCode đã tồn tại hay chưa
							Contact contactCheck = contactRepository.findOneByContactCodeAndType(contactCode, type);
							if (contactCheck == null) {
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			int num = 1;
			while (num < 999999999) {
				num++;
				contactCode = type + String.format("%07d", num);
				// ktra xem contactCode đã tồn tại hay chưa
				Contact contactCheck = contactRepository.findOneByContactCodeAndType(contactCode, type);
				if (contactCheck == null) {
					break;
				}
			}
		}
		return contactCode;
	}

	@Override
	public ContactDTO create(ContactDTO contactDTO, ContactCreateVM param) throws AgencyBusinessException {
		log.debug("Request to create: {}", contactDTO);
		Contact contact = contactMapper.toEntity(contactDTO);
		contact = contactRepository.save(contact);
		if (contact.getContactId() != null) {
			log.debug("Request to save contactRelationship: {}", param);
			if (param.getListRelationship() != null && param.getListRelationship().size() > 0) {
				for (ContactRelationshipVM item : param.getListRelationship()) {

					ContactRelationshipDTO ContactRelationship = new ContactRelationshipDTO();
					Relationship relationship = new Relationship();
					if (item.getRelationId() != null) {
						relationship = relationshipRepository.findOne(item.getRelationId());
					}

					ContactRelationship.setContactId(contact.getContactId());
					ContactRelationship.setContactName(contact.getContactName());
					ContactRelationship.setRelationId(item.getRelationId());
					ContactRelationship.setRelationName(relationship.getRelationshipName());
					ContactRelationship.setContactRelationId(item.getContactRelationId());
					ContactRelationship.setContactRelationName(item.getContactRelationName());

					contactRelationshipService.save(ContactRelationship);
				}
			}

			if (param.getListContactProduct() != null && param.getListContactProduct().size() > 0) {
				for (ContactProductVM item : param.getListContactProduct()) {
					ContactProductDTO info = new ContactProductDTO();

					info.setContactId(contact.getContactId());
					info.setProductCode(item.getProductCode());
					info.setProductName(item.getProductName());

					contactProductService.save(info);
				}
			}

			// Save reminder
			if (param.getListReminders() != null && param.getListReminders().size() > 0) {
				for (ReminderCreateVM r : param.getListReminders()) {
					r.setContactId(contact.getContactId());
					agentReminderService.create(r, contact.getType());
				}
			}
		}

		return contactMapper.toDto(contact);
	}

	@Override
	public ContactDTO update(ContactUpdateVM param, String type) throws AgencyBusinessException {
		log.debug("Request to update: {}", param);

		Contact contact = contactRepository.findByContactIdAndType(param.getContactId(), type);

		if (contact == null) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Không tồn tại dữ liệu");
		}

		contact.setContactName(param.getContactName());
		contact.setContactSex(param.getContactSex());
		contact.setDateOfBirth(param.getDateOfBirth());
		contact.setHomeAddress(param.getHomeAddress());
		contact.setPhone(param.getPhone());
		contact.setHandPhone(param.getHandPhone());
		contact.setEmail(param.getEmail());
		contact.setIdNumber(param.getIdNumber());
		contact.setOccupation(param.getOccupation());
		// Nếu là KH đang là tiềm năng thì k cho update lên thành KH thân thiện, vip
		if (!contact.getGroupType().equals("POTENTIAL")) {
			if (!StringUtils.isEmpty(param.getGroupType())) {
				contact.setGroupType(param.getGroupType());
			}			
		}
		if (!StringUtils.isEmpty(param.getFacebookId())) {
			contact.setFacebookId(param.getFacebookId());
		}
		// update contact
		ContactDTO contactUpdate = contactMapper.toDto(contactRepository.save(contact));

		if (contactUpdate.getContactId() != null) {
			log.debug("Request to save contactRelationship: {}", param);
			// xóa quan hệ
			contactRelationshipRepository.deleteByContactId(contactUpdate.getContactId());
			// xóa sản phẩm bảo hiểm
			contactProductRepository.deleteByContactId(contactUpdate.getContactId());
			// xóa reminder
			agentReminderRepository.deleteByContactId(contactUpdate.getContactId());

			if (param.getListRelationship() != null && param.getListRelationship().size() > 0) {
				List<ContactRelationship> listRelationship = new ArrayList<>();
				for (ContactRelationshipVM item : param.getListRelationship()) {

					ContactRelationshipDTO contactRelationship = new ContactRelationshipDTO();
					Relationship relationship = new Relationship();
					if (item.getRelationId() != null) {
						relationship = relationshipRepository.findOne(item.getRelationId());
					}

					contactRelationship.setContactId(contactUpdate.getContactId());
					contactRelationship.setContactName(contactUpdate.getContactName());
					contactRelationship.setRelationId(item.getRelationId());
					contactRelationship.setRelationName(relationship.getRelationshipName());
					contactRelationship.setContactRelationId(item.getContactRelationId());
					contactRelationship.setContactRelationName(item.getContactRelationName());

					ContactRelationship contactRelationshipSave = contactRelationshipMapper
							.toEntity(contactRelationshipService.save(contactRelationship));
					listRelationship.add(contactRelationshipSave);
				}
				contactUpdate.setListRelationship(listRelationship);
			}

			if (param.getListContactProduct() != null && param.getListContactProduct().size() > 0) {
				List<ContactProduct> listContactProduct = new ArrayList<>();
				for (ContactProductVM item : param.getListContactProduct()) {
					ContactProductDTO info = new ContactProductDTO();

					info.setContactId(contactUpdate.getContactId());
					info.setProductCode(item.getProductCode());
					info.setProductName(item.getProductName());

					ContactProduct contactProductSave = contactProductMapper.toEntity(contactProductService.save(info));
					listContactProduct.add(contactProductSave);
				}
				contactUpdate.setListContactProduct(listContactProduct);
			}
		}

		// Update reminder
		if (param.getListReminders() != null && param.getListReminders().size() > 0) {
			for (ReminderCreateVM r : param.getListReminders()) {
				r.setContactId(contact.getContactId());
				agentReminderService.create(r, contact.getType());
			}
		}

		return contactUpdate;
	}

	@Override
	public ContactDTO findOneByPhoneAndType(String phone, String type) {
		log.debug("Request to findOneByPhoneAndType: phone{}, type{} : ", phone, type);
		ContactDTO result = contactMapper.toDto(contactRepository.findOneByPhoneAndType(phone, type));

		if (result != null) {
			// Get listRelationship by contactId
			List<ContactRelationship> listRelationship = contactRelationshipRepository
					.findByContactId(result.getContactId());
			// Get listContactProduct by contactId
			List<ContactProduct> listContactProduct = contactProductRepository.findByContactId(result.getContactId());

			result.setListRelationship(listRelationship);
			result.setListContactProduct(listContactProduct);
		}

		return result;
	}

	@Override
	public ContactUpdateVM convertContactToVM(ContactDTO contactDTO, String type) {
		log.debug("Request to convertContactToVM: contactDTO{}, type{} : ", contactDTO, type);
		return convertToVM(contactDTO, type);
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */

	private ContactUpdateVM convertToVM(ContactDTO co, String type) {
		ContactUpdateVM contact = new ContactUpdateVM();

		contact.setContactId(co.getContactId());
		contact.setContactCode(co.getContactCode());
		if (!StringUtils.isEmpty(co.getContactName())) {
			contact.setContactName(co.getContactName());
		}
		if (!StringUtils.isEmpty(co.getContactSex())) {
			contact.setContactSex(co.getContactSex());
		}
		if (co.getDateOfBirth() != null) {
			contact.setDateOfBirth(co.getDateOfBirth());
		}
		if (!StringUtils.isEmpty(co.getHomeAddress())) {
			contact.setHomeAddress(co.getHomeAddress());
		}
		if (!StringUtils.isEmpty(co.getPhone())) {
			contact.setPhone(co.getPhone());
		}
		if (!StringUtils.isEmpty(co.getEmail())) {
			contact.setEmail(co.getEmail());
		}
		if (!StringUtils.isEmpty(co.getIdNumber())) {
			contact.setIdNumber(co.getIdNumber());
		}
		if (!StringUtils.isEmpty(co.getOccupation())) {
			contact.setOccupation(co.getOccupation());
		}
		if (!StringUtils.isEmpty(co.getGroupType())) {
			contact.setGroupType(co.getGroupType());
		}
		if (!StringUtils.isEmpty(co.getFacebookId())) {
			contact.setFacebookId(co.getFacebookId());
		}
		if (!StringUtils.isEmpty(co.getCategoryType())) {
			contact.setCategoryType(co.getCategoryType());
		}
		if (!StringUtils.isEmpty(co.getHandPhone())) {
			contact.setHandPhone(co.getHandPhone());
		}

		// Get listRelationship by contactId
		List<ContactRelationship> listRelationship = contactRelationshipRepository.findByContactId(co.getContactId());
		if (listRelationship != null && listRelationship.size() > 0) {
			List<ContactRelationshipVM> lstContactRelationshipVM = new ArrayList<>();
			for (ContactRelationship item : listRelationship) {
				ContactRelationshipVM data = new ContactRelationshipVM();
				if (!StringUtils.isEmpty(item.getRelationId())) {
					data.setRelationId(item.getRelationId());
				}
				if (!StringUtils.isEmpty(item.getContactRelationId())) {
					data.setContactRelationId(item.getContactRelationId());
				}
				if (!StringUtils.isEmpty(item.getContactRelationName())) {
					data.setContactRelationName(item.getContactRelationName());
				}
				if (!StringUtils.isEmpty(item.getRelationName())) {
					data.setRelationName(item.getRelationName());
				}
				lstContactRelationshipVM.add(data);
			}
			if (lstContactRelationshipVM != null && lstContactRelationshipVM.size() > 0) {
				contact.setListRelationship(lstContactRelationshipVM);
			}
		}

		// Get listContactProduct by contactId
		List<ContactProduct> listContactProduct = contactProductRepository.findByContactId(co.getContactId());
		if (listContactProduct != null && listContactProduct.size() > 0) {
			List<ContactProductVM> listContactProductVM = new ArrayList<>();

			for (ContactProduct item : listContactProduct) {
				ContactProductVM data = new ContactProductVM();
				if (!StringUtils.isEmpty(item.getProductCode())) {
					data.setProductCode(item.getProductCode());
				}
				if (!StringUtils.isEmpty(item.getProductName())) {
					data.setProductName(item.getProductName());
				}
				listContactProductVM.add(data);
			}
			if (listContactProductVM != null && listContactProductVM.size() > 0) {
				contact.setListContactProduct(listContactProductVM);
			}
		}

		List<AgentReminderDTO> lstReminder = agentReminderService.findByContactId(co.getContactId(), type);
		if (lstReminder != null && lstReminder.size() > 0) {
			List<ReminderCreateVM> lstReminderCreateVM = new ArrayList<>();
			for (AgentReminderDTO item : lstReminder) {
				ReminderCreateVM data = new ReminderCreateVM();
				data.setContactId(item.getContactId());
				data.setProductCode(item.getProductCode());
				data.setContent(item.getContent());
				data.setRemindeDate(DateUtils.date2Str(item.getRemindeDate()));
				if (!StringUtils.isEmpty(item.getNote())) {
					data.setNote(item.getNote());
				}
				lstReminderCreateVM.add(data);
			}
			contact.setListReminders(lstReminderCreateVM);
		}

		return contact;
	}

}
