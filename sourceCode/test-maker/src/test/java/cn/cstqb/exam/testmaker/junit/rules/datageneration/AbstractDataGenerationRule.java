package cn.cstqb.exam.testmaker.junit.rules.datageneration;

import cn.cstqb.exam.testmaker.junit.rules.AbstractJpaRule;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:53
 */
public abstract class AbstractDataGenerationRule extends AbstractJpaRule {
    protected int dataCount=20;

    public AbstractDataGenerationRule() {
        super();
    }

    @Override
    protected void _before() throws Throwable {
        init();
        populate();
    }

    protected abstract void init();
    public abstract void populate();

    @Override
    protected void _after() {

    }

    public int getDataCount() {
        return dataCount;
    }
}
