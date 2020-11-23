<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>报表项详情编辑</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<style type="text/css">
			.deploy_content{
				height: calc(100% - 65px);
			}
			
			.afterS{
			    height: 35px;
			    line-height: 35px;
			    margin-left: 15px;
			}

		</style>
		<script type="text/javascript" >
			var reportTypeId = '${dataMap.reportTypeId}';
			var reoprtItemLev = '${dataMap.reoprtItemLev}';
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				if('001'!=reportTypeId){
					var lastRow = $('#cwItemForm').find('tr:eq(1)');
					lastRow.find('td:gt(1)').html('');
					if('002'==reportTypeId){
						var lable = $('<label class="control-label">现金流方向</label>');
						var isPut = $('<div class="input-group"><select name="isInput" title="现金流方向" class="form_select form-control" mustinput="0" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"><option value="0">流入</option><option value="1">流出</option></select></div>')
						lastRow.find('td:eq(2)').html(lable);
						lastRow.find('td:eq(3)').html(isPut);
					}
					
				}
				
				if(reoprtItemLev !=''){
					$('input[name=reoprtLevName]').siblings('.input-group-addon').remove();
					var typeObj = $('select[name=reportItemType]');
					typeObj.after('<span class="afterS">' + typeObj.find("option:selected").text() + '</span>').hide();
				}
				
			})
			
			function ajaxInsertItemForm(obj, formid){
				var flag = submitJsMethod($(formid).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(formid).serializeArray()); 
				jQuery.ajax({
					url:webPath+"/mfCusReportItem/insertItemAjax",
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							  window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
							  myclose_click();
						}else if(data.flag == "error"){
							 alert(data.msg,0);
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
		}	
			function selectItem(){
				openReportItemDialog(reportTypeId, function(data){
					if(data==''){
						return false;
					}
					$('input[name=reoprtLevName]').val(data.reportName);
					$('input[name=reoprtItemLev]').val(data.reportItemId);
				})
			}
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column">
				<div class="bootstarpTag">
					<form method="post" id="cwItemForm" theme="simple" name="cwItemForm" action="">
						<dhcc:bootstarpTag property="formfinreport0002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertItemForm(this,'#cwItemForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>