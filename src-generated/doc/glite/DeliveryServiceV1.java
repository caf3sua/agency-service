
package doc.glite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deliveryServiceV1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deliveryServiceV1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="systemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ltmpCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ltmpVersionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ltmpLocale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryOption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deliveryDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deliveryServiceV1", propOrder = {
    "systemCode",
    "ltmpCode",
    "ltmpVersionNumber",
    "ltmpLocale",
    "reportData",
    "deliveryOption",
    "deliveryDesc"
})
public class DeliveryServiceV1 {

    protected String systemCode;
    protected String ltmpCode;
    protected String ltmpVersionNumber;
    protected String ltmpLocale;
    protected String reportData;
    protected String deliveryOption;
    protected String deliveryDesc;

    /**
     * Gets the value of the systemCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Sets the value of the systemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemCode(String value) {
        this.systemCode = value;
    }

    /**
     * Gets the value of the ltmpCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLtmpCode() {
        return ltmpCode;
    }

    /**
     * Sets the value of the ltmpCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLtmpCode(String value) {
        this.ltmpCode = value;
    }

    /**
     * Gets the value of the ltmpVersionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLtmpVersionNumber() {
        return ltmpVersionNumber;
    }

    /**
     * Sets the value of the ltmpVersionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLtmpVersionNumber(String value) {
        this.ltmpVersionNumber = value;
    }

    /**
     * Gets the value of the ltmpLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLtmpLocale() {
        return ltmpLocale;
    }

    /**
     * Sets the value of the ltmpLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLtmpLocale(String value) {
        this.ltmpLocale = value;
    }

    /**
     * Gets the value of the reportData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportData() {
        return reportData;
    }

    /**
     * Sets the value of the reportData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportData(String value) {
        this.reportData = value;
    }

    /**
     * Gets the value of the deliveryOption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryOption() {
        return deliveryOption;
    }

    /**
     * Sets the value of the deliveryOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryOption(String value) {
        this.deliveryOption = value;
    }

    /**
     * Gets the value of the deliveryDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryDesc() {
        return deliveryDesc;
    }

    /**
     * Sets the value of the deliveryDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryDesc(String value) {
        this.deliveryDesc = value;
    }

}
