package com.baoviet.agency.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.AgentDocument;
import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.dto.AgentDocumentDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgentDocumentRepository;
import com.baoviet.agency.repository.AttachmentRepository;
import com.baoviet.agency.service.AgentDocumentService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.UEncrypt;
import com.baoviet.agency.utils.UFile;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for promotion resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "document")
public class AgentDocumentResource {

	private final Logger log = LoggerFactory.getLogger(AgentDocumentResource.class);

	@Autowired
	private AgentDocumentService agentDocumentService;

	@Autowired
	private AgentDocumentRepository agentDocumentRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_DOCUMENT_CREATE')")
	@PostMapping("/add")
	@Timed
	public ResponseEntity<AgentDocumentDTO> add(@Valid @RequestBody AgentDocumentDTO obj) throws URISyntaxException {
		log.debug("REST request to add");

		AgentDocumentDTO result = agentDocumentService.save(obj);
		
		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_DOCUMENT_VIEW')")
	@GetMapping("/get-all")
	@Timed
	public ResponseEntity<List<AgentDocumentDTO>> getAllDocument() throws URISyntaxException {
		log.debug("REST request to getAllDocument");

		List<AgentDocumentDTO> lstAgentDocument = agentDocumentService.getAll();
		
		// Return data
		return new ResponseEntity<>(lstAgentDocument, HttpStatus.OK);
	}

	@GetMapping("/download/{id}")
	@Timed
	public ResponseEntity<AgentDocumentDTO> download(final HttpServletRequest request,  final HttpServletResponse response
			, @PathVariable String id) throws URISyntaxException, IOException {
		log.debug("REST request to download document, id: {}", id);

		AgentDocument entity = agentDocumentRepository.findOne(id);
		
		// Update number download
		if (entity.getNumberDownload() != null) {
			entity.setNumberDownload(entity.getNumberDownload() + 1);
		} else {
			entity.setNumberDownload(1l);
		}
		entity = agentDocumentRepository.save(entity);
		
		// Process download
		response.reset();
		
		response.setContentType("application/octet-stream");
		response.setContentLength(entity.getContent().length);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + entity.getFileName() + "\"");
		
		ServletOutputStream outputStream = response.getOutputStream();
	    outputStream.write(entity.getContent()); 
	    outputStream.flush();
	    outputStream.close();
		
		// Return data
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@GetMapping("/download-attachment/{id}")
	@Timed
	public ResponseEntity<AgentDocumentDTO> downloadAttachmentConversation(final HttpServletRequest request,  final HttpServletResponse response
			, @PathVariable String id) throws AgencyBusinessException, IOException {
		log.debug("REST request to downloadAttachmentConversation document, id: {}", id);

		Attachment entity = attachmentRepository.findOne(id);
		if (entity == null) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file đính kèm " + id);
		}
		// Process download
		response.reset();
		
		response.setContentType("application/octet-stream");
		response.setContentLength(entity.getContent().length);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + entity.getAttachmentName() + "\"");
		
		ServletOutputStream outputStream = response.getOutputStream();
	    outputStream.write(entity.getContent()); 
	    outputStream.flush();
	    outputStream.close();
		
		// Return data
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	// Import file excel
//	@GET
//    @Path("/downloadImport")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response downloadFileImport(@Context HttpServletRequest request)throws Exception;
	
	
	@PostMapping("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadATTT(List<org.apache.cxf.jaxrs.ext.multipart.Attachment> attachments, @Context HttpServletRequest request) {
//		String folderParam = UString.getSafeFileName(request.getParameter("folder"));
		String folderUpload = "";
		String folderParam = "";
		String filePathReturn;
		Map<String, List> returnMap = new HashMap();
		List<String> listFilePathReturn = new ArrayList<String>();

		for (org.apache.cxf.jaxrs.ext.multipart.Attachment attachment : attachments) {
			DataHandler dataHandler = attachment.getDataHandler();

			// get filename to be uploaded
			MultivaluedMap<String, String> multivaluedMap = attachment.getHeaders();
			String fileName = UFile.getFileName(multivaluedMap);

			// write & upload file to server
			try (InputStream inputStream = dataHandler.getInputStream();) {
				String filePath = UFile.writeToFileServerATTT2(inputStream, fileName, folderParam, folderUpload);
				filePathReturn = UEncrypt.encryptFileUploadPath(filePath);
				listFilePathReturn.add(filePathReturn);
			} catch (Exception ex) {
//				throw new BusinessException("Loi khi save file", ex);
			}
		}
		returnMap.put("data", listFilePathReturn);
		return Response.ok(listFilePathReturn).build();
	}
//    
//    @POST
//    @Path("/uploadTemp")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response uploadTemp(List<Attachment> attachments, @Context HttpServletRequest request);   
//    
//    @GET
//    @Path("/downloadFileATTT")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response downloadFileATTT(@Context HttpServletRequest request) throws Exception;
}
