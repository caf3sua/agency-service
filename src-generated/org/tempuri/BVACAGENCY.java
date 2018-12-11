
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "BVAC_AGENCY", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://172.29.1.76/INSUREJ_BRANDNAME/SMSSendService.asmx?wsdl")
public class BVACAGENCY
    extends Service
{

    private final static URL BVACAGENCY_WSDL_LOCATION;
    private final static WebServiceException BVACAGENCY_EXCEPTION;
    private final static QName BVACAGENCY_QNAME = new QName("http://tempuri.org/", "BVAC_AGENCY");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://172.29.1.76/INSUREJ_BRANDNAME/SMSSendService.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BVACAGENCY_WSDL_LOCATION = url;
        BVACAGENCY_EXCEPTION = e;
    }

    public BVACAGENCY() {
        super(__getWsdlLocation(), BVACAGENCY_QNAME);
    }

    public BVACAGENCY(WebServiceFeature... features) {
        super(__getWsdlLocation(), BVACAGENCY_QNAME, features);
    }

    public BVACAGENCY(URL wsdlLocation) {
        super(wsdlLocation, BVACAGENCY_QNAME);
    }

    public BVACAGENCY(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BVACAGENCY_QNAME, features);
    }

    public BVACAGENCY(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BVACAGENCY(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BVACAGENCYSoap
     */
    @WebEndpoint(name = "BVAC_AGENCYSoap")
    public BVACAGENCYSoap getBVACAGENCYSoap() {
        return super.getPort(new QName("http://tempuri.org/", "BVAC_AGENCYSoap"), BVACAGENCYSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BVACAGENCYSoap
     */
    @WebEndpoint(name = "BVAC_AGENCYSoap")
    public BVACAGENCYSoap getBVACAGENCYSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BVAC_AGENCYSoap"), BVACAGENCYSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BVACAGENCY_EXCEPTION!= null) {
            throw BVACAGENCY_EXCEPTION;
        }
        return BVACAGENCY_WSDL_LOCATION;
    }

}
