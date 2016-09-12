<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		/*function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }*/

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
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/log/">日志列表</a></li> --%>
<!-- 	</ul> -->


<div class="container">
	<div class="panel-body">
		<div class="panel panel-default">
			<div class="panel-heading">查询条件</div>
			<div class="panel-body">
				<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/log/" method="post" class="form-horizontal">
					<div class="form-group" >
						<label class="control-label col-sm-2" for="title">操作菜单：</label>
						<div class="col-sm-3">
							<input id="title" name="title" type="text" maxlength="50" class="input-mini" value="${log.title}"/>
						</div>
						<label class="control-label col-sm-2  " for="requestUri">用户ID ：</label>
						<div class="col-sm-3">
							<input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-mini" value="${log.requestUri}"/>
						</div>
						<label class="control-label col-sm-2  " for="requestUri">URI ：</label>
						<div class="col-sm-3">
							<input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-mini" value="${log.requestUri}"/>
						</div>
					</div>
					<div class="form-group" >
						<label class="control-label col-sm-2" for="title">操作菜单：</label>
						<div class="col-sm-3">
							<input id="title" name="title" type="text" maxlength="50" class="input-mini" value="${log.title}"/>
						</div>
						<label class="control-label col-sm-2  " for="requestUri">用户ID ：</label>
						<div class="col-sm-3">
							<input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-mini" value="${log.requestUri}"/>
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



	<%--<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>操作菜单：</label><input id="title" name="title" type="text" maxlength="50" class="input-mini" value="${log.title}"/>
			<label>用户ID：</label><input id="createBy.id" name="createBy.id" type="text" maxlength="50" class="input-mini" value="${log.createBy.id}"/>
			<label>URI：</label><input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-mini" value="${log.requestUri}"/>
		</div><div style="margin-top:8px;">
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
			&nbsp;<label for="exception"><input id="exception" name="exception" type="checkbox"${log.exception eq '1'?' checked':''} value="1"/>只查询异常信息</label>
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>操作菜单</th><th>操作用户</th><th>所在公司</th><th>所在部门</th><th>URI</th><th>提交方式</th><th>操作者IP</th><th>操作时间</th></thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="log">
			<tr>
				<td>${log.title}</td>
				<td>${log.createBy.name}</td>
				<td>${log.createBy.company.name}</td>
				<td>${log.createBy.office.name}</td>
				<td><strong>${log.requestUri}</strong></td>
				<td>${log.method}</td>
				<td>${log.remoteAddr}</td>
				<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
			</tr>
			<c:if test="${not empty log.exception}"><tr>
				<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
&lt;%&ndash; 					用户代理: ${log.userAgent}<br/> &ndash;%&gt;
&lt;%&ndash; 					提交参数: ${fns:escapeHtml(log.params)} <br/> &ndash;%&gt;
					异常信息: <br/>
					${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}</td>
			</tr></c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>--%>
</body>
</html>