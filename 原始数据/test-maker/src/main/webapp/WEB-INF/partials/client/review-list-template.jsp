<%@ page contentType="text/html; charset=UTF-8"%>
<script id="review-list-template" type="text/x-handlebars-template">
    <table class="table table-condensed table-striped table-hover table-bordered">
        <tbody>
        {{#each comments}}
        <tr data-index="{{@index}}" data-id="{{id}}">
            <td class="col-md-1" style="width: 5%"><span>{{id}}</span></td>
            <td class="col-md-2 col-lg-2">
                <div>{{reviewer.fullName}}</div>
                <div>{{date updatedOn}}</div>
            </td>
            <td class="review-content"><div>{{content}}</div></td>
        </tr>
        {{/each}}
        </tbody>
    </table>
</script>
