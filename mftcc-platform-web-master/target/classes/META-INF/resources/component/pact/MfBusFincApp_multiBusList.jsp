<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>多笔借据</title>
	</head>
	<script type="text/javascript">
	var busEntrance='${busEntrance}';
	$(function(){
		//处理暂无数据的情况
		if($('#content tbody tr').length == 0){
		    var thCount = $('#content thead th').length;
			$('#content tbody').append('<tr><td style="text-align: center;" colspan="'+thCount+'">暂无数据</td></tr>');
		}
		$(".table_content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
	});
	//多业务跳转
	function enterClick(obj,url){
		url = "${webPath}" + url;
		//跳页面
		if(busEntrance=="4" ||busEntrance=="5"||busEntrance=="6"){//审批页面多笔业务跳转在弹层页面中进行
			window.location.href=url+"&busEntrance=5"
		}else if(busEntrance=="finc"){
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url+"&busEntrance=finc");
			myclose();
		}
		else{
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url+"&busEntrance=3");
			myclose();
		}

	};
</script>
<body class="bg-white">
 	<div class="container">
 		 <div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content padding_top_20 padding_bottom_20" style="height: 100%; ">
					<dhcc:tableTag paginate="mfBusFincAppList" property="tablefincapp0003" head="true" />
				</div>
			</div>
		</div> 
		</div>
	</div>
</body>
</html>