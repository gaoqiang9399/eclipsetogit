
function showDetail(){
	$('.cd-popup').addClass('is-visible');
}
function closeDetail(){
	$('.cd-popup').removeClass('is-visible');
	$(".ui-layout-pane").removeClass("row");
	var transitionEvent = whichTransitionEvent($('.cd-popup')[0]);
	$('.cd-popup').one(transitionEvent, function(){
		$('.cd-popup').remove();
	});
}
// 被执行人查询
function executor(){
	window.open("http://zhixing.court.gov.cn/search/");

}

function discredit(){
    window.open("http://zxgk.court.gov.cn/");
}

function  verdict(){
    window.open("http://wenshu.court.gov.cn/");

}

function cloudService(){
	top.showDialog("http://"+servicemanagePath+"/servicemanage/index.jsp","三方服务");
}
function loanCalculatorAction(){
	top.showDialog(webPath+"/mfLoanCalculator/input","贷款计算器","90","95");
}
function optSliders() {
	top.openBigForm(webPath+"/sysUser/modifyPasswordInput","修改密码");
}
//还款计划试算
function AcLnPayPlnAppFun(){
	top.showDialog(webPath+"/acLnPayPlnApp/getPageForTools","还款计划试算","90","95");
}
//基准利率查询
function RateNormFun(){
	top.showDialog(webPath+"/rateNorm/getListPage","基准利率查询","90","95");
}

//收益率测算
function AcProfitCalFun(){
	top.showDialog(webPath+"/acProfitCal/inputCalc","收益率测算","90","95");
}
//全局搜索
function searchGlobalAll(){
    top.showDialog(webPath+"/mfQueryEntrance/getSearchGlobalPage","全局搜索","90","95");
}

//日程管理
function FullCalendar(){
	top.showDialog(webPath+"/workCalendar/fullCalendarmonthlist?wait=0","日程管理","90","99");
}
//账户充值
function AccountPay(){
	$.get(webPath+"/accountPay/getDetilPage",function(data){
			if(data.flag=="success"){
				var url = data.url +"?showType=2";
				//rzzl.skipPage(url);
				top.openBigForm(url,"账户中心",function () {});
			}else{
				window.top.alert(data.msg,0);
			}
		}
	);
}
//实名认证
function realNameAuthentication(){
	top.showDialog(webPath+"/mfToolsRealname/input","信息核查","90","95");
}
//短信发送
function sendMessage(){
	top.showDialog(webPath+"/mfToolsSendMessage/input","短信发送","90","95");
}
//下载中心
function downloadTools(){
	top.showDialog(webPath+"/mfToolsDownload/getListPage","下载中心","90","95");
}

//更换皮肤
function changeSysSkin() {
    top.showDialog(webPath+"/mfSysSkinUser/getSkinList","更换皮肤","70","75");
}



//房产评估
function realEstateEval(){
    top.showDialog(webPath+"/evalInfo/openRealEstateEval","房产评估","90","95");
}