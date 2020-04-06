package cn.cstqb.exam.testmaker.dao;

import java.util.List;

import cn.cstqb.exam.testmaker.entities.Chapter;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.Syllabus;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/25
 * Time: 23:08
 */
public interface SyllabusDao extends GenericDao<Syllabus, Integer> {
    /**
     * find the syllabus with the given level
     *
     * @param syllabus
     * @return The syllabus object matching the level or <i>null</i> if not
     * found
     */
    Syllabus findSyllabus(Syllabus syllabus);

    /**
     * Retrieve syllabuses from the server depending on the given the flag to include disabled syllabuses or not
     *
     * @param includeDisabled true to get all syllabuses; <b>false</b> to include only active ones
     * @return a list of syllabuses
     */
    List<Syllabus> findSyllabuses(boolean includeDisabled);

    /**
     * Finds syllabuses used by projects
     *
     * @param syllabus Syllabus to check
     * @return A list of projects using this syllabus
     */
    List<Project> findSyllabuses(Syllabus syllabus);
}
