<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" >
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/archiveInfoMain/findByPageAjax?archiveStatus=01",//列表数据查询的url
			tableId : "tablearchivemainconfim",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	});

	//归档确认
	function inputConfim(lk){
		top.openBigForm(webPath+lk, "归档确认", function () {
			window.location.reload();
		});
	}

	function getDetailPage(object, url) {
		top.LoadingAnimate.start();
		window.location.href = url;
	}

	function downloadFile(url) {
		top.LoadingAnimate.start();
		var param = url.split("?")[1];
		$.ajax({
			url:webPath+'/archiveInfoMain/compressDownloadFileAjax?' + param,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.success){
					window.location.href = url;
					setTimeout("top.LoadingAnimate.stop()", 500);
				} else {
					top.LoadingAnimate.stop()
					window.top.alert(data.msg, 0);
				}
			}, error:function(){
				top.LoadingAnimate.stop()
				window.top.alert("请求链接" + url + "失败！", 0);
			}
		});
	}

	function archiveInfoMainList() {
		window.location.href=webPath+"/archiveInfoMain/getListPage";
	}

	function archiveInfoDetailList() {
		window.location.href=webPath+"/archiveInfoDetail/getListPage";
	}
</script>
</head>
<body class="overflowHidden">
   <div class="container">
   		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<div class="col-md-2">
	  				</div>
	  				<div class="col-md-8 text-center">
	  					<span class="top-title">归档确认</span>
	  				</div>
	  				<div class="col-md-2">
	  				</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/合同编号"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content">
				</div>
			</div>
		</div>
    </div>
    <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		filter_dic = [{
			"optCode" : "archiveDate",
			"optName" : "归档日期",
			"dicType" : "date"
		} ,{
			"optCode" : "archiveStatus",
			"optName" : "档案状态",
			"parm" : ${statusJsonArray},
			"dicType" : "y_n"
		}];
	</script>
</html>