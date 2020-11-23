;
var CusPersonDebtInfoInsert = function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//担保选择组件
        $("select[name=vouType]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:true//多选
		});
		//还款选择组件
        $("select[name=repayType]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false//单选
		});
	};


    var _selectDebtInfo = function(){

        var obj= $('input[name=cusName]');

        var ajaxUrl =webPath+"/mfCusPersonDebtInfo/findPersonDebInfoByPage?cusNo="+cusNo;//请求数据URL;
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:ajaxUrl,
            valueElem:"input[name=cusName]",//真实值选择器
            title: "选择负债人",//标题
            labelShow:false,
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                $("input[name='cusName']").val(sltVal.cusName);
                $("input[name='relationToLoaner']").val(sltVal.relationToLoaner);

            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
				"relationToLoaner":"负债人类型"

            },
            returnData:{//返回值配置
                disName:"cusName",//显示值
                value:"cusName"//真实值
            }
        });
        $('input[name=cusName]').next().click();

    };




	
	var _saveCusPersonDebtInfo=function(obj,saveType){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			/*var checkFlag = "";
			
			//证件号码唯一性验证
			var idNum = $("input[name=loanUnit]").val();
			var idNumTitle = $("input[name=loanUnit]").attr("title");
			var idNumType = $("select[name=idType]").val();
			var relationId = $("input[name=cusNo]").val();
			var idNumResult = checkUniqueVal(idNum,idNumTitle,relationId,"MfCusPersonDebtInfo","01",saveType,"");
			checkFlag = idNumResult.split("&")[0];
			idNumResult = idNumResult.split("&")[1];
			if(checkFlag == "1"){
				window.top.alert(idNumResult,2,function(){
					ajaxInsertCusForm(obj);
				});
			}else{
				ajaxInsertCusForm(obj);
			}*/
			ajaxInsertCusForm(obj);
		}
	};
	var _saveCusPersonDebtInfoAndAdd=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
            var cusNo = $("input[name='cusNo']").val();
            var inputUrl = webPath+"/mfCusPersonDebtInfo/input?cusNo="+cusNo;
            ajaxInserAndAddCusForm(obj,inputUrl);
		}
	};
	var _updateCallBack = function(){
		top.addFlag = true;
		myclose_click();
	};
	var _getEndDate = function(){
		var beginDate =  $("input[name=beginDate]").val();
		var term = $("input[name=loanTerm]").val();
		if(beginDate!=""&&term!=""){			
			var intTerm = parseInt(term);
			var d = new Date(beginDate);
			d.setMonth(d.getMonth()+intTerm);
			var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			$("input[name=endDate]").val(str);
		}else{
			$("input[name=endDate]").val("");
		}
	};
	
	var _getLoanTerm = function(){
		var beginDate =  $("input[name=beginDate]").val().replace(/\-/g, ""); 
		var endDate = $("input[name=endDate]").val().replace(/\-/g, ""); 
		if(beginDate!=""&&endDate!=""){
			UtilDwr.getMonthsAndDays(beginDate,endDate,function(data){
				var loanTerm="";
				if(data[0]>0){
					loanTerm=loanTerm+data[0]+"月";
				}
				if(data[1]>0){
					loanTerm=loanTerm+data[1]+"天";
				}
				$("[name=loanTerm]").val(loanTerm);
			});
			
		}
	};
	return {
		init:_init,
		updateCallBack:_updateCallBack,
		saveCusPersonDebtInfo:_saveCusPersonDebtInfo,
		getEndDate:_getEndDate,
		getLoanTerm:_getLoanTerm,
        saveCusPersonDebtInfoAndAdd:_saveCusPersonDebtInfoAndAdd,
        selectDebtInfo:_selectDebtInfo,
	};
}(window, jQuery);




