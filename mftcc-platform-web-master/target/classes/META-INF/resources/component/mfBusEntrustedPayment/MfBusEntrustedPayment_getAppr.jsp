<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
    <title>列表表单</title>
    <link rel="stylesheet"
          href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
    <link rel="stylesheet"
          href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
    <script type="text/javascript"
            src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
    <script type="text/javascript"
            src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
    <script type="text/javascript" >
        var appId = '${appId}';
        var cusNo = '${cusNo}';
        var appNo = '${appNo}';
        var nodeNo = 'entrusted_payment';
        var pactId = '${pactId}';
        var fincId = '${fincId}';
        var scNo = "entrusted_payment";
        var epId = '${epId}';
        var taskId = '${taskId}';
        var ajaxData = '${ajaxData}';
        var query= '';
        var docParm = "cusNo=" + cusNo + "&relNo=" + fincId + "&scNo=" + scNo;
        var aloneFlag = true;
        var dataDocParm={
            relNo:fincId,
            docType:"lawDoc",
            docTypeName:"受托支付文件",
            docSplitName:"文件查看",
            query:query,
        };
        $(function(){
            //滚动条
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    updateOnContentResize: true
                }
            });
            showApproveHis();
            /*$("select[name=opinionType] option:contains(否决)").hide();
            $(".formAdd-btn").hide();*/
        });
        function doSubmit(obj){
            var opinionType = $("select[name=opinionType]").val();
            var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
            commitProcess(webPath+"/mfBusEntrustedPayment/approveSubmitAjax?fincId="+fincId+"&epId="+epId+"&appId="+ appId+"&appNo="+appNo+'&opinionType='+opinionType+'&approvalOpinion='+approvalOpinion, obj,'sp');
        }
        function showApproveHis() {
            //获得审批历史信息
            showWkfFlowVertical($("#wj-modeler2"), appNo, "", "entrustedPaymentApprove", "1");
            $("#epApproveHis").show();
        }
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="scroll-content" style="padding-bottom: 50px;">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="list-table">
                <div class="title">
                    <span><i class="i i-xing blockDian"></i>受托支付审批</span>
                    <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">
                        <i class="i i-close-up"></i><i class="i i-open-down"></i>
                    </button>
                </div>
                <div class="bootstarpTag">
                    <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                    <form id="cus-form">
                        <dhcc:bootstarpTag property="formentrustedpayment0003" mode="query" />
                    </form>
                    <!--受托支付文件 -->
                    <div class="row clearfix">
                        <div class="col-xs-12 column">
                            <%@ include file="/component/doc/webUpload/uploadutil.jsp" %>
                        </div>
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

    <div class="formRowCenter">
        <dhcc:thirdButton value="提交" action="提交"  onclick="doSubmit('#cus-form');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
    </div>
</div>
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
</body>
</html>