package com.baoviet.agency.service.impl;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.AgentReminder;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.dto.AgentReminderDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgentReminderRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.service.AgentReminderService;
import com.baoviet.agency.service.mapper.AgentReminderMapper;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ReminderCreateVM;
import com.baoviet.agency.web.rest.vm.ReminderSearchVM;


/**
 * Service Implementation for managing GnocCr.
 * @author Duc, Le Minh
 */
@Service
@Transactional
public class AgentReminderServiceImpl implements AgentReminderService {

    private final Logger log = LoggerFactory.getLogger(AgentReminderServiceImpl.class);

    @Autowired
    private AgentReminderRepository agentReminderRepository;
    
    @Autowired
    private AgentReminderMapper agentReminderMapper;

    @Autowired
    private ContactRepository contactRepository;
    
	@Override
	public List<AgentReminderDTO> getAll(String type) {
		log.debug("Request to getAll AgentReminderDTO : type{} ", type);
		List<AgentReminderDTO> data = agentReminderMapper.toDto(agentReminderRepository.findByActiveAndType("1", type));
		if (data != null && data.size() > 0) {
			for (AgentReminderDTO item : data) {
				Contact co = contactRepository.findOne(item.getContactId());
				if (co != null) {
					if (co.getContactName() != null) {
						item.setContactName(co.getContactName());
					}
				}
			}
			return data;
		}
		return null;
	}

	@Override
	public List<AgentReminderDTO> findByContactId(String contactId, String type) {
		log.debug("Request to findByContactId : contactId{}  type{} ", contactId, type);
		
		List<AgentReminderDTO> data = agentReminderMapper.toDto(agentReminderRepository.findByContactIdAndType(contactId, type));
		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}

	@Override
	public List<AgentReminderDTO> searchReminder(ReminderSearchVM param, String type) {
		log.debug("Request to searchReminder : ReminderSearchVM{}  type{} ", param, type);
		List<AgentReminderDTO> data = agentReminderMapper.toDto(agentReminderRepository.searchReminder(param, type));
		if (data != null && data.size() > 0) {
			for (AgentReminderDTO item : data) {
				Contact co = contactRepository.findOne(item.getContactId());
				if (co != null) {
					if (co.getContactName() != null) {
						item.setContactName(co.getContactName());
					}
				}
			}
			return data;
		}
		
		return null;
	}

	@Override
	public AgentReminderDTO findByReminderId(String reminderId, String type) {
		log.debug("Request to searchReminder : reminderId{}  type{} ", reminderId, type);
		
		AgentReminderDTO data = agentReminderMapper.toDto(agentReminderRepository.findByIdAndType(reminderId, type));
		return data;
	}

	@Override
	public AgentReminderDTO create(ReminderCreateVM info, String type) throws AgencyBusinessException {
		log.debug("Request to create : ReminderCreateVM{}  type{} ", info, type);
		
		Contact co = contactRepository.findOne(info.getContactId());
		if (co == null) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Id khách hàng không tồn tại");
		}
		
		// TH update
		if (!StringUtils.isEmpty(info.getId())) {
			AgentReminder reminder = agentReminderRepository.findByIdAndType(info.getId(), type);
			if (reminder == null) {
				throw new AgencyBusinessException("id", ErrorCode.INVALID, "Id nhắc nhở không tồn tại");
			}
			reminder.setProductCode(info.getProductCode());
			reminder.setContent(info.getContent());
			reminder.setNote(info.getNote());
			reminder.setRemindeDate(DateUtils.str2Date(info.getRemindeDate()));
			
			// update
			agentReminderRepository.save(reminder);
			return agentReminderMapper.toDto(reminder);
		} else {
			AgentReminderDTO obj = new AgentReminderDTO();
			obj.setContactId(info.getContactId());
			obj.setProductCode(info.getProductCode());
			obj.setRemindeDate(DateUtils.str2Date(info.getRemindeDate()));
			obj.setContent(info.getContent());
			obj.setNote(info.getNote());
			obj.setType(type);
			obj.setCreatedDate(new Date());
			obj.setActive("1");
			
			AgentReminder entity = agentReminderRepository.save(agentReminderMapper.toEntity(obj));
			
			return agentReminderMapper.toDto(entity);			
		}
	}

	@Override
	public List<AgentReminderDTO> getCountReminder(String type, Integer numberDay) {
		log.debug("Request to getCountReminder : type{}  numberDay{} ", type, numberDay);
		List<AgentReminderDTO> data = agentReminderMapper.toDto(agentReminderRepository.getCountReminder(type, numberDay));
		if (data != null && data.size() > 0) {
			return data;
		}
		return null;
	}

	@Override
	public void delete(String type, String reminderId) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to delete reminder by reminderId, {}", reminderId);
		AgentReminder data = agentReminderRepository.findByIdAndType(reminderId, type);
		if (data != null) {
			agentReminderRepository.delete(reminderId);	
		} else {
			throw new AgencyBusinessException("reminderId", ErrorCode.INVALID, "Id không tồn tại");
		}
	}
	
}
