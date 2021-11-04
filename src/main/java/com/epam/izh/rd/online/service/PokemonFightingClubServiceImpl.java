package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class PokemonFightingClubServiceImpl implements PokemonFightingClubService {
    @Override
    public Pokemon doBattle(Pokemon p1, Pokemon p2) {
        System.out.printf("Бой %s против %s начинается!\n", p1.getPokemonName(), p2.getPokemonName());
        Pokemon tempPokemon;
        Pokemon[] pokemons;
        if (p1.getPokemonId() > p2.getPokemonId()) {
            pokemons = new Pokemon[]{p1, p2};
        } else {
            pokemons = new Pokemon[]{p2, p1};
        }
        while (true) {
            doDamage(pokemons[0], pokemons[1]);
            if (pokemons[1].getHp() <= 0) {
                System.out.println(pokemons[0].getPokemonName() + " победитель!");
                return pokemons[0];
            } else {
                tempPokemon = pokemons[0];
                pokemons[0] = pokemons[1];
                pokemons[1] = tempPokemon;
            }
        }
    }

    @Override
    public void showWinner(Pokemon winner) {
        PokemonFetchingServiceImpl pokemonFetchingService = new PokemonFetchingServiceImpl();
        byte[] imageBytes = pokemonFetchingService.getPokemonImage(winner.getPokemonName());
        BufferedImage bufferedImage;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes)) {
            bufferedImage = ImageIO.read(byteArrayInputStream);
            ImageIO.write(bufferedImage, "png", new File("winner.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doDamage(Pokemon from, Pokemon to) {
        short damage = (short) (from.getAttack() - (from.getAttack() * to.getDefense() / 100));
        to.setHp((short) (to.getHp() - damage));
        System.out.printf("%s наносит %d урона. У %s осталось %d hp.\n", from.getPokemonName(), damage, to.getPokemonName(), (to.getHp() < 0 ? 0 : to.getHp()));
    }
}
