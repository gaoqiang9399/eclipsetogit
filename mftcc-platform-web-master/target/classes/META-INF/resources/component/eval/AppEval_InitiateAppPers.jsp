<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String evalIndexTypeRel = (String)request.getAttribute("evalIndexTypeRel");
	String query =  (String)request.getAttribute("query");
	String appSts =  (String)request.getAttribute("appSts");
	boolean getFlag = (Boolean)request.getAttribute("getFlag");
	Object dataMap = request.getAttribute("dataMap");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
		 	var evalIndexTypeRel = "<%=evalIndexTypeRel%>";
		 	var dataMap = <%=dataMap%>;
		 	var getFlag = <%=getFlag%>;
		 	var query = "<%=query%>";
		 	var appSts = "<%=appSts%>";
		 	var evalAppNo = "${evalAppNo}";
		</script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
		<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
		<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
		<script type="text/javascript" src="${webPath}/component/eval/js/appEval.js"></script>
		<%--滚动样式--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css" />
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<link id="appEvalInfo" type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css"/>
	</head>
	<body class="body_bg">
		<div class="content" style="height: calc(100% - 40px);display: none;">
			<div class="showprogress"><!--进度-->
				<ul>
					<li class="selected" name="chosefin">
						<span class="span_btn">
							<span class="lable">基本信息</span>
						</span>
					</li>
					<!-- <li name="fin">
						<span class="span_btn">
							<span class="lable">财务得分</span>
						</span>
					</li> -->
					<li name="dl">
						<span class="span_btn">
							<span class="lable">定量评分</span>
						</span>
					</li>
					<li name="dx">
						<span class="span_btn">
							<span class="lable">定性评分</span>
						</span>
					</li>
					<li name="adj">
						<span class="span_btn">
							<span class="lable">综合评分</span>
						</span>
					</li>
					<li name="evalapp">
						<span class="span_btn">
							<span class="lable">评级申请</span>
						</span>
					</li>
				</ul>
			</div>
			<div class="content_show">
				<ul class="content_ul">
					<li>
						<div name = "chosefin" class="li_content_type">
							<div class="li_title"><!--<span>选择财报</span>--></div>
							<div class="li_content">
								<form  method="post" theme="simple" name="operform" action="${webPath}/appEval/insertPersAjax">
									<dhcc:formTag property="formeval1003" mode="query"/>
									<c:if test="${query!='query'}">
										<div class="from_btn">
											<dhcc:thirdButton value="保存" action="保存" onclick="evalAjaxSave(this.form,'${webPath}/appEval/updateAjax')"></dhcc:thirdButton>
							    		</div>
						    		</c:if>
								</form>
							</div>
						</div>
					</li>
				<!-- 	<li>
						<div name = "fin" class="li_content_type">
							<div class="li_title">
								<span>财务得分</span>
								<span class="scoreShow">
									<span class="score">
									</span>分
								</span>
							</div>
							<div class="li_content">
								<table class="ls_list_a" style="width:100%">
									<thead></thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</li> -->
					<li>
						<div name = "dl" class="li_content_type">
							<div class="li_title">
								<!--<span>定量得分</span>-->
								<span class="scoreShow">
									<span class="score">
									</span>分
								</span>
							</div>
							<div class="li_content">
								<table class="ls_list_a" style="width:100%">
									<thead></thead>
									<tbody></tbody>
								</table>
							</div>
						</div>
					</li>
					<li>
						<div name = "dx" class="li_content_type">
							<div class="li_title">
								<!--<span>定性得分</span>-->
								<span class="scoreShow">
									<span class="score">
									</span>分
								</span>
							</div>
							<div class="li_content">
								<form style="margin-right: 15px;">
									<table class="ls_list_a" style="width:100%">
										<thead></thead>
										<tbody></tbody>
									</table>
									<c:if test="${query!='query'}">
										<div class="from_btn">
											<dhcc:thirdButton value="保存" action="保存" onclick="evalDxUpdate(this.form,'${webPath}/evalScenceDxVal/updateAjax')"></dhcc:thirdButton>
							    		</div>
							    	</c:if>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div name = "adj" class="li_content_type">
							<div class="li_title"><!--<span>综合得分</span>--></div>
							<div class="li_content">
								<form  method="post" theme="simple" name="operform" action="${webPath}/evalCompreVal/updateAjax">
								   <div class="table_title">非财务评分结果 </div>
								   <div class="table_span">
								   	  <dhcc:formTag property="formeval2006" mode="query"/>
								   </div>
								   <div  class="table_title">客户经理评分调整</div>
								    <div class="table_span">
								   		<dhcc:formTag property="formeval2007" mode="query"/>
								   	</div>
								</form>
								<div name="initadj" style="margin-top: 8px;margin-right: 20px;">
									<form>		
										<table class="ls_list_a adj" style="width:100% ;margin-left: 1px;">
											<thead></thead>
											<tbody></tbody>
										</table>
								    </form>		
								</div>
								<c:if test="${query!='query'}">
									<div class="from_btn">
										<dhcc:thirdButton value="保存" action="保存" onclick="evalCompreUpdate(this,'${webPath}/evalCompreVal/updateAjax')"></dhcc:thirdButton>
							    	</div>
								</c:if>
							</div>
						</div>
					</li>
					<li>
						<div name = "evalapp" class="li_content_type">
							<div class="li_title"><!--<span>评级申请</span>--></div>
							<div class="li_content">
								<form  method="post" theme="simple" name="operform" action="${webPath}/appEval/updateAjax">
								    <div  class="table_title">评级信息</div>
									<div class="table_span">
									    <dhcc:formTag property="formeval1001" mode="query"/>
									</div>
									<c:if test="${query!='query'}">
									   <div class="from_btn">
									   		<dhcc:thirdButton value="保存" action="保存" onclick="evalAppUpdate(this.form,'${webPath}/appEval/updateFormAjax')"></dhcc:thirdButton>
									   		<dhcc:thirdButton value="提交" action="提交" onclick="evalAppSubmit(this.form,'${webPath}/appEval/evalSubmitAjax')"></dhcc:thirdButton>
								    	</div>
							    	</c:if>
								</form>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>