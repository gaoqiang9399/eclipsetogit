<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<!-- <script type="text/javascript"
	src="/factor/component/include/closePopUpBox.js"></script> -->
<style >
</style>
</head>
<script>
function getCensorTypeDialog(censorType) {
		$("input[name=censorName]").val(censorType.censorName);
	};
	
//选择业务员
function selectCensorTypeDialog(callback){
		dialog({
			id:"censorTypeDialog",
    		title:'选择审查分类',
    		url: webPath+'/mfBusCensorFile/getCensorTypeSelectList',
    		width:900,
    		height:540,
    		backdropOpacity:0,
    		onshow:function(){
    			this.returnValue = null;
    		},onclose:function(){
    			if(this.returnValue){
    				//返回对象的属性:opNo,opName
    				if(typeof(callback)== "function"){
    					callback(this.returnValue);
    				}else{
    				}
    			}
    		}
    	}).showModal();
};
</script>
<body class="overflowHidden bg-white">
				<div class="scroll-content">
					<div class="col-md-10 col-md-offset-1 margin_top_20">
						<div class="bootstarpTag fourColumn">
							<div class="form-title"></div>
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="MfBusCensorFileForm"
								name="operform"
								action="${webPath}/mfBusCensorFile/insertBaseAjax">
								<dhcc:bootstarpTag property="formcensorbase0001" mode="query" />
							</form>
						</div>
					</div>
				</div>
	<div class="formRowCenter" id="myfromrow">
		<dhcc:thirdButton value="保存" action="保存"
			onclick="myInsertAjax('#MfBusCensorFileForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
			onclick="myclose();"></dhcc:thirdButton>
	</div>
	<!--<%@ include file="/component/include/PmsUserFilter.jsp"%>-->
</body>
<script type="text/javascript">
	 		function myInsertAjax(dom){//新增方法
	 		var flag = true;
			if(flag){
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
								alert(data.msg, 3);
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
		}
		$(function(){
			//在指定位置添加放大镜并绑定选择事件
			$("input[name='censorName']").after('<span class="input-group-addon"><i class="i i-fangdajing pointer"></i></span>');
			$("span i.i-fangdajing").click(function(){
				selectCensorTypeDialog(getCensorTypeDialog);
			});
		});
</script>
</html>