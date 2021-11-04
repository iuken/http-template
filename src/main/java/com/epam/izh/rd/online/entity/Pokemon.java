package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Покемон. Поля должны заполняться из JSON, который возвратит внешний REST-service
 * Для маппинга значений из массива stats рекомендуется использовать отдельный класс Stat и аннотацию @JsonCreator
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    /**
     * Уникальный идентификатор, маппится из поля pokemonId
     */
    private long pokemonId;

    /**
     * Имя покемона, маппится из поля pokemonName
     */
    private String pokemonName;

    /**
     * Здоровье покемона, маппится из массива объектов stats со значением name: "hp"
     */
    private short hp;

    /**
     * Атака покемона, маппится из массива объектов stats со значением name: "attack"
     */
    private short attack;

    /**
     * Защита покемона, маппится из массива объектов stats со значением name: "defense"
     */
    private short defense;

    String imageUrl;

    public Pokemon(long pokemonId, String pokemonName, short hp, short attack, short defense, String imageURL) {
        this.pokemonId = pokemonId;
        this.pokemonName = pokemonName;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.imageUrl = imageURL;
    }

    @JsonCreator
    public Pokemon(
            @JsonProperty("id") long pokemonId,
            @JsonProperty("name") String pokemonName,
            @JsonProperty("stats") List<Stat> stat,
            @JsonProperty("sprites") Sprite sprite) {
        this.pokemonId = pokemonId;
        this.pokemonName = pokemonName;
        this.hp = stat.get(0).getBaseStat();
        this.attack = stat.get(1).getBaseStat();
        this.defense = stat.get(2).getBaseStat();
        this.imageUrl = sprite.getImageUrl();
    }
}
