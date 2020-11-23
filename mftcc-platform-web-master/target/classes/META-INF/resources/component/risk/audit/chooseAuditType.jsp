<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/finance/paramset/js/LodopFuncs.js"></script>
		<script type="text/javascript" src="${webPath}/component/risk/audit/js/chooseAuditType.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" >

		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。-->
					<!-- <div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="cwJiTiList.jiTiInsert();">新增</button>
					</div> -->
					<%-- <div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder="/>
					</div> --%>
				</div>
			</div>
			<div class="row clearfix">
				<!-- <div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div> -->
			<div id="chooseDaYinTypeDiv" class="table_content pzdivsty">

				<table id="chooseDaYinTypetable" width="100%" border="0" align="center"
					cellspacing="1" class="ls_list" title="选择稽核类型">
					<thead>
						<tr>
							<th scope="col"  align="center" name="type">稽核类型</th>
							<th scope="col"  align="center" name="opertion">操作</th>
						</tr>
					</thead>
					<tbody id="tab">
				<!-- 	//ondblclick="chooseFinBooks(this,'${webPath}/cwZtBooks/setFinBooksSessionAjax?bookCode=basic')"-->
					<%--<tr>
						<td align="center">套打</td>
						<td align="center"><a href="javascript:void(0);" onclick="getPrintData(this)" >选择</a></td>
					</tr>--%>
					 <tr>
						<td align="center">项目稽核</td>  <%--href="javascript:needlePrint();"--%>
						<td align="center"><a onclick="chooseAuditType.submitForm('${appId}','0');" target="_self">选择</a></td>
						</tr>
				<tr>
					<td align="center">合同稽核</td>
					<td align="center"><a onclick="chooseAuditType.submitForm('${appId}','1');" target="_self" >选择</a></td>
				</tr>

					</tbody>
				</table>
			</div>
		</div>
		</div>
	</body>
	<script type="text/javascript">

	</script>
</html>
