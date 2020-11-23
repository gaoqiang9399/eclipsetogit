<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
		$(function(){
		    myCustomScrollbar({
		    	obj : "#content", //页面内容绑定的id
		    	url:webPath+"/cuslendWarning/findRecallBaseWarningListByPageAjax", //列表数据查询的url
		    	tableId : "tablecuslend0001", //列表数据查询的table编号
		    	tableType : "thirdTableTag", //table所需解析标签的种类
		    	pageSize:30, //加载默认行数(不填为系统默认行数)
		    	callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"flag",onText:"启用",offText:"停用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
		    });
		 });
			function addConfig(){
				top.openBigForm(webPath+"/cuslendWarning/addRecallBaseWarning","新增",function(){
					window.updateTableData();
				});
			};
			function clickConfig(url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,"系统消息配置",function(){
					window.updateTableData();
				});
			};
			function updateVar(url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.addFlag = false;
				top.openBigForm(url,"修改",function(){
					if(top.addFlag){
						window.updateTableData();
					}
				});
			};
        //模板标签配置
        function baseTagSet(obj, url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            top.openBigForm(url,'标签配置',null);
        }
		</script>
	</head>
<body style="overflow-y: hidden;">
   		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="addConfig();">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=base">基础配置</a></li>
								<li class="active">催收预警提醒</li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=检查模板名称/产品种类"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
</body>
</html>