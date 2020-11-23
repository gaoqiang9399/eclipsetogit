<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ page import="cn.mftcc.util.DateUtil"%>
<!DOCTYPE html>
<html>
<head>
		<title>凭证详情</title>
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
		</style>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/voucher/js/voucher.js"></script>
	</head>
	<body class="overflowHidden">
	<div class="scroll-content">
		<div id="pzEdit"></div><!--原凭证-->
		
		<div class="wrapper"><!--冲销凭证-->
			<div class="voucher_wrap">
				<div class="voucher_top">
					<div class="row text-center">
					<span>&nbsp;</span>
					</div>
					<div class="mark_wrap">
						<span class="txt">凭证字</span><span class="txt">记-X</span>
						<span class="date_wrap"><span class="txt">日期</span><span class="txt" id="cxVchDate">${dataMap.vch_date}</span><span class="paddrl60_span"></span></span>
					</div>
					<div class="tit_wrap">
						<h1 class="voucher_tit">记账凭证</h1>
						<span class="vch_year">${dataMap.vch_year}</span>年第<span class="vch_period">${dataMap.vch_period}</span>期
					</div>
					<span class="attach_wrap"> 附单据 <span class="vch_attach" class="txt" id="cx_vch_attach"></span>
					 张
					</span>
				</div>
				<table class="voucher" id="CXvoucher">
					<thead>
						<tr>
							<th class="col_operate"></th>
							<th class="col_summary" colspan="2">摘要</th>
							<th class="col_subject" colspan="2">会计科目</th>
							<th class="col_quantity">数量</th>
							<th class="col_currency">币别</th>
							<th class="col_money"><strong class="tit">借方金额</strong>
								<div class="money_unit">
									<span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span>
									<span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span>
									<span class="last">分</span>
								</div></th>
							<th class="col_money col_credit"><strong class="tit">贷方金额</strong>
								<div class="money_unit">
									<span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span>
									<span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span>
									<span class="last">分</span>
								</div></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tfoot>
						<tr>
							<td class="col_operate"></td>
							<td colspan="4" class="col_total">
								合计：<span class="capAmount"></span>
							</td>
							<td class="col_debite">
								<div class="cell_val debit_total"></div>
							</td>
							<td class="col_credit">
								<div class="cell_val credit_total"></div>
							</td>
						</tr>
					</tfoot>
				</table>
				<div class="vch_ft">
					制单人：<span id="newVchPeople">${dataMap.vch_people}</span>
				</div>
				<div id="tag"></div>
				<!--状态标准-->
			</div>
		</div>
		<div>&nbsp;</div>
		<!-- <div class="mod-toolbar-bottom" id="toolBottom">
			<div align="center">
				<a class="ui-btn ui-btn-sp" onclick="chongXiaoThis()">冲销</a>
				<a class="ui-btn m0" id="vchAudit" onclick="closeDiolog()">取消</a>
			</div>
		</div> -->
		<div style="height:80px;">
		</div>
	  </div>
		<div class="formRowCenter">
	   			<dhcc:thirdButton value="冲销" action="冲销"  onclick="chongXiaoThis()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="closeDiolog()"></dhcc:thirdButton>
	   	</div>	
</body>
<script type="text/javascript">
		var voucherNo = '${dataMap.voucherNo}';
		$(function(){
			//自定义滚动条
			$(".page-voucher").mCustomScrollbar({
				advanced:{
					theme:"minimal-dark",
					updateOnContentResize:true
				}
			});
			
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					theme : "minimal-dark",
// 					updateOnContentResize : true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			$('#pzEdit').voucher({
				id:'vchEdit',
				pzno:voucherNo,
				for_which: 'view'
			});
			$("#cx_vch_attach").text(0);
			jQuery.ajax({
				url:webPath+"/cwVoucherMst/getVoucherByNoAjax",
				data:{"voucherNo":voucherNo, "which":"view"},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						var vchMst = data.formData.mst;
						$('.cx_vch_attach').text(vchMst.counts);
						var vchDcl = data.formData.dcl;//list
						showCXVchDetail(vchDcl,"CXvoucher",vchMst);
					}else if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		})
		// 将数据库的金额转换为正确格式
		function formatAmt(editMoney){
			var resAmt="";
			if(editMoney.indexOf(".") !=-1) {
				if(editMoney.charAt(0)=="."){
					resAmt=editMoney.substring(1,3);
				}else{
					var intMoney = editMoney.split(".")[0];
					var subMoney = editMoney.split(".")[1].substring(0, 2);
					resAmt=intMoney + subMoney;
				}
			} else {
				resAmt=editMoney + "00";
			}
			return resAmt;
		}
		/**
		 * 生成冲销凭证的显示
		 */
		function showCXVchDetail(vchDcl,id,vchMst){
		//	alert(+"-"+vchMst.voucherNoteNo);
			var jid="#"+id;
			var len=vchDcl.length;
			
			if(len < 4){
				len = 4;
			}
			
			var drCount=0;
			var crCount=0;
			for(var i=0;i<len;i++){
				var td1="";
				var td2="";
				var td3="";
				var td4="";
				if(i<vchDcl.length){
					td1="冲销 "+vchMst.pzPrefix+" 字 第"+vchMst.voucherNoteNo+"号错误凭证";
					td2=vchDcl[i].accHrt+vchDcl[i].itemsValueName;
					if(vchDcl[i].dcInd==1){
						drCount=drCount+parseFloat(vchDcl[i].txAmt);
						td3=formatAmt(vchDcl[i].txAmt);
					}else{
						crCount=crCount+parseFloat(vchDcl[i].txAmt);
						td4=formatAmt(vchDcl[i].txAmt);
					}
				}
				var htmltr='<tr class="entry_item"><td class="col_operate"></td>';
				htmltr+='<td class="col_summary"><div class="cell_val summary_val">'+td1+'</div></td>';
				htmltr+='<td class="col_option"></td>';
				htmltr+='<td class="col_subject"><div class="cell_val subject_val">'+td2+'</div></td>';
				htmltr+='<td class="col_option"></td>';
				if(parseFloat(td3)>0){
					htmltr+='<td class="col_debite"><div class="cell_val debit_val"><span style="color:red">'+td3+'</span></div></td>';
				}else{
					htmltr+='<td class="col_debite"><div class="cell_val debit_val">'+td3.substring(1, td3.length)+'</div></td>';
				}
				if(parseFloat(td4)>0){
					htmltr+='<td class="col_credit"><div class="cell_val credit_val"><span style="color:red">'+td4+'</span></div></td></tr>';
				}else{
					htmltr+='<td class="col_credit"><div class="cell_val credit_val">'+td4.substring(1, td4.length)+'</div></td></tr>';
				}
				$(jid).find("tbody").append(htmltr);
			}
			
			//如果借方金额和贷方金额相同，显示合计大写金额
			if(drCount==crCount){
				$(jid).find('.capAmount').html(chineseNumber(drCount*(-1)));
			}else{
				$(jid).find('.capAmount').html("");
			}	
			drCount=formatAmt(drCount+"");
			crCount=formatAmt(crCount+"");
			if(parseFloat(drCount)>0){
				$(jid).find('.debit_total').html('<span style="color:red">'+drCount+'</span>');
			}else{
				$(jid).find('.debit_total').html(drCount.substring(1, drCount.length));
			}
			if(parseFloat(crCount)>0){
				$(jid).find('.credit_total').html('<span style="color:red">'+crCount+'</span>');
			}else{
				$(jid).find('.credit_total').html(crCount.substring(1, crCount.length));
			}
		}
		//冲销凭证
		function chongXiaoThis(){
			//确定冲销此凭证？"
			alert(top.getMessage("CONFIRM_OPERATION", "冲销此凭证"),2,function(){
				LoadingAnimate.start();
				$.ajax({
					url:webPath+"/cwVoucherMst/chongXiaoPzAjax",
					data:"voucherNo="+voucherNo,
					dataType:'json',
					type:'post',
					success:function(jsonData){
						LoadingAnimate.stop();
						if(jsonData.flag=="error"){
							alert(jsonData.msg,4);
						}else{
							//jsonData.msg+"要返回凭证列表么？"
							alert(top.getMessage("CONFIRM_CW_ACCOUNT_WRITEOFF", ''),2,function(){
								closeDiolog();
							});
						}
					}
				});
			});
		}
		//关闭弹出层
		function closeDiolog(){
			myclose_click();
		}
</script>
</html>