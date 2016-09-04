<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="panel-group" id="menu-${param.parentId}">
    <c:set var="menuList" value="${fns:getMenuList()}"/>
    <c:set var="firstMenu" value="true"/>
    <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
        <c:if test="${menu.parent.id eq (not empty param.parentId ? param.parentId:1)&&menu.isShow eq '1'}">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu-${param.parentId}"
                       data-href="#collapse-${menu.id}" href="#collapse-${menu.id}" title="${menu.remarks}">
                        <span class="glyphicon glyphicon-chevron-${not empty firstMenu && firstMenu ? 'down' : 'right'}">&nbsp;${menu.name}</span>
                    </a>
                </div>
                <div id="collapse-${menu.id}" class="panel-collapse collapse ${not empty firstMenu && firstMenu ? 'in' : ''}">
                    <div class="panel-body">
                        <ul class="nav nav-pills nav-stacked">
                            <c:forEach items="${menuList}" var="menu2">
                                <c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
                                    <li class="menu">
                                        <a data-href=".menu3-${menu2.id}"
                                           href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}"
                                           target="${not empty menu2.target ? menu2.target : 'mainFrame'}">
                                            <span class="glyphicon glyphicon-${not empty menu2.icon ? menu2.icon : 'circle-arrow-right'}">&nbsp;${menu2.name}</span>
                                        </a>
                                        <ul class="nav nav-pills nav-stacked hide" style="margin:0;padding-right:0;">
                                            <c:forEach items="${menuList}" var="menu3">
                                                <c:if test="${menu3.parent.id eq menu2.id&&menu3.isShow eq '1'}">
                                                    <li class="menu3-${menu2.id} hide">
                                                        <a href="${fn:indexOf(menu3.href, '://') eq -1 ? ctx : ''}${not empty menu3.href ? menu3.href : '/404'}"
                                                           target="${not empty menu3.target ? menu3.target : 'mainFrame'}">
                                                            <span class="glyphicon glyphicon-${not empty menu3.icon ? menu3.icon : 'circle-arrow-right'}">&nbsp;${menu3.name}</span>
                                                        </a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                    <c:set var="firstMenu" value="false"/>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>