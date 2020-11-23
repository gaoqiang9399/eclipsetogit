<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增授信申请表单</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
		<c:choose>
			<c:when test="${creditType=='2'}">
				<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfCusCreditAdjustApplyForm" theme="simple" name="operform" action="${webPath}/mfCusCreditAdjustApply/insertAjax">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
						</form>
					</div>
				</div>
				<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div> 
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusCreditAdjustApplyInsert.ajaxInsert('#MfCusCreditAdjustApplyForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
			</c:when>
			<c:otherwise>
				<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
				 	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditApply/insertAjax">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
						</form>
					</div>
				</div>
				<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div> 
				</div>
				<div class="formRowCenter">
					<dhcc:thirdButton value="保存" action="保存" typeclass="saveButton" onclick="mfCusCreditApplyInsert.ajaxInsert('#operform')"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditApplyInsert.close();"></dhcc:thirdButton>
				</div>
			</c:otherwise>
		</c:choose>
		  
		</div>
	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript">
			var index = 0;  //动态增加产品计数使用
			var path = "${webPath}";
			var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
			var cusType='${mfCusCustomer.cusType}';
			var baseType = "${baseType}";
			var creditType = "${creditType}";
			var creditFlag='${creditFlag}';//是否授信标识0未授信1已授信
			var termFlag='${termFlag}';//如果已授信，当前日期是否在授信期限
			var $form=$("#operform");
			var cusNo='${cusNo}';
			var creditModel='${creditModel}';//授信模式 1客户授信 2立项授信
			var creditAppId = '${creditAppId}';
			var evalCreditAmt  = '${evalCreditAmt}';
			var scNo='${scNo}';
			var docParm = "cusNo="+cusNo+"&relNo="+creditAppId+"&scNo="+scNo;//查询文档信息的url的参数
            var CREDIT_END_DATE_REDUCE ='${CREDIT_END_DATE_REDUCE}';// 授信结束日自动减一天
			$(function(){
				mfCusCreditApplyInsert.init();
				if(evalCreditAmt != null && evalCreditAmt != ""){
                    $("[name=creditSum]").attr("placeholder",evalCreditAmt);
				}
			});
		</script>
</html>
