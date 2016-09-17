<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考核模板管理</title>
    <%--bootstrapTable例子--%>
    <%--http://bootstrap-table.wenzhixin.net.cn/examples/--%>
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

        var templateState = ${fns:getDictListJson("TEMPLATE_STATE")};


        $(function () {
            querys();


            $('#TepDefList').on('click-row.bs.table', function (e, row, $element) {
                        $('.success').removeClass('success');
                        $($element).addClass('success');
                    })
                    .on('dbl-click-row.bs.table', function (e, row) {
                        console.log(row.id);
                        var tepId = row.id
                        showViewByTepId(tepId);
                    })
                    .on('check.bs.table', function (e, row) {

                        var selArys = $('#TepDefList').bootstrapTable('getSelections');
                        if (selArys.length == 1) {
                            $("#edit").removeAttr("disabled");
                            $("#view").removeAttr("disabled");
                            $("#upload").removeAttr("disabled");
                        } else {
                            $("#edit").attr({"disabled": "disabled"});
                            $("#view").attr({"disabled": "disabled"});
                            $("#upload").attr({"disabled": "disabled"});
                        }

                        $("#delete").removeAttr("disabled");
                    })
                    .on('uncheck.bs.table', function (e, row) {
                        var selArys = $('#TepDefList').bootstrapTable('getSelections');
                        if (selArys.length == 0) {
                            $("#delete").attr({"disabled": "disabled"});
                        }

                        if (selArys.length == 1) {
                            $("#edit").removeAttr("disabled");
                            $("#view").removeAttr("disabled");
                            $("#upload").removeAttr("disabled");
                        } else {
                            $("#edit").attr({"disabled": "disabled"});
                            $("#view").attr({"disabled": "disabled"});
                            $("#upload").attr({"disabled": "disabled"});
                        }
                    })
                    .on('check-all.bs.table', function (e, rows) {
                        $("#delete").removeAttr("disabled");
                    })
                    .on('uncheck-all.bs.table', function (e) {

                        $("#edit").attr({"disabled": "disabled"});
                        $("#view").attr({"disabled": "disabled"});
                        $("#upload").attr({"disabled": "disabled"});
                        $("#delete").attr({"disabled": "disabled"});
                    })
                    .on('load-success.bs.table', function (e, data) {
                        layer.msg("加载成功");
                    })
                    .on('load-error.bs.table', function (e, status) {
                        layer.msg("加载数据失败", {time: 1500, icon: 2});
                    })
                    .on('page-change.bs.table', function (e, number, size) {
                        console.log(e, number, size);
                    })


        });



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


        function nameFormatter(value) {
            return '<a href="${ctx}/sys/sysStaffAssessTemplateDefine/form?id=' + value + '">' + value + '</a>';
        }

        function dateFormatter(value) {
            <%--return "<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='" + value1 + "'/>";--%>
            return value;
        }

        function stateFormatter(value) {
            for(var i=0;i<templateState.length;i++) {
                if(value == templateState[i].value) {
                    return templateState[i].label;
                }
            }
        }


        function querys() {
            $("#edit").attr({"disabled": "disabled"});
            $("#view").attr({"disabled": "disabled"});
            $("#upload").attr({"disabled": "disabled"});
            $("#delete").attr({"disabled": "disabled"});

            $("#TepDefList").bootstrapTable({
                url: '${ctx}/sys/sysStaffAssessTemplateDefine/newlist',
                method: "post",  //请求方式（*）
                dataType: "json",
                height: $(window).height - 200,
                singleSelect: false,//复选框只能选择一条记录
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
                        title: '序号',
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },


                    {
                        title: '模板ID',
                        field: 'templateId', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle',
                        formatter:nameFormatter

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
                        valign: 'middle',
                        formatter:dateFormatter
                    },
                    {
                        title: '修改时间',
                        field: 'doneDate', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle'


                    }, {
                        title: '状态',
                        field: 'state', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle',
                        formatter:stateFormatter
                    }, {
                        title: '操作员ID',
                        field: 'user.name', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle' //
                    }
                    <shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
                    , {
                        title: '操作',
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle',
                        formatter: actionFormatter,
                        events: actionEvents
                    }
                    </shiro:hasPermission>
                ]


            })
        }


        function actionFormatter(value, row, index) {
            return [
                '<a class="like" href="javascript:void(0)" title="Upload">',
                '<i class="glyphicon glyphicon-upload"></i>',
                '</a>',
                '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
                '<i class="glyphicon glyphicon-edit"></i>',
                '</a>',
                '<a class="remove ml10" href="javascript:void(0)" title="Remove">',
                '<i class="glyphicon glyphicon-remove"></i>',
                '</a>'
            ].join('');
        }

        window.actionEvents = {
            'click .Upload': function (e, value, row, index) {
//                alert('You click Upload icon, row: ' + JSON.stringify(row));
                console.log(value, row, index);
                showBoxByTepId(row.templateId);
            },
            'click .edit': function (e, value, row, index) {
//                alert('You click edit icon, row: ' + JSON.stringify(row));

                console.log(value, row, index);
                editTepDefByTepId(row.templateId);
            },
            'click .remove': function (e, value, row, index) {
//                alert('You click remove icon, row: ' + JSON.stringify(row));
                console.log(value, row, index);
                delSingleTepDef(row.templateId);
            }
        };


        function queryTable() {
            //先销毁表格
            $('#TepDefList').bootstrapTable('destroy');
            querys();

        }

        function showBox() {
            var templateId = $('#TepDefList').bootstrapTable('getSelections')[0].id;
            showBoxByTepId(templateId);
        }

        function showBoxByTepId(templateId) {
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

        function showView() {
            var templateId = $('#TepDefList').bootstrapTable('getSelections')[0].id;

            showViewByTepId(templateId);
        }

        function showViewByTepId(templateId) {
            var url = "${ctx}/sys/sysStaffAssessTemplateDefine/TemplateView?templateId=" + templateId;
//            window.open(url);
            window.location.href = url;
        }

        function addTepDef() {
            var url = "${ctx}/sys/sysStaffAssessTemplateDefine/form";
            window.location.href = url;
        }

       function editTepDef() {
            var templateId = $('#TepDefList').bootstrapTable('getSelections')[0].id;

           editTepDefByTepId(templateId);

        }

        function editTepDefByTepId(templateId) {

            var url = "${ctx}/sys/sysStaffAssessTemplateDefine/form?id=" + templateId;

            window.location.href = url;

        }

        function delSingleTepDef(templateId) {
            var url = "${ctx}/sys/sysStaffAssessTemplateDefine/delete?id=" + templateId;

            layer.confirm('确认要删除该考核模板吗？', {icon: 3, title: '提示'}, function () {
                window.location.href = url;
            });
        }

        /**
         * 删除数据
         */
        function delMultiTepDefs() {
            var hrs = $("#TepDefList").bootstrapTable('getSelections');
            if (hrs.length < 1) {
                layer.alert('请选择一条或多条数据进行删除！', {icon: 2});
            } else {
                layer.confirm('确定要删除所选数据？', {icon: 3, title: '提示'}, function () {
                    var ids = [];
                    for (var i = 0; i < hrs.length; i++) {
                        ids.push(hrs[i].id);
                    }
                    $.ajax({
                        url: '${ctx}/sys/sysStaffAssessTemplateDefine/deleteSel',
                        traditional: true,  //阻止深度序列化，向后台传送数组
                        data: {templateIds: ids},
                        contentType: 'application/json',
                        success: function (msg) {
                            queryTable();
                        }
                    })
                });
            }
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
                        <button type="button" style="margin-left:50px" id="btnSubmit"  class="btn btn-primary" onclick="queryTable();">查询</button>
                    </div>
                </div>
            </form:form>
            </div>
        </div>
        <sys:message content="${message}"/>
        <div id="toolbar" class="btn-group">
            <shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
                <a>
                    <button onclick="addTepDef();" type="button" id="add" class="btn btn-default" title="add">
                        <i class="glyphicon glyphicon-plus"></i>
                    </button>
                </a>

                <a>
                    <button type="button" onclick="showBox();" id="upload" class="btn btn-default" title="upload">
                        <i class="glyphicon glyphicon-upload"></i>
                    </button>
                </a>

                <a>
                    <button type="button" id="edit" onclick="editTepDef();" class="btn btn-default" title="edit">
                        <i class="glyphicon glyphicon-pencil"></i>
                    </button>
                </a>

                <a>
                    <button type="button" id="view" onclick="showView();" class="btn btn-default" title="view">
                        <i class="glyphicon glyphicon-book"></i>
                    </button>
                </a>

                <a>
                    <button type="button" onclick="delMultiTepDefs();" id="delete" class="btn btn-default" title="delete">
                        <i class="glyphicon glyphicon-trash"></i>
                    </button>
                </a>
            </shiro:hasPermission>
        </div>
        <table id="TepDefList"></table>
    </div>
</div>
</body>
</html>