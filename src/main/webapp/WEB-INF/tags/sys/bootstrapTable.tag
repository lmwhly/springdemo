<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="cells" type="java.lang.String" required="true" description="编号" %>
<%@ attribute name="rows" type="java.lang.String" required="true" description="编号" %>


<table id="table"></table>

<script type="text/javascript">

    function buildTable($el, cells, rows) {
        var i, j, row,
                columns = [],
                data = [];

        for (i = 0; i < cells; i++) {
            columns.push(cells[i]);
        }
        for (i = 0; i < rows; i++) {
            row = {};
            for (j = 0; j < cells; j++) {
                row['field' + j] = 'Row-' + i + '-' + j;
            }
            data.push(row);
        }
        $el.bootstrapTable('destroy').bootstrapTable({
            columns: columns,
            data: data
        });
    }

    $(function () {
        buildTable($('#table'), ${cells}, ${rows});
    });
</script>