<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include/pub_wx.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>用户信息绑定</title>
</head>
<body>
	<form action="">
		<div class="weui_cells weui_cells_form">
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">请输入用户名</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input name="cusName" class="weui_input" type="text"
						placeholder="请输入用户名">
				</div>
			</div>
			<div class="weui_cell weui_cell_select">
				<div class="weui_cell_bd weui_cell_primary">
					<select class="weui_select" name="idType">
						<option selected="" value="0">选择</option>
					</select>
				</div>
			</div>
			<div class="weui_cell">
				<div class="weui_cell_hd">
					<label class="weui_label">证件号码</label>
				</div>
				<div class="weui_cell_bd weui_cell_primary">
					<input name="idNo" class="weui_input" type="text"
						placeholder="请输入证件号码">
				</div>
			</div>
		</div>
		<input type="hidden" name="openId"
			value="<s:property value="openId"/>" />
		<div class="content-padded">
			<button type="button" onclick="ajaxSubmit(this.form);" class="weui_btn weui_btn_primary">确认绑定</button>
		</div>
	</form>
	<script type="text/javascript">
		$(function(){
			 $.getJSON("wechat/include/parm_dic.json","",function(data){  
	            	$("select[name=idType]").empty();
		            $.each(data["idType"],function(i,node){
		            	$("select[name=idType]").append('<option value="'+node.optCode+'">'+node.optName+'</option>')
		            });
		        }); 
		});
		function ajaxSubmit(obj){
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			jQuery.ajax({
				url:webPath+"WxCusRenterActionAjax_bindRenterAjax.action",
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						$.alert(data.content, data.msg);
					}else if(data.flag=="error"){
						$.alert(data.content, data.msg);
					}
				},error:function(data){
					$.alert(data);
				}
			});
		}
	</script>
</body>
</html>