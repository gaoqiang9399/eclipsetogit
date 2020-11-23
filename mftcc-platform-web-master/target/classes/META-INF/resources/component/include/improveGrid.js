
(function($) {
    $.extend({
        tablesorter: new
        function() {
            var parsers = [],
            widgets = [];
            var $arrowUp = $("<span>&uarr;</span>");
            $arrowUp.css({
                "padding": "0px",
                "marginRight": "5px",
                "marginTop": "0px",
                "marginBottom": "0px",
                "fontSize": "12px",
                "color": "black",
                "width": "10px",
                "height": "14px"
            });
            var $arrowDown = $("<span>&darr;</span>");
            $arrowDown.css({
                "padding": "0px",
                "marginRight": "5px",
                "marginTop": "0px",
                "marginBottom": "0px",
                "fontSize": "12px",
                "color": "black",
                "width": "10px",
                "height": "14px"
            });
            this.defaults = {
                cssAsc: "headerSortUp",
                cssDesc: "headerSortDown",
                sortInitialOrder: "asc",
                sortMultiSortKey: "shiftKey",
                sortForce: null,
                sortAppend: null,
                textExtraction: "simple",
                parsers: {},
                widgets: [],
                widgetZebra: {
                    css: ["t1", "t2"]
                },
                headers: {},
                widthFixed: true,
                cancelSelection: true,
                sortList: [],
                headerList: [],
                dateFormat: "us",
                decimal: '.',
                debug: false
            };

            /* debuging utils */
            function benchmark(s, d) {
                log(s + "," + (new Date().getTime() - d.getTime()) + "ms");
            }

            this.benchmark = benchmark;

            function log(s) {
                if (typeof console != "undefined" && typeof console.debug != "undefined") {
                    console.log(s);
                } else {
                    alert(s);
                }
            }

            /* parsers utils */
            function buildParserCache(table, $headers) {
                if (table.config.debug) {
                    var parsersDebug = "";
                }
                var rows = table.tBodies[0].rows;
                if (table.tBodies[0].rows[0]) {
                    var list = [],
                    cells = rows[0].cells,
                    l = cells.length;
                    for (var i = 0; i < l; i++) {
                        var p = false;
                        if ($.metadata && ($($headers[i]).metadata() && $($headers[i]).metadata().sorter)) {
                            p = getParserById($($headers[i]).metadata().sorter);
                        } else if ((table.config.headers[i] && table.config.headers[i].sorter)) {
                            p = getParserById(table.config.headers[i].sorter);
                        }
                        if (!p) {
                            p = detectParserForColumn(table, cells[i]);
                        }
                        if (table.config.debug) {
                            parsersDebug += "column:" + i + " parser:" + p.id + "\n";
                        }
                        list.push(p);
                    }
                }
                if (table.config.debug) {
                    log(parsersDebug);
                }
                return list;
            };

            function detectParserForColumn(table, node) {
                var l = parsers.length;
                for (var i = 1; i < l; i++) {
                    if (parsers[i].is($.trim(getElementText(table.config, node)), table, node)) {
                        return parsers[i];
                    }
                }
                // 0 is always the generic parser (text)
                return parsers[0];
            }

            function getParserById(name) {
                var l = parsers.length;
                for (var i = 0; i < l; i++) {
                    if (parsers[i].id.toLowerCase() == name.toLowerCase()) {
                        return parsers[i];
                    }
                }
                return false;
            }

            /* utils */
            function buildCache(table) {
                if (table.config.debug) {
                    var cacheTime = new Date();
                }
                var totalRows = (table.tBodies[0] && table.tBodies[0].rows.length) || 0,
                totalCells = (table.tBodies[0].rows[0] && table.tBodies[0].rows[0].cells.length) || 0,
                parsers = table.config.parsers,
                cache = {
                    row: [],
                    normalized: []
                };
                for (var i = 0; i < totalRows; ++i) {
                    /** Add the table data to main data array */
                    var c = table.tBodies[0].rows[i],
                    cols = [];
                    cache.row.push($(c));
                    for (var j = 0; j < totalCells; ++j) {
                        cols.push(parsers[j].format(getElementText(table.config, c.cells[j]), table, c.cells[j]));
                    }
                    cols.push(i); // add position for rowCache
                    cache.normalized.push(cols);
                    cols = null;
                };
                if (table.config.debug) {
                    benchmark("Building cache for " + totalRows + " rows:", cacheTime);
                }
                return cache;
            };

            function getElementText(config, node) {
                if (!node) return "";
                var t = "";
                if (config.textExtraction == "simple") {
                    if (node.childNodes[0] && node.childNodes[0].hasChildNodes()) {
                        t = node.childNodes[0].innerHTML;
                    } else {
                        t = node.innerHTML;
                    }
                } else {
                    if (typeof(config.textExtraction) == "function") {
                        t = config.textExtraction(node);
                    } else {
                        t = $(node).text();
                    }
                }
                return t;
            }

            function appendToTable(table, cache) {
                if (table.config.debug) {
                    var appendTime = new Date()
                }
                var c = cache,
                r = c.row,
                n = c.normalized,
                totalRows = n.length,
                checkCell = (n[0].length - 1),
                tableBody = $(table.tBodies[0]),
                rows = [];
                for (var i = 0; i < totalRows; i++) {
                    rows.push(r[n[i][checkCell]]);
                    if (!table.config.appender) {
                        var o = r[n[i][checkCell]];
                        var l = o.length;
                        for (var j = 0; j < l; j++) {
                            tableBody[0].appendChild(o[j]);
                        }
                        //tableBody.append(r[n[i][checkCell]]);
                    }
                }
                if (table.config.appender) {
                    table.config.appender(table, rows);
                }
                rows = null;
                if (table.config.debug) {
                    benchmark("Rebuilt table:", appendTime);
                }
                //apply table widgets
                applyWidget(table);
                // trigger sortend
                setTimeout(function() {
                    $(table).trigger("sortEnd");
                },
                0);

            };

            function buildHeaders(table) {
                if (table.config.debug) {
                    var time = new Date();
                }
                var meta = ($.metadata) ? true: false,
                tableHeadersRows = [];
                for (var i = 0; i < table.tHead.rows.length; i++) {
                    tableHeadersRows[i] = 0;
                };
                $tableHeaders = $("thead th", table);
                $tableHeaders.each(function(index) {
                    this.count = 0;
                    this.column = index;
                    this.order = formatSortingOrder(table.config.sortInitialOrder);
                    if (checkHeaderMetadata(this) || checkHeaderOptions(table, index)) this.sortDisabled = true;
                    if (!this.sortDisabled) {
                        $(this).addClass(table.config.cssHeader);
                    }
                    // add cell to headerList
                    table.config.headerList[index] = this;
                });
                if (table.config.debug) {
                    benchmark("Built headers:", time);
                    log($tableHeaders);
                }
                return $tableHeaders;
            };

            function checkCellColSpan(table, rows, row) {
                var arr = [],
                r = table.tHead.rows,
                c = r[row].cells;
                for (var i = 0; i < c.length; i++) {
                    var cell = c[i];
                    if (cell.colSpan > 1) {
                        arr = arr.concat(checkCellColSpan(table, headerArr, row++));
                    } else {
                        if (table.tHead.length == 1 || (cell.rowSpan > 1 || !r[row + 1])) {
                            arr.push(cell);
                        }
                        //headerArr[row] = (i+row);
                    }
                }
                return arr;
            };

            function checkHeaderMetadata(cell) {
                if (($.metadata) && ($(cell).metadata().sorter === false)) {
                    return true;
                };
                return false;
            }

            function checkHeaderOptions(table, i) {
                if ((table.config.headers[i]) && (table.config.headers[i].sorter === false)) {
                    return true;
                };
                return false;
            }

            function applyWidget(table) {
                var c = table.config.widgets;
                var l = c.length;
                for (var i = 0; i < l; i++) {
                    getWidgetById(c[i]).format(table);
                }
            }

            function getWidgetById(name) {
                var l = widgets.length;
                for (var i = 0; i < l; i++) {
                    if (widgets[i].id.toLowerCase() == name.toLowerCase()) {
                        return widgets[i];
                    }
                }
            };

            function formatSortingOrder(v) {
                if (typeof(v) != "Number") {
                    i = (v.toLowerCase() == "desc") ? 1 : 0;
                } else {
                    i = (v == (0 || 1)) ? v: 0;
                }
                return i;
            }

            function isValueInArray(v, a) {
                var l = a.length;
                for (var i = 0; i < l; i++) {
                    if (a[i][0] == v) {
                        return true;
                    }
                }
                return false;
            }

            function setHeadersCss(table, $headers, list, css) {
                // remove all header information
                $headers.removeClass(css[0]).removeClass(css[1]);
                var h = [];
                $headers.each(function(offset) {
                    if (!this.sortDisabled) {
                        h[this.column] = $(this);
                    }
                });
                var l = list.length;
                for (var i = 0; i < l; i++) {
                    h[list[i][0]].addClass(css[list[i][1]]);
                }
            }

            function setHeadersArrow(table, $headers, list, arrow) {
                // remove all header information
                $headers.each(function() {
                    $("span", this).remove();
                });
                var h = [];
                $headers.each(function(offset) {
                    if (!this.sortDisabled) {
                        h[this.column] = $(this);
                    }
                });
                var l = list.length;
                for (var i = 0; i < l; i++) {
                    h[list[i][0]].append(arrow[list[i][1]]);
                }
            }

            function fixColumnWidth(table, $headers) {
                var c = table.config;
                //alert(c.widthFixed);
                if (c.widthFixed) {
                    var colgroup = $('<colgroup>');
                    $("tr:first td", table.tBodies[0]).each(function() {
                        //alert($(this).width());
                        colgroup.append($('<col>').css('width', $(this).width()));
                    });
                    $(table).prepend(colgroup);
                };
            }

            function updateHeaderSortCount(table, sortList) {
                var c = table.config,
                l = sortList.length;
                for (var i = 0; i < l; i++) {
                    var s = sortList[i],
                    o = c.headerList[s[0]];
                    o.count = s[1];
                    o.count++;
                }
            }

            /* sorting methods */
            function multisort(table, sortList, cache) {
                if (table.config.debug) {
                    var sortTime = new Date();
                }
                var dynamicExp = "var sortWrapper = function(a,b) {",
                l = sortList.length;
                var i;
                for (i = 0; i < l; i++) {
                    var c = sortList[i][0];
                    var order = sortList[i][1];
                    var s = (getCachedSortType(table.config.parsers, c) == "text") ? ((order == 0) ? "sortText": "sortTextDesc") : ((order == 0) ? "sortNumeric": "sortNumericDesc");
                    var e = "e" + i;
                    dynamicExp += "var " + e + " = " + s + "(a[" + c + "],b[" + c + "]); ";
                    dynamicExp += "if(" + e + ") { return " + e + "; } ";
                    dynamicExp += "else { ";
                }
                // if value is the same keep orignal order  
                var orgOrderCol = cache.normalized[0].length - 1;
                dynamicExp += "return a[" + orgOrderCol + "]-b[" + orgOrderCol + "];";
                for (i = 0; i < l; i++) {
                    dynamicExp += "}; ";
                }
                dynamicExp += "return 0; ";
                dynamicExp += "}; ";
                eval(dynamicExp);
                cache.normalized.sort(sortWrapper);
                if (table.config.debug) {
                    benchmark("Sorting on " + sortList.toString() + " and dir " + order + " time:", sortTime);
                }
                return cache;
            };

            function sortText(a, b) {
                return ((a < b) ? -1 : ((a > b) ? 1 : 0));
            };

            function sortTextDesc(a, b) {
                return ((b < a) ? -1 : ((b > a) ? 1 : 0));
            };

            function sortNumeric(a, b) {
                return a - b;
            };

            function sortNumericDesc(a, b) {
                return b - a;
            };

            function getCachedSortType(parsers, i) {
                return parsers[i].type;
            };

            /* public methods */
            this.construct = function(settings) {
                return this.each(function() {
                    if (!this.tHead || !this.tBodies) return;
                    var $this, $document, $headers, cache, config, shiftDown = 0,
                    sortOrder;
                    this.config = {};
                    config = $.extend(this.config, $.tablesorter.defaults, settings);
                    // store common expression for speed                    
                    $this = $(this);
                    // build headers
                    $headers = buildHeaders(this);
                    // try to auto detect column type, and store in tables config
                    this.config.parsers = buildParserCache(this, $headers);
                    // build the cache for the tbody cells
                    cache = buildCache(this);
                    // get the css class names, could be done else where.
                    var sortCSS = [config.cssDesc, config.cssAsc];
                    var sortArrow = [$arrowUp, $arrowDown];
                    // fixate columns if the users supplies the fixedWidth option
                    fixColumnWidth(this);
                    // apply event handling to headers
                    // this is to big, perhaps break it out?
                    $headers.click(function(e) {
                        $this.trigger("sortStart");
                        var totalRows = ($this[0].tBodies[0] && $this[0].tBodies[0].rows.length) || 0;
                        if (!this.sortDisabled && totalRows > 0) {
                            // store exp, for speed
                            var $cell = $(this);
                            // get current column index
                            var i = this.column;
                            // get current column sort order
                            this.order = this.count++%2;
                            var j;
                            // user only whants to sort on one column
                            if (!e[config.sortMultiSortKey]) {
                                // flush the sort list
                                config.sortList = [];
                                if (config.sortForce != null) {
                                    var a = config.sortForce;
                                    for (j = 0; j < a.length; j++) {
                                        if (a[j][0] != i) {
                                            config.sortList.push(a[j]);
                                        }
                                    }
                                }
                                // add column to sort list
                                config.sortList.push([i, this.order]);
                                // multi column sorting
                            } else {
                                // the user has clicked on an all ready sortet column.
                                if (isValueInArray(i, config.sortList)) {

                                    // revers the sorting direction for all tables.
                                    for (j = 0; j < config.sortList.length; j++) {
                                        var s = config.sortList[j],
                                        o = config.headerList[s[0]];
                                        if (s[0] == i) {
                                            o.count = s[1];
                                            o.count++;
                                            s[1] = o.count % 2;
                                        }
                                    }
                                } else {
                                    // add column to sort list array
                                    config.sortList.push([i, this.order]);
                                }
                            };
                            setTimeout(function() {
                                //set css for headers
                                //setHeadersCss($this[0], $headers, config.sortList, sortCSS);
                                setHeadersArrow($this[0], $headers, config.sortList, sortArrow);
                                appendToTable($this[0], multisort($this[0], config.sortList, cache));
                            },
                            1);
                            // stop normal event by returning false
                            return false;
                        }
                        // cancel selection 
                    }).mousedown(function() {
                        if (config.cancelSelection) {
                            this.onselectstart = function() {
                                return false
                            };
                            return false;
                        }
                    });
                    // apply easy methods that trigger binded events
                    $this.bind("update",
                    function() {
                        // rebuild parsers.
                        this.config.parsers = buildParserCache(this, $headers);
                        // rebuild the cache map
                        cache = buildCache(this);
                    }).bind("sorton",
                    function(e, list) {
                        $(this).trigger("sortStart");
                        config.sortList = list;
                        // update and store the sortlist
                        var sortList = config.sortList;
                        // update header count index
                        updateHeaderSortCount(this, sortList);
                        //set css for headers
                        //setHeadersCss(this, $headers, sortList, sortCSS);
                        setHeadersArrow(this, $headers, sortList, sortArrow);
                        // sort the table and append it to the dom
                        appendToTable(this, multisort(this, sortList, cache));
                    }).bind("appendCache",
                    function() {
                        appendToTable(this, cache);
                    }).bind("applyWidgetId",
                    function(e, id) {
                        getWidgetById(id).format(this);
                    }).bind("applyWidgets",
                    function() {
                        // apply widgets
                        applyWidget(this);
                    });
                    if ($.metadata && ($(this).metadata() && $(this).metadata().sortlist)) {
                        config.sortList = $(this).metadata().sortlist;
                    }
                    // if user has supplied a sort list to constructor.
                    if (config.sortList.length > 0) {
                        $this.trigger("sorton", [config.sortList]);
                    }
                    // apply widgets
                    applyWidget(this);
                });
            };

            this.addParser = function(parser) {
                var l = parsers.length,
                a = true;
                for (var i = 0; i < l; i++) {
                    if (parsers[i].id.toLowerCase() == parser.id.toLowerCase()) {
                        a = false;
                    }
                }
                if (a) {
                    parsers.push(parser);
                };
            };

            this.addWidget = function(widget) {
                widgets.push(widget);
            };

            this.formatFloat = function(s) {
                var i = parseFloat(s);
                return (isNaN(i)) ? 0 : i;
            };
            this.formatInt = function(s) {
                var i = parseInt(s);
                return (isNaN(i)) ? 0 : i;
            };

            this.isDigit = function(s, config) {
                var DECIMAL = '\\' + config.decimal;
                var exp = '/(^[-+]?0(' + DECIMAL + '0+)?$)|(^([-+]?[1-9][0-9]*)$)|(^([-+]?((0?|[1-9][0-9]*)' + DECIMAL + '([0-9]*)))$)|(^[-+]?[1-9]+[0-9]*' + DECIMAL + '0+$)/';
                //alert(exp);
                return RegExp(exp).test($.trim(s));
            };

            this.clearTableBody = function(table) {
                if ($.browser.msie) {
                    function empty() {
                        while (this.firstChild) this.removeChild(this.firstChild);
                    }
                    empty.apply(table.tBodies[0]);
                } else {
                    table.tBodies[0].innerHTML = "";
                }
            };
        }
    });

    // extend plugin scope
    $.fn.extend({
        tablesorter: $.tablesorter.construct
    });

    var ts = $.tablesorter;

    // add default parsers
    ts.addParser({
        id: "text",
        is: function(s) {
            return true;
        },
        format: function(s) {
            return $.trim(s.toLowerCase());
        },
        type: "text"
    });

    ts.addParser({
        id: "digit",
        is: function(s, table) {
            var c = table.config;
            return $.tablesorter.isDigit(s, c);
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s);
        },
        type: "numeric"
    });

    ts.addParser({
        id: "summary",
        is: function(s, table) {
            return /^[0-9\.\,\-]*$/.test($.trim(s));
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s.replace(new RegExp(/[^0-9\.\-]/g), ""));
        },
        type: "numeric"
    });

    ts.addParser({
        id: "currency",
        is: function(s) {
            return /^[0-9.]/.test(s);
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s.replace(new RegExp(/[^0-9.]/g), ""));
        },
        type: "numeric"
    });

    ts.addParser({
        id: "ipAddress",
        is: function(s) {
            return /^\d{2,3}[\.]\d{2,3}[\.]\d{2,3}[\.]\d{2,3}$/.test(s);
        },
        format: function(s) {
            var a = s.split("."),
            r = "",
            l = a.length;
            for (var i = 0; i < l; i++) {
                var item = a[i];
                if (item.length == 2) {
                    r += "0" + item;
                } else {
                    r += item;
                }
            }
            return $.tablesorter.formatFloat(r);
        },
        type: "numeric"
    });

    ts.addParser({
        id: "url",
        is: function(s) {
            return /^(https?|ftp|file):\/\/$/.test(s);
        },
        format: function(s) {
            return jQuery.trim(s.replace(new RegExp(/(https?|ftp|file):\/\//), ''));
        },
        type: "text"
    });

    ts.addParser({
        id: "isoDate",
        is: function(s) {
            return /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(s);
        },
        format: function(s) {
            return $.tablesorter.formatFloat((s != "") ? new Date(s.replace(new RegExp(/-/g), "/")).getTime() : "0");
        },
        type: "numeric"
    });

    ts.addParser({
        id: "percent",
        is: function(s) {
            return /\%$/.test($.trim(s));
        },
        format: function(s) {
            return $.tablesorter.formatFloat(s.replace(new RegExp(/%/g), ""));
        },
        type: "numeric"
    });

    ts.addParser({
        id: "usLongDate",
        is: function(s) {
            return s.match(new RegExp(/^[A-Za-z]{3,10}\.? [0-9]{1,2}, ([0-9]{4}|'?[0-9]{2}) (([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(AM|PM)))$/));
        },
        format: function(s) {
            return $.tablesorter.formatFloat(new Date(s).getTime());
        },
        type: "numeric"
    });

    ts.addParser({
        id: "shortDate",
        is: function(s) {
            return /\d{1,2}[\/\-]\d{1,2}[\/\-]\d{2,4}/.test(s);
        },
        format: function(s, table) {
            var c = table.config;
            s = s.replace(/\-/g, "/");
            if (c.dateFormat == "us") {
                // reformat the string in ISO format
                s = s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/, "$3/$1/$2");
            } else if (c.dateFormat == "uk") {
                //reformat the string in ISO format
                s = s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/, "$3/$2/$1");
            } else if (c.dateFormat == "dd/mm/yy" || c.dateFormat == "dd-mm-yy") {
                s = s.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{2})/, "$1/$2/$3");
            }
            return $.tablesorter.formatFloat(new Date(s).getTime());
        },
        type: "numeric"
    });

    ts.addParser({
        id: "time",
        is: function(s) {
            return /^(([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(am|pm)))$/.test(s);
        },
        format: function(s) {
            return $.tablesorter.formatFloat(new Date("2000/01/01 " + s).getTime());
        },
        type: "numeric"
    });

    ts.addParser({
        id: "metadata",
        is: function(s) {
            return false;
        },
        format: function(s, table, cell) {
            var c = table.config,
            p = (!c.parserMetadataName) ? 'sortValue': c.parserMetadataName;
            return $(cell).metadata()[p];
        },
        type: "numeric"
    });

    ts.addWidget({
        id: "zebra",
        format: function(table) {
            if (table.config.debug) {
                var time = new Date();
            }
            $("tr:visible", table.tBodies[0]).filter(':even').removeClass(table.config.widgetZebra.css[1]).addClass(table.config.widgetZebra.css[0]).end().filter(':odd').removeClass(table.config.widgetZebra.css[0]).addClass(table.config.widgetZebra.css[1]);
            if (table.config.debug) {
                $.tablesorter.benchmark("Applying Zebra widget", time);
            }
        }
    });

    //row Highlight widgets: ['rowHover']
    ts.addWidget({
        id: "rowHover",
        format: function(table) {
            $("tbody tr", table).hover(
            function() {
                $(this).addClass("t3");
            },
            function() { 
                $(this).removeClass("t3");
            });
        }
    });

    var $drag = $("<div></div>");
    $drag.css({
        "textAlign": "center",
        "position": "absolute",
        "cursor": "move",
        "border": "1 solid #5fb679",
        "display": "none",
        "zIndex": "999"
    });
    $("body").append($drag);
    var drag = $drag[0];
    var dragStart = false;
    var dragColStart = null;
    var dragColEnd = null;
    var $arrowLeft = $("<span>3</span>");
    $arrowLeft.css({
        "float": "left",
        "padding": "0px",
        "marginTop": "-3px",
        "marginBottom": "2px",
        "marginLeft": "0px",
        "marginRight": "-20px",
        "fontSize": "10px",
        "overflow": "hidden",
        "color": "red",
        "fontFamily": "webdings",
        "width": "10px",
        "height": "15px"
    });
    ts.addWidget({
        id: "moveRow",
        format: function(table) {
            $("thead th", table).mousedown(function(ev) {
                document.onselectstart = function() {
                    return false;
                }
                //dragStart = true;
                event= ev || window.event; 0
                dragColStart = this.cellIndex;
                drag.style.width = this.offsetWidth;
                drag.style.height = this.offsetHeight;
                var clickX = event.x;
                var clickY = event.y;
                if (dragStart) {
                    $(document).mousemove(function() {
                        if ((Math.abs(event.x - clickX) > 5) || (Math.abs(event.y - clickY) > 5)) {
                            drag.style.display = "";
                            drag.style.top = event.y - drag.offsetHeight / 2;
                            drag.style.left = event.x - drag.offsetWidth / 2;
                            for (var i = 0; i < table.rows[0].cells.length; i++) {
                                var cell = table.rows[0].cells[i];
                                var children = cell.childNodes;
                                if ((event.y > cell.offsetTop + getAbsPosY(table.rows[0]) - 100 && event.y < cell.offsetTop + cell.offsetHeight + getAbsPosY(table.rows[0]) + 100) && (event.x > cell.offsetLeft + parseInt(document.body.currentStyle.marginLeft) && event.x < cell.offsetLeft + cell.offsetWidth + parseInt(document.body.currentStyle.marginLeft))) {
                                    cell.runtimeStyle.background = "";
                                    cell.runtimeStyle.backgroundColor = "#FCEECD";
                                    cell.insertBefore($arrowLeft[0], children[0]);
                                    dragColEnd = cell.cellIndex;
                                } else {
                                    //cell.runtimeStyle.background = "url(htmlframe/images/er/th_bg.jpg) repeat-x 50% top";
                                    //cell.runtimeStyle.backgroundColor = "";
                                }
                            }
                            if (! (event.y > table.rows[0].offsetTop + getAbsPosY(table.rows[0]) - 100 && event.y < table.rows[0].offsetTop + table.rows[0].offsetHeight + getAbsPosY(table.rows[0]) + 100)) dragColEnd = null;
                        }
                    });
                }
                drag.innerHTML = this.innerHTML;
                //drag.style.backgroundColor = this.currentStyle.backgroundColor;
                //drag.style.color = this.currentStyle.color;
            });
            $(document).mouseup(function() { //榧犳爣鏉惧紑
                if (dragStart) {
                    dragStart = false;
                    drag.style.display = "none";
                    drag.innerHTML = "";
                    drag.style.width = 0;
                    drag.style.height = 0;
                    for (var i = 0; i < table.rows[0].cells.length; i++) {
                        //table.rows[0].cells[i].runtimeStyle.background = "url(htmlframe/images/er/th_bg.jpg) repeat-x 50% top";
                        //table.rows[0].cells[i].runtimeStyle.backgroundColor = "";
                    }
                    if (dragColStart != null && dragColEnd != null && dragColStart != dragColEnd) {
                        change_col(table, dragColStart, dragColEnd);
                    }
                    dragColStart = null;
                    dragColEnd = null;
                    $(document).unbind("mousemove");
                    document.onselectstart = function() {
                        return true;
                    }
                }
            });
        }
    });
})(jQuery);

function getAbsPosY(pTarget) {
    var y = 0;
    if (pTarget.style.position != "absolute") {
        while (pTarget.offsetParent) {
            y += pTarget.offsetTop;
            pTarget = pTarget.offsetParent;
        }
    }
    y += pTarget.offsetTop;
    return y;
}


function change_col(the_tab, line1, line2) {
    var l,i;
    if (line1 < line2) {
        for (l = line1; l < line2 - 1; l++) {
            for (i = 0; i < the_tab.rows.length; i++) {
                the_tab.rows[i].cells[l].swapNode(the_tab.rows[i].cells[l + 1]);
            }
        }
    } else if (line1 > line2) {
        for (l = line1; l > line2; l--) {
            for (i = 0; i < the_tab.rows.length; i++) {
                the_tab.rows[i].cells[l].swapNode(the_tab.rows[i].cells[l - 1]);
            }
        }
    }
}

$("document").ready(function() {
		try{
		    $("#tablist").tablesorter({
		        widgets: ['zebra', 'rowHover', 'moveRow']
		    });
    }catch(e){}
});