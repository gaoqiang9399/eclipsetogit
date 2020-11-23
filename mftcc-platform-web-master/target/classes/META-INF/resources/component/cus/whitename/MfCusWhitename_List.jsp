<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/cus/whitename/js/MfCusWhitename_List.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		 <style>
		.webuploader-element-invisible {
			position: absolute !important;
			clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
		    clip: rect(1px,1px,1px,1px); 
		}

		.tab-ul li {
			display:inline-block;
			margin-right:10px;
			color:#32b5cb;
			cursor: pointer;
		}
		.tab-ul li:hover {
			color:#009db7;
		}

		</style> 
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
					obj : "#content", //页面内容绑定的idwebPath+"/mfCusGroup/findByPageAjax"
					url : webPath+"/mfCusWhitename/findByPageAjax", //列表数据查询的url
					tableId : "tablewhitename0001", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:15 //加载默认行数(不填为系统默认行数)
					,topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
		    	MfCusWhitename_List.init();//初始化方法
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfCusWhitename_List.finForminput();">新增</button>
							<input name="uploadFile" readonly="readonly" type="text" class="form-control" style="display:none;">
 							<button id="picker"  readonly="readonly" type="button" class="btn btn-primary">导入</button>
							&nbsp;
							<a href="/mfCusWhitename/downloadTemplate">模板下载</a>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">白名单管理</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=客户名称"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
