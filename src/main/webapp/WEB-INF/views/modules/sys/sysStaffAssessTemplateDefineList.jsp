<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考核模板管理</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">

        .container {
            width: 100%;
        }
        .form-group{
            padding: 5px;
            margin:0px;
        }
        .panel-body {
            padding: 5px;
            margin:0px;
        }

    </style>
    <script type="text/javascript">
        function showBox(templateId) {
            $.ajax({
                type: 'post',
                url: '${ctx}/sys/sysStaffAssessTemplateDefine/SaveTemplateId',
                async: false,
                data: {
                    'templateId': templateId
                },
                success: function (data) {
                    $.jBox($("#importBox").html(), {
                        title: "导入模板Excel", buttons: {"关闭": true},
                        bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
                    });
                }
            });
        }

        function showView(templateId) {
            var url = "${ctx}/sys/sysStaffAssessTemplateDefine/TemplateView?templateId=" + templateId;
            //window.showModalDialog(url);	//,[mainTemplateId,pointType,pContent,scoreInfoObjMap[mainTemplateId],recordId],"scroll:yes;resizable:no;status:no;dialogHeight:700px;dialogWidth:800px");
            window.open(url);
        }

        /*function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }*/

        $(function () {
            querys();
        });

        function queryTable() {
            //先销毁表格
            $('#empUserList').bootstrapTable('destroy');
            querys();

        }

        function querys() {
            $("#edit").attr({"disabled": "disabled"});
            $("#delete").attr({"disabled": "disabled"});
            $("#empUserList").bootstrapTable({
                url: '${ctx}/sys/sysStaffAssessTemplateDefine/newlist',
                method: "post",  //请求方式（*）
                dataType: "json",
                height: $(window).height - 200,
                singleSelect: true,//复选框只能选择一条记录
                sortable: true,      //是否启用排序
                sortOrder: "asc",     //排序方式
//                search: true,
                undefinedText: '-',
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: 'undefined',
                striped: true, // 是否显示行间隔色
                queryParams: queryParams,
                responseHandler: responseHandler,
                toolbar: "#toolbar",// 指定工具栏,工具按钮用哪个容器
                cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                showColumns: true, // 显示隐藏列
                showRefresh: true, // 显示刷新按钮
                clickToSelect: true,    //是否启用点击选中行
                showToggle: true,     //是否显示详细视图和列表视图的切换按钮
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                pagination: true, // 是否显示分页（*）
                sidePagination: "server", // 服务端处理分页
                pageNumber: 1,//初始化加载第一页，默认第一页
                pageSize: 5,//每页的记录行数（*）
                pageList: [10, 20, 50, 100],  //可供选择的每页的行数（*）
                columns: [
                    {
                        field: 'isChecked',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        title: '模板ID',
                        field: 'templateId', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle' //
                    },

                    {
                        title: '模板名称',
                        field: 'templateName', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle' //
                    },
                    {
                        title: '创建时间',
                        field: 'createDate', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle' //
                    },
                    {
                        title: '修改时间',
                        field: 'doneDate', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle'//
                    }, {
                        title: '状态',
                        field: 'state', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle' //
                    },{
                        title: '操作员ID',
                        field: 'user.name', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle' //
                        }
                ],

                onCheck: function () {
                    buttonControl('onCheck');
                },
                onCheckAll: function () {
                    buttonControl('onCheckAll');
                },
                onUncheckAll: function () {
                    buttonControl('onUncheckAll');
                },
                onUncheck: function () {
                    buttonControl('onUncheck');
                }
            })
        }

        function buttonControl(type) {
            switch (type) {
                case "onCheck":
                    $("#edit").removeAttr("disabled");
                    $("#delete").removeAttr("disabled");
                    break;
                case "onCheckAll":
                    $("#edit").removeAttr("disabled");
                    $("#delete").removeAttr("disabled");
                    break;
                case "onUncheckAll":
                    $("#edit").removeAttr("disabled");
                    $("#delete").removeAttr("disabled");
                    break;
                case "onUncheck":
                    $("#edit").attr({"disabled": "disabled"});
                    $("#delete").attr({"disabled": "disabled"});
                    break;
                default:
            }
        }

        /** 刷新页面 */
        function refresh() {
            $('#empUserList').bootstrapTable('refresh', {
                url: "${ctx}/sys/user/data", //重设数据来源
                query: {
                    pageNumber: params.pageNumber,
                    pageSize: params.pageSize,
                    state: $("#state").val(),
                    templateName:$("#templateName").val()
                }//传到后台的参数
            });
        }

        /**查询条件与分页数据 */
        function queryParams(params) {
            var temp = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize,
                state: $("#state").val(),
                templateName:$("#templateName").val()
            };
            return temp;
        }

        function responseHandler(res) {
            return {
                total: res.count,
                rows: res.list
            };
        }
    </script>
</head>
<body>
<div id="importBox" class="hide">
    <form id="importForm" action="${ctx}/sys/sysStaffAssessTemplateDefine/import" method="post"
          enctype="multipart/form-data"
          class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
        <input id="btnImport" class="btn btn-primary" type="submit" value="   导    入   "/>
        <a href="${ctx}/gzf/gzfHouseInfo/import/template">下载模板</a>
    </form>
</div>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="${ctx}/sys/sysStaffAssessTemplateDefine/">考核模板列表</a>
    </li>
    <shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
        <li>
            <a href="${ctx}/sys/sysStaffAssessTemplateDefine/form">考核模板添加</a>
        </li>
    </shiro:hasPermission>
</ul>

<div class="container">
    <div class="panel-body">
        <div class="panel panel-default">
            <div class="panel-heading">
                查询条件
            </div>
            <div class="panel-body">
            <form:form id="searchForm" modelAttribute="sysStaffAssessTemplateDefine" action="${ctx}/sys/sysStaffAssessTemplateDefine/newlist" method="post" class="form-horizontal">
                <input id="pageNo" name="pageNo" type="hidden" value="${pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${pageSize}"/>
                <div class="form-group" >
                    <label class="control-label col-sm-2" for="state">状态：</label>
                    <div class="col-sm-3">
                        <form:select path="state" class="input-medium">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('TEMPLATE_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </div>
                    <label class="control-label col-sm-2  " for="templateName">模板名称：</label>
                    <div class="col-sm-3">
                        <form:input path="templateName" htmlEscape="false" maxlength="256" class="form-control input-medium"/>
                    </div>
                    <div class="col-sm-2">
                        <button type="submit" style="margin-left:50px" id="btnSubmit"  class="btn btn-primary">查询
                        </button>
                    </div>
                </div>
            </form:form>
            </div>
        </div>
        <sys:message content="${message}"/>
        <table id="empUserList"></table>
    </div>
</div>

<%--
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>模板ID</th>
        <th>模板名称</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <th>状态</th>
        <th>操作员ID</th>
        <shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="sysStaffAssessTemplateDefine">
        <tr>
            <td><a href="${ctx}/sys/sysStaffAssessTemplateDefine/form?id=${sysStaffAssessTemplateDefine.id}">
                    ${sysStaffAssessTemplateDefine.templateId}
            </a></td>
            <td>
                    ${sysStaffAssessTemplateDefine.templateName}
            </td>
            <td>
                <fmt:formatDate value="${sysStaffAssessTemplateDefine.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                <fmt:formatDate value="${sysStaffAssessTemplateDefine.doneDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${fns:getDictLabel(sysStaffAssessTemplateDefine.state, 'TEMPLATE_STATE', '')}
            </td>
            <td>
                    ${sysStaffAssessTemplateDefine.user.name}
            </td>
            <shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
                <td>
                    <a href="${ctx}/sys/sysStaffAssessTemplateDefine/form?id=${sysStaffAssessTemplateDefine.templateId}">修改</a>
                    <a href="${ctx}/sys/sysStaffAssessTemplateDefine/delete?id=${sysStaffAssessTemplateDefine.templateId}"
                       onclick="return confirmx('确认要删除该考核模板吗？', this.href)">删除</a>
                    <a id="btnImportLink" href="javascript:void(0)"
                       onclick="showBox(${sysStaffAssessTemplateDefine.templateId})">导入</a>
                    <a id="btnView" href="javascript:void(0)"
                       onclick="showView(${sysStaffAssessTemplateDefine.templateId})">预览</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>--%>
</body>
</html>