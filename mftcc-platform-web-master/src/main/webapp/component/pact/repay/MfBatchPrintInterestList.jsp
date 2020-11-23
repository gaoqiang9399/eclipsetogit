<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>小额贷款股份有限公司</title>
    <style type="text/css">
        .title {
            width: 600px;
            margin: 0 auto;
        }

        .title h3 {
            text-align: center;
            font-size: 24px;
            letter-spacing: 0.1em;
        }

        .title h5 {
            text-align: center;
            font-size: 20px;
            letter-spacing: 0.1em;
        }

        .title span {
            display: block;
            text-align: center;
            font-size: 14px;
        }

        table tr {
            height: 22px;
            width: 10px;
        }

        table caption {
            text-align: left;
            color: #000;
        }

        table tr td {
            border: 1px solid #000;
            padding: 2px 4px;
            line-height: 2em;
        }

        td {
            word-break: break-all;
            width: 70px;
        }

        .lastTd {
            word-break: break-all;
            width: 100px;
        }

        .lender {
            height: 80px;
        }

        .unit {
            align: center;
        }

        .end {
            display: block;
            text-align: center;
            font-size: 14px;
            margin-top: 10px;
        }
        .date{
            margin-bottom: 6px;
        }

    </style>
    <script type="text/javascript">
        $(function () {
            $("body").removeClass('modal-open');
        });
    </script>
</head>
<body>
<c:forEach items="${listData}" var="obj" varStatus="status">
    <c:if test="${obj.mfRepayRecheck != null}">
        <div class="title">
            <h3>小额贷款股份有限公司</h3>
            <h5>利息单</h5>
            <span class="date">收款日期：${fn:substring(obj.mfRepayRecheck.regDate, 0, 4)}&nbsp;-
				${fn:substring(obj.mfRepayRecheck.regDate, 4, 6)}&nbsp;-
				${fn:substring(obj.mfRepayRecheck.regDate, 6, -1)} </span>
        </div>
        <table width="800" height="200" border="1" align="center">
            <tr>
                <td colspan="2">单位名称</td>
                <td colspan="2">${obj.mfRepayRecheck.cusName}</td>
                <td colspan="2">合同编号</td>
                <td colspan="2" class="lastTd">${obj.mfRepayRecheck.pactNo}</td>
            </tr>
            <tr>
                <td colspan="2">本金</td>
                <td colspan="2">${obj.mfRepayRecheck.prcpSum}</td>
                <td colspan="2">本期起止日期</td>
                <td colspan="2" class="lastTd">${obj.mfRepayRecheck.currentDate}</td>
            </tr>
            <tr>
                <td colspan="2">利率</td>
                <td colspan="6">${obj.mfRepayRecheck.fincRate}</td>

            </tr>
            <tr>
                <td colspan="2">本期应付利息（大写）</td>
                <td colspan="2">${obj.mfRepayRecheck.currentRepayIntstCh}</td>
                <td colspan="2">本期应付利息（小写）</td>
                <td colspan="2" class="lastTd">${obj.mfRepayRecheck.currentRepayIntst}</td>
            </tr>


        </table>
        <span class="end">小额贷款股份有限公司(财务电子章）</span>
    </c:if>
    <div style="page-break-after: always;"></div>
</c:forEach>
</body>
</html>