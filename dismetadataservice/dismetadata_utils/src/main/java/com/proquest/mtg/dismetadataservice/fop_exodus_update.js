// Is invoked each time a new job arrives in one of the input folders for the flow element.
// The newly arrived job is passed as the second parameter.
function jobArrived( s : Switch, job : Job )
{

    var myFopStatus = 'completed';
	var myOldStatus = 'Roll Submitted';
    var fopMessage = 'Roll Completed';
    var thisRoll = job.getVariableAsString('[Job.Name]');
	job.log (1, "Incoming ROLLID: %1", thisRoll );

  //Database: fop-workit-test-devl.c4dsldybdipr.us-east-1.rds.amazonaws.com:3306";
  var theDataSource ="Fop_Workit_2";
  var theUser = "workit";
  var thePassword = "pw4workit";

  //connect to the database
  var theConnection = new DataSource();
  var theConnectResult = theConnection.connect( theDataSource, theUser, thePassword );

  job.log( 1, "The Connect Result is: " + theConnectResult );
  if(theConnectResult==0)
  {
		job.fail( "Failed to connect to database " + theDataSource + " with user " + theUser + ".");
  }
  else
  {
		job.log(1, "Connected to database " + theDataSource + " with user " + theUser + ".");
  }
  
  var theSQLStatement = "select * from pubInfo where pubNumbersToCreateRoll='" + thisRoll + "'";
  job.log( 1, "SQL Statement: %1", theSQLStatement);
  var SQLSelect = theSQLStatement;
  var theSelectStatement = new Statement( theConnection );
  var myResult = theSelectStatement.execute( SQLSelect );

  var pubID;
  var filmFormat;
  var jobID;
  
  do {
  theSelectStatement.fetchRow();
  pubID = theSelectStatement.getString("PublicationID");
  filmFormat = theSelectStatement.getString("RequestedOutput");
  jobID = theSelectStatement.getString("jobID");

  job.log(1, "PUBID: %1", + pubID);
  job.log(1, "FORMAT: %1", + filmFormat);
  job.log(1, "JOBID: %1", + jobID);
	
	var format;
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

	job.log(1, "EXODUS FILM FORMAT CODE: %1", format );
	var theHTTP = new HTTP(HTTP.NoSSL);
	var pubURL = "http://preproddiss-services.aa1.pqe/dismetadata_service/disout/fopformats/updateAvailableFormats/" + pubID + "/" + format;
	var fopstatusURL = "http://preproddiss-services.aa1.pqe/dismetadata_service/disout/foppubs/updateInProgressStatus/" + pubID + "/N";
	theHTTP.url = pubURL;
    theHTTP.timeOut = 0;

	job.log(1, "available format url" + theHTTP.url);
   var theResponseFilePath = job.createPathWithName( job.getNameProper(), false );
   theHTTP.localFilePath = theResponseFilePath;
   theHTTP.get();
   //var theServerResponse = theHTTP.getServerResponse().toString( "UTF-8" )
   while( !theHTTP.waitForFinished( 3 ) ) { }
   if ( theHTTP.finishedStatus == HTTP.Ok && theHTTP.statusCode == 200 )
   {
		job.log( 1, "EXODUS format update successful for pubid: %1", pubID );
   }
   else {
       job.fail( "EXODUS format update failed for pubid: %1", pubID );
   }
   var theServerResponse = theHTTP.getServerResponse().toString( "UTF-8" );
   
	theHTTP.url = fopstatusURL;

	job.log(1, "fopstatus url" + theHTTP.url);
   var theResponseFilePath = job.createPathWithName( job.getNameProper(), false );
   theHTTP.localFilePath = theResponseFilePath;
   theHTTP.get();
   //var theServerResponse = theHTTP.getServerResponse().toString( "UTF-8" )
   while( !theHTTP.waitForFinished( 3 ) ) { }
   if ( theHTTP.finishedStatus == HTTP.Ok && theHTTP.statusCode == 200 )
   {
		job.log( 1, "EXODUS status update successful for pubid: %1", pubID );
   }
   else {
       job.fail( "EXODUS status update failed for pubid: %1", pubID );
   }
   var theServerResponse = theHTTP.getServerResponse().toString( "UTF-8" );
      } //do while loop
   while(theSelectStatement.isRowAvailable());
   
   var theSQLStatement = "update pubInfo set Status='" + myFopStatus + "', StatusMessage='" + fopMessage + "' where pubNumbersToCreateRoll='" + thisRoll + "' and Status ='" + myOldStatus + "'";
  job.log( 1, "SQL Statement: %1", theSQLStatement);
  var SQLUpdate = theSQLStatement;
  var theUpdateStatement = new Statement( theConnection );
  var myResult = theUpdateStatement.execute( SQLUpdate );

  if( myResult ) {

		job.log( 1, "DB update successful.");

		}

		else {

			job.log( 3,"DB Update failed: %1", myResult );

		}
		if (( myFopStatus == 'Fail' ) || ( !myResult)) {

		   job.fail( "FOP Processing failed");

		}

		else {

			job.log(1, "FOP processing was successful");

		}

}