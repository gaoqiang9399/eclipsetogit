<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
			function iframeInput(obj,url){
				$(obj).parents(".content_table").find(".content_ifream").show();
				$(obj).parents(".content_table").find(".content_ifream iframe").attr("src",url);
			}
		</script>
	</head>
<body>
   <div class="bigform_content">
   		<h3>列表表单</h3>
		<div class="content_table">
			<div class="input_btn">
				<dhcc:button value="新增" action="新增" onclick="iframeInput(this,'${webPath}/demo3/insertAjax');"></dhcc:button>
			</div>
			<dhcc:tableTag property="tabledemo0004" paginate="demoList" head="true"></dhcc:tableTag>
			<div style="display: none;" class="content_ifream">
				<iframe  class="inneriframe" src="" width="100%" border="0"></iframe>
			</div>
		</div>
    </div>
</body>
</html>