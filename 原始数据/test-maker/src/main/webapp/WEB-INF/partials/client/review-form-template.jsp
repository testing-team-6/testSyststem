<%@ page contentType="text/html; charset=UTF-8"%>
<script id="review-form-template" type="text/x-handlebars-template">


    <div id="{{reviewModalId}}" title="题目评审" class="review-comment-modal">
        <form id="{{formId}}">
            <div>
                <label for="qa-comment" class="col-md-2 control-label"> 评审内容 </label>
                <div class="col-md-10">
                    <textarea id="qa-comment" name="content" rows="5" class="form-control" ></textarea>
                </div>
            </div>
            <div style="margin-bottom: 15px">&nbsp;</div>

            <div class="form-group" style="width: auto">
                <label class="col-md-2 control-label">修改状态</label>
                <div class="col-md-10">
                    {{#each statuses}}
                    <button class="btn btn-primary" data-btn-form="{{../formId}}" data-id="{{id}}" data-index="{{@index}}">{{name}}</button>
                    {{/each}}
                </div>
            </div>
        </form>
    </div>

</script>
