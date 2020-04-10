package cn.cstqb.exam.testmaker.util;

import cn.cstqb.exam.testmaker.dao.PaperDao;
import cn.cstqb.exam.testmaker.dao.impl.PaperDaoImpl;
import cn.cstqb.exam.testmaker.entities.Paper;
import cn.cstqb.exam.testmaker.entities.Question;
import cn.cstqb.exam.testmaker.entities.QuestionChoice;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class PDFUtils {
    public static void exportPDF(int paperId) throws FileNotFoundException, DocumentException, IOException {
        // 1.新建document对象
        Document document = new Document();

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter.getInstance(document, new FileOutputStream("./pdf/test.pdf"));

        // 3.打开文档
        document.open();

        // 中文字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese);

        // 4.添加一个内容段落

        PaperDao paperDao = new PaperDaoImpl();
        Paper paper = paperDao.findById(paperId);

        List<Question> questionList = paper.getQuestions();
        int count = 1; //题号
        for (Question q:questionList
             ) {
            document.add(new Phrase(count + ". " + q.getType().getName() + "  (" + q.getScore() + "分)", font));
            document.add(new Paragraph(q.getStem(),font));//题干
            document.add(new Paragraph(q.getScenario(),font));//情景

            Set<QuestionChoice> questionChoices = q.getChoices();//选项
            if(questionChoices.size() > 0){
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
    }
}
