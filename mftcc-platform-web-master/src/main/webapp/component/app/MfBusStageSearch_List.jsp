<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript">
        $(function () {
            myCustomScrollbar({
                obj: "#content",//页面内容绑定的id
                url: webPath + "/mfBusStageSearch/findByPageAjax",//列表数据查询的url
                tableId: "tableMfBusStageSearch",//列表数据查询的table编号
                tableType: "thirdTableTag",//table所需解析标签的种类
                pageSize: 30//加载默认行数(不填为系统默认行数)
            });
        });

        function getDetailPage(obj, url) {
            if (url.substr(0, 1) == "/") {
                url = webPath + url;
            } else {
                url = webPath + "/" + url;
            }
            top.openBigForm(url, "客户详情", function () {
                getListInfo();
            });
        };

        function getAppDetailPage(obj, url) {
            url = url.split("?")[1].split("&");
            var fincId = "";
            var appId = url[1].split("=")[1];
            var busEntrance = url[2].split("=")[1];
            if ("1" == busEntrance) {
                url = webPath + "/mfBusApply/getSummary?appId=" + appId + "&busEntrance=" + busEntrance + "&subStringNub=";
            } else if ("2" == busEntrance) {
                url = webPath + "/mfBusPact/getById?appId=" + appId + "&busEntrance=" + busEntrance + "&subStringNub=";
            } else if ("3" == busEntrance) {
                fincId = url[0].split("=")[1];
                url = webPath + "/mfBusPact/getPactFincById?fincId=" + fincId + "&appId=" + appId + "&busEntrance=" + busEntrance + "&subStringNub=";
            }
            top.openBigForm(url, "项目详情", function () {
                getListInfo();
            });
        };
    </script>
</head>
<body class="overflowHidden">

<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div class="btn-div top-title">业务阶段查询</div>
            <div class="search-div" id="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/业务阶段"/>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="content" class="table_content" style="height: auto;">
            </div>
        </div>
    </div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp" %>
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic = [
        {
            "optName": "项目金额",
            "parm": [],
            "optCode": "appAmt",
            "dicType": "num"
        },{
            "optName": "期限",
            "parm": [],
            "optCode": "term",
            "dicType": "num"
        }, {
            "optCode": "busStage",
            "optName": "业务阶段",
            "parm": ${flowNodeJsonArray},
            "dicType": "y_n"
        }
    ];
</script>
</body>
</html>