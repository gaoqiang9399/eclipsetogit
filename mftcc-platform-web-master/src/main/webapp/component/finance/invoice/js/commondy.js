//限制金额项数字
function limit_money_input(a) {
    var ja = $(a);
    ja.bind("contextmenu", function () {
        return false;
    });
    ja.css('ime-mode', 'disabled');
    ja.bind("keydown", function (e) {
        var key = window.event ? e.keyCode : e.which;
        if (isFullStop(key)) {
            return $(this).val().indexOf('.') < 0 && $(this).val().length > 0;
        }
        return (isSpecialKey(key)) || ((isNumber(key) && !e.shiftKey))
    });
}

function isNumber(key) {
    return (key >= 48 && key <= 57) || (key >= 96 && key <= 105);
}
function isSpecialKey(key) {
    //8:backspace; 46:delete; 37-40:arrows; 36:home; 35:end; 9:tab; 13:enter  116:F5
    return key == 8 || key == 46 || (key >= 37 && key <= 40) || key == 35
        || key == 36 || key == 9 || key == 13 || key == 116;
}
function isFullStop(key) {
    return key == 190 || key == 110;
}
//打印时添加金币符号
function addMoney(a, b) {
    LODOP.ADD_PRINT_TEXT(a - 1, b, 20, 20, "Y");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(a - 9, b, 20, 20, "_");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
    LODOP.ADD_PRINT_TEXT(a - 7, b, 20, 20, "_");
    LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
    LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
}
//激活按钮
function enableButtons(bts) {
    bts.removeAttr("disabled").parent().parent().removeClass("buttonDisabled").addClass("button").hoverClass("buttonHover");
}
//禁用按钮
function disableButtons(bts) {
    bts.attr("disabled", "disabled").parent().parent().removeClass("button").addClass("buttonDisabled").unbind("hover");
}
//使元素重新获取焦点
function reFocus(a, b) {
    if (a == undefined) {
        return;
    }
    if (b) {
        a.value = "";
    }
    if ($.browser.msie) {
        a.focus();
    } else {
        window.setTimeout(function () {
            a.focus();
        }, 1);
    }
}
//生成税率下拉框
function genSzsm(g) {
    var str = "";
    $.each(g, function () {
        if (this.hsbz == "0") {
            str += ("<option value='" + this.sl + "'>" + this.sl * 100 + "%</option>");
        }
    });
    return str;
}
//删除左边一个字符
function delLeftMoney(src) {
    src = $.trim(src);
    return src.substring(1, src.length);
}
//删除数字右边的零
function delRight(src) {
    var temp = src + "";
    while ((temp.charAt(temp.length - 1) == "0" && temp.indexOf(".") != -1) ||
    temp == "0" || temp.charAt(temp.length - 1) == '.') {
        temp = delRightPercent(temp);
    }
    return temp;
}
//删除右边一个字符
function delRightPercent(src) {
    src = $.trim(src);
    return src.substring(0, src.length - 1);
}
//获取序号
function getIndex(src) {
    return src.substring(src.indexOf("_") + 1, src.length);
}
//金额大写
function je2Upper(num) {
    num = Number(num).toFixed(2);
    var strOutput = "";
    var strUnit = '仟佰拾亿仟佰拾万仟佰拾圆角分';
    num += "00";
    if (num.charAt(0) == "-")num = num.substring(1);
    var intPos = num.indexOf('.');
    if (intPos >= 0)
        num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
    strUnit = strUnit.substr(strUnit.length - num.length);
    for (var i = 0; i < num.length; i++)
        strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i, 1), 1)
            + strUnit.substr(i, 1);
    return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+圆/, '圆').replace(/亿零{0,3}万/, '亿').replace(/^圆/, "零圆").replace(/零分$/, '整').replace(/^零圆/, '').replace(/^整$/, '零圆整');
}
//字符串长度，一个中文为两个英文
function countStrLength(str) {
    var _str = str + "";
    if (!_str) {
        return 0;
    }
    var count = 0;
    for (var i = 0; i < _str.length; i++) {
        var c = _str.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || c >= 0xff9f && c <= 0xff60) {
            count++;
        } else {
            count += 2;
        }
    }
    return count;
}
//回填费用项目
function fillFyxm(tr, index, sfhs) {
    var tds = $(tr).children();
    _$("#spmc_" + index).val(tds.eq(2).text());
    _$("#ggxh_" + index).val(tds.eq(4).text());
    _$("#dw_" + index).val(tds.eq(5).text());
    var dj = tds.eq(6).text();
    _$("#spdj_" + index).val(dj);
    var sl = tds.eq(7).attr("sl");
    _$("#sl_" + index).val(sl == 0 ? 0 : sl);
    var hsbz = tds.eq(8).attr("hsbz") == "true";
    if (sfhs && !hsbz) {
        _$("#spdj_" + index).attr("optDj", dj).val(delRight((dj * (1 + sl * 1)).toFixed(6)));
    } else if (!sfhs && hsbz) {
        _$("#spdj_" + index).attr("optDj", dj).val(delRight((dj / (1 + sl * 1)).toFixed(6)));
    } else {
        _$("#spdj_" + index).val(delRight(dj));
        _$("#spdj_" + index).removeAttr("optDj");
    }
    _$("#je_" + index + ",#se_" + index + ",#spsl_" + index).val("");
    $.pdialog.closeCurrent();
    reFocus(_$("#spsl_" + index)[0])
}
//指定jQuery选择器范围为当前标签页
function _$(sel) {
    return $(sel, navTab.getCurrentPanel());
}
//指定jQuery选择器范围为当前对话框
function $_(sel) {
    return $(sel, $.pdialog.getCurrent())
}
//生成拼音
function genPinyin(src) {
    var that = $(src);
    var pyjx = toPinyinJX(that.toPinyin());
    that.parent().next().find("input[name='py']").val(pyjx.substring(0, 4));
}
function toPinyinJX(py) {
    var pyjx = "";
    $.each(py.split(" "), function (i, n) {
        var one = $.trim(n);
        if (one) {
            pyjx += (one.charAt(0).toUpperCase());
        }
    });
    return pyjx;
}
//检查flash
function flashChecker() {
    var hasFlash = 0; //是否安装了flash  
    var flashVersion = 0; //flash版本  

    if (document.all) {
        var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
        if (swf) {
            hasFlash = 1;
            var VSwf = swf.GetVariable("$version");
            flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
        }
    } else {
        if (navigator.plugins && navigator.plugins.length > 0) {
            var swf = navigator.plugins["Shockwave Flash"];
            if (swf) {
                hasFlash = 1;
                var words = swf.description.split(" ");
                for (var i = 0; i < words.length; ++i) {
                    if (isNaN(parseInt(words[i])))
                        continue;
                    flashVersion = parseInt(words[i]);
                }
            }
        }
    }
    return {
        f: hasFlash,
        v: flashVersion
    };
}
//返回DWZ多选Table下的所有ID
function getDwzIds(table, name, str) {
    var ids = [];
    var strIds = "z=z";
    table.find("input:checked").filter("[name='" + name + "']").each(function (i) {
        var val = $(this).val();
        ids.push({name: val});
        strIds += ("&" + name + "=" + val)
    });
    return str ? strIds : ids;
}
//特殊操作前置检查
function checkPerm(f, args) {
    //if(sk_version=="BOC"||(sk_version=="CBC"&&$("#navTab ul.navTab-tab li.selected").attr("tabid").indexOf("zzp")!=-1)
    if (sk_version == "BOC" || sk_version == "CBC"
        || (sk_version == "ABC" && $("#navTab ul.navTab-tab li.selected").attr("tabid").indexOf("zzspp_zsfpkj_nav") != -1)) {
        var prompt = '0';
        if (sk_version == "CBC") {
            prompt = _$("#affJylsh").val(); //prompt值为0表示不需要审核，值为1表示需要审核
        }
        if (sk_version == "CBC" && $("#navTab ul.navTab-tab li.selected").attr("tabid").indexOf("zzp") == -1 && (prompt == '0' || !prompt)) { //建行普票只有重复交易流水才需要确认
            f.call(args);
        } else {
            $.pdialog.open(ctxPath + "/boc/toCheckPerm.do?prompt=" + prompt, "boc_toCheckPerm_dlg", "授权以继续操作", {
                width: 480,
                height: 240,
                callback: function () {
                    if ($.browser.msie) {
                        $_(":password").width(127);
                    }
                    $_("button").click(function () {
                        var form = $_("form");
                        if (!form.valid()) {
                            return false;
                        }
                        ajaxLoad(ctxPath + "/boc/checkPerm.do", form.serialize(), function (json) {
                            if (sk_version == "CBC") {
                                _$("#fhr").val(json.fhr);
                            }
                            $.pdialog.closeCurrent();
                            f.call(args);
                        });
                    })
                }
            })
        }

    } else {
        f.call(args);
    }
}
function exportExcel(op) {
    var form = op.form;
    var url = op.url || form.attr("action");
    var param = form.serializeArray();
    param.push({name: "fileName", value: op.fileName || "download"});
    param.push({name: "pageNum", value: "1"});
    param.push({name: "pageSize", value: "100000"});
    param.push({name: "format", value: "excel"});
    param = $.merge(param, op.param || []);
    url += (url.indexOf('?') == -1 ? "?" : "&") + $.param(param);
    location.href = url;
}
function HashMap() {
    /** Map大小* */
    var size = 0;
    /** 对象* */
    var entry = {};
    /** Map的存put方法* */
    this.put = function (key, value) {
        if (!this.containsKey(key)) {
            size++;
        }
        entry[key] = value;
    };
    /** Map取get方法* */
    this.get = function (key) {
        return this.containsKey(key) ? entry[key] : null;
    };
    /** Map删除remove方法* */
    this.remove = function (key) {
        if (this.containsKey(key) && (delete entry[key])) {
            size--;
        }
    };
    /** 是否包含Key* */
    this.containsKey = function (key) {
        return (key in entry);
    };
    /** 是否包含Value* */
    this.containsValue = function (value) {
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    };
    /** 所有的Value* */
    this.values = function () {
        var values = [];
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    };
    /** 所有的 Key* */
    this.keys = function () {
        var keys = [];
        for (var prop in entry) {
            keys.push(prop);
        }
        return keys;
    };
    /** Map size* */
    this.size = function () {
        return size;
    };
    /**清空Map**/
    this.clear = function () {
        size = 0;
        entry = {};
    }
}

//商品编码-根据名称查询是否有对应的商品编码信息
function checkSpbm(arr, verfiy) {
    var data = [];
    data.async = false;
    var rtn = {};
    $.each(arr, function (i, n) {
        data.push({name: "mc", value: n});
    });
    ajaxLoad(ctxPath + "/spbm/searchSpbm.do", data, function (json) {
        rtn = json.bm;
        if (verfiy) {
            $.each(arr, function (i, n) {
                if (!rtn[n]) {
                    alertMsg.error("商品名称：【" + n + "】没有对应的税收编码，请维护！");
                    rtn = false;
                    return false;
                }
            });
        }
    });
    return rtn;

}
//商品编码-根据spbm查询是否有对应的商品编码信息
function checkSpbm1(arr, verfiy) {
    var data = [];
    data.async = false;
    var rtn = {};
    $.each(arr, function (i, n) {
        data.push({name: "bm", value: n});
    });
    ajaxLoad(ctxPath + "/spbm/searchSpbm1.do", data, function (json) {
        rtn = json.bm;
        if (verfiy) {
            $.each(arr, function (i, n) {
                if (!rtn[n.substring(0, 19)]) {
                    alertMsg.error("商品编码：【" + n + "】没有查到对应的税收信息，请检查！");
                    rtn = false;
                    return false;
                }
            });
        }
    });
    return rtn;
}

/**
 * 全角字符转换成半角字符
 * @param str
 * @returns {String}
 */
function qj2bj(str) {
    var tmp = "";
    for (var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) > 65248 && str.charCodeAt(i) < 65375 && str.charAt(i) != "（" && str.charAt(i) !="）") {
            tmp += String.fromCharCode(str.charCodeAt(i) - 65248);
        } else {
            tmp += String.fromCharCode(str.charCodeAt(i));
        }
    }
    return tmp;
}
function getByDtm(input) {
    var $input = $(input);
    $input.val($.trim(input.value).replace(/[^0-9]/g, ""));
    if ($input.val().length < 6) {
        return;
    }
    ajaxLoad(ctxPath + "/qytt/dtm.do?dtm=" + $input.val(), function (json) {
        if (json.val.length == 0) {
            alertMsg.error("查询结果为空,动态码可能失效");
            return;
        }
        var tt = json.val[0];
        _$("#ghdwmc").val(tt[0]);
        _$("#ghdwdm").val(tt[1]);
        _$("#ghdwdzdh").val(tt[2]);
        _$("#ghdwyhzh").val(tt[3]);
    });
}
function getByDkp() {
    $.pdialog.open(ctxPath + "/qytt/dkp.do?", "qydkp_dlg", "待开具企业抬头信息", {
        width: 768,
        height: 440,
        callback: function () {
            $_("table.list tbody tr").bind("dblclick", function () {
                var tds = $(this).children();
                _$("#ghdwmc").val(tds.eq(2).text());
                _$("#ghdwdm").val((tds.eq(1).text()).toLocaleUpperCase());
                _$("#ghdwdzdh").val(tds.eq(3).text());
                _$("#ghdwyhzh").val(tds.eq(4).text());
                $.pdialog.closeCurrent();
            });
        }
    });
}

function getByKpyh() {
    $.pdialog.closeCurrent();
    $.pdialog.open(ctxPath + "/qytt/kpyh.do?", "kpyh_dlg", "绑定开票用户信息", {
        width: 500,
        height: 300,
        callback: function () {
        }

    });

}
//快速选择商品编码(spmc框的change事件)
function quicklyChoiceSpbm(newLine, hsbz, fplxdm, fskj, zsfs,isCpy) {
    newLine.find(":text[id^='spmc']").bind("change", function () {
        var current = $.pdialog.getCurrent();
        if (current != null && current.css("display") != "none") {
            return;
        }
        var spmc_this = this;
        var spmc = spmc_this.value.trim();
        if (spmc == "") {
            $(spmc_this).parent().parent().find(":text").val("");
            return;
        }
        var id = $(this).attr("id");
        //名称类似于spmc_3,从第五位开始至末端截取角标
        var index = id.substring(5, id.length);
        //var fplxdm = "004";
        ajaxLoad(ctxPath + "/spxx/quickChoiceSpbm.do", {"format": "json", "mc": spmc}, function (json) {
            var spxx = json.spxx;
            $(spmc_this).parent().parent().find(":text").val("");
            if (spxx.length == 1) {
                var tr = $("<tr> <td></td>  <td>" + spxx[0].bm + "</td> <td>" + spxx[0].mc + "</td> <td>" + spxx[0].ggxh + "</td> <td>" + spxx[0].jldw + "</td> <td>" + (spxx[0].dj == "" ? "" : spxx[0].dj.toFixed(6)) + "</td> <td sl='" + spxx[0].sl + "'>" + spxx[0].sl + "</td> <td kysl='" + spxx[0].kysl + "'>" + spxx[0].kysl + "</td> <td hsbz='" + spxx[0].hsbz + "'>" + (spxx[0].hsbz ? '是' : '否') + "</td> <td syyh='" + spxx[0].syyh + "'>" + (spxx[0].syyh == "" ? "" : (spxx[0].syyh == "Y" ? '是' : '否')) + "</td> <td>" + spxx[0].yhlx + "</td> <td mslx='" + spxx[0].mslx + "'>" + spxx[0].mslx + "</td> <td>" + spxx[0].czydm + "</td> </tr>");
                splxxx.spxx1(index, hsbz, fplxdm, fskj, "", "", zsfs);
                splxxx.ggsppc(tr, index, hsbz, fplxdm,isCpy,spxx[0].qysj,json.dqsj);
            } else if (spxx.length > 1) {
                splxxx.spxxQuickChoice(index, hsbz, fplxdm, fskj, "", "", zsfs, spmc,isCpy);
            } else {
                $(spmc_this).parent().parent().find(":text").val("");
            }
        });
    });


}
function deltr(json) {
    dialogAjaxDone(json);
    if (json.statusCode == '200') {
        $_('.list tr.selected').remove()
    }

}
/**
 * 查询总额
 */
function getCxze() {
    var zhjje = 0;
    var zse = 0;
    var zjshj = 0;
    _$("tr #hjje").each(function () {
        zhjje += +_$(this).text();
    });
    _$("tr #se").each(function () {
        zse += +_$(this).text();
    });
    _$("tr #jshj").each(function () {
        zjshj += +_$(this).text();
    });
    _$(".table").after("<div class='panelBar' > <div style='color: red' align='center'><th>本页总合计金额：" + zhjje.toFixed(2) + "，</th><th>总税额：" + zse.toFixed(2) + "，</th><th>总价税合计：" + zjshj.toFixed(2) + "</th></div></div>")
}

/**
 * 纳税人信息导出
 */
function nsrExportExcel() {
    var header = "纳税人识别号@纳税人名称@拼音@地址电话@银行账号@类型@是否公用";
    var headerAtt = "nsrsbh@nsrmc@py@dzdh@yhzh@_type@_public";
    var op = {
        form: _$('form[rel]'),
        fileName: "纳税人信息导出",
        param: [{name: "header", value: header},
            {name: "headerAtt", value: headerAtt}]
    };
    exportExcel(op);
}
/**
 * 纳税人信息模板下载
 */
function nsrmbDownload() {
    window.open(ctxPath + "/resources/download/NsrXx_mb.zip");
}
/**
 * 清单模板下载
 */
function drqdmbDownload() {
    window.open(ctxPath + "/resources/download/Drqd_mb.zip");
}