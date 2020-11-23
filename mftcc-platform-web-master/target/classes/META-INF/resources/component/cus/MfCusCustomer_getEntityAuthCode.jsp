<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>反欺诈报告</title>
	</head>
	<script type="text/javascript">
		var itemNo = "FY009006";//服务的编号
		var firstFlag = '${firstFlag}';
		var cusNo = '${cusNo}';
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			getEntityAuthCode(itemNo);
		});
		
		//获取授权码,返回授权协议（result.html）
		function getEntityAuthCode(itemNo){
			$.post('/servicemanage/authorization/licensing/agreement.json',{itemNo : itemNo,},function(result){
				$("#entityAuthCodeDiv").html(result.html);
				$("#entityAuthCode").val(result.data);
				$(".agree-code").addClass("text-center");
				$(".agree-title").addClass("text-center");
			},'json');
		};
		
		//同意授权
		function agreeAuth(){
			if(firstFlag=="1"){
				top.entityAuthCode = $("#entityAuthCode").val();
				top.flag=true;
				top.cusNo = cusNo;
				myclose_click();
			}else{
				window.location.href = '${webPath}/mfCusCustomer/getCusAntiFraudReport?cusNo='+cusNo+"&ajaxData="+$("#entityAuthCode").val()+"&firstFlag=1";
			}
		}
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div id="entityAuthCodeDiv"></div>
					<input id="entityAuthCode" type="hidden"></input>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="同意" action="同意"  onclick="agreeAuth();"></dhcc:thirdButton>
				<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
