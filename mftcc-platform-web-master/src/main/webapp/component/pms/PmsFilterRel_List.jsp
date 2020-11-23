<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/pms/js/pmsFilterRel.js" ></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pmsFilterRel/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepms1001",//列表数据查询的table编号
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
					{actionUrl:url,title:"自定义筛选关系"}
				);
			}
			function func_input(url){
				rightForm =	new RightForm(
						{actionUrl:url,formUrl:webPath+"/pmsFilterRel/insertAjax",title:"自定义筛选关系",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
					);
			}
			function func_update(obj,url){
				rightForm =	new RightForm(
						{actionUrl:url,formUrl:webPath+"/pmsFilterRel/updateAjax",title:"自定义筛选关系",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
					);
			}
			function reload(){
				rightForm.close();
			}
			function ajaxInsert(obj){
				var flag = submitJsMethod(obj, '');
				flag = valiParma(obj);
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						success:function(data){
							if(data.flag == "success"){
								  alert(top.getMessage("SUCCEED_OPERATION"),1);
								  reload();
								  myCustomScrollbar({
								    	obj:"#content",//页面内容绑定的id
								    	url:webPath+"/pmsFilterRel/findByPageAjax",//列表数据查询的url
								    	tableId:"tablepms1001",//列表数据查询的table编号
								    	tableType:"thirdTableTag", //table所需解析标签的种类
								    	pageSize:30 //加载默认行数(不填为系统默认行数)
								    });
							}
						},
						error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
		</script>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="PmsFilterRel_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<div class="col-xs-9 column">
						<button type="button" class="btn btn-primary pull-left" onclick="func_input('<%=webPath %>/pmsFilterRel/inputAjax');">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
							<li class="active">自定义筛选关系</li>
						</ol>
					</div>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=源请求/关联请求"/>
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
