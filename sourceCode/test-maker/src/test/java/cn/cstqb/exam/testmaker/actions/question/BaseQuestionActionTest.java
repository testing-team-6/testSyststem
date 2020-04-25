package cn.cstqb.exam.testmaker.actions.question;

import cn.cstqb.exam.testmaker.configuration.Constants;
import cn.cstqb.exam.testmaker.entities.*;
import cn.cstqb.exam.testmaker.mailing.MailMessenger;
import cn.cstqb.exam.testmaker.mailing.MailNotificationFactory;
import cn.cstqb.exam.testmaker.mailing.SendMailTask;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceService;
import cn.cstqb.exam.testmaker.util.ServletUtils;
import freemarker.template.TemplateException;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts2.dispatcher.SessionMap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;


import javax.mail.internet.AddressException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.Assert.*;
public class BaseQuestionActionTest {

    private BaseQuestionAction action = new BaseQuestionAction() {

        @Override
        protected String executeImpl() {
            return null;
        }

        @Override
        public String getText(String aTextName) {
            return aTextName;
        }

        @Override
        public String getText(String aTextName, List<?> args) {
            return aTextName;
        }

        @Override
        public void addActionError(String anErrorMessage) {
            numOfCorrectAnswer -= 1;
        }

        @Override
        public void setSession(Map<String, Object> session) {

            super.session = new SessionMap(request) {
                @Override
                public Object get(Object key) {
                    Project prj = new Project();
                    prj.setStartDate(new Date(2020-1900,4-1,23));
                    prj.setFinishDate(new Date(2020-1900,4-1,29));
                    return prj;
                }
            };
        }

    };

    private Question question = new Question() {
        @Override
        public boolean validate() {
            return true;
        }

        @Override
        public boolean validateBasicFields() {
            return true;
        }
    };
    private HashSet<User> ul = new HashSet<>();
    private HashMap<String,Object> hashMap= new HashMap<>();
    User user = new User("3");
    @Before
    public void setUp() {
        action.setServletRequest(new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String name) {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String name) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return "1";
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean create) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public String changeSessionId() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String username, String password) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String name) throws IOException, ServletException {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public long getContentLengthLong() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String name) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return "1";
            }

            @Override
            public String getServerName() {
                return "1";
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String name, Object o) {

            }

            @Override
            public void removeAttribute(String name) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String path) {
                return null;
            }

            @Override
            public String getRealPath(String path) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }
        });
        action.numOfCorrectAnswer = 1;

        ul.add(user);
        question.setId(1);
        question.setReviewers(ul);
    }

    @Test//(question == null)
    public void testValidateInput1() {
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer, 0);
    }

    @Test//(question.getId() != null)&(question.getId() < 1)
    public void testValidateInput2() {
        Question question = new Question();
        question.setId(-1);
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer, 0);
    }

    @Test//(question.getId() != null)&(!question.validate())
    public void testValidateInput3() {
        Question question = new Question();
        question.setId(1);
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer, 0);
    }

    @Test//(!question.validateBasicFields())
    public void testValidateInput4() {
        Question question = new Question() {
            @Override
            public boolean validate() {
                return true;
            }
        };
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer, 0);
    }

    @Test//(question.getAuthor().equals(reviewer))
    public void testValidateInput5(){
        question.setAuthor(new User("3"));

        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthor().equals(question.getQualityAdmin()))
    public void testValidateInput6(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("2"));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringStartDate() == null || question.getAuthoringFinishDate() == null ||
    // question.getReviewingStartDate() == null || question.getReviewingFinishDate() == null)
    public void testValidateInput71(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringStartDate() == null || question.getAuthoringFinishDate() == null ||
    // question.getReviewingStartDate() == null || question.getReviewingFinishDate() == null)
    public void testValidateInput72(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringStartDate() == null || question.getAuthoringFinishDate() == null ||
    // question.getReviewingStartDate() == null || question.getReviewingFinishDate() == null)
    public void testValidateInput73(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24-1));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringStartDate() == null || question.getAuthoringFinishDate() == null ||
    // question.getReviewingStartDate() == null || question.getReviewingFinishDate() == null)
    public void testValidateInput74(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24-1));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringFinishDate().before(question.getAuthoringStartDate()))
    public void testValidateInput8(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24-1));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+1));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getReviewingFinishDate().before(question.getReviewingStartDate()))
    public void testValidateInput9(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+1));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26-1));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getReviewingStartDate().before(question.getAuthoringStartDate()))
    public void testValidateInput10(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+1));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26-3));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+1));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getReviewingFinishDate().before(question.getAuthoringFinishDate()))
    public void testValidateInput11(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+5));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+2));
        action.setQuestion(question);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringStartDate().before(((Project) session.get(Constants.ATTR_PROJECT)).getStartDate()) ||
    // question.getReviewingStartDate().before(((Project) session.get(Constants.ATTR_PROJECT)).getStartDate()))
    public void testValidateInput12(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24-10));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+2));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+2));


        action.setQuestion(question);

        action.setSession(hashMap);

        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }



    @Test//(question.getAuthoringFinishDate().after(((Project) session.get(Constants.ATTR_PROJECT)).getFinishDate()) ||
    // question.getReviewingFinishDate().after(((Project) session.get(Constants.ATTR_PROJECT)).getFinishDate())) {
    public void testValidateInput13(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+2));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+4));
        action.setQuestion(question);
        action.setSession(hashMap);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getAuthoringFinishDate().after(((Project) session.get(Constants.ATTR_PROJECT)).getFinishDate()) ||
    // question.getReviewingFinishDate().after(((Project) session.get(Constants.ATTR_PROJECT)).getFinishDate())) {
    public void testValidateInput131(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,30));
        question.setReviewingStartDate(new Date(2020-1900,5-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,5-1,26+4));
        action.setQuestion(question);
        action.setSession(hashMap);
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }

    @Test//(question.getId() != null && choiceService.findQuestionChoices(question.getId()) != null)
    public void testValidateInput14(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+2));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+2));
        action.setQuestion(question);
        action.setSession(hashMap);
        action.choiceService=new IQuestionChoiceService() {
            @Override
            public void createOrUpdate(QuestionChoice questionChoice) {

            }

            @Override
            public void delete(QuestionChoice questionChoice) {

            }

            @Override
            public Question findQuestion(QuestionChoice questionChoice) {
                return null;
            }

            @Override
            public QuestionChoice findQuestionChoice(int id) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(QuestionChoice QuestionChoice) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(Question question) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(int questionId) {
                ArrayList<QuestionChoice> al = new ArrayList<>();
                al.add(new QuestionChoice());
                return al;
            }

            @Override
            public QuestionChoice findLastQuestionChoice() {
                return null;
            }

            @Override
            public boolean haveSameLabel(QuestionChoice questionChoice) {
                return false;
            }

            @Override
            public boolean correctAnswerMatchType(QuestionChoice questionChoice) {
                return false;
            }

            @Override
            public boolean correctAnswerMatchType(Question question) {
                return false;
            }

            @Override
            public boolean exists(QuestionChoice questionChoice) {
                return false;
            }


        };
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,0);
    }
    @Test//(question.getId() != null && choiceService.findQuestionChoices(question.getId()) != null)
    public void testValidateInput141(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+2));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+2));
        action.setQuestion(question);
        action.setSession(hashMap);
        action.choiceService=new IQuestionChoiceService() {
            @Override
            public void createOrUpdate(QuestionChoice questionChoice) {

            }

            @Override
            public void delete(QuestionChoice questionChoice) {

            }

            @Override
            public Question findQuestion(QuestionChoice questionChoice) {
                return null;
            }

            @Override
            public QuestionChoice findQuestionChoice(int id) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(QuestionChoice QuestionChoice) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(Question question) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(int questionId) {
                ArrayList<QuestionChoice> al = new ArrayList<>();
                al.add(new QuestionChoice());
                return al;
            }

            @Override
            public QuestionChoice findLastQuestionChoice() {
                return null;
            }

            @Override
            public boolean haveSameLabel(QuestionChoice questionChoice) {
                return false;
            }

            @Override
            public boolean correctAnswerMatchType(QuestionChoice questionChoice) {
                return false;
            }

            @Override
            public boolean correctAnswerMatchType(Question question) {
                return true;
            }

            @Override
            public boolean exists(QuestionChoice questionChoice) {
                return false;
            }


        };
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,1);
    }

    @Test
    public void testValidateInput15(){
        question.setAuthor(new User("2"));
        question.setQualityAdmin(new User("3"));
        question.setAuthoringStartDate(new Date(2020-1900,4-1,24));
        question.setAuthoringFinishDate(new Date(2020-1900,4-1,24+2));
        question.setReviewingStartDate(new Date(2020-1900,4-1,26 ));
        question.setReviewingFinishDate(new Date(2020-1900,4-1,26+2));
        action.setQuestion(question);
        action.setSession(hashMap);
        action.choiceService=new IQuestionChoiceService() {
            @Override
            public void createOrUpdate(QuestionChoice questionChoice) {

            }

            @Override
            public void delete(QuestionChoice questionChoice) {

            }

            @Override
            public Question findQuestion(QuestionChoice questionChoice) {
                return null;
            }

            @Override
            public QuestionChoice findQuestionChoice(int id) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(QuestionChoice QuestionChoice) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(Question question) {
                return null;
            }

            @Override
            public List<QuestionChoice> findQuestionChoices(int questionId) {
                return null;
            }

            @Override
            public QuestionChoice findLastQuestionChoice() {
                return null;
            }

            @Override
            public boolean haveSameLabel(QuestionChoice questionChoice) {
                return false;
            }

            @Override
            public boolean correctAnswerMatchType(QuestionChoice questionChoice) {
                return false;
            }

            @Override
            public boolean correctAnswerMatchType(Question question) {
                return false;
            }

            @Override
            public boolean exists(QuestionChoice questionChoice) {
                return false;
            }


        };
        action.validateInput();
        assertEquals(action.numOfCorrectAnswer,1);
    }

    @Test
    public void testGetQuestion() {
        action.setQuestion(question);
        assertEquals(action.getQuestion(),question);
    }

    @Test
    public void testSetQuestion() {
        action.setQuestion(question);
        assertEquals(action.getQuestion(),question);
    }

    @Test
    public void testSendMailToQuestionUser() throws AddressException, TemplateException, IOException, EmailException {

        IQuestionChoiceService choiceService = mock(IQuestionChoiceService.class);
        action.choiceService= choiceService;
        ArrayList<QuestionChoice> questionChoices = new ArrayList<>();
        questionChoices.add(new QuestionChoice());
        when(choiceService.findQuestionChoices(question)).thenReturn(questionChoices);

        action.setQuestion(question);

        action.sendMailToQuestionUser(question,QuestionRole.QA);

    }

    @Test
    public void testSendMailToAllRoles() throws AddressException, TemplateException, EmailException, IOException {
        IQuestionChoiceService choiceService = mock(IQuestionChoiceService.class);
        action.choiceService= choiceService;
        ArrayList<QuestionChoice> questionChoices = new ArrayList<>();
        questionChoices.add(new QuestionChoice());
        when(choiceService.findQuestionChoices(question)).thenReturn(questionChoices);

        MailNotificationFactory mailFactory = new MailNotificationFactory(){
            @Override
            public HtmlEmail buildQuestionNotification(Question question, List<QuestionChoice> choices, QuestionRole role) throws EmailException, TemplateException, IOException {
                return new HtmlEmail();
            }
        };
        action.mailFactory = mailFactory;

        action.sendMailToAllRoles(question);
    }

    @Test
    public void testSendMailToAllRoles2() throws AddressException, TemplateException, EmailException, IOException {
        IQuestionChoiceService choiceService = mock(IQuestionChoiceService.class);
        action.choiceService= choiceService;
        ArrayList<QuestionChoice> questionChoices = new ArrayList<>();
        questionChoices.add(new QuestionChoice());
        when(choiceService.findQuestionChoices(question)).thenReturn(questionChoices);

        MailNotificationFactory mailFactory = new MailNotificationFactory(){
            @Override
            public HtmlEmail buildQuestionNotification(Question question, List<QuestionChoice> choices, QuestionRole role) throws EmailException, TemplateException, IOException {
                return null;
            }
        };
        action.mailFactory = mailFactory;

        action.sendMailToAllRoles(question);
    }


}