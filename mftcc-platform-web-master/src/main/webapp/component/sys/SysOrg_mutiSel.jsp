<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base target="_self">
<title>通过角色选择</title>
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/util.js'></script>
<script type='text/javascript'
	src='${webPath}/dwr/interface/SysOrgDwr.js'></script>
<script src="${webPath}/component/sys/js/sysOrg_mutiSel.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="${webPath}/component/sys/css/sysOrg.css" />
</head>
<body class="overflowHidden">
<dhcc:markPoint markPointName="SysOrg_mutiSel"/>
	<script type="text/javascript">
		var recNo = '${recNo}';
		var ajaxData = '${ajaxData}';
		ajaxData = eval("(" + ajaxData + ")");
		window.onload=function(){ 
			$(".sys-org-tree").height(window.innerHeight);
			$("a.level0").click();
		};
		
		function submitAllCheckBox(){
			var table = $("#sysUserListByNo table").get(0);
			var val = "";
			var opNo = "";
			var opName = "";
			
			var len = $(table).find('input[type=checkbox]:checked').length;
			
			$(table).find('input[type=checkbox]:checked').each(function(i){
				if(i == len-1){
					val = this.value.split('&') [1].split('=')[1];
					opName = opName + val ;				
					val = this.value.split('&') [0].split('=')[1];
					opNo = opNo + val;
				}else{
					val = this.value.split('&') [1].split('=')[1];
					opName = opName + val + "@";
					val = this.value.split('&') [0].split('=')[1];
					opNo = opNo + val + "@";
				}
			});
			
			var userInfo = new Object();
			userInfo.opNo = opNo;
			userInfo.opName = opName;
			parent.dialog.get("SysUserForRecDialog").close(userInfo);
		}
	</script>
	<div class="sys-org-body">
		<div class="btn-div" style="height: 60px;">
  				    <div class="row clearfix tabCont">
					<div class="col-md-12 column">
						<div class="search-div">
							<div class="col-xs-9 column">
								
								<button type="button" class="btn btn-primary" onclick="submitAllCheckBox();">提交</button>
  				    			<!--
  				    			<button type="button" class="btn btn-primary" onclick="addSysUser();">新增员工</button>
  				    			-->
							</div>
							<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=用户名称/登录账户"/>
						</div>
					</div>
				</div>
		</div>
		<div class="sys-org-info" style="position: absolute;" >
			<ul id="sysOrgTree" class="ztree"></ul>
		</div>
		<div class="sys-org-info" style="position: absolute;margin-left: 238px;">
			<div class="container" id = "sysUserListByNo">
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div id="sysUserList" class="table_content"  style="height: auto;">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input id="brNo_hide" style="display: none;"/>
	<!-- <div id="rMenu">
	<button type="button" class="btn btn-primary pull-left" onclick="addSysOrg();">新增部门</button>
	<button type="button" class="btn btn-primary pull-left" style="margin-left: 16;" onclick="addSysUser();">新增员工</button>
	<input id="brNo_hide" style="display: none;"/>
		<ul>
			<li id="m_add" onclick="addTreeNode();">新增机构</li>
			<li id="m_del" onclick="removeTreeNode();">删除机构</li>
			<li id="m_reset" onclick="resetTree();">刷新机构</li>
		</ul>
	</div> -->
	<script type="text/javascript">
		$(function(){
			//alert($(table));
		});
	</script>
</body>
</html>
