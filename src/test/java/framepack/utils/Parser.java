package framepack.utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.Primitives;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String toJson(Object obj){
        Gson gson = new Gson();
        // toJson(Object src) method converts Java object to JSON object
        String jsonObject = gson.toJson(obj);
        return jsonObject;
    }


    public static <T> T  fromJson(Response resp, Class<T> object){
        Gson gson = new Gson();
        // toJson(Object src) method converts Java object to JSON object
        Object jsonObject = gson.fromJson(resp.asString(), (Type) object);
        return  Primitives.wrap(object).cast(jsonObject);
    }

    public static <T> T  fromJson(JSONObject jsonObj, Class<T> object){
        Gson gson = new Gson();
        JsonElement jsonElement = new JsonParser().parse(jsonObj.toString());
        // toJson(Object src) method converts Java object to JSON object
        Object jsonObject = gson.fromJson(jsonElement, (Type) object);
        return  Primitives.wrap(object).cast(jsonObject);
    }

    public static String getSwaggerResponseKeysValue(Response response,String key){
        JSONObject jsonObject;
        String value = null;
        ResponseBody body = response.getBody();
        try {
            JSONArray json =  new JSONArray(body.asString());
            for (int count =0 ; count < json.length() ; count++){
                jsonObject = (JSONObject)json.get(count);
                value = jsonObject.get(key).toString();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static Map<String, List<String>> getSwaggerResponseKeysValueInMap(Response response,String key){
        Map<String, List<String>> map=new HashMap<String,List<String>>();
        List<String> list=new ArrayList();
        JSONObject jsonObject;
        String value = null;
        ResponseBody body = response.getBody();
        try {
            JSONArray json =  new JSONArray(body.asString());
            for (int count =0 ; count < json.length() ; count++){
                jsonObject = (JSONObject)json.get(count);
                value = jsonObject.get(key).toString();
                list.add(value);
            }
            map.put(key,list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

//    public static List<String> jsonPathEval(String response, String jsonPath) {
//        List<String> matches = new ArrayList<>();
//        JsonPath path = JsonPath.compile(jsonPath);
//        DocumentContext jsonContext = JsonPath.parse(response);
//        String jsonpathCreatorName = jsonContext.read(path);
//        matches.add(jsonpathCreatorName);
//        return matches;
//    }

    public static Object getJsonObjectVal(Response resp,String key,String dataType){
        Object val=null;
        JSONObject jsonObject=new JSONObject(resp.body().asString());
        if(dataType.equalsIgnoreCase("string")){
            val=jsonObject.getString(key);
        }
        else if(dataType.equalsIgnoreCase("boolean")){
            val=jsonObject.getBoolean(key);
        }
        else if(dataType.equalsIgnoreCase("int")){
            val=jsonObject.getInt(key);
        }
        else if(dataType.equalsIgnoreCase("double")){
            val=jsonObject.getDouble(key);
        }
        else{
            val=jsonObject.getJSONObject(key);
        }
        return val;
    }

/*
    public static List<String> getJSONValueByExp(String response, String jsonPath) {
        List<String> matches = new ArrayList<>();
        JsonPath path = JsonPath.compile(jsonPath);
        DocumentContext jsonContext = JsonPath.parse(response);
        String jsonpathCreatorName = jsonContext.read(path);
        matches.add(jsonpathCreatorName);
        return matches;
    }
*/

    public static String getSubstringFromString(String actualString,String regexExpression){
        String subString=null;
        Pattern pattern = Pattern.compile(regexExpression);
        Matcher matcher = pattern.matcher(actualString);
        if (matcher.find())
        {
            subString=matcher.group(1);

        }
        return subString;
    }
}

