<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		function insertAjax(obj){
			ajaxInput(obj,"${webPath}/mfCusLegalMember/insertAjax?cusNo=${cusNo}&cusName=${cusName}");
		}
		</script>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
  		<c:if test="${flag!='insert'}">
	    <h3>列表表单</h3>
	    </c:if>
		<div class="content_table">
			<div class="input_btn">
				<dhcc:thirdButton value="新增" action="新增" onclick="insertAjax(this);"></dhcc:thirdButton>
			</div>
			<dhcc:tableTag property="tablecuslegm00002" paginate="mfCusLegalMemberList" head="true"></dhcc:tableTag>
			<div style="display: none;" class="content_form">
				<form  method="post" theme="simple" name="operform" action="">
					<div class="content_Btn">
						<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave(this.form,'${webPath}/mfCusLegalMember/updateAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton typeclass="cancel" value="删除" action="删除" onclick="ajaxFormDelete(this.form,'${webPath}/mfCusLegalMember/deleteAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton typeclass="cancel" value="关闭" action="关闭" onclick="colseBtn(this)"></dhcc:thirdButton>
					</div>
					<dhcc:formTag property="formcuslegm00001" mode="query"></dhcc:formTag>
			    </form>
			</div>
		</div>
    </div>
	</body>	
</html>
