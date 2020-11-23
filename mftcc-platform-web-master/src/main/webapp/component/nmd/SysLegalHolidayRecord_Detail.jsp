<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" theme="simple" id="editWordForm" name="operform" action="${webPath}/sysLegalHolidayRecord/updateAjax">
						<dhcc:bootstarpTag property="formholiday0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="toUpdateThis('#editWordForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="back()"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">
		function back(){
			$(top.window.document).find("#showDialog .close").click();
		}
		function toUpdateThis(dom){//点击修改之后，供list页面回调
			LoadingAnimate.start();
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
				LoadingAnimate.stop();
					if (data.flag == "success") {
						top.detailFlag=true;
						window.top.alert(data.msg, 1);
						 $(top.window.document).find("#showDialog .close").click();
					}else{
						window.top.alert(data.msg, 0);
					}
				},
				error:function() {
				LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	</script>
</html>