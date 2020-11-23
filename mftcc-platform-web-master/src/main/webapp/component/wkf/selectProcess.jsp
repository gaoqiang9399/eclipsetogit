<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<html>
	<head>
		<base target="_self">
		<title>选择流程</title>
		<script type="text/javascript">
			function cancelClick() {
				window.close();
			}
			
			function enterClick(obj) {
				var selectedValue=obj.split("=")[1];
				window.returnValue = selectedValue;
				window.close();
				return;
			}
			window.name="processwindow";
		</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle"></div>
						</div>
						<dhcc:tableTag paginate="pocessDefinitionList" property="tablewkf0015" head="true" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
