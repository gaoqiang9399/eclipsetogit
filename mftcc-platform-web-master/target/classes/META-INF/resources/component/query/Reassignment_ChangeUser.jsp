<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
</head>
<script type="text/javascript">
	$(function() {
		$('input[name=userId]').popupSelection({
			inline : true, //下拉模式
			multiple : false, // 多选
			searchOn : true,
			items : JSON.parse('${ajaxData}'),
			changeCallback : function(obj, elem) {
				// _templateSourceBack($("input[name=templateSource]").val(),obj.data("text"));
			}
		});
	});

	function updateInfo(obj) {
		var flag = submitJsMethod(obj, '');
		if (flag) {
			var url = webPath+"/reassignment/reAssignAjax";
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
						window.top.alert(data.msg,3);
						top.autoFlag=true;
                        myclose_click();
					} else if (data.flag == "error") {
						if (data.flag !== undefined && data.flag != null && data.flag != "") {
							alert(data.msg, 0);
						} else {
							alert(top.getMessage("FAILED_OPERATION", " "), 0);
						}
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
		}
	}
</script>

<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<form method="post" id="reassignmentForm" theme="simple" name="reassignmentForm" action="">
						<dhcc:bootstarpTag property="reassignment0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="updateInfo(reassignmentForm)"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" onclick="myclose_click()" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
