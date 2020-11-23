<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/mfBusCollateralPact/findByPageAjax", //列表数据查询的url
				tableId : "tablecuscollateralpactlist", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });

            function getDetailPage(obj,url){
                if(url.substr(0,1)=="/"){
                    url =webPath + url;
                }else{
                    url =webPath + "/" + url;
                }
                top.LoadingAnimate.start();
                window.location.href=url;
                event.stopPropagation();
            }

            function getTemplateBizConfigId(obj , id){
                uid = id.substring(10,27);
                cusNo = id.substring(34,47);
                appId = id.substring(54);
                $.ajax({
                    url:webPath+"/mfBusCollateralDetailRel/getTemplateBizConfigIdAjax",
                    data:{id:uid},
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        if(data.flag == "success"){
                            printFollowPactFile(data.templateBizConfigId ,data.repayDetailId,cusNo,appId);
                        }else{
                            window.top.alert(data.msg,0);
                        }
                    },error:function(){
                        window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
                    }
                });
            }
            function printFollowPactFile (templateBizConfigId , repayDetailId,cusNo,appId) {//点击查看按钮或者合同打印
                var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
                var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
                var temParm = 'cusNo=' + cusNo + '&appId=' + appId +  '&repayDetailId=' + repayDetailId;
                $.ajax({
                    url : url + "&" + temParm,
                    data : {
                        "returnUrl" : backUrl
                    },
                    type : 'post',
                    dataType : 'json',
                    beforeSend : function() {
                        LoadingAnimate.start();
                    },
                    complete : function() {
                        LoadingAnimate.stop();
                    },
                    error : function() {
                        alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                    },
                    success : function(data) {
                        var poCntObj = $.parseJSON(data.poCnt);
                        mfPageOffice.openPageOffice(poCntObj);
                    }
                });
            };
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="applyInsert();">进件</button>
					</div>
					-->
					<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
					<div class="btn-div">
						<div class="col-md-10 text-center">
							<span class="top-title">从合同查询</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=担保方式/从合同号"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
