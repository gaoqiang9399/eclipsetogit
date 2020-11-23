<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
<body>
   <div class="bigform_content">
   		<h3>列表表单</h3>
		<div class="content_table">
			<div class="input_btn">
				<dhcc:button value="新增" action="新增" onclick="ajaxInput(this,'${webPath}/demo3/insertAjax');"></dhcc:button>
			</div>
			<dhcc:tableTag property="tabledemo0004" paginate="demoList" head="true"></dhcc:tableTag>
			<div style="display: none;" class="content_form">
				<form  method="post" theme="simple" name="operform" action="">
					<div class="content_Btn">
						<dhcc:button value="删除" action="删除" onclick="ajaxDelete(this.form,'${webPath}/demo3/deleteAjax')"></dhcc:button>
						<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form,'${webPath}/demo3/updateAjax')"></dhcc:button>
						<dhcc:button value="关闭" action="关闭" onclick="colseBtn(this)"></dhcc:button>
					</div>
					<dhcc:formTag property="formdemo0007" mode="query"></dhcc:formTag>
			    </form>
			</div>
		</div>
    </div>
</body>
</html>