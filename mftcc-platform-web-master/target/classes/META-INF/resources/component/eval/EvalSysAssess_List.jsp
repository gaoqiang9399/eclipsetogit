<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/component/eval/js/evalSysAssess.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/evalSysAssess/findByPageAjax",//列表数据查询的url
			    	tableId:"tableeval4001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	myFilter:true //是否有我的筛选(列表列动态切换)
			    });
			 });
			 
			 //编辑模型基本信息
			function editEvalSysAssess(obj,url){
			   top.updateFlag = false;
			   top.window.openBigForm(url,'评级级别配置',closeCallBack);
			}
			
			 
			function closeCallBack(){
				if(top.updateFlag){
					window.location.href=webPath+"/evalSysAssess/getListPage";
				}
			}
			 
			 
		</script>
		<style type="text/css">
			.formCol {
			    display: block;
			    margin-bottom: 2px;
			    margin-top: 1px;
			}
			.rightForm-table .formCol textarea {
				height: 25px;
				width: 90%;
				resize:none;
				padding:0 0 0 3px;
			}
			.rightForm-table .formCol input[type='text'][name='url']{
				width: 90%;
			}
		</style>
	</head>
	<body>
		<dhcc:markPoint markPointName="EvalSysAssess_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-md-9 column  color_theme">
							<ol class="breadcrumb pull-left padding_left_0">
								<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
								<li class="active">评级级别</li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
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