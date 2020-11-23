<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<link href="${webPath}component/risk/css/jquery-ui.css" rel="stylesheet">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style>
			.bigform_content input[type="text"] {width: 140px}
			.bigform_content select {width: 140px}
			.bigform_content .content_form {height: auto;}
			.ui-autocomplete {
   				 max-height: 130px;
   				 overflow-y: auto;
  			  /* prevent horizontal scrollbar */
   				 overflow-x: hidden;
 				 }
  /* IE 6 doesn't support max-height
   * we use height instead, but this forces the menu to always be this tall
   */
  * html .ui-autocomplete {
    height: 100px;
  }
  	.content_Btn input {
  		width: 50px;
    	height: 24px;
    	line-height: 24px;
  	}
  	.bigform_content .input_btn input[type="button"]{
  		height: 25px;
  		width: 60px;
  	}
  	.content_form table {
   		 margin-bottom: 10px;
	}
		</style>
		<script type="text/javascript" src="${webPath}component/risk/js/jquery-ui.js"></script>
		<script type="text/javascript">
			var availableTags = [];
			<c:forEach var="parmKey" items="${request.parmKeys}">
				var item = "${parmKey.keyName}"+"-"+"${parmKey.keyChnName}"
				availableTags.push(item);
			</s:iterator>
			<c:forEach var="parmKey" items="${request.parmKeys}">
				var item = "${parmKey.keyName}"+"-"+"${parmKey.keyChnName}"
				availableTags.push(item);
			</c:iterator>

		</script>
		<script type="text/javascript" >
		var itemNo = '${itemNo}';
		var threashodeName = null;
		var threashodeIndex = null;
		$(document).ready(function() {
				
				$("input[name=threashodeValue]").parent().parent().show();
				 $("input[name=maxValue]").parent().parent().hide();
				$("input[name=dicKeyName]").parent().parent().hide();
				
				$( "input[name='dicKeyName']" ).autocomplete({
					source: availableTags
				});
				
				var oledicKeyName = $("input[name='dicKeyName']").val();
				
				$("input[name='dicKeyName']").blur(function() {
					
					var dicKeyName = $("input[name='dicKeyName']").val();
					if(oledicKeyName==dicKeyName) {
						return;
					}else {
						oledicKeyName = dicKeyName;
					}
					$.ajax({
						url:webPath+'/riskItemThreashode/getParmDicAjax',
						data:{
						keyName:dicKeyName
						},
						type:'post',
						dataType:'json',
						success:function(data) {
							var dicKeyValue = $("select[name='dicKeyValue']");
							var parmkeys = data.parmDics;
							dicKeyValue.empty();
							
							if(parmkeys.length == 0) {
								alert(dicKeyName+"未对应相关字典项",0);
								return;
							};
							$.each(parmkeys,function(i,value) {
								dicKeyValue.append("<option value='"+value.optCode+"'>"+value.optName+"</option>"); 
							});
						},error:function() {
							alert("获取字典项失败",0);
						}
					
					});
				});
			});
			
		
			function insertAjax(obj){
				
				$("input[name='threashodeName']").removeAttr("readonly");
				$("input[name='threashodeChnName']").removeAttr("readonly");
				$("select[name=threashodeType]").removeAttr("disabled");
				$("input[name='dicKeyName']").removeAttr("readonly");
				ajaxInput(obj,webPath+"/riskItemThreashode/insertAjax?itemNo="+itemNo,true);
				
				/* if( $("select[name=threashodeType]").val() == "1"){//数值
					$("input[name=threashodeValue]").parent().parent().show();
					$("input[name=maxValue]").parent().parent().hide();
					$("input[name=dicKeyName]").parent().parent().hide();
				}else if( $("select[name=threashodeType]").val() == "2"){//字典项
					$("input[name=dicKeyName]").parent().parent().show();
					$("input[name=maxValue]").parent().parent().hide();
					$("input[name=threashodeValue]").parent().parent().hide();
				}if( $("select[name=threashodeType]").val() == "3"){//范围区间
					$("input[name=maxValue]").parent().parent().show();
					$("input[name=dicKeyName]").parent().parent().hide();
					$("input[name=threashodeValue]").parent().parent().hide();
				} */
				threashodeTypeChange();
				
				
				
				$("input[name=threashodeIndex]").val(1);
				
				
			};
			
			function ajaxSaveR(obj,url){
				if(formYanZheng()){
					$("select[name=threashodeType]").removeAttr("disabled");
					ajaxSave(obj,url,function(){
						var itemNo = $("input[name='itemNo']").val();
						var threashodeName = $("input[name='threashodeName']").val();
						var threashodeIndex = $("input[name='threashodeIndex']").val();
						var ajaxUrl = webPath+"/riskItemThreashode/getByIdAjax?itemNo="+itemNo+"&threashodeName="+threashodeName+"&threashodeIndex="+threashodeIndex;
						jQuery.ajax({
							url:ajaxUrl,
							type:"POST",
							dataType:"json",
							beforeSend:function(){  
							},success:function(data){
								if(data.flag == 'success'){
									var $content_from =  $(window.parent.document);
								  	  $.each(data.formData,function(name,value) {
									   	setFormEleValue(name, value,$content_from.find("#RiskItemThreashodeActionAjax_updateAjax_action"));
									  });
									  $content_from.show();
									  initIfreamHeight();
								}else{
									alert(top.getMessage("ERROR_SELECT"),0);
								}
							},error:function(data){
								alert(top.getMessage("ERROR_SELECT"),0);
							}
						});
					});
				}
				
			};
			
			function ajaxFormDeleteR(obj,url){
				ajaxFormDelete(obj,url,function(){
					var appNo = $("input[name='appNo']").val();
					var ajaxUrl = webPath+"/appProjectPas/getByIdAjax?appNo="+appNo;
					jQuery.ajax({
						url:ajaxUrl,
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == 'sucess'){
								var $content_from =  $(window.parent.document);
							  	  $.each(data.formData,function(name,value) {
								   	setFormEleValue(name, value,$content_from.find("#AppProjectPasActionAjax_updateAjax_action"));
								  });
								  $content_from.show();
								  initIfreamHeight();
							}else{
								alert(top.getMessage("ERROR_SELECT"),0);
							}
						},error:function(data){
							alert(top.getMessage("ERROR_SELECT"),0);
						}
					});
				});
			};
			
			
			

			 function ajaxGetById(obj,ajaxUrl){
					if(ajaxUrl!==undefined&&ajaxUrl!=null&&ajaxUrl!=""){
						//ajaxUrl = encodeURI(ajaxUrl);
						jQuery.ajax({
							url:ajaxUrl,
							type:"POST",
							dataType:"json",
							beforeSend:function(){  
							},success:function(data){
								if(data.flag == "success"){
									$(obj).parents(".content_table").find(".content_form").find("form").attr("action","");
									  var $content_from =  $(".content_table").find(".content_form");
								  	 $.each(data.formData,function(name,value) {
									   	setFormEleValue(name, value,$content_from.find("form"));
									  });
									  $content_from.show();
									  initIfreamHeight();
									

									  var threashodeName = $("input[name='threashodeName']");
									  threashodeName.attr("readonly",true);
									  threashodeTypeChange();
									  if($(".content_table").find("table.ls_list").find("tbody").find("tr").length > 1){
										  $("select[name=threashodeType]").attr("disabled",true);
										  if( $("select[name=threashodeType]").val() == "1"){//数值
												
										  }else if( $("select[name=threashodeType]").val() == "2"){//字典项
											  $("input[name='dicKeyName']").attr("readonly",true);
										  		
											}else if( $("select[name=threashodeType]").val() == "3"){//范围区间
												
										  }
											
										}else{
											$("input[name='threashodeName']").removeAttr("readonly");
											$("input[name='threashodeChnName']").removeAttr("readonly");
											$("select[name=threashodeType]").removeAttr("disabled");
											$("input[name='dicKeyName']").removeAttr("readonly");
										}
											var dicKeyValue = $("select[name='dicKeyValue']");
											var parmkeys = data.parmDics;
											dicKeyValue.empty();
											
											$.each(parmkeys,function(i,value) {
												dicKeyValue.append("<option value='"+value.optCode+"'>"+value.optName+"</option>"); 
											});
											
											var selectOption = data.selectOption;
											dicKeyValue.val(selectOption);
											
								}else{
									 alert(top.getMessage("ERROR_SELECT"),0);
								}
							},error:function(data){
								 alert(top.getMessage("ERROR_SELECT"),0);
							}
						});
					}else{
						alert("请检查列表链接",0);
					}
				};
			function ajaxGetByIdNew(obj,ajaxUrl){
					 if($(".content_table").find("table.ls_list").find("tbody").find("tr").length == 0){
						 
						 insertAjax(obj);
						 return false;
					 }
					 //RiskItemThreashodeActionAjax_getByIdAjax.action;threashodeName-threashodeName;threashodeIndex-threashodeIndex;itemNo-itemNo;onClick-ajaxGetByIdNew(this)
					ajaxUrl = webPath+"/riskItemThreashode/getByIdAjax1?itemNo="+itemNo;
					
					 if(ajaxUrl!==undefined&&ajaxUrl!=null&&ajaxUrl!=""){
							jQuery.ajax({
								url:ajaxUrl,
								type:"POST",
								dataType:"json",
								beforeSend:function(){  
								},success:function(data){
									if(data.flag == "success"){
										$(obj).parents(".content_table").find(".content_form").find("form").attr("action",webPath+"/riskItemThreashode/insertAjax?itemNo="+itemNo);
										  var $content_from =  $(".content_table").find(".content_form");
									  	 $.each(data.formData,function(name,value) {
										   	setFormEleValue(name, value,$content_from.find("form"));
										  });
										  $content_from.show();
										  initIfreamHeight();
										  $("input[name=threashodeIndex]").val(data.size);
										  
										  $("input[name='threashodeName']").attr("readonly",true);
										  $("input[name='threashodeChnName']").attr("readonly",true);
										  threashodeTypeChange();
										  $("select[name=threashodeType]").attr("disabled",true);
										  if( $("select[name=threashodeType]").val() == "1"){//数值
												
										  }else if( $("select[name=threashodeType]").val() == "2"){//字典项
											  $("input[name='dicKeyName']").attr("readonly",true);
										  		
											}else if( $("select[name=threashodeType]").val() == "3"){//范围区间
												
										  }
												var dicKeyValue = $("select[name='dicKeyValue']");
												var parmkeys = data.parmDics;
												dicKeyValue.empty();
												
												$.each(parmkeys,function(i,value) {
													dicKeyValue.append("<option value='"+value.optCode+"'>"+value.optName+"</option>"); 
												});
												
												var selectOption = data.selectOption;
												dicKeyValue.val(selectOption);
												
									}else{
										 alert(top.getMessage("ERROR_SELECT"),0);
									}
								},error:function(data){
									 alert(top.getMessage("ERROR_SELECT"),0);
								}
							});
						}else{
							alert("请检查列表链接",0);
						}
			};
			function threashodeTypeChange(){
				if( $("select[name=threashodeType]").val() == "1"){//数值
					$("input[name=threashodeValue]").parent().parent().show();
					$("input[name=maxValue]").parent().parent().hide();
					$("input[name=dicKeyName]").parent().parent().hide();
				}else if( $("select[name=threashodeType]").val() == "2"){//字典项
					$("input[name=dicKeyName]").parent().parent().show();
					$("input[name=maxValue]").parent().parent().hide();
					$("input[name=threashodeValue]").parent().parent().hide();
				}else if( $("select[name=threashodeType]").val() == "3"){//范围区间
					$("input[name=maxValue]").parent().parent().show();
					$("input[name=dicKeyName]").parent().parent().hide();
					$("input[name=threashodeValue]").parent().parent().hide();
				}
			};
			function formYanZheng(){//提交表单之前进行数据验证
				if( $("select[name=threashodeType]").val() == "1"){//数值
					var threashodeValue = $("input[name=threashodeValue]").val();
					if(isNaN(threashodeValue)){
						alert("请输入正确数值",0);
					}
					return !isNaN(threashodeValue);
				}else if( $("select[name=threashodeType]").val() == "2"){//字典项
					if($("input[name=dicKeyName]").val == ""){
						alert("请输入字典项键值",0);
						return false;
					}
					if($("select[name=dicKeyValue]").val == "" ){
						alert("请选择字典项数值",0);
						return false;
					}
					return true;
				}if( $("select[name=threashodeType]").val() == "3"){//范围区间
					if(isNaN($("input[name=maxValue]").val())){
						alert("请输入正确数值",0);
						return false;
					}
					if(isNaN($("input[name=minValue]").val())){
						alert("请输入正确数值",0);
						return false;
					}
					return true;
				}
			};
			
		</script>
	</head>
<body  style="padding:0 15px;background: white;">
   <div class="bigform_content" style="padding-top: 0px;">
		<div class="content_table">
			<div class="input_btn" style="margin-top: 5px;margin-bottom: 0px;">
				<c:if test="${query ne 'query'}">
				<dhcc:thirdButton value="新增" action="新增" onclick="ajaxGetByIdNew(this);"></dhcc:thirdButton>
				</c:if>
			</div>
			<c:if test="${query ne 'query'}">
			<dhcc:tableTag property="tablerisk0002" paginate="riskItemThreashodeList" head="true"></dhcc:tableTag>
			</c:if>
			<c:if test="${query eq 'query'}">
			<dhcc:tableTag property="tablerisk0004" paginate="riskItemThreashodeList" head="true"></dhcc:tableTag>
			</c:if>
			<div  class="content_form" style="display: none;padding-top: 0px;">
				<form method="post" theme="simple" name="operform" action="">
					<div class="content_Btn" style="position: static;text-align: right;">
						<dhcc:thirdButton value="删除" action="删除" onclick="ajaxFormDeleteR(this.form,'${webPath}/riskItemThreashode/deleteAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSaveR(this.form,'${webPath}/riskItemThreashode/updateAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton value="关闭" action="关闭" onclick="colseBtn(this)"></dhcc:thirdButton>
					</div>
					<dhcc:formTag property="formrisk0004" mode="query"></dhcc:formTag>
			    </form>
			</div>
		</div>
    </div>
</body>
</html>