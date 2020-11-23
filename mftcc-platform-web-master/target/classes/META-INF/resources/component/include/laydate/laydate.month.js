;!function(win){

//全局配置，如果采用默认均不需要改动
var config =  {
    path: '', //laydate所在路径
    defSkin: 'molv', //初始化皮肤
    format: 'YYYYMM', //日期格式
    min: '1900-01-01 00:00:00', //最小日期
    max: '2099-12-31 23:59:59', //最大日期
    isv: false,
    init: true
};

var DatesMonth = {}, doc = document, creat = 'createElement', byid = 'getElementById', tags = 'getElementsByTagName';
var as = ['laydatemonth_box', 'laydatemonth_void', 'laydatemonth_click', 'LayDateSkin', 'skins/', '/laydate.css'];


//主接口
win.laydatemonth = function(dom){
    var options = {};
    try{
        as.event = win.event ? win.event : laydatemonth.caller.arguments[0];
    } catch(e){};
    DatesMonth.run(options,dom);
    return laydatemonth;
};

laydatemonth.v = '1.1';

//获取组件存放路径
DatesMonth.getPath = (function(){
    var js = document.scripts, jsPath = js[js.length - 1].src;
    return config.path ? config.path : jsPath.substring(0, jsPath.lastIndexOf("/") + 1);
}());

DatesMonth.use = function(lib, id){
    var link = doc[creat]('link');
    link.type = 'text/css';
    link.rel = 'stylesheet';
    link.href = DatesMonth.getPath + lib + as[5];
    id && (link.id = id);
    doc[tags]('head')[0].appendChild(link);
    link = null;
};

DatesMonth.trim = function(str){
    str = str || '';
    return str.replace(/^\s|\s$/g, '').replace(/\s+/g, ' ');
};

//补齐数位
DatesMonth.digit = function(num){
    return num < 10 ? '0' + (num|0) : num;
};

DatesMonth.stopmp = function(e){
    e = e || win.event;
    e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
    return this;
};

DatesMonth.each = function(arr, fn){
    var i = 0, len = arr.length;
    for(; i < len; i++){
        if(fn(i, arr[i]) === false){
            break
        }
    }
};

DatesMonth.hasClass = function(elem, cls){
    elem = elem || {};
    return new RegExp('\\b' + cls +'\\b').test(elem.className);
};

DatesMonth.addClass = function(elem, cls){
    elem = elem || {};
    DatesMonth.hasClass(elem, cls) || (elem.className += ' ' + cls);
    elem.className = DatesMonth.trim(elem.className);
    return this;
};

DatesMonth.removeClass = function(elem, cls) {
    elem = elem || {};
    if (DatesMonth.hasClass(elem, cls)) {
        var reg = new RegExp('\\b' + cls +'\\b');
        elem.className = elem.className.replace(reg, '');
    }
    return this;
};

//清除css属性
DatesMonth.removeCssAttr = function(elem, attr){
    var s = elem.style;
    if(s.removeProperty){
        s.removeProperty(attr);
    } else {
        s.removeAttribute(attr);
    }
};

//显示隐藏
DatesMonth.shde = function(elem, type){
    elem.style.display = type ? 'none' : 'block';
};

//简易选择器
DatesMonth.query = function(nodeArgs){
    if(nodeArgs && nodeArgs.nodeType === 1){
        if(nodeArgs.tagName.toLowerCase() !== 'input'){
            throw new Error('选择器elem错误');
        }
        return nodeArgs;
    }

    var node = (DatesMonth.trim(nodeArgs)).split(' '), elemId = doc[byid](node[0].substr(1)), arr;
    if(!elemId){
        return;
    } else if(!node[1]){
        return elemId;
    } else if(/^\./.test(node[1])){
        var find, child = node[1].substr(1), exp = new RegExp('\\b' + child +'\\b');
        arr = []
        find = doc.getElementsByClassName ? elemId.getElementsByClassName(child) : elemId[tags]('*');
        DatesMonth.each(find, function(ii, that){
            exp.test(that.className) && arr.push(that); 
        });
        return arr[0] ? arr : '';
    } else {
        arr = elemId[tags](node[1]);
        return arr[0] ? elemId[tags](node[1]) : '';
    }
};

//事件监听器
DatesMonth.on = function(elem, even, fn){
    elem.attachEvent ? elem.attachEvent('on'+ even, function(){
        fn.call(elem, win.even);
    }) : elem.addEventListener(even, fn, false);
    return DatesMonth;
};

//阻断mouseup
DatesMonth.stopMosup = function(evt, elem){
    if(evt !== 'mouseup'){
        DatesMonth.on(elem, 'mouseup', function(ev){
            DatesMonth.stopmp(ev);
        });
    }
};

DatesMonth.run = function(options,dom){
    var S = DatesMonth.query, month, year, elem, devt, even = as.event, target;
    try {
        target = even.target || even.srcElement || {};
    } catch(e){
        target = {};
    }
    elem = options.elem ? S(options.elem) : target;

    //as.elemv = /textarea|input/.test(elem.tagName.toLocaleLowerCase()) ? 'value' : 'innerHTML';
    //if (config.init) elem[as.elemv] = laydatemonth.now(null, options.format || config.format)
	as.elemv = "value";
	
	if(typeof(dom.value)!="undefined"&&dom.value!=""){
		year = parseInt(dom.value.substr(0,4));
		month = parseInt(dom.value.substr(4,2));
	}
	
	
	
    if(even && target.tagName){
        if(!elem || elem === DatesMonth.elem){
            return;
        }
        DatesMonth.stopMosup(even.type, elem);
        DatesMonth.stopmp(even);
        DatesMonth.view(elem, options);
        DatesMonth.reshow();
        if(typeof(month)!="undefined"&&month!=""){
//      	month = this.inymd[1];
		$(this.box).find("#laydatemonth_table td[m='"+month+"']").addClass("laydatemonth_click");
        }
//      this.ymd[0] = year;
//      this.ymd[1] = month;
    } else {
        devt = options.event || 'click';
        DatesMonth.each((elem.length|0) > 0 ? elem : [elem], function(ii, that){
            DatesMonth.stopMosup(devt, that);
            DatesMonth.on(that, devt, function(ev){
                DatesMonth.stopmp(ev);
                if(that !== DatesMonth.elem){
                    DatesMonth.view(that, options);
                    DatesMonth.reshow();
                }
            });
        });
    }
};

DatesMonth.scroll = function(type){
    type = type ? 'scrollLeft' : 'scrollTop';
    return doc.body[type] | doc.documentElement[type];
};

DatesMonth.winarea = function(type){
    return document.documentElement[type ? 'clientWidth' : 'clientHeight']
};

//判断闰年
DatesMonth.isleap = function(year){
    return (year%4 === 0 && year%100 !== 0) || year%400 === 0;
};

//检测是否在有效期
DatesMonth.checkVoid = function(YY, MM, DD){
    var back = [];
    YY = YY|0;
    MM = MM|0;
    DD = DD|0;
    if(YY < DatesMonth.mins[0]){
        back = ['y'];
    } else if(YY > DatesMonth.maxs[0]){
        back = ['y', 1];
    } else if(YY >= DatesMonth.mins[0] && YY <= DatesMonth.maxs[0]){
        if(YY == DatesMonth.mins[0]){
            if(MM < DatesMonth.mins[1]){
                back = ['m'];
            } else if(MM == DatesMonth.mins[1]){
                if(DD < DatesMonth.mins[2]){
                    back = ['d'];
                }
            }
        }
        if(YY == DatesMonth.maxs[0]){
            if(MM > DatesMonth.maxs[1]){
                back = ['m', 1];
            } else if(MM == DatesMonth.maxs[1]){
                if(DD > DatesMonth.maxs[2]){
                    back = ['d', 1];
                }
            }
        }
    }
    return back;
};

//时分秒的有效检测
DatesMonth.timeVoid = function(times, index){
    if(DatesMonth.ymd[1]+1 == DatesMonth.mins[1] && DatesMonth.ymd[2] == DatesMonth.mins[2]){
        if(index === 0 && (times < DatesMonth.mins[3])){
            return 1;
        } else if(index === 1 && times < DatesMonth.mins[4]){
            return 1;
        } else if(index === 2 && times < DatesMonth.mins[5]){
            return 1;
        }
    } else if(DatesMonth.ymd[1]+1 == DatesMonth.maxs[1] && DatesMonth.ymd[2] == DatesMonth.maxs[2]){
        if(index === 0 && times > DatesMonth.maxs[3]){
            return 1;
        } else if(index === 1 && times > DatesMonth.maxs[4]){
            return 1;
        } else if(index === 2 && times > DatesMonth.maxs[5]){
            return 1;
        }
    }
    if(times > (index ? 59 : 23)){
        return 1;
    }
};

//检测日期是否合法
DatesMonth.check = function(){
    var reg = DatesMonth.options.format.replace(/YYYY|MM|DD|hh|mm|ss/g,'\\d+\\').replace(/\\$/g, '');
    var exp = new RegExp(reg), value = DatesMonth.elem[as.elemv];
    var arr = value.match(/\d+/g) || [], isvoid = DatesMonth.checkVoid(arr[0], arr[1], arr[2]);
    if(value.replace(/\s/g, '') !== ''){
        if(!exp.test(value)){
            DatesMonth.elem[as.elemv] = '';
            DatesMonth.msg('日期不符合格式，请重新选择。');
            return 1;
        } else if(isvoid[0]){
            DatesMonth.elem[as.elemv] = '';
            DatesMonth.msg('日期不在有效期内，请重新选择。');
            return 1;
        } else {
            isvoid.value = DatesMonth.elem[as.elemv].match(exp).join();
            arr = isvoid.value.match(/\d+/g);
            if(arr[1] < 1){
                arr[1] = 1;
                isvoid.auto = 1;
            } else if(arr[1] > 12){
                arr[1] = 12;
                isvoid.auto = 1;
            } else if(arr[1].length < 2){
                isvoid.auto = 1;
            }
            if(arr[2] < 1){
                arr[2] = 1;
                isvoid.auto = 1;
            } else if(arr[2] > DatesMonth.months[(arr[1]|0)-1]){
                arr[2] = 31;
                isvoid.auto = 1;
            } else if(arr[2].length < 2){
                isvoid.auto = 1;
            }
            if(arr.length > 3){
                if(DatesMonth.timeVoid(arr[3], 0)){
                    isvoid.auto = 1;
                };
                if(DatesMonth.timeVoid(arr[4], 1)){
                    isvoid.auto = 1;
                };
                if(DatesMonth.timeVoid(arr[5], 2)){
                    isvoid.auto = 1;
                };
            }
            if(isvoid.auto){
                DatesMonth.creation([arr[0], arr[1]|0, arr[2]|0], 1);
            } else if(isvoid.value !== DatesMonth.elem[as.elemv]){
                DatesMonth.elem[as.elemv] = isvoid.value;
            }
        }
    }
};

//生成日期
DatesMonth.months = [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
DatesMonth.viewDate = function(Y, M, D){
    var S = DatesMonth.query, log = {}, De = new Date();
    Y < (DatesMonth.mins[0]|0) && (Y = (DatesMonth.mins[0]|0));
    Y > (DatesMonth.maxs[0]|0) && (Y = (DatesMonth.maxs[0]|0));
    
    De.setFullYear(Y, M, D);
    log.ymd = [De.getFullYear(), De.getMonth(), De.getDate()];
    
    DatesMonth.months[1] = DatesMonth.isleap(log.ymd[0]) ? 29 : 28;
    
    De.setFullYear(log.ymd[0], log.ymd[1], 1);
   	log.FDay = De.getDay();
    
    log.PDay = DatesMonth.months[M === 0 ? 11 : M - 1] - log.FDay + 1;
    log.NDay = 1;
    
    //渲染日
    DatesMonth.each(as.tds, function(i, elem){
        var YY = log.ymd[0], MM = log.ymd[1] + 1, DD;
        elem.className = '';
       
        if(DatesMonth.checkVoid(YY, MM, DD)[0]){
            DatesMonth.addClass(elem, as[1]);
        }
        
        DatesMonth.options.festival && DatesMonth.festival(elem, MM + '.' + DD);
        elem.setAttribute('y', YY);
        YY = MM = DD = null;
    });
    
    DatesMonth.valid = !DatesMonth.hasClass(log.thisDay, as[1]);
    DatesMonth.ymd = log.ymd;
    
    //锁定年月
    as.year.value = DatesMonth.ymd[0] + '年';
//  as.month.value = DatesMonth.digit(DatesMonth.ymd[1] + 1) + '月';
    
    //定位月
   /* DatesMonth.each(as.mms, function(i, elem){
        var getCheck = DatesMonth.checkVoid(DatesMonth.ymd[0], (elem.getAttribute('m')|0) + 1);
        if(getCheck[0] === 'y' || getCheck[0] === 'm'){
            DatesMonth.addClass(elem, as[1]);
        } else {
            DatesMonth.removeClass(elem, as[1]);
        }
        DatesMonth.removeClass(elem, as[2]);
        getCheck = null
    });
    DatesMonth.addClass(as.mms[DatesMonth.ymd[1]], as[2]);
    
    //定位时分秒
    log.times = [
        DatesMonth.inymd[3]|0 || 0, 
        DatesMonth.inymd[4]|0 || 0, 
        DatesMonth.inymd[5]|0 || 0
    ];
    DatesMonth.each(new Array(3), function(i){
        DatesMonth.hmsin[i].value = DatesMonth.digit(DatesMonth.timeVoid(log.times[i], i) ? DatesMonth.mins[i+3]|0 : log.times[i]|0);
    });*/
    
    //确定按钮状态
    DatesMonth[DatesMonth.valid ? 'removeClass' : 'addClass'](as.ok, as[1]);
};

//节日
DatesMonth.festival = function(td, md){
    var str;
    switch(md){
        case '1.1':
            str = '元旦';
        break;
        case '3.8':
            str = '妇女';
        break;
        case '4.5':
            str = '清明';
        break;
        case '5.1':
            str = '劳动';
        break;
        case '6.1':
            str = '儿童';
        break;
        case '9.10':
            str = '教师';
        break;
        case '10.1':
            str = '国庆';
        break;
    };
    str && (td.innerHTML = str);
    str = null;
};

//生成年列表
DatesMonth.viewYears = function(YY){
    var S = DatesMonth.query, str = '';
    DatesMonth.each(new Array(14), function(i){
        if(i === 7) {
            str += '<li '+ (parseInt(as.year.value) === YY ? 'class="'+ as[2] +'"' : '') +' y="'+ YY +'">'+ YY +'年</li>';
        } else {
            str += '<li y="'+ (YY-7+i) +'">'+ (YY-7+i) +'年</li>';
        }
    }); 
    S('#laydatemonth_ys').innerHTML = str;
    DatesMonth.each(S('#laydatemonth_ys li'), function(i, elem){
        if(DatesMonth.checkVoid(elem.getAttribute('y'))[0] === 'y'){
            DatesMonth.addClass(elem, as[1]);
        } else {
            DatesMonth.on(elem, 'click', function(ev){
                DatesMonth.stopmp(ev).reshow();
                DatesMonth.viewDate(this.getAttribute('y')|0, DatesMonth.ymd[1], DatesMonth.ymd[2]);
            });
        }
    });
};

//初始化面板数据
DatesMonth.initDate = function(){
    var S = DatesMonth.query, log = {}, De = new Date();
    var ymd = [];
    if(DatesMonth.elem[as.elemv]!=""){
    	var year = parseInt(DatesMonth.elem[as.elemv].substr(0,4));
		var month = parseInt(DatesMonth.elem[as.elemv].substr(4,2));
		ymd.push(year);
		ymd.push(month);
		ymd.push(De.getDate());
    }else{
    	ymd = [De.getFullYear(), De.getMonth()+1, De.getDate()];
    }
   
    /*if(ymd.length < 3){
        ymd = DatesMonth.options.start.match(/\d+/g) || [];
        if(ymd.length < 3){
            ymd = [De.getFullYear(), De.getMonth()+1, De.getDate()];
        }
    }*/
    DatesMonth.inymd = ymd;
    DatesMonth.viewDate(ymd[0], ymd[1]-1, ymd[2]);
};

//是否显示零件
DatesMonth.iswrite = function(){
    var S = DatesMonth.query, log = {
        time: S('#laydatemonth_hms')
    };
    DatesMonth.shde(log.time, !DatesMonth.options.istime);
    DatesMonth.shde(as.oclear, !('isclear' in DatesMonth.options ? DatesMonth.options.isclear : 1));
    DatesMonth.shde(as.otoday, !('istoday' in DatesMonth.options ? DatesMonth.options.istoday : 1));
    DatesMonth.shde(as.ok, !('issure' in DatesMonth.options ? DatesMonth.options.issure : 1));
};

//方位辨别
DatesMonth.orien = function(obj, pos){
    var tops, rect = DatesMonth.elem.getBoundingClientRect();
    obj.style.left = rect.left + (pos ? 0 : DatesMonth.scroll(1)) + 'px';
    if(rect.bottom + obj.offsetHeight/1.5 <= DatesMonth.winarea()){
        tops = rect.bottom - 1;         
    } else {
        tops = rect.top > obj.offsetHeight/1.5 ? rect.top - obj.offsetHeight + 1 : DatesMonth.winarea() - obj.offsetHeight;
    }
    obj.style.top = Math.max(tops + (pos ? 0 : DatesMonth.scroll()),1) + 'px';
};

//吸附定位
DatesMonth.follow = function(obj){
    if(DatesMonth.options.fixed){
        obj.style.position = 'fixed';
        DatesMonth.orien(obj, 1);
    } else {
        obj.style.position = 'absolute';
        DatesMonth.orien(obj);
    }
};

//生成表格
DatesMonth.viewtb = (function(){
    var tr, view = [], weeks = [ '第一季度', '第二季度', '第三季度', '第四季度'],
    months = [["3","6","9","12"],["2","5","8","11"],["1","4","7","10"]];
    var log = {}, table = doc[creat]('table'), thead = doc[creat]('thead');
    thead.appendChild(doc[creat]('tr'));
    log.creath = function(i){
        var th = doc[creat]('th');
        th.innerHTML = weeks[i];
        thead[tags]('tr')[0].appendChild(th);
        th = null;
    };
    
    DatesMonth.each(new Array(3), function(i){
        view.push([]);
        tr = table.insertRow(0);
        var td;
        DatesMonth.each(new Array(4), function(j){
        	i === 0 && log.creath(j);
        	td = tr.insertCell(j);
        	td.innerHTML = months[i][j];
        	td.setAttribute("m",months[i][j]);
        });
    });
    
    table.insertBefore(thead, table.children[0]); 
    table.id = table.className = 'laydatemonth_table';
    tr = view = null;
    return table.outerHTML.toLowerCase();
}());

//渲染控件骨架
DatesMonth.view = function(elem, options){
    var S = DatesMonth.query, div, log = {};
    options = options || elem;

    DatesMonth.elem = elem;
    DatesMonth.options = options;
    DatesMonth.options.format || (DatesMonth.options.format = config.format);
    DatesMonth.options.start = DatesMonth.options.start || '';
    DatesMonth.mm = log.mm = [DatesMonth.options.min || config.min, DatesMonth.options.max || config.max];
    DatesMonth.mins = log.mm[0].match(/\d+/g);
    DatesMonth.maxs = log.mm[1].match(/\d+/g);
    
    if(!DatesMonth.box){
        div = doc[creat]('div');
        div.id = as[0];
        div.className = as[0];
        div.style.cssText = 'position: absolute;';
        div.setAttribute('name', 'laydatemonth-v'+ laydatemonth.v);
        
        div.innerHTML =  log.html = '<div class="laydatemonth_top">'
          +'<div class="laydatemonth_ym laydatemonth_y" id="laydatemonth_YY" style="width:220px">'
            +'<a class="laydatemonth_choose laydatemonth_chprev laydatemonth_tab" style="left:20px"><cite></cite></a>'
            +'<input id="laydatemonth_y" readonly style="left:100px;float: left; margin-left:50px"><label style="right: 70px;"></label>'
            +'<a class="laydatemonth_choose laydatemonth_chnext laydatemonth_tab" style="left:25px"><cite></cite></a>'
            +'<div class="laydatemonth_yms">'
              +'<a class="laydatemonth_tab laydatemonth_chtop"><cite></cite></a>'
              +'<ul id="laydatemonth_ys"></ul>'
              +'<a class="laydatemonth_tab laydatemonth_chdown"><cite></cite></a>'
            +'</div>'
          +'</div>'
        +'</div>'
        
        + DatesMonth.viewtb
        
        +'<div class="laydatemonth_bottom">'
          +'<ul id="laydatemonth_hms">'
            +'<li class="laydatemonth_sj">时间</li>'
            +'<li><input readonly>:</li>'
            +'<li><input readonly>:</li>'
            +'<li><input readonly></li>'
          +'</ul>'
          +'<div class="laydatemonth_time" id="laydatemonth_time"></div>'
          +'<div class="laydatemonth_btn">'
            +'<a id="laydatemonth_clear">清空</a>'
            +'<a id="laydatemonth_today">本月</a>'
            +'<a id="laydatemonth_ok" style="display:none;">确认</a>'
          +'</div>'
          +(config.isv ? '<a href="http://sentsin.com/layui/laydate/" class="laydatemonth_v" target="_blank">laydatemonth-v'+ laydatemonth.v +'</a>' : '')
        +'</div>';
        doc.body.appendChild(div); 
        DatesMonth.box = S('#'+as[0]);        
        DatesMonth.events();
        div = null;
    } else {
        DatesMonth.shde(DatesMonth.box);
    }
    DatesMonth.follow(DatesMonth.box);
    options.zIndex ? DatesMonth.box.style.zIndex = options.zIndex : DatesMonth.removeCssAttr(DatesMonth.box, 'z-index');
    DatesMonth.stopMosup('click', DatesMonth.box);
    
    DatesMonth.initDate();
    DatesMonth.iswrite();
    //DatesMonth.check();
    
};

//隐藏内部弹出元素
DatesMonth.reshow = function(){
    DatesMonth.each(DatesMonth.query('#'+ as[0] +' .laydatemonth_show'), function(i, elem){
        DatesMonth.removeClass(elem, 'laydatemonth_show');
    });
    return this;
};

//关闭控件
DatesMonth.close = function(){
    DatesMonth.reshow();
    DatesMonth.shde(DatesMonth.query('#'+ as[0]), 1);
    DatesMonth.elem = null;
};

//转换日期格式
DatesMonth.parse = function(ymd, hms, format){
    ymd = ymd.concat(hms);
    format = format || (DatesMonth.options ? DatesMonth.options.format : config.format);
    return format.replace(/YYYY|MM|DD|hh|mm|ss/g, function(str, index){
        ymd.index = ++ymd.index|0;
        return DatesMonth.digit(ymd[ymd.index]);
    });     
};

//返回最终日期
DatesMonth.creation = function(ymd, hide){
    var S = DatesMonth.query, hms = DatesMonth.hmsin;
    var getDates = DatesMonth.parse(ymd, [hms[0].value, hms[1].value, hms[2].value]);
    DatesMonth.elem[as.elemv] = getDates.substr(0,6);
    if(!hide){
        DatesMonth.close();
        typeof DatesMonth.options.choose === 'function' && DatesMonth.options.choose(getDates); 
    }
};

//事件
DatesMonth.events = function(){
    var S = DatesMonth.query, log = {
        box: '#'+as[0]
    };
    
    DatesMonth.addClass(doc.body, 'laydate_body');
    
    as.tds = S('#laydatemonth_table td');
    as.mms = S('#laydatemonth_ms span');
    as.year = S('#laydatemonth_y');
    as.month = S('#laydatemonth_m');

    //显示更多年月
    DatesMonth.each(S(log.box + ' .laydatemonth_ym'), function(i, elem){
        DatesMonth.on(elem, 'click', function(ev){
            DatesMonth.stopmp(ev).reshow();
            DatesMonth.addClass(this[tags]('div')[0], 'laydatemonth_show');
            if(!i){
                log.YY = parseInt(as.year.value);
                DatesMonth.viewYears(log.YY);
            }
        });
    });
    
    DatesMonth.on(S(log.box), 'click', function(){
        DatesMonth.reshow();
    });
    
    //切换年
    log.tabYear = function(type){  
        if(type === 0){
            DatesMonth.ymd[0]--;
        } else if(type === 1) {
            DatesMonth.ymd[0]++;
        } else if(type === 2) {
            log.YY -= 14;
        } else {
            log.YY += 14;
        }
        if(type < 2){
            DatesMonth.viewDate(DatesMonth.ymd[0], DatesMonth.ymd[1], DatesMonth.ymd[2]);
            DatesMonth.reshow();
        } else {
            DatesMonth.viewYears(log.YY);
        }
    };
    DatesMonth.each(S('#laydatemonth_YY .laydatemonth_tab'), function(i, elem){
        DatesMonth.on(elem, 'click', function(ev){
            DatesMonth.stopmp(ev);
            log.tabYear(i);
        });
    });
    
    
    //切换月
    /*log.tabMonth = function(type){
        if(type){
            DatesMonth.ymd[1]++;
            if(DatesMonth.ymd[1] === 12){
                DatesMonth.ymd[0]++;
                DatesMonth.ymd[1] = 0;
            }            
        } else {
            DatesMonth.ymd[1]--;
            if(DatesMonth.ymd[1] === -1){
                DatesMonth.ymd[0]--;
                DatesMonth.ymd[1] = 11;
            }
        }
        DatesMonth.viewDate(DatesMonth.ymd[0], DatesMonth.ymd[1], DatesMonth.ymd[2]);
    };*/
    /*DatesMonth.each(S('#laydatemonth_MM .laydatemonth_tab'), function(i, elem){
        DatesMonth.on(elem, 'click', function(ev){
            DatesMonth.stopmp(ev).reshow();
            log.tabMonth(i);
        });
    });*/
    
    //选择月
    /*DatesMonth.each(S('#laydatemonth_ms span'), function(i, elem){
        DatesMonth.on(elem, 'click', function(ev){
            DatesMonth.stopmp(ev).reshow();
            if(!DatesMonth.hasClass(this, as[1])){
                DatesMonth.viewDate(DatesMonth.ymd[0], this.getAttribute('m')|0, DatesMonth.ymd[2]);
            }
        });
    });*/
    
    //选择日
    DatesMonth.each(S('#laydatemonth_table td'), function(i, elem){
        DatesMonth.on(elem, 'click', function(ev){
            if(!DatesMonth.hasClass(this, as[1])){
                DatesMonth.stopmp(ev);
                DatesMonth.creation([this.getAttribute('y')|0, this.getAttribute('m')|0, this.getAttribute('d')|0]);
            }
        });
    });
    
    //清空
    as.oclear = S('#laydatemonth_clear');
    DatesMonth.on(as.oclear, 'click', function(){
        DatesMonth.elem[as.elemv] = '';
        DatesMonth.close();
    });
    
    //今天
    as.otoday = S('#laydatemonth_today');
    DatesMonth.on(as.otoday, 'click', function(){
        var now = new Date();
        DatesMonth.creation([now.getFullYear(), now.getMonth() + 1, now.getDate()]);
    });
    
    //确认
    as.ok = S('#laydatemonth_ok');
    DatesMonth.on(as.ok, 'click', function(){
        if(DatesMonth.valid){
            DatesMonth.creation([DatesMonth.ymd[0], DatesMonth.ymd[1]+1, DatesMonth.ymd[2]]);
        }
    });
    
    //选择时分秒
    log.times = S('#laydatemonth_time');
    DatesMonth.hmsin = log.hmsin = S('#laydatemonth_hms input');
    log.hmss = ['小时', '分钟', '秒数'];
    log.hmsarr = [];
    
    //生成时分秒或警告信息
    DatesMonth.msg = function(i, title){
        var str = '<div class="laydte_hsmtex">'+ (title || '提示') +'<span>×</span></div>';
        if(typeof i === 'string'){
            str += '<p>'+ i +'</p>';
            DatesMonth.shde(S('#'+as[0]));
            DatesMonth.removeClass(log.times, 'laydatemonth_time1').addClass(log.times, 'laydatemonth_msg');
        } else {
            if(!log.hmsarr[i]){
                str += '<div id="laydatemonth_hmsno" class="laydatemonth_hmsno">';
                DatesMonth.each(new Array(i === 0 ? 24 : 60), function(i){
                    str += '<span>'+ i +'</span>';
                });
                str += '</div>'
                log.hmsarr[i] = str;
            } else {
                str = log.hmsarr[i];
            }
            DatesMonth.removeClass(log.times, 'laydatemonth_msg');
            DatesMonth[i=== 0 ? 'removeClass' : 'addClass'](log.times, 'laydatemonth_time1');
        }
        DatesMonth.addClass(log.times, 'laydatemonth_show');
        log.times.innerHTML = str;
    };
    
    log.hmson = function(input, index){
        var span = S('#laydatemonth_hmsno span'), set = DatesMonth.valid ? null : 1;
        DatesMonth.each(span, function(i, elem){
            if(set){
                DatesMonth.addClass(elem, as[1]);
            } else if(DatesMonth.timeVoid(i, index)){
                DatesMonth.addClass(elem, as[1]);
            } else {
                DatesMonth.on(elem, 'click', function(ev){
                    if(!DatesMonth.hasClass(this, as[1])){
                        input.value = DatesMonth.digit(this.innerHTML|0);
                    }
                });
            } 
        });
        DatesMonth.addClass(span[input.value|0], 'laydatemonth_click');
    };
    
    //展开选择
    DatesMonth.each(log.hmsin, function(i, elem){
        DatesMonth.on(elem, 'click', function(ev){
            DatesMonth.stopmp(ev).reshow();
            DatesMonth.msg(i, log.hmss[i]);
            log.hmson(this, i);
        });
    });
    
    DatesMonth.on(doc, 'mouseup', function(){
        var box = S('#'+as[0]);
        if(box && box.style.display !== 'none'){
            DatesMonth.close();
        }
    }).on(doc, 'keydown', function(event){
        event = event || win.event;
        var codes = event.keyCode;

        //如果在日期显示的时候按回车
        if(codes === 13 && DatesMonth.elem){
            DatesMonth.creation([DatesMonth.ymd[0], DatesMonth.ymd[1]+1, DatesMonth.ymd[2]]);
        }
    });
};

DatesMonth.init = (function(){
    DatesMonth.use('need');
    DatesMonth.use(as[4] + config.defSkin, as[3]);
    DatesMonth.skinLink = DatesMonth.query('#'+as[3]);
}());

//重置定位
laydatemonth.reset = function(){
    (DatesMonth.box && DatesMonth.elem) && DatesMonth.follow(DatesMonth.box);
};

//返回指定日期
laydatemonth.now = function(timestamp, format){
//	timestamp = "2016-11";
//	var dt=new Date(timestamp.replace(/-/g,"/"))
//	alert(dt)
    var De = new Date((timestamp|0) ? function(tamp){
        return tamp < 86400000 ? (+new Date + tamp*86400000) : tamp;
    }(parseInt(timestamp)) : +new Date);
    return DatesMonth.parse(
        [De.getFullYear(), De.getMonth()+1, De.getDate()],
        [De.getHours(), De.getMinutes(), De.getSeconds()],
        format
    );
};

//皮肤选择
laydatemonth.skin = function(lib){
    DatesMonth.skinLink.href = DatesMonth.getPath + as[4] + lib + as[5];
};

}(window);
