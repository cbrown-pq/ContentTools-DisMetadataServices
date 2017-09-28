package com.proquest.mtg.dismetadataservice.fopeligiblepubs.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"publication"})
@XmlRootElement(name = "FopEligiblePubsList")
public class FopEligiblePubsList {

    @XmlElement(name = "Publication", required = true)
    protected List<FopEligiblePubsList.Publication> publication;
    
    public List<FopEligiblePubsList.Publication> getPublication() {
    	if (publication == null) {
    		publication = new ArrayList<FopEligiblePubsList.Publication>();
    	}
    	return this.publication;
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "pubID",
        "pubSource"
    })
    
    public static class Publication {

        @XmlElement(name = "PubID", required = true)
        protected String pubID;
        @XmlElement(name = "PubSource", required = true)
        protected String pubSource;

        public String getPubID() {
            return pubID;
        }

        public void setPubID(String value) {
            this.pubID = value;
        }

        public String getPubSource() {
            return pubSource;
        }

        public void setPubSource(String value) {
            this.pubSource = value;
        }
    }
}
