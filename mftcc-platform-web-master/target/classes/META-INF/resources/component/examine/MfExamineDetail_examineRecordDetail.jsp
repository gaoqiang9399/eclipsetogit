<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
            <div class="btn-div">
                <div class="col-md-2">
                    <%--<dhcc:pmsTag pmsId="query_examine_info">
                        <button type="button" class="btn btn-primary" onclick="addExamineDetail('${webPath}/MfExamineDetailController/addExamineDetail');">新增</button>
                    </dhcc:pmsTag>--%>
                </div>
                <div class="col-md-8 text-center">
                    <span class="top-title">贷后检查详情</span>
                </div>
            </div>


            <%--<div class="search-div" id="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
            </div>--%>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="content" class="table_content"  style="height: auto;">
            </div>
        </div>
    </div>
</div>
<%--<%@ include file="/component/include/PmsUserFilter.jsp"%>--%>
</body>
<script type="text/javascript">
    /*我的筛选加载的json*/
    /*filter_dic =[{
        "optName": "检查主体",
        "parm": ${examineDataObjJsonArray},
        "optCode":"pasMinNo",
        "dicType":"y_n"
    }
    ];*/
    $(function(){
        var examHisId = "${examHisId}";
        myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/MfExamineDetailController/findExamineDetailByAjax",//列表数据查询的url
            tableId:"tableexamineDetailList",//列表数据查询的table编号
            tableType:"thirdTableTag",//table所需解析标签的种类
            myFilter:false,
            pageSize:0,//加载默认行数(不填为系统默认行数)
            data:{examHisId:examHisId}
        });
    });
</script>
</html>