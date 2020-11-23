<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
			<style type="text/css">
	.business-span{
		font-size: 22px;
    	color: #32b5cb;
    	margin-right:5px;
	}
	.advice{
	    top: 0px;
	}
	.adviceinfo{
		color: #f34d00;
		font-size: 18px;
		margin-left:5px;
	}
	</style>
	</head>
<body class="overflowHidden bg-white">
		<div class="scroll-content mCustomScrollbar _mCS_1 mCS_no_scrollbar" >
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="height:88%">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="relationt-form" theme="simple"
						name="operform" action="${webPath}/mfCusRelation/insertAjax">
						<dhcc:bootstarpTag property="formrelationtype0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		</div>
		<div class="formRowCenter" style="clear:both;">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#relationt-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			
		</div>

	
</body>
<script type="text/javascript">
	
	var type = (eval("(" + '${json}' + ")")).relationType;
	var cusName=(eval("(" + '${json}' + ")")).cusName;
	var cusNo=$("input[name=cusNo]").val();
	$("input[name='cusName']").attr('value',cusName);
	/* $(function(){
	 	var optionStr = '';
		$.each(type,function(i,obj){
			optionStr = optionStr + "<option value='"+obj.id+"'>"+obj.relationTypeName+"</option>";
			console.log(obj.relationTypeName+'sss');
		});
		$("select[name=relationType]").html(optionStr);	
	}); */
	//新版选择组件
	 $(function(){
	 	$("select[name=relationType]").popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
		});
	 }); 
	
	//取被选的客户的信息
	function getCusDialog(customer){
		$("input[name=cusName1]").val(customer.cusName);
		$("input[name=cusNo1]").val(customer.cusNo);
		$("input[name=cusBaseType1]").val(customer.cusBaseType);
		$("input[name=idNum]").val(customer.idNum);
	};
	//当手动录入/改变将要关联的客户的名称时，清空选择系统客户所带出的值
	function cusName1Change(){
		$("input[name=cusNo1]").val("");
		$("input[name=cusBaseType1]").val("");
		
	}
	function ajaxInsertThis(obj){
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				LoadingAnimate.start();
				var url = $(obj).attr("action");
				var relationTypeName = $(obj).find("[name=relationType]").find("option:selected").text();
				$(obj).find("[name=relationTypeName]").val(relationTypeName);
				var dataParam = JSON.stringify($(obj).serializeArray());
				jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
						top.relation = true;
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
		$(document.body).height($(window).height());
	</script>
	<script type="text/javascript" src="${webPath}/component/cus/relation/js/MfCusRelationTypeInsert.js"></script>
</html>
