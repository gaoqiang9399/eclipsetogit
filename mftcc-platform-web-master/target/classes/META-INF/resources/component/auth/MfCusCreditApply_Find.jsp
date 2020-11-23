<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>查询</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">信息查询</div>
						
						<form  method="post" theme="simple" name="cms_form" action="${webPath}/mfCusCreditApply/getTabView">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query" />
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:button value="查询" action="search" onclick="checkboxclick();" commit="true" typeclass="btn_101" ></dhcc:button>
				<dhcc:button value="重写" action="search" onclick="cleanbox();" typeclass="button3" ></dhcc:button>
	   		</div>
   		</div>
	</body>
</html>