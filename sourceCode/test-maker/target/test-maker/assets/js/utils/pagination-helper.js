function PaginationHelper(pagingAction, dataLoadingAction, dataLoadingCallback) {
    this.pageSizeSelector= $('.page-size-select');
    this.firstPageLink=$('.pagination > .first-page');
    this.lastPageLink=$('.pagination > .last-page');
    this.pageCount=0;
    this.currentPage= 1;
    this.pageSize=this.pageSizeSelector.find(':selected').val();
    this.pagingAction=pagingAction;
    this.dataLoadingAction = dataLoadingAction;
    this.callback=dataLoadingCallback;

    var that=this;
    this.pageSizeSelector.change(function () {
        that.pageSize = $(this).val();
        console.log('Selected page size: %s', that.pageSize);
        that.loadPagingInfo().done(function (data) {
            if (that.currentPage > that.pageCount) {
                that.currentPage=1;
            }
            that.loadDataForSelectedPage();
        });
    });

    $('.pagination').on('click','.page-number',function (e) {
        e.preventDefault();
        that.currentPage = $(this).data('id');
        console.log('About to show content for page %s', that.currentPage);
        that.loadDataForSelectedPage();
    });


    this.firstPageLink.click(function () {
        that.goToFirstPage();
    });

    this.lastPageLink.click(function () {
        that.goToLastPage();
    });
}

PaginationHelper.prototype.loadPagingInfo = function () {
    var that=this;
    return $.get(that.pagingAction,{pageSize: that.pageSize})
        .done(function (data, textStatus, jqXHR) {
            that.pageCount=data.pageCount;
            console.log('%d pages loaded for %s', that.pageCount, that.pagingAction);
            if(that.pageCount ===0) {
                that.currentPage=0;
                console.warn('Page count is zero!');
                that.highlightCurrentPage();
                return false;
            }
            var pageLinks=[];
            for(var i=1; i<= that.pageCount; i++) {
                pageLinks.push('<li class="page-number" data-id="'+i+'"><a href="#">'+ i+'</a></li>');
            }
            $('.pagination > .page-number').remove();
            that.firstPageLink.after(pageLinks.join(' '));
            that.highlightCurrentPage();
        });
};

PaginationHelper.prototype.highlightCurrentPage= function () {
    console.log('current page number: %s', this.currentPage);
    $('.pagination > .page-number').removeClass('active');

    //no data on the page
    if (this.pageCount === 0||this.currentPage === 0) {
        this.firstPageLink.addClass('disabled');
        this.lastPageLink.addClass('disabled');
        return false;
    }

    //first page
    if(this.currentPage ===1) {
        this.firstPageLink.addClass('disabled');
        if(this.pageCount > 1) {
            this.lastPageLink.removeClass('disabled');
            $('.pagination [data-id="' + this.currentPage + '"]').addClass('active');
        }else{
            this.lastPageLink.addClass('disabled');
        }
        return false;
    }

    //last page
    if (this.currentPage === this.pageCount) {
        this.firstPageLink.removeClass('disabled');
        this.lastPageLink.addClass('disabled');
        $('.pagination [data-id="' + this.currentPage + '"]').addClass('active');
        return false;
    }

    //in the middle
    if(this.pageCount > 1 && this.currentPage >= 1) {
        this.firstPageLink.removeClass('disabled');
        this.lastPageLink.removeClass('disabled');

        $('.pagination [data-id="' + this.currentPage + '"]').addClass('active');
    }
};

PaginationHelper.prototype.goToFirstPage =function () {
    this.currentPage=1;
    this.loadDataForSelectedPage();
};

PaginationHelper.prototype.goToLastPage =function (reload) {
    this.currentPage = this.pageCount;
    if (reload) {
        this.loadPagingInfo().done(this.loadDataForSelectedPage());
    }else{
        this.loadDataForSelectedPage();
    }
};

PaginationHelper.prototype.loadDataForSelectedPage =function () {
    var that=this;
    console.log('Loading data from: %s', this.dataLoadingAction);
    if (this.currentPage === 0) {
        console.error('Current page is zero. Maybe the property is not initialized properly');
        return false;
    }
    return AjaxUtils.loadData(this.dataLoadingAction,{
        pageSize: this.pageSize,
        pageNumber: this.currentPage
    },true)
        .done(function (data, textStatus, jqXHR) {
            that.pageCount=data.pageCount;
            that.highlightCurrentPage();
        })
        .done(that.callback);
};
