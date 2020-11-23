<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>详情</title>
    <script type="text/javascript">

        $(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
        });

        function saveMsgConfig(obj){
            var flag = submitJsMethod($(obj).get(0), '');
            if(flag){
                ajaxInsertCusForm(obj);
            }
        };
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="freeMarkerForm" theme="simple" name="operform" action="${webPath}/freeMarkerDownloadModel/updateAjaxp">
                    <dhcc:bootstarpTag property="formfreeMarker0002" mode="query"/>
                </form>
            </div>
        </div>
    </div>


    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="saveMsgConfig('#freeMarkerForm')"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>