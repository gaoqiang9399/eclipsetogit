<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
%>
<script type="text/javascript">
	var cusNo = '<%=cusNo%>';
	$(function(){
		$.ajax({
			url:webPath+"/bizInfoChange/getTopListAjax",
			data:{relNo:cusNo},
			type:"POST",
			dataType:"json",
			success:function(data) {
			$("#bizInfoChange").empty();
				if (data.bizInfoChangeList.length == 0) {
					$("#bizInfoChange").append('<div class="no-content"> 暂无数据</div>');
				} else {
					var htmlStr = "";
					$.each(data.bizInfoChangeList,function(i,bizInfoChange) {
							htmlStr = htmlStr +'<div><p class="his-title">'
												+'<span>'+bizInfoChange.opName+'</span>'
												+'<span class="change-date">'+bizInfoChange.date+'</span>'
												+'<span class="change-time">'+bizInfoChange.time+'</span>'
												+'</p>';
							var content = bizInfoChange.cont;
							if(bizInfoChange.cont.length>30){
								content = content.substring(0, 30) + "...";
								htmlStr = htmlStr+'<p class="his-cont" title="'+bizInfoChange.cont+'">'+content+'</p></div>';
							}else{
								htmlStr = htmlStr+'<p class="his-cont">'+bizInfoChange.cont+'</p></div>';
							}
	
						});
					$("#bizInfoChange").append(htmlStr);
				}
			},
			error:function() {
	
			}
		});
		var relNo = "cusNo-"+cusNo;
		$(".his-detail-opt").click(function(){
			 top.createShowDialog(webPath+'/bizInfoChange/getListPageForRenter?relNo='+relNo,'信息变更记录','90','90');
		});
	});
</script>
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title his-detail-opt">信息变更记录</button>
		<button type="button" class="btn btn-font-qiehuan pull-right his-detail-opt" ><i class="i i-qiehuan" style="font-size:22px;"></i></button>
	</div>
</div>
<div class="row clearfix padding_left_30 his-info">
	<div class="col-xs-12 col-md-12 column" id="bizInfoChange"></div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					

	
								