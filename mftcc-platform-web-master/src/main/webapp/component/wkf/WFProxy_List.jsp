<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/cla/js/ClaModelBase.js"></script> 
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/cla/css/ClaModelBase.css"/>
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<%--字体图标--%>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
		<style>
		.content table td{color:#7e9abb; font-weight:700;}
		.searchstyle{margin-top:2px;}
		.searchstyle input{height:20px;}
		.btn_80{border: medium none;
    border-radius: 2px;
    color: #fff;
    height: 22px;
    margin: 1px 5px 0;
    padding: 0 10px;
    width: auto;
        font-size: 12px;
    background-color: #1e94cc;}
    .otherStyleBody .page_w{bottom:60px;}
     .otherStyleBody .page_w li{list-style:none;}
     .body_bg .table_content .mCSB_container{padding:0;}
     .page_wn li input[type="submit"]{background:#1e94cc;}
     .col_content .mCSB_scrollTools{ display:none;}
		</style>
	</head>
	<body class="body_bg overflow otherStyleBody" >
	<dhcc:markPoint markPointName="WFProxy_List"/>
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wFProxy/findByPage">
		<!--  <div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<table width="100%" align="center" class="searchstyle">
							<tr>
								<td><dhcc:formTag property="formwkf0016" mode="query" /></td>
							</tr>
						</table>
						<div class="tools_372">
							<dhcc:button value="查询" action="查询" commit="true"
								typeclass="btn_80"></dhcc:button>
						</div>
					</div>
				</div>
			</div>
		</div>-->
		<div class="content">
    		<div class="row">
    			<div class="col-md-12">
    				<div class="select-border">
    					<form id="ClaModelBase_ConfPage" action="">
	    					<div class="colmon">
	    						<table align="left" class="searchstyle">
									<tr>
										<td><dhcc:formTag property="formwkf0016" mode="query" /></td>
									</tr>
								</table>
	    					</div>
	    					<div class="colmon">
	    						<dhcc:button value="查询" action="WFProxy004" commit="true"
								typeclass="btn_80"></dhcc:button>
	    					</div>
	    					
    					</form>
    				</div>
    			</div>
    		</div>
		<div class="row row_content">
    			<div class="col-md-12 col_content"   style="height:calc(100% - 117px)">
    				<div class="bgColor"   style="height:100%"> 
    					<div class="title">
    						<h4>转授权/代理列表</h4>
    						<span>
    							<dhcc:button value="新增" action="WFProxy001" typeclass="t_ico_tj" onclick="${webPath}/wFProxy/input"></dhcc:button>		
    							    						</span>
    					</div>
    					<div class="table_content parent">
    						<dhcc:tableTag paginate="wFProxyList" property="tablewkf0024" head="true" />
    					</div>
    				</div>
    			</div>
		<!--<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle">转授权/代理列表</div>
						<dhcc:button value="新增" action="新增" typeclass="t_ico_tj" onclick="${webPath}/wFProxy/input"></dhcc:button>		
						</div>
						<dhcc:tableTag paginate="wFProxyList" property="tablewkf0024" head="true" />
					</div>
				</div>
			</div>
		</div>  -->
	</form>
</body>
</html>
