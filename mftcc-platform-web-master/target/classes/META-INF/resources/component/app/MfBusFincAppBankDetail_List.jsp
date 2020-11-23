<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
	</head>
	<script type="text/javascript" >
        var inoutFlag='${inoutFlag}';
        var exportId = '${exportId}';
        $(function(){
            myCustomScrollbar({
                obj:"#content",//页面内容绑定的id
                url:webPath+"/mfBusFincApp/getBankAgriculturalDetail?exportId="+exportId,//列表数据查询的url
                tableId:"tablemfBusFincAppBank001",//列表数据查询的table编号
                tableType:"thirdTableTag",//table所需解析标签的种类
                pageSize : 30,//加载默认行数(不填为系统默认行数)
                topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
            });
        });
	</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=付款人姓名"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<%--<div class="col-md-12 column">--%>
			<div id="content" class="table_content"  style="height: auto;">
			</div>
		<%--</div>--%>
	</div>

	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
	<script type="text/javascript">
        filter_dic = [
			{
                "optCode" : "feiYongHeJi",
                "optName" : "交易金额",
                "parm" :  "",
                "dicType" : "num"
            }
		];
	</script>
</html>