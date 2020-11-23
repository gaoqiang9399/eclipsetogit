<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${webPath}/themes/factor/css/set.css?v=${cssJsVersion}" />
	<%--字体图标--%>
	<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<title>移动端产品设置</title>
<style>
.rotate-div{
    width: 200px;
    margin-right: 30px;
    position: relative;
    padding: 0px;
    display: inline-block;
}
.rotate-body {
	margin-left: 40px;
	margin-top: 20px;
	overflow: hidden;
	zoom:1;
}
.rotate-title{
	height: 40px;
    line-height: 40px;
    color: #32b5cb;
    font-size: 16px;
    margin-left: 10px;
}
.rotate-des {
	display: flex;
	background: #fafafa;
	width: 200px;
	margin-bottom: 20px;
	height: 70px;
	align-items: center;
	justify-content: center;
	cursor: pointer;
	border: 1px solid #ddd;
}
.rotate-content{
	margin-left:20px;
}
.hline {
	width: 100%;
	height: 1px;
	margin: 0px auto;
	padding: 0px;
	background-color: #cfcfcf;
	overflow: hidden;
}
.min-hight-100{
	min-height: 100px;
}
.des-hover{
	min-height: 70px;
    width: 100%;
    border: 1px solid #ddd;
    background: #7D7D7D;
    opacity: 0.8;
    display: none;
    position: absolute;
    text-align: center;
    top: 0px;
    left: 0px;
    padding: 0px;
}
.rotate-div:hover .des-hover{
	display: block;
}
.opt-btn{
		color: #32b5cb;
		font-size: 18px;
		cursor: pointer;
		background: white;
		border: none;
		height: 35px;
		width: 35px;
		border-radius: 20px;
		margin: 15px 10px 15px 10px;
}
.rotate-tubiao{
	margin-left: 15px;
	color: #32b5cb;
}
</style>
</head>
<body class="bg-white">
	<div class="container">
		<!-- 产品设置 -->
		 <div class="row clearfix">
				<div class="list-item">
					<%-- <div class="row clearfix">
						<div class="col-md-9 column">
							<div class="title-div">
								<ol class="breadcrumb pull-left padding_left_0" id="kindFormConfig<s:property value='kindNo'/>">
									<li class="active"><span name="title"><s:property value="kindName" />移动端表单配置</span></li><span class="active"></span>
									<a href="${webPath}/mfSysKind/getById?kindNo=${formActive.kindNo}" class="config-font">配置</a>
								</ol>
							</div>
						</div>
					</div> --%>
					<div class="row clearfix">
						<div class="rotate-body min-hight-100" id="deploy">
						<div class="rotate-title">已配置</div>
						<%--保存过的属性 --%>
						<c:forEach var="formActive" items="${savedList}" varStatus="st">
							<div class="rotate-div " >
								<div class="rotate-obj">
									<div class="rotate-des" >
										${formActive.labelName}
										<span class="rotate-tubiao i i-duihao2"></span>
									</div>
									<div class="des-hover">
										<button class="opt-btn i i-bianji3"  data-deploy="1" onclick="AppKindConfigList.openFormActiveSetting(this,'${formActive.kindNo}','${formActive.fieldName}','','${formActive.initValue}','${formActive.labelName}')"></button>
<!-- 										<button class="opt-btn i i-x42" onclick=""></button> -->
										<button class="opt-btn i i-x42" data-deploy="1" onclick="AppKindConfigList.deleteFormActiveSetting(this,'${formActive.kindNo}','${formActive.fieldName}','','${formActive.initValue}','${formActive.labelName}')"></button>
									</div>
									<%--  <div class="rotate-content" onclick="event.cancelBubble = true">
										<p>默认值：<s:property value="defaultVal"/></p>
										<p>移动端展示：<s:if test="#formActive.mobleShow==1">启用</s:if><s:else>禁用</s:else></p>
										<p>移动端使用：<s:if test="#formActive.mobileUse==1">启用</s:if><s:else>禁用</s:else></p>
										<p>P&nbsp;C&nbsp;端展示：<s:if test="#formActive.pcShow==1">启用</s:if><s:else>禁用</s:else></p>
										<p>P&nbsp;C&nbsp;端使用：<s:if test="#formActive.pcUse==1">启用</s:if><s:else>禁用</s:else></p>
									</div>  --%>
								</div>
							</div>
						</c:forEach>
						</div>
						<div class="hline"></div>
						<div class="rotate-body" id="undeploy">
						<div class="rotate-title">未配置</div>
							<%--未保存的属性 --%>
						<c:forEach var="unformActive" items="${unsavedList}" varStatus="st">
							<div class="rotate-div">
								<div class="rotate-obj" ><%-- <s:property value="unit"/> 单位值传递有问题--%>
									<div class="rotate-des" >
										${unformActive.labelName}
<!-- 										<span class="rotate-tubiao i i-bianji3"></span> -->
									</div>
									<div class="des-hover">
										<button class="opt-btn i i-gouxuan"  data-deploy="0" onclick="AppKindConfigList.initFormActiveSetting(this,'${unformActive.kindNo}','${unformActive.fieldName}','','${unformActive.initValue}','${unformActive.labelName}')"></button>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
					</div>
				</div>
		</div> 
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/frontview/js/AppKindConfigList.js"></script>
<script type="text/javascript">
		var path = '<%=request.getContextPath() %>';
		var basePath = '${webPath}';
		$(function(){
			AppKindConfigList.init();
		});
</script>
</html>
