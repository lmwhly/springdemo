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

        function queryTable() {
            //先销毁表格
            $('#empUserList').bootstrapTable('destroy');
            querys();
        }


        function querys() {
            $("#edit").attr({"disabled": "disabled"});
            $("#delete").attr({"disabled": "disabled"});
            $("#empUserList").bootstrapTable({
                url: '${ctx}/sys/user/testdata',
                method: "post",  //请求方式（*）
                dataType: "json",
                height: $(window).height - 200,
                singleSelect: true,//复选框只能选择一条记录
                sortable: true,      //是否启用排序
                sortOrder: "asc",     //排序方式
                search: true,

                undefinedText: '-',
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: 'limit',
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
                ],

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
        function queryParams(params) {
            var temp = {
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                order: params.order,//排位命令（desc，asc）
                search:params.search,
                sort:params.sort,
                departmentname: $("#txt_search_departmentname").val(),

            };
            return temp;
        }


        function responseHandler(res) {

//分页后的返回值， 是有格式要求的，必须满足如下格式
            /*           "total": 500,
             "rows": [{},{}.....]*/


            if (res.IsOk) {
                return {
                    total: res.total,
                    rows: res.records
                };
            } else {
                return {
                    "rows": [],
                    "total": 0
                };
            }

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
                        <button type="button" style="margin-left:50px" id="btn_query" onclick="queryTable()"
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












