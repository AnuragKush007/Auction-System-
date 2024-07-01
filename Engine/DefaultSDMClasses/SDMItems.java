
package sdm.engine.DefaultSDMClasses;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{}SDM-item" maxOccurs="unbounded"/>
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
    "sdmItem"
})
@XmlRootElement(name = "SDM-items")
public class SDMItems {

    @XmlElement(name = "SDM-item", required = true)
    protected List<SDMItem> sdmItem;

    /**
     * Gets the value of the sdmItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sdmItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSDMItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SDMItem }
     * 
     * 
     */
    public List<SDMItem> getSDMItem() {
        if (sdmItem == null) {
            sdmItem = new ArrayList<SDMItem>();
        }
        return this.sdmItem;
    }

}