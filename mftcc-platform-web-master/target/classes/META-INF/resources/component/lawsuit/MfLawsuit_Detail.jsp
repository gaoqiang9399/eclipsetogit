<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>法律诉讼详情-查询</title>
    <script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuitDetail.js"></script>
    <script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
    <link rel="stylesheet" href="${webPath}/component/lawsuit/css/MfLawsuit_Detail.css?v=${cssJsVersion}"></link>
    <script type="text/javascript">
        var pactId = '${mfLawsuit.pactId}';
        var caseId = '${mfLawsuit.caseId}';
        var assetId = '${mfLawsuit.assetId}'
        var cusNo = '${mfLawsuit.cusNo}'
        var scNo = 'lawsuit';
        var query = '${query}';
        var ifBizManger='${ifBizManger}';
        var isCusDoc = "cusDoc";
        var docParm = "cusNo="+cusNo+"&relNo=" + caseId + "&scNo=" + scNo;//查询文档信息的url的参数
        //var aloneFlag = true;
        /*var dataDocParm={
            relNo:caseId,
            docType:"lawDoc",
            docTypeName:"案件资料",
            docSplitName:"法律诉讼相关文件",
            query:query,
        };*/
        $(function() {
            lawsuitDetail.init();
        });
        //单字段编辑的保存回调方法。
        function oneCallback(data) {
            var $_form = this;
            var formAction = $_form.attr("action");
            if(formAction == webPath+"/mfLitigationExpenseApply/updateAjaxByOne") {//法律诉讼费用单子段编辑的回调函数
                $.ajax({
                    url :webPath+"/mfLitigationExpenseApply/dealCostSumAmtAjax",
                    type:"post",
                    data:{assetId:caseId},
                    success:function(dataMap){
                        if(dataMap!='' ||dataMap != null){
                            $('#cost').html(dataMap.costSum);
                        }
                    }
                });

            }else if(formAction == webPath+"/mfLawsuitFollow/updateAjaxByOne"){//法律诉讼执行回收金额单子段编辑的回调函数

                $.ajax({
                    url :webPath+"/mfLawsuitFollow/dealFollowPerformAjax",
                    type:"post",
                    data:{caseId:caseId},
                    success:function(dataMap){
                        if(dataMap!='' ||dataMap != null){
                            $('#actAmt').html(dataMap.recoverableAmount);
                        }
                    }
                });
            }else if(formAction == webPath+"/mfLawsuitPerformReg/updateAjaxByOne"){//法律诉讼执行金额登记单子段编辑的回调函数

                    $.ajax({
                        url :webPath+"/mfLawsuitPerformReg/dealPerformAmtAjax",
                        type:"post",
                        data:{caseId:caseId},
                        success:function(data){
                            $('#loanBalance').html(data.loanBalance);
                           // $('input[name=performPrcp]').parents("tr").find("td").eq(4).find('.fieldShow').html(data.fenpeiAmt);

                            $('#mfLawsuitPerformReg').html(data.tableHtml);


                        }
                    });
            }
        }
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content" style="padding:10px;">
        <div class="row clearfix head-info">
            <div class="col-md-3 text-center head-img padding_top_20">
                <div style="position:relative;">
                    <button type="button" class="btn btn-font-pact font-pact-div padding_left_15">
                        <i class="i i-lawsuit font-icon"></i>
                        <div class="font-text-left">案件信息</div>
                    </button>
                </div>
            </div>
            <div class="col-md-6 head-content">
                <div class="bus head-title" title="${mfLawsuit.caseTitle}">${mfLawsuit.caseTitle}</div>
                <div class="margin_top_5">
                    <table>
                        <tr>
                            <td>
                                <p class="cont-title ">诉讼类型</p>
                                <p><span class="content-span font_size_16">${dataMap.caseType}</span></p>
                            </td>
                            <td>
                                <p class="title">诉讼金额</p>
                                <p><span class="content-span font_size_16" id="caseAmt">${dataMap.caseAmt}</span><span class="unit-span margin_right_5">元</span> </p>
                            </td>
                            <td>
                                <p class="cont-title">起诉时间</p>
                                <p><span class="content-span font_size_16">${mfLawsuit.caseTime}</span></p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p class="cont-title">诉讼余额</p>
                                <p><span id="loanBalance" class="content-span font_size_16"><%--${dataMap.loanBalance}--%></span><span class="unit-span margin_right_5">元</span> </p>
                            </td>
                            <td>
                                <p class="cont-title">费用金额</p>
                                <p><a href="" id="cost" class="content-span font_size_16" onclick="lawsuitDetail.getFeeSumDetail();"><%--${dataMap.cost}--%></a><span class="unit-span margin_right_5">元</span> </p>
                            </td>
                            <td>
                                <p class="cont-title">执行回收金额</p>
                                <p><span id="actAmt" class="content-span font_size_16"><%--${actAmt}--%></span><span class="unit-span ">元</span> </p>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="col-md-3">
                <div class="i i-warehouse seal-font">
                    <div class="seal-name-div">${mfLawsuit.caseState}</div>
                </div>
            </div>
        </div>
        <!--案件详情 -->
        <div class="row clearfix">
            <div class="form-table base-info">
                <div class="title">
                    <span><i class="i i-xing blockDian"></i>案件详情</span>
                    <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
                    <button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.lawsuitAssets();" title="涉案资产"><i class="i i-assets">涉案资产</i></button>
                    <button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.costReg();" title="费用登记"><i class="i i-dengji">费用登记</i></button>
                    <button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.performReg();" title="执行金额登记"><i class="i i-dengji">执行金额登记</i></button>
                    <button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.insertFollow();" title="案件跟进"><i class="i i-dengji">案件跟进</i></button>
                    <%--<button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.returnCollect();" title="返回催收"><i class="i i-cuishou">返回催收</i></button>--%>
                    <%--<button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.filling();" title="结案归档"><i class="i i-guidang">结案归档</i></button>--%>
                    <!-- 						<button class="btn btn-link pull-right formAdd-btn" onclick="lawsuitDetail.filePrint()" title="文件打印"><i class="i i-x">文件打印</i></button>
                     -->					</div>
                <div id="suitDetail" class="content collapse in">
                    <form id="edit-form" theme="simple" name="operform" action="${webPath}/mfLawsuit/updateAjax">
                        <dhcc:propertySeeTag property="formlawsuit0001" mode="query" />
                    </form>
                </div>
            </div>
        </div>
        <dhcc:pmsTag pmsId="mf-history-service">
            <jsp:include page="/component/lawsuit/CostRegList.jsp"/>
        </dhcc:pmsTag>
        <!-- 执行回收情况登记 -->
        <jsp:include page="/component/lawsuit/ExecutionRecoveryList.jsp"/>
        <!-- 执行金额情况登记 -->
        <jsp:include page="/component/lawsuit/MfLawsuitPerformRegList.jsp"/>

        <!--案件资料 -->
        <div class="row clearfix">
               <%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
        </div>
        <!--案件跟进 -->
        <jsp:include page="/component/lawsuit/MfLawsuitFollow_ShowList.jsp"/>
        <%-- 	<div id="suitFollowDiv" class="row clearfix">
                <div class="list-table">
                    <div class="title">
                        <span><i class="i i-xing blockDian"></i>案件跟进</span>
                        <button class="btn btn-link formAdd-btn" onclick="lawsuitDetail.insertFollow();" title="新增跟进"><i class="i i-jia3"></i></button>
                        <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitTrack"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
                    </div>
                    <div id="suitTrack" class="content collapse in">
                        <div id="follow-form" class="row clearfix" style="display: none;">
                            <div class="col-md-10 col-md-offset-1 column margin_top_20">
                                <div class="bootstarpTag">
                                    <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                                    <form method="post" id="lawsuit-follow" theme="simple" name="operform" action="${webPath}/mfLawsuit/followAjax">
                                        <dhcc:bootstarpTag property="formlawsuitfollow0002" mode="query" />
                                    </form>
                                </div>
                                <div class="formRowCenter background-border-none position-fixed">
                                    <dhcc:thirdButton value="保存" action="保存" typeclass ="ajaxInsert followAjax"></dhcc:thirdButton>
                                </div>
                            </div>
                        </div>
                        <div id="follow-info" class="row clearfix">
                            <c:if test="${fn:length(mfLawsuit.follows)==0}">
                                <div class="message">暂无跟进信息</div>
                            </c:if>
                            <c:if test="${fn:length(mfLawsuit.follows) != 0}">
                                <c:forEach items="${mfLawsuit.follows}" var="follow" varStatus="status">
                                    <div class="follow-item">
                                        <div class="margin_bottom_10">
                                            <input type="hidden" class="follow-type" value="${follow.followType}" />
                                            <span class="follow-type-name">[${follow.followTypeName}]</span>
                                            <span class="follow-op">${follow.opName}</span>
                                            <span class="follow-date">登记于:&nbsp;${follow.creatDate}</span>
                                        </div>
                                        <div class="follow-content">
                                            <p>${follow.remark}</p>
                                        </div>
                                        <div id="${follow.followId}" class="follow-opt">
<!-- 											<a href="javascript:void(0);" onclick="lawsuitDetail.getFollowAjax(this)">详情</a>  -->
                                            <a href="javascript:void(0);" onclick="lawsuitDetail.deleteFollow(this)">删除</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div> --%>
        <!--判决/调解情况 -->
        <div class="row clearfix">
            <div class="form-table base-info">
                <div class="title">
                    <span><i class="i i-xing blockDian"></i>判决调解情况</span>
                </div>
                <div id="decisionMediateDetail" class="content collapse in">
                    <form id="edit-form" theme="simple" name="operform" action="${webPath}/mfLawsuitFollow/updateAjax">
                        <dhcc:propertySeeTag property="formdecisionMediateDetail" mode="query" ifBizManger="ifBizManger" />
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>