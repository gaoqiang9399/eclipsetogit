<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<%-- <body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">客户产品产能信息表</div>
				<form  method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formcusgoods0002" mode="query" />
				</form>	
			</div>
		</div>
	</body> --%>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
				<form  method="post" id="cusGoodsUpdate" theme="simple" name="operform" action="${webPath}/mfCusGoods/updateAjax">
					<dhcc:bootstarpTag property="formcusgoods0001" mode="query" />
				</form>
			</div>
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusGoodsUpdate');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
		<script type="text/javascript">
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			//销售区域选择组件
        $("select[name=saleArea]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//单选
					addBtn:{
						"title":"新增",
						"fun":function(hiddenInput, elem){
							$(elem).popupSelection("hideSelect",elem);
							BASE.openDialogForSelect('新增销售区域','SALE_AREA',elem);
						}
					}
		});
		//产品遵守规则选择组件
        $("select[name=goodsRule]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//单选
					addBtn:{
						"title":"新增",
						"fun":function(hiddenInput, elem){
							$(elem).popupSelection("hideSelect",elem);
							BASE.openDialogForSelect('新增产品遵守规则','GOODS_RULE',elem);
						}
					}
		});
		});
		function updateCallBack(){
			top.addFlag = true;

			myclose_click();
		};
		</script>
	</body>
</html>