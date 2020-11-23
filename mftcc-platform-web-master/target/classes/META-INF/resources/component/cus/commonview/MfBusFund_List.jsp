<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
	var cusNo='${param.cusNo}';
	$(function(){
		//$("#addMfBusFundRowDiv").show();
		var mfBusFundListSize = ${mfBusFundList};
		if(mfBusFundListSize != 0){
			$("#addMfBusFundList .ls_list").find("thead").find("tr").each(function(){
				var tdArr = $(this).children("th");
				
				tdArr.eq(0).css("text-align","center");
				tdArr.eq(1).css("text-align","center");
				tdArr.eq(2).css("text-align","center");
				tdArr.eq(3).css("text-align","center");
				tdArr.eq(4).css("text-align","center");
			});
			$("#addMfBusFundList .ls_list").find("tbody").find("tr").each(function(){
				var tdArr = $(this).children("td");
				
				tdArr.eq(0).css("text-align","left");
				tdArr.eq(1).css("text-align","center");
				tdArr.eq(2).css("text-align","center");
				tdArr.eq(3).css("text-align","center");
				tdArr.eq(4).css("text-align","center");
			});
			
			$("#addMfBusFundList .ls_list").show(); 
		}
	});
</script>
<!--基金公司开户明细-->
<div class="arch_sort" id = "addmfBusFundRowDiv" style="border: 0px;">
	<div class="dynamic-block" title="基金公司开户明细" name="MfBusFundAction"
		data-sort="14" data-tablename="mf_bus_fund">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>基金公司开户明细</span>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#addMfBusFundList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="addMfBusFundList" style="margin-top: 10px;">
				${addMfBusFundListHtml}
			</div>
		</div>
	</div>
</div>
