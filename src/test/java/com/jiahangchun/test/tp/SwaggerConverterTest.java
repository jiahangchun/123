package com.jiahangchun.test.tp;

import com.jiahangchun.test.tp.convert.SwaggerConverter;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SwaggerConverterTest {


    public static void main(String[] args) throws Exception {
        URL remoteSwaggerFile = new URL("http://petstore.swagger.io/v2/swagger.json");
        Path outputDirectory = Paths.get("/Users/jiahangchun/Desktop/test.yml");

        SwaggerConverter swaggerConverter = SwaggerConverter.from(remoteSwaggerFile, "/pet/{petId}/uploadImage").build();
        swaggerConverter.toFolder(outputDirectory);
        System.out.println("success.");
    }
}
