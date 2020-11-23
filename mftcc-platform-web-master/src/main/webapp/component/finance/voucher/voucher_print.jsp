<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<%
	// ↓ 使用规范命名的变量。
	String contextPath = request.getContextPath();
	%>
	<head>
		<title>凭证录入</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/vchprint.css" />
	</head>
<body id="vchPrintBody">
	<c:set var="vSize" value="dataMap.list.size"></c:set>
	<c:forEach items="${dataMap.list}" var="voucher" varStatus="st">
		<c:if test="${st.Odd}">
			<div id="page${st.count/2+1}">
		</c:if>
		<div class="voucher_wrap">
			<table class="voucher_top">
				<tr>
					<td class="company_wrap"></td>
					<td class="date_wrap"><span class="voucher_tit">记账凭证</span></td>
					<td class="attach_wrap">附件数：<span id="vch_attach">${voucher.vchAttach}</span></td>
				</tr>
				<tr>
					<td class="company_wrap"><span id="company">${voucher.company}</span></td>
					<td class="date_wrap">日期：<span id="vch_date">${voucher.vchDate}</span></td>
					<td class="proof_wrap">凭证字：<span id="pzPrefix">${voucher.pzPrefix}</span>（<span id="pageNum">${voucher.pageNum}</span>）
					</td>
				</tr>
			</table>
			<table class="voucher" id="voucher">
				<thead>
					<tr>
						<th class="col_operate"></th>
						<th class="col_summary" colspan="2">摘要</th>
						<th class="col_subject" colspan="2">会计科目</th>
						<th class="col_money">借方金额</th>
						<th class="col_money col_credit">贷方金额</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="dSize" value="#voucher.detials.size"></c:set>
					<c:forEach var="detail" items="${ voucher.detials}">
						<tr class="entry_item">
							<td class="col_operate"></td>
							<td class="col_summary" data-edit="summary"><div class="cell_val summary_val">
									${detail.summary}
								</div></td>
							<td class="col_option"></td>
							<td class="col_subject" data-edit="subject">
								<div class="cell_val subject_val">
									${detail.subject}
								</div>
							</td>
							<td class="col_option"></td>
							<td class="col_debite" data-edit="money"><div class="cell_val debit_val">
									${detail.debit}
								</div></td>
							<td class="col_credit" data-edit="money"><div class="cell_val credit_val">
									${detail.credit}
								</div></td>
						</tr>
					</c:forEach>
					<c:forEach begin="#dSize" end="4">
						<tr class="entry_item">
							<td class="col_operate"></td>
							<td class="col_summary" data-edit="summary"><div class="cell_val summary_val"></div></td>
							<td class="col_option"></td>
							<td class="col_subject" data-edit="subject">
								<div class="cell_val subject_val"></div>
							</td>
							<td class="col_option"></td>
							<td class="col_debite" data-edit="money"><div class="cell_val debit_val"></td>
							<td class="col_credit" data-edit="money"><div class="cell_val credit_val"></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td class="col_operate"></td>
						<td colspan="4" class="col_total">合计：<span id="capAmount">${voucher.capAmount}</span></td>
						<td class="col_debite"><div class="cell_val debit_total" id="debit_total">
								${voucher.debitTotal}
							</div></td>
						<td class="col_credit"><div class="cell_val credit_total" id="credit_total">
								${voucher.creditTotal}
							</div></td>
					</tr>
				</tfoot>
			</table>
			<div class="vch_ft">
				<span class="fr" id="modifyTime">最后修改时间：<span>${voucher.modifyTime}</span></span> <span class="fr" id="createTime">录入时间：<span>${voucher.createTime}</span></span> <span class="fr" id="ldr_people">审核人：<span>${voucher.ldrPeople}</span></span> 制单人：<span id="vch_people">${voucher.vchPeople}</span>
			</div>
		</div>
		<c:if test="${st.Even}">
			</div>
		</c:if>
	</c:forEach>
</body>
</html>