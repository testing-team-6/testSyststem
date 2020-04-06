package cn.cstqb.exam.testmaker.services.impl;

import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.exceptions.EntityPersistenceException;
import cn.cstqb.exam.testmaker.modules.ISyllabusModuleWorker;
import cn.cstqb.exam.testmaker.services.*;
import com.google.common.base.Strings;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/26
 * Time: 22:52
 */
public class DebugInitializationServiceImpl implements IDebugInitializationService {

    @Inject private IUserService userService;
    @Inject private IProjectService projectService;
    @Inject private IKnowledgePointService pointService;
    @Inject private ISyllabusModuleWorker syllabusWorker;
    @Inject private IQuestionLanguageService languageService;
    @Inject private IQuestionTypeService typeService;
    @Inject private IQuestionStatusService statusService;

    private int count=5;
    private Random random = new Random();
    private DecimalFormat decimalFormat = new DecimalFormat("000");

    @Override
    public void setCount(int count) {
        this.count=count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void initSyllabus() {
        if (syllabusWorker.findAll().size() >= count) {
            return;
        }

        for (int i = 0; i < count; i++) {
            String idStr = decimalFormat.format(i + 1);
            Syllabus syllabus = new Syllabus("TEST-LEVEL-" + idStr, "Version 1." + idStr);
            try {
                syllabusWorker.createOrUpdate(syllabus);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            initChapters(syllabus);
        }
    }

    private void initChapters(Syllabus syllabus) {
        for (int i = 0; i < count; i++) {
            String idStr = decimalFormat.format(i + 1);
            Chapter chapter = new Chapter("TEST CHAPTER "+idStr, syllabus);
            chapter.setNumber(String.format("%d.%d", random.nextInt(20), i));
            try {
                syllabusWorker.createOrUpdate(chapter);
            } catch (EntityPersistenceException e) {
                e.printStackTrace();
            }
            initKnowledgePoints(chapter,i+1);
        }
    }

    private void initKnowledgePoints(Chapter chapter, int chapterNumber) {
        if(pointService.findAll().size()>=count) return;
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String idStr = decimalFormat.format(i + 1);
            KnowledgePoint point = new KnowledgePoint(String.format("%s.%s.%s", chapterNumber,chapterNumber, idStr), "Knowledge point "+idStr, String.format("K%d",random.nextInt(5)), (short) (random.nextInt(3)+1), chapter);
            try {
                syllabusWorker.createOrUpdate(point);
            } catch (EntityPersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initUsers() {
        if(userService.findAllUsers().size()>=count) return;
        for (int i = 0; i < count; i++) {
            String idStr = decimalFormat.format(i + 1);
            String username = String.format("user-%s", idStr);
            User user = new User(username);
            user.setEmail(String.format("%s@test.com", username));
            user.setFullName(String.format("User %s", idStr));
            user.setPassword("test123");
            userService.createUser(user);
        }
    }

    @Override
    public void initQuestionAttributes() {
        initLanguages();
        initQuestionTypes();
        initQuestionStatuses();
    }

    private void initLanguages(){
        int existingRecords=languageService.findAll().size();
        if(existingRecords>=count) return;
        Locale[] locales=Locale.getAvailableLocales();
        int _count=count;
        for (int i = 0; i < _count; i++) {
            Locale locale = locales[i];
            String name = locale.getDisplayName();
            if (!Strings.isNullOrEmpty(name)) {
                QuestionLanguage language = new QuestionLanguage(locale.getDisplayName());
                languageService.saveOrUpdate(language);
            }else {
                _count++;
            }
        }
    }

    private void initQuestionTypes() {
        if(typeService.findAll().size()>=count) return;
        for (int i = 0; i < count; i++) {
            QuestionType type = new QuestionType(String.format("类型 %s", decimalFormat.format(i+1)));
            typeService.saveOrUpdate(type);
        }
    }

    private void initQuestionStatuses(){
        if(statusService.findAllStatuses().size() >=count) return;
        for (int i = 0; i < count; i++) {
            QuestionStatus status = new QuestionStatus(String.format("状态 %s", decimalFormat.format(i+1)));
            status.setStart(i==0);
            status.setFinish(i== count-1);
            statusService.saveOrUpdate(status);
        }
    }
}
