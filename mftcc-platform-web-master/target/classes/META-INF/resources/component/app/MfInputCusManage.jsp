<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>修改</title>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="inputCusManageForm" theme="simple" name="operform" action="${webPath}/mfBusApply/inputCusManageAjax">
                    <dhcc:bootstarpTag property="forminputCusManage" mode="query" />
                    <div class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">
                        <button id="inputCusManage_button" type="button" class="btn btn-default" value="计算"
                                style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
                                onclick="MfInputCusManage.inputCusManageAjax('#inputCusManageForm');" >查询</button>
                    </div>
                </form>
                <div>
                </div>
                <div class="table_content" style="width:100%;margin-left:-15px;">
                    <div id="changediv" style="position: relative;margin-bottom:10px;margin-top:20px;" align="center">
                    </div>
                </div>
            </div>
            <div id = "query_cus" class="bigform_content col_content list-table-replan" style="display: none">
                <div class="title">
                    <span>
                        <i class="i i-xing blockDian"></i>
                        客户数据
                    </span>
                </div>
                <div id="queryCusList" class="content">
                    <dhcc:tableTag paginate="tablecusmanagequerylist" property="tablecusmanagequerylist" head="true" />
                </div>
            </div>
            <div id = "query_bus" class="bigform_content col_content list-table-replan" style="display: none">
                <div class="title">
                    <span>
                        <i class="i i-xing blockDian"></i>
                        业务数据
                    </span>
                </div>
                <div id="queryBusList" class="content">
                    <dhcc:tableTag paginate="tablecusmanagequerylist" property="tablecusmanagequerylist" head="true" />
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        MfInputCusManage.init();
    })
</script>
<script type="text/javascript" src='${webPath}/component/app/js/MfInputCusManage.js?v=${cssJsVersion}'> </script>
</html>
