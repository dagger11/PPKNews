package com.ppk.news.data.model;

import java.io.Serializable;

public class SourceModel implements Serializable {
    String id;
    String name;


    public SourceModel(){}

    public SourceModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
