<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer_fundForm.js'> </script>
		<script type="text/javascript">
		var cusType;
		var mobilemsg = "";
		var postalCodemsg = '';
		$(function(){
			cusFund.init("insert");
		})
		function showcheckinfo(obj){
			cusFund.showcheckinfo(obj);
		}
		function controlMax(){
			cusFund.controlMax();
		}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">客户登记</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfCusCustomer/insertForBusAjax">
							<dhcc:bootstarpTag property="formcus00002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
	   		<input type="hidden" id="type" value="1"></input>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="cusFund.cusInfoSave('#cusInsertForm','insert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cusFund.cancelInsert();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
