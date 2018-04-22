package com.xebia;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

class JsonWriterTest {

    @Test
    void shouldDoStuff() throws IOException, URISyntaxException, TemplateException {
        TestResult tr1 = new TestResult("Test1", true);
        TestResult tr2 = new TestResult("Test2", false);

        List<TestResult> c = new ArrayList<>();
        c.add(tr1);
        c.add(tr2);

        JsonWriter jw = new JsonWriter();
        jw.write(c);

    }
}