<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<%-- <body>
		<div class="bigform_content">
			<form method="post" theme="simple" name="operform" action="${webPath}/mfOaBankNote/insert">
				<dhcc:bigFormTag property="formbanknote0001" mode="query"/>
				<div class="formRow">
	    			<dhcc:thirdButton value="提交" action="提交" commit="true"></dhcc:thirdButton>
	    			<dhcc:thirdButton value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form)"></dhcc:thirdButton>
	    		</div>
			</form>	
		</div>
	</body> --%>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="banknoteForm" theme="simple" name="operform" action="${webPath}/mfOaBankNote/insertAjax">
							<dhcc:bootstarpTag property="formbanknote0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
	   		<input type="hidden" id="type" value="1"></input>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" ></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/oa/banknote/js/MfOaBankNote_insert.js"></script>
<script type="text/javascript">
	// 接收传参等
	OaBankNoteInsert.path = "${webPath}";
	$(function() {
		OaBankNoteInsert.init();
	});
</script>
</html>
