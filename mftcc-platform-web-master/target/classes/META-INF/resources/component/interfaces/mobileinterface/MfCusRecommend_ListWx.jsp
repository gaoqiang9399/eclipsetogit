<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		    var agenciesUid="${agenciesUid}";
			$(function(){
 				var redDate=getNowFormatDate();
 				$("#redDate").val(redDate);
			    getPlanList();
			    getLoanAmt();
			});
			 //获取列表信息
			 function getPlanList(){
					var redDate=$("#redDate").val().replace(/-/g,"");
					var endDate=$("#endDate").val().replace(/-/g,"");
				 	myCustomScrollbar({
					obj : "#content", //页面内容绑定的id
					url : webPath+"/mfCusRecommend/findWxByPageAjax", //列表数据查询的url
					tableId : "tablerecommendwx0002", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:30, //加载默认行数(不填为系统默认行数)
					//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
					data:{"agenciesUid":agenciesUid,"redDate":redDate,"endDate":endDate},
				    });
			  };
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。-->
					<div class="btn-div">
						日期：
						<input title="日期" name="redDate" id="redDate"
								value=""
								datatype="6" mustinput="1" maxlength="10"
								onfocus="fPopUpCalendarDlg({choose:getCountAndLoanAmt})"
								onmousedown="enterKey()" onkeydown="enterKey();"
								class="datelogo BOTTOM_LINE" type="text">-
						<input title="日期" name="endDate" id="endDate"
						value=""
						datatype="6" mustinput="1" maxlength="10"
						onfocus="fPopUpCalendarDlg({choose:getCountAndLoanAmt})"
						onmousedown="enterKey()" onkeydown="enterKey();"
						class="datelogo BOTTOM_LINE" type="text">
						&nbsp;&nbsp;
						共有：<span id="perCounts">0</span>&nbsp;&nbsp;
						成功借款：<span id="loanAmt">0</span>&nbsp;&nbsp;
						应付佣金：<input type="text" name="brokerage" id="brokerage" onkeyup="value=value.replace(/[^\d.]/g,'')"/>&nbsp;&nbsp;
						已打款金额：<span id="remittance">0</span>&nbsp;&nbsp;
						<button type="button" class="btn btn-primary" onclick="remittanceMoney();">打款</button>
					</div>
					
					<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
	<script type="text/javascript" >
		function getCountAndLoanAmt(){
			getPlanList();
			getLoanAmt();
		}
		function getLoanAmt(){
			var redDate=$("#redDate").val().replace(/-/g,"");
			var endDate=$("#endDate").val().replace(/-/g,"");
			if(parseInt(endDate)<parseInt(redDate)){
				$("#endDate").val("");
				alert(top.getMessage("FAILED_OPERATION","结束日期不能大于开始日期"),2);
				return false;
			}
			$.ajax({
				url:webPath+'/mfCusRecommend/getPutoutAmtAndNumAjax',
				data:{"agenciesUid":agenciesUid,"redDate":redDate,"endDate":endDate},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},
				success:function(data){
					if(data.flag=="success"){
						 $("#perCounts").html(data.perCounts+"人");
						 $("#loanAmt").html(data.putoutAmtSum+"元");
						 $("#remittance").html(data.remittance+"元");
					}else{
						window.top.alert(data.msg, 0);
					}
				},error:function(data){
					 LoadingAnimate.stop();
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			}); 
		}
	 function remittanceMoney(){
	   var brokerage=$("#brokerage").val();
	   var redDate=$("#redDate").val().replace(/-/g,"");
	   var endDate=$("#endDate").val().replace(/-/g,"");
	   if(""==brokerage){
	   		alert(top.getMessage("NOT_FORM_EMPTY","应付的佣金"), 2);
	   		return;
	   }
		$.ajax({
			url:webPath+'/mfCusRecommend/remittanceMoneyAjax',
			data:{"agenciesUid":agenciesUid,"brokerage":brokerage,"redDate":redDate,"endDate":endDate},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				if(data.flag=="success"){
					 if(data.mfCusRecommendList.length>0){
						 $("#remittance").html(brokerage+"元");
						 alert(top.getMessage("SUCCEED_OPERATION",data.msg),2);
						 updateTableData();//重新加载列表数据
					 }else{
					 	window.top.alert("没有可打款的数据", 0);
					 }
				}else{
					window.top.alert(data.msg, 0);
				}
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		}); 
	}
	function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
	</script>
</html>
