<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="MfRiskLevelAdjustForm" theme="simple"
						name="operform"
						action="${webPath}/mfRiskLevelAdjust/insertAjax">
						<dhcc:bootstarpTag property="formriskleveladjustbase" mode="query" />
					</form>
				</div>
				<c:if test="${comeFrom == '1' and mfBusFincAppListSize > 0}">
				<div class="arch_sort" style="border: 0px;">
					<div class="dynamic-block" title="客户借据列表" name="MfCusFincList-div" data-sort="14" data-tablename="mf_bus_finc_app">
						<div class="list-table">
							<div class="title">
								<span class="formName"><i class="i i-xing blockDian"></i>客户借据列表</span>
								<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfCusFincList">
									<i class="i i-close-up"></i><i class="i i-open-down"></i>
								</button>
							</div>
							<div disable="true" class="content collapse in" id="MfCusFincList">
								<div id="content" class="table_content" style="height: auto;">
									${tableHtml }
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			</div>
			<!--上传文件-->
			<div class="row clearfix">
				<div class="col-xs-10 col-md-offset-1 column">
					<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
			<dhcc:thirdButton value="保存" action="保存"
				onclick="MfRiskLevelAdjust_Insert.ajaxSave('#MfRiskLevelAdjustForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
				onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	<script type="text/javascript"
		src="${webPath}/component/risk/risklevelmanage/js/MfRiskLevelAdjust_Insert.js"></script>
	<script type="text/javascript">
		var webPath = "${webPath}";
		var id = '${mfRiskLevelAdjust.id}';
		var adjustType = ${adjustType};
		var managePlan =  ${managePlan};
		var comeFrom =  '${comeFrom}';
		var mfBusFincAppListSize =  '${mfBusFincAppListSize}';
		var manageSts =  '${manageSts}';
		var aloneFlag = true;
		var dataDocParm={
			relNo:id,
			docType:"messageDoc",
			docTypeName:"附件资料",
			docSplitName:"附件资料",
			query:''
		};
		$(function(){
			MfRiskLevelAdjust_Insert.init();
		 });
	</script>
</body>
</html>
