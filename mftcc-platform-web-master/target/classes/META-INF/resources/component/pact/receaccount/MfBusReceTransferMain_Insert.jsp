<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceTransferMain_Insert.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
            var ajaxData = '${ajaxData}';
            ajaxData = JSON.parse(ajaxData);
            var isCusDoc = "cusDoc";
            var docParm = 'relNo=&scNo=rece_trans';
            $(function(){
                MfBusReceTransferMain_Insert.init();
            });

		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">账款转让登记</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<s:form method="post" id="MfBusReceTransferMainForm" theme="simple" name="operform" action="${webPath}/mfBusReceTransferMain/insertAjax">
							<dhcc:bootstarpTag property="formreceTransferMain" mode="query"/>
						</s:form>
					</div>
					<div class="list-table" style="display:none;">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>账款转让列表</span>
							<button class="btn btn-link formAdd-btn" onclick="CertiInfoList.insertCertiInfo();" title="新增"><i class="i i-jia3"></i></button>
							<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#accountList">
								<i class="i i-close-up"></i>
								<i class="i i-open-down"></i>
							</button>
						</div>
						<div id="accountList" class="content collapse in" aria-expanded="true">

						</div>
					</div>
					<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="MfBusReceTransferMain_Insert.receTransferInsertAjax('#MfBusReceTransferMainForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
