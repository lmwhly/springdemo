<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
    <style type="text/css">

        label.form-control {
            background-color: #f3f3f3;
        }


    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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


            projectfileoptions = {
                showUpload: false,
                showRemove: false,
                language: 'zh',
                allowedPreviewTypes: ['image'],
                allowedFileExtensions: ['jpg', 'png', 'gif'],
                maxFileSize: 2000
            }

            $('input[class=projectfile]').each(function () {
                var imageurl = $(this).attr("value");
                if (imageurl) {
                    var op = $.extend({ initialPreview: [ // 预览图片的设置
                        "<img src='" + imageurl + "' class='file-preview-image'>"] }, projectfileoptions);
                    $(this).fileinput(op);
                } else {
                    $(this).fileinput(projectfileoptions);
                }
            });
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">头像:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <input id="imgUpload" type="file" name="imgFile" class="projectfile"  accept="image/*" value="${user.photo}">
                <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
            </div>
		</div>

		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">归属公司:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
                <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="form-control required"/>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">归属部门:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
                <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">工号:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:input path="no" htmlEscape="false" maxlength="50" class="form-control required"/>
			</div>
            <label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">姓名:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
			</div>
            <label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">登录名:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control required userName"/>
			</div>
            <label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">密码:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="${empty user.id?'form-control required':''}"/>
            </div>
            <c:if test="${empty user.id}"> <label class="control-label text-danger">*</label></c:if>
            <c:if test="${not empty user.id}"><label class="control-label help-inline">若不修改密码，请留空。</label></c:if>


		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">确认密码:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword" class="form-control"/>
				<c:if test="${empty user.id}"></c:if>
			</div>
            <label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">邮箱:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:input path="email" htmlEscape="false" maxlength="100" class="form-control email"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">电话:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:input path="phone" htmlEscape="false" maxlength="100" cssClass="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">手机:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:input path="mobile" htmlEscape="false" maxlength="100" cssClass="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">是否允许登录:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:select path="loginFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<p class="help-block">“是”代表此账号允许登录，“否”则表示此账号不允许登录</p>
			</div>
            <label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">用户类型:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:select path="userType" class="input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">用户角色:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
			</div>
            <label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2  control-label">备注:</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control input-xlarge"/>
			</div>
		</div>

		<c:if test="${not empty user.id}">
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2  control-label">创建时间:</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
					<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2  control-label">最后登陆:</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
					<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>

        <div class="form-group">
            <div class="col-md-offset-2 col-xs-offset-2 col-sm-offset-2">
                <shiro:hasPermission name="sys:user:edit">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
                </shiro:hasPermission>
                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
            </div>
        </div>
	</form:form>
</body>
</html>