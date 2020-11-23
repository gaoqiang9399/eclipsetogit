<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>小额贷款股份有限公司还款凭证</title>
<style type="text/css">
.title {
	width: 600px;
	margin: 0 auto;
}

.title h3 {
	text-align: center;
	font-size: 28px;
	letter-spacing: 0.1em;
}

.title span {
	display: block;
	text-align: center;
	font-size: 20px;
}

table tr {
	height: 32px;
	width: 10px;
}

table caption {
	text-align: left;
	color: #000;
}

table tr td {
	border: 1px solid #000;
	padding: 2px 4px;
	line-height: 2em;
}

td {
	word-break: break-all;
}

.lender {
	height: 80px;
}

.unit {
	align : center;	
}.date{
	margin-bottom: 15px;
 }
</style>
<script type="text/javascript">
	$(function() {
		$("body").removeClass('modal-open');
	});
</script>
</head>
<body>
	<c:forEach items="${listData}" var="obj" varStatus="status">
		<c:if test="${obj.mfRepayRecheck != null}">
			<div class="title">
				<h3>小额贷款股份有限公司还款凭证</h3>
				<span class="date">收款日期：${fn:substring(obj.mfRepayRecheck.regDate, 0, 4)}&nbsp;-
				${fn:substring(obj.mfRepayRecheck.regDate, 4, 6)}&nbsp;-
				${fn:substring(obj.mfRepayRecheck.regDate, 6, -1)} </span>
			</div>
			<table width="800" height="350" border="1" align="center">
				<tr>
					<td>还款人名称</td>
					<td colspan="17">${obj.mfRepayRecheck.cusName}</td>
				</tr>
				<tr>
					<td>合同编号</td>
					<td colspan="17">${obj.mfRepayRecheck.pactNo}</td>
				</tr>
				<tr>
					<td rowspan="2">本息合计（币种）</td>
					<td colspan="6" rowspan="2">人民币${obj.mfRepayRecheck.amtCapital}</td>
					<td class="unit"><span>亿</span></td>
					<td class="unit">千</td>
					<td class="unit">百</td>
					<td class="unit">十</td>
					<td class="unit">万</td>
					<td class="unit">千</td>
					<td class="unit">百</td>
					<td class="unit">十</td>
					<td class="unit">元</td>
					<td class="unit">角</td>
					<td class="unit">分</td>
				</tr>
				<tr>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,0,1) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,1,2) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,2,3) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,3,4) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,4,5) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,5,6) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,6,7) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,7,8) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,8,9) }</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,9,10)}</td>
					<td class="unit">${fn:substring(obj.mfRepayRecheck.amt,10,11)}</td>
				</tr>
				<tr>
					<td colspan="18">收回 ${fn:substring(obj.mfRepayRecheck.putoutDate,0,4) } 年 
										${fn:substring(obj.mfRepayRecheck.putoutDate,4,6) } 月 
										${fn:substring(obj.mfRepayRecheck.putoutDate,6,-1) } 日发放，
										${fn:substring(obj.mfRepayRecheck.intstEndDate,0,4) } 年 
										${fn:substring(obj.mfRepayRecheck.intstEndDate,4,6) }&nbsp;月
										${fn:substring(obj.mfRepayRecheck.intstEndDate,6,-1) } 日到期的贷款（利率
										${obj.mfRepayRecheck.fincRate } ） 尚欠本金：
						 				${obj.mfRepayRecheck.loanBal }</td>
				</tr>
				<tr>
					<td>本金：</td>
					<td colspan="4">￥ ${obj.mfRepayRecheck.prcpSum }</td>
					<td colspan="2">利息：</td>
					<td colspan="11">￥ ${obj.mfRepayRecheck.normIntstSum }</td>
				</tr>
				<tr class="lender" align="center">
					<td colspan="18">贷款人(财务电子章）</td>
				</tr>
			</table>
		</c:if>
		<div style="page-break-after: always;"></div>
	</c:forEach>
</body>
</html>