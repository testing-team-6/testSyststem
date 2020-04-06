package cn.cstqb.exam.testmaker.exceptions;

import javax.management.InstanceAlreadyExistsException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 21:00
 */
public class EntityAlreadyExistsException extends InstanceAlreadyExistsException {
    /**
     * Default constructor.
     */
    public EntityAlreadyExistsException() {
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message the detail message.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
