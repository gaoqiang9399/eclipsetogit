<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
<%-- 		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusListedInfo.js'> </script> --%>
		<style>
			.formRowCenter{
				position:fixed;
				bottom:0;
				width:100%;
				margin:0px 0px;
			}
		</style>
		<script type="text/javascript">
			$(function() {
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="MfCusListedInfoInsert" method="post" theme="simple" name="operform" action="${webPath}/mfCusListedInfo/insertAjax">
						<dhcc:bootstarpTag property="formcuslistinfo0002" mode="query"/>
					</form>
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
		    	<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#MfCusListedInfoInsert')"></dhcc:thirdButton>
		    	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		    </div>
	    </div>
	</body>
</html>
