<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/prdct/js/MfSysKind.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
					<div class="col-md-10 col-md-offset-1 column margin_top_20">
						<div class="bootstarpTag fourColumn">
	            				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="kindForm-add" theme="simple" name="operform" action="${webPath}/mfSysKind/insertAjax">
								<dhcc:bootstarpTag property="formsyskind0003" mode="query"/>
							</form>	
						</div>
					</div>
			</div>	
		
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="saveKindInfo('#kindForm-add');"></dhcc:thirdButton> 
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>	
			</div>
		</div>
	</body>
	<%-- <body class="overflowAuto body_bg">
		<div class="bigform_content">
			<div class="content_table">
				<s:form method="post" theme="simple" id ="kindForm-add"  name="operform" action="${webPath}/mfSysKind/insertAjax">
					<div class="form_title">产品设置</div>
					<dhcc:bigFormTag property="formsyskind0003" mode="query"/>
					<dhcc:bigFormTag property="formsyskind0003" mode="query"/>
					<div class="formRowCenter">
		    			<dhcc:thirdButton value="保存" action="保存" onclick="saveKindInfo('#kindForm-add');"></dhcc:thirdButton>
		    			<!-- <input type="submit" value="保存" onclick="return submitJsMethod(this.form, '');" style="display: none;"> -->
		    			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="back();"></dhcc:thirdButton>
		    		</div>
				</s:form>
			</div>	
		</div> --%>
	<script type="text/javascript">
	var option1 = $("select[name=vouTypeDef]").find("option");
	//var option2 = $("select[name=rateTypeDef]").find("option");
	//var option3 = $("select[name=icTypeDef]").find("option");
	//var option4 = $("select[name=repayTypeDef]").find("option");
	var feeStdList = (eval("(" + '${json}' + ")")).feeStdList;
	var modelList = (eval("(" + '${json}' + ")")).modelList;
	var pleDyFormList = (eval("(" + '${json}' + ")")).pleDyFormList;
	var path= '${webPath}';
	$(function(){
	
		/* $("body").mCustomScrollbar({
				advanced:{
					theme:"minimal-dark",
					updateOnContentResize:true
				}
		});
		var busModelOption = $("select[name=busModel]").find("option");
		var busModel = '${busModel}';
		makeOptionsJQ(busModelOption, busModel);
		
		var optionStr = '';
		$.each(feeStdList,function(i,obj){
			optionStr = optionStr + "<option value='"+obj.feeStdNo+"'>"+obj.feeStdName+"</option>";
		});
		$("select[name=feeStdNo]").html(optionStr);
		$("input[name='feeStdName']").val($("select[name=feeStdNo]").find("option:selected").text());
	
		optionStr = '';
		$.each(modelList,function(i,obj){   
			optionStr = optionStr + "<option value='"+obj.modelNo+"'>"+obj.modelName+"</option>";
		});
		$("select[name=pactModelNo]").html(optionStr);
		$("input[name='pactModelName']").val($("select[name=pactModelNo]").find("option:selected").text());

		optionStr = '';
		$.each(pleDyFormList,function(i,obj){
			optionStr = optionStr + "<option value='"+obj.pleFormNo+"'>"+obj.pleFormName+"</option>";
		});
		$("select[name=pleFormNo]").html(optionStr);
		$("input[name='pleFormName']").val($("select[name=pleFormNo]").find("option:selected").text()); */
		
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
		var ajaxData = '${ajaxData}';
	    ajaxData = JSON.parse(ajaxData);
	    initKindConfig(ajaxData,'add');
	    
	});	
	
	</script>
	</body>
</html>
