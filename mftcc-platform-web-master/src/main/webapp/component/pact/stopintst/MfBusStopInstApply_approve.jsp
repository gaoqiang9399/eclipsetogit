<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/pact/stopintst/js/MfBusStopIntstApply.js"></script>
    <script type="text/javascript">
        var stopId = '${mfBusStopIntstApply.stopId}';
        $(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
            //意见类型新版选择组件
            $('select[name=opinionType]').popupSelection({
                inline: true, //下拉模式
                multiple: false, //单选
                changeCallback:WkfApprove.opinionTypeChange
            });
        });

        //审批提交
        function doSubmit(obj){
            var opinionType = $("[name=opinionType]").val();
            var flag = submitJsMethod($(obj).get(0), '');
            if(flag){
                commitProcess("${webPath}/mfBusStopIntstApply/submitUpdateAjax?opinionType="+opinionType+"&appNo="+stopId,obj,'mfBusStopIntstApply');
            }
        }
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">

        <div class="col-md-8 col-md-offset-2 margin_top_20">
            <div class="bootstarpTag">
                <!-- <div class="form-title">停息申请表</div> -->
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="MfBusStopIntstApplyForm" theme="simple" name="operform" action="${webPath}/mfBusStopIntstApply/insertAjax">
                    <dhcc:bootstarpTag property="formstopintstapprove" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
        <dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#MfBusStopIntstApplyForm')"></dhcc:thirdButton>
        <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
    </div>
</div>
<input name="taskId" id="taskId" type="hidden" value="${taskId }" />
<input name="activityType" id="activityType" type="hidden" value="${activityType }" />
<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext }" />
<input name="transition" type="hidden" />
<input name="nextUser" type="hidden" />
<input name="designateType" type="hidden" value="${designateType }" />
</body>
</html>
