// Is invoked each time a new job arrives in one of the input folders for the flow element.
// The newly arrived job is passed as the second parameter.
function jobArrived( s : Switch, job : Job )
{
	var pubID = job.getPrivateData("publicationID");
	var jobID = job.getPrivateData("jobID");
	var filmFormat = job.getPrivateData("RequestedOutput");
	
	if (filmFormat == '105')
	{
		format = 'MFC';
	}
	else if (filmFormat == '35')
	{
		format = 'MFL';
	}
	else
	{
		job.log( 1, "ERROR: Invalid film format:", filmFormat );
		job.log (1, "FAILURE for pubid:", pubID );
	}

	var theHTTP = new HTTP(HTTP.NoSSL);
	var pubURL = "http://10.241.17.211:8080/dismetadata_service/disout/fopformats/updateAvailableFormats/" + pubID "/" + format;

	theHTTP.url = pubURL;
    theHTTP.timeOut = 0;
	job.log(2, "Attempting to connect to FOP Database....");

	//Database: fop-workit-test-devl.c4dsldybdipr.us-east-1.rds.amazonaws.com:3306";
  var theDataSource ="Fop_Workit";
  var theUser = "workit";
  var thePassword = "pw4workit";
   var theResponseFilePath = job.createPathWithName( job.getNameProper(), false );
   theHTTP.localFilePath = theResponseFilePath;
   theHTTP.get();
   var theServerResponse = theHTTP.getServerResponse().toString( "UTF-8" )
   while( !theHTTP.waitForFinished( 3 ) ) { }
   if ( theHTTP.finishedStatus == HTTP.Ok && theHTTP.statusCode == 200 )
   {

		job.log( 3, "EXPEP update successful for pubid:", pubID );

		}

		else {

			job.fail( "EXPEP update failed for pubid:", pubID );

		}
job.sendToSingle(job.getPath());
}

// Is invoked at regular intervals regardless of whether a new job arrived or not.
// The interval can be modified with s.setTimerInterval().
function timerFired( s : Switch )
{
}