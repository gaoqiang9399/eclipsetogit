<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String fileSize = PropertiesUtil.getUploadProperty("fileSize");
%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
</head>
<script type="text/javascript">
	var appId='${appId}';
	$(function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	});

	function validateDupCertiNo(){
		$.ajax({
			url:webPath+"/certiInfo/validateDupCertiNoAjax",
			data : {
				ajaxData : 	$("input[name=certiNo]").val()
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.result == "0"){
					func_uior_addTips($("input[name=certiNo]"),"权属证书编号已存在");
					$("input[name=certiNo]").val("");
				}
			},
			error : function(data) {
				$("input[name=certiNo]").val("");
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};

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
					tableId:"tablearchivecertivoucher"
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						/*top.addReceInfoFlag = true;
						var receAccountList=$('#bigFormShowiframe', parent.document).contents().find("#receAccountList");
						if(receAccountList.length<=0){
							receAccountList=$('#taskShowDialogiframe', parent.document).contents().find("#receAccountList");
						}
						receAccountList.html(data.tableData);*/
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
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
</script>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="certiInfoInsert" theme="simple" name="operform" action="${webPath}/archiveInfoMain/updateForListAjax">
						<dhcc:bootstarpTag property="formarchivecertiedit" mode="query"/>
					</form>
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
