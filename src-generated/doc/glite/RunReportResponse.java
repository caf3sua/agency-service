
package doc.glite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for runReportResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="runReportResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReportResponse" type="{}reportResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "runReportResponse", propOrder = {
    "reportResponse"
})
public class RunReportResponse {

    @XmlElement(name = "ReportResponse")
    protected ReportResponse reportResponse;

    /**
     * Gets the value of the reportResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ReportResponse }
     *     
     */
    public ReportResponse getReportResponse() {
        return reportResponse;
    }

    /**
     * Sets the value of the reportResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportResponse }
     *     
     */
    public void setReportResponse(ReportResponse value) {
        this.reportResponse = value;
    }

}
