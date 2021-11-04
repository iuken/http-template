package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonFightingClubServiceImplTest {

    PokemonFightingClubServiceImpl pokemonFightingClubService;

    @BeforeAll
    void setup() {
        this.pokemonFightingClubService = new PokemonFightingClubServiceImpl();
    }

    @AfterAll
    void cleanUp() {
        new File("winner.png").delete();
    }

    @Test
    void testDoBattlePokemonWithLargerIdShouldStart() {
        Pokemon shouldLose = new Pokemon(1L, "pokemon 1", (short) 1, (short) 100, (short) 0, "");
        Pokemon shouldWin = new Pokemon(2L, "pokemon 2", (short) 1, (short) 100, (short) 0, "");
        Pokemon winner = pokemonFightingClubService.doBattle(shouldLose, shouldWin);
        assertEquals(shouldWin, winner);
    }

    @Test
    void testShowWinnerShouldCreateFile() {
        File oldImage = new File("winner.png");
        oldImage.delete();

        PokemonFetchingServiceImpl pokemonFetchingService = new PokemonFetchingServiceImpl();
        pokemonFightingClubService.showWinner(pokemonFetchingService.fetchByName("pikachu"));

        assertTrue(new File("winner.png").exists());
    }

    @Test
    void testDoDamageReduceDefendingPokemonHp() {
        short hpBeforeFight = 100;
        Pokemon from = new Pokemon(1L, "pokemon 1", (short) 100, (short) 50, (short) 50, "");
        Pokemon to = new Pokemon(2L, "pokemon 2", hpBeforeFight, (short) 50, (short) 50, "");
        pokemonFightingClubService.doDamage(from, to);
        assertTrue(to.getHp() < hpBeforeFight);
    }
}