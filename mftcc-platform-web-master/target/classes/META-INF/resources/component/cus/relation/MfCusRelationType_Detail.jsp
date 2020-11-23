<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style type="text/css">
		.bigform_content .content_table .ls_list thead tr th{
			font-weight: bold;
		}
		</style>
	</head>
	<body>
		<div>	
			<div style="vertical-align: bottom; display: block;" class="tabCont">
				<div class="btn-div" style="display: block;">
					<div class="btn-left-div">
						<dhcc:thirdButton value="新增" action="新增" onclick="addRelationType();"></dhcc:thirdButton>
					</div>
				</div>
			</div>
				<div id="content" class="table_content"  style="height: auto;">
					<dhcc:tableTag property="tableMfcusRelationType0001" paginate="mfCusRelationTypeList" head="true"></dhcc:tableTag>
				</div>
		</div>
	    <div style="display: none;padding-left: 20px;padding-right: 20px;" id="addRelationType-div">
	    	<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusRelationType/insertAjax">
	    		<p class="tip"><span>说明：</span>带*号的为必填项信息，请填写完整。</p>
	    		<div>
		    		<dhcc:bootstarpTag property="formrelationtype0001" mode="query"/>
	    		</div >
	    		<div style="height: 150px;"></div>
	    		<div class="formRowCenter">
		    		<dhcc:thirdButton value="保存" action="保存" onclick="submitRelation(this.form);"></dhcc:thirdButton>
 					<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancel();"></dhcc:thirdButton>
		    	</div>
		    </form>
	    </div>
	</body>
	<script type="text/javascript">
	function cancel(){
		dialog.get('addRelationDialog').close();
	}
	function submitRelation(obj){
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
							window.location.reload()
							dialog.get('addRelationDialog').close();
						}else if(data.flag == "error"){
							 alert(data.msg,0);
						}
						 dialog.get('addRelationDialog').close();
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}else{
				alert(top.getMessage("NOT_EMPTY"),0);
			}
		};
	function addRelationType(){
			dialog({
				id:'addRelationDialog',
				content:$('#addRelationType-div'),
				width:800,
				title:'新增关联关系',
				backdropOpacity:0,
				onShow:function(){
					$("#addRelation-div").find("input").val("");
					$("#addRelation-div").find("select").val("");
				}
				
			}).showModal();
		};
	</script>
</html>