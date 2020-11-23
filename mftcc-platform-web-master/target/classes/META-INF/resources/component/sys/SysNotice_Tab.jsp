<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style type="text/css">
				*{
					margin: 0px;
					padding: 0px;
				}
				#myTab.nav-tabs{
				   height: 35px;
				   padding-top: 5px;
				   border-bottom: 1px solid #009BED;
				}
				#myTab.nav>li {
				}
				#myTab.nav>li>a {
				    position: relative;
				    display: block;
				    padding: 3px 9px;
				    border-top: 1px #ddd solid;
				    border-left: 1px #ddd solid;
				    border-right: 1px #ddd solid;
				}
				#myTab.nav-tabs>li.active>a{
					border: 1px solid #009BED;
				    border-bottom-color: transparent;
				}
				.tab-content{
				    position: absolute;
				    top: 35px;
				    bottom: 0px;
				    left: 0px;
				    right: 0px;
				}
		</style>
		<script type="text/javascript">
			$(function(){
				$("#myTab>li>a").bind("click",function(){
					$("#SysRoleIframepage").attr("src",$(this).data("url"));
				});
				$("#myTab>li>a").eq(0).click();
			});
		</script>
	</head>
	<body>
		<ul id="myTab" class="nav nav-tabs">
			<li><a data-toggle="tab" data-url="${webPath}/sysOrg/getListPageForNotice">推送子公司</a></li>
			<li><a data-toggle="tab" data-url="${webPath}/sysUser/getListPageForNotice">推送用户</a></li>
			<li><a data-toggle="tab" data-url="${webPath}/sysNotice/getListPage">通知列表</a></li>
		</ul>
		<div class="tab-content">
			<iframe id="SysRoleIframepage" src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="SysRoleIframepage"></iframe>
		</div>
	</body>
</html>