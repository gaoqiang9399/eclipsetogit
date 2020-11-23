<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
	var agenciesUid='${param.agenciesUid}';
	$(function(){
		var agenciesListSize = ${agenciesListSize};
		if(agenciesListSize != 0){
			//console.log($("#addAgenciesDetailList .ls_list").find("tbody").find("tr").html());
			//$("#addAgenciesDetailList .ls_list").find("tbody").find("tr").removeClass(".list-table .ls_list tbody tr td");
			$("#addAgenciesDetailList .ls_list").find("thead").find("tr").each(function(){
				var tdArr = $(this).children("th");
				//console.log(tdArr.html());
				/* console.log(tdArr.eq(1).html());*/
				tdArr.eq(0).css("text-align","center");
				tdArr.eq(1).css("text-align","right");
				tdArr.eq(2).css("text-align","right");
				tdArr.eq(3).css("text-align","right");
				tdArr.eq(4).css("text-align","right");
				tdArr.eq(5).css("text-align","center");
				tdArr.eq(6).css("text-align","center");
				tdArr.eq(7).css("text-align","center");
			});
			
			$("#addAgenciesDetailList .ls_list").find("tbody").find("tr").each(function(){
				var tdArr = $(this).children("td");
				//console.log(tdArr.html());
				/* console.log(tdArr.eq(1).html());*/
				tdArr.eq(0).css("text-align","left");
				tdArr.eq(1).css("text-align","right");
				tdArr.eq(2).css("text-align","right");
				tdArr.eq(3).css("text-align","right");
				tdArr.eq(4).css("text-align","right");
				tdArr.eq(5).css("text-align","center");
				tdArr.eq(6).css("text-align","center");
				tdArr.eq(7).css("text-align","center");
				tdArr.eq(8).css("text-align","center");
			});
			$("#addAgenciesDetailRowDiv").show(); 
			$("#addAgenciesDetailList .ls_list").show(); 
		}
	});
</script>
<!--额度追加历史-->
<div class="arch_sort" id = "addAgenciesDetailRowDiv" style="border: 0px;display:none;">
	<div class="dynamic-block" title="存出保证金明细" name="MfBusAgenciesDetailAction"
		data-sort="14" data-tablename="mf_bus_agencies_detail">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>存出保证金明细</span>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#addAgenciesDetailList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="addAgenciesDetailList" style="margin-top: 10px;">
				${addAgenciesDetailListHtml}
			</div>
		</div>
	</div>
</div>
