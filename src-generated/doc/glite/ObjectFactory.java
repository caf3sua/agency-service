
package doc.glite;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the doc.glite package. 
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

    private final static QName _RunReportV1_QNAME = new QName("http://lite.ws.bv.com/", "runReportV1");
    private final static QName _RunReportV2_QNAME = new QName("http://lite.ws.bv.com/", "runReportV2");
    private final static QName _DeliveryServiceV1_QNAME = new QName("http://lite.ws.bv.com/", "deliveryServiceV1");
    private final static QName _DeliveryServiceV2_QNAME = new QName("http://lite.ws.bv.com/", "deliveryServiceV2");
    private final static QName _RunReportV1Response_QNAME = new QName("http://lite.ws.bv.com/", "runReportV1Response");
    private final static QName _DeliveryService_QNAME = new QName("http://lite.ws.bv.com/", "deliveryService");
    private final static QName _GetServerInfoResponse_QNAME = new QName("http://lite.ws.bv.com/", "getServerInfoResponse");
    private final static QName _GetServerInfo_QNAME = new QName("http://lite.ws.bv.com/", "getServerInfo");
    private final static QName _ForceGCResponse_QNAME = new QName("http://lite.ws.bv.com/", "forceGCResponse");
    private final static QName _RunReport_QNAME = new QName("http://lite.ws.bv.com/", "runReport");
    private final static QName _DeliveryServiceResponse_QNAME = new QName("http://lite.ws.bv.com/", "deliveryServiceResponse");
    private final static QName _RunReportV2Response_QNAME = new QName("http://lite.ws.bv.com/", "runReportV2Response");
    private final static QName _DeliveryServiceV1Response_QNAME = new QName("http://lite.ws.bv.com/", "deliveryServiceV1Response");
    private final static QName _ForceGC_QNAME = new QName("http://lite.ws.bv.com/", "forceGC");
    private final static QName _RunReportResponse_QNAME = new QName("http://lite.ws.bv.com/", "runReportResponse");
    private final static QName _DeliveryServiceV2Response_QNAME = new QName("http://lite.ws.bv.com/", "deliveryServiceV2Response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: doc.glite
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeliveryResponse }
     * 
     */
    public DeliveryResponse createDeliveryResponse() {
        return new DeliveryResponse();
    }

    /**
     * Create an instance of {@link ReportResponse }
     * 
     */
    public ReportResponse createReportResponse() {
        return new ReportResponse();
    }

    /**
     * Create an instance of {@link DeliveryService }
     * 
     */
    public DeliveryService createDeliveryService() {
        return new DeliveryService();
    }

    /**
     * Create an instance of {@link GetServerInfoResponse }
     * 
     */
    public GetServerInfoResponse createGetServerInfoResponse() {
        return new GetServerInfoResponse();
    }

    /**
     * Create an instance of {@link GetServerInfo }
     * 
     */
    public GetServerInfo createGetServerInfo() {
        return new GetServerInfo();
    }

    /**
     * Create an instance of {@link DeliveryServiceV1 }
     * 
     */
    public DeliveryServiceV1 createDeliveryServiceV1() {
        return new DeliveryServiceV1();
    }

    /**
     * Create an instance of {@link DeliveryServiceV2 }
     * 
     */
    public DeliveryServiceV2 createDeliveryServiceV2() {
        return new DeliveryServiceV2();
    }

    /**
     * Create an instance of {@link RunReportV1Response }
     * 
     */
    public RunReportV1Response createRunReportV1Response() {
        return new RunReportV1Response();
    }

    /**
     * Create an instance of {@link RunReportV1 }
     * 
     */
    public RunReportV1 createRunReportV1() {
        return new RunReportV1();
    }

    /**
     * Create an instance of {@link RunReportV2 }
     * 
     */
    public RunReportV2 createRunReportV2() {
        return new RunReportV2();
    }

    /**
     * Create an instance of {@link DeliveryServiceV2Response }
     * 
     */
    public DeliveryServiceV2Response createDeliveryServiceV2Response() {
        return new DeliveryServiceV2Response();
    }

    /**
     * Create an instance of {@link DeliveryServiceV1Response }
     * 
     */
    public DeliveryServiceV1Response createDeliveryServiceV1Response() {
        return new DeliveryServiceV1Response();
    }

    /**
     * Create an instance of {@link ForceGC }
     * 
     */
    public ForceGC createForceGC() {
        return new ForceGC();
    }

    /**
     * Create an instance of {@link RunReportResponse }
     * 
     */
    public RunReportResponse createRunReportResponse() {
        return new RunReportResponse();
    }

    /**
     * Create an instance of {@link ForceGCResponse }
     * 
     */
    public ForceGCResponse createForceGCResponse() {
        return new ForceGCResponse();
    }

    /**
     * Create an instance of {@link RunReport }
     * 
     */
    public RunReport createRunReport() {
        return new RunReport();
    }

    /**
     * Create an instance of {@link DeliveryServiceResponse }
     * 
     */
    public DeliveryServiceResponse createDeliveryServiceResponse() {
        return new DeliveryServiceResponse();
    }

    /**
     * Create an instance of {@link RunReportV2Response }
     * 
     */
    public RunReportV2Response createRunReportV2Response() {
        return new RunReportV2Response();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReportV1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "runReportV1")
    public JAXBElement<RunReportV1> createRunReportV1(RunReportV1 value) {
        return new JAXBElement<RunReportV1>(_RunReportV1_QNAME, RunReportV1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReportV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "runReportV2")
    public JAXBElement<RunReportV2> createRunReportV2(RunReportV2 value) {
        return new JAXBElement<RunReportV2>(_RunReportV2_QNAME, RunReportV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryServiceV1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "deliveryServiceV1")
    public JAXBElement<DeliveryServiceV1> createDeliveryServiceV1(DeliveryServiceV1 value) {
        return new JAXBElement<DeliveryServiceV1>(_DeliveryServiceV1_QNAME, DeliveryServiceV1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryServiceV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "deliveryServiceV2")
    public JAXBElement<DeliveryServiceV2> createDeliveryServiceV2(DeliveryServiceV2 value) {
        return new JAXBElement<DeliveryServiceV2>(_DeliveryServiceV2_QNAME, DeliveryServiceV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReportV1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "runReportV1Response")
    public JAXBElement<RunReportV1Response> createRunReportV1Response(RunReportV1Response value) {
        return new JAXBElement<RunReportV1Response>(_RunReportV1Response_QNAME, RunReportV1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "deliveryService")
    public JAXBElement<DeliveryService> createDeliveryService(DeliveryService value) {
        return new JAXBElement<DeliveryService>(_DeliveryService_QNAME, DeliveryService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServerInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "getServerInfoResponse")
    public JAXBElement<GetServerInfoResponse> createGetServerInfoResponse(GetServerInfoResponse value) {
        return new JAXBElement<GetServerInfoResponse>(_GetServerInfoResponse_QNAME, GetServerInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServerInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "getServerInfo")
    public JAXBElement<GetServerInfo> createGetServerInfo(GetServerInfo value) {
        return new JAXBElement<GetServerInfo>(_GetServerInfo_QNAME, GetServerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForceGCResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "forceGCResponse")
    public JAXBElement<ForceGCResponse> createForceGCResponse(ForceGCResponse value) {
        return new JAXBElement<ForceGCResponse>(_ForceGCResponse_QNAME, ForceGCResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "runReport")
    public JAXBElement<RunReport> createRunReport(RunReport value) {
        return new JAXBElement<RunReport>(_RunReport_QNAME, RunReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "deliveryServiceResponse")
    public JAXBElement<DeliveryServiceResponse> createDeliveryServiceResponse(DeliveryServiceResponse value) {
        return new JAXBElement<DeliveryServiceResponse>(_DeliveryServiceResponse_QNAME, DeliveryServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReportV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "runReportV2Response")
    public JAXBElement<RunReportV2Response> createRunReportV2Response(RunReportV2Response value) {
        return new JAXBElement<RunReportV2Response>(_RunReportV2Response_QNAME, RunReportV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryServiceV1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "deliveryServiceV1Response")
    public JAXBElement<DeliveryServiceV1Response> createDeliveryServiceV1Response(DeliveryServiceV1Response value) {
        return new JAXBElement<DeliveryServiceV1Response>(_DeliveryServiceV1Response_QNAME, DeliveryServiceV1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForceGC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "forceGC")
    public JAXBElement<ForceGC> createForceGC(ForceGC value) {
        return new JAXBElement<ForceGC>(_ForceGC_QNAME, ForceGC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "runReportResponse")
    public JAXBElement<RunReportResponse> createRunReportResponse(RunReportResponse value) {
        return new JAXBElement<RunReportResponse>(_RunReportResponse_QNAME, RunReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliveryServiceV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lite.ws.bv.com/", name = "deliveryServiceV2Response")
    public JAXBElement<DeliveryServiceV2Response> createDeliveryServiceV2Response(DeliveryServiceV2Response value) {
        return new JAXBElement<DeliveryServiceV2Response>(_DeliveryServiceV2Response_QNAME, DeliveryServiceV2Response.class, null, value);
    }

}
