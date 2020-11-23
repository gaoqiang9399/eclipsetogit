<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
	var cusNo='${param.agenciesUid}';
	$(function(){
		  jQuery.ajax({
	            url:webPath + "/mfCommissionChangeRecord/getListByCusNoAjax",
	            data:{cusNo:cusNo},
	            type:"post",
	            success:function(data){
	                if(data.flag == "success"){
	                	$("#commissionChangeRecordList").html(data.commissionChangeRecordListHtml);
	                	$("#commissionChangeRecordList .ls_list").show(); 
	                }else{
	                	alert(data.msg,3);
	                }
	            },
	            error:function(){
	            	alert(top.getMessage("ERROR_SELECT"),0);
	            }
	        });
	});
	function getListPage(){
		top.openBigForm(webPath+"/mfCommissionChangeRecordDetail/getListPageByCusNo?cusNo=" + cusNo,"分润记录明细", function(){
 		});	
	}
</script>
<!--分润记录-->
<div class="arch_sort" id = "commissionChangeRecordRowDiv" style="border: 0px;">
	<div class="dynamic-block" title="分润记录" name="MfCommissionChangeRecordAction"
		data-sort="14" data-tablename="mf_commission_change_record">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>分润记录</span>
				<button class="btn btn-link formAdd-btn" onclick="getListPage();" title="分润记录明细">
					分润记录明细
				</button>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#commissionChangeRecordList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="commissionChangeRecordList" style="margin-top: 10px;">
			</div>
		</div>
	</div>
</div>

