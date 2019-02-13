package br.com.phibonatii.phibonatii.model;

import java.io.Serializable;

public class Ranking implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Boolean me;

    public Ranking(Long id, String name, String description, Boolean me) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.me = me;
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

    public Boolean getMe() {
        return me;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }
}
