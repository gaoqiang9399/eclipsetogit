<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			 	initPage();
			 });
			 function initPage(){
			 	 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaBadge/findByPageAjaxManage", //列表数据查询的url
				tableId : "tablebadge0002", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 }
		</script>
		<!-- ${webPath}/component/include/myCustomScrollbar.js -->
		<!-- /factor/WebRoot/component/oa/js/MfOaEntrance.js -->
		
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<!-- 新增用章申请按钮 -->
					<!--
					<div  class="btn-div">
							<button type="button" class="btn btn-info pull-left"
								onclick="openWindow231();">申请用章</button>
							<ol class="breadcrumb pull-left">
								<li class="active"></li>
							</ol>
					</div>
					-->
					
					<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="applyInsert();">进件</button>
					</div>
					-->
					<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=用章名称"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<!-- 测试弹框 -->
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
	<!-- /factor/WebRoot/component/oa/consumable/js/MfOaConsList.js -->
	<!-- ${webPath}/component/ -->
	<!--<script type="text/javascript" src="${webPath}/component/oa/consumable/js/MfOaConsList.js"></script>-->
	<script type="text/javascript" src="${webPath}/component/include/closePopUpBox.js"></script>
	
	<script type="text/javascript">
	function finForm_input(url){//新增弹框
		top.addFlag = false;
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.createShowDialog(url,"用章申请",'70','60',function(){
			if(top.addFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});
	}
	function openWindow231(){//大型新增弹框
		top.window.openBigForm(webPath+'/mfOaBadge/input','用章申请',function(){
			initPage();
		});
	}
	function getByIdThis(url){//详情弹框
	/*
		top.detailFlag = false;
		top.createShowDialog(url,"设置汇率",'70','60',function(){
			if(top.detailFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});
	*/	
		//新增大型弹框
		/*
		top.createShowDialog(url,"用章详情",'70','60',function(){
		
			if(top.detailFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});	
		*/
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
			top.window.openBigForm(url,'用章详情',function(){
				initPage();
			});
		}
		
		function submitBadge(url){
			window.top.alert(top.getMessage("CONFIRM_COMMIT"),2,function(){
			var ajaxParam = {};
			if(url.indexOf("ActionAjax_")!=-1&&url.indexOf("?")!=-1){//ajax提交
				var urlParams = url.split("?");
				url = urlParams[0];
				$.each(urlParams[1].split("&"), function(index,val){
					var key = val.split("=")[0];
					var value = val.split("=")[1];
					ajaxParam[key] = value;
				});
			}
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				data:ajaxParam,
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_OPERATION"),3);
						$(".ui-dialog-close").trigger("click");
						updateTableData();
						}else if(data.flag == "error"){
						alert(data.msg,3);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
		});
	}
	
	function outBadgeAjax(url){
		window.top.alert("您确定要进行出章操作吗?",2,function(){
			var ajaxParam = {};
			if(url.indexOf("ActionAjax_")!=-1&&url.indexOf("?")!=-1){//ajax提交
				var urlParams = url.split("?");
				url = urlParams[0];
				$.each(urlParams[1].split("&"), function(index,val){
					var key = val.split("=")[0];
					var value = val.split("=")[1];
					ajaxParam[key] = value;
				});
			}
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				data:ajaxParam,
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_OPERATION"),3);
						$(".ui-dialog-close").trigger("click");
						updateTableData();
						}else if(data.flag == "error"){
						alert(data.msg,3);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
		});
	}
	
	
	
	
		function revertBadgeAjax(url){
		window.top.alert("您确定要进行归还操作吗?",2,function(){
			var ajaxParam = {};
			if(url.indexOf("ActionAjax_")!=-1&&url.indexOf("?")!=-1){//ajax提交
				var urlParams = url.split("?");
				url = urlParams[0];
				$.each(urlParams[1].split("&"), function(index,val){
					var key = val.split("=")[0];
					var value = val.split("=")[1];
					ajaxParam[key] = value;
				});
			}
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				data:ajaxParam,
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						alert(top.getMessage("SUCCEED_OPERATION"),3);
						$(".ui-dialog-close").trigger("click");
						updateTableData();
						}else if(data.flag == "error"){
						alert(data.msg,3);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
		});
	}
	
	
	
	
	
	
	filter_dic = [
		      {
                  "optName": "用章名称",
                  "parm": [],
                  "optCode":"badageName",
                  "dicType":"val"
              },{
				"optCode" : "busiType",
				"optName" : "方式",
				"parm" : ${busiTypeJsonArray},
				"dicType" : "y_n"
			}
          ];
	
	
	</script>	
</html>