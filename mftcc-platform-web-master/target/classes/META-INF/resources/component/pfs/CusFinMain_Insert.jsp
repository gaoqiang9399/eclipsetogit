<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
	<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
	<script type="text/javascript" src="${webPath}/component/pfs/js/cusFinUploadFiles.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css">
	<link rel="stylesheet" href="${webPath}/component/pfs/css/cusFinUploadFiles.css"/>
	<script type="text/javascript">
		var yearThis = '${yearThis}'; //当前年份
	</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="cusFinInsertForm" theme="simple" name="operform" action="${webPath}/cusFinMain/insertAjax">
							<dhcc:bootstarpTag property="formpfs0001" mode="query" />
						</form>
						<table class="table table-bordered" style="margin-top: -21px; font-size:14px;">
							<tr>
								<td class="tdlable right" colspan="1" rowspan="1">
									<label class="control-label ">选择文件</label>
								</td>
								<td class="tdvalue  right" colspan="1" rowspan="1">
									<div class="input-group">
										<div>
											<div id="picker">选择文件</div>
										</div>
										<div id="uploader" class="wu-example">
											<!--用来存放文件信息-->
											<div id="thelist" class="uploader-list"></div>
										</div>
										<div id="finData" class="data-list" style="display: none">
											<input type="hidden" name="finData">
										</div>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass="insertAjax"></dhcc:thirdButton>
			</div>
		</div>
		<div style="display:none;padding-left:10px;width:800px;" id="showInfo-div"></div>
	</div>
</body>
</html>
