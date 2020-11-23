<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ webPath + "/";
	MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
%>
<!DOCTYPE html>
<html style="height:100%;width: 100%;">
    <head>
    <script type="text/javascript">
    var webPath = "${webPath}";
    var evalScenceNo = '${evalScenceNo}';
    var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
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
 		<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js"></script>
    </head>
    <body class="overflow otherStyleBody" style="height:100%;width: 100%;overflow-y:scroll;overflow-x:hidden;padding-right: 0px;">
    <dhcc:markPoint markPointName="EvalScenceConfig_Setting"/>
    	<div class="content" style="height:100%;width: 100%;">
    		<div id = "configDiv" class="row row_content" style="margin-top:10px;">
    			<div class="col-md-10 col_content content_setting" style="width: 100%;">
    				<div class="bgColor">
    					<div class="title">
    						<h5>评级模型：${evalScenceConfig.evalScenceName}</h5>
    						<div class="li_btn" style="float: left;">
	    						<input type="button" value="新增评分卡" onclick="MfEvalGradeCardComm.addGradeCard(this)">
	    						<input type="button" value="新增指标项" onclick="addEvalItem()">
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
									<div class="time-line-body" >
										<dl class="time-line-dl" style="margin-right: -100px;"></dl>
									</div>
								</div> 
							</div>
						</div>
    				</div>
    			</div>
    		</div>
    	</div>
 	</body>
<%-- <script type="text/javascript"
	src="${webPath}/component/eval/js/EvalScenceConfig.js"></script> --%>
	<script type="text/javascript"
	src="${webPath}/component/eval/js/EvalScenceConfigNew.js"></script>
<script type="text/javascript"
	src="${webPath}/component/eval/js/EvalScenceConfigOther.js"></script>
<script type="text/javascript"
	src="${webPath}/component/eval/js/MfEvalGradeCard_comm.js"></script>
<script type="text/javascript">
	var evalIndexTypeRel = '${evalScenceConfig.evalIndexTypeRel}';
	var AppPropertyDatas={};
	$(function() {
		getListForAppPropertyData();
		getByIdForList();
		navLine.createNavLine();
	});
</script>
</html>