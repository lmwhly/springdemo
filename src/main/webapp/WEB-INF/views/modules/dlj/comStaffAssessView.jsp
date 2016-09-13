<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考核明细查看</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.inputclass{
    font:12px/1.5 "Microsoft Yahei",tahoma,arial,"Hiragino Sans GB",\5b8b\4f53;
    width:100%;
    text-align:center;
    vertical-align:middle;
}
	
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		var scoreMap = {};
		<c:forEach items="${recordList}" var="t">  
			scoreMap[${t.detailTemplate.detailTemplateId}]=${t.scoreValue};
			scoreMap[${t.detailTemplate.detailTemplateId}+'_remark']='${t.remark}';
		</c:forEach>  
		$.ajax({
            type : 'post',
            url : '${ctx}/sys/sysStaffAssessTemplateDefine/showDetail',
            async : false,
            data : {
                'templateId' : '${comStaffAssessRecord.template.templateId}'
            },
            success : function(data) {
            	showDetail(data,scoreMap);
            }
        });
    });
	function showDetail(data,scoreMap){
		var rowNum = data.length;
		var colNum = data[0].length;
		var totalPoint = 0;
		var totalScore = 0;
		var html = "<thead><tr><td style=\"text-align: center;\" colspan='"+colNum+"'><b style=\"font-size:11pt;\">考核内容</b></td><td style=\"text-align: center;font-size:15px;\" ><b style=\"font-size:11pt;\">分值</b></td><td style=\"text-align: center;\" ><b style=\"font-size:11pt;\">得分</b></td><td style=\"text-align: center;\" ><b style=\"font-size:11pt;\">备注</b></td></tr></thead><tbody>";
		for(var i=0;i<rowNum;i++){
			var tdList = data[i];
			var rowHtml="<tr>";
			for(var j =0; j<tdList.length;j++){
				var tdJson = getJson(data[i][j]);
				if(j==tdList.length-1){
					rowHtml+= "<td style=\"text-align: center;width:30%;font-size:9pt;\"  rowspan='"+(tdJson.endRow-tdJson.startRow+1)+"' colspan='"+(tdJson.endCol-tdJson.startCol+1)+"'>"+tdJson.templateContent+"</td>"
					rowHtml+= "<td style=\"text-align: center;font-size:9pt;\">"+tdJson.point+"</td>";
					rowHtml+= "<td style=\"text-align: center;font-size:9pt;padding-bottom:0px;padding-top: 0px;padding-left:0px;padding-right:0px;width:5%;\">"+scoreMap[tdJson.detailTemplateId]+"</td>";
					rowHtml+= "<td style=\"text-align: center;font-size:9pt;padding-bottom:0px;padding-top: 0px;padding-left:0px;padding-right:0px;width:20%;\">"+scoreMap[tdJson.detailTemplateId+"_remark"]+"</td>";
					totalPoint += tdJson.point;
					totalScore += scoreMap[tdJson.detailTemplateId];
				}else{
					rowHtml+= "<td style=\"text-align: center;font-size:9pt;\"  rowspan='"+(tdJson.endRow-tdJson.startRow+1)+"' colspan='"+(tdJson.endCol-tdJson.startCol+1)+"'>"+tdJson.templateContent+"</td>"
				}
			}
			rowHtml+= "</tr>";
			html+= rowHtml;
		}
		html+="<tr>";
		html+="<td  colspan='"+colNum+"' style=\"text-align: center;\"><b style=\"font-size:11pt;\">合计</b></td>";
		html+="<td style=\"text-align: center;\"><b style=\"font-size:11pt;\">"+totalPoint+"</b></td>";
		html+="<td id='totalScoreTd' style=\"text-align: center;\"><b style=\"font-size:11pt;\">"+totalScore+"</b></td>";
		html+="<td style=\"text-align: center;font-size:9pt;padding-bottom:0px;padding-top: 0px;padding-left:0px;padding-right:0px;width:20%;\">"+"${comStaffAssessRecord.remark}"+"</td>";
		html+= "</tr>";
		html+="</tbody>";
		$("#showTable").html(html);
	}
	function getJson(jsonStr){
		return eval('('+jsonStr+')');
	}
	</script>
</head>
<style>body,th,td{margin:0;padding:0;height:30px;}table{border-collapse:collapse;border-spacing:0}</style>
<body >
	<div>
		<table class="table table-striped">
			<tr>
				<td style="width:10%;border-left-width:1px;border-left-color: #ddd;border-left-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					<b style="font-size:11pt;">被考核人：</b>
				</td>
				<td style="width:10%;font-size:10pt;border-right-width:1px;border-right-color: #ddd;border-right-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					${comStaffAssessRecord.targetUser.name}
				</td>
				<td style="width:10%;border-left-width:1px;border-left-color: #ddd;border-left-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					<b style="font-size:11pt;">考核类型：</b>
				</td>
				<td style="width:10%;font-size:10pt;border-right-width:1px;border-right-color: #ddd;border-right-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					${fns:getDictLabel(comStaffAssessRecord.ext1, 'ASSESS_TYPE', '')}
				</td>
				<td style="width:10%;border-left-width:1px;border-left-color: #ddd;border-left-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					<b style="font-size:11pt;">考核区间：</b>
				</td>
				<td style="width:10%;font-size:10pt;border-right-width:1px;border-right-color: #ddd;border-right-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					${comStaffAssessRecord.assessMonth}
				</td>
				<td style="width:10%;border-left-width:1px;border-left-color: #ddd;border-left-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					<b style="font-size:11pt;">考核模板：</b>
				</td>
				<td style="width:30%;font-size:10pt;border-right-width:1px;border-right-color: #ddd;border-right-style: solid;border-bottom-width:1px;border-bottom-color: #ddd;border-bottom-style: solid;">
					${comStaffAssessRecord.template.templateName}
				</td>
			</tr>
		</table>
	</div>
	<div>
		<table id="showTable" style="height:auto;width:100%;cellpadding:0;cellspacing:0;"  class="table table-striped table-bordered table-condensed"  >
		</table>
	</div>
	<div class="form-actions"  align="center">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>