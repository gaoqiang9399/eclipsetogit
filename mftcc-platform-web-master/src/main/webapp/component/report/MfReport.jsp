<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/component/include/common.jsp"%>
<%@ page import="app.component.report.entity.MfReportFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<%
 String layout = "layout/view";
 List<MfReportFilter> list = new ArrayList<MfReportFilter>();
 list  = (List)request.getAttribute("dataList");
 int len = list.size();
 %>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<link id="BS-factor" rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor${skinSuffix}.css" />
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${webPath}/UIplug/iconmoon/style.css" />
<script type="text/javascript" src="${webPath}/<%=layout%>/js/zl.js?v=${cssJsVersion}"></script>
<%--滚动条js 和鼠标滚动事件js--%>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<%--滚动样式--%>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
<link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/report/js/MfReport.js"></script>
<link id="MfQueryEntrance" rel="stylesheet" href="/factor/component/query/css/MfQueryEntrance${skinSuffix}.css?v=${cssJsVersion}" />
<style>
	i{
		color:#31B6CE;
	}
</style>
<script type="text/javascript">	
	<!-- 振凯加的,暂时先不动  --> 
	var webPath = '${webPath}';
	var tipsTimeoutId;
	var _showTips = function (obj) {
		top.LoadingAnimate.stop();
		var d = dialog({
			id : "oaInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "3px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};
	function openTuDemo(parm,title){
		if (parm=="1" || parm=="2" || parm=="3" || parm=="4" || parm=="5" || parm=="6") {
			var url = webPath + '/component/report/MfReportDemo.jsp?parm='+parm;
			top.openBigForm(url,title,function(){},'80','85');
			return;
		}
		var target  = event.target || event.srcElement;
		_showTips(target);
		return;
	};
	function openReport(parm,title){
		var url= '/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=3e008f6ddf0419ba884d877ee6bf3e92&flagA=0&flagB=0&flagC=0';
		switch(parm){
			case "1":
				break;
			case "2":
				url='/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=ddbd996a0fd8e20e5c8ea732449552a6&flagA=0&flagB=0&flagC=0';
				break;
			case "3":
				url='/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=9e8526aa820e46a918f9528f2cd5585e&flagA=0&flagB=0';
				break;
			case "4":
				url='/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=6cd0ac6bf19de84a5bc1b865e520a3a2&flagA=0';
				break;
			case "5":
				url='/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=cd9adab761dfa2cf6f7fe9b379159fe7&flagA=0&flagB=0';
				break;
			default :
				var target  = event.target || event.srcElement;
				_showTips(target);
				return false;
		}
		top.openBigForm(url,title,function(){});
	};
	function openJIanGuan(parm,title){
		var url = webPath + '/component/report/MfReportDemo2.jsp?parm='+parm;		
		top.openBigForm(url,title,function(){},'85','85');
	};
	<!-- 结束  -->
	<!-- 重新开始写  -->
	$(function(){
		$("#addQuery").click(function(){
			top.openBigForm(webPath+"/mfReportFilter/getFilter","查询设置","78","72");
		});
		
		<% for(int i=0;i<len;i++){ 
			String queryClass = list.get(i).getQueryClass();
			String queryName = list.get(i).getQueryName();
			String queryDesc = list.get(i).getQueryDescript();
			String id = list.get(i).getId();
		%>
			var queryClass = '<%=queryClass%>';
			var queryName = '<%=queryName%>';
			var queryDesc = '<%=queryDesc%>';
			var id = '<%=id%>';
			var cusClass = '<i class="i i-ren2 "></i>';
			var busClass = '<i class="i i-bi1 "></i>';
			var moneyClass = '<i class="i i-rili "></i>';
			var pledgeClass = '<i class="i i-fangdajing "></i>';
			if(queryClass == 'cus'){
				var htmlStr ='<div class="col-xs-3 column id="'+id+'" onclick="openThis('+"'"+id+"'"+')"><div class="box-content-bg"><div class="box-content-sm query">'+cusClass
							+'<div class="info-box-content"><p>'+queryName+'<p class="content-des">'+queryDesc+'</p></div></div></div></div>';
			}else if(queryClass == 'bus'){
				var htmlStr ='<div class="col-xs-3 column id="'+id+'" onclick="openThis('+"'"+id+"'"+')"><div class="box-content-bg"><div class="box-content-sm query">'+busClass
							+'<div class="info-box-content"><p>'+queryName+'<p class="content-des">'+queryDesc+'</p></div></div></div></div>';
			}else if(queryClass == 'finc'){
				var htmlStr ='<div class="col-xs-3 column id="'+id+'" onclick="openThis('+"'"+id+"'"+')"><div class="box-content-bg"><div class="box-content-sm query">'+moneyClass
							+'<div class="info-box-content"><p>'+queryName+'<p class="content-des">'+queryDesc+'</p></div></div></div></div>';
			}else if(queryClass == 'pledge'){
				var htmlStr ='<div class="col-xs-3 column id="'+id+'" onclick="openThis('+"'"+id+"'"+')"><div class="box-content-bg"><div class="box-content-sm query">'+pledgeClass
							+'<div class="info-box-content"><p>'+queryName+'<p class="content-des">'+queryDesc+'</p></div></div></div></div>';
			}
				$(".addQuery").before(htmlStr);
		<%}%>
		
	});
		function openThis(id){
			$.ajax({
				type:"get",
				url:webPath+"/mfReportFilter/getById?id="+id,
				success: function(data){
					var query_content = data.query_content;
					var query_name = data.query_name;
					var query_class = data.query_class;
					if(query_class == "cus"){
						top.openBigForm(webPath+"/mfReportCustomer/getCusList?ajaxData="+query_content,query_name,function(){},'85','85');	
					}else if(query_class == "bus"){
						top.openBigForm(webPath+"/mfReportCustomer/getBusList?ajaxData="+query_content,query_name,function(){},"85","85");
					}else if(query_class == "finc"){
						top.openBigForm(webPath+"/mfReportCustomer/getFincList?ajaxData="+query_content,query_name,function(){},"85","85");
					}else if(query_class == "pledge"){
						top.openBigForm(webPath+"/mfReportCustomer/getPledgeList?ajaxData="+query_content,query_name,function(){},"85","85");
					}
				}
			});	
		}
		function openBase(id){
			switch (id) {
				case "cusBaseInfo":
					top.openBigForm(webPath+"/mfReportCustomer/getCusList","客户基本信息查询",function(){} ,"85","85");
					break;
				case "daikuanzonghe":
					top.openBigForm(webPath+"/mfReportCustomer/getBusList","贷款综合查询",function(){},"85","85");
					break;
				case "fanDetail":
					top.openBigForm(webPath+"/mfReportCustomer/getFincList","放款统计明细查询",function(){},"85","85");
					break;
				case "yapin":
					top.openBigForm(webPath+"/mfReportCustomer/getPledgeList","押品基本信息查询",function(){},"85","85");
					break;
				default:
					var target  = event.target || event.srcElement;
					_showTips(target);
					break;
			}
		}
		<!-- 重写结束  -->
</script>

<body>
<div class="container">
	<div class="row clearfix report-body reportName">
		<div class="report-title">台账查询</div>
		<div class="col-xs-3 column"  id="cusBaseInfo" onclick="openBase('cusBaseInfo');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-ren2"></i>
	          		<div class="info-box-content">
	           			<p>客户基本信息查询</p>
	           			<p class="content-des">查询平台登记的客户情况</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="daikuanzonghe" onclick="openBase('daikuanzonghe');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-bi1"></i>
	          		<div class="info-box-content">
	           			<p>贷款综合查询</p>
	           			<p class="content-des">查询平台综合贷款情况</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="fanDetail" onclick="openBase('fanDetail');">
			<div class="box-content-bg">
				<div  class="box-content-sm">
	           		<i class="i i-rili"></i>
	          		<div class="info-box-content">
	           			<p>放款统计明细查询</p>
	           			<p class="content-des">查询放款明细情况</p>
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="yapin" onclick="openBase('yapin');">
			<div class="box-content-bg">
				<div  class="box-content-sm">
	           		<i class="i i-fangdajing"></i>
	          		<div class="info-box-content">
	           			<p>押品基本信息查询</p>
	           			<p class="content-des">查询押品基本信息</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="pactSts" onclick="openBase('pactSts');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-bi1"></i>
	          		<div class="info-box-content">
	           			<p>合同状态查询</p>
	           			<p class="content-des">查询合同状态</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column"  id="bussStage" onclick="openBase('bussStage');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-ren2"></i>
	          		<div class="info-box-content">
	           			<p>贷款办理阶段查询</p>
	           			<p class="content-des">贷款办理阶段查询 </p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="classify" onclick="openBase('classify');">
			<div class="box-content-bg">
				<div  class="box-content-sm">
	           		<i class="i i-fangdajing"></i>
	          		<div class="info-box-content">
	           			<p>客户分类查询</p>
	           			<p class="content-des">查询客户分类信息</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="bussRecycle" onclick="openBase('bussRecycle');">
			<div class="box-content-bg">
				<div  class="box-content-sm">
	           		<i class="i i-rili"></i>
	          		<div class="info-box-content">
	           			<p>业务回收明细查询</p>
	           			<p class="content-des">查询业务回收明细</p>
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="bussFee" onclick="openBase('bussFee');">
			<div class="box-content-bg">
				<div  class="box-content-sm">
	           		<i class="i i-fangdajing"></i>
	          		<div class="info-box-content">
	           			<p>业务费用情况查询</p>
	           			<p class="content-des">查询业务费用情况</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column"  id="expectRepay" onclick="openBase('expectRepay');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-rili"></i>
	          		<div class="info-box-content">
	           			<p>预期还款查询</p>
	           			<p class="content-des">查询预期还款</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="bussExpire" onclick="openBase('bussExpire');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-bi1"></i>
	          		<div class="info-box-content">
	           			<p>业务到期查询</p>
	           			<p class="content-des">查询业务到期</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column"  id="bussBal" onclick="openBase('bussBal');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-ren2"></i>
	          		<div class="info-box-content">
	           			<p>业务余额情况查询</p>
	           			<p class="content-des">查询业务余额情况</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="overdueBussDetail" onclick="openBase('overdueBussDetail');">
			<div class="box-content-bg">
				<div  class="box-content-sm query">
	           		<i class="i i-bi1"></i>
	          		<div class="info-box-content">
	           			<p>逾期业务明细查询</p>
	           			<p class="content-des">查询逾期业务明细</p> 
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column" id="bussGua" onclick="openBase('bussGua');">
			<div class="box-content-bg">
				<div  class="box-content-sm">
	           		<i class="i i-fangdajing"></i>
	          		<div class="info-box-content">
	           			<p>业务反担保信息查询</p>
	           			<p class="content-des">查询业务反担保信息</p>
	           		</div>
	           	</div>
	        </div>
		</div>
		<div class="col-xs-3 column addQuery">
			<div class="box-content-bg">
				<div  class="box-content-sm" id="addQuery">
	           		<i class="i i-jia1"></i>
	          		<div class="info-box-content">
	           			<p>&nbsp;</p>
	           			<p class="content-des">&nbsp;</p>
	           		</div>
	           	</div>
	        </div>
		</div>
	</div>
	<!-- 台账查询结束 --> 
	<div class="row clearfix report-body">
		<div class="report-title">统计报表</div>
		<div class="rotate-body">
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac1"  onclick="openReport('1','客户到期表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						客户到期表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac2" onclick="openReport('2','逾期统计表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						逾期统计表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac3" onclick="openReport('3','放款统计汇总表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						放款统计汇总表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport('4','余额汇总表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						余额汇总表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport('5','押品情况表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						押品情况表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac2" onclick="openReport('7','还款统计表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						还款统计表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport('9','五级分类情况统计表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						五级分类情况统计表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac2" onclick="openReport('12','业务经营统计月报表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						业务经营统计月报表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac3" onclick="openReport('13','贷款累放累收月报表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						贷款累放累收月报表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac3" onclick="openReport('14','业务综合统计表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						业务综合统计表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac1" onclick="openReport('15','贷款情况汇总表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						贷款情况汇总表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport('18','信息来源统计表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						信息来源统计表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac1" onclick="openReport('20','项目催收情况表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						项目催收情况表
					</div>
				</div>
			</div>
			<div class="rotate-div">
				<div class="rotate-obj rotate-bac4" onclick="openReport('21','项目审批时效明细表');">
					<div class="rotate-tubiao i i-bi1"></div>
					<div class="rotate-des" >
						项目审批时效明细表
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row clearfix report-body">
    	<div class="report-title">分析报表</div>
    	<div class="rotate-body">
			<div class="rotate-div1">
				<div class="rotate-obj1">
					<div class="rotate-des1" onclick="openTuDemo('2','资金回收预测 ');">资金回收预测</div>
					<div class="rotate-tuxing rotate-bacImag3" onclick="openTuDemo('2','资金回收预测 ');"></div>
				</div>
			</div>
			<div class="rotate-div1">
				<div class="rotate-obj1">
					<div class="rotate-des1" onclick="openTuDemo('1','五级分类分布图 ');">五级分类分布图</div>
					<div class="rotate-tuxing rotate-bacImag1" onclick="openTuDemo('1','五级分类分布图 ');"></div>
				</div>
			</div>
			
			<div class="rotate-div1">
				<div class="rotate-obj1">
					<div class="rotate-des1" onclick="openTuDemo('3','贷款金额统计表');">贷款金额统计表</div>
					<div class="rotate-tuxing rotate-bacImag3" onclick="openTuDemo('3','贷款金额统计表');"></div>
				</div>
			</div>
			<!-- <div class="rotate-div1">
				<div class="rotate-obj1">
					<div>客户类型分布图</div>
					<div class="rotate-tuxing rotate-bacImag6" onclick="openTuDemo('5','客户类型分布图');"></div>
				</div>
			</div>
			 -->
			<div class="rotate-div1">
				<div class="rotate-obj3">
					<div class="rotate-des1" onclick="openTuDemo('4','月放款/回收统计表');">月放款/回收统计表</div>
					<div class="rotate-tuxing rotate-bacImag2" onclick="openTuDemo('4','月放款/回收统计表');"></div>
				</div>
			</div>
			
			<div class="rotate-div1">
				<div class="rotate-obj3">
					<div class="rotate-des1">贷款用途分布图</div>
					<div class="rotate-tuxing rotate-bacImag1" onclick="openTuDemo('6','贷款用途分布图');"></div>
				</div>
			</div> 
		</div>
	</div>
	<div class="row clearfix report-body">
    	<div class="report-title">监测报表</div>
    	<div class="rotate-body">
    		<div class="rotate-jianguan rotate-bacImag4" onclick="openJIanGuan('1','监测报表')"></div>
    		<div class="rotate-jianguan rotate-bacImag5" onclick="openJIanGuan('2','监测报表')"></div>
    	</div>
	</div>
</div>
</body>
</html>