<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<link rel="stylesheet" href="${webPath}/component/oa/css/MfOaFormStyle.css" type="text/css" />
<script type="text/javascript" src='${webPath}/component/oa/debt/js/MfOaExpense.js'> </script>
<SCRIPT type="text/javascript">    
       $(function(){
       	 $("input[name='applyAmt']").keyup();
 	 	 $("input[name='applyAmt']").blur();
         bigMoney();
		 $(".mysearchh-div").find("li").removeClass("mySelectedNode");
		 $(".mysearchh-div").find("li:nth-child(${mfOaDebt.applyType})").addClass("mySelectedNode");
	 });
</SCRIPT>
<style type="text/css">
.mysearchh-div li {
	display:none;
}
li.mySelectedNode {
	display:block;
}
</style>
</head>
<body class="body_bg overFlowHidden" style="background-color: #f4f4f4;">
	<div class="scroll-content" id="debtFind">
		<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class= "bootstarpTag divform">
					<form method="post" id="OaAExpenseInsert" theme="simple" name="operform" action="${webPath}/mfOaExpense/insertAjax">
						<table class="hundred" border="0" align="center" cellpadding="1" cellspacing="1" style="margin-left: 0%;">
							<tr align="center">
								<td colspan="14" class="mainTd_2_1">
									<div>
										<span>借款申请单</span>
									</div>
								</td>
							</tr>
							<tr>
								<td height="30" colspan="2"></td>
							</tr>
							<tr class="rows">
								<td class="td_bottom" colspan="2" class="hundred">
									<div class="label" style=" width: 10%;">标题</div>
									<div  class="leftBorder" style="width: 90%; ">
										<input type="text" readonly = "readonly" onblur="func_uior_valTypeImm(this);" class="Required text hundred" mustinput="1" name="applyName" value=${mfOaDebt.applyName}></input>
									</div>
								</td>
							</tr>
							<tr>
								<td  class="td_bottom" class="tdleft">
									<div class="label" style=" width: 19.23%;">申请人</div>
									<div class="leftBorder" style="width: 73.08%; ">
										<input class="text hundred " readonly="readonly" name="opName" value=${mfOaDebt.opName}></input>
									</div>
								</td>
								<td class="tdhw">
									<div class="label" style=" width: 20.83%;">部门</div>
									<div class="leftBorder" style="width: 79.17%; ">
										<input  class="text hundred" readonly="readonly" name="brName" value=${mfOaDebt.brName}></input>
									</div>
								</td>
							</tr>
							<tr>
								<td >
									<input hidden name="debtId" value=${mfOaDebt.debtId}></input>
								</td>
								<td >
									<input hidden name="applyTime" value=${mfOaDebt.applyTime}></input>
								</td>
							</tr>
						</table>
						<div style="padding: 20px 16px; border: 1px solid #a1a6a7; margin: 0px -15px;">
							<table class="hundred" border="0" align="" cellpadding="1" cellspacing="1" style="margin-left: 0%;">
								<tr>
									<td colspan="2" class="hundred"><div class="label fifteen" >申请类别</div>
										<div  class="eight-five" style="float: left; ">
											<div class="search-div" id="search-div">
												<div class="mysearch-div mysearchh-div">
													<ul class="ztree">
														<li name="applyType" value="1" >
															<a class="level0"> 
																<span class="button borderR-N"> 
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
																</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">因公借款</span>
																</div>
															</a>
														</li>
														<li name="applyType" value="2">
															<a class="level0"> 
																<span class="button borderR-N"> 
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
																</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">个人借款</span>
																</div>
															</a>
														</li>
														<li name="applyType" value="3">
															<a class="level0"> 
																<span class="button borderR-N"> 
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
																</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">预付款</span>
																</div>
															</a>
														</li>
														<li  name="applyType" value="4">
															<a class="level0"> 
																<span class="button borderR-N"> 
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
																</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">其他</span>
																</div>
															</a>
														</li>
													</ul>
												</div>
											</div>
										</div>
										</div>
									</td>
								</tr>
								<tr class="rows">
									<td colspan="2" style="padding-top: 6px">
										<div class="label fifteen" >本次借款金额(元)</div>
										<div class="eight-five">
											<input readonly="readonly" value=${mfOaDebt.applyAmt}  class="Required text" mustinput="1" type="text" title="申请金额(元)" id="applyAmt" name="applyAmt" datatype="12" onblur="bill(this);resetTimes();" onfocus="selectInput(this);" onkeyup="money(this);" onKeyDown="textdown(this,event,13);"  onmousedown="enterKey()" style=" width: 30%;float: left;"></input>
											<span style="float:left;padding-top: 7px;padding-right: 20px;font-size:14px;">大写(元)</span>
											<input type="text" class="Required text pull-left" title="申请金额(元)" name="bigMoney"  onblur="bill(this);resetTimes();" onfocus="selectInput(this);"  onmousedown="enterKey()" onkeydown="enterKey();" readonly = "readonly" style="float: left;" value=""></input>
										</div>
									</td>
								</tr>
								<tr class="rows">
									<td height="40" colspan="5">
										<font color="#585858">
										<textarea readonly = "readonly" type="textarea" value=${mfOaDebt.reason} name="reason" title="借款理由" class="hundred" rows="5" id="textarea" onKeyDown="textdown(event)">${mfOaDebt.reason}</textarea>
									</td>
								</tr>
								<tr class="rows">
									<td colspan="2" class="hundred" style="padding-top: 6px">
										<div class="label fifteen">申请时间</div>
										<div class="leftBorder" style="width: 63.46%;">
											<input readonly = "readonly" value=${mfOaDebt.applyTime} class="Required text pull-left" mustinput="1" type="text" title="申请金额(元)" name="applyTime" onblur="func_uior_valTypeImm(this);func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"onmousedown="enterKey()" onkeydown="enterKey();"></input>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter" style="margin-top: -60px;">
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/debt/js/MfOaDebtDetail.js"></script>
<script type="text/javascript">
OaDebtDetail.path = "${webPath}";
		$(function() {
			OaDebtDetail.init();  
			$("li").bind("click",selectType);
			 
			$(".mysearchh-div").find("li:nth-child(${mfOaDebt.applyType})").css("dispaly","block");
		});	
</script>
</html>