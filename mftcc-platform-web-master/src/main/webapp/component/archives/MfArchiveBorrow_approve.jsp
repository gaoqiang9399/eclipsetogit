<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript">
        var borrowId = '${mfArchiveBorrow.borrowId}';
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
                commitProcess("${webPath}/mfArchiveBorrow/submitUpdateAjax?opinionType="+opinionType+"&appNo="+borrowId,obj,'mfArchiveBorrow');
            }
        }
    </script>

</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <!--
        两列表单使用 col-md-8 col-md-offset-2
        四列表单使用 col-md-10 col-md-offset-1
         -->
        <div class="col-md-10 col-md-offset-1 margin_top_20">
            <div class="bootstarpTag">
               <%--<div class="form-title">借阅申请审批</div>--%>
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="MfArchiveBorrowForm" theme="simple" name="operform" action="${webPath}/mfArchiveBorrow/insertAjax">
                    <dhcc:bootstarpTag property="formmfarchiveborrowApproval" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
        <dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#MfArchiveBorrowForm');"></dhcc:thirdButton>
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
