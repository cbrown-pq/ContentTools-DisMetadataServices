package com.proquest.mtg.dismetadataservice.rest;

	import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

	import com.google.inject.Inject;
/*import com.proquest.mtg.dismetadataservice.exodus.FopFormatsDataQuery;*/
import com.proquest.mtg.dismetadataservice.exodus.FopFormatsDataProvider;
import com.proquest.mtg.dismetadataservice.format.FOPUpdateAvailablePubsFormatFactory;



	@Path("/fopformats/")
	public class FOPUpdateAvailableFormatsServiceProvider {
		
		private FOPUpdateAvailablePubsFormatFactory fopUpdateAvailablePubsFormatFactory;
		
		@Inject
		public FOPUpdateAvailableFormatsServiceProvider(FOPUpdateAvailablePubsFormatFactory fopUpdateAvailablePubsFormatFactory) {
			this.fopUpdateAvailablePubsFormatFactory = fopUpdateAvailablePubsFormatFactory;
		}
		
		
		public FOPUpdateAvailablePubsFormatFactory getFopUpdateAvailablePubsFormatFactory() {
			return fopUpdateAvailablePubsFormatFactory;
		}
		

		@GET
		@Path("/updateAvailableFormats/{pubID}/{format}")
		public String updateInprogressStatusFor(@PathParam("pubID") String pubNumber, @PathParam("format") String format) throws WebApplicationException {
			String[] formats = format.split(",");
			String result = null;
			try {
				System.out.println("\nProcessing Pub-Id: " + pubNumber);
				for (String filmType : formats) {
				    result = getFopUpdateAvailablePubsFormatFactory().updateFOPFormatsQuery(pubNumber,format);
				}
			} catch (IllegalArgumentException e) {
				throw new DisServiceException(Response.Status.NO_CONTENT);
			}catch (Exception e) {
				e.printStackTrace();
				throw new DisServiceException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
			}
			return result;
		}



		}


