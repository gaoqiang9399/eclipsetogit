<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var cusNo = '${param.cusNo}';
    var appId = '${param.appId}';
    var sign = "detail";//详情
    $(function() {
        MfBusCoborrList.showCocoborrPactList();
    });
</script>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusCoborrList.js?v=${cssJsVersion}"></script>
<style type="text/css">
	th {
		text-align: left !important;
	}
	.list-table-replan .ls_list tbody tr td {
		text-align: left !important;
	}
</style>
<!--收款计划信息 -->
<div class="row clearfix" id="applyCusBorrowerListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>共同借款人</span>
              <dhcc:pmsTag pmsId="apply-gjr-add-info">
				<button class="btn btn-link formAdd-btn"  onclick="MfBusCoborrList.selectCoborrNumPactList()" title="新增"><i class="i i-jia3"></i></button>
			  </dhcc:pmsTag>
			</div>
			<div class="content margin_left_15 collapse in" id="applyCusBorrowerList">
				<dhcc:tableTag property="tableapplyCusBorrowerList" paginate="tableapplyCusBorrowerList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

