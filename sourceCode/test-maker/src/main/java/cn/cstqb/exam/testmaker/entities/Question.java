package cn.cstqb.exam.testmaker.entities;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/29
 * Time: 22:38
 */
@Entity
public class Question extends AbstractBaseEntity {
    @Column(length = 40)
    private String description;

    @Transient
    private Map<Integer, String> difficultyMapping;

    @Column(columnDefinition = "TEXT")
    private String scenario;

    //TODO facilitator can edit show as a reminder to others
    @JsonIgnore
    private String memo;

    /*
     * Make stem to be nullable is for create only a skeleton of the question entity.
     */
    @Column(nullable = true, columnDefinition = "TEXT")
    private String stem;

    @Column(nullable = false)
    private int difficulty;

    @Transient
    @JsonIgnore
    private String difficultyLabel;

    @OneToOne
    @JoinColumn(nullable = false)
    private QuestionLanguage language;

    @OneToOne
    @JoinColumn(nullable = false)
    private QuestionType type;

    @Column(nullable = false, columnDefinition = "BIT(1) DEFAULT 0")
    private boolean multipleChoice;

    @Column(nullable = false)
    private short score;


    @ManyToOne(cascade = CascadeType.REMOVE)
    private KnowledgePoint knowledgePoint;

    @ManyToMany
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "question_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "reviewer_id")
            }
    )
    private Set<User> reviewers;

    @Transient
    @OneToMany(mappedBy = "question")
    private Set<QuestionChoice> choices;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @OneToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @JsonIgnore
    private Date authoringStartDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @JsonIgnore
    private Date authoringFinishDate;

    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date reviewingStartDate;

    @Temporal(TemporalType.DATE)
    @JsonIgnore
    private Date reviewingFinishDate;

    @OneToOne
    @JoinColumn(name = "qualityAdmin_id", nullable = false)
    private User qualityAdmin;

    @OneToOne
    @JoinColumn(nullable = false)
    private QuestionStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date published;

    @OneToMany(mappedBy = "question")
    private Set<QuestionImage> images;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @JsonIgnore
    private String customFiled1;
    @JsonIgnore
    private String customField2;
    @JsonIgnore
    private String customField3;

    public Question(String stem, int difficulty, QuestionLanguage language,
                    QuestionType type, short score, Project project, User author) {
        this.stem = stem;
        this.difficulty = difficulty;
        this.language = language;
        this.type = type;
        this.score = score;
        this.project = project;
        this.author = author;
        difficultyMapping = ApplicationConfigContext.getInstance().getQuestionDifficultyLevels();
    }

    public Question(Project project) {
        this.project = project;
        difficultyMapping = ApplicationConfigContext.getInstance().getQuestionDifficultyLevels();
    }

    public Question() {
        difficultyMapping = ApplicationConfigContext.getInstance().getQuestionDifficultyLevels();
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public QuestionLanguage getLanguage() {
        return language;
    }

    public void setLanguage(QuestionLanguage language) {
        this.language = language;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public Set<QuestionChoice> getChoices() {
        return choices;
    }

    public void setChoices(Set<QuestionChoice> choices) {
        this.choices = choices;
    }

    public KnowledgePoint getKnowledgePoint() {
        return knowledgePoint;
    }

    public void setKnowledgePoint(KnowledgePoint knowledgePoint) {
        this.knowledgePoint = knowledgePoint;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getAuthoringStartDate() {
        return authoringStartDate;
    }

    public void setAuthoringStartDate(Date authoringStartDate) {
        this.authoringStartDate = authoringStartDate;
    }

    public Date getAuthoringFinishDate() {
        return authoringFinishDate;
    }

    public void setAuthoringFinishDate(Date authoringFinishDate) {
        this.authoringFinishDate = authoringFinishDate;
    }

    public Date getReviewingStartDate() {
        return reviewingStartDate;
    }

    public void setReviewingStartDate(Date reviewingStartDate) {
        this.reviewingStartDate = reviewingStartDate;
    }

    public Date getReviewingFinishDate() {
        return reviewingFinishDate;
    }

    public void setReviewingFinishDate(Date reviewingFinishDate) {
        this.reviewingFinishDate = reviewingFinishDate;
    }

    public User getQualityAdmin() {
        return qualityAdmin;
    }

    public void setQualityAdmin(User qualityAdmin) {
        this.qualityAdmin = qualityAdmin;
    }

    public QuestionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestionStatus status) {
        this.status = status;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getDifficultyLabel() {
        return this.difficultyLabel;
    }

    public Set<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<User> reviewers) {
        this.reviewers = reviewers;
    }

    public Set<QuestionImage> getImages() {
        return images;
    }

/*    public void setImages(Set<QuestionImage> images) {
        this.images = images;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String customField1) {
        this.remark = customField1;
    }

    public String getCustomFiled1() {
        return customFiled1;
    }

    public void setCustomFiled1(String customFiled1) {
        this.customFiled1 = customFiled1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public String getCustomField3() {
        return customField3;
    }

    public void setCustomField3(String customField3) {
        this.customField3 = customField3;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description, difficultyMapping, scenario, stem, difficulty, difficultyLabel, language, type, multipleChoice, score, reviewers, project, author, authoringStartDate, authoringFinishDate, reviewingStartDate, reviewingFinishDate, qualityAdmin, status, published);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        return Objects.equal(this.description, other.description) && Objects.equal(this.difficultyMapping, other.difficultyMapping) && Objects.equal(this.scenario, other.scenario) && Objects.equal(this.stem, other.stem) && Objects.equal(this.difficulty, other.difficulty) && Objects.equal(this.difficultyLabel, other.difficultyLabel) && Objects.equal(this.language, other.language) && Objects.equal(this.type, other.type) && Objects.equal(this.multipleChoice, other.multipleChoice) && Objects.equal(this.score, other.score) && Objects.equal(this.project, other.project) && Objects.equal(this.author, other.author) && Objects.equal(this.authoringStartDate, other.authoringStartDate) && Objects.equal(this.authoringFinishDate, other.authoringFinishDate) && Objects.equal(this.reviewingStartDate, other.reviewingStartDate) && Objects.equal(this.reviewingFinishDate, other.reviewingFinishDate) && Objects.equal(this.qualityAdmin, other.qualityAdmin) && Objects.equal(this.status, other.status) && Objects.equal(this.published, other.published);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("description", description)
                .add("difficultyMapping", difficultyMapping)
                .add("scenario", scenario)
                .add("stem", stem)
                .add("difficulty", difficulty)
                .add("language", language)
                .add("type", type)
                .add("multipleChoice", multipleChoice)
                .add("score", score)
                .add("project", project)
                .add("author", author)
                .add("authoringStartDate", authoringStartDate)
                .add("authoringFinishDate", authoringFinishDate)
                .add("reviewingStartDate", reviewingStartDate)
                .add("reviewingFinishDate", reviewingFinishDate)
                .add("qualityAdmin", qualityAdmin)
                .add("status", status)
                .add("published", published)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

    @PrePersist
    @PreUpdate
    private void setDefaults() {
        this.difficulty = 2;
        if (knowledgePoint != null) {
            this.score = knowledgePoint.getScore();
        }
    }

    @PostLoad
    private void onLoad() {
        this.difficultyLabel = difficultyMapping.get(this.difficulty);
//        if (knowledgePoint != null && this.score!=knowledgePoint.getScore()) {
//            this.score=knowledgePoint.getScore();
//        }
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return project != null
                && author != null
                && authoringStartDate != null
                && authoringFinishDate != null
                && qualityAdmin != null
                && type != null
                && language != null
                && status != null
                ;
    }

    /**
     * This method only checks basic mandatory fields. Fields that can be auto-populated are not checked.
     *
     * @return
     */
    public boolean validateBasicFields() {
        return author != null
                && authoringStartDate != null
                && authoringFinishDate != null
                && qualityAdmin != null
                && type != null
                && language != null
                ;
    }
}
