<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>新增</title>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="registForm" theme="simple" name="operform" action="${webPath}/recallBase/insertForSimuAjax1">
                    <dhcc:bootstarpTag property="formcollectionhandleadd" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div id="registBtn" class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存"
                          onclick="RecallBaseQueryEntrance.doSubmit('#registForm','regist');"></dhcc:thirdButton>
        <%--<c:if test='${query eq "detailPage"}'>--%>
            <%--<dhcc:thirdButton value="返回" action="返回" typeclass="cancel"--%>
                              <%--onclick="ReCallBaseInsert.returnChoose();"></dhcc:thirdButton>--%>
        <%--</c:if>--%>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>

<script type="text/javascript" src="${webPath}/component/rec/js/RecallBase_QueryEntrance.js?v=${cssJsVersion}"></script>
<script type="text/javascript">

    var preRepayType="2";
    var termInstMustBack="0";
    var returnPlanPoint="2";
    function sysUserCallBack(data) {//个人的回调，催收人号
        $("input[name=mgrNo]").val(data.opNo);
    }
    $(function(){
        RecallBaseQueryEntrance.init();
    });
    $(document.body).height($(window).height());
</script>
</html>
