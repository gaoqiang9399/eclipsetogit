<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusLegalEquityInfo.js"></script>
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
		})
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusLegalEquityInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusLegalEquityInfo/updateAjax">
							<dhcc:bootstarpTag property="formcusequ00003" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#cusLegalEquityInfoUpdate');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>