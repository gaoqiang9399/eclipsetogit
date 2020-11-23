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
                obj : "#content", //页面内容绑定的id
                url : webPath+"/mfRepayHistory/findRepayHisByPageAjax", //列表数据查询的url
                tableId : "tablerepayhis0001_query", //列表数据查询的table编号
                tableType : "thirdTableTag", //table所需解析标签的种类
                pageSize:30 //加载默认行数(不填为系统默认行数)
                //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
            });
        });
        function getDetailPage(obj,url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            top.window.openBigForm(url,"还款历史明细",function(){});
        }
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12">
			<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。-->
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
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称/借据编号"/>
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
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic = [
        {
            "optName" : "客户名称",
            "optCode" : "cusName",
            "dicType" : "val"
        },{
            "optName" : "项目名称",
            "optCode" : "appName",
            "dicType" : "val"
        },{
            "optName" : "借据号",
            "optCode" : "fincShowId",
            "dicType" : "val"
        },{
            "optName": "还款日期",
            "parm": [],
            "optCode":"repayDate",
            "dicType":"date"
        }
    ];
</script>
</html>
