
package sdm.engine.DefaultSDMClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;all>
 *         &lt;element name="SDM-Zone">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}SDM-items"/>
 *         &lt;element ref="{}SDM-stores"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "super-duper-market-descriptor")
public class SuperDuperMarketDescriptor {

    @XmlElement(name = "SDM-Zone", required = true)
    protected SuperDuperMarketDescriptor.SDMZone sdmZone;
    @XmlElement(name = "SDM-items", required = true)
    protected SDMItems sdmItems;
    @XmlElement(name = "SDM-stores", required = true)
    protected SDMStores sdmStores;

    /**
     * Gets the value of the sdmZone property.
     * 
     * @return
     *     possible object is
     *     {@link SuperDuperMarketDescriptor.SDMZone }
     *     
     */
    public SuperDuperMarketDescriptor.SDMZone getSDMZone() {
        return sdmZone;
    }

    /**
     * Sets the value of the sdmZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link SuperDuperMarketDescriptor.SDMZone }
     *     
     */
    public void setSDMZone(SuperDuperMarketDescriptor.SDMZone value) {
        this.sdmZone = value;
    }

    /**
     * Gets the value of the sdmItems property.
     * 
     * @return
     *     possible object is
     *     {@link SDMItems }
     *     
     */
    public SDMItems getSDMItems() {
        return sdmItems;
    }

    /**
     * Sets the value of the sdmItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMItems }
     *     
     */
    public void setSDMItems(SDMItems value) {
        this.sdmItems = value;
    }

    /**
     * Gets the value of the sdmStores property.
     * 
     * @return
     *     possible object is
     *     {@link SDMStores }
     *     
     */
    public SDMStores getSDMStores() {
        return sdmStores;
    }

    /**
     * Sets the value of the sdmStores property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMStores }
     *     
     */
    public void setSDMStores(SDMStores value) {
        this.sdmStores = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SDMZone {

        @XmlAttribute(name = "name", required = true)
        protected String name;

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }

}
