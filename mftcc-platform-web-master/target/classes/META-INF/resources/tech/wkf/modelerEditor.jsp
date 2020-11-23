<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <title>模板设计</title>
	<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
<link rel="stylesheet" type="text/css" href="css/GooFlow2.css"/>
<link rel="stylesheet" href="ztree/css/zTreeStyle.css" type="text/css"/>
<link rel="stylesheet" href="ztree/css/zTreeMenuStyle.css" type="text/css"/>
<link rel="stylesheet" href="css/model.css" type="text/css"/>
<link rel="stylesheet" href="css/jq22.css">
<link rel="stylesheet" href="css/properties.css">
<link rel="stylesheet" href="css/modelAlert.css">
 
<!-- <link rel="stylesheet" type="text/css" href="css/GooFlow.css"/> -->
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/jquery.layout-latest.js"></script>
<script src="js/GooFunc.js"></script>
<link rel="stylesheet" type="text/css" href="css/default.css"/>
<script src="js/GooFlow.js"></script>
<script src="ztree/js/jquery.ztree.all-3.5.min.js"></script>
 <script src="ztree/js/ztree.js"></script>
<script src="js/commonProperties.js"></script>
<script src="js/commonTools.js"></script>
<!--<script src="js/properties.js"></script>--> 
<script src="js/alertDiv.js"></script>
<script src="js/velocity.js"></script>
<script src="js/velocity.ui.js"></script>
<script src="js/Quttons.js"></script>
<script src="js/propertiesV2.js"></script>

<script src="js/lavalamp.min.js"></script>
<script src="js/jquery.form.js"></script>

<link href="css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.mousewheel.min.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- select2 -->
	<link rel="stylesheet" type="text/css" href="css/select2/select2.min.css"/> 
	<link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.css"/>
	<script src="js/select2/select2.js" type="text/javascript"></script>
<script type="text/javascript">
var property={
	width:4000,
	height:2000,
	haveHead:true,
	headBtns:[{type:"open",name:"导入"},{type:"download",name:"导出"},{type:"saveAndStart",name:"保存并启动"},{type:"save",name:"保存"},{type:"list",name:"流程列表"}],
	modelBtns:[{type:"cut",name:"剪切"},{type:"copy",name:"复制"},{type:"paste",name:"粘贴"},{type:"redo",name:"撤销"},{type:"undo",name:"前进"},{type:"reload",name:"刷新"}],
	useOperStack:true
};
var golFlow;
var realPath = '${webPath}';
$(document).ready(function(){
	 golFlow=$.createGooFlow($('#toolDiv'),$('#modelerTools'),$('#modeler'),property,true);
	 $('body').layout({ 
		    applyDefaultStyles: true,
		    north__closable:false,
		    north__size:45,
		    west__size:85,//pane的大小
				spacing_open:0,
		    sliderCursor:"pointer"//在某个Pane隐藏后，当鼠标移到边框上时的指针样式。
			 });
});
</script>
</head>
<body onselectstart="return false"  oncontextmenu="self.event.returnValue=false;" ondragstart="return false;">
		<div class="ui-layout-north" >
			<div id="toolDiv" class = "toolDiv">
				<!--<a href="javascript:" onclick="self.location=document.referrer;"><img style="cursor:pointer; float:right;margin:6px;" src="imgs/return.png" /></a>-->
			</div>
			<form id="wkfopenform" action="${webPath}/wkfDesgin/open.action"
            	enctype="multipart/form-data" method="post" style="display:none;">
        		<input type="file" id="wkfopen" name="upload" accept=".wfdl.xml"/>
        	</form>
		</div>
	
		<div class="treeDivbg ui-layout-west" >
			<!--<div style ="border-bottom: 1px solid #4c4c4c;background-color: #343436;border-top-left-radius: 3px;border-top-right-radius: 3px;color: white;height: 17px;" align="left"></div>-->
    		<div class="zTreeDemoBackground" id="zTreeDemoBackground">
				<ul id="tree" class="ztree"></ul>
			</div>
		</div>
		<!--<div class="ui-layout-east">
    		<div id = "property" style="width:100%;height:100%;background-color:#F1F1F1;">
    		</div>
		</div>-->
		<div class="ui-layout-center">
			<div id = "modelerTools" class="GooFlow" style="border-bottom: 1px solid #dadada;width:100%;height:30px;background:#F6F6F8;"></div>
			<div id = "modeler"></div>
		</div>
		
		<div class="cd-popup" role="alert">
			<div class="cd-popup-container">
				<div class="qutton_close" onclick="hideModelAlert();"></div>
			</div>
		</div> 
		<div class="double-bounce">
			<div class="double-bounce1"></div>
			<div class="double-bounce2"></div>
		</div>
</body>
</html>