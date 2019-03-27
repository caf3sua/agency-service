package com.baoviet.agency.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ExchangeRateService;
import com.baoviet.agency.service.FilesService;
import com.baoviet.agency.service.ReportService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.ReportBchhVM;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for Gnoc CR resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "test")
public class TestResource {

	private final Logger log = LoggerFactory.getLogger(TestResource.class);

	private static final String ENTITY_NAME = "test";

	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private CodeManagementService codeManagementService;

	@Autowired
	private FilesService filesService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ReportService reportService;

	@GetMapping("/anchi")
	@Timed
	public ResponseEntity getAll() throws URISyntaxException {
		log.debug("REST request to getAll contact");

		// Return data
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(path = "/report", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	@Timed
	public ResponseEntity<String> saveFiles(@Valid @RequestBody ReportBchhVM param) throws IOException {

		// Save to disk

		// String content = filesDTO.getContentFile();
		//// File file = new File( "D:/des.jpg" );
		//// byte[] bytes = Base64 .decode(content.getBytes());
		//// FileUtils.writeByteArrayToFile( file, bytes );
		//
		// BASE64Decoder decoder = new BASE64Decoder();
		// byte[] imageByte = decoder.decodeBuffer(content);
		//// ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		//// image = ImageIO.read(bis);
		//// bis.close();
		// File file = new File( "D:/" + filesDTO.getFullName());
		// FileUtils.writeByteArrayToFile( file, imageByte );
		//
		// String id = null;
		// id = filesService.save(filesDTO);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@GetMapping("/detect")
	@ResponseBody
	public String home(Device device) {
	    String msg = null;
	    if (device.isMobile()) {
	        msg = "mobile";
	    } else if (device.isTablet()) {
	        msg = "tablet";
	    } else {
	        msg = "desktop";
	    }
	    return msg + " device on platform " + device.getDevicePlatform().name();
	}

}
