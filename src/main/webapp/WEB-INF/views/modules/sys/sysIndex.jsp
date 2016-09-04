<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="blank"/>

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

            // 绑定菜单单击事件
            $("#menu a.menu").click(function () {
                // 一级菜单焦点
                $("#menu li.menu").removeClass("active");
                $(this).parent().addClass("active");
                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame") {
                    $("#left,#openClose").hide();
                    wSizeWidth();
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
            $("#menu a.menu:first span").click();

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
                <li><a href="#" target="_blank" title="访问网站主页"><span class="glyphicon glyphicon-home"></span></a></li>
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
        <div id="content" class="row">
            <div id="left">
            </div>
            <div id="openClose" class="close">&nbsp;</div>
            <div id="right">
                <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
            </div>
        </div>
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