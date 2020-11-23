<%@page import="app.component.common.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.divlist #tab_1 td {
	text-align: center;
}
</style>
<title>会计周期设置</title>
<script type="text/javascript">
	var cw_show_year=${cw_show_year};
	var cw_show_month=${cw_show_month};
	var cc=0;
	/* //点击保存
	function saveonclick(){
		if(cc!=0){
			alert('申请已经提交，请不要反复提交。',1);
			return false;
		}
		cc++;
		var dataParam = JSON.stringify($('#myform_01').serializeArray()); 
		$.ajax({	
			url:'${webPath}/cwCycleHst/insertCwcycle',
			type:'post',
			data:{ajaxData:dataParam},
			async:false,
			dataType:'json',
			error:function(){
				alert("error");
			},
			success:function(data){
				if(data=='0000'){
					if('${param.state}' == '1'){
						window.parent.location.reload();
					}else{
						window.location.reload();
					}
				}else if(data=='1111'){
					cc--;
					alert('0','保存失败。','系统提示');
					return false;
				}else if(data=='0001'){
					cc--;
					alert('2','该会计期间已经被使用，不允许再更改。','系统提示');
					return false;
				}else if(data=='0002'){
					cc--;
					alert('2','日期设置有误，请核对后再次输入。','系统提示');
					return false;
				}else if(data!='0002'){
					cc--;
					var da = data.split("@");
					if(da[0]=='A0020'){
						var a = "期间【"+da[1]+"】的结束日期";
						var parm ={'id':'A0020','parm0':a,'parm1':'开始日期'};
						alert('2',parm,'系统提示');
						return false;
					}
				}else{
					cc--;
					alert('2',data,'系统提示');
					return false;
				}
		
			}
		})
		cc++;
	}
	 */
	
	//获取自然年会计 期间 		 	
	function rdos(val){
		var dataParam = JSON.stringify($('#myform_01').serializeArray()); 
		alert(dataParam)
		if(val=='Y'){// 自然年度
			$.ajax({	
				url:webPath+'/cwCycleHst/getPeriod',
				type:'post',
				data:{ajaxData:dataParam},
				async:false,
				dataType:'json',
				error:function(){
					alert("error");
				},
				success:function(data){
					for(var i=1;i<=data.list.length;i++){
						$('#s_'+i).val(data.list[i-1][0]);
						$('#e_'+i).val(data.list[i-1][1]);
					}
					$('input[type=text][id^=e]').each(function(){
						$(this).attr("disabled",'disabled');
					});
				}
			});
		}
		if(val=='N'){// 非自然年度
			$.ajax({	
				url:webPath+'/cwCycleHst/getPeriodByYear',
				type:'post',
				data:{ajaxData:dataParam},
				async:false,
				dataType:'json',
				error:function(){ 
					alert("error");
				},
				success:function(data){	
				    alert(data.list.length)			
					$('input[type=text][id^=e]:gt('+(cw_show_month)+')').each(function(){
						$(this).removeAttr("disabled");
					});
					$('input[type=text][id^=e_12]').attr("disabled","disabled");
				}
			});
		}
	}

	function my_back(){ 
		history.back();
	}
	//点击获得  起始和结束期间 
	function onblurck1(val,val1){
		var v=$('#'+val).val(); 
		//var dataParam = JSON.stringify($('#myform_01').serializeArray()); 
		 if(v==''||v==null){
			var parm ={'id':'A0015','parm0':'结束日期'};
            alert('2',parm,'系统提示');
            return ;
		} 
		if(test(val)){
		alert(456);
			$.ajax({	
				url:webPath+'/cwCycleHst/getPeriodByNum',
				type:'post',
				data:{ajaxData:dataParam},
				async:false,
				dataType:'json',
				error:function(){
					alert("error");
				},
				success:function(data){
					console.log(data);
					for(var i=1;i<=data.list.length;i++){
						$('#s_'+i).val(data.list[i-1][0]);
						$('#e_'+i).val(data.list[i-1][1]);
					}
				}
			})
		}else{
			var parm ={'id':'A0013','parm0':'正确的日期格式：YYYY-MM-DD'};
			alert('2',parm,'系统提示',function(dt){
				if(dt){
					$('#'+val).focus();
				}
			});
			return false;
		}	
	}
	//检查日期格式 	
	function checkDate(str)
    {
        try
        {
            if(!/\-/.test(str)||str.indexOf("/")>-1)
            {
            	//var parm ={'id':'A0013','parm0':'正确的日期格式：YYYY-MM-DD。'};
            	//alert('2',parm,'系统提示');
            	//alert('2','请输入正确的日期格式：YYYY-MM-DD。','系统提示');
                return false;
            }
            
            str = str.replace(/\-/g,"/");//必须要用正则来替换，否则只替换第一个出现的字符串
            var tmp = str.split("/");
            if(tmp.length > 3)
            {
            	
                return false;
            }
            
            var tempDate = new Date(str);
            
            var year = tempDate.getYear();
            var month = tempDate.getMonth() + 1;
            var day = tempDate.getDate();
            //判断中间不允许有空格
            if(/\s/.test(tmp[0]) || /\s/.test(tmp[1]) || /\s/.test(tmp[2]))
            {
            	alert('2','中间不允许出现空格。请输入正确的日期格式：YYYY-MM-DD。','系统提示');
                return ;
            }
            
            //判断年、月、日位数,可以根据自己需求修改
            if(tmp[0].length < 3 || tmp[0].length > 4)
            {
            	alert('2','年份输入错误。请输入正确的日期格式：YYYY-MM-DD。','系统提示');
                return ;
            }
            
            if(tmp[1].length > 2||tmp[1].length<2)
            {
            	alert('2','月份输入错误。请输入正确的日期格式：YYYY-MM-DD。','系统提示');
                return ;
            }
            
            if(tmp[2].length > 2||tmp[2].length<2)
            {
            	alert('2','天数输入错误。请输入正确的日期格式：YYYY-MM-DD。','系统提示');
                return ;
            }
            //判断年、月、日位数,可以根据自己需求修改
            
            if(tempDate != null)
            {    
                return year == tmp[0] && month == tmp[1] && day == tmp[2];            
            }else
            {
                return false;
            }
        }catch(ex){
            //alert(ex.message);
            return false;
        }
        return true;
    }
    function test(val){
        var str = $('#'+val).val();
        if(checkDate(str)){
            //alert("输入正确！");
            return true;
        }else{
        	//alert("请输入正确的日期！");
            return false;
        }
    }
    //点击保存
    function saveData(){
    	var dataParam = JSON.stringify($('#myform_01').serializeArray()); 
    	alert(dataParam)
    	$.ajax({
    		url:webPath+'/cwCycleHst/insertCwcycle',
    		data:'ajaxData='+dataParam,
    		dataType:'json',
    		type:'post',
    		success:function(data){
    			alert(0)
    		}
    	})
    }
	</script>
</head>
<body>
	<div>
		<div style="vertical-align: bottom; display: block;" class="tabCont">
			<strong>会计周期管理</strong>
		</div>
	</div>

	<!--页面显示区域-->
	<div id="content" class="table_content" style="height: auto;"></div>
	<form id="myform_01">
		<div name="emp_list1" id="emp_list1" value="1" class="divlist">
			<table>
				<tr>
					<td style="width: 80px;">
					启用会计期间 ： <input
						type="text" class="form-control form-warp" readonly name="Week"
						id="Week" autocomplete="off" onclick="laydatemonth(this);"
						onkeydown="enterKey();"></td>
					<td> <input type="radio" id="rdo1" checked="checked"
						onclick="rdos('Y')" name="rdo" value="Y"/>自然年度会计期间
					</td>
					<td> <input type="radio" id="rdo2"
						onclick="rdos('N')" name="rdo" value="N"/>会计期间期数（12）
					</td>
				</tr>
			</table>
			<table id="tab_1">
				<tr>
					<th>期间</th>
					<th>起始日期
						<DIV class="boder-common"></DIV>
					</th>
					<th>结束日期
						<DIV class="boder-common"></DIV>
					</th>
				</tr>
				<tr>
					<td>01</td>
					<td><input type="text" disabled="disabled" id="s_1" name="s_1"
						value="" /></td>
					<td><input type="text" id="e_1" name="e_1" disabled
						onblur="javascript:onblurck('e_1','1');" /></td>
				</tr>
				<tr>
					<td>02</td>
					<td><input type="text" disabled="disabled" id="s_2" name="s_2" /></td>
					<td><input type="text" id="e_2" name="e_2" disabled
						onblur="javascript:onblurck('e_2','2');" /></td>
				</tr>
				<tr>
					<td>03</td>
					<td><input type="text" disabled="disabled" id="s_3" name="s_3" /></td>
					<td><input type="text" id="e_3" name="e_3" disabled
						onblur="javascript:onblurck('e_3','3');" /></td>
				</tr>
				<tr>
					<td>04</td>
					<td><input type="text" disabled="disabled" id="s_4" name="s_4" /></td>
					<td><input type="text" id="e_4" name="e_4" disabled
						onblur="javascript:onblurck('e_4','4');" /></td>
				</tr>
				<tr>
					<td>05</td>
					<td><input type="text" disabled="disabled" id="s_5" name="s_5" /></td>
					<td><input type="text" id="e_5" name="e_5" disabled
						onblur="javascript:onblurck('e_5','5');" /></td>
				</tr>
				<tr>
					<td>06</td>
					<td><input type="text" disabled="disabled" id="s_6" name="s_6" /></td>
					<td><input type="text" id="e_6" name="e_6" disabled
						onblur="javascript:onblurck('e_6','6');" /></td>
				</tr>
				<tr>
					<td>07</td>
					<td><input type="text" disabled="disabled" id="s_7" name="s_7" /></td>
					<td><input type="text" id="e_7" name="e_7" disabled
						onblur="javascript:onblurck('e_7','7');" /></td>
				</tr>
				<tr>
					<td>08</td>
					<td><input type="text" disabled="disabled" id="s_8" name="s_8" /></td>
					<td><input type="text" id="e_8" name="e_8" disabled
						onblur="javascript:onblurck('e_8','8');" /></td>
				</tr>
				<tr>
					<td>09</td>
					<td><input type="text" disabled="disabled" id="s_9" name="s_9" /></td>
					<td><input type="text" id="e_9" name="e_9" disabled
						onblur="javascript:onblurck('e_9','9');" /></td>
				</tr>
				<tr>
					<td>10</td>
					<td><input type="text" disabled="disabled" id="s_10"
						name="s_10" /></td>
					<td><input type="text" id="e_10" name="e_10" disabled
						onblur="javascript:onblurck('e_10','10');" /></td>
				</tr>
				<tr>
					<td>11</td>
					<td><input type="text" disabled="disabled" id="s_11"
						name="s_11" /></td>
					<td><input type="text" id="e_11" name="e_11" disabled
						onblur="javascript:onblurck('e_11','11');" /></td>
				</tr>
				<tr>
					<td>12</td>
					<td><input type="text" disabled="disabled" id="s_12"
						name="s_12" value=""/></td>
					<td><input type="text" disabled="disabled" id="e_12"
						name="e_12" value="" /></td>
					<td style="display:none"><input type="text" id="years"
						name="years" value="2017" /></td>					<!--年份要根据最打结账日期所在年份去确定-->
				</tr>
			</table>
		</div>
		<table>
			<tr>
				<td><div class="btn-div">
				<button type="button" class="btn btn-info" onclick="saveData()">保存</button>
				</td>
			</tr>
		</table>
	</form>
	<a href="${webPath}/cwCycleHst/insertZiRanCwcycle">批量插入二十年自然年</a>
</body>
<script type="text/javascript">
	$(function (){
	alert("${cw_show_month}");
	alert("${cw_show_year}");
			rdos('Y');
		});
</script>
</html>
