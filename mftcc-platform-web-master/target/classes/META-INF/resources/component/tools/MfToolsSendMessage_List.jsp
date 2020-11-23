<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户手机号/短息内容"/>
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
</body>     
<%-- <script type="text/javascript" src="${webPath}/component/tools/MfToolsSendMessageList.js"></script> --%>
<script type="text/javascript">
		$(function (){
			myCustomScrollbar({
	    		obj:"#content",//页面内容绑定的id
	    		url:webPath+"/mfToolsSendMessage/findByPageAjax",//列表数据查询的url
	    		tableId:"tabletools0001",//列表数据查询的table编号
	    		tableType:"thirdTableTag",//table所需解析标签的种类
	        	pageSize:30//加载默认行数(不填为系统默认行数) 
	        	/* ownHeight : true, */
	   		 });	
		});
		/*我的筛选加载的json*/
		filter_dic = [
		      {
                  "optName": "手机号码",
                  "parm": [],
                  "optCode":"cusTel",
                  "dicType":"val"
              }, {
                  "optName": "发送时间",
                  "parm": [],
                  "optCode":"sendTime",
                  "dicType":"num"
              },
               {
                  "optName": "发送状态",
                  "optCode":"sendSts",
                  "parm": [{
                  "optName" : "成功",
					"optCode" : "0"
                  },{
					"optName" : "失败",
					"optCode" : "1"
				}],
                  "dicType":"SEND_STS"
              }, 
          ];
          
          function ajaxGetById(obj ,url){
          	top.addFlag = false;
			top.openBigForm(url,"短信内容", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		}
</script>
</html>


