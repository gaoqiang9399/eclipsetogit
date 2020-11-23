<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/PledgeEval_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
	</head>
	<script type="text/javascript">
		var ajaxData = '${ajaxData}';
	    ajaxData = JSON.parse(ajaxData);
	    var appId='${appId}';
	    var entrance = "business";
		var formId='${formId}';
		var entrFlag='${entrFlag}';
		var cusNo='${cusNo}';
		var busModel='${mfBusApply.busModel}';
		var classId='${classId}';
		var vouType = '${vouType}';
		var classModel = '${classModel}';
		var corpCusTypeStr='${dataMap.corpCusTypeStr}';
	    var perCusTypeStr='${dataMap.perCusTypeStr}';
		var corpIdTypeStr='${dataMap.corpIdTypeStr}';
	    var perIdTypeStr='${dataMap.perIdTypeStr}';
	  	//是否支持跳过标识0不支持1支持
	    var supportSkipFlag ='${supportSkipFlag}';
		var isQuote="0";
	    var extensionApplyId ='${extensionApplyId}';
	    var nodeNo = '${nodeNo}';
	    var changeCusRelFlag = '${dataMap.changeCusRelFlag}';
	    var busEndDate = '${busEndDate}';
		var appAmt = '${mfBusApply.appAmt}';

		$(function() {
			PledgeEval_Insert.init();
			
		});
		function selectAssureList(){
			CollateralCommon.selectAssureList();
		}
		
	</script>
	<script type="text/javascript">
			//根据身份证信息填写性别，生日，年龄
			var func_using_IDcard_to_set_sex_birthday=function(idNode){				
				var idType = $("select[name=idType]").val();				
				var id = $(idNode).val();				
				if(idType == "0" && 18 == id.length ){				
					$("select[name=sex]").val("");					
					StringUtil.setBirthyAndSexByID(idNode, 'sex', 'brithday','age');							
				}
			}
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn" style="min-height:100%;">
					<c:if test='${vouType=="1"}'>
           			 	<div class="text-center">主反担保方式为无反担保，不需要添加反担保信息</div>
	           		</c:if>
	           		<c:if test='${vouType!="1"}'>
	           			 <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						  <form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/insertPledgeEvalAjax">
							 <dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						  </form>
						  <input type="hidden" name = "isQuote"/>
						  <input type="hidden" name = "entrFlag"/>
					</c:if>
				    </div>
				<!-- 授信中和担保信息新增押品信息，不需要上传要件 -->
				<c:if test="${entrFlag!='credit' && entrFlag!='collateral'}">
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
				</c:if>
			</div>
		 </div>
			<div class="formRowCenter">
				<c:if test='${vouType!="1"}'>
	   				<dhcc:thirdButton value="保存" action="保存" onclick="PledgeEval_Insert.insertCollateralBase('#pledgeBaseInfoInsert','0');"></dhcc:thirdButton>
				</c:if>
	   			<dhcc:thirdButton value="跳过" action="跳过" typeclass="skipButton" onclick="PledgeEval_Insert.insertCollateralBase('#pledgeBaseInfoInsert','1');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
