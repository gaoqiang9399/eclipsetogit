<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src="${webPath}/component/finance/othreport/js/comItemZoom.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript">
		function getCowItemInfoArtDialog(cowItemInfo){
			console.log(cowItemInfo);
			$("input[name=accHrt]").val(cowItemInfo.accHrt);
			$("input[name=accNo]").val(cowItemInfo.accNo);
		
		};
		
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" theme="simple" name="operform" id="relationDetail_form" action="${webPath}/cwRelation/updateAjax">
						<dhcc:bootstarpTag property="formcwrelation0002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#relationDetail_form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {

		var accNohtml = $("input[name='accNo']").parent();
		var value = $("input[name='accNo']").val();
		$("input[name='accNo']").parent().html("");
		accNohtml
				.append("<input class='form-control Required' type=\"text\" title=\"科目名称\" id=\"accNo\" name=\"accNo\" value=\""
						+ value
						+ "\" datatype=\"0\" mustinput=\"1\" maxlength=\"30\" onblur=\"func_uior_valTypeImm(this);\" onmousedown=\"enterKey()\" onclick=\"autoComPleter(this, '2', comPlateBack)\" onkeydown=\"enterKey();\">");
		accNohtml
				.append("<span class=\"input-group-addon\"><i class=\"i i-fangdajing pointer comitem_select\"></i></span>");

		//科目弹窗
		$('.comitem_select').on('click', function() {
			openComItemDialog('2', function(data) {
				if (data) {
					$("input[name='accNo']").val(data.showName);
				}
			});
		})

	});
	function comPlateBack(selected){
		$('#accNo').val(selected.accNo+"/"+selected.accName);
	}
	function myclose_showDialog() {
		myclose_click();
	}

	function ajaxInsertThis(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {

					if (data.flag == "success") {
						top.addFlag = true;
						//window.location.reload();
						myclose_showDialog();
						//myclose_showDialogClick();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {

					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	}
</script>
</html>