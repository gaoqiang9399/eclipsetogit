 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet"
	href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
<script type="text/javascript">
		var baseId = '';
		var webPath = '${webPath}';
</script>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content" >
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">基本信息</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="OaArchivesBaseInsert" theme="simple" name="operform" action="${webPath}/mfOaArchivesBase/insertAjax">
							<dhcc:bootstarpTag property="formarchive0001" mode="query" />
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter"
				style="height: 50px; padding: 5px; position: fixed; bottom: 0px; width: 100%;">
				<dhcc:thirdButton value="保存" action="保存" typeclass="baseInfo_addAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="关闭" typeclass="myback myclose cancel"></dhcc:thirdButton>
			</div>
		</div>
</body>
<script type="text/javascript" 	src="${webPath}/component/oa/archive/js/MfOaDrchivesBase_find.js"></script>
<script type="text/javascript">
	$(function(){
		OaArchivesFind.init(); 
		$("select[name=opSts]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,
			multiple : false//单选
		});
		baseInfoAddAjax("#OaArchivesBaseInsert");
		myback();
	});
	var baseInfoAddAjax = function(obj){
		$(".baseInfo_addAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.location.href = webPath+"/mfOaArchivesBase/getById?baseId="+data.mfOaArchivesBase.baseId;
						} else {
							alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();
					}
				});
			}
		});
	};
	var myback = function(){
		$(".myback").bind("click", function(event){
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
</html>