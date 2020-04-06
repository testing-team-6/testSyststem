/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/2/8
 * Time: 15:44
 */

function Dialogs() {
}

Dialogs.prototype.showDialog= function (message,type, options, callback) {
    var defaultOptions = {
        message: message,
        type: type,
        nl2br: false,
        callback: callback
    };
    var mergedOptions = $.extend(true, defaultOptions, options);
    return BootstrapDialog.alert(mergedOptions);
};


/**
 * Show a warning dialog
 * @type {alert}
 */
Dialogs.warning = function (message) {
    BootstrapDialog.alert({
        message: message,
        type: BootstrapDialog.TYPE_WARNING
    });
};

/**
 * Shows an error dialog
 * @param message The message to show up
 * @param large  True to show a large modal dialog
 * @param callback
 */
Dialogs.error = function (message,callback, large) {

    var callBackFunc, largeDialog;
    if (arguments.length === 2) {
        if (_.isFunction(arguments[1])) {
            callBackFunc=arguments[1];
        }
        if (_.isBoolean(arguments[1])) {
            largeDialog = arguments[1];
        }
    }else if (arguments.length === 3) {
        callBackFunc=callback;
        largeDialog = large;
    }

     return new BootstrapDialog({
         type: BootstrapDialog.TYPE_DANGER,
         nl2br: false,
         size: largeDialog?BootstrapDialog.SIZE_WIDE:BootstrapDialog.SIZE_NORMAL,
         data: {
             callback: callBackFunc
         },
         message: message,
         onhide: function(dialog) {
             !dialog.getData('btnClicked') && dialog.isClosable() && typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(false);
         },
         buttons: [{
             label: BootstrapDialog.DEFAULT_TEXTS.OK,
             action: function(dialog) {
                 dialog.setData('btnClicked', true);
                 typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(true);
//                 dialog.close();
                 BootstrapDialog.closeAll();
             }
         }]
     }).open();
};

/**
 *
 * @param message The message shown to the user
 * @param callback The callback function for button event. Optional
 * @param large to to show the dialog in larger size
 * @returns {boolean}
 */
Dialogs.confirm = function (message, callback, large) {
    var largeModal=false;
    if (!_.isUndefined(large) && _.isBoolean(large)) {
        largeModal = large;
    }
    var defaultOptions = {
        type: BootstrapDialog.TYPE_WARNING,
        title: BootstrapDialog.DEFAULT_TEXTS.CONFIRMATION,
        message: message,
        closable: false,
        nl2br: false,
        size: !largeModal?BootstrapDialog.SIZE_NORMAL:BootstrapDialog.SIZE_WIDE,
        data: {
            'callback': callback
        },
        buttons: [
            {
                label: BootstrapDialog.DEFAULT_TEXTS.CANCEL,
                action: function (dialog) {
                    typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(false);
//                    dialog.close();
                    BootstrapDialog.closeAll();
                }
            },
            {
                label: BootstrapDialog.DEFAULT_TEXTS.OK,
                cssClass: 'btn-primary',
                action: function (dialog) {
                    typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(true);
//                    dialog.close();
                    BootstrapDialog.closeAll();
                }
            }
        ]
    };
    BootstrapDialog.show(defaultOptions);
};
/**
 * Informational dialog
 * @param message
 * @param delay
 * @param large
 */
Dialogs.info = function (message, delay) {
    var defaultOptions = {
        size: BootstrapDialog.SIZE_WIDE
    };
    var dlg=new Dialogs().showDialog(message, BootstrapDialog.TYPE_INFO, defaultOptions);
    if (delay) {
        setTimeout(function () {
            dlg.close();
        },delay);
    }
};

Dialogs.previewImage = function (image) {
    var content = "<div style='height: 480px' align='center'><img src='" + image + "' class='img-responsive img-thumbnail'/></div>";

    var defaultOptions = {
        type: BootstrapDialog.TYPE_INFO,
        title: '图片预览',
        message: content,
        closable: true,
        nl2br: false,
        size: BootstrapDialog.SIZE_WIDE,
        buttons: [
            {
                label: '关闭',
                cssClass: 'btn-primary',
                action: function (dialog) {
                    typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(true);
                    dialog.close();
                    BootstrapDialog.closeAll();
                }
            }
        ]
    };
    BootstrapDialog.show(defaultOptions);
};

/**
 * Success dialog
 * @param message
 */
Dialogs.success = function (message) {
    new Dialogs().showDialog(message, BootstrapDialog.TYPE_SUCCESS);
};

Dialogs.showHints = function (hints) {
    var message='<ul class="list-group">';
    for(var hint in hints) {
        message=message+'\n<li class="list-group-item list-group-item-warning">'+hints[hint]+'</li>';
    }
    message=message+'\n</ul>';
    Dialogs.error(message, true);
};

Dialogs.showActionErrors = function (errors) {
    var source = '<ul class="list-group">' +
        '{{#each errors}}' +
        '<li class="list-group-item list-group-item-warning">{{this}}</li>' +
        '{{/each}}</ul>';
    var template = Handlebars.compile(_.unescape(source));
    Dialogs.error(template({errors: errors}), true);
};

Dialogs.showAjaxLoadInfo = function (jqContainer, dataSize) {
    AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/ajax-load-info.hbs.html', function (template) {
        jqContainer.html(template({size: dataSize}));
        var ajaxAlert = $('#load-alert');
        ajaxAlert.alert();
        setTimeout(function () {
            ajaxAlert.alert('close');
        }, 800);
    })
};

Dialogs.showAjaxInfo = function (jqContainer, result) {
    AjaxUtils.getTemplateAjax(CONTEXT.ctx + '/assets/templates/questions/ajax-success-manipulate.hbs.html', function(template){
        jqContainer.html(template({result: result}))
        var ajaxAlert = $('#manipulate-alert');
        ajaxAlert.alert();
        setTimeout(function(){
            ajaxAlert.alert('close');
        }, 800);
    })
}


Dialogs.ajaxError = function (jqXHR) {
    console.log('Ajax error occurred');
    console.dir(jqXHR);
    if (jqXHR.responseJSON && jqXHR.responseJSON.actionErrors) {
        Dialogs.showActionErrors(jqXHR.responseJSON.actionErrors)
    }else if(jqXHR.status === 401){
        Dialogs.error('您还未登录，请先到登录页面完成登录认证！');
    }else{
        Dialogs.error(jqXHR.responseText.replace(/\n/,' '), true);
    }
};
