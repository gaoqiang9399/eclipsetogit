//常规关闭弹出框，只关闭弹出框，不做回调
function myclose(){
	//$(top.window.document).find(".dhccModalDialog_bg").hide();
	//$(top.window.document).find(".dhccModalDialog").find('iframe').remove();
 	//$(top.window.document).find(".dhccModalDialog").remove();
 	setTimeout(function() {
		window.top.cloesBigForm(window);
	}, 100);
}
//关闭弹出框，并支持回调
function myclose_click(){
//	$(top.window.document).find(".dhccModalDialog .i-x5").click();
	$(".dhccModalDialog .i-x5",parent.document).last().click();
}

//任务中关闭弹出框
function myclose_task(){
	/*setTimeout(function() {
	window.top.cloesBigForm(window);
	}, 100);*/
	$(top.window.document).find("#taskShowDialog").remove();
}
//关闭showDialog弹出框
function myclose_showDialog(){
	$(top.window.document).find("#showDialog").remove();
}
//关闭showDialog弹出框，支持回调
function myclose_showDialogClick(){
	$(top.window.document).find("#showDialog .close").click();
}