<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>财务报表基础元素管理列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cusFinParm/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepfs5005",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:false, //是否有我的筛选(列表列动态切换)
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{},//指定参数
			    	callback:function(){
			    		/* $("table").tableRcswitcher({
			    		name:"demoPosition"}); */
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			 function func_getById(obj ,url){
				 tr = $(obj).parents("tr");
				$(tr).parents("table").find(".selected").removeClass("selected");
				$(tr).addClass("selected");
				c =	new RightForm(
					{actionUrl:url,formurl:webPath+"/cusFinParm/updateAjax",title:"财务报表基础元素管理"
					,btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this,reload)","data-elem" : tr}]}
				);
			}
			function reload(){
				c.close();
				$("table").tableRcswitcher({
			    	name:"useFlag"});
				$(tr).parents("table").find(".selected").removeClass("selected");
			}
			//新增
			function func_input(url){
				c =	new RightForm(
					{actionUrl:url,formurl:webPath+"/cusFinParm/insertAjax",title:"财务报表基础元素管理",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}]}
				);
			}
		</script>
	</head>
<%-- <body style="overflow-y: hidden;">
		<!--标记点 未后退准备-->
		<dhcc:markPoint markPointName="CusFinParm_List"/>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
			<strong>财务报表基础元素</strong>
				<div class="search-group">
					<!--我的筛选选中后的显示块-->
					<div class="search-lable" id="pills">
						<dhcc:thirdButton value="新增" action="新增"
							onclick="func_input('${webPath}/cusFinParm/inputAjax')"></dhcc:thirdButton>
						<!--我的筛选按钮-->
						<div class="filter-btn-group">
							<!--自定义查询输入框-->
							<input placeholder="智能搜索" id="filter_in_input" class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search" class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> --%>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="CusFinParm_List"/>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<div class="btn-left">
					<dhcc:thirdButton value="新增" action="新增" onclick="func_input('${webPath}/cusFinParm/inputAjax')"></dhcc:thirdButton>
				</div>
				<div class="search-group">
				<div class="search-lable" id="pills">
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input placeholder="智能搜索" id="filter_in_input"  class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search"  class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<!--待定是否放置自定义table标签?-->
		</div>
	</body>	
</html>