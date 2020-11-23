<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ webPath + "/";
%>
<!DOCTYPE html>
<html>
    <head>
    <script type="text/javascript">
    var evalScenceNo = '${evalScenceNo}';
    var webPath="${webPath}";
    </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/themes/view/js/jquery.serializejson.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/EvalScenceConfig.css"/>
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" /> 
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-main.css" /> 
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="${webPath}/component/sys/css/B1.css" />
		<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css" />
		<%--时间轴--%>
		<script type="text/javascript" src="${webPath}/component/include/navLine.js"></script>
		<!--加载动画js  -->
 		<%-- <script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js"></script> --%>
    </head>
    <body class="overflow otherStyleBody">
    <dhcc:markPoint markPointName="EvalScenceConfig_Setting"/>
    	<div class="content">
    		<div id = "configDiv" class="row row_content" style="margin-top:20px;">
    			<div class="col-md-10 col_content content_setting" style="width: 100%;">
    				<div class="bgColor">
    					<div class="title">
    						<h5>检查模型：${mfExamineTemplateConfig.templateName}</h5>
    						<div class="li_btn" style="float: left;">
	    						<input type="button" value="新增检查卡" onclick="MfExamineCard.addExamCard()">
    						</div>
    					</div>
    					<div class="table_content" style="height:auto;width: 100%;">
    						<div class="settings" style="width: 80%;position:absolute">
    							
    						</div>
    					</div>
    					<div class="work-zone-timeLine col-sm-2" style="position: fixed;margin-top: 10%;margin-right: 35px;height:70%">
							<div class="time_contents">
								<div class="time-line-bg">
									<div class="time-line-line" style="height: 293px;"></div>
									<div class="time-line-body">
										<dl class="time-line-dl"></dl>
									</div>
								</div> 
							</div>
						</div>
    				</div>
    			</div>
    		</div>
    	</div>
 	</body>
<script type="text/javascript"
	src="${webPath}/component/examine/js/ExamineIndexConfig_Setting.js"></script>
<script type="text/javascript"
	src="${webPath}/component/examine/js/MfExamineCard.js"></script>
<script type="text/javascript">
	var evalIndexTypeRel = '${evalScenceConfig.evalIndexTypeRel}';
	var templateId='${mfExamineTemplateConfig.templateId}'
	var AppPropertyDatas={};
	var examIndexDatas="";
	var dxDatas = {};
	var levelDatas = {};
	$(function() {
		getListForAppPropertyData();
		getByIdForList();
		navLine.createNavLine();
	});
</script>
</html>