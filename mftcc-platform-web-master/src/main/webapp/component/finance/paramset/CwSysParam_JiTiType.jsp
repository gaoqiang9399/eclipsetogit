<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计提设置</title>
<script type="text/javascript">
	$(function(){

		changeJiTiType();
			
		$("select[name='jiTiType']").change(function(){
			changeJiTiType();
		});
	})
	function changeJiTiType(){
		var jitiType = $("select[name='jiTiType']").val()
			 if(jitiType==0){
			    $("[name=zcJiTi]").parents("tr").hide();
				$("[name=gzJiTi]").parents("tr").hide();
				$("[name=cjJiTi]").parents("tr").hide();
				$("[name=kyJiTi]").parents("tr").hide();
				$("[name=ssJiTi]").parents("tr").hide(); 
				$("[name=zcJiTi]").removeAttr("mustinput");
				$("[name=gzJiTi]").removeAttr("mustinput");
				$("[name=cjJiTi]").removeAttr("mustinput");
				$("[name=kyJiTi]").removeAttr("mustinput");
				$("[name=ssJiTi]").removeAttr("mustinput");
				
				$("[name=allJiTi]").parents("tr").show();
				$("[name=allJiTi]").attr("mustinput","1");
				
			}else{
				$("[name=allJiTi]").parents("tr").hide();
				$("[name=allJiTi]").removeAttr("mustinput");
				
				$("[name=zcJiTi]").parents("tr").show();
				$("[name=gzJiTi]").parents("tr").show();
				$("[name=cjJiTi]").parents("tr").show();
				$("[name=kyJiTi]").parents("tr").show();
				$("[name=ssJiTi]").parents("tr").show();
				
				$("[name=zcJiTi]").attr("mustinput","1");
				$("[name=gzJiTi]").attr("mustinput","1");
				$("[name=cjJiTi]").attr("mustinput","1");
				$("[name=kyJiTi]").attr("mustinput","1");
				$("[name=ssJiTi]").attr("mustinput","1");
				
			}
		
	}
</script>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" theme="simple" name="operform" id="cwjiti_form" action="${webPath}/cwSysParam/jiTiInsertAjax">
						<dhcc:bootstarpTag property="formcwjiti0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="savejitiAjax('#cwjiti_form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">
	function savejitiAjax(obj){
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
					//loadingAnimateClose();
					if(data.flag == "success"){
						top.addFlag = true;
						myclose_showDialogClick();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}
</script>
</html>