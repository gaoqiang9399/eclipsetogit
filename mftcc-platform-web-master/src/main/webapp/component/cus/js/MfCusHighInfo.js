var MfCusHighInfo = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	/*education*/
	var _educationList = function(){
		var highId = $("input[name=highId]").val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfCusHighChildInfo/getPageList",
            data: {
                highChildType: "1",
				highId:highId
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    $("#education").html(data.tableHtml);
                } else {
                    alert("数据查询出错")
                }
            }
        });
	}
	var _educationAdd = function(){
        var highId = $("input[name=highId]").val();
        $.ajax({
            url: webPath + "/mfCusHighChildInfo/highChildInput",
			data:{highChildType:1,highId:highId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#cusHighChildInfoInsert").html(data.formHtml);
                    $("#list").css("display","none");
                    $("#insert").css("display","block");
                } else {
                    alert("数据查询出错")
                }
            }
        });
	}
    /*work*/
    var _workList = function(){
        var highId = $("input[name=highId]").val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfCusHighChildInfo/getPageList",
            data: {
                highChildType: "2",
                highId:highId
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    $("#work").html(data.tableHtml);
                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
    var _workAdd = function () {
        var highId = $("input[name=highId]").val();
        $.ajax({
            url: webPath + "/mfCusHighChildInfo/highChildInput",
            data:{highChildType:2,highId:highId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#cusHighChildInfoInsert").html(data.formHtml);
                    $("#list").css("display","none");
                    $("#insert").css("display","block");
                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
    /*social*/
   /* var _socialList = function(){
        var highId = $("input[name=highId]").val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: webPath + "/mfCusHighChildInfo/getPageList",
            data: {
                highChildType: "3",
                highId:highId
            },
            async: false,
            success: function (data) {
                if (data.flag == "success") {
                    $("#social").html(data.tableHtml);
                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
    var _socialAdd = function () {
        var highId = $("input[name=highId]").val();
        $.ajax({
            url: webPath + "/mfCusHighChildInfo/highChildInput",
            data:{highChildType:3,highId:highId},
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#cusHighChildInfoInsert").html(data.formHtml);
                    $("#list").css("display","none");
                    $("#insert").css("display","block");
                } else {
                    alert("数据查询出错")
                }
            }
        });
    }*/
    //更新
    var _updateHighChild = function (obj,url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        $.ajax({
            url: url,
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    $("#update").css("display","block");
                    $("#cusHighChildInfoUpdate").html(data.formHtml);
                    $("#list").css("display","none");

                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
    //删除
    var _deleteHighChild = function (obj,url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        $.ajax({
            url: url,
            type:'post',
            dataType:'json',
            success: function (data) {
                if (data.flag == "success") {
                    window.top.alert(data.msg,1);
                    _educationList();
                    _workList();
                } else {
                    alert("数据查询出错")
                }
            }
        });
    }
	return {
		init : _init,
        educationList:_educationList,
        workList:_workList,
        educationAdd:_educationAdd,
        workAdd:_workAdd,
        updateHighChild:_updateHighChild,
        deleteHighChild:_deleteHighChild
	};

}(window, jQuery);



function saveCusHighInfoInsert(obj,saveType){
	var flag = submitJsMethod($(obj).get(0), '');

	var sysProjectName = $("#sysProjectName").val();
	if (sysProjectName == "WangXin") {// 网信-车商贷，证件到期日应在6个月以上
		var iDEndDate = $("input[name='iDEndDate']").val();
		iDEndDate = iDEndDate.replace(/-/g, "");

		var mydate = new Date();
		mydate.setMonth(mydate.getMonth() + 6);
		var date_ = mydate.getFullYear() + '' + (mydate.getMonth() + 1) + '' + mydate.getDate();

		if (iDEndDate < date_) {
			flag = false;
			alert(top.getMessage("FAILED_SAVE_CONTENT", { "content" : "", "reason" : "<br>证件到期日应在6个月以上" }), 0);
		}
	}

	if(flag){
		var checkFlag = "";
		//证件号码唯一性验证
		var idNum = $("input[name=idNum]").val();
		var idNumTitle = $("input[name=idNum]").attr("title");
		var idNumType = $("select[name=idType]").val();
		var relationId = $("input[name=highId]").val();
		var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusHighInfo","01",saveType,"");
		checkFlag = idNumResult.split("&")[0];
		idNumResult = idNumResult.split("&")[1];
		if(checkFlag == "1"){
			window.top.alert(idNumResult,2,function(){
				ajaxInsertCusForm(obj);
			});
		}else{
			
			ajaxInsertCusForm(obj);
		}
	}
}

function updateCallBack(){
	top.addFlag = true;
	myclose_click();
};

//根据身份证获取年龄性别信息
function getBirthdayAndSex(){
    var idType = $("[name='idType']").val();
    if("0"==idType){
        StringUtil.setBirthyAndSexByID($("input[name='idNum']"), 'sex', 'brithday','age');
    }
}

//选择网点信息
function selectWebsiteInfo(){
    var cusNo = $("input[name=cusNo]").val();
    $("input[name=belongWebsiteName]").popupList({
        searchOn: true, //启用搜索
        multiple: false, //false单选，true多选，默认多选
        ajaxUrl : webPath+"/mfCusNetworkInfo/findByPageAjax?cusNo=" + cusNo,// 请求数据URL
        valueElem:"input[name=belongWebsiteName]",//真实值选择器
        title: "网点信息",//标题
        elemEdit: true,
        changeCallback:function(elem){//回调方法
            var sltVal = elem.data("selectData");
            $("input[name='belongWebsiteId']").val(sltVal.websiteId);
            $("input[name='belongWebsiteName']").val(sltVal.websiteName);
        },
        tablehead:{//列表显示列配置
            "websiteId":"网点ID",
            "websiteCode":"网点编号",
            "websiteName":"网点名称"
        },
        returnData:{//返回值配置
            disName:"websiteName",//显示值
            value:"websiteId"//真实值
        }
    });
    $('input[name=belongWebsiteName]').next().click();
};