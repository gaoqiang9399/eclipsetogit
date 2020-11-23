<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String fileSize = PropertiesUtil.getUploadProperty("fileSize");
%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/doc/webUpload/js/uploadForMainPage.js"></script>
<script type="text/javascript" src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
<link id="main-detail" rel="stylesheet" href="${webPath}/themes/factor/css/main-detail${skinSuffix}.css"/>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/webuploader/webuploader.css"/>
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/upload.css?v=${cssJsVersion}"/>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}"/>
<script type="text/javascript" src="${webPath}/UIplug/ViewImg/viewerForList.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css" type="text/css"/>

</head>
<script type="text/javascript">
	var appId='${appId}';
	var cusNo = "${cusNo}";
	var archiveMainNo = '${archiveMainNo}';
	var scNo = '${scNo}';
	var aloneFlag = true;
	var queryFile = '${queryFile}';
	var fileSize =<%=fileSize%>;
	if (fileSize == null) {
		fileSize = 100 * 1024 * 1024;//如果配置文件中没有配置upload.fileSize属性，默认大小是20M
	}
	var collateralId='${collateralId}';
	var dataDocParm={
		relNo:appId,
		docType:"19",
		docTypeName: "反担保资料",
		docSplitNoArr : "[{'docSplitNo':20042315503804210},{'docSplitNo':20042315505645611}]",
		docSplitName: "原始凭证",
		query:''
	};
	var docParm ="query="+queryFile+"&cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询文档信息的url的参数
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
		htmlStr = '<div class="dynamic-block" name="collateralBase' + collateralId + '">' +
				'<div class="form-table">' +
				'<div class="content collapse in" id="collateralBase' + collateralId + '">' +
				'<div id="rotate-body' + collateralId + '\"></div></div></div></div>';
		$(".block-add").after(htmlStr);

		var pledgeDocInfoHtml = '<div class="overflowHidden bg-white"><div class="row clearfix">'
				+ '<div class="upload_body"><ul class="ztree" id="uploadTree' + collateralId + '"></ul></div>'
				+ '<div id="view-imgs"><div class="enlarge-img">'
				+ '<input class="close_btn" type="button" value="" onclick="close_view();"/>'
				+ '<div class="img-tools"><span class="rotateRight">向右旋转</span> <span class="rotateLeft">向左旋转</span>'
				+ '<strong class="title"></strong><div class="view-img last-img"><div></div></div>'
				+ '<div class="view-img next-img"><div></div></div>'
				+ '</div></div></div></div></div>';
		$("#collateralBase" + collateralId).append(pledgeDocInfoHtml);

		dataDocParm = {
			relNo:collateralId,
			docType:"19",
			docTypeName: "反担保资料",
			docSplitNoArr : "[{'docSplitNo':20042315505645611}]",
			docSplitName: "他项凭证",
			query:''
		};
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
			</div>
			<div class="col-md-8 col-md-offset-2 "  id = "pledgeBaseInfo">
				<div class="col-xs-12 column info-block">
					<div class="block-add"></div>

				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="updateCertiInfo('#certiInfoInsert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div> 。
</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
</html>
