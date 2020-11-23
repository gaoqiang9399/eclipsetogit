<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript"
            src="${webPath}/component/app/js/MfBusApply_RiskManagementYYResult.js"></script>
    <script type="text/javascript">
        var type = '${type}';
        var cusNo = '${cusNo}';
        $(function(){
            MfBusApply_RiskManagementYYResult.init();
        });
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="ApiReturnRecord" theme="simple"
                      name="operform" action="${webPath}/apiReturnRecord/insertAjax">
                    <dhcc:bootstarpTag property="formyouyuriskManagement" mode="query" />
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="查询" action="查询"
                          onclick="MfBusApply_RiskManagementYYResult.selectAjax('#ApiReturnRecord','0')"></dhcc:thirdButton>
        <dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
                          onclick="MfBusApply_RiskManagementYYResult.myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
