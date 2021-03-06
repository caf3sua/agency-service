
package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ReceiveMTSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ReceiveMTSoap {


    /**
     * Thong tin de gui Tin nhan {input_type:loai ung dung do he thong SMSC cung cap,mess_type:0-cskh 1-Quang cao,mobie_number:sdt dang 0912-012,content:Noi dung khong vuot qua 160 ky tu, requestid:brandname/dau so de gui,typemt:Ky hieu BR/NUM,sending_time:Thoi gian gui dang dd/MM/yyyy HH:mm:ss-de trong thi gui ngay,username:tai khoan xac thuc,password:mat khau xac thuc},pk_corp:Ma Tapdoan,pk_serviceid:ID duy nhat cua app, sms_mo_id, deptcode1:Cong Ty TV, deptcode2:Phong Ban
     * 
     * @param deptcode2
     * @param pkCorp
     * @param pkServiceid
     * @param userName
     * @param content
     * @param typemt
     * @param sendingTime
     * @param smsMoId
     * @param password
     * @param requestid
     * @param inputType
     * @param deptcode1
     * @param mobieNumber
     * @param messType
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "InputMT", action = "http://tempuri.org/InputMT")
    @WebResult(name = "InputMTResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "InputMT", targetNamespace = "http://tempuri.org/", className = "org.tempuri.InputMT")
    @ResponseWrapper(localName = "InputMTResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.InputMTResponse")
    public String inputMT(
        @WebParam(name = "input_type", targetNamespace = "http://tempuri.org/")
        int inputType,
        @WebParam(name = "mess_type", targetNamespace = "http://tempuri.org/")
        int messType,
        @WebParam(name = "mobie_number", targetNamespace = "http://tempuri.org/")
        String mobieNumber,
        @WebParam(name = "content", targetNamespace = "http://tempuri.org/")
        String content,
        @WebParam(name = "requestid", targetNamespace = "http://tempuri.org/")
        String requestid,
        @WebParam(name = "typemt", targetNamespace = "http://tempuri.org/")
        String typemt,
        @WebParam(name = "sending_time", targetNamespace = "http://tempuri.org/")
        String sendingTime,
        @WebParam(name = "userName", targetNamespace = "http://tempuri.org/")
        String userName,
        @WebParam(name = "password", targetNamespace = "http://tempuri.org/")
        String password,
        @WebParam(name = "pk_corp", targetNamespace = "http://tempuri.org/")
        String pkCorp,
        @WebParam(name = "pk_serviceid", targetNamespace = "http://tempuri.org/")
        String pkServiceid,
        @WebParam(name = "sms_mo_id", targetNamespace = "http://tempuri.org/")
        String smsMoId,
        @WebParam(name = "deptcode1", targetNamespace = "http://tempuri.org/")
        String deptcode1,
        @WebParam(name = "deptcode2", targetNamespace = "http://tempuri.org/")
        String deptcode2);

}
