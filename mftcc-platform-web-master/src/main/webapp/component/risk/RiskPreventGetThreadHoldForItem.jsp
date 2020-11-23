<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/risk/js/RiskPreventSceGen.js"></script>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/risk/css/RiskPreventSceGen.css"/>
		
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<script type="text/javascript">
			$(function(){
				$(".select-border").css("height","150px");
				$(".table_content").height("115px");
				$("#saveThreadHold").hide();
			});
			function editThreadHode(obj) {
				if(checkIfCanSave()==false) {
						return;
				}
				var tr = $(obj).parent().parent();
				var threadHoldName = tr.attr("threadHoldName");
				var threadHoldType = tr.find("td:nth-child(2)");
				
				if(threadHoldType.text()!="字典项") {
					var threadHoldValueInput = tr.find("td:nth-child(3)").find("input");
					threadHoldValueInput.removeAttr("disabled");
					threadHoldValueInput.focus();
					$("#saveThreadHold").attr("threadholdname",tr.attr("threadholdname"));
					$("#saveThreadHold").show();
				}else {
					var threadHoldValueInput = tr.find("td:nth-child(3)").find("select");
					threadHoldValueInput.removeAttr("disabled");
					threadHoldValueInput.focus();
					$("#saveThreadHold").attr("threadholdname",tr.attr("threadholdname"));
					$("#saveThreadHold").show();
					
				}
			}
			
			function saveThreadHold(obj) {
				var itemNo = '${riskPrevent.itemNo}';
				var threadHoldName = $(obj).attr("threadholdname");
				var editTr = $("tr[name='"+threadHoldName+"']");
				var threadHoldType = editTr.find("td:nth-child(2)");
				var threadHoldVal = "";
				if(threadHoldType.text()!="字典项") {
					threadHoldVal = editTr.find("td:nth-child(3)").find("input").val();
				}else {
					threadHoldVal = editTr.find("td:nth-child(3)").find("select").val();
				}
				
				$.ajax({
						url:webPath+"/riskPrevent/saveEditedThreadHold",
						data:{
							"riskPrevent.itemNo":itemNo,
							"riskPrevent.threadHoldName":threadHoldName,
							"riskPrevent.threadHoldVal":threadHoldVal
							
						},
						type:"POST",
						dataType:"json",
						async:false,//关键
						success:function(data){
							if(data.flag=="error"){
								//myAlert(data.msg);
								alert(data.msg,0);
							}else{
								if(threadHoldType.text()!="字典项") {
									threadHoldVal = editTr.find("td:nth-child(3)").find("input").attr("disabled","disabled");
								}else {
									threadHoldVal = editTr.find("td:nth-child(3)").find("select").attr("disabled","disabled");
								}
								flag = true;//必须写
								$(obj).attr("threadholdname","");
				        		$(obj).hide();
								//myAlert(top.getMessage("SUCCEED_SAVE"));
								alert(top.getMessage("SUCCEED_SAVE"),1);
								
							}
						},error:function(data){
							//myAlert(  top.getMessage("FAILED_SAVE"));
							alert(  top.getMessage("FAILED_SAVE"),0);
						}
					});
				
			}
			
			function checkIfCanSave() {
				var saveButtonRelNo = $("#saveThreadHold").attr("threadholdname");
				if(saveButtonRelNo != "") {
					//myAlert("存在未保存的阀值项");
					alert("存在未保存的阀值项",0);
					return false;
				}else {
					return true;
				}
			}
		</script>
		<style>
		
		.colmon textarea{
			height:60px;
			resize: none;
			color: #009CE2;
			float:right;
			width:390px;
		}
		.colmon .textareaOnly{
			height:90px;
		}
		.colmon .textareaHidden{
			display:none;
		}
		.content .select-border label.colmonLabel{
			margin-left:0;
			color:#7e9abb;
			margin-top:5px;
			overflow:hidden;
		}
		.content .select-border label.colmonLabel span{
			display:block;
			float:left;
			width:50px;
			text-align:center;
			margin-right:10px;	
			margin-top:10px;
		}
		.content .select-border label.colmonLabel1 span{
			margin-top:20px;
		}
		.content .select-border label.colmonLabel1{
			margin-top:0;
		}
		</style>
    </head>
    <body class="overflow">
    	<div class="content" style="margin:0">
    		<div class="row">
    			<div class="col-md-12">
    				<div class="select-border" style="margin: 5px 5px 0;">
	    					<div class="colmon">
	    					 
	    						 <!-- <label>风险拦截描述：</label><label style="color: #009CE2"><s:property value="riskPrevent.itemSqlDesc"/></label>
	    						  -->
	    						<c:if test="${riskPrevent.itemSqlDesc ne null && riskPrevent.formularDesc ne null && riskPrevent.itemSqlDesc ne '' && riskPrevent.formularDesc ne ''"> 
	    						<label class="colmonLabel">
	    						<span>方案SQL描述</span>
	    						<textarea onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="textareaInputCount(this)" onfocus="showCharsInfo(this);" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" rows="1" cols="70" datatype="0" maxlength="1000" mustinput="0" readonly ="readonly">${riskPrevent.itemSqlDesc}</textarea>
	    						</label>
	    						<label class="colmonLabel colmonLabel1">
	    						<span>方案公式描述</span>
	    						<textarea onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="textareaInputCount(this)" onfocus="showCharsInfo(this);" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" rows="1" cols="70" datatype="0" maxlength="1000" mustinput="0"  readonly ="readonly">${riskPrevent.formularDesc}</textarea>
	    						</label>
	    						</c:if> 
	    						<c:if test="${riskPrevent.itemSqlDesc ne null && riskPrevent.itemSqlDesc ne '' && (riskPrevent.formularDesc eq null || riskPrevent.formularDesc eq '')">
	    						<label class="colmonLabel">
	    						<span>方案SQL描述</span>
	    						<textarea onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="textareaInputCount(this)" onfocus="showCharsInfo(this);" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" rows="1" cols="70" datatype="0" maxlength="1000" mustinput="0" readonly ="readonly">${riskPrevent.itemSqlDesc}</textarea>
	    						</label>
	    						</c:if>
	    						<c:if test="${riskPrevent.formularDesc ne null && riskPrevent.formularDesc ne '' && (riskPrevent.itemSqlDesc eq null || riskPrevent.itemSqlDesc eq '')">
	    						<label class="colmonLabel">
	    						<span>方案SQL描述</span>
	    						<textarea onkeydown="enterKey();" onmousedown="enterKey()" onkeyup="textareaInputCount(this)" onfocus="showCharsInfo(this);" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" rows="1" cols="70" datatype="0" maxlength="1000" mustinput="0" readonly ="readonly">${riskPrevent.itemSqlDesc}</textarea>
	    						</label>
	    						</c:if>
	    						<c:if test="${riskPrevent.formularDesc eq null && riskPrevent.formularDesc eq '' && riskPrevent.itemSqlDesc eq null && riskPrevent.itemSqlDesc eq ''}">
	    							<script type="text/javascript">
	    								$(".select-border").hide();
	    							</script>
	    						</c:if>
	    						
	    					</div>
	    					
    				</div>
    			</div>
    		</div>
    		<div class="row row_content" style="margin:1px">
    			<div class="col-md-12 col_content" style="margin: 0;padding: 0;">
    				<div class="bgColor" style="height: 160px;"> 
    					<div class="title" style="height: 20px;">
    						<h5 style="margin: 6px -5px; color:#32b5cb;">阀值组合</h5>
    						<span class="selectedButtons" style="left: 75%;">
    						    <input type="button" style="background-color: #32b5cb;margin: 8px 15px" value="保存修改的阀值" id="saveThreadHold" threadholdname="" onclick="saveThreadHold(this)">
    						</span>
    					</div>
    					
    					<div class="table_content parent" style="height: 135px">
    					<table cellspacing="1" border="0" width="100%" align="center"  class="ls_list" id="dimetablist">
							
							<thead> 
								<tr> 
								 <th align="left" name="dimesName" scope="col"  style="width: 30%">阀值名称</th>
								 <th align="left" name="dimesName" scope="col" style="width: 30%">阀值类型</th>
								 <th align="left" name="dimesName" scope="col"  style="width: 30%">数值</th>
								 <th align="left" name="dimesName" scope="col" style="width: 10%">操作</th>
								 
								 		 
								 
								</tr>
							</thead>
							 <tbody>
							 	<c:forEach var="rth" items="${riskPrevent.riskItemThreashodes}">
							 		<tr threadHoldName=${rth.threashodeName} name=${rth.threashodeName}>
							 		<td align="left" >${rth.threashodeChnName}</td>
							 		<td align="left" >${rth.threashodeType}</td>
							 		<td align="left" >
							 			<c:if test="${rth.threashodeType eq '字典项'}">
							 				<select style="width:70%" disabled="disabled">
							 					<c:forEach var="pd" items="${rth.parmDic}">
							 						<c:if test="${pd.optCode eq rth.dicKeyValue}">
							 							<option value="${pd.optCode }" selected="selected">${pd.optName}</option>
							 						</c:if>
							 						<c:if test="${pd.optCode ne rth.dicKeyValue}">
							 							<option value="${pd.optCode}" >${pd.optName}</option>
							 						</c:if>
							 					</c:forEach>
							 				</select>
							 			</c:if>
							 			<c:if test="${rth.threashodeType ne '字典项'}">
							 			<input style="width:70%"  type="text" value=${rth.threashodeValue} disabled="disabled">
							 			</c:if>
							 		</td>
							 		<td align="left" ><a onclick="editThreadHode(this)" class="abatch" style="color:#32b5cb;">编辑</a></td>
							 		</tr>
							 	</c:forEach>
							 </tbody> 
							</table>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    	<script type="text/javascript">
	    	if($(".select-border").css("display")=="none"){
	    		$(".bgcolor").css("height","314px")
	    	}
	   </script>
 	</body>
</html>