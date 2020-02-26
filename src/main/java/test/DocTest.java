package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.convert.SwaggerConverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocTest {

    public static String resultUrl = "{\"carParkAvailable\":\"是否存在停车位\",\"carParkingPhoto\":\"{\\\"name\\\":\\\"string\\\",\\\"photoId\\\":\\\"1001\\\",\\\"url\\\":\\\"string\\\"}\",\"city\":\"杭州市\",\"cityId\":\"4\",\"companyName\":\"名字叫Xxx\",\"contact\":\"人物的描述\",\"detailAddress\":\"西湖区天和华丰\",\"district\":\"余杭区\",\"districtId\":\"5\",\"id\":\"主键\",\"lat\":\"21.29612\",\"lng\":\"113.157337\",\"mobile\":\"15700082377\",\"postCode\":\"311122\",\"province\":\"浙江省\",\"provinceId\":\"22\",\"shipId\":\"SH1999_1\",\"shipIdPhotoParams\":\"{\\\"name\\\":\\\"string\\\",\\\"photoId\\\":\\\"1001\\\",\\\"url\\\":\\\"string\\\"}\"}";

    public static void main(String[] args) throws Exception {
    }

    private static void testPrettyFormat() {
        String result = "";
        result = resultUrl.replaceAll("\"\\{", "\\{");
        result = result.replaceAll("}\"", "}");
        result = result.replaceAll("\\\\", "");

        JSONObject object = JSONObject.parseObject(result);
        String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println(pretty);


        JSONObject object2 = JSONObject.parseObject("data ...... result");
        String pretty2 = JSON.toJSONString(object2, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        System.out.println("=========================second result=" + pretty2);
    }


    private static void testConverterString() throws MalformedURLException {
        URL remoteSwaggerFile = new URL("http://localhost:8080/platform/v2/api-docs");
        SwaggerConverter swaggerConverter = SwaggerConverter.from(remoteSwaggerFile, "/crm/shipId/get/default/shipId").build();
        String data = swaggerConverter.toConvertString();
        System.out.println("==================\r\n" + data);
    }


    private static void testConverter() throws MalformedURLException {
        URL remoteSwaggerFile = new URL("http://localhost:8080/platform/v2/api-docs");
        Path outputDirectory = Paths.get("/Users/jiahangchun/Desktop/test");
        SwaggerConverter swaggerConverter = SwaggerConverter.from(remoteSwaggerFile, "/crm/shipId/get/default/shipId").build();
        swaggerConverter.toFolder(outputDirectory);
        System.out.println("success.");
    }
}
