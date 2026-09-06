package com.example.webapp;

import org.apache.jasper.JspC;
import org.junit.jupiter.api.Test;

import java.nio.file.*;

class JspCompilationTest {
    @Test
    void allJspAndTagFilesCompileWithJakartaJasper() throws Exception {
        Path output = Path.of("target", "jsp-check");
        Files.createDirectories(output);
        JspC compiler = new JspC();
        compiler.setUriroot(Path.of("src", "main", "webapp").toAbsolutePath().toString());
        compiler.setOutputDir(output.toAbsolutePath().toString());
        compiler.setClassPath(System.getProperty("java.class.path"));
        compiler.setCompile(true);
        compiler.setFailOnError(true);
        compiler.execute();
    }
}
