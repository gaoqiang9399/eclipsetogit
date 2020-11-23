<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	
	<body style="overflow: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<button type="button" class="btn btn-primary pull-left" onclick="newTempModel();">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=template">模板设置</a></li>
							<li class="active">模板模型配置 </li>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<dhcc:tableTag property="tabletemplatemodel0001" paginate="mfTemplateModelList" head="true"></dhcc:tableTag>
					</div>
				</div>
			</div>
		</div>
	</body>	
	<script type="text/javascript">
	function newTempModel(){
		top.addFlag = false;
		 top.htmlStrFlag = false;
		top.openBigForm(webPath + "/mfTemplateModel/input","模板新增",function(){
			if(top.addFlag){
				if(top.htmlStrFlag){
					var tableHtml = $(top.htmlString).find("tbody").html();
					$(".table_content").find("tbody").html(tableHtml);
				}
			}
		});
	};
	
	function getByIdThis(url){
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.openBigForm(url,"模板编辑",function(){
			if(top.addFlag){
				if(top.htmlStrFlag){
					var tableHtml = $(top.htmlString).find("tbody").html();
					$(".table_content").find("tbody").html(tableHtml);
				}
			}
		});
	};
	</script>
</html>
