<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
	$(function() {
		$("body").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true,
			},
		});
	})
	
	function getHisView(obj,url){
		var hisNo = url.split("?")[1].split("&")[0].split("=")[1];
		window.top.window.showDialog(webPath+"/recallBaseHis/getHisView?hisNo="+hisNo,"催收详情",80,90);
	}
</script>
</head>
<body style="padding: 10px; height: 100%;" class="contentA cellBoxBg ">
	<h3>催收情况</h3>
	<div class="bigform_content">
		<div class="content_table">
			<dhcc:tableTag property="tablerec0003" paginate="RecallBaseHisList"
				head="true"></dhcc:tableTag>
		</div>
	</div>
</body>
</html>