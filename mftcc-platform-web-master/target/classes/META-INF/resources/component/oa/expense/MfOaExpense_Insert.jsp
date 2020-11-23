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
</head>
<body class="body_bg overflowHidden" style="background-color: #f4f4f4;">
	<div class="container form-container" >
		<div class="scroll-content ">
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag divform">
					<form method="post" id="OaAExpenseInsert" theme="simple" name="operform" action="${webPath}/mfOaExpense/insertAjax">
						<table width="100%" border="0" align="center" cellpadding="1"
							cellspacing="1" style="margin-left: 0%;">
							<tr align="center">
								<td colspan="14" class="mainTd_2_1">

									<div>
										<span>报销申请单</span>
									</div>
							</tr>
							<tr>
								<td height="20" colspan="2"></td>
							</tr>
							<tr class="rows">
								<td class="td_bottom hundred" colspan="2"><div class="label" style=" width: 10%;">标题</div>
									<div class="leftBorder"  style=" width: 90%;">
										<input type="text" onblur="func_uior_valTypeImm(this);" class="Required  hundred" mustinput="1" name="name"
											style="outline: none; border: 0;" value=${mfOaExpense.name}></input>
									</div></td>
							</tr>

							<tr>
								<td class="td_bottom tdhw"><div class="label" style="width: 19.23%;">申请人</div>
									<div class="leftBorder" style=" width: 73.08%;">
										<input  class="text hundred" readonly="readonly" name="opName"
										 value=${mfOaExpense.opName}></input>
									</div></td>
								<td class="tdhw"><div class="label"
										style=" width: 20.83%;">部门</div>
									<div class="leftBorder" style=" width: 79.17%;">
										<input class="text hundred" readonly="readonly" name="brName"  value=${mfOaExpense.brName}></input>
									</div>
									<input hidden name="expenseId" value=${mfOaExpense.expenseId}></input>
									<input hidden name="regTime" value=${mfOaExpense.regTime}></input>
									</td>
							</tr>
								<tr>
									<td colspan="2" class="hundred"><div class="label fifteen"
											style="padding-top: 12px;">费用类别</div>
										<div  class="eight-three" style="float: left; ">
											<div class="search-div" id="search-div">
												<div id="expense-type" class="mysearch-div mysearchh-div">
													<ul id="exptype-show" class="ztree">
														<li name="type" value="1" class="mySelectedNode"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">业务宣传费用</span>
																</div>
														</a></li>
														<li name="type" value="2"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">业务招待费用</span>
																</div>
														</a></li>
														<li  name="type" value="3"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">会议费</span>
																</div>
														</a></li>
														<li name="type" value="4"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">修理费</span>
																</div>
														</a></li>
														<input type="hidden" name="type" value="1"/>
													</ul>
												<div class="dropdown pull-left show">
													<button type="button" class="btn dropdown-toggle " id="dropdownMenu"
														data-toggle="dropdown" aria-expanded="false">
														更多 <span class="caret"></span>
													</button>
													<ul id="exptype-more" class="dropdown-menu ztree in" role="menu">
														<li  name="type" value="5"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">购置低值易耗品</span>
																</div>
														</a></li>
														<li  name="type" value="6"><a
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
										</div> 
										
										</div></td>

								</tr>
								
								<tr class="rows">
									<td colspan="2" class="hundred" style="padding-top: 6px">
										<div class="label fifteen" >本次报销金额(元)</div>
										<div class="leftBorder eight-three">
											<input class="Required text pull-left width_30" mustinput="1" type="text" title="申请金额(元)" id="amt" name="amt" datatype="12" onblur="initAmt(this);resetTimes();func_uior_valFormat_tips(this, 'nonnegative');" onfocus="selectInput(this);" onkeyup="money(this);textupp(this,13);" onKeyDown="textdown(this,event,13);"  onmousedown="enterKey()" maxlength="15"></input>
											<span class="capidal_title">大写(元)</span>
											<input  type="text" title="申请金额(元)" name="bigMoney"  onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"  onmousedown="enterKey()" onkeydown="enterKey();" class="Required text pull-left" value=" "></input>
										</div>
									</td>
								</tr>
								<tr class="rows">
									<td height="40" colspan="3">
										<div class="leftBorder"
											<font color="#585858">
											<textarea name="applyReason"  class="hundred" rows="5" mustinput="1" id="textarea" maxlength="200" onKeyDown="textdown(this,event,200);" onKeyUp="textup()" placeholder="报销理由" value=${mfOaExpense.applyReason} ></textarea>
										</div>
									</td>
								</tr>
								
								<tr class="rows">
								<td class="tdleft"  ><div class="label" style=" width: 28.84%;">发票数量</div>
									<div class="leftBorder"  style=" width: 63.46%;">
										<input  class="Required text " type="text" datatype="1" title="发票数量" name="billCount" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeydown="enterKey();"class="Required hundred" mustinput="1" 
											 value=${mfOaExpense.billCount} ></input>
									</div></td>
								<td id="billNo" class="rows tdhw"><div class="label"
										style="width: 31.25%;">发票号码</div>
									<div class="leftBorder" style=" width: 68.75%;">
										<input type="text" name="billNo" onblur="bill(this);" class="Required text hundred" mustinput="1" 
											 value=${mfOaExpense.billNo}></input>
									</div></td>
							</tr>
								
							<tr class="rows">
									<td  class="tdleft" colspan='2'><div class="label padding_top_20 width_11" >立即提交</div>
										<div  class="pull_left">
											<div id="nowSub-type" class="search-div radioo" id="search-div">
												<div class="mysearch-div mysearch-div2">
													<input  type="hidden" name="nowSub" value="1" />
													<ul class="ztree">
														<li name="nowSub" value="1" class="mySelectedNode"><a
															class="level0"> <span class="button borderR-N ">
																	<i class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option ">
																	<span id="my_filter_1_span" class="node_name"
																		style="color: black;">是</span>
																</div>
														</a></li>
														<li name="nowSub" value="0"><a class="level0"> <span class="button borderR-N ">
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
	<!-- 	</div>
	</div>
	</div> -->
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/expense/js/MfOaExpenseInsert.js"></script>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<script type="text/javascript" src='UIplug/umeditor-dev/umeditor.config.js'></script>
<script type="text/javascript" src='UIplug/umeditor-dev/editor_api.js'></script>
<script type="text/javascript">
var query = '${query}';
		$(function() {
			OaAExpense.init();
			$("li").bind("click",selectType);
		});     
</script>
<script type="text/javascript" src='${webPath}/component/oa/debt/js/MfOaExpense.js'> </script>
</html>
