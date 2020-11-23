<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情展示</title>
		<script type="text/javascript">
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});
			});
			var cusNo = '${cusNo}';
			var appId = '${mfBusDocSupplement.appId}';
			var pactId = '${pactId}';
			var docParm = "cusNo="+cusNo+"&appId="+appId+"&pactId="+pactId+"&query=''";
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips"></div>
						<form method="post" id="inputCommonForm" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formmfBusDocSupplementDetail" mode="query" />
						</form>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
				</div>
				
			</div>
			<%-- <div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" onclick="insertTuning('#inputCommonForm')"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfTuningReport_Detail.closeMyOnly()"></dhcc:thirdButton>
			</div> --%>
		</div>
	</body>
</html>