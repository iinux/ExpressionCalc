package com.mycompany.helloworld.basic;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FasterXml {
    public static void main(String[] args) throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);

        XmlMapper xmlMapper = new XmlMapper(module);

        String file = "pom.xml";
        String content = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Project value = xmlMapper.readValue(content, Project.class);
        System.out.println(value);
    }

    @ToString
    static class Project {
        public String modelVersion;
        public String groupId;
        public String artifactId;
        public String version;
        public String name;
        public String url;
        public String xmlns;
        public String xsi;
        public String schemaLocation;
        public String text;
    }
}

