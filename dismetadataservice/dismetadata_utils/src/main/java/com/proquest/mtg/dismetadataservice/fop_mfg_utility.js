// Is invoked each time a new job arrives in one of the input folders for the flow element.
// The newly arrived job is passed as the second parameter.
function jobArrived( s : Switch, job : Job )
{
	var pubID = job.getPrivateData("PublicationID");
	var jobID = job.getPrivateData("jobID");
	var format = job.getPrivateData("RequestedOutput");
	var theHTTP = new HTTP(HTTP.NoSSL);
	var pubURL = "http://10.241.17.211:8080/fop_service/fop/manufacturing/submit/" + pubID + "/" + format;
	

	theHTTP.url = pubURL;
	theHTTP.timeOut = 0;
	job.log(2, "Attempting to connect to FOP Database....");

	//Database: fop-workit-test-devl.c4dsldybdipr.us-east-1.rds.amazonaws.com:3306";
  var theDataSource ="Fop_Workit";
  var theUser = "workit";
  var thePassword = "pw4workit";

  //connect to the database
  var theConnection = new DataSource();
  var theConnectResult = theConnection.connect( theDataSource, theUser, thePassword );

  job.log( 2, "The Connect Result is: " + theConnectResult );
  if(theConnectResult==0)
  {
		job.fail( "Failed to connect to database " + theDataSource + " with user " + theUser + ".");
  }
  else
  {
		job.log(2, "Connected to database " + theDataSource + " with user " + theUser + ".");
  }
  

  var theResponseFilePath = job.createPathWithName( job.getNameProper(), false );
  theHTTP.localFilePath = theResponseFilePath;
  theHTTP.get();

  while( !theHTTP.waitForFinished( 3 ) ) {  job.log( 5, "Working...", theHTTP.progress() ); }
  var myFopStatus = '';
  job.log(1, "http status" + theHTTP.finishedStatus);
  job.log(1, "http code" + theHTTP.statusCode);
  job.log(1, "http url" + theHTTP.url);
  if ( theHTTP.finishedStatus == HTTP.Ok && theHTTP.statusCode == 200 )
  {
	  myFopStatus = 'success';
	  job.log(1, "Successful fop_mfg processing for pubID %1", pubID);
  }
  else
  {
	  myFopStatus = 'fail';
	  job.log(1, "Failed processing fop_mfg with status code %1", theHTTP.statusCode );
  }

  var fopMessage = theHTTP.getServerResponse().toString( "UTF-8" );
  fopMessage = fopMessage.replace(/\'/g, "\\'");
  job.log( 1, "Server response: %1", fopMessage );

  // Update status code and message.
  //var theSQLStatement = "update pubInfo set Status='" + myFopStatus + "', StatusMessage='" + fopMessage + "' where publicationID='" + pubID + "'";
  var theSQLStatement = "update pubInfo set Status='" + myFopStatus + "', StatusMessage='" + fopMessage + "' where publicationID='" + pubID + "' and jobID ='" + jobID + "'";
  job.log( 1, "SQL Statement: %1", theSQLStatement);
  var SQLUpdate = theSQLStatement;
  var theUpdateStatement = new Statement( theConnection );
  var myResult = theUpdateStatement.execute( SQLUpdate );

  if( myResult ) {

		job.log( 3, "FOP_WORKIT DB update successful.");

		}

		else {

			job.log( "FOP_WORKIT DB Update failed: %1", myResult );

		}
		if (( myFopStatus == 'Fail' ) || ( !myResult)) {

		   job.fail( "FOP_WORKIT Processing failed");

		}

		else {

			job.log(1, "FOP_WORKIT processing was successful");

		}
job.sendToSingle(job.getPath());
}