<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工考核记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function openDetailWindow(){
			var assessType = $("#assessType").find("option:selected").text();
			var templateName = $("#template").find("option:selected").text();
			$("#assessTypeName").val(assessType);
			$("#templateName").val(templateName);
			var url = "${ctx}/dlj/comStaffAssessRecord/templateMarkNew";
			$("#inputForm").attr("action",url);
			$("#inputForm").submit();
			//window.showModalDialog(url,[],"scroll:yes;resizable:no;status:no;dialogHeight:600px;dialogWidth:1000px");
//			window.location=url;
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/dlj/comStaffAssessRecord/">员工考核记录列表</a></li>
		<li class="active"><a href="${ctx}/dlj/comStaffAssessRecord/form?id=${comStaffAssessRecord.recordId}">员工考核记录<shiro:hasPermission name="dlj:comStaffAssessRecord:edit">${not empty comStaffAssessRecord.recordId?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="dlj:comStaffAssessRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="comStaffAssessRecord" action="${ctx}/dlj/comStaffAssessRecord/TemplateMarkNew" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="template.templateName" id="templateName"/>
		<sys:message content="${message}"/>		
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">被考核人：</label>
			<div class="col-md-2 col-sm-2 col-xs-2">
				<sys:treeselect id="targetUser" name="targetUser.id" value="${comStaffAssessRecord.targetUser.id}" labelName="targetUser.name" labelValue="${comStaffAssessRecord.targetUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="form-control"  allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">考核类型：</label>
			<div class="col-md-2 col-sm-2 col-xs-2">
				<form:select path="ext1" class="form-control" id="assessType">
					<form:options items="${fns:getDictList('ASSESS_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">考核模板：</label>
			<div class="col-md-2 col-sm-2 col-xs-2">
				<form:select path="template.templateId" class="form-control " id="template">
					<form:options items="${templateList}" itemLabel="templateName" itemValue="templateId" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">考核时段：</label>
			<div class="col-md-2 col-sm-2 col-xs-2">
				<form:input path="assessMonth" htmlEscape="false" maxlength="6" class="form-control "/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-1 col-xs-offset-1 col-sm-offset-1 col-md-2 col-sm-2 col-xs-2">
				<input id="btnMark" class="btn btn-primary" type="button" value="打 分" onclick="openDetailWindow()"/>&nbsp;
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</div>
	</form:form>
</body>
</html>