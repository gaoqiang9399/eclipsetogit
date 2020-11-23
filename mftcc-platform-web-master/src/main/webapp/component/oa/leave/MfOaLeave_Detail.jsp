<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/component/oa/leave/css/MfOaLeaveInsert.css" />
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript">    
function textdown(e) {
    textevent = e;
    if (textevent.keyCode == 8) {
        return;
    }
    if (document.getElementById('textarea').value.length >= 200) {
        if (!document.all) {
            textevent.preventDefault();
        } else {
            textevent.returnValue = false;
        }
    }
}
function textup() {
    var s = document.getElementById('textarea').value;
    //判断ID为text的文本区域字数是否超过100个 
    if (s.length > 200) {
        document.getElementById('textarea').value = s.substring(0, 200);
    }
}
		
</script>
</head>
<body class="body_bg overFlowHidden" style="background-color: #f4f4f4;">
	<div class="mf_content" >
		<div class="content-box">
			<div class="tab-content">
				<div class="content-div">
					<form method="post" method="post" id="OaLeaveInsert" theme="simple"
						name="operform" action="${webPath}/mfOaLeave/updateAjax">
						<table>
							<tr align="center">
								<td colspan="14" class="mainTd_2_1">

									<div>
										<span>请假申请单</span>
									</div>
							</tr>
							<tr>
								<td  colspan="2">
									<input type="hidden" title="请假编号" name="leaveNo" value="${mfOaLeave.leaveNo}"></input>
								</td>
							</tr>
							<tr>
								<td  style="width: 52%;">
								<div class="label" style=" float: left; width: 19.23%;">申请人</div>
									<div
										style="float: left; width: 73.08%; border-bottom: solid 1px #949191;">
										<input readonly="readonly" name="opName"
											class="input-style" value="${mfOaLeave.opName}"></input>
									</div>
									<input type="hidden" title="申请人编号" name="opNo" value="${mfOaLeave.opNo}"></input>
									</td>
								<td  style="width: 48%;"><div class="label"
										style=" float: left; width: 20.83%;">部门</div>
									<div
										style="float: left; width: 79.17%; border-bottom: solid 1px #949191;">
										<input readonly="readonly" name="brName"
											class="input-style" value="${mfOaLeave.brName}"></input>
									</div>
									<input type="hidden" title="部门编号" name="brNo" value="${mfOaLeave.brNo}"></input>
									</td>
							</tr>
						</table>
						<div style="padding: 20px 16px; border: 1px solid #a1a6a7; margin: 0px -15px;">
							<table width="100%" border="1"  cellpadding="1"
								cellspacing="1" style="margin-left: 0%;">
								<tr>
									<td colspan="2" style="width: 100%;"><div class="label"
											style=" float: left; width: 10%;padding-top: 12px;">请假类型</div>
										<div style="float: left; width: 90%;">

											<div class="search-div" id="search-div">
												<div class="mysearch-div mysearchh-div">
													<ul class="ztree">
														<li id="leave-7" name="7" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option" >
																	<span id="my_filter_1_span" class="node_name">事假</span>
																</div>
														</a></li>
														<li id="leave-6" name="6" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">带薪年假</span>
																</div>
														</a></li>
														<li id="leave-5" name="5" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">探亲假</span>
																</div>
														</a></li>
														<li id="leave-4" name="4" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">婚假</span>
																</div>
														</a></li>
														<li id="leave-3" name="3" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">产假</span>
																</div>
														</a></li>
														<li id="leave-2" name="2" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">倒休假</span>
																</div>
														</a></li>
														<li id="leave-1" name="1" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">病假</span>
																</div>
														</a></li>
														<li id="leave-0" name="0" class="leave"><a
															class="level0"> <span class="button gaga"> <i
																	class="i i-radio1"></i> <i class="i i-radio2"></i>
															</span>
																<div class="filter_option">
																	<span id="my_filter_1_span" class="node_name">丧假</span>
																</div>
														</a></li>
													</ul>
												</div>
											</div>
										</div> 
										<input type="hidden" title="请假类型" name="leaveType"></input>
										</td>
								</tr>
								<tr class="rows">
								<td  style="width: 52%;padding-top: 6px">
								<div  class="label"style=" float: left; width: 19%;padding-top: 13px;">开始时间</div>
									<div  class="left"
										style="float: left; width: 73%; border-bottom: solid 1px #949191; position:relative;">
										<input style="font-size:14px" class="Required input-style" mustinput="1" type="text" title="开始时间" id="startTime" name="startTime" datatype="6" onchange="OaLeave.startTimeChange(this);return false;" onclick="fPopUpCalendarDlg(this);return false;" onmousedown="enterKey()" onkeydown="enterKey();" class="datelogo BOTTOM_LINE" id="startTime" onblur="func_uior_valTypeImm(this);" value="${fn:substring(mfOaLeave.startTime,0,4)}-${fn:substring(mfOaLeave.startTime,4,6)}-${fn:substring(mfOaLeave.startTime,6,8)}"></input>
										<i class="i i-rili pointer" onclick="selectrili(this);"></i>
									</div></td>
								<td  style="width: 48%;padding-top: 6px"><div class="label"
										style=" float: left; width: 21%;padding-top: 13px;">结束时间</div>
									<div class="left"
										style="float: left; width: 79%; border-bottom: solid 1px #949191; position:relative;">
										<input style="font-size:14px" class="Required input-style" mustinput="1" type="text" title="结束时间"  id="endTime" name="endTime" datatype="6" onchange="OaLeave.startTimeChange(this);return false;" onclick="fPopUpCalendarDlg(this);return false;" onmousedown="enterKey()" onkeydown="enterKey();" class="datelogo BOTTOM_LINE" id="startTime" onblur="func_uior_valTypeImm(this);" value="${fn:substring(mfOaLeave.endTime,0,4)}-${fn:substring(mfOaLeave.endTime,4,6)}-${fn:substring(endTime.startTime,6,8)}"></input>
										<i class="i i-rili pointer" onclick="selectrili(this);"></i>
									</div></td>
							</tr>
							<tr class="rows">
								<td  style="width: 52%;padding-top: 6px">
								<div class="label" style=" float: left; width: 19%;padding-top: 13px;">请假时长</div>
									<div
										style="float: left; width: 73%; border-bottom: solid 1px #949191;">
										<input  class="Required input-style" mustinput="0" type="text" title="请假时长" name="timeSum" datatype="0" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();" class="BOTTOM_LINE" value="${mfOaLeave.timeSum}"></input>
									</div></td>
								<td  style="width: 48%;padding-top: 6px"><div class="label"
										style=" float: left; width: 21%;padding-top: 13px;">申请时间</div>
									<div
										style="float: left; width: 79%; border-bottom: solid 1px #949191;">
										<input   type="text" title="申请时间" readonly name="createTime" value="${mfOaLeave.createTime}"  onblur="func_uior_valTypeImm(this);checkByKindInfo(this);func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();"></input>
									</div></td>
							</tr>
								<tr>
									<td  colspan="3">
									<div style="height:25px;"></div>
											<textarea name="leaveReason" style="width: 100%;" rows="5"
												id="textarea" onKeyDown="textdown(event)" onKeyUp="textup()"
												onfocus="if(value=='请假理由'){value=''}"
												onblur="if (value ==''){value='请假理由'}">${mfOaLeave.leaveReason}</textarea></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter" style="margin-top: -41px;">
		<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/leave/js/MfOaLeave.js"></script>

<script type="text/javascript">
 $("input[name='startTime']").attr({'id':'startTime'});
 $("input[name='endTime']").attr({'id':'endTime'});
OaLeave.path = "${webPath}";
		$(function() {
			OaLeave.init();
		});
/* laydate({
  elem: '#startTime',
  event: 'click', 
  istime:true,
  issure: true,
  format: 'YYYY/MM/DD hh:mm:ss'    
});
laydate({
  elem: '#endTime',
  event: 'click',    
  istime:true,
  issure: true,
  format: 'YYYY/MM/DD hh:mm:ss'   
});  */	
	
	</script>
</html>
