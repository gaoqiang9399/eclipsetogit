<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改</title>
</head>
<body class="overflowHidden">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form  method="post" id="clearBrForm" name="operform" action="${webPath}/brClear/clearBrAjax">
                    <dhcc:bootstarpTag property="formclearBrEdit" mode="query"/>
                </form>
            </div>
            <div style="display: table" class="col-md-12 col-md-offset-0 margin_top_10" id="test">
                <div class="row clearfix">
                    <div class="col-xs-12 column">
                        <div class="list-table-replan">
                            <div class="content margin_left_15 collapse in" id="tablebrclear0001">
                                <dhcc:tableTag property="tablebrclear0001" paginate="tablebrclear0001" head="true"></dhcc:tableTag>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="清理" action="清理" onclick="MfBrClealInfo.dataBrClear();"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
<script>
    $(function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    })
</script>
<script type="text/javascript" src='${webPath}/component/clean/js/MfBrClearInfo.js?v=${cssJsVersion}'> </script>
</html>
