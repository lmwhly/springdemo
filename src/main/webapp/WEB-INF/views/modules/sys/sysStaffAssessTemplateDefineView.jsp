<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核模板预览</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
        $.ajax({
            type : 'post',
            url : '${ctx}/sys/sysStaffAssessTemplateDefine/showDetail',
            async : false,
            data : {
                'templateId' : '${templateId}'
            },
            success : function(data) {
            	showDetail(data);
            }
        });
    });
	function showDetail(data){
		var rowNum = data.length;
		var colNum = data[0].length;
		var html = "<thead><tr><td style=\"text-align: center;\" colspan='"+colNum+"'><b>模板内容</b></td><td style=\"text-align: center;\" ><b>分值</b></td></tr></thead><tbody>";
		for(var i=0;i<rowNum;i++){
			var tdList = data[i];
			var rowHtml="<tr>";
			for(var j =0; j<tdList.length;j++){
				var tdJson = getJson(data[i][j]);
				rowHtml+= "<td rowspan='"+(tdJson.endRow-tdJson.startRow+1)+"' colspan='"+(tdJson.endCol-tdJson.startCol+1)+"'>"+tdJson.templateContent+"</td>"
				if(j==tdList.length-1){

					rowHtml+= "<td>"+tdJson.point+"</td>";
				}
			}
			rowHtml+= "</tr>";
			html+= rowHtml;
		}
		html+="</tbody>";
		$("#showTable").html(html);
	}
	function getJson(jsonStr){
		return eval('('+jsonStr+')');
	};
	</script>
</head>
<body >
	<div style="margin-top: 5%;margin-left: 5%;margin-right: 5%;width: 90%">
		<table id="showTable" style="width: 100%;"  class="table table-striped table-bordered table-condensed"  >
		</table>
	</div>
</body>
</html>