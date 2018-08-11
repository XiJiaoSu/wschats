package websockts.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * json && object 转换工具类
 * @author will
 *
 */
@Component("parser")
public class JsonParser implements Parser {

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    public <T>T stringToObject(String content, Class<T> clazz) {
        if (content==null||content.length()==0||clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) content : mapper.readValue(content, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public <T>String ObjectToString(T object) {
        if (object==null){
            return "";
        }else {
            try {
                return object instanceof String? (String) object :mapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
}
