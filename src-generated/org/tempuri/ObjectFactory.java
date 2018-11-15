
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuthTokenKeyHeader_QNAME = new QName("http://tempuri.org/", "AuthTokenKeyHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDMLoaiAnChi }
     * 
     */
    public GetDMLoaiAnChi createGetDMLoaiAnChi() {
        return new GetDMLoaiAnChi();
    }

    /**
     * Create an instance of {@link GetDMLoaiAnChiResponse }
     * 
     */
    public GetDMLoaiAnChiResponse createGetDMLoaiAnChiResponse() {
        return new GetDMLoaiAnChiResponse();
    }

    /**
     * Create an instance of {@link GetAnChiByAgencyResponse }
     * 
     */
    public GetAnChiByAgencyResponse createGetAnChiByAgencyResponse() {
        return new GetAnChiByAgencyResponse();
    }

    /**
     * Create an instance of {@link GetAnChiByAgency }
     * 
     */
    public GetAnChiByAgency createGetAnChiByAgency() {
        return new GetAnChiByAgency();
    }

    /**
     * Create an instance of {@link UpdateListAnChiResponse }
     * 
     */
    public UpdateListAnChiResponse createUpdateListAnChiResponse() {
        return new UpdateListAnChiResponse();
    }

    /**
     * Create an instance of {@link AuthTokenKeyHeader }
     * 
     */
    public AuthTokenKeyHeader createAuthTokenKeyHeader() {
        return new AuthTokenKeyHeader();
    }

    /**
     * Create an instance of {@link UpdateListAnChi }
     * 
     */
    public UpdateListAnChi createUpdateListAnChi() {
        return new UpdateListAnChi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthTokenKeyHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "AuthTokenKeyHeader")
    public JAXBElement<AuthTokenKeyHeader> createAuthTokenKeyHeader(AuthTokenKeyHeader value) {
        return new JAXBElement<AuthTokenKeyHeader>(_AuthTokenKeyHeader_QNAME, AuthTokenKeyHeader.class, null, value);
    }

}
