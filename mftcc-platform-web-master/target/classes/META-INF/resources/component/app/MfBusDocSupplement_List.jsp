<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusDocSupplement/findByPageAjax",//列表数据查询的url
			    	tableId:"tablemfBusDocSupplement",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			function getDetailPage(obj,url){	
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,"客户详情", function(){
			 			getListInfo();
			 		});			
			};
			
			function getDetail(obj,url){	
				top.openBigForm(url,"上传资料", function(){});			
			};
			
			function getAppDetail(obj,url){	
				top.openBigForm(url,"业务详情", function(){});			
			};
			
		</script>
	</head>
	<body class="overflowHidden">
		
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div top-title">业务要件补充</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/合同编号"/>
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript">
		filter_dic =[{"optCode":"fincSts","optName":"借据状态","parm":[{"optName":"未提交","optCode":"1"},{"optName":"审核中","optCode":"2"},{"optName":"已否决","optCode":"3"},{"optName":"审批通过待放款","optCode":"4"},{"optName":"审批通过已复核","optCode":"5"},{"optName":"已还款未复核","optCode":"6"},{"optName":"完结","optCode":"7"}],"dicType":"y_n"}];
		</script>
	</body>	
</html>