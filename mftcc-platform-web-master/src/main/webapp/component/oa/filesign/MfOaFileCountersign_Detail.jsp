<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
<script type="text/javascript">
	var aloneFlag = true;
	var dataDocParm = {
		relNo : '${fileNo}',
		docType : "fileSignDoc",
		docTypeName : "会签文件",
		docSplitName : "",
		query : "query",
	};
	
	$(function(){
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$('.hidden-content').addClass('hidden');
	})
</script>
</head>
<body class="overFlowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fileSignInsert" theme="simple" name="operform" action="${webPath}/mfOaFileCountersign/insertAjax">
						<dhcc:bootstarpTag property="formfilesign0002" mode="query" />
					</form>
				</div>
				<div class="row clearfix">
					<div class="col-xs-12 column">
						<div id="doc_div"></div>
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="关闭" action="关闭" onclick="myclose_click();" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>