<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>编辑提交</title>
		<script type="text/javascript" src="${webPath}/component/checkoff/js/MfBusCheckoffs_List.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
			
			<c:if test='${checkOffStatus=="1"}'>
			<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
				<form method="post" id="MfBusCheckoffsForm" theme="simple" name="operform" action="${webPath}/mfBusCheckoffs/insertAjax">
								
								<dhcc:bootstarpTag property="formcheckoffadd" mode="query"/>
							
						</form>
						</div>
						</div>

				</c:if>
				<c:if test='${checkOffStatus!="1"}'>
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
					<form method="post" id="MfBusCheckoffsForm" theme="simple" name="operform" action="${webPath}/mfBusCheckoffs/insertAjax">
								
								<dhcc:bootstarpTag property="formcheckoffadd" mode="query"/>
							
						</form>
						</div>
                    <!-- 审批历史 -->
                    <div id="checkoffs-His-block" class="approval-hist">
                        <div class="list-table">
                            <div class="title">
                                <span><i class="i i-xing blockDian"></i>核销审批历史</span>
                                <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
                                    <i class='i i-close-up'></i>
                                    <i class='i i-open-down'></i>
                                </button>
                            </div>
                            <div class="content margin_left_15 collapse in " id="spInfo-div">
                                <div class="approval-process">
                                    <div id="modeler1" class="modeler">
                                        <ul id="wj-modeler2" class="wj-modeler" isApp = "false">
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
						</div>
						
				</c:if>


	   		</div>


			<div class="formRowCenter" id="waitApproveSubmit" style="display:none;">
				<!-- 等待提交申请页面 -->

<%-- 	   			<dhcc:thirdButton value="保存" action="保存" onclick="checkOffs.updCheckOffSubmit('#MfBusCheckoffsForm')"></dhcc:thirdButton> --%>
	   			<dhcc:thirdButton value="保存" action="保存" onclick="checkOffs.updCheckOffSubmit('#MfBusCheckoffsForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="删除" action="删除" typeclass="cancel" onclick="checkOffs.delCheckOff('${checkoffId}');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
			<div class="formRowCenter" id="watiExeCheckOff" style="display:none;">
				<!-- 等待核销执行页面 -->

	   			<dhcc:thirdButton value="核销" action="核销" onclick="checkOffs.exeCheckOff('${checkoffId}')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   		
			<div class="formRowCenter" id="watiExeTakeback" style="display:none;">
				<!-- 等待核销收回页面 -->

	   			<dhcc:thirdButton value="收回" action="收回" onclick="checkOffs.checkOffTakeback('${checkoffId}')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
			<div class="formRowCenter" id="detailShowPge" style="display:none;">
				<!-- 只看详情页面-->
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
		<script type="text/javascript">
		var tmpprcp=$('input[name=checkoffPrcp]').val();
			var tmpInitst=$('input[name=checkoffIntst]').val();
			var tmpFee=$('input[name=checkoffFee]').val();
			var tmpSum=$('input[name=checkoffSum]').val();
			
		//1-未提交 2-审批中 3-待核销 4-已核销 5-已收回 6-已否决
			if("1"=='${checkOffStatus}'){
				$("#waitApproveSubmit").show();
				$("#watiExeCheckOff").hide();
				$("#watiExeTakeback").hide();
				$("#detailShowPge").hide();
			}else
			if("3"=='${checkOffStatus}'){
				$("#waitApproveSubmit").hide();
				$("#watiExeCheckOff").show();
				$("#watiExeTakeback").hide();
				$("#detailShowPge").hide();
			}else
			if("4"=='${checkOffStatus}'){
				$("#waitApproveSubmit").hide();
				$("#watiExeCheckOff").hide();
				$("#watiExeTakeback").show();
				$("#detailShowPge").hide();
			}else{
				$("#waitApproveSubmit").hide();
				$("#watiExeCheckOff").hide();
				$("#watiExeTakeback").hide();
				$("#detailShowPge").show();
			}
	
			$(function(){
                //滚动条
                myCustomScrollbarForForm({
                    obj:".scroll-content",
                    advanced : {
                        updateOnContentResize : true
                    }
                });

				var curType1=$('select[name=checkoffType]').val();
				var checkoffId = $("input[name=checkoffId]").val();
				checkOffs.checkOffTypeChg(curType1);//初始化核销类型
		
			//意见类型新版选择组件
				$('select[name=checkoffType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback : function (obj, elem) {
						checkOffs.checkOffTypeChg($(obj).val());
					}
				}); 
				
				$('select[name=checkoffMethod]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback : function (obj, elem) {
					}

				});

                showWkfFlowVertical($("#wj-modeler2"), checkoffId, "3","ver_approval");
                $("#leaveApproveHis").show();
			})
			
		</script>
	</body>
</html>
