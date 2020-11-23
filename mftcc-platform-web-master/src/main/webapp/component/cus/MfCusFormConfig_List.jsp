<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body style="height: auto;overflow-y: hidden;">
	 	 <div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div color_theme">
						<ol class="breadcrumb pull-left padding_left_0">
							<li><a href="${webPath}/sysDevView/setC?setType=form">表单设置</a></li>
							<li class="active">客户表单配置</li>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="table_content">
						<div class="content_table">
							<dhcc:tableTag property="tablenmd00002" paginate="parmDiclist" head="true"></dhcc:tableTag>
						</div>
    				</div>
				</div>
			</div>
		</div>
	</body>	
	
<script type="text/javascript">
	$(function() {
    	 $("tr").bind("dblclick", function(event){
    	    
    	 	var optCode = $("tr").find('td').eq(0).html();
    	 	var optName = $("tr").children('td').eq(1).html();
    	 	var url ="${webPath}/mfCusFormConfig/getAllFormConList?optCode="+optCode+"&&optName="+optName;
    	 	window.location.href = url;
    	 })
	});	
</script>
	
</html>
