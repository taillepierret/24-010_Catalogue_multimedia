package com.example.cataloguemultimedia.data;

import java.util.ArrayList;
import java.util.Arrays;

public class FakeContents
{
    private static ArrayList<content> ANIMES_CONTENTS = new ArrayList<>(Arrays.asList(
            new content("One piece", "Luffy et son équipage naviguent sur les mers à la recherche du légendaire trésor appelé One Piece et doivent faire face à de nombreuses aventures.", content_type.ANIME, new ArrayList<String>(Arrays.asList("VF", "VOSTFR"))),
            new content("Naruto", "Naruto Uzumaki est un jeune garçon de 12 ans du village caché de Konoha dans le pays du Feu. Il est rejeté par tous les habitants du village car il porte en lui le démon.", content_type.ANIME, new ArrayList<String>(Arrays.asList("VF", "VOSTFR"))),
            new content("Dragon Ball", "Dragon Ball suit les aventures de Son Goku depuis son enfance jusqu'à l'âge adulte", content_type.ANIME, new ArrayList<String>(Arrays.asList("VF", "VOSTFR")))
    ));

    private static ArrayList<content> MOVIES_CONTENTS = new ArrayList<>(Arrays.asList(
            new content("Star Wars", "Dans une galaxie lointaine, très lointaine, un groupe de jeunes rebelles, mené par Luke Skywalker, s'oppose aux forces du mal de l'Empire galactique.", content_type.MOVIE, new ArrayList<String>(Arrays.asList("VF", "VOSTFR", "VO"))),
            new content("Harry Potter", "Harry Potter, un jeune orphelin, découvre qu'il est un sorcier et qu'il est inscrit à l'école de sorcellerie de Poudlard.", content_type.MOVIE, new ArrayList<String>(Arrays.asList("VF", "VOSTFR", "VO"))),
            new content("Le Seigneur des Anneaux", "Frodon Sacquet, un jeune hobbit, doit détruire un anneau magique pour sauver la Terre du Milieu de Sauron, le Seigneur des Ténèbres.", content_type.MOVIE, new ArrayList<String>(Arrays.asList("VF", "VOSTFR", "VO")))
    ));

    private static ArrayList<content> SERIES_CONTENTS = new ArrayList<>(Arrays.asList(
            new content("Game of Thrones", "Dans le royaume de Westeros, plusieurs familles nobles se disputent le trône de fer.", content_type.SERIES, new ArrayList<String>(Arrays.asList("VO", "VOSTFR"))),
            new content("Breaking Bad", "Walter White, un professeur de chimie, se lance dans la fabrication de méthamphétamine pour subvenir aux besoins de sa famille après avoir été diagnostiqué d'un cancer.", content_type.SERIES, new ArrayList<String>(Arrays.asList("VF", "VOSTFR", "VO"))),
            new content("Friends", "Les aventures de six amis new-yorkais qui partagent leur quotidien.", content_type.SERIES, new ArrayList<String>(Arrays.asList("VF", "VOSTFR", "VO")))
    ));

    public static String Fake_API_result =
            "{\"results\":\"[{\\\"nom\\\": [\\\"Breaking Bad\\\"], \\\"saison\\\": [\\\"Saison 1\\\", \\\"Saison 2\\\", \\\"Saison 3\\\", \\\"Saison 4\\\", \\\"Saison 5\\\", \\\"Saison 1\\\", \\\"Saison 2\\\", \\\"Saison 3\\\", \\\"Saison 4\\\", \\\"Saison 5\\\", \\\"Saison 1\\\", \\\"Saison 2\\\", \\\"Saison 3\\\", \\\"Saison 4\\\", \\\"Saison 5\\\", \\\"Saison 1\\\", \\\"Saison 2\\\", \\\"Saison 3\\\", \\\"Saison 4\\\", \\\"Saison 5\\\", \\\"Saison 1\\\", \\\"Saison 2\\\", \\\"Saison 3\\\", \\\"Saison 4\\\", \\\"Saison 5\\\"], \\\"bande_audio\\\": [\\\"VOSTFR HD\\\", \\\"VOSTFR HD\\\", \\\"VOSTFR HD\\\", \\\"VOSTFR HD\\\", \\\"VOSTFR HD\\\", \\\"VF\\\", \\\"VF\\\", \\\"VF\\\", \\\"VF\\\", \\\"VF\\\", \\\"VOSTFR\\\", \\\"VOSTFR\\\", \\\"VOSTFR\\\", \\\"VOSTFR\\\", \\\"VOSTFR\\\", \\\"VF HD\\\", \\\"VF HD\\\", \\\"VF HD\\\", \\\"VF HD\\\", \\\"VF HD\\\", \\\"MULTI 4K UHD\\\", \\\"MULTI 4K UHD\\\", \\\"MULTI 4K UHD\\\", \\\"MULTI 4K UHD\\\", \\\"MULTI 4K UHD\\\"], \\\"lien\\\": [\\\"/?p=serie&id=2000-breaking-bad-saison1\\\", \\\"/?p=serie&id=2001-breaking-bad-saison2\\\", \\\"/?p=serie&id=2002-breaking-bad-saison3\\\", \\\"/?p=serie&id=2003-breaking-bad-saison4\\\", \\\"/?p=serie&id=2004-breaking-bad-saison5\\\", \\\"/?p=serie&id=2557-breaking-bad-saison1\\\", \\\"/?p=serie&id=2558-breaking-bad-saison2\\\", \\\"/?p=serie&id=2559-breaking-bad-saison3\\\", \\\"/?p=serie&id=2560-breaking-bad-saison4\\\", \\\"/?p=serie&id=2561-breaking-bad-saison5\\\", \\\"/?p=serie&id=2562-breaking-bad-saison1\\\", \\\"/?p=serie&id=2563-breaking-bad-saison2\\\", \\\"/?p=serie&id=2564-breaking-bad-saison3\\\", \\\"/?p=serie&id=2565-breaking-bad-saison4\\\", \\\"/?p=serie&id=2566-breaking-bad-saison5\\\", \\\"/?p=serie&id=3064-breaking-bad-saison1\\\", \\\"/?p=serie&id=3065-breaking-bad-saison2\\\", \\\"/?p=serie&id=3066-breaking-bad-saison3\\\", \\\"/?p=serie&id=3067-breaking-bad-saison4\\\", \\\"/?p=serie&id=3068-breaking-bad-saison5\\\", \\\"/?p=serie&id=14739-breaking-bad-saison1\\\", \\\"/?p=serie&id=14740-breaking-bad-saison2\\\", \\\"/?p=serie&id=14741-breaking-bad-saison3\\\", \\\"/?p=serie&id=14742-breaking-bad-saison4\\\", \\\"/?p=serie&id=14743-breaking-bad-saison5\\\"], \\\"image\\\": [\\\"/img/series/de1be67aedcb432ec505a355767b63f9.webp\\\", \\\"/img/series/f5d8c901e53bf5bf1c0536c121c03219.webp\\\", \\\"/img/series/fc104d360344fb9ced8004b506d463ff.webp\\\", \\\"/img/series/6617e3ac1a41857d41bb3a1a3efe55a7.webp\\\", \\\"/img/series/bc6a07e3650fcc1982cdf99da9b855b2.webp\\\", \\\"/img/series/06b890072c5cf9a3ae43235591f3a4c2.webp\\\", \\\"/img/series/6394eff547ff3158c147d1146f2a996b.webp\\\", \\\"/img/series/6580dae5f105a7c45bd63ba36f2ea196.webp\\\", \\\"/img/series/c72ef392f78879cac4bd5b8da009b380.webp\\\", \\\"/img/series/f3bfd0a692f31a623131013003c9c9e9.webp\\\", \\\"/img/series/f7c0d7c1eb88b3e636b117ce4752a252.webp\\\", \\\"/img/series/4bce0c072a63273979ef66c380cb005b.webp\\\", \\\"/img/series/7f4e23c1805a01ea8b4266620b536ca5.webp\\\", \\\"/img/series/f504c36c778a6d1ffddb9f80e09d143f.webp\\\", \\\"/img/series/ad0a334beaa0be12c4eb4421ab43e36d.webp\\\", \\\"/img/series/25e34f6c63314bac4275bd0e8ee33314.webp\\\", \\\"/img/series/ecada191d6b776db9878c079ac98df84.webp\\\", \\\"/img/series/3ba2bca61c104973b05e327a77d392cf.webp\\\", \\\"/img/series/1dc43e309790ad988ebd78ec6bc48af9.webp\\\", \\\"/img/series/c8383324b55c7966b73253ae2f0c75bc.webp\\\", \\\"/img/series/88dbbe772c0d58d64c905ff5510baee8.webp\\\", \\\"/img/series/c250c9a87dfd9fbc1d10997fe3f42fb0.webp\\\", \\\"/img/series/07d8e6f111648325ef842be88e6f2a92.webp\\\", \\\"/img/series/5b2f201926fe50d96e1c245a7f7a543e.webp\\\", \\\"/img/series/c9ec934a06c921452e28b43ddc83da9b.webp\\\"], \\\"date_de_publication\\\": [\\\"14 February 2019\\\", \\\"14 February 2019\\\", \\\"14 February 2019\\\", \\\"14 February 2019\\\", \\\"14 February 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"29 April 2019\\\", \\\"18 June 2019\\\", \\\"18 June 2019\\\", \\\"18 June 2019\\\", \\\"18 June 2019\\\", \\\"18 June 2019\\\", \\\"10 May 2022\\\", \\\"10 May 2022\\\", \\\"10 May 2022\\\", \\\"10 May 2022\\\", \\\"10 May 2022\\\"]}, {\\\"nom\\\": [\\\"Bangkok Breaking\\\"], \\\"saison\\\": [\\\"Saison 1\\\", \\\"Saison 1\\\"], \\\"bande_audio\\\": [\\\"VOSTFR\\\", \\\"VOSTFR HD\\\"], \\\"lien\\\": [\\\"/?p=serie&id=12788-bangkok-breaking-saison1\\\", \\\"/?p=serie&id=12789-bangkok-breaking-saison1\\\"], \\\"image\\\": [\\\"/img/series/ca369c013a9aa61538647b015866a111.webp\\\", \\\"/img/series/d36cca940a316683bedc2fb47652ca45.webp\\\"], \\\"date_de_publication\\\": [\\\"23 September 2021\\\", \\\"23 September 2021\\\"]}]\"}";

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
