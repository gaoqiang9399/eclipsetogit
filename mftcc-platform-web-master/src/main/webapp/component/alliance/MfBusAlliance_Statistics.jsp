<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String allianceId = (String)request.getParameter("allianceId");
%>
<script type="text/javascript">
$(function(){
    var allianceId = '<%=allianceId%>';
	//异步获取客户的历史统计信息
	$.ajax({
		url:webPath+"/mfBusAlliance/getCompleteBusDataAjax",
		data:{allianceId:allianceId},
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.flag == "success"){
				$("#personNum").text(data.personNum);
				$("#applyAmt").text(data.applyAmt);
                $("#assureNum").text(data.assureNum);
                $("#assureAmt").text(data.assureAmt);
			}else{
				alert(data.msg, 0);
			}
		}
	});
});
//历史完结任务
function hisApply(){
	top.openBigForm(webPath+'/mfBusAlliance/getFinshListPage?allianceId='+allianceId,'历史业务',function() {})
}
</script>

<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title" onClick="hisApply()">联保体使用历史记录</button>
		<button type="button" class="btn btn-font-qiehuan pull-right" onClick="hisApply()"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
	</div>
</div>
<div class="row clearfix padding_left_12 his-statistic">
	<div class="col-xs-12 col-md-12 column" >
		<table>
			<tbody>
				<tr>
					<td >
						<p class="ptitle">借款人数</p>
						<p class="pvalue"><span id="personNum">0</span><span>&nbsp;人</span></p>
					</td>
					<td >
						<p class="ptitle">借款金额</p>
						<p class="pvalue"><span id="applyAmt">0</span><span>&nbsp;元</span></p>
					</td>
                </tr>
                <tr>
                    <td >
                        <p class="ptitle">担保总数</p>
                        <p class="pvalue"><span id="assureNum">0</span><span>&nbsp;笔</span></p>
                    </td>
                    <td >
                        <p class="ptitle">担保总额</p>
                        <p class="pvalue"><span id="assureAmt">0</span><span>&nbsp;元</span></p>
                    </td>
                </tr>
			</tbody>
		</table>
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					

	
								