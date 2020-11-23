<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	<script type="text/javascript">
		$(function() {
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced:{
					updateOnContentResize:true
				}
			});
			changeBillType();
		});			
	function changeBillType(){
		var billType = $("select[name=billType]").val();
		$.ajax({
			url:webPath+"/pssBillnoConf/getBillNoFormAjax",
			data:{billType:billType},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				$("#billNoConf").empty().html(data.formHtml);
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	function setDefaultDigit(){
		var noStart = $("input[name=noStart]").val();
		if(noStart==""){
			$("input[name=noStart]").val("1");
		}
	}	
	function saveConf(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {ajaxData : dataParam},
				type : "POST",
				dataType : "json",
				beforeSend : function() {},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
					    alert(top.getMessage("SUCCEED_OPERATION") ,1);
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	</script>
</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-title">单号编码规则</div>
		           		<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="billNoConf" theme="simple" name="operform" action="${webPath}/pssBillnoConf/saveConfAjax">
							<dhcc:bootstarpTag property="formbillnoconf0002" mode="query"/>
						</form>	
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
				<dhcc:pmsTag pmsId="pss-billno-conf-insert">
		   			<dhcc:thirdButton value="保存" action="保存" onclick="saveConf('#billNoConf');"></dhcc:thirdButton>
	   			</dhcc:pmsTag>
	   		</div>
   		</div>
	</body>
</html>
