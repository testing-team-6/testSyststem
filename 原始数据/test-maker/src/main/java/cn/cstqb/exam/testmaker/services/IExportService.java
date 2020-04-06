package cn.cstqb.exam.testmaker.services;

import cn.cstqb.exam.testmaker.entities.Project;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

/**
 * <div>
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao <br>
 * Date: 2015/11/9 <br>
 * Time: 19:46 <br>
 * </div>
 */
public interface IExportService {
    /**
     * Exports the specified project to the system export directory
     * @param project The project to be exported.
     * @return The number of questions exported.
     */
    int export(Project project) throws IOException, NoSuchAlgorithmException;
}
