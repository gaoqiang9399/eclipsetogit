<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%--<%@ include file="/component/include/pub_view.jsp"%>  --%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript" src="${webPath}/component/encrypt/js/MfEncryptFields_List.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
    <link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
    <script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
    <script type="text/javascript" >
        $(function(){
            MfEncryptFields_List.init();
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div class="btn-div">
                    <button type="button" class="btn btn-primary" onclick="MfEncryptFields_List.input();">新增</button>
                    <button type="button" class="btn btn-primary" onclick="MfEncryptFields_List.cleanCache();">同步缓存</button>
                    <button type="button" class="btn btn-primary" onclick="MfEncryptFields_List.encryptHistoryData();">数据批量加密</button>
                    <button type="button" class="btn btn-primary" onclick="MfEncryptFields_List.dencryptHistoryData();">数据批量解密</button>
            </div>
            <div class="search-div" id="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=表名/字段"/>
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
</body>
</html>
