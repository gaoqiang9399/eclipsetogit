<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceTransfer_List.js?v=${cssJsVersion}"></script>
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	<script type="text/javascript" >
        var appId = '${appId}';
        var pactId = '${pactId}';
        var transMainId = '${transMainId}';
        var isCusDoc = "cusDoc";
        var query = '${query}';
        var docParm = 'relNo=' + appId + '&scNo=rece_trans'+'&query='+query;
        $(function(){
            MfBusReceTransfer_List.init();
        });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>账款转让列表</span>
					<c:if test="${transSts=='0'}">
						<button class="btn btn-link formAdd-btn" onclick="MfBusReceTransfer_List.insertReceTransInfo();" title="新增"><i class="i i-jia3"></i></button>
					</c:if>
					<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#accountList">
						<i class="i i-close-up"></i>
						<i class="i i-open-down"></i>
					</button>
				</div>
				<div id="accountList" class="content collapse in" aria-expanded="true">
					<dhcc:tableTag property="tablereceTransBaseList" paginate="mfBusReceTransferList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
			<%@ include file="/component/pact/receaccount/pub_rece_trans_approve_his.jsp"%>
		</div>
	</div>

	<div class="formRowCenter">
		<c:if test="${transSts=='0'}">
			<dhcc:thirdButton value="保存" action="保存"  onclick="MfBusReceTransfer_List.updateReceTransMainAjax();"></dhcc:thirdButton>
		</c:if>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>