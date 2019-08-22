package com.proquest.mtg.dismetadataservice.ecms;

	import java.io.*;
import java.sql.SQLException;
import java.util.List;
    import java.util.regex.Matcher;
	import java.util.regex.Pattern;
	import java.util.Date;

    import java.text.SimpleDateFormat;
    import java.text.ParseException;
	import javax.xml.parsers.*;
	import javax.xml.xpath.*;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.DocumentBuilder;
	
	import org.xml.sax.*;
	import org.xml.sax.SAXException;
	
	import org.json.JSONObject;
//import org.apache.http.ParseException;
import org.json.JSONArray;
	
	import org.w3c.dom.Document;
	import org.w3c.dom.NodeList;
	import org.w3c.dom.Node;
	import org.w3c.dom.Element;

    import com.google.common.collect.Lists;
    
    
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData;
import com.proquest.mtg.dismetadataservice.exodus.SplitAdvisors;
import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisor;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Advisors;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateAbstract;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.AlternateTitle;
	import com.proquest.mtg.dismetadataservice.metadata.Author;
	import com.proquest.mtg.dismetadataservice.metadata.Author.Degree;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Batch;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.CmteMember;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.DissLanguage;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.FormatRestriction;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Keyword;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.ManuscriptMedia;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.PdfAvailableDateStatus;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SalesRestriction;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.School;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Subject;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.SuppFile;
    import com.proquest.mtg.dismetadataservice.exodus.DisPubMetaData.Title;
    import com.proquest.mtg.dismetadataservice.metadata.DisVolIssProvider;


	public class ECMSMetaDataFormatFactory {

		public static final String kEmptyValue = "";

		public static final DisPubMetaData constructECMSMetaData(String ecmsData, String mr3Data, String pqOpenUrlBase, int excludeRestriction, int excludeAbstract, int excludeAltAbstract) throws Exception {
	    	DisPubMetaData result = new DisPubMetaData();
	    	Batch items = new Batch();
	    	String disscode = "";
	    try {

			//Batch items = new Batch();
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
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
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           Author author = new Author();
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        	   String myAuthorName = nNode.getTextContent();
					author.setAuthorFullName(nNode.getTextContent());
					author.setSequenceNumber(i+1);
	           }
	           results.add(author);
	           //CBNEW START
		        xPathfactory = XPathFactory.newInstance();
		        xpath = xPathfactory.newXPath();
		        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/ScholarlyInfo/DegreeDescription");
		        NodeList nodeList2 = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	  			List<Degree> degreeresults = null;
		        degreeresults = Lists.newArrayList();
				Degree degree = new Degree();
		        for (int j = 0; j < nodeList2.getLength(); j++) {
		           Node nNode2 = nodeList2.item(j);
		           
		           if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
			              String degreeDescription = nNode2.getTextContent();

			  			  if (null != degreeDescription) {
			    		      degree.setDegreeDescription(degreeDescription);
			      			  degree.setDegreeYear(result.getPubDate());
			      			  //System.out.println("Degree :" +degree);
			      			  degreeresults.add(degree);
			      			  degree.setSequenceNumber(j+1);
						  }
		           }
		        }
		        //CBNEW END
		        //CBNEW 2 START
		        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/ScholarlyInfo/DegreeName");
		        nodeList2 = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
		        for (int j = 0; j < nodeList2.getLength(); j++) {
		           Node nNode2 = nodeList2.item(j);
		           
		           if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
			              String degreeName = nNode2.getTextContent();

			  			  if (null != degreeName) {
					          degree.setDegreeCode(degreeName);
						  }
		           }
		        }
		        //CBNEW 2 END
		        //CBNEW 3 START
		        expr = xpath.compile("//DateInfo/Dates[@DateType=\"DegreeDate\"]");
		        nodeList2 = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

		        for (int j = 0; j < nodeList2.getLength(); j++) {
		           Node nNode2 = nodeList2.item(j);
		           
		           if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
		              result.setPubDate(nNode2.getTextContent());
		              degree.setDegreeYear(result.getPubDate());
		           }
		        }
		        //CBNEW 3 END
				author.setDegrees(degreeresults);
	        }
	        //System.out.println("Get Authors" +results.toString());
	        result.setAuthors(results);
	        //System.out.println("RESULT :" +result);
	        
	        //3. Title   * Strip out html tags
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//RECORD/ObjectInfo/Title");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
       
			Title title = new Title();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                alttitle.setAltTitle(nNode.getTextContent());
	                alternatetitleresults.add(alttitle);
	           }
	        }
	        result.setAlternateTitles(alternatetitleresults);
	        
	        //5. degree year for first author  */DateInfo/Dates@DateType=DegreeDate
	       /* xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//DateInfo/Dates[@DateType=\"DegreeDate\"]");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              result.setPubDate(nNode.getTextContent());
	           }
	        }*/
	        
	        //7. has pdf
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Component/Representation[@RepresentationType=\"PDFFullText\"]/PDFType");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        String hasPDF = "N";

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		              if (null != nNode.getTextContent()) {
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              result.setExternalURL(nNode.getTextContent());
	           }
	        }
	        
	        //31. ISBN * dis_items.ditm_isbn_number  * /RECORD/ObjecID@IDType=DocISBN
	        //   -  Stored without dashes.  CSV output contains dashes.
	        //   -  Approved for no dash output per Mark Dill.
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//ObjectID[@IDType=\"DocISBN\"]");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
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

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        	  Subject genSubj = new Subject();
	              genSubj.setSubjectDesc(nNode.getTextContent());
	              subjectresults.add(genSubj);   
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		          Keyword keyitem = new Keyword();
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
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
			//Advisor advisor = new Advisor();
			String delimitedAdvisors = "";

		    Advisors advisors = new Advisors();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           Advisor advisor = new Advisor();
	           //Advisors advisors = new Advisors();
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					advisor.setAdvisorFullName(nNode.getTextContent());
					if ((null != delimitedAdvisors) && (delimitedAdvisors != "")) {
					delimitedAdvisors = (delimitedAdvisors + ';' + nNode.getTextContent());
					}
					else {
						delimitedAdvisors = nNode.getTextContent();
					}
					//System.out.println("ADVISOR :" +nNode.getTextContent());
					//System.out.println("DELIM ADVISOR :" +delimitedAdvisors);
					advisors.setAdvisorsExodusStr(nNode.getTextContent());
					//advisorresult.add(advisor);
	           }
	           //System.out.println("ADDING ADVISOR :" +advisor.getAdvisorFullName());
	           advisorresult.add(advisor);
	           //CBNEW ADVISORS
		        advisors.setAdvisor(advisorresult);
		        //System.out.println("ADVISORS :" +advisors.getAdvisorsExodusStr());
	        }
	        result.setAdvisors(getAdvisorsFor(delimitedAdvisors));
	        
	        
	        //39. Committee member  * /Contributor@ContribRole=CmteMember/OriginalForm
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/OriginalForm");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<CmteMember> cmteresults = null;
	        cmteresults = Lists.newArrayList();

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           CmteMember cmteitem = new CmteMember();
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        	   //System.out.println("CMTE MEMBER :" +nNode.getTextContent());
					cmteitem.setFullName(nNode.getTextContent());
	                cmteresults.add(cmteitem);
	           }
	           //CBNEW START
	        //}
	        // #CB:  The next two, and previous expr, are probably not needed, but I am going to leave them in until
	        //       I am sure that we are not messing up the advisor results.  Advisor really shouldn't 
	        //       be in this part, but for some reason I put it in there and it worked in testing.
	        //       This should be cleaned up.
	        expr = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/LastName");
	        NodeList nodeList2 = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        for (int j = 0; j < nodeList.getLength(); j++) {
		           Node nNode2 = nodeList2.item(j);
		           //CBNEW ADVISOR
		           Advisor advisor = new Advisor();
		           if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
						cmteitem.setLastName(nNode2.getTextContent());
						advisorresult.add(advisor);
		           }
		    }
	        expr = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/FirstName");
	        nodeList2 = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        for (int j = 0; j < nodeList.getLength(); j++) {
		           Node nNode2 = nodeList2.item(j);
		           //CB NEW ADVISOR
		           Advisor advisor = new Advisor();
		           if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
						cmteitem.setFirstName(nNode2.getTextContent());
						advisorresult.add(advisor);
		           }
		    }
	    }
	        //CBNEW END
	        result.setCmteMembers(cmteresults);
	        
	        //CBTEST CMTE START
	        // There must be a better way to do this with the expr variables.  Investigate and improve.
	        expr = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/FirstName");
	        NodeList nodeList3 = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        XPathExpression expr2 = xpath.compile("//ObjectID[@IDType=\"DissertationNum\"]");
	        expr2 = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/LastName");
	        NodeList nodeList4 = (NodeList) expr2.evaluate(ecmsdoc, XPathConstants.NODESET);
	        XPathExpression expr3 = xpath.compile("//ObjectID[@IDType=\"DissertationNum\"]");
	        expr3 = xpath.compile("//Contributor[@ContribRole=\"CmteMember\"]/OriginalForm");
	        NodeList nodeList5 = (NodeList) expr3.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<CmteMember> cmteresults2 = null;
	        //CmteMember cmteitem = new CmteMember();
	        cmteresults2 = Lists.newArrayList();
	        for (int k = 0; k < nodeList3.getLength(); k++) {
		           Node nNode3 = nodeList3.item(k);
		           Node nNode4 = nodeList4.item(k);
		           Node nNode5 = nodeList5.item(k);
		           //CB NEW CMTEMEMBER
		           CmteMember cmteitem = new CmteMember();
		           //Advisor advisor = new Advisor();
		           if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
		        	   cmteitem.setLastName(nNode4.getTextContent());
						cmteitem.setFirstName(nNode3.getTextContent());
						cmteitem.setFullName(nNode5.getTextContent());
						cmteresults2.add(cmteitem);
		           }
		    }
	        result.setCmteMembers(cmteresults2);
	        //CBTEST CMTE END
	        
	        
	        //41. Subject group description  * exodus:dvsg_description.  FlexTerm@
	        //    FlexTermName=DissPaperCategory/FlexTermValue
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//FlexTerm[@FlexTermName=\"DissPaperCategory\"]/FlexTermValue");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        	   Subject genSubj = new Subject();
	               String gDesc = nNode.getTextContent();
				   genSubj.setSubjectGroupDesc(gDesc);
				   subjectresults.add(genSubj);
	           }
	        }
	       
	        
	        //42. Subject code
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//ClassTerm[@TermVocab=\"DISSSUBJ\"]/ClassCode");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	        	  Subject genSubj = new Subject();
		          String gCode = nNode.getTextContent();
	              genSubj.setSubjectCode(gCode);
	              subjectresults.add(genSubj);
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
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
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              result.setBLNumber(trimmed(nNode.getTextContent()));
	           }
	        }
	        
	        
	        //58. PQOpenURL
			result.setPqOpenURL(pqOpenUrlBase + pubId.trim());
			
	        //61. DCIRefs  Y if /Component@ComponentType=CitationReferences exists?  (NOTE: Make sure reptype = seed is okay
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//Component[@ComponentType=\"CitationReferences\"]/Representation[@RepresentationType=\"Seed\"]/Resides");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
            String dciRefExistsFlag = "N";
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              String dciRefNames = nNode.getTextContent();
	  			  if (null != dciRefNames) {
					result.setDciRefExistsFlag(dciRefExistsFlag);
				  }
	           }
	        }
	        
            //10. page count
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//RECORD/ObjectInfo/PageCount");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		              String pagecount = nNode.getTextContent();
		  			  if (null != pagecount) {
						result.setPageCount(pagecount);
					  }
	           }
	        }

      
	        expression = "/IngestRecord/RECORD/ObjectInfo/Abstract";	        
	        nodeList = (NodeList) xPath.compile(expression).evaluate(
	           ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              Element eElement = (Element) nNode;

	              //23. Abstract
	              if (i == 0) {
	              String Abstract = eElement
	                      .getElementsByTagName("AbsText")
	                      .item(0)
	                      .getTextContent();
	              Abstract = Abstract.replaceAll("<[^>]*>", "");
	              //System.out.println("ABSTRACT IN CSV :" +Abstract);
	              result.setAbstract(Abstract);
	              }
	              else {
	              //24. Alternate Abstract 
				  AlternateAbstract altabstracts = new AlternateAbstract();
// NOTE: This getElement might cause problems, but seems to be working.
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
	              //26. Alternate Title  * Within Abstract field with 'Title of Dissertation: ...'
	              //25. Alternate Abstract language    *RawLang
	              //27. Alternate Title language   *RawLang
	              //48. Dissertations language code  *Language/RawLang split or ISOCODE?
	           }
	        }
	        
	        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/Language/RawLang");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        List<DissLanguage> langresult = Lists.newArrayList();
	          
	          
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	              String DissLangCode = null;
	              String DissLangDesc = null;
	              String langString = nNode.getTextContent();
		          String[] langstrs = langString.split("[\\;]");
		          if (langstrs.length > 1 && langstrs[1] != null) {
	                  String lang1 = langstrs[0];
	                  String lang2 = langstrs[1];
	                  if (lang1.equals("English")) {
	                	  DissLangCode = lang1;
	                	  DissLangDesc = lang2;
	                  }
	                  else {
	                	  DissLangCode = lang2;
	                	  DissLangDesc = lang1;
	                  }
	               }
		          else {
	                  if (langString.equals("English")) {
	                	  DissLangCode = "EN";
	                  }
	                  DissLangDesc = langString;
		        			  
		          }
                  DissLanguage language = new DissLanguage((DissLangDesc), required(DissLangCode));
                  langresult.add(language);
	           }
	        }
	        result.setDissLanguages(langresult);
	        

// Degree Information
	        // Degree Name
/*	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/ScholarlyInfo/DegreeName");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
  			List<Degree> degreeresults = null;
	        degreeresults = Lists.newArrayList();
			Degree degree = new Degree();
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		              String degreeName = nNode.getTextContent();

		  			  if (null != degreeName) {
				          degree.setDegreeCode(degreeName);
					  }
	           }
	        }
	        
	        // Degree description
	        xPathfactory = XPathFactory.newInstance();
	        xpath = xPathfactory.newXPath();
	        expr = xpath.compile("//IngestRecord/RECORD/ObjectInfo/ScholarlyInfo/DegreeDescription");
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		              String degreeDescription = nNode.getTextContent();

		  			  if (null != degreeDescription) {
		    		      degree.setDegreeDescription(degreeDescription);
		      			  degree.setDegreeYear(result.getPubDate());
		      			  System.out.println("Degree :" +degree);
		      			  degreeresults.add(degree);
					  }
	           }
	        }*/
			//author.setDegrees(degreeresults);
	        
	        
            //  ECMS Bundle codes to determine DB Type code from MR3 data
	        expr = xpath.compile("//IngestRecord/ControlStructure/ObjectBundleData/ObjectBundleValue");
	        // Need to add check for ObjectBundleType value of DissProductCode
	        nodeList = (NodeList) expr.evaluate(ecmsdoc, XPathConstants.NODESET);
            //String disscode = "";
            
	        for (int i = 0; i < nodeList.getLength(); i++) {
		           Node nNode = nodeList.item(i);
		           
		           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			              String bundleDescription = nNode.getTextContent();
			              if (bundleDescription.equals("PQDTGLOBAL_A_SIDE")) {
			            	  disscode = "A";
			              }
			              else if (bundleDescription.equals("PQDTGLOBAL_B_SIDE")) {
			            	  disscode = "B";
			              }
			              else {
			            	  // Invalid bundle description
			              }
		           }
		           //items.setDBTypeCode(disscode);
	        }
			
            //12. Volume/issue   *  Needs to be split out from UMILocalPC	        
	        expression = "/IngestRecord/RECORD/ObjectInfo/ScholarlyInfo/UMILocalPC";	        
	        nodeList = (NodeList) xPath.compile(expression).evaluate(
	           ecmsdoc, XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); i++) {
	           Node nNode = nodeList.item(i);
	           
	           if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	              String umilocalpc = nNode.getTextContent();
	  			  if (null != umilocalpc) {
                          String dissdesc = "";
                          String typeCodeVolIssGroup = "";
                          String typeCodeGroup = "";
                          String volIssGroup = "";
                          String disstype = "";
                          //String disscode = "";
                          String dissvol = "";
                          String dississ = "";
	                      String[] strs = umilocalpc.split("[,]");
	                      
	                      if (strs.length > 1 && strs[1] != null) {
	                    	  typeCodeVolIssGroup = strs[0];
	                    	  if (null !=  typeCodeVolIssGroup) {
	                    		  String[] volIssStrs = typeCodeVolIssGroup.split("[\\s]");
	    	                      if (volIssStrs.length > 1 && volIssStrs[1] != null) {
	    	                    	  typeCodeGroup = volIssStrs[0];
	    	                    	  if (null != typeCodeGroup) {
	    	                    		  String[] typeStrs = typeCodeGroup.split("[\\-]");
		    	   	                      	if (typeStrs.length > 1 && typeStrs[1] != null) {
		    	   	                      		disstype = typeStrs[0];
		    	   	                      		disscode = typeStrs[1];
		    	   	                      	}
		    	   	                      	else {
		    	   	                      		disstype = typeStrs[0];
		    	   	                      	}
	    	                    	  }
	    	                    	  volIssGroup = volIssStrs[1];
	    	                    	  //System.out.println("Volume Issue Group :" +volIssGroup);
	    	                    	  if (null != volIssGroup) {
	    	                    		String[] volStrs = volIssGroup.split("[\\/]");
	    	   	                      	if (volStrs.length > 1 && volStrs[1] != null) {
	    	   	                      		dissvol = volStrs[0];
	    	   	                      		dississ = volStrs[1];
	    	   	                      	}
	    	   	                      	else {
	    	   	                      		dissvol = volStrs[0];
	    	   	                      		System.out.println("VOLUME :" +dissvol);
	    	   	                      	}
	    	                    	  }
	    	                      }
	    	                      else {
	    	                    	  disstype = volIssStrs[0];
	    	                      }
	                    	  }
	                    	  dissdesc = strs[1];
	                    	  dissdesc = dissdesc.trim();
	                      }
	                      
	                      // 11. DISS TYPE CODE   - setDBTypeCode()
	                      //System.out.println("DISS TYPE(DBTYPECODE :" +disstype);
	                      //items.setDBTypeCode(disstype);
	                      
	                      // 51. DAI Section Code  -  setDAISectionCode()
	                      //disscode="c";
	      				  items.setDAISectionCode(disscode);
	      				  String volumeIssue = "";
	      				  if ((null != dissvol) && (null != dississ)){
	      					  if (!dississ.isEmpty()) {
	      					    volumeIssue = (dissvol + "-" +dississ);
	      					  }
	      					  else {
	      						  volumeIssue = dissvol;
	      					  }
	      				  }
	                      //String volumeIssue = (dissvol + "-" +dississ);
	      				  //volumeIssue = "49-05";
	    	              items.setVolumeIssue(volumeIssue);
	                      
	    	              //56. Dissertation code   *  Needs to be split out from UMILocalPC
	    	              dissdesc = "Dissertations Abstract Int";
	                      items.setDBTypeDesc(dissdesc);
	               }
	               //result.setBatch(items);
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
	    //33. MR3: Publication date  -  /Title/PublicationDate
        JSONObject json = new JSONObject(mr3Data);
        String PublicationDate = "";
        String VolIssDate = "";
        String pubDate = json.optString("PublicationDate");
        if (null != pubDate && !pubDate.isEmpty()) {
        	//Change ISO date to dd-MMM-yyyy format
        	String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        	String formatRequest = "dd-MMM-yyyy";
        	try {
        		PublicationDate = convertDate(pubDate,datePattern, formatRequest);
        		// Also get the vol/Iss creation date
        		formatRequest = "dd-MMMM-yyyy";
        		VolIssDate = convertDate(pubDate,datePattern,formatRequest);
        		//System.out.println("VOLISS DATE :" +VolIssDate);
        	} catch (ParseException e) {
        		e.printStackTrace();
        	}
        	
        	result.setPubDate(PublicationDate);
           result.setFirstPublicationDate(VolIssDate);
        }
        
	    // MR3 data
        //8. MR3: pdf available date  -  /Title/PdfAvailableDate
        String PDFDate = "";
        String pdfAvailableDate = json.optString("PdfAvailableDate");
        //System.out.println("HERE");
        if (null != pdfAvailableDate && !pdfAvailableDate.isEmpty()) {
        	//Change ISO date to dd-MMM-yyyy format
        	String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        	String formatRequest = "dd-MMM-yyyy";
        	try {
        		PDFDate = convertDate(pdfAvailableDate,datePattern,formatRequest);
        	} catch (ParseException e) {
        		e.printStackTrace();
        	}
        	//System.out.println("PDF DATE :" +PDFDate);
           //result.setFirstPublicationDate(PDFDate);
           PdfAvailableDateStatus pdfavail = new PdfAvailableDateStatus();
           pdfavail.setPdfAvailableDate(PDFDate);
           result.setPdfStatus(pdfavail);
        }
        
        //13. MR3:  Active Sales Restriction code  -  /Title/ActiveSalesRestrictionCodes/ActiveCode
        String activeCode = json.optString("ActiveCode");
        if(null != activeCode) {
           //System.out.println("MR3 Active Sales Restriction Code :" +activeCode);
        }
        
        //15. MR3:  Sales Restriction code  -  /Title/Restrictions/Restriction/Code*
        //16. MR3:  Sales Restriction description  -  /Title/Restrictions/Restriction/Type
        //17. MR3:  Sales Restriction start date  -  /Title/Restrictions/Restriction/RestrictionDates/StartDate
        //18. MR3:  Sales Restriction end date  -  /Title/Restrictions/Restriction/RestrictionDates/EndDate
        
        JSONArray sRestrictions = json.getJSONArray("Restrictions");
        List<SalesRestriction> salesrestrictionresults = null;
        salesrestrictionresults = Lists.newArrayList();
        for (int i = 0; i < sRestrictions.length(); i++) {
            
            JSONObject srestriction = sRestrictions.getJSONObject(i); 

            String salesStartDate = "";
            String salesEndDate = "";
            String code = srestriction.optString("Code");
            String type = srestriction.optString("Type");
            String rDates = srestriction.optString("RestrictionDates");
            rDates = rDates.replaceAll("\"", "");
			Pattern pattern = Pattern.compile(".*StartDate:(.*)}");
			Matcher matcher = pattern.matcher(rDates);
			if (matcher.find())
			{
				salesStartDate = matcher.group(1);
			}
			pattern = Pattern.compile(".*EndDate:(.*)}");
			matcher = pattern.matcher(rDates);
			if (matcher.find())
			{
				salesEndDate = matcher.group(1);
			}
			

            SalesRestriction salesRestrictions = new SalesRestriction();
            if (null != code) {
               salesRestrictions.setCode(code);
            }
            if (null != type) {
            	salesRestrictions.setDescription(type);
            }
            if (null != salesStartDate && !salesStartDate.isEmpty()) {
            	String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            	String formatRequest = "dd-MMM-yyyy";
            	try {
            		salesStartDate = convertDate(salesStartDate,datePattern,formatRequest);
            	} catch (ParseException e) {
            		e.printStackTrace();
            	}
            	salesRestrictions.setRestrictionStartDate(salesStartDate);
            }
            if (null != salesEndDate && !salesEndDate.isEmpty()) {
            	String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            	String formatRequest = "dd-MMM-yyyy";
            	try {
            		salesEndDate = convertDate(salesEndDate,datePattern,formatRequest);
            	} catch (ParseException e) {
            		e.printStackTrace();
            	}
            	salesRestrictions.setRestrictionEndDate(salesEndDate);
            }
            salesrestrictionresults.add(salesRestrictions);
          }
        result.setSalesRestrictions(salesrestrictionresults);

        //14. MR3:  Active Format Restriction code  -  N/A   - Talk to MR3   ex.  9712788
        //19. MR3:  Format Restriction code   -  /Title/Formats/
        //20. MR3:  Format Restriction description   -   N/A  - Talk to MR3
        //21. MR3:  Format Restriction start date   -  /Title/Formats/Format/RestrictionDates?  Doesn't match up with value
        //22. MR3:  Format Restriction end date  -  /Title/Formats/Format/RestrictionDates?  Doesn't match up with value
        JSONArray fRestrictions = json.getJSONArray("Formats");
        List<FormatRestriction> formatrestrictionresults = null;
        formatrestrictionresults = Lists.newArrayList();
        for (int i = 0; i < fRestrictions.length(); i++) {
            
            JSONObject frestriction = fRestrictions.getJSONObject(i); 

            String formatStartDate = "";
            String formatEndDate = "";
            String fCode = frestriction.optString("Code");
            String format = frestriction.optString("Format");
            String created = frestriction.optString("Created");
            String rDates = frestriction.optString("AvailableDate");
            rDates = rDates.replaceAll("\"", "");
			Pattern pattern = Pattern.compile(".*StartDate:(.*)}");
			Matcher matcher = pattern.matcher(rDates);
			if (matcher.find())
			{
				formatStartDate = matcher.group(1);
			}
			pattern = Pattern.compile(".*EndDate:(.*)}");
			matcher = pattern.matcher(rDates);
			if (matcher.find())
			{
				formatEndDate = matcher.group(1);
			}

            FormatRestriction formatRestrictions = new FormatRestriction();
            if (null != fCode) {
            	formatRestrictions.setCode(fCode);
            }
            //if (null != format) {
            //	formatRestrictions.setDesc(format);
            //}
            if (null != formatStartDate && !formatStartDate.isEmpty()) {
            	String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            	String formatRequest = "dd-MMM-yyyy";
            	try {
            		formatStartDate = convertDate(formatStartDate,datePattern,formatRequest);
            	} catch (ParseException e) {
            		e.printStackTrace();
            	}
            	formatRestrictions.setFormatRestrictionStartDt(formatStartDate);
            }
            if (null != formatEndDate && !formatEndDate.isEmpty()) {
            	String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            	String formatRequest = "dd-MMM-yyyy";
            	try {
            		formatEndDate = convertDate(formatEndDate,datePattern,formatRequest);
            	} catch (ParseException e) {
            		e.printStackTrace();
            	}
            	formatRestrictions.setFormatRestrictionEndDt(formatEndDate);
            }
            formatrestrictionresults.add(formatRestrictions);
          }
        result.setFormatRestrictions(formatrestrictionresults);
        
        
        //28. MR3:  Manuscript Media code  - /Title/ManuscriptMedium
        String mmCode = json.optString("ManuscriptMedium");
        if (null != mmCode && !mmCode.isEmpty()) {
        	//System.out.println("MR3 Manuscript :" +mmCode);
           ManuscriptMedia mmedia = new ManuscriptMedia();
           mmedia.setManuscriptMediaCode(mmCode);
           result.setManuscriptMedia(mmedia);
        }
       
        
        //29. MR3:  Manuscript Media description   N/A  - Ask DissOps
        
        //32. MR3:  Open Access Flag    - /Title/OpenAccessFlag
        String oaFlag = json.optString("OpenAccessFlag");
        if (null != oaFlag) {
           result.setOpenAccessFlag(oaFlag);
        }

        //40. MR3: External ID  -  /Title/ExternalID
        String xID = json.optString("ExternalID");
        if (null != xID) {
           result.setExternalId(xID);
        }
        
        // Split publication date which is PublicationDate and is in format dd-MMM-yyyy at this point.
        String dissVolumeIssue = "";
        if (null != VolIssDate && !VolIssDate.isEmpty()) {
           //System.out.println("VOLISS UNSPLIT :" +VolIssDate);
           String[] dateStrs = VolIssDate.split("\\-");
           String pubMonth = dateStrs[1];
           pubMonth = pubMonth.toUpperCase();
           String publicationYear = dateStrs[2];
           int pubYear = Integer.parseInt(publicationYear);
           //  Vol/Iss.   Not pulled from MR3, but other parts of this are pulled by MR3 and it is assembled here.
          dissVolumeIssue = DisVolIssProvider.DisVolIssProvider(pubYear,pubMonth);
        }
        else {
        	throw new Exception();
        }
        
        //  MR3: DBTypeCode
        //Batch items = new Batch();
        String disstype= json.optString("DissType");
        String dissdesc = "";
        if (null != disstype) {
        	if ((disstype.equals("DAC")) || (disstype.equals("DAI"))) {
        		disstype = "DAI";
        		dissdesc = "Dissertations Abstracts International";
        	}
        	if (disstype.equals("MAI")) {
        		dissdesc = "Masters Abstracts International";
        	}
              items.setVolumeIssue(dissVolumeIssue);
            items.setDAISectionCode(disscode);
            items.setDBTypeDesc(dissdesc);
        	items.setDBTypeCode(disstype);
        }
        result.setBatch(items);
        
        //63. MR3:  Dissertations valid source  -  /Title/DissertationsValidSource
        String dissValidSource = json.optString("DissertationsValidSource");
        if (null != dissValidSource) {
           result.setDisValidSource(dissValidSource);
        }
     
        //64. MR3:  Dissertations available formats  - /Title/DissertationsAvailableFormats/AvailableFormat*
        List<String> avformatname = null;
        avformatname = Lists.newArrayList();
        String availableFormats = json.optString("DissertationsAvailableFormats");
        if (null != availableFormats) {
        availableFormats = availableFormats.replaceAll("\"", "").replace("[", "").replace("]", "");
        String strArray[] = availableFormats.split(",");
        for(int i=0;i < strArray.length; i++) {
            avformatname.add(strArray[i]);
        }
        result.setDisAvailableFormats(avformatname);
        }
        

        //  MR3:  School Code Number
        String schoolCodeNumber = json.optString("SchoolCode");
        School school = new School();
		  if (null != schoolCodeNumber) {
			  //System.out.println("MR3 Schoolcode:" +schoolCodeNumber);
			  school.setSchoolCode(schoolCodeNumber);
		  }
		  
        //  MR3:  School Name
        String schoolCodeName = json.optString("SchoolName");
        //School school = new School();
		  if (null != schoolCodeName) {
	          school.setSchoolName(schoolCodeName);
		  }
		  
	        //  MR3:  School Country
	        String schoolCountry = json.optString("SchoolCountry");
			  if (null != schoolCountry) {
				  school.setSchoolCountry(schoolCountry);
			  }
	          
		    //  MR3:  School State
		    String schoolState = json.optString("SchoolStateProvince"); 
			  if (null != schoolState) {
				  //System.out.println("MR3 School state province :" +schoolState);
				  school.setSchoolState(schoolState);
			  }
		  result.setSchool(school);
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
		
		public static String convertDate(String dateString, String datePattern, String formatRequest)  throws ParseException {
        	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        	//System.out.println("DATE STRING :" +dateString);
        	Date date = simpleDateFormat.parse(dateString);
        	//String PublicationDate= new SimpleDateFormat("dd-MMM-yyyy").format(date);
        	String PublicationDate= new SimpleDateFormat(formatRequest).format(date);
			return PublicationDate;
		}
		
		public static String createVolumeIssue(Date creationDate) {
			String volumeIssue = "";
			return volumeIssue;
		}
		
		//CBNEW
		private static Advisors getAdvisorsFor(String delimitedAdvisorStr) throws SQLException {
			Advisors result = null;
			if (null != delimitedAdvisorStr && ! delimitedAdvisorStr.isEmpty()) {
				result = new Advisors();
				result.setAdvisorsExodusStr(delimitedAdvisorStr);
				List<Advisor> advisors = Lists.newArrayList();
				List<String> advisorNames = SplitAdvisors.split(delimitedAdvisorStr); 
				//List<String> altAdvisorNames = getAlternateAdvisorsFor(itemId);
				for (int i=0; i<advisorNames.size(); ++i) {
					Advisor item = new Advisor();
					item.setAdvisorFullName(advisorNames.get(i));
					//if (altAdvisorNames.size() >= i+1) {
					//	item.setAltAdvisorFullName(altAdvisorNames.get(i));
					//}
					advisors.add(item);
				}
				result.setAdvisor(advisors);
			} 
			return result;
		}

		//CBNEWEND
		
        //47. Author LOC citizenship    * Dropped per Mark Dill.   *DONE
        //54. Page number   * Not available in ECMS.  Dropped per Mark Dill.   *DONE
        //60. Keyword Source    * Not available.  ECMS may be updated to hold this. Jessica will add this into ECMS.
        //62. Reference location    * Drop per Mark Dill   *DONE
        //65. FOP Quantity.*  Drop per Mark Dill  *DONE
        //Note: AuthorCitizenship will be removed.  *DONE
		
		//NOTE:  Need to Add ORCID and REPOSITORY
		//  ORCID  -  Comes from Dis_Authors
		//  REPOSITORY -  Comes from dvr_harvest_source in dis_valid_repositories
		
	}
