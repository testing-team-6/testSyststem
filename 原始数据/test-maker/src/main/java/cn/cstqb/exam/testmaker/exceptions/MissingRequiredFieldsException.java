package cn.cstqb.exam.testmaker.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 21:12
 */
public class MissingRequiredFieldsException extends EntityPersistenceException {
    /**
     * Constructs an <code>IllegalArgumentException</code> with no
     * detail message.
     */
    public MissingRequiredFieldsException() {
    }

    /**
     * Constructs an <code>IllegalArgumentException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public MissingRequiredFieldsException(String s) {
        super(s);
    }
}
