<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>归档文件归还</title>
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
			
			function returnFile(obj) {
					var flag = submitJsMethod($(obj).get(0), '');
					if (flag) {
						window.top.alert(top.getMessage("CONFIRM_OPERATION","归还文件"), 2, function(){
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
										window.top.alert(top.getMessage("SUCCEED_OPERATION","归还"), 1);
										setTimeout("myclose_click();", 1000);
									} else {
										window.top.alert(data.msg, 0);
									}
								},
								error : function(data) {
									LoadingAnimate.stop();
									alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
								}
							});
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
						<form  method="post" id="formdl_archive_lend02" theme="simple" name="operform" action="${webPath}/archiveLendInfo/returnFileAjax">
							<dhcc:bootstarpTag property="formdl_archive_lend02" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="归还" action="归还" onclick="returnFile('#formdl_archive_lend02');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
