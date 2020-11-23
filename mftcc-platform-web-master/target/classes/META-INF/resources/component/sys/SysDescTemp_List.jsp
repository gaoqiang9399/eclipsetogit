<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/sys/js/SysDescTemp.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.dev.js"></script>
<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.month.js"></script>
<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.year.js"></script>
<script type="text/javascript" src="${webPath}/component/include/laydate/laydate.timer.js"></script>
<script type="text/javascript" src="${webPath}/component/include/IndustryAndArea.js"></script>
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/sys/css/SysDescTemp.css"/>
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-main.css" />
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<!-- select3 -->
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
    </head>
    <body class="overflow otherStyleBody">
    	<div class="content">
    		<div class="row">
    			<div class="col-md-12">
    				<div class="select-border">
    					<form id="SysDescTemp_getListPage">
	    					<div class="colmon">
	    						<label>模板名称</label><input type="text" name="desctempName"/>
	    					</div>
	    					<div class="colmon">
	    						<label class="form_select_lebel">所属视角对象</label><select class="form_select" name="viewpointNo"></select>
	    					</div>
	    					<div class="colmon">
	    						<label>是否启用</label><input type="checkbox" name="desctempSts" value="1" checked/>
	    					</div>
	    					<div class="colmon">
	    						<input type="button" value="查询" onclick="selectSysDescTemp()"/>
	    					</div>
	    					<div class="colmon right">
	    						<i class="i i-xuanzhuan1" title="刷新数据典" onclick="refreshParmKey(this)"></i>
	    						<i class="fa fa-spinner fa-spin fa-hidden" title="正在刷新"></i>
	    					</div>
    					</form>
    				</div>
    			</div>
    		</div>
    		<div class="row row_content tabs_list">
    			<div class="col-md-12 col_content col_list">
    				<div class="bgColor"> 
    					<div class="title">
    						<h4>业务描述模板列表</h4>
    						<span>
    							<input type="button" value="新增" onclick="optContent(true,null)">
    						</span>
    					</div>
    					<div class="table_content parent">
    						<dhcc:tableTag paginate="sysDescTempList" property="tabledesctemp0001" head="true"></dhcc:tableTag>
    						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
    					</div>
    				</div>
    			</div>
    			<div class="col-md-6 col_content col_list_content">
    				<div class="bgColor"> 
    					<div class="title">
    						<h4>业务描述配置</h4>
    						<span>
    							<input type="button" value="保存" onclick="tempSave(this)">
    							<input type="button" value="取消" onclick="optContent(false)">
    						</span>
    					</div>
    					<div class="table_content child">
    						<form method="post" theme="simple" name="operform" action="">
	    						<dhcc:formTag property="formdesctemp0001" mode="query"></dhcc:formTag>
	    					</form>
    					</div>
    					<div class="pageerc"></div>
    				</div>
    			</div>
    		</div>
    	</div>
 	</body>
</html>