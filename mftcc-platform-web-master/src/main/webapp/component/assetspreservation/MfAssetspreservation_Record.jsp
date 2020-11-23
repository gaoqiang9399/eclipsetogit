<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%
	String pledgeNo = (String)request.getParameter("pledgeNo");
%>
<script type="text/javascript">
	var pledgeNo='<%=pledgeNo%>';
	$(function(){
		  jQuery.ajax({
	            url:webPath + "/mfAssetsPreservation/getListByPledgeNoAjax",
	            data:{pledgeNo:pledgeNo},
	            type:"post",
	            success:function(data){
	                if(data.flag == "success"){
	                	if(data.size > 0){
		                	$("#MfAssetsPreservationRecordList").html(data.tableHtml);
		                	$("#MfAssetsPreservationRecordList .ls_list").show(); 
		                	$("#MfAssetsPreservationRecordDiv").show(); 
	                	}
	                }else{
	                	alert(data.msg,3);
	                }
	            },
	            error:function(){
	            	alert("资产保全历史记录查询失败",0);
	            }
	        });
	});
</script>
<!--资产保全历史记录-->
<div class="arch_sort" id = "MfAssetsPreservationRecordDiv" style="border: 0px;display:none;">
	<div class="dynamic-block" title="资产保全历史记录" name="MfAssetsPreservationRecordAction"
		data-sort="14" data-tablename="mf_assets_preservation">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>资产保全记录</span>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#MfAssetsPreservationRecordList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="MfAssetsPreservationRecordList" style="margin-top: 10px;">
			</div>
		</div>
	</div>
</div>

