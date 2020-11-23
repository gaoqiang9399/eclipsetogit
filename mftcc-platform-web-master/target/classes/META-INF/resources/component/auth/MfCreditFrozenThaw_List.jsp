<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var creditAppId = '${param.creditAppId}';
    var hisTableId = '${param.formId}';
    function initFrozenThaw(){
        $.ajax({
            url:webPath+"/mfCreditIntentionApply/getFrozenThawListAjax?creditAppId="+creditAppId+"&tableId="+hisTableId,
            type:'post',
            dataType:'json',
            success:function(data){
                var html = data.htmlStr;
                if(html==''){
                    $('#creditFrozenThaw-block').addClass('hidden');
                }else{
                    $('#creditFrozenThaw-block').removeClass('hidden');
                    $("#mcreditFrozenThawList").empty().html(html);
                }
            }
        });
    }
    $(function() {
        initFrozenThaw()
    });
</script>
<!--还款历史信息 -->
<div class="row clearfix hidden" id="creditFrozenThaw-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="creditFrozenThaw">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 冻结/解冻历史
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mcreditFrozenThawList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mcreditFrozenThawList">
				<dhcc:tableTag property="tablefrzoenBase" paginate="mfCreditFrozenThawList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>


