<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	<script type="text/javascript">
		$(function(){
		var PUSH_CAPITAL_TYPE =${ajaxData};
			$("input[name=pushCapitalType]").popupSelection({
                title:true, //标题
                searchOn:true,//启用搜索
                inline:false,//弹窗模式
                multiple:true,//多选
                labelShow:false,//选择区域显示已选择项
                items:PUSH_CAPITAL_TYPE
			});
			
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfCusLegalEquityInfoForm" theme="simple" name="operform" action="${webPath}/mfCusLegalEquityInfo/insertAjax">
							<dhcc:bootstarpTag property="formcusequ00003" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#MfCusLegalEquityInfoForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
