<%@ page language="java" import="java.util.*,app.base.User,org.apache.struts2.ServletActionContext" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String userNo = User.getRegNo(ServletActionContext.getRequest());
%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
<script type="text/javascript" src="${webPath}/component/rec/js/recallBase_Pers.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript">
	var userNo = '<%=userNo%>';
	$(function() {
// 		$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					theme:"minimal-dark",
// 					updateOnContentResize:true
// 				}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/recallBase/findByPageAjax",//列表数据查询的url
			tableId : "tablerec0010",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true,//是否有我的筛选
			data:{
				userNo:userNo
			},
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	});
	function updateRecallBaseForm(obj, url){
		top.createShowDialog(url,'催收回执信息登记','90','90',closeCallBack);
	}
	function openDetailInfo(obj, url){
		top.openBigForm(url,'催收任务详细信息','90','90');
	}
</script>
<style type="text/css">
.table_content .ls_list tbody tr.selected{
	background-color:#F7F7F7;
	}
</style>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<!--<button type="button" class="btn btn-primary pull-left" onclick="openConSelectTableList('${webPath}');">新增</button>-->
					<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
							<li class="active">我的催收任务</li>
					</ol>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称,项目名称,合同号码"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
					<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>

<script type="text/javascript">
//{"optCode":"mustCompleteDate","optName":"催收完成日期","dicType":"date"},{"optCode":"regDate","optName":"登记日期","dicType":"date"}
filter_dic =[{"optCode":"mustCompleteDate","optName":"催收完成日期","dicType":"date"},{"optCode":"regDate","optName":"登记日期","dicType":"date"},{"optCode":"recallAmt","optName":"催收金额","dicType":"num"},{"optCode":"recallWay","optName":"催收方式","parm":[{"optName":"短信催收","optCode":"1"},{"optName":"电话催收","optCode":"2"},{"optName":"信函催收","optCode":"3"},{"optName":"外访催收","optCode":"4"},{"optName":"委外催收","optCode":"5"},{"optName":"法务催收","optCode":"6"}],"dicType":"y_n"}];</script>
</html>
