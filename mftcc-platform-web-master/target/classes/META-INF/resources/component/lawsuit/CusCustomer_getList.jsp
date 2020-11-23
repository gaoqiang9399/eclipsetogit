<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var applyFlag='0000';
			$(function(){
			      myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusCustomer/getCusListAjax",
			    	tableId:"tablecuscustomerlist",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body class="bodybg-gray" style="background: white;">
		
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=客户名称"/>
						<!-- end -->
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!--页面显示区域-->
				 <div id="content" class="table_content cusTel_list"  style="height: auto;">
				</div> 
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
			function choseCredit(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var assetId = parmArray[0].split("=")[1];
                var applyFlag='${applyFlag}';
				$.ajax({
					url:webPath+"/mfLawsuit/getInfo",
					dataType:"json",
					data:{assetId:assetId,applyFlag:applyFlag},
					type:"POST",
					success:function(data){
						if(data.flag == "success"){
							  top.cwBackData = data.formData;
							  top.cwBackData.htmlStr = data.htmlStr;
							  top.cwBackData.bean = data.bean;
							  myclose_showDialogClick();
						}else{
							alert("获取客户信息失败",0);
						}
					},error:function(){
						alert("获取客户信息出错",0);
					}
				});
			};
			function bacthCusTes(){
				var tranceNos = getCheckedNos();
				top.cwBackData = tranceNos;
				myclose_showDialogClick();
			}
	</script>
	
	
</html>
