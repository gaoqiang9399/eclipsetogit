<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/plugin/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/component/archives/plugin/viewer.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoMain_Insert.js"></script>
<script type="text/javascript">
	var isDoc = '${isDoc}';
	var archiveMainNo='${archiveMainNo}';
	var archivePactStatus='${archivePactStatus}';

    $(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
	})

	//预览
	function previewFile(obj, url) {
		window.top.loadding();
//		notie.alert(4, '文件正在打开请稍等', -1);
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
//				notie.alert_hide();
			}
		});
	}

	//信息确认
	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			//获取选中文档
			var paperNoStr = "";
			$('.paperList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var paperVal = this.value.split('&') [0] ;
				paperNoStr=paperNoStr+","+paperVal.split("=")[1];
			});
			if(paperNoStr != ""){
				paperNoStr=paperNoStr.substr(1);
			}
			//合同模板
			var templateStr = "";
			var templateNumStr = "";//份数
			var templateTypeStr = "";//模板类型
			var typeFlag = 0;
			//授信合同
			$('.creditTemplateList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var templateVal = this.value.split('&') [0] ;
				templateStr=templateStr+","+templateVal.split("=")[1];
				//可编辑的字段
				var templateNumVal = $(this).parent().parent().find("input[name='templateNum']").val();
				templateNumStr=templateNumStr+","+templateNumVal;
				var templateTypeVal = $(this).parent().parent().find("[name='templateType']").val();
				if(templateTypeVal == ""){
					typeFlag = 1;
				}
				templateTypeStr=templateTypeStr+","+templateTypeVal;
			});
			//单笔合同
			$('.appTemplateList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var templateVal = this.value.split('&') [0] ;
				templateStr=templateStr+","+templateVal.split("=")[1];
				//可编辑的字段
				var templateNumVal = $(this).parent().parent().find("input[name='templateNum']").val();
				templateNumStr=templateNumStr+","+templateNumVal;
				var templateTypeVal = $(this).parent().parent().find("[name='templateType']").val();
				if(templateTypeVal == ""){
					typeFlag = 1;
				}
				templateTypeStr=templateTypeStr+","+templateTypeVal;
			});
			if(typeFlag == 1){
				window.top.alert("选择的归档的合同，未填写模板类型",0);
				return false;
			}

			if(templateStr != ""){
				templateStr=templateStr.substr(1);
				templateNumStr=templateNumStr.substr(1);
				templateTypeStr=templateTypeStr.substr(1);
			}

			//非系统生成合同
			var extendPactStr = "";
			$('.expandTemplateList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var extendVal = this.value.split('&') [0] ;
				extendPactStr=extendPactStr+","+extendVal.split("=")[1];
			});
			if(extendPactStr != ""){
				extendPactStr=extendPactStr.substr(1);
			}
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ajaxData:JSON.stringify($(obj).serializeArray()),
					paperNos:paperNoStr,
					templateNos:templateStr,
					templateNumStr:templateNumStr,
					templateTypeStr:templateTypeStr,
					extendPactS:extendPactStr
				},
				url : "${webPath}/archiveInfoMain/confimArchiveInfo",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,1);
						myclose_click();
					}else{
						window.top.alert(data.msg,0);
					}
				}
			});
		}
	}

	function extendDetail(obj, url) {
		top.window.openBigForm( webPath + url ,'非系统生成相关合同详情',function() {
		});
	}

	function paperDetail(obj, url) {
		top.window.openBigForm( webPath + url +"&archivePactStatus="+archivePactStatus ,'其他资料详情',function() {
		});
	}

	function pactDetail(obj, url) {
		top.window.openBigForm( webPath + url +"&archivePactStatus="+archivePactStatus ,'非系统生成合同及附件详情',function() {
		});
	}

	function checkAll(){
		$("#quanxuan").find("input[type='checkbox']").each(function(i,n){
			$(n).prop('checked', true);
			/*if ($(n).is(":checked")) {
				$(n).prop('checked', false);
			}else{
				$(n).prop('checked', true);
			}*/
		});
	}

	function closeWindow(){
		myclose_click();
	};
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content" id="quanxuan">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoMain/confimArchiveInfo">
					<dhcc:bootstarpTag property="formarchivemainconfim" mode="query"/>
				</form>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>资料列表</span>
				</div>
				<div class="docList content collapse in" id="docList" name="docList">
					<dhcc:tableTag property="tablearchiveconfimdoclist" paginate="docManageList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>其他资料列表</span>
					<button type="button" class="btn btn-link pull-right  download-btn" onclick="checkAll();">全选</button>
				</div>
				<div class="paperList content collapse in" id="paperList" name="paperList">
					<dhcc:tableTag property="tablearchivepaperconfim" paginate="paperInfoDetailList" head="true"></dhcc:tableTag>
				</div>
			</div>

			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>授信合同列表</span>
				</div>
				<div class="creditTemplateList content collapse in" id="creditTemplateList" name="creditTemplateList">
					<dhcc:tableTag property="tablearchiveconfimtemplatelist" paginate="creditTemplateList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>单笔合同列表</span>
				</div>
				<div class="appTemplateList content collapse in" id="appTemplateList" name="appTemplateList">
					<dhcc:tableTag property="tablearchiveconfimtemplatelist" paginate="appTemplateList" head="true"></dhcc:tableTag>
				</div>
			</div>

			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>非系统生成合同及附件</span>
				</div>
				<div class="expandTemplateList content collapse in" id="expandTemplateList" name="expandTemplateList">
					<dhcc:tableTag property="tablearchiveextendconfim" paginate="pactDetailList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="归档并入库" action="归档并入库" onclick="save('#cwBillForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
