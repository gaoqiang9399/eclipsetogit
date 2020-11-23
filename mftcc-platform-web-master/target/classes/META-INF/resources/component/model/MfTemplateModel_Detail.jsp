<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="body_bg overFlowHidden">
	<div class="mf_content">
		<div class="content-box">
			<p class="tip"><span>说明：</span>带*号的为必填项信息，请填写完整。</p>
			<div class="tab-content">
				<form method="post" id="tempModel-form"  theme="simple" name="operform" action="${webPath}/mfTemplateModel/updateAjax">
					<dhcc:bootstarpTag property="formtemplatemodel0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter" style="position: fixed;left: 40%;bottom: 0px;">
		<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#tempModel-form');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
	</body>
	<script type="text/javascript">
	function ajaxUpdateThis(obj){//obj是form对象
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
						  top.addFlag = true;
						  if(data.htmlStrFlag == "1"){
							  top.htmlStrFlag = true;
							  top.htmlString = data.htmlStr;
						  }
						  myclose_click();
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
