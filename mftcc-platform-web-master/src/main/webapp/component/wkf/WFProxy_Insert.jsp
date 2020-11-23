<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
	
	function selectProcess() {
			var result = window.showModalDialog("<%=webPath %>/wFProxy/selectBusiness",'选择代理任务','dialogWidth:500px;dialogHeight:400px;status:no;');
			if( result != null && result != "undefined" && result != "" ) {
				document.getElementsByName("process")[0].value = result;
			}
		}
	</script>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
				<div class="right_v">
				<form method="post" theme="simple" name="operform"
					action="${webPath}/wFProxy/insert.action">
					<dhcc:formTag property="formwkf0017" mode="query" />
					<div class="from_btn">
						<dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
						<dhcc:button typeclass="button_form" value="返回" action="返回" onclick="${webPath}/wFProxy/findByPage"></dhcc:button>
					</div>
				</form>

			</div>
			</div>
			</div>
		</div>
	</body>
</html>