<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="body_bg">
	    <div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<button type="button" class="btn btn-primary" onclick="relationDetail();">查看关联关系图</button>
						<button type="button" class="btn btn-primary" onclick="refreshRelation();">重新检测关联关系</button>
						<%--<button type="button" class="btn btn-primary" onclick="busApplySearch()">业务查询</button>--%>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<dhcc:tableTag property="tablecusrelation0001" paginate="mfCusRelationList" head="true"></dhcc:tableTag>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
	<script type="text/javascript">
	var cusNo="${cusNo}";
	function relationDetail(obj){
		window.location.href=webPath+"/mfCusRelation/forDetail?cusNo="+cusNo;
	}
	function busApplySearch(obj){
		window.location.href=webPath+"/mfBusStageSearch/getListPage";
	}
	$(function(){
		//处理暂无数据的情况
		if($('#content tbody tr').length == 0){
		    var thCount = $('#content thead th').length;
			$('#content tbody').append('<tr><td style="text-align: center;" colspan="'+thCount+'">暂无数据</td></tr>');
		}
	});
	
	function refreshRelation(){

		var cusNo = '${cusNo}';
		LoadingAnimate.start();
		$.ajax({
				url:webPath+'/mfCusRelation/getListPageAjax',
				type:'post',
				data:{cusNo:cusNo},
				dataType:"json",
				success:function(){
					LoadingAnimate.stop();
				window.location.reload();
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", ""));
					LoadingAnimate.stop();
				}
			});
	};
	</script>	
</html>