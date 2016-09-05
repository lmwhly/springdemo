<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>修改密码</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#oldPassword").focus();
            $("#inputForm").validate({
                rules: {},
                messages: {
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                },
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
    <li><a href="${ctx}/sys/user/info">个人信息</a></li>
    <li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <div class="form-group">
        <label class="col-md-1 control-label">旧密码:</label>
        <div class="col-md-2">
            <input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3"
                   class="form-control required"/>
        </div>
        <label class="control-label text-danger">*</label>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">新密码:</label>
        <div class="col-md-2">
            <input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3"
                   class="form-control required"/>
        </div>
        <label class="control-label text-danger">*</label>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">确认新密码:</label>
        <div class="col-md-2">
            <input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" class="form-control required" equalTo="#newPassword"/>
        </div>
        <label class="control-label text-danger">*</label>
    </div>
    <div class="form-actions">
        <div class="col-sm-offset-1">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </div>
    </div>
</form:form>
</body>
</html>