
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="input_type" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mess_type" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mobie_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typemt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sending_time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pk_corp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pk_serviceid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sms_mo_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deptcode1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deptcode2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inputType",
    "messType",
    "mobieNumber",
    "content",
    "requestid",
    "typemt",
    "sendingTime",
    "userName",
    "password",
    "pkCorp",
    "pkServiceid",
    "smsMoId",
    "deptcode1",
    "deptcode2"
})
@XmlRootElement(name = "InputMT")
public class InputMT {

    @XmlElement(name = "input_type")
    protected int inputType;
    @XmlElement(name = "mess_type")
    protected int messType;
    @XmlElement(name = "mobie_number")
    protected String mobieNumber;
    protected String content;
    protected String requestid;
    protected String typemt;
    @XmlElement(name = "sending_time")
    protected String sendingTime;
    protected String userName;
    protected String password;
    @XmlElement(name = "pk_corp")
    protected String pkCorp;
    @XmlElement(name = "pk_serviceid")
    protected String pkServiceid;
    @XmlElement(name = "sms_mo_id")
    protected String smsMoId;
    protected String deptcode1;
    protected String deptcode2;

    /**
     * Gets the value of the inputType property.
     * 
     */
    public int getInputType() {
        return inputType;
    }

    /**
     * Sets the value of the inputType property.
     * 
     */
    public void setInputType(int value) {
        this.inputType = value;
    }

    /**
     * Gets the value of the messType property.
     * 
     */
    public int getMessType() {
        return messType;
    }

    /**
     * Sets the value of the messType property.
     * 
     */
    public void setMessType(int value) {
        this.messType = value;
    }

    /**
     * Gets the value of the mobieNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobieNumber() {
        return mobieNumber;
    }

    /**
     * Sets the value of the mobieNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobieNumber(String value) {
        this.mobieNumber = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the requestid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestid() {
        return requestid;
    }

    /**
     * Sets the value of the requestid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestid(String value) {
        this.requestid = value;
    }

    /**
     * Gets the value of the typemt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypemt() {
        return typemt;
    }

    /**
     * Sets the value of the typemt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypemt(String value) {
        this.typemt = value;
    }

    /**
     * Gets the value of the sendingTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingTime() {
        return sendingTime;
    }

    /**
     * Sets the value of the sendingTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingTime(String value) {
        this.sendingTime = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the pkCorp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPkCorp() {
        return pkCorp;
    }

    /**
     * Sets the value of the pkCorp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPkCorp(String value) {
        this.pkCorp = value;
    }

    /**
     * Gets the value of the pkServiceid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPkServiceid() {
        return pkServiceid;
    }

    /**
     * Sets the value of the pkServiceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPkServiceid(String value) {
        this.pkServiceid = value;
    }

    /**
     * Gets the value of the smsMoId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmsMoId() {
        return smsMoId;
    }

    /**
     * Sets the value of the smsMoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmsMoId(String value) {
        this.smsMoId = value;
    }

    /**
     * Gets the value of the deptcode1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeptcode1() {
        return deptcode1;
    }

    /**
     * Sets the value of the deptcode1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeptcode1(String value) {
        this.deptcode1 = value;
    }

    /**
     * Gets the value of the deptcode2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeptcode2() {
        return deptcode2;
    }

    /**
     * Sets the value of the deptcode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeptcode2(String value) {
        this.deptcode2 = value;
    }

}
