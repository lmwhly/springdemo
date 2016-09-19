<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
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
		var oa_notify_type = ${fns:getDictListJson("oa_notify_type")};
		var oa_notify_status = ${fns:getDictListJson("oa_notify_status")};
		var oa_notify_read = ${fns:getDictListJson("oa_notify_read")};


		$(document).ready(function() {
			querys();
		});

		function querys() {
			$("#edit").attr({"disabled": "disabled"});
			$("#view").attr({"disabled": "disabled"});
			$("#upload").attr({"disabled": "disabled"});
			$("#delete").attr({"disabled": "disabled"});

			$("#TepDefList").bootstrapTable({
				url: '${ctx}/oa/oaNotify/newlist',
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
						title: '标题',
						field: 'title', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter:function (value, row, index) {
							return '<a href="${ctx}/oa/oaNotify/${requestScope.oaNotify.self?'view':'form'}?id=' + row.id + '">' + value + '</a>';
						}
					},

					{
						title: '类型',
						field: 'type', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter:function (value, row, index) {
							for(var i=0;i<oa_notify_type.length;i++)
							{
								if(value == oa_notify_type[i].value) return oa_notify_type[i].label;
							}
							return value;
						}
					},
					{
						title: '状态',
						field: 'status', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter:function (value, row, index) {
							for(var i=0;i<oa_notify_status.length;i++)
							{
								if(value == oa_notify_status[i].value) return oa_notify_status[i].label;
							}
							return value;
						}
					},
					{
						title: '查阅状态',
						field: 'readFlag', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter:function (value, row, index) {
							<c:if test="${requestScope.oaNotify.self}">

								for(var i=0;i<oa_notify_read.length;i++)
								{
									if(value == oa_notify_read[i].value) return oa_notify_read[i].label;
								}
								return value;
							</c:if>
							<c:if test="${!requestScope.oaNotify.self}">
								return row.readNum +"/"+ (Number(row.readNum) + Number(row.unReadNum))
							</c:if>
						}


					}, {
						title: '更新时间',
						field: 'updateDate', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter:function (value, row, index) {

							return formatDate(value, "yyyy-MM-dd HH:mm:ss");
						}
					}
					<c:if test="${!requestScope.oaNotify.self}">
					<shiro:hasPermission name="oa:oaNotify:edit">
					, {
						title: '操作',
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter: actionFormatter,
						events: actionEvents
					}
					</shiro:hasPermission>
					</c:if>
				]


			})
		}

		function actionFormatter(value, row, index) {
			return [
				'<a class="edit ml10" href="javascript:void(0)" title="Edit">',
				'<i class="glyphicon glyphicon-edit"></i>',
				'</a>',
				'<a class="remove ml10" href="javascript:void(0)" title="Remove">',
				'<i class="glyphicon glyphicon-remove"></i>',
				'</a>'
			].join('');
		}

		function addOaNofify() {
			var url = "${ctx}/oa/oaNotify/form";
			window.location.href = url;
		}

		window.actionEvents = {
			'click .edit': function (e, value, row, index) {
				editOaNotifyById(row.id);
			},
			'click .remove': function (e, value, row, index) {
				delOaNotifyById(row.id);
			}
		};

		function editOaNotifyById(templateId) {

			var url = "${ctx}/oa/oaNotify/form?id=" + templateId;

			window.location.href = url;

		}

		function delOaNotifyById(templateId) {
			var url = "${ctx}/oa/oaNotify/delete?id=" + templateId;

			layer.confirm('确认要删除该通知吗？', {icon: 3, title: '提示'}, function () {
				window.location.href = url;
			});
		}

		function queryTable() {
			//先销毁表格
			$('#TepDefList').bootstrapTable('destroy');
			querys();

		}

		/**查询条件与分页数据 */
		function queryParams(params) {
			var temp = {
				pageNumber: params.pageNumber,
				pageSize: params.pageSize,
				title: $("#title").val(),
				type:$("#type").val(),
				self:${oaNotify.self}
				<c:if test="${!oaNotify.self}">
				, status:$("#status").val()
				</c:if>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/oa/oaNotify/${oaNotify.self?'self':''}">通知列表</a></li>
		<c:if test="${!oaNotify.self}"><shiro:hasPermission name="oa:oaNotify:edit"><li><a href="${ctx}/oa/oaNotify/form">通知添加</a></li></shiro:hasPermission></c:if>
	</ul>



	<div class="container">
		<div class="panel-body">
			<div class="panel panel-default">
				<div class="panel-heading">
					查询条件
				</div>
				<div class="panel-body">
					<form:form id="searchForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/${oaNotify.self?'self':''}" method="post" class="form-horizontal">
						<div class="form-group" >
							<label class="control-label col-sm-1" for="title">标题：</label>
							<div class="col-sm-2">
								<form:input path="title" htmlEscape="false" maxlength="200" class="form-control"/>
							</div>
							<label class="control-label col-sm-1" for="type">类型：</label>
							<div class="col-sm-2">
								<form:select path="type" class="form-control">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>
							<c:if test="${!requestScope.oaNotify.self}">
								<label class="control-label col-sm-1" for="status">状态：</label>
								<div class="col-sm-1" style="margin: 7px 0px;">
									<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</div>
							</c:if>
							<div class="col-md-offset-2 col-xs-offset-2 col-sm-offset-2  col-sm-2 ">
								<button type="button" style="margin-left:50px" id="btn_query" onclick="queryTable();"  class="btn btn-primary">查询
								</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<sys:message content="${message}"/>
			<div id="toolbar" class="btn-group">
				<shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
					<a>
						<button onclick="addOaNofify();" type="button" id="add" class="btn btn-default" title="add">
							<i class="glyphicon glyphicon-plus"></i>
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