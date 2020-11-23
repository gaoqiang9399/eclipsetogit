<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoMain_Insert.js"></script>
<style type="text/css">
	.cover {
		cursor: default;
	}
	.infoTilte{
		margin-left: 14px;
		margin-bottom: 20px;
		margin-top:0px;
	}
	.form-margin{
		margin-top: 20px;
		margin-left: 4px;
	}
	.set-disabled{
		color:#aaa;
	}
	.button-bac32B5CB{
		background-color:#32B5CB
	}
	.button-bac32B5CB:hover{
		color: #fff;
		background-color: #018FA7;
	}
	.button-bacFCB865{
		background-color:#FCB865
	}
	.button-bacFCB865:hover{
		background-color : #E9AA64;
		color : #fff;
	}
	.button-bacE14A47{
		background-color:#E14A47
	}
	.button-bacE14A47:hover{
		color : #fff;
		background-color:#C9302C
	}
	.button-his{
		margin-top: 20px;
	}
	.info-collect{
		margin-top: -30px;
	}
</style>
<script type="text/javascript">
	var appId = '${appId}';
	var archivePactStatus = '${archivePactStatus}';
	var aloneFlag = true;//必须
	var dataDocParm = {
		relNo:appId,
		docType:"22",
		docTypeName: "还款凭证",
		docSplitNoArr : "[{'docSplitNo':20042516251810811}]",
		docSplitName: "还款凭证",
		query:''
	};
	$(function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		showDocInfo(appId);
	});

	//展示要件资料
	function showDocInfo (appId) {
		initAloneDocNodes(appId);
		isSupportBase64 = (function () {
			var data = new Image();
			var support = true;
			data.onload = data.onerror = function () {
				if (this.width != 1 || this.height != 1) {
					support = false;
				}
			};
			data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
			return support;
		})();
	};

	//更新
	function updateInfo(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					appId:appId
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
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
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 margin_top_20">
			<div class="bootstarpTag">
				<!-- <div class="form-title"></div> -->
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="formarchivepaperinsert" theme="simple" name="operform" action="${webPath}/archiveInfoVoucherReturn/updateRepayVoucher">
					<dhcc:bootstarpTag property="formarchivevoucherbus" mode="query"/>
				</form>

			</div>
			<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
		</div>

	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="保存" action="保存" onclick="updateInfo('#formarchivepaperinsert');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
