<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ page import="app.component.common.BizPubParm" %>
<%
	String path = request.getContextPath();
	//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String layout = "layout/view";
	String wayclass = request.getParameter("wayclass");
	String inputSearch = request.getParameter("inputSearch").trim();
	inputSearch = java.net.URLDecoder.decode(inputSearch,"UTF-8");
	String firstKindNo = request.getParameter("firstKindNo");
	String investigateScNo = BizPubParm.SCENCE_TYPE_DOC_INVESTIGATION;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/Font-Awesome/css/font-awesome.css" />
		<link rel="stylesheet" href="${webPath}/layout/view/page/css/searchList.css" />
		<script type="text/javascript" src="${webPath}/layout/view/page/js/lavalamp.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/jQuery.easing.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/zl.js?v=${cssJsVersion}"></script>
		<link id="filter" rel="stylesheet" href="${webPath}/themes/factor/css/filter${skinSuffix}.css?v=${cssJsVersion}" />
		<!--jquery.autocompleter.js与common.jsp重复，可是去掉就报错-->
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.autocompleter.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/autocompleter.js"></script>
		<script type="text/javascript" src="${webPath}/component/demo/js/myCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/autocompleter.css" />
		<script type="text/javascript" src="${webPath}/component/include/highlight.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/page/js/searchList.js?v=${cssJsVersion}"></script>
		<script>
		top.LoadingAnimate.stop();
		var investigateScNo ='<%=investigateScNo%>';
		var gsTableData={};
		var sysGlobalType = parent.sysGlobalType;
		var tableParmList = parent.tableParmList;
		var wayclass = '<%=wayclass%>';
		var menuBtn=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
		var vpNo = '1';
		var riskLevel = '${riskLevel}';
		var firstKindNo = '<%=firstKindNo%>';
		var tenSpace = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';//十个空格 
		var applyHtmlMake = '<div class="forth font_size_14"><a>下一个业务步骤 :</a></div>';
		var pactHtmlMake = "";
		
		$(document).ready(function(){
			initGSHeader();
			addLampClick();
			$(".topd").click(function(){
				$(".search-row").dequeue().animate({
      				height:'toggle'
    			}, 500,"backout");
			});
			//<!-- 下划线暂时隐掉  -->
			//$(".lavaLamp").lavaLamp({ fx: "backout", speed: 700 ,wrapper:false});
			setCurred("0px",$(".current-cat").css("width"));
			$(".lavaLamp li").click(function(){
				$(".lavaLamp li a").removeClass("active");
				$(this).find("a").eq(0).addClass("active");
				var w = parseInt($(this).css("width").split("p")[0]);
				var l = $(".lavaLamp li").index($(this)[0])*w;
				postion["left"] = l;
				postion["width"] = w;
				setCurred(l+"px",$(this).css("width"));
				clkFlag = true;
			});
		});
		
		function setCurred(left,width){
			$(".back").css({
				"left": left,
				"width": width
			});
		}
		var gsData="";
		//初始化表头
		function initGSHeader(){
			var $lamp = $(".lavaLamp");
			gsData = parent.rzzl.getGlobalSearchData();
// 			console.log(gsData);
			$lamp.append("<li name='all' class='mySelectedNode'><a class='active'><div class='filter_option'><span class='node_name'>全部</span></div></a></li>");
			for(var i in gsData){
			var url = gsData[i].url;
			var lav = $lamp.find("li[name='"+gsData[i].type+"']");
			if(wayclass == "cusSearch"){
				if(gsData[i].type == "cusCustomer"){
					if(lav.length==0){
						var len = gsData[i].hex.length;
						var ww = (len-4)>0?(120+(len-4)*5):120;
						$lamp.append("<li style='width:80px;' name='"+gsData[i].type+"'><a class='active'><div class='filter_option'><span class='node_name'>"+gsData[i].hex+"</span></div></a></li>");
						gsTableData[gsData[i].type] = new Array();
						gsTableData[gsData[i].type].push(gsData[i]);
					}else{
						<!-- 这行是计数的,设计图里没有计数,先隐掉,以免又要 -->
						//lav.eq(0).find("span").eq(0).html(parseInt(lav.eq(0).find("span").eq(0).html())+1);
						gsTableData[gsData[i].type].push(gsData[i]);
					}
				}
			}else if(wayclass == "applySearch"){
				if(gsData[i].type == "apply"){
					if(lav.length==0){
						var len = gsData[i].hex.length;
						var ww = (len-4)>0?(120+(len-4)*5):120;
						$lamp.append("<li style='width:90px;' name='"+gsData[i].type+"'><a class='active'><div class='filter_option'><span class='node_name'>"+gsData[i].hex+"</span></div></a></li>");
						gsTableData[gsData[i].type] = new Array();
						gsTableData[gsData[i].type].push(gsData[i]);
					}else{
						//lav.eq(0).find("span").eq(0).html(parseInt(lav.eq(0).find("span").eq(0).html())+1);
						gsTableData[gsData[i].type].push(gsData[i]);
					}
				}
			}else if(wayclass == "pactSearch"){
				if(gsData[i].type == "pact"){
					if(lav.length==0){
						var len = gsData[i].hex.length;
						var ww = (len-4)>0?(120+(len-4)*5):120;
						$lamp.append("<li style='width:80px;' name='"+gsData[i].type+"'><a class='active'><div class='filter_option'><span class='node_name'>"+gsData[i].hex+"</span></div></a></li>");
						gsTableData[gsData[i].type] = new Array();
						gsTableData[gsData[i].type].push(gsData[i]);
					}else{
						//lav.eq(0).find("span").eq(0).html(parseInt(lav.eq(0).find("span").eq(0).html())+1);
						gsTableData[gsData[i].type].push(gsData[i]);
					}
				}		
			}else if(wayclass == "allSearch"){
					if(lav.length==0){
						var len = gsData[i].hex.length;
						var ww = (len-4)>0?(120+(len-4)*5):120;
						$lamp.append("<li style='width:100px;' name='"+gsData[i].type+"'><a class='active'><div class='filter_option'><span class='node_name'>"+gsData[i].hex+"</span></div></a></li>");
						gsTableData[gsData[i].type] = new Array();
						gsTableData[gsData[i].type].push(gsData[i]);
					}else{
						//lav.eq(0).find("span").eq(0).html(parseInt(lav.eq(0).find("span").eq(0).html())+1);
						gsTableData[gsData[i].type].push(gsData[i]);
					}
			}
			}//for循环结束
			initTable();
		}
		//初始化table
		function initTable(){
			var $table = $('<table width="100%" border="0" cellspacing="0" cellpadding="0"></table>');
				$table.removeClass("customStyle");
			$table.append("<thead></thead><tbody></tbody>");
			var $body = $table.children("tbody");
			var type = "all";
			var sumNum = parent.rzzl.getGlobalSearchData().length;
            if(wayclass == "cusSearch"){
                type = "cusCustomer";
                sumNum = gsTableData[type].length;
            }else if(wayclass == "applySearch"){
                type = "apply";
                sumNum = gsTableData[type].length;
            }else if(wayclass == "pactSearch"){
                type = "pact";
                sumNum = gsTableData[type].length;

            }
			$table.find("thead").append('<tr><td width="100%" class="font_size_14">共为您搜索相关结果'+sumNum+'个</td></tr>');
// 			console.log(parent.rzzl.getGlobalSearchData());
// 			console.log(gsTableData);
			showSearchInfo(0,30,$table);
			$(".tableContent").append($table);
			if($table.find("tr").length==0){
				$(".tableHeader").hide();
			}else{
				$(".tableHeader").show();
			}
			fHl(document.body,'<%=inputSearch%>', false);
			setColor($table);
		}
		//动态加载查询出的数据
		function showSearchInfo(benLine,endLine,tableObj){
			var num=0;
			i=0;
			var lenSum=gsData.length;
			var k="";
			while (num<endLine && i<lenSum){
				var obj=gsData[i++];
				k=obj.type;
                if(wayclass == "cusSearch" ) {
                    if (k == "cusCustomer") {
                        if(num>=benLine){
                            appendCus(obj,tableObj);
                        }
                        num++;
                    }
                }else if(wayclass == "applySearch") {
					if(k == "apply") {
                        if(num>=benLine) {
                            appendApply(obj,tableObj);
                        }
                        num++;
                    }
                }else if(wayclass == "pactSearch") {
                    if (k == "pact") {
                        if(num>=benLine) {
                            appendPact(obj,tableObj);
                        }
                        num++;
					}
                }else if(wayclass == "allSearch"){
                    if(num>=benLine) {
                        if (k == "cusCustomer") {
                            appendCus(obj,tableObj);
                        } else if (k == "apply") {
                            appendApply(obj,tableObj);
                        } else if (k == "pact") {
                            appendPact(obj,tableObj);
                        }
                    }
                    num++;
				}
			}
		}

		function appendPact(obj,tableObj) {
            var pact = obj;
            var pactEntity = obj.entity;
            var beginDate = ftime(pactEntity.begin_date);
            var endDate = ftime(pactEntity.end_date);
            var termShow = pactEntity.term_show;
            var pactId = pactEntity.pact_id;
            var appId = pactEntity.app_id;
            var checkUrl = webPath + '/mfExamineHis/input?pactId=' + pactId + '&appId=' + appId;
            pactHtmlMake = '<div class="forth font_size_14"><a href="javascript:void(0)" onclick="searchList.loanAfterExamine(\'' + checkUrl + '\')">贷后检查</a>' + tenSpace + '<a href="javascript:void(0)" onclick="clickLable(\'' + pact.url + '\')">业务还款</a></div>'
            tableObj.find("tbody").append('<tr><td width="70%"><div onclick="clickLable(\'' + pact.url + '\')"><div class="first font_size_14 color_theme" >' + pactEntity.cus_name + '&nbsp;（' + pact.hex + '）</div><div class="second">' + pact.label + '</div><div class="third">合同开始日：' + beginDate + tenSpace + '合同结束日：' + endDate + tenSpace + '期限：' + termShow + '</div></div>' + pactHtmlMake + '</td></tr>');
        }

		function appendCus(obj,tableObj){
            var apply = obj;
            var applyEntity = obj.entity;
            var appId = applyEntity.app_id;
            var appAmt = fmoney(applyEntity.app_amt, 2);
            var termShow = "";
            if (applyEntity.term_show) {
                var termShowPonit = applyEntity.term_show.indexOf("个");
                if (termShowPonit > 0) {
                    termShow = applyEntity.term_show.substring(0, termShowPonit);
                } else {
                    termShow = applyEntity.term_show.substring(0, applyEntity.term_show.length - 1);
                }
            }
            var appSts = applyEntity.app_sts;
            var wkfAppId = applyEntity.wkf_app_id;
            //getNextBusPoint(appId,appSts,wkfAppId);
            applyHtmlMake = "";
            tableObj.find("tbody").append('<tr id=' + appId + ' class="apply"><td width="70%"><div onclick="clickLable(\'' + apply.url + '\')"><div class="first  font_size_14 color_theme" >' + applyEntity.cus_name + '&nbsp;（' + apply.hex + '）</div><div class="second">' + apply.label + '</div><div class="third">申请金额：' + appAmt + '元' + tenSpace + '申请期限：' + termShow + '个月</div></div>' + applyHtmlMake + '</td></tr>');
        }

		function appendApply(obj,tableObj){
            var cus = obj;
            var cusEntity = obj.entity;
            var cusType = "search";
            var contacts_name = "未填写";
            var contacts_tel = "未填写";
            var id_num = "未填写";
            var comm_address = "未填写";

            var appId=cusEntity.app_id;
            var cusNo="";
              $.ajax({
                   url : webPath+"/mfBusApply/getByNewId?appId="+appId,
                   type : "POST",
                   dataType : "json",
                   async : false,
                   success : function(data) {
                       cusNo=data;
                   }
               });
            if (cusEntity.contacts_name) {
                contacts_name = cusEntity.contacts_name;
            }
            if (cusEntity.contacts_tel) {
                contacts_tel = cusEntity.contacts_tel;
            }
            if (cusEntity.id_num) {
                id_num = cusEntity.id_num;
            }
            if (cusEntity.comm_address) {
                comm_address = cusEntity.comm_address;
            }
            tableObj.find("tbody").append('<tr class="cus"><td width="70%"><div onclick="clickLable(\'' + cus.url + '\')"><div class="first font_size_14 color_theme" >' + cusEntity.cus_name + '&nbsp;（' + cus.hex + '）</div><div class="second">' + cus.label + tenSpace + '证件号码：' + id_num + tenSpace + '姓名：' + contacts_name + tenSpace + '电话：' + contacts_tel + tenSpace + '</div><div class="third">地点：' + comm_address + '</div></div>' + '<div class="forth font_size_14"><a href="javascript:void(0)" onclick="searchList.updateCusFormStas(\'' + cusNo + '\');">完善资料</a>' + tenSpace + '<a href="javascript:void(0)" onclick="searchList.getAppAuth(\'' + cusNo + '\',\'' + cusType + '\');">申请授信</a>' + tenSpace + '<a class="yewubanli" id="' + cus.entity.cus_no + '" onclick="openCusApply(' + "'" + cusNo + "'" + ')">业务办理</a></div>' + '</td></tr>');
		}


		//表头的树形节点点击 动态加载已查询出客户 业务 合同的数据
		function showSearchLavaLampInfo(benLine,endLine,tableObj,type,gsTableData){
			var num=0;
			var lenSum=gsTableData[type].length;
			if(endLine>lenSum){
				endLine=lenSum;
			}
			for(var i = benLine;i<endLine;i++){
				num++;
				if(num>30){
					break;
				}
				if(type=="apply"){
					if(gsTableData['apply'].length<=i){
						break;
					}
					var apply = gsTableData['apply'][i].entity;
					var appId = apply.app_id;
					var appAmt =fmoney(apply.app_amt,2);
					var termShow = apply.term_show;
					var appSts = apply.app_sts;
					var wkfAppId = apply.wkf_app_id;
					//getNextBusPoint(appId,appSts,wkfAppId);
					applyHtmlMake="";
					tableObj.find("tbody").append('<tr><td width="70%"><div onclick="clickLable(\''+gsTableData['apply'][i].url+'\')"><div class="first font_size_14 color_theme" >'+apply.cus_name+'&nbsp;（'+gsTableData['apply'][i].hex+'）</div><div class="second">'+gsTableData['apply'][i].label+'</div><div class="third">申请金额：'+appAmt+'元'+tenSpace+'申请期限：'+termShow+'</div></div>'+applyHtmlMake+'</td></tr>');
				}else if(type=="cusCustomer"){
					if(gsTableData['cusCustomer'].length<=i){
						break;
					}
					var cus = gsTableData['cusCustomer'][i].entity;
					var cusNo = cus.cus_no;
					var cusType = cus.cus_type;
					var contacts_name="未填写";
					var contacts_tel="未填写";
					var id_num="未填写";
					var comm_address="未填写";
					if(cus.contacts_name){
						contacts_name = cus.contacts_name;
					}
					if(cus.contacts_tel){
						contacts_tel = cus.contacts_tel;
					}
					if(cus.id_num){
						id_num = cus.id_num;
					}
					if(cus.comm_address){
						comm_address = cus.comm_address;
					}
					tableObj.find("tbody").append('<tr class="cus"><td width="70%"><div onclick="clickLable(\''+gsTableData['cusCustomer'][i].url+'\')"><div class="first font_size_14 color_theme" >'+gsTableData['cusCustomer'][i].entity.cus_name+'&nbsp;（'+gsTableData['cusCustomer'][i].hex+'）</div><div class="second">'+gsTableData['cusCustomer'][i].label+tenSpace+'证件号码：'+id_num+tenSpace+'姓名：'+contacts_name+tenSpace+'电话：'+contacts_tel+tenSpace+'</div><div class="third">地点：'+comm_address+'</div></div>'+'<div class="forth font_size_14"><a href="javascript:void(0)" onclick="searchList.updateCusFormStas(\''+cusNo+'\');">完善资料</a>'+tenSpace+'<a href="javascript:void(0)" onclick="searchList.getAppAuth(\''+cusNo+'\',\''+cusType+'\');">申请授信</a>'+tenSpace+'<a class="yewubanli" id="'+gsTableData['cusCustomer'][i].entity.cus_no+'" onclick="openCusApply('+"'"+cusNo+"'"+')">业务办理</a></div>'+'</td></tr>');
				}else if(type=="pact"){
					if(gsTableData['pact'].length<=i){
						break;
					}
					var pact = gsTableData['pact'][i].entity;
					var beginDate = ftime(pact.begin_date);
					var endDate = ftime(pact.end_date);
					var termShow = pact.term_show; 
					var pactId = pact.pact_id;
					var appId = pact.app_id;
					var checkUrl = webPath+'/mfExamineHis/input?pactId='+pactId+'&appId='+appId;
					pactHtmlMake = '<div class="forth font_size_14"><a href="javascript:void(0)" onclick="searchList.loanAfterExamine(\''+checkUrl+'\')">贷后检查</a>'+tenSpace+'<a href="javascript:void(0)" onclick="clickLable(\''+gsTableData['pact'][i].url+'\')">业务还款</a></div>';
					tableObj.find("tbody").append('<tr><td width="70%"><div onclick="clickLable(\''+gsTableData['pact'][i].url+'\')"><div class="first font_size_14 color_theme" >'+gsTableData['pact'][i].entity.cus_name+'&nbsp;（'+gsTableData['pact'][i].hex+'）</div><div class="second">'+gsTableData['pact'][i].label+'</div><div class="third">合同开始日：'+beginDate+tenSpace+'合同结束日：'+endDate+tenSpace+'期限：'+termShow+'</div></div>'+pactHtmlMake+'</td></tr>');
				}
			}
		}
		
		function scrollPageLavaLamp(type,data){
			//滚动分页展示 
			var time = 30;//每页显示条数
			var j = 1;
			var len = 0;

            var type = "all";
            var len = parent.rzzl.getGlobalSearchData().length;
            data=gsData;
            if(wayclass == "cusSearch"){
                type = "cusCustomer";
                len = gsTableData[type].length;
                data=gsTableData[type];
            }else if(wayclass == "applySearch"){
                type = "apply";
                len = gsTableData[type].length;
                data=gsTableData[type];
            }else if(wayclass == "pactSearch"){
                type = "pact";
                len = gsTableData[type].length;
                data=gsTableData[type];
            }
			var times = parseInt(len/time);
			$(".allData").text(len);
			if(times==0){
				$(".showData").text(len);
			}else{
				$(".showData").text("30");
			}
			var nScrollHeight = 0;
			var nScrollTop = 0;
			var nDivHeight = $(".container").height();
			var tableObj=$(".tableContent").find("table ");
			$(".tableContent").unbind ('scroll');
			$(".tableContent").scroll(function(){
				nScrollHeight = $(this)[0].scrollHeight; //是tableContent的高度  
				nScrollTop = $(this)[0].scrollTop;//从顶端到现在滚动的高度 
				var paddingBottom = parseInt($(this).css('padding-bottom'));//.tableContent的内边距 
				var paddingTop = parseInt($(this).css('padding-top'));
				if(paddingBottom+paddingTop+nScrollTop+nDivHeight >= nScrollHeight){//判斷滾動條到達底部 
					$(".fa-3x").show();
					setTimeout(function(){
						$(".fa-3x").hide();
					},1000);
					if(j==times){
						var trLen=tableObj.find("tr").length;
						if(trLen>=len){
							showSearchLavaLampInfoPage(len,len,tableObj,type,data);
						}else{
							showSearchLavaLampInfoPage(j*30,len,tableObj,type,data);
						}
						$(".showData").text(len);
					}else if(j<times){
						showSearchLavaLampInfoPage(j*30,(j+1)*30,tableObj,type,data);
						j++;
						$(".showData").text(j*30);
					}
				}
			});
			//滚动分页结束
		};
		//表头的树形节点点击 动态加载已查询出客户 业务 合同的数据
		function showSearchLavaLampInfoPage(benLine,endLine,tableObj,type,gsTableData){
			var num=0;
			var lenSum=0;
			if(type=="all"){
				tableObj.remove();
				showSearchInfo(benLine,endLine,tableObj);
				$(".tableContent").append(tableObj);
				if(tableObj.find("tr").length==0){
					$(".tableHeader").hide();
				}else{
					$(".tableHeader").show();
				}
				fHl(document.body,'<%=inputSearch%>', false);
				setColor(tableObj);
			}else{
				lenSum=gsTableData.length;
				if(endLine>lenSum){
					endLine=lenSum;
				}
				for(var i = benLine;i<endLine;i++){
					num++;
					if(num>30){
						break;
					}
					if(type=="apply"){
						if(gsTableData.length<=i){
							break;
						}
						var apply = gsTableData[i].entity;
						var appId = apply.app_id;
						var appAmt =fmoney(apply.app_amt,2);
						var termShow = apply.term_show;
						var appSts = apply.app_sts;
						var wkfAppId = apply.wkf_app_id;
						applyHtmlMake="";
						tableObj.find("tbody").append('<tr><td width="70%"><div onclick="clickLable(\''+gsTableData[i].url+'\')"><div class="first font_size_14 color_theme" >'+apply.cus_name+'&nbsp;（'+gsTableData[i].hex+'）</div><div class="second">'+gsTableData[i].label+'</div><div class="third">申请金额：'+appAmt+'元'+tenSpace+'申请期限：'+termShow+'</div></div>'+applyHtmlMake+'</td></tr>');
					}else if(type=="cusCustomer"){
						if(gsTableData.length<=i){
							break;
						}
						var cus = gsTableData[i].entity;
						var cusNo = cus.cus_no;
						var cusType = cus.cus_type;
						var contacts_name="未填写";
						var contacts_tel="未填写";
						var id_num="未填写";
						var comm_address="未填写";
						if(cus.contacts_name){
							contacts_name = cus.contacts_name;
						}
						if(cus.contacts_tel){
							contacts_tel = cus.contacts_tel;
						}
						if(cus.id_num){
							id_num = cus.id_num;
						}
						if(cus.comm_address){
							comm_address = cus.comm_address;
						}
						tableObj.find("tbody").append('<tr class="cus"><td width="70%"><div onclick="clickLable(\''+gsTableData[i].url+'\')"><div class="first font_size_14 color_theme" >'+cus.cus_name+'&nbsp;（'+gsTableData[i].hex+'）</div><div class="second">'+gsTableData[i].label+tenSpace+'证件号码：'+id_num+tenSpace+'姓名：'+contacts_name+tenSpace+'电话：'+contacts_tel+tenSpace+'</div><div class="third">地点：'+comm_address+'</div></div>'+'<div class="forth font_size_14"><a href="javascript:void(0)" onclick="searchList.updateCusFormStas(\''+cusNo+'\');">完善资料</a>'+tenSpace+'<a href="javascript:void(0)" onclick="searchList.getAppAuth(\''+cusNo+'\',\''+cusType+'\');">申请授信</a>'+tenSpace+'<a class="yewubanli" id="'+cus.cus_no+'" onclick="openCusApply('+"'"+cusNo+"'"+')">业务办理</a></div>'+'</td></tr>');
					}else if(type=="pact"){
						if(gsTableData.length<=i){
							break;
						}
						var pact = gsTableData[i].entity;
						var beginDate = ftime(pact.begin_date);
						var endDate = ftime(pact.end_date);
						var termShow = pact.term_show; 
						var pactId = pact.pact_id;
						var appId = pact.app_id;
						var checkUrl = webPath+'/mfExamineHis/input?pactId='+pactId+'&appId='+appId;
						pactHtmlMake = '<div class="forth font_size_14"><a href="javascript:void(0)" onclick="searchList.loanAfterExamine(\''+checkUrl+'\')">贷后检查</a>'+tenSpace+'<a href="javascript:void(0)" onclick="clickLable(\''+gsTableData[i].url+'\')">业务还款</a></div>';
						tableObj.find("tbody").append('<tr><td width="70%"><div onclick="clickLable(\''+gsTableData[i].url+'\')"><div class="first font_size_14 color_theme" >'+pact.cus_name+'&nbsp;（'+gsTableData[i].hex+'）</div><div class="second">'+gsTableData[i].label+'</div><div class="third">合同开始日：'+beginDate+tenSpace+'合同结束日：'+endDate+tenSpace+'期限：'+termShow+'</div></div>'+pactHtmlMake+'</td></tr>');
					}
				}
			}
		}
		//表头的树形节点触发事件
		function addLampClick(){
			$(".lavaLamp li").click(function(){
				var $table = $(".tableContent table");
				$table.html("");
				$table.removeClass("customStyle");
				$table.append("<thead></thead><tbody></tbody>");
				$table.find("thead").append('<tr><td width="100%" class="font_size_14">共为您搜索相关结果'+parent.rzzl.getGlobalSearchData().length+'个</td></tr>');
				var type = $(this).attr("name");
				if($(".mySelectedNode").length>0){
					$(".lavaLamp li").removeClass("mySelectedNode");
					$(this).addClass("mySelectedNode");//选中样式
				}else{
					$(this).addClass("mySelectedNode");//选中样式
				}
				var len=0;
				if(type=="all"){
					$table.remove();
					initTable();
					len=parent.rzzl.getGlobalSearchData().length;
					$table.find("thead .font_size_14").html('共为您搜索相关结果'+len+'个');
					if(len>30){
						$(".footer .showData").html(30);
					}else{
						$(".footer .showData").html(len);
					}
					$(".footer .allData").html(len);
				}else{
					showSearchLavaLampInfo(0,30,$table,type,gsTableData);
					len=gsTableData[type].length;
					$table.find("thead .font_size_14").html('共为您搜索相关结果'+len+'个');
					if(len>30){
						$(".footer .showData").html(30);
					}else{
						$(".footer .showData").html(len);
					}
					$(".footer .allData").html(len);
				}
				fHl(document.body, '<%=inputSearch%>', false);
				scrollPageLavaLamp(type,gsTableData[type]);
			});
		}
		function setColor(tableObj){
			$(".lavaLamp li").find("span").css("color","#333");
			$(".footer").find("span").css("color","#333");
			tableObj.find("thead").find("span").css("color","#333");
		}
		function clickLable(url){
			window.location.href= webPath + url;
		}
		//业务办理
		function openCusApply(cusNo){
			//判断该客户是否完善了基本信息
			$.ajax({
				url:webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+cusNo,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.fullFlag == '1'){//全部都填写了
						top.createShowDialog("/mfBusApply/inputQuery?cusNo="+cusNo+"&kindNo="+firstKindNo,"业务申请");
					}else if(data.fullFlag == '0'){
						alert(top.getMessage("FIRST_COMPLETE_INFORMAATION","客户信息"),0);
					}
				}
			});
		}
		//驼峰转小写加_(匹配数据字段)
		function getEntityCol(str){
			var returnStr = "";
				for(i=0;i<str.length;i++){
					var chars = str.charAt(i);
					if((chars>'A'&&chars<'Z')||chars=='A'||chars=='Z'){
						chars = chars.toLowerCase();
						returnStr+="_"+chars;
					}else{
						returnStr+=chars;
					}
				}
				return returnStr;
		}
		//根据类型获取
		function getTypeParm(type){
			 var parm = {};
			 if(tableParmList!==undefined){
			 		$.each(tableParmList, function(index,val) {
			 		   var typeTmp = val.split(":")[0];
			 			 if(typeTmp == type){
			 			    if( val.split(":").length=2){
			 			    	var keys = val.split(":")[1];
			 			 	  	parm[keys.split("-")[0]] = keys.split("-")[1];
			 			    }
			 			 }
			 		});
			 }
			 return parm;
		}
		//获取数据字典项值
		function getParmDic(data,str){
			var parmDicName = str;
			$.each(data, function(index,obj) {
				if(obj.optCode==str){
					parmDicName = obj.optName;
					return false;
				}
			});
			if(parmDicName==""||parmDicName==undefined||parmDicName==null){
				parmDicName = str;
			}
			return parmDicName;
		}
		var getNextBusPoint = function(appId,appSts,wkfAppId){
			if(appSts == '2'){
				 applyHtmlMake = '<div class="forth font_size_14"><span>申请已提交，正在审批中</span></div>';
			}else if(appSts == '4'){
				 applyHtmlMake = '<div class="forth font_size_14"><span>申请已审批通过，请在合同视角中查看信息</span></div>';
			}else if(appSts == '5'){
				 applyHtmlMake = '<div class="forth font_size_14"><span>申请已被否决，业务结束</span></div>';
			}else{
				$.ajax({
					type:"post",
					async:false,
					url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId,
					success:function(data){
						var url = data.url;
						var title=data.title;
						var nodeName = data.nodeName;
						/* if(menuBtn.indexOf(nodeName)!="-1"){
							applyHtmlMake = '<div class="forth">下一业务步骤：<a>'+title+'</a></div>';
							$(".forth").click(function(){
								toNextBusPoint(url,title,nodeName);
							}); 
						} */
						applyHtmlMake = '<div class="forth font_size_14">下一业务步骤：<a href="javascript:void(0)" onclick="toNextBusPoint(\''+url+'\',\''+title+'\',\''+nodeName+'\',\''+appId+'\',\''+wkfAppId+'\',\''+appSts+'\')">'+title+'</a></div>';
					}
				});
			}
		};
		//跳转至下一业务节点
		function toNextBusPoint(url,title,nodeName,appId,wkfAppId,appSts){
			if(riskLevel == "99"){//riskLevel为99标书拒绝级业务
				var pointInfo = '<div style="height: 120px;padding: 20px;width: 300px;"><div style="height: 40px;">该业务风险过高，无法进行下一步</div><div><a  href="javaScript:void(0);" onclick="busRisk();">查看风险>></a></div></div>';
				top.dialog({
					title:'风险提示',
					id:"riskInfoDialog",
					backdropOpacity:0,
					content:pointInfo
				}).showModal();
				return false;
			}
			var tmpUrl=url.split("&")[0];
			var popFlag = tmpUrl.split("?")[1].split("=")[1];
			if(popFlag=="0"){
				alert(top.getMessage("CONFIRM_OPERATION",title),2,function(){
				if(!searchList.valiDocIsUp(appId)){
					return;
				} 
				LoadingAnimate.start();
				$.ajax({
					url:url,
					type:'post',
		    		dataType:'json',
					success:function(data){
						LoadingAnimate.stop();
						if(data.flag=="success"){
							if(data.node=="processaudit"){
								//审批提醒弹窗，不自动关闭
								searchList.getTaskInfo(wkfAppId,appId,appSts);
								window.top.alert(data.msg,3);
							}
						}else{		
							LoadingAnimate.stop();
							alert(data.msg,0);
						}
					}
				});
			});
			}else{
				if(nodeName=="investigation"){  //尽调报告
					url = url+"&scNo="+investigateScNo;
					top.window.openBigForm(url,title,function(){
						$.ajax({
							url:webPath+"/docManage/getDocNodesAjax?"+docParm,
							type:"POST",
							dataType:"json",
							success:function(data){
								searchList.getTaskInfo(wkfAppId,appId,appSts);
							},error:function(){}
						});
					});
				}
				//押品登记节点，选择押品类别
				top.appId = appId;
				top.wkfAppId = wkfAppId;
				top.appSts = appSts;
				if(nodeName=="pledge_reg"){
					top.window.openBigForm(url,title,searchList.wkfCallBack);
				}else{
					top.window.openBigForm(url,title,searchList.wkfCallBack);
				}
			}
		}
		//xinjia
		$(function(){
			/* $('#nope1').autocompleter('option',{
				  source:searchDatas
			}); */
			var inputSearch = '<%=inputSearch%>';
			$("#nope1").val(inputSearch);
			
			$("#nope1").bind("input propertychange",function(){
				inputSearch = $("#nope1").val();
			});
			$("#search").click(function(){
				$("#nope1").focus();
				var wayclass="allSearch";
				var firstKindNo = '<%=firstKindNo%>';
				inputSearch = encodeURIComponent(inputSearch);
    			inputSearch = encodeURIComponent(inputSearch);
				sreached(searchDatas,wayclass,inputSearch,firstKindNo);
			});
			$(".search-input").bind("keydown",function(e){
				if(e.keyCode == "13"){
					var wayclass="allSearch";
					var firstKindNo = '<%=firstKindNo%>';
					inputSearch = encodeURIComponent(inputSearch);
    				inputSearch = encodeURIComponent(inputSearch);
					sreached(searchDatas,wayclass,inputSearch,firstKindNo);
				}
			});
			scrollPage();
		});
		
		function scrollPage(){
			//滚动分页展示 
			var time = 30;//每页显示条数
			var j = 1;
            var type = "all";
            var len = parent.rzzl.getGlobalSearchData().length;
            if(wayclass == "cusSearch"){
                type = "cusCustomer";
                len = gsTableData[type].length;
                data=gsTableData[type];
            }else if(wayclass == "applySearch"){
                type = "apply";
                len = gsTableData[type].length;
                data=gsTableData[type];
            }else if(wayclass == "pactSearch"){
                type = "pact";
                len = gsTableData[type].length;
                data=gsTableData[type];
            }
			var times = parseInt(len/time);
			$(".allData").text(len);
			if(times==0){
				$(".showData").text(len);
			}else{
				$(".showData").text("30");
			}
			var nScrollHeight = 0;
			var nScrollTop = 0;
			var nDivHeight = $(".container").height();
			var tableObj=$(".tableContent").find("table ");
			$(".tableContent").scroll(function(){
				nScrollHeight = $(this)[0].scrollHeight; //是tableContent的高度  
				nScrollTop = $(this)[0].scrollTop;//从顶端到现在滚动的高度 
				var paddingBottom = parseInt($(this).css('padding-bottom'));//.tableContent的内边距 
				var paddingTop = parseInt($(this).css('padding-top'));
				if(paddingBottom+paddingTop+nScrollTop+nDivHeight >= nScrollHeight){//判斷滾動條到達底部 
					$(".fa-3x").show();
					setTimeout(function(){
						$(".fa-3x").hide();
					},1000);
					if(j==times){
						var trLen=tableObj.find("tr").length;
						if(trLen>=len){
							showSearchInfo(len,len,tableObj);
						}else{
							showSearchInfo(j*30,len,tableObj);
						}
						$(".showData").text(len);
					}else if(j<times){
						j*30;
						showSearchInfo(j*30,(j+1)*30,tableObj);
						j++;
						$(".showData").text(time*j);
					}
				}
			});
			//滚动分页结束
		}
		//格式化金额
		function fmoney(s, n) { 
			n = n > 0 && n <= 20 ? n : 2; 
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
			var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
			t = ""; 
			for (i = 0; i < l.length; i++) { 
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
			} 
			return t.split("").reverse().join("") + "." + r; 
		};
		//格式化日期
		function ftime(time){
			if(time!=""&&time!=null){
				var timeNew = String(time);
				return timeNew.substring(0,4)+"-"+timeNew.substring(4,6)+"-"+timeNew.substring(6,8);
			}else{
				return "未登记";
			};
		};
		</script>
	</head>
	<body style="overflow-y: hidden;">
	<div class="container">
		<div class="topcontent mysearch-div">
			<ul class="lavaLamp ztree">
			</ul>
		</div>
		<!--新加搜索框-->
		<div class="row search-row">
			<div class="input-group input-group-lg search-input-div col-sm-6">
				<input id="nope1" type="text" class="form-control search-input" />
				<span id="search" class="input-group-addon pointer"><i class="i i-fangdajing"></i>搜索</span>
			</div>
		</div>
		<!--搜索框结束-->
		<!-- <div class="topd"><span>全局搜索</span><i class="icon-chevron-down"></i></div> -->
		
		<div class="tableHeader" style="padding: 0 15px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			</table>
		</div>
		<div class="row">
			<div class="tableContent" style="overflow-y:auto; ">
				
			</div>
		</div>
	</div>
		<div class="footer">
			<div><i class="fa fa-spinner fa-pulse fa-3x" style="display:none"></i>当前显示<span class="showData"></span>条数据，共<span class="allData"></span>条</div>
		</div>
	</body>
</html>
