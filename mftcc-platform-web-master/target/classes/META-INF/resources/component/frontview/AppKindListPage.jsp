<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<% 
	String param  = (String)request.getParameter("param");
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<script type="text/javascript">
	$(function(){
		LoadingAnimate.start();
	    myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfFrontKind/getKindsByPageAjax",//列表数据查询的url
	    	tableId:"tablekindlist0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30,//加载默认行数(不填为系统默认行数)
	    	data:{},//指定参数
	    	callback:function(){
	    		LoadingAnimate.stop();
	    	}
	    });
	 });
	var cusNo = '<%=param%>';
	window.name = "curWindow";
			function cancelClick() {
				window.close();
			}
			
			function choseKind(lk) 
			{
				var parm=lk.split("?")[1];
				var parmArray=parm.split("&");
				var kindNameVal = parmArray[0].split("=")[1];
				var kindNoVal = parmArray[1].split("=")[1];
				//插入到appkind中
				var url=webPath+"/mfFrontKind/insertAjax";
				$.post(url,{kindNo:kindNoVal,kindName:kindNameVal},function(result){
						if(result.flag == 'success'){
							top.addFrontKindFlag=true;
							top.addFrontKindJsonObj[kindNoVal]=kindNameVal;
						}
						top.alert(result.msg,1);
						
  				});
			}
		
		</script>
	<body class="bg-white" style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div col-xs-6 pull-right"">
						<div class="znsearch-div">
							<div class="input-group pull-right">
								<i class="i i-fangdajing"></i>
								<input type="text" class="form-control" id="filter_in_input" placeholder="产品种类编号/产品种类名称">
								<span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
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