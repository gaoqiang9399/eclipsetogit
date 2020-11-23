<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="cn.mftcc.util.DateUtil"%>
<%@page import="app.base.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%@ page import="config.YmlConfig" %>
<%@ page import="app.base.SpringUtil" %>
<%@ include file="/component/include/webPath.jsp" %>
<%
	String path = request.getContextPath();
	String ContextPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String layout = "layout/view";
	String sysDate = DateUtil.getDate();
	String weekDay = DateUtil.getWeekDay(sysDate);
	String viewDate = DateUtil.getDiyDate(sysDate, "yyyy年MM月dd日");
	String orgName = (String)request.getSession().getAttribute("orgName");
	String regName =  (String)request.getSession().getAttribute("regName");
	String regNo =(String)request.getSession().getAttribute("regNo");
	String phone = (String)request.getSession().getAttribute("mobile");
	String email = (String)request.getSession().getAttribute("email");
	if(phone==null||"".equals(phone)){
		phone="未登记手机号";
	}
	if(email==null||"".equals(email)){
		email="未登记邮箱";
	}
	MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
	YmlConfig ymlConfig = (YmlConfig)SpringUtil.getBean(YmlConfig.class);
	String htTheLogo=ymlConfig.getUpload().get("wareTheLoginFilePath");
%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript">
		var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
		var loadingSmallGifPath = "<%=mfSysCompanyMst.getLoadAnimationSmallImg()%>";
		var path = "${webPath}";
		var servicemanagePath = "${servicemanagePath}";
		var regNo = "<%=regNo%>";
	</script>

	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<title>仓储管理系统</title>
	<link rel="stylesheet"  id="aaa"  href="${webPath}/themes/factor/css/formStyle.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	<link rel="stylesheet" href="${webPath}/themes/view/css/formline.css" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/formline.css?v=${cssJsVersion}" />

	<link rel="shortcut icon" type="image/x-icon" href="${webPath}/favicon.ico" />
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${webPath}/layout/ie8/js/es5-shim.js"></script>
	<script type="text/javascript" src="${webPath}/layout/ie8/js/es5-sham.js"></script>
	<script type="text/javascript" src="${webPath}/layout/ie8/js/json2.js"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.layout.min.js"></script>
	<!-- 弹窗消息模板 -->
	<script type="text/javascript" src="${webPath}/component/include/getMessage.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/zl.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/queryView.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.autocompleter.js"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/notification.js"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/fixedDiv.js" ></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/fixedDivFun.js" ></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/moveBack.js" ></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/view.js" ></script>
	<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
	<script type='text/javascript' src="${webPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${webPath}/dwr/interface/MessagePush.js"></script>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/fakeLoader.js"></script>
	<%--加密算法--%>
	<script type="text/javascript" src="${webPath}/component/include/encryptJS/MD5.js"></script>

	<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
	<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
	<script type="text/javascript" src="${webPath}/<%=layout%>/js/jAlert.js"></script>
	<%--bootstrap--%>
	<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js" ></script>
	<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/layout.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/menu.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/page/css/fakeLoader.css" />
	<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
	<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/pageInfo.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/A2B.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/autocompleter.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/animate.min.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/notification.css" />
	<script type="text/javascript" src="${webPath}/layout/view/js/warns.js" ></script>
	<link rel="stylesheet" href="${webPath}/tech/wkf/css/modelAlert.css" />
	<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/fixedDiv.css" />
	<link rel="stylesheet" href="${webPath}/tech/wkf/css/processDetail.css" />
	<!--artDialog控件  -->
	<script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
	<link id="ui-dialog" rel="stylesheet" href="${webPath}/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
	<script type="text/javascript" src="${webPath}/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>

	<!--加载动画js  -->
	<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>

	<link id="filter" rel="stylesheet" href="${webPath}/themes/view/css/filter${skinSuffix}.css" />

	<link id="BS-factor" rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor${skinSuffix}.css?v=${cssJsVersion}" />
	<link id="layout" rel="stylesheet" href="${webPath}/themes/factor/css/layout${skinSuffix}.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/A2B.css" />
	<link id="viewUserDef" rel="stylesheet" href="${webPath}/themes/factor/css/viewUserDef${skinSuffix}.css?v=${cssJsVersion}" />
	<link id="menu-query" rel="stylesheet" href="${webPath}/themes/factor/css/menu-query${skinSuffix}.css?v=${cssJsVersion}" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/pageInfo.css" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/modelAlert.css" />

	<script>
		//待办任务完成后的处理
		var viewMenuData = eval('('+'${viewMenuData}'+')');
		var sysProjectName = '${sysProjectName}';

		function onPageLoad(){
			var userId = '${regNo}';
			MessagePush.onPageLoad(userId);
		}

		function showMessage(msgType,msgCount,autoMessage,taskInfo,username){
			var taskInfoObj=$.parseJSON(taskInfo);
			if(typeof(autoMessage) !='undefined' && autoMessage=="sendWebPageMessage"){
				newsDialog.showInnerMessage(taskInfoObj);
			}else{
				newsDialog.showPushNews(taskInfoObj);
			}

		}
		//变更皮肤
		window.top.changeSystemFrameSkin=function(oldskin,skin){
			$("link").each(function(){
				var id = $(this).attr("id");
				if(typeof (id) !="undefined"){
					var href =$(this).attr("href");
					if(oldskin=="default"){
						$(this).attr("href",href.replace(id, id+"_"+skin));
					}else{
						if(skin=="default"){
							$(this).attr("href", href.replace(id + "_" + oldskin, id));
						}else {
							$(this).attr("href", href.replace(id + "_" + oldskin, id + "_" + skin));
						}
					}
				}
			});
		}
		//待办任务完成后的处理
		function closePop(){
			var dm = document.getElementById("b1_iframe").contentWindow;
			if($(dm.document).find(".task_select").length>0){
				var $task = $(dm.document).find(".task_select");
				var data = $task.data("info");
				$task.removeClass("task_correct").removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
					$(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
						$(this).empty();
						document.getElementById("b1_iframe").contentWindow.mcSelector.mCustomScrollbar("update");
					});
				});
				document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
			}
			//同步更新待办任务的条数
			if($("#task_count").length>0){
				var curCount = $("#count_input").val();
				curCount = curCount*1-1;
				if(curCount>=0 && curCount<100){
					if(curCount==0){
						$("#task_count").text("");
					}else{
						$("#task_count").text(curCount);
					}
				}else if(curCount<0){
					$("#task_count").text("");
				}
				$("#count_input").val(curCount);
			}
		}

		function showPageInfo(value){
			rzzl.setPageInfo(value, "#pageInfoContent");
		}
		var myLayout;
		$(document).ready(function() {

			rzzl.setBox_A(".pt-page[name='A'] iframe");
			myLayout = $('body').layout({
				center__paneSelector: ".outer-center",
				west__paneSelector: ".outer-west",
				west__size: 100,
				spacing_open: 0,
				west__childOptions: {
					center__paneSelector: ".outer-west-center",
					north__paneSelector: ".outer-west-logo",
					north__size: 55,
					spacing_open: 0
				},
				center__childOptions: {
					center__paneSelector: ".middle-center",
					north__paneSelector: ".middle-north",
					north__size:56,
					spacing_open: 0,
					spacing_closed:0,
					north__childOptions: {
						center__paneSelector: ".middle-north-center",
						west__paneSelector: ".middle-north-west",
						east__paneSelector: ".middle-north-east",
						west__size: 440,
						east__size: 80,
						spacing_open: 0
					}
				}
			});
			// add by LiuYF 文字被后边的div覆盖。不知道怎么改layout的单个z-index属性。
			$(".middle-north-west").css("z-index","1");
			rzzl.initMenu(".menu_B");
			$(".pageInfo").click(function(){
				$(this).hide();
				$("#nope").val("");
				$("#nope").show();
				$("#nope").focus();
			});
			rzzl.switchB.init($(".switchB"));
		});
		function whichTransitionEvent(el){
			var t;
			var transitions = {
				'transition':'transitionend',
				'OTransition':'oTransitionEnd',
				'MozTransition':'transitionend',
				'WebkitTransition':'webkitTransitionEnd'
			};

			for(t in transitions){
				if( el.style[t] !== undefined ){
					return transitions[t];
				}
			}
		}

		/**系统退出**/
		function sysQuit(){
			$.ajax({
				url :  webPath+"/sysWareLogin/logout",
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert("退出成功", 1);
						window.top.location.href = webPath+"/sysWareLogin/wareLogin";
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});

		}

	</script>
	<style type="text/css">
		div[name='A']{
			background-color:#fff;
		}
	</style>
</head>
<body class="overflowHidden" onLoad="onPageLoad();dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);">
<div class="outer-center">
	<div class="middle-center pt-perspective">
		<div class="pt-page" name="A">
			<iframe id="a_iframe" src="${webPath}/sysSkip/skipToC?entranceNo=99" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="iframepage"></iframe>
		</div>
		<div class="pt-page pt-page-current" name="B">
			<div class="pg" name="B1">
				<iframe id="b1_iframe"  src="" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="b1_iframe"></iframe>
			</div>
			<div class="pg" name="B2">
				<iframe id="b2_iframe" src="${webPath}/bwmTaskRoleRel/getListPage" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" name="b2_iframe"></iframe>
			</div>
		</div>
		<div class="switchB">
			<div class="A"></div>
			<div class="B"></div>
			<div class="current"></div>
		</div>
	</div>
	<div id="middle-north" class="middle-north">
		<!-- 切换层 -->
		<div class="middle-north-west TopBar-chevron" style="height:55px;">
			<span class="logodes"><%= mfSysCompanyMst.getSystemName() %></span>
			<img src="${webPath}/<%= mfSysCompanyMst.getPropagandaLanguageImg() %>" style="width: 214px;margin-top:15px; margin-left:283px;" />
		</div>

		<!-- 详情 -->
		<div class="middle-north-center">
			<div class="pull-right">
				<ul class="nav navbar-nav">
					<%--<!--待办任务-->--%>
					<%--<li>--%>
					<%--<a id="messagePage" href="#" class="task-msg" onclick="getMessagePage();">--%>
					<%--<i class="i i-xinxi middle-north-i"></i>--%>
					<%--<span class="pull-left">待办任务</span>--%>
					<%--</a>--%>
					<%--</li>--%>
					<!--个人中心-->
					<li id="perDa" class="dropdown user-menu">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
							<img  id="headImgShow" class="user-image "/>
							<span>欢迎您，${regName}</span>
						</a>
						<ul class="dropdown-menu" style="height:190px; min-width:200px;z-index:-2;">
							<li class="my-info-detail">
								<div class="row clearfix">
									<div class="col-md-8 column">
										<p><%=regName%></p>
										<p id="phone"><%=phone%></p>
									</div>
									<div id="u-pic" class="col-md-4 column">
										<img id="headImgShow1" class="account-img"/>
									</div>
								</div>
								<div class="row clearfix">
									<p><%=orgName%></p>
									<p id="email"><%=email%></p>
								</div>
							</li>
							<li class="my-info-opt btn-group">
								<button class="btn btn-link" onclick="optSliders();">
									<i class="i i-password" ></i><span>修改密码</span>
								</button>
								<div class="my-line"></div>
								<button class="btn btn-link pull-right" onclick="sysQuit();">
									<i class="i i-tuichu"></i><span>退出系统</span>
								</button>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>

		<div class="middle-north-east">
			<div class="pull-right">
				<ul class="nav navbar-nav">
					<!--工具箱 -->
					<li class="toolbox-li">
						<a href="#" onClick="getfixedDiv();"><i class="i i-menu menu"></i></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="outer-west" style="overflow:visible!important;">
	<!-- LOGO-->
	<!-- 视角-->
	<div class="outer-west-center" style="background-color: #e2e6f0;overflow:visible;">
		<div class="outer-west-logo" style=" height:55px; display: table-cell;vertical-align:middle;">
			<img  src="${webPath}/<%= mfSysCompanyMst.getSystemLogoImg() %>?v=${cssJsVersion}" class="logoimg" >
		</div>
		<ul class="menu_B">
		</ul>
	</div>
</div>
<div class="outer-center" id="queryPage">
	<div class="query-search">
		<span><i class="i i-fangdajing"></i></span>
		<input type="text" class="queryInput" id="queryInput" placeholder="请输入关键字"/>
	</div>
	<div class="queryListDiv" >
		<div class="queryAttention">
		</div>
		<div class="queryFirDiv">
		</div>
		<div class="querySecDiv">
		</div>
		<div class="queryThirDiv">
		</div>
	</div>
	<input type="hidden" name="queryMenuNo"/>
</div>
</body>
<script type="text/javascript">
	var viewFlag = "view";
	var opNo='${regNo}';
	var opNoType = '${opNoType}';
	var trenchId = '${trenchId}';
	var trenchUid = '${trenchUid}';
	var brNo = '${brNo}';
	var agenciesId = '${agenciesId}';
	var agenciesUid = '${agenciesUid}';
	var warehouseOrgId = '${warehouseOrgId}';
	var warehouseOrgUId = '${warehouseOrgUId}';
	var homePage = <%=request.getSession().getAttribute("homePage") %>;
	var accreditRe = <%=request.getSession().getAttribute("accreditRe") %>;
	var accreditReMsg = '${accreditReMsg}';
	var url=webPath+"/sysUser/getByIdAjax?opNo="+opNo+"&query=query";
	var queryMenueArr = new Array ();
	$(function () {
		$('#myModal').modal('hide');
		view.init();
		// 初始化右下角消息弹窗
		newsDialog.setInitContent();
		queryView.bindQueryMouse(queryMenueArr)
	});
	//消息中心
	function getMessagePage(){

		rzzl.skipPage(webPath+"/sysTaskInfo/getListPage?pasMaxNo=1");
		LoadingAnimate.stop();
		$("#messagePage").addClass("messagePageSel");
		$(".menu-active").removeClass("menu-active");
		$("#perDa").removeClass("messagePageSel");
	}
</script>
</html>
<%@ include file="newsDialog.jsp" %>
<%@ include file="meiLaiPhone.jsp" %>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>