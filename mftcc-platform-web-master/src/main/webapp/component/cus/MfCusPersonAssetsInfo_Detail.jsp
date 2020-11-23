<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusPersonAssets.js'> </script>
	</head>
	<script type="text/javascript">
	var flag = '${flag}';
		$(function() {
			init();
			//资产类型选择组件
	       /* $("select[name=assetsType]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false//单选
			}); */
			//$("select[name=assetsType]").setAttribute("readOnly",true); 
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
					<form  method="post" id="MfCusPersonAssetsInfoupdate" theme="simple" name="operform" action="${webPath}/mfCusPersonAssetsInfo/updateAjax">
						<dhcc:bootstarpTag property="formcuspersassets0001" mode="query"/>
					</form>
				</div>
			</div>
		</div>	
		
		<div class="formRowCenter">
	    	<dhcc:thirdButton value="保存" action="保存" onclick="saveCusPersonAssetsInfo('#MfCusPersonAssetsInfoupdate','update');"></dhcc:thirdButton>
	    	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	    </div>
	    </div>
	</body>
</html>