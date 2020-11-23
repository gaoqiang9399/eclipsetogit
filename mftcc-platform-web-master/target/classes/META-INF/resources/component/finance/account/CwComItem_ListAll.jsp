<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		 <%-- <link rel="stylesheet" type="text/css" href="${webPath}/layout/view/page/css/C.css" /> --%>
		 <style type="text/css">
		 	.backS1{}
		 	.backS2{margin-right: 15px;}
		 	.backS3{margin-right: 30px;}
		 	.backS4{margin-right: 45px;}
		 	.backS5{margin-right: 45px;}
		 	.backS6{margin-right: 55px;}
		 	.backS7{margin-right: 65px;}
		 	.backS8{margin-right: 75px;}
		 	.backS9{margin-right: 85px;}
		 	.backS10{margin-right: 95px;}
		 
		 </style>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwComItem/findByPageAjax",//列表数据查询的url
			    	tableId:"tablecomItem0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 
			 function choseCwComItem(parm){
			 	console.log(parm);
			 	
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var cwComItemInfo = new Object();
				cwComItemInfo.accNo = parmArray[0].split("=")[1];
				cwComItemInfo.accName = parmArray[1].split("=")[1];
				cwComItemInfo.accHrt = parmArray[2].split("=")[1];
				parent.dialog.get('cwComItemDialog').close(cwComItemInfo);
			}
			
		</script>
	</head>
	<body>
		

	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<!-- 我的筛选选中后的显示块 -->
			<div class="search-div col-xs-8 pull-right"">
					<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
			</div>
		</div>
	</div>
		
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
	</body>	
	<script type="text/javascript">
	/* 	
		$(".level01").bind('click',function(){
			var that = $(this);
				$(".level01").removeClass('mySelectedNode');
				that.addClass('mySelectedNode');
		})
		 */
	</script> 
	<script type="text/javascript" src="${webPath}/component/finance/account/js/CwComItem_List.js"></script>

</html>
