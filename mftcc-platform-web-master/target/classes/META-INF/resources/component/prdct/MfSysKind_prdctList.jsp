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
	    	url:webPath+"/mfSysKind/findByPageAjax",//列表数据查询的url
	    	tableId:"tablekind0001",//列表数据查询的table编号
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
			};
			
			function enterClick(lk){
				var parm=lk.split("?")[1];
				var parmArray=parm.split("&");
				var kindNo = parmArray[0].split("=")[1];
				//跳页面
				var url=webPath+"/mfBusApply/input?query='0'&kindNo="+kindNo+"&cusNo="+cusNo;
				$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
				$(top.window.document).find("#showDialog").remove();

			};
			function enterClickForMulti() 
			{
				var elements=document.getElementsByName("wkfUserName");
				var selectedValue = ""
				var userId="";
				var userName="";
				var selectedUserIdValue="";
				var selectedUserNameValue="";
				for(var i=0; i<elements.length; i++) 
				{
					if(elements[i].checked)
					{
						var elementValue=elements[i].value;
						userId=elementValue.split("&")[0];
						userId=userId.split("=")[1];
						selectedUserIdValue=selectedUserIdValue+userId+",";
						userName=elementValue.split("&")[1];
						userName=userName.split("=")[1];
						selectedUserNameValue=selectedUserNameValue+userName+",";
					}
				}
				if( selectedUserIdValue == "" ) {
					alert("请选择用户！");
					return;
				}
				selectedUserIdValue=selectedUserIdValue.substring(0,selectedUserIdValue.length-1);
				selectedUserNameValue=selectedUserNameValue.substring(0,selectedUserNameValue.length-1);
				selectedValue=selectedUserIdValue+":"+selectedUserNameValue;
				window.returnValue = selectedValue;
				window.close();
				return;
			};
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