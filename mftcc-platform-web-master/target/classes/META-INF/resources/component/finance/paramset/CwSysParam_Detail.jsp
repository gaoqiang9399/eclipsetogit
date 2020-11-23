<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style type="text/css">
			.content-box{
				width: 90%;
			}
			#tempModel-form label.form-label{
				width: 30%;
			}
			#tempModel-form .rows .input-box{
				width: 60%;
			}
		</style>
	</head>

	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="tempModel-form"  theme="simple" name="operform" action="${webPath}/cwSysParam/updateAjax">
						<dhcc:bootstarpTag property="formparamset0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter" style="position: fixed;left: 40%;bottom: 0px;">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#tempModel-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancel();"></dhcc:thirdButton>
		</div>
		</div>
	</body>
	<script type="text/javascript">
	$(function(){
		var ptype = $('input[name="ptype"]').val();
		//将文本框替换成select下拉框
		if('select'==ptype){
			var params =  eval($('input[name="params"]').val());
			var _val =  $('input[name="pvalue"]').val();
			var parent =  $('input[name="pvalue"]').parent();
			var value = $('<select name="pvalue" title="参数化值" class="form_select" mustinput="0" maxlength="20" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></select>')
			$('input[name="pvalue"]').remove();
			parent.append(value);
			for(var item in params){
				if(params[item][0]==_val){
					value.append('<option value="'+params[item][0]+'" selected>'+params[item][1]+'</option>');
				}else{
					value.append('<option value="'+params[item][0]+'">'+params[item][1]+'</option>');
				}
			}
		}
	});
	
	function cancel() {
		$(top.window.document).find("#showDialog").remove();
	};
	 function ajaxUpdateThis(obj){//点击编辑之后，供list页面回调
	//obj是form对象
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						  top.detailFlag = true;
						  if(data.detailFlag == "1"){
							  top.detailFlag = true;
							  top.htmlString = data.detailFlag;
						  }
						  window.top.alert(data.msg, 1);
						  $(top.window.document).find("#showDialog .close").click();
						  
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					LoadingAnimate.stop(); 
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}; 
	
	</script>
</html>