<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
%>
<script type="text/javascript">
var cusNo = '<%=cusNo%>';
$(function(){
	getCusTrackTopList();
});
//获取客户跟进列表前几条
function getCusTrackTopList(){
	$.ajax({
		url :webPath+"/mfCusTrack/getTopListAjax",
		data:{cusNo:cusNo},
		type : "POST",
		dataType : "json",
		success : function(data) {
		
			if (data.flag == "success") {
				$("#cusTrackBlock").empty();
				if (data.mfCusTrackList.length == 0) {
					$("#cusTrackBlock").append('<div class="no-content"> 暂无数据</div>');
				} else {
					var cusTrackHtml = "";
					$.each(data.mfCusTrackList,
						function(i,cusTrack) {
							cusTrackHtml = cusTrackHtml 
							  + '<div><p class="his-title">'
										+ '<span style="margin-left: 0px;" class="change-date">'
											+ cusTrack.regDate
										+ '</span>'
										+ '<span class="change-time">'
											+ cusTrack.regTime
										+ '</span>'
										+ '<span style="margin-left: 5px;">'
											+ cusTrack.opName
										+ '</span>'
									+ '</p>';
							var content = cusTrack.trackContent;
							if(cusTrack.trackContent.length>30){
								content = content.substring(0, 30) + "...";
								cusTrackHtml = cusTrackHtml+'<p class="his-cont" title="'+cusTrack.trackContent+'">'+content+'</p></div>';
							}else{
								cusTrackHtml = cusTrackHtml+'<p class="his-cont">'+cusTrack.trackContent+'</p></div>';
							}

						});
					$("#cusTrackBlock").append(cusTrackHtml);
				}
			}else{

			}
		}
	});
}

function cusTrack(type) {
	
	top.updateFlag = false;
	top.openBigForm(webPath+'/mfCusTrack/getListPage?cusNo=' + cusNo+ "&query=" + type,'客户跟进',function(){
		if (top.updateFlag){
			getCusTrackTopList();
		}
	});
};
</script>
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title" onclick="cusTrack('0');">${param.blockName}</button>
		
		<button type="button" class="btn btn-font-qiehuan pull-right" onclick="cusTrack('0');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
	</div>
</div>
<div class="row clearfix padding_left_30 his-info">
	<div class="col-xs-12 col-md-12 column" id="cusTrackBlock">
		
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>