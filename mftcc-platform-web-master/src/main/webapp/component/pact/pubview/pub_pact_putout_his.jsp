<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var hisTableId= '${param.formId}';
	$(function() {
		putoutHisList.init();
	});

    function fkDaYinPrint(){
        var url ;//将方式设置为还款
        url = '${webPath}/cwPrintTmplItem/choosePrintType?busType=fk&fincId='+fincId+"&cusNo="+cusNo+"&pactId="+pactId+"&appId="+appId+"&pactModelNo="+pactModelNo;
        top.addFlag = false;
        top.htmlStrFlag = false;
        top.createShowDialog(url,"选择打印方式",'50','50',function(){

        });
    }
</script>
<!-- 放款历史 -->
<div class="row clearfix" id="putout-his-block">
	<div class="dynamic-block" title="放款历史" name="putoutHisDiv">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#putoutHis">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="putoutHis" name="putoutHis"></div>
		</div>
	</div>
</div>
