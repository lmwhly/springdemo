<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>个人信息</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">

        .form-horizontal .form-group {
            margin-right: 0px;
        }

        label.form-control {
            background-color: #f3f3f3;
        }




    </style>
    <script type="text/javascript">
        projectfileoptions : {
            showUpload : false,
                    showRemove : false,
                    language : 'zh',
                    allowedPreviewTypes : [ 'image' ],
                    allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
                    maxFileSize : 2000
        }




        $(document).ready(function () {


            // 文件上传框
            $('input[class=projectfile]').each(function () {
                var imageurl = $(this).attr("value");

                if (imageurl) {
                    var op = $.extend({
                        initialPreview: [ // 预览图片的设置
                            "<img src='" + imageurl + "' class='file-preview-image'>",]
                    }, projectfileoptions);

                    $(this).fileinput(op);
                } else {
                    $(this).fileinput(projectfileoptions);
                }
            });


            $('#datetimepicker1').datetimepicker({
                format: 'YYYY-MM-DD',//日期格式化，只显示日期
                locale: 'zh-CN',     //中文化
                showClear: true
            });


            /*  $("#imgUpload")
             .fileinput({
             language: 'zh',
             uploadUrl: "/Product/imgDeal",
             autoReplace: true,
             maxFileCount: 1,
             allowedFileExtensions: ["jpg", "png", "gif"],
             browseClass: "btn btn-primary", //按钮样式
             previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
             })
             .on("fileuploaded", function (e, data) {
             var res = data.response;
             if (res.state > 0) {
             alert('上传成功');
             alert(res.path);
             }
             else {
             alert('上传失败')
             }
             })*/


//            layer.msg('更新失败！');

            $("#inputForm").validate({
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


            function iframeCallback(form, callback) {
                YUNM.debug("带文件上传处理");

                var $form = $(form), $iframe = $("#callbackframe");

                var data = $form.data('bootstrapValidator');
                if (data) {
                    if (!data.isValid()) {
                        return false;
                    }
                }

                // 富文本编辑器
                $("div.summernote", $form).each(function () {
                    var $this = $(this);

                    if (!$this.summernote('isEmpty')) {
                        var editor = "<input type='hidden' name='" + $this.attr("name") + "' value='" + $this.summernote('code') + "' />";
                        $form.append(editor);
                    } else {
                        $.showErr("请填写项目详情");
                        return false;
                    }

                });

                if ($iframe.size() == 0) {
                    $iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
                }
                if (!form.ajax) {
                    $form.append('<input type="hidden" name="ajax" value="1" />');
                }
                form.target = "callbackframe";

                _iframeResponse($iframe[0], callback || YUNM.ajaxDone);
            }


            function _iframeResponse(iframe, callback) {
                var $iframe = $(iframe), $document = $(document);

                $document.trigger("ajaxStart");

                $iframe.bind("load", function (event) {
                    $iframe.unbind("load");
                    $document.trigger("ajaxStop");

                    if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || // For
                            // Safari
                            iframe.src == "javascript:'<html></html>';") { // For FF, IE
                        return;
                    }

                    var doc = iframe.contentDocument || iframe.document;

                    // fixing Opera 9.26,10.00
                    if (doc.readyState && doc.readyState != 'complete')
                        return;
                    // fixing Opera 9.64
                    if (doc.body && doc.body.innerHTML == "false")
                        return;

                    var response;

                    if (doc.XMLDocument) {
                        // response is a xml document Internet Explorer property
                        response = doc.XMLDocument;
                    } else if (doc.body) {
                        try {
                            response = $iframe.contents().find("body").text();
                            response = jQuery.parseJSON(response);
                        } catch (e) { // response is html document or plain text
                            response = doc.body.innerHTML;
                        }
                    } else {
                        // response is a xml document
                        response = doc;
                    }

                    callback(response);
                });
            }


        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
    <li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
</ul>
<br/>


<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal"
           enctype="multipart/form-data" onsubmit="return iframeCallback(this, pageAjaxDone)">
    <sys:message content="${message}"/>
    <div class="form-group">
        <label class="col-md-1 control-label">头像:</label>
        <div class="col-md-5">
            <form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                <%--&lt;%&ndash;<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>&ndash;%&gt;--%>
                <%--<input id="imgUpload" type="file" class="file-loading" accept="image/*">--%>
            <input type="file" name="image" class="file-loading" value="${deal.image}"/>
            <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">测试日期:</label>
        <div class="col-md-5">
            <div class='input-group date' id='datetimepicker1'>
                <input type='text' class="form-control"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">归属公司:</label>
        <div class="col-md-5">
            <label class="form-control">${user.company.name}</label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">归属部门:</label>
        <div class="col-md-5">
            <label class="form-control">${user.office.name}</label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">姓名:</label>
        <div class="col-md-5">
            <form:input path="name" htmlEscape="false" maxlength="50" class="form-control required" readonly="true"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">邮箱:</label>
        <div class="col-md-5">
            <form:input path="email" htmlEscape="false" maxlength="50" class="form-control email"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">电话:</label>
        <div class="col-md-5">
            <form:input path="phone" htmlEscape="false" maxlength="50" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">手机:</label>
        <div class="col-md-5">
            <form:input path="mobile" htmlEscape="false" maxlength="50" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">备注:</label>
        <div class="col-md-5">
            <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200"
                           class="form-control input-xlarge"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">用户类型:</label>
        <div class="col-md-5">
            <label class="form-control">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">用户角色:</label>
        <div class="col-md-5">
            <label class="form-control">${user.roleNames}</label>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label">上次登录:</label>
        <div class="col-md-5">
            <label class="form-control">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate
                    value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
        </div>
    </div>

</form:form>
</body>
</html>

