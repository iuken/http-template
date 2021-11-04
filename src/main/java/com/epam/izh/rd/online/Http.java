package com.epam.izh.rd.online;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonFetchingServiceImpl;
import com.epam.izh.rd.online.service.PokemonFightingClubServiceImpl;

public class Http {
    public static void main(String[] args) {

        PokemonFetchingServiceImpl pokemonFetchingService = new PokemonFetchingServiceImpl();

        Pokemon pokemon1 = pokemonFetchingService.fetchByName("pikachu");
        Pokemon pokemon2 = pokemonFetchingService.fetchByName("slowpoke");

        PokemonFightingClubServiceImpl pokemonFightingClubService = new PokemonFightingClubServiceImpl();

        Pokemon winner = pokemonFightingClubService.doBattle(pokemon1, pokemon2);

        pokemonFightingClubService.showWinner(winner);
    }
}
