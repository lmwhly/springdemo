<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工考核记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dlj/comStaffAssessRecord/">员工考核记录列表</a></li>
		<shiro:hasPermission name="dlj:comStaffAssessRecord:edit"><li><a href="${ctx}/dlj/comStaffAssessRecord/form">员工考核记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="comStaffAssessRecord" action="${ctx}/dlj/comStaffAssessRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>被考核人：</label>
				<sys:treeselect id="targetUser" name="targetUser.id" value="${comStaffAssessRecord.targetUser.id}" labelName="targetUser.name" labelValue="${comStaffAssessRecord.targetUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>考核类型：</label>
				<form:select path="ext1" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ASSESS_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>考核时段：</label>
				<form:input path="assessMonth" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ASSESS_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>打分人：</label>
				<sys:treeselect id="gradeUser" name="gradeUser.id" value="${comStaffAssessRecord.gradeUser.id}" labelName="gradeUser.name" labelValue="${comStaffAssessRecord.gradeUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>考核时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${comStaffAssessRecord.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${comStaffAssessRecord.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
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
	<div class="pagination">${page}</div>
</body>
</html>