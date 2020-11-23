<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var cusNo = '${param.cusNo}';
    var balTableId = '${param.formId}';
    $(function() {
		if(BussNodePmsBiz.checkPmsBiz("cus-edit-SubjectData")){
            $.ajax({
                url: webPath+'/cusFinSubjectData/getAttentionListAjax?cusNo='+cusNo+'&tableId='+balTableId,
                success:function(data){
                    if(data.flag=="success"){
                        var html = data.htmlStr;
                        $("#subjectData").empty().html(html);
                    }
                },error:function(){
                    alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                }
            });
		}else{  //当科目余额权限被控制时，重点科目余额信息也不显示
		    $("#cus-subject-block").remove();
		}
    });
</script>
<!--  科目余额信息 -->
<div class="row clearfix" id="cus-subject-block">
	<div class="dynamic-block" title="科目余额信息" name="subjectBal">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#subjectData">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="subjectData" name="subjectData"></div>
		</div>
	</div>
</div>