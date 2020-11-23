<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>选择发票</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/commondy.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/fpdy2.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/LodopFuncs.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/invoice/js/print.js"></script>
<script type="text/javascript">

	function preview(invoiceId){
		$.ajax({
			type : "POST",
			url : "${webPath}/cwInvoice/getById/"+invoiceId,
			dataType : "json",
			success : function(data) {
				printZzP_sz(data.data,"ture",-4,-19.5);
			}
		});
	};

	function print(invoiceId){
		$.ajax({
			type : "POST",
			url : "${webPath}/cwInvoice/getById/"+invoiceId,
			dataType : "json",
			success : function(data) {
				printZzP_sz(data.data,"false",-4,-19.5);
			}
		});
	};

</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-title"></div>
				<form method="post" id="cwBillForm" theme="simple" >
					<table class="table" style="" id="tablist" width="100%" border="0" align="center" cellspacing="1" class="ls_list" >
						<thead class="tbody" style="">
						<tr class="tr">
							<th class="th" scope="col" width="20%" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap;padding: 0px 5px;cursor: pointer;">发票类型</th>
							<th class="th" scope="col" width="20%" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap;padding: 0px 5px;cursor: pointer;">发票号码</th>
							<th class="th" scope="col" width="20%" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap;padding: 0px 5px;cursor: pointer;">合计金额</th>
							<th class="th" scope="col" width="20%" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap;padding: 0px 5px;cursor: pointer;">合计税额</th>
							<th class="th" scope="col" colspan="1" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap;padding: 0px 5px;cursor: pointer;"> 操作 </th>
						</tr>
						</thead>
						<tbody class="tbody" id="tab">
						<c:forEach var="invoice" items="${invoicelist}" >
							<tr class="tr">
								<td class="td" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap; padding: 5px;" >
									<c:if test="${invoice.fplxdm == '004'}">专票</c:if>
									<c:if test="${invoice.fplxdm == '007'}">普票</c:if>
									<%--<c:if test="${invoice.fplxdm == '007'}">普票</c:if>
									<c:if test="${invoice.fplxdm == '025'}">卷票</c:if>
									<c:if test="${invoice.fplxdm == '026'}">电票</c:if>
									<c:if test="${invoice.fplxdm == '005'}">机动车票</c:if>
									<c:if test="${invoice.fplxdm == '006'}">二手车票</c:if>--%>
								</td>
								<td class="td" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap; padding: 5px;" >${invoice.fphm}</td>
								<td class="td" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap; padding: 5px;" >${invoice.hjje}</td>
								<td class="td" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap; padding: 5px;" >${invoice.hjse}</td>
								<td class="td" style="height: 47px;line-height: 47px;text-align: center;white-space: nowrap; padding: 5px;" align="center">
									<a href="javascript:void(0);" onclick="preview('${invoice.id}');return false;" class="abatch">预览</a>
									&#12288;
									<a href="javascript:void(0);" onclick="print('${invoice.id}');return false;" class="abatch">打印</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>
