<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>	</title>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" >
			$(function(){
				getLoanParm();
				$("#loanParmDIv").click(function(){
					updatePwd();
				});
			 });
			function updatePwd(){
				//处理url，区分客户没有业务的情况以及有业务的情况，分别处理清理的交互
				//确认是否重置密码
				//url = webPath+"/mfDataClean/getDetailPage?cusNo="+resObj.cusNo+"&appId="+resObj.appId+"&pactId="+resObj.pactId;
				var loanParm = $("#loanParm").val();
					if(loanParm=="0"){
						loanParm="1"
					}else{
						loanParm="0"
					}
					$.ajax({
						url:webPath+"/mfKingsoftPaymentPassword/updateLoanParm",
						type:"POST",
						dataType:"json",
						data:{loanParmFlag:loanParm},
						success:function(data){
							$("#loanParm").val(loanParm);
							if(loanParm=='0'){//如果是关闭
								$("#open").hide();
								$("#close").show();
							}else{
								$("#close").hide();
								$("#open").show();
							}
						},error:function(data){
						 	alert("修改失败");
						}
					});
			};
			function getLoanParm(){
				$.ajax({
						url:webPath+"/mfKingsoftPaymentPassword/getLoanParm",
						type:"POST",
						dataType:"json",
						success:function(data){
							$("#loanParm").val(data.loanParm);
							if(data.loanParm=='0'){//如果是关闭
								$("#open").hide();
								$("#close").show();
							}else{
								$("#close").hide();
								$("#open").show();
							}
						},error:function(data){
						 	alert("获取失败");
						}
					});
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="search-div">
						<div class="col-xs-9 column">
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=base">基础配置</a></li>
								<li class="active">放款参数设置</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
						<input id="loanParm" value="" type="hidden">
						<div id="loanParmDIv">
							<span>是否允许提现：</span>
							<span id="close" class="swraper lease " style="display:none;width: 50px; line-height: 18px;" input-name="flag" input-value="1" input-type="checkbox"><span class="stoggler off" style="font-size: 12px; transform: translateX(-41px);"><span class="slabel-on">允许</span><span class="sblob" style="width: 16px; height: 16px;"></span><span class="slabel-off">禁止</span></span></span>
							<span id="open" class="swraper lease " style="display:none;width: 50px; line-height: 18px;" input-name="flag" input-value="1" input-type="checkbox"><span class="stoggler on" style="font-size: 12px; transform: translateX(-9px);"><span class="slabel-on">允许</span><span class="sblob" style="width: 16px; height: 16px;"></span><span class="slabel-off">禁止</span></span></span>
						</div>
						<!-- <span class="config-font" name="mfcollateralclassname"><span class="swraper lease" style="width: 50px; line-height: 12px;" input-name="useFlag" input-value="1" input-type="checkbox"><span class="stoggler off" style="font-size: 12px; transform: translateX(-44px);"><span class="slabel-on">启用</span><span class="sblob" style="width: 10px; height: 10px;"></span><span class="slabel-off">停用</span></span></span><input name="useFlag" type="checkbox" value="1" style="display: none;"></span>
						<span class="config-font" name="mfcollateralclassname"><span class="swraper lease" style="width: 50px; line-height: 12px;" input-name="useFlag" input-value="1" input-type="checkbox"><span class="stoggler on" style="font-size: 12px; transform: translateX(-6px);"><span class="slabel-on">启用</span><span class="sblob" style="width: 10px; height: 10px;"></span><span class="slabel-off">停用</span></span></span><input name="useFlag" type="checkbox" value="1" style="display: none;"></span> -->
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
