<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>标签配置</title>
		<style>
			.option-div{
				margin-right:0px;
			}
		</style>
		<script type="text/javascript" src="${webPath}/component/model/js/MfTemplateTagSet_baseTagSet.js"></script>
		<script type="text/javascript">
			var itemList = '${ajaxData}';
			var templateNo ='${templateNo}';
			itemList = JSON.parse(itemList);
			var groupFlag = '${groupFlag}';
			groupFlag = JSON.parse(groupFlag);
		    $(function(){
		    	TagItemList.init();
		    });
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div id="itemsDiv" class="margin_top_25">
					
				</div>
			</div>
			<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存"
						onclick="TagItemList.updateTagSet();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>