<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var cusNo = '${param.cusNo}';
    var appId = '${param.appId}';
    var nodeNo = '${param.nodeNo}';
    var pactId = '${param.pactId}';
    var fincId = '${param.fincId}';
    var busType = '${param.busType}';
    $(function() {
        $.ajax({
            url:webPath+"/mfChangeInfoRecord/findChangeInfoRecordList?appId="+appId+"&cusNo="+cusNo+"&nodeNo="+nodeNo+"&pactId="+pactId+"&fincId="+fincId+"&busType="+busType,
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    var html = data.tableHtml;
                    $("#changeInfoList").empty().html(html);
                }else{
                    $("#changeInfoListDiv").remove();
                }
            }
        });
    });
</script>
<style type="text/css">
	th {
		text-align: left !important;
	}
	.list-table-replan .ls_list tbody tr td {
		text-align: left !important;
	}
</style>
<!--收款计划信息 -->
<div class="row clearfix" id="changeInfoListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>变更记录</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#changeInfoList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="changeInfoList">
				<dhcc:tableTag property="tableCusChangeInfoList" paginate="changeInfoList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

