package com.proquest.mtg.dismetadataservice.media;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.mtg.services.bindings.docfrosting.DocFrosting;

public class PdfMediaInfoFactory_Tests {
	static String baseUrl;
	static DocFrosting docFrosting;
	static PdfMediaInfoFactory target;
	static List<PdfMediaInfo> pdfs;

	public static File getRootDir() throws Exception {
		String appRootDirResourceLocatorFile = "PdfMediaInfoFactory_Tests/ResourceLocator.txt";
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader
				.getResource(appRootDirResourceLocatorFile);
		if (null == url) {
			fail("Failed to find test resource: "
					+ appRootDirResourceLocatorFile);
		}
		return new File(url.toURI().getPath()).getParentFile();
	}
	
	@BeforeClass
	public static void setUp() throws Exception {
		loadDocFrostingFromFile();
		baseUrl = "http://preprodservices.aa1.pqe";
		target = new PdfMediaInfoFactory(baseUrl);
		pdfs = target.makeFrom(docFrosting);
	}

	private static void loadDocFrostingFromFile() throws Exception {
		File docFrostingFile = new File(getRootDir(), "ExampleDocFrosting.xml");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(docFrostingFile);
			docFrosting = DocFrosting.unmarshal(fileReader);
		} finally {
			if (null != fileReader) {
				fileReader.close();
			}
		}
	}
	
	@Test
	public void hasCorrect_BaseUrl() throws Exception {
		assertThat(target.getBaseUrl().toString(), is(baseUrl));
	}
	
	@Test
	public void hasCorrect_NumberOfPdfs() throws Exception {
		assertThat(pdfs.size(), is(2));
	}
	
	@Test
	public void hasCorrect_PdfType_Pdf1() throws Exception {
		assertThat(pdfs.get(0).getPdfType(), is(PdfType.IMAGE_300DPI));
	}
	
	@Test
	public void hasCorrect_PdfType_Pdf2() throws Exception {
		assertThat(pdfs.get(1).getPdfType(), is(PdfType.SEARCHABLE));
	}
	
	@Test
	public void hasCorrect_Url_Pdf1() throws Exception {
		assertThat(pdfs.get(0).getUrlSpec(), 
				is(baseUrl + "/media/pq/classic/doc/2826902241/fmt/ai/rep/NPDF"));
	}
	
	@Test
	public void hasCorrect_Url_Pdf2() throws Exception {
		assertThat(pdfs.get(1).getUrlSpec(), 
				is(baseUrl + "/media/pq/classic/doc/2826902241/fmt/ai/rep/SPDF"));
	}
}
