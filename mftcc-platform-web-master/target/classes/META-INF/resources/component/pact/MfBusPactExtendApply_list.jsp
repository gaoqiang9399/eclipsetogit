<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	var appId = '${param.appId}';
	var extendApplyTableId = '${param.formId}';
	$(function() {
        $.ajax({
            url:webPath+"/mfBusPact/getExendListHtmlAjax?appId="+appId+"&tableId="+extendApplyTableId,
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.ifHasList == "1"){
                    var html = data.tableData;
                    $("#mfBusPactExtendList").empty().html(html);
                }else{
                    $("#mfBusPactExtendList").empty();
                }
            }
        });
	});
     function extendDetail (obj, url) {
         top.window.openBigForm( webPath + url + "&appId="+appId,'非系统生成相关合同详情',function() {
         });
    }
</script>
<c:if test="${busModel != '12'}">
<!--收款计划信息 -->
<div class="row clearfix" id="pactExtendListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>非业务生成合同信息</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExtendList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfBusPactExtendList">
				<dhcc:tableTag property="tablepactExtendApplyList" paginate="mfBusPactExtendList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>
</c:if>

