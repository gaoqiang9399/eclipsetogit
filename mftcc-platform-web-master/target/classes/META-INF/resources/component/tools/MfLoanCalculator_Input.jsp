<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				$("select[name=repayDateState]").html('<option value="2">贷款发放日</option><option value="1">固定还款日</option>');
				$("select[name=advRepayState]").html('<option value="0">否</option><option value="1">是</option>');
				$("select[name=advRepayType]").html('<option value="0">一次性还清</option><option value="1">部分还款</option>');
				$("select[name=opType]").html('<option value="0">减少月还款额</option>');
				changeRepayDateState($("select[name=repayDateState]")[0]);
				changeRepayType( $("select[name=repayType]")[0]);
				changeAdvRepayType( $("select[name=advRepayType]")[0]);
				changeAdvRepayState( $("select[name=advRepayState]")[0]);
			 });
 			function openList(obj){
				var flag = submitJsMethod($(obj).get(0), '');
				if($("[name=advRepayState]").val()==1 && $("[name=advRepayDate]").val()==""){
			        alert("提前还款时间不能为空!");
			        return;
				}
				if (flag) {
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					$.ajax({
						url : url,
						data : {
							ajaxData : dataParam
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
						    $("#pre_content").hide();
							LoadingAnimate.stop();
							if (data.flag == "success") {						
								var dataStr = "";
								var dataStr1 = "";
								var perdNo = "";
								var lsbl = 0.0;//临时变量
								var ljTotal=0.0;
								var yhlxTotal=0.0;
								var preLxTotal = 0.0;
								var xyRepay=0.0;
								var lx = 0.0;
								var arrStr = data.resultArr;
								var arr = arrStr.split("},{");
								var trHtml1 = "";
								var trHtml = "";
								if($("#tabContent").html().length>0){
									$("#tabContent").html("");
								}
								if($("#preTabContent").html().length>0){
									    $("#preTabContent").html("");
								}
								if($("[name=advRepayState]").val()=="1" && $("[name=advRepayType]").val()=="1"){//提前还款部分还款 
								    $("#pre_content").show();//style.display='block';
								    var arrPreMap = data.mapPreResult;
								    perdNo=arrPreMap.perdNo;
								    lx = arrPreMap.lxTotal;
								    var arrPreStr = arrPreMap.resultArr;
								    var advRepayMoney = $("[name=advRepayMoney]").val();
								    var arr1 = arrPreStr.split("},{");
								    for(var i =0;i<arr1.length;i++){
								        if(arr1.length!=1){
											if(i==0){
												dataStr1 = arr1[i]+"}";
											}else if(i==arr1.length-1){
												dataStr1 = "{"+arr1[i];
											}else{
												dataStr1 = "{"+arr1[i]+"}";
											}
									    }else{
										dataStr1=arr1[i];
									    }
									    var obj = eval('(' + dataStr1 + ')');
									    trHtml1 ='<tr>';
									    var j=0;
									    for(var o in obj){
									        j++;
									        if(j==2 || j==3){
									           obj[o]=obj[o].substring(0,obj[o].length-4)+"-"+obj[o].substring(obj[o].length-4,obj[o].length-2)+"-"+obj[o].substring(obj[o].length-2,obj[o].length);
									        }
									        if(i==0){
									            if(j==4){
									               obj[o]=parseFloat(obj[o])+parseFloat(advRepayMoney);
									               obj[o]= obj[o].toFixed(2);
									               lsbl=obj[o];
									            }
									            if(j==5){
									                obj[o]=arrPreMap.actint;
									                lsbl= parseFloat(lsbl)+parseFloat(obj[o]);
									                preLxTotal=preLxTotal+parseFloat(obj[o]);
									            }
									            if(j==6){
									                obj[o]=lsbl.toFixed(2);
									            }
									        }else{
									            if(j==5){
									                preLxTotal=preLxTotal+parseFloat(obj[o]);
									            }
									        }
									        trHtml1 = trHtml1 + '<td align="center">'+obj[o]+'</td>';
									    }
									    trHtml = trHtml+"</tr>";
									    $("#preTabContent").append(trHtml1);
								    }
								}
								for(var i =0;i<arr.length;i++){
									if(arr.length!=1){
										if(i==0){
											dataStr = arr[i]+"}";
										}else if(i==arr.length-1){
											dataStr = "{"+arr[i];
										}else{
											dataStr = "{"+arr[i]+"}";
										}
									}else{
										dataStr=arr[i];
									}
									var obj = eval('(' + dataStr + ')');
									trHtml ='<tr>';
									var j=0;
									for(var o in obj){
										j++;
										if(j==1){
										    if($("[name=advRepayState]").val()=="1" && $("[name=advRepayType]").val()=="0"){//提前还款一次性还款 
										        var arrMap = data.mapResult;
										        if(parseInt(obj[o])==parseInt(arrMap.perdNo)){
										            $("#changediv").html('<table style ="font-size:14px;">'
										            +'<tr><td>已还利息额：&nbsp;&nbsp;'+yhlxTotal.toFixed(2)+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>已还款总额：&nbsp;&nbsp;'+ ljTotal.toFixed(2)+'元</td></tr>'
										            +'<tr><td>该月还款额：&nbsp;&nbsp;'+arrMap.preRepayMoney+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>下月还款额：&nbsp;&nbsp;'+xyRepay.toFixed(2)+'元</td></tr>'
										            +'<tr><td>节省利息支出：&nbsp;&nbsp;'+(arrMap.lxTotal-yhlxTotal).toFixed(2)+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>新的最后还款期：&nbsp;&nbsp;'+$("[name=advRepayDate]").val()+'</td></tr></table>');
										            return;
										        }
										    }
										    if($("[name=advRepayState]").val()=="1" && $("[name=advRepayType]").val()=="1"){//提前还款部分还款 
										        if(parseInt(obj[o])==parseInt(perdNo)){
										            $("#changediv").html('<table style ="font-size:14px;">'
										            +'<tr><td>已还利息额：&nbsp;&nbsp;'+yhlxTotal.toFixed(2)+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>已还款总额：&nbsp;&nbsp;'+ ljTotal.toFixed(2)+'元</td></tr>'
										            +'<tr><td>节省利息支出：&nbsp;&nbsp;'+(lx-yhlxTotal-preLxTotal).toFixed(2)+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>新的最后还款期：&nbsp;&nbsp;'+$("[name=endDate]").val()+'</td></tr></table>');
										            return;
										        }
										    }
										}
										//不展示第二列 开始日期    不展示第三列到期日期
								        //if(j!=2&&j!=3){
								        if(j==2 || j==3){
								           obj[o]=obj[o].substring(0,obj[o].length-4)+"-"+obj[o].substring(obj[o].length-4,obj[o].length-2)+"-"+obj[o].substring(obj[o].length-2,obj[o].length);
								        }else if(j==5){
								            yhlxTotal=yhlxTotal+parseFloat(obj[o]);
								        }else if(j==6){
								            ljTotal=ljTotal+parseFloat(obj[o]);
								        }
								        
								        	trHtml = trHtml + '<td align="center">'+obj[o]+'</td>';
								        //}
								    }
									trHtml = trHtml+"</tr>";
									$("#tabContent").append(trHtml);
								}
								//页面显示累计还款总额及支付息款 
								$("#changediv").html('');
								$("#changediv").html('<table style ="font-size:14px;">'
				                              +'<tr><td>累计还款总额：&nbsp;&nbsp;'+ljTotal.toFixed(2)+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>支付息款：&nbsp;&nbsp;'+yhlxTotal.toFixed(2)+'元</td></tr></table>');
							} else {
								window.top.alert(data.msg, 0);
							}
						},
						error : function() {
							loadingAnimate.stop();
						}
					});
			}
		};
		function changeDate(){
		   var endDate = "";
		   var loanMon = $("[name=loanMon]").val();
		   var provideDate = $("[name=provideDate]").val();
		   var year = provideDate.substring(0,provideDate.length-6);
		   var month = provideDate.substring(provideDate.length-5,provideDate.length-3);
		   var day = provideDate.substring(provideDate.length-2,provideDate.length);
		   if(loanMon==null || loanMon==""){
		       alert("请选择贷款期限");
		       $("[name=provideDate]").val("");
		   }else{
               $.ajax({
                   url:webPath+"/mfLoanCalculator/getEndDateInfoMapAjax",
                   data:{"beginDate":provideDate,"term":loanMon},
                   type:'post',
                   dataType:'json',
                   success:function(data){
                       if(data.flag == "success"){
                           var endDate=data.endDate;
                           var endDateShow=data.endDateShow;
                           $("[name=endDate]").val(endDate);
                           if($("[name=repayDateState]").val()=="2"){
                               $("[name=startDated]").val($("[name=provideDate]").val());
                           }
                       }else{
                           window.top.alert(data.msg,0);
                       }
                   },error:function(){
                       window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
                   }
               });
		   }
		}


            //改变期限
            function changeTerm() {

                var provideDate = $("[name=provideDate]").val();
                var endDate = "";
                var loanMon = $("[name=loanMon]").val();

                if (provideDate == null || provideDate == "") {


                } else {
                    $.ajax({
                        url: webPath + "/mfLoanCalculator/getEndDateInfoMapAjax",
                        data: {"beginDate": provideDate, "term": loanMon},
                        type: 'post',
                        dataType: 'json',
                        success: function (data) {
                            if (data.flag == "success") {
                                var endDate = data.endDate;
                                var endDateShow = data.endDateShow;
                                $("[name=endDate]").val(endDate);
                                if ($("[name=repayDateState]").val() == "2") {
                                    $("[name=startDated]").val($("[name=provideDate]").val());
                                }
                            } else {
                                window.top.alert(data.msg, 0);
                            }
                        }, error: function () {
                            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
                        }
                    });
                }
            };
		function changeRepayDateState(obj){
		    var $obj = $(obj);
		    var value = $obj.val();
		    var ss="";
		    if(value=="2"){
		        $("[name=regularRepayDate]").parent().parent().parent().hide();
		        $("[name=startDated]").val($("[name=provideDate]").val());
                $("[name=regularRepayDate]").val("");
		    }else{
		        $("[name=regularRepayDate]").parent().parent().parent().show();
		            ss=new Date();
		            if($("[name=regularRepayDate]").val().length==0){
		                ss="";
		            }
		            if($("[name=regularRepayDate]").val().length==1){
		                ss=ss.getFullYear()+"-"+ss.getMonth()+"-0"+$("[name=regularRepayDate]").val();
		            }
		            if($("[name=regularRepayDate]").val().length==2){
		                ss=ss.getFullYear()+"-"+ss.getMonth()+"-"+$("[name=regularRepayDate]").val();
		            }
		        $("[name=startDated]").val(ss);
		    }
		}
		function clickChangeDate(obj){
		    var s=new Date();
		    var $obj = $(obj);
		    var value = $obj.val();
		    if(parseInt(value)<=0||parseInt(value)>31){
		       alert("请输入有效数据!");
		       $("[name=regularRepayDate]").val("");
		       return;
		    }else if(value.length==1){
		        s=s.getFullYear()+"-"+(s.getMonth()+1)+"-0"+$("[name=regularRepayDate]").val();
		    }else{
		       s=s.getFullYear()+"-"+(s.getMonth()+1)+"-"+$("[name=regularRepayDate]").val();
		    }
		    $("[name=startDated]").val(s);
		}
		function changeAdvRepayState(obj){
		    var $obj = $(obj);
		    var value = $obj.val();
		    if(value=="0"){
		        $("[name=advRepayDate]").parent().parent().parent().hide();
		        $("select[name=advRepayType]").parent().parent().parent().hide();
		        $("select[name=opType]").parent().parent().parent().hide();
		    }else{
		        $("[name=advRepayDate]").parent().parent().parent().show();
			    $("select[name=advRepayType]").parent().parent().parent().show();
		        if($("[name=advRepayType]").val()==0){
			        $("select[name=opType]").parent().parent().parent().hide();
		        }else{
		           $("select[name=opType]").parent().parent().parent().show();
		        }
		    }
		}
		function changeAdvRepayType(obj){
		    var $obj = $(obj);
		    var value = $obj.val();
		    if(value=="1"){
		        $("select[name=opType]").parent().parent().parent().show();
		    }else{
		        $("select[name=opType]").parent().parent().parent().hide();
		    }
		}
		//function chackPreDate(){
		//}
		//还款方式该表给规则赋值
		function changeRepayType(obj){
			var $obj = $(obj);
			var value = $obj.val();
			if(value=="1"||value=="2"||value=="3"||value=="8"){//等额本息、等额本金、利随本清、按期还息隐藏计息类型
				$("select[name=intstType]").parent().parent().parent().hide();		
			}
			if(value=="7"){//等本等息
				$("select[name=intstType]").parent().parent().parent().show();	
			}
		}
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="loancalculator" theme="simple" name="operform" action="${webPath}/mfLoanCalculator/calculateAjax">
						<dhcc:bootstarpTag property="formloancalculator00001" mode="query" />						
						<div class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">
							<button type="button" class="btn btn-default" value="计算"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="openList('#loancalculator');">计算</button>
						</div>
						</form>	
				<div>
			</div>
			<div class="table_content" style="width:100%;margin-left:-15px;">
			<div id="changediv" style="position: relative;margin-bottom:10px;margin-top:20px;" align="center">
				<table style ="font-size:14px;">
				  <tr><td>累计还款总额：&nbsp;&nbsp;元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>支付息款：&nbsp;&nbsp;元</td></tr>
				</table>
		    </div>
		    </div>
				<div id="content" class="table_content" style="width:100%;margin-left:-15px;">
				<div style="position: relative;margin-bottom:10px;color: #7c7c7c;font-size:14px;" align="left">还款计划</div>
						<table id="tablist" class="ls_list" style="" title="tablecusandapply0001" width="100%" cellspacing="1" border="0" align="center">
							<thead>
								<tr>
									<th scope="col" name="num" align="center">期号</th>
									<th scope="col" name="planBeginDate" align="center">开始时间</th>
									<th scope="col" name="perdEndDate" align="center">结束时间</th>
									<th scope="col" name="prcpAmt" align="center">每期本金(元)</th>
									<th scope="col" name="normInt" align="center">每期利息(元)</th>
									<th scope="col" name="perdIntstAmt" align="center">期供金额(元)</th>
									<th scope="col" name="loanBal" align="center">剩余本金(元)</th>
								</tr>
							</thead>
							<tbody id="tabContent">
							</tbody>
						</table>
				</div>
				<div id="pre_content" style="display:none;">
				<div class="table_content" style="width:100%;margin-left:-15px;">
				<div style="position: relative;margin-bottom:10px;color: #7c7c7c;font-size:14px;" align="left">提前还款计划</div>
						<table id="tablist" class="ls_list" style="" title="tablecusandapply0001" width="100%" cellspacing="1" border="0" align="center">
							<thead>
								<tr>
									<th scope="col" name="num" align="center">期号</th>
									<th scope="col" name="planBeginDate" align="center">开始时间</th>
									<th scope="col" name="perdEndDate" align="center">结束时间</th>
									<th scope="col" name="prcpAmt" align="center">每期本金(元)</th>
									<th scope="col" name="normInt" align="center">每期利息(元)</th>
									<th scope="col" name="perdIntstAmt" align="center">期供金额(元)</th>
									<th scope="col" name="loanBal" align="center">剩余本金(元)</th>
								</tr>
							</thead>
							<tbody id="preTabContent">
							</tbody>
						</table>
				</div>
				</div>
			</div>			
		</div>
	</div>

</body>
</html>
