<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
        <script type="text/javascript" src='${webPath}/component/invoicemanage/js/MfBusInvoicemanage_Coment.js?v=${cssJsVersion}'></script>
        <script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer_InputUpdate.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src='${webPath}/component/cus/identitycheck/js/IdentityCheck.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js?v=${cssJsVersion}"></script>
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
        MfBusInvoicemanage_Coment.init();

        $(function () {
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    theme : "minimal-dark",
                    updateOnContentResize : true
                }
            });
        });
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfBusInvoicemanage/insertInvoiceAjax">
							<dhcc:bootstarpTag property="formMfBusInvoiceCheckout" mode="query"/>
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

	   		<input type="hidden" id="type" value="1"></input>
			<div class="formRowCenter">
<%--<dhcc:thirdButton value="保存" action="保存" typeclass="save" onclick="MfBusInvoicemanage_Form.ajaxSave('#cusInsertForm')"></dhcc:thirdButton>--%>
	<%--<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfBusInvoicemanage_Coment.cancelInsert()"></dhcc:thirdButton>--%>

	   		</div>
   		</div>
	</body>
</html>
