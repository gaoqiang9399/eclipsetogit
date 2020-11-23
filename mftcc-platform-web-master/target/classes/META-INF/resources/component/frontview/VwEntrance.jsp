<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title></title>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css" />
<script type="text/javascript">
 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/vwBannerManage/getListPage';
			break;
		case "2":
			window.location = webPath+'/vwMenuManage/getListPage';
			break;
		case "3":
			window.location = webPath+'/vwSetManage/getById';
			break;
		case "4":
			window.location = webPath+'/vwLinkManage/getListPage';
			//CwParmEntrance.showTips(obj);
			break;
		case "5":
			window.location = webPath+'/vwContManage/getListPage';
			break;
		case "6":
			window.location = webPath+'/vwFeatureManage/getListPage';
			break;
		case "7":
			window.location = '/factor/component/frontview/AppSettingEntrance.jsp';
			break;
			
		}
	}
</script>
<body>
<div class="container">
	<div class="row clearfix report-body">
		<div class="rotate-body margin_top_20">
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac3" onclick="openReport(this, '3','基本信息设置');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						基本信息设置
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac1"  onclick="openReport(this, '1','banner管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						banner管理
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac2" onclick="openReport(this, '2','菜单管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						菜单管理
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac5" onclick="openReport(this, '5','菜单内容管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						菜单内容管理
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport(this, '4','链接管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						链接管理
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac3" onclick="openReport(this, '6','平台特点管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						平台特点管理
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport(this, '7','移动端管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						移动端管理
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>