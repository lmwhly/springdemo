
$(function() {
	$('.dashboard').dashboard({
		afterPanelRemoved: function(panelId) {
			location.href = 'a/remove?id=' + panelId;
		},
		afterOrdered: function(panelOrders) {
			var ids = ''
			for (var key in panelOrders) {
				var id = key;
				var priority = panelOrders[key];
				ids += 'ids=' + id.replace('panel', '') + '&priorities=' + priority + '&';
			}
			location.href = 'a/updateOrder?' + ids;
		}
	});
});

function insertWidget() {
	$('#portalItemId').prop('disabled', true);
	$('#portalItemName').val('');
	$('#widgetModal').modal('show');
}

function updateWidget(id, widgetId, name) {
	$('#portalItemId').prop('disabled', false);
	$('#portalItemId').val(id);
	$('#portalWidgetId').val(widgetId);
	$('#portalItemName').val(name);
	$('#widgetModal').modal('show');
}

