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
	var cusNo='${cusNo}';
	var appId='${appId}';
	var pactId='${pactId}';
    var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId

    $(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})

		$.ajax({
			url:"${webPath}/archiveInfoMain/getVoucherReceiveAjax?archiveMainNo="+archiveMainNo,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("#bizConfigs").html(data.bizConfigs);
					$("#bizConfigs td[mytitle]:contains('...')").initMytitle();
					$("#template_div").show();
					$(".docType").each(function(){
						$(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/word.png' height='20px' width='20px' style='margin-top:10px;'>");
					});
				} else {
					window.top.alert(data.msg, 0);
				}
			}
		});
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
			}
		});
	}

	//信息确认
	function save(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			//凭证
			var voucherStr = "";
			var voucherOriginalNoStr = "";//原始凭证号
			var voucherOriginalStr = "";//原始凭证名称
			var voucherOtherNoStr = "";//他项凭证号
			var voucherOtherStr = "";//他项凭证名称
			$('.voucherList #tab').find($('input[type=checkbox]:checked')).each(function () {
				var voucherVal = this.value.split('&') [0] ;
				voucherStr=voucherStr+","+voucherVal.split("=")[1];
				//可编辑的字段
				var voucherOriginalNoVal = $(this).parent().parent().find("input[name='voucherOriginalNo']").val();
				voucherOriginalNoStr=voucherOriginalNoStr+","+voucherOriginalNoVal;
				var voucherOriginalVal = $(this).parent().parent().find("input[name='voucherOriginal']").val();
				voucherOriginalStr=voucherOriginalStr+","+voucherOriginalVal;
				/*var voucherOtherNoVal = $(this).parent().parent().find("input[name='voucherOtherNo']").val();
				voucherOtherNoStr=voucherOtherNoStr+","+voucherOtherNoVal;
				var voucherOtherVal = $(this).parent().parent().find("input[name='voucherOther']").val();
				voucherOtherStr=voucherOtherStr+","+voucherOtherVal;*/
			});
			if(voucherStr != ""){
				voucherStr=voucherStr.substr(1);
				voucherOriginalNoStr=voucherOriginalNoStr.substr(1);
				voucherOriginalStr=voucherOriginalStr.substr(1);
				// voucherOtherNoStr=voucherOtherNoStr.substr(1);
				// voucherOtherStr=voucherOtherStr.substr(1);
			}
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				data:{
					ajaxData:JSON.stringify($(obj).serializeArray()),
					vouchers:voucherStr,
					voucherOriginalNoStr:voucherOriginalNoStr,
					voucherOriginalStr:voucherOriginalStr
					// voucherOtherNoStr:voucherOtherNoStr,
					// voucherOtherStr:voucherOtherStr
				},
				url : "${webPath}/archiveInfoMain/confimVoucherOri",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,1);
						if(voucherStr != ""){
							window.location.href = "${webPath}/archiveInfoMain/downloadReceipt?archiveMainNo="+archiveMainNo;
						}else{
							myclose_click();
						}
					}else{
						window.top.alert(data.msg,0);
					}
				}
			});
		}
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
				<form method="post" id="cwBillForm" theme="simple" name="operform" action="${webPath}/archiveInfoMain/confimVoucherOri">
					<dhcc:bootstarpTag property="formarchivemainconfim" mode="query"/>
				</form>
			</div>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>凭证列表</span>
				</div>
				<div class="voucherList content collapse in" id="voucherList" name="voucherList">
					<dhcc:tableTag property="tablearchiveconfimvoucher" paginate="voucherList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<div name="stampTemplateInfo" title="文档信息" class="dynamic-block list-table" style="display: none">
				<div id="template_div" class="template_div" style="display: none;">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>电子文档</span>
							<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bizConfigs">
								<i class="i i-close-up"></i>
								<i class="i i-open-down"></i>
							</button>
							<button type="button" class=" btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.downloadSelTemplateZip();">打包下载</button>
								<%--<button type="button" class="btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.checkAll();">全选</button>--%>
						</div>
						<div id="bizConfigs" class="content collapse in" aria-expanded="true"></div>
						<div id="qrcode"></div>
					</div>
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
