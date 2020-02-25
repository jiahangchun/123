package test;

import com.convert.SwaggerConverter;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocTest {
    public static void main(String[] args) throws Exception {
        System.out.println("sssss");
        testConverterString();
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
