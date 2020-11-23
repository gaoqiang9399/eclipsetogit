<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html  style="height:100% !important;">
<head>
<title>详情</title>
<script type="text/javascript">
    var invoiceId='${invoiceId}';
    var webPath = "${webPath}";
    var fieldType="${fieldType}";
    var aloneFlag = true;
    var dataDocParm="";
    dataDocParm={
        relNo:invoiceId,
        docType:"invoiceDoc",
        docTypeName:"发票资料",
        docSplitName:"发票资料",
        query:''
    };
</script>
</head>
<body class="overflowHidden bg-white">   
		<div class="container form-container">
		<%--	<div id="infoDiv" style="display:block;height:100%;">
				<iframe src="${webPath}/mfBusInvoicemanage/getById?invoiceId=${invoiceId}&ifEdit=${ifEdit}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</div>--%>
			<%--<div id="approvalDiv" class="scroll-content" style="display:none;">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfBusInvoiceForm" theme="simple" name="operform" action="${webPath}/mfBusInvoicemanage/submitUpdateAjax">
							<dhcc:bootstarpTag property="formMfBusInvoicemanage" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>--%>
			<div class="scroll-content">
				<div class="col-xs-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfBusInvoicemanage/submitUpdateAjax">
							<dhcc:bootstarpTag property="mfBusinvoicecheck" mode="query"/>
						</form>
					</div>
					<div id="goodsDetailListdiv" class="bigform_content col_content">
						<div class="title"><h5>开票列表</h5></div>
						<div id="evalIndexSubList" class="table_content padding_0">
							<dhcc:tableTag paginate="showList" property="tableinvoiceManageHistoryDetaill" head="true" />
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
			<div id="approvalBtn" class="formRowCenter " style="display:block;">
				<dhcc:thirdButton value="审批" action="审批" onclick="WkfInvoiceViewPoint.doSubmitAjax('#cusInsertForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
		<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript" src="${webPath}/component/invoicemanage/js/WkfInvoiceViewPoint.js"></script>
<script type="text/javascript">
		$(function(){
            WkfInvoiceViewPoint.init();
		});	
</script>
</html>