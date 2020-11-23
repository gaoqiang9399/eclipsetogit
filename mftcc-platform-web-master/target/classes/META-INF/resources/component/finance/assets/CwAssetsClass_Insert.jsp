<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/cw_assist_util.css" />
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_assist_util.js"></script>
		
		<script type="text/javascript">
		$(function(){
			//根据使用期限带出使用年限
			$('input[name=assetsYear]').blur(function(){
				$('input[name=assetsTerm]').val($('input[name=assetsYear]').val()*12);
			});
// 			var accountType = $('input[name=accountType]').val();
// 			if('1'==accountType) {
// 				$('#account_form').find('tr:eq(2)').remove();
// 			}
			
			$('#account_form').on('click', 'input[name=comAssets], input[name=comDepre]', function(){
				var input = $(this);
				autoComPleter(this, '2', function(selected){
					input.val(selected.accNo + "/" + selected.accName);
					input.trigger("change");//当赋值后触发change事件。
				});
			});
			//预计使用年限值范围
			$('input[name=assetsYear]').attr('datatype', '1');
			$('#account_form').on('blur', 'input[name=assetsYear]', function(){
				var text = $(this).val();
				if(Number(text) < 0){
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"预计使用年限" , "timeTwo": "0"}), 1)
					$(this).val('0')
				}
			});
			//预计净残值率值范围
			$('input[name=residualRate]').attr('datatype', '9');
			$('#account_form').on('blur', 'input[name=residualRate]', function(){
				var text = $(this).val();
				if(Number(text) < 0){
					alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"预计净残值率" , "timeTwo": "0"}), 1)
					$(this).val('0')
				}
				if(Number(text) > 100){
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"预计净残值率" , "timeTwo": "100"}), 1);
					$(this).val('0')
				}
			});
			
			//绑定辅助核算事件
			$("[name=comAssets],[name=comDepre]").change(function(){
				var comLebel=this.value;//选择的科目
				var com=comLebel.split("/");
				var accNo=com[0];//选择之后的科目号accNo
				checkSubInfo($(this),accNo);
			})
			//一开始就在科目输入框后加上一个输入框，存辅助核算项，与插件对应。
			$("[name^=com]").each(function(){
				var thisName=this.name;
				var itemName=thisName.replace("com","item");
				var itemVal=$("[name="+itemName+"]").val();//数据库存的辅助核算项字符串
				var editsCount = $(this).siblings(".edit_items").length;
				if(editsCount == 0){
					$(this).after("<input type='hidden' class='edit_items' autocomplete='off' value=\""+itemVal+"\">");
				}
			})
		});
		//给表单设计器的辅助核算输入框赋值
		function changeItemsInput(){
			var itemsDoms=$(".edit_items");
			for(var i=0;i<itemsDoms.length;i++){
				var prevName=$(itemsDoms[i]).prev().attr("name");//生成它的科目框的name
				var thisName=prevName.replace("com","item");//对应的辅助核算输入框的name
				$("[name="+thisName+"]").val(itemsDoms[i].value);
			}
		}
		function ajaxAddAcount(form){
			changeItemsInput();
			var flag = submitJsMethod($(form).get(0), '');
			if(flag){
				
				var url = $(form).attr("action");
				var dataParam = JSON.stringify($(form).serializeArray());
				jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							var obj = {};
							obj.id = data.cwAssetsClass.classNo;
							obj.name = data.cwAssetsClass.className;
							top.cwAssetsClsData = obj;
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
		//放大镜
		function comCallBack(obj){
			if(obj){
				$('input[name=comAssets]').val(obj.showName);
				$('input[name=comAssets]').trigger("change");//当赋值后触发change事件。
			}
		}
		function comCallBack1(obj){
			if(obj){
				$('input[name=comDepre]').val(obj.showName);
				$('input[name=comDepre]').trigger("change");//当赋值后触发change事件。
			}
		}
		
	</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form method="post" id="account_form"  theme="simple" name="operform" action="${webPath}/cwAssetsClass/insertAjax">
							<dhcc:bootstarpTag property="formassetsclass0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxAddAcount('#account_form');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
