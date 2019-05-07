	package com.proquest.mtg.dismetadataservice.ecms;

	import java.io.*;
    import java.util.List;
    
	import javax.xml.parsers.*;
	import javax.xml.xpath.*;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.DocumentBuilder;
	
	import org.xml.sax.*;
	import org.xml.sax.SAXException;
	
	import org.json.JSONObject;
	
	import org.w3c.dom.Document;
	import org.w3c.dom.NodeList;
	import org.w3c.dom.Node;
	import org.w3c.dom.Element;

    import com.google.common.collect.Lists;
    //import com.google.inject.Inject;
    //import com.google.inject.name.Named;
    
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateAbstract;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateTitle;
	import com.proquest.mtg.dismetadataservice.metadata.Author;
	import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
	//import com.proquest.mtg.dismetadataservice.properties.DisMetadataProperties;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
    //import com.proquest.mtg.dismetadataservice.exodus.PubMetaDataQuery;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;


	public class ECMSMetaDataFormatFactory {
		//private List<SuppFile> suppFiles;
		//private static String pqOpenUrlBase;
		public static final String kEmptyValue = "";

		public static final DisPubMetaData constructECMSMetaData(String ecmsData, String mr3Data, String pqOpenUrlBase, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
	    	DisPubMetaData result = new DisPubMetaData();
	    try {
			Batch items = new Batch();
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder;

	        dBuilder = dbFactory.newDocumentBuilder();

	        InputSource is = new InputSource(new StringReader(ecmsData));
	        Document ecmsdoc = dBuilder.parse(is);
	        ecmsdoc.getDocumentElement().normalize();

	        XPath xPath =  XPathFactory.newInstance().newXPath();

	        String expression = "/IngestRecord/ControlStructure/Parent/ParentInfo";	        
	        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
	           ecmsdoc, XPathConstants.NODESET);
	        
	        //1. Pubnumber  * /RECORD/ObjectID@IDType=DissertationNum
	        XPathFactory xPathfactory = XPathFactory.newInstance();
	        XPath xpath = xPathfactory.newXPath();
	        XPathExpression expr = xpath.compile("//ObjectID[@IDType=\"DissertationNum\"]");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        String pubId = "";

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("DissNum:" +nNode.getTextContent());
	              pubId = nNode.getTextContent();
	           }
	        }
            result.setPubNumber(pubId);
	        
	        //2. authors   * fullname  - /RECORD/Contributors/Contributor@ContribRole=Author/OriginalForm
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"Author\"]/OriginalForm");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
			List<Author> results = null;
	        results = Lists.newArrayList();
			Author author = new Author();
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Author:" +nNode.getTextContent());
					author.setAuthorFullName(nNode.getTextContent());
	                results.add(author);
	           }
	        }
	        result.setAuthors(results);
	        
	        // Not Used for CSV, but check other feeds
	        /*xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"Author\"]/LastName");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Author:" +nNode.getTextContent());
	              result.setLastName(nNode.getTextContent());
	           }
	        }
	        */
	        
	     // Not Used for CSV, but check other feeds
	        /*xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"Author\"]/MiddleName");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              //result.setMiddleName(nNode.getTextContent());
	           }
	        }
            */
		
	     // Not Used for CSV, but check other feeds
	        /*xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"Author\"]/FirstName");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Author:" +nNode.getTextContent());
	              //result.setFirstName(nNode.getTextContent());
	           }
	        }*/
	        
	        //3. Title   * Strip out html tags
	        // * Get CDATA for Title
	        // * Get RawLang for Title Language
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//RECORD/ObjectInfo/Title");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        
            //List<Title> titleresults = null;
            //titleresults = Lists.newArrayList();        
			  Title title = new Title();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Title: [" +nNode.getTextContent()+ "]");
	              title.setMasterTitle(nNode.getTextContent());
	              result.setTitle(title);
	           }
	        }
	        
	        //Alternate Title   * Strip out html tags
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//RECORD/ObjectInfo/AlternateTitle");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        
            List<AlternateTitle> alternatetitleresults = null;
            alternatetitleresults = Lists.newArrayList();        
			  AlternateTitle alttitle = new AlternateTitle();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Title: [" +nNode.getTextContent()+ "]");
	                alttitle.setAltTitle(nNode.getTextContent());
	                alternatetitleresults.add(alttitle);
	           }
	        }
	        result.setAlternateTitles(alternatetitleresults);
	        
	        //5. degree year for first author  */DateInfo/Dates@DateType=DegreeDate
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//DateInfo/Dates[@DateType=\"DegreeDate\"]");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Degree Date: [" +nNode.getTextContent()+ "]");
	              result.setPubDate(nNode.getTextContent());
	           }
	        }
	        
	        //7. has pdf
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Component/Representation[@RepresentationType=\"PDFFullText\"]/PDFType");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        String hasPDF = "NOPDF";

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Has PDF: [" +nNode.getTextContent()+ "]");
	              if (null != nNode.getTextContent()) {
	            	  hasPDF = "Y";
	              }
	           }
	        }
	        result.setHasPDF(hasPDF);
	        
	        
	        //9. has supplemental files?
	        //52. Supplemental File name   /Attachments/Attachment/AttachName
	        //53. Supplemental file description   /Attachments/Attachment/AttachCategory
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//RECORD/Attachments/Attachment");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<SuppFile> suppresults = null;
	        suppresults = Lists.newArrayList();
	        
	        String hasSuppFiles = "N";
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		              if (null != nNode.getTextContent()) {
		            	  System.out.println("Setting Suppfiles to Y in ECMSMETADATA");
		              }
		              SuppFile suppitem = new SuppFile();
	        	   String childTagName = nNode.getFirstChild().getNextSibling().getNodeName();
	        	   if (childTagName == "AttachCategory") {
	        	       suppitem.setSuppFileCategory(nNode.getFirstChild().getNextSibling().getTextContent());
	        	   }
	        	   else if (childTagName == "AttachDesc") {
	        		   suppitem.setSuppFileDesc(nNode.getFirstChild().getNextSibling().getTextContent());
	        	   }
	                  suppitem.setSuppFilename(nNode.getFirstChild().getTextContent());
	              if ((null != nNode.getLastChild().getTextContent()) || (null != nNode.getFirstChild().getTextContent())) {
	            	  hasSuppFiles = "Y";
	              }
	        	   if (childTagName == "AttachCategory") {
	                  suppitem.setSuppFileCategory(nNode.getLastChild().getTextContent());
	        	   }
	        	   else if (childTagName == "AttachDesc") {
	        		   suppitem.setSuppFileDesc(nNode.getLastChild().getTextContent());
	        	   }
	        	   suppresults.add(suppitem);
	           }
	        }
	        result.setSuppFiles(suppresults);
	        result.setHasSuppFiles(hasSuppFiles);

	        
	        //30. External URL    */FlexTerm@FlexTermName=ITTUrlIdxTxt/FlexTermValue
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//FlexTerm[@FlexTermName=\"ITTUrlIdxTxt\"]/FlexTermValue");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("External URL: [" +nNode.getTextContent()+ "]");
	              result.setExternalURL(nNode.getTextContent());
	           }
	        }
	        
	        //31. ISBN * dis_items.ditm_isbn_number  * /RECORD/ObjecID@IDType=DocISBN
	        //   -  Stored without dashes.  CSV output contains dashes.
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//ObjectID[@IDType=\"DocISBN\"]");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              result.setISBN(trimmed(nNode.getTextContent()));
	           }
	        }
	        
	        //34. Subject description  * Converted form of GenSubjValue@TermVocab=PQSUBJ
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Terms/GenSubjTerm[@TermVocab=\"PQSUBJ\"]/GenSubjValue");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<Subject> subjectresults = null;
	        subjectresults = Lists.newArrayList();
		      Subject item = new Subject();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			      //Subject item = new Subject();
	              System.out.println("Subject Description: [" +nNode.getTextContent()+ "]");
	              item.setSubjectDesc(nNode.getTextContent());
	              subjectresults.add(item);   
	           }
	        }
	        
	        
	        //35. Keywords  * Flex term value for FlexTerm@FlexTermName=DissPaperKwd
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Terms/FlexTerm[@FlexTermName=\"DissPaperKwd\"]/FlexTermValue");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<Keyword> keywordresults = null;
	        keywordresults = Lists.newArrayList();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		          Keyword keyitem = new Keyword();
	              System.out.println("Keywords: [" +nNode.getTextContent()+ "]");
				  keyitem.setValue(nNode.getTextContent());
	              keywordresults.add(keyitem);
	           }
	        }
	        result.setKeywords(keywordresults);

	        
	        //37. Department name    * /Department
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/ScholarlyInfo/Department");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<String> departmentname = null;
	        departmentname = Lists.newArrayList();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Department name: [" +nNode.getTextContent()+ "]");
	              departmentname.add(nNode.getTextContent());
	           }
	        }
	        result.setDepartments(departmentname);
	        
	        
	        //38. Advisors  * /Contributor@ContribRole=Advisor/OriginalForm
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"Advisor\"]/OriginalForm");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
			List<Advisor> advisorresult = null;
			advisorresult = Lists.newArrayList();
			Advisor advisor = new Advisor();
			Advisors advisors = new Advisors();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
				//Advisor item = new Advisor();
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Advisors: [" +nNode.getTextContent()+ "]");
					advisor.setAdvisorFullName(nNode.getTextContent());
					advisorresult.add(advisor);
	           }
	        }
	        advisors.setAdvisor(advisorresult);
	        result.setAdvisors(advisors);
	        
	        
	        //39. Committee member  * /Contributor@ContribRole=CmteMember/OriginalForm
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/OriginalForm");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<CmteMember> cmteresults = null;
	        cmteresults = Lists.newArrayList();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Committee member: [" +nNode.getTextContent()+ "]");
					CmteMember cmteitem = new CmteMember();
					cmteitem.setFullName(nNode.getTextContent());
	                cmteresults.add(cmteitem);
	           }
	        }
	        result.setCmteMembers(cmteresults);
	        
	        
	        //41. Subject group description  * exodus:dvsg_description.  FlexTerm@
	        //    FlexTermName=DissPaperCategory/FlexTermValue
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//FlexTerm[@FlexTermName=\"DissPaperCategory\"]/FlexTermValue");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
			List<Subject> subjresults = null;
	        subjresults = Lists.newArrayList();
			Subject subject = new Subject();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Subject Group Description: [" +nNode.getTextContent()+ "]");
	              item.setSubjectDesc(nNode.getTextContent());
					subject.setSubjectGroupDesc(nNode.getTextContent());
	                subjresults.add(subject);
	           }
	        }
	        
	        //42. Subject code  * Part appears to be a part of subject code.  There can be
	        //multiple and they are separated by a | 
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//ClassTerm[@TermVocab=\"DISSSUBJ\"]/ClassCode");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("ClassCode: [" +nNode.getTextContent()+ "]");
	              item.setSubjectCode(nNode.getTextContent());
	              subject.setSubjectCode(nNode.getTextContent());
	              subjresults.add(subject);
	           }
	        }
	        result.setSubjects(subjectresults);
	        
	        
	        //55. Publisher   * /ObjectInfo/ObjectType@ObjectTypeOrigin=Publisher/
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//ObjectInfo/ObjectType[@ObjectTypeOrigin=\"Publisher\"]");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Publisher: [" +nNode.getTextContent()+ "]");
	              result.setPublisher(nNode.getTextContent());
	           }
	        }
	
	        
	        //57. British Library number  * /FlexTerm@FlexTermName=ITTBLNumIdxTxt/FlexTermValue
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//FlexTerm[@FlexTermName=\"ITTBLNumIdxTxt\"]/FlexTermValue");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              result.setBLNumber(trimmed(nNode.getTextContent()));
	           }
	        }
	        
	        
	        //58. PQOpenURL
	        // PQOpenURL is constructed in PubMetaDataProvider
			result.setPqOpenURL(pqOpenUrlBase + pubId.trim());
	        
	        //61. DCIRefs  Y if /Component@ComponentType=CitationReferences exists?  (NOTE: Make sure reptype = seed is okay
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Component[@ComponentType=\"CitationReferences\"]/Representation[@RepresentationType=\"Seed\"]/Resides");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
            String dciRefExistsFlag = "N";
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Has References: [" +nNode.getTextContent()+ "]");
	              String dciRefNames = nNode.getTextContent();
	  			if (null != dciRefNames) {
					result.setDciRefExistsFlag(dciRefExistsFlag);
				}
	           }
	        }
	        
	        
	        /*expression = "/IngestRecord/ControlStructure";	        
	        nodeList = (NodeList) xPath.compile(expression).evaluate(
	           ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              Element eElement = (Element) nNode;
	              System.out.println("Part : " 
	                 + eElement
	                 .getElementsByTagName("Part")
	                 .item(0)
	                 .getTextContent());
	           }
	        }*/
	        
	        expression = "/IngestRecord/RECORD/ObjectInfo/Abstract";	        
	        nodeList = (NodeList) xPath.compile(expression).evaluate(
	           ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName()+ "I value " +i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              Element eElement = (Element) nNode;
	              //System.out.println("Version :" + eElement.getAttribute("MinorVersion"));
	              //10. page count
	              //result.setPageCount(trimmed(eElement.getElementsByTagName("PageCount").item(0).getTextContent()));
	              //23. Abstract  * Split out from html/head/body tags
	              if (i == 0) {
	              System.out.println("Abstract : " 
	                      + eElement
	                      .getElementsByTagName("AbsText")
	                      .item(0)
	                      .getTextContent());
	              String Abstract = eElement
	                      .getElementsByTagName("AbsText")
	                      .item(0)
	                      .getTextContent();
	              Abstract = Abstract.replaceAll("<[^>]*>", "");
	              // Split Abstract here for Alt Title and Alt Abstract.
	              // NO... Alternate Abstracts should be in the second set of Abstract tags.  Don't split
	              // because I think that is incorrect.
	              result.setAbstract(Abstract);
	              }
	              else {
	              //24. Alternate Abstract   * Within Abstract field.  Separated by \nABSTRACT\n???
	              //List<AlternateAbstract> altabsresults = null;
	              //altabsresults = Lists.newArrayList();        
				  AlternateAbstract altabstracts = new AlternateAbstract();
	              /*--String AltAbstract = eElement
	                      .getElementsByTagName("AbsText")
	                      .item(1)
	                      .getTextContent();*/
	              //if (null != AltAbstract) {
	            	//  System.out.println("ALT ABS :" +AltAbstract);
	              String altAbstract = eElement
	                      .getElementsByTagName("AbsText")
	                      .item(0)
	                      .getTextContent();
	              String altabsLang = eElement
	                      .getElementsByTagName("RawLang")
	                      .item(0)
	                      .getTextContent();
	              altAbstract = altAbstract.replaceAll("<[^>]*>", "");
				  altabstracts.setAbstractText(altAbstract);
				  altabstracts.setLanguage(altabsLang);
				  result.setAlternateAbstracts(altabstracts);
	              }
	             // }
	              //26. Alternate Title  * Within Abstract field with 'Title of Dissertation: ...'
	              //25. Alternate Abstract language    *RawLang
	              //27. Alternate Title language   *RawLang
	              //48. Dissertations language code  *Language/RawLang split or ISOCODE?
	             /*-- String DissLangCode = null;
	              String DissLangDesc = null;
	              String langcode = eElement
	                      .getElementsByTagName("RawLang")
	                      .item(0)
	                      .getTextContent();
		          String[] langstrs = langcode.split("[\\;]");*/
                  /*--if (langstrs.length > 1 && langstrs[1] != null) {
                  String lang1 = langstrs[0];
                  String lang2 = langstrs[1];
                  System.out.println("lang 1: " +lang1+ "\nlang 2 : " +lang2);
                  if (lang1 == "English") {
                	  DissLangCode = lang1;
                	  DissLangDesc = lang2;
                  }
                  else {
                	  DissLangCode = lang2;
                	  DissLangDesc = lang1;
                  }
                  }*/
                 /*-- List<DissLanguage> langresult = Lists.newArrayList();
                  DissLanguage language = new DissLanguage((DissLangDesc), required(DissLangCode));
                  langresult.add(language);
                  result.setDissLanguages(langresult);*/
	              //49. English translation of Title  * English overwrite title
	              //50. Variant Title   * English Overwrite Title /Title   * Same as English Translation of Title.
	           }
	        }
	        
	        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/Language/RawLang");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<DissLanguage> langresult = Lists.newArrayList();
	          
	          
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              System.out.println("Has Language info: [" +nNode.getTextContent()+ "]");
	              String DissLangCode = null;
	              String DissLangDesc = null;
	              String langString = nNode.getTextContent();
		          String[] langstrs = langString.split("[\\;]");
		          if (langstrs.length > 1 && langstrs[1] != null) {
	                  String lang1 = langstrs[0];
	                  String lang2 = langstrs[1];
	                  System.out.println("lang 1: " +lang1+ "\nlang 2 : " +lang2);
	                  if (lang1 == "English") {
	                	  DissLangCode = lang1;
	                	  DissLangDesc = lang2;
	                  }
	                  else {
	                	  DissLangCode = lang2;
	                	  DissLangDesc = lang1;
	                  }
	               }
                  DissLanguage language = new DissLanguage((DissLangDesc), required(DissLangCode));
                  langresult.add(language);
	           }
	        }
	        result.setDissLanguages(langresult);
	        
	        
	        expression = "/IngestRecord/RECORD/ObjectInfo/ScholarlyInfo";	        
	        nodeList = (NodeList) xPath.compile(expression).evaluate(
	           ecmsdoc, XPathConstants.NODESET);
  			List<Degree> degreeresults = null;
	        degreeresults = Lists.newArrayList();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           System.out.println("\nCurrent Element :" + nNode.getNodeName());
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              Element eElement = (Element) nNode;
					Degree degree = new Degree();
	              //4. degree code for first author
				degree.setDegreeCode(eElement
	                      .getElementsByTagName("DegreeName")
	                      .item(0)
	                      .getTextContent());
				//author.setDegreeCode(nNode.getTextContent());
	              //12. Volume/issue   *  Needs to be split out from UMILocalPC
	              //45. School state   * SchoolLocation  - Split off from Country if United States.
	              //46. School LOC country   * May be SchoolLocation.
	              String umilocalpc = eElement
	                      .getElementsByTagName("UMILocalPC")
	                      .item(0)
	                      .getTextContent();
	              //String[] strs = null;
	              //if (umilocalpc.contains("MAI")) {
	            //	  System.out.println("FOUND MAI");
	            //	  strs = umilocalpc.split("[,\\/\\s]", 2);
	              //}
	              //else {
	            //	  strs = umilocalpc.split("[,\\-\\/\\s]", 2);
	              //}
                          String dissdesc = "";
	                      String[] strs = umilocalpc.split("[,\\-\\/\\s, 2]");
	                      String disstype = strs[0];
	                      String disscode = strs[1];
	                      String dissvol = strs[2];
	                      String dississ = strs[3];
	                      if (strs.length > 7 && strs[7] != null) {
	                          dissdesc = strs[5]+" "+strs[6]+" "+strs[7];
	                      }
	                      else {
	                          dissdesc = strs[5]+" "+strs[6]; 
	                      }
	                      
	                      // 11. DISS TYPE CODE   - getBatch().getDBTypeCode()
	                      items.setDBTypeCode(disstype);
	                      
	                      // 51. DAI Section Code  -  getBatch().getDAISectionCode()
	      				  items.setDAISectionCode(disscode);
	                      String volumeIssue = (dissvol + "-" +dississ);
	    	              items.setVolumeIssue(volumeIssue);
	                      
	    	              //56. Dissertation code   *  Needs to be split out from UMILocalPC
	                      System.out.println("Desc:" +dissdesc);
	                      items.setDBTypeDesc(dissdesc);
	                      result.setBatch(items);
	                      
	    	      //6. school name	                              
				  School school = new School();
				  school.setSchoolName(eElement
		                      .getElementsByTagName("SchoolCodeName")
		                      .item(0)
		                      .getTextContent());
		          school.setSchoolCode(eElement
		                      .getElementsByTagName("SchoolCodeNum")
		                      .item(0)
		                      .getTextContent());
	              
	              //44. School country
		          String schoolLocation = eElement
	                      .getElementsByTagName("SchoolLocation")
	                      .item(0)
	                      .getTextContent();
		          String[] schoolstrs = schoolLocation.split("[\\--]");
                  String schoolCountry = schoolstrs[0];
                  String schoolState = "";
                  if (schoolstrs.length > 2 && schoolstrs[2] != null) {
                	  schoolState = schoolstrs[2];
                  }
				  school.setSchoolCountry(schoolCountry);
				  school.setSchoolState(schoolState);
				  result.setSchool(school);
					
					
	              //59. Degree description for first Author
    				degree.setDegreeDescription(eElement
  	                      .getElementsByTagName("DegreeDescription")
  	                      .item(0)
  	                      .getTextContent());
    				degree.setDegreeDescription(eElement
  	                      .getElementsByTagName("DegreeDescription")
  	                      .item(0)
  	                      .getTextContent());
    				degree.setDegreeYear(result.getPubDate());
    				degreeresults.add(degree);
    				author.setDegrees(degreeresults);
	           }
	        }
	     } catch (ParserConfigurationException e) {
	        e.printStackTrace();
	     } catch (SAXException e) {
	        e.printStackTrace();
	     } catch (IOException e) {
	        e.printStackTrace();
	     } catch (XPathExpressionException e) {
	        e.printStackTrace();
	     }
	    
	    // MR3 data
        //8. MR3: pdf available date  -  /Title/PublicationDate
	    //33. MR3: Publication date  -  /Title/PublicationDate
        JSONObject json = new JSONObject(mr3Data);
        //String AvailDate = json.getString("PublicationDate");
        String PublicationDate = json.getString("PublicationDate");
        //System.out.println("MR3 Available Date :" +AvailDate);
        result.setFirstPublicationDate(PublicationDate);
        //result.set
        
        //13. MR3:  Active Sales Restriction code  -  /Title/ActiveSalesRestrictionCodes/ActiveCode
        //String ActiveCode = json.getString("ActiveCode");
        //System.out.println("MR3 Active Sales Restriction Code :" +ActiveCode);
        
        //15. MR3:  Sales Restriction code  -  /Title/Restrictions/Restriction/Code*
        String srCode = json.getJSONArray("Restrictions").getJSONObject(0).getString("Code");
        //String salesRestrictionCode = json.getString("Code");
        System.out.println("MR3 sr Code :" +srCode);
        
        //16. MR3:  Sales Restriction description  -  /Title/Restrictions/Restriction/Type
        //17. MR3:  Sales Restriction start date  -  /Title/Restrictions/Restriction/RestrictionDates/StartDate
        //18. MR3:  Sales Restriction end date  -  /Title/Restrictions/Restriction/RestrictionDates/EndDate
        
        //28. MR3:  Manuscript Media code  - /Title/ManuscriptMediumCode
        String mmCode = json.getString("ManuscriptMediumCode");
        System.out.println("MR3 mmCode :" +mmCode);
       
        
        //29. MR3:  Manuscript Media description   N/A  - Ask DissOps
        
        //32. MR3:  Open Access Flag    - /Title/OpenAccessFlag
        String oaFlag = json.getString("OpenAccessFlag");
        System.out.println("MR3 OAFlag :" +oaFlag);
        result.setOpenAccessFlag(oaFlag);
        
        //40. MR3: External ID  -  /Title/ExternalID
        String xID = json.getString("ExternalID");
        System.out.println("MR3 ExternalID :" +xID);
        result.setExternalId(xID);
        
        //63. MR3:  Dissertations valid source  -  /Title/DissertationsValidSource
        String dissValidSource = json.getString("DissertationsValidSource");
        System.out.println("MR3 Valid Source :" +dissValidSource);
        result.setDisValidSource(dissValidSource);
        
        //64. MR3:  Dissertations available formats  - /Title/DissertationsAvailableFormats/AvailableFormat*
        String availableFormats = json.getString("DissertationsAvailableFormats");
        System.out.println("MR3 Available Formats :" +availableFormats);
        //result.setDisAvailableFormats(availableFormats);
        
	    return result;
	  }

		public static String trimmed(String x) {
			if (null != x) {
				x = x.trim();
			}
			return x;
		}
		
		public static String required(String x) {
			x = trimmed(x);
			if (null == x || x.isEmpty()) {
				x = kEmptyValue;
			}
			return x;
		}

        //14. MR3:  Active Format Restriction code  -  N/A   - Talk to MR3   ex.  9712788
        //15. MR3:  Sales Restriction code  -  /Title/Restrictions/Restriction/Code*
        //16. MR3:  Sales Restriction description  -  /Title/Restrictions/Restriction/Type
        //17. MR3:  Sales Restriction start date  -  /Title/Restrictions/Restriction/RestrictionDates/StartDate
        //18. MR3:  Sales Restriction end date  -  /Title/Restrictions/Restriction/RestrictionDates/EndDate
        //19. MR3:  Format Restriction code   -  /Title/Formats/
        //20. MR3:  Format Restriction description   -   N/A  - Talk to MR3
        //21. MR3:  Format Restriction start date   -  /Title/Formats/Format/RestrictionDates?  Doesn't match up with value
        //22. MR3:  Format Restriction end date  -  /Title/Formats/Format/RestrictionDates?  Doesn't match up with value

		
        //47. Author LOC citizenship    * Dropped per Mark Dill.   *DONE
        //54. Page number   * Not available in ECMS.  Dropped per Mark Dill.   *DONE
        //60. Keyword Source    * Not available.  ECMS may be updated to hold this. Jessica will add this into ECMS.
        //62. Reference location    * Drop per Mark Dill   *DONE
        //65. FOP Quantity.*  Drop per Mark Dill  *DONE
        //Note: AuthorCitizenship will be removed.  *DONE
		
		//private void setCommitteeMemberInformation() throws SQLException {
		//	List<CmteMember> result = null;
		//	CmteMember item = new CmteMember();
			//item.setFirstName(required(cursor.getString(kColumnCommitteeFirstName)));
			//item.setMiddleName(trimmed(cursor.getString(kColumnCommitteeMiddleName)));
			//item.setLastName(required(cursor.getString(kColumnCommitteeLastName)));
			//item.setSuffix(trimmed(cursor.getString(kColumnCommitteeSuffix)));
		//	result.add(item);
		//}
		
	}