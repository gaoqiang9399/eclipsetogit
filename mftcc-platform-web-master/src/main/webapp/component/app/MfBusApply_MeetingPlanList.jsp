<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
                init();
			 });
            function init() {
                LoadingAnimate.start();
                myCustomScrollbar({
                    obj:"#content",//页面内容绑定的id
                    url:webPath+"/mfBusApply/findByPageMeetingPlanAjax",//列表数据查询的url
                    tableId:"tableapplymeetingplanlist",//列表数据查询的table编号
                    tableType:"thirdTableTag",//table所需解析标签的种类
                    pageSize:30,//加载默认行数(不填为系统默认行数)
                    callback:function(){
                        LoadingAnimate.stop();
                    }//方法执行完回调函数（取完数据做处理的时候）
                });
            }
		</script>
	</head>
	<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=上会时间"/>
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
