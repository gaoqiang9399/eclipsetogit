<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	
	<style type="text/css">
		
	</style>
	<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_10">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="initAssistInsert_form"  theme="simple" name="operform" action="${webPath}/cwInitBal/initAssistInsertAjax">
							<dhcc:bootstarpTag property="forminitassist0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="initAssistInsert('#initAssistInsert_form');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
				<!-- myclose_showDialog -->
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var txType;
	var cwRelatiionList = '${dataMap.cwRelatiionList}';
	$(function(){
		if(cwRelatiionList){
			var relationlist = $.parseJSON(cwRelatiionList);
			console.log(relationlist);
			
			$("[name=employee]").parents("tr").hide();
			 $("[name=department]").parents("tr").hide();
			 $("[name=customer]").parents("tr").hide();
			 $("[name=defineself]").parents("tr").hide();
			 $("[name=employee]").removeAttr("mustinput");
			 $("[name=department]").removeAttr("mustinput");
			 $("[name=customer]").removeAttr("mustinput");
			 $("[name=defineself]").removeAttr("mustinput");
			for(var i=0;i<relationlist.length;i++){
				//console.log(relationlist[i]);
				if(relationlist[i].txflag==0){
					$("[name=employee]").parents("tr").show();
					$("[name=employee]").attr("mustinput","1");//给属性添加必填项
				}else if(relationlist[i].txflag==1){
				   $("[name=department]").parents("tr").show();
				   $("[name=department]").attr("mustinput","1");//给属性添加必填项
				}else if(relationlist[i].txflag==2){
				   $("[name=customer]").parents("tr").show();
				   $("[name=customer]").attr("mustinput","1");//给属性添加必填项
				}else if(relationlist[i].txflag==3){
					txType=relationlist[i].txType;
					$("[name=defineself]").parents("tr").show();
					var strmu = "<font color=\"#FF0000\">*</font>"+relationlist[i].txName;
				    $("[name=defineself]").parents("tr").children().eq(0).children().text("");//relationlist[i].txName
				    $("[name=defineself]").parents("tr").children().eq(0).children().append(strmu);
				    $("[name=defineself]").attr("mustinput","1");//给属性添加必填项
				}
				
			}
		}
	});
	//员工回调
	function employeelist(obj){
		$("input[name=employee]").val(obj.txName).blur();
		$("input[name=employeeNo]").val(obj.txCode);
		
	}
	//部门回调
	function departmentlist(obj){
		$("input[name=department]").val(obj.txName).blur();
		$("input[name=departmentNo]").val(obj.txCode);
	}
	//客户回调
	function cuslist(obj){
		 $("input[name=customer]").val(obj.txName).blur();
		 $("input[name=customerNo]").val(obj.txCode);
	}
	//自定义
	function otherlistdata(obj){
		 $("input[name=defineself]").val(obj.txName).blur();
		 $("input[name=defineselfNo]").val(obj.txCode);
	}
	
	function initAssistInsert(obj){
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
						//top.showName = accNo + '/' + accName;
						top.addFlag = true;
						myclose_click();
						//myclose_showDialogClick();
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
