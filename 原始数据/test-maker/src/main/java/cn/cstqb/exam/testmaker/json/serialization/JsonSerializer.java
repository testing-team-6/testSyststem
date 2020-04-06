package cn.cstqb.exam.testmaker.json.serialization;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/7
 * Time: 21:23
 */
public class JsonSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    private ObjectMapper mapper;

    private static final ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();

    public JsonSerializer() {
        this.mapper = new ObjectMapper();
    }

    @Inject
    private void init() {
        logger.debug("JsonSerializer.init: Initializing ObjectMapper...");
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(configContext.getDefaultDateFormat(), Locale.SIMPLIFIED_CHINESE);
        mapper.setDateFormat(dateFormat);
    }

    public void write(File output, Object value) throws IOException {
        logger.debug("JsonSerializer.write: writing value to json {}", output );
        mapper.writeValue(output, value);
    }

    public void write(Path path, Object value) throws IOException {
        write(path.toFile(),value);
    }

    public String write(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}
