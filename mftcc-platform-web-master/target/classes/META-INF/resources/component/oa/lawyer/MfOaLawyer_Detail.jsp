<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/wkf/Wkf_Base.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>详情</title>
    <link rel="stylesheet"
          href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css"/>
    <link rel="stylesheet"
          href="${webPath}/tech/wkf/detail/wjProcessDetail.css"/>
    <script type="text/javascript"
            src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
    <script type="text/javascript"
            src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
    <script type="text/javascript">
        var id = '${mfOaLawyer.id}';
        var from = "lawyer";
        var aloneFlag = true;
    </script>
</head>
<body class="bg-white" style="height: 100%;">



    <div class=" scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;padding:0px;">
        <div class="col-xs-10 col-xs-offset-1" style="margin-top: 20px;">
            <div class="arch_sort">
                <div class="row clearfix">
                    <div class="col-xs-12 column info-block">
                        <div id="spInfo-block">
                            <div class="form-table">
                                <div class="title">
                                    <span><i class="i i-xing blockDian"></i>律师详情</span>
                                    <button class="btn btn-link pull-right formAdd-btn"
                                            data-toggle="collapse" data-target="#spInfo-div"></button>
                                </div>
                                <div id="base_div" disable="true" class="content collapse in">
                                    <form method="post" id="MfOaLeaveForm"
                                          theme="simple" name="operform"
                                          action="${webPath}/mfOaLawyer/updateAjax">
                                        <dhcc:propertySeeTag property="formlawyerDetail0002"
                                                             mode="query"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 10px;"></div>
    </div>
    <div id="approvalBtn" class="formRowCenter " style="display: block;">
        <dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>

</body>
<script type="text/javascript">
    $(function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                theme: "minimal-dark",
                updateOnContentResize: true
            }
        });
    });

</script>
</html>