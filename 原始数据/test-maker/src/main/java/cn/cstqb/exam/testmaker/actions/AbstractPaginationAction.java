package cn.cstqb.exam.testmaker.actions;

import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/12
 * Time: 23:00
 */
public abstract class AbstractPaginationAction extends BaseAction {
    protected int pageSize=Integer.MAX_VALUE;
    protected int pageNumber=1;
    protected int count;
    protected int pageCount;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getCount() {
        return count;
    }

    public int getPageCount() {
        return pageCount;
    }

    /**
     * method to load entity count from service layer
     */
    protected abstract void initEntityCount();

    /**
     * subclasses should implement this method to put business logic
     * @return
     */
    protected abstract String doExecuteImpl();

    /**
     * Each pagination action must implement this method as an action to load paging information.
     * @return
     */
    public String paging() {
        validateInput();
        if (hasActionErrors()) {
            return ERROR;
        }
        calculatePageCount();
        return SUCCESS;
    }
    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        calculatePageCount();
        return doExecuteImpl();
    }

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        validatePaginationParams();
    }

    protected void validatePaginationParams() {
        if (pageSize <1) {
            addActionError(getText("error.paging.required.pageSize", Lists.newArrayList(pageSize)));
            return;
        }
        if (pageNumber<1) {
            addActionError(getText("error.paging.required.pageNumber", Lists.newArrayList(pageNumber)));
        }
    }


    protected void calculatePageCount() {
        initEntityCount();
        pageCount = count / pageSize;
        int reminder = count % pageSize;
        pageCount = reminder > 0 ? pageCount = pageCount + 1 : pageCount;
    }

}
