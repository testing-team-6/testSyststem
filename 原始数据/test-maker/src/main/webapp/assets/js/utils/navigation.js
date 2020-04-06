
/*
 * DOM variables
 */
var navPanel = $('#navigation-panel');
var workArea = $('.main');
var menuItem = $('.nav-menu-item');
var defaultPage = menuItem.first();


function Navigation() {}

/**
 * Load page through url parameter: src
 */
Navigation.loadPageByActionName=function(actionName) {
    console.log('Loading action: %s', actionName);
    var isValid = Navigation.isValidAction(actionName);
    if (isValid) {
        var menuItem = navPanel.find('a[href="' + actionName + '"]');
        Navigation.loadPage(menuItem);
    }else {
        console.warn('Invalid URL parameter value: %s. Loading default page', actionName);
        Navigation.loadPage(defaultPage);
    }
};

/**
 *
 * @param menuItem
 */
Navigation.loadPage=function (menuItem) {
    if (!menuItem) { // do nothing if the menu item is invalid: null/undefined
        return false;
    }
    /*
     * whenever which menu item is clicked, the current content pane will be cleared first.
     */
    workArea.children().detach();
    var url = menuItem.attr('href');
    if (url) {
        workArea.load(url, function () {
            console.log('Page Content [%s] loaded into work area!', url);
            Navigation.highlight(menuItem);
            document.location.hash=url;
        });
    }
}

/**
 * set the given menu as active
 * @param menuItem
 */
Navigation.highlight = function (menuItem) {
    Navigation.resetActiveMenu();
    menuItem.parent().addClass('active');
};


Navigation.resetActiveMenu=function () {
    //clear active class for all list items;
    menuItem.each(function () {
        $(this).parent().removeClass('active');
    });
};

Navigation.isValidAction=function (value) {
    if(value===undefined|| _.trim(value) === '') return false;
    return value.match(/^.+\.action$/i)
};
