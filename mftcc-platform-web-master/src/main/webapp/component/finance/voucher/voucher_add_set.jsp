<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>凭证录入</title>
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/voucher.css" />
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/icon.css" />
<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/iconColor.css" />
<style type="text/css">
			* {
				-webkit-box-sizing: content-box;
				-moz-box-sizing: content-box;
				box-sizing: content-box;
			}
			.wrapper { 
				margin: 20px auto;
			}
			.flie9{
				float:left;
			}
			.page-voucher{
				background-color: #ffffff;
			}
</style>
<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<script type="text/javascript" src="${webPath}/component/finance/voucher/js/voucher.js"></script>
<script type="text/javascript">
	$(function(){
		comData = eval('${dataMap.comData}');
		remarkData = eval('${dataMap.remarkData}');
		//初始化科目分类信息
		initSubInfo();
		//初始化凭证相关事件
		addVchEvent();
		//初始金额汇总事件
		addAmtEvend();
		//获取辅助核算数据
		getAssistData();
		$('.edit_money').blur();
		
		//自定义滚动条
		$(".page-voucher").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
	})
</script>
<script type="text/javascript">
		var aloneFlag = true;
		var dataDocParm = {
			relNo : '${dataMap.voucherNo}',
			docType : "voucher",
			docTypeName : "附件",
			docSplitName : "",
			query : "",
		};
			
	</script>
</head>
<body class="page-voucher">
	<div class="wrapper clearfix">
		<div class="mod-toolbar-top" id="toolTop">
			<!-- lzs由于IE9不兼容样式，所以加了style 2017年6月14日 -->
			<div class="left " style="float:left;">
				<a class="ui-btn ui-btn-sp" id="save">保存</a>
			</div>
			<div class="ui-btn-menu fl" >
				<a id="more" class="ui-btn menu-btn">更多<b></b></a>
				<div class="con more-operate-con">
					<ul class="more-operate">
						<li><a href="javascript:void(0);" id="saveAsTemp">保存为凭证模板</a></li>
						<li><a href="javascript:void(0);" id="createWithTemp">从模板生成凭证</a></li>
					</ul>
				</div>
			</div>
			<div class="right" style="display: none;">
				<div id="keyboardshow" class="fl">
					<a href="javascript:void(0);" id="keyboard" title="如何快捷录入"></a> <img src="${webPath}/component/finance/voucher/images/vchKeyBoard.png" id="imgKeyBoard">
				</div>
				<a class="ui-btn-prev ui-btn-prev-dis" id="prev" title="上一张"><b></b></a> <a class="ui-btn-next ui-btn-next-dis" id="next" title="下一张"><b></b></a>
			</div>
		</div>
		<div class="voucher_wrap">
			<div class="voucher_top">
				<div class="mark_wrap">
					<span class="txt">凭证字</span>
					<c:set var="proofNo" value="dataMap.pzProofNo"></c:set>
					<select class="ui-combo-wrap" id="pzProofNo" name="pzProofNo" style="width: 58px;">
						<c:forEach items="${dataMap.proofs}" var="proof">
							<option 
								<c:if test='${proofNo!="" && proofNo!=null}'>
									<c:if test="${proof.pzProofNo==dataMap.pzProofNo}" >selected</c:if>
								</c:if>
								<c:if test='${proof.isAuto=="Y"}'>selected</c:if>
								value="${proof.pzProofNo}" >${proof.pzPrefix}</option>
						</c:forEach>
					</select>
					<input type="hidden" id="voucherNo" name="voucherNo">
					<input type="hidden" id="which" name="which" value="${dataMap.which }">
					<input type="hidden" id="businessNo" name="businessNo" value="${dataMap.businessNo }">
					<span class="ui-spinbox-wrap">
						<input type="text" class="ui-spinbox input-txt" value="${dataMap.vch_num }" id="vch_num" autocomplete="off">
						<span class="btn-wrap"><a class="btn-up"></a><a class="btn-down"></a></span>
					</span><span class="txt">号</span>
					<span class="date_wrap">
						<span class="txt">日期</span>
						<input type="text" class="ui-input ui-datepicker-input" id="vch_date" name="vch_date" readonly="" value="${dataMap.vch_date }">
					</span>
				</div>
				<div class="tit_wrap">
					<h1 class="voucher_tit">记账凭证</h1>
					<span id="vch_year">${dataMap.vch_year }</span>年第<span id="vch_period">${dataMap.vch_period }</span>期
				</div>
				<span class="attach_wrap"> 附单据 <input type="text" class="ui-input" id="vch_attach" value="0"> 张
				</span>
			</div>
			<table class="voucher" id="voucher">
				<thead>
					<tr>
						<th class="col_operate"></th>
						<th class="col_summary" colspan="2">摘要</th>
						<th class="col_subject" colspan="2">会计科目</th>
						<th class="col_money"><strong class="tit">借方金额</strong>
							<div class="money_unit">
								<span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span> <span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span> <span class="last">分</span>
							</div></th>
						<th class="col_money col_credit"><strong class="tit">贷方金额</strong>
							<div class="money_unit">
								<span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span> <span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span> <span class="last">分</span>
							</div></th>
					</tr>
				</thead>
				<tbody>
					<c:set var="dSize" value="${fn:length(dataMap.dcl) }"></c:set>
					
					<c:forEach items="${ dataMap.dcl}" var="detail">
						<tr class="entry_item">
							<td class="col_operate">
								<div class="operate">
									<a title="增加分录" class="add"></a> <a title="删除分录" class="del"></a>
								</div>
							</td>
							<td class="col_summary" data-edit="summary">
								<div class="cell_val summary_val" style="display: block;">${detail.txDesc }</div>
								<input type="text" class="edit_summary" autocomplete="off" value="${detail.txDesc}" style="display: none;">
								<input type="hidden" class="edit_vchBody" autocomplete="off" value="${detail.vchBody}" style="display: none;">
							</td>
							<td class="col_option">
								<div class="option">
									<a class="selSummary">摘要</a>
								</div>
							</td>
							<td class="col_subject" data-edit="subject">
								<div class="subject-dtl">
									<div class="cell_val subject_val">${detail.accHrt}<c:if test='${detail.itemsValueName!=null && detail.itemsValueName!=""}'><span>${ detail.itemsValueName}</span></c:if></div>
									<input type="text" class="edit_subject" autocomplete="off" value="${detail.accHrt}" style="display: none;">
								</div>
								<input type="hidden" class="edit_items" autocomplete="off" value='${detail.itemsValueNo}'>
							</td>
							<td class="col_option">
								<div class="option">
									<a class="selSub">科目</a>
								</div>
							</td>
							<td class="col_debite" data-edit="money">
								<div class="cell_val debit_val">${detail.debite }</div>
								<input type="text" class="edit_money" autocomplete="off" value="${detail.debite}" style="display: none;">
							</td>
							<td class="col_credit" data-edit="money">
								<div class="cell_val credit_val">${detail.credit }</div>
								<input type="text" class="edit_money" autocomplete="off" value="${detail.credit}" style="display: none;">
							</td>
						</tr>
					</c:forEach>
					<c:if test="${dSize < 4}">
					</c:if>
					<c:if test="${dSize < 4}">
						<c:forEach begin="${dSize }" end="3">
							<tr class="entry_item">
								<td class="col_operate">
									<div class="operate">
										<a title="增加分录" class="add"></a> <a title="删除分录" class="del"></a>
									</div>
								</td>
								<td class="col_summary" data-edit="summary">
									<div class="cell_val summary_val" style="display: block;"></div>
								</td>
								<td class="col_option">
									<div class="option">
										<a class="selSummary">摘要</a>
									</div>
								</td>
								<td class="col_subject" data-edit="subject">
									<div class="subject-dtl">
										<div class="cell_val subject_val"></div>
									</div>
								</td>
								<td class="col_option">
									<div class="option">
										<a class="selSub">科目</a>
									</div>
								</td>
								<td class="col_debite" data-edit="money">
									<div class="cell_val debit_val"></div>
								</td>
								<td class="col_credit" data-edit="money">
									<div class="cell_val credit_val"></div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				<tfoot>
					<tr>
						<td class="col_operate"></td>
						<td colspan="4" class="col_total">合计：<span id="capAmount"></span>
						</td>
						<td class="col_debite">
							<div class="cell_val debit_total" id="debit_total"></div>
						</td>
						<td class="col_credit">
							<div class="cell_val credit_total" id="credit_total"></div>
						</td>
					</tr>
				</tfoot>
			</table>
			<div class="vch_ft">
				 制单人：<span id="vch_people">${dataMap.vch_people }</span>
			</div>
			<div id="auditTag"></div>
			<!--状态标准-->
		</div>
		<div class="mod-toolbar-bottom" id="toolBottom">
			<div class="fr" style="float:right; border-bottom: 50px"   >
				<a class="ui-btn ui-btn-sp m0" id="saveB">保存</a>
			</div>
		</div>
		<div class="zhengbao-pic"></div>
	</div>
	<div id="isItem" style="top: 255px; left: 251px;display: none;"><ul></ul></div>
	<div id="COMBO_WRAP">
		<div class="ui-droplist-wrap ui-summary-wrap" style="position: absolute; top: 0px; z-index: 1000; width: 278px; display: none;">
			<div class="droplist" style="position: relative; overflow: auto; height: 182px;" id="selSum"></div>
			<div class="extra-list-ctn">
				<a href="javascript:void(0);" id="quickAddSummary" class="quick-add-link"> <i class="ui-icon-add"></i>新增摘要
				</a>
			</div>
		</div>
		<div class="ui-droplist-wrap ui-subject-wrap" style="position: absolute; top: 151px; z-index: 1000; width: 323px; display: none; left: 348px;">
			<div class="droplist" style="position: relative; overflow: auto; height: 182px;" id="selSubject"></div>
			<div class="extra-list-ctn">
				<a href="javascript:void(0);" id="quickAddSubject" class="quick-add-link"><i class="ui-icon-add"></i>新增科目</a>
			</div>
		</div>
	</div>
	<div class="pika-single is-hidden is-bound"></div>
	<!-- <div class="col-xs-12 column"style="width: 1140px; top: -30px; padding-left: 79px"> -->
	<div class="col-xs-12 column" style="width: 1140px; top: -20px;margin:auto;float: none;">
				<div id="doc_div"></div>
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	</div>
</body>
<script type="text/javascript">
	var voucherNos = '${dataMap.voucherNo}';
	$(function(){
		$("#voucherNo").val(voucherNos);
	});
</script>
</html>