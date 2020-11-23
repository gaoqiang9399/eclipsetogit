<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
	<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
	<script type="text/javascript" src="${webPath}/component/archives/plugin/viewer.js?v=${cssJsVersion}"></script>
	<link rel="stylesheet" href="${webPath}/component/archives/plugin/viewer.css" type="text/css">
	<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" >
		var appId = "${appId}";
		var docType = "${docType}";
		$(function(){
			/*myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/archiveInfoDetail/findQueryByPage?appId="+appId, //列表数据查询的url
				tableId : "tablearchivequery", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			});*/
		});

		//客户视角
		function getDetailPage(obj,url){
			if(url.substr(0,1)=="/"){
				url =webPath + url;
			}else{
				url =webPath + "/" + url;
			}
			top.openBigForm(url, "客户信息", function () {
				window.location.reload();
			});
		}

		//合同视角
		function getDetailPact(obj,url){
			if(url.substr(0,1)=="/"){
				url =webPath + url;
			}else{
				url =webPath + "/" + url;
			}
			top.openBigForm(url, "合同信息", function () {
				window.location.reload();
			});
		}

		//预览
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
		//下载资料-wsd
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
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
				}
			});
		}
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="scroll-content" style="overflow-y: auto">
	<div class="col-md-10 col-md-offset-1 column margin_top_20">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>资料列表</span>
			</div>
			<div class="docList content collapse in" id="docList" name="docList">
				<dhcc:tableTag property="tablearchivedocdetail" paginate="docManageList" head="true"></dhcc:tableTag>
			</div>
		</div>

		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>合同列表</span>
			</div>
			<div class="templateList content collapse in" id="templateList" name="templateList">
				<dhcc:tableTag property="tablearchivetemplatedetail" paginate="successTemplateList" head="true"></dhcc:tableTag>
			</div>
		</div>

		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>凭证列表</span>
			</div>
			<div class="voucherList content collapse in" id="voucherList" name="voucherList">
				<dhcc:tableTag property="tablearchivevoucherdetail" paginate="voucherList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>
<%--<%@ include file="/component/include/PmsUserFilter.jsp"%>--%>
</body>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
<script type="text/javascript">
	filter_dic = [
		{
			"optName" : "资料类型",
			"parm" : ${docTypeJsonArray},
			"optCode" : "docType",
			"dicType" : "y_n"
		},{
			"optName" : "客户名称",
			"parm" : [],
			"optCode" : "cusName",
			"dicType" : "val"
		},{
			"optName" : "项目名称",
			"parm" : [],
			"optCode" : "appName",
			"dicType" : "val"
		}
	];
</script>
</html>
