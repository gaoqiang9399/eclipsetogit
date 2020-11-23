<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body class="bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag">
                <form  method="post" theme="simple" name="operform" action=""  id="insertForm">
                    <dhcc:bootstarpTag property="formbusIntentionApplyDetail" mode="query"/>
                </form>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-xs-12 column" >
                <%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
       <%-- <dhcc:thirdButton value="发起授信申请" action="发起授信申请" onclick="MfBusIntentionApply.creditApply()"></dhcc:thirdButton>--%>
           <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/intention/js/MfBusIntentionApply.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var id = '${id}';
    var aloneFlag = true;
    var dataDocParm={
        relNo:id,
        docType:"31",
        docTypeName:"意向资料",
        docSplitName:"意向资料",
        query:''
    };
    $(function() {
        //滚动条
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                updateOnContentResize : true
            }
        });
    });
</script>
</html>
