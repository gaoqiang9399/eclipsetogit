//文档打开后执行的方法
function afterDocumentOpened() {
	backupLicense();//备份协议
	$("#pageOfficeDiv").css("height","1000px");
	//启动全屏
	if($("#autoFullScreen").val()=='1') {
		fullScreen();//全屏方法
	}
	//将excel替换map转换成json
	var replaceExcelFunc=decodeURIComponent($("#replaceExcelFunc").val());
	if(replaceExcelFunc!="") {//执行字段替换方法
		var data=$.parseJSON(replaceExcelFunc);
		var fileType=data[0].value;//1为word2为excel
        var i;
        var key,value;
		if(fileType==1) {//word
			var ct=document.getElementById("PageOfficeCtrl").Document.Application.ActiveDocument.Content;
			for(i=1;i<data.length;i++){
				key=data[i].key;
				value=data[i].value;
				ct.Find.Execute(key,false,false,false,false,false,true,1,false,value,2);//执行查找替换方法
			}
		}else if(fileType==2) {//excel
			var cs=document.getElementById("PageOfficeCtrl").Document.Application.ActiveSheet.Cells;
			var match = /^\d+(\.\d+)?$/;//数字类型
			for(i=1;i<data.length;i++){
				key=data[i].key;
				value=data[i].value;
				if(match.test(value)) {
					value="'"+value;//对数字类型要转换成文本输出
				}
				//先判断存在再替换
				if(cs.Find(key)!=null) cs.Replace(key,value);
			}
		}
	}
}

//备份协议
function backupLicense() {
	$.ajax({//获取新旧标识
		url : '/pageoffice/backupLicense',
		type : 'post',
		async : false,
		dataType : 'json',
		error : function() {
		}, 
		success : function(data) {
		}
	});
}
//全屏切换
function fullScreen() {
	var fs=document.getElementById("PageOfficeCtrl").FullScreen;
	document.getElementById("PageOfficeCtrl").FullScreen = !fs;
}

//向word中插入excel表格
function insertExcel(key,fileName) {
	var mac = "function insertExcel()" + "\r\n"
	+"Do while True"+ "\r\n"
	+"With Selection.Find" + "\r\n"
	+".Text = \""+key+"\"" + "\r\n"
	+".Replacement.Text = \"\"" + "\r\n"
	+".Forward = True" + "\r\n"
	+".Wrap = wdFindContinue" + "\r\n"
	+".Format = False" + "\r\n"
	+".MatchCase = False" + "\r\n"
	+".MatchWholeWord = False" + "\r\n"
	+".MatchByte = True" + "\r\n"
	+".MatchWildcards = False" + "\r\n"
	+".MatchSoundsLike = False" + "\r\n"
	+".MatchAllWordForms = False" + "\r\n"
	+"End With" + "\r\n"
	+"Selection.Find.Execute" + "\r\n"
	+"if Selection.Find.found=True Then"+"\r\n"
	+"Selection.Delete Unit:=wdCharacter, Count:=1" + "\r\n"
	+"Selection.InlineShapes.AddOLEObject ClassType:=\"Excel.Sheet.8\", FileName:= _" + "\r\n"
	+"\""+fileName+"\" _" + "\r\n"
	+", LinkToFile:=False, DisplayAsIcon:=False" + "\r\n"
	+"Else" + "\r\n"
	+"Exit Do" + "\r\n"
	+"End if" + "\r\n"
	+ "Loop"+ "\r\n"
	+ "End function";
	return document.getElementById("PageOfficeCtrl").RunMacro("insertExcel", mac);
}

//显示打印窗口 若无打印机则不显示
function poPrint() {
	document.getElementById("PageOfficeCtrl").ShowDialog(4); 
}

//保存成功提示
function saveOkAlert() {
	$("#pageOfficeDiv").html("");//清空DIV才能显示提示框
	var parm ={'id':'B0001','parm0':''};
	//jAlert('2',parm,'系统提示',function(data){
		poClose();
	//});
}

//div调用 pageOfficeLink
function poLinkDivLoad(url) {
	//创建poDiv对象
	if($("#poDiv").length==0)
		$("body").append($('<div id="poDiv" style="display: none; width: 100%; height: auto;margin-top: -5px;"></div>'));
	$("#poDiv").load(url);
}		
		
//系统关闭方法
function poClose() {
	var returnUrl=$("#returnUrl").val();
	if(returnUrl=="blank" || returnUrl=="BLANK") {
		//清空先要将窗口最小化
		document.getElementById("PageOfficeCtrl").FullScreen= false;
		$("#pageOfficeDiv").hide();
	}else if(returnUrl=="" || returnUrl=="null" || returnUrl=="NULL" || returnUrl==null) {
		$("#pageOfficeDiv").hide();
		try {
			window.external.close();//关闭pageOfficeLink窗口
		} catch (e) {
			window.close();// 关闭一般窗口
		} finally {
		}
		//关闭当前web页
	}else {//关闭后打开指定url
		//先最小化再隐藏可以加快速度没有停顿
		var po=document.getElementById("PageOfficeCtrl");
		if(po!=null) po.FullScreen= false;//如果有就最小化
		$("#pageOfficeDiv").hide();
		//----------------------------------------------------------
		var w=window.open("","_self","");
		setTimeout(function(){
			w.location=returnUrl.replace(/\|and\|/g,"&");//解决某些浏览器不能执行window.open问题
		}, 100);
		//window.open(returnUrl.replace(/\|and\|/g,"&"),'_self','');
		//---------------------------------------------------------
	}
}
//factor系统关闭方法
function poFactorClose() {
	//关闭当前web页
	$("#pageOfficeDiv").hide();
	try {
		window.external.close();//关闭pageOfficeLink窗口
	} catch (e) {
		window.close();// 关闭一般窗口
	} finally {
	}
}
//书签执行的方法
function bookMark() {
	var markUrl=$("#markUrl").val();
	//document.getElementById("PageOfficeCtrl").ShowHtmlModelessDialog(markUrl, "parameter=xx", "left=300px;top=auto;height=auto;width=320px;frame:no;");
	markDialog(markUrl);
}

//菜单对话窗口
function markDialog(markUrl){
	var sreturn= self.showModalDialog(markUrl,"_self","dialogHeight:500px;dialogWidth:888px;dialogLeft:200px;help：no;status:no;scroll:auto;edge:sunken;resizable=no;location=no;");
	if(typeof(sreturn)!="undefined" && sreturn.length>0)
	{  	
		var temp_val=sreturn.split('@');
		var fileType=$("#fileType").val();
		if(fileType=='1')
			addWordMark("$"+temp_val[0]+"$",temp_val[1]);
		else if(fileType=='2')
			addExcelMark("$"+temp_val[0]+"$");
	}
}

//ShowHtmlModelessDialog方式添加书签方法
function addBookMark(textName){
	var bkText=textName.split("=")[0];
	var bkName=textName.split("=")[1];
	var fileType=$("#fileType").val();
	if(fileType=='1')
		addWordMark("$"+bkText+"$",bkName);
	else if(fileType=='2')
		addExcelMark("$"+bkText+"$");
}
//添加书签
function addWordMark(bkText,bkName) {
	var po=document.getElementById("PageOfficeCtrl").Document.Application;
	var r = po.Selection.Range;
	r.Text =bkText;
	if(typeof(bkName) != "undefined" && bkName.indexOf("PO")<0){
		bkName = "BK"+bkName;
	}
	po.ActiveDocument.Bookmarks.Add(bkName,r);
}
//添加excel书签
function addExcelMark(bkText) {
	bookName = bkText;
	if(bkText=="$审贷会意见$"){//需要插入行的书签记录插入位置
		cellAddr = bkText+"#@#"+$("#cellAddr").val();
	}
	var po=document.getElementById("PageOfficeCtrl");
	var cells=po.Document.Application.ActiveCell.value=bkText;
}

//获取当前点击的单元格的地址
function OnCellClick(Celladdress) {
	$("#cellAddr").val(Celladdress);
}