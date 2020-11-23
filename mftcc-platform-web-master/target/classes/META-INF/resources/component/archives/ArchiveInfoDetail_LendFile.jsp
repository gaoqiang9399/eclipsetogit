<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>归档文件借阅</title>
		<script type="text/javascript">
			$(function() {
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced : {
// 						theme : "minimal-dark",
// 						updateOnContentResize : true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
		
			/* function myclose(){
				$(top.window.document).find("#showDialog .close").click();
			} */
			
			function lendFile(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var url = $(obj).attr("action");
					var dataParm = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					jQuery.ajax({
						url : url,
						data : {
							ajaxData : dataParm
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							LoadingAnimate.stop();
							if (data.success) {
								top.refresh = true;
								window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT", "借阅信息"), 1);
								setTimeout("myclose_click();", 1000);
							} else {
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
						}
					});
				}
			};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="formdl_archive_lend01" theme="simple" name="operform" action="${webPath}/archiveInfoDetail/lendFileAjax">
							<dhcc:bootstarpTag property="formdl_archive_lend01" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="lendFile('#formdl_archive_lend01');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
