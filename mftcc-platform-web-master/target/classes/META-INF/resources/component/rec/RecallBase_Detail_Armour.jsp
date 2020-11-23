<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	    <link rel="stylesheet" href="${webPath}/component/rec/css/RecallBase.css" />
	</head>
	<script type="text/javascript" src="${webPath}/component/rec/js/RecallBase_Insert.js"></script>
	<script type="text/javascript">
		var aloneFlag = true;
		var recallId = '${recallId}';
		var dataDocParm={
			relNo:recallId,
			docType:"lawDoc",
			docTypeName:"催收资料",
			docSplitName:"催收相关资料",
			query:'query',
		};

		var pactId = '${recallBase.conNo}';
		var cusNo = '${recallBase.cusNo}';
		function sysUserCallBack(data){//个人的回调
			$("input[name=mgrNo]").val(data.opNo);
		}
		$(function(){
			ReCallBaseInsert.init();
		});
		$(document.body).height($(window).height());
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="form-div">
						<div class="col-md-10 col-md-offset-1 column margin_top_20">
							<div id="registDiv">
								<div class="bootstarpTag">
									<form method="post" id="registForm" theme="simple" name="edit-form" action="${webPath}/recallBase/insertForSimuAjax">
										<dhcc:bootstarpTag property="formrec0013" mode="query" />
									</form>
										<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="registBtn" class="formRowCenter">
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
