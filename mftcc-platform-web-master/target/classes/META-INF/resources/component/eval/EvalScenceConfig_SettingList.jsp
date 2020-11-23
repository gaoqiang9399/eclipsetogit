<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript">
		$(function(){
		    myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/evalScenceConfig/findByPageAjax",//列表数据查询的url
		    	tableId:"tableeval0001",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,
		    	callback:function(){
		    		$("table").tableRcswitcher({
		    		name:"useFlag",onText:"启用",offText:"禁用"});
		    	}//方法执行完回调函数（取完数据做处理的时候）
		    });
		 });
		//新增评级场景
		function addEvalScenceConfig(){
			window.parent.openBigForm("${webPath}/evalScenceConfig/input","评级场景");
		}
		//编辑评级场景
		function getById(obj,url){
			window.parent.openBigForm(url,"评级场景");
		}
		//评级场景配置
		function getEvalScenceConfig(obj,url){
			var argument = url.split("\?")[1];
			var evalScenceNo = argument.split("&")[0];
			var evalIndexTypeRel = argument.split("&")[1];
			window.parent.openBigForm("${webPath}/evalScenceConfig/getEvalScenceConfigSetting?evalScenceNo="+evalScenceNo+"&evalIndexTypeRel="+evalIndexTypeRel,"评级场景");
		}

		function deleteScence(obj,url){
			var argument = url.split("\?")[1];
			var evalScenceNo = argument.split("&")[0].split("=")[1];
			$.ajax({
				url:webPath+"/evalScenceConfig/getScenceIsUsedAjax",
				data:{evalScenceNo:evalScenceNo},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						ajaxTrDelete(obj,url);
					}else{
						window.top.alert(data.msg,0);
					}
				},error:function(){
					 window.top.alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
				}
			}); 
			
		}
		</script>
    </head>
    <body>
    <dhcc:markPoint markPointName="EvalScenceConfig_Setting"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="addEvalScenceConfig();">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
								<li class="active">评级模型配置 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
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