<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.ArrayList,java.util.List"
	pageEncoding="UTF-8"%>
<%@page import="app.component.sys.entity.SysOrg"%>
<%@page import="app.component.sys.entity.SysOrgTree"%> 
<%@ include file="../include/pub.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base target="_self">
		<title>通过角色选择</title>
		<%
				String path = request.getContextPath();
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 

		<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/util.js'></script>
		<script type='text/javascript'src='${webPath}/dwr/interface/SysOrgDwr.js'></script>
		<script language="javascript" src="${webPath}/UIplug/xtree/js/xtree.js" type="text/javascript"></script> 
	</head>
	<body>
		
<div class="right_bg">
<script type="text/javascript">
	   
	function scall() {
		document.all("show").style.top = ((document.documentElement.scrollTop) + 10) + "px";
		//document.all("show").style.right = ((document.documentElement.scrollRight) + 240) + "px";
	}
	function DisplayValue(data){
		$.each(data,function(i,list){
			console.log(list);
		});
	   document.all("brNo").value=data.brNo==null?"":data.brNo;
	   document.all("brName").value=data.brName==null?"":data.brName;
	   myform.brNo.readOnly=true;
	   document.all("brLev").value=data.brLev==null?"":data.brLev;
	   document.all("brTel").value=data.brTel==null?"":data.brTel;
	   document.all("brFax").value=data.brFax==null?"":data.brFax;
	   document.all("brType").value=data.brType==null?"":data.brType;
	   document.all("brAddr").value=data.brAddr==null?"":data.brAddr;
	   document.all("brArea").value=data.brArea==null?"":data.brArea;
	   document.all("brPost").value=data.brPost==null?"":data.brPost;
	   document.all("brFinCode").value=data.brFinCode==null?"":data.brFinCode;
	   document.all("brSts").value=data.brSts==null?"":data.brSts;
	   document.all("upOne").value=data.upOne==null?"":data.upOne;
	   document.all("upTwo").value=data.upTwo==null?"":data.upTwo;
	   var div = document.all("show");
	   div.style.display = "block"; 
	   
	   myform.editbutton.style.display="block";
	   myform.creatAdd.style.display="block";
	   myform.deletebutton.style.display="block";
	   myform.addbutton.style.display="none";
	}
	 function AddDepartment(){
	 	myform.upTwo.value=myform.upOne.value;
	 	myform.upOne.value=myform.brNo.value;
	 	myform.brLev.value= parseInt(myform.brLev.value)+1;
	 	<%--myform.brLev.disabled=true;--%>
	 	myform.brNo.value="";
	 	myform.brNo.readOnly=false;
	 	myform.brTel.value="";
	 	myform.brFax.value="";
		myform.brAddr.value="";
		myform.brArea.value="";
		myform.brPost.value="";
		myform.brFinCode.value="";
	 	myform.editbutton.style.display="none";
	 	myform.deletebutton.style.display="none";
	 	myform.creatAdd.style.display="none";
	 	myform.addbutton.style.display="block";
	 }
	 function editDepartment(){
	 	myform.action=webPath+"/sysOrg/UpdateByTree";
	 	var brNo=document.getElementById("brNo").value;
	 	var brName=document.getElementById("brName").value;
	 	var upOne=document.getElementById("upOne").value;
	 	var brFinCode=document.getElementById("brFinCode").value;
	 	if(brNo==""||brNo==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "机构编号"));
	 		return ;
	 	}
	 	if(brName==""||brName==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "机构名称"));
	 		return ;
	 	}
	 	if(upOne==""||upOne==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "上一级机构号"));
	 		return ;
	 	}
	 	if(brFinCode==""||brFinCode==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "业务机构编码"));
	 		return ;
	 	}
	 	if(confirm("确定要保存吗?"))
	 	{
	 		myform.submit();
	 	}
	 }
	 
	  function insertDepartment(){
	 	var brNo=document.getElementById("brNo").value;
	 	var brName=document.getElementById("brName").value;
	 	var upOne=document.getElementById("upOne").value;
	 	var brFinCode=document.getElementById("brFinCode").value;
	 	if(brNo==""||brNo==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "机构编号"));
	 		return ;
	 	}
	 	if(brName==""||brName==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "机构名称"));
	 		return ;
	 	}
	 	if(upOne==""||upOne==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "上一级机构号"));
	 		return ;
	 	}
	 	if(brFinCode==""||brFinCode==null){
	 		alert(top.getMessage("NOT_FORM_EMPTY", "业务机构编码"));
	 		return ;
	 	}
	 	myform.action=webPath+"/sysOrg/InsertByTree";
	 	

	 	myform.submit();
	 }
	 function deleteDepartment(){
	 	if(confirm("确定要删除吗?"))
	 	{
	 		//是否有子节点
 			SysOrgDwr.checkDownOne(document.getElementById("brNo").value,callBackSysOrg);
	 	}
	 }
	 function callBackSysOrg(data){
	 	 var displayType = "<%=(String)request.getAttribute("displayType")%>"
		  myform.action=webPath+"/sysOrg/delByTree";
	 	if(data=="true"){
 			myform.submit();
	 	}else{
	 		alert(data);
	 	}
	 }
</script>
<div style="position:absolute;" align="left">
	
	<script type="text/javascript">
	var node_root = new WebFXTree("机构树","", "classic");
	node_root.target="_self";
	
	<%
		SysOrgTree sot = new SysOrgTree();
		SysOrg sysOrg = sot.getTreeTop();
		sysOrg.setUpOne("-1");
	%>
	
	<%!
	private void addNode(SysOrg sysOrg, JspWriter out) throws Exception{
		 String brNo=String.valueOf(sysOrg.getBrNo());
		 String upOne=String.valueOf(sysOrg.getUpOne());
		 String brName=sysOrg.getBrName();
		 out.print("var node_"+brNo+"=new WebFXTreeItem('"+brName+"', 'javascript:DoSelect("+brNo+");');");
		 out.print("node_"+brNo+".target='_self';");
		 if ("-1".equalsIgnoreCase(upOne)){
		 	out.print("node_root.add(node_"+brNo+");");
		 }else{
		 	out.print("node_"+upOne+".add(node_"+brNo+");");
		 }
		 SysOrgTree sot = new SysOrgTree();
		 List child=sot.getChildren(brNo);
		 for( int i=0; child!=null&&i<child.size(); i++){
			SysOrg childOrg = (SysOrg)child.get(i);
			addNode(childOrg, out);
		 } 
	}
	%>
	<%            
		addNode(sysOrg, out);
	%> 
	document.write(node_root);
	function DoSelect(value) { 
		//DWREngine.setAsync(false);
		dwr.engine.setAsync(false);
		SysOrgDwr.searchSysOrg(value,function(data){
			DisplayValue(data);
		})
	}
	</script>
</div>
<form name="myform" method="post" theme="simple" validate="true">
<div id="show" style="width:400px;height:400px;border: 0px solid #000000;display:none;position:absolute;right:100px;top:10px;" > 
 	
 	<table  align="center" >
		<tr>
			<td align="right" width="25%">
				机构编号<font color="red">*</font>
			</td>
			<td align="left">
				<input size="30" maxlength="10" date="true" emptyok="false" alt="机构编号" name="brNo" id="brNo" value="${brNo }" />
			</td>
		</tr>
		<tr>
			<td align="right" width="25%">
				机构名称<font color="red">*</font>
			</td>
			<td align="left">
			    <input size="30" maxlength="20"  emptyok="false" alt="机构名称" name="brName" id="brName" value="${brName}">
			</td>
		</tr>
		<tr>
			<td align="right" width="25%">
				机构等级<font color="red">*</font>
			</td>
			<td align="left"   >
				<select  alt="机构等级" name="brLev" style="width:220px"  id="brLev">
					<option value="1">1级</option>
					<option value="2">2级</option>
					<option value="3">3级</option>
					<option value="4">4级</option>
					<option value="5">5级</option>
				</select>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				机构电话<font color="red">*</font>
			</td>
			<td align="left">
			    <input size="30" maxlength="13"  emptyok="false" alt="机构电话" name="brTel" id="brTel" value="${brTel}"/> 
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				机构传真
			</td>
			<td align="left">
			    <input size="30" maxlength="13"  emptyok="false" alt="机构传真" name="brFax" id="brFax" value="${brFax}"/>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				机构类型
			</td>
			<td align="left">
					 <select name="brType" style="width:220px" id="brType">
					    <option value="1">管路机构</option>
					    <option value="2">资金方</option>
					    <option value="3">经营商</option>
					    <option value="4">供应方</option> 
					 </select>>
				<%--  <dict:select ddname="BR_TYPE" alt="机构类型"
											property="brType" style="width:220px" /> --%>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				机构地址<font color="red">*</font>
			</td>
			<td align="left">
			    <input size="30" maxlength="40"  emptyok="false" alt="机构地址" name="brAddr" id="brAddr" value="${brAddr}"/>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				行政区划
			</td>
			<td align="left">
			    <input size="30" maxlength="6"  emptyok="false" alt="行政区划" name="brArea" id="brArea" value="${brArea}"/>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				邮政编码<font color="red">*</font>
			</td>
			<td align="left">
			    <input size="30" maxlength="6"  emptyok="false" alt="邮政编码" name="brPost" id="brPost" value="${brPost}"/>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				业务机构编码
			</td>
			<td align="left">
			    <input size="30" maxlength="30"  emptyok="false" alt="业务机构编码" name="brFinCode" id="brFinCode" value="${brFinCode}"/>
			</td>
		</tr> 
		<tr>
			<td align="right" width="25%">
				机构状态
			</td>
			<td align="left">
				 <select name="brSts" style="width:220px" id="brSts">
				    <option value="1">正常</option>
				    <option value="2">注销</option>
				 </select>
			</td>
		</tr>  
		<tr>
		   <td><input type='hidden' name='upOne' id="upOne" value='"+data.upOne+"' readonly/></td><td><input type='hidden' name='upTwo' id="uptTwo" value='"+data.upTwo+"'/></td>
		</tr> 
	</table>
	<div align="center" style="margin-top: 10px" class="from_btn">
	<table align="center"><tr><td><input type='button' name='editbutton'  onclick='editDepartment();' value='编辑' class="button_form">
    </td><td><input type='button' name='deletebutton' onclick='deleteDepartment();' value='删除'  class="button_form">
    </td><td><input type='button' name='addbutton' value="提交" style="display:none;" onclick='insertDepartment();'  class="button_form"/>
    </td><td><input type='button' name='creatAdd' onclick='AddDepartment();' value='添加'  class="button_form">
    </td></tr></table>
  </div>
</form>
</div>
	</body>
</html>