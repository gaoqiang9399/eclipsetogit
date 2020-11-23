<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
<script type="text/javascript">
	var baseId = "${baseId}";
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
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn" >
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="base_form" theme="simple" name="operform" action="${webPath}/mfOaArchivesBase/insertAjax">
							<dhcc:bootstarpTag property="formarchive0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass="base_insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript" 	src="${webPath}/component/oa/archive/js/MfOaDrchivesBase_find.js"></script>
	<script type="text/javascript">
		$(function(){
			OaArchivesFind.init();
		})
	</script>
</html>