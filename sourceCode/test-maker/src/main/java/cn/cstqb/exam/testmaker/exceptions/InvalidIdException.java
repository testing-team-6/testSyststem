package cn.cstqb.exam.testmaker.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/4
 * Time: 15:09
 */
public class InvalidIdException extends EntityException {

    private static final String message="Invalid entity id. The entity id value must be greater than zero!";
    /**
     * Constructs an <code>IllegalArgumentException</code> with no
     * detail message.
     */
    public InvalidIdException() {
        super(message);
    }

    public InvalidIdException(Integer id) {
        super(String.format(message+" Actual: %d", id));
    }
}
