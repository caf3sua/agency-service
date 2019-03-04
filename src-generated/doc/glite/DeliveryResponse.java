
package doc.glite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deliveryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deliveryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reportStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportStatusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deliveryResponse", namespace = "", propOrder = {
    "reportStatus",
    "reportStatusMessage"
})
public class DeliveryResponse {

    protected String reportStatus;
    protected String reportStatusMessage;

    /**
     * Gets the value of the reportStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportStatus() {
        return reportStatus;
    }

    /**
     * Sets the value of the reportStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportStatus(String value) {
        this.reportStatus = value;
    }

    /**
     * Gets the value of the reportStatusMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportStatusMessage() {
        return reportStatusMessage;
    }

    /**
     * Sets the value of the reportStatusMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportStatusMessage(String value) {
        this.reportStatusMessage = value;
    }

}
