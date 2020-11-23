<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusBankAccManage.js"></script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="cusBankAccInsert" theme="simple" name="operform" action="${webPath}/mfCusBankAccManage/insertAjax">
						<dhcc:bootstarpTag property="formcusbank00003" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusFormFormat('#cusBankAccInsert');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="closeDialog();"></dhcc:thirdButton>
		</div>
	</div>
<script type="text/javascript">
	var sysProjectName ='${dataMap.sysProjectName}'; 
	$(function() {
		MfCusBankAccManage.init();
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	 	$("input[name='accountNo']").on('keyup input',function(){
	 	  var  accountNo =$(this).val();
	        var reg=/^-?[0-9,\s]*$/;//此写法允许首字符为0
			if(!reg.test(accountNo)){
				$(this).val("");
			}else{
				 if(/\S{5}/.test(accountNo)){
		            this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
				}
	       	}		       	
        }); 
         //账户用途选择组件
        $("select[name=useType]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false//单选
		});
         
        if('ygbank'==sysProjectName){
	        bindCnapsDataSource($("input[name=bankNumbei]"));
        }
	});
	function ajaxInsertCusFormFormat(obj){
		var accountNo = $("input[name='accountNo']").val();
		$("input[name='accountNo']").val(accountNo.trim().replace(/\s/g,""));
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
						var cusBankAcc = new Object();
						cusBankAcc.id = data.mfCusBankAccManage.id;
						cusBankAcc.accountNo = data.mfCusBankAccManage.accountNo;
						cusBankAcc.accountName = data.mfCusBankAccManage.accountName;
						cusBankAcc.bank = data.mfCusBankAccManage.bank;
						cusBankAcc.bankAuthSts = data.mfCusBankAccManage.bankAuthSts;
						parent.dialog.get('addBankAccDialog').close(cusBankAcc).remove();
					}else{
						alert(data.msg,0);
					}
				}
			});
		}
	}
	function getBankByCardNumber(obj){
		if('ygbank'!=sysProjectName){
			var identifyNumber = obj.value.trim().replace(/\s/g,"");
			$.ajax({
				url:webPath+"/bankIdentify/getByIdAjax",
				data:{identifyNumber:identifyNumber},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						BASE.removePlaceholder($("input[name=bankNumbei]"));
						BASE.removePlaceholder($("input[name=bank]"));
						$("input[name=bankNumbei]").val(data.bankId);
						$("input[name=bank]").val(data.bankName);
					}else{
						$("input[name=bankNumbei]").val("");
						$("input[name=bank]").val("");
					}	
				},error:function(){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	}
	
	function bindCnapsDataSource(obj){
		$(obj).popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfCnapsBankInfo/getCnapsListAjax",// 请求数据URL
			valueElem:"input[name=bankNumbei]",//真实值选择器
			title: "选择行号",//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=bankNumbei]"));
				BASE.removePlaceholder($("input[name=bank]"));
				var sltVal = elem.data("selectData");
				$("input[name=bankNumbei]").val(sltVal.bankcode);
				$("input[name=bank]").val(sltVal.bankname);
			},
			tablehead:{//列表显示列配置
				"bankcode":"行号",
				"bankname":"行名"
			},
			returnData:{//返回值配置
				disName:"bankcode",//显示值
				value:"bankname"//真实值
			}
		});
        $("input[name=bankNumbei]").next().click();
	}
	//在账户名项添加onChange事件 修改账户名 清空关联的账户信息
	var accountNameChange_reset = function(obj){
			$("input[name='idNum']").val("");
			$("input[name='reservedPhone']").val("");
			$("input[name='accountNo']").val("");
	}
	function closeDialog(){
		parent.dialog.get('addBankAccDialog').close().remove();
	}
</script>
</body>
</html>
