<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/include/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>�ճ��޸�</title>
		<script src="${webPath}/creditapp/js/sys_manage.js" type="text/javascript"></script>
	</head>
	<body class="body_bg">
		<form method="post" name="cms_form" action="${pageContext.request.webPath}/workCalendar/update">
			<div class="right_bg">
				<div class="right_w">
					<div class="from_bg">
						<div class="right_v">
							<dhcc:formTag property="formhom2003" mode="query" />
							<div class="from_btn">
								<c:if test="${query ne 'query' }">
									 <input type="submit" name="save" value="����" class="button_form"/>
								 </c:if>
								<c:if test="${flag eq '1' }">
									<input type="button" name="back" value="����" onClick="javascript:self.close();" class="button_form" />
								</c:if>
								<c:if test="${flag eq '2' }">
                         			<input type="button" name="back" value="����" onClick="javascript:window.location='${webPath}/workCalendar/warnlist'" class="button_form" />
								</c:if>
								<c:if test="${flag ne '2'&& flag ne '1' }">
                         			<input type="button" name="back" value="����" onClick="javascript:window.location='${webPath}/workCalendar/findByPage'" class="button_form" />
                         		</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>