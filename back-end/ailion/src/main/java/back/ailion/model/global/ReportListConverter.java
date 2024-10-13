package back.ailion.model.global;

import back.ailion.model.entity.Recommend;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;

import javax.persistence.AttributeConverter;
import java.util.List;

//public class ReportListConverter implements AttributeConverter<List<Recommend>, String> {
//    private static final ObjectMapper mapper = new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
//
//    @Override
//    public String convertToDatabaseColumn(List<Recommend> attribute) {
//        try {
//            return mapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            throw new IllegalArgumentException();
//        }
//    }
//
//    @Override
//    public List<Recommend> convertToEntityAttribute(String dbData) {
//        try {
//            return mapper.readValue(dbData, new TypeReference<>() {
//            });
//        } catch (IOException | JsonProcessingException e) {
//            throw new IllegalArgumentException();
//        }
//    }
//}