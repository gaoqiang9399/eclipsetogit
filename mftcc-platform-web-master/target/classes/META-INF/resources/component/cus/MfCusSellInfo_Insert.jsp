<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
		<style>
			.formRowCenter{
				position:fixed;
				bottom:0;
				width:100%;
				margin:0px 0px;
			}
		</style>
		<script type="text/javascript">
			$(function() {
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				//经销区域选择组件
			       $("select[name=sellArea]").popupSelection({
							searchOn:true,//启用搜索
							inline:true,//下拉模式
							multiple:false,//单选
							addBtn:{
								"title":"新增",
								"fun":function(hiddenInput, elem){
									$(elem).popupSelection("hideSelect", elem);
									BASE.openDialogForSelect('新增经销区域','SALE_AREA',elem);
								}
							}
				});
				//经销行业别选择组件
			       $("select[name=sellWayclass]").popupSelection({
			       		title:true,
						searchOn:true,//启用搜索
						inline:false,//弹窗模式
						multiple : false,//单选
						valueClass : "show-text",//自定义显示值样式
						labelShow : false//已选项显示
				});
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="MfCusSellInfoInsert" method="post" theme="simple" name="operform" action="${webPath}/mfCusSellInfo/insertAjax">
						<dhcc:bootstarpTag property="formcussell0002" mode="query"/>
					</form>
				</div>
			</div>	
		</div>
		<div class="formRowCenter">
	    	<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#MfCusSellInfoInsert')"></dhcc:thirdButton>
	    	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	    </div>
	    </div>
	</body>
</html>
