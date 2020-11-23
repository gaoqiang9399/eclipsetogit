top.brNoData;
var queryConditionTop = 0;
var queryConditionLeft = 0;
var underQueryConditionTop = 0;
var inputClickFlag = false;
var ReportEntrance = function (window, $) {
    var _init = function () {
        $("body").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            }
        });

        $.each(tatolList, function (i, cusTable) {
            var itemId = cusTable.itemId;
            var flag = cusTable.attentionFlag;
            if (flag == "1") {//为选中
                $("#" + itemId).css("display", "block");
            } else {//已选中
                $("#" + itemId).css("display", "none");
            }
        });

        $("#addQuery").click(function () {
            top.itemId = "";
            top.flag = false;
            top.window.openBigForm(webPath + "/mfReportFilter/getFilter?funcType=" + "1", "台账查询设置", function () {
                if (top.flag) {
                    _addItemCallBack("1");
                }
            });
        });
        $("#addTotalQuery").click(function () {
            top.itemId = "";
            top.flag = false;
            top.window.openBigForm(webPath + "/mfReportFilter/getFilter?funcType=" + "3", "统计查询设置", function () {
                if (top.flag) {
                    _addItemCallBack("3");
                }
            });
        });
    };
    var _openInit = function () {
        //控制部门和操作员的只读属性
        if (funRoleType == '1') {//本人
            $('#brNo').attr("readonly", "readonly");
            $('#brNoName').attr("readonly", "readonly");
            $('#opNo').attr("readonly", "readonly");
            $('#opNoName').attr("readonly", "readonly");
        } else if (funRoleType == '2') {//本部门
            $('#brNo').attr("readonly", "readonly");
            $('#brNoName').attr("readonly", "readonly");
        }


        //单击台账查询,统计报表区域,隐藏条件
        $('.row.info-title').click(function () {
            //$('.search_con').hide();
        });

//		$('.leftBorderDiv').click(function(){
        _clearAllInputValue();//清空数据
        _hideQueryTr();
        _dealSaveFlag();
        _showOpenQueryMessage(reportId);
        _showOpenFormConditionVal(reportId);
        _popQueryDiv($('.ui-btn-menu .menu-btn'));
        $('.search_con').show();
        iObj = $(this);
//			queryConditionTop = $(this).offset().top;
//			underQueryConditionTop = $(this).offset().top+65;
//			queryConditionLeft = Y;
        var date=new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        if(month <10) {
            month = "0"+month;
        }
        var curday = date.getDate();
        if(curday <10) {
            curday = "0"+curday;
        }
        var yesday = date.getDate()-1;
        if(yesday <10) {
            yesday = "0"+yesday;
        }
        var curdate=""+year+"-"+month+"-"+curday;
        var yesdate=""+year+"-"+month+"-"+yesday;
        if (reportId == 'report-loan-loanStatus' && $('#month').val() == '') {//贷款情况汇总表中,月份默认上个月
            $('#month').val(lastMonth);
        }
        if (reportId == 'report-loan-loanCnt' && $('#month').val() == '') {//贷款累放累月月报表,月份默认当前月
            $('#month').val(currMonth);
        }
        if(reportId=="report-base-overdueDetail"){
            var queryDate = currDate.substring(0, 4)+"-"+currDate.substring(4,6)+"-"+currDate.substring(6,8);
            $('#dateSingle').val(queryDate);
        }
        if (reportId == 'report-ygyh-loanFinancialDaily' && $('#dateSingle').val() == '') {
            $('#dateSingle').val(yesdate);
        }
//		});
        $("input").click(function () {
            inputClickFlag = true;
        });
        cintyInit();
        $("input[name=cityName]").click(function () {
            $(".pops-value").click();
            inputClickFlag = true;
        });
    };
    //弹出列表,回显值
    var openDialogShowValue = function (itemsno, noObj, nameObj) {
        openConditionListDialog(itemsno, function (data) {

            if (data) {
                if (data.no != '') {
                    if (noObj == 'brNo') {//选择部门时,清空操作员信息
                        if (data.no != $('#' + noObj).val()) {
                            $('#opNo').val('');
                            $('#opNoName').val('');
                        }
                    }
                    $('#' + noObj).val(data.no);
                    $('#' + nameObj).val(data.name);
                    top.brNoData = '';
                } else {
                    $('#' + noObj).val('');
                    $('#' + nameObj).val('');
                }
            }
        });
    };
    //弹出列表,回显值
    //弹出列表,回显值
    var openDialogShowValueSearch = function (itemsno, noObj, nameObj,tableId,keyName) {


        openConditionListDialog(itemsno,noObj,tableId,keyName, function (data) {


            if (data) {
                var html;
                if (data.no != '') {
                    if (noObj == 'brNo') {//选择部门时,清空操作员信息
                        if (data.no != $('#' + noObj).val() ) {
                            $('#opNo').val('');
                            $('#opNoName').val('');
                            _resetOpNo();
                        }
                    }

                    $('#' + noObj).val(data.no);
                    $('#' + nameObj).val(data.name);
                    var name = data.name.split(",");
                    html = "";
                    for (var int = 0; int < name.length; int++) {
                        html = html + "<span>" + name[int] + "</span>";
                    }
                    $('#' + nameObj).next().html(html);
                    top.brNoData = '';

                    $("li").each(function (index, element) {
                        //设置是否显示更多
                        if (!$(this).is(":hidden")) {

                            var tmpWidth = $(this).width();
                            tmpWidth = tmpWidth * 0.75;
                            var tmpLen = 0;
                            $(this).find("span").each(function () {
                                tmpLen += ($(this).width()+35);
                            });

                            if (tmpWidth >= tmpLen) {
                                $(this).find("a").css("visibility", "hidden");
                            } else {
                                $(this).find("a").css("visibility", "visible");
                            }
                        }

                    });
                } else {
                    $('#' + noObj).val('');
                    $('#' + nameObj).val('');

                    if (noObj == 'brNo') {//选择部门时,清空操作员信息
                        if (data.no == '' || data.no != $('#' + noObj).val() ) {
                            $('#opNo').val('');
                            $('#opNoName').val('');
                            _resetOpNo();
                        }
                    }

                    html = "&nbsp;</br>&nbsp;";

                    $('#' + nameObj).next().html(html);
                    top.brNoData = '';
                }
            }
        });
    };
    //保存查询条件
    var _saveSqlCondition = function (reportId) {
        var url = webPath + "/mfReportQueryConditionUser/saveReoprtSqlCondition";
        var subArr = [];
        $("input").each(function (index, element) {

            if ($(this).attr('saveFlag') == '1' && $(this).parent().css("display") != 'none') {
                var subObj = {};
                var date,year,month;
                if ($(this).attr('noFlag') == '1') {
                    subObj.value = $(this).val();
                    subObj.nameValue = $(this).next().val();
                } else {
                    subObj.value = $(this).val();
                    subObj.nameValue = $(this).val();
                }
                var inputId = $(this).attr('id');
				var inputValue = $(this).val();
				if(inputId == "busDate" && (inputValue == null || inputValue == "" || typeof(inputValue) == "undefined")) {
					date = new Date();
					year = date.getFullYear();
				    month = date.getMonth() + 1;
				    if(month <10) {
				        month = "0"+month;
				    }
				    var strDate = date.getDate();
				    if(strDate <10) {
				        strDate = "0"+strDate;
				    }
				    subObj.value=""+year+month+strDate;
				}else if(inputId == "month" && (inputValue == null || inputValue == "" || typeof(inputValue) == "undefined")) {
						date = new Date();
						year = date.getFullYear();
				        month = date.getMonth() + 1;
				        if(month <10) {
				        	month = "0"+month;
				        }
				        subObj.value=""+year+month;
                }else if(inputId == 'date' ||inputId.substring(inputId.length-9)=='BeginDate'||inputId.substring(inputId.length-7)=='EndDate'){
                    subObj.value= $(this).val().replace(new RegExp("-","gm"),"");//替换日期之间的横杠
                }
                subArr.push(subObj);
            }
        });
        var condition = JSON.stringify(subArr);
        //LoadingAnimate.start();
        jQuery.ajax({
            url: url,
            data: {reportId: reportId, sqlCondition: condition},
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                //LoadingAnimate.stop();
                if (data.flag == "success") {
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
                //LoadingAnimate.stop();
            }
        });
    };

    //查询追加条件,同时弹出报表
    var _reportQuery = function (type, obj, id, title) {
        if (type == '1') {
            _openBase(obj, id, title);
        } else {
            _openReport(obj, id, title);
        }
    };

    //展示查询条件值
    _showFormConditionVal = function (id) {
        jQuery.ajax({
            url: webPath + "/mfReportQueryConditionUser/showFormConditionVal",
            data: {reportId: id},
            type: "POST",
            dataType: "json",
            async: false,
            beforeSend: function () {
            }, success: function (data) {
                if (data.flag == "success") {
                    var result = data.result;
                    if (result != '' && result != null) {
                        var jsonObj = JSON.parse(result);//转换为json对象
                        var num = 0;
                        var inputId;
                        $("input").each(function (index, element) {
                            if ($(this).attr('noFlag') == '0' && $(this).parent().css("display") != 'none') {
                                if ($(this).attr('saveFlag') == '0') {
                                    $(this).prev().val(jsonObj[num].value);
                                    $(this).val(jsonObj[num].nameValue);
                                    inputId = $(this).attr('id');
                                } else {
                                    inputId = $(this).attr('id');
                                    if (inputId == 'date' || inputId == 'beginDate' || inputId == 'endDate' || inputId == 'beginDate2' || inputId == 'endDate2') {
                                        $(this).val(_formatDate(jsonObj[num].value));
                                    } else {
                                        $(this).val(jsonObj[num].value);
                                    }

                                }
                                num++;
                            }
                        });
                    }
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
            }
        });
    };
    //展示查询条件值
    _showOpenFormConditionVal = function (id) {
        jQuery.ajax({
            url: webPath + "/mfReportQueryConditionUser/showFormConditionVal",
            data: {reportId: id},
            type: "POST",
            dataType: "json",
            async: false,
            beforeSend: function () {
            }, success: function (data) {
                if (data.flag == "success") {
                    var result = data.result;
                    if (result != '' && result != null) {
                        var jsonObj = JSON.parse(result);//转换为json对象
                        var num = 0;
                        $("input").each(function (index, element) {
                            if ($(this).attr('noFlag') == '0' && $(this).parent().css("display") != 'none') {
                                if ($(this).attr('saveFlag') == '0') {
                                    $(this).prev().val(jsonObj[num].value);
                                    $(this).val(jsonObj[num].nameValue);
                                    var name = jsonObj[num].nameValue.split(",");
                                    var html = "";
                                    for (var int = 0; int < name.length; int++) {
                                        html = html + "<span>" + name[int] + "</span>";
                                    }
                                    $(this).next().html(html);
                                } else {
                                    var inputId = $(this).attr('id');
                                    if (inputId == 'date' || inputId == 'beginDate' || inputId == 'endDate' || inputId == 'beginDate2' || inputId == 'endDate2') {
                                        $(this).val(_formatDate(jsonObj[num].value));
                                    } else {
                                        $(this).val(jsonObj[num].value);
                                    }

                                }

                                num++;
                            }
                        });
                    }

                } else if (data.flag == "error") {
                }
            }, error: function (data) {
            }
        });
    };
    var _formatDate = function (date) {
        if (date.length == 8) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        } else {
            return date;
        }
    }

    var _openBase = function (obj, id, title) {
        var jspUrl = webPath + "/component/report/MfReportEntranceOpen.jsp?&reportId=" + id + "&reporttype=D" + "&funRoleType=" + funRoleType + "&reportUrl=" + reportUrl+ "&reportProjectFlag=" + reportProjectFlag;
        switch (id) {
            // 中关村台账开始
            case "report_zgc_18":
                top.openBigForm(jspUrl + "&uid=9a423ab2a79ac28ae5547a827ede9511", "小微事业部代偿台账", function () {
                }, "90", "90");
                break;
            case "report_zgc_20":
                top.openBigForm(jspUrl + "&uid=61441adb17d688423c5d02554a744450", "财务开票台账", function () {
                }, "90", "90");
                break;
            case "report_zgc_22":
                top.openBigForm(jspUrl + "&uid=cdaf39a69f90479c5c9caf1477697c63", "工程担保项目总台帐", function () {
                }, "90", "90");
                break;

            //中关村台账结束
            case "report-cus-basedata":
                top.openBigForm(jspUrl + "&uid=0d8412dde180cd67217e2d6f4c1ff0a4", "客户基本信息查询", function () {
                }, "90", "90");
                break;
            case "report-pledge-basedata":
                top.openBigForm(jspUrl + "&uid=6166aad9086ed0a9928b2fea15467e10", "押品基本信息查询", function () {
                }, "90", "90");
                break;
            case "pactSts":
                top.openBigForm(jspUrl + "&uid=0ec0169a0a3304f8344b787beab14a01", "合同状态查询", function () {
                }, "90", "90");
                break;
            case "report-bo-handle-stage":
                top.openBigForm(jspUrl + "&uid=535620a2b1cfbe650fd875f11adb6ed7", "贷款办理阶段查询", function () {
                }, "90", "90");
                break;
            case "report-cus-class":
                top.openBigForm(jspUrl + "&uid=c40a78d685e2979113f1afa5f6cba65a", "客户分类查询", function () {
                }, "90", "90");
                break;
            case "report-get-detail":
                top.openBigForm(jspUrl + "&uid=abe36298b963240eb3693fd7a686af29", "业务回收明细查询", function () {
                }, "90", "90");
                break;
            case "report-enquiry-repayment-due":
                top.openBigForm(jspUrl + "&uid=40d665640405f8e68c779afb3a8eea68", "还款到期查询", function () {
                }, "90", "90");
                break;
            case "report-loan-process-stage":
                top.openBigForm(jspUrl + "&uid=252c497b0e31492d8e6adc68bdbc83e5", "贷款办理阶段查询", function () {
                }, "90", "90");
                break;
            case "report-consolidated-loan-enquiryy":
                top.openBigForm(jspUrl + "&uid=8f1a668b113aea84eda75c63318bc0d4", "放款台账", function () {
                }, "90", "90");
                break;
            case "report-repayment-details-ledger":
                top.openBigForm(jspUrl + "&uid=8cf35fb1cdc5dc02dec20e8f0fc0b988", "还款明细台账", function () {
                }, "90", "90");
                break;
            case "report-overdue-business-details":
                top.openBigForm(jspUrl + "&uid=4fddc2d2f079694d961af3e28d4ae5b7", "逾期业务明细查询", function () {
                }, "90", "90");
                break;
            case "bussFee":
                top.openBigForm(jspUrl + "&uid=6ae8625d982dec652cdf96d6a3836c2e", "业务费用情况查询", function () {
                }, "90", "90");
                break;
            case "report-bo-enddate":
                top.openBigForm(jspUrl + "&uid=e0c7e339365500361ef98ddacd03aaba", "业务到期查询", function () {
                }, "90", "90");
                break;
            case "bussBal":
                top.openBigForm(jspUrl + "&uid=0cb800b35cd34676477419a4fe85a233", "业务余额情况查询", function () {
                }, "90", "90");
                break;
            case "report-overbo-detail":
                top.openBigForm(jspUrl + "&uid=194391b549870fae91035736e1e2aa4c", "逾期业务明细统计表", function () {
                }, "90", "90");
                break;
            case "report-assue-data-account":
                top.openBigForm(jspUrl + "&uid=d78b57d87e179c1100cc41ca09d2a4ba", "业务反担保信息统计表", function () {
                }, "90", "90");
                break;
            case "report-expect-return-money":
                top.openBigForm(jspUrl + "&uid=e3a900d46e83201fc8f091929b315d1a&currDate=" + currDate, "预计还款查询", function () {
                }, "90", "90");
                break;
            case "report-repayhis-detail":
                top.openBigForm(jspUrl + "&uid=e8e47af45641967b6daf9f27a707ad60", "还款历史回收明细查询", function () {
                }, "90", "90");
                break;
            case "lendingStatics":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", "放款统计汇总表", function () {
                }, "90", "90");
//					top.openBigForm(webPath+"/mfReportLoan/getPutListByCus?reportId="+id,"放款统计汇总表",function(){} ,"85","85");
                break;
            case "report-app-detail":
                top.openBigForm(jspUrl + "&uid=5efadb06a6b9be8d05425f9183c58555&flagA=0&flagB=0", "项目审批时效明细表", function () {
                }, "90", "90");
                break;
            case "report-prtSaleYear":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");
                //top.openBigForm(webPath+"/mfProSaleYearly/proSaleStatYealy?reportId="+id,title,function(){},"90","90");
                break;
            case "report-empOpStat":
                //这样写能加载报表格式top.openBigForm(jspUrl+"&uid=6fe45fc6ef732fc32da52b4096e1982f","员工业务受理质量表",function(){},"90","90");
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", "员工业务受理质量表", function () {
                }, "90", "90");
//					top.openBigForm(webPath+"/mfEmpOpStat/empOpStat?reportId="+id,'员工业务受理质量表',function(){},"90","90");
                break;
            case "report-basic-cost-feerep":
                top.openBigForm(jspUrl + "&reportId=" + "report-basic-cost-feerep" + "&type=java", "基础表费用子表", function () {
                }, "90", "90");
                break;
            case "report-basic-assure-feerep":
                top.openBigForm(jspUrl + "&reportId=" + "report-basic-assure-feerep" + "&type=java", "保证金", function () {
                }, "90", "90");
                break;
            case "report-placement-rep":
                top.openBigForm(jspUrl + "&reportId=" + "report-placement-rep" + "&type=java", "资金安排表", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep" + "&type=java", "业务进度表(日报)", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep-mon":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep-mon" + "&type=java", "业务进度表(月报)", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep-year":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep-year" + "&type=java", "业务进度表(年报)", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep-other":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep-other" + "&type=java", "业务进度表(年报)", function () {
                }, "90", "90");
                break;
            case "report-appEfficiency":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", "审批人员时效分析表", function () {
                }, "90", "90");
                //top.openBigForm(webPath+"/mfAppEfficiency/appEfficiency?reportId="+id,'审批人员时效分析表',function(){},"90","90");
                break;
            case "report-asset-protect":
                top.openBigForm(jspUrl + "&uid=a1bfbab9fdfd84147e784be6a39fdd33", "资产保全查询", function () {
                }, "90", "90");
                break;
            case "report-project-collection":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", "项目催收情况", function () {
                }, "90", "90");
                //top.openBigForm(webPath+"/mfReportRecall/getRecallList?reportId="+id,"项目催收情况",function(){} ,"90","90");
                break;
            case "report-cus-black-list":
                top.openBigForm(webPath + "/wangxinRep/getBlackList?reportId=" + id, title, function () {
                }, "90", "90");
                break;
            case "report-loan-yuqi":
               // top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {}, "90", "90");
                top.openBigForm(jspUrl+"&uid=1417e9dc5bd0dcf9c94370c5bb804a82","逾期业务明细查询",function(){},"90","90");
                break;
            case "report-risk-daily-report":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-credit-query":
                top.openBigForm(jspUrl + "&uid=c01c00a0fd1ec74f2c402fb782e840a3", "征信查询台帐", function () {
                }, "90", "90"); //阳光银行征信查询
                break;
            case "report-loan-overall-find":
                top.openBigForm(jspUrl + "&uid=6bac390c372b1afe451fcf288f020307", "贷款综合查询", function () {
                }, "90", "90");
                break;
            case "report-makeplan-payment-rep":
                top.openBigForm(jspUrl + "&uid=08eb22666fd55878342233a7428cdc71", "排标/出款计划表", function () {
                }, "90", "90");//阳光银行出账台账查询
                break;
            case "report-jinJian-count":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-loan-JSfkmx":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");//金山放款明细表
                break;
            case "report-loan-JSskmx":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");//金山收款明细表
                break;
            case "report-loan-JSsryc":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");//金山收入预测表
                break;
            case "report_stock_statistics":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-loan-rate-float-range":
                top.openBigForm(jspUrl + "&uid=6a357a30588c3b40cc762c353d860438&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-loan-rate-fix-timelimit":
                top.openBigForm(jspUrl + "&uid=a99068ea10cd34b92db3e81c3b843f58&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-fix-loan-rate-term":
                top.openBigForm(jspUrl + "&uid=b4e14c4fa072cc0a624cb3629b8ef375&type=java", title, function () {
                }, "90", "90");
                break;
            default:
                _showTips(obj);
                break;
        }
    };

    var _openThis = function (id) {
        $.ajax({
            type: "get",
            url: webPath + "/mfReportFilter/getById?id=" + id,
            success: function (data) {
                var query_content = encodeURIComponent(data.query_content);
                var query_name = data.query_name;
                var query_class = data.query_class;
                if (query_class == "cus") {
                    top.openBigForm(webPath + "/mfReportCustomer/getCusList?ajaxData=" + query_content, query_name, function () {
                    }, '90', '90');
                } else if (query_class == "bus") {
                    top.openBigForm(webPath + "/mfReportCustomer/getBusList?ajaxData=" + query_content, query_name, function () {
                    }, "90", "90");
                } else if (query_class == "finc") {
                    top.openBigForm(webPath + "/mfReportCustomer/getFincList?ajaxData=" + query_content, query_name, function () {
                    }, "90", "90");
                } else if (query_class == "pledge") {
                    top.openBigForm(webPath + "/mfReportCustomer/getPledgeList?ajaxData=" + query_content, query_name, function () {
                    }, "90", "90");
                }
            }
        });
    };

    var tipsTimeoutId;
    var _showTips = function (obj) {
        top.LoadingAnimate.stop();
        var d = dialog({
            id: "oaInBuilding",
            content: "正在建设中，敬请期待。",
            padding: "3px"
        }).show(obj);
        if (tipsTimeoutId) {
            clearTimeout(tipsTimeoutId);
        }
        tipsTimeoutId = setTimeout(function () {
            d.close().remove();
        }, 1000);
    };

    var _openTuDemo = function (parm, title) {
        if (parm != "") {
            var url = webPath + '/component/report/MfReportDemo.jsp?parm=' + parm;
            top.openBigForm(url, title, function () {
            }, '80', '85');
            return;
        }
        _showTips(this);
        return;
    };
    var _openReport = function (obj, parm, title) {
        var jspUrl = webPath + "/component/report/MfReportEntranceOpen.jsp?&reportId=" + parm + "&reporttype=D" + "&reportUrl=" + reportUrl+ "&reportProjectFlag=" + reportProjectFlag;
        var url = '';
        switch (parm) {
            //中关村报表开始
            case "report_zgc_01":
                top.openBigForm(jspUrl+"&uid=ae9d4a10883f15bbdbd56c597e941ff9","每日发领导信息",function(){},"90","90");
                break;
            case "report_zgc_02":
                top.openBigForm(jspUrl+"&uid=4499c757705423b7ead21c5fb418fa45","工程保证担保事业部机构人员数量明细表",function(){},"90","90");
                break;
            case "report_zgc_03":
                top.openBigForm(jspUrl+"&uid=ce0dd4ae839a980d21985d274b36f6b2","工程保证担保事业部机构与人员情况统计表",function(){},"90","90");
                break;
            case "report_zgc_04":
                top.openBigForm(jspUrl+"&uid=5eeaf01353cdefd94bed5506c38e7cbb","工程担保年度累计办事处同期比较统计表",function(){},"90","90");
                break;
            case "report_zgc_05":
                top.openBigForm(jspUrl+"&uid=b21c588462f671e80842571639b09e02","工程担保月度事业部经营业绩统计表",function(){},"90","90");
                break;
            case "report_zgc_06":
                top.openBigForm(jspUrl+"&uid=68d6597bbcc635409801037ce7ecde9e","工程担保年度累计业务银行保函及公司自营保函统计表",function(){},"90","90");
                break;
            case "report_zgc_07":
                top.openBigForm(jspUrl+"&uid=8cd4cbe486d913f6a771ddc936ca5ae0","工程担保年度累计办事处业务开拓情况统计表",function(){},"90","90");
                break;
            case "report_zgc_08":
                top.openBigForm(jspUrl+"&uid=3539ece8e04efd36edddad3769dbcee9","工程担保年度累计办事处业务人员业绩完成情况统计表",function(){},"90","90");
                break;
            case "report_zgc_09":
                top.openBigForm(jspUrl+"&uid=a3ed28a4fa0cff23363e2902639d7b0b","工程担保月度办事处同期比较统计表",function(){},"90","90");
                break;
            case "report_zgc_10":
                top.openBigForm(jspUrl+"&uid=553dec5078c0756fe7faccea792cf7cf","工程担保年度累计办事处完成年度预算情况统计表",function(){},"90","90");
                break;
            case "report_zgc_11":
                top.openBigForm(jspUrl+"&uid=6fb0c53f76873ebe8a4b073b6835d685","工程担保年度累计事业部经营业绩统计表",function(){},"90","90");
                break;
            case "report_zgc_12":
                top.openBigForm(jspUrl+"&uid=bc64ff75f51317d3d3025de1deb6572a","档案统计表",function(){},"90","90");
                break;
            case "report_zgc_13":
                top.openBigForm(jspUrl+"&uid=29a9483d21929db8a6aee1278d38c53a","个人业务统计",function(){},"90","90");
                break;
            case "report_zgc_14":
                top.openBigForm(jspUrl+"&uid=5cbc96f2533dcf807d36716f45c34a26","风险项目情况",function(){},"90","90");
                break;
            case "report_zgc_15":
                top.openBigForm(jspUrl+"&uid=e77a2d64288586ec98869cb29c1ec07a","签约情况",function(){},"90","90");
                break;
            case "report_zgc_16":
                top.openBigForm(jspUrl+"&uid=d574416d33dc631c766b9cdeb56beb53","每月新增明细表",function(){},"90","90");
                break;
            case "report_zgc_17":
                top.openBigForm(jspUrl+"&uid=045874f1295cc7ebbc8dd9f3fdc799bf","权限内合同清单",function(){},"90","90");
                break;
            case "report_zgc_19":
                top.openBigForm(jspUrl+"&uid=a7f0a142feb4f61d2723330b59007214","填报清单",function(){},"90","90");
                break;
            case "report_zgc_21":
                top.openBigForm(jspUrl+"&uid=9eb9870c0b687bb8a32fcb799e28b5ca","权限内风控审核汇总表（发公司风控部） ",function(){},"90","90");
                break;
            case "report_zgc_23":
                top.openBigForm(jspUrl+"&uid=51f61d17bb1e14a873aaf5bc7f418553","或有表",function(){},"90","90");
                break;
            //中关村报表结束
            case "report-ygyh-loanSummaryDaily":
                top.openBigForm(jspUrl+"&uid=098005d70e0e36d6d8d6107aa7342461","贷款日报表",function(){},"90","90");
                break;
            case "report-ygyh-loanSimple":
                top.openBigForm(jspUrl+"&uid=22ed35b1febefa311c9a90bd62856406","贷款台账简",function(){},"90","90");
                break;
            case "report-performance-statis-daily":
                top.openBigForm(jspUrl+"&reportId="+parm+"&type=java",title,function(){},"90","90");
                break;//阳光银行  业绩统计表
            case "report-loan-overall-time":
                top.openBigForm(jspUrl + "&uid=6bac390c372b1afe451fcf288f020309", "贷款台账", function () {
                }, "90", "90");
                break;
            case "report-loan-taizhang":
                top.openBigForm(jspUrl + "&uid=6bac390c372b1afe451fcf288f020308", "授信台账", function () {
                }, "90", "90");//阳光银行出账台账查询
                break;
            case "report-pact-enddate":
                url = jspUrl + '&uid=3e008f6ddf0419ba884d877ee6bf3e92&flagA=0&flagB=0&flagC=0';
                break;
            case "12":
                url = jspUrl + '&uid=042089842fda9181f89857bcd327374e&flagA=0&flagB=0';
                break;
            case "18":
                url = jspUrl + '&uid=b9c67ef6dc02cefc68ca2154b887d1ff&flagA=0&flagB=0';
                break;
            case "15":
                url = jspUrl + '&uid=490d89e4c67a38c055e3cc5d019b53a1&flagA=0&flagB=0';
                break;
            case "report-loan-repayCount":
                url = jspUrl + '&uid=a3cae1b8e70bb14ad39fbfea0ce87c1f';
                break;
            case "report-five-class-account":
                url = jspUrl + '&uid=6c55a87342d3056659d2a5b8ddc610a6';
                break;
            case "report-service-account-month":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfReportRepayCount/getBussinessMonth?reportId="+parm,title,function(){} ,"90","90");
                break;
            case "report-data-source":
                top.openBigForm(jspUrl + "&uid=72e36a069f963ad70bb565e8367b4696", "项目来源统计表", function () {
                }, "90", "90");
                break;
            case "report-loan-loanCnt":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java&currMonth=" + currMonth, title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfReportLoanAccu/getLoanAccuReportList?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-busStatistics":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfReportLoanStatus/getMfReportBusStatistics?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-loanStatus":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java&lastMonth=" + lastMonth, title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfReportLoanStatus/getMfReportStatusList?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-balAccount":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfBalAnalysis/getBalAnalysisList?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-loanCountVoutype":
                top.openBigForm(jspUrl + "&uid=93c8a3fe267d44a109f7ed726734ba63", "放款统计汇总表（按担保类型）", function () {
                }, "90", "90");
                /*top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                }, "90", "90");*/
                break;
            case "report-loan-loanCountCustype":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfReportLoan/getPutListByCus?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-credit-count-base":
                top.openBigForm(jspUrl + "&uid=2d255b2ad66f1266deef36db3ff8a98e", "放款统计明细表", function () {
                }, "90", "90");
                break;
            case "report-credit-count":
                /*  top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                  }, "90", "90");*/
                top.openBigForm(jspUrl + "&uid=e0ec3e18b96c342a76b3dde042fb3085", "放款统计明细表", function () {
                }, "90", "90");
                break;
            case "report-expect-repay":
                top.openBigForm(jspUrl + "&uid=264b2b7c85c5f35e4630b81b67d801c2", "预期还款查询", function () {
                }, "90", "90");
                break;
            case "report-loan-max-ten":
                top.openBigForm(jspUrl + "&uid=7832fd9060b2bcbf3a71235413325873", "贷款公司最大十户贷款情况表", function () {
                }, "90", "90");
                break;
            case "report-extension-form":
                top.openBigForm(jspUrl + "&uid=0c7f74cf9565d764ab0460ae435a2f63", "贷款展期情况表", function () {
                }, "90", "90");
                break;
            case "report-loan-bal-state":
                top.openBigForm(jspUrl + "&uid=e8539c0f864ca11412ccec554cd102b3", "贷款余额明细表", function () {
                }, "90", "90");
                break;
            case "report-loan-five-level":
                top.openBigForm(jspUrl + "&uid=d6d9a69ccce00a3f94a2358ea5793850", "贷款五级分类表", function () {
                }, "90", "90");
                break;
            case "report-loan-operations-state":
                top.openBigForm(jspUrl + "&uid=dadab32c88510afc2759f298fec13663", "贷款业务情况表", function () {
                }, "90", "90");
                break;
            case "report-loan-special-sta":
                top.openBigForm(jspUrl + "&uid=278656261c7f34b28aa1b0e07209eec7", "专项统计报表", function () {
                }, "90", "90");
                break;
            case "report-loan-bus-state-month":
                top.openBigForm(jspUrl + "&uid=2e51dfe564b16e5c18146a18332c7b64", "贷款业务状况月报表", function () {
                }, "90", "90");
                break;
            case "report-credit-his-sta":
                top.openBigForm(jspUrl + "&uid=8a9740ef63055bada2ce7ac75d74b7ce", "授信额度占用历史统计表", function () {
                }, "90", "90");
                break;
            case "report-base-overdueDetail":
                top.openBigForm(jspUrl + "&uid=1b8bbc221e6ac555899d11e9dd8808ef&currDate=" + currDate, "逾期明细表", function () {
                }, "90", "90");
                break;
            case "report-prtSaleYear":
                top.openBigForm(jspUrl + "&reportId=" + parm + "&type=java", title, function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfProSaleYearly/proSaleStatYealy?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-empOpStat":
                top.openBigForm(jspUrl + "&reportId=" + id + "&type=java", "员工业务受理质量表", function () {
                }, "90", "90");
//				top.openBigForm(webPath+"/mfEmpOpStat/empOpStat?reportId="+parm,title,function(){},"85","85");
                break;
            case "report-basic-cost-feerep":
                top.openBigForm(jspUrl + "&reportId=" + "report-basic-cost-feerep" + "&type=java", "基础表费用子表", function () {
                }, "90", "90");
                break;

            case "report-basic-assure-feerep":
                top.openBigForm(jspUrl + "&reportId=" + "report-basic-assure-feerep" + "&type=java", "保证金", function () {
                }, "90", "90");
                break;

            case "report-placement-rep":
                top.openBigForm(jspUrl + "&reportId=" + "report-placement-rep" + "&type=java", "资金安排表", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep" + "&type=java", "业务进度表（日报）", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep-mon":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep-mon" + "&type=java", "业务进度表（月报）", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep-year":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep-year" + "&type=java", "业务进度表（年报）", function () {
                }, "90", "90");
                break;
            case "report-bussprogr-detail-rep-other":
                top.openBigForm(jspUrl + "&reportId=" + "report-bussprogr-detail-rep-other" + "&type=java", "业务进度表（其他）", function () {
                }, "90", "90");
                break;
            case "report-cus-credit-summary":
                top.openBigForm(jspUrl + "&uid=26c29ff3a8780c4ec1672a8c3c4991c6", "客户授信情况汇总表", function () {
                }, "90", "90");
                break;
            case "report-appEfficiency":
                top.openBigForm(webPath + "/mfAppEfficiency/appEfficiency?reportId=" + parm, title, function () {
                }, "85", "85");
                break;
            case "report-basic-rep":
                top.openBigForm(jspUrl + "&uid=4b6ab81c4f880067a57f8d8602289e6b", "基础表", function () {
                }, "90", "90");//阳光银行出账台账查询
                break;
            case "report-basicrepayment-rep":
                top.openBigForm(jspUrl + "&uid=102b553c9de5b215051ca92e2e3c9638", "还款计划表", function () {
                }, "90", "90");//汇款计划表
                break;
            case "report-repay-detail-base":
                top.openBigForm(jspUrl + "&uid=5efadb06a6b9be8d05425f9183c58904a", "还款明细表", function () {
                }, "90", "90");
                break;
            case "report-yangguangbank-hkmxb":
                top.openBigForm(jspUrl + "&uid=194d120a3137d2d07656d3683cf8e50c", "还款明细表", function () {
                }, "90", "90");
                break;
            case "report-loan-overall-month":
                top.openBigForm(jspUrl+"&uid=620613d016178b6d6a2d0c725c23ce68","贷款台账(月报)",function(){},"90","90");//阳光银行贷款台账历史查询
                break;
            case "report-loan-rate-float-range":
                top.openBigForm(jspUrl + "&uid=6a357a30588c3b40cc762c353d860438&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-loan-rate-fix-timelimit":
                top.openBigForm(jspUrl + "&uid=a99068ea10cd34b92db3e81c3b843f58&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-fix-loan-rate-term":
                top.openBigForm(jspUrl + "&uid=b4e14c4fa072cc0a624cb3629b8ef375&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-corp-loan-special-statistics":
                top.openBigForm(jspUrl + "&uid=b780ceb9f1472b98d8bbc8ff93ff1070&type=java", title, function () {
                }, "90", "90");
                break;
            case "report-corp-loan-case-condition":
                top.openBigForm(jspUrl + "&uid=09293927d5647f77741bf074ec7b31c3&type=java", title, function () {
                }, "90", "90");
                break;
            default :
                _showTips(obj);
                return false;
        }
        if (url != '') {
            top.openBigForm(url, title, function () {
            });
        }
    };
    var _openJIanGuan = function (parm, title) {
        var url = webPath + '/component/report/MfReportDemo2.jsp?parm=' + parm;
        top.openBigForm(url, title, function () {
        }, '90', '90');
    };
    var _addItemCallBack = function (funcType) {
        $.ajax({
            url: webPath + "/mfReportFilter/getListPageAjax",
            data: {
                funcType: funcType,
            },
            type: 'post',
            dataType: 'json',
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    var tatolList = data.unattention;
                    $.each(tatolList, function (i, cusTable) {
                        var itemId = cusTable.itemId;
                        var flag = cusTable.attentionFlag;
                        if (flag == "1") {//为选中
                            $("#" + itemId).css("display", "block");
                        } else {//已选中
                            $("#" + itemId).css("display", "none");
                        }
                    });
                } else if (data.flag == "error") {
                    alert(data.msg, 0);
                }
            }, error: function (data) {
                LoadingAnimate.stop();
                alert("操作失败！", 0);
            }
        });

    };

    var _popQueryDiv = function (obj) {
        if (obj.hasClass("ui-btn-dis")) {
            return false;
        }
        $(obj).parent().toggleClass('ui-btn-menu-cur');
    };


    //查询条件弹窗选择
    var openConditionListDialog = function (txType,noObj,tableId,keyName, callback) {
        var conditionVal = $('#'+noObj).val();
        var brNoDef = $('#brNo').val();

        if (txType == '24') {
            top.createShowDialog(encodeURI(webPath + '/component/report/sqlConditionDialog.jsp?txType=' + txType + "&conditionVal=" + conditionVal+ "&tableId=" + tableId+ "&keyName=" + keyName+ "&brNoDef=" + brNoDef), "选择", '750px', '600px', function () {
                if (callback && typeof(callback) == "function") {
                    callback.call(this, top.brNoData);
                }
            });
        } else {
            top.createShowDialog(encodeURI(webPath + '/component/report/sqlConditionDialog.jsp?txType=' + txType + "&conditionVal=" + conditionVal+ "&tableId=" + tableId+ "&keyName=" + keyName+ "&brNoDef=" + brNoDef), "选择", '600px', '600px', function () {
                if (callback && typeof(callback) == "function") {
                    callback.call(this, top.brNoData);
                }
            });
        }
    };

    //清空查询条件文本框内容
    var _clearAllInputValue = function () {
        $("input").each(function (index, element) {
            $(this).val('');
        });
    };

    //隐藏查询条件
    var _hideQueryTr = function (obj) {
        $("ul[id=more-conditions]").children().hide();
        $('#cusNoLi').hide();
        $('#numTypeLi').hide();
    };

    //保存标志
    var _dealSaveFlag = function (obj) {
        $('#brNo').attr('saveFlag', '0');
        $('#opNo').attr('saveFlag', '0');
        $('#pledgeClass').attr('saveFlag', '0');
        $('#cusType').attr('saveFlag', '0');
        $('#kindType').attr('saveFlag', '0');
        $('#dataSource').attr('saveFlag', '0');
        $('#beginDate').attr('saveFlag', '0');
        $('#endDate').attr('saveFlag', '0');
        $('#beginDate2').attr('saveFlag', '0');
        $('#endDate2').attr('saveFlag', '0');
        $('#repayDate').attr('saveFlag', '0');
        $('#repayDate2').attr('saveFlag', '0');
        $('#date').attr('saveFlag', '0');
        $('#month').attr('saveFlag', '0');
        $('#year').attr('saveFlag', '0');
        $('#week').attr('saveFlag', '0');
        $('#fincId').attr('saveFlag', '0');
        $('#cusNo').attr('saveFlag', '0');
        $('#stsNo').attr('saveFlag', '0');
    };

    //显示查询条件
    var _showQueryMessage = function (reportId) {
        wholeReportId = reportId;
        jQuery.ajax({
            url: webPath + "/mfReportQueryConditionConfig/getConditonShowByIdAjax",
            data: {reportId: reportId},
            type: "POST",
            dataType: "json",
            async: false,
            beforeSend: function () {
            }, success: function (data) {
                if (data.flag == "success") {
                    var result = data.result;
                    if (result != '') {
                        var jsonObj = JSON.parse(result);//转换为json对象
                        for (var i in jsonObj) {
                            var name = jsonObj[i].name;
                            var liId = jsonObj[i].liId;
                            var labelId = jsonObj[i].labelId;
                            var lableName = jsonObj[i].lableName;
                            if (name == 'dateInterval2') {//时间间隔处理
                                $('#beginDate2').attr('saveFlag', '1');
                                $('#endDate2').attr('saveFlag', '1');
                            } else if (name == 'dateInterval') {//时间间隔处理
                                $('#beginDate').attr('saveFlag', '1');
                                $('#endDate').attr('saveFlag', '1');
                            }else if (name == 'repayDateLi') {//时间间隔处理
                                $('#repayDate').attr('saveFlag', '1');
                                $('#repayDate2').attr('saveFlag', '1');
                            } else {
                                $('#' + name).attr('saveFlag', '1');
                            }
                            if (name == 'cityNo') {
                                $('#cityName').show();
                            }

                            if (lableName.length > 4) {
                                $('#' + labelId).css('line-height', '20px');
                                lableName = lableName.substring(0, 4) + "\n" + lableName.substring(4, lableName.length);
                            }
                            $('#' + labelId).html(lableName + ":");
                            $('#' + liId).show();
                        }
                    }
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
            }
        });
    };
    //多选查询条件标签
    var _showBigFormQueryCondition = function (name,dataSourceNo,tableId,keyName) {
        var buttonClick="";
            if(name=="brNo"||name=="companyNo"||name=="manageBrNo") {
                if (funRoleType == '1' || funRoleType == '2') {//非本人或本部门时
                    buttonClick = "";
                }else {
                    buttonClick='onclick="ReportEntrance.openDialogShowValueSearch(\''+dataSourceNo+'\',\''+name+'\', \''+name+'Name\',\''+tableId+'\',\''+keyName+'\')"';
                }
            }else if(name=="opNo"||name=="manageOpNo") {
                if (funRoleType == '1') {//非本人或本部门时
                    buttonClick = "";
                }else{
                    buttonClick='onclick="ReportEntrance.openDialogShowValueSearch(\''+dataSourceNo+'\',\''+name+'\', \''+name+'Name\',\''+tableId+'\',\''+keyName+'\')"';
                }
            }else{
                    buttonClick='onclick="ReportEntrance.openDialogShowValueSearch(\''+dataSourceNo+'\',\''+name+'\', \''+name+'Name\',\''+tableId+'\',\''+keyName+'\')"';
                }

        var str='<li class="li-one-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label for="#filter-fromSubject" class=" st1 li-lable-open" id="'+name+'Label"></label>\n' +
            '<input type="hidden" id="'+name+'" saveFlag="1" noFlag="1">\n' +
            '<input type="hidden" class="form-control form-warp form-input-width" name="'+name+'Name" id="'+name+'Name" saveFlag="0" noFlag="0" autocomplete="off">\n' +
            '<div class="st2 clearfix" id="'+name+'Div" onclick="ReportEntrance.openDataSource(this)">\n' +
            '&nbsp;</br>&nbsp;\n' +
            '</div>\n' +
            '<div class="btns btns_div">\n' +
            '<a href="#" id="conditions-trigger" class="conditions-trigger-open2 conditions-expand" tabindex="-1" data-flag="0" onclick="ReportEntrance.getMoreData(this);">更多<b></b></a>\n' +
            '<button  type="button" id="'+name+'Button" class="btns_button"'+buttonClick+'>+多选</button>\n' +
            '</div>\n' +
            '</li>';
        return str;

    };
    //日期范围查询条件标签
    var _showDateQueryCondition = function (name) {
        var str='<li class="li-two-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label id="'+name+'Label" class="li-lable-open st1"></label>\n' +
            '<input type="text" class="form-control form-warp cw-week dateInterver dateIntervalLable" readonly name="'+name+'BeginDate" style="background-color:#FFFFFF" saveFlag="1"  noFlag="0"  id="'+name+'BeginDate" autocomplete="off" onclick="ReportEntrance.showCalendarDlg(this);" onkeydown="enterKey();">\n' +
            '<span>至</span>\n' +
            '<input type="text" class="form-control form-warp cw-week dateInterver dateIntervalLable" readonly name="'+name+'EndDate"  style="background-color:#FFFFFF"saveFlag="1"  noFlag="0"  id="'+name+'EndDate" autocomplete="off" onclick="ReportEntrance.showCalendarDlg(this);" onkeydown="enterKey();">\n' +
            '</li>';
        return str;

    };
    //单个日期条件标签
    var _showSingelDateQueryCondition = function (name) {
        var str='<li class="li-one-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label id="'+name+'Label" class="li-lable-open st1"></label> \n' +
            '<input type="text" class="form-control form-warp cw-week  dateInterver dateIntervalLable" value="" name="'+name+'Single" id="'+name+'Single"  saveFlag="1"   noFlag="0" autocomplete="off" onclick="fPopUpCalendarDlg(this);">\n' +
            '</li>';
        return str;

    };
    //月查询条件标签
    var _showMonthQueryCondition = function (name) {
        var str='<li class="li-one-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label id="'+name+'Label" class="li-lable-open st1"></label> \n' +
            '<input type="text" class="form-control form-warp cw-week  dateInterver dateIntervalLable" value="" name="'+name+'" id="'+name+'"  saveFlag="1"   noFlag="0" autocomplete="off" onclick="laydatemonth(this);">\n' +
            '</li>';
        return str;

    };
    //周查询条件标签
    var _showWeeksQueryCondition = function (name) {
        var str='<li class="li-one-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label id="'+name+'Label" class="li-lable-open st1"></label> \n' +
            '<input type="text" class="form-control form-warp cw-week  dateInterver dateIntervalLable" value="" name="'+name+'" id="'+name+'"  saveFlag="1"   noFlag="0" autocomplete="off" onclick="laydateWeekly(this,\'weekly\');">\n' +
            '</li>';
        return str;

    };
    //年查询条件标签
    var _showYearQueryCondition = function (name) {
        var str='<li class="li-one-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label id="'+name+'Label" class="li-lable-open st1"></label> \n' +
            '<input type="text" class="form-control form-warp cw-week  dateInterver dateIntervalLable" value="" name="'+name+'" id="'+name+'"  saveFlag="1"   noFlag="0" autocomplete="off" onclick="laydateYear(this);">\n' +
            '</li>';
        return str;

    };
    //input框查询条件标签
    var _showInputQueryCondition = function (name) {
        var str='<li class="li-one-wrap st3" id="'+name+'Li" style="display: none;" showFlag="0">\n' +
            '<label id="'+name+'Label" class="li-lable-open st1"></label> \n' +
            '<input type="text" class="form-control form-warp cw-week  dateInterver dateIntervalLable" value="" name="'+name+'" id="'+name+'"  saveFlag="1"   noFlag="0" autocomplete="off">\n' +
            '</li>';
        return str;

    };
    //显示查询条件
    var _showOpenQueryCondition = function (reportId) {
        wholeReportId = reportId;
        jQuery.ajax({
            url: webPath + "/mfReportQueryConditionConfig/getConditonShowByIdAjax",
            data: {reportId: reportId},
            type: "POST",
            dataType: "json",
            async: false,
            beforeSend: function () {
            }, success: function (data) {
                if (data.flag == "success") {
                    var result = data.result;
                    if (result != '') {
                        var jsonObj = JSON.parse(result);//转换为json对象
                        var conditionHtml='';
                        for (var i in jsonObj) {
                            var name = jsonObj[i].name;
                            var lableType = jsonObj[i].lableType;  //代表标签类型
                            var dataSourceNo = jsonObj[i].dataSourceNo; //代表标签数据源的唯一标识
                            var tableId = jsonObj[i].tableId; //查询条件的tableId
                            var keyName = jsonObj[i].keyName; //数据字典查询条件的数据字典名称
                            if(lableType=='1'){//多选弹框
                                conditionHtml+= _showBigFormQueryCondition(name,dataSourceNo,tableId,keyName);
                            }else if(lableType=='2'){//日期范围
                                conditionHtml+= _showDateQueryCondition(name);
                            }else if(lableType=='3'){ //单个日期
                                conditionHtml+=_showSingelDateQueryCondition(name);
                            }else if(lableType=='4'){ //年
                                conditionHtml+=_showYearQueryCondition(name);
                            }else if(lableType=='5'){  //月
                                conditionHtml+=_showMonthQueryCondition(name);
                            }else if(lableType=='6'){//周
                                conditionHtml+=_showWeeksQueryCondition(name);
                            }else if(lableType=='7'){ //input框
                                conditionHtml+=_showInputQueryCondition(name);
                            }else{
                                conditionHtml='';
                            }
                        }
                        $('#more-conditions').html(conditionHtml);
                    }
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
            }
        });
    };
    //显示查询条件
    var _showOpenQueryMessage = function (reportId) {
        wholeReportId = reportId;
        jQuery.ajax({
            url: webPath + "/mfReportQueryConditionConfig/getConditonShowByIdAjax",
            data: {reportId: reportId},
            type: "POST",
            dataType: "json",
            async: false,
            beforeSend: function () {
            }, success: function (data) {
                if (data.flag == "success") {
                    var result = data.result;
                    if (result != '') {
                        var jsonObj = JSON.parse(result);//转换为json对象
                        for (var i in jsonObj) {
                            var name = jsonObj[i].name;
                            var lableType = jsonObj[i].lableType;
                            var lableName = jsonObj[i].lableName;
                            if (lableType == '2') {//时间间隔处理
                                $('#'+name+'BeginDate').attr('saveFlag', '1');
                                $('#'+name+'EndDate').attr('saveFlag', '1');
                            }else {
                                $('#' + name).attr('saveFlag', '1');
                            }
//							$('#'+liId).css({ "cssText": "float:left !important;margin-left:10px" });
                            if (i < 3) {
                                $('#' + name+'Li').show();
                                $('#' + name+'Label').html(lableName + ":");
                            } else {
                                $('#op_more_tr').show();
                                $('#' + name+'Li').attr('showFlag', '1');
                                $('#' + name+'Label').html(lableName + ":");
                            }
                        }
                    }
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
            }
        });
    };
    //日期验证
    var _validateSave = function (reportId) {
        var flag=true;
        jQuery.ajax({
            url: webPath + "/mfReportQueryConditionConfig/getConditonShowByIdAjax",
            data: {reportId: reportId},
            type: "POST",
            dataType: "json",
            async: false,
            beforeSend: function () {
            }, success: function (data) {
                if (data.flag == "success") {
                    var result = data.result;
                    if (result != '') {
                        var jsonObj = JSON.parse(result);//转换为json对象
                        for (var i in jsonObj) {
                            var name = jsonObj[i].name;
                            var lableType = jsonObj[i].lableType;  //代表标签类型
                            if (lableType == '2') {
                                if ($('#' + name + 'Li').css("display") != 'none') {
                                    var beginDate = $('#' + name + 'BeginDate').val();
                                    var endDate = $('#' + name + 'EndDate').val();
                                    if (beginDate != '' && endDate != '') {
                                        beginDate = beginDate.replace(new RegExp("-", "gm"), "");//替换日期之间的横杠
                                        endDate = endDate.replace(new RegExp("-", "gm"), "");//替换日期之间的横杠
                                        if (beginDate > endDate) {
                                            alert(top.getMessage("NOT_FORM_TIME", {
                                                "timeOne": "开始日期",
                                                "timeTwo": "结束日期"
                                            }), 1);
                                            flag = false;
                                        }
                                    }
                                }
                            }
                            if (reportId == 'report-ygyh-loanFinancialDaily') {
                            if (lableType == '3') {
                                if ($('#' + name + 'Li').css("display") != 'none') {
                                    var dateSingle = $('#' + name + 'Single').val();
                                    dateSingle = dateSingle.replace(new RegExp("-", "gm"), "");//替换日期之间的横杠
                                    if (dateSingle == '') {
                                        alert(top.getMessage("FIRST_SELECT_FIELD", {"field": "查询日期"}), 1);
                                        flag = false;
                                    }

                                }
                            }
                        }
                        }
                    }
                } else if (data.flag == "error") {
                }
            }, error: function (data) {
            }
        });
       /* if(reportId=='report-aj-creditLoan'||reportId=='report-aj-creditBus'||reportId=='report-aj-creditBusAsset'||reportId=='report-aj-dayAddBus'||reportId=='report-aj-dayAddBusAsset'||reportId=='report-aj-busAccumulative'||reportId=='report-aj-busAccumulativeAsset'){
        if ($('#kindType2Li').css("display") != 'none') {
            var ajkindNo = $('#ajkindNo').val();
            if (ajkindNo == '') {
                alert(top.getMessage("FIRST_SELECT_FIELD", {"field": "产品名称"}), 1);
                return false;
            }
        }
        }
        if(reportId=='report-aj-creditBus'||reportId=='report-aj-creditBusAsset'||reportId=='report-aj-dayAddBus'||reportId=='report-aj-dayAddBusAsset'||reportId=='report-aj-busAccumulative'||reportId=='report-aj-busAccumulativeAsset'){
            if ($('#dateIntervalLi').css("display") != 'none') {
                var beginDate = $('#beginDate').val();
                var endDate = $('#endDate').val();
                if (beginDate ==''||endDate=='') {
                    alert(top.getMessage("FIRST_SELECT_FIELD", {"field": "放款日期"}), 1);
                    return false;

                }
            }
        }*/
        // if ($('#brNoLi').css("display") != 'none') {
        //     var brNo = $('#brNo').val();
        //     if (brNo == '') {
        //         alert(top.getMessage("FIRST_SELECT_FIELD", {"field": "部门"}), 1);
        //         return false;
        //     }
        // }
        // if ($('#opNoLi').css("display") != 'none') {
        //     var opNo = $('#opNo').val();
        //     if (opNo == '') {
        //         var opHtml = $('#opNoLable').html().replace(":", "");
        //         alert(top.getMessage("FIRST_SELECT_FIELD", {"field": opHtml}), 1);
        //         return false;
        //     }
        // }
        /*if(reportId=='report-aj-creditBus'||reportId=='report-aj-creditBusAsset'){
            if ($('#dateIntervalLi').css("display") != 'none') {
                var beginDate = $('#beginDate').val();
                var endDate = $('#endDate').val();
                if (beginDate != '' && endDate != '') {
                    var time= Date.parse(endDate)-Date.parse(beginDate);
                    var days = parseInt(time / (1000 * 60 * 60 * 24));
                    if (days > parseInt(30)) {
                        alert(top.getMessage("NOT_FORM_TIME", {"timeOne": "放款日期的范围", "timeTwo": "31天"}), 1);
                        return false;
                    }

                }
            }
        }
        if(reportId=='report-aj-dayAddBus'||reportId=='report-aj-dayAddBusAsset'||reportId=='report-aj-busAccumulative'||reportId=='report-aj-busAccumulativeAsset'){
            if ($('#dateIntervalLi').css("display") != 'none') {
                var beginDate = $('#beginDate').val();
                var endDate = $('#endDate').val();
                if (beginDate != '' && endDate != '') {
                   var time= Date.parse(endDate)-Date.parse(beginDate);
                   var days = parseInt(time / (1000 * 60 * 60 * 24));
                    if (days > parseInt(10)) {
                        alert(top.getMessage("NOT_FORM_TIME", {"timeOne": "放款日期的范围", "timeTwo": "10天"}), 1);
                        return false;
                    }

                }
            }
        }
*/

        return flag;
    };

    //保存查询条件
    var _reportSave = function () {
        if (_validateSave(wholeReportId)) {
            $('.search_con').hide();
            _saveSqlCondition(wholeReportId);
        }
    };

    //重置查询条件
    var _resetQueryInput = function () {
        $("input").each(function (index, element) {
            var objId = $(this).attr("id");
            if (objId == 'brNo' || objId == 'brName' || objId == 'opNo' || objId == 'opName') {
                if ($(this).attr("readonly") != 'readonly') {
                    $(this).val('');
                    $("#opNoDiv").html("");
                }
            } else {
                $(this).val('');
            }
        });
    };
    var _resetOpNo = function () {
        $("#opNoDiv").html("");
        // $("#opNoDiv").css("height:42px");
    }
    //重置查询条件
    var _showCalendarDlg = function (obj) {
        var objId = $(obj).attr('id');
        if (wholeReportId == 'report-expect-return-money' && (objId == 'beginDate' || objId == 'endDate' || objId == 'beginDate2' || objId == 'endDate2')) {
            var minDate = currDate.substring(0, 4) + "-" + currDate.substring(4, 6) + "-" + currDate.substring(6, 8);
            fPopUpCalendarDlg({
                isclear: true,
                max: '2099-12-31 23:59:59', //最大日期
                choose: function (data) {
                }
            });
        } else if (wholeReportId == 'report-prtSaleYear' || objId == 'year') {
            fPopUpCalendarDlg({
                isclear: false,
                dateFormat: "YYYY",
                choose: function (data) {
                    $('#year').val(data.substring(0, 4));
                }
            });
        } else {
            fPopUpCalendarDlg(obj);
        }
        var laydate_box_Left = parseFloat($('#laydate_box').css("left").replace("px", ""));//日期控件
        var laydate_box_width = parseFloat($('#laydate_box').width());//日期控件宽度
        var row_info_content_width = parseFloat($('.row.info-content').eq(0).width()) + 30;//日期控件宽度
        if (laydate_box_Left + laydate_box_width > row_info_content_width) {
            laydate_box_Left = parseFloat(laydate_box_Left) - 116;
            $('#laydate_box').css("left", laydate_box_Left + "px");
        }
    };
    //保存查询条件
    var _openSaveSqlCondition = function () {
        if (_validateSave(wholeReportId)) {
            var url = webPath + "/mfReportQueryConditionUser/reportOpenQuery";
            var subArr = [];
            var queryStr = {};
            var conValue="";
            $("input").each(function (index, element) {
                var $parent = $(this).parent();
                if ($(this).attr('saveFlag') == '1' && ($parent.attr("showflag") == '0' && !$parent.is(":hidden") || $parent.attr("showflag") == '1')) {
                    var subObj = {};
                    if ($(this).attr('noFlag') == '1') {
                        subObj.value = $(this).val();
                        subObj.nameValue = $(this).next().val();
                    } else {
                        subObj.value = $(this).val();
                        subObj.nameValue = $(this).val();
                    }
                    var inputId = $(this).attr('id');
					var inputValue = $(this).val();
					if(inputId.substring(inputId.length-6)=='Single'||inputId.substring(inputId.length-9)=='BeginDate'||inputId.substring(inputId.length-7)=='EndDate' ){
                        subObj.value= $(this).val().replace(new RegExp("-","gm"),"");//替换日期之间的横杠
                    }
                    if(subObj.value != ''){
                        if(reportId!="report-loan-loanCountVoutype"){
                        queryStr[inputId]=subObj.value
                        if(inputId=='busDate'){
                            conValue=subObj.value;
                        }
                    }}
                    subArr.push(subObj);
                }

            });
            var condition = JSON.stringify(subArr);
            jQuery.ajax({
                url: url,
                data: {reportId: reportId, sqlCondition: condition, type: type},
                type: "POST",
                dataType: "json",
                async: false,
                beforeSend: function () {
                }, success: function (data) {
                    if (data.flag == "success") {
                        if($.isEmptyObject(queryStr)){
                            $('#queryStr').val("");
                        }else{
                            if(reportProjectFlag=='2'){
                                $('#queryStr').val((JSON.stringify(queryStr)+"").replace(/"/g,"'"));
                            }else{
                                $('#queryStr').val(JSON.stringify(queryStr));
                            }

                        }
                        if (type != 'java') {
                            if(reportProjectFlag=='2'){
                            $('#conValue').val(conValue);
                            $('#sqlCondition').val(encodeURIComponent(data.querySqlCondition));
                            $('#btnSearchForm').submit();
                            }else{
                                $('#sqlCondition').val(data.querySqlCondition);
                                $('#btnSearchForm').submit();
                            }
                        } else {
                            $('#sqlMap').val(data.querySqlCondition);
                            $('#btnSearchForm').submit();
                        }

                    } else if (data.flag == "error") {
                    }
                }, error: function (data) {
                }
            });
        }
    };
    var _saveCurSqlCondition = function () {
            var url = webPath + "/mfReportQueryConditionUser/updateSqlCondition";
            var subArr = [];
            var queryStr = {};
            var conValue="";
            $("input").each(function (index, element) {
                var $parent = $(this).parent();
                if ($(this).attr('saveFlag') == '1' && ($parent.attr("showflag") == '0' && !$parent.is(":hidden") || $parent.attr("showflag") == '1')) {
                    var subObj = {};
                    if ($(this).attr('noFlag') == '1') {
                        subObj.value = $(this).val();
                        subObj.nameValue = $(this).next().val();
                    } else {
                        subObj.value = $(this).val();
                        subObj.nameValue = $(this).val();
                    }
                    var inputId = $(this).attr('id');
                    var inputValue = $(this).val();
                    var date,year,month;
                    if(inputId == "busDate" && (inputValue == null || inputValue == "" || typeof(inputValue) == "undefined")) {
                        date = new Date();
                        year = date.getFullYear();
                        month = date.getMonth() + 1;
                        if(month <10) {
                            month = "0"+month;
                        }
                        var strDate = date.getDate();
                        if(strDate <10) {
                            strDate = "0"+strDate;
                        }
                        subObj.value=""+year+month+strDate;
                        $(this).val(""+year+"-"+month+"-"+strDate);
                    }else if(inputId == "month" && (inputValue == null || inputValue == "" || typeof(inputValue) == "undefined")) {
                        date = new Date();
                        year = date.getFullYear();
                        month = date.getMonth() + 1;
                        if(month <10) {
                            month = "0"+month;
                        }
                        subObj.value=""+year+month;
                        $(this).val(""+year+month);
                    }else if(inputId == 'date'||inputId.substring(inputId.length-9)=='BeginDate'||inputId.substring(inputId.length-7)=='EndDate'){
                        subObj.value= $(this).val().replace(new RegExp("-","gm"),"");//替换日期之间的横杠
                    }
                    if(subObj.value != ''){
                        queryStr[inputId]=subObj.value
                        if(inputId=='busDate'){
                            conValue=subObj.value;
                        }
                    }
                    subArr.push(subObj);
                }
            });
            var condition = JSON.stringify(subArr);
            jQuery.ajax({
                url: url,
                data: {reportId: reportId, sqlCondition: condition, type: type},
                type: "POST",
                dataType: "json",
                async: false,
                beforeSend: function () {
                }, success: function (data) {
                    if (data.flag == "success") {
                        alert(top.getMessage("SUCCEED_SAVE_CONTENT", "设置默认查询条件"), 1);
                    } else if (data.flag == "error") {
                        alert(top.getMessage("FAILED_OPERATION", "设置默认查询条件"), 0);
                    }
                }, error: function (data) {
                }
            });
    };
    //城市区域选择组件
    var cintyInit = function () {
        var setting = {
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "ps", "N": "ps"}
            }
        };
        $("input[name=cityName]").popupSelection({
            ajaxUrl: webPath + "/nmdArea/getAreaListAllAjax",
            searchOn: true,//启用搜索
            multiple: true,//单选
            ztree: true,
            ztreeSetting: setting,
            title: "城市",
            changeCallback: function (elem) {
                var areaNos = elem.data("values").val();
                var nodes = elem.data("treeNode");
                var address = "";
                for (var i = 0; i < nodes.length; i++) {
                    var addressTmp = nodes[i].name;
                    var parNode = nodes[i].getParentNode();
                    while (parNode) {
                        addressTmp = parNode.name + addressTmp;
                        parNode = parNode.getParentNode();
                    }
                    address += "," + addressTmp;
                }
                address = address.substring(1);
                BASE.removePlaceholder($("#cityName"));
                $("#cityName").val(address);
                if (areaNos != null) {
                    var reg = /[\s\|]/g;
                    areaNos = areaNos.replace(reg, ",");
                }
                $("#cityNo").val(areaNos);
            },
        });
    };
    //获取javaBean报表的请求地址
    var _getJavaUrl = function (id,reportUrl) {
        switch (id) {
            case "report-performance-statis-daily":// 阳关银行 业绩统计表
                javaUrl = webPath +"/mfJskgReport/getPerformanceStaList?reportId="+id+"&uuid=649aeadebf769d80dc82d1ee0535df86&reportUrl="+reportUrl;
                break;
            case "lendingStatics":
                javaUrl = webPath + "/mfReportLoan/getPutListByCus?reportId=" + id;
                break;
            case "report-prtSaleYear":
                javaUrl = webPath + "/mfProSaleYearly/proSaleStatYealy?reportId=" + id;
//			top.openBigForm(webPath+"/mfProSaleYearly/proSaleStatYealy?reportId="+id,title,function(){},"90","90");
                break;
            case "report-empOpStat":
                javaUrl = webPath + "/mfEmpOpStat/empOpStat?reportId=" + id;
                break;
            case "report-appEfficiency":
                javaUrl = webPath + "/mfAppEfficiency/appEfficiency?reportId=" + id;
                break;
            case "report-project-collection":
                javaUrl = webPath + "/mfReportRecall/getRecallList?reportId=" + id;
                break;
           /* case "report-credit-count":
                javaUrl = webPath + "/mfReportLoan/getPutList?reportId=" + id+"&uuid=2d255b2ad66f1266deef36db3ff8a98e&reportUrl="+reportUrl;
                break;*/
            case "report-service-account-month":
                javaUrl = webPath + "/mfReportRepayCount/getBussinessMonth?reportId=" + id;
                break;
        /*    case "report-data-source":
                javaUrl = webPath + "/mfBalAnalysis/getSourceInfo?reportId=" + id+"&uuid=7954b029f8ff6c3304f909126575634e&reportUrl="+reportUrl;
                break;*/
            case "report-loan-loanCnt":
                javaUrl = webPath + "/mfReportLoanAccu/getLoanAccuReportList?reportId=" + id;
//			top.openBigForm(webPath+"/mfReportLoanAccu/getLoanAccuReportList?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-busStatistics":
                javaUrl = webPath + "/mfReportLoanStatus/getMfReportBusStatistics?reportId=" + id;
//			top.openBigForm(webPath+"/mfReportLoanStatus/getMfReportBusStatistics?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-loanStatus":
                javaUrl = webPath + "/mfReportLoanStatus/getMfReportStatusList?reportId=" + id;
//			top.openBigForm(webPath+"/mfReportLoanStatus/getMfReportStatusList?reportId="+parm,title,function(){} ,"85","85");
                break;
            case "report-loan-balAccount":
                javaUrl = webPath + "/mfBalAnalysis/getBalAnalysisList?reportId=" + id;
//			top.openBigForm(webPath+"/mfBalAnalysis/getBalAnalysisList?reportId="+parm,title,function(){} ,"85","85");
                break;
          /*  case "report-loan-loanCountVoutype":
                javaUrl = webPath + "/mfReportLoan/getPutListByVouType?reportId=" + id+"&uuid=55c9bc31406e3f65e0145a753f25ea91&reportUrl="+reportUrl;
                break;*/
            case "report-loan-loanCountCustype":
                javaUrl = webPath + "/mfReportLoan/getPutListByCus?reportId=" + id;
                break;
            case "report-hbxd-repay":
                javaUrl = webPath + "/hbxdEfcdmRepayData/getListPage?reportId=" + id;//华北小贷金融监管还款业务数据查询
                break;
            case "report-loan-JSfkmx":
                javaUrl = webPath + "/jinSankingSoft/JSfkmxList?reportId=" + id;//金山放款明细表
                break;
            case "report-loan-JSskmx":
                javaUrl = webPath + "/jinSankingSoft/JSskmxList?reportId=" + id;//金山收款明细表
                break;
            case "report-loan-JSsryc":
                javaUrl = webPath + "/jinSankingSoft/JSsrycList?reportId=" + id;//金山收入预测表
                break;
            case "report-jinJian-count":
                javaUrl = webPath + "/yanGoReport/getJinJianReportList?reportId=" + id;//每日进件
                break;
            case "report-risk-daily-report":
                javaUrl = webPath + "/yanGoReport/getRiskDailyReportList?reportId=" + id;//风险日报
                break;
            case "report_stock_statistics":
                javaUrl = webPath + "/yanGoReport/getStockStatisticsReportList?reportId=" + id;//每日存量统计
                break;
            case "report-loan-rate-float-range":
                javaUrl = webPath + "/mfRateReport/getLoanRateList?reportId=" + id+"&uuid=6a357a30588c3b40cc762c353d860438";//分企业类型人民币贷款利率浮动区间分布表
                break;
            case "report-loan-rate-fix-timelimit":
                javaUrl = webPath + "/mfRateReport/getFixLoanRateTimeLimitList?reportId=" + id+"&uuid=a99068ea10cd34b92db3e81c3b843f58";//人民币贷款固定利率期限结构表
                break;
            case "report-fix-loan-rate-term":
                javaUrl = webPath + "/mfRateReport/getRMBFixedIntRateList?reportId=" + id+"&uuid=b4e14c4fa072cc0a624cb3629b8ef375";//人民币贷款固定利率区间分布表
                break;
            case "report-corp-loan-special-statistics":
                javaUrl = webPath + "/mfRateReport/getSpecialStatisticalReportsList?reportId=" + id+"&uuid=b780ceb9f1472b98d8bbc8ff93ff1070";//贷款公司、小额贷款公司专项统计报表
                break;
            case "report-corp-loan-case-condition":
                javaUrl = webPath + "/mfRateReport/getMfReportStatusList?reportId=" + id+"&uuid=09293927d5647f77741bf074ec7b31c3";//小贷公司贷款业务状况报表
                break;
            case "report-aj-dayAddBus"://小额信贷批量业务日新增业务分析表（担保方）
                javaUrl=webPath+"/anjiaRep/getDayAddVouList?reportId="+id;
                break;

        }


    };

    /*  大屏跳转 */
    var _openLargeScreen = function (obj,parm, title) {

        switch (parm) {
            case "report-zgc-vouBus":
                window.open(reportUrl+"/RDP-SERVER/bddpshow/show/58a929da48520d224cce3992a7d83a6c");
                break;
            case "report-large-screen-analysis":
                window.open(reportUrl+"/RDP-SERVER/bddpshow/show/e621b29d62822bdbb09fd42ede83fef6");
                break;
            case "report-large-screen-swiper":
                window.open(reportUrl+"/RDP-SERVER/modules/bddp/swiper/swiper.html");
                break;
            case "report-swiper":
                window.open(reportUrl+"/RDP-SERVER/modules/bddp/swiper/swiper.html");
                break;
            default :
                _showTips(obj);
                return false;
        }
    };
    var _openDataSource = function (obj) {
        var $obj = $(obj);
        var $btnId = $obj.attr('id').substr(0,($obj.attr('id').length-3))+'Button';
        $("#"+$btnId).click();
    };

    //获取javaBean报表的请求地址
    var _getMoreData = function (obj) {
        var moreFlag = $(obj).data("flag");
        if (moreFlag == undefined) {
            moreFlag = 0;
        }
        if (moreFlag == 0) {
            obj.parentElement.parentElement.style = "height:auto";
            var height = $(obj).parent().parent().height();
            $(obj).parent().parent().find("label").first().height(height);
            /*$('#brNoLabel').height(126);*/
            $(obj).data("flag", "1");
            $(obj).html("收起");
        } else {
            obj.parentElement.parentElement.style = "height:42px";
            $(obj).data("flag", "0");
            $(obj).html("更多");
        }
    };
    var _getMoreLi = function (obj) {
        var moreFlag = $(obj).data("flag");
        if (moreFlag == undefined) {
            moreFlag = 0;
        }
        if (moreFlag == 1) {
            //如果是收起
            var tmpCnt = 0;
            $("li").each(function (index, element) {
                if ($(this).attr('showFlag') == '0' && !$(this).is(":hidden")) {
                    if (tmpCnt > 0) {
                        $(this).attr('showFlag', "1");

                    }
                    tmpCnt++;
                }

            });
        }


        $("li").each(function (index, element) {
            if ($(this).attr('showFlag') == '1') {
                if (moreFlag == 0) {
                    //展开
                    //$(obj).data("flag", "1");
                    $(this).show();
                    $("#shouqi_icon").show();
                    $("#zhankai_icon").hide();

                } else {//收起
                    //$(obj).data("flag", "0");
                    $(this).hide();
                    //保留一行
                    $("#shouqi_icon").hide();
                    $("#zhankai_icon").show();
                }

            }

        });

        if (moreFlag == 0) {
            $("li").each(function (index, element) {
                //设置是否显示更多
                if (!$(this).is(":hidden")) {

                    var tmpWidth = $(this).width();
                    tmpWidth = tmpWidth * 0.78;
                    var tmpLen = 0;
                    $(this).find("span").each(function () {
                        tmpLen += $(this).width();
                    });

                    if (tmpWidth >= tmpLen) {
                        $(this).find("a").css("visibility", "hidden");
                    } else {
                        $(this).find("a").css("visibility", "visible");
                    }
                }

            });
        }
        $("#cityNameButton").show();
    };
    return {
        init: _init,
        openBase: _openBase,
        openThis: _openThis,
        openTuDemo: _openTuDemo,
        openReport: _openReport,
        openJIanGuan: _openJIanGuan,
        saveSqlCondition: _saveSqlCondition,
        reportQuery: _reportQuery,
        showFormConditionVal: _showFormConditionVal,
        validateSave: _validateSave,
        reportSave: _reportSave,
        resetQueryInput: _resetQueryInput,
        showCalendarDlg: _showCalendarDlg,
        openInit: _openInit,
        openSaveSqlCondition: _openSaveSqlCondition,
        getJavaUrl: _getJavaUrl,
        getMoreData: _getMoreData,
        getMoreLi: _getMoreLi,
        openLargeScreen:_openLargeScreen,
        openDataSource:_openDataSource,
        showOpenQueryCondition:_showOpenQueryCondition,
        openDialogShowValueSearch:openDialogShowValueSearch,
        saveCurSqlCondition:_saveCurSqlCondition
    };
}(window, jQuery);