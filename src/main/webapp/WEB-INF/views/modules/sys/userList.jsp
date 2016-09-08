<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">


        $(document).ready(function () {
            querys();
        });


        function querys() {
            $("#edit").attr({"disabled": "disabled"});
            $("#delete").attr({"disabled": "disabled"});
            $("#empUserList").bootstrapTable({
                url: '${ctx}/sys/user/testdata',
                method: "post",
                height: '500',
                undefinedText: '-',
                striped: true, // 是否显示行间隔色
                queryParams: queryParams,
                cache: false, // 是否使用缓存
                toolbar: "#toolbar",// 指定工具栏
                showColumns: true, // 显示隐藏列
                showRefresh: true, // 显示刷新按钮
                uniqueId: "id", // 每一行的唯一标识
                pagination: true, // 分页
                sidePagination: "server", // 服务端处理分页
                pageNumber: 1,//默认加载页
                pageSize: 1,//每页数据
                pageList: [2, 4, 8],
                columns: [

                    {
                        field: 'state',
                        checkbox: true,
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        title: 'id',
                        field: 'id', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle', //
                        sortable: true
                    }, {
                        title: '用户名',
                        field: 'name', // 字段
                        align: 'center', // 对齐方式（左 中 右）
                        valign: 'middle', //
                        sortable: true
                    }, {
                        title: '状态',
                        field: 'isNewRecord',
                        align: 'center',
                        valign: 'middle',
                        formatter: genderFormatter,
                        sortable: true
                    }

                    /*{
                     field: 'state',
                     checkbox: true,
                     align: 'center',
                     valign: 'middle'
                     }, {
                     title: '用户名',
                     field: 'userName', // 字段
                     align: 'center', // 对齐方式（左 中 右）
                     valign: 'middle', //
                     sortable: true
                     }, {
                     title: '用户编号',
                     field: 'empNo',
                     align: 'center',
                     valign: 'middle',
                     sortable: true
                     }, {
                     title: '姓名',
                     field: 'empName',
                     align: 'center',
                     valign: 'middle',
                     sortable: true
                     }, {
                     title: '职位',
                     field: 'position',
                     align: 'center',
                     valign: 'middle',
                     sortable: true
                     }, {
                     title: '状态',
                     field: 'isDelete',
                     align: 'center',
                     valign: 'middle',
                     formatter: genderFormatter,
                     sortable: true
                     }*/
                ],
                responseHandler: function (res) {
                    return {
                        total: res.total,
                        rows: res.records
                    };
                },
                onCheck: function () {
                    buttonControl('#empUserList', '#edit', '#delete');
                },
                onCheckAll: function () {
                    buttonControl('#empUserList', '#edit', '#delete');
                },
                onUncheckAll: function () {
                    buttonControl('#empUserList', '#edit', '#delete');
                },
                onUncheck: function () {
                    buttonControl('#empUserList', '#edit', '#delete');
                }
            })
        }
        /** 替换数据为文字 */
        function genderFormatter(value) {
            if (value == null || value == undefined) {
                return "-";
            } else if (value == 1) {
                return "已删除";
            } else if (value == 0) {
                return "正常";
            }
        }
        /** 刷新页面 */
        function refresh() {
            $('#empUserList').bootstrapTable('refresh');
        }
        /**查询条件与分页数据 */
        function queryParams(pageReqeust) {
//            pageReqeust.enabled = $("#enabled").val();
//            pageReqeust.querys = $("#querys").val();

            pageReqeust.pageSize = this.limit;
            pageReqeust.offset = this.offset;


            return pageReqeust;
        }
        /** 编辑数据 */
        /*function editHr() {
         var selectRow = $("#empUserList").bootstrapTable('getSelections');
         if (selectRow.length != 1) {
         layer.alert('请选择并只能选择一条数据进行编辑！', {icon: 2});
         return false;
         } else {
         window.location = createUrl("admin/hrEmployee/view?username=" + selectRow[0].userName);
         }
         }*/
        /**
         * 删除数据
         */
        /*function deleteHr() {
         var hrs = $("#empUserList").bootstrapTable('getSelections');
         if (hrs.length < 1) {
         layer.alert('请选择一条或多条数据进行删除！', {icon: 2});
         } else {
         layer.confirm('确定要删除所选数据？', {icon: 3, title:'提示'}, function(){
         var userNames = [];
         for (var i=0;i<hrs.length;i++){
         userNames.push(hrs[i].userName);
         }
         $.ajax({
         url:'../../../admin/hrEmployee/delete',
         traditional: true,  //阻止深度序列化，向后台传送数组
         data:{userNames:userNames},
         contentType:'application/json',
         success:function(msg){
         if(msg.success){
         layer.alert(msg.msg,{icon:1});
         }else{
         layer.alert(msg.msg,{icon:2});
         }
         refresh();
         $("#edit").attr({"disabled":"disabled"});
         $("#delete").attr({"disabled":"disabled"});
         }
         })
         });
         }
         }*/

    </script>
</head>
<body>
<div class="panel-body" style="padding-bottom:0px;">
    <div class="panel panel-default">
        <div class="panel-heading">查询条件</div>
        <div class="panel-body">
            <form id="formSearch" class="form-horizontal">
                <div class="form-group" style="margin-top:15px">
                    <label class="control-label col-sm-1" for="txtStartDate">开始时间</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="txtStartDate">
                    </div>
                    <label class="control-label col-sm-1" for="txtEndDate">结束时间</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="txtEndDate">
                    </div>
                    <label class="control-label col-sm-1" for="txtMerName">名称</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="txtMerName">
                    </div>
                    <div class="col-sm-4" style="text-align:left;">
                        <button type="button" style="margin-left:50px" id="btn_query" onclick="initTable()"
                                class="btn btn-primary">查询
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>


    <div class="container-fluid">
        <div id="toolbar" class="btn-group">
            <a href="<@url value='/view/user/emp/add' />">
                <button type="button" id="add" class="btn btn-default">
                    <i class="glyphicon glyphicon-plus"></i>
                </button>
            </a>
            <a>
                <button onclick="editHr();" type="button" id="edit" class="btn btn-default">
                    <i class="glyphicon glyphicon-pencil"></i>
                </button>
            </a>
            <a>
                <button type="button" onclick="deleteHr();" id="delete" class="btn btn-default">
                    <i class="glyphicon glyphicon-trash"></i>
                </button>
            </a>

            <button id="btn_add" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <button id="btn_edit" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>
            <button id="btn_delete" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
            </button>


        </div>
        <table id="empUserList">`
        </table>  <!-- 留意-->
    </div>
</div>

</body>
</html>












