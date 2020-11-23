<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
	<link type="text/css" rel="stylesheet" href="${webPath}/component/examine/css/ExamineApply${skinSuffix}.css" />
	<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
	<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
	<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
	<script type="text/javascript" src='${webPath}/component/pfs/js/CusFinMain_List.js?v=${cssJsVersion}'></script>
	<script type="text/javascript" src='${webPath}/component/importexcel/js/MfCusImportExcel_upload.js?v=${cssJsVersion}'></script>
	<script type="text/javascript">
        var webPath = "${webPath}";
        var importStage = "";//历史数据导入阶段
		var hisId = "${hisId}";
		var flag = "${flag}";
        $(function(){
            if (flag=="add"){
                MfCusImportExcel_upload.initUpload();
			}else if (flag=="detail"){
                MfCusImportExcel_upload.initDetailUpload();
			}
        });
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="eval-content" style="">
	<div class="showprogress">
		<!--进度-->
		<ul>
			<li class="selected" name="uploadFileLi"><span class="span_btn">
						<span class="lable color_theme">文件导入<i class="i i-jiantou2"></i></span>
				</span></li>
			<li name="checkImportDataLi"><span class="span_btn"> <span
					class="lable color_theme">数据校验<i class="i i-jiantou2"></i></span>
				</span></li>
			<li name="uploadSuccessLi"><span class="span_btn"> <span
					class="lable color_theme">数据导入<i class="i i-jiantou2"></i></span>
				</span></li>
		</ul>
	</div>
	<div class="content_show">
		<ul class="content_ul" style="display: block;">
			<li>
				<div id="uploadFileLi-div" class="li_content_type container form-container">
					<div class="scroll-content" id="chosefin">
						<div class="col-xs-6 col-xs-offset-3 column" style="margin-top: 100px;">
							<div id="uploader" class="wu-example">
								<div id="thelist" class="cb-upload-div input-group input-group-lg">
									<input name="uploadFile" readonly="readonly" type="text" class="form-control">
									<span id="picker" readonly="readonly" class="btn btn-primary pointer" style="height: 46px;font-size:17px;line-height: 28px;">上传...</span>
									<div class="hidden" id="picker-outer"></div>
								</div>
							</div>
						</div>
					</div>

				</div>
			</li>
			<li>
				<div id="checkImportDataLi-div" class="li_content_type eval-app container form-container" style="display: none">
					<div id="importCensusList" class="table_content">

					</div>
				</div>
			</li>
			<li>
				<div id="uploadSuccessLi-div" class="li_content_type container form-container" style="display: none">
					<div id="importSuccessList" class="table_content">

					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<div class="formRowCenter" style="bottom:40px\9;">
	<dhcc:thirdButton value="下一步" action="下一步" typeclass="next" onclick="MfCusImportExcel_upload.nextSubmit('#ecamHisInsertForm')"></dhcc:thirdButton>
	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
</div>
</body>
</html>
