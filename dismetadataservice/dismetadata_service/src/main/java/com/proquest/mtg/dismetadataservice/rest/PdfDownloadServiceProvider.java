package com.proquest.mtg.dismetadataservice.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.inject.Inject;
import com.proquest.mtg.dismetadataservice.pdf.PDFDownloadOptions;
import com.proquest.mtg.dismetadataservice.pdf.PdfMediaProvider;
import com.proquest.mtg.dismetadataservice.pdf.SupportedPdfTypes;

@Path("/getPdf/")
public class PdfDownloadServiceProvider {
	private final PdfMediaProvider pdfMediaProvider;
	
	@Inject
	public PdfDownloadServiceProvider(PdfMediaProvider pdfMediaProvider) {
		this.pdfMediaProvider = pdfMediaProvider;
	}
	
	public PdfMediaProvider getPdfMediaProvider() {
		return pdfMediaProvider;
	}
		
	@GET
	@Path("/{pubNumber}/{pdfType}")
	@Produces("application/pdf")
	public Response getDisMetaData(@PathParam("pubNumber") String pubNumber,
			@PathParam("pdfType") String pdfType,
			@DefaultValue("0") @QueryParam("ec") int excludeCopyright,
			@DefaultValue("0") @QueryParam("ri") int restrictionIncluded) throws WebApplicationException {
		ResponseBuilder response = null;
		byte[] input = null;
		try {
			if (! SupportedPdfTypes.kSupportedPdfTypes.contains(pdfType.toUpperCase())) {
				throw new Exception("Pdf type : " + pdfType + " is not supported.");
			} else {
				PDFDownloadOptions  pdfDownloadOptions = new PDFDownloadOptions(
						pdfType, excludeCopyright, restrictionIncluded);				
				input = getPdfMediaProvider().downloadFor(pubNumber, pdfDownloadOptions);
				response = Response.status(Response.Status.OK).entity(input);
				response.header("Content-Disposition", "attachment; filename=out.pdf");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response =  Response.status(Response.Status.OK).entity(e.getMessage());
		}
		return response.build();
	}
}
