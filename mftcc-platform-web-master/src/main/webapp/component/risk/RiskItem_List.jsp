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
		<script type="text/javascript" >
		    var pageStr = '${pageStr}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/riskItem/findByPageAjax?pageStr="+pageStr,//列表数据查询的url
			    	tableId:"tablerisk0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30
			    });
			 });
			 
			 //风险拦截项新增
			function addRiskPreventItem(){
				window.parent.openBigForm(webPath+"/riskItem/input?pageStr="+pageStr);
			}
			function getById(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				url = url + "&pageStr="+pageStr;
				window.parent.openBigForm(url);
			};
		</script>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="RiskItem_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="addRiskPreventItem();">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础设置</a></li>
							<% if("risk".equals(pageStr)){%>
								<li class="active">风险拦截项配置  </li>
								
							<%}else if("cus".equals(pageStr)){%>
							<li class="active">客户准入项配置  </li>
							<%}else if("pro".equals(pageStr)){%>
							<li class="active">产品准入项配置  </li>
							<% }else{%>
								<li class="active">客户分类入项配置 </li>
							<%}%>
							
						</ol>
					</div>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=方案名称/方案描述"/>
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
