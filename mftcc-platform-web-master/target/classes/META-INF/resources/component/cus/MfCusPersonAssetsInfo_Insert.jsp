<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusPersonAssets.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
		<script type="text/javascript"  src='${webPath}/component/cus/js/assetsEvalCommon.js?v=${cssJsVersion}'> </script>

	</head>
	<script type="text/javascript">
	var pageView = '';
    var cusNo;
	var flag = '${flag}';
    var projectName = '${projectName}';
    var cusType = '${cusType}';
    var title;
		$(function() {
            MfCusPersonAssets.init();
			cusNo=$("input[name=cusNo]").val();
            title = $(top.window.document).find("#myModalLabel").text();
		});
    //验证页面“请选择”是否全部完成
    function validateAndInsert(){
        saveCusPersonAssetsInfo('#MfCusPersonAssetsInfoInsert','insert');
    }
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">

				<span id="noSign" style="display: none;position: absolute;top: 8px;left: 220px;z-index: 999" >
				<div  >
					<span style="height: 40px; line-height: 40px">
						有无资产信息：有信息： <input type="radio" name="useflag"  class="write"  value="1"  style="cursor: pointer">
					</span>
					<span style="height: 40px; line-height: 40px">无信息：<input   class="write" value="0" type="radio" name="useflag"></span>
				</div>

				</span>
				<div class="col-md-8 col-md-offset-2 column margin_top_20" id="cusAssetsDiv">
					<div class="bootstarpTag">
						<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form  method="post" id="MfCusPersonAssetsInfoInsert" theme="simple" name="operform" action="${webPath}/mfCusPersonAssetsInfo/insertAjax">
								<dhcc:bootstarpTag property="formcusAssetsBase" mode="query"/>
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
		</div>
		<input type = 'hidden' name = 'relNameHidden' />
		<div class="formRowCenter" id="saveBtn">
			<dhcc:pmsTag pmsId="cus-saveAndAdd">
				<dhcc:thirdButton value="保存并新增" action="保存并新增" onclick="saveCusPersonAssetsInfoAndAdd('#MfCusPersonAssetsInfoInsert','insert')"></dhcc:thirdButton>
			</dhcc:pmsTag>
	    	<dhcc:thirdButton value="保存" action="保存" onclick="validateAndInsert();"></dhcc:thirdButton>
			<dhcc:pmsTag pmsId="return-page">
				<dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel" onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
			</dhcc:pmsTag>
	    	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	    </div>
		<div class="formRowCenter" style="display: none" id="saveBtnHouseEval">
			<dhcc:thirdButton value="在线评估" action="在线评估" typeclass="insertAjax" onclick="MfCusPersonAssets.ajaxSave('#houseEvalForm','1');"></dhcc:thirdButton>
			<dhcc:thirdButton value="跳转人工评估" action="跳转人工评估" typeclass="insertAjax" onclick="MfCusPersonAssets.changeRengong();"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusPersonAssets.changeFormDisplay();"></dhcc:thirdButton>
		</div>

		<div class="formRowCenter" style="display: none" id="saveBtnRHouseEval">
			<dhcc:thirdButton value="人工评估" action="人工评估" typeclass="insertAjax" onclick="MfCusPersonAssets.ajaxSave('#houseEvalFormMan','2');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusPersonAssets.changeFormDisplay();"></dhcc:thirdButton>
		</div>
		<script>
            $(function(){
                noSign();
            });
            function noSign(){
				$("#noSign").hide();
            }
            //暂停/启用切换
            $('.write').click(function(){
                var  flag = $(this).val();
                $(this).toggleClass('btn_on').toggleClass('btn_off');
                //var className =document.getElementById("noSign").className;
                if (flag=='0'){
                    $("input[name=assetsValue]").val("0");
                    $("input[name=assetsValue]").attr({ readonly: 'true' });
                    $("input[name=assetsOwner]").attr({ readonly: 'true' });
                    $("select[name=assetsType]").find("option[value = 'A']").show();
                    $("select[name=assetsType]").find("option[value = 'A']").prop("selected","true");
                    $("select[name=assetsType]").find("option[value = '0']").hide();
                    $("select[name=assetsType]").find("option[value = '1']").hide();
                    $("select[name=assetsType]").find("option[value = '2']").hide();
                    $("select[name=assetsType]").find("option[value = '3']").hide();
                    $("select[name=assetsType]").find("option[value = '4']").hide();
                    $("select[name=assetsType]").find("option[value = '5']").hide();
                    $("select[name=assetsType]").find("option[value = '6']").hide();
                    $("select[name=assetsType]").find("option[value = '7']").hide();
                    $("select[name=assetsType]").find("option[value = '8']").hide();
                }else {
                    $("input[name=assetsValue]").val("");
                    $("input[name=assetsValue]").removeAttr("readonly");
                    $("input[name=assetsOwner]").removeAttr("readonly");
                    $("select[name=assetsType]").find("option[value = 'A']").hide();
                    $("select[name=assetsType]").find("option[value = '0']").show();
                    $("select[name=assetsType]").find("option[value = '0']").prop("selected","true");
                    $("select[name=assetsType]").find("option[value = '1']").show();
                    $("select[name=assetsType]").find("option[value = '2']").show();
                    $("select[name=assetsType]").find("option[value = '3']").show();
                    $("select[name=assetsType]").find("option[value = '4']").show();
                    $("select[name=assetsType]").find("option[value = '5']").show();
                    $("select[name=assetsType]").find("option[value = '6']").show();
                    $("select[name=assetsType]").find("option[value = '7']").show();
                    $("select[name=assetsType]").find("option[value = '8']").show();
                }
            });
		</script>
	</body>
</html>
