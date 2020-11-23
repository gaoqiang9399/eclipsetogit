<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
		/*td[colspan="3"] .pops-value{
			min-width: 752px !important;
		}*/
		</style>
		<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysKind.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
					<div class="col-md-10 col-md-offset-1 column margin_top_20">
						<div class="bootstarpTag fourColumn">
	            				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="kindFormAdd" theme="simple" name="operform" action="${webPath}/mfSysKind/insertAjax">
								<dhcc:bootstarpTag property="formsyskind0001" mode="query"/>
							</form>	
						</div>
					</div>
			</div>	
		
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="saveKindInfo('#kindFormAdd');"></dhcc:thirdButton> 
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>	
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var option1 = $("select[name=vouTypeDef]").find("option");
	var path= '${webPath}';
	var ajaxData = '${ajaxData}';
    ajaxData = JSON.parse(ajaxData);
	$(function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		
	    initKindConfig(ajaxData,'add');
	 	/*// 开办地区选择组件
		$("input[name=exhibitionArea]").popupSelection({
			ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
			searchOn : true,// 启用搜索
			multiple : true,// 单选
			ztree : true,
			ztreeSetting : setting,
			title : "展业区域",
			handle : BASE.getIconInTd($("input[name=exhibitionArea]")),
			changeCallback : function(elem) {
				BASE.removePlaceholder($("input[name=exhibitionArea]"));
				var nodes = elem.data("treeNode");
				var exhibitionAreaName = "";
				var len = elem.data("treeNode").length;
				for (var i = 0; i < len; i++) {
					exhibitionAreaName += nodes[i].name + "|";
				}
				$("input[name=exhibitionAreaName]").val(exhibitionAreaName);
			}
		});*/
	});	
	
	</script>
	</body>
</html>
