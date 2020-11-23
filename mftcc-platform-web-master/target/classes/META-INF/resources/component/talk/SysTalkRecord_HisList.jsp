<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>和【<%=request.getAttribute("target") %>】的聊天记录</title>

	</head>
	<body class="body_bg">
		<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
				<form method="post" theme="simple" name="cms_form"
					action="${webPath}/sysTalkRecord/findHisMsg">
					<table width="100%" align="center" class="searchstyle">
						<tr>
							<td>
								<dhcc:formTag property="formtalk0003" mode="query" />
							</td>
						</tr>
					</table>
					<!-- button div -->
					<div class="tools_372">
						<input type="submit" value="查询" class="btn_80"/>
					</div>
					</div>
					</div>
				</div>
			</div>				
					<!-- 列表div -->
			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="table_content">
							<div style="vertical-align: bottom;" class="tabCont">
								<div style="float: left" class="tabTitle">
									聊天信息列表
								</div>
							</div>
							<dhcc:tableTag paginate="sysTalkRecordList" property="tabletalk0003"
								head="true" />
					</div>
				</form>
			</div>
					</div>
				</div>
			</div>

	</body>
</html>