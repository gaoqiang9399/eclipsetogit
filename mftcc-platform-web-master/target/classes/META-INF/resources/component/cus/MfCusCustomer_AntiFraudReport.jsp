<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.core.struts.taglib.JsonFormUtil"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>反欺诈报告</title>
		 <link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_AntiFraudReport.css?v=${cssJsVersion}" />
	</head>
	<script type="text/javascript">
		var cusNo = '${cusNo}';
		var idCardName='${dataMap.idCardName}';
		var idCardNum='${dataMap.idCardNum}';
		var mobile='${dataMap.mobile}';
		var eductionBckgrd='${dataMap.eductionBckgrd}';
		var company='${dataMap.company}';
		var address='${dataMap.address}';
		var refName='${dataMap.refName}';
		var refMobile='${dataMap.refMobile}';
		var reasonCode='${dataMap.reasonCode}';
		var itemNo = '${dataMap.itemNo}';//服务的编号
		var itemType = '${dataMap.itemType}';//服务类型
		var firstFlag = '${firstFlag}';
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			if(firstFlag=="1"){
				if(checkParam()){//参数非空校验
					//查询报告
					reportQuery();
				}
			}else{
				if(idCardNum==""){
					window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"身份证号码","where":"客户基本信息"}),0);
					return false;
				}else{
					$.ajax({
						url:webPath+"/mfCusCustomer/getCusAntiFraudReportAjax?idNum="+idCardNum,
						success:function(data){ 
							if(data.flag=="success"){
								var reportHtml = '<div class="mainWrapper">'
								+'<div class="mainWrapperTitleBar clearfix">'
								+'<h5 class="mainWrapperTitle">'
								+'<a class="print-res" target="_blank" href="/servicemanage/history/showpdf?serialno='+data.serialno+'">打印结果</a>'
								+'<a id="queryHis" class="print-res" href="#reportHistory">历史记录</a>'
								+'<div id="requery" class="btn btn-primary" onclick="reQueryReport();">重新查询</div>'
								+'</h5>'
								+'</div>'
								+data.html
								+'</div>';
								$('#reportContent').empty().html(reportHtml);
							    $(".r-main").addClass("col-md-8 col-md-offset-2");
							      var headHtml=getTableHeadHtml();
					    	    $("#reportHistory .list-table").empty().html(headHtml);
					    	    if(data.antiFraudReportList.length>0){
							   		var htmlList = "";
							    	$.each(data.antiFraudReportList,function(i,antiFraud){
						    			htmlList = htmlList+'<tr>'
													+'<td align="center">'+antiFraud.serialno+'</td>'
													+'<td align="center">'+antiFraud.idCardName+'</td>'
													+'<td align="center">'+antiFraud.idCardNum+'</td>'
													+'<td align="center">'+antiFraud.mobile+'</td>'
													+'<td align="center">'+antiFraud.occTimeRender+'</td>'
													+'<td align="center"><a target="_blank" href="/servicemanage/history/showpdf?serialno='+antiFraud.itemType+antiFraud.serialno+'" >查看</a></td>'
													+'<td align="center"><a target="_blank" href="/servicemanage/history/downloadpdf?serialno='+antiFraud.itemType+antiFraud.serialno+'">下载</a></td>'
													+'</tr>';
							    	});
							    	$("#reportHistory #tab").empty().html(htmlList);
					   			 }
							}else{
								alert(data.msg,0);
							}
						},error:function(){
								alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
						}
					});
				}
			}
		});
		
		//检查参数
		function checkParam(){
			if(idCardNum==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"身份证号码","where":"客户基本信息"}),0);
				return false;
			}
			if(idCardName==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"身份证姓名","where":"客户基本信息"}),0);
				return false;
			}
			if(mobile==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"手机号码","where":"客户基本信息"}),0);
				return false;
			}
			if(eductionBckgrd==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"学历","where":"客户基本信息"}),0);
				return false;
			}
			if(company==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"工作单位","where":"职业信息"}),0);
				return false;
			}
			if(address==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"通讯地址","where":"客户基本信息"}),0);
				return false;
			}
			if(refName==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"联系人姓名","where":"社会关系"}),0);
				return false;
			}
			if(refMobile==""){
				window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"联系人手机号码","where":"社会关系"}),0);
				return false;
			}
			if(reasonCode==""){
				window.top.alert(top.getMessage("NOT_FORM_EMPTY","查询原因"),0);
				return false;
			}
			return true;
		}
		
		//查询个人反欺诈报告
		function reportQuery(){
			$.ajax({
				url:webPath+"/mfCusCustomer/getEntityAuthCodeAjax",
				success:function(result){
					if(result.flag=="success"){
						var entityAuthCode =result.data; 
						$.post("/servicemanage/report/person/antifraud.json",{
							idCardName : idCardName,
							idCardNum : idCardNum,
							mobile : mobile,
							eductionBckgrd : eductionBckgrd,
							company : company,
							address : address,
							refName : refName,
							refMobile : refMobile,
							reasonCode : reasonCode,
							entityAuthCode : entityAuthCode,
							itemNo : itemNo
						},
						  function(data){
						    $('#reportContent').html(data.html);
						    var aHtml = $("#reportContent .print-res").prop("outerHTML");
						    if(aHtml==undefined){
						   		 aHtml="";
						    }
						    var htmlStr = '<h5 class="mainWrapperTitle">'
						    		+aHtml
						   			+'<a id="queryHis" class="print-res" href="#reportHistory">历史记录</a>'
						   			+'<div id="requery" class="btn btn-primary" onclick="reQueryReport();">重新查询</div></h5>';
						    $(".mainWrapperTitleBar").html(htmlStr);
						    $(".r-main").addClass("col-md-8 col-md-offset-2");
						  	reportHistoryQuery();
						  },"json");
					}
				}
			  });
			};
			
			
			//异步获取查询历史列表
			function reportHistoryQuery(){
				if(idCardNum==""){
					window.top.alert(top.getMessage("NOT_EMPTY_CONTENT",{"field":"身份证号码","where":"客户基本信息"}),0);
					return false;
				}else{
					$.ajax({
						url:webPath+"/mfCusCustomer/getReportHistoryAjax?idNum="+idCardNum,
						success:function(data){ 
							if(data.flag=="success"){
					    	    var headHtml=getTableHeadHtml();
					    	    $("#reportHistory .list-table").empty().html(headHtml);
					    	    if(data.antiFraudReportList.length>0){
							   		var htmlList = "";
							    	$.each(data.antiFraudReportList,function(i,antiFraud){
						    			htmlList = htmlList+'<tr>'
													+'<td align="center">'+antiFraud.serialno+'</td>'
													+'<td align="center">'+antiFraud.idCardName+'</td>'
													+'<td align="center">'+antiFraud.idCardNum+'</td>'
													+'<td align="center">'+antiFraud.mobile+'</td>'
													+'<td align="center">'+antiFraud.occTimeRender+'</td>'
													+'<td align="center"><a target="_blank" href="/servicemanage/history/showpdf?serialno='+antiFraud.itemType+antiFraud.serialno+'" >查看</a></td>'
													+'<td align="center"><a target="_blank" href="/servicemanage/history/downloadpdf?serialno='+antiFraud.itemType+antiFraud.serialno+'">下载</a></td>'
													+'</tr>';
							    	});
							    	$("#reportHistory #tab").append(htmlList);
					   			 }else{
					   				 $("#reportHistory").find("tbody").html('<tr><td style="text-align: center;" colspan=6">暂无数据</td></tr>');
					   			 }
							}else{
								alert(data.msg,0);
							}
						},error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				}
			};
			
			
			function getTableHeadHtml(){
				var headHtml = 	'<div class="title"><span class="formName"><i class="i i-xing blockDian"></i>查询历史</span><button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#historyList"><i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'
							+'<div class="content collapse in" id="historyList">'
								+'<table id="tablist" class="ls_list" title="tableantifraudhislist" width="100%" cellspacing="1" border="0" align="center">'
									+'<thead>' 
									+'<tr>'
									+'<th scope="col" name="serialno" sorttype="0" align="center">交易流水号</th>'
									+'<th scope="col" name="idCardName" sorttype="0" align="center">身份证姓名</th>'
									+'<th scope="col" name="idCardNum" sorttype="0" align="center">身份证号码</th>'
									+'<th scope="col" name="mobile" sorttype="0" align="center">手机号码</th>'
									+'<th scope="col" name="occTimeRender" sorttype="0" align="center">查询时间</th>'
									+'<th scope="col" colspan="2" align="center"> <font class="button_color">操作</font></th>'
									+'</tr>'
									+'</thead>'
									 +'<tbody id="tab">' 
									 
									 +'</tbody>'
								+'</table>'
							+'</div>';
				return headHtml;
			}
	
			//重新查询
			function reQueryReport(){
				alert(top.getMessage("CONFIRM_QUERY_COST","9.00"),2,function(){
					if(checkParam()){//参数非空校验
						//查询报告
						reportQuery();
					}
				});
			};
		
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div id="reportContent" class="row clearfix"></div>
				<div id="reportHistory" class="row clearfix">
					<div class="col-md-8 col-md-offset-2">
						<div class="list-table">
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
