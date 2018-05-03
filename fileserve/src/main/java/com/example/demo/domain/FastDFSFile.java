package com.example.demo.domain;

import com.example.demo.dao.FileManagerConfig;
import lombok.Data;

import java.lang.annotation.Annotation;

@Data
public class FastDFSFile implements FileManagerConfig{
    private static final long serialVersionUID = 1L;

    private byte[] content;
    private String name;
    private String ext;
    private String length;
    private String author = FILE_DEFAULT_AUTHOR;

    public FastDFSFile(byte[] content, String ext) {
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile(byte[] content, String name, String ext) {
        this.content = content;
        this.name = name;
        this.ext = ext;
    }

    public FastDFSFile(byte[] content, String name, String ext, String length,
                       String author) {
        this.content = content;
        this.name = name;
        this.ext = ext;
        this.length = length;
        this.author = author;
    }

    @Override
    public String encoding() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
