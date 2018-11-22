package com.baoviet.agency.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.FileContentDTO;
import com.baoviet.agency.domain.Conversation;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AttachmentDTO;
import com.baoviet.agency.dto.ConversationDTO;
import com.baoviet.agency.repository.AgreementRepository;
import com.baoviet.agency.repository.ConversationRepository;
import com.baoviet.agency.service.AttachmentService;
import com.baoviet.agency.service.ConversationService;
import com.baoviet.agency.service.mapper.AgreementMapper;
import com.baoviet.agency.service.mapper.ConversationMapper;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DbUtil;
import com.baoviet.agency.web.rest.vm.ConversationVM;


/**
 * Service Implementation for managing Conversation.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ConversationServiceImpl implements ConversationService {
	
	private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private ConversationMapper conversationMapper;
	
	@Autowired
	private AgreementMapper agreementMapper;
	
	@Autowired
	private AgreementRepository agreementRepository;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Override
	public String insertConversation(ConversationDTO info) {
		Conversation entity = conversationMapper.toEntity(info);
		
		entity = conversationRepository.save(entity);		
		log.debug("REST request insertConversation" + entity.getConversationId());
		return entity.getConversationId();
	}
	
	@Override
	public List<ConversationDTO> getByParrentId(String agreementId) {
		return conversationMapper.toDto(conversationRepository.findByParrentId(agreementId));
	}

	@Override
	public ConversationDTO save(ConversationVM info, AgencyDTO currentAgency, AgreementDTO agreement) {
		ConversationDTO conversation = new ConversationDTO();

		String title = "Đại lý - " + info.getTitle();
		conversation.setTitle(title);
		conversation.setParrentId(agreement.getAgreementId());
		conversation.setSendEmail(info.getSendEmail());
		if (!StringUtils.isEmpty(info.getConversationContent())) {
			conversation.setConversationContent(info.getConversationContent());
		} 
		if (!StringUtils.isEmpty(agreement.getLineId())) {
			conversation.setLineId(agreement.getLineId());
		}
		if (!StringUtils.isEmpty(currentAgency.getEmail())) {
			conversation.setUserName(currentAgency.getEmail());
		}
		conversation.setUserId(currentAgency.getId());
		conversation.setSendDate(new Date());
		conversation.setResponseDate(new Date());
		conversation.setCreateDate(DbUtil.getSysDate(em));
		conversation.setRole("agency"); // mặc định để đại lý
		if (!StringUtils.isEmpty(info.getRole())) {
			conversation.setRole(info.getRole());	
		}
		
		String conversationId = this.insertConversation(conversation);
		
		// Lưu file
		// data content
		if (info.getImgGycbhContents() != null && info.getImgGycbhContents().size() > 0) {
			for (FileContentDTO item : info.getImgGycbhContents()) {
				AttachmentDTO attachmenInfo = new AttachmentDTO();
				attachmenInfo.setAttachmentName(item.getFilename());
				if (!StringUtils.isEmpty(item.getContent())) {
					attachmenInfo.setContentFile(item.getContent());
				}
				attachmenInfo.setModifyDate(new Date());
				attachmenInfo.setTradeolSysdate(new Date());
				attachmenInfo.setUserId(currentAgency.getId());
				attachmenInfo.setParrentId(conversationId);
				attachmenInfo.setIstransferred(0);
				String attachmentId = attachmentService.save(attachmenInfo);
				log.debug("Request to save Attachment, attachmentId{}", attachmentId);
			}
		}
		
		// chuyển trạng thái đơn sang chờ BV Giám định
		agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
		agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);
		agreementRepository.save(agreementMapper.toEntity(agreement));
		
		return conversation;
	}
	
	@Override
	public ConversationDTO saveAdmin(ConversationVM info, AgencyDTO currentAgency, AgreementDTO agreement) {
		ConversationDTO conversation = new ConversationDTO();

		String title = "Bảo Việt - " + info.getTitle();
		conversation.setTitle(title);
		conversation.setParrentId(agreement.getAgreementId());
		conversation.setSendEmail(info.getSendEmail());
		if (!StringUtils.isEmpty(info.getConversationContent())) {
			conversation.setConversationContent(info.getConversationContent());
		} 
		if (!StringUtils.isEmpty(agreement.getLineId())) {
			conversation.setLineId(agreement.getLineId());
		}
		if (!StringUtils.isEmpty(currentAgency.getEmail())) {
			conversation.setUserName(currentAgency.getEmail());
		}
		conversation.setUserId(currentAgency.getMa());
		conversation.setSendDate(new Date());
		conversation.setResponseDate(new Date());
		conversation.setCreateDate(DbUtil.getSysDate(em));
		conversation.setRole("baoviet");
				
		String conversationId = this.insertConversation(conversation);
		conversation.setConversationId(conversationId);
		// Lưu file
		// data content
		if (info.getImgGycbhContents() != null && info.getImgGycbhContents().size() > 0) {
			for (FileContentDTO item : info.getImgGycbhContents()) {
				AttachmentDTO attachmenInfo = new AttachmentDTO();
				attachmenInfo.setAttachmentName(item.getFilename());
				if (!StringUtils.isEmpty(item.getContent())) {
					attachmenInfo.setContentFile(item.getContent());
				}
				attachmenInfo.setModifyDate(new Date());
				attachmenInfo.setTradeolSysdate(new Date());
				attachmenInfo.setUserId(currentAgency.getMa());
				attachmenInfo.setParrentId(conversationId);
				attachmenInfo.setIstransferred(0);
				String attachmentId = attachmentService.save(attachmenInfo);
				log.debug("Request to save Attachment, attachmentId{}", attachmentId);
			}
		}
		
		// chuyển trạng thái đơn sang yêu cầu đại lý bổ sung thông tin
		agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_BOSUNG_TT);
		agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_BOSUNG_TT);
		agreementRepository.save(agreementMapper.toEntity(agreement));
		
		return conversation;
	}
}
