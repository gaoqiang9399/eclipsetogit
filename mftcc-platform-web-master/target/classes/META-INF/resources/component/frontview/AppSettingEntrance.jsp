<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE>
<html>
<head>
<title></title>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/AppSettingEntrance.css" />
<script type="text/javascript">
 	var webPath = '${webPath}';
	function openReport(obj, parm,title){
		switch(parm){
		case "1":
			window.location = webPath+'/appSettingManage/getBannerListPage';
			break;
		case "2":
			window.location = webPath+'/mfFrontKind/getAppKindConfig';
			break;
			
		}
	}
</script>
<body>
<div class="container">
	<div class="row clearfix report-body">
		<div class="rotate-body margin_top_20">
		
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac1"  onclick="openReport(this, '1','banner管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						banner管理
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac2"  onclick="openReport(this, '2','产品管理');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						产品管理
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
</body>
</html>