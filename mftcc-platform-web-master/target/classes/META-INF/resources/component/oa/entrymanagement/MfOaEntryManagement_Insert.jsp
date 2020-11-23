<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content" >
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">基本信息</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfOaEntryManagement" theme="simple" name="operform" action="${webPath}/mfOaEntryManagement/insertAjax">
							<dhcc:bootstarpTag property="formentrymanagement0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" typeclass="baseInfo_addAjax"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="myclose cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
<script type="text/javascript" 	src="${webPath}/component/oa/entrymanagement/js/MfOaEntryManagement_Find.js"></script>
<script type="text/javascript">
var processId='${processId}';
	var insertOrUpdate = '${insertOrUpdate}';
	$(function(){
		MfOaEntryManagement_Find.init(); 
		baseInfoAddAjax("#MfOaEntryManagement");
		myback();
	});
	var baseInfoAddAjax = function(obj){
		$(".baseInfo_addAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (data.flag == "success") {
							window.top.alert(data.msg, 1);
							if(insertOrUpdate == "1"){//新增
								window.location.href = webPath+"/mfOaEntryManagement/getById?baseId="+data.mfOaEntryManagement.baseId+"&entryManagementId="+data.mfOaEntryManagement.entryManagementId+"&applySts="+data.mfOaEntryManagement.applySts;
							}else{
								top.htmlStr = data.htmlStr;
								top.baseFlag = true;
								myclose_click();
							}
						} else {
							alert(data.msg, 0);
						}
					},
					error : function() {
					}
				});
			}
		});
	};
	var myback = function(){
		$(".myclose").bind("click", function(event){
			myclose_click();
		});
	};
	function getCensorTypeDialog(censorType) {
		$("input[name=nativePlace]").val(censorType.areaName);
	};
	
	//选择业务员
	function selectCensorTypeDialog(callback){
			dialog({
				id:"censorTypeDialog",
	    		title:'选择省份',
	    		url: webPath+'/nmdArea/getProvinceList',
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
	</body>
</html>
