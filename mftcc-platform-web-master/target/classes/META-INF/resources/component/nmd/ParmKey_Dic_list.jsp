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
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/nmd/js/ParmKeyDic.js"></script>
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/nmd/css/ParmKeyDic.css"/>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-main.css" />
		<style>
		.swraper.lease{ margin-left:10px;}
		</style>
    </head>
    <body class="overflow otherStyleBody">
    <dhcc:markPoint markPointName="ParmKey_Dic_list"/>
    	<div class="content">
    		<div class="row">
    			<div class="col-md-12">
    				<div class="select-border">
    					<form id="ParmKey_getListPage" action="ParmKey_getListPage.action">
	    					<div class="colmon">
	    						<label>数据字典名</label><input type="text" name="keyName"/>
	    					</div>
	    					<div class="colmon">
	    						<label>数据字典名称</label><input type="text" name="keyChnName"/>
	    					</div>
	    					<div class="colmon">
	    						<label>是否启用</label><input type="checkbox" name="ifStsFlag" value="1" checked/>
	    					</div>
	    					<div class="colmon">
	    						<input type="button" value="查询" onclick="selectParmKey(this)"/>
	    					</div>
	    					<div class="colmon right">
	    						<i class="fa fa-refresh refresh-ctrl" title="刷新数据典" onclick="refreshParmKey(this)"></i>
	    						<i class="fa fa-spinner fa-spin fa-hidden spinner-ctrl" title="正在刷新"></i>
	    					</div>
    					</form>
    				</div>
    			</div>
    		</div>
    		<div class="row row_content">
    			<div class="col-md-6 col_content">
    				<div class="bgColor"> 
    					<div class="title">
    						<h4>数据字典描述</h4>
    						<span>
    							<input type="button" value="新增" onclick="ParmKeyInput(this,'${webPath}/parmKey/insertAjax')">
    						</span>
    					</div>
    					<div class="form_content hiden">
    						<form method="post" theme="simple" name="operform" action="">
	    						<div class="form_btn">
	    							<input type="button" value="保存" onclick="ParmKeySave(this.form,'${webPath}/parmKey/updateAjax')">
	    							<input type="button" value="取消" onclick="formExit(this)">
	    						</div>
	    						<dhcc:formTag property="formnmd0001" mode="query"></dhcc:formTag>
	    					</form>
    					</div>
    					<div class="table_content parent" style="height: 600px;">
    						<dhcc:tableTag paginate="parmKeyList" property="tablenmd0001" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
    					</div>
    				</div>
    			</div>
    			<div class="col-md-6 col_content">
    				<div class="bgColor"> 
    					<div class="title">
    						<h4>数据字典项</h4>
    						<span>
    							<input type="button" value="新增" onclick="parmDicInput(this,'/parmDic/insertAjax')">
    							<input type="button" value="保存" onclick="parmDicSave(this,'/parmDic/updateAjax')">
    							<input type="button" value="取消" onclick="parmDicCancel(this)">
    						</span>
    					</div>
    					<div class="table_content child">
    						<dhcc:tableTag paginate="parmDicList" property="tablenmd0002" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
 	</body>
</html>