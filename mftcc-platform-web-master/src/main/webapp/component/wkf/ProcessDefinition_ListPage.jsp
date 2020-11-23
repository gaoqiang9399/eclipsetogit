<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	
<script type="text/javascript">
	var myCustomScrollbar;
	$(function() {
		myCustomScrollbar = myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/processDefinition/findByPageAjax",//列表数据查询的url
			tableId : "tablewkf0021",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true,
			data:{
				processDefDesc : $("#filter_in_input_def_desc").val(),
				processDefName : $("#filter_in_input_def_name").val(),
				processDefKey : $("#filter_in_input_def_key").val()
			}
		//是否有我的筛选
		});
		initCheck();
		
		$(document).keyup(function(event){
		  if(event.keyCode ==13){
		    defSearch();
		  }
		});
		
	});
	
	function initCheck(){
		var th = $(".table-float-head").find("th").eq(0);
		th.unbind();
		th.html("<input type=\"checkbox\">");
		th.find("input").bind("change",function(){
			if($(this).is(':checked')){
				$(".check input[type=checkbox]").prop("checked",true);
			}else{
				$(".check input[type=checkbox]").prop("checked",false);
			}
		});
	}
	
	function openProcessDesigner(id){
		id=id.split("?")[1];
		window.open(webPath+"/tech/wkf/modelerEditor.jsp?command=DesignProcess&"+id);
		
	}
	function defSearch(){
		myCustomScrollbar.data = {
			processDefDesc : $("#filter_in_input_def_desc").val(),
			processDefName : $("#filter_in_input_def_name").val(),
			processDefKey : $("#filter_in_input_def_key").val()
		};
		myCustomScrollbar.reload();
		initCheck();
	}
	
	function defDelete(){
		var defId = "";
		$(".check input[type=checkbox]").each(function(){
			if($(this).is(':checked')){
				defId += $(this).attr("value").split("=")[1]+",";
			}
		});
		if(defId!=""){
			defId = defId.substring(0,defId.length-1);
			jQuery.ajax( {
				type : "POST",
				cache:false,
				async:true,
				url:webPath+"/processDefinition/deleteMore",
				data:{processDefId:defId},
				success : function(data) {
					if(data.flag = "success"){
						myCustomScrollbar.reload();
						initCheck();
					}
				}
			});
		}
	}
</script>
	<body class="bodybg-gray">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="search-div">
					<div class="col-md-2 column  color_theme">
						<ol class="breadcrumb pull-left padding_left_0">
							<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
							<li class="active">流程定义列表 </li>
						</ol>
					</div>
					<div class="col-md-10 column">
						<form class="form-inline pull-right padding_right_15" role="form">
							<div class="form-group">
								<input type="text" id="filter_in_input_def_key" class="form-control" placeholder="流程标识">
							</div>
							<div class="form-group">
								<input type="text" id="filter_in_input_def_name" class="form-control" placeholder="流程名称">
							</div>
							<div class="form-group">
								<input type="text" id="filter_in_input_def_desc"  class="form-control" placeholder="流程描述">
							</div>
							<button type="button" class="btn btn-primary" id="filter_btn_search" onclick="defSearch();">搜索</button>
							<button type="button" class="btn btn-default" id="filter_btn_search" onclick="defDelete();">删除</button>
						</form>
					</div>
				</div>
					
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content" style="height: auto;">
						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>