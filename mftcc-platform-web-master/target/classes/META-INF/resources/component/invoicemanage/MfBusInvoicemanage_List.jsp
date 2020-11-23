<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var fincId = '${fincId}';
			$(function(){
                MfBusInvoicemanage_List.init();

			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<%--<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2 text-center">
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">发票管理</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
					</div>
				</div>
			</div>--%>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
                <div class="formRowCenter">
                    <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
                    <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
                </div>
        </div>
		<script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanage_List.js"></script>
		<script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoiceOutmanage_Form.js"></script>

	</body>
</html>
