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
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			$(function() {
				myCustomScrollbar({
					obj : "#content",//页面内容绑定的id
					url : webPath+"/archiveInfoDetail/findByPageAjax",//列表数据查询的url
					tableId : "tabledl_archive_detail01",//列表数据查询的table编号
					tableType : "thirdTableTag",//table所需解析标签的种类
					pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
				});
			});
			
			function archiveInfoMainList() {
				window.location.href=webPath+"/archiveInfoMain/getListPage";
			}
			
			function archiveInfoDetailList() {
				window.location.href=webPath+"/archiveInfoDetail/getListPage";
			}
			
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
						alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
					}, complete:function(){
						window.top.loaded();
						notie.alert_hide();
					}
				});
			}
			
			function downloadFile(url) {
				top.LoadingAnimate.start();
				var param = url.split("?")[1];
				$.ajax({
					url:webPath+'/archiveInfoDetail/isDownLoadFileExistAjax?' + param,
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
			
			function showDetail(obj, url) {
				window.parent.openBigForm(url, "归档明细信息", function() {});
			}
			
			function recoverFile(obj, url){
				window.top.alert(top.getMessage("CONFIRM_OPERATION","恢复已经删除的文件"), 2, function(){
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.success){
								var $newObj = $("<span class='listOpStyle'>恢复</span>");
								$(obj).replaceWith($newObj);
								window.top.alert(top.getMessage("SUCCEED_OPERATION", "归档文件恢复"), 1);
							} else {
								window.top.alert(data.msg, 0);
							}
						}, error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
						}
					});
				});
			}
		</script>
	</head>
<body class="overflowHidden">
   <div class="container">
   		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="archiveInfoMainList();">归档合同</button>
	  					<button type="button" class="btn btn-primary" onclick="archiveInfoDetailList();">归档明细</button>
					</div>
	  				<div class="col-md-8 text-center">
	  					<span class="top-title">归档明细</span>
	  				</div>
	  				<div class="col-md-2">
	  				</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=文件名称"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content">
				</div>
			</div>
		</div>
    </div>
    <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	
	filter_dic = [ {
		"optCode" : "docSize",
		"optName" : "文件大小(kb)",
		"dicType" : "num"
	}, {
		"optCode" : "updateDate",
		"optName" : "更新日期",
		"dicType" : "date"
	} ];
</script>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</html>