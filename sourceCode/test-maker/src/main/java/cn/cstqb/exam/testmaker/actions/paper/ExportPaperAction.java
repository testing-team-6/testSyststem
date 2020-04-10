package cn.cstqb.exam.testmaker.actions.paper;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created with IntelliJ IDEA.
 * User: Lame-Lamb
 * Date: 2020/4/10
 * Time: 9:18
 */

public class ExportPaperAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        HttpServletResponse resp = ServletActionContext.getResponse();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=test.txt");
        ServletOutputStream out = resp.getOutputStream();
        String path = ServletActionContext.getServletContext().getRealPath("/");    // resources/webapp
        InputStream is = new FileInputStream(new File(path + "test.txt")) {
        };
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        is.close();
        out.close();
        return SUCCESS;
    }
}