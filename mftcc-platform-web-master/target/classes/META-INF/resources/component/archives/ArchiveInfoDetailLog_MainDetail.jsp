<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String archiveMainNo = (String)request.getParameter("archiveMainNo");
%>
<script type="text/javascript">
    var archiveMainNo = '<%=archiveMainNo%>';
$(function(){
	$.ajax({
		url:webPath+"/archiveInfoDetailLog/getAllForMainAjax",
		data:{archiveMainNo:archiveMainNo},
		type:"POST",
		dataType:"json",
		success:function(data) {
		$("#archiveInfoDetailLog").empty();
			if (data.logList.length == 0) {
				$("#archiveInfoDetailLog").append('<div class="no-content">暂无数据</div>');
			} else {
				var htmlStr = "";
				$.each(data.logList,
					function(i, log) {
						htmlStr = htmlStr + '<div><p class="his-title">'
								+ '<span>' + log.opName + '</span>'
								+ '<span class="change-date">' + log.opDate + '</span>'
								+ '<span class="change-time">' + log.opTime + '</span>'
								+ '</p>';
						var description = log.description;
						if((!description || description !== '') && description.length > 120) {
							description = description.substring(0, 120) + "...";
							htmlStr = htmlStr+'<p class="his-cont" title="' + log.description + '">' + description + '</p></div>';
						} else {
							htmlStr = htmlStr+'<p class="his-cont">' + description + '</p></div>';
						}

					});
				$("#archiveInfoDetailLog").append(htmlStr);
			}
		},
		error:function() {
		}
	});
	$("#log-detail").click(function() {
		 top.createShowDialog(webPath+'/archiveInfoDetailLog/getListPage?archiveMainNo=' + archiveMainNo, '文件操作详情', '90', '90');
	});
});
</script>
</head>
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title his-detail-opt">操作记录</button>
		<button type="button" class="btn btn-font-qiehuan pull-right his-detail-opt" id="log-detail"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
	</div>
</div>
<div class="row clearfix padding_left_30 his-info">
	<div class="col-xs-12 col-md-12 column" id="archiveInfoDetailLog">
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					

	
								