;
var MfCusBankAccManage = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$(function(){
			//省份选择组件
	        $("input[name=province]").popupSelection({
		        	ajaxUrl:webPath+"/nmdArea/getProvinceAjax",
					searchOn : true,//启用搜索
					multiple : false,//单选
					valueClass : "show-text",//自定义显示值样式
					ztree : true,
					ztreeSetting : setting,
					title : "选择省份",
					handle : BASE.getIconInTd($("input[name=provinceId]")),
					changeCallback : function (elem) {
						var node = elem.data("treeNode");
						var address=node.name;
						$("input[name=province]").val(address);
						$("input[name=provinceId]").val(node.id);
						var $proviceObj = $("input[name=province]").parent(".input-group").find(".pops-label-alt");
						$proviceObj.removeClass("pops-label-alt");
						$proviceObj.html(address);
						//初始化对应城市
						$("input[name=city]").val("");
						$("input[name=cityId]").val("");
						$("input[name=popscity]").parent().find(".show-text").remove();
						cityPopupSelection(node.id);
					}
			});
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
            $("input[name='accountNo']").on('keyup input',function(){
                var  accountNo =$(this).val();
                var reg=/^-?[0-9,\s]*$/;//此写法允许首字符为0
                if(!reg.test(accountNo)){
                    $(this).val("");
                }else{
                    if(/\S{5}/.test(accountNo)){
                        this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
                    }
                }
            });

            function cityPopupSelection(provinceId){
	    		//城市选择组件
	            $("input[name=city]").popupSelection({
	    	        	ajaxUrl:webPath+"/nmdArea/getCityAjax?uplev="+provinceId,
	    				searchOn : true,//启用搜索
	    				multiple : false,//单选
	    				valueClass : "show-text",//自定义显示值样式
	    				ztree : true,
	    				ztreeSetting : setting,
	    				title : "选择城市",
	    				handle : BASE.getIconInTd($("input[name=cityId]")),
	    				changeCallback : function (elem) {
	    					var node = elem.data("treeNode");
	    					var address=node.name;
	    					$("input[name=city]").val(address);
	    					$("input[name=cityId]").val(node.id);
	    					var $cityObj = $("input[name=city]").parent(".input-group").find(".pops-label-alt");
	    					$cityObj.removeClass("pops-label-alt");
	    					$cityObj.html(address);
	    				}
	    		});
	    	}
	        
		 });
	};
	
	var _selectProvinceDialog = function(callback){
		top.dialog({
			id:"areaDialog",
			title:'选择省份',
			url: webPath+"/nmdArea/getProvince",
			width:350,
			height:420,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					//返回对象的属性disNo,disName
					if(typeof(callback)== "function"){
						callback(this.returnValue);
					}else{
					}
				}
			}
		}).showModal();
	};
	
	var _selectProvinceCallBack = function(areaInfo){
		$("input[name=province]").val(areaInfo.disName);
		$("input[name=provinceId]").val(areaInfo.disNo);
	};
	
	//改变省份，城市置空  
	var _provinceChange = function(){
		$("input[name=city]").val("");
		$("input[name=cityId]").val("");
		//详情页面编辑
		if($('span[field-name="city"]').find("div[class=fieldShow]")){
			$('span[field-name="city"]').find("div[class=fieldShow]").html("");
		}
	};
	
	
	var _selectCityDialog = function(callback){
		var uplev = $("input[name=provinceId]").val();
		if(uplev != null || uplev != ""){
			top.dialog({
				id:"areaDialog",
				title:'选择城市',
				url: webPath+"/nmdArea/getCity?uplev="+uplev,
				width:350,
				height:420,
				backdropOpacity:0,
				onshow:function(){
					this.returnValue = null;
				},onclose:function(){
					if(this.returnValue){
						//返回对象的属性disNo,disName
						if(typeof(callback)== "function"){
							callback(this.returnValue);
						}else{
						}
					}
				}
			}).showModal();
		}else{
			alert("请先选择省份！");
		};
	};
	
	var _selectCityCallBack = function(areaInfo){
		$("input[name=city]").val(areaInfo.disName);
		$("input[name=cityId]").val(areaInfo.disNo);
	};
	var _selectBankNumbei = function(){
        bindCnapsDataSource($("input[name=bankNumbei]"));
	};
	var   _checkAccount  =   function(){
		var cusNo  =  $("input[name=cusNo]").val();
		var accountNo = $("input[name=accountNo]").val();
	    if(accountNo == ""){
	    	alert("请输入银行卡号", 0);
	    	return  false;
	    }
	    accountNo   =  accountNo.replace(/(^\s+)|(\s+$)/g,"");
		$.ajax({
			url : webPath + "/mfCusBankAccManage/signReq",
			data : {
				cusNo : cusNo,
				accountNo:accountNo
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "error") {
				    alert(data.msg, 0);	
				} else if(data.flag == "success"){
					
				}
			}
			
		})
		
		
	}
	
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectCityCallBack:_selectCityCallBack,
		selectCityDialog:_selectCityDialog,
		provinceChange:_provinceChange,
		selectProvinceCallBack:_selectProvinceCallBack,
		selectProvinceDialog:_selectProvinceDialog,
		checkAccount:_checkAccount,
        selectBankNumbei:_selectBankNumbei
	};
	
}(window, jQuery);

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
            $("input[name='websiteId']").val(sltVal.websiteId);
            $("input[name='websiteName']").val(sltVal.websiteName);
            $("input[name='websiteCode']").val(sltVal.websiteCode);
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


//中天帐号填写
function accountNoChange(obj){

	var no = $(obj).val();

    $('input[name=accountNo]').val(no);
};
function changeBankByUseType(obj){
    var useTypeVal = $(obj).val();
    if (useTypeVal == "0" || useTypeVal == "C") {//企业基本账户、关联银行户
        $("select[name=bank]").parents("tr").show();
        $("[name=bankFullName]").parents("tr").show();
        $("[name=bank]").attr("mustinput","1");
        $("[name=bankFullName]").attr("mustinput","1");
    } else if (useTypeVal == "D") {//受托收款账户
        $("select[name=bank]").parents("tr").show();
        $("[name=bankFullName]").parents("tr").show();
        $("[name=bank]").attr("mustinput","0");
        $("[name=bankFullName]").attr("mustinput","0");
    } else {
        $("select[name=bank]").parents("tr").hide();
        $("[name=bankFullName]").parents("tr").hide();
        $("[name=bank]").attr("mustinput","0");
        $("[name=bankFullName]").attr("mustinput","0");
    }
};
function ajaxInsertCusFormFormat(obj) {
    var accountNo = $("input[name='accountNo']").val();
    $("input[name='accountNo']").val(accountNo.trim().replace(/\s/g, ""));
    ajaxInsertCusForm(obj);
}
function ajaxInsertCusFormFormatAndAdd(obj) {
    var accountNo = $("input[name='accountNo']").val();
    $("input[name='accountNo']").val(accountNo.trim().replace(/\s/g, ""));
    var cusNo = $("input[name='cusNo']").val();
    var inputUrl = webPath+"/mfCusBankAccManage/input?cusNo="+cusNo;
    ajaxInserAndAddCusForm(obj,inputUrl);
}
function getBankByCardNumber(obj) {
    //阳光银行 自行选择
    if ('ygbank' != sysProjectName) {
        var identifyNumber = obj.value.trim().replace(/\s/g, "");
        $.ajax({
            url : webPath + "/bankIdentify/getByIdAjax",
            data : {
                identifyNumber : identifyNumber
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    BASE.removePlaceholder($("input[name=bankNumbei]"));
                    BASE.removePlaceholder($("input[name=bank]"));
                    $("input[name=bankNumbei]").val(data.bankId);
                    $("input[name=bank]").val(data.bankName);
                    if(data.bankCode){
                        $("input[name=bankCode]").val(data.bankCode);
                    }
                } else {
                    $("input[name=bankNumbei]").val("");
                    $("input[name=bank]").val("");
                }
            }
        });
    }
}

//在账户名项添加onChange事件 修改账户名 清空关联的账户信息
var accountNameChange_reset = function(obj) {
    $("input[name='idNum']").val("");
    $("input[name='reservedPhone']").val("");
    $("input[name='accountNo']").val("");
}

/* function bindCnapsDataSource(obj) {
    $(obj).popupList({
        searchOn : true, //启用搜索
        multiple : false, //false单选，true多选，默认多选
        ajaxUrl : webPath + "/mfCnapsBankInfo/getCnapsListAjax",// 请求数据URL
        valueElem : "input[name=bankNumbei]",//真实值选择器
        title : "选择行号",//标题
        changeCallback : function(elem) {//回调方法
            BASE.removePlaceholder($("input[name=bankNumbei]"));
            BASE.removePlaceholder($("input[name=bank]"));
            var sltVal = elem.data("selectData");
            $("input[name=bankNumbei]").val(sltVal.bankcode);
            $("input[name=bank]").val(sltVal.bankname);
        },
        tablehead : {//列表显示列配置
            "bankcode" : "行号",
            "bankname" : "行名"
        },
        returnData : {//返回值配置
            disName : "bankcode",//显示值
            value : "bankname"//真实值
        }
    });
} */

function bindCnapsDataSourceHM(obj) {
    $(obj).popupList({
        searchOn : true, //启用搜索
        multiple : false, //false单选，true多选，默认多选
        ajaxUrl : webPath + "/mfCnapsBankInfo/getCnapsListAjax",// 请求数据URL
        valueElem : "input[name=bankNumbei]",//真实值选择器
        title : "选择行号",//标题
        changeCallback : function(elem) {//回调方法
            BASE.removePlaceholder($("input[name=bankNumbei]"));
            BASE.removePlaceholder($("input[name=bankFullName]"));
            var sltVal = elem.data("selectData");
            $("input[name=bankNumbei]").val(sltVal.bankcode);
            $("input[name=bankFullName]").val(sltVal.bankname);
        },
        tablehead : {//列表显示列配置
            "bankcode" : "行号",
            "bankname" : "行名"
        },
        returnData : {//返回值配置
            disName : "bankname",//显示值
            value : "bankcode"//真实值
        }
    });
}

function initBankArea() {
    //银行区域选择组件
    $("input[name=bankArea]")
        .popupSelection(
            {
                ajaxUrl : webPath
                + "/nmdArea/getAllExceptDirectCityAjax",
                searchOn : true,//启用搜索
                multiple : false,//单选
                valueClass : "show-text",//自定义显示值样式
                ztree : true,
                ztreeSetting : setting,
                title : "银行城市",
                handle : BASE
                    .getIconInTd($("input[name=bankArea]")),
                changeCallback : function(elem) {
                    var node = elem.data("treeNode");
                    var address = node.name;
                    $("input[name=bankArea]").val(address);
                    var $careaProviceObj = $(
                        "input[name=careaProvice]").parent(
                        ".input-group").find(".pops-label-alt");
                    $careaProviceObj.removeClass("pops-label-alt");
                    $careaProviceObj.html(address);
                }
            });
}

function insertCallBack() {
    top.addFlag = true;
    top.formOrTable = "table";
    myclose_click();
};

//验证页面“请选择”是否全部完成
function validateAndInsert(){
    ajaxInsertCusFormFormat('#cusBankAccInsert');
}

function getBankName(bank) {
    var bankObj = null;
    getBankBin(bank, function(err, data) {
        if(!err) {
            bankObj = {
                bankName: data.bankName,
                cardType: data.cardTypeName,
                bankCode: data.bankCode
            };
        }
    })
    return bankObj;
}
function bindCnapsDataSourceZm(obj) {
    var bankNumbei = $("div[class='pops-value']");
    if(bankNumbei != "undefined")
    {
        $("div[class='pops-value']").remove();
    }
    var element = $(obj).attr('name');
    $(obj).popupList({
        searchOn : true, //启用搜索
        multiple : false, //false单选，true多选，默认多选
        ajaxUrl : webPath + "/mfCnapsBankInfo/getCnapsListAjax",// 请求数据URL
        valueElem : "input[name=bankNumbei]",//真实值选择器
        title : "选择行号",//标题
        changeCallback : function(elem) {//回调方法
            BASE.removePlaceholder($("input[name=bankNumbei]"));
            BASE.removePlaceholder($("input[name=bank]"));
            var sltVal = elem.data("selectData");
            $("input[name=bankNumbei]").val(sltVal.bankcode);
            $("input[name=bank]").val(sltVal.bankname);
        },
        tablehead : {//列表显示列配置
            "bankcode" : "行号",
            "bankname" : "行名"
        },
        returnData : {//返回值配置
            disName : "bankcode",//显示值
            value : "bankname"//真实值
        }
    });
    $('input[name='+element+']').next().click();
};
function selectAreaCallBack(areaInfo){
    $("input[name=bankArea]").val(areaInfo.disName);
};

