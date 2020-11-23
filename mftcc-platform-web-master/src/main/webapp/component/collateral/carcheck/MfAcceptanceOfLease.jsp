<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>详情</title>
	<script type="text/javascript" src='${webPath}/component/collateral/carcheck/js/MfRegisterCarDetail.js'></script>
	<script type="text/javascript">
        var appId = '${appId}';
        var pledgeNoStr = "";
        $(function(){
            MfRegisterCarDetail.init();
        });
	</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-8 col-md-offset-2 margin_top_20">
			<div class="bootstarpTag">
				<!-- <div class="form-title">登记提车地点</div> -->
				<p style="text-align: center"><font size="4" face="verdana" >申请信息</font></p>
				<form method="post" id="MfRegisterCarDetailForm" theme="simple" name="operform">
					<dhcc:bootstarpTag property="formapplyNjzlDetail" mode="query"/>
				</form>
			</div>
			<p style="text-align: center"><font size="4" face="verdana" >网站</font></p>
			<div class="bigform_content col_content">
				<iframe style="width: 100%;height: 100%;" src="https://www.baidu.com" frameborder="0" scrolling="no" id="testIframe" onload="this.height=100;this.width=100;"></iframe>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="确定" action="确定" onclick="MfRegisterCarDetail.submitFormAjax()"></dhcc:thirdButton>
		<%--<dhcc:thirdButton value="保存" action="保存" onclick="MfRegisterCarDetail.insertAjax('#MfRegisterCarDetailForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="提交" action="提交" onclick="MfRegisterCarDetail.submitCusAjax('#MfRegisterCarDetailForm')"></dhcc:thirdButton>
--%>		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
<script type="text/javascript">
    function reinitIframe(){
        var iframe = document.getElementById("testIframe");
        try{
            var bHeight = iframe.contentWindow.document.body.scrollHeight;
            var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
            var height = Math.max(bHeight, dHeight);
            iframe.height = height;
            console.log(height);
        }catch (ex){}
    }
    window.setInterval("reinitIframe()", 200);
</script>
</html>