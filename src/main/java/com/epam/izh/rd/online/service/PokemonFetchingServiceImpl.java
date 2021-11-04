package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.factory.ObjectMapperFactoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PokemonFetchingServiceImpl implements PokemonFetchingService {

    private String urlPrefix = "https://pokeapi.co/api/v2/pokemon/";

    private static void cleanup(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    @Override
    public Pokemon fetchByName(String name) throws IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapperFactoryImpl().getObjectMapper();
        URL jsonUrl;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Pokemon pokemon;
        try {
            jsonUrl = new URL(urlPrefix + name);
            connection = (HttpURLConnection) jsonUrl.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.connect();
            inputStream = connection.getInputStream();
            pokemon = objectMapper.readValue(inputStream, Pokemon.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Невозможно получить покемона с именем " + name);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            cleanup(inputStream);
        }
        return pokemon;
    }

    @Override
    public byte[] getPokemonImage(String name) throws IllegalArgumentException {
        URL pokemonImageUrl;
        InputStream inputStream = null;
        String imageUrlString = fetchByName(name).getImageUrl();
        byte[] imageBytes;
        try {
            pokemonImageUrl = new URL(imageUrlString);
            inputStream = pokemonImageUrl.openStream();
            imageBytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Изображение не загружено");
        } finally {
            cleanup(inputStream);
        }
        return imageBytes;
    }
}
