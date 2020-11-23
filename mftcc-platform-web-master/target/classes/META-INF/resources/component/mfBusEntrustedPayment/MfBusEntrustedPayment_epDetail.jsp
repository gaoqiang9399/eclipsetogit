<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>

<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <link rel="stylesheet"
          href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
    <link rel="stylesheet"
          href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
    <script type="text/javascript"
            src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
    <script type="text/javascript"
            src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
    <script type="text/javascript">
        //展示审批历史

        $(function () {
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    updateOnContentResize: true
                }
            });
            showApproveHis();
        });

        var cusNo = '${cusNo}';
        var fincId = '${fincId}';
        var appId = '${appId}';
        var pactId = '${pactId}';
        var epId = '${epId}';
        var scNo = "entrusted_payment";
        var nodeNo = '${nodeNo}';
        var query = '${query}';
        // var isCusDoc = "cusDoc";
        var docParm = "cusNo=" + cusNo + "&relNo=" + fincId + "&scNo=" + scNo;
        var aloneFlag = true;
        var dataDocParm = {
            relNo: fincId,
            docType: "lawDoc",
            docTypeName: "受托支付文件",
            docSplitName: "文件上传",
            query: query,
        };

        /* function update(obj) {
             var url = $(obj).attr("action");
             var ajaxData = {};
             $(obj).find('input,select').each(function () {
                 ajaxData[this.name] = this.value
             });
             $.ajax({
                 url: url,
                 data: {ajaxData: JSON.stringify(ajaxData)},
                 success: function (data) {
                     if (data.flag == "success") {
                         alert(data.msg);
                     } else {
                         alert(data.msg);
                     }
                 }, error: function () {
                 }
             });
         }*/
        function showApproveHis() {
            //获得审批历史信息
            showWkfFlowVertical($("#wj-modeler2"), epId, "", "entrustedPaymentApprove", "1");
            $("#epApproveHis").show();
        }

        function updatePayState(){
            var url = "${webPath}/mfBusEntrustedPayment/updatePayState";
            alert(top.getMessage("CONFIRM_COMMIT"),2,function() {
                $.ajax({
                    url: url,
                    data: {epId: epId},
                    success: function (data) {
                        if (data.flag == "success") {
                            DIALOG.msg(data.msg,function () {
                                myclose_click();
                            });
                        } else {
                            alert(top.getMessage("ERROR_FIN_VERIFY"),0,function() {
                            });
                        }
                    }, error: function () {
                        alert(top.getMessage("ERROR_NULL_POINT"),0,function() {
                        });
                    }
                });
            });
        }
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>

                <dhcc:bootstarpTag property="formentrustedpayment0001" mode="query"/>
                <%--<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->--%>
                <!--受托支付文件 -->
                <div class="row clearfix">
                    <div class="col-xs-12 column">
                        <%@ include file="/component/doc/webUpload/uploadutil.jsp" %>
                    </div>
                </div>
            </div>
            <c:if test="${appState != 0}">
                <div class="arch_sort">
                    <div id="epApproveHis" class="row clearfix" style="display: none;">
                        <div class="col-xs-12 column info-block">
                            <div id="spInfo-block">
                                <div class="form-table">
                                    <div class="title">
                                        <span><i class="i i-xing blockDian"></i>审批历史</span>
                                        <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse"
                                                data-target="#spInfo-div">
                                            <i class="i i-close-up"></i><i class="i i-open-down"></i>
                                        </button>
                                    </div>
                                    <div class="content margin_left_15 collapse in " id="spInfo-div">
                                        <div class="approval-process">
                                            <div id="modeler1" class="modeler">
                                                <ul id="wj-modeler2" class="wj-modeler" isApp="false">
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

</div>
<div class="formRowCenter">
    <%-- <dhcc:thirdButton value="保存" action="保存"
                       onclick="updates('#epform');"></dhcc:thirdButton>--%>
    <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    <c:if test="${appState=='2' && mfBusEntrustedPayment.payState!='1'}">
        <dhcc:pmsTag pmsId="query-bus-entrustedPayment-state">
            <dhcc:thirdButton value="支付确认" action="支付确认" onclick="updatePayState();"></dhcc:thirdButton>
        </dhcc:pmsTag>
    </c:if>
</div>
</body>
<%--<script type="text/javascript" src="${webPath}/component/mfBusEntrustedPayment/js/MfBusBankAccCom.js"></script>--%>
<%--<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactInsert.js"></script>--%>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript">

    var f = false;
    $('#wj-modeler2').on('DOMNodeInserted', function () {
        if (!f) {
            $('#wj-modeler2').get(0).children[0].style.display = "none";//隐藏第一个节点信息
            f = true;
        }
    })
</script>
</html>
