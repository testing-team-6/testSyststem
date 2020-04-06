package cn.cstqb.exam.testmaker.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/21
 * Time: 20:13
 */
public interface Constants {

    public static final String ATTR_USER = "USER";
    public static final String ATTR_ADMIN_USER = "ADMIN";
    public static final String ATTR_PROJECT = "PROJECT";
    public static final String ATTR_RELEASE = "RELEASE";
    public static final String ATTR_FACILITATOR = "FACILITATOR";

    public static final String RESULT_LOGIN_ADMIN = "admin.login.result";
    public static final String RESULT_LOGIN = "index.login.result";

    public static final String RESULT_NO_PROJECTS = "NoProjects";
    public static final String RESULT_FORM_INVALID = "InvalidForm";
    public static final String RESULT_EXCEPTION = "exception";
    public static final String RESULT_NOT_FOUND = "NotFound";
    public static final String RESULT_NO_CONTENT = "NoContent";
    public static final String RESULT_NOT_MODIFIED = "NotModified";
    public static final String RESULT_NOT_MODIFIED_304 = "NotModified_304";
    public static final String RESULT_USER_LOCKED = "UserLocked";
    public static final String RESULT_USER_LOGIN_FAILED = "UserLoginFailed";
    public static final String RESULT_USER_NOT_AUTHENTICATED = "Unauthorized";
    public static final String RESULT_USER_NOT_AUTHORIZED = "Unauthorized";
    public static final String RESULT_ALREADY_EXISTS = "AlreadyExist";


    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FILE_UPLOAD_FAILED="UploadFailed";
    public static final String FILE_UPLOAD_UNMATCH="UnmatchFile";
    public static final String FILE_DELETE_FAILED="FileDeleteFailed";
}
