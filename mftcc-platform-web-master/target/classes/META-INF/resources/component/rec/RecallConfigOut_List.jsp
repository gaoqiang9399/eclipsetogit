<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" >
			function insertAjax(obj){
				ajaxInput(obj,webPath+"/recallConfigOut/insertAjax?outNo=${outNo}");
			}
		</script>
	</head>
<body>
   <div class="bigform_content">
   		<h3>列表表单</h3>
		<div class="content_table">
			<div class="input_btn">
				<dhcc:thirdButton value="新增" action="新增" onclick="insertAjax(this);"></dhcc:thirdButton>
			</div>
			<dhcc:tableTag property="tabledlrecallconfigout0001" paginate="RecallConfigOutList" head="true"></dhcc:tableTag>
			<div style="display: none;" class="content_form">
				<form method="post" theme="simple" name="operform" action="">
					<div class="content_Btn">
						<dhcc:thirdButton value="删除" action="删除" onclick="ajaxFormDelete(this.form,'${webPath}/recallConfigOut/deleteAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave(this.form,'${webPath}/recallConfigOut/updateAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton value="关闭" action="关闭" onclick="colseBtn(this)"></dhcc:thirdButton>
					</div>
					<dhcc:formTag property="formdlrecallconfigout0002" mode="query"></dhcc:formTag>
			    </form>
			</div>
		</div>
    </div>
</body>
</html>