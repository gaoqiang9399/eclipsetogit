<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="MfMoveableAccountCheckInfoForm" theme="simple" name="operform" action="${webPath}/mfMoveableAccountCheckInfo/insertAjax">
							<dhcc:bootstarpTag property="formaccountcheck0003" mode="query"/>
						</form>
					</div>
					<div id="unusualTailListdiv" class="bigform_content col_content">
						<div class="title"><h5>异常跟踪</h5></div>
							<div id="unusualTailList" class="table_content padding_0">
								<dhcc:tableTag paginate="mfMoveableUnusualTailInfoList" property="tableunusualTail0001" head="true" />
							</div> 
					</div>
					<!--上传文件-->
					<div class="row clearfix" style="margin-top: 20px;">
						<!-- <div class="title"><h5>对账单资料</h5></div> -->
						<div class="col-xs-12 column" style=" width: 109%;margin-left: -4%;margin-top: 0%;">
							<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
				</div>
	   		</div>
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/movable/js/MfMoveableAccountCheckInfo.js"></script>
	<script type="text/javascript">
		var accountCheckId=$("input[name=accountCheckId]").val();
		var aloneFlag = true;
		var dataDocParm={
			relNo:accountCheckId,
			docType:"accountCheckDoc",
			docTypeName:"",
			docSplitName:"对账单资料",
			query:'query'
		};
		$(function(){
			MfMoveableAccountCheckInfo.init();
		});
	</script>
</html>