<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>报表项详情编辑</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<style type="text/css">
			.itemS1{
				padding-left: 5px;
			}
			.itemS2{
				padding-left: 30px;
			}
			.itemS3{
				padding-left: 45px;
			}
			.strong{
				font-weight: bold;
			}
		</style>
	</head>
<body class="overflowHidden">
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwReportDeploy"/>
	<div class="deploy_content">
		<form method="post" id="cwDeployForm" theme="simple" name="cwDeployForm" action="">
			<div id="content" class="table_content cw_voucher_list">
				<dhcc:tableTag property="tablefinreport0004" paginate="cwItemCalcList" head="true"></dhcc:tableTag>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		//自定义滚动条
		$("body").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
	})
	//yht bug修改
	function showMingXiThis(com,showType,week,comName){
		diologFunction(com,showType,week,comName);
	}
		//yhtbug修改
		function diologFunction(dCom,dShowType,dWeek,dComName){
			var url='${webPath}/component/finance/othreport/detailAccountList.jsp?dCom='+dCom+'&dStartWeek='+dWeek+'&dEndWeek='+dWeek+'&dFlag=y'+'&dComName='+dComName;
// 			layer.open({
// 	        type: 2,
// 	        title:"明细分类账",
// 	        offset:'25px',
// 	        maxmin: true,
// 	        shadeClose: true, //点击遮罩关闭层
// 	        area : ['80%' , '90%'],
// 	        content: encodeURI(url)
// 		 });
			top.createShowDialog(encodeURI(url),"明细分类账",'85','85',function(){
			
		});
		}
		
</script>
</html>