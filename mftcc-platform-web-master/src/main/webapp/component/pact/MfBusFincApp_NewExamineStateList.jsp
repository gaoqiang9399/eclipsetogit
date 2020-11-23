<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_NewExamineStateList.js"></script>
	<script type="text/javascript" >
		var pasNoStr="";
        $(function(){
            MfBusFincApp_NewExamineStateList.init();
        });
	</script>
	<style type="text/css">
		.change-td-color-0{
			color:red !important;
		}
		.ui-dialog-title{
			font-size: medium;/*修改指派弹框的标题字体样式*/
		}
	</style>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<dhcc:pmsTag pmsId="query_examine_info">
						<%--<button type="button" class="btn btn-primary" onclick="MfBusFincApp_NewExamineStateList.addExamineTask('${webPath}/mfBusFincApp/examineTaskInsert');">新增</button>--%>
						<button type="button" class="btn btn-primary" onclick="MfBusFincApp_NewExamineStateList.assignExamineTaskBatch();">指派</button>
						<button type="button" class="btn btn-primary" onclick="MfBusFincApp_NewExamineStateList.ignoreExamineTaskBatch('${webPath}/mfExamineHis/ignoreExamineTask');">忽略</button>
					</dhcc:pmsTag>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">保后检查任务</span>
				</div>
			</div>


			<div class="search-div" id="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;">
			</div>
		</div>
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic =[{ "optCode" : "loanBal",
        "optName" : "借据余额",
        "dicType" : "num"
    },{
        "optName": "任务状态",
        "parm": ${fincExamineStsJsonArray},
        "optCode":"pasSts",
        "dicType":"y_n"
    },
        {
            "optName": "创建日期",
            "optCode":"createDate",
            "dicType":"date"
        },
        {
            "optName": "最晚检查日期",
            "optCode":"dueDate",
            "dicType":"date"
        }
    ];

</script>
</html>