
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="blank"/>
    <c:set var="tabmode" value="${empty cookie.tabmode.value ? '0' : cookie.tabmode.value}"/>
    <c:if test="${tabmode eq '1'}">
        <link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css"/>
        <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
    </c:if>


    <script src='${ctxStatic}/portal/dashboard.js' type='text/javascript'></script>
    <link rel='stylesheet' href='${ctxStatic}/portal/dashboard.css' type='text/css' media='screen'/>
    <script type="text/javascript" src="${ctxStatic}/portal/portal.js"></script>


    <style type="text/css">
        #main {
            padding: 0;
            margin: 0;
        }

        #header {
            margin: 0 0 8px;
            position: static;
        }

        #header li {
            font-size: 14px;
            _font-size: 12px;
        }

        #footer {
            margin: 8px 0 0 0;
            padding: 3px 0 0 0;
            font-size: 11px;
            text-align: center;
            border-top: 2px solid #0663A2;
        }

        #footer, #footer a {
            color: #999;
        }

        #left {
            overflow-x: hidden;
            overflow-y: auto;
        }

        #userControl > li > a { /*color:#fff;*/
            text-shadow: none;
        }

        #userControl > li > a:hover, #user #userControl > li.open > a {
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

            // <c:if test="${tabmode eq '1'}"> 初始化页签
            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: {'height': $('#right').height() - tabTitleHeight},
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });
            //</c:if>

            // 绑定菜单单击事件
            $("#menu a.menu").click(function () {
                $("#content").show();
                $("#dashboard").hide();


                // 一级菜单焦点
                $("#menu li.menu").removeClass("active");
                $(this).parent().addClass("active");
                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame") {
                    $("#left,#openClose").hide();
                    wSizeWidth();

                    // <c:if test="${tabmode eq '1'}"> 隐藏页签
                    $(".jericho_tab").hide();
                    $("#mainFrame").show();//</c:if>

                    return true;
                }
                // 左侧区域显示
                $("#left,#openClose").show();

                if (!$("#openClose").hasClass("close")) {
                    $("#openClose").click();
                }

                // 显示二级菜单
                var menuId = "#menu-" + $(this).attr("data-id");
                if ($(menuId).length > 0) {
                    $("#left .panel-group").hide();
                    $(menuId).show();
                    // 初始化点击第一个二级菜单
                    if (!$(menuId + " .panel-collapse:first").hasClass('in')) {
                        $(menuId + " .panel-heading:first a").click();
                    }
                    if (!$(menuId + " .panel-collapse li:first ul:first").is(":visible")) {
                        $(menuId + " .panel-collapse a:first span").click();
                    }
                    // 初始化点击第一个三级菜单
                    $(menuId + " .panel-collapse li:first li:first a:first span").click();
                } else {
                    // 获取二级菜单数据
                    $.get($(this).attr("data-href"), function (data) {
                        if (data.indexOf("id=\"loginForm\"") != -1) {
                            alert('未登录或登录超时。请重新登录，谢谢！');
                            top.location = "${ctx}";
                            return false;
                        }
                        $("#left .panel-group").hide();
                        $("#left").append(data);
                        // 链接去掉虚框
                        $(menuId + " a").bind("focus", function () {
                            if (this.blur) {
                                this.blur()
                            }
                            ;
                        });
                        // 二级标题
                        $(menuId + " .panel-heading a").click(function () {
                            $(menuId + " .accordion-toggle span").removeClass('glyphicon glyphicon-chevron-down').addClass('glyphicon glyphicon-chevron-right');
                            if (!$($(this).attr('data-href')).hasClass('in')) {
                                $(this).children("span").removeClass('glyphicon glyphicon-chevron-right').addClass('glyphicon glyphicon-chevron-down');
                            }
                        });
                        // 二级内容
                        $(menuId + " .panel-collapse a").click(function () {
                            $(menuId + " li").removeClass("active");
                            $(this).parent().addClass("active");
                        });
                        // 展现三级
                        $(menuId + " .panel-body a").click(function () {
                            var href = $(this).attr("data-href");
                            if ($(href).length > 0) {
                                $(href).toggle().parent().toggle();
                                return false;
                            }

                            // <c:if test="${tabmode eq '1'}"> 打开显示页签
                            return addTab($(this)); // </c:if>

                        });
                        // 默认选中第一个菜单
                        $(menuId + " .panel-collapse a:first span").click();
                        $(menuId + " .panel-collapse li:first li:first a:first span").click();
                    });
                }
                // 大小宽度调整
                wSizeWidth();
                return false;
            });
            // 初始化点击第一个一级菜单
//            $("#menu a.menu:first span").click();

            // <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
            $("#userInfo .dropdown-menu a").mouseup(function () {
                return addTab($(this), true);
            });// </c:if>

            // 鼠标移动到边界自动弹出左侧菜单
            $("#openClose").mouseover(function () {
                if ($(this).hasClass("open")) {
                    $(this).click();
                }
            });
            // 获取通知数目  <c:set var="oaNotifyRemindInterval" value="${fns:getConfig('oa.notify.remind.interval')}"/>
            function getNotifyNum() {
                $.get("${ctx}/oa/oaNotify/self/count?updateSession=0&t=" + new Date().getTime(), function (data) {
                    var num = parseFloat(data);
                    if (num > 0) {
                        $("#notifyNum,#notifyNum2").show().html("(" + num + ")");
                    } else {
                        $("#notifyNum,#notifyNum2").hide()
                    }
                });
            }

            getNotifyNum();
            //<c:if test="${oaNotifyRemindInterval ne '' && oaNotifyRemindInterval ne '0'}">
            setInterval(getNotifyNum, ${oaNotifyRemindInterval});
            //</c:if>
        });


        // <c:if test="${tabmode eq '1'}"> 添加一个页签
        function addTab($this, refresh) {
            $(".jericho_tab").show();
            $("#mainFrame").hide();
            $.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
            return false;
        }// </c:if>

    </script>
</head>
<body>
<div id="main">
    <nav id="header" class="navbar navbar-default navbar-fixed-top" >
        <div class="container-fluid">
            <div class="navbar-header">
                <span id="productName" class="navbar-brand">${fns:getConfig('productName')}</span>
            </div>
            <ul id="menu" class="nav navbar-nav navbar-left" style="*white-space:nowrap;float:none;">
                <c:set var="firstMenu" value="true"/>
                <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                    <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
                        <li class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
                            <c:if test="${empty menu.href}">
                                <a class="menu" href="javascript:" data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" data-id="${menu.id}"><span>${menu.name}</span></a>
                            </c:if>
                            <c:if test="${not empty menu.href}">
                                <a class="menu" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame"><span>${menu.name}</span></a>
                            </c:if>
                        </li>
                        <c:if test="${firstMenu}">
                            <c:set var="firstMenuId" value="${menu.id}"/>
                        </c:if>
                        <c:set var="firstMenu" value="false"/>
                    </c:if>
                </c:forEach>
            </ul>

            <ul id="userControl" class="nav navbar-nav navbar-right">
                <%--<li><a href="#" target="_blank" title="访问网站主页"><span class="glyphicon glyphicon-home"></span></a></li>--%>
                <li><a href="javascript:location.reload(); " target="_self" title="访问网站主页"><span class="glyphicon glyphicon-home"></span></a></li>


                <li id="themeSwitch" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="页签模式">
                        <span class="glyphicon glyphicon-th-large"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="javascript:cookie('tabmode','${tabmode eq '1' ? '0' : '1'}');location=location.href">${tabmode eq '1' ? '关闭' : '开启'}页签模式</a>
                        </li>
                        <li>
                            <a href="javascript:insertWidget();">添加控制面板</a>
                        </li>
                    </ul>
                </li>

                <li id="userInfo" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${ctx}/sys/user/info" target="mainFrame">
                                <span class="glyphicon glyphicon-user"></span>&nbsp; 个人信息
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/sys/user/modifyPwd" target="mainFrame">
                                <span class="glyphicon glyphicon-lock"></span>&nbsp; 修改密码
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/oa/oaNotify/self" target="mainFrame">
                                <span class="glyphicon glyphicon-bell"></span>&nbsp; 我的通知
                                <span id="notifyNum2" class="label label-info hide"></span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li><a href="${ctx}/logout" title="退出登录">退出</a></li>
                <li>&nbsp;</li>
            </ul>
        </div>
    </nav>
    <div class="container-fluid">
        <div id="content" class="row" hidden>
            <div id="left">
            </div>
            <div id="openClose" class="close">&nbsp;</div>
            <div id="right">
                <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
            </div>
        </div>


        <!-- dashboard begin -->
        <div data-height="300" class="container-fluid dashboard dashboard-draggable" id="dashboard" style="margin-top:70px;">
            <header></header>
            <section class="row">
                <c:forEach items="${map}" var="entry">
                    <div class="portal-col col-md-4 col-sm-6" data-id="${entry.key}" data-order="${entry.key}">
                        <c:forEach items="${entry.value}" var="item">
                            <div data-id="${item.id}" class="portlet" data-order="${item.rowIndex}">
                                <div data-url="${ctxStatic}${item.portalWidget.url}" class="panel panel-default" id="panel${item.id}"
                                     data-id="${item.id}">
                                    <div class="panel-heading">
                                        <div class="panel-actions">
                                            <button class="btn btn-sm refresh-panel"><i class="glyphicon glyphicon-refresh"></i>
                                            </button>
                                            <div class="dropdown">
                                                <button data-toggle="dropdown" class="btn btn-sm" role="button"><span
                                                        class="caret"></span></button>
                                                <ul aria-labelledby="dropdownMenu1" role="menu" class="dropdown-menu">
                                                    <li>
                                                        <a href="javascript:void(0);updateWidget('${item.id}','${item.portalWidget.id}','${item.name}')"><i
                                                                class="glyphicon glyphicon-pencil"></i> 编辑</a></li>
                                                    <li><a class="remove-panel" href="#"><i
                                                            class="glyphicon glyphicon-remove"></i> 移除</a></li>
                                                </ul>
                                            </div>
                                        </div>
                                        <i class="glyphicon glyphicon-list"></i> ${item.name}
                                    </div>
                                    <div class="panel-body">
                                        <table class="table table-hover">
                                            <thead>
                                            <tr>
                                                <th>编号</th>
                                                <th>名称</th>
                                                <th>创建时间</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${personalTasks.result}" var="item">
                                                <tr>
                                                    <td>${item.id}</td>
                                                    <td>${item.name}</td>
                                                    <td><fmt:formatDate value="${item.createTime}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                    <td>
                                                        <a href="${ctx}/operation/task-operation-viewTaskForm.do?humanTaskId=${item.id}"
                                                           class="btn btn-xs btn-primary">处理</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>

            </section>
        </div>

        <div id="widgetModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">编辑组件</h4>
                    </div>
                    <div class="modal-body">
                        <form id="widgetForm" action="${ctx}/save" method="post">
                            <input id="portalItemId" type="hidden" name="id" value="">
                            <div class="form-group">
                                <label for="portalWidgetId">组件</label>
                                <select id="portalWidgetId" class="form-control" name="portalWidgetId">
                                    <c:forEach items="${portalWidgets}" var="item">
                                        <option value="${item.id}">${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="portalItemName">标题</label>
                                <input id="portalItemName" class="form-control" type="text" value="" name="portalItemName">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" onclick="$('#widgetForm').submit();">保存</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- dashboard end -->



        <div id="footer" class="row">
            Copyright &copy; ${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="http://luoo.com" target="_blank">MyWork</a> ${fns:getConfig('version')}
        </div>
    </div>
</div>
<script type="text/javascript">
    var leftWidth = 160; // 左侧窗口大小
    var tabTitleHeight = 33; // 页签的高度
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $("#header"), footerObj = $("#footer");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize() {
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({
            "overflow-x": strs[1] < minWidth ? "auto" : "hidden",
            "overflow-y": strs[0] < minHeight ? "auto" : "hidden"
        });
        mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
        $("#openClose").height($("#openClose").height() - 5);
        wSizeWidth();
    }
    function wSizeWidth() {
        if (!$("#openClose").is(":hidden")) {
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
            $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 5);
        } else {
            $("#right").width("100%");
        }
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>