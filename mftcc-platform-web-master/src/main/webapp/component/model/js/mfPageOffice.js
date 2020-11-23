//pageoffice调用js
var mfPageOffice = function(window, $){
	
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： factor/component/include/mfPageOffice.js
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/factor
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	//回调函数名
    var callBackfun = "";
    //打开文档(业务取值调用)
	var _openPageOffice = function(pageCntObj){
		var filePath = pageCntObj.filePath;
		var templateSuffix = pageCntObj.templateSuffix;
		if(templateSuffix == '4'){//html
			_addOpenHtml(pageCntObj);
		}else if(typeof(filePath) != "undefined" && filePath != "" ){
			_editOpen(pageCntObj);
		}else{
			callBackfun = pageCntObj.callBackfun;
			_addOpen(pageCntObj);
		}
	};
	//新增打开
	var _addOpen = function(pageCntObj){
		var bookParmStr=JSON.stringify(pageCntObj);
        var url;
		bookParmStr = encodeURIComponent(bookParmStr);
		if(pageOfficeVersion == "0"){
            url = "/mfToPageOffice/mfPageOffice?bookParmStr="+bookParmStr;
		}else if(pageOfficeVersion == "1"){
            url = "/pageOffice/pageOfficeAdd?paramStr="+bookParmStr;
		}
		pageOfficeLink(url);
	};
	//编辑打开
	var _editOpen = function(pageCntObj){
		var fileName = pageCntObj.fileName;
		var filePath = pageCntObj.filePath;
		var saveFileName = pageCntObj.saveFileName;
		if(typeof(saveFileName) == "undefined" || saveFileName == "" ){
			saveFileName = fileName;
		}
		pageCntObj.pathFileName = filePath+fileName;
		pageCntObj.savePath = filePath;
		pageCntObj.saveFileName = saveFileName;
        pageCntObj.fileType = "0";
		var poCnt=JSON.stringify(pageCntObj);
		var url = "";
        var fileNameTmp = fileName.toUpperCase();
		if(fileNameTmp.indexOf(".PDF")>-1){
            if(pageOfficeVersion == "0"){
                url =  "/mfToPageOffice/mfOpenPdf?poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
            }else if(pageOfficeVersion == "1"){
                url =  "/pageOffice/mfOpenPdf?poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
            }
		}else{
            if(pageOfficeVersion == "0"){
                url =  "/mfToPageOffice/pageOfficeEdit?poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
            }else if(pageOfficeVersion == "1"){
                url =  "/pageOffice/pageOfficeEdit?poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
            }
		}
		pageOfficeLink(url);
	};

    //生成并打开html文件
    var _addOpenHtml = function(pageCntObj){
        var templateBizConfigId = pageCntObj.templateBizConfigId;
        var paramStr=JSON.stringify(pageCntObj);
        paramStr = encodeURIComponent(paramStr);
        var url = webPath +"/mfToPageOffice/showHtmlTemplate?templateBizConfigId=" + templateBizConfigId+"&paramStr="+paramStr;
        window.open(url);
    };
	//兼容浏览器打开
	var pageOfficeLink = function(url){
		var pageOfficeUrl = "";
        var poLinkUrl = "";
        if(pageOfficeVersion == "0"){
            pageOfficeUrl =  localhostPath+"/pageoffice/pageOfficeFactor.do?method=getPoLink";
        }else if(pageOfficeVersion == "1"){
            pageOfficeUrl =  pageOfficePath+"/officeService/pageOffice/getPoLink";
        }
		$.ajax({
			url :pageOfficeUrl ,
			type : 'post',
			async : false,
			dataType : 'json',
			error : function() {
			}, 
			success : function(data) {
                if(pageOfficeVersion == "0"){
                    poLinkUrl="PageOffice://|"+webPath+url+data[1];
                }else if(pageOfficeVersion == "1"){
                    poLinkUrl="PageOffice://|"+pageOfficePath+"/officeService"+url+data[1];
                }
				window.open(poLinkUrl,'_self','');
			}
		});
	};
	//刷新
	var _getCallBackfun = function (){
		return callBackfun;
	};
	//判断和主业务无关联的模板是否保存过,relNo-保存模板时关联号 templateNo-模板编号 filename-打开文档名称
	var _saveModleInfo = function(relNo,templateNo,filename){
        var retunData = new Object();
		$.ajax({
            url: webPath+"/mfTemplateModelRel/getIfSaveTemplateInfo",
            type:"post",
            data:{"relNo":relNo,"templateNo":templateNo},
            async: false,
            dataType:"json",
            error:function(){alert('error')},
            success:function(data){
                var saveBtn = "1";
                var saveUrl = "";
                if(data.saveFlag !="0"){//保存过的文档
                    saveBtn = "0";
                }else{//没保存的指定保存路径
                    data.filePath = "";
                    saveUrl = webPath+"/component/model/saveModelInfo.jsp?userId="+data.userId+"&relNo="+relNo+"&filename="+filename+"&templateNo="+templateNo;
                }
                data.saveBtn = saveBtn;
                data.saveUrl = saveUrl;
                retunData = data;
            }
        });
        return retunData;
	};
	return {
		callBackfunName : _getCallBackfun,
		openPageOffice:_openPageOffice,
		editOpen : _editOpen,
        saveModleInfo : _saveModleInfo
	};
}(window, jQuery);

//回调刷新
var pageCallback = function(){
	var reInitFun = mfPageOffice.callBackfunName();
	if (typeof(reInitFun) != "undefined" && reInitFun != "" ){
		eval(reInitFun+"()");
	}
};