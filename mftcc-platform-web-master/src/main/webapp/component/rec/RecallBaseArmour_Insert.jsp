<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	    <link rel="stylesheet" href="${webPath}/component/rec/css/RecallBase.css" />
	</head>
	<script type="text/javascript" src="${webPath}/component/rec/js/RecallBase_Insert.js"></script>
	<script type="text/javascript">
		var recallId = '${recallId}';
		var aloneFlag = true;
		var dataDocParm={
			relNo:recallId,
			docType:"lawDoc",
			docTypeName:"催收资料",
			docSplitName:"催收相关资料",
			query:'',
		};

		var pactId = '${recallBase.conNo}';
		var cusNo = '${recallBase.cusNo}';
		function sysUserCallBack(data){//个人的回调
			$("input[name=mgrNo]").val(data.opNo);
		}
		$(function(){
			ReCallBaseInsert.init();
		});
		$(document.body).height($(window).height());
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="head-div">
						<div class="row clearfix margin_bottom_20">
							<span class="font_size_16">${recallBase.appName}</span>
						</div>
						<div class="row clearfix">
							<div class="col-md-9 column">
								<table class="common-head">
									<tr>
										<td class="tdlabel">客户名称：</td>
										<td class="tdvalue" colspan="5">${recallBase.cusName}</td>
									</tr>
									<tr>
										<td class="tdlabel">联系人：</td>
										<td class="tdvalue">${recallBase.cusContactName}</td>
										<td class="tdlabel">应还本金：</td>
										<td class="tdvalue">${dataMap.recallUnpayAmt1}元</td>
										<td class="tdlabel">应还利息：</td>
										<td class="tdvalue">${dataMap.recallUnpayAmt2}元</td>
									</tr>
									<tr>
										<td class="tdlabel">联系电话：</td>
										<td class="tdvalue">${recallBase.cusTel}</td>
										<td class="tdlabel">违约金：</td>
										<td class="tdvalue">${dataMap.brcContAmt}元</td>
										<td class="tdlabel">催收总额：</td>
										<td class="tdvalue">${dataMap.recallAmt}元</td>
									</tr>
								</table>
							</div>
							<div class="col-md-3 column">
								<div style="height:90px;padding-top: 50px;">
									<button type="button" class="btn btn-primary btn-rec active" onclick="ReCallBaseInsert.recallRegist(this);">催收办理</button>
									<button type="button" class="btn btn-primary btn-rec" onclick="ReCallBaseInsert.recallAssign(this);">催收指派</button>
								</div>
							</div>
						</div>
					</div>
					<div class="form-div">
						<div class="col-md-10 col-md-offset-1 column margin_top_20">
							<div id="registDiv">
								<div class="opt-title color_theme">催收办理</div>
								<div class="bootstarpTag">
									<form method="post" id="registForm" theme="simple" name="edit-form" action="${webPath}/recallBase/insertForSimuAjax">
										<dhcc:bootstarpTag property="formrec0013" mode="query" />
									</form>
										<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
								</div>
								<%-- <%@ include file="/component/include/biz_node_plugins.jsp"%> --%><!-- 功能挂载(要件、文档、费用...) -->
							</div>
							<div id="assignDiv" class="hide">
								<div class="opt-title color_theme">催收指派</div>
								<div class="bootstarpTag">
									<form method="post" id="assignForm" theme="simple" name="edit-form" action="${webPath}/recallBase/insertAjax">
										<dhcc:bootstarpTag property="formrec0009" mode="query" />
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="registBtn" class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="ReCallBaseInsert.doSubmit('#registForm','regist');"></dhcc:thirdButton>
				  <c:if test='${query eq "detailPage"}'>
					 <dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="ReCallBaseInsert.returnChoose();"></dhcc:thirdButton>
				 </c:if>
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
			<div id="assignBtn" class="formRowCenter hide" >
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="ReCallBaseInsert.doSubmit('#assignForm','assign');"></dhcc:thirdButton>
				 <c:if test='${query eq "detailPage"}'>
					 <dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="ReCallBaseInsert.returnChoose();"></dhcc:thirdButton>
				 </c:if>
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
