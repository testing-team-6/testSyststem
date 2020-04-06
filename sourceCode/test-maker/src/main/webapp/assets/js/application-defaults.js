/**
 *
 * Global defaults
 */
/*$.expr[":"].contains = $.expr.createPseudo(function(arg) {
    return function( elem ) {
        return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
    };
});*/

$.extend($.expr[":"], {
    "containsNC": function(elem, i, match, array) {
        return (elem.textContent || elem.innerText || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
    }
});


TM = {
    ctx: APP_SESSION.ctx
};

CONTEXT = APP_SESSION;

/**
 *
 * Setting moment.js defaults
 */
    //globally sets moment.js locale
moment.locale(['zh', 'zh-CN', 'zh-Hans-CN']);


/**
 * BootstrapDialog translation messages
 */
BootstrapDialog.DEFAULT_TEXTS = {
    OK: '确认',
    CANCEL: '取消',
    CONFIRMATION: '确认'
};
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DEFAULT] = '信息';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_INFO] = '信息';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_PRIMARY] = '信息';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_SUCCESS] = '成功';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_WARNING] = '警告';
BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DANGER] = '错误';

function BbootstrapDualListbox(){};
BbootstrapDualListbox.options = {
    filterTextClear: '显示全部',
    infoText: '显示全部',
    infoTextEmpty: '空列表',
    filterPlaceHolder: '搜索',
    moveAllLabel:'全部添加',
    removeAllLabel: '全部删除'
};

function FormValidators(){}
FormValidators.validateSelect2 = function (selector) {
    var s2;
    if(_.isString(selector)) {
        s2 = $(selector);
    }else if(_.isObject(selector)) {
        s2 = selector;
    }

    var selectedOption = s2.select2().find(':selected');
    if(selectedOption.val().length <1) {
        Dialogs.error('请从下拉列表选择一项！');
        return false;
    }
    return true;
};

/**
 * Utility for data table filtering
 * @constructor
 */
function TableFilter(table, searchBox){
    this.table=table;
    this.searchBox=searchBox;
    var that=this;
/*    this.searchBox.on('keyup', function () {
        var text= $.trim($(this).val());
        that.search(text);
    });*/
    this.searchBox.on('keyup', function () {
        var text= $.trim($(this).val());
        if(text.length ===0||text===undefined){
            that.clearFilter();
        }
    });
    this.searchBox.bind('blur change', function (e) {
        var text= $.trim($(this).val());
        that.search(text);
    });

}
TableFilter.prototype.search = function (text) {
    if(text.length ===0||text===undefined){
        this.clearFilter();
    }else {
        this.table.find('tbody>tr').filter(':not(:containsNC("'+text+'"))').addClass('hidden');
    }
};

/**
 * Reset the visibility of data table and show all existing rows.
 */
TableFilter.prototype.clearFilter = function () {
    this.table.find('tbody>tr').removeClass('hidden');
};
