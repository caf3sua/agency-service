
package doc.glite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deliveryServiceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deliveryServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeliveryResponse" type="{}deliveryResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deliveryServiceResponse", propOrder = {
    "deliveryResponse"
})
public class DeliveryServiceResponse {

    @XmlElement(name = "DeliveryResponse")
    protected DeliveryResponse deliveryResponse;

    /**
     * Gets the value of the deliveryResponse property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryResponse }
     *     
     */
    public DeliveryResponse getDeliveryResponse() {
        return deliveryResponse;
    }

    /**
     * Sets the value of the deliveryResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryResponse }
     *     
     */
    public void setDeliveryResponse(DeliveryResponse value) {
        this.deliveryResponse = value;
    }

}
