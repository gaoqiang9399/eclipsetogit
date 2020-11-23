<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href="${webPath}/component/sys/css/sysUser.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" >
		var ajaxData = '${ajaxData}';
	    ajaxData = eval("("+ajaxData+")");
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/sysUser/findByPageAjax2",//列表数据查询的url
			    	tableId:"tablesysuser0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag"//table所需解析标签的种类
		    		/* callback:function(){
				    	$("table").tableRcswitcher({
				    	name:"userFlag",onText:"生效",offText:"失效"});
				    }// */
			    });
			    
			 });
		function choseUser(parm){
			parm=parm.split("?")[1];
			var parmArray=parm.split("&");
			var userInfo = new Object();
			userInfo.opNo = parmArray[0].split("=")[1];
			userInfo.opName = parmArray[1].split("=")[1];
			//这里最好remove掉
			//因为如果多个需要选择用户，它们的回调函数不一样，如果不remove掉，会导致回调函数的混乱。
			parent.dialog.get('userDialog').close(userInfo).remove();
		};
		</script>
	</head>
	<body class="bg-white">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=操作员名称/操作员编号"/>
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
