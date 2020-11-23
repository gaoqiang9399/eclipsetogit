function dateValidate(datestrArgs,num){
	var datestr = new String(datestrArgs.replace( /^\s*/, "").replace( /\s*$/, ""));
	var len = datestr.indexOf('-');
	if(parseInt(len)!=-1){
	    var ye = datestr.split("-");
		if(ye.length==3&&ye[0].length==4&&ye[1].length==2&&ye[2].length==2){
			for(var i=0;i<datestr.length;i++)datestr = datestr.replace("-","");
		}else{
			alert("日期格式不正确，请重新输入！");
			return false;
		}
	}
	var month,day;
	var flag=datestr.replace(/^\d+$/);
	if(flag!='undefined'){alert("输入的日期非数字！");return;}
	if(datestr.length!=parseInt(num)){alert("输入日期不是"+num+"位！");return;}
	if(datestr.length>=4)var year= datestr.substring(0,4);
	if(datestr.length>=6)month=datestr.substring(4,6);else month=01;
	if(datestr.length>=8)day=datestr.substring(6,8);else day=01;
	
  //tempdate= new String (year+month+day); 
  //alert(tempdate); 
 if ((year.length!=4) || (month.length>2) || (day.length>2)) 
 { 
  alert("不是有效的日期格式!"); 
  return false; 
 } 
 if(year<=1900){
	alert("日期不能小于1900年！");
	return false;
 }
 if (!((1<=month) && (12>=month) && (31>=day) && (1<=day)) ) 
 { 
  alert ("不是有效的月或天!"); 
  return false; 
 } 
 if (!((year % 4)==0) && (month==2) && (day==29)) 
 { 
  alert ("这不是一个瑞年!"); 
  return false; 
 } 
 if ((month<=7) && ((month % 2)==0) && (day>=31)) 
 { 
  alert ("此月没有31号!"); 
  return false; 
 } 
 if ((month<=7) && ((month % 2)==1) && (day>31)) 
 { 
  alert ("日期过限最大31号!"); 
  return false; 
 } 
 if ((month>=8) && ((month % 2)==1) && (day>=31)) 
 { 
  alert ("此月最大30号!"); 
  return false; 
 } 
 if ((month>=8) && ((month % 2)==0) && (day>31)) 
 { 
  alert ("日期过限最大31号!"); 
  return false; 
 } 
 if ((month==2) && (day==30)) 
 { 
  alert("二月没有30号!"); 
  return false; 
 } 
 return true; 
}
function dateVerdict(obj,value,num){
	if(value!=null&&value!=""&&value.length != 8&&num == 8){
		alert("日期格式不正确，请重新输入！")
		obj.value=""
	}
	if(value!=null&&value!=""){
		if(!dateValidate(value,num))obj.value="";
	}
}