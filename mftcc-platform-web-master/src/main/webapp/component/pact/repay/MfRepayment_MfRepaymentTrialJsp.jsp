
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    <link rel="stylesheet" href="${webPath}/component/pact/css/MfRepayment_MfRepaymentJsp.css" />
    <script type="text/javascript" src="${webPath}/component/pact/repay/js/mfRepaymentJsp.js"></script>
    <script type="text/javascript" src="${webPath}/component/pact/repay/js/repayMent.js"></script>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
    <script type="text/javascript" src="${webPath}/component/pact/repay/js/initRepaymentDoc.js"></script>
    <!-- 还款方式 为利随本清 并且 利随本清利息收取方式 是 3-按还款本金收取利息（例如还本1000 收 利息 =1000*（还款日期-还款计划开始日期）*日利率）  时使用 -->
    <script type="text/javascript" src="${webPath}/component/pact/repay/js/mfLsbqRaymentJsp.js"></script>
    <!-- 弹层关闭的方法 -->
    <script type="text/javascript" src='${webPath}/component/include/closePopUpBox.js'>
    </script>
    <script type="text/javascript" src="${webPath}/component/pact/repay/js/MfRepaymentTrial.js"></script>
    <title>还款</title>
</head>
<script type="text/javascript">
    var preRepayType='${mfPrepaymentBean.preRepayType}';//提前还款：1-提前结清 2-提前归还部分本金
    var termInstMustBack='${mfPrepaymentBean.termInstMustBack}';//当期本息是否必须归还：1-是、0-否
    var returnPlanPoint='${mfPrepaymentBean.returnPlanPoint}';//保留小数位
    var tiQianHuanKuanWeiYueJin='${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}';// 提前结清时  该违约金 是定值
    var yingShouZongJiAllFormat = '${mfRepaymentBean.yingShouZongJiAllFormat}';//实收金额
</script>
<script type="text/javascript">
    //"YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin"]
    var payOrder = "${mfBusAppKind.repaymentOrderStr}" ;//代表还款顺序  应该取传过来的值
    var p8044 = "2";// 还款总额保留小数舍入 0-取底1-取顶2-四舍五入  应该取传过来的值
    var appId="${mfBusFincApp.appId}";
    var fincId="${mfBusFincApp.fincId}";
    var docParm = "";//查询文档信息的url的参数
    $(function() {
        var jieYuTuiKuanVal = $("#jieYuTuiKuan_input_hidden").val();
        if ("2" == jieYuTuiKuanVal) {//允许退款
            $("#benCiChongDiOrTuiKuan").text("本次退款");
        }
        //隐藏相关还款信息
        hideRepayInfo();
        initRepaymentDoc.initDoc("normalDoc");
    });
</script>
<body class="body_bg">
<div class="bigform_content">
    <div class="content_table" style="background: #F9F9F9;">
        <div class="baseinf">
            <div class="pay-cus">
                <div class="col-xs-3 col-md-3 pay-cus-img">
                    <img id="headImgShow" name="headImgShow" class="img-circle"
                         src="${webPath}/uploadFile/viewUploadImageDetail?srcPath=themes%252Ffactor%252Fimages%252Fuser_0.jpg&amp;fileName=user2.jpg">
                </div>
                <div class="pay-cus-name">
                    ${mfCusCustomer.cusName }
                </div>
                <%--<div class="pay-cus-phone">
                    ${mfCusCustomer.contactsTel }
                    <!--  					 	<i class="i i-dianhua"></i>  -->
                    <!-- 					 	<i class="i i-youjian"></i>  -->
                </div>--%>
            </div>
            <div class="col-md-6 repayright payinf">
                <div class="paydetail" style="width: 100%; margin: 0px;">
                    <table style="width: 100%;">
                        <tr>
                            <td class="tdlable" align="right">合同编号</td>
                            <td class="tdvalue">
                                <span class="fieldShow ">${mfBusFincApp.pactNo}</span>
                            </td>
                            <c:choose>
                                <c:when test="${borrowCodeType == '1'}">
                                    <td class=" tdlable" align="right">借款编码</td>
                                    <td class=" tdvalue">
                                        <span class="fieldShow ">${mfBusFincApp.borrowCode}</span>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class=" tdlable" align="right">借据编号</td>
                                    <td class=" tdvalue">
                                        <span class="fieldShow ">${mfBusFincApp.fincShowId}</span>
                                    </td>
                                </c:otherwise>
                            </c:choose>

                        </tr>
                        <tr>
                            <td class=" tdlable" align="right">合同金额</td>
                            <td class=" tdvalue">
                                <span class="fieldShow " id="putoutAmt">${mfBusFincApp.putoutAmtFormat}元</span>
                            </td>
                            <td class=" tdlable" align="right">合同利率</td>
                            <td class=" tdvalue">
                                <span class="fieldShow ">${mfBusFincApp.fincRateShow}${rateUnit}</span>
                            </td>
                        </tr>
                        <tr>
                            <td class=" tdlable" align="right">开始日期</td>
                            <td class=" tdvalue">
                                <span class="fieldShow ">${mfBusFincApp.intstBeginDate}</span>
                            </td>
                            <td class=" tdlable" align="right">结束日期</td>
                            <td class=" tdvalue">
                                <span class="fieldShow ">${mfBusFincApp.intstEndDate}</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="payinf clearfix">
            <div class="realpay" style="width:100%">
                <form method="post" theme="simple" name="operform" action="${webPath}/mfRepayment/repaymentAjax">
                    <table id="formrepay0004" class="from_w" title="formrepay0004" align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tbody>
                        <tr>
                            <td class="tdlable" align="right">产品名称</td>
                            <td class="tdvalue" align="left">
											<span id="kindName" class="realvalue">
                                                ${mfBusFincApp.kindName}
                                            </span>
                                <i class="unit"  style="top: 0px;"></i>
                            </td>
                            <td class="tdlable" align="right">应收总额</td>
                            <td class="tdvalue" align="left">
										<span id="yingShouZongJiAll" class="realvalue">
                                            ${mfRepaymentBean.yingShouZongJiAllFormat}
                                        </span>
                                <span>&nbsp;元</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdlable" align="right">还款日期</td>
                            <td class="tdvalue" align="left">
                                <div class="realvalue">
                                    <input title="预计归还日期" name="systemDateLong" id="systemDateLong" value="${mfRepaymentBean.systemDateLong }" datatype="6" mustinput="1" maxlength="10" readonly="readonly"
                                           onfocus="fPopUpCalendarDlg({choose:MfRepaymentTrial.pdRiQiEvent});"  class="datelogo BOTTOM_LINE" type="text">
                                    <i class="i i-rili pointer" style="color: #32b5cb; margin-left: -20px;" onclick="$('#systemDateLong').focus();"></i>
                                </div>
                            </td>
                            <td class="tdlable" align="right">提前还本</td>
                            <td class="tdvalue" align="left">
                                <div class="realvalue">
                                    <input title="预计归还日期" name = "tiQianHuanBen" id = "tiQianHuanBen"   onchange="MfRepaymentTrial.pdIsNotValue(this);" type="text" datatype="12">
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
        <div style="clear: both;"></div>
        <div class="row" style="display: none;">
            <div class="table_show">
                <div class="title">应收信息 单位(元)</div>
                <div class="col-md-12">
                    <div class="list-table">
                        <div class="content margin_left_15 collapse in">
                            <table id="yingShou_List_table_001" width="100%" border="0" cellspacing="1" class="ls_list" title="">
                                <tr>
                                    <th scope="col" align="center" sorttype="0">期号</th>
                                    <th scope="col" align="center" sorttype="0">逾期利息</th>
                                    <th scope="col" align="center" sorttype="0">复利利息</th>
                                    <th scope="col" align="center" sorttype="0">利息罚息</th>
                                    <th scope="col" align="center" sorttype="0">违约金</th>
                                    <th scope="col" align="center" sorttype="0">利息</th>
                                    <th scope="col" align="center" sorttype="0">费用</th>
                                    <th scope="col" align="center" sorttype="0">本金</th>
                                    <th scope="col" align="center" sorttype="0">减免金额</th>
                                </tr>
                                <c:forEach var="loanReceivableBean" items="${mfReceivableList}" varStatus="stat">
                                    <c:choose>
                                        <c:when test="true">
                                            <tr id="tr_${loanReceivableBean.termNum}_termNum" termNum="${loanReceivableBean.termNum}">
                                                <input id="input_hidden_${loanReceivableBean.termNum}_returnPlanId_input" value="${loanReceivableBean.returnPlanId}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_outFlag_input" value="${loanReceivableBean.outFlag}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_benQiYingShouZongJi_input" value="${loanReceivableBean.benQiYingShouZongJi}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanFaXi_input" value="${loanReceivableBean.yingShouFaXi}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanYuQiLiXi_input" value="${loanReceivableBean.yingShouYuQiLiXi}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanFuLiLiXi_input" value="${loanReceivableBean.yingShouFuLiLiXi}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanFuLiLiXiPart_input" value="${loanReceivableBean.yingShouFuLiLiXiPart}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanYuQiWeiYueJin_input" value="${loanReceivableBean.yingShouYuQiWeiYueJin}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanFeiYong_input" value="${loanReceivableBean.yingShouFeiYong}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanLiXi_input" value="${loanReceivableBean.yingShouLiXi}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanLiXiNormPart_input" value="${loanReceivableBean.yingShouLiXiNormPart}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanLiXiOverPart_input" value="${loanReceivableBean.yingShouLiXiOverPart}" type="hidden" />
                                                <input id="input_hidden_${loanReceivableBean.termNum}_yingHuanBenJin_input" value="${loanReceivableBean.yingShouBenJin}" type="hidden" />
                                                <td align="center">
                                                    <span class="tab_span">${loanReceivableBean.termNum}</span>
                                                </td>
                                                <td align="center" title="${loanReceivableBean.yingShouYuQiLiXi}">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouYuQiLiXi_Text" datatype="12" onchange="funcCheckRepay(this); resetTimes(); modifyAmtFun('${loanReceivableBean.termNum}','YuQiLiXi');"
                                                           value="${loanReceivableBean.yingShouYuQiLiXi}" fromVal="${loanReceivableBean.yingShouYuQiLiXi}" type="text" style="width: 50px; display: none; color: #0099ff; line-height: 24px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouYuQiLiXi" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouYuQiLiXiFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouYuQiLiXi_td_span">${loanReceivableBean.yingShouYuQiLiXiFormat}</span>
                                                </td>
                                                <td align="center" title="${loanReceivableBean.yingShouFuLiLiXi}">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouFuLiLiXi_Text" datatype="12" onchange="funcCheckRepay(this); resetTimes(); modifyAmtFun('${loanReceivableBean.termNum}','FuLiLiXi');"
                                                           value="${loanReceivableBean.yingShouFuLiLiXi}" fromVal="${loanReceivableBean.yingShouFuLiLiXi}" type="text" style="width: 50px; display: none; color: #0099ff; line-height: 24px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouFuLiLiXi" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouFuLiLiXiFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouFuLiLiXi_td_span">${loanReceivableBean.yingShouFuLiLiXiFormat}</span>
                                                </td>
                                                <td align="center" title="${loanReceivableBean.yingShouFuLiLiXiPart}">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouFuLiLiXiPart_Text" datatype="12" onchange="funcCheckRepay(this); resetTimes(); modifyAmtFun('${loanReceivableBean.termNum}','FuLiLiXiPart');"
                                                           value="${loanReceivableBean.yingShouFuLiLiXiPart}" fromVal="${loanReceivableBean.yingShouFuLiLiXiPart}" type="text" style="width: 50px; display: none; color: #0099ff; line-height: 24px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouFuLiLiXiPart" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouFuLiLiXiPartFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouFuLiLiXiPart_td_span">${loanReceivableBean.yingShouFuLiLiXiPartFormat}</span>
                                                </td>
                                                <td align="center" title="${loanReceivableBean.yingShouYuQiWeiYueJin}">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouYuQiWeiYueJin_Text" datatype="12"
                                                           onchange="funcCheckRepay(this); resetTimes(); modifyAmtFun('${loanReceivableBean.termNum}','YuQiWeiYueJin');" value="${loanReceivableBean.yingShouYuQiWeiYueJin}"
                                                           fromVal="${loanReceivableBean.yingShouYuQiWeiYueJin}" type="text" style="width: 50px; display: none; color: #0099ff; line-height: 24px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouYuQiWeiYueJin" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouYuQiWeiYueJinFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouYuQiWeiYueJin_td_span">${loanReceivableBean.yingShouYuQiWeiYueJinFormat}</span>
                                                </td>
                                                <td align="center" title="${loanReceivableBean.yingShouLiXi}">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouLiXi_Text" datatype="12" onchange="funcCheckRepay(this); resetTimes();modifyAmtFun('${loanReceivableBean.termNum}','LiXi');"
                                                           value="${loanReceivableBean.yingShouLiXi}" fromVal="${loanReceivableBean.yingShouLiXi}" type="text" style="width: 50px; display: none; color: #0099ff; line-height: 24px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouLiXi" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouLiXiFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouLiXi_td_span">${loanReceivableBean.yingShouLiXiFormat}</span>
                                                </td>
                                                <td align="center" title="${loanReceivableBean.yingShouFeiYong}">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouFeiYong_Text" datatype="12"
                                                           onchange="funcCheckRepay(this); resetTimes();modifyAmtFun('${loanReceivableBean.termNum}','FeiYong');" value="${loanReceivableBean.yingShouFeiYong}" type="text"
                                                           fromVal="${loanReceivableBean.yingShouFeiYong}" style="width: 50px; display: none; color: #0099ff; line-height: 24px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouFeiYong" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouFeiYongFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouFeiYong_td_span">${loanReceivableBean.yingShouFeiYongFormat}</span>
                                                </td>
                                                <td align="center">
                                                    <input id="input_text_${loanReceivableBean.termNum}_yingShouBenJin_Text" datatype="12"
                                                           onchange="funcCheckRepay(this); resetTimes();modifyAmtFun('${loanReceivableBean.termNum}','BenJin');" value="${loanReceivableBean.yingShouBenJin}" type="text"
                                                           fromVal="${loanReceivableBean.yingShouBenJin}" style="width: 50px; display: none; color: #0099ff; line-height: 24px; margin-top: 5px; height: auto;" />
                                                    <span id="input_shouldPay_${loanReceivableBean.termNum}_yingShouBenJin" style="display: none;">&nbsp;应收:${loanReceivableBean.yingShouBenJinFormat}</span>
                                                    <span class="tab_span" id="input_span_${loanReceivableBean.termNum}_yingShouBenJin_td_span">${loanReceivableBean.yingShouBenJinFormat}</span>
                                                </td>
                                                <td align="center">
														<span class="tab_span">
															<input id="${loanReceivableBean.termNum}_youHuiJine_input" datatype="12" name="name_youHuiJine_input" placeholder="${loanReceivableBean.maxYouhuiAmt}" disabled
                                                                   maxYouHui="${loanReceivableBean.maxYouhuiAmt}" onchange="funcCheckRepay(this); resetTimes();modifyFavorableAmt('${loanReceivableBean.termNum}','')" value="0.00" fromVal="0.00"
                                                                   type="text" style="width: 50px; line-height: 24px; margin-top: 5px; height: auto;" />
														</span>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <input id="input_hidden_${loanReceivableBean.termNum}_gsonStr_HeJiJinE" value="" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_termNum" value="${loanReceivableBean.termNum}" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_yingShouBenJin" value="${loanReceivableBean.yingShouBenJin}" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_yingShouLiXi" value="${loanReceivableBean.yingShouLiXi}" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_yingShouFeiYong" value="${loanReceivableBean.yingShouFeiYong}" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_yingShouFaXi" value="${loanReceivableBean.yingShouFaXi}" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_yingShouYuQiWeiYueJin" value="${loanReceivableBean.yingShouYuQiWeiYueJin}" type="hidden" />
                                            <input id="input_hidden_${loanReceivableBean.termNum}_youHuiJine" type="hidden" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <tr>
                                    <td align="center">
                                        <span class="tab_span">合计</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_yuqilixi">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_fulilixi">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_fulilixipart">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_wyj">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_lixi">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_feiyong">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_benjin">0.00</span>
                                    </td>
                                    <td align="center">
                                        <span class="tab_span" id="item_sum_youhui">0.00</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <span class="tab_span">实收合计</span>
                                    </td>
                                    <td style='text-align: center; font-size: 24px !important; color: #0099ff;' colspan='5'>
                                        <span class="tab_span" id="all_item_sum">0.00</span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="clear: both;"></div>
        <!-- 还款资料 -->
        <div class="col-md-12 col-md-offset-0 column" >
            <%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
        </div>
    </div>

</div>

<div id="hidden_parm" style="display: none;">
    <input id="cusNo_input_hidden" value="${mfCusCustomer.cusNo}" type="hidden" />
    <input id="cusName_input_hidden" value="${mfCusCustomer.cusName}" type="hidden" />
    <input id="appName_input_hidden" value="${mfBusFincApp.appName}" type="hidden" />
    <input id="loanBal_input_hidden" value="${mfBusFincApp.loanBal}" type="hidden" />
    <!--还款计划保留小数位0-不保留1-保留一位2-保留两位-->
    <input id="returnPlanPoint_hidden" value="${mfBusAppKind.returnPlanPoint}" type="hidden" />
    <input id="pactId" value="${mfRepaymentBean.pactId}" type="hidden" />
    <input id="fincId" value="${mfRepaymentBean.fincId}" type="hidden" />
    <input id="input_hidden_returnMethod" value="${mfRepaymentBean.returnMethod}" type="hidden" />
    <input id="shiShouBenJin_input_text" value="${mfRepaymentBean.shiShouBenJin}" type="hidden" />
    <input id="shiShouLiXi_input_text" value="${mfRepaymentBean.shiShouLiXi}" type="hidden" />
    <input id="shiShouFaXi_input_text" value="${mfRepaymentBean.shiShouFaXi}" type="hidden" />
    <input id="shiShouYuQiLiXi_input_text" value="${mfRepaymentBean.shiShouYuQiLiXi}" type="hidden" />
    <input id="shiShouFuLiLiXi_input_text" value="${mfRepaymentBean.shiShouFuLiLiXi}" type="hidden" />
    <input id="shiShouFuLiLiXiPart_input_text" value="${mfRepaymentBean.shiShouFuLiLiXiPart}" type="hidden" />
    <input id="shiShouFeiYong_input_text" value="${mfRepaymentBean.shiShouFeiYong}" type="hidden" />
    <input id="shiShouYuQiWeiYueJin_input_text" value="${mfRepaymentBean.shiShouYuQiWeiYueJin}" type="hidden" />
    <input id="benCiJieYu_input_text" value="${mfRepaymentBean.benCiJieYu}" type="hidden" />
    <input id="benCiChongDi_input_text" value="${mfRepaymentBean.benCiChongDi}" type="hidden" />
    <input id="shangCiJieYu_input_text" value="${mfRepaymentBean.shangCiJieYu}" type="hidden" />
    <input id="yingShouZongJiAll_input_text" value="${mfRepaymentBean.yingShouZongJiAll}" type="hidden" />
    <input id="shiShouZongJi_input_text" value="${mfRepaymentBean.yingShouZongJiAll}" type="hidden" />
    <input id="totalPay_input_hidden" value="${mfRepaymentBean.totalPay}" type="hidden" />
    <input id="systemDateLong_input_hidden" value="${mfRepaymentBean.systemDateLong}" type="hidden" />
    <input id="repayType_input_hidden" value="${mfBusFincApp.repayType}" type="hidden" />
    <input id="shiShouYouHuiZongJi_input_text" value="0.00" type="hidden" />
    <!-- 是否允许结余退款 0 不允许退款 借据金额 直接冲抵  1 允许退款 结余金额 进行直接退回 	-->
    <input id="jieYuTuiKuan_input_hidden" value="0" type="hidden" />
    <!-- cmpdRateType 复利利息是否收取：0-不收取、1-收取 -->
    <input id="cmpdRateType_input_hidden" value="${mfBusAppKind.cmpdRateType}" type="hidden" />
    <!--  InterestDerateFlag 利息是否支持减免 1-是 0-否  启用利息减免：还款时，利息支持减免优惠，包括正常利息和罚息-->
    <input id="interestDerateFlag_input_hidden" value="${mfBusAppKind.interestDerateFlag}" type="hidden" />
    <!--允许最后一期结余：0-不允许、1-允许-->
    <input id="lastTermBalanceType_input_hidden" value="${mfBusAppKind.lastTermBalanceType}" type="hidden" />
    <!-- 还款方式 为利随本清 并且 利随本清利息收取方式 是 3-按还款本金收取利息（例如还本1000 收 利息 =1000*（还款日期-还款计划开始日期）*日利率） 该状态 为3 为3 时 还款时 显示还本金额  -->
    <input id="lsbqHuanBenFlag_input_hidden" value="${lsbqHuanBenFlag}" type="hidden" />
    <!-- 剩余未还本金 -->
    <input id="shengYuBenJin_input_text" value="${mfBusFincApp.loanBal}" type="hidden" />
    <!-- 是否是利率浮动分开 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值） -->
    <input id="overCmpdFltSaveflag_input_text" value="${mfBusAppKind.overCmpdFltSaveflag}" type="hidden" />
    <!-- 费用是否显示  0 不展示  1 展示-->
    <input id="feeYongShowFlag_input_text" value="${mfRepaymentBean.feeYongShowFlag}" type="hidden" />
    <!-- 逾期违约金是否显示  0 不展示  1 展示-->
    <input id="yuQiWeiYueJinShowFlag_input_text" value="${mfRepaymentBean.yuQiWeiYueJinShowFlag}" type="hidden" />
</div>
<%-- 还款试算  根据事件不同展示不同的信息--%>
<!--提前还款信息  -->
<div class="payinf clearfix" id="beforpaymoney" style="display:none">
    <div class="paydetail">
        <table title="paydetail" width="100%" height="auto">
            <tbody>
            <tr>
                <td class="tdlable" align="right"><span class="theader">剩余本金</span>
                </td>
                <td class="tdvalue" align="left" width="0%"><span
                        id="shiShouBenJin" class="fieldShow"
                        style="display: inline-block;">
                    ${mfPrepaymentBean.shengYuBenJinFormat}</span><span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right"><span class="theader">当期本金</span>
                </td>
                <td class="tdvalue" align="left" width="0%"><span
                        id="dangQiBenJin" class="fieldShow"
                        style="display: inline-block;">
                    ${mfPrepaymentBean.dangQiBenJinFormat}</span><span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right"><span class="theader">实收利息</span>
                </td>
                <td class="tdvalue" align="left" width="0%"><span
                        id="shiShouLiXi" class="fieldShow"
                        style="display: inline-block;">
                    ${mfPrepaymentBean.shiShouLiXiFormat}</span><span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right"><span class="theader">本次结余</span>
                </td>
                <td class="tdvalue" align="left" width="0%"><span
                        id="benCiJieYu" class="fieldShow"
                        style="display: inline-block;">
                    ${mfPrepaymentBean.benCiJieYuFormat}</span><span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right"><span  id="benCiChongDiOrTuiKuan" class="theader">本次冲抵</span>
                </td>
                <td class="tdvalue" align="left" width="0%"><span
                        id="benCiChongDi" class="fieldShow"
                        style="display: inline-block;">
                    ${mfPrepaymentBean.benCiChongDiFormat}</span><span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right"><span class="theader">违约金</span></td>
                <td class="tdvalue" align="left" width="0%">
						<span name="tiQianHuanKuanWeiYueJin" id="tiQianHuanKuanWeiYueJin"
                              class="fieldShow"  style="display: inline-block;">${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}</span><span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right"><span class="theader">实收总计</span></td>
                <td class="tdvalue" align="left" width="0%"><span class="fieldShow"
                                                                  style="" id="shiShouZongJi">
                    ${mfPrepaymentBean.shiShouZongJi}</span><span>&nbsp;元</span></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<!--正常还款信息  -->
<div class="payinf clearfix" id="normalpaymoney" style="display: none">
    <div class="paydetail">
        <table title="paydetail" width="100%" height="auto">
            <tbody>
            <tr>
                <td class="tdlable" align="right">
                    <span class="theader">实收本金</span>
                </td>
                <td class="tdvalue" align="left" width="0%">
									<span id="normalShiShouBenJin" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.shiShouBenJinFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
                <td class="tdlable" align="right">
                    <span class="theader">实收利息</span>
                </td>
                <td class="tdvalue" align="left" width="0%">
									<span id="normalShiShouLiXi" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.shiShouLiXiFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right" id="shiShouYuQiWeiYueJinTd">
                    <span class="theader">违约金</span>
                </td>
                <td class="tdvalue" align="left" width="0%" id="shiShouYuQiWeiYueJinTdValue">
									<span id="shiShouYuQiWeiYueJin" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.shiShouYuQiWeiYueJinFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
                <td class="tdlable" align="right" id="shiShouFeiYongTd">
                    <span class="theader">实收费用</span>
                </td>
                <td class="tdvalue" align="left" width="0%" id="shiShouFeiYongTdValue">
									<span id="shiShouFeiYong" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.shiShouFeiYongFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right">
                    <span class="theader">逾期利息</span>
                </td>
                <td class="tdvalue" align="left" width="0%">
									<span id="shiShouYuQiLiXi" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.shiShouYuQiLiXiFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
                <td class="tdlable" align="right" id="shiShouFuLiLiXiTd" >
                    <span class="theader">复利利息</span>
                </td>
                <td class="tdvalue" align="left" width="0%" id="shiShouFuLiLiXiTdValue">
									<span id="shiShouFuLiLiXi" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.shiShouFuLiLiXiFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
            </tr>
            <c:if test="${mfBusAppKind.overCmpdFltSaveflag ==1}">
                <tr>
                    <td class="tdlable" align="right" id="shiShouFuLiLiXiPartTd">
                        <span class="theader">利息罚息</span>
                    </td>
                    <td class="tdvalue" align="left" width="0%" id="shiShouFuLiLiXiPartTdValue">
										<span id="shiShouFuLiLiXiPart" class="fieldShow" style="display: inline-block;">
                                                ${mfRepaymentBean.shiShouFuLiLiXiPartFormat}
                                        </span>
                        <span>&nbsp;元</span>
                    </td>
                </tr>
            </c:if>
            <c:if test="${mfRepaymentBean.feiYongFaXiFlag ==1}">
                <tr>
                    <td class="tdlable" align="right" id="shiShouFeiYongFaXiTd">
                        <span class="theader">费用罚息</span>
                    </td>
                    <td class="tdvalue" align="left" width="0%" id="shiShouFeiYongFaXiTdValue">
										<span id="shiShouFeiYongFaXi" class="fieldShow" style="display: inline-block;">
                                                ${mfRepaymentBean.shiShouFeiYongFaXiFormat}
                                        </span>
                        <span>&nbsp;元</span>
                    </td>
                </tr>
            </c:if>

            <tr>
                <td class="tdlable" align="right">
                    <span class="theader">本次结余</span>
                </td>
                <td class="tdvalue" align="left" width="0%">
									<span id="normalBenCiJieYu" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.benCiJieYuFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
                <td class="tdlable" align="right">
                    <span id="normalBenCiChongDiOrTuiKuan" class="theader">本次冲抵</span>
                </td>
                <td class="tdvalue" align="left" width="0%">
									<span id="normalBenCiChongDi" class="fieldShow" style="display: inline-block;">
                                        ${mfRepaymentBean.benCiChongDiFormat}
                                    </span>
                    <span>&nbsp;元</span>
                </td>
            </tr>
            <tr>
                <td class="tdlable" align="right">应收总额</td>
                <td class="tdvalue" align="left">
										<span id="normalYingShouZongJiAll" class="fieldShow">
                                            ${mfRepaymentBean.yingShouZongJiAllFormat}
                                        </span>
                    <span>&nbsp;元</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div style="height:50px;">
    <!-- 页面隐藏域 -->
    <div id="hidden_parm1" style="display: none;">
        <input id="dangQiBenJin_input_text1" value="${mfPrepaymentBean.dangQiBenJin}" type="hidden" />
        <input id="shengYuBenJin_input_text1" value="${mfPrepaymentBean.shengYuBenJin}" type="hidden" />
        <input id="fincId1" value="${mfPrepaymentBean.fincId}" type="hidden" />
        <input id="shiShouLiXi_input_text1"  value="${mfPrepaymentBean.shiShouLiXi}" type="hidden" />
        <input id="shiShouLiXiTip_input_text1" value="${mfPrepaymentBean.shiShouLiXi}" type="hidden" />
        <input id="tiQianHuanKuanWeiYueJin_input_text1" value="${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}" type="hidden" />
        <input id="tiQianHuanKuanWeiYueJinTip_input_text1" value="${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}" type="hidden" />
        <input id="shiShouZongJi_input_text1" value="${mfPrepaymentBean.shiShouZongJi}" type="hidden" />
        <input id="benCiJieYu_input_text1" value="${mfPrepaymentBean.benCiJieYu}" type="hidden" />
        <input id="benCiChongDi_input_text1" value="${mfPrepaymentBean.benCiChongDi}" type="hidden" />

        <input id="shiShouBenJin_input_text2" value="${mfRepaymentBean.shiShouBenJin}" type="hidden" />
        <input id="shiShouLiXi_input_text2" value="${mfRepaymentBean.shiShouLiXi}" type="hidden" />
        <input id="shiShouYuQiLiXi_input_text2" value="${mfRepaymentBean.shiShouYuQiLiXi}" type="hidden" />
        <input id="shiShouFuLiLiXi_input_text2" value="${mfRepaymentBean.shiShouFuLiLiXi}" type="hidden" />
        <input id="shiShouFuLiLiXiPart_input_text2" value="${mfRepaymentBean.shiShouFuLiLiXiPart}" type="hidden" />
        <input id="shiShouFeiYong_input_text2" value="${mfRepaymentBean.shiShouFeiYong}" type="hidden" />
        <input id="shiShouFeiYongFaXi_input_text2" value="${mfRepaymentBean.shiShouFeiYongFaXi}" type="hidden" />
        <input id="shiShouYuQiWeiYueJin_input_text2" value="${mfRepaymentBean.shiShouYuQiWeiYueJin}" type="hidden" />
        <input id="yingShouZongJiAll_input_text2" value="${mfRepaymentBean.yingShouZongJiAll}" type="hidden" />
    </div>
</div>

</body>
</html>