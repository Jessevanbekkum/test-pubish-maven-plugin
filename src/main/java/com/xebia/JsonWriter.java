package com.xebia;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonWriter {

    private final Configuration cfg;

    JsonWriter() throws IOException, URISyntaxException {
        // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.27) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        cfg = new Configuration(Configuration.VERSION_2_3_27);

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:

        cfg.setClassForTemplateLoading(this.getClass(), "/");
//        cfg.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/").toURI()));

        cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

// Wrap unchecked exceptions thrown during template processing into TemplateException-s.
        cfg.setWrapUncheckedExceptions(true);
    }


    void write(List<TestResult> root) throws IOException, TemplateException {

        Map<String, List<TestResult>> parameters = new HashMap<>();
        parameters.put("results", root);
        Template template = cfg.getTemplate("template.ftl");
        Writer out = new OutputStreamWriter(System.out);
        template.process(parameters, out);
    }
}
