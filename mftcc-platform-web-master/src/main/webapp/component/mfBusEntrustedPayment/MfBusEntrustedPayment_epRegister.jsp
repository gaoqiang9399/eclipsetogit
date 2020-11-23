<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>

<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript">
        var cusNo = '${cusNo}';
        var fincId = '${fincId}';
        var appId = '${appId}';
        var pactId = '${pactId}';
        var scNo = "entrusted_payment";
        var nodeNo = '${nodeNo}';
        var query = '${query}';
        var f = '${flag}';
        // var isCusDoc = "cusDoc";
        var docParm = "cusNo=" + cusNo + "&relNo=" + fincId + "&scNo=" + scNo;
        var aloneFlag = true;
        $(function () {
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    updateOnContentResize: true
                }
            });
            if(f=="error"){
                DIALOG.msg("受托支付已登记，请勿重复提交!",function () {
                    myclose_click();
                });
            }
            //受托金额根据借据金额自动带出
            var putoutAmtReal = $("input[name=putoutAmtReal]").val();
            $("input[name=epAmt]").val(putoutAmtReal)

            var atr  = $("input[name=epAmt]").attr("onblur").replace(";func_uior_valTypeImm(this)","");
            $("input[name=epAmt]").attr("onblur", atr);
        })


        var dataDocParm = {
            relNo: fincId,
            docType: "lawDoc",
            docTypeName: "受托支付文件",
            docSplitName: "文件上传",
            query: query,
        };

        function func_uior_valueFormatEpAmt(obj) {
            if ($(obj).val() == "" || $(obj).val() == undefined || $(obj).val() == null) {
                msg = "受托金额不能为空！";
                func_uior_addTips(obj, msg);
                return false;
            }
            var epAmt = $(obj).val().replaceAll(",", "") * 1.00;
            var putoutAmtReal = $("input[name=putoutAmtReal]").val().replaceAll(",", "") * 1.00;
            var msg = "";
            if (epAmt > putoutAmtReal) {
                msg = "受托金额不能大于借据金额！";
                func_uior_addTips(obj, msg);
                return false;
            } else if (!(epAmt > 0)) {
                msg = "受托金额必须大于0！";
                func_uior_addTips(obj, msg);
                return false;
            } else
                return true
        }

        function insert(obj) {
            var url = $(obj).attr("action");
            var ajaxData = [];
            $(obj).find('input').each(function () {
                if(this.name!=""&&this.name!=undefined&&this.name!=null)
                    data={};
                    data["name"]=this.name;
                    data["value"]=this.value;
                    ajaxData.push(data);
            });
            var flag = func_uior_valueFormatEpAmt("input[name=epAmt]");
            if (flag) {
                $.ajax({
                    url: url,
                    data: {ajaxData: JSON.stringify(ajaxData)},
                    success: function (data) {
                        if (data.flag == "success") {
                            DIALOG.msg(data.msg,function () {
                                myclose_click();
                            });
                        } else {
                            DIALOG.msg(data.msg,function () {});
                        }
                    }, error: function (date) {
                        DIALOG.msg("错误,请稍候重试或联系管理员！",function () {});
                    }
                });
            }
        }
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="epform" theme="simple" name="operform"
                      action="${webPath}/mfBusEntrustedPayment/insertAjax">
                    <dhcc:bootstarpTag property="formentrustedpayment0002" mode="query"/>
                </form>
                <%--<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->--%>
                <!--受托支付文件 -->
                <div class="row clearfix">
                    <div class="col-xs-12 column">
                        <%@ include file="/component/doc/webUpload/uploadutil.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="formRowCenter">
    <dhcc:thirdButton value="保存" action="保存"
                      onclick="insert('#epform');"></dhcc:thirdButton>
    <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/mfBusEntrustedPayment/js/MfBusBankAccCom.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactInsert.js"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript">

</script>
</html>
