<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>详情</title>
    <script type="text/javascript">
        $(function () {
            $("#tablist").show();
        });
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 margin_top_20">
            <div class="arch_sort" style="border: 0px;">
                <div class="dynamic-block" title="还款申请详情" name="MfRepayApplyAction" data-sort="14"
                     data-tablename="mf_repay_apply">
                    <div class="list-table">
                        <div class="title">
                            <span class="formName"><i class="i i-xing blockDian"></i>还款申请详情</span>
                            <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse"
                                    data-target="#MfRepayApplyAction">
                                <i class="i i-close-up"></i><i class="i i-open-down"></i>
                            </button>
                        </div>
                        <div class="content collapse in" id="MfRepayApplyAction"
                             style="margin-top: 10px;">
                            <form method="post" id="MfRepayApplyForm" theme="simple" name="operform"
                                  action="${webPath}/mfRepayApply/updateAjax">
                                <dhcc:propertySeeTag property="formRepayAppDetail" mode="query"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <%--还款借据--%>
            <div class="arch_sort family_sort" style="border: 0px;">
                <div class="dynamic-block" title="还款借据" name="MfRepayFincAction" data-sort="14" data-tablename="mf_mfRepay_finc">
                    <div class="list-table">
                        <div class="title">
                            <span class="formName"><i class="i i-xing blockDian"></i>还款借据</span>
                            <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfRepayFincAction">
                                <i class="i i-close-up"></i><i class="i i-open-down"></i>
                            </button>
                        </div>
                        <div class="content collapse in" id="MfRepayFincAction">
                            ${tableHtml}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="formRowCenter">
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>