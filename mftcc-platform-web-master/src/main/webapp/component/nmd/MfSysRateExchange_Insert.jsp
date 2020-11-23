<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%-- <%@ include file="/component/include/pub_view.jsp"%> --%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/mfSysRateExchange/insertAjax">
						<dhcc:bootstarpTag property="formexchangerate0002" mode="query" />
					</form>
					<div id="descContent">
						
					</div>
				</div>
			</div>
		</div>
		
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myInsertAjax('#insertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
	
</body>
<script type="text/javascript" src="${webPath}/component/finance/paramset/js/CwProofWordsInsertJs.js"></script>
<script type="text/javascript">
function myInsertAjax(dom){//新增方法
	var url = $(dom).attr("action");
	var dataParam = JSON.stringify($(dom).serializeArray());
	LoadingAnimate.start();
	//验证表单的值
	var thisValue = document.getElementsByName("exchangeRate")[0].value;
	if(isNaN(thisValue)){
		window.top.alert("汇率的值必须是数字", 1);
		LoadingAnimate.stop();
		return;
	}
	$.ajax({
		url:url,
		data : {
			ajaxData : dataParam
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop(); 
			if (data.flag == "success") {
				top.addFlag=true;
				window.top.alert(data.msg, 1);
				 $(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
			} else {
				window.top.alert(data.msg, 0);
			}
		},
		error : function() {
			LoadingAnimate.stop(); 
			alert(top.getMessage("ERROR_REQUEST_URL", url));
		}
	});
}
</script>
<script type="text/javascript">
	$(function() {
    	CwProofWordsInsertJs.init();  
	});
	
	function getDesc(){
		
		
		var thisCurshor = document.getElementsByName("curshot")[0].value;
		var thisExchangeShort = document.getElementsByName("exchangeRate")[0].value;
		
		var thisSelectElement = document.getElementsByName("curshot")[0];
		var index = thisSelectElement.selectedIndex;
		var thisName = thisSelectElement.options[index].text;
		
		
		
		document.getElementById("descContent").innerHTML = "1 " + thisName+"("+thisCurshor+")"+" = "+thisExchangeShort+" 人民币(CNY)" ;
		};
		getDesc();
		document.getElementsByName("exchangeRate")[0].onkeyup = getDesc;
		
</script>

</html>
