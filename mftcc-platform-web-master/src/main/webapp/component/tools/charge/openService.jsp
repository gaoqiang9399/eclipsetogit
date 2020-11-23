<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
		<%
			String operate = request.getParameter("operate");
	        String itemNo = request.getParameter("itemNo");//服务id
	        String itemType = request.getParameter("itemType");//服务类型
	        itemType = new String(itemType.getBytes("ISO-8859-1"), "UTF-8");  
	        operate = new String(operate.getBytes("ISO-8859-1"), "UTF-8");  
		%>
<head>
<style type="text/css">
	.btt{
		position: absolute;
		bottom: 21px;
		left: 44%;
	}
	td{
		padding-left: 67px;
	}
	.inp{
		border: 1px solid #32b5cb;
		border-radius: 0px;
		padding: 6px 12px 6px 24px;
		font-size: 12px;
		display: inline-block !important;
		width:auto !important;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开通服务</title>
	<script type="text/javascript">
		$(function(){
			var sts;
			var operate = '<%=operate%>';
			var itemType = '<%=itemType%>';
			if(itemType=="短信服务"){
				$('#DX').removeAttr("style");
			}else{
				$('#Other').removeAttr("style");
			}
			if(operate=="开通"){
				sts="1";
			}else{
				sts="0";
			}
			$("div").delegate("#save","click",function(){
				var smsSuffix = $('#smsSuffix').val();
				var itemNo = '<%=itemNo%>';
				var itemType = '<%=itemType %>';
							alert(itemNo);
				if(itemType=="短信服务"){
					if(smsSuffix==""){
						alert('必填系统提示');
						return false;
					}
				}
				$.ajax({
						url : webPath+"/accountPay/openServiceAjax",
						data : {
							"itemNo":itemNo,
							"smsSuffix":smsSuffix,
							"sts":sts,
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							if (data.flag == "success") {
								$("input[name=msgTel]").val("");
			                    $("input[name=sendMsg]").val("");
			                    window.location.reload();
								window.top.alert(data.msg, 1);
							} else {
								alert(  top.getMessage("FAILED_SAVE"), 0);
							}
						},
						error : function() {
							LoadingAnimate.stop();
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
			});
		});
	</script>
</head>
<body>
	<table style="margin-top: 29px;">
		<tr id="Other" style="display: none;">
			<td class="fill">服务介绍</td>
			<td><span>服务的介绍</span></td>
		</tr>
		<tr id="DX" style="display: none;">
			<td class="fill">短信后缀</td>
			<td><input id = "smsSuffix" class = "form-control inp" type="text" value="">
				<span style="color:red;">*</span></td>
		</tr>
	</table>
	<div><input id="save" class="btn btt btn-primary" type="button" value = "开通"></div>
</body>
</html>