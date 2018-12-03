package com.baoviet.agency.web.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baoviet.agency.domain.AgentDocument;
import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.dto.AgentDocumentDTO;
import com.baoviet.agency.dto.excel.BasePathInfoDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgentDocumentRepository;
import com.baoviet.agency.repository.AttachmentRepository;
import com.baoviet.agency.service.AgentDocumentService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.MediaTypeUtils;
import com.baoviet.agency.utils.UEncrypt;
import com.baoviet.agency.utils.UFile;
import com.baoviet.agency.utils.UString;
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
	
	@Autowired
    private ServletContext servletContext;
        
	@Value("${spring.upload.folder-upload}")
	private String folderUpload;
	
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
//	  @GET
//    @Path("/downloadImport")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response downloadFileImport(@Context HttpServletRequest request)throws Exception;
	
	@PostMapping("/upload-file")
    public ResponseEntity<BasePathInfoDTO> uploadFile(@RequestParam("file") MultipartFile file) throws Exception  {
		BasePathInfoDTO response = new BasePathInfoDTO();
		
		if (file.isEmpty()) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file upload");
        }
		
		long currentTime = System.currentTimeMillis();

		// Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folderUpload + currentTime + "_" + UString.getSafeFileName(file.getOriginalFilename()));
        Files.write(path, bytes);
        
        String filePathReturn = UEncrypt.encryptFileUploadPath(path.toString());
        System.out.println(filePathReturn);
        
        response.setPath(filePathReturn);
		
		// Return data
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/download-template")
    public ResponseEntity<InputStreamResource> downloadTemplate(@RequestParam("filename") String filename, final HttpServletResponse response) throws Exception  {
		
		if (StringUtils.isEmpty(filename)) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại đường dẫn đến file upload");
        }
		
		File file = new ClassPathResource("/templates/" + filename).getFile();
		//File file = ResourceUtils.getFile("src/main/resources/templates/" + filename);
		if (!file.exists()) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file");
		}
		
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
 
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
 
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
	}
	
	@GetMapping("/download-file")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam("path") String path, final HttpServletResponse response) throws Exception  {
		
		if (StringUtils.isEmpty(path)) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại đường dẫn đến file upload");
        }
		
		String filename = UEncrypt.decryptFileUploadPath(path);
		if (StringUtils.isEmpty(filename)) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file");
        }
		
		File file = new File(filename);
		
		int lastIndex = filename.lastIndexOf(File.separatorChar);
		String filenameReturn = filename.substring(lastIndex + 1);
		
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filenameReturn);
 
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
 
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + UString.extractOriginalFileName(file.getName()))
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
	}
}
