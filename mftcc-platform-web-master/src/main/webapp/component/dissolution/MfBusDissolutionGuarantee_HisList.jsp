<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript" src="${webPath}/component/dissolution/js/MfBusDissolutionGuarantee.js"></script>

    <script type="text/javascript" >
        $(function(){
            myCustomScrollbar({
                obj : "#content", //页面内容绑定的id
                url : webPath+"/mfBusDissolutionGuarantee/findByDissolutionPageAjax", //列表数据查询的url
                tableId : "tablebusDissolutionGuaranteeHisList", //列表数据查询的table编号
                tableType : "tableTag", //table所需解析标签的种类
                pageSize:30, //加载默认行数(不填为系统默认行数)
                topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
            });
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12">
            <div class="btn-div">
                <div class="col-md-12 text-center">
                    <span class="top-title">解保历史</span>
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
<script type="application/javascript">
</script>
</html>
