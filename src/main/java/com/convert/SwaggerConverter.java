package com.convert;

import com.common.BizException;
import com.common.CommonUtil;
import com.convert.bo.SwaggerDetailBo;
import com.convert.config.*;
import com.convert.data.SwaggerDataFetcher;
import com.convert.data.format.SwaggerDataFormat;
import com.convert.doc.MarkupDocBuilder;
import com.convert.doc.MarkupDocBuilders;
import com.convert.doc.MarkupLanguage;
import com.convert.doc.SwaggerDocument;
import com.swagger.dto.ResultData;
import com.swagger.dto.SwaggerApiListDto;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.lang3.Validate;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class SwaggerConverter {

    public final Context context;
    private final SwaggerDocument swaggerDocument;

    /**
     * 构造器
     *
     * @param context
     */
    public SwaggerConverter(Context context) {
        this.context = context;
        this.swaggerDocument = new SwaggerDocument(context);
    }

    /**
     * 生成Builder
     *
     * @param swaggerURL
     * @return
     */
    public static Builder from(URL swaggerURL, String searchUrl) {
        Validate.notNull(swaggerURL, "swaggerURL must not be null");
        Validate.notNull(searchUrl, "searchUrl must not be null");
        return new Builder(swaggerURL, searchUrl);
    }

    /**
     * 生成文件
     *
     * @param outputDirectory
     */
    public void toFolder(Path outputDirectory) {
        Validate.notNull(outputDirectory, "outputDirectory must not be null");
        context.setOutputPath(outputDirectory);

        //选择具体的处理器
        //将对象转换成string
        MarkupDocBuilder builder = applyDocument();
        //生成文件
        builder.writeToFile(outputDirectory.resolve("markdown"), StandardCharsets.UTF_8);
    }

    public String toConvertString(){
        //选择具体的处理器
        //将对象转换成string
        MarkupDocBuilder builder = applyDocument();
        return builder.writeToConsole();
    }

    /**
     * 生成对应的文本
     *
     * @return
     */
    private MarkupDocBuilder applyDocument() {
        return swaggerDocument.apply(
                context.createMarkupDocBuilder(),
                SwaggerDocument.parameters(
                        context.getSwaggerDetailBo(),
                        context.getDefinitionVoMap()));
    }

    public static class Builder {

        private final URL swaggerLocation;
        private SwaggerDetailBo swaggerDetailBo;
        private Map<String, List<ResultData>> definitionVoMap;
        private SwaggerConfigBuilder config;
        private SwaggerExtensionRegister extensionRegister;
        private final String searchUrl;

        Builder(URL swaggerUrl, String searchUrl) {
            try {
                this.swaggerLocation = swaggerUrl;
                this.searchUrl = searchUrl;
            } catch (Exception e) {
                throw new IllegalArgumentException("swaggerURL is in a wrong format", e);
            }
        }

        public SwaggerConverter build() {
            ConfigBO configBO = new SwaggerConfigBuilder(new PropertiesConfiguration()).build();

            if (extensionRegister == null) {
                //加载一些插件
                extensionRegister = new SwaggerExtensionBuilder().build();
            }
            //解析数据
            initOriginData(swaggerLocation, searchUrl);
            Context context = new Context(
                    configBO, extensionRegister, swaggerDetailBo, definitionVoMap, swaggerLocation);
            initExtensions(context);
            applySwaggerExtensions(context);
            return new SwaggerConverter(context);
        }

        /**
         * 初始化
         *
         * @param swaggerUrl
         */
        private void initOriginData(URL swaggerUrl, String searchUrl) {
            SwaggerDataFetcher swaggerDataFetcher = SwaggerDataFetcher.from(swaggerUrl).build();
            SwaggerDataFormat.Result result = swaggerDataFetcher.toFormatData();
            //筛选出当前需要的值
            List<SwaggerApiListDto> swaggerApiListDtos = result.getSwaggerApiListDtoList();
            SwaggerApiListDto swaggerApiListDto = swaggerApiListDtos.stream().filter(x -> {
                String url = x.getUrl();
                if(CommonUtil.isEmpty(url)){
                    return false;
                }
                return url.contains(searchUrl);
            }).findFirst().orElse(null);
            if(CommonUtil.isEmpty(swaggerApiListDto)){
                throw new BizException("can not found api.");
            }
            result.formCenterApi(swaggerApiListDto);
            //解析生成具体的swagger详情
            SwaggerDetailBo swaggerDetailBo=SwaggerDataFormat.transform(this.swaggerDetailBo, result);
            Validate.notNull(swaggerDetailBo, "swaggerApiListDto must not be null");
            this.swaggerDetailBo=swaggerDetailBo;
            this.definitionVoMap = result.getDefinitionMap();
        }


        /**
         * 为了将我们获取到数据全部设置到插件里面，供给插件使用
         *
         * @param context
         */
        private void initExtensions(Context context) {
            extensionRegister.getSwaggerModelExtensions().forEach(extension -> extension.setGlobalContext(context));
        }

        /**
         * 让插件开始运转
         *
         * @param context
         */
        private void applySwaggerExtensions(Context context) {
            extensionRegister.getSwaggerModelExtensions().forEach(extension -> extension.apply(context.getSwaggerDetailBo()));
        }

    }

    /**
     * Converter转换器里面包含了所有的信息
     */

    public static class Context {
        private final ConfigBO config;
        private final SwaggerDetailBo swaggerDetailBo;
        private final URL swaggerLocation;
        private final SwaggerExtensionRegister extensionRegistry;
        private final Labels labels;
        private Path outputPath;
        private final Map<String, List<ResultData>> definitionVoMap;

        public Context(ConfigBO config,
                       SwaggerExtensionRegister extensionRegistry,
                       SwaggerDetailBo swagger,
                       Map<String, List<ResultData>> definitionVoMap,
                       URL swaggerLocation) {
            this.config = config;
            this.extensionRegistry = extensionRegistry;
            this.swaggerDetailBo = swagger;
            this.swaggerLocation = swaggerLocation;
            this.labels = new Labels(config);
            this.definitionVoMap = definitionVoMap;
        }

        /**
         * 选择具体的执行器
         *
         * @return
         */
        public MarkupDocBuilder createMarkupDocBuilder() {
            //原本这边还可以根据 配置properties 文件 动态选择语言的
            //目前为了简单起见 简化下
            // config.getMarkupLanguage()->MarkupLanguage
            //config.getAnchorPrefix()->anchorPrefix
            return MarkupDocBuilders.documentBuilder(
                    MarkupLanguage.MARKDOWN
            ).withAnchorPrefix("anchorPrefix");
        }

        public ConfigBO getConfig() {
            return config;
        }

        public SwaggerDetailBo getSwaggerDetailBo() {
            return swaggerDetailBo;
        }

        public URL getSwaggerLocation() {
            return swaggerLocation;
        }

        public SwaggerExtensionRegister getExtensionRegistry() {
            return extensionRegistry;
        }

        public Labels getLabels() {
            return labels;
        }

        public Path getOutputPath() {
            return outputPath;
        }

        public void setOutputPath(Path outputPath) {
            this.outputPath = outputPath;
        }

        public Map<String, List<ResultData>> getDefinitionVoMap() {
            return definitionVoMap;
        }
    }

}
