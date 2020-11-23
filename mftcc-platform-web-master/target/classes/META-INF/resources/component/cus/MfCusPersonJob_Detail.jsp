<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="cusPersonJobInsert" theme="simple" name="operform" action="${webPath}/mfCusPersonJob/updateAjax">
					<dhcc:bootstarpTag property="formcusJobBase" mode="query" />
				</form>
			</div>
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusPersonJobInsert','update');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
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
			//单位性质选择组件
        $("select[name=corpKind]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false//单选
		});
	});
</script>
</html>