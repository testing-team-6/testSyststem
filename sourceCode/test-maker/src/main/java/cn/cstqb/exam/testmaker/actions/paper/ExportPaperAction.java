package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.entities.Paper;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Created with IntelliJ IDEA.
 * User: Lame-Lamb
 * Date: 2020/4/10
 * Time: 9:18
 */

public class ExportPaperAction extends BasePaperAction {
    @Override
    public String executeImpl() throws Exception {
        generatePaperPdf(1);

        HttpServletResponse resp = ServletActionContext.getResponse();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment;filename=test.pdf");
        ServletOutputStream out = resp.getOutputStream();
        String path = ServletActionContext.getServletContext().getRealPath("/");
        InputStream is = new FileInputStream(new File(path + "test.pdf"));
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        is.close();
        out.close();
        return SUCCESS;
    }

    private void generatePaperPdf(Integer paperId) {
        System.out.println(paperService == null);
        Paper paper = paperService.find(paperId);
        System.out.println(paper.getName());
    }
}