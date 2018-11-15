package com.baoviet.agency.utils.logging;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

	private String wsUsername;
    
	private String wsPassword;
    
	
	public SOAPLoggingHandler() {
		super();
	}
	
	public SOAPLoggingHandler(String wsUsername, String wsPassword) {
		super();
		this.wsUsername = wsUsername;
		this.wsPassword = wsPassword;
	}

	@Override
	public void close(MessageContext arg0) {
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		// if this is a request, true for outbound messages, false for inbound
		if (isRequest) {

			try {
				SOAPMessage soapMsg = context.getMessage();
				SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnv.getHeader();

				// if no header, add one
				if (soapHeader == null) {
					soapHeader = soapEnv.addHeader();
				}

				// add a soap header, name as "mac address"
				QName qname = new QName("http://tempuri.org/", "AuthTokenKeyHeader");
				SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);
				
				soapHeaderElement.addChildElement(soapEnv.createName("userName", "", "http://tempuri.org/")).addTextNode(wsUsername);
				soapHeaderElement.addChildElement(soapEnv.createName("password", "", "http://tempuri.org/")).addTextNode(wsPassword);
				soapMsg.saveChanges();
			} catch (SOAPException e) {
				System.err.println(e);
			}
		}

		SOAPMessage msg = context.getMessage();
		try {
			OutputStream out = new ByteArrayOutputStream();
			msg.writeTo(out);

			log.debug("print SOAP message: ");
			log.debug("{}", StringUtils.trim(formatXml(out.toString())));
		} catch (Exception ex) {
		}
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return Collections.EMPTY_SET;
	}

	public String formatXml(String xml) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(parseXml(xml));
			transformer.transform(source, result);
			return result.getWriter().toString();
		} catch (Exception e) {
			return xml;
		}
	}

	private Document parseXml(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
