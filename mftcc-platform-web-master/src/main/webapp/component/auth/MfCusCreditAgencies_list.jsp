<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	var proTableId = '${param.formId}';
	var nodeNo = '${param.nodeNo}';
	var appNodeNo = '${param.appNodeNo}';
	$(function() {
        agenciesListInit();
	});
	function agenciesListInit() {
        $.ajax({
            url:webPath+"/mfCusCreditApply/getMfCusCreditAgenciesListAjax?creditAppId="+creditAppId+"&tableId="+proTableId,
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.ifHasList == "1"){
                    var html = data.htmlStr;
                    $("#mfCusCreditAgenciesList").empty().html(html);
                    //补充资料及最后一个审批岗位，允许调整授信合作银行

                }else{
                    $("#mfCusCreditAgenciesList").empty();
                }
                if(nodeNo.indexOf("credit_supplement")!=-1||nodeNo.indexOf("dep_manager")!=-1||appNodeNo.indexOf("collateral")!=-1||appNodeNo.indexOf("report")!=-1){
                    $("#agenciesAdd").show();
                    $("#breedAdd").show();
                    $("#mfCusCreditAgenciesList").find("a").show();
                    $("#mfBusBreedList").find("a").show();
                }else{
                    $("#agenciesAdd").hide();
                    $("#breedAdd").hide();
                    $("#mfCusCreditAgenciesList").find("a").hide();
                    $("#mfBusBreedList").find("a").hide();
                }
            }
        });
    }
    var agenciesInput = function () {
        top.openBigForm(webPath + "/mfCusCreditApply/agenciesInputView?creditAppId=" + creditAppId, "添加合作银行", function () {
            agenciesListInit();
        });
    }
     function agenciesDetail (obj, url) {
         if(url.substr(0,1)=="/"){
             url =webPath + url;
         }else{
             url =webPath + "/" + url;
         }
        top.openBigForm(url, "添加合作银行", function () {
            agenciesListInit();
            $.ajax({
                url:webPath+"/mfCusCreditApply/getMfBusBreedListAjax?creditAppId="+creditAppId+"&tableId="+proTableId1,
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.ifHasBreedList == "1"){
                        var html = data.htmlStr;
                        $("#mfBusBreedList").empty().html(html);
                        //补充资料及最后一个审批岗位，允许调整授信合作银行
                    }else{
                        $("#mfBusBreedList").empty();
                    }
                    if(nodeNo.indexOf("credit_supplement")!=-1||nodeNo.indexOf("dep_manager")!=-1||appNodeNo.indexOf("collateral")!=-1||appNodeNo.indexOf("report")!=-1){
                        $("#agenciesAdd").show();
                        $("#breedAdd").show();
                        $("#mfCusCreditAgenciesList").find("a").show();
                        $("#mfBusBreedList").find("a").show();
                    }else{
                        $("#agenciesAdd").hide();
                        $("#breedAdd").hide();
                        $("#mfCusCreditAgenciesList").find("a").hide();
                        $("#mfBusBreedList").find("a").hide();
                    }
                }
            });
        });
    }
    function deleteAgencies(obj, url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        alert("是否确定删除(会同时删除该合作银行下的业务品种授信额度)？",2,function(){
            $.ajax({
                type: "get",
                url: url,
                success: function(data) {
                    if (data.flag == "success") {
                        alert(top.getMessage("SUCCEED_DELETE"),1);
                        agenciesListInit();
                        $.ajax({
                            url:webPath+"/mfCusCreditApply/getMfBusBreedListAjax?creditAppId="+creditAppId+"&tableId="+proTableId1,
                            type:'post',
                            dataType:'json',
                            success:function(data){
                                if(data.ifHasBreedList == "1"){
                                    var html = data.htmlStr;
                                    $("#mfBusBreedList").empty().html(html);
                                }else{
                                    $("#mfBusBreedList").empty();
                                }
                                //补充资料及最后一个审批岗位，允许调整授信合作银行
                                if(nodeNo.indexOf("credit_supplement")!=-1||nodeNo.indexOf("dep_manager")!=-1||appNodeNo.indexOf("collateral")!=-1||appNodeNo.indexOf("report")!=-1){
                                    $("#agenciesAdd").show();
                                    $("#breedAdd").show();
                                    $("#mfCusCreditAgenciesList").find("a").show();
                                    $("#mfBusBreedList").find("a").show();
                                }else{
                                    $("#agenciesAdd").hide();
                                    $("#breedAdd").hide();
                                    $("#mfCusCreditAgenciesList").find("a").hide();
                                    $("#mfBusBreedList").find("a").hide();
                                }
                            }
                        });
                    } else {
                        alert(data.msg,0);
                    }
                },
                error: function() {
                    alert(top.getMessage("FAILED_DELETE"),0);
                }
            });
        });
    }
</script>
<!--收款计划信息 -->
<div class="row clearfix" id="cusCreditAgenciesListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>授信合作银行列表</span>
				<button id="agenciesAdd"  style="display: none" class="btn btn-link formAdd-btn"  onclick="agenciesInput();" title="新增"><i class="i i-jia3"></i></button>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCusCreditAgenciesList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfCusCreditAgenciesList">
				<dhcc:tableTag property="tablecuscreditAgenciesList" paginate="mfCusAgenciesCreditList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

