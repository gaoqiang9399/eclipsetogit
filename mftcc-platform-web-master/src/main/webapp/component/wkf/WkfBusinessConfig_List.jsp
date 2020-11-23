<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"action="">
		
		<div>
			<div style="vertical-align: bottom; display: block;" class="tabCont">
				<strong>工作流业务信息</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<dhcc:formTag property="formwkf0010" mode="query" />
					<dhcc:button value="查询" action="查询" commit="true"
							typeclass="btn_80"></dhcc:button>
					<dhcc:button value="新增" action="WkfBusinessConfig001" 
									onclick="${webPath}/wkfBusinessConfig/input"></dhcc:button>
				</div>
			</div>
			</div>
		</div>
		<div id="content" class="table_content" style="height: auto;">
			<dhcc:tableTag paginate="wkfBusinessConfigList" property="tablewkf0010"
								head="true" />
		</div>
	</form>
</body>
</html>