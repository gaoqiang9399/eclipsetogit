<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<script type="text/javascript" >
        $(function(){
            //滚动条
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    updateOnContentResize: true
                }
            });
        });
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>科目详情信息</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#subjectList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="subjectList" name="subjectList">
					<dhcc:tableTag property="tablelastLevList" paginate="lastLevList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>

	<div class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>