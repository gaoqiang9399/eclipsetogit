<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincId = '${param.fincId}';
	var tableId = '${param.tableId}';
	$(function() {
        $.ajax({
            url:webPath+"/mfBusFincApp/getCapitalDetaiListByFincIdAjax?fincId="+fincId+"&tableId=tablecapitaldetailfinc",
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.flag=="success"){
                    var html = data.htmlStr;
                    $("#capitalList").empty().html(html);
                    $("#capitalList").show();

                }else{
                    $("#capitalList").empty().html(data.msg);
                }

            }
        });
	});
</script>
<!--收款计划信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#capitalList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="capitalList">
				<dhcc:tableTag property="tablecapitaldetailfinc" paginate="capitalList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

