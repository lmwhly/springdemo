<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
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
				url: '${ctx}/sys/dict/newlist',
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
						title: '键值',
						field: 'value', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle' //
					},

					{
						title: '标签',
						field: 'label', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle' //
					},
					{
						title: '类型',
						field: 'type', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle' //
					},
					{
						title: '描述',
						field: 'description', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle'//
					}, {
						title: '排序',
						field: 'sort', // 字段
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
					limit: params.limit,   //页面大小
					offset: params.offset,  //页码
					type: $("#type").val(),
					description:$("#description").val()
				}//传到后台的参数
			});
		}

		/**查询条件与分页数据 */
		function queryParams(params) {
			var temp = {
				limit: params.limit,   //页面大小
				offset: params.offset,  //页码
				type: $("#type").val(),
				description:$("#description").val()
			};
			return temp;
		}


		function responseHandler(res) {
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/dict/">字典列表</a></li>
		<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10">字典添加</a></li></shiro:hasPermission>
	</ul>

	<div class="container">
		<div class="panel-body">
			<div class="panel panel-default">
				<div class="panel-heading">查询条件</div>
				<div class="panel-body">
					<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="form-horizontal">
						<div class="form-group" >
							<label class="control-label col-sm-2" for="type">类型：</label>
							<div class="col-sm-3">
								<form:select id="type" path="type" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${typeList}" htmlEscape="false"/>
								</form:select>
							</div>
							<label class="control-label col-sm-2  " for="description">描述 ：</label>
							<div class="col-sm-3">
								<form:input path="description" htmlEscape="false" maxlength="50" class="form-control input-medium"/>
							</div>
							<div class="col-sm-2">
								<button type="button" style="margin-left:50px" id="btnSubmit" onclick="queryTable()"  class="btn btn-primary">查询
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
	<%--<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>类型：</label><form:select id="type" path="type" class="input-medium"><form:option value="" label=""/><form:options items="${typeList}" htmlEscape="false"/></form:select>
		&nbsp;&nbsp;<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>键值</th><th>标签</th><th>类型</th><th>描述</th><th>排序</th><shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td>${dict.value}</td>
				<td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
				<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
				<td>${dict.description}</td>
				<td>${dict.sort}</td>
				<shiro:hasPermission name="sys:dict:edit"><td>
    				<a href="${ctx}/sys/dict/form?id=${dict.id}">修改</a>
					<a href="${ctx}/sys/dict/delete?id=${dict.id}&type=${dict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
    				<a href="<c:url value='${fns:getAdminPath()}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>">添加键值</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>--%>
</body>
</html>