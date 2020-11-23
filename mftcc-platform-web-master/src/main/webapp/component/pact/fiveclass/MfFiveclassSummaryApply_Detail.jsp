<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/component/pact/css/MfPactFiveclass_commonForBatch.css" />
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_commonForBatch.js'></script>
		<script type="text/javascript" src='${webPath}/component/pact/js/MfFiveclassSummaryApply_Detail.js'></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript">
			var path = "${webPath}";
			var ajaxData = ${ajaxData};
			var applyIdnew =  '${applyIdnew}';
			$(function(){
                MfFiveclassSummaryApply_Detail.init();
				showApproveHis();
			 });
			//展示审批历史
		    function showApproveHis(){
				//获得审批历史信息
				showWkfFlowVertical($("#wj-modeler2"),applyIdnew,"30","fiveclass_summary_approval");
			}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">五级分类认定汇总申请表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfFiveclassSummaryApplyForm" theme="simple" name="operform" action="${webPath}/mfFiveclassSummaryApply/insertAjax">
							<dhcc:bootstarpTag property="formfiveclassapplyDetail" mode="query"/>
						</form>
					</div>
					<div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;">
				
					</div>
				</div>
				<div class="arch_sort">
				<div  class="row clearfix" >
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block" class="approval-hist">
							<div class="list-table">
							   <div class="title">
									 <span><i class="i i-xing blockDian"></i>人工认定申请审批历史</span>
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
		</div>
	</body>
</html>
