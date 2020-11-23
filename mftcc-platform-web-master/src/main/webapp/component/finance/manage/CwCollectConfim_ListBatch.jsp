<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/finance/manage/js/CwCollectConfim_Batch.js"></script>
<script type="text/javascript" >
	var queryType = "${queryType}";
	var showName = "${showName}";
	function inputForCheck(obj,lk) {
		top.openBigForm(webPath + lk, showName, function () {
			window.location.reload();
		});
	}

	//批量到账确认
	function batchConfim(){
 //       window.top.alert("确定批量到账确认？",2,function(){
            var idStr = "";
            var actualAmtStr = "";
            var amtFlag = 0;
            $('.docList #tab tr').each(function () {
                var idVal = $(this).find("input[name='id']").val();
                idStr = idStr + "," + idVal;
                var actualAmtVal = $(this).find("input[name='actualReceivedAmt']").val();
				actualAmtVal = actualAmtVal.replace(/,/g,"");
                if(verify(actualAmtVal)){
                    actualAmtStr = actualAmtStr + "," + actualAmtVal;
                }else{
                	amtFlag = 1;
                }
            });
		if(amtFlag == 1){
			window.top.alert("实际到账金额校验失败！", 0);
			return false;
		}
            if(idStr==""){
                window.top.alert(top.getMessage("FIRST_SELECT_FIELD",showName + "的数据"), 0);
                return false;
            }else{
                idStr=idStr.substr(1);
                actualAmtStr=actualAmtStr.substr(1);
                LoadingAnimate.start();
                $.ajax({
                    type : "POST",
                    data:{
                        ids : idStr,
                        actualAmts : actualAmtStr
                    },
                    url : "${webPath}/cwCollectConfim/batchConfim",
                    dataType : "json",
                    success : function(data) {
                        LoadingAnimate.stop();
                        if(data.flag=="success"){
                            window.top.alert(data.msg,1);
                            myclose_click();
                        }else{
                            window.top.alert(data.msg,0);
                        }
                    },error : function(xmlhq, ts, err) {
                        loadingAnimate.stop();
                    }
                });
            }
 //       });
	}
    function verify(str){
        if(str.match(/^(:?(:?\d+.\d+)|(:?\d+))$/)){
            return true;
        }else{
            return false;
        }
    }
    $(function(){
        if(queryType == "2"){
            CwCollectConfim_Batch.init();
        }
    });
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<c:choose>
					<c:when test="${queryType=='2'}">
						<button type="button" class="btn btn-primary" onclick="CwCollectConfim_Batch.batchConfimGcdb();">确认</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-primary" onclick="batchConfim();">确认</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>${showName}列表</span>
		</div>
		<div class="docList content collapse in" id="docList" name="docList">
			${tableHtml}
		</div>
	</div>
</div>
</body>
</html>
