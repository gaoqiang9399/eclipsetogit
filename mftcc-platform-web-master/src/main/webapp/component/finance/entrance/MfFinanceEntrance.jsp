<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${webPath}/component/finance/entrance/css/MfFinanceEntrance.css" />
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
                <c:if test="${mfQueryItem.msgCount > 0}">
                    <c:choose>
                        <c:when test="${mfQueryItem.msgCount>99}">
                            <span class="badge">99+</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge" >${mfQueryItem.msgCount}</span>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <div><i class="i i-${ mfQueryItem.itemIcon}"></i></div>
                <div><c:out value="${mfQueryItem.itemName}"></c:out></div>
            </div>
        </dhcc:pmsTag>
    </c:forEach>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/finance/entrance/js/MfFinanceEntrance.js"></script>
<script type="text/javascript">
    $(function() {
        MfFinanceEntrance.init();
    });
</script>
</html>