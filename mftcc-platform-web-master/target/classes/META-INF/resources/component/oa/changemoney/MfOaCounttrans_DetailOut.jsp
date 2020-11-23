<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="/factor/component/include/closePopUpBox.js"></script>
		 <script type="text/javascript" src="${webPath}/component/oa/changemoney/js/MfOaCounttrans_List.js"></script>
			</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfOaCounttransForm" theme="simple" name="operform" action="${webPath}/mfOaCounttrans/duscussOutAjax">
							<dhcc:bootstarpTag property="formchangemoney0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<dhcc:thirdButton value="转款" action="转款" onclick="changeMoney('#MfOaCounttransForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript">
		function changeMoney(dom){
		window.top.alert("您确定要进行转款操作吗?",2,function(){
			//获取表单数据
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		//发送ajax请求
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
						/*
						//触发搜索按钮,点击数据
						$("#filter_btn_search").click();
						//重新加载页面数据
						*/
						//$("#filter_btn_search").click();
						$("#filter_btn_search").click();
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
			$("#filter_btn_search").click();
		});
						
							
		}
		
	
		function myInsertAjax(dom){//新增方法
		//获取表单数据
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		//发送ajax请求
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
						/*
						//触发搜索按钮,点击数据
						$("#filter_btn_search").click();
						//重新加载页面数据
						*/
						$("#filter_btn_search").click();
						myclose_click();
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
</html>