<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<style type="text/css">
		.doc-upload-little-title {
			color: #000;
			font-weight: bold; 
			line-height: 2;
			font-size: 20px;
			border-bottom: 1px #000 solid;
		}
		.padding_class {
			margin-bottom: 10px;
		}
		.padding_class + .padding_class {
			border-top : dashed 1px #666;
		}
		.court-upload-text{
			line-height: 1.8;
			color: #666;
			font-size: 14px;
		}
		.court-upload-text1{
			padding-top:8px;
			padding-bottom: 25px;
		}
		.court-upload-record{
			line-height: 1.8;
			color: #666;
			font-size: 14px;
			padding-bottom: 20px;
		}
		.labelText {
			color: #000;
			font-size: 16px;
		}
		.labelText1 {
			color: #000;
			font-size: 16px;
			padding-right: 40px;
		}
		.labelText2 {
			color: #000;
			font-size: 16px;
		}
		.a{
			font-weight: bold;
		
		}
		body{
			background-color: #fff;
		} 
		</style>
		<script type="text/javascript" >
			var busName='${busName}';
			var busNumber = '${busNumber}';
			$(function(){
			    var creditReportData = JSON.parse('${result }');// \b是个已发现的特殊字符，无法parse。
			    console.log(creditReportData);
			    var littleTitleHtml ='<div class="court-upload-text1">'+ '<span class="labelText1">'+'<span class="a">被查询人名称：'+'</span>'+busName+'</span>'+
						'<span class="labelText2">'+'<span class="a">被查询人号码：'+'</span>'+busNumber+'</span>'+'</div>';
				 $(".content").append(littleTitleHtml);			
				if(creditReportData && creditReportData.errorcode == "11111"){
					var resultData = creditReportData;
					console.log("resultData.cpws.status="+resultData.data.cpws.status);
					var bigTitleHtml = '<div class="doc-upload-big-title">法院执行记录</div>';
					var littleTitleHtml = '<div class="doc-upload-little-title">裁判文书</div>';
					 $(".content").append(littleTitleHtml);
					if(resultData.data.cpws && resultData.data.cpws.status == "EXIST"){
						var list = resultData.data.cpws.listData;
						for ( var i = 0; i < list.length; i++) {
							var e = list[i];
							for(var p in e){
								if(e[p] == "null" || e[p] == null){
									e[p] = "";
								}
							}
							var childButtonHmtl=   	 '<div class="weui-uploader__bd padding_class">'
										+'<ul class="doc-upload-ul" id="'+e.idCardName+'"></ul>'
											+'<div class="court-upload-text" >'+'<span class="labelText">案号：</span>'+e.caseNo+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">案件类型：</span>'+e.caseType+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">姓名：</span>'+e.recordTime+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">内容：</span>'+e.content+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">标题：</span>'+e.title+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">简述：</span>'+e.remark+'</div>'
											/* +'<div class="court-upload-text" >'+'<span class="labelText">法官：</span>'+e.judgeId+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">法庭：</span>'+e.court+'</div>' */
									+'</div>'
								+'</div>';
							var childHtml=childButtonHmtl;
						    $(".content").append(childHtml);
						}
					}else{
					     var littleTitleHtml = '<div class="court-upload-record">暂无法院执行记录！</div>';
					  	 $(".content").append(littleTitleHtml);
						
					}
					var littleTitleHtml = '<div class="doc-upload-little-title">执行公告</div>';
					 $(".content").append(littleTitleHtml);
					if(resultData.data.Zxgg && resultData.data.Zxgg.status == "EXIST"){
						var list = resultData.data.Zxgg.listData;
					
						for ( var i = 0; i < list.length; i++) {
							var e = list[i];
							for(var p in e){
								if(e[p] == "null" || e[p] == null){
									e[p] = "";
								}
							}
							var childButtonHmtl=   	 '<div class="weui-uploader__bd padding_class">'
										+'<ul class="doc-upload-ul" id="'+e.idCardName+'"></ul>'
											+'<div class="court-upload-text" >'+'<span class="labelText">案号：</span>'+e.caseNo+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">罚款金额：</span>'+e.executionTarget+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">立案时间：</span>'+e.recordTime+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">法院名称：</span>'+e.court+'</div>'
									+'</div>'
								+'</div>';
							var childHtml=childButtonHmtl;
						    $(".content").append(childHtml);
						}
					}else{
						
						  var littleTitleHtml ='<div class="court-upload-record">暂无法院执行记录！</div>';
					   	  $(".content").append(littleTitleHtml);
					}
					var littleTitleHtml = '<div class="doc-upload-little-title">失信公告</div>';
					$(".content").append(littleTitleHtml);
					if(resultData.data.sxgg && resultData.data.sxgg.status == "EXIST"){
						var list = resultData.data.sxgg.listData;
						for ( var i = 0; i < list.length; i++) {
							var e = list[i];
							for(var p in e){
								if(e[p] == "null" || e[p] == null){
									e[p] = "";
								}
							}
							for(var p in e){
								if(e[p] == "null" || e[p] == null){
									e[p] = "";
								}
							}
							var childButtonHmtl=   	 '<div class="weui-uploader__bd padding_class">'
										+'<ul class="doc-upload-ul" id="'+e.idCardName+'"></ul>'
											+'<div class="court-upload-text" >'+'<span class="labelText">性别：</span>'+e.gender+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">年龄：</span>'+e.age+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">履行情况：</span>'+e.implementationStatus+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">执行依据文号：</span>'+e.exeCid+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">做出执行依据单位：</span>'+e.executableUnit+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">失信被执行人行为具体情形：</span>'+e.specificCircumstances+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">案号：</span>'+e.caseNo+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">发布时间：</span>'+e.posttime+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">立案时间：</span>'+e.recordTime+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">法院名称：</span>'+e.court+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">自然人或企业：</span>'+e.type+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">省份：</span>'+e.province+'</div>'
									+'</div>'
								+'</div>';
							var childHtml=childButtonHmtl;
						    $(".content").append(childHtml);
						}
					}else{
						  var littleTitleHtml = '<div class="court-upload-record">暂无法院执行记录！</div>';
					  	   $(".content").append(littleTitleHtml);
					
					}
					var littleTitleHtml = '<div class="doc-upload-little-title">法院公告</div>';
					$(".content").append(littleTitleHtml);
					if(resultData.data.fygg && resultData.data.fygg.status == "EXIST"){
						var list = resultData.data.fygg.listData;
						for ( var i = 0; i < list.length; i++) {
							var e = list[i];
							for(var p in e){
								if(e[p] == "null" || e[p] == null){
									e[p] = "";
								}
							}
							var childButtonHmtl=   	 '<div class="weui-uploader__bd padding_class">'
									+'<ul class="doc-upload-ul" id="'+e.idCardName+'"></ul>'
											+'<div class="court-upload-text" >'+'<span class="labelText">公告类型：</span>'+e.announcementType+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">立案时间：</span>'+e.recordTime+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">内容：</span>'+e.content+'</div>'
											+'<div class="court-upload-text" >'+'<span class="labelText">法院名称：</span>'+e.court+'</div>'
									+'</div>'
								+'</div>';
							var childHtml=childButtonHmtl;
						    $(".content").append(childHtml);
						}
					}else{
						var littleTitleHtml = '<div class="court-upload-record">暂无法院执行记录！</div>';
					  	$(".content").append(littleTitleHtml);
						
					}
				} else {
					var errorMsgHtml ="<center>" +"<h3>" + (creditReportData.errorMsg || "该客户没有法院执行记录！") + "</h3>"+"</center>";
					$(".content").append(errorMsgHtml);
				}
			 });
		</script>
	</head>
	<body class="overflowHidden1">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-xs-8 col-xs-offset-2 content" >
				</div>
			</div>
		</div>
	</body>
</html>
