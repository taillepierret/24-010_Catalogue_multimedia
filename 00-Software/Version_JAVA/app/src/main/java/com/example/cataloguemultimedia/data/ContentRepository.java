package com.example.cataloguemultimedia.data;

import java.util.ArrayList;

public class ContentRepository
{
    private static ArrayList<content> getAnimesContentList()
    {
        return FakeContents.getAnimesContents();
    }

    private static ArrayList<content> getMoviesContentList()
    {
        return FakeContents.getMoviesContents();
    }

    private static ArrayList<content> getSeriesContentList()
    {
        return FakeContents.getSeriesContents();
    }

    public static ArrayList<content> getContentList(content_type type, String seachQuery)
    {
        switch (type)
        {
            case MOVIE:
                return getMoviesContentList();
            case SERIES:
                return getSeriesContentList();
            case ANIME:
                return getAnimesContentList();
            default:
                return null;
        }
    }
}
