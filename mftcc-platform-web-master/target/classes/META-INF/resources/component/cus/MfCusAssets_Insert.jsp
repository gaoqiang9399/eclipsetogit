<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusAssets.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript"  src='${webPath}/component/cus/js/assetsEvalCommon.js?v=${cssJsVersion}'> </script>
	</head>
	<script type="text/javascript">
	var formId = '${formId}';
    var title ;
	$(function(){
// 		$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					theme:"minimal-dark",
// 					updateOnContentResize:true
// 				}
// 			});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
			var assAreaValLabel=$("input[name=assAreaVal]").parents(".rows").find(".form-label");
			var assAreaValLabelText =$(assAreaValLabel).text();
			$(assAreaValLabel).empty().append("<span class='required'>*</span>"+assAreaValLabelText);
			var assAreaLabel=$("input[name=assArea]").parents(".rows").find(".form-label");
			var assAreaLabelText =$(assAreaLabel).text();
			$(assAreaLabel).empty().append("<span class='required'>*</span>"+assAreaLabelText);
			//$("input[name=assAreaVal]").attr("mustinput","1");
			//$("input[name=assArea]").attr("mustinput","1");
       		 title = $(top.window.document).find("#myModalLabel").text();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20" id="cusAssetsDiv">
					<div class="bootstarpTag">
						<form  method="post" id="cusAssetsInsert" theme="simple" name="operform" action="${webPath}/mfCusAssets/insertAjax">
							<dhcc:bootstarpTag property="formcusassets00003" mode="query"/>
						</form>
					</div>
				</div>

				<div class="col-md-10 col-md-offset-1 column margin_top_20" style="display: none" id="houseEvalDiv">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post"  theme="simple" id="houseEvalForm" name="operform" action="${webPath}/mfHouseEval/evalHouseInfo">
							<dhcc:bootstarpTag property="formHouseEval" mode="query"/>
						</form>
					</div>
				</div>

				<div class="col-md-8 col-md-offset-2 column margin_top_20" style="display: none" id="houseEvalRDiv">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post"  theme="simple" id="houseEvalFormMan" name="operform" action="${webPath}/mfHouseEval/evalHouseInfo">
							<dhcc:bootstarpTag property="formHouseEvalMan" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter" id="saveBtn">
	  			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusAssetsInsert');"></dhcc:thirdButton>
	  			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		  	</div>
			<div class="formRowCenter" style="display: none" id="saveBtnHouseEval">
				<dhcc:thirdButton value="在线评估" action="在线评估" typeclass="insertAjax" onclick="MfCusAssets.ajaxSave('#houseEvalForm','1');"></dhcc:thirdButton>
				<dhcc:thirdButton value="跳转人工评估" action="跳转人工评估" typeclass="insertAjax" onclick="MfCusAssets.changeRengong();"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusAssets.changeFormDisplay();"></dhcc:thirdButton>
			</div>

			<div class="formRowCenter" style="display: none" id="saveBtnRHouseEval">
				<dhcc:thirdButton value="人工评估" action="人工评估" typeclass="insertAjax" onclick="MfCusAssets.ajaxSave('#houseEvalFormMan','2');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusAssets.changeFormDisplay();"></dhcc:thirdButton>
			</div>
	  	</div>
	</body>
</html>
