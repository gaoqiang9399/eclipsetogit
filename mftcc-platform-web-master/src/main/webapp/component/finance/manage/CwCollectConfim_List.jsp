<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" >
	var opNo = '${opNo}';
	var queryType = '${queryType}';
	var showName = '${showName}';
	$(function(){
	    var tableId = "tablepact1001";
	    if(queryType == "2"){
            tableId = "tablepact1001_GCDB";
        }
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/cwCollectConfim/findByPageAjax?opNo=" + opNo + "&queryType="+queryType,//列表数据查询的url
			tableId:tableId,//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			topHeight:0,
			pageSize:30,//加载默认行数(不填为系统默认行数)
			callback:function(){
				$('#content tbody tr').each(function(index,item){//给编辑按钮绑定单击事件
					var status = $(item).find("input[type=hidden][name=status]").val();
					if(status != "1" && queryType != "2"){
						$(item).find("input[type=checkbox]").attr("disabled","disabled")
					}
				})
			}
		});
	});

	function inputForCheck(obj,lk) {
		top.openBigForm(webPath + lk, showName, function () {
            updateTableData();
		});
	}

	function getDetailPage(obj,lk) {
		top.openBigForm(webPath + lk, "到账详情", function () {
            updateTableData();
		});
	}

	//批量到账确认
	function batchConfimPage(){
		var idStr = "";
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
		    var statusVal =  $(this).parents("tr").find("[name='status']").val();
		    if(statusVal == "1"){
                val = this.value.split('&') [0] ;
                idStr=idStr+","+val.split("=")[1];
            }
		});
		if(idStr==""){
			window.top.alert(top.getMessage("FIRST_SELECT_FIELD","需要"+showName+"的数据"), 0);
			return false;
		}else{
//			window.top.alert("确定批量到账确认？",2,function(){
				idStr=idStr.substr(1);
				top.openBigForm("${webPath}/cwCollectConfim/getBatchConfimListPage?ids="+idStr + "&queryType=" + queryType + "&showName" + showName, "批量" + showName, function () {
                    updateTableData();
				});
//			});
		}
	}
	//批量下载
	function batchDownload(){
		var templateId = "";
		var appId = "";
        var paramMap = new Object();
        var url = webPath + "/mfTemplateBizConfig/getBizConfigsForListAjax";
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
            var appIdVal =  $(this).parents("tr").find("[name='appId']").val();
            appId = appId + "," + appIdVal
            $.ajax({
                url :url,
                data : {
                    "nodeNo" : "charge-fee",
                    "temBizNo" : appIdVal,
                    "paramMap":JSON.stringify(paramMap)
                },
                type : 'post',
                dataType : 'json',
                async: false,
                error : function() {

                },
                success : function(data) {
                    if (data.flag == "success") {
                        var list = data.list;
						for(var i= 0 ;i< list.length; i++){
                            templateId=templateId + "," + list[i].templateBizConfigId;
						}
                    }
                }
            });
		});
        if(appId==""){
            window.top.alert(top.getMessage("FIRST_SELECT_FIELD","需要下载缴款通知书的数据"), 0);
            return false;
        }
		if(templateId==""){
			window.top.alert("所选数据没有可以下载的缴款通知书", 0);
			return false;
		}else{
            try{
                var url = webPath+"/docUpLoad/getZipFileDownloadForSelectTemplet?docBizNo="+templateId + "&showName=缴款通知书打包文件";
                var elemIF = document.createElement("iframe");
                elemIF.src = url;
                elemIF.style.display = "none";
                document.body.appendChild(elemIF);
            }catch(e){

            }
		}
	}
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div style="display:none;">
				<input name="cusName" type="hidden"></input>
				<input name="cusNo" type="hidden"></input>
			</div>
			<div class="btn-div">
					<button type="button" class="btn btn-primary" onclick="batchConfimPage();">批量${showName}</button>
				    <c:if test="${queryType == '2'}">
						<button type="button" class="btn btn-primary" onclick="batchDownload();">批量下载</button>
					</c:if>
			</div>
			<div class="search-div">
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
	filter_dic = [
		{
			"optCode" : "collectTime",
			"optName" : "费用确认日期",
			"dicType" : "date"
		} ,{
			"optName": "状态",
			"parm": ${statusJsonArray},
			"optCode":"status",
			"dicType":"y_n"
		},
	];
</script>
</html>
