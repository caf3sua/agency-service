
package doc.glite;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DocGLitev2018", targetNamespace = "http://lite.ws.bv.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DocGLitev2018 {


    /**
     * 
     * @param password
     * @param userID
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getServerInfo", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.GetServerInfo")
    @ResponseWrapper(localName = "getServerInfoResponse", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.GetServerInfoResponse")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/getServerInfoRequest", output = "http://lite.ws.bv.com/DocGLitev2018/getServerInfoResponse")
    public String getServerInfo(
        @WebParam(name = "userID", targetNamespace = "")
        String userID,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param ltmpVersionNumber
     * @param ltmpCode
     * @param systemCode
     * @param ltmpLocale
     * @param reportData
     * @return
     *     returns doc.glite.ReportResponse
     */
    @WebMethod
    @WebResult(name = "ReportResponse", targetNamespace = "")
    @RequestWrapper(localName = "runReport", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.RunReport")
    @ResponseWrapper(localName = "runReportResponse", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.RunReportResponse")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/runReportRequest", output = "http://lite.ws.bv.com/DocGLitev2018/runReportResponse")
    public ReportResponse runReport(
        @WebParam(name = "systemCode", targetNamespace = "")
        String systemCode,
        @WebParam(name = "ltmpCode", targetNamespace = "")
        String ltmpCode,
        @WebParam(name = "ltmpVersionNumber", targetNamespace = "")
        String ltmpVersionNumber,
        @WebParam(name = "ltmpLocale", targetNamespace = "")
        String ltmpLocale,
        @WebParam(name = "reportData", targetNamespace = "")
        String reportData);

    /**
     * 
     * @param ltmpVersionNumber
     * @param ltmpCode
     * @param systemCode
     * @param ltmpLocale
     * @param reportData
     * @param deliveryDesc
     * @param deliveryOption
     * @return
     *     returns doc.glite.DeliveryResponse
     */
    @WebMethod
    @WebResult(name = "DeliveryResponse", targetNamespace = "")
    @RequestWrapper(localName = "deliveryService", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.DeliveryService")
    @ResponseWrapper(localName = "deliveryServiceResponse", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.DeliveryServiceResponse")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/deliveryServiceRequest", output = "http://lite.ws.bv.com/DocGLitev2018/deliveryServiceResponse")
    public DeliveryResponse deliveryService(
        @WebParam(name = "systemCode", targetNamespace = "")
        String systemCode,
        @WebParam(name = "ltmpCode", targetNamespace = "")
        String ltmpCode,
        @WebParam(name = "ltmpVersionNumber", targetNamespace = "")
        String ltmpVersionNumber,
        @WebParam(name = "ltmpLocale", targetNamespace = "")
        String ltmpLocale,
        @WebParam(name = "reportData", targetNamespace = "")
        String reportData,
        @WebParam(name = "deliveryOption", targetNamespace = "")
        String deliveryOption,
        @WebParam(name = "deliveryDesc", targetNamespace = "")
        String deliveryDesc);

    /**
     * 
     * @param ltmpVersionNumber
     * @param ltmpCode
     * @param systemCode
     * @param ltmpLocale
     * @param reportData
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "ReportResponse", targetNamespace = "")
    @RequestWrapper(localName = "runReportV1", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.RunReportV1")
    @ResponseWrapper(localName = "runReportV1Response", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.RunReportV1Response")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/runReportV1Request", output = "http://lite.ws.bv.com/DocGLitev2018/runReportV1Response")
    public String runReportV1(
        @WebParam(name = "systemCode", targetNamespace = "")
        String systemCode,
        @WebParam(name = "ltmpCode", targetNamespace = "")
        String ltmpCode,
        @WebParam(name = "ltmpVersionNumber", targetNamespace = "")
        String ltmpVersionNumber,
        @WebParam(name = "ltmpLocale", targetNamespace = "")
        String ltmpLocale,
        @WebParam(name = "reportData", targetNamespace = "")
        String reportData);

    /**
     * 
     * @param ltmpVersionNumber
     * @param ltmpCode
     * @param systemCode
     * @param ltmpLocale
     * @param reportData
     * @return
     *     returns java.util.List<doc.glite.ReportResponse>
     */
    @WebMethod
    @WebResult(name = "ReportResponse", targetNamespace = "")
    @RequestWrapper(localName = "runReportV2", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.RunReportV2")
    @ResponseWrapper(localName = "runReportV2Response", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.RunReportV2Response")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/runReportV2Request", output = "http://lite.ws.bv.com/DocGLitev2018/runReportV2Response")
    public List<ReportResponse> runReportV2(
        @WebParam(name = "systemCode", targetNamespace = "")
        String systemCode,
        @WebParam(name = "ltmpCode", targetNamespace = "")
        String ltmpCode,
        @WebParam(name = "ltmpVersionNumber", targetNamespace = "")
        String ltmpVersionNumber,
        @WebParam(name = "ltmpLocale", targetNamespace = "")
        String ltmpLocale,
        @WebParam(name = "reportData", targetNamespace = "")
        String reportData);

    /**
     * 
     * @param password
     * @param userID
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "forceGC", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.ForceGC")
    @ResponseWrapper(localName = "forceGCResponse", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.ForceGCResponse")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/forceGCRequest", output = "http://lite.ws.bv.com/DocGLitev2018/forceGCResponse")
    public String forceGC(
        @WebParam(name = "userID", targetNamespace = "")
        String userID,
        @WebParam(name = "password", targetNamespace = "")
        String password);

    /**
     * 
     * @param ltmpVersionNumber
     * @param ltmpCode
     * @param systemCode
     * @param ltmpLocale
     * @param reportData
     * @param deliveryDesc
     * @param deliveryOption
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "DeliveryResponse", targetNamespace = "")
    @RequestWrapper(localName = "deliveryServiceV1", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.DeliveryServiceV1")
    @ResponseWrapper(localName = "deliveryServiceV1Response", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.DeliveryServiceV1Response")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/deliveryServiceV1Request", output = "http://lite.ws.bv.com/DocGLitev2018/deliveryServiceV1Response")
    public String deliveryServiceV1(
        @WebParam(name = "systemCode", targetNamespace = "")
        String systemCode,
        @WebParam(name = "ltmpCode", targetNamespace = "")
        String ltmpCode,
        @WebParam(name = "ltmpVersionNumber", targetNamespace = "")
        String ltmpVersionNumber,
        @WebParam(name = "ltmpLocale", targetNamespace = "")
        String ltmpLocale,
        @WebParam(name = "reportData", targetNamespace = "")
        String reportData,
        @WebParam(name = "deliveryOption", targetNamespace = "")
        String deliveryOption,
        @WebParam(name = "deliveryDesc", targetNamespace = "")
        String deliveryDesc);

    /**
     * 
     * @param ltmpVersionNumber
     * @param ltmpCode
     * @param systemCode
     * @param ltmpLocale
     * @param reportData
     * @param deliveryDesc
     * @param deliveryOption
     * @return
     *     returns java.util.List<doc.glite.DeliveryResponse>
     */
    @WebMethod
    @WebResult(name = "DeliveryResponse", targetNamespace = "")
    @RequestWrapper(localName = "deliveryServiceV2", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.DeliveryServiceV2")
    @ResponseWrapper(localName = "deliveryServiceV2Response", targetNamespace = "http://lite.ws.bv.com/", className = "doc.glite.DeliveryServiceV2Response")
    @Action(input = "http://lite.ws.bv.com/DocGLitev2018/deliveryServiceV2Request", output = "http://lite.ws.bv.com/DocGLitev2018/deliveryServiceV2Response")
    public List<DeliveryResponse> deliveryServiceV2(
        @WebParam(name = "systemCode", targetNamespace = "")
        String systemCode,
        @WebParam(name = "ltmpCode", targetNamespace = "")
        String ltmpCode,
        @WebParam(name = "ltmpVersionNumber", targetNamespace = "")
        String ltmpVersionNumber,
        @WebParam(name = "ltmpLocale", targetNamespace = "")
        String ltmpLocale,
        @WebParam(name = "reportData", targetNamespace = "")
        String reportData,
        @WebParam(name = "deliveryOption", targetNamespace = "")
        String deliveryOption,
        @WebParam(name = "deliveryDesc", targetNamespace = "")
        String deliveryDesc);

}
