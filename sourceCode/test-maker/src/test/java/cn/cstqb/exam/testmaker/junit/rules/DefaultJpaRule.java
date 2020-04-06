package cn.cstqb.exam.testmaker.junit.rules;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/27
 * Time: 8:48
 */
public class DefaultJpaRule extends AbstractJpaRule {
    public DefaultJpaRule(boolean ddlCreate) {
        super();
        if (ddlCreate) {
            System.setProperty("jpa.hibernate.hbm2ddl.auto", "create");
        } else {
            System.setProperty("jpa.hibernate.hbm2ddl.auto", "update");
        }
    }

    public DefaultJpaRule() {
        super();
    }

    @Override
    protected void _before() throws Throwable {

    }

    @Override
    protected void _after() {

    }
}
