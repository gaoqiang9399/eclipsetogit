<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet" href="${webPath}/component/cus/applycusper/css/MfApplyCusPerDetail.css" />
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
		var applyauthId = '${applyauthId}';
</script>
</head>
<body class="bg-white" style="height: 100%;">
	<div class="scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;padding:0px;">
		<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="客户权限申请"  data-sort="14" >
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>客户权限申请</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" >
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in"  style="margin-top: 10px;">
							<form method="post"  theme="simple" name="operform" >
								<dhcc:propertySeeTag property="formapplycusper0001" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort">
				<div id="dimissionApproveHis" class="row clearfix" style="display: none;">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block" class="approval-hist">
							<div class="list-table">
							   <div class="title">
									 <span><i class="i i-xing blockDian"></i>客户权限申请审批历史</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
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
				</div>
			</div>
		</div>
		<div style="height: 10px;"></div>
	</div>
</div>
</body>
<script type="text/javascript">
		$(function(){
			$(".scroll-content").mCustomScrollbar({
				advanced:{
					//滚动条根据内容实时变化
					updateOnContentResize:true
				}
			});
			showApproveHis();
		});
		function backList(){
			myclose_click();
		}
		//展示审批历史
	    function showApproveHis(){
			//获得审批历史信息
			showWkfFlowVertical($("#wj-modeler2"),applyauthId,"38","");
			$("#dimissionApproveHis").show();
		}
</script>
</html>