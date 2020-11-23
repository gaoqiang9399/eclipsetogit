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
		 $(".mysearch-div1").find("li").removeClass("mySelectedNode");
		 $(".mysearch-div1").find("li:nth-child(${mfOaDebt.debtSts+1})").addClass("mySelectedNode");
	 }); 
</SCRIPT>
</head>
<body class="body_bg overFlowHidden" style="background-color: #f4f4f4;">
	<div class="scroll-content">
		<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class= "bootstarpTag divform">
					<form method="post" method="post" id="OaDebtDetail" theme="simple"
						name="operform" action="${webPath}/mfOaDebt/updateAjax">
						<table  class="hundred" border="0" align="center" cellpadding="1"cellspacing="1" style="margin-left: 0%;">
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
								<td class="td_bottom" colspan="2" class="hundred"><div class="label"
										style=" width: 10%;">标题</div>
									<div class="leftBorder" style="width: 90%; ">
										<input type="text" onblur="func_uior_valTypeImm(this);" class="Required text hundred" mustinput="1" name="applyName"
											 value=${mfOaDebt.applyName}></input>
									</div></td>
							</tr>

							<tr>
								<td class="td_bottom tdleft"><div class="label"
										style=" width: 19.23%;">申请人</div>
									<div  class="leftBorder" style="width: 73.08%; ">
										<input readonly="readonly" name="opName" class="text hundred"
											 value=${mfOaDebt.opName}></input>
									</div></td>
								<td class="tdhw"><div class="label"
										style=" width: 20.83%;">部门</div>
									<div class="leftBorder"  style="width: 79.17%; ">
										<input readonly="readonly" name="brName" class="text hundred"
											  value=${mfOaDebt.brName}></input>
									</div></td>
							</tr>
							<tr>
								<td >
										<input hidden name="debtId" value=${mfOaDebt.debtId}></input></td>
								<td >
										<input hidden name="applyTime" value=${mfOaDebt.applyTime}></input></td>
							</tr>
						</table>
						<div style="padding: 20px 16px; border: 1px solid #a1a6a7; margin: 0px -15px;">
							<table  class="hundred" border="1" align="" cellpadding="1"
								cellspacing="1" style="margin-left: 0%;">
								<tr>
									<td colspan="2"  class="hundred"><div class="label fifteen" >申请类别</div>
										<div class="applytype eight-three" style="float: left;">

											<div class="search-div" id="search-div">
												<div class="mysearch-div mysearchh-div">
													<ul class="ztree">
														<input  type="hidden" name="applyType" value="1" />
														<li  name="applyType" value="1" class="mySelectedNode"><a
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
														<li  name="applyType" value="3" ><a
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
									<td colspan="2" style="padding-top: 6px"><div class="label fifteen" >本次借款金额(元)</div>
										<div  class=" eight-three" >
											<input value=${mfOaDebt.applyAmt}  class="Required text" mustinput="1" type="text" title="申请金额(元)" id="applyAmt" name="applyAmt" datatype="12" onblur="bill1(this);;resetTimes();" onfocus="selectInput(this);" onkeyup="money(this);textupp(this,13);" onKeyDown="textdown(this,event,13);"  onmousedown="enterKey()"  
												style="width: 30%;float: left;" ></input>
												<span style="float:left;padding-top: 7px;padding-right: 20px;font-size:14px;">大写(元)</span><input type="text" title="申请金额(元)" name="bigMoney"  onblur="bill(this);resetTimes();" onfocus="selectInput(this);"  onmousedown="enterKey()" onkeydown="enterKey();" 
												style="float: left;" value=" "></input>
										</div></td>
								</tr>
								<tr class= "rows">
									<td height="40" colspan="5"><font color="#585858">
											<textarea class="Required text" mustinput="1"  value=${mfOaDebt.reason} name="reason" title="借款理由" style="border:1px solid #949191 !important;"
												id="textarea" onKeyDown="textdown(this,event,200)" onKeyUp="textupp(this,200)"
												placeholder="借款理由">${mfOaDebt.reason}</textarea></td>
								</tr>
								<tr>
									<td colspan="2" class="hundred" style=" padding-top: 12px;"><div class="label fifteen" >立即提交</div>
										<div class="eight-five" style="float: left; ">
											<input  hidden type="radio" name="debtSts" value="Y">
											<input checked="checked" hidden type="radio" name="debtSts" value="N">


											<div class="search-div radioo" id="search-div">
												<div class="mysearch-div mysearch-div1">
													<ul class="ztree">
														<li name="debtSts" value="Y" ><a
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

										</div></td>
								</tr>

							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
					<div class="formRowCenter" style="margin-top: -60px;">
						<dhcc:thirdButton value="更新" action="提交" typeclass="updateAjax"></dhcc:thirdButton>
						<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
					</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/debt/js/MfOaDebtDetail.js"></script>
<script type="text/javascript">
OaDebtDetail.path = "${webPath}";
		$(function() {
			OaDebtDetail.init(); 
			$("li").bind("click",selectType); 
			$("li[value='"+${mfOaDebt.applyType}+"']").click();
		});	
</script>
</html>