package com.jiahangchun.test.tp;

import static org.asciidoctor.Asciidoctor.Factory.create;
import org.asciidoctor.Asciidoctor;

import java.util.HashMap;
import java.util.Map;

public class AsciidoctorJTest {

    public static void main(String[] args) {
        Asciidoctor asciidoctor = create();
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("toc","left");
        String html = asciidoctor.convert(
                "Writing AsciiDoc is _easy_!",
                map
                );
        System.out.println(html);
    }
}
