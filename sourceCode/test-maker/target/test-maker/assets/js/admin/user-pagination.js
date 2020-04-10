/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/1/17
 * Time: 8:50
 */
(function () {
    var pageSize = 10;
    var pageIndex = 1;
    $(function () {
        /*
         * Load the first page on page load
         */
        loadData();
        /**
         * Load next page
         */
        $('#nextPage').click(function (e) {
            pageIndex++;
            console.log("Page to be loaded: %s", pageIndex);
            loadData();
            return false;
        });

        $('#prevPage').click(function () {
            pageIndex--;
            if (pageIndex < 1) {
                pageIndex = 1;
                return false;
            }
            console.log("Load previous page %s", pageIndex);
            loadData();
            return false;
        });

        /**
         *
         */
        function getPageCount(count) {
            var remainder = count % pageSize;
            var pageCount = 0;
            if (remainder > 0) {
                pageCount = (count - remainder) / pageSize + 1;
            } else {
                pageCount = count / pageSize;
            }
            console.log("Calculated page count: %s", pageCount);
            return pageCount;
        }


        function updatePagerState(pageCount) {
            var nextPageCtrl = $('#nextPage');
            var prevPageCtrl = $('#prevPage');
            var enablePager = function (pager) {
                pager.removeClass('disabled');
            };

            var disablePager = function (pager) {
                pager.addClass('disabled');
            };
            /*
             * Update first page state
             */
            if (pageIndex === 1) {
                disablePager(prevPageCtrl);
                //has more pages
                if (pageIndex < pageCount) {
                    enablePager(nextPageCtrl);
                }
            }

            /*
             * Update last page state
             */
            if (pageIndex >= pageCount) {
                console.warn('Last page is reached');
                pageIndex = pageCount;
                disablePager(nextPageCtrl);
                if (pageIndex > 1) {
                    enablePager(prevPageCtrl);
                }
            }

            /*
             * Middle pages
             */
            if (pageIndex > 1 && pageIndex < pageCount) {
                enablePager(prevPageCtrl);
                enablePager(nextPageCtrl);
            }
        }


        function loadData() {
            var pageOptions = {
                pageSize: pageSize,
                pageNumber: pageIndex
            };

            var countUrl = CONTEXT.ctx + '/web/user/count.action';

            $.post(countUrl, function (data) {
                console.log('user count: %s', data.count);
                /*
                 * Do nothing if last page is reached
                 */
                var pageCount = getPageCount(data.count);
                if (pageIndex >= 1 && pageIndex <= pageCount) {
                    loadPage();
                }
                updatePagerState(pageCount);
                return false;
            });

            var table = $('#user-table').bootstrapTable();

            var loadPage = function () {
                console.log("Loading data for page %s", pageIndex);
                $.get("list-user.action", pageOptions, function (data) {
                    var tableContent = $('#user-table > tbody');
                    tableContent.empty();

                    data.entities.forEach(function (user) {
                        //             console.dir(user);
                        var row = '<tr>' +
                            '<td><input type="checkbox"></td>' +
                            '<td>' + user.id + '</td>' +
                            '<td>' + user.username + '</td>' +
                            '<td><address>' + user.email + '</address></td>' +
                            '<td>' + (!user.phone ? '' : user.phone) + '</td>' +
                            '<td>' + user.createdOn + '</td>' +
                            '</tr>';
                        tableContent.append(row);

                    });
                });
            };

        }

    });
})();
