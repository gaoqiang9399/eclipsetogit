<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/include/tableFour.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
	</head>
<body class="overflowHidden">
   <div class="bigform_content">
   		<h3>列表表单</h3>
		<div class="content_table">
			<div class="input_btn">
				<dhcc:button value="新增" action="新增" onclick="ajaxInputFour(this,'${webPath}/demo3/insertAjax');"></dhcc:button>
			</div>
			<div style="display: none;" class="content_form">
				<form  method="post" theme="simple" name="operform" action="">
					<div class="content_Btn">
						<dhcc:button value="删除" action="删除" onclick="ajaxDeleteFour(this.form,'${webPath}/demo3/deleteAjax')"></dhcc:button>
						<dhcc:button value="保存" action="保存" onclick="ajaxSaveFour(this.form,'${webPath}/demo3/updateAjax')"></dhcc:button>
						<dhcc:button value="关闭" action="关闭" onclick="colseBtnFour(this)"></dhcc:button>
					</div>
					<dhcc:formTag property="formdemo0007" mode="query"></dhcc:formTag>
			    </form>
			</div>
			<div class="table_show">
				<dhcc:tableTag property="tabledemo0005" paginate="demoList" head="true"></dhcc:tableTag>
			</div>
		</div>
    </div>
</body>
</html>