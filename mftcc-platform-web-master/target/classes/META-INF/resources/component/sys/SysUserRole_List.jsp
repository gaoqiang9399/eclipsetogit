<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../include/pub.jsp"%>
<%
	String path=request.getContextPath();
%>
<html>
	<head>
		<title>操作权限列表</title>
		<meta http-equiv="Pragma" content="no-cache"> 
		<meta http-equiv="Cache-Control" content="no-cache">   
		<meta http-equiv="Expires" content="0"> 
	</head>
<script type='text/javascript' src='${webPath}/dwr/interface/sysrolemenuDwr.js'></script>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type="text/javascript">
		<%
		String trees=(String)request.getAttribute("menuStr");
		String roleno=(String)request.getParameter("userid");
		String old_menu_no = (String)request.getAttribute("menuno_str");
		%>	
</script>
	<body leftmargin="0" topmargin="0">
	<div class="right_bg">
			<div class="right_w">
		<div id="">
			<form method="post" theme="simple" validate="true" name="cms_form" action="${webPath}/tblOrgJobusers/saveAndUpdate">
			<div>
				<span><input type="button" name="btn4" value="展开" onclick="javascript:expandAll();" class="btn_80" /></span>
				<span><input type="button" name="btn4" value="折叠" onclick="javascript:collapseAll();" class="btn_80" /></span>
				<span><input type="button" name="btn4" value="保存" onclick="func_save();" class="btn_80" /></span>
				<span><input type="button" name="btn4" value="返回" onclick="javascript:window.location='${webPath}/workflow/WorkFlowManagement/listUsersByPage';" class="btn_80" /></span>
			</div>
			
			<div>
				<table class="tableStyle01" align="left" width="100%" border= "1"   cellpadding= "0 "   cellspacing= "0 "   style= "border-collapse:   collapse ">
					<tr>
					<td class="TDstyle02">
					<div id="menuTree" width="100%" height="100%" style="padding:0px 10px;"></div>
					<input type="hidden" name="menus" id="menus" size="100"/>
					<input type="hidden" name="roleno" id="roleno" size="100" value="<%=roleno %>"/>
					<input type="hidden" name="menuno" id="menuno" size="100" />
					</td>
					<td valign="top" class="TDstyle02" width="50%">
						<div style="float:center;">
						<center>配置角色可见按钮</center>
						<table class="tableStyle01" align=center width="100%" cellpadding= "0 "   cellspacing= "1" >
						<tr>
							<td valign="top" class="bartop" width='25%' align='center'>选择</td>
							<td valign="top" class="bartop" width='75%' align='center'>按钮功能</td>
						</tr>
						</table>
						</div>
						<div id="menuAdd" style="display:none;float:center;">
						</div>
					</td>
					</tr>
				</table>
				</div>
				<input type="hidden" name="role_no" value="${role_no}"/>
			</form>
		</div>
	</body>
		<script type="text/javascript" src="${webPath}/creditapp/js/tree/common-min.js"></script>
		<script type="text/javascript" src="${webPath}/creditapp/js/tree/TreePanel.js"></script>
	<script type="text/javascript">
		var root=<%=trees%>;
		tree = new TreePanel({
				'root' : root,
				'renderTo':'menuTree',
				'handler':seeFocusNode
			});
		tree.render();
		
		function getMenus(){
			var menu='';
			for(var k in tree.nodeHash){
				var node = tree.nodeHash[k];
				if(node.checked==1||node.checked==2){
					var value = node.attributes['id'];
					if(value != null){
						menu+=value+",";
					}
				}
			}
			document.getElementById("menus").value=menu;
		}
	
		function func_save(){
			getMenus();
			var menus=document.all("menus").value;
			var roleno=document.all("roleno").value;
			//DWREngine.setAsync(false);
			dwr.engine.setAsync(false);
			var logo;
			sysrolemenuDwr.UsersaveAndUpdate(menus,roleno,function(data) {
		       	if( data == null) {
		          	return;
		       	} else {
					logo=data;
	       		}
       		});
       		if(logo=="1") {
	       		alert(top.getMessage("SUCCEED_SAVE"));	
	       	}else{
	       		alert(top.getMessage("FAILED_OPERATION"," "));
	       	}
		}
		
		function initiate(){
			var menu='';
			for(var k in tree.nodeHash){
				var node = tree.nodeHash[k];
				if(isCheckedMenu(node.id)) {
					node.setCheck(1);//节点的checkbox选中
				} else {
					node.setCheck(0);//节点的checkbox不选中
				}
			}
		}
		function expandAll(){
			tree.expandAll();
		}
		function collapseAll(){
			tree.collapseAll();
		}
		function seeFocusNode(node){
				if(node!=null){
				if(node.attributes.lvl=='3'){
				var menuno=node.attributes.id;
						document.all('menuno').value=menuno;
						//DWREngine.setAsync(false);
						dwr.engine.setAsync(false);
						var buttonlist;
						sysrolemenuDwr.getMenuButton(menuno,function(data) {
					       	if( data == null) {
					          	return;
					       	}else {
								buttonlist=data;
				       		}
			       		});
			       		if(buttonlist==""){
			       			document.getElementById("menuAdd").style.display="none";
			       			return false;
			       		}
				       	var menuDiv = document.getElementById("menuAdd");
				       	menuDiv.style.display="block";
				       	var str = "";
				       	str += "<div class='table_w'><table border='1' id='tab' cellpadding='0' cellspacing='0' style='border-collapse:collapse' width='100%'>";	
				       	for(var i=0;i<buttonlist.length;i++){
				       		str += "<tbody id='tab'><tr>";
				       		str += "<td  align='center' width='25%' align='center'>";
				       		if(isCheckedButtonNo(menuno,buttonlist[i].button_no)) {
								str += "<input type='checkbox' name='checkboxList' value='"+buttonlist[i].button_no+"' checked='true'>";//checkbox选中
							} else {
								str += "<input type='checkbox' name='checkboxList' value='"+buttonlist[i].button_no+"'>";//checkbox不选中
							}
				       		//str += "<input type='checkbox' name='checkboxList' value='"+buttonlist[i].button_no+"'>";
				       		str += "</td>";
				       		str += "<td  align='center' width='75%' align='center'>";
				       		str += buttonlist[i].button_desc;
				       		str += "</td>";
				       		str += "</tr>";
				       	}
				       	str += "<tr>";
				       	str += "<td  align='center' colspan='2' align='center'>";
				       	str += "<input type='button' name='btn4' value='保存' onclick='func_button_save()' class='button_form'/>"
				       	str += "</td>";
				       	str += "</tr>";
				       	str += "</table>";
				       	menuDiv.innerHTML = str;
				       	
				       	
			       		
					}else{
					alert("请选择最底层根节点进行用户按钮配置！")
						return;
					}
				}else{
					alert('没有选中节点')
				}
			}
			function isCheckedButtonNo(menuno,buttonno) {
			//DWREngine.setAsync(false);
			dwr.engine.setAsync(false);
			var userbuttonlist;
			var roleno=document.all("roleno").value;
			sysrolemenuDwr.getUserButtonList(roleno,menuno,function(data) {
				if( data == null) {
					return;
				}else {
					userbuttonlist=data;
				}
			});
			var length = userbuttonlist.length
			for( var i = 0 ; i < length; i++) {
			if(buttonno == userbuttonlist[i].button_no) return true;
           }
			return false;
		}
		//菜单对应按钮设置
		function func_button_save(){
		var element=document.getElementsByName("checkboxList");
		   var boxStr="";
		   var count=0;
		   if(element.length>0){
		      for(var i=0;i<element.length;i++){
		         if(element[i].checked==true){  
		            if(boxStr==""){
		              boxStr=element[i].value;
		              count++;
		            }else{
		               boxStr=boxStr+"@"+element[i].value;  
		              count++;  
		            }                           
		         }                        
		      }
		    }
			var roleno=document.all("roleno").value;
			var menuno=document.all("menuno").value;
			//DWREngine.setAsync(false);
			dwr.engine.setAsync(false);
			var sts;
			sysrolemenuDwr.UsersaveButton(roleno,menuno,boxStr,function(data) {
				if( data == null) {
					return;
				}else {
					sts=data;
				}
			});
			if(sts=="1") {
	       		alert(top.getMessage("SUCCEED_SAVE"));	
	       	}else{
	       		alert(top.getMessage("FAILED_OPERATION"," "));
	       	}
		}
		function isCheckedMenu(menu_no) {
			var length = menuno_data.length;
			for( var i = 0 ; i < length; i++) {
				if(menu_no == menuno_data[i]) return true
			}
			return false;
		}
		
		//原来选择的菜单
		var str_menuno = "[" + "<%=old_menu_no%>" + "]";
		var menuno_data = eval(str_menuno);
		
		initiate();
	</script>
</html>