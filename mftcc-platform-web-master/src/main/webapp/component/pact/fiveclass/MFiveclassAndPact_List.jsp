<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfPactFiveclass/findFiveClassAndPactByPageAjax",//列表数据查询的url
			    	tableId:"tablequeryfiveandpact0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
                    callback:function(){
						$("#tab").find("tr").each(function () {
                            var tdArr = $(this).children();
                           // var color = tdArr.eq(8).text();//获取五级分类的值;
                            var color = tdArr.find("input[name='fiveclassSts']").val();
							if(color==("1")){//正常
							   tdArr.eq(3).find("a").css("color","#00BB00");
							}else if(color==("2")){//关注
                                tdArr.eq(3).find("a").css("color","#019858");
                            }else if(color==("3")){//次级
                                tdArr.eq(3).find("a").css("color","#e9aa64");
                            }else if(color==("4")){//可疑
                                tdArr.eq(3).find("a").css("color","#FFAF60");
                            }else if(color==("5")){//损失
                                tdArr.eq(3).find("a").css("color","red");
                            }
                        })
					}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			 function getDetailApply(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 function getDetailCus(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 function getDetailFive(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 
			function fiveclassView(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url, "五级分类详情",function(){});
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div top-title">五级分类</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/借据号"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		filter_dic = [
		      {
                  "optName": "五级分类",
                  "parm": ${fiveStsJsonArray},
                  "optCode":"fiveclass",
                  "dicType":"y_n"
              }, {
                  "optName": "项目名称",
                  "parm": [],
                  "optCode":"appName",
                  "dicType":"val"
              }, {
                  "optName": "客户名称",
                  "parm": [],
                  "optCode":"cusName",
                  "dicType":"val"
              }, {
				"optCode" : "pactSts",
				"optName" : "申请状态",
				"parm" : ${appStsJsonArray},
				"dicType" : "y_n"
			},{
				"optCode" : "classMehod",
				"optName" : "分类方法",
				"parm" : ${classMethodJsonArray},
				"dicType" : "y_n"
			} ];
	</script>
</html>
