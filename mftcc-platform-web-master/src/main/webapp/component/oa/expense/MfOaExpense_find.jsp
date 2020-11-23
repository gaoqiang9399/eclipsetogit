<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script type="text/javascript"
	src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>

<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
<script type="text/javascript" src='${webPath}/component/oa/debt/js/MfOaExpense.js'> </script>
<link rel="stylesheet" href="${webPath}/component/oa/css/MfOaFormStyle.css" type="text/css" />
<SCRIPT type="text/javascript">    
		var type = '${mfOaExpense.type}';
		$(function(){
			$("input[name='amt']").keyup();
		 	$("input[name='amt']").blur();
			bigMoney();
	      	if(${mfOaExpense.type>0}){
				$(".mysearchh-div").find("li").removeClass("mySelectedNode");
				$("li[value='"+type+"']").addClass("mySelectedNode");
				$("input[name='type']").val(type);
			}
			$(".mysearch-div2").find("li").removeClass("mySelectedNode");
			$(".mysearch-div2").find("li:nth-child(${2})").addClass("mySelectedNode");
		 });  
</SCRIPT>
<style type="text/css">

</style>
</head>
<body class="body_bg overFlowHidden" style="background-color: #f4f4f4;">
	<div class="mf_content" id="expenseFind" style=" ">
		<div class="content-box">
			<div class="tab-content">
				<div class="divform">
					<form method="post" id="OaAExpenseInsert" theme="simple" name="operform" action="${webPath}/mfOaExpense/insertAjax">
						<table  class="hundred" border="0" align="center" cellpadding="1"
							cellspacing="1" style="margin-left: 0%;">
							<tr align="center">
								<td colspan="2" class="mainTd_2_1">
									<div>
										<span>报销申请单</span>
									</div>
							</tr>
							<tr>
								<td height="30" colspan="2"></td>
							</tr>
							<tr class="rows">
								<td class="td_bottom" colspan="2" class="hundred"><div class="label"
										style="width: 10%;">标题</div>
									<div  class="leftBorder" style="width: 90%; ">
										<input type="text" onblur="func_uior_valTypeImm(this);" class="Required text hundred" mustinput="1" name="name"
											value=${mfOaExpense.name}></input>
									</div></td>
							</tr>

							<tr class="rows">
								<td  class="tdleft"><div class="label"
										style="width: 19.23%;">申请人</div>
									<div class="leftBorder" style="float: left; width: 73.08%; ">
										<input class="text hundred" readonly="readonly" name="opName"
											value=${mfOaExpense.opName}></input>
									</div></td>
								<td  class="tdhw"><div class="label"
										style="width: 20.83%;">部门</div>
									<div class="leftBorder" style="width: 79.17%; ">
										<input class="text hundred" readonly="readonly" name="brName"
											 value=${mfOaExpense.brName}></input>
									</div></td>
							</tr>
							<tr>
								<td><input hidden name="expenseId" value=${mfOaExpense.expenseId}></input>
								</td>
								<td><input hidden name="regTime" value=${mfOaExpense.regTime}></input>
								</td>
							</tr>
						</table>
						<div style="padding: 20px 16px; border: 1px solid #a1a6a7; margin: 0px -15px;">
							<table class="hundred" border="1" align="" cellpadding="1"
								cellspacing="1" style="margin-left: 0%;">
								<tr>
									<td colspan="2"  class="hundred"><div class="label"
											style="width: 15%;padding-top: 12px;">费用类别</div>
										<div  class="eight-five" style="float: left; ">
											<div class="search-div" id="search-div">
												<div class="mysearch-div mysearchh-div">
													<ul id="exptype-show" class="ztree">
														<li name="type" value="1"><a
															class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">业务宣传费用</span>
																</div>
														</a></li>
														<li name="type" value="2"><a class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">业务招待费用</span>
																</div>
														</a></li>
														<li  name="type" value="3"><a class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i></span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">会议费</span>
																</div>
														</a></li>
														<li name="type" value="4"><a class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">修理费</span>
																</div>
														</a></li>
														<input  type="hidden" name="type" value="" >
													</ul>
													<div class="dropdown pull-left show">
													<button type="button" class="btn dropdown-toggle " id="dropdownMenu"
														data-toggle="dropdown" aria-expanded="false">
														更多 <span class="caret"></span>
													</button>
													<ul id="exptype-more" class="dropdown-menu ztree in" role="menu">
														<li  name="type" value="5"><a class="level0"> <span class="button borderR-N"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">购置低值易耗品</span>
																</div>
														</a></li>
														<li  name="type" value="6"><a class="level0"> <span class="button borderR-N"> <i
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
									<td colspan="2" class="hundred" style="padding-top: 6px"><div class="label" style="width: 15%;">本次报销金额(元)</div>
										<div  class="leftBorder eight-five" >
											<input class="Required text" mustinput="1" type="text" title="申请金额(元)" id="amt" name="amt" datatype="12" onblur="bill(this);resetTimes();" onfocus="selectInput(this);" onkeyup="money(this);textupp(this,13);" onKeyDown="textdown(this,event,13);"  onmousedown="enterKey()"  
												style="width: 30%;float: left;"  value=${mfOaExpense.amt}></input>
												<span style="float:left;padding-top: 7px;padding-right: 20px;font-size:14px;">大写(元)</span><input  type="text" title="申请金额(元)" name="bigMoney"  onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"  onmousedown="enterKey()" onkeydown="enterKey();" 
												style="width: 53%;float: left;" value=" "></input>
										</div></td>
								</tr>
								<tr class="rows">
									<td height="40" colspan="3"><font color="#585858">
											<textarea name="applyReason"  class="hundred" rows="5"
												id="textarea" onKeyDown="textdown(event)" onKeyUp="textup()"
												onfocus="if(value=='报销理由(限200字)'){value=''}"
												onblur="if (value ==''){value='报销理由(限200字)'}" >${mfOaExpense.applyReason}</textarea></td>
								</tr>
								
								<tr class="rows">
								<td  class="tdleft" style="padding-top: 10px;" ><div class="label"
										style="width: 28.84%;">发票数量</div>
									<div class="leftBorder" style="width: 63.46%; ">
										<input class="text hundred" datatype="1" title="发票数量" name="billCount" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeydown="enterKey();"class="Required" mustinput="1" 
											 value=${mfOaExpense.billCount} ></input>
									</div></td>
								<td id="billNo" class="rows tdhw"><div class="label"
										style="width: 31.25%;">发票号码</div>
									<div  class="leftBorder" style="width: 68.75%; ">
										<input class="text" name="billNo" onblur="bill(this);" class="Required text hundred" mustinput="1" 
											 value=${mfOaExpense.billNo}></input>
									</div></td>
							</tr>
								
								<tr class="rows">
									<td  class="tdleft"><div class="label" style="padding-top: 7px;width: 28.84%;">立即提交</div>
										<div style="float: left; width: 63.46%;">
											<input  type="hidden" name="nowSub" value="0">
											<div class="search-div radioo" id="search-div">
												<div class="mysearch-div mysearch-div2">
													<ul class="ztree">
														<li name="nowSub" value="1"><a class="level0"> <span class="button borderR-N ">
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
		</div>
	</div>
					<div class="formRowCenter" style="margin-top: -60px;">
						<dhcc:thirdButton value="更新" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
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
			$("li").bind("click",selectType);
			$(".mysearchh-div").find("li[value='"+type+"']").click();
		});	
</script>
</html>