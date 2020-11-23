<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/CertiInfoList.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			var appId = '${appId}';
            var isCusDoc = "cusDoc";
            var docParm = 'relNo=' + appId + '&scNo=rece_trans';
			$(function(){
                CertiInfoList.init();
			});
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<%--<div class="row clearfix bg-white tabCont">--%>
			<%--<div class="col-md-12 column">--%>
				<%--<div class="search-div" id="search-div">--%>
					<%--<button type="button" id="certiAdd" class="btn btn-primary" onclick="CertiInfoList.insertCertiInfo();">新增</button>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div id ="scroll-content" class="row clearfix">
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>账款转让列表</span>
					<button class="btn btn-link formAdd-btn" onclick="CertiInfoList.insertCertiInfo();" title="新增"><i class="i i-jia3"></i></button>
					<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#accountList">
						<i class="i i-close-up"></i>
						<i class="i i-open-down"></i>
					</button>
				</div>
				<div id="accountList" class="content collapse in" aria-expanded="true">
					<dhcc:tableTag property="tablecertiReceTransBaseList" paginate="certiInfoList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
		</div>

		<div class="formRowCenter">
			<c:if test="${ifApproval=='1'}">
				<dhcc:thirdButton value="提交" action="提交" typeclass="certiSubmit" onclick="CertiInfoList.submitProcess();"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	</body>
</html>