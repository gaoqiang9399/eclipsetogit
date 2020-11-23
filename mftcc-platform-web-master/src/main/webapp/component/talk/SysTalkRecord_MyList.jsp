<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>聊天记录查询</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
				<form method="post" theme="simple" name="cms_form"
					action="${webPath}/sysTalkRecord/findMyMsg">
					<table width="100%" align="center" class="searchstyle">
						<tr>
							<td>
								<dhcc:formTag property="formtalk0002" mode="query" />
							</td>
						</tr>
					</table>

					<!-- button div -->
					
					<div class="tools_372">
						<dhcc:button value="查询" action="search" commit="true"
									typeclass="btn_80"></dhcc:button>
					</div>
					</div>
					</div>
				</div>
			</div>
			<p class="p_blank">
				&nbsp;
			</p>
					
					<!-- 列表div -->
					<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<div style="vertical-align: bottom;" class="tabCont">
								<div style="float: left" class="tabTitle">
									聊天信息列表
								</div>
							</div>
							<dhcc:tableTag paginate="sysTalkRecordList" property="tabletalk0002"
								head="true" />
					</div>
				</form>
			</div>
					</div>
				</div>
			</div>

	</body>
</html>