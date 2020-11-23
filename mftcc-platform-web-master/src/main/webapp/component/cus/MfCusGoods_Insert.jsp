<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
<%-- 	<body>
		<div class="bigform_content">
			<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusGoods/insert">
				<dhcc:bigFormTag property="formcusgoods0001" mode="query"/>
				<div class="formRow">
	    			<dhcc:thirdButton value="提交" action="提交" commit="true"></dhcc:thirdButton>
	    			<dhcc:thirdButton value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form)"></dhcc:thirdButton>
	    		</div>
			</form>	
		</div>
	</body> --%>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="cusGoodsInsert" theme="simple" name="operform" action="${webPath}/mfCusGoods/insertAjax">
						<dhcc:bootstarpTag property="formcusgoods0001" mode="query"/>
					</form>
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusGoodsInsert');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript">

	$(function() {
// 		$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 		});
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
	 function insertCallBack() {
		top.addFlag = true;
		top.formOrTable = "table";
		myclose_click();
	}; 
</script>
</html>
