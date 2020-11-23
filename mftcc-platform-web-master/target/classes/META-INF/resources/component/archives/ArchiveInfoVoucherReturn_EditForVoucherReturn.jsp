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
	var collateralId = '${collateralId}';
	var aloneFlag = true;//必须
	var dataDocParm = {
		relNo:collateralId,
		docType:"19",
		docTypeName: "他项凭证",
		docSplitNoArr : "[{'docSplitNo':20042315505645611}]",
		docSplitName: "他项凭证",
		query:''
	};
	$(function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		initPlageDoc();
	});

	function initPlageDoc(){
		initAloneDocNodes(collateralId);
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
	}

	function updateCertiInfo(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					appId:appId,
					archiveMainNo:archiveMainNo,
					tableId:"tablearchivecerticonfim"
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.addReceInfoFlag = true;
						var receAccountList=$('#bigFormShowiframe', parent.document).contents().find("#receAccountList");
						if(receAccountList.length<=0){
							receAccountList=$('#taskShowDialogiframe', parent.document).contents().find("#receAccountList");
						}
						receAccountList.html(data.tableData);
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				}
			});
		}
	}

	//表单信息提示
	function func_uior_addTips(obj,msg){
		var $this =$(obj);
		var val = $this.val();
		if ($this.hasClass("Required")) {
			$this.removeClass("Required");
		}
		if($this.parent().find(".Required-font").length>0){
			$this.parent().find(".Required-font").remove();
		}
		var $label = $('<div class="error required">'+msg+'</div>');
		$label.appendTo($this.parent());
		$this.addClass("Required");
		$this.one("focus.addTips", function(){
			$label.remove();
		});
	};
	function validateDupCertiNo(){
		$.ajax({
			url:webPath+"/certiInfo/validateDupCertiNoAjax",
			data : {
				ajaxData : 	$("input[name=certiNo]").val()
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if(data.result == "0"){
					func_uior_addTips($("input[name=certiNo]"),"权属证书编号已存在");
					$("input[name=certiNo]").val("");
				}
			}
		});
	};
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="certiInfoInsert" theme="simple" name="operform" action="${webPath}/archiveInfoMain/updateCertiForConfim">
						<dhcc:bootstarpTag property="formarchivecertiedit" mode="query"/>
					</form>
				</div>

				<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
			</div>
		</div>
		<div class="formRowCenter">
			<%--<dhcc:thirdButton value="保存" action="保存" onclick="updateCertiInfo('#certiInfoInsert');"></dhcc:thirdButton>--%>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div> 。
</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</html>
