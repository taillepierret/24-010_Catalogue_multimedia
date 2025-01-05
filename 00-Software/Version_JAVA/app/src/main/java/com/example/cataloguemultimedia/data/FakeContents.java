package com.example.cataloguemultimedia.data;

import java.util.ArrayList;
import java.util.Arrays;

public class FakeContents
{
    private static ArrayList<content> ANIMES_CONTENTS = new ArrayList<>(Arrays.asList(
            new content("One piece", "Luffy et son équipage naviguent sur les mers à la recherche du légendaire trésor appelé One Piece et doivent faire face à de nombreuses aventures.", content_type.ANIME, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VF, Soundtrack.VOSTFR))),
            new content("Naruto", "Naruto Uzumaki est un jeune garçon de 12 ans du village caché de Konoha dans le pays du Feu. Il est rejeté par tous les habitants du village car il porte en lui le démon.", content_type.ANIME, new ArrayList<>(Arrays.asList(Soundtrack.VOSTFR, Soundtrack.VO))),
            new content("Dragon Ball", "Dragon Ball suit les aventures de Son Goku depuis son enfance jusqu'à l'âge adulte", content_type.ANIME, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VOSTFR, Soundtrack.VO)))
    ));

    private static ArrayList<content> MOVIES_CONTENTS = new ArrayList<>(Arrays.asList(
            new content("Star Wars", "Dans une galaxie lointaine, très lointaine, un groupe de jeunes rebelles, mené par Luke Skywalker, s'oppose aux forces du mal de l'Empire galactique.", content_type.MOVIE, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VF, Soundtrack.VOSTFR))),
            new content("Harry Potter", "Harry Potter, un jeune orphelin, découvre qu'il est un sorcier et qu'il est inscrit à l'école de sorcellerie de Poudlard.", content_type.MOVIE, new ArrayList<>(Arrays.asList(Soundtrack.VOSTFR, Soundtrack.VO))),
            new content("Le Seigneur des Anneaux", "Frodon Sacquet, un jeune hobbit, doit détruire un anneau magique pour sauver la Terre du Milieu de Sauron, le Seigneur des Ténèbres.", content_type.MOVIE, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VOSTFR, Soundtrack.VO)))
    ));

    private static ArrayList<content> SERIES_CONTENTS = new ArrayList<>(Arrays.asList(
            new content("Game of Thrones", "Dans le royaume de Westeros, plusieurs familles nobles se disputent le trône de fer.", content_type.SERIES, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VF, Soundtrack.VOSTFR))),
            new content("Breaking Bad", "Walter White, un professeur de chimie, se lance dans la fabrication de méthamphétamine pour subvenir aux besoins de sa famille après avoir été diagnostiqué d'un cancer.", content_type.SERIES, new ArrayList<>(Arrays.asList(Soundtrack.VOSTFR, Soundtrack.VO))),
            new content("Friends", "Les aventures de six amis new-yorkais qui partagent leur quotidien.", content_type.SERIES, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VOSTFR, Soundtrack.VO)))
    ));

    public static ArrayList<content> getAnimesContents()
    {
        return ANIMES_CONTENTS;
    }

    public static ArrayList<content> getMoviesContents()
    {
        return MOVIES_CONTENTS;
    }

    public static ArrayList<content> getSeriesContents()
    {
        return SERIES_CONTENTS;
    }
}
