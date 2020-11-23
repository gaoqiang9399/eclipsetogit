<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增授信申请表单</title>
    <script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
    <script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
    <script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
    <script type="text/javascript">
        var index = 0;  //动态增加产品计数使用
        var path = "${webPath}";
        var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
        var cusType = '${mfCusCustomer.cusType}';
        var baseType = "${baseType}";
        var creditType = "${creditType}";
        var creditFlag = '${creditFlag}';//是否授信标识0未授信1已授信
        var termFlag = '${termFlag}';//如果已授信，当前日期是否在授信期限内
        var $form = $("#operform");
        var cusNo = '${cusNo}';
        var projectName = '${projectName}';//获得项目名称
        var mfBusFundDetailList = '${mfBusFundDetailList}';
        var creditModel = '${creditModel}';//授信模式 1客户授信 2立项授信
        var creditAppId = '${creditAppId}';
        var scNo = '${scNo}';
        var docParm = "cusNo=" + cusNo + "&relNo=" + creditAppId + "&scNo=" + scNo;//查询文档信息的url的参数
        $(function () {
            mfCusCreditApplyInsert.init();
        });
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" theme="simple" name="operform" id="mfBusFundDetailForm"
                      action="${webPath}/mfCusCreditApply/insertAjax">
                    <dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
                </form>
            </div>
        </div>
        <!--上传文件-->
        <div class="row clearfix">
            <div class="col-xs-10 col-md-offset-1 column">
                <%@include file="/component/doc/webUpload/uploadutil.jsp" %>
            </div>
        </div>
        <div class="bigform_content col_content">
            <div id="mfBusFundDetailList" class="table_content">
                <dhcc:tableTag paginate="mfBusFundDetailList" property="tablemfbusfunddetail0002" head="true"/>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" typeclass="saveButton"
                          onclick="mfCusCreditApplyInsert.insertAjax('#mfBusFundDetailForm')"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
                          onclick="mfCusCreditApplyInsert.close();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>