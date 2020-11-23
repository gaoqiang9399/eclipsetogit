<%@ page language="java" import="java.util.*,app.component.param.entity.BusCtlParmMang" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<% List<BusCtlParmMang> busCtlParmMangList = (List)request.getAttribute("busCtlParmMangList"); %>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" >
			
			$(function(){
				$("body").mCustomScrollbar({advanced:{
						updateOnContentResize:true,}
				});
				$(".tableOddStyle").hover(function(){
					$(this).css("backgroundColor","#f8f9fb");
					$(this).next().css("backgroundColor","#f8f9fb");
				},function(){
					$(this).css("backgroundColor","#fff");
					$(this).next().css("backgroundColor","#fff");
				})
				$(".tableEvenStyle").hover(function(){
					$(this).css("backgroundColor","#f8f9fb");
					$(this).prev().css("backgroundColor","#f8f9fb");
				},function(){
					$(this).css("backgroundColor","#fff");
					$(this).prev().css("backgroundColor","#fff");
				})
			})
			
		function addRow(){
			var html = '<tr class="tableOddStyle">'+
							'<td width="8%" align="right">关键字：</td>'+
							'<td width="10%" style="color:#8aa9dd;font-weight:700;text-align:left;"><input type="text" name="keyName" class="input1"></td>'+
							'<td width="8%" align="right">公式编辑：</td>'+
							'<td width="" style="color:#8aa9dd;font-weight:700; text-align:left;"><input type="text" name="formula" class="input1"></td>'+
							'<td></td>'+
							'</tr>'+
						'<tr class="tableEvenStyle">'+
							'<td width="8%" align="right">公式描述：</td>'+
							'<td colspan="3"><input type="text" name="remark" value="" style="width:500px" class="input2"/></td>'+
							'<td align="right" width="20%">'+
								'<input type="button"  value="取消" onclick="cancel(this)" class="del"/> '+
								'<input type="button"  value="保存" onclick="insert(this)" class="save"/>'+
							'</td>'+
						'</tr>';
			$('tr:first').before(html);
			$(".tableOddStyle").hover(function(){
					$(this).css("backgroundColor","#f8f9fb");
					$(this).next().css("backgroundColor","#f8f9fb");
				},function(){
					$(this).css("backgroundColor","#fff");
					$(this).next().css("backgroundColor","#fff");
			})
			$(".tableEvenStyle").hover(function(){
					$(this).css("backgroundColor","#f8f9fb");
					$(this).prev().css("backgroundColor","#f8f9fb");
				},function(){
					$(this).css("backgroundColor","#fff");
					$(this).prev().css("backgroundColor","#fff");
			})
		}
			
			function del(obj,keyName){
				if(confirm('请确认是否要删除？')){
					jQuery.ajax({
						url:webPath+"/busCtlParmMang/deleteAjax?keyName="+keyName,
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
								 alert(top.getMessage("SUCCEED_OPERATION"),1);
							}else{
								 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						},error:function(data){
							 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
							 
						}
					});
						
					$(obj).parent('td').parent('tr').prev('tr').remove();
					$(obj).parent('td').parent('tr').remove();
				}
			}
			
			function cancel(obj){
				$(obj).parent('td').parent('tr').prev('tr').remove();
				$(obj).parent('td').parent('tr').remove();
			}
			
			function insert(obj){
				var keyName = $(obj).parent('td').parent('tr').prev('tr').find('td').eq(1).find("input").val();
				var formula = $(obj).parent('td').parent('tr').prev('tr').find('td').eq(3).find("input").val();
				var remark = $(obj).parent('td').prev('td').find('input').val();
				if(keyName == '' || formula=='' || remark==''){
					 //$.myAlert.Alert("必输项不能为空！");
					 alert(top.getMessage("NOT_FORM_EMPTY", "必输项"),0);
				}else{
					var data = {keyName:keyName,formula:formula,remark:remark};
					jQuery.ajax({
						url:webPath+"/busCtlParmMang/insertAjax",
						data:{ajaxData:JSON.stringify(data)},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
								 alert(top.getMessage("SUCCEED_OPERATION"),1);
							}else{
								 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						},error:function(data){
							 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
							 
						}
					});
					
					$(obj).parent('td').find('.del').attr('onclick','del(this,'+keyName+')');
					$(obj).parent('td').find('.del').attr('value','删除');
				}
			}
			function update(obj){
				var keyName = $(obj).parent('td').parent('tr').prev('tr').find('td').eq(1).find("input").val();
				var formula = $(obj).parent('td').parent('tr').prev('tr').find('td').eq(3).find("input").val();
				var remark = $(obj).parent('td').prev('td').find('input').val();
				if(keyName == '' || formula=='' || remark==''){
					 //$.myAlert.Alert("必输项不能为空！");
					 alert(top.getMessage("NOT_FORM_EMPTY", "必输项"),0);
				}else{
					var data = {keyName:keyName,formula:formula,remark:remark};
					jQuery.ajax({
						url:webPath+"/busCtlParmMang/updateAjax",
						data:{ajaxData:JSON.stringify(data)},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
								 //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
								 alert(top.getMessage("SUCCEED_OPERATION"),1);
							}else{
								 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						},error:function(data){
							// $.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
			
		</script>
	<style>
		.bigform_content{width:96%;}
		body.contentA>#mCSB_1{ background:#fff !important;}
		.bigformAdd{border-radius:0px;font-size:12px; color:#fff;background:none;background-color:#32b5cb;border:none;width:78px;line-height:30px; display:block; float:right;margin-top:-30px;}
		.bigformAdd:hover{
			background:#009db7;
		}
		.content_table{ margin-left:30px;margin-top:20px; }
		.content_table tr{color:#758697;font-size:12px;line-height:30px;}
		.content_table table tr.tableEvenStyle:last-child{ border-bottom: 1px dashed #E6EAEE;}
		.content_table .tableOddStyle{border-top: 1px dashed #E6EAEE;line-height:50px;}
		.content_table .tableEvenStyle td{padding-bottom:13px;}
		.input1{
		 	width:60px;
		 	border:none; 
		 	background:none;
		 	background-color:
		 	#e0e6ed;height:20px;
		 }
		.input2{
			 width:506px; 
			 border:none;
			 border: 1px solid #E6EAEE; 
			 color:#ccc
		 }
		.save{
			border-radius:0px;
			font-size:12px ; 
			color:white ;
			background-color:#32b5cb ;
			border:1px solid #32b5cb;
			width:50px ;
			line-height:26px ; 
			margin-right: 10px;
			float:right;
			display: block;
		}
		.save:hover{
			background-color:#009db7 ;
		}
		
		.del{
			border-radius:0px;
			font-size:12px ; 
			color:#626a73;
			background-color:#fff ;
			border:1px solid #d2d3d6;
			width:50px ;
			line-height:26px ; 
			float:right;
			display: block;
			margin-right: 5px;
		}
		.del:hover{
			background-color:#e7e7e7;
		}
	</style>
	</head>
<body style="height:100%;" class="contentA cellBoxBg">

   <div class="bigform_content">
   		<h3>业务控制参数</h3>
   		<input type="button" value="新&nbsp;&nbsp;&nbsp;增" onclick="addRow();" class="bigformAdd">
		<div class="content_table">
			<table width="100%">
				<tr>
					<td></td>
				</tr>
				<%
				for(BusCtlParmMang busCtlParmMang : busCtlParmMangList){%>
					<tr  class="tableOddStyle">
						<td width="8%" align="right">
							关键字：
						</td>
						<td width="10%" style="color:#8aa9dd;font-weight:700;text-align:left;">
							<input type="text" name="keyName" class="input1" value="<%= busCtlParmMang.getKeyName()%>">
						</td>
						<td width="8%" align="right">
							公式编辑：
						</td>
						<td style="color:#8aa9dd;font-weight:700; text-align:left;">
							<input type="text" name="formula" class="input1" value="<%= busCtlParmMang.getFormula()%>">
						</td>
						<td>
						</td>
					</tr>
					<tr class="tableEvenStyle">
						<td width="8%" align="right">
							公式描述：
						</td>
						<td colspan="3">
							<input type="text" name="remark" value="<%= busCtlParmMang.getRemark()%>" style="width:500px" class="input2"/>
						</td>
						<td align="right" width="20%">
							<input type="button"  value="删除" onclick="del(this,'<%= busCtlParmMang.getKeyName()%>')" class="del"/> 
							<input type="button"  value="保存" onclick="update(this)" class="save"/>
						</td>
					</tr>
				<%} %>			
			</table>
		</div>
    </div>
</body>
</html>