<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<%-- <head>
		<title>详情</title>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">股权投资情况</div>
				<form  method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formcusequ00001" mode="query" />
				</form>	
			</div>
		</div>
	</body> --%>
	
	<head>
		<title>详情</title>
		
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
				<form  method="post" id="cusEquityInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusEquityInfo/updateAjax">
					<dhcc:bootstarpTag property="formcusequ00003" mode="query" />
				</form>
			</div>
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusEquityInfoUpdate');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
		<script type="text/javascript">
		var PUSH_CAPITAL_TYPE =${ajaxData};
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					updateOnContentResize:true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
		
				 $("input[name=pushCapitalType]").popupSelection({
					searchOn:true,//启用搜索
					multiple:true,//单选
					labelShow: false,//不显示已选选项
					inline:false,//弹出模式
					title:"出资方式",
					items:PUSH_CAPITAL_TYPE
			});
		});
		function updateCallBack(){
			top.addFlag = true;
			
			myclose_click();
		};
		</script>
	</body>
</html>