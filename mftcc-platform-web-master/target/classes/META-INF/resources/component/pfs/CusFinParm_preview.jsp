<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html style="height:100%">
	<head>
		<title></title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
	<style type="text/css">
		.ls_list tr td.td-des{
			text-align: left;
			width: 50%;
			padding-left: 20px;
		}
		.ls_list tr td.td-des1{
			text-align: left;
			width: 50%;
			padding-left: 40px;
		}
		.ls_list tr td.td-des2{
			text-align: left;
			width: 50%;
			padding-left: 60px;
		}
		.ls_list tr td.td-des6{
			text-align: left;
			width: 35%;
			padding-left: 20px;
		}
		.ls_list tr td.td-des7{
			text-align: left;
			width: 35%;
			padding-left: 40px;
		}
		.ls_list tr td.td-des8{
			text-align: left;
			width: 35%;
			padding-left: 60px;
		}
		.ls_list tr td{
			border: 1px solid;
			font-size: 12px;
		}
		.ls_list tr th{
			border: 1px solid;
			font-size: 14px;
		}
		.ls_list thead tr{
			border: 1px solid;
			height: 30px;
			line-height: 30px;
			border-bottom: 2px solid;
			border-top: 2px solid;
		}
		.ls_list tbody tr{
			border: 1px solid;
			height: 25px;
			line-height: 25px;
		}
	</style>
	
	</head>
	<body style="overflow-y: hidden;background: white;">
		<!-- <div style="text-align: right; height: 50px;line-height: 50px; padding-top: 10px;">
			<select style=" margin-right: 50px; height: 25px;line-height: 25px; padding-top: 2px; padding-bottom: 2px;width: 150px">
				<option>本期数:本年数</option>
			</select>
		</div> 小贷特有的，这里暂不需要-->
		<div style="text-align: center; font-size: 18px;font-weight: bold;margin-top: 20px;">${reportName }</div>
		<div style="margin-top: 20px;padding-left: 20px;padding-right: 50px;">
			<span style="font-size: 14px;">编制单位：北京微金时代公司</span>  
			<span style="margin-left: 30%;font-size: 14px;">日期：2016年11月30日</span> 
			<span style="font-size: 14px;float: right;">单位：元</span>
		</div>
		<div style="padding: 20px;overflow: auto;" class="showReport-div">	
			<div>
			<%String reportType = (String)request.getAttribute("reportType"); 
			if("1".equals(reportType)){
			%>
 			<table class="ls_list"   id="parmShow-table1" style="background: white;width: 98%;">
			<thead><tr><th>资产</th><th>期末金额</th><th>负债和所有者权益(股东权益)</th><th>期末金额</th></tr></thead>
				<tbody>
				<c:forEach items="${cusFinParmList }" var="cusFinParm1" >
					<tr>
						<c:if test="${fn:length(cusFinParm1.cnt)==2 }">
							<td class="td-des6" >${cusFinParm1.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt)==4 }">
							<td class="td-des7" >${cusFinParm1.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt)!=4 && fn:length(cusFinParm1.cnt)!=2  }">
							<td class="td-des8" >${cusFinParm1.codeName}</td>
						</c:if>
						<td> </td> 
						
						<c:if test="${fn:length(cusFinParm1.cnt1)==2 }">
							<td class="td-des6" >${cusFinParm1.codeName1}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt1)==4 }">
							<td class="td-des7" >${cusFinParm1.codeName1}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt1)!=4 && fn:length(cusFinParm1.cnt1)!=2  }">
							<td class="td-des8" >${cusFinParm1.codeName1}</td>
						</c:if>
						<td> </td> 
						
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%}else{ %>
			<table class="ls_list"   id="parmShow-table2" style="width:98%;background: white;">
			<thead><tr><th>项目</th><th>期末金额</th></tr></thead>
				<tbody>
				<c:forEach items="${cusFinParmList }" var="cusFinParm1">
					<tr>
						<c:if test="${fn:length(cusFinParm1.cnt)==2 }">
							<td class="td-des" >${cusFinParm1.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt)==4 }">
							<td class="td-des1" >${cusFinParm1.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt)!=4 && fn:length(cusFinParm1.cnt)!=2  }">
							<td class="td-des2" >${cusFinParm1.codeName}</td>
						</c:if>
						<td> </td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			
			<%} %>
			</div>
			<div style="padding-left: 20px;padding-right: 20px;margin-top: 15px;">
				<span style="font-size: 14px;">填报单位公章</span>  
				<span style="margin-left: 20%;font-size: 14px;">法定代表人</span> 
				<span style="margin-left: 20%;font-size: 14px;">财务负责人</span> 
				<span style="font-size: 14px;float: right;">会计</span>
			</div>
		</div>
	</body>	
	<script type="text/javascript">
	$(function(){
		$(".showReport-div").height($("body").height()-125);
	 	 $(".showReport-div").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:false
			}
		}); 
	});
	</script>
</html>