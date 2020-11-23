<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
            $(function () {
                myCustomScrollbar({
                    obj: "#content", //页面内容绑定的id
                    url: webPath + "/mfBusAlliance/findByPageAjax", //列表数据查询的url
                    tableId: "tablealliance0001", //列表数据查询的table编号
                    tableType: "tableTag", //table所需解析标签的种类
                    pageSize: 30, //加载默认行数(不填为系统默认行数)
                    topHeight: 100, //顶部区域的高度，用于计算列表区域高度。
                });
            });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">

					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary" onclick="MfBusAllianceList.allianceInsert(this);">新增</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">联保体</span>
						</div>
					</div>

					<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=联保体名称"/>
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
	<script type="text/javascript" src="${webPath}/component/alliance/js/MfBusAllianceList.js"></script>
</html>
