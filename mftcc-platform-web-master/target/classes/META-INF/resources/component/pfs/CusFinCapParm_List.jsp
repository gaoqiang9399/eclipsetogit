<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src='${webPath}/component/pfs/js/CusFinCapParm_List.js'></script>
		<script type="text/javascript" src='${webPath}/component/pfs/js/CusFinCommon.js'></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFinCapParm_List.css"/>
	</head>
	<body style="overflow-y: hidden;height: 100%;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<button type="button" class="btn btn-primary pull-left" onclick="cusFinCapParmList.showNewParm('1');">新增</button>
						<ol class="breadcrumb pull-left">
							<li class="active">${reportName}</li>
						</ol>
					</div>
				</div>
			</div>
		</div>
		<div id="parmShow-div" class="table_content" style="padding-bottom: 25px;" >
			<table class="ls_list"  id="parmShow-table1" style="width: 50%;display:inline-table;">
				<thead>
					<tr><th colspan="2">资产</th></tr>
				</thead>
				<tbody>
				<c:forEach items="${cusFinParmList }" var="cusFinParm1" varStatus="status">
					<tr>
						<c:if test="${fn:length(cusFinParm1.cnt) ==2 }">
							<td class="td-des" >${cusFinParm1.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt) ==4 }">
							<td class="td-des1" >${cusFinParm1.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm1.cnt) !=4 && fn:length(cusFinParm1.cnt) !=2}">
							<td class="td-des2" >${cusFinParm1.codeName}</td>
						</c:if>
						<td class="td-caozuo">
							<span class="span-this" onclick="cusFinCapParmList.showNewParm('21','${cusFinParm1.codeColumn}','${cusFinParm1.codeName}',this,'${cusFinParm1.zcfzType}');">新增</span>
							<span class="span-this" style="margin-left: 20px;" onclick="cusFinCapParmList.showNewParm('31','${cusFinParm1.codeColumn}',null,this,'${cusFinParm1.zcfzType}');">配置</span>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<table class="ls_list"  id="parmShow-table2" style="display: inline-table;width: 49%;">
				<thead>
					<tr><th colspan="2">负债和所有者权益</th></tr>
				</thead>
				<tbody>
				<c:forEach items="${cusFinParmList1 }" var="cusFinParm2" varStatus="status">
					<tr>
						<c:if test="${fn:length(cusFinParm2.cnt) == 2}">
							<td class="td-des" >${cusFinParm2.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm2.cnt) == 4}">
							<td class="td-des1" >${cusFinParm2.codeName}</td>
						</c:if>
						<c:if test="${fn:length(cusFinParm2.cnt) != 2 && fn:length(cusFinParm2.cnt) != 4}">
							<td class="td-des2" >${cusFinParm2.codeName}</td>
						</c:if>
						<td class="td-caozuo">
							<span class="span-this" onclick="cusFinCapParmList.showNewParm('22','${cusFinParm2.codeColumn}','${cusFinParm2.codeName}',this,'${cusFinParm2.zcfzType}');">新增</span>
							<span class="span-this" style="margin-left: 20px;" onclick="cusFinCapParmList.showNewParm('32','${cusFinParm2.codeColumn}',null,this,'${cusFinParm2.zcfzType}');">配置</span>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<%-- <div style="display: none;padding-left: 10px;width: 800px;" id="newParm-div">
			<form id="newParm-form">
				<div id="newParm-dgcctag">
					<dhcc:bootstarpTag property="formpfs5008" mode="query"/>
				</div>
				<div class="table_content"  style="height: auto;display: none;" id="ys-div">
					<table  class="ls_list" style="width: 100%;" id="ys-table">
						<thead>
							<tr style="height:40px;"><th>名称</th><th>运算符</th><th>操作</th></tr>
						</thead>
						<tbody>
							<tr>
								<td style="width: 50%;">
									<input name="formulaName" onclick="cusFinCommon.selectParm(this);" style="width: 70%;">
									<input name="formulaColumn" style="display: none;">
								</td>
								<td style="width: 30%;">
									<select name="formulaType" style="width: 60%;">
										<option value="1">+</option>
										<option value="2">-</option>
									</select>
								</td>
								<td>
									<button type="button" name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="cusFinCommon.planInput(this);"></button>
									<button type="button" name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="cusFinCommon.planCancel(this);"></button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="formRowCenter">
	  				<dhcc:thirdButton value="保存" action="保存" onclick="cusFinCapParmList.ajaxInsertThis(this.form);"></dhcc:thirdButton>
	  				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="cusFinCommon.newParmClose();"></dhcc:thirdButton>
		  		</div>
	  		</form>
		</div> --%>
		<div style="display: none;padding-left: 10px;width: 800px; height:100%" id="newParm-div">
			<div class="container form-container">
			<form id="newParm-form" style="height:100%" >
				<div class="scroll-content">
					<div class="col-md-10 col-md-offset-1 margin_top_20">
						<div class="bootstarpTag">
							<div id="newParm-dgcctag">
								<dhcc:bootstarpTag property="formpfs5008" mode="query"/>
							</div>
							<div class="table_content"  style="height: auto;display: none;" id="ys-div">
								<table  class="ls_list" style="width: 100%;" id="ys-table">
									<thead>
										<tr style="height:40px;"><th>名称</th><th>运算符</th><th>操作</th></tr>
									</thead>
									<tbody>
										<tr>
											<td style="width: 50%;">
												<input name="formulaName" onclick="cusFinCommon.selectParm(this);" style="width: 70%;">
												<input name="formulaColumn" style="display: none;">
											</td>
											<td style="width: 30%;">
												<select name="formulaType" style="width: 60%;">
													<option value="1">+</option>
													<option value="2">-</option>
												</select>
											</td>
											<td>
												<button type="button" name="addBtn" class="btn addRow btn-mini showBtn curSelectedBtn showBtna" onclick="cusFinCommon.planInput(this);"></button>
												<button type="button" name="delBtn" class="btn delRow btn-mini showBtn curSelectedBtn showBtnb" onclick="cusFinCommon.planCancel(this);"></button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
		   		</div>
				<div class="formRowCenter">
	  				<dhcc:thirdButton value="保存" action="保存" onclick="cusFinCapParmList.ajaxInsertThis(this.form);"></dhcc:thirdButton>
	  				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="cusFinCommon.newParmClose();"></dhcc:thirdButton>
		  		</div>
		  		</form>
			</div>
	</div> 
	</body>	
	<script type="text/javascript">
		var $globalObj = null;
		var globalType = null;
		var globalUrl = null;
		var $formulaNameObj =null;
		var reportType= '${reportType}';
		var reportName= '${reportName}';
		$(function(){
			cusFinCapParmList.init();
		});
 </script>
</html>
