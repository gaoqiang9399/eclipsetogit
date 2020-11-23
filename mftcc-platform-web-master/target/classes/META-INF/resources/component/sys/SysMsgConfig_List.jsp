<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/sysMsgConfig/findByPageAjax",//列表数据查询的url
			    	tableId:"tablesys0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			var rightForm;
			function func_getById(obj ,url){
				 tr = $(obj).parents("tr");
				$(tr).parents("table").find(".selected").removeClass("selected");
				$(tr).addClass("selected");
				rightForm =	new RightForm(
					{actionUrl:url,title:"消息模板配置"}
				);
			}
			function func_update(obj,url){
				rightForm =	new RightForm(
						{actionUrl:url,formUrl:webPath+"/sysMsgConfig/updateAjax",title:"消息模板配置",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
				);
			}
			function func_input(url){
				rightForm =	new RightForm(
						{actionUrl:url,formUrl:webPath+"/sysMsgConfig/insertAjax",title:"消息模板配置",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
					);
			}
			function reload(){
				rightForm.close();
			}
			function ajaxInsert(obj){
				var flag = submitJsMethod(obj, '');
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								  window.top.alert(data.msg,1);
								  reload();
								  myCustomScrollbar({
								    	obj:"#content",//页面内容绑定的id
								    	url:webPath+"/sysMsgConfig/findByPageAjax",//列表数据查询的url
								    	tableId:"tablesys0001",//列表数据查询的table编号
								    	tableType:"thirdTableTag",//table所需解析标签的种类
								    	pageSize:30//加载默认行数(不填为系统默认行数)
								    });
							}else{
								 window.top.alert(data.msg,0);
							}
						},error:function(data){
							window.top.alert(top.getMessage("FAILED_OPERATION"," "));
						}
					});
				}
			}
		</script>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="SysMsgConfig_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="func_input('${webPath}/sysMsgConfig/inputAjax');">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=template">模板设置</a></li>
								<li class="active">消息模板配置 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=消息内容"/>
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
