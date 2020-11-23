<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../include/pub.jsp"%>
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
		String roleno=(String)request.getParameter("roleNo");
		%>
		function selectAllBtn(){
			$("input:checkbox").each(function(){
				$(this).attr("checked",true);
			});
		}
		function unSelectBtn(){
			$("input:checkbox").each(function(){
				if($(this).attr("checked") || $(this).attr("checked")=="checked"){
					$(this).attr("checked",false);
				}else {
					$(this).attr("checked",true);
				}
			});
		}
</script>
	<body leftmargin="0" topmargin="0">
	<div class="right_bg">
		<div class="right_w">
		<div id="man_zone">
			<form method="post" theme="simple" validate="true" name="cms_form" action="${webPath}/tblOrgJobusers/saveAndUpdate">
			<div class="from_btn" style="text-align: left;">
				<span><input type="button" name="btn4" value="展开" onclick="javascript:expandAll();" class="btn_80" /></span>
				<span><input type="button" name="btn4" value="折叠" onclick="javascript:collapseAll();" class="btn_80" /></span>
				<span><input type="button" name="btn4" value="保存" onclick="func_save();" class="btn_80" /></span>
				<span><input type="button" name="btn4" value="返回" onclick="javascript:window.location='${webPath}/tblOrgJobusers/listByPage'" class="btn_80" /></span>
			</div>
			
			<div>
				<table class="tableStyle01" align="left"  width="100%" border= "1 "   cellpadding= "0 "   cellspacing= "0 "   style= "border-collapse:   collapse ">
				
					<tr>
					<td valign="top" class="TDstyle02" width="50%">
					<div id="menuTree" width="100%" height="100%" style="padding:0px 10px"></div>
					<input type="hidden" name="menuno" id="menuno" size="100">
					<input type="hidden" name="roleno" id="roleno" size="100" value="<%=roleno %>">
					</td>
					<td valign="top" class="TDstyle02" width="50%">
						<div id="menutable" style="float:center;">
						<center>配置角色可见按钮</center>
						<table id="tab" width="100%" border="0" align="center" cellspacing=1
			class="ls_list">
			<thead>
			<tr>
				<th valign="top" width="15%" align="center" class="first">选择</th>
				<th valign="top" width="15%" align="center">编号</th>
				<th valign="top" width="70%" align="center" class="last">按钮功能</th>
			</tr>
			</thead>
						</table>
						</div>
					</td>
					</tr>
				</table>
			</div>
			<input type="hidden" name="role_no" value="${role_no }" />
			</form>
		</div>
		</div>
		</div>
		<form name="operform" action="#" method="post">
    	</form>
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
		      if(count==0){
		        alert("至少选择一项!");
		        return false;
		      } 
			}
			var roleno=document.all("roleno").value;
			var menuno=document.all("menuno").value;
			//DWREngine.setAsync(false);
			dwr.engine.setAsync(false); 
			var sts;
			sysrolemenuDwr.saveButton(roleno,menuno,boxStr,function(data) {
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
						var tablehead = "";
		       			tablehead += "<thead>";
		       			tablehead += "<tr>";
		       			tablehead += "<th class='first' valign='top' width='15%' align='center'>选择</th>";
		       			tablehead += "<th valign='top' width='15%' align='center'>编号</th>";
		       			tablehead += "<th class='last' valign='top' width='70%' align='center'>按钮功能</th>";
		       			tablehead += "</tr>";
		       			
		       			$("#menutable").find("table").html(tablehead);
			       		if(buttonlist==""){
			       			//document.getElementById("menuAdd").style.display="none";
			       			
			       			return false;
			       		}
				       //	var menuDiv = document.getElementById("menuAdd");
				       	//menuDiv.style.display="block";
				       	var str = "<tbody id='tab'>";
				       //	str += "<div class='table_w'><table  id='tab' width='100%' border='1' align='center' cellspacing=0 width='100%'  style= 'border-collapse:   collapse'>";	
				       	for(var i=0;i<buttonlist.length;i++){
				       		if(i%2==1){
				       			str += "<tr class='t2'>";
				       		}else {
				       			str += "<tr class='t1'>";
				       		}
				       		str += "<td align='center' width='15%' align='center'>";
				       		if(isCheckedButtonNo(menuno,buttonlist[i].button_no)) {
								str += "<input type='checkbox' name='checkboxList' value='"+buttonlist[i].button_no+"' checked='true'>";//checkbox选中
							} else {
								str += "<input type='checkbox' name='checkboxList' value='"+buttonlist[i].button_no+"'>";//checkbox不选中
							}
				       		//str += "<input type='checkbox' name='checkboxList' value='"+buttonlist[i].button_no+"'>";
				       		str += "</td>";
				       		str += "<td  align='center' width='15%' align='center'>";
				       		str += buttonlist[i].button_no;
				       		str += "</td>";
				       		str += "<td  align='center' width='70%' align='center'>";
				       		str += buttonlist[i].button_desc;
				       		str += "</td>";
				       		str += "</tr>";
				       	}
				       	str += "<tr>";
				       	str += "<td  align='center' colspan='3' align='center'>";
				       	str += "<div class='from_btn'><input type='button' value='全选' onclick='selectAllBtn();' class='button3'/>&nbsp;&nbsp;<input type='button' value='反选' onclick='unSelectBtn();' class='button3'/>&nbsp;&nbsp;<input type='button' name='btn4' value='保存' onclick='func_save()' class='button3'/></div>"
				       	str += "</td>";
				       	str += "</tr>";
				       	str += "</tbody>";
				      // 	str += "</table>";
				      $("#menutable").find("table").append(str);
				       //	menuDiv.innerHTML = str;
				       	
				       	
			       		
					}else{
						return;
					}
				}else{
					alert('没有选中节点')
				}
			}
	function isCheckedButtonNo(menuno,buttonno) {
		//DWREngine.setAsync(false);
		dwr.engine.setAsync(false);
			var rolebuttonlist;
			var roleno=document.all("roleno").value;
			sysrolemenuDwr.getButtonList(roleno,menuno,function(data) {
				if( data == null) {
					return;
				}else {
					rolebuttonlist=data;
				}
			});
			var length = rolebuttonlist.length;
			for( var i = 0 ; i < length; i++) {
				if(buttonno == rolebuttonlist[i].name3) return true;
			}
			return false;
		}
	</script>
</html>