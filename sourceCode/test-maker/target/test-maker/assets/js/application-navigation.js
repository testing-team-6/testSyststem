function AppNavigator(){
    this.pageContainer = $('.main');
    this.menuItem=null;
/*    this.menuItem.click = function (e) {
//        this.loadPageByUrl(this.menuItem.attr('href'));
        e.preventDefault();
    };*/
}

AppNavigator.prototype.loadPageByUrl = function (url, callback) {
    if (arguments.length <1) {//no param
        console.error('Page URL is required');
        return false;
    }

    if (arguments.length===1 && !_.isString(url)) {
        console.error('Page URL is invalid and should be a string: %s', url);
        return false;
    }
    if (callback && !_.isFunction(callback)) {
        console.error('Callback is not a function');
        return false;
    }

    this.pageContainer.children().detach();
    this.pageContainer.load(url, function () {
        console.log('Page Content [%s] loaded into work area!', url);
        document.location.hash=url;
        if (callback !== undefined) {
            callback();
        }
    });
};

/**
 * Load page through url parameter: src
 */
AppNavigator.prototype.loadPageByUrlHash = function (fallbackUrl) {
    var path = document.location.hash.replace(/#/i, '');
    console.log('Action loaded from hash: %s', path);
    var isValid = AppNavigator.isValidAction(path);
    if (isValid) {
        this.loadPageByUrl(path);
    } else {
        console.warn('Invalid URL parameter value: %s. Loading default page', path);
        this.loadPageByUrl(fallbackUrl);
    }
};


AppNavigator.isValidAction = function (value) {
    if (value === undefined || _.trim(value) === '') return false;
    return value.match(/^.+\.action$/i)
};
