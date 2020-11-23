<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo='${cusNo}';
			var appId='${appId}';
			var entrance = "business";
			var entrFlag='${entrFlag}';
			
			var formId='${formId}';
			var busModel='${mfBusApply.busModel}';
			var classId='${classId}';
			var isQuote="0";
			var ajaxData = '${ajaxData}';
		    ajaxData = eval("("+ajaxData+")");
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="confirmEval" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="submitConfirmValue('#confirmEval')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
	<script type="text/javascript">
	$(function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("input[name=classId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.collClass
		});
	})
	function submitConfirmValue(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:webPath+"/mfBusCollateralRel/doJustConfirmEvalInfo",
				type:'post',
				data : {
					ajaxData : dataParam,
					appId:appId,
					entrFlag:entrFlag,
					isQuote:isQuote
				},
				success:function(data){
					LoadingAnimate.stop();
					if(data.flag=="success"){
						window.top.alert(data.msg,3);
						var url = '${webPath}/mfBusApply/getSummary?appId='+appId+'&busEntrance=1';
					$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						top.flag = true;
						myclose_click();
					}else{
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			})
		}
	}
	</script>
</html>
