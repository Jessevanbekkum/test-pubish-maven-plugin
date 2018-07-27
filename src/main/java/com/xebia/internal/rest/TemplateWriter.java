package com.xebia.internal.rest;

import com.xebia.internal.parser.TestResultImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.maven.plugin.logging.Log;

public class TemplateWriter {

  private final Configuration cfg;
  private final Path templateFile;
  private final Supplier<Log> log;

  public TemplateWriter(Path templateFile, Supplier<Log> log) {
    this.templateFile = Objects.requireNonNull(templateFile);
    this.log = log;
    // Create your Configuration instance, and specify if up to what FreeMarker
    // version (here 2.3.27) do you want to apply the fixes that are not 100%
    // backward-compatible. See the Configuration JavaDoc for details.

    cfg = new Configuration(Configuration.VERSION_2_3_27);

    // Specify the source where the template files come from. Here I set a
    // plain directory for it, but non-file-system sources are possible too:

    cfg.setClassForTemplateLoading(this.getClass(), "/");

    cfg.setDefaultEncoding("UTF-8");

    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
    cfg.setLogTemplateExceptions(false);

    // Wrap unchecked exceptions thrown during template processing into TemplateException-s.
    cfg.setWrapUncheckedExceptions(true);
  }


  public String write(List<TestResultImpl> root) {

    try {
      Map<String, List<TestResultImpl>> parameters = new HashMap<>();
      parameters.put("results", root);
      log.get().info("file:" + templateFile.toString());
      Template template = cfg.getTemplate(templateFile.getFileName().toString());
      Writer out = new StringWriter();
      template.process(parameters, out);
      out.close();
      return out.toString();
    } catch (IOException | TemplateException e) {
      throw new RuntimeException(e);
    }

  }
}
