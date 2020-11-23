<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${webPath}/component/archives/entrance/css/ArchiveEntrance.css" />
    <script>
        var list = [];
        list = ${dataMap.mfQueryItemList};
        for(var i = 0;i<list.length;i++){
            var item = list[i];
            var pmsBizNo= "第"+i+"次 pmsBizNo:"+item.pmsBizNo;
            var itemId= "第"+i+"次 itemId:"+item.itemId;
            var itemIcon= "第"+i+"次 itemIcon:"+item.itemIcon;
            var itemName= "第"+i+"次 itemName:"+item.itemName;
            console.log(pmsBizNo);
            console.log(itemId);
            console.log(itemIcon);
            console.log(itemName);
        }
    </script>
</head>
<body>
<div class="container box">
    <c:forEach items="${ dataMap.mfQueryItemList}" var="mfQueryItem">
        <dhcc:pmsTag pmsId="${ mfQueryItem.pmsBizNo}">
            <div class="btn btn-app" id="${ mfQueryItem.itemId}">
                <div><i class="i i-${ mfQueryItem.itemIcon}"></i></div>
                <div><c:out value="${mfQueryItem.itemName}"></c:out></div>
            </div>
        </dhcc:pmsTag>
    </c:forEach>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/archives/entrance/js/ArchiveEntrance.js"></script>
<script type="text/javascript">
    $(function() {
        ArchiveEntrance.init();
    });
</script>
</html>