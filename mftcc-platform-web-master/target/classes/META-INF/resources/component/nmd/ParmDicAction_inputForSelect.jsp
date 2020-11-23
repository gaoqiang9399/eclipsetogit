<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-xs-10 col-xs-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="parmDicInsert"  theme="simple" name="operform" action="${webPath}/parmDic/insertForSelectAjax">
							<dhcc:bootstarpTag property="formnmd0004" mode="query"/> 
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
    			<dhcc:thirdButton value="保存" action="保存" onclick="BASE.saveParmDic('#parmDicInsert')"></dhcc:thirdButton>
    		</div>
		</div>
	</body>
</html>
