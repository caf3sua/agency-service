
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="maDaiLy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "maDaiLy"
})
@XmlRootElement(name = "GetDMLoaiAnChi")
public class GetDMLoaiAnChi {

    protected String maDaiLy;

    /**
     * Gets the value of the maDaiLy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaDaiLy() {
        return maDaiLy;
    }

    /**
     * Sets the value of the maDaiLy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaDaiLy(String value) {
        this.maDaiLy = value;
    }

}
