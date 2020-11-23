<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
	<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfBusAgencies.js"></script>
	<script type="text/javascript" >
        var agenciesId = '${agenciesId}';
        $(function(){
            LoadingAnimate.start();
            var table = "tablecus00001";
            var url = webPath+"/mfBusAgencies/findByPageAjax";
            if(null == agenciesId || ''== agenciesId){
                url = webPath+"/mfBusAgencies/findByPageAjax";
            }else{
                url = webPath+"/mfBusAgencies/findByPageAjax?agenciesId="+agenciesId;
			}
            myCustomScrollbar({
                obj:"#content",//页面内容绑定的id
                url:url,//列表数据查询的url
                tableId:"tablemfbusagenciesSelectList",//列表数据查询的table编号
                tableType:"thirdTableTag",//table所需解析标签的种类
                pageSize:30,//加载默认行数(不填为系统默认行数)
               // data:{cusType:cusType},//指定参数
                callback:function(){
                    LoadingAnimate.stop();
                }//方法执行完回调函数（取完数据做处理的时候）
            });
        });

        function choseAgencies(parm) {
            parm = parm.split("?")[1];
            var parmArray = parm.split("&");
            var agencies = new Object();
            agencies.agenciesId = parmArray[0].split("=")[1];
            agencies.agenciesName = parmArray[1].split("=")[1];
            $("input[name=parentAgenciesId]",parent.document).val(agencies.agenciesId);
           	$("input[name=parentAgenciesName]",parent.document).val(agencies.agenciesName);
            parent.dialog.get('agenciesDialog').close(agencies).remove();
        }

	</script>
</head>
<body>
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="search-div col-xs-5 pull-right">
				<div class="znsearch-div">
					<div class="input-group pull-right">
						<i class="i i-fangdajing"></i>
						<input type="text" class="form-control" id="filter_in_input" placeholder="上级资金机构名称">
						<span class="input-group-addon" id="filter_btn_search">搜索</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--页面显示区域-->
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;">
			</div>
		</div>
	</div>
</div>
</body>
</html>
