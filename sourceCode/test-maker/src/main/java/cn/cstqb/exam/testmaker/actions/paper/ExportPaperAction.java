package cn.cstqb.exam.testmaker.actions.paper;

import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import cn.cstqb.exam.testmaker.services.IPaperService;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.services.IQuestionService;
import cn.cstqb.exam.testmaker.services.impl.QuestionServiceImpl;
import com.google.inject.Inject;
import com.google.common.collect.Lists;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import static org.apache.struts2.ServletActionContext.getServletContext;


public class ExportPaperAction extends BasePaperAction {
    private int paperId;

    void setPaperService(IPaperService paperService){
        this.paperService = paperService;
    }
    void setQuestionService(IQuestionChoiceService questionChoiceService){
        this.questionChoiceService = questionChoiceService;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Override
    public void validateInput() {
        if (paperId < 1) {
            addActionError(getText("error.paper.id.invalid", Lists.newArrayList(paperId)));
        }
    }

    @Override
    public String executeImpl() throws Exception {
        generatePaperPdf(paperId);

        HttpServletResponse resp = ServletActionContext.getResponse();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment;filename=test.pdf");
        ServletOutputStream out = resp.getOutputStream();
        String path = getServletContext().getRealPath("/");
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

    private void generatePaperPdf(int paperId) {
        try {

            // 1.新建document对象
            Document document = new Document();

            // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
            // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
            String path = getServletContext().getRealPath("/");
            File file = new File(path + "test.pdf");
//            if(file.exists()){//删除以往的PDF文件
//                file.delete();
//            }
            file.createNewFile(); //创建新的PDF
            PdfWriter.getInstance(document, new FileOutputStream(file));

            // 3.打开文档
            document.open();

            // 中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese);

            // 4.添加内容
            Paper paper = paperService.find(paperId);
            document.add(new Phrase(paper.getName() + "\n",font)); //添加试卷名称
            Set<Question> questionList = paper.getQuestions();

            int count = 1; //题号
            for (Question q:questionList
            ) {
                document.add(new Phrase(count + ". " + q.getType().getName() + "  (" + q.getScore() + "分)", font));
                document.add(new Paragraph(q.getStem(),font));//题干
                document.add(new Paragraph(q.getScenario(),font));//情景

                List<QuestionChoice> questionChoices = questionChoiceService.findQuestionChoices(q);//选项

                if(questionChoices != null){
                    if(q.isMultipleChoice()){
                        document.add(new Paragraph("(多选题）",font));
                    }else {
                        document.add(new Paragraph("(单选题）",font));
                    }

                    for (QuestionChoice choice :
                            questionChoices) {
                        String str = choice.getChoiceLabel() + ". " + choice.getContent();
                        document.add(new Paragraph(str, font));
                    }
                }

                document.add(new Paragraph("\n", font));
                count ++;
            }

            // 5.关闭文档
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}