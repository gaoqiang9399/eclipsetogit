<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>同盾核查</title>
</head>
<script language="javascript">
    function uploadTTYYpdf(authCode) {
        var ttyyHtml = $("#ttyyHtml").html();
        ttyyHtml = '<xmp>' + ttyyHtml + '</xmp>';
        $.ajax({
            url: webPath + "/apiReturnRecord/showTTYYpdf",
            type: "post",
            data: {"ttyyHtml": ttyyHtml},
            async: false,
            dataType: "json",
            error: function () {
                alert("pdf生成失败");
            },
            success: function (data) {
                top.openBigForm(webPath + '/apiReturnRecord/showpdf?authCode=' + data.authCode, 'pdf打印', function () {
                });
            }
        });
    }
</script>
<link rel="stylesheet" href="${webPath}/component/thirdservice/tongdu/css/MfCus_RisManagemenTDHtml.css"/>
<script type="text/javascript">
    var requestParaMap = '${requestParaMap}';
    var riskItemsList = '${riskItemsList}';
    var authCode = '${authCode}';
    $(function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                theme: "minimal-dark",
                updateOnContentResize: true
            }
        });
    });
</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn" id="ttyyHtml">
                <div class="rep-main">
                    <c:if test="${message == null || message == ''}">
                        <div><font size="5">同盾接口—${message} !!!</font></div>
                    </c:if>
                    <c:if test="${message != null}">
                        <div class="rep-item">
                            <button class="btn btn-primary dropdown-toggle" onclick="uploadTTYYpdf(authCode)">打印结果</button>
                            <table class="rep-table">
                                <thead>
                                <tr>
                                    <th colspan="4">同盾搜素条件</th>
                                </tr>
                                <tr></tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="text-center width-15"><span
                                            class="rep-th-span padding-left-20">关系人</span>
                                    </th>
                                    <td class="width-10"><span
                                            class="rep-td-span padding-left-20">${requestParaMap.partyName}</span>
                                    </td>
                                    <th class="text-center width-15"><span
                                            class="rep-th-span padding-right-20">手机号</span>
                                    </th>
                                    <td><span
                                            class="rep-td-span padding-left-20">${requestParaMap.partyTel}</span>
                                    </td>
                                    <th class="text-center width-15"><span
                                            class="rep-th-span padding-left-20">身份证号</span>
                                    </th>
                                    <td ><span
                                            class="rep-td-span padding-left-20">${requestParaMap.partyIdNum}</span></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="rep-item">
                            <table class="rep-table">
                                <thead>
                                <tr>
                                    <th colspan="4">同盾返回结果</th>
                                </tr>
                                <tr></tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class=" text-center "><span class="rep-th-span padding-left-20">风险分数</span>
                                    </th>
                                    <td class=""><span
                                            class="rep-td-span padding-left-20">${finalScore}</span>
                                    </td>
                                    <th class=" text-center "><span
                                            class="rep-th-span padding-right-20">决策结果</span>
                                    </th>
                                    <td class=" "><span
                                            class="rep-td-span padding-left-20">${finalDecision}</span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="rep-item">
                            <table class="rep-table">
                                <thead>
                                <tr>
                                    <th colspan="3">风险项目信息</th>
                                </tr>
                                <tr></tr>
                                </thead>
                                <c:forEach items="${riskItemsList}" varStatus="i" var="risk_items">
                                <tr>
                                    <th class=" text-center width-15"><span
                                            class="rep-th-span padding-left-20">策略模式(${i.index+1})</span>
                                    </th>
                                    <td class="width-20"><span
                                            class="rep-td-span padding-left-20">${risk_items.policy_mode}</span>
                                    </td>
                                    <th class="text-center width-15"><span
                                            class="rep-th-span padding-left-20">策略分数</span>
                                    </th>
                                    <td class="width-20"><span
                                            class="rep-td-span padding-left-20">${risk_items.policy_score}</span>
                                    </td>
                                    <th class=" text-center width-15"><span
                                            class="rep-th-span padding-right-20">策略名称</span>
                                    </th>
                                    <td  class="width-35"><span
                                            class="rep-td-span padding-left-20">${risk_items.policy_name}</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th class=" text-center  width-15"><span class="rep-th-span padding-left-20">风险分数</span>
                                    </th>
                                    <td><span
                                            class="rep-td-span padding-left-20">${risk_items.score}</span>
                                    </td>
                                    <th class="text-center  width-15"><span
                                            class="rep-th-span padding-left-20">决策结果</span>
                                    </th>
                                    <td class="width-15"><span
                                            class="rep-td-span padding-left-20">${risk_items.decision}</span>
                                    </td>
                                    <th class=" text-center width-15"><span
                                            class="rep-th-span padding-right-20">策略决策结果</span>
                                    </th>
                                    <td><span
                                            class="rep-td-span padding-left-20">${risk_items.policy_decision}</span>
                                    </td>
                                </tr>
                                <tr>
                                        <%--<th class=" text-center width-20"><span class="rep-th-span padding-left-20">规则编号</span>--%>
                                        <%--</th>--%>
                                        <%--<td class="width-20"><span--%>
                                        <%--class="rep-td-span padding-left-20">${risk_items.rule_id}</span>--%>
                                        <%--</td>--%>
                                    <th class="text-center width-15"><span class="rep-th-span padding-left-20">规则名称</span></th>
                                    <td colspan="5"><span class="padding-left-20">${risk_items.risk_name}</span></td>

                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div id="bussButton" class="formRowCenter">
        <input type="button" value="关闭" onclick="myclose();" class="cancel"></input>
    </div>
</div>
</body>
</html>