<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="app.creditapp.wkf.entity.*,app.creditapp.wkf.bo.*,
	    app.creditapp.sys.entity.*,app.creditapp.sys.bo.*,
	    app.base.SourceTemplate"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %> 
<%@ include file="/component/include/pub_view.jsp"%>
<script type='text/javascript' src='${webPath}/dwr/interface/workflowDwr.js'></script>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/dwr.js'></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组用户信息</title>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
</head>
<body>
<div class="right_bg">
<%
   WkfApprovalRoleBo wkfApprovalRoleBo = (WkfApprovalRoleBo) SourceTemplate.getSpringContextInstance().getBean("wkfApprovalRoleBo");
   TblOrgUserBo tblOrgUserBo = (TblOrgUserBo) SourceTemplate.getSpringContextInstance().getBean("tblOrgUserBo");   
   String saveFlag="insert";
   WkfApprovalRole wkfApprovalRole=(WkfApprovalRole)request.getAttribute("wkfApprovalRole");   
   String wkfRoleNo="";
   String wkfRoleName="";
   WkfApprovalUserBo wkfApprovalUserBo;
   List<WkfApprovalUser> wkfApprovalUserList=null;
   if(null!=wkfApprovalRole)
   {
	   wkfRoleNo=wkfApprovalRole.getWkfRoleNo();
       wkfRoleName=wkfApprovalRole.getWkfRoleName();
       wkfApprovalUserBo = (WkfApprovalUserBo) SourceTemplate.getSpringContextInstance().getBean("wkfApprovalUserBo");
       WkfApprovalUser wkfApprovalUserT=new WkfApprovalUser();
       wkfApprovalUserT.setWkfRoleNo(wkfRoleNo);
       wkfApprovalUserList=wkfApprovalUserBo.getAllList(wkfApprovalUserT);
       saveFlag="update";
   }
%>
		<form name="form" action="${webPath}/wkfApprovalRole/insertOrUpdate" method="post">
		<input type="hidden" name="saveFlag" value="<%=saveFlag%>" >
			<table align="center">				
				<tr>
					<td>
						审批角色编号:
					</td>
					<td><input type="text" name="wkfRoleNo" value="<%=wkfRoleNo%>"  class="BOTTOM_LINE" size="40"> </td>
				</tr>
				<tr>
					<td class="TDstyle02">
						审批角色名称:
					</td>
					<td><input type="text" name="wkfRoleName" value="<%=wkfRoleName%>" class="BOTTOM_LINE" size="40"/> </td>
				</tr>
				<tr>
	          <td align='right'  class="TDstyle02">成员类别：</td>
	          <td><select id="memberType" name="memberType" onchange="Click_memberType(this.value);" >
	          		<option value=""></option>
	          		<option value='0'>机构</option>
	          		<option value='1'>用户</option>
	          		<option value='2'>角色</option>
	          		<option value='3'>审批角色</option>
	          	  </select>
	          </td>
	        </tr>			
				
			</table>
			 <table border="0" cellpadding="0" cellspacing="0" align="center">
	   		<tr>
	          <td width="30%" align="center">
	          <select  multiple="multiple"  id="select1"  size='20' style="width:150;height:200"/>
	          </td>
	          <td width="30%" align="center">
                 <input type="button" id='addone' onclick="addOne()" value="添    加" class="button_form"></input><br><br>
		         <input type="button" id='deleteone' onclick="deleteOne()" value="删    除" class="button_form"></input><br><br>
		         <input type="button" id='addall' onclick="javascript:addAll()" value="添加全部" class="button_form"></input><br><br>
		         <input type="button" id='deleteall' onclick="javascript:deleteAll()" value="删除全部" class="button_form"></input>
              </td>
	          <td width="40%" align="center"> 
	          <select style="width:120;height:200" multiple="multiple"  id="members"  name="members" size='20'/>
	          </td>
	        </tr>
	        <tr>
	        <td></td>
			<td align="center">
				<input type="button" value="提交" onclick="mysubmit()" class="button_form"/> 
			    <input type="reset" value="重置"  class="button_form"/> 
				<input type="button" value="返回" onclick="window.location='${webPath}/wkfApprovalRole/findByPageForGroup'"  class="button_form"/>
			</td>
			</tr>	        
	   </table>		
	</form>
</div>
<script language="javascript" type="text/javascript">
<%
if("update".equals(saveFlag))
{%>
	document.all("wkfRoleNo").readOnly=true;
<%
}
if(null!=wkfApprovalUserList&&wkfApprovalUserList.size()>0)
{
	WkfApprovalUser wkfApprovalUser=null;
	TblOrgUser tblOrgUserT=null;
	for(int m=0;m<wkfApprovalUserList.size();m++)
	{
		wkfApprovalUser=wkfApprovalUserList.get(m);
		System.out.println(wkfApprovalUser.getWkfUserName());
		String un=wkfApprovalUser.getWkfUserName();
		tblOrgUserT= tblOrgUserBo.getByUsername(wkfApprovalUser.getWkfUserName());
		if(null!=tblOrgUserT)
		{%>
		var members=document.all("members");
		var opt=document.createElement("option");
		members.options.add(opt);
		opt.text="<%=tblOrgUserT.getDisplayname()%> [<%=tblOrgUserT.getUsername()%>]";
		opt.value="<%=tblOrgUserT.getUsername()%>";
		opt.value+=":"+1;
		opt.selected=true;
		<%
		}
	}
}
%>
function mysubmit()
{    
	var val=document.all("wkfRoleNo").value;
	val=val.replace(/^\s*|\s*$/g, "");
	if(null==val||""==val)
	{
		alert("审批角色编号不能为空");
		return false;
	}
	var valName=document.all("wkfRoleName").value;
	valName=valName.replace(/^\s*|\s*$/g, "");
	if(null==valName||""==valName)
	{
		alert("审批角色名称不能为空");
		return false;
	}
	<%
	if("insert".equals(saveFlag))
	{
		%>
		workflowDwr.findWkfApprovalRole(val,function(data)
		{
			if(data==1)
			{
				alert("当前审批编号已存在");
				return false;
			}
			else
			{
				var members=document.all("members");
			    for(var i=0;i<members.length;i++)
			    {
			        members.options[i].selected = true;
			    }
			    document.form.submit();
			}
		});
		<%
	}
	else
	{
		%>
		var members=document.all("members");
	    for(var i=0;i<members.length;i++)
	    {
	        members.options[i].selected = true;
	    }
	    document.form.submit();
		<%
	}
	%>
	
} 
function Click_memberType(type)
{
   	var select1=document.all("select1");
   	while(select1.options.length>0)
   	{
   		select1.options.remove(0);
    }
   	if (type==0)
   	{
   		<%
   		TblOrgDepartmentsBo tblOrgDepartmentsBo = (TblOrgDepartmentsBo) SourceTemplate.getSpringContextInstance().getBean("tblOrgDepartmentsBo");
   	    List<TblOrgDepartments> tblOrgDepartmentsList=tblOrgDepartmentsBo.getAllList(null);
   	    System.out.println("tblOrgDepartmentsList:"+tblOrgDepartmentsList.size());
   	    TblOrgDepartments tblOrgDepartments;
   		for(int i=0;i<tblOrgDepartmentsList.size();i++)
   		{  
   			tblOrgDepartments=tblOrgDepartmentsList.get(i);
	     	%>
	     	 var oTextNode = document.createElement("option");
	     	 select1.options.add(oTextNode);
	     	 oTextNode.innerText="<%=tblOrgDepartments.getDisplayname()%> [<%=tblOrgDepartments.getBrNo()%>]";
	     	 oTextNode.value="<%=tblOrgDepartments.getBrNo()%>";
	         oTextNode.value+=":"+type;
	     <%
	     }
   		%>  
   	}
   	else if(type==1)
   	{
   		<%
   	    List<TblOrgUser> tblOrgUserList=tblOrgUserBo.getAllList(null);
   	    System.out.println("tblOrgUserList:"+tblOrgUserList.size());
   	    TblOrgUser tblOrgUser;
   		for(int i=0;i<tblOrgUserList.size();i++)
   		{  
   			tblOrgUser=tblOrgUserList.get(i);
	     	%>
	     	 var oTextNode = document.createElement("option");
	     	 select1.options.add(oTextNode);
	     	 oTextNode.innerText="<%=tblOrgUser.getDisplayname()%> [<%=tblOrgUser.getUsername()%>]";
	     	 oTextNode.value="<%=tblOrgUser.getUsername()%>";
	     	 oTextNode.value+=":"+type;
	     <%
	     }
   		%>
   	}
   	else if(type==2)
   	{
   		<%
   		SysRoleBo sysRoleBo = (SysRoleBo) SourceTemplate.getSpringContextInstance().getBean("sysRoleBo");
   	    List<SysRole> sysRoleList=sysRoleBo.getAllList(null);
   	    System.out.println("sysRoleList:"+sysRoleList.size());
   	 	SysRole sysRole;
   	    for(int i=0;i<sysRoleList.size();i++)
   	    {  
   	    	sysRole=sysRoleList.get(i);
	     	%>
	     	 var oTextNode = document.createElement("option");
	     	 select1.options.add(oTextNode);
	     	 oTextNode.innerText="<%=sysRole.getRoleName()%> [<%=sysRole.getRoleNo()%>]";
	     	 oTextNode.value="<%=sysRole.getRoleNo()%>";
	     	 oTextNode.value+=":"+type;
	     <%
	     }
	     	%>

   	}
   	else if(type==3)
   	{
   		<%
   	    List<WkfApprovalRole> wkfApprovalRoleList=wkfApprovalRoleBo.getAllList(null);
   	    System.out.println("wkfApprovalRoleList:"+wkfApprovalRoleList.size());
   	    WkfApprovalRole wkfApprovalRoleT;
   	    for(int i=0;i<wkfApprovalRoleList.size();i++)
   	    {  
   	    	wkfApprovalRoleT=wkfApprovalRoleList.get(i);
	     	%>
	     	 var oTextNode = document.createElement("option");
	     	 select1.options.add(oTextNode);
	     	 oTextNode.innerText="<%=wkfApprovalRoleT.getWkfRoleName()%> [<%=wkfApprovalRoleT.getWkfRoleNo()%>]";
	     	 oTextNode.value="<%=wkfApprovalRoleT.getWkfRoleNo()%>";
	     	 oTextNode.value+=":"+type;
	     <%
	     }
	     %>
   	}
}

function addOne(){ 
   	var select1=document.all("select1");
   	var members=document.all("members");
   	for(var i=select1.options.length-1;i>=0;i--)
   	{
   		if (select1.options[i].selected)
   		{  
   			var opt=document.createElement("option");
   			members.options.add(opt);
   			opt.text=select1.options[i].text;
			opt.value=select1.options[i].value;
			opt.selected=true;
			select1.options.remove(i);
   		}
   	} 
}
function addAll(){ 
   	var select1=document.all("select1");
   	var members=document.all("members");
   	for(var i=select1.options.length-1;i>=0;i--)
   	{
			var opt=document.createElement("option");
			members.options.add(opt);
			opt.text=select1.options[i].text;
		opt.value=select1.options[i].value;
		opt.selected=true;
		select1.options.remove(i); 
   	} 
}
function deleteOne(){
	var select1=document.all("select1");
   	var members=document.all("members");
   	for(var i=members.options.length-1;i>=0;i--)
   	{
   		if (members.options[i].selected)
   		{  
   			var opt=document.createElement("option");
   			select1.options.add(opt);
   			opt.text=members.options[i].text;
			opt.value=members.options[i].value;
			members.options.remove(i);
   		}
   	} 
}
function deleteAll(){ 
   	var select1=document.all("select1");
   	var members=document.all("members");
   	for(var i=members.options.length-1;i>=0;i--)
   	{
			var opt=document.createElement("option");
			select1.options.add(opt);
			opt.text=members.options[i].text;
		opt.value=members.options[i].value;
		members.options.remove(i); 
   	} 
}
</script>
	</body>
</html>