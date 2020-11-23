<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/docTypeConfig/findByPageAjax",//列表数据查询的url
			    	tableId:"tabledoc0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag"//table所需解析标签的种类
			    });
			 });
			function newDocType(url){
				top.addFlag = false;
				top.openBigForm(url,"文档类型配置",function(){
					if(top.addFlag){
						window.updateTableData();
					}
				});
			};
			
			
			function formDesignThis(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				$.ajax({
					url:url,
					type:"POST",
					dataType:"json",
					success:function(data){
						if(data.flag == "success"){
							var url = webPath+'/tech/dragDesginer/openForm.action?formId='+data.formNo;
							window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
						}else{
							alert("获取表单文件出错",0);
						}
					},error:function(){
						alert("请求出错",0);
					}
				});
			};
		</script>
	</head>
<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="newDocType('${webPath}/docTypeConfig/input');">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础设置</a></li>
							<li class="active">文档类型配置</li>
						</ol>
					</div>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=文档名称"/>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
</body>	
</html>
