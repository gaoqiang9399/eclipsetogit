<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/archives/plugin/viewer.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/component/archives/plugin/viewer.css" type="text/css">
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
			
			function previewFile(obj, url) {
				window.top.loadding();
				notie.alert(4, '文件正在打开请稍等', -1);
				$.ajax({
					url:url,
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.success){
							var archiveDocAttribute = data.archiveInfoDetail.archiveDocAttribute;
							if (!archiveDocAttribute || archiveDocAttribute !== '') {
								if (archiveDocAttribute == '01') {
<%-- 									window.top.dhccModalDialog.open('${webPath}UIplug/PDFjs/web/viewer.html?file=${webPath}file/' --%>
// 										+ data.viewPath, data.archiveInfoDetail.docName, function(){}, "90", "90", "400", "300");
									var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
									if(isWin){
										var poCntObj = $.parseJSON(data.poCnt);
										mfPageOffice.openPageOffice(poCntObj);
									}else{
										window.top.alert("当前操作系统不支持在线预览，请下载到本地查阅！",0);
									}
								
								} else if (archiveDocAttribute == '02') {
									$("#img" + data.archiveInfoDetail.archiveDetailNo).remove();
									var $image = $("<img>");
									$image.attr("class", "image");
									$image.attr("id", "img" + data.archiveInfoDetail.archiveDetailNo);
									$image.attr("img_id", data.archiveInfoDetail.archiveDetailNo);
									$image.attr("alt", data.archiveInfoDetail.docName);
									$image.attr("src", webPath+"/archiveInfoDetail/getImageByteArrayOutputStream?archiveDetailNo=" + data.archiveInfoDetail.archiveDetailNo);
									$image.hide();
									$("body").append($image);
									
									$('.image').viewer();
									$image.click();
								} else if (archiveDocAttribute == '03' || archiveDocAttribute == '04') {
									window.top.dhccModalDialog.open("${webPath}component/archives/plugin/video/viewer.jsp" + url.substring(url.indexOf('?')), data.archiveInfoDetail.docName, function(){}, "90", "90", "400", "300");
								} else {
									window.top.alert("无法预览该文件！", 0);
								}
							} else {
								window.top.alert("无法预览该文件！", 0);
							}
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

			function returnFile(obj, url){
				window.parent.openBigForm(url, "归档文件归还");
			}
		</script>
	</head>
<body class="overflowHidden">
   <div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
					<dhcc:thirdTableTag property="tabledl_archive_lend02" paginate="lendIncludeDetailAndLogList" head="true"></dhcc:thirdTableTag>
				</div>
			</div>
		</div>
    </div>
</body>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
</html>