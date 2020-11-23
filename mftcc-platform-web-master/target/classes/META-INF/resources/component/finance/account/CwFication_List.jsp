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
			    	url:webPath+"/cwFication/findByPageAjax",//列表数据查询的url
			    	tableId:"tablefication0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
						$(".listOpStyle").each(function(){
							if($(this).text()=="数据管理"){
					  		$(this).parents("td").html("<a href='javascript:void(0)' onclick='SyncThis(this)'>数据同步</a>");
							}
						})
			    	}
			    });
			    //系统数据同步
			     $("#sysDataSync").bind("click",function(){
				 	sysDataSync("");//同步所有
				 })
			 });
		</script>
		
		
	</head>

	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					
					<button type="button" class="btn btn-primary pull-left" onclick="addCwFication()">新增</button>
					<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/cwParamset/cwParamEntrance">设置</a></li>
								<li class="active">辅助核算 </li>
							</ol>
				</div>
				<div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pills">
						<div id="filter_list" class="pull-left">
							<ul class="ztree" id="my_filter" style="-moz-user-select: none;">
							</ul>
						</div>
						<div>
						<span class="btn btn-primary pull-left" id="sysDataSync" title="将系统中的员工、部门、客户同步到相应的辅助核算类型下">系统数据同步</span>
						</div>
					</div>
					<div class="col-xs-3 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="类型名称">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
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
</body>   
	<script type="text/javascript">
	
		function ajaxGetReviewInfo(obj,url){
			window.location.href=webPath+"/"+url;
		}
		
		function cwFicationDataGetListPage(obj,url){

            window.location.href=webPath+url;
		}
		function cwRelationGetListPage(obj,url){//
			window.location.href=webPath+"/"+url;
		}
	
	
		function addCwFication(){
			top.addFlag = false;
		 	top.htmlStrFlag = false;
			top.createShowDialog("${webPath}/cwFication/input","新增辅助项",'90','90',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
				/* if(top.htmlStrFlag){
					var tableHtml = $(top.htmlString).find("tbody").html();
					$(".table_content").find("tbody").html(tableHtml);
				
				} */
				}
			});
		
		}
		function getFicationById(url){
			top.addFlag = false;
		 	top.htmlStrFlag = false;
			top.createShowDialog(webPath+"/"+url,"辅助项详情",'90','90',function(){
			if(top.addFlag){
			updateTableData();//重新加载列表数据
				
			} 
			});
		}
		/* function addFicationByType(url) {
			top.addFlag = false;
			top.htmlStrFlag = false;
			//top.showDialog(url,"新增辅助核算数据", "50","70");
		 	top.openBigForm(url,"新增辅助核算数据",'50','70',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
			}
			}); 
		} */
		
		function sysDataSync(type){
		LoadingAnimate.start();
			$.ajax({
				url:webPath+"/cwFicationData/sysDataSyncAjax",
		 		data:'type='+type,
		 		type:'POST',
		 		dataType:'json',
		 		success:function(data){
		 			LoadingAnimate.stop();
		 			if(data.flag=='success'){
		 				alert(data.msg,1);
		 			}else{
		 				alert(data.msg,0);
		 			}
		 		},error:function(){
		 			LoadingAnimate.stop();
		 			alert(top.getMessage("FAILED_OPERATION"),0);
		 		}
		 	})
		}
		function SyncThis(dom){
			var typeDesc=$(dom).parents("tr").find("td:eq(0)").text();
			var type="";
			if(typeDesc=="员工"){
				type="0";
			}else if(typeDesc=="部门"){
				type="1";
			}else if(typeDesc=="客户"){
				type="2";
			}else{
				type="";
			}
			sysDataSync(type);
		}
	</script>
	
</html>
