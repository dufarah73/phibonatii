package br.com.phibonatii.phibonatii.model;

import java.io.Serializable;

public class Radar implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Long range;

    public Radar(Long id, String name, String description, Long range) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.range = range;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRange() {
        return range;
    }

    public void setRange(Long range) {
        this.range = range;
    }
}
