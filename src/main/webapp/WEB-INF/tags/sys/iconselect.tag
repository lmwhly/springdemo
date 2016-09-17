<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<i id="${id}Icon" class="icon-${not empty value?value:' hide'}"></i>&nbsp;<label id="${id}IconLabel">${not empty value?value:'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn">选择</a>&nbsp;&nbsp;
<script type="text/javascript">
	$("#${id}Button").click(function(){

		/*layer.open({
			title:"选择图标",
			type: 2,
			area: ['700px', '$(top.document).height()-180'],

			content: "${ctx}/tags/iconselect?value=" + $("#${id}").val(),
			btn: ['确定', '清除', '关闭'],
			yes: function (index, layero) {
//获取子窗口的dom对象
				var body = layer.getChildFrame('body', index);
//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var iframeWin = window[layero.find('iframe')[0]['name']];

				var icon = iframeWin.$("#icon").val();
				icon = $.trim(icon).substr(5);
				$("#${id}Icon").attr("class", "icon-" + icon);
				$("#${id}IconLabel").text(icon);
				$("#${id}").val(icon);
				layer.close(index); //如果设定了yes回调，需进行手工关闭*!/!*!/
			}
			, btn2: function (index, layero) {
				$("#${id}Icon").attr("class", "icon- hide");
				$("#${id}IconLabel").text("无");
				$("#${id}").val("");
			}, btn3: function (index, layero) {
				layer.close(index);
			}
			, cancel: function () {
				//右上角关闭回调
			}


		});*/


		top.$.jBox.open("iframe:${ctx}/tags/iconselect?value="+$("#${id}").val(), "选择图标", 700, $(top.document).height()-180, {
            buttons:{"确定":"ok", "清除":"clear", "关闭":true}, submit:function(v, h, f){
                if (v=="ok"){
                	var icon = h.find("iframe")[0].contentWindow.$("#icon").val();
                	icon = $.trim(icon).substr(5);
                	$("#${id}Icon").attr("class", "icon-"+icon);
	                $("#${id}IconLabel").text(icon);
	                $("#${id}").val(icon);
                }else if (v=="clear"){
	                $("#${id}Icon").attr("class", "icon- hide");
	                $("#${id}IconLabel").text("无");
	                $("#${id}").val("");
                }
            }, loaded:function(h){
//                $(".jbox-content", top.document).css("overflow-y","hidden");
            }
        });
	});
</script>
