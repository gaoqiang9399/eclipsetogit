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
	.mysearchh-div li {
		display:none;
	}
</style>
<SCRIPT type="text/javascript">    
	$(function() {
		$("input[name='amt']").keyup();
		$("input[name='amt']").blur();
		bigMoney();
		if ("${mfOaExpense.type}" > 0) {
			$(".mysearchh-div").find("li").removeClass("mySelectedNode");
			$(".mysearchh-div").find("li:nth-child(${mfOaExpense.type})").addClass("mySelectedNode");
			$(".mysearchh-div").find("li:nth-child(${mfOaExpense.type})").css("display", "block");
			$(".mysearch-div2").find("li").removeClass("mySelectedNode");
			$(".mysearch-div2").find("li:nth-child(${1})").addClass("mySelectedNode");
		}
	});
</SCRIPT>
</head>
<body class="body_bg overFlowHidden" style="background-color: #f4f4f4;">
	<div class="scroll-content" id="expenseDetail">
		<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class= "bootstarpTag divform">
					<form method="post" id="OaAExpenseInsert" theme="simple" name="operform" action="${webPath}/mfOaExpense/insertAjax">
						<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" style="margin-left: 0%;">
							<tr align="center">
								<td colspan="14" class="mainTd_2_1">
									<div>
										<span>报销申请单</span>
									</div>
								</td>
							</tr>
							<tr>
								<td height="30" colspan="2"></td>
							</tr>
							<tr class="rows">
								<td class="td_bottom hundred" colspan="2" >
									<div class="label" style="width: 10%;">标题</div>
									<div class="leftBorder" style="width: 90%; ">
										<input readonly="readonly" type="text" onblur="func_uior_valTypeImm(this);" class="Required text hundred" mustinput="1" name="name" value=${mfOaExpense.name}></input>
									</div>
								</td>
							</tr>

							<tr>
								<td class="td_bottom" height="40" style="width: 52%;">
									<div class="label" style="width: 19.23%;">申请人</div>
									<div class=" pull-left width_73" style="width: 73.08%;">
										<input class="input-style" readonly="readonly" name="opName" value=${mfOaExpense.opName}></input>
									</div>
								</td>
								<td class="tdhw">
									<div class="label" style="width: 20.83%;">部门</div>
									<div class="leftBorder" style="width: 79.17%; ">
										<input class="text hundred" readonly="readonly" name="brName" value=${mfOaExpense.brName}></input>
									</div>
									<input hidden name="expenseId" value=${mfOaExpense.expenseId}></input>
									<input hidden name="regTime" value=${mfOaExpense.regTime}></input>
								</td>
							</tr>
						</table>
						<div style="padding: 20px 16px; border: 1px solid #a1a6a7; margin: 0px -15px;">
							<table class="hundred" border="0" cellpadding="1" cellspacing="1" style="margin-left: 0%;">
								<tr>
									<td colspan="2" style="width: 100%;">
										<div class="label" style="width: 15%;">费用类别</div>
										<div class="eight-five" style="float: left;">
											<div class="search-div" id="search-div">
												<div class="mysearch-div mysearchh-div">
													<ul class="ztree">
														<li >
															<a class="level0"> 
																<span class="button borderR-N"> <i class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">业务宣传费用</span>
																</div>
															</a>
														</li>
														<li >
															<a class="level0"> 
																<span class="button borderR-N"> <i class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">业务招待费用</span>
																</div>
															</a>
														</li>
														<li >
															<a class="level0"> 
																<span class="button borderR-N"> <i class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">会议费</span>
																</div>
															</a>
														</li>
														<li >
															<a class="level0"> 
																<span class="button borderR-N"> <i class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">修理费</span>
																</div>
															</a>
														</li>
														<li >
															<a class="level0"> 
																<span class="button borderR-N"> <i class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">购置低值易耗品</span>
																</div>
															</a>
														</li>
														<li >
															<a class="level0"> 
																<span class="button borderR-N"> <i class="i i-radio1"></i> <i class="i i-radio2"></i></span>
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
									<td colspan="2" class="hundred" style="padding-top: 6px">
										<div class="label" style="width: 15%;">本次报销金额(元)</div>
										<div class=" eight-five" >
											<input  readonly = "readonly" class="Required text" mustinput="1" type="text" title="申请金额(元)" id="amt" name="amt" datatype="12" onblur="bill(this);resetTimes();" onfocus="selectInput(this);" onkeyup="money(this);"  onmousedown="enterKey()" style=" width: 30%; float: left;"  value=${mfOaExpense.amt}></input>
											<span style="float:left;padding-top: 7px;padding-right: 20px;font-size:14px;">大写(元)</span>
											<input  readonly = "readonly" type="text" class="Required text pull-left" title="申请金额(元)" name="bigMoney"  onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"  onmousedown="enterKey()" onkeydown="enterKey();" style=" float: left;" value=" "></input>
										</div>
									</td>
								</tr>
								<tr class="rows">
									<td height="40" colspan="3">
										<font color="#585858">
										<textarea readonly="readonly" name="applyReason" class="hundred" rows="5" id="textarea"  value=${mfOaExpense.applyReason}>${mfOaExpense.applyReason}</textarea>
									</td>
								</tr>
								<tr class="rows">
								<td class="tdleft" style="padding-top: 10px;" >
									<div class="label" style="height: 30px; width: 28.84%;">发票数量</div>
									<div class="leftBorder" style="width: 63.46%; ">
										<input readonly = "readonly" class="text hundred" datatype="1" title="发票数量" name="billCount" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeydown="enterKey();"class="Required" mustinput="1"  value=${mfOaExpense.billCount} ></input>
									</div>
								</td>
								<td id="billNo" class="tdhw" class="rows">
									<div class="label" style="height: 30px; width: 31.25%;">发票号码</div>
									<div  class="leftBorder" style="width: 68.75%; ">
										<input readonly = "readonly" class="text hundred" name="billNo" onblur="bill(this);" class="Required" mustinput="1"  value=${mfOaExpense.billNo}></input>
									</div>
								</td>
							</tr>
							<tr class="rows">
								<td  class="tdleft" >
									<div class="label" style="width: 28.84%;">申请时间</div>
									<div  class="leftBorder" style="width: 63.46%;">
										<input readonly = "readonly" value=${mfOaExpense.regTime} class="Required hundred text" mustinput="1" type="text" title="申请金额(元)" name="regTime" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"onmousedown="enterKey()" onkeydown="enterKey();" ></input>
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
<script type="text/javascript" src="${webPath}/component/oa/expense/js/MfOaExpenseInsert.js"></script>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<script type="text/javascript" src='UIplug/umeditor-dev/umeditor.config.js'></script>
<script type="text/javascript" src='UIplug/umeditor-dev/editor_api.js'></script>
<script type="text/javascript">
	$(function() {
		OaAExpense.init();
		$(".mysearchh-div").find("li:nth-child(${mfOaExpense.type})").css("dispaly", "block");
	});
</script>
</html>
