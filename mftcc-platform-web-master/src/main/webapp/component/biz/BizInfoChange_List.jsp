<%@ page language="java" import="java.util.*,app.component.biz.entity.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
		.line{
			border-bottom: 2px solid #ddd;
			width: 10px;
			display: block;
			margin-left: -12px;
		}
		tr{
			height: 30px;
			vertical-align: middle;
		}
		td{
			padding-right: 10px;
			vertical-align: middle;
		}
		.emBig{
			width:12px;background-color:#32b5cb;height:12px;display:block;border-radius:20px;position:absolute;left:-6px;top:9px; cursor:pointer;
		}
		.emSmall{
			width:8px;background-color:b2c2c6;height:8px;display:block;border-radius:20px;position:absolute;left:-4px;top:12px; cursor:pointer;
		}
		.title{
			padding-top: 10px;
			padding-left: 20px;
			display: block;
		}
		
		.left{
			width: 15%;
			float: left;
			font-size: 12px;
			padding-left: 50px;
		}
		.middle{
			width: 50%;
			float: left;
			font-size: 12px;
		}
		.right{
			width: 35%;
			float: left;
			font-size: 12px;
		}
		
		.navYear{
			text-align: right;font-size: 14px;font-weight: bold;cursor: pointer;color: #32b5cb;
		}
		.navYear:hover{
			text-decoration: underline;
		}
		.navMonth{
			text-align: right;color: #32b5cb;cursor: pointer;
		}
		.navMonth:HOVER{
			text-decoration: underline;
		}
		
		.sub{
			width: 300px;
			margin-top: 15px;
			color: #666666;
			line-height: 26px;	
		}
		
		.cont{
			cursor: pointer;
		}
		
		.circle{
			width: 4px;
			height: 4px;
			background-color: #666666;
			border-radius:2px;
		}
		
		.lineTd{
			border-left:2px solid #ddd;
		}
		.i-jingbao{color:#ff0201; font-size:14px;}
		.i-zhuxiao{color:#be9763; font-size:16px;}
		.i-zizhukaitongmoniqiquanzhanghu01{color:#41c151; font-size:14px;}
		.i-wenjian3{color:#A39F8E; font-size:14px;}
		.i-dian{color:#A39F8E; font-size:8px;}
		
		a{cursor: pointer;}
		
		.rightCont{
			display: block;
			float: left;
		}
		</style>
		
	</head>
	<% 
		List<BizInfoChange> yearList = (List)request.getAttribute("yearList");
		List<BizInfoChangeSub> bizInfoChangeSubList = (List)request.getAttribute("bizInfoChangeSubList");
	
	%>	
<body   class="contentA cellBoxBg " style="width: 100%;height: 100%;overflow: hidden;padding: 0px;background:#fff;">
	<div style="border:none !important;height: 100%;width: 100%;background:#fff !important;" class="cellBg">
	<h3 style="margin-left:10px;">信息变更</h3>
	<div id="left" class="left">
		<table style="border-collapse: separate;border-spacing: 0px 0px;width: 100%">
		<% for(BizInfoChange yearChange:yearList){%>
			<tr>
				<td class="navYear" width="50%">
					<span class="navDate" navDate="<%= yearChange.getDate()%>" onclick="changeDate(this,'<%= yearChange.getDate()%>')"> <%= yearChange.getDate()+"年"%></span>
				</td>
				<td  class="lineTd" navDate="<%= yearChange.getDate()%>">
					<span class="line"></span>
				</td>
			</tr>	
			<%for(BizInfoChange monthChange:yearChange.getSubList()){%>
			<tr >
				<td  class="navMonth">
					<span  class="navDate" navDate="<%= monthChange.getDate()%>" onclick="changeDate(this,'<%=monthChange.getDate()%>')"> <%=monthChange.getDate().substring(4,6)+"月"%></span>
				</td>
				<td class="lineTd" navDate="<%= monthChange.getDate()%>">
					<span class="line"></span>
				</td>
			</tr >
			<%}
		}%>
		</table>
	</div>
	
	<div id="middle" class="middle">
		<table style="border-collapse: separate;border-spacing: 0px 0px;width: 100%">
		<% for(BizInfoChange yearChange:yearList){%>
			<tr date="<%= yearChange.getDate()%>">
				<td style="text-align: right;font-size: 14px;font-weight: bold;" width="20%"><%= yearChange.getDate()+"年"%></td>
				<td style="border-left:1px solid #ddd;position:relative">
					<em class="emBig"></em>
				</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>	
			<%for(BizInfoChange monthChange:yearChange.getSubList()){%>
			<tr date="<%=monthChange.getDate()%>">
				<td  style="text-align: right;color: #A7B5B8" width="20%"><%=monthChange.getDate().substring(4,6)+"月"%></td>
				<td style="border-left:1px solid #ddd;position:relative">
					<em class="emSmall"></em>
				</td>
				<td style="color: #A7B5B8;width: 18%;"><%=monthChange.getCont().split("@")[0].substring(4,6)+"/"+monthChange.getCont().split("@")[0].substring(6,8)%>
					<%=monthChange.getCont().split("@")[4]%>
				</td>
				<td>
					<%if("1".equals(monthChange.getCont().split("@")[3])){%>
						<img src="${webPath}/component/biz/imgs/kaihu.png"></img>
					<%}else if("2".equals(monthChange.getCont().split("@")[3])){%>
						<img src="${webPath}/component/biz/imgs/yujing.png"></img>
					<%}else if("3".equals(monthChange.getCont().split("@")[3])){%>
						<i class="i i-wenjian3" style="color:#898989;font-size:14px;line-height:30px;"></i>
					<%}else if("4".equals(monthChange.getCont().split("@")[3])){%>
						<img src="${webPath}/component/biz/imgs/zhuxiao.png"></img>
					<%}%>
				</td>
				<td width="80%"><span class="cont" onclick="getSub(this,'<%=monthChange.getCont().split("@")[2]%>')"> <%=monthChange.getCont().split("@")[1]%></span> </td>
			</tr >
				<%for(BizInfoChange dayChange:monthChange.getSubList()){%>
					<tr >
						<td></td>
						<td style="border-left:1px solid #ddd;position:relative">
							<%if(null != dayChange.getDate() &&  !"".equals(dayChange.getDate())){%>
								<em class="emSmall"></em>
							<%} %>
						</td>
						</td>
						<td style="color: #A7B5B8;">
							<%if(null != dayChange.getDate() &&  !"".equals(dayChange.getDate())){%>
								<%= dayChange.getDate().substring(4,6)+"/"+dayChange.getDate().substring(6,8) %> <%=dayChange.getTime()%>
							<%} else {%>
									<span style="padding-left:36px;"><%=dayChange.getTime()%></span>
							<%}%>
						</td>
						<td>
							<%if("1".equals(dayChange.getChangeType())){%>
								<img src="${webPath}/component/biz/imgs/kaihu.png"></img>
							<%}else if("2".equals(dayChange.getChangeType())){%>
								<img src="${webPath}/component/biz/imgs/yujing.png"></img>
							<%}else if("3".equals(dayChange.getChangeType())){%>
								<i class="i i-wenjian3" style="color:#898989;font-size:14px;line-height:30px;"></i>
							<%}else if("4".equals(dayChange.getChangeType())){%>
								<img src="${webPath}/component/biz/imgs/zhuxiao.png"></img>
							<%}%>
						</td>
						<td width="80%"><span class="cont" onclick="getSub(this,'<%=dayChange.getChangeNo()%>')"> <%= dayChange.getCont()%></span></td>
					</tr>
				<%} %>
			<%}
		}%>
		</table>
	</div>
	
	<div id="right" class="right" >
		<%if(null != bizInfoChangeSubList && bizInfoChangeSubList.size() != 0)
			{for(BizInfoChangeSub sub:bizInfoChangeSubList) {%>
			<div class="sub" changeNo="<%=sub.getChangeNo()%>">
				<span class="i i-dian"></span>
				<span style="rightCont"> <%=sub.getCont()%></span> 
			</div>
			<%}
		}%>
	</div>
	</div>
</body>
	<script type="text/javascript" >
			var dates = '${dateStr}'.split('@');
			var dateLength = dates.length;
			var num = 0;
			var scrollFunc=function(e){ 
			    ee=e || window.event; 
			   	var value = '';
			    if(e.wheelDelta){//IE/Opera/Chrome 
			        value=e.wheelDelta; 
			    }else if(e.detail){//Firefox 
			        value=e.detail; 
			    } 
			    if(value > 0){//向下滚
			    	if((num+1) < dateLength){
			    		num++;
			    		$('#right').empty();
			    		$('.navDate').css('text-decoration','none');
			    		$('.lineTd').css('border-left-color','#ddd');
			    		
			    		$('.navDate[navDate="'+dates[num]+'"]').css('text-decoration','underline');
			    		$('.lineTd[navDate="'+dates[num]+'"]').css('border-left-color','#32b5cb');
			    		
			    		$('tr[date="'+dates[num]+'"]').prev().prevAll().slideUp(100);
						$('tr[date="'+dates[num]+'"]').nextAll().slideDown(100);
						$('tr[date="'+dates[num]+'"]').prev().slideDown(100);
						$('tr[date="'+dates[num]+'"]').slideDown(100);
						
						$('.cont').css('color','black');
			    	}
			    }else{//向上滚
			    	if(num >0){
			    		num--;
			    		$('#right').empty();
			    		$('.navDate').css('text-decoration','none');
			    		$('.lineTd').css('border-left-color','#ddd');
			    		
			    		$('.navDate[navDate="'+dates[num]+'"]').css('text-decoration','underline');
			    		$('.lineTd[navDate="'+dates[num]+'"]').css('border-left-color','#32b5cb');
			    		
			    		$('tr[date="'+dates[num]+'"]').prev().prevAll().slideUp(100);
						$('tr[date="'+dates[num]+'"]').nextAll().slideDown(100);
						$('tr[date="'+dates[num]+'"]').prev().slideDown(100);
						$('tr[date="'+dates[num]+'"]').slideDown(100);
						
						$('.cont').css('color','black');
			    	}
			    }
			} 
			/*注册事件*/ 
			if(document.addEventListener){ 
			    document.addEventListener('DOMMouseScroll',scrollFunc,false); 
			}//W3C 
			window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome 
			function changeDate(obj,date){
				$("#middle").mCustomScrollbar("scrollTo",'tr[date="'+date+'"]');
				/* $('tr[date="'+date+'"]').prev().prevAll().slideUp(100);
				$('tr[date="'+date+'"]').nextAll().slideDown(100);
				$('tr[date="'+date+'"]').prev().slideDown();
				$('tr[date="'+date+'"]').slideDown(); */
				$('.navDate').css('text-decoration','none');
				$(obj).css('text-decoration','underline');
				$('.lineTd').css('border-left-color','#ddd');
				$(obj).parent('td').next('td').css('border-left-color','#32b5cb');
				
				$('#right').empty();
				$('.cont').css('color','black');
				
				for(var i = 0;i<dates.length;i++){
					if(dates[i] == date){
						num = i;
						break;
					}
				}
			}
			function getSub(obj,changeNo){
				jQuery.ajax({
					url:webPath+"/bizInfoChangeSub/getListAjax",
					data:{changeNo:changeNo},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							var list = data.bizInfoChangeSubList;
							var html = '';
							$.each(list,function(n,obj){
								html += 
								'<div class="sub" changeNo="'+obj.changeNo+'">'+
									'<span class="i i-dian"></span>'+
									'<span style="rightCont">'+obj.cont+'</span>'+
								'</div>';
							});
							$('#right').empty();
							$('#right').append(html);
							$('.cont').css('color','black');
							$(obj).css('color','#32b5cb');
							
							$('.i-dian').each(function(){
								$(this).height($(this).parent('.sub').height()+5);
							})
						}else{
							 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					},error:function(data){
						 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
			$('.navDate:first').css('text-decoration','underline');
			$('.lineTd:first').css('border-left-color','#32b5cb');
			
			$('.i-dian').each(function(){
				$(this).height($(this).parent('.sub').height());
			})
			
			$(function(){
				$("#middle").mCustomScrollbar({
					axis:"y",
					theme:"minimal-dark",
					advanced:{ 
						updateOnBrowserResize:true 
					},autoHideScrollbar: true
				});
				$("#middle").height($(window).height()-100);
			});
		</script>