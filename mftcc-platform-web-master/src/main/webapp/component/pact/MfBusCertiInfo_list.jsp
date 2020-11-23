<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${webPath}/component/collateral/js/CertiInfo_InputList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var appId = '${param.appId}';
	var certiInfoTableId = '${param.formId}';
	$(function() {
        $.ajax({
            url:webPath+"/certiInfo/getCertiListHtmlAjax?appId="+appId+"&tableId="+certiInfoTableId,
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.ifHasList == "1"){
                    var html = data.tableData2;
                    $("#certiInfoListDiv").show();
                    $("#certiInfoList").empty().html(html);
                    var checkShouli=BussNodePmsBiz.checkPmsBiz("certiinfo_shouli");
                    if(!checkShouli){
                        $("#certiInfoList").find(".abatch").hide();
                    }
                }else{
                    $("#certiInfoList").empty();
                    $("#certiInfoListDiv").hide();
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
<div class="row clearfix" id="certiInfoListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>抵质押落实详情</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#certiInfoList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="certiInfoList">
				<dhcc:tableTag property="tablepledgeBaseInfoList" paginate="tablepledgeBaseInfoList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>
</c:if>

