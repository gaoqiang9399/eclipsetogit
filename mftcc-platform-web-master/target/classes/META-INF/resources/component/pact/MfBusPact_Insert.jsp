<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>

<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript">
        var cusNo = '${mfBusPact.cusNo}';
        var busModel = '${mfBusPact.busModel}';
        var appId = '${appId}';
        var pactId = '${pactId}';
        var wkfAppId = '${wkfAppId}';
        var taskId = '${taskId}';
        var coborrNum = ${coborrNum};//共同借款人
        var ajaxData = ${ajaxData};
        //ajaxData = JSON.parse(ajaxData);
        var relNo = '${mfBusPact.pactId}';// 要件业务编号
        var cmpdRateType = '${mfBusPact.cmpFltRateShow}';
        var processId = '${mfBusPact.pactProcessId}';
        var calcIntstFlag = '${calcIntstFlag}';//1-算头不算尾 2-首尾都计算
        var pactEndDateShowFlag = '${pactEndDateShowFlag}';//合同借据结束日期展示  1-显示结束日期 2-显示结束日期减一天
        var repayType = '${repayType}';//还款方式
        var autoComData = [];
        var creditPactId = '${mfBusPact.creditPactId}';
        var projectName = '${projectName}';
        var followPactNoShowSts = '${followPactNoShowSts}';
        var nodeNoOld = '${nodeNo}';
        var fincId = '';
        var baseRateChange = '${baseRateChange}';
        function checkOrg(obj) {
            var brName = $("input[name='companyName']").val();
            if (autoComData.length == 0) {
                url = webPath + "/sysOrg/getAllChildCompanyAjax?brName=" + brName;
                $.getJSON(url, {}, function (data) {
                    autoComData = data.items;
                    for (var item in autoComData) {
                        autoComData[item].label = autoComData[item].brName;
                    }
                    prodAutoMenu(obj, autoComData, function (data) {
                        $(obj).val(data.brName);
                        $("input[name='companyId']").val(data.brNo);
                    }, null, '', false);
                });
            }
            prodAutoMenu(obj, autoComData, function (data) {
                $(obj).val(data.brName);
                $("input[name='companyId']").val(data.brNo);
            }, null, '', false);
        }


    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="pactInsertForm" theme="simple" name="operform" action="${webPath}/mfBusPact/updateAjax">
                    <dhcc:bootstarpTag property="formpact0008" mode="query"/>
                </form>
                <c:if test="${mfBusFollowPactList != null}">
                    <div class="list-table">
                        <div class="title">
                            <span><i class="i i-xing blockDian"></i>从合同文档</span>
                            <button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#followPact">
                                <i class="i i-close-up"></i>
                                <i class="i i-open-down"></i>
                            </button>
                        </div>
                        <div id="followPact" class="content collapse in" aria-expanded="true">
                            <dhcc:tableTag paginate="mfBusFollowPactList" property="tablemfBusFollowPactNo" head="true"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${busModel != '12'}">
                    <div class="list-table" id="mfBusPactExtendListDiv">
                        <div class="title">
                            <span><i class="i i-xing blockDian"></i>非系统生成相关合同</span>
                            <button class="btn btn-link formAdd-btn" onclick="MfBusPactInsert.addPactExtend();" title="新增"><i class="i i-jia3"></i></button>
                            <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExtendList">
                                <i class="i i-close-up"></i><i class="i i-open-down"></i>
                            </button>
                        </div>
                        <div class="content collapse in" id="mfBusPactExtendList" name="mfBusPactExtendList">
                            <dhcc:tableTag property="tablemfBusPactExtendAppList" paginate="mfBusPactExtendList" head="true"></dhcc:tableTag>
                        </div>
                    </div>
                </c:if>

                <div id="coborrNumName" class="row clearfix" style="display: none">
                <%@ include file="/component/app/MfBusCoborrPactList.jsp"%>
            </div>
                <c:if test="${busModel != '12'}">
                    <div class="form-tips">说明：系统将主要合同已经生成，请点击下方系统生成合同名称，完成编辑后请保存。除系统生成合同以外，可以添加其他合同。</div>
                </c:if>
                <%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
            </div>
        </div>
    </div>

</div>

<c:if test="${SAVE_ONLY_5 == '0'}">
    <div class="formRowCenter">
        <c:if test="${busModel != '12'}">
            <dhcc:thirdButton value="暂存" action="暂存" onclick="MfBusPactInsert.ajaxUpdateConfirm('#pactInsertForm', '1');"></dhcc:thirdButton>
        </c:if>
        <dhcc:thirdButton value="提交" action="提交" onclick="MfBusPactInsert.ajaxUpdateConfirm('#pactInsertForm', '0');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</c:if>
<c:if test="${SAVE_ONLY_5 == '1'}">
    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="MfBusPactInsert.ajaxUpdateConfirm('#pactInsertForm', '1');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
        <dhcc:thirdButton value="提交" action="提交" onclick="MfBusPactInsert.ajaxUpdateConfirm('#pactInsertForm', '0');"></dhcc:thirdButton>
    </div>
</c:if>

</body>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactInsert.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var kindNo = ${mfBusPact.kindNo}
    $(function () {
        MfBusPactInsert.init();
    });
</script>
</html>
