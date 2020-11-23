<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusEntZoned.js"></script>
    <script type="text/javascript" src="${webPath}/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>
    <script type="text/javascript">
        $(function () {
            /*$(".scroll-content").mCustomScrollbar({//滚动条的生成
                advanced: {
                    theme: "minimal-dark",
                    updateOnContentResize: true
                }
            });*/
            MfCusEntZoned.init();
        });

        //行业分类选择后的回调处理
        function nmdWaycCallBack(nmdWayInfo) {
            var oldWayClass = $("input[name=wayClass]").val();
            $("input[name=wayClassDes]").val(nmdWayInfo.wayName);
            $("input[name=wayClass]").val(nmdWayInfo.wayNo);
            $("input[name=wayMaxClass]").val(nmdWayInfo.wayMaxClass);
        };
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <!--
        两列表单使用 col-md-8 col-md-offset-2
        四列表单使用 col-md-10 col-md-offset-1
         -->
        <div class="col-md-8 col-md-offset-2 margin_top_20">
            <div class="bootstarpTag">
                <!-- <div class="form-title">法院信息 mf_cus_court_info</div> -->
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="MfCusEntZonedForm" theme="simple" name="operform" action="${webPath}/mfCusEntZoned/insertAjax">
                    <dhcc:bootstarpTag property="formmfcusEntZonedBase" mode="query"/>
                </form>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-xs-12 column">
                <div class="list-table-replan">
                    <div class="content margin_left_15 collapse in" id="capitalList">
                        <dhcc:tableTag property="tablecusEntZonedBaseList" paginate="mfCusEntZonedList" head="true"></dhcc:tableTag>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
        <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#MfCusEntZonedForm')"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
