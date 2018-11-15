package com.baoviet.agency.service.mapper;

import org.mapstruct.Mapper;

import com.baoviet.agency.domain.CategoryReminder;
import com.baoviet.agency.dto.CategoryReminderDTO;

/**
 * Mapper for the entity GnocCr and its DTO CategoryReminder.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryReminderMapper extends EntityMapper <CategoryReminderDTO, CategoryReminder> {
}
