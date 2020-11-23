<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
var cusNo=$("input[name=cusNo]").val();
	$(function() {
		$.ajax({
			url:"/mfAssetManage/getMfBusFinAppList",
			type:'post',
			data:{cusNo:cusNo},
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#mfBusFinAppList").empty().html(html);
			}
		});
	});
</script>
<!--借据信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>借据信息</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusFinAppList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfBusFinAppList">
				<dhcc:tableTag property="tablemfbusfinapplist" paginate="mfBusFinAppList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>