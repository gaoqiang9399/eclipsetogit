<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String query =  (String)request.getAttribute("query");
	Object dataMap = request.getAttribute("dataMap");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
		 	var dataMap = <%=dataMap%>;
		 	var query = "<%=query%>";
			function initTbody(data){
				var $table = $("table.ls_list_a");
				var thLength = $table.find("thead th").length;
				$table.find("tbody").html("");
				$.each(data, function(index,entityObj) {
					var $tr = $("<tr></tr>");
					var level = entityObj.level;
					var upIndexNo = entityObj.upIndexNo;
					$tr.data("entityObj",entityObj);
					if(level==1){
						$tr.append('<td class="font_weight border_left" rowspan="1">'+entityObj.indexName+'</td><td class="border_right"></td>');
						$table.find("tbody").append($tr);
					}else{
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
									var $radioInput = $('<input name="'+thisIndexNo+'" type="radio" value="'+entityObj.stdCore+'">');
									if(query=="query"){
										$radioInput.attr("readonly","readonly");
									}
									if($ValTd.find("table").length>0){
										$ValTd.find("table tbody").append('<tr><td>'+$radioInput.prop('outerHTML')+entityObj.indexName+'</td></tr>');
									}else{
										var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 5px 0px"><tbody><tr><td></td></tr></tbody></table>');
										$radioInput.attr("checked","checked");
										$thisTable.find("tr td").append($radioInput.prop('outerHTML')+entityObj.indexName);
										$ValTd.append($thisTable);
									}
								}
							}
						});
					}
				});
			};
			function intiVal(dxData){
				if(dxData==null||dxData===undefined||dxData==""){
					return false;
				}
				//判断定性存不存在 初始化数据
				if($(".content_ul li div[name=dx]").length>0){
					$(".content_ul li div[name=dx]").find(".li_title .scoreShow .score").text(dxData.score);
					var dxList = dxData.scoreList;
					for(var i=0;i<dxList.split("@").length-1;i++){
						var dxobj = dxList.split("@")[i];
						var name = dxobj.split(":")[0];
						var vuale = dxobj.split(":")[1];
						$(".content_ul li div[name=dx]").find("input[type=radio][name="+name+"]").each(function(index){
							if(vuale==$(this).attr("value")){
								$(this).prop("checked",true);
								return false;
							}
						});
					}
				}
			}
			function evalDxUpdate(obj){
				var url = '${webPath}/evalScenceDxVal/insertOrUpdateProjectEvalAjax';
				var $obj = $(obj);
				var evalScenceNo = dataMap.scenceNo;
				var evalAppNo = dataMap.evalAppNo;
				var dataParam = JSON.stringify($obj.serializeJSON());
				$.ajax({
					type:"post",
					url:url,
					async:false,
					data:{ajaxData:dataParam,evalScenceNo:evalScenceNo,evalAppNo:evalAppNo},
					success:function(data){
						if(data.flag == "success"){
							alert(data.msg,1);
							$(".content_ul li div[name=dx]").find(".li_title .scoreShow .score").text(data.formData.score);
						}else if(data.flag == "error"){
							alert(data.msg,0);
						}
					},error:function(){
						alert(  top.getMessage("FAILED_SAVE"),0);
					}
				});
				
			}
			$(function(){
				$(".li_content").height($("body").height()-50);
				if(dataMap!=null&&dataMap!=""&&dataMap!==undefined){
					initTbody(dataMap.DX);
					intiVal(dataMap.dxData);
				}
				$(".li_content").mCustomScrollbar({
					axis:"y",
					advanced:{ 
						updateOnBrowserResize:true 
					},autoHideScrollbar: true
				});
				$(window).resize(function() {
				$(".li_content").height($("body").height()-50);
				$(".li_content").mCustomScrollbar("update");
			});
			});
		 </script>
		<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
		<link id="appEvalInfo" type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css"/>
		<style type="text/css">
			.content_show .content_ul li {
			    height: 100%;
			    width: 100%;
			}
			.content .content_ul {
			    display: inline-flex;
			    height: 100%;
			    position: relative;
			    width: 100%;
			}
			.li_content .ls_list_a tbody tr td.border_left {
			    font-size: 12px;
			}
			.score {
			    margin-right: 4px;
			}
			.mCustomScrollBox.mCS-light{
				width:100%;
			}
		</style>
	</head>
	<body class="body_bg">
		<div class="content" style="">
			<div class="content_show">
				<ul class="content_ul">
					<li>
						<div name = "dx" class="li_content_type" style="width: 100%; margin: 2px 0;"> 
							<div class="li_title">
								<!--<span>定性得分</span>-->
								<span class="scoreShow">
									<span class="score">
									</span>分
								</span>
							</div>
							<div class="li_content">
								<form style="margin-right: 15px;">
									<table class="ls_list_a" style="width:100%">
										<thead>
											<tr>
											 	<th style="width: 20%;" name="indexName">指标名称</th>
											 	<th name="ctrl_btn">打分选项</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>
									<c:if test="${query!='query'}">
										<div class="from_btn">
											<dhcc:thirdButton value="保存" action="AppProjectEavl001" onclick="evalDxUpdate(this.form)"></dhcc:thirdButton>
							    		</div>
							    	</c:if>
								</form>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>