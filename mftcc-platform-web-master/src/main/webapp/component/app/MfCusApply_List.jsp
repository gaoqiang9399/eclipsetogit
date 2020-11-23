<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript" src="<%=webPath %>/component/app/js/MfCusApply_List.js"></script>
    <script type="text/javascript" src="<%=webPath %>/component/include/myRcswitcher.js"></script>
    <script type="text/javascript">
        var cusNo='${cusNo}';
        $(function() {
            mfCusApply_List.init();
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div style="display:none;">
                <input name="kindName" type="hidden"></input>
                <input name="kindNo" type="hidden"></input>
            </div>
            <div class="search-div" id="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/证件号码"/>
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
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic = [ {
        "optName" : "客户类别",
        "parm" : ${RECOMMEND_CUS_TYPE},
        "optCode" : "cusType",
        "dicType" : "y_n"
    },{
        "optName" : "客户分类",
        "parm" : ${CLASSIFY_TYPE},
        "optCode" : "classifyType",
        "dicType" : "y_n"
    },{
            "optName" : "客户状态",
            "parm" : ${RECOMMEND_CUS_TST},
            "optCode" : "groomflag",
            "dicType" : "y_n"
        }
    ];
</script>
</html>
