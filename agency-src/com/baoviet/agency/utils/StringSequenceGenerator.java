package com.baoviet.agency.utils;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGeneratorHelper.BigDecimalHolder;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.id.SequenceGenerator;

@SuppressWarnings("deprecation")
public class StringSequenceGenerator extends SequenceGenerator {
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) {
		return super.generate( session, obj ).toString();
	}

    protected IntegralDataTypeHolder buildHolder() {
        return new BigDecimalHolder();
    }
}
