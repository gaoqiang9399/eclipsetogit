<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusPersonDebtInfo.js'> </script>
	</head>
	<script type="text/javascript">
		$(function() {
			CusPersonDebtInfoInsert.init();
				//担保选择组件
        $("select[name=vouType]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false//单选
		});
		//还款选择组件
        $("select[name=repayType]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false//单选
		});
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
<!-- 						<div class="form-title">负债详情</div> -->
						
					<form  method="post" id="MfCusPersonDebtInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusPersonDebtInfo/updateAjax">
						<dhcc:bootstarpTag property="formcuspersdebt0001" mode="query"/>
					</form>
				</div>
			</div>
		</div>	
		
		<div class="formRowCenter">
	    	<dhcc:thirdButton value="保存" action="保存" onclick="CusPersonDebtInfoInsert.saveCusPersonDebtInfo('#MfCusPersonDebtInfoUpdate','update');"></dhcc:thirdButton>
	    	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	    </div>
	    </div>
	</body>
</html>