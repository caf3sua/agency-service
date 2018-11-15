package com.baoviet.agency.dto.momo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ExtraDataDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<BookingInfoDTO> bookingInfo;
}
