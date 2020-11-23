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
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/MfBusCensorFile/findByPageBaseTypeAjax",
			    	//列表数据查询的url
			    	tableId:"tablecensorbasetype",//列表数据查询的table编号
			    	tableType:"thirdTableTag"//table所需解析标签的种类
		    		/* callback:function(){
				    	$("table").tableRcswitcher({
				    	name:"userFlag",onText:"生效",offText:"失效"});
				    }// */
			    });
			    $(".table-float-th").parent().remove();
			    $("table.table-float-head").remove();
			 });
		function choseType(parm){
			parm=parm.split("?")[1];
			var parmArray=parm.split("&");
			var censorType = new Object();
			censorType.censorNo = parmArray[0].split("=")[1];
			censorType.censorName = parmArray[1].split("=")[1];
			//这里最好remove掉
			//因为如果多个需要选择用户，它们的回调函数不一样，如果不remove掉，会导致回调函数的混乱。
			parent.dialog.get('censorTypeDialog').close(censorType).remove();
		};
		</script>
	</head>
	<body class="bg-white">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		</div>
		
	</body>	
</html>
