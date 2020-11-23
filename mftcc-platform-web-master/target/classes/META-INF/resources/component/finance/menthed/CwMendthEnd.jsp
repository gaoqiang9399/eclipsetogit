<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>月末结账</title>
		<link rel="stylesheet" href='${webPath}/component/finance/menthed/css/CwMonthEnd.css'/>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/closePopUpBox.js"></script>
		<style type="text/css">
			.lrinp-w{
				width:100px;
			    display:  inline-block !important; 
			}
			 .accnoKm{
				width:200px;
				display:  inline-block !important; 
			} 
			.bottcls,.kmst-in{
				padding-bottom: 5px;
			}
		</style>
		<script type="text/javascript">
			var proofs = '${dataMap.proofs}';
			var currDate = '${dataMap.currDate }';
			var doNotClose = '${dataMap.doNotClose}';
			var yearEndFlag = '${dataMap.yearEndFlag}';//yearEndFlag年结，0不是第12期，1是12期
			var fptqFlag=1;
			$(function(){
				var errorMsg = '${dataMap.errorMsg}';
				if(errorMsg){
					 alert(errorMsg,0);
				}
				showNoMeedVch('${dataMap.isExistBroken}', "${dataMap.vchNotJZ}", "${dataMap.isMonth}","${dataMap.isBalance}");
				$('#moreTrigger').bind('click', function(){
					$(this).hasClass('show') ? 
					$("#month_more").stop().slideUp(200,function(){
						$('#moreTrigger').removeClass('show').html('更多设置<i></i>');
						$("#month_more").css("display","none");
					}) : $("#month_more").stop().slideDown(200,function(){
						$('#moreTrigger').addClass('show').html('收起更多<i></i>');
						$("#month_more").css("display","inline");
					});
				})
				$('#moreTriggerYear').bind('click', function(){
					$(this).hasClass('show') ? 
					$("#year_more").stop().slideUp(200,function(){
						$('#moreTriggerYear').removeClass('show').html('更多设置<i></i>');
						$("#year_more").css("display","none");
					}) : $("#year_more").stop().slideDown(200,function(){
						$('#moreTriggerYear').addClass('show').html('收起更多<i></i>');
						$("#year_more").css("display","inline");
					});
				})
				$('#btn_reclose').bind('click', function(){
					reCloseToPrevMonth();
				})
// 				$('#zjTxt').removeClass();
				//科目弹窗
				$('.comitem_select').on('click', function(){
					var forId = $(this).attr('for');
					openComItemDialog('0', function(data){
						if(data){
							//$('#'+forId).val(data.id);
							$('#'+forId).val(data.id+"/"+data.name);
							
						}
					});
				})
				//yearEndFlag年结，0不是第12期，1是12期 lzs
				if(yearEndFlag==1){
					$("#yearEndpz").removeClass("hidden");
					$("#yearEndkm").removeClass("hidden");
				}
				
				//$("#yearEndpz").removeClass("hidden");
				// $("#yearEndkm").removeClass("hidden");
				//$("#createlrpz").removeClass("hidden");
				
				
				//初始化凭证字查询条件
				if(proofs){
					var vehmarks = eval(proofs);
					$('.vch-select').each(function(){
						this.options.length=0;
						for(var i=0; i<vehmarks.length; i++){
							if('Y'==vehmarks[i].isAuto)
								this.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo, true, true));
							else
								this.add(new Option(vehmarks[i].pzPrefix, vehmarks[i].pzProofNo));
						}
						
					});
				}
				
				$('.vch-date').on('click', function(){
					fPopUpCalendarDlg({
						isclear: false,
						min: currDate.substring(0, 8) + '01 00:00:00', //最小日期
						max: currDate + ' 23:59:59', //最大日期
						choose:function(data){
						}	
					});
				})
				
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			})
			
			//生成月结凭证
			function createMonthVch(){
				if(doNotClose=='0'){
					/**验证字段是否为空*/
					/**封装数据*/
					var monthForm = $('#monthForm');
					var dataParam = JSON.stringify(monthForm.serializeArray()); 
					LoadingAnimate.start();
					jQuery.ajax({
						url:webPath+"/cwMenthEnd/createMonthVchAjax",
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								alert(top.getMessage("SUCCEED_OPERATION"),1);
								var vch = $.parseJSON(data.voucher);
								if(vch.zjpz != ''){
									$('#zjpz').html(vch.zjpz);
									$('#zjTxt').removeClass();
								}
								if(vch.txpz != ''){
									$('#txpz').html(vch.txpz);
									$('#txTxt').removeClass();
								}
								if(vch.jzpz != ''){
									$('#jzpz').html(vch.jzpz);
									$('#jzTxt').removeClass();
								}
								if(vch.sypz != ''){
									$('#sypz').html(vch.sypz);
									$('#syTxt').removeClass();
								}
								
								if(vch.yearpz != ''){
									$('#yearpz').html(vch.yearpz);
									$('#yearTxt').removeClass();
									$("#createlrpz").removeClass("hidden");
								} 
								//yearEndFlag年结，0不是第12期，1是12期 lzs
								if(yearEndFlag==1){
									//$("#yearEndpz").removeClass("hidden");
									//$("#yearEndkm").removeClass("hidden");
									$('#btn_vch').hide();
									$('#btn_yearEnd').removeClass("hidden")
									
								}else{
									$('#btn_vch').hide();
									$('#btn_close').removeAttr('disabled').removeClass('btn-default').addClass('btn-primary')
									.bind('click', function(){
										closeToNextMonth();
									})
								}
								
							}else if(data.flag=="error"){
								alert(data.msg,0);
								/* if(alertFlag){
									 alert(data.msg,0);
								}else{
									alert(data.msg,0);
								} */
							}
							LoadingAnimate.stop();
						},error:function(data){
							if(alertFlag){
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}else{
								alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
							LoadingAnimate.stop();
						}
					});
				}
				
			}
			//反结账到上一期
			var kps = '0';
			function reCloseToPrevMonth(){
				if('1'==kps){
					//alert('程序正在处理，请不要重复提交。',1);
					alert(top.getMessage("WAIT_OPERATION", "处理"))
					return false;
				}else{
					kps = '1';
					LoadingAnimate.start();
					jQuery.ajax({
						url:webPath+"/cwMenthEnd/reCloseToPrevMonthAjax",
						data:{ajaxData:''},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								alert(top.getMessage("SUCCEED_OPERATION"),1);
								location.reload();
							}else if(data.flag=="error"){
								if(alertFlag){
									 alert(data.msg,0);
								}else{
									alert(data.msg,0);
								}
							}
							kps = '0';
							LoadingAnimate.stop();
						},error:function(data){
							if(alertFlag){
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}else{
								alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
							kps = '0';
							LoadingAnimate.stop();
						}
					});
				}
			}
			
			//结账到下期
			var kps = '0';
			function closeToNextMonth(){
				//12期时，生成凭证
				if(yearEndFlag==1){
					var fptqflag =$("#fptqpz").is(":checked");//选中为true，否则为false
					if(fptqflag){
						var fdyygjAmt = $("#fdyygjAmt").val();
						var ybfxzbAmt = $("#ybfxzbAmt").val();
						var ryyygjAmt = $("#ryyygjAmt").val();
						var yfglAmt = $("#yfglAmt").val();
						if((!fdyygjAmt||fdyygjAmt==0)&&(!ybfxzbAmt||ybfxzbAmt==0)&&(!ryyygjAmt||ryyygjAmt==0)&&(!yfglAmt||yfglAmt==0)){
							alert(top.getMessage("FAILED_OPERATION","年结分配利润金额不能全为空或不能全为0, "),0);
							//NOT_FORM_EMPTY，
							return;
						}
						createFenPeiPz();
					}else{
						fptqFlag==1;
					}
				}
				if(fptqFlag==0){//年节添加判断
					return;
				} 
				if('1'==kps){
					//alert('程序正在处理，请不要重复提交。',1);
					alert(top.getMessage("WAIT_OPERATION", "处理"))
					return false;
				}else{
					kps = '1';
					LoadingAnimate.start();
					jQuery.ajax({
						url:webPath+"/cwMenthEnd/closeToNextMonthAjax",
						data:{ajaxData:''},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								alert(top.getMessage("SUCCEED_OPERATION"),1);
								location.reload();
							}else if(data.flag=="error"){
								if(alertFlag){
									 alert(data.msg,0);
								}else{
									alert(data.msg,0);
								}
							}
							kps = '0';
							LoadingAnimate.stop();
						},error:function(data){
							if(alertFlag){
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}else{
								alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
							kps = '0';
							LoadingAnimate.stop();
						}
					});
				}
			}
			
			//异步获取结账下一期标志
			function getCloseNextFlag(){
				jQuery.ajax({
					url:webPath+"/cwMenthEnd/getCwCloseNextFlagAjax",
					data:{ajaxData:''},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							doNotClose = data.doNotClose;
							showNoMeedVch(data.isExistBroken, data.vchNotJZ, data.isMonth,data.isBalance);
						}else if(data.flag=="error"){
							 alert(data.msg,0);
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
			
			//显示不满足消息提示
			function showNoMeedVch(isExistBroken, vchNotJZ, isMonth,isBalance){
				if(doNotClose=='1'){
					$('#notJz_Msg').text('不满足结账条件。');
					var $noMeedDiv = $('#jz_tips');
					$noMeedDiv.html('');
// 					$noMeedDiv.append('<i></i>您无法结账，');//2017.6.14 新增试算平衡判断，改为了逗号隔开提示
					$noMeedDiv.append('<i></i>您无法结账');
					if(isExistBroken==true ||'true'==isExistBroken){
						$noMeedDiv.append('，请<a onclick="vchReArrang();" >「整理」</a>断号凭证');
					}
// 					if((isExistBroken==true ||'true'==isExistBroken) && ''!=vchNotJZ){
// 						$noMeedDiv.append('<span> 与 </span>');
// 					}	
					if(''!=vchNotJZ){
						//将审核修改成记账
						$noMeedDiv.append('，请记账:' + vchNotJZ + ' 凭证');
					}
					if('1'==isMonth){
						$noMeedDiv.append('，结账期开始日期大于系统日期');
					}
					if("0"==isBalance){
						$noMeedDiv.append("，财务初始余额试算不平衡");
					}
					$noMeedDiv.append('！');
					$('#notJz_tips').removeClass('hidden');
				}else{
					$('#notJz_Msg').text('财务初始余额试算平衡。');
					$('#notJz_tips').addClass('hidden');
				}
			}
			
			//弹窗查看凭证详情
			function ajaxGetVoucherByNo(obj){
				window.parent.openBigForm('${webPath}/cwVoucherMst/toVoucherEdit?voucherNo='+$(obj).attr('voucherNo'), '凭证详情',getCloseNextFlag);
			}
			
			//凭证整理
			function vchReArrang(){
				window.parent.openBigForm('${webPath}/cwVoucherMst/goZhengliPage', '凭证整理',getCloseNextFlag);
			}
			
			function toInitBalPage(){
				window.location.href = "${webPath}/cwInitBal/getInitPage";
			}
		</script>
	</head>
<body class="scroll-content">
	<div class="container monthed_div">
		<div class="month_wrap">
			<div class="row month_summary">
				<div class="col-md-12">
					<span class='month_tit mgr' id="vchWeek">${dataMap.monthedWeek }</span>
					<span>共录入凭证<span>${dataMap.vchSize }</span>张，附件数<span>${dataMap.vchCounts }</span>张，</span>
					<span id="notJz_Msg">
<%-- 						<c:if test='${dataMap.vchNotJZ!="" || dataMap.isExistBroken==true}'> --%>
<!-- 							不满足结账条件。 -->
<%-- 						</c:if> --%>
<%-- 						<c:if test='${dataMap.vchNotJZ=="" && dataMap.isExistBroken==false}'> --%>
<!-- 							财务初始余额试算平衡。 -->
<%-- 						</c:if> --%>
					</span>
				</div>
			</div>
			<div class="row hidden" id="notJz_tips">
				<div class="col-xs-12">
					<div class="ui-tips pull-left ui-tips-error" id="jz_tips"></div>
				</div>
			</div>
			<div id='month_step_body' class='month_step step_display'>
				<form id="monthForm">
					<div id='month'>
						<div class="row vch_item">
							<div class="col-md-12">
								<input type="checkbox" id="zjBoxs" name="zjBoxs" checked="" />
								<span class='month_tit txt'>计提折旧</span>
								<span class="hidden" id="zjTxt"><span class="txt">已生成以下折旧凭证：</span><span id="zjpz"><a>记-1</a></span></span>
							</div>
							<div class="col-md-12">
								<span class="txt">凭证日期：</span>
								<input type="text" class="form-control vch-date mgr" id="zjVchDate" name="zjVchDate" readonly value="${dataMap.currDate }">
								<span class="txt">凭证字:</span>
								<select class="form-control vch-select mgr" id="zjVchZi" name="zjVchZi"><option value="1000">记</option></select>
								<span class="txt">凭证摘要:</span>
								<input type="text" class="form-control vch-summary" id="zjVchSummary" name="zjVchSummary" value="本月计提折旧">
							</div>
						</div>
						<div class="row vch_item" style="display: none;">
							<div class="col-md-12">	
								<input type="checkbox" id="txBoxs" name="txBoxs" />
								<span class='month_tit txt'>摊销费用</span>
								<span class="hidden" id="txTxt"><span class="txt">已生成以下摊销凭证：</span><span id="txpz"><a>记-2</a></span></span>
							</div>
							<div class="col-md-12">
								<span class="txt">凭证日期：</span>
								<input type="text" class="form-control vch-date mgr" id="txVchDate" name="txVchDate" readonly value="${dataMap.currDate }">
								<span class="txt">凭证字:</span>
								<select class="form-control vch-select mgr" id="txVchZi" name="txVchZi"><option value="1000">记</option></select>
								<span class="txt">凭证摘要:</span>
								<input type="text" class="form-control vch-summary" id="txVchSummary" name="txVchSummary" value="本月摊销费用">
							</div>
						</div>
						<div class="row vch_item">
							<div class="col-md-12">		
								<span class='month_tit txt'>结转损益</span>
								<span class="hidden" id="jzTxt"><span class="txt">已生成以下结转损益凭证：</span><span id="jzpz"><a>记-3</a></span></span>
							</div>
							<div class="col-md-12">
								<span class="txt">凭证日期：</span>
								<input type="text" class="form-control vch-date mgr" id="jzVchDate" name="jzVchDate" readonly value="${dataMap.currDate }">
								<span class="txt">凭证字:</span>
								<select class="form-control vch-select mgr" id="jzVchZi" name="jzVchZi"><option value="1000">记</option></select>
								<span class="txt">凭证摘要:</span>
								<input class="form-control vch-summary" type="text" id="jzVchSummary" name="jzVchSummary" maxlength="200" value='结转本期损益到本年利润' />
							</div>
						</div>
						
						
						<!-- <div id='month_more' style="display: none;"> -->
						<div id='month_more' >
							<div class="row set_item">
								<div class="col-md-12">
									<span class="txt">凭证分类：</span>
									<input type="radio" id="rdox1" checked="checked" name="rdox" value="1" />
									<span class="txt mgr">分别生成收益凭证和损失凭证</span>
									<input type="radio" id="rdox2" name="rdox" value="0" />
									<span class="txt">生成一张凭证，既包括收益也包括损失</span>
								</div>
							</div>
							<div class="row set_item">
								<div class="col-md-12">
								<div class="label_account">本年利润科目：</div>
									<input class="form-control vch-account" type="text" onclick="autoComPleter(this, '0')" id="lrkm" name="lrkm" value="${dataMap.lrkm }" />
									<span class="glyphicon glyphicon-search search-addon comitem_select" for="lrkm"></span>
								</div>
							</div>
							<div class="row set_item">
								<div class="col-md-12">
									<input type="checkbox" checked="" id="tzBoxs" name="tzBoxs" />
									<span class='month_tit mgr'>结转以前年度损益调整</span>
									<span class="hidden" id="syTxt"><span class="txt">已生成以下损益调整凭证：</span><span id="sypz"><a>记-4</a></span></span>
								</div>
							</div>
							<%-- <div class="row set_item">
								<div class="col-md-12">
								<div class="label_account">本年利润科目：</div>
									<input class="form-control vch-account" type="text" onclick="autoComPleter(this, '0')" id="lrkm" name="lrkm" value="${dataMap.lrkm }" />
									<span class="glyphicon glyphicon-search search-addon comitem_select" for="lrkm"></span>
								</div>
							</div> --%>
							<!-- lzs -->
							
							<div class="row set_item">
								<div class="col-md-12">		
									<div class="label_account">以前年度损益调整科目：</div>
									<input class="form-control vch-account" type="text" onclick="autoComPleter(this, '0')" id="tzkm" name="tzkm" value="${dataMap.tzkm }" />
									<span class="glyphicon glyphicon-search search-addon comitem_select" for="tzkm"></span>
								</div>
							</div>			
							<div class="row set_item">
								<div class="col-md-12">	
								<div class="label_account">以前年度损益调整科目的结转科目：</div>
									<input class="form-control vch-account" type="text" onclick="autoComPleter(this, '0')" id="jzkm" name="jzkm" value="${dataMap.jzkm }" />
									<span class="glyphicon glyphicon-search search-addon comitem_select" for="jzkm"></span>
								</div>
							</div>
							
							<!-- lzs -->
						<div class="row vch_item  hidden" style="margin-bottom: 10px;" id="yearEndpz">
							<div class="col-md-12">		
								<span class='month_tit txt'>年终处理</span>
								<span class="hidden" id="yearTxt"><span class="txt">已生成以下年终处理凭证：</span><span id="yearpz"><a>记-3</a></span></span>
							</div>
							<div class="col-md-12">
								<span class="txt">凭证日期：</span>
								<input type="text" class="form-control vch-date mgr" id="yearVchDate" name="yearVchDate" readonly value="${dataMap.currDate }">
								<span class="txt">凭证字:</span>
								<select class="form-control vch-select mgr" id="yearVchZi" name="yearVchZi"><option value="1000">记</option></select>
								<span class="txt">凭证摘要:</span>
								<input class="form-control vch-summary" type="text" id="yearVchSummary" name="yearVchSummary" maxlength="200" value='结转本年利润到未分配利润' />
								<!--lzs  -->
								<!-- <div class="row set_item">
									<div class="col-md-12 hidden" id="createlrpz">
										<div style="width:211px;float:left">
											<input type="checkbox" value="生成利润分配凭证" id="fenpeipz"/>
											<span class="txt mgr"><label for="fenpeipz">生成利润分配凭证</label></span> 
											<span class="hidden" id="yearlrfp"><span class="txt">已生成以下年终处理凭证：</span><span id="yearfp"><a>记-3</a></span></span>
										</div>
										<input type="checkbox" value="生成利润提取凭证" id="tiqupz"/>
										<span class="txt"><label for="tiqupz">生成利润提取凭证</label></span>
										<span class="hidden" id="yearlrtq"><span class="txt">已生成以下年终处理凭证：</span><span id="yeartq"><a>记-3</a></span></span>
									</div>
								</div> -->
							</div>
							<div class="row set_item  hidden" id="yearEndkm">
								<div class="col-md-12">
								<div class="label_account" style="padding-top: 10px">未分配利润科目：</div>
									<input class="form-control vch-account" type="text" onclick="autoComPleter(this, '0')" id="wfpkm" name="wfpkm" value="${dataMap.wfplrkm }" />
									<span class="glyphicon glyphicon-search search-addon comitem_select" for="wfpkm"></span>
								</div>
							</div>
						</div>
							
						</div>
					</div>
				</form>	
				<form>
					<div id='year' style="display: none;">
					<!-- <div id='year' > -->
						<div class="row">
							<span class='month_tit span_left'>6、年终处理</span><span id="nzpz"></span>
						</div>
						<div>
							<div class='row'>
								<div class="col-md-4">
									<span class="span_left">当期未分配利润：</span>
								</div>
								<div class="col-md-8">
									<input class="form-control" type="text" onclick="autoComPleter(this, '0')" id="acc_no_6"
										name="acc_no_6" />
								</div>
							</div>
						</div>
						<!-- <div class='row'>
							<span class="span_right">余额：</span>
							<span id='acc_no_amt1'>0.00</span> 元
						</div> -->
						<div class='row'>
							<span class="span_right">本年利润科目余额：</span>
							<span id='acc_no_amt2'>0.00</span> 元
						</div>
						<div class='row'>
							<span class="span_left">未分配利润累计金额：</span>
							<span id='money_lrfp'>0.00</span>元
						</div>
						<div class='row'>
							<input type="checkbox" id="boxs_1" name="boxs" checked/>是否分配提取
						</div>
						<div id='fenpei_1' class="row">
							<div class="col-md-4">
								<span class="span_left">利润分配——提取法定盈余公积</span>
							</div>
							<div class="col-md-8">
								<span><input class="form-control" type="text" id="boxs_1_1" maxlength="16"
									onkeyup="value=value.replace(/[^0-9\.]/g,'')" name="boxs_1_1" />元</span>
							</div>
						</div>
						<div id='fenpei_2' class="row">
							<div class="col-md-4">
								<span class="span_left">利润分配——提取一般风险准备</span>
							</div>
							<div class="col-md-8">
								<span><input class="form-control" type="text" id="boxs_1_2" maxlength="16"
									onkeyup="value=value.replace(/[^0-9\.]/g,'')" name="boxs_1_2" />元</span>
							</div>
						</div>	
						<div id='fenpei_3' class="row">
							<div class="col-md-4">
								<span class="span_left">利润分配——提取任意盈余公积</span>
							</div>
							<div class="col-md-8">
								<span><input class="form-control" type="text" id="boxs_1_3" maxlength="16"
									onkeyup="value=value.replace(/[^0-9\.]/g,'')" name="boxs_1_3" />元</span>
							</div>
						</div>
						<div id='fenpei_4' class="row">
							<div class="col-md-4">
								<span class="span_left">利润分配——提取应付股利</span>
							</div>
							<div class="col-md-8">
								<span><input class="form-control" type="text" id="boxs_1_4" maxlength="16"
									onkeyup="value=value.replace(/[^0-9\.]/g,'')" name="boxs_1_4" />元</span>
							</div>
						</div>
						<div class='row'>
							<span class="span_left">提取累计金额：</span>
							<span id='tqlj_amt'>0.00</span>元
						</div>
						<div class='row'>
							<span class="span_left">未分配利润剩余金额：</span>
							<span id='wfpsy_amt3'>0.00</span>元
						</div>
					</div>
				</form>
					<!-- <p class="more text-right"><a href="#" id="moreTrigger" class="">更多设置<i></i></a></p> -->
				
			</div>
			<!-- lzs begin  hidden-->
				<div id='year_step_body' class='month_step step_display hidden '>
				<form id="monthForm">
					<div id='month'>
						<!-- lzs -->
						<div class="row vch_item " style="margin-bottom: 10px;" id="yearEndpzw">
							<div class="col-md-12">		
								<span class='month_tit txt'>年终处理</span>
								<span class="hidden" id="yearTxt"><span class="txt">已生成以下年终处理凭证：</span><span id="yearpz"><a>记-3</a></span></span>
							</div>
							<div class="col-md-12">
								<%-- <span class="txt">凭证日期：</span>
								<input type="text" class="form-control vch-date mgr" id="yearVchDate" name="yearVchDate" readonly value="${dataMap.currDate }">
								<span class="txt">凭证字:</span>
								<select class="form-control vch-select mgr" id="yearVchZi" name="yearVchZi"><option value="1000">记</option></select>
								<span class="txt">凭证摘要:</span>
								<input class="form-control vch-summary" type="text" id="yearVchSummary" name="yearVchSummary" maxlength="200" value='结转本年利润到未分配利润' />
								 --%><!--lzs  -->
								<!-- <div class='row'>
									<span class="span_right">余额：</span>
									<span id='acc_no_amt1'>0.00</span> 元
								</div> -->
								<div class='row kmst-in hidden' >
									<span class="span_right">本年利润科目:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<input type="text" id="bnlrAccNo"  class="form-control accnoKm mgr" disabled  value="${dataMap.lrkm }"/>
									<%-- <span id='acc_no_1'>${dataMap.lrkm }</span> --%>
									<span class="span_right">本年利润科目余额：</span>
									<span id='bnlrBal'>0.00</span> 元
								</div>
								<div class='row'>
									<span class="span_right">未分配利润科目：</span>
									<input type="text" id="wfplrAccNo"  class="form-control accnoKm mgr" disabled  value="${dataMap.wfplrkm }"/>
									<!-- <span id='acc_no_2'>111000</span> -->
									<span class="span_left">未分配利润累计金额：</span>
									<span id='wfplrBal'>0.00</span>元
								</div>
								<div class="row set_item">
									<div class="col-md-12 " id="createlrpz">
										<div>
											<input type="checkbox" value="是否分配提取" id="fptqpz" checked/>
											<span class="txt mgr"><label for="fptqpz">是否分配提取</label></span> 
											<span class="hidden" id="yearlrfp1"><span class="txt">已生成以下年终处理凭证：</span><span id="yearfp"><a>记-3</a></span></span>
											<div>
												<div class="col-md-12 bottcls"><span class="col-md-3">利润分配—提取法定盈余公积：</span><input type="text" id="fdyygjAmt" onchange="fptqFun(this)" class="form-control lrinp-w mgr" maxlength="16"onkeyup="value=value.replace(/[^0-9\.]/g,'')" value=""/><span>元</span></div>
												<div class="col-md-12 bottcls"><span class="col-md-3">利润分配—提取一般风险准备：</span><input type="text" id="ybfxzbAmt" onchange="fptqFun(this)" class="form-control lrinp-w mgr" maxlength="16"onkeyup="value=value.replace(/[^0-9\.]/g,'')" value=""/><span>元</span></div>
												<div class="col-md-12 bottcls"><span class="col-md-3">利润分配—提取任意盈余公积：</span><input type="text" id="ryyygjAmt" onchange="fptqFun(this)" class="form-control lrinp-w mgr" maxlength="16"onkeyup="value=value.replace(/[^0-9\.]/g,'')" value=""/><span>元</span></div>
												<div class="col-md-12 bottcls"><span class="col-md-3">利润分配—提取应付股利：</span><input type="text" id="yfglAmt" onchange="fptqFun(this)" class="form-control lrinp-w mgr"  maxlength="16"onkeyup="value=value.replace(/[^0-9\.]/g,'')"value=""/><span>元</span></div>
												<div class="col-md-12 bottcls"><span>提取累计金额：</span><span id="tqAmt">0.00</span>元</div>
												<div class="col-md-12 bottcls"><span>未分配利润剩余金额：</span><span id="fpsAmt">0.00</span>元</div>
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>	
					<form>
						<div id='year_more' style="display: none;">
							<!-- lzs -->
							
						</div>
					</div>
				</form>	
					<!-- <p class="more text-right"><a href="#" id="moreTriggerYear" class="">更多设置<i></i></a></p> -->
				
			</div>
			<!-- lzs end  -->
			<div class='month_btn'>
				<c:if test='${dataMap.reClose=="1"}'>
					<span class="pull-left">
						<button class="btn btn-primary" id="btn_reclose">反结账</button>
					</span>
				</c:if>
				<!-- <span class="pull-left" style="margin-left: 10px">
					<button class="btn btn-primary hidden" id="btn_pzVch" onclick="pzVch();">结账页</button>
				</span> -->
				<span>
					<button class="btn btn-primary" id="btn_vch" onclick="createMonthVch();">生成凭证</button>
					<button class="btn btn-primary hidden" id="btn_yearEnd" onclick="createYearEndVch();">年结</button>
					
					<button class="btn btn-default" id="btn_close" disabled="">结账</button>
				</span>
				<c:if test='${dataMap.doNotClose=="0"}'>
				</c:if>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//点击生成年结凭证
	function createYearEndVch(){
		$("#month_step_body").hide();
		$("#year_step_body").removeClass("hidden");
		$("#btn_yearEnd").hide();
		$("#btn_pzVch").removeClass("hidden");
		$('#btn_close').removeAttr('disabled').removeClass('btn-default').addClass('btn-primary')
									.bind('click', function(){
			closeToNextMonth();
		})
		//获取未分配余额，未分配利润累计金额
		$.ajax({
				url:webPath+"/cwMenthEnd/getYearKemuBalAjax",
				data:null,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						var yearmap = $.parseJSON(data.yearMap);
						
						$("#bnlrBal").text(yearmap.bnlrBal);
						$("#bnlrAccNo").val(yearmap.bnlrAccNo);
						$("#wfplrBal").text(yearmap.wfplrBal);
						$("#wfplrAccNo").val(yearmap.wfplrAccNo);
						
					}else if(data.flag=="error"){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	}
	function pzVch(){
		$("#month_step_body").show();
		$("#year_step_body").addClass("hidden");
		$("#btn_yearEnd").show();
		$("#btn_pzVch").addClass("hidden");
	}
	/*计算利润分区提取数据*/
	function fptqFun(obj){
		
		var wfplrBal = $("#wfplrBal").text();
		var fdyygjAmt = $("#fdyygjAmt").val();
		var ybfxzbAmt = $("#ybfxzbAmt").val();
		var ryyygjAmt = $("#ryyygjAmt").val();
		var yfglAmt = $("#yfglAmt").val();
		
		var fiveamtJson = {"wfplrBal":wfplrBal,"fdyygjAmt":fdyygjAmt,"ybfxzbAmt":ybfxzbAmt,"ryyygjAmt":ryyygjAmt,"yfglAmt":yfglAmt}; 
			 $.ajax({
				 url:webPath+"/cwMenthEnd/getfptqAmtAjax",
				data:fiveamtJson,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						fptqFlag = 1;
						var amtmap = $.parseJSON(data.amtmap);
						if(amtmap){
							$("#tqAmt").text(amtmap.hebal);
							$("#fpsAmt").text(amtmap.sybal);
						}
						
					}else if(data.flag == "error"){
						// $("#btn_close").hide();
						fptqFlag = 0;
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
	}
	/* 生成分配凭证*/
	
	function createFenPeiPz(){
		var fdyygjAmt = $("#fdyygjAmt").val();
		var ybfxzbAmt = $("#ybfxzbAmt").val();
		var ryyygjAmt = $("#ryyygjAmt").val();
		var yfglAmt = $("#yfglAmt").val();
		
		if((!fdyygjAmt||fdyygjAmt==0)&&(!ybfxzbAmt||ybfxzbAmt==0)&&(!ryyygjAmt||ryyygjAmt==0)&&(!yfglAmt||yfglAmt==0)){
			alert(top.getMessage("FAILED_OPERATION","年结分配利润金额不能全为空或不能全为0 "),0);
			//NOT_FORM_EMPTY，
			fptqFlag = 0;
		}
		var yearVchDate = $("#yearVchDate").val();
		var yearVchZi = $("#yearVchZi").val();
// 		var yfglAmt = $("#yfglAmt").val();
		var fiveamtJson = {"fdyygjAmt":fdyygjAmt,"ybfxzbAmt":ybfxzbAmt,"ryyygjAmt":ryyygjAmt,"yfglAmt":yfglAmt,"yearVchZi":yearVchZi,"yearVchDate":yearVchDate}; 
			 $.ajax({
				 url:webPath+"/cwMenthEnd/createfptqpzAjax",
				data:fiveamtJson,
				type:"POST",
				dataType:"json",
				async: false,
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
					fptqFlag = 1;
						var amtmap = $.parseJSON(data.amtmap);
						if(amtmap){
							$("#tqAmt").text(amtmap.hebal);
							$("#fpsAmt").text(amtmap.sybal);
						}
					}else if(data.flag == "error"){
						// $("#btn_close").hide();
						fptqFlag = 0;
						 alert(data.msg,0);
						 return;
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
		return;
	}
	
</script>

</html>