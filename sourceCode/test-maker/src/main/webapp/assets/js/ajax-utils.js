'use strict';

function AjaxUtils() {
}
/**
 *
 * @param param
 * @param successCallback
 * @returns {{type: string, async: boolean, contentType: string, cache: boolean, data: null, dataType: string, success: null}}
 */
AjaxUtils.createAjaxSettings = function (param, successCallback) {
    var data = _.isNull(param) || _.isUndefined(param) ? null : param;
    if (data) {
        //console.log('The provided ajax parameter object.');
        //console.dir(data);
        data = JSON.stringify(data);
    }
    return {
        type: 'POST',
        async: true,
        contentType: 'application/json',
        cache: false,
        data: data,
        dataType: 'json',
        success: successCallback === 'undefined' ? null : successCallback
    };
};
/**
 * Submit the input data through the URL. If submission is successful, a success message dialog will appear.
 * @param url
 * @param data
 * @param silent flag to popup dialog
 * @returns {*}
 */
AjaxUtils.postData = function (url, data, silent) {
    console.log('Posting data to URL [%s].', url);
    return $.ajax(url, AjaxUtils.createAjaxSettings(data))
        .done(function (data, textStatus, jqXHR) {
            if (!silent) {
                Dialogs.success('操作成功！');
                //Dialogs.showAjaxInfo($('.msg-area'), "成功");
            }
            return false;
        })
        .error(function (jqXHR, textStatus, errorThrown) {
            Dialogs.ajaxError(jqXHR);
        });
};
AjaxUtils.postSilentData = function (url, data, silent) {
    console.log('Posting data to URL [%s].', url);
    return $.ajax(url, AjaxUtils.createAjaxSettings(data))
        .done(function (data, textStatus, jqXHR) {
            if (!silent) {
                Dialogs.showAjaxLoadInfo($('.msg-area'), 1);
            }
            return false;
        })
        .error(function (jqXHR, textStatus, errorThrown) {

        })
}
/**
 *
 * @param url
 * @param data
 * @param async
 * @returns {*}
 */
AjaxUtils.loadData = function (url, data, async) {
    console.log('load data from URL [%s].', url);
    console.log('Parameters: %s', JSON.stringify(data));
    if (!data) {
        data = {};
    }
    var options = $.extend(true, AjaxUtils.createAjaxSettings(data), {
        async: async === undefined ? true : async
    });
    return $.ajax(url, options)
        .done(function (data, textStatus, jqXHR) {
            console.log('Data loaded successfully!');
            return false;
        })
        .error(function (jqXHR, textStatus, errorThrown) {
            Dialogs.ajaxError(jqXHR);
        });
};

/**
 * Load data from server and the Content-Type is revert to default
 * @param url action url
 * @param data The parameter object. A plain object or string that is sent to the server with the request.
 */
AjaxUtils.getData = function (url, data) {
    var options = $.extend(true, AjaxUtils.createAjaxSettings(), {
        type: 'POST',
        data: data ? JSON.stringify(data) : '{}',
        contentType: 'application/json'
    });
    //delete options.contentType;
    return $.ajax(url, options)
        .done(function (data, textStatus, jqXHR) {
            console.log('Data loaded successfully!');
            return false;
        })
        .error(function (jqXHR, textStatus, errorThrown) {
            console.log('Error occurred');
            console.dir(jqXHR);
            Dialogs.ajaxError(jqXHR);
        });
};

/**
 * Simple wrapper for $.get()
 * @param url
 * @param data
 * @returns {*}
 */
AjaxUtils.get = function (url, data) {
    return $.get(url, data).error(function (jqXHR, textStatus, errorThrown) {
        console.log('Error occurred');
        console.dir(jqXHR);
        Dialogs.ajaxError(jqXHR);
    });
};

AjaxUtils.getTemplateAjax = function (path, callback) {
    if (arguments.length !== 2 || !path || !callback) {
        console.error('Path and callback formal parameters are required!');
        return false;
    }
    if (_.trim(path) === '') {
        console.error('Path is invalid: [%s]', path);
        return false;
    }
    if (!_.isFunction(callback)) {
        console.error('The callback parameter must be a function');
        return false;
    }
    return $.ajax({
        url: path,
        cache: true//,
        //success: function (data) {
        //    var template = Handlebars.compile(data);
        //
        //    //execute the callback if passed
        //    if (callback) {
        //        return callback(template);
        //    }
        //}
    }).done(function (data) {//data here is the content of the template file
        return callback(Handlebars.compile(data));
    });
};
