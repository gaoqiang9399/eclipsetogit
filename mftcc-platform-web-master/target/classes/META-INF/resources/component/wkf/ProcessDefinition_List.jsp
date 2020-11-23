<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	function openProcessDesigner(id)
	{
		id=id.split("?")[1];
		window.open("workflow/WFDLDesigner.jsp?command=DesignProcess&"+id);
		
	}
	</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/processDefinition/findByPage">
		<!-- <div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0013" mode="query" /></td>
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
							<div style="float:left" class="tabTitle">流程定义列表</div>
						</div>
						<dhcc:tableTag paginate="pocessDefinitionList" property="tablewkf0021" head="true" />
					</div>
				</div>
			</div>
		</div> -->
		<div>
			<div style="vertical-align: bottom; display: block;" class="tabCont">
				<strong>流程定义列表</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<dhcc:formTag property="formwkf0013" mode="query" />
					<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
					
				</div>
			</div>
			</div>
		</div>
		<div id="content" class="table_content" style="height: auto;">
			<dhcc:tableTag paginate="pocessDefinitionList" property="tablewkf0021" head="true" />
		</div>
	</form>
</body>
</html>