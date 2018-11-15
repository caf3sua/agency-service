package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.SmsSend;
import com.baoviet.agency.dto.SmsSendDTO;

/**
 * Mapper for the entity GnocCr and its DTO PurposeOfUsage.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SmsSendMapper extends EntityMapper <SmsSendDTO, SmsSend> {
}
