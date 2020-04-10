/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/15
 * Time: 0:24
 */
$(function () {


    /*
     * DOM variables
     */
    var navPanel = $('#navigation-panel');
    var workArea = $('.main');
    var menuItem = $('.nav-menu-item');
    var defaultPage = menuItem.first();

    loadPageByUrlHash();

    /**
     * Event handling when clicking on the menu item.
     */
    menuItem.click(function (e) {
        loadPage($(this));
        e.preventDefault();
    });

    /**
     * Load page through url parameter: src
     */
    function loadPageByUrlHash() {
        var actionName=document.location.hash.replace(/#/i,'');
        console.log('Action loaded from hash: %s', actionName);
        var isValid = isValidAction(actionName);
        if (isValid) {
            var menuItem = navPanel.find('a[href="' + actionName + '"]');
            loadPage(menuItem);
        }else {
            console.warn('Invalid URL parameter value: %s. Loading default page', actionName);
            loadPage(defaultPage);
        }
    }

    /**
     *
     * @param menuItem
     */
    function loadPage(menuItem) {
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
                highlight(menuItem);
                document.location.hash=url;
            });
        }
    }

    /**
     * set the given menu as active
     * @param menuItem
     */
    function highlight(menuItem) {
        resetActiveMenu();
        menuItem.parent().addClass('active');
    }


    function resetActiveMenu() {
        //clear active class for all list items;
        menuItem.each(function () {
            $(this).parent().removeClass('active');
        });
    }

    function isValidAction(value) {
        if(value===undefined|| _.trim(value) === '') return false;
        return value.match(/^.+\.action$/i)
    }
});


