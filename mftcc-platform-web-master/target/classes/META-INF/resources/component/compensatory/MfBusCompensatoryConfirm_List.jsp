<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/compensatory/js/MfBusCompensatoryConfirm_List.js'></script>
		<script type="text/javascript">
			var compensatoryId = '${compensatoryId}';
			$(function(){
				MfBusCompensatoryConfirmList.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="mfBusCompensatoryConfirmForm" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formcompensatoryConfirm0001" mode="query"/>
						</form>
					</div>
					<div class="list-table" id="mfBusCompensatoryDocListDiv">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>资料清单</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusCompensatoryDocList">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="mfBusCompensatoryDocList" name="mfBusCompensatoryDocList">
							<dhcc:tableTag property="tablecompensatorydoc" paginate="mfBusCompensatoryDocList" head="true"></dhcc:tableTag>
						</div>
					</div>
					<!-- 要件信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>要件上传</span>
								<iframe id="mfBusCompensatoryUploadIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusCompensatoryUpload.jsp?query=${query }&compensatoryId=${mfBusCompensatoryApply.compensatoryId }"></iframe>
							</div>
						</div>
					</div>
					<div class="bigform_content col_content" style="display: none">
						<div id="mfBusCompensatoryApplyList" class="table_content">
							<dhcc:tableTag paginate="mfBusCompensatoryApplyDetailList" property="tablecompensatoryConfirm0001" head="true" />
						</div>
					</div>
				</div>
	   		</div>
	   		
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="确认" action="确认" typeclass="saveButton" onclick="MfBusCompensatoryConfirmList.ajaxInsert('#mfBusCompensatoryConfirmForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>