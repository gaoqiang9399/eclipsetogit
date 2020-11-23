
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ page language="java" import="app.util.AppConfig" session="true"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="body_bg">
<form method="post" theme="simple" name="cms_form"
					action="${webPath}/wkfTask/findByPageForRepayAdv">
<div class="right_bg">
	<div class="right_w">
		<div class="from_bg">
			<div class="right_v">
					<table width="100%" align="center" class="searchstyle">
						<tr>
							<td>
								<dhcc:formTag property="formwkf0036" mode="query" />
							</td>
						</tr>
					</table>
					<div class="tools_372">
						<dhcc:button value="查询" action="查询" commit="true"
									typeclass="btn_80"></dhcc:button>
					</div>
			</div>
		</div>
	</div>
</div>
<p class="p_blank">&nbsp;</p>
 
<div class="right_bg">
	<div class="right_w">
		<div class="from_bg">
			<div class="right_v">
			<div style="vertical-align: bottom;" class="tabCont">
				<div style="float:left" class="tabTitle">提前结清待处理列表</div>
			</div>
			<dhcc:tableTag paginate="wkfTaskList" property="tablewkf0049" head="true" />
			</div>
		</div>
	</div>
</div>
</form>			
</body>
<script type="text/javascript">
	function funcPrint(lk) {
		var contNo = lk.split("?")[1].split("&")[0];
		var params = contNo;
		var url = "<%=AppConfig.getReportURL()%>"
				+ "pub/reportpubExt.jsp?raq=util/tqjqsqh.raq&&params=" + params;
		window
				.showModalDialog(url, window,
					"dialogWidth=50;dialogHeight=50;resizable=no;scrollbars=no;status:yes;help:no;");
	}
	
	function func_upload(lk){
	
		var appNo = lk.split("?")[1].split("&")[0].split("=")[1]; 
		var cifNo = lk.split("?")[1].split("&")[1].split("=")[1]; 
		var url = webPath+"/docManage/findByPageForAlp?relNo="+appNo+"&cifNo="+cifNo+"&docScene=31&flag=dealerTerm";
		window.location.href = url;
	}
</script>
</html>