<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style>
		body {
			height: auto;
			position: relative;
		}
		
		label {
			font-weight: 400;
			color: #333;
			margin-left: 10px;
			line-height: 24px;
			font-size:14px;
		}
		
		input {
			
		}
		
		button {
		backround: none;
		border: none;
		background-color: #fff;
		border: 1px solid #d2d3d6;
		border-radius: 0px;
		color: #fff;
		font-size: 14px;
	}
	
	#dimeConfirm {
		margin-left: 30px;
		margin-top: 10px;
		background: #32b5cb;
		border: 1px solid #32b5cb;
	}
	#dimeConfirm:hover{
		background:#009db7;
	}
	#cancleConfirm{
		color: #000;
	}
	#cancleConfirm:hover{
		background: #e7e7e7;
	}
		</style>
		<script type="text/javascript">
		$(document).ready(function() {
			var scNo = "<s:property value='scNo'/>";
			var tr = $("#"+scNo+"",window.parent.document);
			var dime1 = tr.find("td:nth-child(2)").attr("keyName");
			var dime2 = tr.find("td:nth-child(3)").attr("keyName");
			var dime3 = tr.find("td:nth-child(4)").attr("keyName");
			$("input[name='limitDime']").each(function() {
				var checkVal = $(this).val();
				if(checkVal== dime1 || checkVal==dime2 || checkVal==dime3) {
					
					$(this).attr("checked","checked");
				}
			});
			
				$("input[name='limitDime']").click(function() {
					
					var checkOption = $(this);
					var checkLenth = $("input[name='limitDime']:checked").length;
					if(checkLenth>1) {
						$("input[name='limitDime']:checked").each(function() {
							 var selectVal = $(this).val();
							 if(selectVal == "SCENCE_DEFAULT_DIME") {
								checkOption.attr("checked",false);
								alert("默认维度不可和其他维度组合");
								return;
							}
						});
					}
					if(checkLenth>3) {
						$(this).attr("checked",false);
						alert("最多只能选择三个维度");
					}
				});
				
				$("#cancleConfirm").click(function() {
				var parentLimitDiv = $("#limitDiv",window.parent.document);
				parentLimitDiv.hide();
				});
				
				//配置维度按钮点击事件
				$("#dimeConfirm").click(function() {
					var parentLimitDiv = $("#limitDiv",window.parent.document);
					var limtDimesCheckBox = $('input:checkbox[name=limitDime]:checked');
					var dime1Select = $("<select name='dime1Select'></select>");
					var dime2Select = $("<select name='dime2Select'></select>");
					var dime3Select = $("<select name='dime3Select'></select>");
					
					var scNo = "<s:property value='scNo'/>";
					var tr = $("#"+scNo+"",window.parent.document);
					var dime1 = tr.find("td:nth-child(2)");
					var dime2 = tr.find("td:nth-child(3)");
					var dime3 = tr.find("td:nth-child(4)");
					
					
					var checkLenth = $("input[name='limitDime']:checked").length;
					if(checkLenth == 0) {
						dime1.empty();
						dime2.empty();
						dime3.empty();
					}
					if(checkLenth == 1) {
						var oldDime1Val = dime1.attr("keyname");
						var oldDime1JavaVal = dime1.attr("javaname");
						limtDimesCheckBox.each(function() {
						var keyName  = $(this).val();
					    var keyChnName = $(this).parent().text();
					    var option;
					    var javainput1;
					    var label = $("<nobr><label class=javaLabel style=width:34%;font-size:12;>Java变量名:</label></nobr>");
					    if(oldDime1Val == keyName) {
					    	option = $("<option value="+keyName+" selected=selected>"+keyChnName+"</option>");
					    	javainput1 = $("<input name=javainput1 type=text style=width:66% value="+oldDime1JavaVal+"></input>");
					    }else {
					    	option = $("<option value="+keyName+">"+keyChnName+"</option>");
					    	javainput1 = $("<input  name=javainput1 type=text style=width:66%></input>");
					    	
					    }
						dime1Select.append(option);
						dime1.empty();
						dime2.empty();
						dime3.empty();
						dime1.append(dime1Select);
						//dime1.append(label);
						//dime1.append(javainput1);
						});
					}else if(checkLenth == 2) {
						var oldDime1Val = dime1.attr("keyname");
						var oldDime2Val = dime2.attr("keyname");
						var oldDime1JavaVal = dime1.attr("javaname");
						var oldDime2JavaVal = dime2.attr("javaname");	
						//alert(oldDime2JavaVal);			
						limtDimesCheckBox.each(function() {
						var keyName  = $(this).val();
					    var keyChnName = $(this).parent().text();
					    var option1 ;
						var option2 ;
					    var	javainput1 = $("<input name=javainput1 type=text style=width:66% value="+oldDime1JavaVal+"></input>");
						var javainput2 = $("<input name=javainput2 type=text style=width:66% value="+oldDime2JavaVal+"></input>");

						var label1 = $("<nobr><label class=javaLabel style=width:34%;font-size:12;>Java变量名:</label></nobr>");
						var label2 = $("<nobr><label class=javaLabel style=width:34%;font-size:12;>Java变量名:</label></nobr>");
						
						
					    if(oldDime1Val == keyName) {
					    	option1 = $("<option value="+keyName+" selected=selected>"+keyChnName+"</option>");
					    	
					    }else {
					    	option1 = $("<option value="+keyName+">"+keyChnName+"</option>");
					    
					    }
					    
					     if(oldDime2Val == keyName) {
					    	option2 = $("<option value="+keyName+" selected=selected>"+keyChnName+"</option>");
					    	
					    	
					    }else {
					    	option2 = $("<option value="+keyName+">"+keyChnName+"</option>");
					    
					    }
						
						dime1Select.append(option1);
						dime2Select.append(option2);
						dime1.empty();
						dime2.empty();
						dime3.empty();
						dime1.append(dime1Select);
						dime2.append(dime2Select);
						//dime1.append(label1);
						//dime1.append(javainput1);
						//dime2.append(label2);
						//dime2.append(javainput2);
						});
					}else if(checkLenth == 3) {
						var oldDime1Val = dime1.attr("keyname");
						var oldDime2Val = dime2.attr("keyname");
						var oldDime3Val = dime3.attr("keyname");
						var oldDime1JavaVal = dime1.attr("javaname");
						var oldDime2JavaVal = dime2.attr("javaname");	
						var oldDime3JavaVal = dime3.attr("javaname");	
						
						limtDimesCheckBox.each(function() {
						var keyName  = $(this).val();
					    var keyChnName = $(this).parent().text();
					    var option1;
						var option2;
						var option3;
						var	javainput1 = $("<input name=javainput1 type=text style=width:66% value="+oldDime1JavaVal+"></input>");
						var javainput2 = $("<input name=javainput2 type=text style=width:66% value="+oldDime2JavaVal+"></input>");
						var javainput3 = $("<input name=javainput3 type=text style=width:66% value="+oldDime3JavaVal+"></input>");

						var label1 = $("<nobr><label class=javaLabel style=width:34%;font-size:12;>Java变量名:</label></nobr>");
						var label2 = $("<nobr><label class=javaLabel style=width:34%;font-size:12;>Java变量名:</label></nobr>");
						var label3 = $("<nobr><label class=javaLabel style=width:34%;font-size:12;>Java变量名:</label></nobr>");
						
					    if(oldDime1Val == keyName) {
					    	option1 = $("<option value="+keyName+" selected=selected>"+keyChnName+"</option>");
					    }else {
					    	option1 = $("<option value="+keyName+">"+keyChnName+"</option>");
					    
					    }
					    
					     if(oldDime2Val == keyName) {
					    	option2 = $("<option value="+keyName+" selected=selected>"+keyChnName+"</option>");
					    }else {
					    	option2 = $("<option value="+keyName+">"+keyChnName+"</option>");
					    
					    }
					    
					    if(oldDime3Val == keyName) {
					    	option3 = $("<option value="+keyName+" selected=selected>"+keyChnName+"</option>");
					    }else {
					    	option3 = $("<option value="+keyName+">"+keyChnName+"</option>");
					    
					    }
						
						dime1Select.append(option1);
						dime2Select.append(option2);
						dime3Select.append(option3);
						
						dime1.empty();
						dime2.empty();
						dime3.empty();
						dime1.append(dime1Select);
						dime2.append(dime2Select);
						dime3.append(dime3Select);
						
						//dime1.append(label1);
						//dime1.append(javainput1);
						//dime2.append(label2);
						//dime2.append(javainput2);
						//dime3.append(label3);
						//dime3.append(javainput3);
						
						
						});
					}
					
					
					parentLimitDiv.hide();
				});
			});
		</script>
	</head>
<body style="height:180px;">
   <div class="bigform_content">
   	 <%-- <s:iterator value="limiDimes" id="ld">
   	    <label><input name="limitDime" type="checkbox" style="margin-right: 10px;" value="<s:property value='#ld.keyName'/>" /><s:property value="#ld.keyChnName"/></label> 
   	 </s:iterator> --%>
   	 <c:forEach var=""limitDime"" items="${limiDimes}">
   	    <label><input name="limitDime" type="checkbox" style="margin-right: 10px;" value="${limitDime.keyName}" />${limitDime.keyChnName}</label> 
   	 </c:forEach>
	   	 <div style="position:absolute;bottom:0px;">
		   	 <button  id="dimeConfirm" >确定</button>
		   	 <button  id="cancleConfirm" >取消</button>
	   	 </div>
	</div>
</body>
</html>