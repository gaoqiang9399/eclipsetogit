<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String reportId = request.getParameter("reportId");
	String reporttype = request.getParameter("reporttype");
	String funRoleType = request.getParameter("funRoleType");
	String type = request.getParameter("type");
	String uid = request.getParameter("uid");
	String reportUrl = request.getParameter("reportUrl");
	String reportProjectFlag = request.getParameter("reportProjectFlag");
	String currMonth = request.getParameter("currMonth");
	String currDate = request.getParameter("currDate");
	String yesterday = request.getParameter("yesterday");
	String reduceThirtyOne = request.getParameter("reduceThirtyOne");
	String id = request.getParameter("id");
	String lastMonth = request.getParameter("lastMonth");
	String preIndex="designPreviewIndex.jsp";
	if("C".equals(reporttype)){
		preIndex="designPreviewIndexC.jsp";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>报表入口页面</title>
	<link rel="stylesheet" href="${webPath}/component/report/css/MfReportEntrance.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/component/include/laydate/need/FlexoCalendar.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
	<script type="text/javascript" src="${webPath}/component/include/laydate/FlexoCalendar.js"></script>
	<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
	<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<body class="overflowHidden">
<table style="width: 100%; height: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
	<tbody>
	<tr>
		<td style="overflow: auto;padding-left:5px;padding-right:5px;">
			<table width="100%" height="100%" style="table-layout: fixed;">
				<tbody>
				<tr>
					<td  ><div class="searchlist" style="-ms-overflow-x: auto;margin-top:5px;">
						<div style="width: 6%;float: right;margin-right: 8px;">
							<a class="ui-btn ui-btn-sp" style="margin-top: 1px;width: 100%;text-align: center;"
							   onclick="ReportEntrance.openSaveSqlCondition()">查询</a>
							<a class="ui-btn ui-btn-sp" style="padding: 0px;width: 100%;text-align: center;"
							   onclick="ReportEntrance.saveCurSqlCondition()">设为默认</a>
						</div>
						<form id="btnSearchForm" action="" method="post" target="ifmreport">
							<input type="hidden" id="sqlCondition" name="sqlCondition" value="">
							<input type="hidden" id="sqlMap" name="sqlMap" value="">
							<input type="hidden" id="queryStr" name="queryStr" value="">
							<input type="hidden" id="conValue" name="conValue" value="">
							<ul class="filter-list" id="more-conditions"
								style="padding-left: 0px; border-bottom: 1px solid #ddd;margin-bottom:0px;">
							</ul>
							<input type="button" class="btnsearch" value="查询1" onclick="dosearch()"/>
						</form>
					</div>
					</td>
				</tr>
				</tbody>
			</table>
		</td>
	</tr>
	<tr id="op_more_tr" style="display:none;">
		<td valign="top" style="text-align:center;height:30px;" >
			<span style="display:none; background:#F1FAF9;padding-left:50px;padding-right:50px;cursor:pointer" tabindex="-1" data-flag="1" onclick="ReportEntrance.getMoreLi(this)" id="shouqi_icon"> <img src="images/shouqi.png" style="width:10px;height:10px;">&nbsp;&nbsp;收起查询条件</span>
			<span style="background:#F1FAF9;padding-left:50px;padding-right:50px;cursor:pointer" tabindex="-1" data-flag="0" onclick="ReportEntrance.getMoreLi(this)" id="zhankai_icon"> <img src="images/zhankai.png" style="width:10px;height:10px;">&nbsp;&nbsp;展开查询条件</span>
		</td>
	</tr>
	<tr>
		<td valign="top" style="padding-left:5px;padding-right:5px;" height="100%"><div style="width:100%;height:100%;z-index:-1;">
			<iframe id="ifmreport" name="ifmreport" src="about:blank" frameborder="0" style="width: 100%; height: 100%;"></iframe>
		</div></td>
	</tr>
	</tbody>
</table>

<script type="text/javascript" src="${webPath}/component/report/js/MfReportEntrance.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var openReportId = "<%=reportId%>";
	var reportId = "<%=reportId%>";
	var openReporttype = "<%=reporttype%>";
	var openUid = "<%=uid%>";
	var openPreIndex = "<%=preIndex%>";
	var funRoleType = "<%=funRoleType%>";
	var type = "<%=type%>";
	var id = "<%=id%>";
	var currMonth = "<%=currMonth%>";
	var reportUrlNew = "<%=reportUrl%>";
	var reportProjectFlag = "<%=reportProjectFlag%>";
	var lastMonth = "<%=lastMonth%>";
	var currDate = "<%=currDate%>";
	var yesterday = "<%=yesterday%>";
	var reduceThirtyOne = "<%=reduceThirtyOne%>";
	var javaUrl = "";
	function openReportAction(){
		jQuery.ajax({
			url:webPath+"/mfReportQueryConditionUser/reportQuery",
			data:{reportId:openReportId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){
			},success:function(data){
				ReportEntrance.openInit();
				if(data.flag == "success"){
					if(reportProjectFlag=="2"){
						var reportUrl = reportUrlNew+ "/RDP-SERVER/rdppage/show/"+openUid;
					}else{
						var reportUrl = reportUrlNew+ "/REPORT_RC/report/design/"+openPreIndex+"?reporttype="+openReporttype+"&uid="+openUid;
					}
					$('#btnSearchForm').attr("action",reportUrl);
					$('#sqlCondition').val(data.querySqlCondition);
					$('#btnSearchForm').submit();
					$("#beginDate").val("<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>");
					ReportEntrance.openSaveSqlCondition();
				}else if(data.flag == "error"){
				}
			},error:function(data){
			}
		});
	}
	$(function(){
		ReportEntrance.showOpenQueryCondition(openReportId);
		if(type!='java'){
			openReportAction();
		}else{
			jQuery.ajax({
				url:webPath+"/mfReportQueryConditionUser/reportQuery",
				data:{reportId:openReportId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){
				},success:function(data){
					if(data.flag == "success"){
						ReportEntrance.openInit();
						//var reportUrl = "MfAppEfficiencyAction_appEfficiency.action?reportId=report-appEfficiency";
						ReportEntrance.getJavaUrl(reportId,reportUrlNew);

						$('#btnSearchForm').attr("action",javaUrl);
						if(openReportId=="report-aj-dayAddBus"||openReportId=="report-aj-dayAddBusAsset"||openReportId=="report-aj-busAccumulative"||openReportId=="report-aj-busAccumulativeAsset"){
							var yesDate = yesterday.substring(0, 4)+"-"+yesterday.substring(4,6)+"-"+yesterday.substring(6,8);
							$('#beginDate').val(yesDate);
							$('#endDate').val(yesDate);
						}
						ReportEntrance.openSaveSqlCondition();
					}else if(data.flag == "error"){
					}
				},error:function(data){
				}
			});

		}


	});

</script>
</body>
</html>