<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工考核记录管理</title>
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
		var assessType = ${fns:getDictListJson("ASSESS_TYPE")};

		$(document).ready(function() {

			querys();


			$('#RecordDetailList').on('click-row.bs.table', function (e, row, $element) {
						$('.success').removeClass('success');
						$($element).addClass('success');
					})
					.on('dbl-click-row.bs.table', function (e, row) {
						console.log(row.id);
						var tepId = row.id
						showViewByRecordId(tepId);
					})
					.on('check.bs.table', function (e, row) {

						var selArys = $('#RecordDetailList').bootstrapTable('getSelections');
						if (selArys.length == 1) {
							$("#edit").removeAttr("disabled");
							$("#view").removeAttr("disabled");
						} else {
							$("#edit").attr({"disabled": "disabled"});
							$("#view").attr({"disabled": "disabled"});
						}

						$("#delete").removeAttr("disabled");
					})
					.on('uncheck.bs.table', function (e, row) {
						var selArys = $('#RecordDetailList').bootstrapTable('getSelections');
						if (selArys.length == 0) {
							$("#delete").attr({"disabled": "disabled"});
						}

						if (selArys.length == 1) {
							$("#edit").removeAttr("disabled");
							$("#view").removeAttr("disabled");
						} else {
							$("#edit").attr({"disabled": "disabled"});
							$("#view").attr({"disabled": "disabled"});
						}
					})
					.on('check-all.bs.table', function (e, rows) {
						$("#delete").removeAttr("disabled");
					})
					.on('uncheck-all.bs.table', function (e) {

						$("#edit").attr({"disabled": "disabled"});
						$("#view").attr({"disabled": "disabled"});
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
			for(var i=0;i<assessType.length;i++) {
				if(value == assessType[i].value) {
					return assessType[i].label;
				}
			}
		}

		function querys() {
			$("#edit").attr({"disabled": "disabled"});
			$("#view").attr({"disabled": "disabled"});
			$("#delete").attr({"disabled": "disabled"});

			$("#RecordDetailList").bootstrapTable({
				url: '${ctx}/dlj/comStaffAssessRecord/newlist',
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
						title: '被考核人',
						field: 'targetUser.name', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle'
					},


					{
						title: '考核类型',
						field: 'ext1', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle',
						formatter:nameFormatter

					},

					{
						title: '考核时段',
						field: 'assessMonth', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle' //
					},
					{
						title: '总分',
						field: 'totalScore', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle'
					},
					{
						title: '状态',
						field: 'state', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle'


					}, {
						title: '打分人',
						field: 'gradeUser.name', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle'
					}, {
						title: '考核时间',
						field: 'createDate', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle' //
					}, {
						title: '备注',
						field: 'remark', // 字段
						align: 'center', // 对齐方式（左 中 右）
						valign: 'middle' //
					}, {
						title: '考核模板',
						field: 'template.templateName', // 字段
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
				'<a class="edit ml10" href="javascript:void(0)" title="Edit">',
				'<i class="glyphicon glyphicon-edit"></i>',
				'</a>',
				'<a class="remove ml10" href="javascript:void(0)" title="Remove">',
				'<i class="glyphicon glyphicon-remove"></i>',
				'</a>'
			].join('');
		}

		window.actionEvents = {

			'click .edit': function (e, value, row, index) {
//                alert('You click edit icon, row: ' + JSON.stringify(row));

				console.log(value, row, index);
				editRecordDetailByRecordId(row.id);
			},
			'click .remove': function (e, value, row, index) {
//                alert('You click remove icon, row: ' + JSON.stringify(row));
				console.log(value, row, index);
				delSingleRecordDetail(row.id);
			}
		};


		function queryTable() {
			//先销毁表格
			$('#RecordDetailList').bootstrapTable('destroy');
			querys();

		}

		function addRecordDetail() {
			var url = "${ctx}/dlj/comStaffAssessRecord/form";
			window.location.href = url;
		}


		function showView() {
			var recordId = $('#RecordDetailList').bootstrapTable('getSelections')[0].id;

			showViewByRecordId(recordId);
		}

		function showViewByRecordId(recordId) {
			var url = "${ctx}/dlj/comStaffAssessRecord/markView?id=" + recordId;
			window.location.href = url;
		}


		function editRecordDetail() {
			var recordId = $('#RecordDetailList').bootstrapTable('getSelections')[0].id;

			editRecordDetailByRecordId(recordId);

		}

		function editRecordDetailByRecordId(recordId) {

			var url = "${ctx}/dlj/comStaffAssessRecord/update?id=" + recordId;

			window.location.href = url;

		}

		function delSingleRecordDetail(recordId) {
			var url = "${ctx}/dlj/comStaffAssessRecord/delete?id=" + recordId;

			layer.confirm('确认要删除该员工考核记录吗？', {icon: 3, title: '提示'}, function () {
				window.location.href = url;
			});
		}

		/**
		 * 删除数据
		 */
		function delMultiRecordDetail() {
			var hrs = $("#RecordDetailList").bootstrapTable('getSelections');
			if (hrs.length < 1) {
				layer.alert('请选择一条或多条数据进行删除！', {icon: 2});
			} else {
				layer.confirm('确定要删除所选数据？', {icon: 3, title: '提示'}, function () {
					var ids = [];
					for (var i = 0; i < hrs.length; i++) {
						ids.push(hrs[i].id);
					}
					$.ajax({
						url: '${ctx}/dlj/comStaffAssessRecord/deleteSel',
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dlj/comStaffAssessRecord/">员工考核记录列表</a></li>
		<shiro:hasPermission name="dlj:comStaffAssessRecord:edit">
			<li><a href="${ctx}/dlj/comStaffAssessRecord/form">员工考核记录添加</a></li>
		</shiro:hasPermission>
	</ul>

	<div class="container">
		<div class="panel-body">
			<div class="panel panel-default">
				<div class="panel-heading">
					查询条件
				</div>
				<div class="panel-body">
					<form:form id="searchForm" modelAttribute="comStaffAssessRecord" action="${ctx}/dlj/comStaffAssessRecord/form" method="post" class="form-horizontal">
						<div class="form-group" >
							<label class="control-label col-sm-1" for="targetUser">被考核人</label>
							<div class="col-sm-2">
								<sys:treeselect id="targetUser" name="targetUser.id" value="${comStaffAssessRecord.targetUser.id}" labelName="targetUser.name" labelValue="${comStaffAssessRecord.targetUser.name}"
												title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
							</div>

							<label class="control-label col-sm-1" for="targetUser">打分人</label>
							<div class="col-sm-2">
								<sys:treeselect id="gradeUser" name="gradeUser.id" value="${comStaffAssessRecord.gradeUser.id}" labelName="gradeUser.name" labelValue="${comStaffAssessRecord.gradeUser.name}"
												title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
							</div>

							<label class="control-label col-sm-1" for="state">状态</label>
							<div class="col-sm-2">
								<form:select path="state" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('TEMPLATE_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>

							<label class="control-label col-sm-1" for="assessMonth">考核时段：</label>
							<div class="col-sm-2">
								<form:input path="assessMonth" htmlEscape="false" maxlength="6" class="form-control"/>
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-sm-1" for="beginDate">开始时间</label>
							<div class="col-sm-2">
								<div class='input-group date' id='datetimepicker1'>
									<input id="beginDate" type='text' class="form-control"  value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" placeholder="开始时间" maxlength="20"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
								</div>
							</div>
							<label class="control-label col-sm-1" for="endDate">结束时间</label>
							<div class="col-sm-2">
								<div class='input-group date' id='datetimepicker2'>
									<input id="endDate" type='text' class="form-control"  value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" placeholder="结束时间"  maxlength="20"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
								</div>
							</div>

							<label class="control-label col-sm-1" for="ext1">考核类型</label>
							<div class="col-sm-2">
								<form:select path="ext1" class="form-control">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('ASSESS_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>


							<div class="col-md-offset-2 col-sm-1">
								<button type="button" style="margin-left:50px" id="btnSubmit" onclick="queryTable()" class="btn btn-primary">查询
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
						<button onclick="addRecordDetail();" type="button" id="add" class="btn btn-default" title="add">
							<i class="glyphicon glyphicon-plus"></i>
						</button>
					</a>

					<a>
						<button type="button" id="view" onclick="showView();" class="btn btn-default" title="view">
							<i class="glyphicon glyphicon-book"></i>
						</button>
					</a>

					<a>
						<button type="button" id="edit" onclick="editRecordDetail();" class="btn btn-default" title="edit">
							<i class="glyphicon glyphicon-pencil"></i>
						</button>
					</a>

					<a>
						<button type="button" onclick="delMultiRecordDetail();" id="delete" class="btn btn-default" title="delete">
							<i class="glyphicon glyphicon-trash"></i>
						</button>
					</a>
				</shiro:hasPermission>
			</div>
			<table id="RecordDetailList"></table>
		</div>
	</div>



	<%--<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>被考核人</th>
				<th>考核类型</th>
				<th>考核时段</th>
				<th>总分</th>
				<th>状态</th>
				<th>打分人</th>
				<th>考核时间</th>
				<th>备注</th>
				<th>考核模板</th>
				<shiro:hasPermission name="dlj:comStaffAssessRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="comStaffAssessRecord">
			<tr>
				<td>
					${comStaffAssessRecord.targetUser.name}
				</td>
				<td>
					${fns:getDictLabel(comStaffAssessRecord.ext1, 'ASSESS_TYPE', '')}
				</td>
				<td>
					${comStaffAssessRecord.assessMonth}
				</td>
				<td>
					${comStaffAssessRecord.totalScore}
				</td>
				<td>
					${fns:getDictLabel(comStaffAssessRecord.state, 'ASSESS_STATE', '')}
				</td>
				<td>
					${comStaffAssessRecord.gradeUser.name}
				</td>
				<td>
					<fmt:formatDate value="${comStaffAssessRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${comStaffAssessRecord.remark}
				</td>
				<td>
					${comStaffAssessRecord.template.templateName}
				</td>
				<td>
    				<a href="${ctx}/dlj/comStaffAssessRecord/markView?id=${comStaffAssessRecord.recordId}">查看</a>
				<c:if test="${comStaffAssessRecord.state eq '1'}">
					<shiro:hasPermission name="dlj:comStaffAssessRecord:edit">
    				<a href="${ctx}/dlj/comStaffAssessRecord/update?id=${comStaffAssessRecord.recordId}">修改</a>
					<a href="${ctx}/dlj/comStaffAssessRecord/delete?id=${comStaffAssessRecord.recordId}" onclick="return confirmx('确认要删除该员工考核记录吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>--%>
</body>
</html>