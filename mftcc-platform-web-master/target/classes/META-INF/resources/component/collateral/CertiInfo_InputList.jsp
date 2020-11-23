<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<title>列表表单</title>
	<script type="text/javascript" src="${webPath}/component/collateral/js/CertiInfo_InputList.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" >
        var appId = '${appId}';
        var cusNo = '${cusNo}';
        var pactId = '${pactId}';
        var flag = '${flag}';
         var nodeNo = '${nodeNo}';
         var type = '${type}';
         var collateralType = '${collateralType}';
        $(function(){
            CertiInfo_InputList.init();
        });
        function doSubmit(){
            $.ajax({
                url: webPath + "/certiInfo/submitAppr?appId=" + appId + "&type=" + type + "&collateralType="+collateralType,
                success: function (data) {
                    if (data.flag == "success") {

                        DIALOG.msg(data.msg,function () {
                            top.addFlag = true;
                            top.flag=true;
                            CertiInfo_InputList.myclose(null);
                        });

                    } else {
                        DIALOG.error(data.msg,function () {});
                    }
                }, error: function () {
                    DIALOG.error(data.msg,function () {});
                }
            });
        }
         function doSkip(){
            DIALOG.confirm("是否确认已受理抵质押落实?",function(){
                $.ajax({
                    url:webPath+"/mfBusApply/commitBusProcessAjax?appId="+appId,
                    success:function(data){
                        if(data.flag=="success"){
                            top.addFlag = true;
                            top.flag=true;
                            CertiInfo_InputList.myclose(null);
                        }else{
                            alert(data.msg,0);
                        }
                    }
                });
            });
        };
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<%--<div class="bootstarpTag fourColumn">--%>
				<%--<div class="form-tips">说明：</div>--%>
			<%--</div>--%>
			<div class="list-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>本次需要办理抵质押的列表</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#pledgeBaseInfoList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="pledgeBaseInfoList" name="pledgeBaseInfoList">
					<dhcc:tableTag property="tablepledgeBaseInfoList" paginate="pledgeBaseInfoList" head="true"></dhcc:tableTag>
				</div>
			</div>
			<%--<div class="list-table">--%>
				<%--<div class="title">--%>
					<%--<span><i class="i i-xing blockDian"></i>权证登记列表</span>--%>
					<%--<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receAccountList">--%>
						<%--<i class="i i-close-up"></i><i class="i i-open-down"></i>--%>
					<%--</button>--%>
				<%--</div>--%>
				<%--<div class="content collapse in" id="receAccountList" name="receAccountList">--%>
					<%--<dhcc:tableTag property="tablecertiInfoInputList" paginate="certiInfoList" head="true"></dhcc:tableTag>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
		</div>
	</div>

	<div class="formRowCenter">
	<%--<dhcc:thirdButton value="受理" action="受理"  onclick="doSkip();"></dhcc:thirdButton>--%>
		<dhcc:thirdButton value="提交" action="提交"  onclick="doSubmit();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>