
package doc.glite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reportResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reportBytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="reportContentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportLocale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "reportResponse", namespace = "", propOrder = {
    "reportBytes",
    "reportContentType",
    "reportLocale",
    "reportStatus",
    "reportStatusMessage"
})
public class ReportResponse {

    protected byte[] reportBytes;
    protected String reportContentType;
    protected String reportLocale;
    protected String reportStatus;
    protected String reportStatusMessage;

    /**
     * Gets the value of the reportBytes property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getReportBytes() {
        return reportBytes;
    }

    /**
     * Sets the value of the reportBytes property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setReportBytes(byte[] value) {
        this.reportBytes = value;
    }

    /**
     * Gets the value of the reportContentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportContentType() {
        return reportContentType;
    }

    /**
     * Sets the value of the reportContentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportContentType(String value) {
        this.reportContentType = value;
    }

    /**
     * Gets the value of the reportLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportLocale() {
        return reportLocale;
    }

    /**
     * Sets the value of the reportLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportLocale(String value) {
        this.reportLocale = value;
    }

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
