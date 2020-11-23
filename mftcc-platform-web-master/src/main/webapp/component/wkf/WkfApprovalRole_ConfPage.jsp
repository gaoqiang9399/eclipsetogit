<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="s" uri="/struts-tags"%> --%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>

        <!--弹窗选择公用js  -->
        <script type="text/javascript" src="${webPath }/themes/factor/js/selectInfo.js"></script>
        <script type="text/javascript" src="${webPath }/component/include/tablelistshowdiv.js"></script>
        <!--artdialog插件  -->
        <script type="text/javascript" src="${webPath }/UIplug/artDialog/dist/dialog-plus-min.js"></script>
        <link id="ui-dialog" rel="stylesheet" href="${webPath }/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
        <!-- 引入自定义的弹窗扩展 -->
        <script type="text/javascript" src="${webPath }/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>
        <script src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"
                type="text/javascript"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/wkf/js/wkfApprovalRole.js"></script> 
		<script type="text/javascript" src="${webPath}/component/include/jquery.autocompleter.js"></script> 
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/wkf/css/wkfApprovalRole.css"/>
		<link rel="stylesheet" href="${webPath}/component/include/autocompleter.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<style type="text/css">
		.table_content.parent .ls_list tbody tr.selected{
			background-color:#e0e0e0;
			}
		</style>
		<script type="text/javascript">
			 var webPath = "${webPath}" || "";
		</script>
    </head>
    <body class="overflow otherStyleBody">
    <dhcc:markPoint markPointName="WkfApprovalRole_ConfPage"/>
    	<div class="content">
    		<div class="row">
    			<div class="col-md-12">
    				<div class="select-border">
    					<form id="WkfApprovalRole_ConfPage" action="">
	    					<div class="colmon">
	    						<label>角色名称</label><input type="text" name="wkfRoleName"/>
	    					</div>
	    					
	    					<div class="colmon">
	    						<input type="button" value="查询" onclick="selectWkfApprovalRole(this)"/>
	    					</div>
	    					
    					</form>
    				</div>
    			</div>
    		</div>
    		<div class="row row_content">
    			<div class="col-md-6 col_content" style="height:calc(100% - 117px)">
    				<div class="bgColor" style="height:100%"> 
    					<div class="title">
    						<h4>审批角色</h4>
    						<span>
    							<input type="button" value="新增" onclick="wkfApprovalRoleInput(this,'${webPath}/wkfApprovalRole/insertAjax')">
    						</span>
    					</div>
    					<div class="form_content hiden">
    						<form method="post" theme="simple" name="operform" action="">
	    						<div class="form_btn">
	    							<input type="button" value="保存" onclick="wkfApprovalRoleSave(this.form,'${webPath}/wkfApprovalRole/updateAjax')">
	    							<input type="button" value="取消" onclick="formExit(this)">
	    						</div>
	    						<dhcc:formTag property="formwkf0001" mode="query"></dhcc:formTag>
	    					</form>
    					</div>
    					<div class="table_content parent">
    						<dhcc:tableTag paginate="wkfApprovalRoleList" property="tablewkf0001" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
    					</div>
    				</div>
    			</div>
    			<div class="col-md-6 col_content" style="height:calc(100% - 117px)">
    				<div class="bgColor" style="height:100%"> 
    					<div class="title">
    						<h4>审批用户</h4>
    						<span >
    							<input type="button" value="新增" onclick="wkfApprovalUserInput(this,'${webPath}/wkfApprovalUser/insertAjax')">
    						</span>
    					</div>
    					<div class="form_content child hiden">
    						<form method="post" theme="simple" name="childoperform" action="">
	    						<div class="form_btn">
	    							<input type="button" value="保存" onclick="wkfApprovalUserSave(this.form,'${webPath}/wkfApprovalUser/updateAjax')">
	    							<input type="button" value="取消" onclick="formExit(this)">
	    						</div>
	    						<dhcc:formTag property="formwkf0043" mode="query"></dhcc:formTag>
	    					</form>
    					</div>
    					<div class="table_content child">
    						<dhcc:tableTag paginate="wkfApprovalUserList" property="tablewkf0015" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
 	</body>
</html>