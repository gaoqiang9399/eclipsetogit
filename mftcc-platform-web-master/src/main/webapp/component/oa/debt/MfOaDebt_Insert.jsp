<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<script type="text/javascript" src='${webPath}/component/oa/debt/js/MfOaExpense.js'> </script>
<link rel="stylesheet" href="${webPath}/component/oa/css/MfOaFormStyle.css" type="text/css" />
<style type="text/css">
	.error .required{
		margin: 0px 0px 0px 0px;
	}
</style>
</head>
<body class="body_bg overflowHidden" style="background-color: #f4f4f4;">
	<div class="container form-container" >
		<div class="scroll-content ">
				<!-- <div class= "mCustomScrollBox mCS-light mCSB_vertical mCSB_inside">
					<div class="mCSB_container"> -->
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag divform">
					<form method="post" method="post" id="OaDebtInsert" theme="simple" name="operform" action="${webPath}/mfOaDebt/insertAjax">
						<table class="hundred " border="0" align="center" cellpadding="1" cellspacing="1" >
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
									<div class="label" style="width: 10%;">标题</div>
									<div class="leftBorder" style="width: 90%; ">
										<input type="text" onblur="func_uior_valTypeImm(this);" class="Required text hundred" mustinput="1" name="applyName"></input>
									</div>
								</td>
							</tr>
							<tr>
									<td class="td_bottom" height="40" style="width: 52%;">
									<div class="label" style="width: 19.23%;">申请人</div>
									<div class=" pull-left width_73" style="width: 73.08%;">
										<input readonly="readonly" name="opName" class="input-style"  value=${mfOaDebt.opName}></input>
									</div>
								</td>
								<td >
									<div class="label" style="width: 20.83%;">部门</div>
									<div  class="leftBorder" style="width: 79.17%; ">
										<input readonly="readonly" name="brName" class="input-style" value=${mfOaDebt.brName}></input>
									</div>
								</td>
							</tr>
						<!-- </table>
						<div style="padding: 20px 16px;  margin: 0px -15px;">
							<table class="hundred" border="1" align="" cellpadding="1" cellspacing="1" style="margin-left: 0%;"> -->
								<tr>
									<td colspan="2" class="hundred"><div class="label width_11" >申请类别</div>
										<div class="eight-three pull-left">
											<div class="search-div" id="search-div">
												<div class="mysearch-div">
													<ul class="ztree">
													 	<input  type="hidden" name="applyType" value="1" />
														<li name="applyType" value="1" class="mySelectedNode"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">因公借款</span>
																</div>
														</a></li>
														<li  name="applyType" value="2" ><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">个人借款</span>
																</div>
														</a></li>
														<li name="applyType" value="3"  ><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">预付款</span>
																</div>
														</a></li>
														<li  name="applyType" value="4" ><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">其他</span>
																</div>
														</a></li>
													</ul>
												</div>
											</div>
										</div>
										</div></td>

								</tr>
								<tr class="rows">
									<td colspan="2"><div class="label fifteen" >本次借款金额(元)</div>
										<div class="leftBorder eight-three">
											<input class="Required text pull-left width_30" mustinput="1" type="text" title="申请金额(元)" id="applyAmt" name="applyAmt" datatype="12" onblur="bill1(this);resetTimes();func_uior_valFormat_tips(this, 'nonnegative');" onfocus="selectInput(this);" onkeyup="money(this);textupp(this,13);" onKeyDown="textdown(this,event,13);"  onmousedown="enterKey()" maxlength="15"></input>
												<span class="capidal_title">大写(元)</span><input  class="Required text pull-left" type="text" title="申请金额(元)" name="bigMoney"  onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"  onmousedown="enterKey()" onkeydown="enterKey();" 
												 value=" "></input>
										</div></td>
								</tr>
								<tr class= "rows">
									<td height="40" colspan="5">
										<div class="leftBorder">
											<textarea class="Required  border-textarea" mustinput="1"  name="reason" id="textarea" onKeyDown="textdown(this,event,200)" onKeyUp="textupp(this,200)" placeholder="借款理由"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" class="hundred padding_top_10 " ><div class="label  width_11" >立即提交</div>
										<div >
											<input type="hidden" name="debtSts" value="Y" />
											<div class="search-div radioo" id="search-div">
												<div class="mysearch-div mysearch-div1">
													<ul class="ztree">
														<li  name="debtSts" value="Y"  class="mySelectedNode"><a
															class="level0"> <span class="button borderR-N ">
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option ">
																	<span id="my_filter_1_span" class="node_name"
																		style="color: black;">是</span>
																</div>
														</a></li>
														<li  name="debtSts" value="N" ><a
															class="level0"> <span class="button borderR-N ">
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option  ">
																	<span id="my_filter_1_span" class="node_name"
																		style="color: black;">否</span>
																</div>
														</a></li>
													</ul>
												</div>
											</div>
										</div>
										</div>
									</td>
								</tr>
							</table>
					</form>
					</div>
				</div>
			</div>
		</div>
	<!-- </div>
	</div> -->
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	</div>
</body>
<script type="text/javascript"	src="${webPath}/component/oa/debt/js/MfOaDebt.js"></script>
<script type="text/javascript">
OaDebt.path = "${webPath}";
var query = '${query}';
	$(function() {
		OaDebt.init();
		$("li").bind("click",selectType);
	});
</script>
</html>
