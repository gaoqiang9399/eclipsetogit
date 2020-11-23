<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- 		<script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script> --%>
		<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
		<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
<%-- 		<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" /> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript">
		var fincId,fincSts;
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		$(function(){
			fincId = '${fincId}';
			if(fincSts != '0' && fincSts != '1'){
				//获得审批信息
// 				getSPInfo();
			/* showWkfFlow($("#wj-modeler2"),fincId); */
		    showWkfFlowVertical($("#wj-modeler2"),fincId,"3","putout_approval");
			}else{
				$("#spInfo-block").remove();
			}
			setBlock();
			
		});
		
		
		function setBlock(){
			var clearDiv = '<div style="clear:both;"></div>';
			var htmlStr = "";
			var formInfo =  "<div class='content' style='margin-top:15px;' name='fincinfo'></div>";
				htmlStr = htmlStr + "<div class='block-info'><div class='base-info'>"
						 + formInfo +  "</div></div>";
				$(".block-new-block").before(htmlStr);
				$(".block-new-block").before(clearDiv);
			$.ajax({
				url:webPath+"/mfBusFincApp/getFincApp?fincId="+fincId,
				type:'post',
				dataType:'html',
				success:function(data){
					var $html = $(data);
					var formStr = $html.find(".bigForm_content_form").prop("outerHTML");
					$(".content[name='fincinfo']").html(formStr);
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		};
		
		
		
		function getSPInfo(){
			$.ajax({
				type: "post",
				data:{appNo:"${fincId}"},
				dataType: 'json',
				url:webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					Wkf_zTreeNodes=data.zTreeNodes;
					Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
				}
			});
		}
		</script>
	</head>
<body style="overflow-y:auto;">
    <div class="layout">
		<div id="freewall" style="margin: 8px;height: auto;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:2px; width:100%; height: auto;background-color:#FFFFFF;' data-handle=".handle">
				<div class="info margin_top_025">
					<div class="block-new-block"></div>
					<div style="clear:both;"></div>
					<!-- 附属信息 -->
					<div class="block-info add-block-here approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							 </div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
<%-- 								<iframe src='tech/wkf/processDetail.jsp?appNo=<s:property value="mfBusFincApp.fincId"/>&appWorkflowNo=<s:property value="mfBusFincApp.approveProcessId"/>' marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" id = "processDetailIframe"></iframe> --%>
<!-- 								<ul id="wfTree" class="ztree"> -->
<!-- 			 					</ul> -->
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
</body>
</html>