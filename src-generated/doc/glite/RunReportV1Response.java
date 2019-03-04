
package doc.glite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for runReportV1Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="runReportV1Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReportResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "runReportV1Response", propOrder = {
    "reportResponse"
})
public class RunReportV1Response {

    @XmlElement(name = "ReportResponse")
    protected String reportResponse;

    /**
     * Gets the value of the reportResponse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportResponse() {
        return reportResponse;
    }

    /**
     * Sets the value of the reportResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportResponse(String value) {
        this.reportResponse = value;
    }

}
