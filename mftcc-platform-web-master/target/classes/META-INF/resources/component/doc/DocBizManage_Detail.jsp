<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" theme="simple" name="operform" action="${webPath}/docBizManage/insert">
							<dhcc:bootstarpTag property="formdoc0009" mode="query" />
						</form>
					</div>
				</div>
			</div>	
				<div class="formRowCenter">
		    			<dhcc:thirdButton value="提交" action="提交" commit="true"></dhcc:thirdButton>
		    			<dhcc:thirdButton value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form)"></dhcc:thirdButton>
		    	</div>
		</div>

	</body>
</html>