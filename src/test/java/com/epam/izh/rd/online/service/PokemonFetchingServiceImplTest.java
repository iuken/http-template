package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonFetchingServiceImplTest {

    private PokemonFetchingServiceImpl pokemonFetchingServiceImpl;
    private WireMockServer wireMockServer;

    @BeforeAll
    void setup() {
        this.pokemonFetchingServiceImpl = new PokemonFetchingServiceImpl();
        this.wireMockServer = new WireMockServer();
        this.wireMockServer.start();
        pokemonFetchingServiceImpl.setUrlPrefix("http://localhost:8080/");
        stubFor(get(urlEqualTo("/test1"))
                .willReturn(aResponse().withBodyFile("test1.json")));
        stubFor(get(urlEqualTo("/test2"))
                .willReturn(aResponse().withBodyFile("test2.json")));
        stubFor(get(urlEqualTo("/test3"))
                .willReturn(aResponse().withBodyFile("test3.json")));
        stubFor(get(urlEqualTo("/testing_image.png"))
                .willReturn(aResponse().withBodyFile("testing_image.png")));
    }

    @AfterAll
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void testFetchByNameShouldReturnPokemonFromLocalFile() {
        Pokemon pokemonActual = pokemonFetchingServiceImpl.fetchByName("test1");
        Pokemon pokemonExpected = new Pokemon(132L, "ditto", (short) 48, (short) 48, (short) 48, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png");
        assertEquals(pokemonActual, pokemonExpected);
    }

    @Test
    void testFetchingByNameShouldThroeException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pokemonFetchingServiceImpl.fetchByName("test3");
        });

        String expectedMessage = "Невозможно получить покемона с именем test3";
        String actualMessage = exception.getMessage();

        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetPokemonImageShouldReturnPokemonImageFromLocalFile() throws IOException {
        byte[] expectedByteArray = Files.readAllBytes(Paths.get("src/test/resources/__files/testing_image.png"));
        byte[] pokemonImageActual = pokemonFetchingServiceImpl.getPokemonImage("test2");
        assertArrayEquals(expectedByteArray, pokemonImageActual);
    }
}