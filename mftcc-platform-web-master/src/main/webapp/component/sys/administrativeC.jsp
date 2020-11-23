<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String layout = "layout/view";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link id="C" rel="stylesheet" type="text/css" href="${webPath}/<%=layout%>/page/css/C${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css" />
		<%-- <script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script> --%>
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	</head>
		<script type="text/javascript">
	function openProcessDesigner(){
		window.open("workflow/WFDLDesigner.jsp");
	}
	function openProcessDesignerNew(){
		window.open("tech/wkf/modelerEditor.jsp");
	}
	$(function(){
	$("body").mCustomScrollbar()
	})
	</script>
	<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="sysC"/>
		<div class="opt_div">
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>OA其他功能</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaFixedAssets/getListPage">资产管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaFixedAssetsTrans/getList">资产变动</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaCompanyPolicy/getListShow">制度查看</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaCompanyPolicy/getListPage">制度管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaVehicleUser/getListPage">车辆使用登记</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaAppGas/getListPage">油卡充值申请</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaVehicleCost/getListPage">车辆费用申请</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaVehicleInfo/getListPage">车辆管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaMroomInfo/getListPage">会议室管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaMeetingRecord/getListShow">会议纪要查看</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaSealInfo/getListPage">公章信息</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaAppSeal/getListPages">公章确认</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaOfficeSupplies/getListPage">办公用品管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaAppOffSup/getStsListPage">办公用品申领审批</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaGiftInfo/etListPage">礼品管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/oaCompanyArc/getListPage">公司资料管理</a>
					</li>
				</ul>
             </div>
		</div>
		
		<script type="text/javascript">
		//window.parent.getHelp();
		getHelp();
		</script>
	</body>
</html>
