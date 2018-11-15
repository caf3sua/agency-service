package com.baoviet.agency.service;

import com.baoviet.agency.domain.KcareRate;

/**
 * Service Interface for managing AgencyPremium.
 */
public interface AgencyPremiumService {

    KcareRate getAllKcareRateByParams(int age, String sex, String program);
}

