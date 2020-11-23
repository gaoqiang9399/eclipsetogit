
/*注意：为每个dialog弹窗设置id，而且id不要与方法名一致；关闭弹窗时使用dialog.get(id名).close(回传参数);
 * 要使用onshow方法将returnValue置为null。
 * */
//选择资产类别
function selectConsClassDialog(callback){
    dialog({
        id:"consClassDialog",
        title:'选择资产类别',
        url:webPath+'/mfOaConsClass/getSelectListPage',
        width:350,
        height:300,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
// 选择分润配置
function selectShareProfitConfigDialog(callback){
    dialog({
        id:"shareProfitConfig",
        title:'选择基础分润配置',
        url:webPath + '/mfShareProfitConfig/getListPage',
        width:700,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
// 选择风险调级合同
function selectVouAfterRiskLevelAdjustDialog(callback){
    dialog({
        id:"vouAfterRiskLevelAdjust",
        title:'选择委托担保合同编号',
        url:webPath + '/mfVouAfterRiskLevelAdjust/getvouAfterRiskLevelAdjustListPage',
        width:1200,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
// 选择退费申请合同
function selectRefundManageDialog(callback){
    dialog({
        id:"refundManage",
        title:'选择委托担保合同编号',
        url:webPath + '/mfRefundManage/getRefundManageListPage',
        width:1200,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
// 选择手续费管理申请合同
function selectHandAmtManageDialog(callback){
    dialog({
        id:"handAmtManage",
        title:'选择委托担保合同编号',
        url:webPath + '/mfHandAmtManage/getHandAmtManageListPage',
        width:1200,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择低值易耗品
function selectConsDialog(callback){
    var classNo = $("input[name='classNo']").val();
    dialog({
        id:"consDialog",
        title:'选择低值易耗品',
        //查询已选类别下的易耗品
        url:webPath+'/mfOaCons/getSelectListPage?classNo='+classNo,
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择业务员
function selectUserDialog(callback){
    dialog({
        id:"userDialog",
        title:'选择业务员',
        url:webPath+'/sysUser/getListPageForSel?opNoType=1',
        width:900,
        height:540,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择业务员
/*function selectUserDialog(callback){
    dialog({
        id:"userDialog",
        title:'选择人员',
        url:webPath+'/sysUser/getListPageForSel?opNoType=1',
        width:900,
        height:540,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};*/
//选择业务员
function selectUserDialog2(callback){
    dialog({
        id:"userDialog",
        title:'选择人员',
        url:webPath+'/sysUser/getListPageForSel2?opNoType=1',
        width:900,
        height:540,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择发布范围
function selectNoticeScorpDialog(callback){
    dialog({
        id:"NoticeScorpDialog",
        title:'选择发布范围',
        url:webPath+'/mfOaNotice/getSysUserListPage?opNoType=1',
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择操作员。多选.自定义弹出框标题
function selectUserCustomTitleDialog(title,callback){
    var checkedNode=title.split("?")[1];
    if(checkedNode){
        checkedNode=checkedNode.replace(new RegExp(/(\|)/g),',').replace(new RegExp(/(;)/g),',');
    }
    dialog({
        id:"NoticeScorpDialog",
        title:title.split("?")[0],
        url:webPath+'/mfOaNotice/getSysUserListPage?checkedNode='+checkedNode+"&opNoType=1",
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择部门
function selectOrgSelectDialog(callback,opNoType){
    dialog({
        id:"orgSelectDialog",
        title:'选择部门',
        url:webPath+'/sysOrg/sysOrgSelect?opNoType='+opNoType,
        width:525,
        height:495,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择部门,多选
function selectOrgCheckDialog(callback){
    dialog({
        id:"orgSelectDialog",
        title:'选择部门',
        url: webPath+'/sysOrg/sysOrgSelectCheck',
        width:350,
        height:420,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性docNo,docName；如果多个，使用|分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }else{//没有选中的值时，传空
                if(typeof(callback)== "function"){
                    callback("");
                }
            }
        }
    }).showModal();
};
//选择客户手机号
function selectCusTelDialog (callback){
    dialog({
        id:"getCusPhoneDialog",
        title:'选择收信人',
        url:webPath+'/mfCusCustomer/findCusPhone',
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择员工手机号
function selectSysUserTelDialog(callback){
    dialog({
        id:"SysUserTelDialog",
        title:'选择收信人',
        url:webPath+'/mfToolsSendMessage/getSysUserListPage',
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择所属集团
function selectGroupDialog(callback){
    dialog({
        id:"groupDialog",
        title:'选择所属集团',
        url:webPath+'/mfCusGroup/getListPage',
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:groupName,groupNo
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                    $("input[name=groupName]").val(this.returnValue.groupName);
                }
            }
        }
    }).showModal();
};
//选择客户当selectType为1时选择客户，selectType为2时选择客户联系人和联系方式，
//selectType为3时选择与一个合同相关的的客户,cusType记录合同号
//selectType为4时选择主体客户关联关系，不能是主体客户身，cusType记录当前主体客户号
//selectType为5时选择个人客户或企业客户 cusType 1-企业客户 2-个人客户
//selectType为6时选择个人客户和企业客户
function selectCusDialog(callback,cusType,title,selectType){
    if(title == null || title == undefined || title==''){
        title = '选择客户';
    }
    var url;
    if("1" == selectType){
        url = "/mfCusCustomer/getListPageForSelect?cusType="+cusType;
        if(cusType == null || cusType == ''){
            url = "/mfCusCustomer/getListPageForSelect";
        }
    }else if("3" == selectType){
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType+"&cusType="+cusType;
    }else if("4" == selectType){
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType+"&cusType="+cusType;
    }else if("5" == selectType){
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType+"&cusType="+cusType;
    }else if("6" == selectType){//选择个人和企业客户
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType;
    }else if("7" == selectType){//根据客户类型
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType+"&cusType="+cusType;
    }else if("8" == selectType){//进件
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType+"&cusType="+cusType;
    } else if("12" == selectType){//
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType;
    }
    else{
        url = "/mfCusCustomer/getListPageForSelect?selectType="+selectType;
    }

    dialog({
        id:'cusDialog',
        title:title,
        url:webPath+url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                    getCusInfoArtDialog(this.returnValue);
                }
            }
        }
    }).showModal();
};
//选择借据
//selectType为1时选择传入客户cusNo的借据
function selectFincDialog(callback,cusNo,title,selectType){
    if(title == null || title == undefined || title==''){
        title = '选择业务';
    }
    var url;
    if("1" == selectType){
        url = webPath+"/mfBusFincApp/getListPageForSelect?cusNo="+cusNo;
    }
    //20190314yxl 在进件 新增 业务申请中筛选用户借据状态为5的借据
    else  if("finc_sts5" == selectType){
        url = webPath+"/mfBusFincApp/getListPageForSelect?cusNo="+cusNo+"&sts5=5";
    }
    else{
        url = webPath+"/mfBusFincApp/getListPageForSelect?selectType="+selectType;
    }

    dialog({
        id:'fincDialog',
        title:title,
        url: url,
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                    getCusInfoArtDialog(this.returnValue);
                }
            }
        }
    }).showModal();
};
//选择核心企业
function selectCusCoreCompanyDialog(callback){

    dialog({
        id:'selectCusCoreCompanyDialog',
        title:"选择核心企业",
        url: webPath +'/mfCusCoreCompany/getListPageForSelect',
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{

                }
            }
        }
    }).showModal();
};
// 选择链属企业
function selectCompanyName(obj,callback){

    dialog({
        id:'cusDialog',
        title:"选择链属企业",
        url: webPath +'/mfCusCustomer/getListPageForSelect?selectType=5&cusType=1',
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(obj,this.returnValue);
                }else{

                }
            }
        }
    }).showModal();
};
//选择银行卡账号
function selectBankAccDialog(callback,cusNo,title){
    if(title == null){
        title = '选择银行卡账号';
    }
    var url = '/mfCusBankAccManage/getListPageForSelect';
    if(cusNo != null){
        url = '/mfCusBankAccManage/getListPageForSelect?cusNo='+cusNo;
    }
    dialog({
        id:'bankAccDialog',
        title:title,
        url:webPath+url,
        width:700,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:id,accountNo,accountName,bank
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                    var bankAcc = this.returnValue;
                    getBankAccInfoArtDialog(bankAcc);
                }
            }
        }
    }).showModal();
};

//选择公司账号
function selectCusBankAccDialog(callback,title,accProperty){
    if(title == null){
        title = '选择公司账号';
    }
    var url = '/cwCusBankAccManage/getListPageForSelect';
    if(accProperty != null){
        url = '/cwCusBankAccManage/getListPageForSelect?accProperty='+accProperty;
    }
    dialog({
        id:'cusBankAccDialog',
        title:title,
        url:webPath+url,
        width:1000,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
};

function selectrili(obj,name,mintime,maxtime,chooseCallback){
    var opts = {};

    // elem
    if (!name) {
        opts.elem = $(obj).parent().parent().find("input")[0];
    } else {
        opts.elem = $(obj).parent().parent().find("input[name="+name+"]")[0];
    }

    if (mintime) {
        opts.min = mintime;
    }
    if (maxtime) {
        opts.max = maxtime;
    }

    if (typeof chooseCallback === "function") {
        opts.choose = chooseCallback;
    }

    fPopUpCalendarDlg(opts);
};

function selectFincUseDialog(callback){
    dialog({
        id:"fincUseDialog",
        title:'投向分类',
        url:webPath+'/mfBusApply/getPageFinUse',
        width:650,
        height:300,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性fincUse,fincUseShow
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
function selectAreaDialog(callback){
    top.dialog({
        id:"areaDialog",
        title:'行政区域',
        url:webPath+'/nmdArea/getListAll',
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

function selectAreaByStepLoadingDialog(callback, ele){
    top.dialog({
        id:"areaDialog",
        title:'行政区域',
        url: webPath+'/nmdArea/getLv1Page',
        width:350,
        height:420,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性disNo,disName
                if(typeof(callback)== "function"){
                    callback(this.returnValue, ele);
                }else{
                }
            }
        }
    }).showModal();
};
//zm
function selectAreaByStepLoadingDialogZm(callback, ele){
    top.dialog({
        id:"areaDialog",
        title:'行政区域',
        url: webPath+'/nmdArea/getLv1PageSort',
        width:350,
        height:420,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性disNo,disName
                if(typeof(callback)== "function"){
                    callback(this.returnValue, ele);
                }else{
                }
            }
        }
    }).showModal();
};

function selectBankAreaDialog(callback){
    top.dialog({
        id:"areaDialog",
        title:'银行城市',
        url:webPath+'/nmdArea/getBankAreaListAll',
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
function selectwayClassDialog(callback){
    top.dialog({
        id:"wayClassDialog",
        title:'行业分类',
        url:webPath+'/mfCusCorpBaseInfo/wayClassSelectPge',
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
//选择产品种类,支持多选
function selectKindMutiDialog(callback,kindNo){
    dialog({
        id:"kindMutiDialog",
        title:'选择产品',
        url:webPath+'/mfSysKind/getPageForMutiSel?kindNo='+kindNo,
        width:600,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:kindNo,kindName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//根据业务模式展示可供选择的产品种类 多选
function selectKindByBusModelMutiDialog(callback,kindNo,busModelArr){
    dialog({
        id:"kindMutiDialog",
        title:'选择产品',
        url:webPath+'/mfSysKind/getPageForMutiSelByBusModel?busModel='+busModelArr+"&kindNo="+kindNo,
        width:600,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:kindNo,kindName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择客户类型,支持多选
function selectCusTypeMutiDialog(callback,cusType){
    dialog({
        id:"cusTypeMutiDialog",
        title:'选择客户类型',
        url:webPath+'/mfCusFormConfig/getCusTypeForMutiSel?optCode='+cusType,
        width:600,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:cusTypeNo,cusTypeDes;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择文档类型,支持多选
function selectDocTypeDialog(callback,docSplitNo,docType,obj){
    var theEvent = window.event || arguments.callee.caller.arguments[0];
    var srcElement = theEvent.srcElement || theEvent.target;

    dialog({
        id:"docTypeDialog",
        title:'文档类型',
        url:webPath+'/docTypeConfig/getPageForSel?docType='+docType+'&docSplitNo='+docSplitNo,
        width:350,
        height:420,
        quickClose : true,
//		backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性docNo,docName；如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue,obj);
                }
            }else{//没有选中的值时，传空
                if(typeof(callback)== "function"){
                    callback("",obj);
                }
            }
        }
    }).show(srcElement);
};
//选择场景,支持多选
function selectScenceMutiDialog(callback,scNo){
    dialog({
        id:"scenceMutiDialog",
        title:'选择场景',
        url:webPath+'/docModel/getScenceForMutiSel?scNo='+scNo,
        width:600,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性docNo,docName；如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

function selectCusIdNumDialog(callback){
    dialog({
        id:"CusIdNumDialog",
        title:'选择客户身份证号',
        url:webPath+'/mfCusCorpBaseInfo/getListPageForSelectIdNum?legalIdType=0',
        width:550,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:客户名称。联系人，联系人手机号码
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择多个角色
function selectRoleDialog(callback,roleNo){
    var opNoType1 = "1";
    if(typeof (opNoType) != "undefined" ){
        opNoType1 = opNoType;
    }
    var url = webPath+'/sysRole/getPageForMutiSel?roleNo='+roleNo + "&opNoType=" + opNoType1;
    url = encodeURI(url);
    dialog({
        id:"SysRoleDialog",
        title:'选择角色',
        url:url,
        width:700,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:roleNo,roleName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择案件
function selectLawsuit(callback){

    dialog({
        id:"Lawsuit",
        title:'选择案件',
        url:webPath+'/mfLawsuit/getSelectInfo',
        width:700,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:roleNo,roleName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择多个用户(催收方案配置用)
function selectUserForRecDialog(callback,recNo){
    dialog({
        id:"SysUserForRecDialog",
        title:'选择接收人',
        url:webPath+'/recallBase/getSysOrgListPage?recNo='+recNo,
        width:1100,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
}
//选择押品类型弹窗
function selectCollateralTypeDataDialog(callback,vouType){
    dialog({
        id:"collateralTypeDataDialog",
        title:'选择押品种类',
        url:webPath+'/mfCollateralClass/getListPageForSelect?vouType='+vouType,
        width:800,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
}
//选择业务拥有的未入库或已出库押品，并排除场景已使用的押品
function selectBussCollateralDataDialog(callback,appId,pledgeNoStr){
    var  tmpheight=$(window.document).height();
    tmpheight=tmpheight*0.80;
    dialog({
        id:"collateralDataDialog",
        title:'选择押品',
        url:webPath+'/pledgeBaseInfo/getPledgeListByBussPageSelect?&appId='+appId+"&pledgeNoStr="+pledgeNoStr,
        width:800,
        height: tmpheight,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择业务中关联的未出库的押品
function selectCollateralDataDialog(callback,cusNo,appId,pledgeMethod){
    var  tmpheight=$(window.document).height();
    tmpheight=tmpheight*0.80;
    dialog({
        id:"collateralDataDialog",
        title:'选择押品',
        url:webPath+'/pledgeBaseInfo/getPledgeInfoPageForSelect?pledgeMethod='+pledgeMethod+"&cusNo="+cusNo+"&appId="+appId,
        width:800,
        height: tmpheight,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择担保方式，多选
function selectVouTypeMutiDialog(callback,vouType){
    dialog({
        id:"vouTypeMutiDialog",
        title:'选择担保方式',
        url:webPath+'/riskPreventSceGen/getVouTypeForMutiSel?optCode='+vouType,
        width:900,
        height:540,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:cusTypeNo,cusTypeDes;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择业务模式 多选
function selectBusModelMutiDialog(callback,busModel){
    dialog({
        id:"busModelMutiDialog",
        title:'选择担保方式',
        url:webPath+'/riskPreventSceGen/getBusModelForMutiSel?optCode='+busModel,
        width:600,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:cusTypeNo,cusTypeDes;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择移交客户(多选)
function selectTransCusInfo(callback,cusMngNo){
    dialog({
        id:"transCusInfo",
        title:'选择移交客户',
        url:webPath+'/mfOaTrans/getCustomerList?cusMngNo='+cusMngNo,
        width:900,
        height:540,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择移交项目(多选)
function selectTransBusInfo(callback,cusMngNo){
    dialog({
        id:"transBusInfo",
        title:'选择移交项目',
        url:webPath+'/mfOaTrans/getBusList?cusMngNo='+cusMngNo,
        width:1100,
        height:500,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择担保方式，多选（押品动态表单配置使用，查询结果（抵押、质押、转让）） 20170622韩国兴
function selectVouTypeMutiForPledgeDialog(callback,vouType){
    dialog({
        id:"vouTypeMutiForPledgeDialog",
        title:'选择担保方式',
        url:webPath+'/mfCollateralClass/getVouTypeForMutiSel?optCode='+vouType,
        width:600,
        height:350,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:vouTypeNo,vouTypeDes;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};
//选择业务方式，多选（押品动态表单配置使用） 20170622韩国兴
function selectClassModelMutiForPledgeDialog(callback,classModel){
    dialog({
        id:"classModelMutiForPledgeDialog",
        title:'选择业务模式',
        url:webPath+'/mfCollateralClass/getClassModelForMutiSel?optCode='+classModel,
        width:600,
        height:350,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:classModelNo,classModelDes;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择业务中关联的未出库的押品
function selectCarModelDataDialog(callback){
    var  tmpheight=$(window.document).height();
    tmpheight=tmpheight*0.80;
    dialog({
        id:"carModelDataDialog",
        title:'选择车型',
        url:webPath+'/mfCarModel/getCarModelPageForSelect',
        width:800,
        height: tmpheight,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
            }
        }
    }).showModal();
};

//选择共同借款人,支持多选
function selectCocoborrMutiDialog(cusNo,appId,callback){
    dialog({
        id:"cocoborrMutiDialog",
        title:'选择共同借款人',
        url:webPath+'/mfUserPermission/selectCocoboList?cusNo='+cusNo+"&appId="+appId,
        width:900,
        height:540,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:opNo,opName;如果多个，使用@分隔
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                }
                $.ajax({
                    type: "post",
                    dataType: 'json',
                    url:webPath+ "/mfBusApply/getCocoborrNum",
                    data:{cusNos:$("input[name='coborrNo']").val(),cusMngName:$("input[name='cusMngName']").val(),cusMngNo:$("input[name='cusMngNo']").val()},
                    async: false,
                    success: function(data) {
                        if(data.flag=="success"){
                            $("#tableHtml").empty().html(data.tableData);
                            $("#coborrNumName").css("display","block");
                            $("input[name='coborrNum']").val(data.idNums);
                            $("input[name='coborrNo']").val(data.coborrNo);
                            $("input[name='certificateNum']").val(data.idNums);
                        }else{
                            window.top.alert(data.msg,0);
                            $("input[name='coborrName']").val(null);
                            $("input[name='coborrName']").next().html("");
                            $("#tableHtml").empty();
                        }
                    }
                });
            }
        }
    }).showModal();
};
//选择共同借款人,单选
function selectCocoborrListDialog(cusNo,callback){
    dialog({
        id:'coborrDialog',
        title:"选择共同借款人",
        url:webPath+"/mfBusApply/getListPageForSelect?cusNo="+cusNo+"&cusType=",
        width:900,
        height:400,
        backdropOpacity:0,
        onshow:function(){
            this.returnValue = null;
        },onclose:function(){
            if(this.returnValue){
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if(typeof(callback)== "function"){
                    callback(this.returnValue);
                }else{
                    getCusInfoArtDialog(this.returnValue);
                }
            }
        }
    }).showModal();
};

//选择所有权人,单选
function selectCertificateName() {
    dialog({
        id: 'coborrDialog',
        title: "选择所有权人",
        url: webPath +"/pledgeBaseInfo/getCertificateName",//请求数据URL;
        width: 900,
        height: 400,
        backdropOpacity: 0,
        onshow: function () {
            this.returnValue = null;
        }, onclose: function () {
            cusInfo= this.returnValue;
            $("input[name=certificateName]").val(cusInfo.cusName);
            $("input[name=certificateNo]").val(cusInfo.cusNo);
        }
    }).showModal();
};
