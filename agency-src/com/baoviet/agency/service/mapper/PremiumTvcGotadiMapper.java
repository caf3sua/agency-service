package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.PremiumTvcGotadi;
import com.baoviet.agency.dto.PremiumTvcGotadiDTO;

/**
 * Mapper for the entity GnocCr and its DTO Contact.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PremiumTvcGotadiMapper extends EntityMapper <PremiumTvcGotadiDTO, PremiumTvcGotadi> {
}
