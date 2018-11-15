
package vn.bluezone.w2m;

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
 *         &lt;element name="SendMTResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "sendMTResult"
})
@XmlRootElement(name = "SendMTResponse")
public class SendMTResponse {

    @XmlElement(name = "SendMTResult")
    protected String sendMTResult;

    /**
     * Gets the value of the sendMTResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendMTResult() {
        return sendMTResult;
    }

    /**
     * Sets the value of the sendMTResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendMTResult(String value) {
        this.sendMTResult = value;
    }

}
