<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/dwr.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/interface/ScenceDwr.js'></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/scence/findByPageAjax",//列表数据查询的url
			    	tableId:"tableparam0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	callback:function(){
				    	$("table").tableRcswitcher({
				    	name:"useFlag",onText:"使用",offText:"未使用"});
				    }//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			  function getDetail(url){
			 	window.top.openBigForm(url,'业务场景详情');
			 }
			 function getUpdate(obj,url){
			 	var scNo = url.split("=");
			 	ScenceDwr.ifIsUsed(scNo[1],function(data){
			 		if(data!=null && data != ""){
			 			if(data=="success"){
			 				window.top.openBigForm(url,'业务场景更新');
			 			}else{
			 				alert("当前业务场景已生效不能修改！",0);
			 			}
			 		}else{
			 			alert(top.getMessage("FAILED_OPERATION"," "),0);
			 		}
			 	});
			 	
			 	
			 }
			 /* if(ScenceDwr.ifIsUsed(scNo[1])){
			 		window.top.openBigForm(url,'业务场景更新');
			 	}else{
			 		alert("当前业务场景已生效不能修改",0);
			 	} */
		</script>
	</head>
	<body>
	<dhcc:markPoint markPointName="Scence_List"/>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >业务场景详情</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<dhcc:thirdButton value="新增" action="Scence001" onclick="window.top.openBigForm('${webPath}/scence/input','业务场景新增')"></dhcc:thirdButton>
				<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input placeholder="智能搜索" id="filter_in_input"  class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search"  class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
							<button id="fiter_ctrl_btn" class="filter_btn_myFilter" type="button"  ><i class="fa fa-caret-down"></i></button>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
				addDefFliter("0", "业务场景类型", "scenceType", "SCENCE_TYPE", "01,02,03");
				addDefFliter("0", "生效标识", "useFlag", "USE_FLAG", "0,1");
			</script>
</html>
