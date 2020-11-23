<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/cus/js/ChannelSelfPutoutSet.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript">
		var cusNo="${cusNo}";
		$(function(){
			ChannelSelfPutoutSet.init();
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfCusSelfPutoutConfigApply/insertAjax">
							<dhcc:bootstarpTag property="formcommon" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" typeclass="save" onclick="ChannelSelfPutoutSet.insertAjax('#cusInsertForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
