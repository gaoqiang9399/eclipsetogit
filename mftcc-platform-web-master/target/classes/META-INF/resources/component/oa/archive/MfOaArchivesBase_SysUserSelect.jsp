<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
				LoadingAnimate.start();
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfOaArchivesBase/getSysUserPageAjax",//列表数据查询的url
			    	tableId:"tableselectsysuser0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		LoadingAnimate.stop();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			function enterClick(parm){
				var parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var opNo = parmArray[0].split("=")[1];
				var data = new Object();
				data.opNo=opNo;
				parent.dialog.get('selectSysUserDialog').close(data).remove();
			};
		</script>
	</head>
	<body>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=操作员编号/姓名"/>
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

</html>
