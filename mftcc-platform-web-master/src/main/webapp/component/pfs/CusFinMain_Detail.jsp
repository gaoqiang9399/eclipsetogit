<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
			.formRow {
			    margin: 15px 0;
			    text-align: center;
			    width: 100%;
			}
			table {
			    width: 60%;
			}
			textarea{
				resize:none;
				width:80%;
			}
			.hidden-content{
				display: none;
			}
		</style>
		<script type="text/javascript">
		var year =["2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025"];
		var selectYearHtml = '<select name="finRptYear">';
		for(var i=0;i<year.length;i++){
			selectYearHtml=selectYearHtml+'<option value="'+year[i]+'">'+year[i]+'</option>';
		}
		selectYearHtml=selectYearHtml+'</select>年';
		
		var month =["01","02","03","04","05","06","07","08","09","10","11","12"];
		var selectMonthHtml = '<select name="finRptMonth" style="margin-left:10px;">';
		for(var i=0;i<month.length;i++){
			selectMonthHtml=selectMonthHtml+'<option value="'+month[i]+'">'+month[i]+'</option>';
		}
		selectMonthHtml=selectMonthHtml+'</select>月';
		
		var quater =["01","02","03","04"];
		var selectQuaterHtml = '<select name="finRptQuater" style="margin-left:10px;">';
		for(var i=0;i<quater.length;i++){
			selectQuaterHtml=selectQuaterHtml+'<option value="'+quater[i]+'">'+quater[i]+'</option>';
		}
		selectQuaterHtml=selectQuaterHtml+'</select>季';
		
		
			$(function(){
				$("select[name=finRptType]").attr("disabled",true);
				
				ifAudChange();
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			function finRptTypeChange(){
				var $finRptDateParent = $("input[name=finRptDate]").parent();
				$finRptDateParent.empty();
				$finRptDateParent.append('<input name="finRptDate" style="display:none;">');
				if($("select[name=finRptType]").val() == "1"){//月报
					$finRptDateParent.append(selectYearHtml+selectMonthHtml);
					$("input[name=finRptDate]").val($("select[name=finRptYear]").val()+$("select[name=finRptMonth]").val());
				
				}else if($("select[name=finRptType]").val() == "2"){//季报
					$finRptDateParent.append(selectYearHtml+selectQuaterHtml);
					$("input[name=finRptDate]").val($("select[name=finRptYear]").val()+$("select[name=finRptQuater]").val());
				
				}else if($("select[name=finRptType]").val() == "3"){//年报
					$finRptDateParent.append(selectYearHtml);
					$("input[name=finRptDate]").val($("select[name=finRptYear]").val());
				}
				
			};
			
			//是否审计变化时
			function ifAudChange(){
				if($("select[name=ifAud]").val() == "0"){
					$("input[name=audOrg]").attr("mustinput","1");
					var text =$("input[name=audOrg]").parents("div.rows").eq(0).find(".form-label.right ").text();
					$("input[name=audOrg]").parents("div.rows").eq(0).find(".form-label.right ").html('<span class="required">*</span>'+ text  );

					$("input[name=audIdea]").attr("mustinput","1");
					text =$("input[name=audIdea]").parents("div.rows").eq(0).find(".form-label.right ").text();
					$("input[name=audIdea]").parents("div.rows").eq(0).find(".form-label.right ").html('<span class="required">*</span>'+ text  );
				
					$("input[name=audDate]").attr("mustinput","1");
					text =$("input[name=audDate]").parents("div.rows").eq(0).find(".form-label.right ").text();
					$("input[name=audDate]").parents("div.rows").eq(0).find(".form-label.right ").html('<span class="required">*</span>'+ text  );
				
				}else{
					$("input[name=audOrg]").attr("mustinput","0");
					$("input[name=audOrg]").parents("div.rows").eq(0).find(".form-label.right ").find(".required").remove();
					
					$("input[name=audIdea]").attr("mustinput","0");
					$("input[name=audIdea]").parents("div.rows").eq(0).find(".form-label.right ").find(".required").remove();
					
					$("input[name=audDate]").attr("mustinput","0");
					$("input[name=audDate]").parents("div.rows").eq(0).find(".form-label.right ").find(".required").remove();
				}
			};
			function ajaxInsertThis(obj,callback){
			 
				$("select[name=finRptType]").attr("disabled",false);
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 // window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
								 top.updateFlag = true;
								  $(top.window.document).find("#showDialog .close").click();
							}else if(data.flag == "error"){
								alert(top.getMessage("FAILED_OPERATION"," "),0);
							}else if(data.flag == "exist"){
								alert("该报表已存在，请勿重复录入！",0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			};
			//是否审计变化时
	function ifAudChange() {
		
		if ($("select[name=ifAud]").val() == "1") {
			$("input[name=audOrg]").attr("mustinput", "1");	
			var audOrgtext	= $("input[name=audOrg]").parents("tr").find(".control-label   ").text(); 
			$("input[name=audOrg]").parents("tr").find(".control-label  ").html('<font color="#FF0000">*</font>'+audOrgtext); 						
			$("input[name=audIdea]").attr("mustinput", "1");
			var audIdeatext = $("input[name=audIdea]").parents("tr").find(".control-label ").text();
			$("input[name=audIdea]").parents("tr").eq(0).find(".control-label ").html('<font color="#FF0000">*</font>' + audIdeatext);
			$("input[name=audDate]").attr("mustinput", "1");
			var audDatetext = $("input[name=audDate]").parents("tr").find(".control-label ").text();
			$("input[name=audDate]").parents("tr").eq(0).find(".control-label ").html('<font color="#FF0000">*</font>' + audDatetext);
		} else {
			$("input[name=audOrg]").attr("mustinput", "0");
			$("input[name=audOrg]").parents("tr").eq(0).find(".control-label ").find("font").remove();

			$("input[name=audIdea]").attr("mustinput", "0");
			$("input[name=audIdea]").parents("tr").eq(0).find(".control-label ").find("font").remove();

			$("input[name=audDate]").attr("mustinput", "0");
			$("input[name=audDate]").parents("tr").eq(0).find(".control-label ").find("font").remove();
		}
	};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
			            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="cusFinInsertForm" theme="simple" name="operform" action="${webPath}/cusFinMain/updateAjax">
								<dhcc:bootstarpTag property="formpfs0001" mode="query"/>
							</form>	
					<div>
				</div>
			</div>
			</div>
		</div>
			<c:if test="${query!='query' }">
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#cusFinInsertForm')"></dhcc:thirdButton>
	   		</div>
			</c:if>
		</div>
	</body>
</html>
