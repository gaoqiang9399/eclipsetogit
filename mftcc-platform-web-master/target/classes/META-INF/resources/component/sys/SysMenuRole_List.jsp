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
		String old_menu_no = (String)request.getAttribute("menuno_str");
		%>	
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
				<table class="tableStyle01" align="left">
					<tr>
					<td class="TDstyle02">
					<div id="menuTree" width="100%" height="100%" style="padding:0px 10px"></div>
					<input type="hidden" name="menus" id="menus" size="100">
					<input type="hidden" name="roleno" id="roleno" size="100" value="<%=roleno %>">
					</td>
					</tr>
				</table>
				</div>
				<input type="hidden" name="roleNo" value="${roleNo}">
			
			</form>
		</div>
		</div>
		</div>
	</body>
		<script type="text/javascript" src="${webPath}/creditapp/js/tree/common-min.js"></script>
		<script type="text/javascript" src="${webPath}/creditapp/js/tree/TreePanel.js"></script>
	<script type="text/javascript">
		var root=<%=trees%>;
		tree = new TreePanel({
				'root' : root,
				'renderTo':'menuTree'
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
			var roleno=document.getElementById("roleno").value;
			//DWREngine.setAsync(false);
			dwr.engine.setAsync(false);
			var logo;
			sysrolemenuDwr.saveAndUpdate(menus,roleno,function(data) {
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
		function isCheckedMenu(menu_no) {
			var length = menuno_data.length;
			for( var i = 0 ; i < length; i++) {
				if(menu_no == menuno_data[i]) return true;
			}
			return false;
		}
		
		//原来选择的菜单
		var str_menuno = "[" + "<%=old_menu_no%>" + "]";
		var menuno_data = eval(str_menuno);
		initiate();
	</script>
</html>