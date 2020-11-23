<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	function lkconfirm(lk)
	{
		alert("请确定删除该审批角色以及该审批角色下的所有审批用户，按“取消”表示不进行此操作！",2,function(){
			location.href = lk;
		});
    	/* flag=window.confirm("请确定删除该审批角色以及该审批角色下的所有审批用户，按“取消”表示不进行此操作！");
        if(flag)
        {
  	    	location.href = lk;
        } */
    }
	</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wkfApprovalRole/findByPageForGroup">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0009" mode="query" /></td>
							</tr>
						</table>
						<div class="tools_372">
							<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<p class="p_blank">&nbsp;</p>
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle"></div>
							<dhcc:button value="新增" action="新增" typeclass="t_ico_tj"
							onclick="${webPath}/wkfApprovalRole/groupInput"></dhcc:button>
						</div>
						<dhcc:tableTag paginate="wkfApprovalRoleList" property="tablewkf0007"
								head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>