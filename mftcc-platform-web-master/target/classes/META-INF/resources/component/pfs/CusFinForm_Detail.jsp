<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
		<style type="text/css">
		.select-table {
			margin-left: auto;
			margin-right: auto;
		}
		.select-table .cBtn {
		   border: 1px solid #d2d3d6;
		    height: 30px;
		    margin-right: 10px;
		    text-align: center;
		    width: 50px;
		    background: white;
		    line-height: 30px;
		}
		.select-table tr{
			height: 50px;
			/* line-height: 50px; */
		}
		.select-table .des-td{
			text-align: right;padding-right: 10px;color: #929292;
		}
		.select-table .parm-input{
			 height: 34px;
			 margin-right: 5px;
			 width: 260px;
			 padding-right: 25px;
			font-size: 12px;
		}
		.select-table .parm-fangdajing{
			  position: absolute;
			  top: 10px;
			  left: 235px;
			  cursor: pointer;
		}
		.operation-div{
			margin-bottom: 10px;
		}
		</style>
	</head>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="margin-top: 30px;">
				<div class="bootstarpTag" id="tab-content">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" theme="simple" id="operform" name="operform" action="${webPath}/cusFinForm/updateAjax">
						<dhcc:bootstarpTag property="formpfs2001" mode="query"/>
						<div>
							<table style="margin-top: 30px;" class="select-table">
							<tbody>
								<tr>
								<td  class="des-td">报表元素</td>
								<td>
									<div style="display: inline-block;position: relative;float: left;">
										<input class="parm-input" name="parm-input" readonly="readonly">
										<i class="i i-fangdajing parm-fangdajing" onclick="selectParm(this);"></i>
									</div>
									<select style="height: 34px;width:120px;float: left;" name="qcOrqm">
										<option value="1">期末金额</option>
										<option value="2">期初金额</option>
									</select>	
								</td>
								</tr>
								<tr><td class="des-td">运算符</td>
								<td>
									<div class="operation-div">
									<button class="cBtn" type="button" onclick="operation('7');">7</button>
									<button class="cBtn" type="button" onclick="operation('8');">8</button>
									<button class="cBtn" type="button" onclick="operation('9');">9</button>
									<button class="cBtn" type="button" onclick="operation('+');">+</button>
	 								<button class="cBtn" type="button" onclick="operation('clear');" style="font-size: 12px;">清空</button>
	 								</div>
	 								<div class="operation-div">
									<button class="cBtn" type="button" onclick="operation('4');">4</button>
									<button class="cBtn" type="button" onclick="operation('5');">5</button>
									<button class="cBtn" type="button" onclick="operation('6');">6</button>
									<button class="cBtn" type="button" onclick="operation('-');">-</button>
	 								</div>
	 								<div class="operation-div">
									<button class="cBtn" type="button" onclick="operation('1');">1</button>
									<button class="cBtn" type="button" onclick="operation('2');">2</button>
									<button class="cBtn" type="button" onclick="operation('3');">3</button>
									<button class="cBtn" type="button" onclick="operation('/');">/</button>
	 								</div>
	 								<div class="operation-div">
									<button class="cBtn" type="button" onclick="operation('0');">0</button>
									<button class="cBtn" type="button" onclick="operation('(');">(</button>
									<button class="cBtn" type="button" onclick="operation(')');">)</button>
									<button class="cBtn" type="button" onclick="operation('*');">*</button>
	 								</div>
								</td>
								</tr>
							</tbody>
							</table>
						</div>
					</form>
				</div>	
			</div>
		</div>		
		<div class="formRowCenter">
		    <dhcc:thirdButton value="保存" action="保存" onclick="insertAjaxThis('#operform');"></dhcc:thirdButton>
 			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		 </div>
	</div>
	</body>
	<script type="text/javascript">
	$(function(){
		$("input[name=formNo]").attr("readonly",true);
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});
	
	function operation(parm){//运算符触发的函数，0是清空
		if(parm == "clear"){
			$("#tab-content").find("input[name=formFormula]").val("");
			$("#tab-content").find("textarea[name=formDesc]").val("");
			$("#tab-content").find("input[name=parm-input]").val("");
		}else{
			var temp =$("#tab-content").find("input[name=formFormula]").val()+parm;
			$("#tab-content").find("input[name=formFormula]").val(temp);
			temp =$("#tab-content").find("textarea[name=formDesc]").val()+parm;
			$("#tab-content").find("textarea[name=formDesc]").val(temp);
		}
		
	};
	
	
	function selectParm(objThis){
		//$formulaNameObj = $(objThis);
		dialog({
			title:'选择报表项',
			id:"selectParmDialog",
			backdropOpacity:0,
			height:500,
			width:600,
			url:webPath+"/cusFinParm/getListPageForSelect",
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
    			if(typeof(this.returnValue) == "undefined" || typeof(this.returnValue) == null || this.returnValue == ''){
    			
    			}else{
    				var temp1 ="";
    				var temp2 ="";
    				if($("#tab-content").find("select[name=qcOrqm]").val() == "2"){
    					temp1 ="@";
    					temp2="(期初)";
    				}
    				var temp =$("#tab-content").find("input[name=formFormula]").val()+temp1+this.returnValue.codeColumn;
    				$("#tab-content").find("input[name=formFormula]").val(temp);
    				temp =$("#tab-content").find("textarea[name=formDesc]").val()+temp2+this.returnValue.codeName;
    				$("#tab-content").find("textarea[name=formDesc]").val(temp);
    				$("#tab-content").find("input[name=parm-input]").val(this.returnValue.codeName);
    			}
    		}
		}).showModal();
	};
	function insertAjaxThis(obj){
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
						alert(data.msg,1);
						top.updateFlag=true;
						 top.cusFinForm=data.cusFinForm;
						 myclose_click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	
	</script>
</html>
