<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="/include/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>�ҵ��ճ̹����б�</title>
	</head>
	<body class="body_bg">
		<form method="post" theme="simple" name="cms_form"
			action="${pageContext.request.webPath}/workCalendar/warnlist">
			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<table width="100%" align="center" class="searchstyle">
								<tr>
									<td>
										<dhcc:searchTag property="formhom2004" mode="query" />
									</td>
									<td>
										<div class="tools_372">
											<dhcc:button value="��ѯ" action="��ѯ" commit="true"
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
							<div class="tabCont">
								<div class="tabTitle">�ճ�������Ϣ�б�</div>
							</div>
							<dhcc:tableTag paginate="list" property="tablehom2002" head="true" />
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>