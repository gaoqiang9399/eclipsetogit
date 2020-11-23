<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="/include/tld.jsp"%>
<html>
	<head>
		<title>我的日程管理列表</title>
	</head>
	<body class="body_bg">
		<form method="post" theme="simple" name="cms_form"
			action="${pageContext.request.webPath}/workCalendar/findByPage">
			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<table width="100%" align="center" class="searchstyle">
								<tr>
									<td>
										<dhcc:searchTag property="formhom2001" mode="query" />
									</td>
									<td>
										<div class="tools_372">
											<dhcc:button value="查询" action="查询" commit="true"
												typeclass="btn_80"></dhcc:button>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<p class="p_blank">
				&nbsp;
			</p>

			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<div style="vertical-align: bottom;" class="tabCont">
								<div style="float: left" class="tabTitle">
									日程管理信息列表
								</div>
							</div>
							<dhcc:tableTag paginate="calendarList" property="tablehom2001" head="true" />
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>