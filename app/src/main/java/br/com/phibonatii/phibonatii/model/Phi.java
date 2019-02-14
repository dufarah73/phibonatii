package br.com.phibonatii.phibonatii.model;

import java.io.Serializable;

public class Phi implements Serializable {
    private Long id;
    private String nickname;
    private String fullName;
    private Boolean me;
    private Long amount;

    public Phi(Long id, String name, String fullName) {
        this.id = id;
        this.nickname = name;
        this.fullName = fullName;
        me = Boolean.FALSE;
        amount = Long.valueOf(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getMe() {
        return me;
    }

    public void setMe(Boolean me) {
        this.me = me;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
