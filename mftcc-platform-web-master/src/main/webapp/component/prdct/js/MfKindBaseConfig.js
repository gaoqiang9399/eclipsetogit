;
var MfKindBaseConfig=function(window, $){
	//初始化产品基础配置信息
	var _init = function(data){
		var htmlStr = getPrdctBaseConfigHtml(data);
		$(".nav-content-div").html(htmlStr);
		$(".config-div").mCustomScrollbar("scrollTo","top"); // 滚动到顶部（垂直滚动条）
		initBindEvent(data);
		//初始化选择组件
		initPopUpSelection(data);
	};
	//产品基础配置Html
	var getPrdctBaseConfigHtml = function(data){
		var mfSysKind = data.mfSysKind;
		var htmlStr="";
		htmlStr=htmlStr+'<div class="content-div baseConfig"><div class="sub-content-div padding_left_15">'
		+'<div class="sub-content padding_left_20 margin_top_15">';
		
		//产品名称
		htmlStr = htmlStr+ getPrdctNameConfigHtml(mfSysKind);
		//产品描述
		htmlStr = htmlStr+ getPrdctDescConfigHtml(mfSysKind);
		//产品子类
		htmlStr = htmlStr+ getPrdctSubKindConfigHtml(mfSysKind,data.subKindList);
		//客户类别
		htmlStr = htmlStr+ getPrdctCusTypeConfigHtml(mfSysKind,data.cusSubTypeList);
		//贷款对象
		htmlStr = htmlStr+ getPrdctLoanObejctConfigHtml(mfSysKind,data.loanObejctList);
		//币种
//		htmlStr = htmlStr+ getPrdctCurTypeConfigHtml(mfSysKind,data.curTypeList);
		//产品属性
		htmlStr = htmlStr+ getPrdctPropertyConfigHtml(mfSysKind,data.kindPropertyList);
		//开办部门
		htmlStr = htmlStr+ getPrdctDepartmentConfigHtml(mfSysKind);
		//开办角色
		htmlStr = htmlStr+ getPrdctRoleConfigHtml(mfSysKind);
		//合同性质
		htmlStr = htmlStr+ getPrdctPactPropertyConfigHtml(mfSysKind,data.pactPropertyList,data.pactPropertyMap);
		//担保方式
		htmlStr = htmlStr+ getPrdctVouTypeConfigHtml(mfSysKind,data.vouTypeList,data.vouTypeMap);
		//资产类别
        htmlStr = htmlStr+ getPrdctAssetClassConfigHtml(mfSysKind);
		//贷款投向
		htmlStr = htmlStr+ getPrdctFincUseConfigHtml(mfSysKind);
        //业务品种
        htmlStr = htmlStr+ getPrdctBusBreedConfigHtml(mfSysKind);
		//合同额度是否循环
		htmlStr = htmlStr+ getPrdctAuthCycleConfigHtml(mfSysKind);
		//是否能进行额度测算
		htmlStr = htmlStr+ getIfCreditCalcConfigHtml(mfSysKind);
		//是否是小微
		htmlStr = htmlStr+ getIfSmailCamllConfigHtml(mfSysKind);
		//展业区域
		htmlStr = htmlStr+ getExhibitionAreaConfigHtml(mfSysKind);
		
		htmlStr = htmlStr+'</div></div></div>';
		return htmlStr;
	};
	
	//初始化属性上的绑定事件
	var initBindEvent = function(data){
		//产品名称绑定事件
		kindNameBindEvent();
		kindDescBindEvent();
		//子产品绑定事件
		subKindBindEvent();
		//客户类别绑定事件
		cusTypeBindEvent();
		//贷款对象绑定事件
		loanObejctBindEvent();
		//币种绑定事件
		curNoBindEvent();
		//产品属性绑定事件
		kindPropertyBindEvent();
		//开办部门绑定事件
		brNoBindEvent(data);
		//开办角色绑定事件
		roleNoBindEvent();
		//合同性质绑定事件
		pactPropertyBindEvent(data.pactPropertyMap);
		//默认合同性质绑定事件
		pactPropertyDefBindEvent();
		//担保方式绑定事件
		vouTypeBindEvent(data.vouTypeMap);
		//默认担保方式绑定事件
		vouTypeDefBindEvent();
		//资产类别绑定时事件
        assetClassBindEvent();
		//贷款投向绑定事件
		fincUseBindEvent();
		//业务品种绑定事件
        busBreedBindEvent();
		//合同额度是否循环绑定事件
		authCycleBindEvent();
        ifCreditCalcBindEvent();
        ifSmailCamllBindEvent();
		//展业区域绑定事件
		exhibitionAreaBindEvent();
	};
	
	var initBrNoPopupSelection  = function(data){
		//开办部门 ztree 选择设置
		var ztreeSetting = {
				check: {  
	                enable: true,  
	                chkStyle: "checkbox",  
	                chkboxType: { "Y": "s", "N": "" }  
	            },
	            data:    {
	                simpleData:{
	                    enable:true
	                }
	            },
		}
		//部门新组件
		$("input[name=brNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			ztree:true,
			parentSelect : true,//选择父节点
			ztreeSetting : ztreeSetting,
			title:"开办部门",
			items:data.org,
			changeCallback : function (obj, elem) {
				var brNos = $("input[name=brNo]").val();
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				mfSysKind.brNo=brNos;
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData,function(data){
					var mfSysKindTmp = data.mfSysKind;
					var brNoName=mfSysKindTmp.ext1;
					if(mfSysKindTmp.ext1.length>50){
						brNoName = brNoName.substring(0, 50)+ "...";
					}
					$(".brNo .span-text").text(brNoName);
					$(".brNo .span-text").attr("title",mfSysKindTmp.ext1);
				});
				
			},
		});
	};
	//初始化开办角色选择组件
	var initRolePopupSelection = function(data){
		//角色新组件
		$("input[name=roleNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			title:"开办角色",
			items:data.role,
			labelShow: false,
            selectAllItems:true,
			changeCallback : function (obj, elem) {
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				mfSysKind.roleNo=$("input[name=roleNo]").val();
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData,function(data){
					var mfSysKindTmp = data.mfSysKind;
					var roleNoName=mfSysKindTmp.ext2;
					if(mfSysKindTmp.ext2.length>50){
						roleNoName = roleNoName.substring(0, 50)+ "...";
					}
					$(".roleNo .span-text").text(roleNoName);
					$(".roleNo .span-text").attr("title",mfSysKindTmp.ext2);
				});
			},
		});
	};
	
	//初始化贷款投向选择组件
	var initFincUsePopUpSelection = function(data){
		$("input[name=fincUse]").popupSelection({
			searchOn:true,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			title:"贷款投向",
			items:data.trade,
			labelShow: false,
            selectAllItems:true,
			changeCallback : function (obj, elem) {
				var newFincUse = $("input[name=fincUse]").val();
				if(newFincUse==""){
					var oldFincUse = $("input[name=popsfincUse]").val();
					 $("input[name=fincUse]").val(oldFincUse);
					alert(top.getMessage("NOT_FORM_EMPTY","贷款投向"),0);
				}else{
					var mfSysKind={};
					mfSysKind.kindNo=kindNo;
					mfSysKind.fincUse=newFincUse;
					var ajaxData = JSON.stringify(mfSysKind);
					MfSysKindConfig.updateKindConfig(ajaxData,function(data){
						var mfSysKindTmp = data.mfSysKind;
						var fincUseName=mfSysKindTmp.fincUseName;
						if(mfSysKindTmp.fincUseName.length>50){
							fincUseName = fincUseName.substring(0, 50)+ "...";
						}
						$("input[name=popsfincUse]").val(mfSysKindTmp.fincUse);
						$(".fincUse .span-text").text(fincUseName);
						$(".fincUse .span-text").attr("title",mfSysKindTmp.fincUseName);
					});
				}
			},
		});
	};
	//初始化业务品种选择组件
	var initBusBreedPopUpSelection = function(data){
		$("input[name=busBreed]").popupSelection({
			searchOn:true,//启用搜索
			inline:false,//下拉模式
			multiple:true,//多选选
			title:"业务品种",
			items:data.busBreed,
			labelShow: false,
            selectAllItems:true,
			changeCallback : function (obj, elem) {
				var newBusBreed = $("input[name=busBreed]").val();
				if(newBusBreed==""){
					var oldBusBreed = $("input[name=popsbusBreed]").val();
					 $("input[name=busBreed]").val(oldBusBreed);
					alert(top.getMessage("NOT_FORM_EMPTY","业务品种"),0);
				}else{
					var mfSysKind={};
					mfSysKind.kindNo=kindNo;
					mfSysKind.busBreed=newBusBreed;
					var ajaxData = JSON.stringify(mfSysKind);
					MfSysKindConfig.updateKindConfig(ajaxData,function(data){
						var mfSysKindTmp = data.mfSysKind;
						var busBreedName=mfSysKindTmp.busBreedName;
						if(mfSysKindTmp.busBreedName.length>50){
                            busBreedName = busBreedName.substring(0, 50)+ "...";
						}
						$("input[name=popsbusBreed]").val(mfSysKindTmp.busBreed);
						$(".busBreed .span-text").text(busBreedName);
						$(".busBreed .span-text").attr("title",mfSysKindTmp.busBreedName);
					});
				}
			},
		});
	};
	//展业区域选择组件
	var initExhibitionAreaPopupSelection = function(data){
		// 开办地区选择组件
		$("input[name=exhibitionArea]").popupSelection({
			ajaxUrl : webPath + "/nmdArea/getAllCityAjax",
			searchOn : true,// 启用搜索
			multiple : true,// 单选
			ztree : true,
			ztreeSetting : setting,
			title : "展业区域",
			labelShow: false,
			async:false,
			handle : BASE.getIconInTd($("input[name=exhibitionArea]")),
			changeCallback : function(elem) {
				BASE.removePlaceholder($("input[name=exhibitionArea]"));
				var nodes = elem.data("treeNode");
				var exhibitionAreaName = "";
				var len = elem.data("treeNode").length;
				for (var i = 0; i < len; i++) {
					exhibitionAreaName += nodes[i].name + "|";
				}
				$("input[name=exhibitionAreaName]").val(exhibitionAreaName);
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				mfSysKind.exhibitionAreaName=$("input[name=exhibitionAreaName]").val();
				mfSysKind.exhibitionArea=$("input[name=exhibitionArea]").val();
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData,function(data){
					var mfSysKindTmp = data.mfSysKind;
					var exhibitionAreaName=mfSysKindTmp.exhibitionAreaName;
					if(exhibitionAreaName.length>50){
						exhibitionAreaName = exhibitionAreaName.substring(0, 50)+ "...";
					}
					$(".exhibitionArea .span-text").text(exhibitionAreaName);
					$(".exhibitionArea .span-text").attr("title",mfSysKindTmp.exhibitionAreaName);
				});
			}
		});
	};

    function zTreeBeforeCheck(treeId, treeNode) {
        return !treeNode.isParent;//当是父节点 返回false 不让选取
    }
    var initAssetClassPopupSelection  = function(data){
        //资产类别 ztree 选择设置
        var ztreeSetting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: { "Y": "s", "N": "" }
            },
            data:    {
                simpleData:{
                    enable:true
                }
            }/*,callback: {
                beforeCheck: zTreeBeforeCheck
            }*/
        };

        //资产类别选择组件
        $("input[name=assetClass]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//下拉模式
            multiple:true,//多选选
            ztree:true,
            parentSelect : true,//选择父节点
            ztreeSetting : ztreeSetting,
            title:"资产类别",
            items:data.assetClass,
            changeCallback : function (elem) {
                var assetClasses = $("input[name=assetClass]").val();
                var node = elem.data("treeNode");
                var assetClassName="";
                for(var i=0;i<node.length;i++){
                    assetClassName = assetClassName+node[i].name+"|";
                }
                var mfSysKind={};
                mfSysKind.kindNo=kindNo;
                mfSysKind.assetClass=assetClasses;
                mfSysKind.assetClassName=assetClassName;

                var ajaxData = JSON.stringify(mfSysKind);
                MfSysKindConfig.updateKindConfig(ajaxData,function(data){
                    var mfSysKindTmp = data.mfSysKind;
                    var assetClassName=mfSysKindTmp.assetClassName;
                    if(assetClassName!=null){
                        if(mfSysKindTmp.assetClassName.length>50){
                            assetClassName = assetClassName.substring(0, 50)+ "...";
                        }
                    }else{
                        assetClassName="";
					}
                    $(".assetClass .span-text").text(assetClassName);
                    $(".assetClass .span-text").attr("title",mfSysKindTmp.assetClassName);
                });

            },
        });
    };
	//初始化选择组件
	var initPopUpSelection = function(data){
		//开办部门选择组件初始化
		initBrNoPopupSelection(data);
		//开办角色选择组件初始化
		initRolePopupSelection(data);
		//贷款投向选择组件初始化
		initFincUsePopUpSelection(data);
		//业务品质选择组件初始化
		initBusBreedPopUpSelection(data);
		//资产类别选择组件初始化
        initAssetClassPopupSelection(data);
        //展业区域选择组件初始化
        initExhibitionAreaPopupSelection(data);
		$(".pops-value").hide();
	};
	
	//产品名称
	var getPrdctNameConfigHtml = function(mfSysKind){
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div">'
		+'<div class="item-title">'
		+'<span >产品名称 </span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-div margin_bottom_5">'
		+'<span class="kindName">'
		+'<span class="span-read"><span class="span-text">'+mfSysKind.kindName+'</span></span>'
		+'<span class="span-edit">'
		+'<input title="产品名称" name="kindName" mustinput="1" class="Required" onblur="func_uior_valTypeImm(this);"  type="text" value="'+mfSysKind.kindName+'">'
		+'</span>'
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	var kindNameBindEvent = function(){
		$(".kindName .span-read").dblclick(function(){
			$(".kindName .span-read").css("display","none");
			$(".kindName .span-edit").css("display","inline-block");
			$("input[name=kindName]").css("width",$(this).width());
			$("input[name=kindName]").focus();
			
		});
		$("input[name=kindName]").blur(function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.kindName=$("input[name=kindName]").val();
			if($("input[name=kindName]").val()!=""){
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData,function(data){
					var mfSysKindTmp = data.mfSysKind;
					$(".kindName .span-text").text(mfSysKindTmp.kindName);
					$(".nav-div .nav-title").text(mfSysKindTmp.kindName+"--"+mfSysKindTmp.busModelName);
					$(".kindName .span-read").css("display","inline-block");
					$(".kindName .span-edit").css("display","none");
				});
			}else{
				alert(top.getMessage("NOT_FORM_EMPTY","产品名称"),0);
			}
		});
	};
	
	
	//产品描述
	var getPrdctDescConfigHtml = function(mfSysKind){
		var htmlStr="";
		var remark = mfSysKind.remark;
		var spanText = mfSysKind.remark;
		if(remark==null){
			spanText='<span class="unregistered">未登记</span>';
			remark="";
		}
		htmlStr=htmlStr+'<div class="item-div">'
		+'<div class="item-title">'
		+'<span >产品描述 </span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-div margin_bottom_5">'
		+'<span class="kindDesc">'
		+'<span class="span-read"><span class="span-text">'+spanText+'</span></span>'
		+'<span class="span-edit">'
		+'<input title="产品描述" name="remark" mustinput="1" class="Required" onblur="func_uior_valTypeImm(this);"  type="text" value="'+remark+'">'
		+'</span>'
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	var kindDescBindEvent = function(){
		$(".kindDesc .span-read").dblclick(function(){
			$(".kindDesc .span-read").css("display","none");
			$(".kindDesc .span-edit").css("display","inline-block");
			$("input[name=remark]").css("width",$(this).width());
			$("input[name=remark]").focus();
			
		});
		$("input[name=remark]").blur(function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.remark=$("input[name=remark]").val();
			if($("input[name=remark]").val()!=""){
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData,function(data){
					var mfSysKindTmp = data.mfSysKind;
					$(".kindDesc .span-text").text(mfSysKindTmp.remark);
					$(".kindDesc .span-read").css("display","inline-block");
					$(".kindDesc .span-edit").css("display","none");
				});
			}else{
				alert(top.getMessage("NOT_FORM_EMPTY","产品描述"),0);
			}
		});
	};
	
	//贷款对象
	var getPrdctLoanObejctConfigHtml = function(mfSysKind,loanObejctList){
		var subHtmlStr = '';
		$.each(loanObejctList,function(i,parmDic){
			var checkStr="";
			var marginStr="";	
			if(mfSysKind.loanObejct==parmDic.optCode){
				checkStr='checked="checked"';
			}
			subHtmlStr = subHtmlStr+'<span class="margin_right_25"><input class="margin_right_5" id="loanObejct'+parmDic.optCode+'" type="radio" name="loanObejct" value="'+parmDic.optCode+'" '+checkStr+'>'+parmDic.optName+'</span>';
		});
		
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div loanObejct">'
		+'<div class="item-title">'
		+'<span>贷款对象</span>'
		+'</div>'
		+'<div class="item-content">'
		+'<div class="main-content-div">'
		+ subHtmlStr
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//贷款对象绑定事件
	var loanObejctBindEvent = function(){
		$(".loanObejct input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.loanObejct=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//币种
	var getPrdctCurTypeConfigHtml = function(mfSysKind,curTypeList){
		var subHtmlStr = "";
		$.each(curTypeList,function(i,parmDic){
			var checkStr="";
			var marginStr="";	
			if(mfSysKind.curNo==parmDic.optCode){
				checkStr='checked="checked"';
			}
			if(i>0){
				marginStr="margin_left_10";	
			}
			subHtmlStr = subHtmlStr+'<input class="margin_right_5 '+marginStr+'" id="curNo'+parmDic.optCode+'" type="radio" name="curNo" value="'+parmDic.optCode+'" '+checkStr+'>'+parmDic.optName;
		});
		
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div curNo">'
		+'<div class="item-title">'
		+'<span>币种 </span>'
		+'</div>'
		+'<div class="item-content">'
		+ subHtmlStr
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//币种绑定事件
	var curNoBindEvent = function(){
		$(".curNo input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.curNo=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//子产品
	var getPrdctSubKindConfigHtml = function(mfSysKind,subKindList){
		var subHtmlStr = '<div class="main-content-desc"><span class="content-desc">产品子类：同一产品下不同的子类</span></div>';
		var len = subKindList.length;
		if(len==0){
			subHtmlStr = subHtmlStr+'<div class="main-content-div">';
		}
		$.each(subKindList,function(i,parmDic){
			var curChecked="";
			if(mfSysKind.subKindNo!=null && mfSysKind.subKindNo!=""){
				if(mfSysKind.subKindNo.indexOf(parmDic.optCode+"|")!=-1){
					curChecked="curChecked";
				}else if(mfSysKind.subKindNo==parmDic.optCode){
					curChecked="curChecked";
				}
			}
			if(i%4==0){
				if(i!=0){
					subHtmlStr = subHtmlStr+'</div>';
					subHtmlStr = subHtmlStr+'<div class="main-content-div clearfix " style="padding-top:0px;">';
				}else{
					subHtmlStr = subHtmlStr+'<div class="main-content-div clearfix ">';
				}
			}
			subHtmlStr = subHtmlStr+'<span class="item-checkbox col-md-3" >'
			+'<span class="checkbox-span '+curChecked+' margin_right_5" data-subkindno="'+parmDic.optCode+'"><i class="i i-gouxuan1"></i></span>'
			+'<span>'+parmDic.optName+'</span>'
			+'</span>';
		});
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div subKindNo" data-oldval="'+mfSysKind.subKindNo+'">'
		+'<div class="item-title"><span>产品子类 </span></div>'
		+'<div class="item-content">'
		+ subHtmlStr
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//子产品绑定事件
	var subKindBindEvent = function(){
		$(".subKindNo .checkbox-span").bind("click",function(){
			if($(this).hasClass("curChecked")){
				$(this).removeClass("curChecked");
			}else{
				$(this).addClass("curChecked");
			}
			var tmpStr="";
			$.each($(".subKindNo .checkbox-span.curChecked"),function(index,item){
				tmpStr = tmpStr +$(item).data("subkindno")+"|";
			});
			$(".subKindNo").data("oldval",tmpStr);
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.subKindNo=tmpStr;
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	
	//客户类别
	var getPrdctCusTypeConfigHtml = function(mfSysKind,cusSubTypeList){
		var subHtmlStr = '<div class="main-content-desc"><span class="content-desc">客户类别：规定什么类型的客户可以办理该产品相关业务</span></div>'
			+'<div class="main-content-div">'
			$.each(cusSubTypeList,function(i,parmDic){
				var curChecked="";
				if(mfSysKind.cusType.indexOf(parmDic.optCode+"|")!=-1){
					curChecked="curChecked";
				}else if(mfSysKind.cusType==parmDic.optCode){
					curChecked="curChecked";
				}
				subHtmlStr = subHtmlStr+'<span class="item-checkbox margin_right_25">'
				+'<span class="checkbox-span '+curChecked+' margin_right_5" data-custype="'+parmDic.optCode+'"><i class="i i-gouxuan1"></i></span>'
				+'<span>'+parmDic.optName+'</span>'
				+'</span>';
			});
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div cusType" data-oldval="'+mfSysKind.cusType+'">'
		+'<div class="item-title"><span>客户类别 </span></div>'
		+'<div class="item-content">'
		+ subHtmlStr
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//客户类别绑定事件
	var cusTypeBindEvent = function(){
		$(".cusType .checkbox-span").bind("click",function(){
			if($(this).hasClass("curChecked")){
				$(this).removeClass("curChecked");
			}else{
				$(this).addClass("curChecked");
			}
			var tmpStr="";
			$.each($(".cusType .checkbox-span.curChecked"),function(index,item){
				tmpStr = tmpStr +$(item).data("custype")+"|";
			});
			if(tmpStr==""){
				$(this).addClass("curChecked");
				alert(top.getMessage("NOT_FORM_EMPTY","客户类别"),0);
			}else{
				$(".cusType").data("oldval",tmpStr);
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				mfSysKind.cusType=tmpStr;
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData);
			}
		});
	};
	
	
	//开办部门
	var getPrdctDepartmentConfigHtml = function(mfSysKind){
		var brNoName=mfSysKind.ext1;
		if(mfSysKind.ext1.length>50){
			brNoName = brNoName.substring(0, 50)+ "...";
		}
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div brNo">'
		+'<div class="item-title"><span>开办部门</span></div>'
		+'<div class="item-content">'
		+'<div class="main-content-div">'
		+'<span class="span-read">'
		+'<span class="span-text" title="'+mfSysKind.ext1+'">'+brNoName+'</span>'
		+'<input  name="brNo" type="hidden" value="'+mfSysKind.brNo+'"/>'
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//开办部门编辑绑定点击事件
	var brNoBindEvent = function(data){
		$(".brNo .span-read").dblclick(function(){
			$(this).find(".pops-value").click();
		});
	};
	//开办角色
	var getPrdctRoleConfigHtml = function(mfSysKind){
		var roleNoName=mfSysKind.ext2;
		if(mfSysKind.ext2.length>50){
			roleNoName = roleNoName.substring(0, 50)+ "...";
		}
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div roleNo">'
		+'<div class="item-title"><span>开办角色</span></div>'
		+'<div class="item-content">'
		+'<div class="main-content-div">'
		+'<span class="span-read">'
		+'<span class="span-text" title="'+mfSysKind.ext2+'">'+roleNoName+'</span>'
		+'<input  name="roleNo" title="开办角色" type="hidden" value="'+mfSysKind.roleNo+'"/>'
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	//开办角色编辑绑定点击事件
	var roleNoBindEvent = function(){
		$(".roleNo .span-read").dblclick(function(){
			$(this).find(".pops-value").click();
		});
	};
	//产品属性
	var getPrdctPropertyConfigHtml = function(mfSysKind,kindPropertyList){
		var subHtmlStr = '<div class="main-content-desc"><span class="content-desc">产品属性：说明该产品的适用范围是线上还是线下</span></div>'
		+'<div class="main-content-div">';
		$.each(kindPropertyList,function(i,parmDic){
			var curChecked="";
			if(mfSysKind.kindProperty.indexOf(parmDic.optCode+"|")!=-1){
				curChecked="curChecked";
			}else if(mfSysKind.kindProperty==parmDic.optCode){
				curChecked="curChecked";
			}
			subHtmlStr = subHtmlStr+'<span class="item-checkbox margin_right_25">'
			+'<span class="checkbox-span '+curChecked+' margin_right_5" data-kindproperty="'+parmDic.optCode+'"><i class="i i-gouxuan1"></i></span>'
			+'<span>'+parmDic.optName+'</span>'
			+'</span>';
		});
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div kindProperty" data-oldval="'+mfSysKind.kindProperty+'">'
		+'<div class="item-title"><span>产品属性 </span></div>'
		+'<div class="item-content">'
		+ subHtmlStr
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	
	//产品属性绑定事件
	var kindPropertyBindEvent = function(){
		$(".kindProperty .checkbox-span").bind("click",function(){
			if($(this).hasClass("curChecked")){
				$(this).removeClass("curChecked");
			}else{
				$(this).addClass("curChecked");
			}
			var tmpStr="";
			$.each($(".kindProperty .checkbox-span.curChecked"),function(index,item){
				tmpStr = tmpStr +$(item).data("kindproperty")+"|";
			});
			if(tmpStr==""){
				$(this).addClass("curChecked");
				alert(top.getMessage("NOT_FORM_EMPTY","产品属性"),0);
			}else{
				$(".kindProperty").data("oldval",tmpStr);
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				mfSysKind.kindProperty=tmpStr;
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData);
			}
		});
	};
	//合同性质
	var getPrdctPactPropertyConfigHtml = function(mfSysKind,pactPropertyList,pactPropertyMap){
		var subHtmlStr = '<div class="main-content-div">';
		$.each(pactPropertyList,function(i,parmDic){
			var curChecked="";
			if(mfSysKind.pactProperty.indexOf(parmDic.optCode+"|")!=-1){
				curChecked="curChecked";
			}else if(mfSysKind.pactProperty==parmDic.optCode){
				curChecked="curChecked";
			}
			subHtmlStr = subHtmlStr+'<span class="item-checkbox margin_right_25">'
			+'<span class="checkbox-span '+curChecked+' margin_right_5" data-pactproperty="'+parmDic.optCode+'"><i class="i i-gouxuan1"></i></span>'
			+'<span>'+parmDic.optName+'</span>'
			+'</span>';
		});
		//默认合同性质
		subHtmlStr = subHtmlStr+'<div class="main-content"><span>默认合同性质：</span><span class="pactPropertyDef">';
		var pactPropertyArray = mfSysKind.pactProperty.split("|");
		$.each(pactPropertyArray,function(i,item){
			if(item!=""){
				var checkStr="";
				if(mfSysKind.pactPropertyDef==item){
					checkStr='checked="checked"';
				}
				subHtmlStr = subHtmlStr+'<span id="pactPropertyDef'+item+'" class="margin_right_10"><input class="margin_right_5"  type="radio" name="pactPropertyDef" value="'+item+'" '+checkStr+'>'+pactPropertyMap[item]+'</span>';
			}
		});
		subHtmlStr = subHtmlStr+"</span></div>";
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div pactProperty" data-oldval="'+mfSysKind.pactProperty+'">'
		+'<div class="item-title"><span>合同性质 </span></div>'
		+'<div class="item-content">'
		+ subHtmlStr
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	var pactPropertyBindEvent = function(pactPropertyMap){
		$(".pactProperty .checkbox-span").bind("click",function(){
			var curVal = $(this).data("pactproperty");
			var curpactPropertyDefHtml = $(".pactPropertyDef").prop('innerHTML');
			if($(this).hasClass("curChecked")){
				$(this).removeClass("curChecked");
				$("#pactPropertyDef"+curVal).remove();
			}else{
				$(this).addClass("curChecked");
				$(".pactPropertyDef").append('<span id="pactPropertyDef'+curVal+'" class="margin_right_10"><input class="margin_right_5"  type="radio" name="pactPropertyDef" value="'+curVal+'">'+pactPropertyMap[curVal]+'</span>');
			}
			var tmpStr="";
			$.each($(".pactProperty .checkbox-span.curChecked"),function(index,item){
				tmpStr = tmpStr +$(item).data("pactproperty")+"|";
			});
			if(tmpStr==""){
				$(this).addClass("curChecked");
				$(".pactPropertyDef").append(curpactPropertyDefHtml);
				alert(top.getMessage("NOT_FORM_EMPTY","合同性质"),0);
			}else{
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				$(".pactProperty").data("oldval",tmpStr);
				mfSysKind.pactProperty=tmpStr;
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData);
			}
		});
	};
	var pactPropertyDefBindEvent = function(){
		$(".pactPropertyDef input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.pactPropertyDef=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	
	//担保方式
	var getPrdctVouTypeConfigHtml = function(mfSysKind,vouTypeList,vouTypeMap){
		var subHtmlStr = '<div class="main-content-desc"><span class="content-desc">担保方式：该产品支持质押、抵押、保证以及信用多种担保方式</span></div>'
		+'<div class="main-content-div">';
		$.each(vouTypeList,function(i,parmDic){
			var curChecked="";
			if(mfSysKind.vouType.indexOf(parmDic.optCode+"|")!=-1){
				curChecked="curChecked";
			}else if(mfSysKind.vouType==parmDic.optCode){
				curChecked="curChecked";
			}
			subHtmlStr = subHtmlStr+'<span class="item-checkbox margin_right_25">'
			+'<span class="checkbox-span '+curChecked+' margin_right_5" data-voutype="'+parmDic.optCode+'"><i class="i i-gouxuan1"></i></span>'
			+'<span>'+parmDic.optName+'</span>'
			+'</span>';
		});
		//默认担保方式
		subHtmlStr = subHtmlStr+'<div class="main-content"><span>默认担保方式：</span><span class="vouTypeDef">';
		var vouTypeArray = mfSysKind.vouType.split("|");
		$.each(vouTypeArray,function(i,item){
			if(item!=""){
				var checkStr="";
				var marginStr="";	
				if(mfSysKind.vouTypeDef==item){
					checkStr='checked="checked"';
				}
				subHtmlStr = subHtmlStr+'<span id="vouTypeDef'+item+'" class="margin_right_10"><input class="margin_right_5"  type="radio" name="vouTypeDef" value="'+item+'" '+checkStr+'>'+vouTypeMap[item]+'</span>';
			}
		});
		subHtmlStr = subHtmlStr+'</span></div>';
		
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div vouType" data-oldval="'+mfSysKind.vouType+'">'
		+'<div class="item-title"><span>担保方式 </span></div>'
		+'<div class="item-content">'
		+ subHtmlStr
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	var vouTypeBindEvent = function(vouTypeMap){
		$(".vouType .checkbox-span").bind("click",function(){
			var curVal = $(this).data("voutype");
			var curVouTypeDefHtml = $(".vouTypeDef").prop('innerHTML');
			if($(this).hasClass("curChecked")){
				$(this).removeClass("curChecked");
				$("#vouTypeDef"+curVal).remove();
			}else{
				$(this).addClass("curChecked");
				$(".vouTypeDef").append('<span id="vouTypeDef'+curVal+'" class="margin_right_10"><input class="margin_right_5"  type="radio" name="vouTypeDef" value="'+curVal+'">'+vouTypeMap[curVal]+'</span>');
			}
			var tmpStr="";
			$.each($(".vouType .checkbox-span.curChecked"),function(index,item){
				tmpStr = tmpStr +$(item).data("voutype")+"|";
			});
			if(tmpStr==""){
				$(this).addClass("curChecked");
				$(".vouTypeDef").append(curVouTypeDefHtml);
				alert(top.getMessage("NOT_FORM_EMPTY","担保方式"),0);
			}else{
				$(".vouType").data("oldval",tmpStr);
				var mfSysKind={};
				mfSysKind.kindNo=kindNo;
				mfSysKind.vouType=tmpStr;
				var ajaxData = JSON.stringify(mfSysKind);
				MfSysKindConfig.updateKindConfig(ajaxData);
			}
		});
	};
	var vouTypeDefBindEvent = function(){
		$(".vouTypeDef input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.vouTypeDef=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};


    //资产类别
    var getPrdctAssetClassConfigHtml = function(mfSysKind){
        var assetClassName=mfSysKind.assetClassName;
        if(assetClassName!=null){
            if(mfSysKind.assetClassName.length>50){
                assetClassName = assetClassName.substring(0, 50)+ "...";
            }
        }else{
            assetClassName="";
        }
        var htmlStr="";
        htmlStr=htmlStr+'<div class="item-div assetClass">'
            +'<div class="item-title"><span>资产类别</span></div>'
            +'<div class="item-content">'
            +'<div class="main-content-div">'
            +'<span class="span-read">'
            +'<span class="span-text" title="'+mfSysKind.assetClassName+'">'+assetClassName+'</span>'
            +'<input  name="assetClass" type="hidden" value="'+mfSysKind.assetClass+'"/>'
            +'</span>'
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };

    //资产类别编辑绑定点击事件
    var assetClassBindEvent = function(data){
        $(".assetClass .span-read").dblclick(function(){
            $(this).find(".pops-value").click();
        });
    };

	
	//贷款投向
	var getPrdctFincUseConfigHtml = function(mfSysKind){
		var fincUseName=mfSysKind.fincUseName;
		if(mfSysKind.fincUseName.length>50){
			fincUseName = fincUseName.substring(0, 50)+ "...";
		}
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div fincUse">'
		+'<div class="item-title"><span>贷款投向 </span></div>'
		+'<div class="item-content">'
		+'<div class="main-content-div">'
		+'<span class="span-read">'
		+'<span class="span-text" title="'+mfSysKind.fincUseName+'">'+fincUseName+'</span>'
		+'<input  name="fincUse" title="贷款投向" type="hidden" value="'+mfSysKind.fincUse+'"/>'
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};

	var fincUseBindEvent = function(){
		$(".fincUse .span-read").dblclick(function(){
			$(this).find(".pops-value").click();
		});
	};

    //业务品种
    var getPrdctBusBreedConfigHtml = function(mfSysKind){
        var busBreedName=mfSysKind.busBreedName;
        if(mfSysKind.busBreedName.length>50){
            busBreedName = busBreedName.substring(0, 50)+ "...";
        }
        var htmlStr="";
        htmlStr=htmlStr+'<div class="item-div busBreed">'
            +'<div class="item-title"><span>业务品种 </span></div>'
            +'<div class="item-content">'
            +'<div class="main-content-div">'
            +'<span class="span-read">'
            +'<span class="span-text" title="'+mfSysKind.busBreedName+'">'+busBreedName+'</span>'
            +'<input  name="busBreed" title="业务品种" type="hidden" value="'+mfSysKind.busBreed+'"/>'
            +'</span>'
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };
    var busBreedBindEvent = function(){
        $(".busBreed .span-read").dblclick(function(){
            $(this).find(".pops-value").click();
        });
    };
	//合同额度是否循环
	var getPrdctAuthCycleConfigHtml = function(mfSysKind){
		var checkStr0="";
		var checkStr1="";
		if(mfSysKind.authCycle=="1"){
			checkStr1='checked="checked"';
		}else{
			checkStr0='checked="checked"';
		}
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div authCycle">'
		+'<div class="item-title"><span>合同额度是否循环</span></div>'
		+'<div class="item-content">'
		+'<div class="main-content-div">'
		+'<span id="authCycle1" class="margin_right_25"><input class="margin_right_5"  type="radio" name="authCycle" value="1" '+checkStr1+'>是</span>'
		+'<span id="authCycle0" class="margin_right_25"><input class="margin_right_5"  type="radio" name="authCycle" value="0" '+checkStr0+'>否</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};

    //是否进行额度测算
    var getIfCreditCalcConfigHtml = function(mfSysKind){
        var checkStr0="";
        var checkStr1="";
        if(mfSysKind.ifCreditCalc=="1"){
            checkStr1='checked="checked"';
        }else{
            checkStr0='checked="checked"';
        }
        var htmlStr="";
        htmlStr=htmlStr+'<div class="item-div ifCreditCalc">'
            +'<div class="item-title"><span>是否进行额度测算</span></div>'
            +'<div class="item-content">'
            +'<div class="main-content-div">'
            +'<span id="ifCreditCalc1" class="margin_right_25"><input class="margin_right_5"  type="radio" name="ifCreditCalc" value="1" '+checkStr1+'>是</span>'
            +'<span id="ifCreditCalc0" class="margin_right_25"><input class="margin_right_5"  type="radio" name="ifCreditCalc" value="0" '+checkStr0+'>否</span>'
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };
    //是否小微
    var getIfSmailCamllConfigHtml = function(mfSysKind){
        var checkStr0="";
        var checkStr1="";
        if(mfSysKind.ifSmailCamll=="1"){
            checkStr1='checked="checked"';
        }else{
            checkStr0='checked="checked"';
        }
        var htmlStr="";
        htmlStr=htmlStr+'<div class="item-div ifSmailCamll">'
            +'<div class="item-title"><span>是否小微</span></div>'
            +'<div class="item-content">'
            +'<div class="main-content-div">'
            +'<span id="ifSmailCamll1" class="margin_right_25"><input class="margin_right_5"  type="radio" name="ifSmailCamll" value="1" '+checkStr1+'>是</span>'
            +'<span id="ifSmailCamll0" class="margin_right_25"><input class="margin_right_5"  type="radio" name="ifSmailCamll" value="0" '+checkStr0+'>否</span>'
            +'</div>'
            +'</div>'
            +'</div>';
        return htmlStr;
    };


    //合同额度是否循环绑定事件
	var authCycleBindEvent = function(){
		$(".authCycle input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.authCycle=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	var ifCreditCalcBindEvent = function(){
		$(".ifCreditCalc input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.ifCreditCalc=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};
	var ifSmailCamllBindEvent = function(){
		$(".ifSmailCamll input[type=radio]").bind("click",function(){
			var mfSysKind={};
			mfSysKind.kindNo=kindNo;
			mfSysKind.ifSmailCamll=$(this).val();
			var ajaxData = JSON.stringify(mfSysKind);
			MfSysKindConfig.updateKindConfig(ajaxData);
		});
	};

	//展业区域
	var getExhibitionAreaConfigHtml = function(mfSysKind){
		var exhibitionAreaName=mfSysKind.exhibitionAreaName;
		if(exhibitionAreaName == null){
            exhibitionAreaName = "";
        }
		if(exhibitionAreaName.length>50){
			exhibitionAreaName = exhibitionAreaName.substring(0, 50)+ "...";
		}
		var htmlStr="";
		htmlStr=htmlStr+'<div class="item-div exhibitionArea">'
		+'<div class="item-title"><span>展业区域 </span></div>'
		+'<div class="item-content">'
		+'<div class="main-content-div">'
		+'<span class="span-read">'
		+'<span class="span-text" title="'+exhibitionAreaName+'">'+exhibitionAreaName+'</span>'
		+'<input  name="exhibitionArea" title="展业区域" type="hidden" value="'+mfSysKind.exhibitionArea+'"/>'
		+'<input  name="exhibitionAreaName" title="展业区域" type="hidden" value="'+exhibitionAreaName+'"/>'
		+'</span>'
		+'</div>'
		+'</div>'
		+'</div>';
		return htmlStr;
	};
	var exhibitionAreaBindEvent = function(){
		$(".exhibitionArea .span-read").dblclick(function(){
			$(this).find(".pops-value").click();
		});
	};
	return{
		init:_init,
	};
}(window, jQuery);