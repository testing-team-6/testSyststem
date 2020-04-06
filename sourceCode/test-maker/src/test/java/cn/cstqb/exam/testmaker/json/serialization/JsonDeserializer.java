package cn.cstqb.exam.testmaker.json.serialization;

import cn.cstqb.exam.testmaker.entities.AbstractEntity;
import cn.cstqb.exam.testmaker.entities.KnowledgePoint;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/26
 * Time: 22:09
 */
public class JsonDeserializer {
    private ObjectMapper mapper;
    private JsonFactory factory;
    public JsonDeserializer() {
        mapper = new ObjectMapper();
        factory = new JsonFactory();
        factory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    }

    public List<KnowledgePoint> readKnowledgePoints(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<List<KnowledgePoint>>() {});
    }

    public static void main(String[] args) throws IOException {
        File kp = new File("D:\\eclipse\\workspace-mine\\test-maker\\src\\main\\webapp\\WEB-INF\\initialization\\json\\cn.cstqb.exam.testmaker.entities.KnowledgePoint.json");
        JsonDeserializer reader = new JsonDeserializer();
        List<KnowledgePoint> result= reader.readKnowledgePoints(kp);
        System.out.println(result.getClass().getName());
        for (KnowledgePoint o : result) {
            System.out.printf("element type:%s\n", o.getClass().getName());
            System.out.println(o);
        }
    }
}
