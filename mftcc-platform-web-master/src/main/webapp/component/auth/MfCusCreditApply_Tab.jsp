<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ include file="/include/tld.jsp"%>
<%@ include file="/include/incTab.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详细页面</title>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" height="100%">
							<tr>
								<td>
									<div class="tab_btn_div">
										<dhcc:button typeclass="back_btn" value="返回列表" action="返回列表"
											onclick="${webPath}/mfCusCreditApply/find"></dhcc:button>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<dhcc:tabTag tabList="tabList"></dhcc:tabTag>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>