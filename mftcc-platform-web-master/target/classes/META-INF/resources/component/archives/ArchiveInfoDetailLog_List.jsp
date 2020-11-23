<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" >
            $(function() {
                myCustomScrollbar({
                    obj : "#content",//页面内容绑定的id
                    url : webPath+"/archiveInfoDetailLog/findByPageAjax",//列表数据查询的url
                    tableId : "tablelarchivedetailloglist",//列表数据查询的table编号
                    tableType : "tableTag",//table所需解析标签的种类
                    pageSize : 30,//加载默认行数(不填为系统默认行数)
                    topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
                });
            });
		</script>
	</head>
	<body class="overflowHidden">
   <%--<div class="bigform_content">

		<div class="content_table">
			<dhcc:tableTag property="tabledl_archive_detail_log01" paginate="ArchiveInfoDetailLogList" head="true"></dhcc:tableTag>
			<div style="display: none;" class="content_form">
				<form  method="post" theme="simple" name="operform" action="">
					<div class="content_Btn">
						<dhcc:thirdButton value="删除" action="删除" onclick="ajaxFormDelete(this.form,webPath+'/archiveInfoDetailLog/deleteAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave(this.form,webPath+'/archiveInfoDetailLog/updateAjax')"></dhcc:thirdButton>
						<dhcc:thirdButton value="关闭" action="关闭" onclick="colseBtn(this)"></dhcc:thirdButton>
					</div>
					<dhcc:formTag property="formdl_archive_detail_log02" mode="query"></dhcc:formTag>
			    </form>
			</div>
		</div>
    </div>--%>
   <div class="container">
	   <div class="row clearfix">
		   <div class="col-md-12 column">
			   <div id="content" class="table_content" style="height: auto;">
			   </div>
		   </div>
	   </div>
   </div>
   <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>