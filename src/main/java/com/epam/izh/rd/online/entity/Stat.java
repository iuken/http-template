package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {
    private short baseStat;

    @JsonCreator
    public Stat(@JsonProperty("base_stat") short baseStat) {
        this.baseStat = baseStat;
    }

    public short getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(short baseStat) {
        this.baseStat = baseStat;
    }

}
