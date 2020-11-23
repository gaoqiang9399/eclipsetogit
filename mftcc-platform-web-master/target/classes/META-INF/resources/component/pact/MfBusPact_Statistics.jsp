<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
%>
<script type="text/javascript">
var cusNo = '<%=cusNo%>';
$(function(){
	//异步获取客户的历史统计信息
	$.ajax({
		url:webPath+"/mfBusPact/getCompleteBusDataAjax",	
		data:{cusNo:cusNo},
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag == "success"){
				$("#totalAmt").text(data.totalAmt);
				$("#totalCnt").text(data.totalCnt);
			}else{
				alert(data.msg, 0);
			}
		}
	});
});
//历史完结任务
function hisApply(){
	top.openBigForm(webPath+'/mfBusPact/getFinshPactListPage?cusNo='+cusNo,'历史完结业务',function() {})
}
</script>

<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title" onClick="hisApply()">历史完结业务</button>
		<button type="button" class="btn btn-font-qiehuan pull-right" onClick="hisApply()"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
	</div>
</div>
<div class="row clearfix padding_left_12 his-statistic">
	<div class="col-xs-12 col-md-12 column" >
		<table>
			<tbody>
				<tr>
					<td >
						<p class="ptitle">历史成交额</p>
						<p class="pvalue"><span id="totalAmt">0.00</span><span>&nbsp;万元</span></p>
					</td>
					<td >
						<p class="ptitle">历史成交次数</p>
						<p class="pvalue"><span id="totalCnt">0</span><span>&nbsp;次</span></p>
					</td>
					<td >
						<p class="ptitle">逾期情况</p>
						<p class="pvalue"><span id="overdue">无</span></p>
					</td>
			</tbody>
		</table>
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					

	
								