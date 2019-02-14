package br.com.phibonatii.phibonatii.model;

import java.io.Serializable;

public class Bona implements Serializable {
    private Long id;
    private String description;
    private String specification;
    private Long howMuch;
    private Boolean stillHidden;
    private Boolean foundNotConfirmed;
    private Long distanceFromMe;

    public Bona(Long id, String description, String specification) {
        this.id = id;
        this.description = description;
        this.specification = specification;
        howMuch = Long.valueOf(0);
        stillHidden = Boolean.FALSE;
        foundNotConfirmed = Boolean.FALSE;
        distanceFromMe = Long.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Long getHowMuch() {
        return howMuch;
    }

    public void setHowMuch(Long howMuch) {
        this.howMuch = howMuch;
    }

    public Boolean getStillHidden() {
        return stillHidden;
    }

    public void setStillHidden(Boolean stillHidden) {
        this.stillHidden = stillHidden;
    }

    public Boolean getFoundNotConfirmed() {
        return foundNotConfirmed;
    }

    public void setFoundNotConfirmed(Boolean foundNotConfirmed) { this.foundNotConfirmed = foundNotConfirmed; }

    public Long getDistanceFromMe() {
        return distanceFromMe;
    }

    public void setDistanceFromMe(Long distanceFromMe) {
        this.distanceFromMe = distanceFromMe;
    }
}
