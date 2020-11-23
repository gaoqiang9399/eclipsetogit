<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		//var cusType = '${cusType}';
			$(function(){
			      myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusCustomer/getCusListAjax",//列表数据查询的url
			    	tableId:"tableCustomerList0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			    
			    // addCheckAllEvent();
			   // $("input[name='cusTel']").val(123);
			 });
		
			
		</script>
	</head>
	<body class="bodybg-gray" style="background: white;">
		
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- <div class="btn-div">
						<button type="button" class="btn btn-primary" id="batchRe_btn" onclick="bacthCusTes();">批量选择</button>
				</div> -->
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
			function choseCus(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var cusNo = parmArray[0].split("=")[1];
				
				$.ajax({
					url:webPath+"/mfCusCustomer/getCusByIdAjax?cusNo="+cusNo,
					dataType:"json",
					type:"POST",
					success:function(data){
						if(data.flag == "success"){
							//alert(data.cusInfo.cusTel+"/"+data.cusInfo.cusNo)
							//parent.dialog.get('cusDialog').close(data.cusInfo);
							 // top.getTel(data.cusInfo);
							  top.cwBackData = data.cusInfo;
							
							//alert($("input[name=cusTel]").val());
							myclose_showDialogClick();
						}else{
							alert("获取客户信息失败");
						}
					},error:function(){
						alert("获取客户信息出错");
					}
				});
			};
			
			//去除表头 点击事件 换成 全选事件
			var isCheckAll = false;
			function addCheckAllEvent() {
			 	$(".table-float-head").delegate("th:first-child","click",function(){
					if (isCheckAll) {
						$(".cusTel_list").find(':checkbox').each(function() {
							this.checked = false;
						});
						isCheckAll = false;
					} else {
						$(".cusTel_list").find(':checkbox').each(function() {
							this.checked = true;
						});
						isCheckAll = true;
					}
				});
			}
			
			function bacthCusTes(){
				var tranceNos = getCheckedNos();
				// top.cwBackData = data.cusInfo;
				//alert(tranceNos);
				 top.cwBackData = tranceNos;
				//alert($("input[name=cusTel]").val());
				myclose_showDialogClick();
			}
			
	</script>
	
	
</html>
