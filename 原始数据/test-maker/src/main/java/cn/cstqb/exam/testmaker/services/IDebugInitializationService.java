package cn.cstqb.exam.testmaker.services;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/26
 * Time: 22:51
 */
public interface IDebugInitializationService {

    void setCount(int count);
    int getCount();
    void initUsers();
    void initSyllabus();

    void initQuestionAttributes();

}
