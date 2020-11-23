<%@ page language="java" pageEncoding="GBK"%>
<%
	String contextName = request.getContextPath();
	String command = request.getParameter("command");
	if( command == null ) {
		command = "";
	}
	
	String objectId = request.getParameter("objectId");
	if( objectId == null ) {
		objectId = "";
	}
%>
<!-- saved from url=(0014)about:internet -->
<html lang="en">
<!-- 
Smart developers always View Source. 

This application was built using Adobe Flex, an open source framework
for building rich Internet applications that get delivered via the
Flash Player or to desktops via Adobe AIR. 

Learn more about Flex at http://flex.org 
// -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">  
<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->
<title>微金时代工作流程设计器</title>
<script src="AC_OETags.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body { margin: 0px; overflow:hidden }
</style>
<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 10;
// Minor version of Flash required
var requiredMinorVersion = 2;
// Minor version of Flash required
var requiredRevision = 124;
// -----------------------------------------------------------------------------
// -->
</script>
</head>

<body scroll="no">
<script language="JavaScript" type="text/javascript">
<!--
var command = "<%=command%>";
var objectId = "<%=objectId%>";
var contextName = "<%=contextName%>";
// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	// DO NOT MODIFY THE FOLLOWING FOUR LINES
	// Location visited after installation is complete if installation is required
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = window.location;
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

    var flashVars = "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"";
    var appVars = "&UploadURL=FileUploadServlet";
    appVars += "&DownloadURL=wfdl";
    appVars += "&ProcessService=ProcessService";
    appVars += "&UserService=UserService";
    appVars += "&GroupService=GroupService";
    appVars += "&BranchService=BranchService";
    appVars += "&Context=/" + contextName + "/";
    if( command != "" ) {
    	 appVars += "&Command=" + command;
    }
    if( objectId != "" ) {
    	 appVars += "&ObjectId=" + objectId;
    }
    
    flashVars += appVars;
    

	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", flashVars,
		"width", "100%",
		"height", "100%",
		"align", "middle",
		"id", "WFDLDesigner",
		"quality", "high",
		"bgcolor", "#ffffff",
		"name", "WFDLDesigner",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
} else if (hasRequestedVersion) {
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	var flashVars = "";
	var appVars = "UploadURL=FileUploadServlet";
    appVars += "&DownloadURL=wfdl";
    appVars += "&ProcessService=ProcessService";
    appVars += "&UserService=UserService";
    appVars += "&GroupService=GroupService";
    appVars += "&BranchService=BranchService";
    appVars += "&Context=/" + contextName + "/";
    if( command != "" ) {
    	 appVars += "&Command=" + command;
    }
    if( objectId != "" ) {
    	 appVars += "&ObjectId=" + objectId;
    }
    
  	flashVars += appVars;
  	
	AC_FL_RunContent(
			"src", "WFDLDesigner",
			"FlashVars", flashVars,
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "WFDLDesigner",
			"quality", "high",
			"bgcolor", "#ffffff",
			"name", "WFDLDesigner",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
	);
  } else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
  }
// -->
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="WFDLDesigner" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="WFDLDesigner.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="WFDLDesigner.swf" quality="high" bgcolor="#ffffff"
				width="100%" height="100%" name="WFDLDesigner" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</noscript>
</body>
</html>
