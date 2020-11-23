//打印，套打功能
function doTaoDaPrintByAppId(url){
	url = url+"&busType=hk&"+docParm;//将方式设置为还款
	top.addFlag = false;
	top.htmlStrFlag = false;
	top.createShowDialog(url,"选择打印方式",'50','50',function(){
	});
}