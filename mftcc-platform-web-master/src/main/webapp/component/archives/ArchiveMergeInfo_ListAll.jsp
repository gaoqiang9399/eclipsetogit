<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" >
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

			function deleteMergeFile(obj, url){
				window.top.alert("确定要删除文件吗？", 2, function(){
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.success){
								var deleteTd = $(obj).parent("td").get(0);
								var tr = $(deleteTd).parent("tr").get(0);
								$(tr).remove();
								window.top.alert("文件删除成功！", 1);
							} else {
								window.top.alert(data.msg, 0);
							}
						}, error:function(){
							window.top.alert("请求链接" + url + "失败！", 0);
						}
					});
				});
			}
			
			function previewMergeFile(obj, url) {
				window.top.loadding();
				notie.alert(4, '文件正在打开请稍等', -1);
				$.ajax({
					url:url,
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.success){
							window.top.dhccModalDialog.open('${webPath}UIplug/PDFjs/web/viewer.html?file=${webPath}file/'
								+ data.viewPath, data.archiveMergeInfo.mergeFileName, function(){}, "90", "90", "400", "300");
						} else {
							window.top.alert(data.msg, 0);
						}
					}, error:function(){
						window.top.alert("请求链接" + url + "失败！", 0);
					}, complete:function(){
						window.top.loaded();
						notie.alert_hide();
					}
				});
			}
			
			function downloadMergeFile(url) {
				top.LoadingAnimate.start();
				var param = url.split("?")[1];
				$.ajax({
					url:webPath+'/archiveMergeInfo/isDownLoadFileExistAjax?' + param,
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.success){
							window.location.href = url;
							setTimeout("top.LoadingAnimate.stop()", 500);
						} else {
							top.LoadingAnimate.stop();
							window.top.alert(data.msg, 0);
						}
					}, error:function(){
						top.LoadingAnimate.stop();
						window.top.alert("请求链接" + url + "失败！", 0);
					}
				});
			}
		</script>
	</head>
<body class="overflowHidden">
   <div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
					<dhcc:thirdTableTag property="tabledl_archive_merge02" paginate="mergeIncludeDetailAndLogList" head="true"></dhcc:thirdTableTag>
				</div>
			</div>
		</div>
    </div>
</body>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</html>