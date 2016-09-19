<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
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
		<li><a href="${ctx}/oa/oaNotify/">通知列表</a></li>
		<li class="active"><a href="${ctx}/oa/oaNotify/form?id=${oaNotify.id}">通知<shiro:hasPermission name="oa:oaNotify:edit">${oaNotify.status eq '1' ? '查看' : not empty oaNotify.id ? '修改' : '添加'}</shiro:hasPermission><shiro:lacksPermission name="oa:oaNotify:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">类型：</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:select path="type" class="form-control required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<label class="control-label text-danger">*</label>
		</div>	
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">标题：</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:input path="title" htmlEscape="false" maxlength="200" class="form-control required"/>
			</div>
			<label class="control-label text-danger">*</label>
		</div>
		<div class="form-group">
			<label class="col-md-1 col-xs-1 col-sm-2 control-label">内容：</label>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/>
			</div>
			<label class="control-label text-danger">*</label>
		</div>
		<c:if test="${oaNotify.status ne '1'}">
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2 control-label">附件：</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
					<form:hidden id="nameImage" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<input id="imgUpload" type="file" name="imgFile" class="projectfile"  accept="image/*" value="${oaNotify.files}">
					<p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2 control-label">状态：</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
					<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> 发布后不能进行操作。</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2 control-label">接受人：</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
	                <sys:treeselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
						title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" notAllowSelectParent="true" checked="true"/>
				</div>
				<label class="control-label text-danger">*</label>
			</div>
		</c:if>
		<c:if test="${oaNotify.status eq '1'}">
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2 control-label">附件：</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
					<form:hidden id="nameImage" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<input id="imgUpload" type="file" name="imgFile" class="projectfile"  accept="image/*" value="${oaNotify.files}">
					<p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 col-xs-1 col-sm-2 control-label">接受人：</label>
				<div class="col-md-4 col-sm-4 col-xs-4">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th>接受人</th>
								<th>接受部门</th>
								<th>阅读状态</th>
								<th>阅读时间</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
							<tr>
								<td>
									${oaNotifyRecord.user.name}
								</td>
								<td>
									${oaNotifyRecord.user.office.name}
								</td>
								<td>
									${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
								</td>
								<td>
									<fmt:formatDate value="${oaNotifyRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp; 总共：${oaNotify.readNum + oaNotify.unReadNum}
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<div class="col-md-offset-2 col-xs-offset-2 col-sm-offset-2">
				<c:if test="${oaNotify.status ne '1'}">
					<shiro:hasPermission name="oa:oaNotify:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
					</shiro:hasPermission>
				</c:if>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</div>
	</form:form>
</body>
</html>