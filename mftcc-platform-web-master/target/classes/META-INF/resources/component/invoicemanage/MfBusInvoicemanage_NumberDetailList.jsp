<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var invoiceId = '${invoiceId}';
			$(function(){
             /*   myCustomScrollbar({
                    obj : "#content", //页面内容绑定的id
                    url : webPath+"/mfBusInvoicemanage/getNumberDetailOut?invoiceId=" + invoiceId, //列表数据查询的url
                    tableId : "tableInvoiceManagerRepayPlan", //列表数据查询的table编号
                    tableType : "thirdTableTag", //table所需解析标签的种类
                    pageSize:30 //加载默认行数(不填为系统默认行数)
                });*/
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix">
				<div class="title" style="height: auto; text-align: center"><h5>还款历史详情</h5></div>
				<%--<div id="tableData" class="table_content padding_0">
					<dhcc:thirdTableTag paginate="tableData" property="tableInvoiceManagerRepayPlan" head="true" />
				</div>--%>
				<div id="evalIndexSubList" class="table_content">
					<dhcc:tableTag paginate="showList" property="tableinvoiceManageHistoryDetaill" head="true" />
				</div>
			</div>
                <div class="formRowCenter">
                    <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
                    <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click()"></dhcc:thirdButton>
                </div>
        </div>
		<script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanage_NumberDetailList.js"></script>
		<script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoiceOutmanage_Form.js"></script>

	</body>
</html>
