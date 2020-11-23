<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
	var cusNo='${param.cusNo}';
	$(function(){
		//$("#addMfBusFundDetailRowDiv").show();
		var mfBusFundDetailListSize = ${mfBusFundDetailList};
		if(mfBusFundDetailListSize != 0){
			$("#addMfBusFundDetailList .ls_list").find("thead").find("tr").each(function(){
				var tdArr = $(this).children("th");
				
				tdArr.eq(0).css("text-align","center");
				tdArr.eq(1).css("text-align","center");
				tdArr.eq(2).css("text-align","right");
				tdArr.eq(3).css("text-align","right");
				tdArr.eq(4).css("text-align","center");
				tdArr.eq(5).css("text-align","center");
				tdArr.eq(6).css("text-align","center");
				tdArr.eq(7).css("text-align","center");
			});
			$("#addMfBusFundDetailList .ls_list").find("tbody").find("tr").each(function(){
				var tdArr = $(this).children("td");
				
				tdArr.eq(0).css("text-align","left");
				tdArr.eq(1).css("text-align","center");
				tdArr.eq(2).css("text-align","right");
				tdArr.eq(3).css("text-align","right");
				tdArr.eq(4).css("text-align","center");
				tdArr.eq(5).css("text-align","center");
				tdArr.eq(6).css("text-align","center");
				tdArr.eq(7).css("text-align","center");
				tdArr.eq(8).css("text-align","center");
			});
			
			$("#addMfBusFundDetailList .ls_list").show(); 
		}
	});
</script>
<!--基金认购明细-->
<div class="arch_sort" id = "addmfBusFundDetailRowDiv" style="border: 0px;">
	<div class="dynamic-block" title="基金认购明细" name="MfBusFundDetailAction"
		data-sort="14" data-tablename="mf_bus_fund_detail">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>基金认购明细</span>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#addMfBusFundDetailList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="addMfBusFundDetailList" style="margin-top: 10px;">
				${addMfBusFundDetailListHtml}
			</div>
		</div>
	</div>
</div>
