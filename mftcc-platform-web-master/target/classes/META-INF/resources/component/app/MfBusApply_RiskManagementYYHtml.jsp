<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>天天有余</title>
</head>
<style>
    #tableMsg td {
        text-align: center;
        text-align:center;
        text-overflow:ellipsis;
        white-space:nowrap;
        overflow:hidden;
    }
</style>
<script language="javascript">
    function uploadTTYYpdf(authCode) {
        var ttyyHtml = $("#ttyyHtml").html();
        ttyyHtml = '<xmp>'+ttyyHtml+'</xmp>';
        $.ajax({
            url:webPath+"/apiReturnRecord/showTTYYpdf",
            type:"post",
            data:{"ttyyHtml":ttyyHtml},
            async: false,
            dataType:"json",
            error: function () {
                alert("pdf生成失败");
            },
            success:function(data){
                top.openBigForm(webPath+'/apiReturnRecord/showpdf?authCode='+data.authCode,'pdf打印',function() {});
            }
        });
    }
</script>
<script type="text/javascript">
    var parmMap = '${dataMap.parmMap}';
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
<body class="bg-white">
<div class="col-md-10 col-md-offset-1 margin_top_20">
    <div class="bootstarpTag" id="ttyyHtml">
        <div class="rep-main">
            <div class="rep-item">
        <c:if test="${message != null}"><div><font size="5">天天有余接口查询失败！${message} !!!</font></div></c:if>
        <c:if test="${message == null || message == ''}">
        <table width="100%" id="tableTitle" border="0" cellpadding="0" cellspacing="0" style="table-layout:fixed" class="rep-table border-bot-table">
            <thead>
                <tr>
                    <button class="btn btn-primary dropdown-toggle" onclick="uploadTTYYpdf(authCode)" class="rep-label-2" >打印结果</button>
                </tr>
            </thead>
            <tr><th colspan="4" align="center" style="font-size:25px">进件反欺诈风险报告</th></tr>
            <tr>
                <td align="left">生成时间：</td><td align="left">${apiReturnRecord.txTime}</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td align="left">反欺诈扫描得分</td>
                <td align="left">
                    <c:if test="${apiReturnRecord.score == null || apiReturnRecord.score == ''}">无</c:if>
                    <c:if test="${apiReturnRecord.score != null}"><span>${apiReturnRecord.score}</span></c:if>
                </td>
                <td>风险等级：</td>
                <td align="left">
                    <c:if test="${apiReturnRecord.grade == null || apiReturnRecord.grade == ''}">无</c:if>
                    <c:if test="${apiReturnRecord.grade == '1'}"><span>高风险</span></c:if>
                    <c:if test="${apiReturnRecord.grade == '2'}"><span>中高风险</span></c:if>
                    <c:if test="${apiReturnRecord.grade == '3'}"><span>中风险</span></c:if>
                    <c:if test="${apiReturnRecord.grade == '4'}"><span>中低风险</span></c:if>
                    <c:if test="${apiReturnRecord.grade == '5'}"><span>低风险</span></c:if>
                </td>
            </tr>
            <tr>
                <td align="left">共发现新风险异常：</td>
                <td align="left">
                    <c:if test="${dataMap.riskLablList == null || dataMap.riskLablList == ''}">无</c:if>
                    <c:if test="${dataMap.riskLablList != null}"><span>${dataMap.riskLablList}条</span></c:if>
                </td>
                <td align="left">关联项目：</td>
            <td align="left">
                <c:if test="${mfBusApply.kindNo == null || mfBusApply.kindNo == ''}">无</c:if>
                <c:if test="${mfBusApply.kindNo != null}"><span>${mfBusApply.kindName}</span></c:if>
            </td>
            </tr>
            <tr><th style="font-size:20px">账户基本信息</th></tr>
            <tr>
                <td>申请借款金额：</td>
                <td>
                    <c:if test="${mfBusApply.appAmt == null || mfBusApply.appAmt == ''}">无</c:if>
                    <c:if test="${mfBusApply.appAmt != null}"><span>${mfBusApply.appAmt}</span></c:if>
                </td>
                <td>申请借款期限：</td>
                <td>
                    <c:if test="${mfBusApply.termShow == null || mfBusApply.termShow == ''}">无</c:if>
                    <c:if test="${mfBusApply.termShow != null}"><span>${mfBusApply.termShow}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>申请借款日期：</td>
                <td>
                    <c:if test="${mfBusApply.regDate == null || mfBusApply.regDate == ''}">无</c:if>
                    <c:if test="${mfBusApply.regDate != null}"><span>${mfBusApply.regDate}</span></c:if>
                </td>
                <td>借款用途：</td>
                <td>
                    <c:if test="${mfBusApply.fincUseDes == null || mfBusApply.fincUseDes == ''}">无</c:if>
                    <c:if test="${mfBusApply.fincUseDes != null}"><span>${mfBusApply.fincUseDes}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>姓名：</td>
                <td>
                    <c:if test="${mfCusCustomer.cusName == null || mfCusCustomer.cusName == ''}">无</c:if>
                    <c:if test="${mfCusCustomer.cusName != null}"><span>${mfCusCustomer.cusName}</span></c:if>
                </td>
                <td>身份证号：</td>
                <td>
                    <c:if test="${mfCusCustomer.idNum == null || mfCusCustomer.idNum == ''}">无</c:if>
                    <c:if test="${mfCusCustomer.idNum != null}"><span>${mfCusCustomer.idNum}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>手机号码：</td>
                <td>
                    <c:if test="${mfCusCustomer.cusTel == null || mfCusCustomer.cusTel == ''}">无</c:if>
                    <c:if test="${mfCusCustomer.cusTel != null}"><span>${mfCusCustomer.cusTel}</span></c:if>
                </td>
                <td>银行卡号：</td>
                <td>
                    <c:if test="${mfCusBankAccManage.accountNo == null || mfCusBankAccManage.accountNo == ''}">无</c:if>
                    <c:if test="${mfCusBankAccManage.accountNo != null}"><span>${mfCusBankAccManage.accountNo}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>电子邮箱：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.email == null || mfCusPersBaseInfo.email == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.email != null}"><span>${mfCusPersBaseInfo.email}</span></c:if>
                </td>
                <td>学历：</td>
                <td>
                    <c:if test="${empty mfCusPersBaseInfo.email}"><span>无</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '0'}"><span>未知</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '1'}"><span>研究生</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '2'}"><span>大学本科</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '3'}"><span>大学专科和专科学校</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '4'}"><span>中等专业学校或中等技术学校</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '5'}"><span>技术学校</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '6'}"><span>高中</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '7'}"><span>初中</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '8'}"><span>小学</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '9'}"><span>文盲或半文盲</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.education == '10'}"><span>本科以上</span></c:if>
                </td>
            </tr>
            <tr>
                <td>婚姻状况：</td>
                <td>
                    <c:if test="${empty mfCusPersBaseInfo.marrige}"><span>无</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '1'}"><span>未婚</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '2'}"><span>已婚有子女</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '3'}"><span>	已婚</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '4'}"><span>	已婚无子女</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '5'}"><span>丧偶</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '6'}"><span>离异</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '7'}"><span>	其他</span></c:if>
                    <c:if test="${mfCusPersBaseInfo.marrige == '8'}"><span>	再婚</span></c:if>
                </td>
                <td>出生年月：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.brithday == null || mfCusPersBaseInfo.brithday == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.brithday != null}"><span>${mfCusPersBaseInfo.brithday}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>户籍地址：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.regHomeAddre == null || mfCusPersBaseInfo.regHomeAddre == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.regHomeAddre != null}"><span>${mfCusPersBaseInfo.regHomeAddre}</span></c:if>
                </td>
                <td>公司名称：</td>
                <td>
                    <c:if test="${mfCusPersonJob.workUnit == null || mfCusPersonJob.workUnit == ''}">无</c:if>
                    <c:if test="${mfCusPersonJob.workUnit != null}"><span>${mfCusPersonJob.workUnit}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>所在省市区：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.careaCity == null || mfCusPersBaseInfo.careaCity == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.careaCity != null}"><span>${mfCusPersBaseInfo.careaCity}</span></c:if>
                </td>
                <td>通讯录记录数：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.recordNum == null || mfCusPersBaseInfo.recordNum == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.recordNum != null}"><span>${mfCusPersBaseInfo.recordNum}</span></c:if>
                </td>
            </tr>
            <tr>
                <td>运营商归属地：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.operatorAdr == null || mfCusPersBaseInfo.operatorAdr == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.operatorAdr != null}"><span>${mfCusPersBaseInfo.operatorAdr}</span></c:if>
                </td>
                <td>运营商认证结果：</td>
                <td>
                    <c:if test="${mfCusPersBaseInfo.authResults == null || mfCusPersBaseInfo.authResults == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.authResults != null}"><span>${mfCusPersBaseInfo.authResults}</span></c:if>
                </td>
            </tr>
            <c:forEach items="${mfCusFamilyInfoList}" varStatus="i" var="mfCusFamilyInfo" >
                <tr>
                    <th style="font-size:20px">紧急联系人${i.index+1}信息</th>
                </tr>
                <tr>
                    <td>姓名：</td>
                    <td>
                        <c:if test="${mfCusFamilyInfo.relName == null}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.relName == ''}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.relName != null}"><span>${mfCusFamilyInfo.relName}</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td>身份证：</td>
                    <td>
                        <c:if test="${mfCusFamilyInfo.idNum == null}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.idNum == ''}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.idNum != null}"><span>${mfCusFamilyInfo.idNum}</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td>电话：</td>
                    <td>
                        <c:if test="${mfCusFamilyInfo.relTel == null}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.relTel == ''}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.relTel != null}"><span>${mfCusFamilyInfo.relTel}</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td>住址：</td>
                    <td>
                        <c:if test="${mfCusFamilyInfo.postalAddress == null || mfCusFamilyInfo.postalAddress == ''}">无</c:if>
                        <c:if test="${mfCusFamilyInfo.postalAddress != null}"><span>${mfCusFamilyInfo.postalAddress}</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td>所属关系：</td>
                    <td>
                        <c:if test="${mfCusFamilyInfo.relative == null}"><span>无</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '1'}"><span>配偶</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '2'}"><span>子女</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '3'}"><span>父母</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '4'}"><span>亲戚</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '5'}"><span>其他</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '6'}"><span>兄弟</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '8'}"><span>朋友</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '9'}"><span>直系亲属</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '10'}"><span>非直系亲属</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '11'}"><span>经营性合伙人</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '12'}"><span>本次购机合伙</span></c:if>
                        <c:if test="${mfCusFamilyInfo.relative == '13'}"><span>同事</span></c:if>
                    </td>
                </tr>
            </c:forEach>
            <tr><th style="font-size:20px">借款平台信息</th></tr>
            <tr>
                <td>借款平台订单号：</td>
                <td>
                    <c:if test="${mfBusPact.pactNo == null || mfBusPact.pactNo == ''}">无</c:if>
                    <c:if test="${mfBusPact.pactNo != null}"><span>${mfBusPact.pactNo}</span></c:if>
                </td><td></td><td></td>
            </tr>
            <tr>
                <td>对客户授信额度：</td><td>
                    <c:if test="${mfCusPersBaseInfo.creditSum51 == null || mfCusPersBaseInfo.creditSum51 == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.creditSum51 != null}"><span>${mfCusPersBaseInfo.creditSum51}元</span></c:if>
                </td>
                <td>客户现可用额度：</td><td>
                    <c:if test="${mfCusPersBaseInfo.authBal51 == null || mfCusPersBaseInfo.authBal51 == ''}">无</c:if>
                    <c:if test="${mfCusPersBaseInfo.authBal51 != null}"><span>${mfCusPersBaseInfo.authBal51}元</span></c:if>
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="col-md-12 col-md-offset-0 margin_top_20">
    <div class="bootstarpTag">
        <table  id="tableMsg" width="100%" border="1" cellpadding="0" cellspacing="0" style="table-layout:fixed">
        <tr>
            <td colspan="2" rowspan="1"></td>
            <td colspan="3" rowspan="1">检查项目</td>
            <td colspan="1" rowspan="1">是否命中</td>
            <td colspan="4" rowspan="1">详细信息 </td>
        </tr>
        <tr align="center">
            <td colspan="2" rowspan="1">银行卡四要素验证</td>
            <td colspan="3" rowspan="1">银行卡四要素验证</td>
            <td colspan="1" rowspan="1"></td>
            <td colspan="4" rowspan="1">${dataMap.fourResult}</td>
        </tr>
        <tr>
            <td colspan="2" rowspan="3">黑名单验证</td>
            <td colspan="3" rowspan="1">一级关联黑产个数</td>
            <td colspan="1" rowspan="1"></td>
            <td colspan="4" rowspan="1">
                <c:if test="${blackDetailMap.level1BlackCount == null && blackDetailMap.level1BlackCount == ''}">无</c:if>
                <c:if test="${blackDetailMap.level1BlackCount != null}"><span>${blackDetailMap.level1BlackCount}个</span></c:if>
            </td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1">二级关联黑产个数</td>
            <td colspan="1" rowspan="1"></td>
            <td colspan="4" rowspan="1">
                <c:if test="${blackDetailMap.level2BlackCount == null && blackDetailMap.level2BlackCount == ''}">无</c:if>
                <c:if test="${blackDetailMap.level2BlackCount != null}"><span>${blackDetailMap.level2BlackCount}个</span></c:if>
            </td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1">三级关联黑产个数</td>
            <td colspan="1" rowspan="1"></td>
            <td colspan="4" rowspan="1">
                <c:if test="${blackDetailMap.level3BlackCount == null && blackDetailMap.level3BlackCount == ''}">无</c:if>
                <c:if test="${blackDetailMap.level3BlackCount != null}"><span>${blackDetailMap.level3BlackCount}个</span></c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2" rowspan="100">反欺诈验证</td>
            <td colspan="3" rowspan="1"  title="涉嫌从事包装客户资料、伪造客户资料、冒用客户资料、套取机构风险政策等职业的用户或者机构成员">
                涉嫌从事包装客户资料、伪造客户资料、冒用客户资料、套取机构风险政策等职业的用户或者机构成员
            </td>
            <td colspan="1" rowspan="1">
                <c:if test="${dataMap.AF01 == '1'}"><span style="color: red">命中</span></c:if>
                <c:if test="${empty dataMap.AF01}"><span style="color: green">未命中</span></c:if>
            </td>
            <td colspan="4" rowspan="1"></td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1">黄赌毒</td>
            <td colspan="1" rowspan="1">
                <c:if test="${dataMap.AF02 == '1'}"><span style="color: red">命中</span></c:if>
                <c:if test="${empty dataMap.AF02}"><span style="color: green">未命中</span></c:if>
            </td>
            <td colspan="4" rowspan="1"></td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1" title="输入虚假身份信息或有恶意申请/操作记录，个人信息疑似泄漏、冒用、伪造等">输入虚假身份信息或有恶意申请/操作记录，个人信息疑似泄漏、冒用、伪造等</td>
            <td colspan="1" rowspan="1">
                <c:if test="${dataMap.AF03 == '1'}"><span style="color: red">命中</span></c:if>
                <c:if test="${empty dataMap.AF03}"><span style="color: green">未命中</span></c:if>
            </td>
            <td colspan="4" rowspan="1"></td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1">薅羊毛</td>
            <td colspan="1" rowspan="1">
                <c:if test="${dataMap.AF04 == '1'}"><span style="color: red">命中</span></c:if>
                <c:if test="${empty dataMap.AF04}"><span style="color: green">未命中</span></c:if>
            </td>
            <td colspan="4" rowspan="1"></td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1">身份信息涉嫌伪造</td>
            <td colspan="1" rowspan="1">
                <c:if test="${dataMap.AF05 == '1'}"><span style="color: red">命中</span></c:if>
                <c:if test="${empty dataMap.AF05}"><span style="color: green">未命中</span></c:if>
            </td>
            <td colspan="4" rowspan="1"></td>
        </tr>
        <tr>
            <td colspan="3" rowspan="1">恶意骗贷</td>
            <td colspan="1" rowspan="1">
                <c:if test="${dataMap.AF06 == '1'}"><span style="color: red">命中</span></c:if>
                <c:if test="${empty dataMap.AF06}"><span style="color: green">未命中</span></c:if>
            </td>
            <td colspan="4" rowspan="1"></td>
        </tr>
            <tr>
                <td colspan="3" rowspan="1">失信名单</td>
                <td colspan="1" rowspan="1">
                    <c:if test="${dataMap.AF07 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF07}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">支付欺诈</td>
                <td>
                    <c:if test="${dataMap.AF08 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF08}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1" title="设备和 IP 命中黑数据库，包括使用虚拟机、代理设备、代理 IP、猫池等">设备和 IP 命中黑数据库，包括使用虚拟机、代理设备、代理 IP、猫池等</td>
                <td>
                    <c:if test="${dataMap.AF09 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF09}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1" title="输入信息和以下高风险可能性关联度较高：被盗风险较高、社交圈子不固定、地理圈子变化较大">输入信息和以下高风险可能性关联度较高：被盗风险较高、社交圈子不固定、地理圈子变化较大</td>
                <td>
                    <c:if test="${dataMap.AF10 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF10}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">多头借贷倾向
                </td>
                <td>
                    <c:if test="${dataMap.AF11 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF11}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">信用逾期预测</td>
                <td>
                    <c:if test="${dataMap.AF12 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF12}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">欺诈</td>
                <td>
                    <c:if test="${dataMap.AF13 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF13}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">违法</td>
                <td>
                    <c:if test="${dataMap.AF14 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF14}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">刷单</td>
                <td>
                    <c:if test="${dataMap.AF15 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF15}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">信贷欺诈</td>
                <td>
                    <c:if test="${dataMap.AF16 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF16}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">套现</td>
                <td>
                    <c:if test="${dataMap.AF17 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF17}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">境外欺诈</td>
                <td>
                    <c:if test="${dataMap.AF18 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF18}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">养号</td>
                <td>
                    <c:if test="${dataMap.AF19 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF19}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">盗号</td>
                <td>
                    <c:if test="${dataMap.AF20 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF20}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">欺诈作案APP用户</td>
                <td>
                    <c:if test="${dataMap.AF21 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF21}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">黑产</td>
                <td>
                    <c:if test="${dataMap.AF22 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF22}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">欺诈团伙</td>
                <td>
                    <c:if test="${dataMap.AF23 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF23}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="1">在逃嫌犯</td>
                <td>
                    <c:if test="${dataMap.AF24 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF24}"><span style="color: green">未命中</span></c:if>
                </td>
                <td colspan="4" rowspan="1"></td>
            </tr>
            <tr>
                <td colspan="3" rowspan="6">法院失信</td>
                <td colspan="1" rowspan="6">
                    <c:if test="${dataMap.AF25 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF25}"><span style="color: green">未命中</span></c:if>
                </td>
            </tr>
            <tr>
                <td>案件编号：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF25.case_no}">${dataMap.parmMap.AF25.case_no}</td>
            </tr>
            <tr>
                <td>公开日期：</td>
                <td colspan="3" rowspan="1" >${dataMap.parmMap.AF25.publishDate}</td>
            </tr>
            <tr>
                <td>执行义务：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF25.duty}">${dataMap.parmMap.AF25.duty}</td>
            </tr>
            <tr>
                <td>执行情况：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF25.performance}">${dataMap.parmMap.AF25.performance}</td>
            </tr>
            <tr>
                <td>违反条例：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF25.disruptTypeName}">${dataMap.parmMap.AF25.disruptTypeName}</td>
            </tr>
            <tr>
                <td colspan="3" rowspan="5">工商偷税漏税</td>
                <td colspan="1" rowspan="5">
                    <c:if test="${dataMap.AF26 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF26}"><span style="color: green">未命中</span></c:if>
                </td>
            </tr>
            <tr>
                <td>税务主体：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF26.taxpayer_name}">${dataMap.parmMap.AF26.taxpayer_name}</td>
            </tr>
            <tr>
                <td>纳税识别号：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF26.taxpayer_iden_num}">${dataMap.parmMap.AF26.taxpayer_iden_num}</td>
            </tr>
            <tr>
                <td>公开机构：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF26.info_source}">${dataMap.parmMap.AF26.info_source}</td>
            </tr>
            <tr>
                <td>主要原因：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF26.major_fact}">${dataMap.parmMap.AF26.major_fact}</td>
            </tr>
            <tr>
                <td colspan="3" rowspan="6">股权冻结</td>
                <td colspan="1" rowspan="6">
                    <c:if test="${dataMap.AF27 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF27}"><span style="color: green">未命中</span></c:if>
                </td>
            </tr>
            <tr>
                <td>执行法院：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF27.execute_court}">${dataMap.parmMap.AF27.execute_court}</td>
            </tr>
            <tr>
                <td>处罚编号：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF27.adjudicate_no}">${dataMap.parmMap.AF27.adjudicate_no}</td>
            </tr>
            <tr>
                <td>冻结金额：</td>
                <td colspan="3" rowspan="1" >${dataMap.parmMap.AF27.freeze_amount}</td>
            </tr>
            <tr>
                <td title="处罚公开时间">处罚公开时间：</td>
                <td colspan="3" rowspan="1">${dataMap.parmMap.AF27.pub_date}</td>
            </tr>
            <tr>
                <td title="合作企业名称">合作企业名称：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF27.crop_name}">${dataMap.parmMap.AF27.crop_name}</td>
            </tr>
            <tr>
                <td colspan="3" rowspan="6">无照经营</td>
                <td colspan="1" rowspan="6">
                    <c:if test="${dataMap.AF28 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF28}"><span style="color: green">未命中</span></c:if>
                </td>
            </tr>
            <tr>
                <td>处罚编号：</td>
                <td colspan="3" rowspan="1" >${dataMap.parmMap.AF28.punishment_no}</td>
            </tr>
            <tr>
                <td>执法原因：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF28.cause_of_action}">${dataMap.parmMap.AF28.cause_of_action}</td>
            </tr>
            <tr>
                <td>处罚部门：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF28.handle_org_name}">${dataMap.parmMap.AF28.handle_org_name}</td>
            </tr>
            <tr>
                <td>公示日期：</td>
                <td colspan="3" rowspan="1">${dataMap.parmMap.AF28.pub_date}</td>
            </tr>
            <tr>
                <td>处罚内容：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF28.punishment_content}">${dataMap.parmMap.AF28.punishment_content}</td>
            </tr>
            <tr>
                <td colspan="3" rowspan="6">行政处罚</td>
                <td colspan="1" rowspan="6">
                    <c:if test="${dataMap.AF29 == '1'}"><span style="color: red">命中</span></c:if>
                    <c:if test="${empty dataMap.AF29}"><span style="color: green">未命中</span></c:if>
                </td>
            </tr>
            <tr>
                <td>注册时间：</td>
                <td colspan="3" rowspan="1">${dataMap.parmMap.AF29.reg_date}</td>
            </tr>
            <tr>
                <td>案件编号：</td>
                <td colspan="3" rowspan="1" >${dataMap.parmMap.AF29.case_no}</td>
            </tr>
            <tr>
                <td>处罚部门：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF29.handle_org_name}">${dataMap.parmMap.AF29.handle_org_name}</td>
            </tr>
            <tr>
                <td>判决法院：</td>
                <td colspan="3" rowspan="1" title="${dataMap.parmMap.AF29.court}">${dataMap.parmMap.AF29.court}</td>
            </tr>
            <tr>
                <td>执行金额：</td>
                <td colspan="3" rowspan="1" >${dataMap.parmMap.AF29.exec_money}</td>
            </tr>
            </c:if>
    </table>
    </div>
</div>
    </div>
</div>
</body>
</html>