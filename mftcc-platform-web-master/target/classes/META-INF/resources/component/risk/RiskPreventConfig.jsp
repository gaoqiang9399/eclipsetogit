<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<% 
	String pageStr = (String)request.getAttribute("pageStr");
%>
<html>
	<head>
		<title>列表</title>
<%-- 		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script> --%>
<%-- 		<script type="text/javascript" src="${webPath}/component/app/js/AppProperty.js" ></script> --%>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" /> --%>

		<script type="text/javascript">
		var pageStr = '${pageStr}';
		var myCustomScrollbarOpt;
			$(function() {
				 myCustomScrollbarOpt = myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/riskPreventSceGen/findByPageAjax?pageStr="+pageStr,//列表数据查询的url
			    	tableId:"tablerisk0021",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	myFilter : true //是否有我的筛选
			    });
			
			 $("button[name='configDime']").click(function() {
			 	var scNo = $(this).attr("scNo");
			 	var offset = $(this).offset();
			 	$("#limitDiv").css({"position": "absolute","margin-left": offset.left-104,"margin-top":offset.top+30,"z-index":"999"});
			 	$("#limitDiv").show();
			 	$("#limitFrame").attr("src",webPath+"/riskPrevent/getLimitDimes?scNo="+scNo);
			 });
			 
			 $("button[name='saveConfig']").click(function() {
			 	 var scNo = $(this).attr("scNo");
			 	 var tr = $("#"+scNo+"");
			 	 var dimeStr="";
			 	 var dimeJavaStr="";
			 	 var dime1 = tr.find("select[name='dime1Select']").val();
			 	 var dime2 = tr.find("select[name='dime2Select']").val();
			 	 var dime3 = tr.find("select[name='dime3Select']").val();
			 	 var dimeJava1 = tr.find("input[name='javainput1']").val();
			 	 var dimeJava2 = tr.find("input[name='javainput2']").val();
			 	 var dimeJava3 = tr.find("input[name='javainput3']").val();
			 	 if(dime1==undefined || dime1==null || dime1=="") {
			 	 	alert("请配置维度");
			 	 	return;
			 	 }else {
			 	 	if(dimeJava1 == "") {
			 	 		//alert("请配置维度一对应的java变量");
			 	 		//return;
			 	 	}
			 	 	dimeStr += dime1;
			 	 	dimeJavaStr += dimeJava1;
			 	 	if(dime2!=undefined && dime2!=null && dime2!="") {
			 	 		if(dime1 == dime2) {
			 	 			alert("维度不能重复");
			 	 			return;
			 	 		}else {
			 	 			if(dimeJava2 == "") {
			 	 				//alert("请配置维度二对应的java变量");
			 	 				//return;
			 	 			}
			 	 			dimeStr += "|"+dime2;
			 	 			dimeJavaStr += "|"+dimeJava2;
			 	 			if(dime3!=undefined && dime3!=null && dime3!="") {
			 	 				if(dime1 == dime2 || dime1 == dime3 || dime2 == dime3) {
			 	 				alert("维度不能重复");
			 	 				return;
			 	 				}else {
			 	 					if(dimeJava3 == "") {
			 	 						//alert("请配置维度三对应的java变量");
			 	 						//return;
			 	 					}
			 	 					dimeStr += "|"+dime3;
			 	 					dimeJavaStr += "|"+dimeJava3;
			 	 				}
			 	 			}
			 	 		}
			 	 	}
			 	 }
			 	 //alert(dimeStr);
			 	 //alert(dimeJavaStr);
			 	 
			 	$.ajax({
					url:webPath+"/riskPrevent/insertConfig",
					data:{
						"riskPrevent.scNo":scNo,
						"riskPrevent.dimeParmKeysStr":dimeStr,
						"riskPrevent.dicJavaNamesStr":dimeJavaStr,
					},
					type:"POST",
					dataType:"json",
					async:false,//关键
					success:function(data){
						if(data.flag=="error"){
							alert(data.msg);
							
						}else{
							alert(data.msg);
							flag = true;//必须写
							window.location.reload();
						}
					},error:function(data){
						alert(  top.getMessage("FAILED_SAVE"));
					}
				});
				 //window.location.reload();
			 });
			 $("#limitDiv").hide();
		});
		
		//新增风险拦截模型
		 function addRiskPrevent(){
			window.parent.openBigForm(webPath+"/riskPreventSceGen/input?pageStr="+pageStr);
		 }
		 
		 //编辑模型基本信息
		function editRiskPrevent(obj,url){
			 top.updateFlag = false;
// 			  top.window.openBigForm(url,'风险拦截配置',closeCallBack);
			  top.window.openBigForm(url,'',closeCallBack);
		 };
		 
		function closeCallBack(){
			 if(top.updateFlag){
					window.location.href=webPath+"/riskPrevent/getListPage?pageStr="+pageStr;
			 }
		};
		
		//风险配置
		function cinfigRiskItem(obj,url){
			url = url.replace(/\|/g, "%7C");
			url = url + "&pageStr="+pageStr;
// 		 	top.window.openBigForm(url,'维度组合+拦截项配置',myclose);
		 	top.window.openBigForm(url,'模型配置',myclose);
		};
		
		
		
		
		
	</script>
	</head>


<body>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="RiskPreventConfig"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="addRiskPrevent();">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础设置</a></li>
							<% if("risk".equals(pageStr)){%>
								<li class="active">风险拦截模型配置 </li>
							<%}else if("cus".equals(pageStr)){%>
								<li class="active">客户准入模型配置  </li>
							<% }else{%>
								<li class="active">产品准入模型配置  </li>
							<%}%>
						</ol>
					</div>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=模型名称"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>
