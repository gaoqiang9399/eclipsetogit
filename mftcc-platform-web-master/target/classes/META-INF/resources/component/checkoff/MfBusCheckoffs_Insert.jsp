<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/checkoff/js/MfBusCheckoffs_List.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusCheckoffsForm" theme="simple" name="operform" action="${webPath}/mfBusCheckoffs/insertAjax">
							<dhcc:bootstarpTag property="formcheckoffdetail" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="checkOffs.addCheckOffSubmit('#MfBusCheckoffsForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
		<script type="text/javascript">
		
			var tmpprcp=$('input[name=checkoffPrcp]').val();
			var tmpInitst=$('input[name=checkoffIntst]').val();
			var tmpFee=$('input[name=checkoffFee]').val();
			var tmpSum=$('input[name=checkoffSum]').val();
			var dataSourceType=${dataSourceType};
			
			$(function(){
				checkOffs.checkOffDatasource(dataSourceType);//初始化核销数据数据来源
				var curType1=$('select[name=checkoffType]').val();
				checkOffs.checkOffTypeChg(curType1);//初始化核销类型
		
			//意见类型新版选择组件
				$('select[name=checkoffType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback : function (obj, elem) {
						checkOffs.checkOffTypeChg($(obj).val());
					}
				}); 
				
				$('select[name=checkoffMethod]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback : function (obj, elem) {
					}

				}); 

			})
		</script>
	</body>
</html>
