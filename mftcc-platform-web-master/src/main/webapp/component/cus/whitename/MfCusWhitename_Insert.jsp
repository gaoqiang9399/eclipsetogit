<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
		<script type="text/javascript" src="${webPath}/component/cus/whitename/js/MfCusWhitename_Insert.js"></script>
		<script type="text/javascript" >
			$(function(){
				MfCusWhitename_Insert.init();
			 });
		</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">人力需求表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusWhitenameForm" theme="simple" name="operform" action="${webPath}/mfCusWhitename/insertAjax">
							<dhcc:bootstarpTag property="formwhitenamebase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="MfCusWhitename_Insert.ajaxSave('#MfCusWhitenameForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfCusWhitename_Insert.myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>

	</body>
</html>
<script type="text/javascript">
	$(function () {
        $.ajax({
            url:webPath + '/mfBusTrench/getCusTypeNotShowAjax',
            data:'baseTypes=1,2',
            dataType:'json',
            async:false,
            type:'POST',
            success:function(data){
                var notShowCusTypes=data.cusTypeList;
                if(notShowCusTypes!=null){
                    for(var i=0;i<notShowCusTypes.length;i++){
                        var typeNo=notShowCusTypes[i].typeNo;
                        console.log(typeNo);
                        $("[name=cusType]").find("option[value="+typeNo+"]").remove();
                    }
                }
            }
        });
    });
</script>

