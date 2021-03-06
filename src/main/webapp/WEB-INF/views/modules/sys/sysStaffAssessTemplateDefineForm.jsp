<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysStaffAssessTemplateDefine/">考核模板列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysStaffAssessTemplateDefine/form?id=${sysStaffAssessTemplateDefine.id}">考核模板<shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">${not empty sysStaffAssessTemplateDefine.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysStaffAssessTemplateDefine:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysStaffAssessTemplateDefine" action="${ctx}/sys/sysStaffAssessTemplateDefine/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">模板名称：</label>
			<div class="col-md-2 col-sm-2 col-xs-2">
				<form:input path="templateName" htmlEscape="false" maxlength="256" class="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">状态：</label>
			<div class="col-md-1 col-sm-1 col-xs-1">
				<form:select path="state" class="form-control">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('TEMPLATE_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-1 col-xs-offset-1 col-sm-offset-1 col-md-2 col-sm-2 col-xs-2">
				<shiro:hasPermission name="sys:sysStaffAssessTemplateDefine:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
				</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</div>
	</form:form>
</body>
</html>