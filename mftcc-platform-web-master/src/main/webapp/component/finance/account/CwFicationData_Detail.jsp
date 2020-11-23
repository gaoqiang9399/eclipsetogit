<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
<body class="overflowHidden bg-white">
	<%-- <div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">辅助核算项数据表</div>
				<form method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formficationdata0001" mode="query" />
				</form>	
			</div>
		</div> --%>
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" theme="simple" name="operform" id="ficationdataDetail_form" action="${webPath}/cwFicationData/updateAjax">
							<dhcc:bootstarpTag property="formficationdata0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter" >
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#ficationdataDetail_form');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
			</div>
		</div>
</body>
<script type="text/javascript">
	function ajaxInsertThis(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");

			var dataParam = JSON.stringify($(obj).serializeArray());

			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {

					if (data.flag == "success") {
						top.addFlag = true;
						window.location.reload();
						/* if (data.htmlStrFlag == "1") {
							top.htmlStrFlag = true;
							top.htmlString = data.htmlStr;
						} */
						myclose_showDialogClick();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {

					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	}
</script>
	
</html>