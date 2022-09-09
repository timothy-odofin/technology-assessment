package technology.assessment.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;

import java.lang.reflect.Type;
import java.util.List;

public class RestMapper {
    private static Gson getGson(){

        return new Gson();
    }
    public static String mapToJson(Object obj) throws JsonProcessingException {

        return getGson().toJson(obj);
    }
    public static <T> T mapFromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }
    public static ApiResponse mapFromJson(String json) {
        return getGson().fromJson(json, getTestUserType());
    }
    public static Type getTestUserType(){
        return new TypeToken<ApiResponse<List<UserResponse>>>(){}.getType();
    }
}
