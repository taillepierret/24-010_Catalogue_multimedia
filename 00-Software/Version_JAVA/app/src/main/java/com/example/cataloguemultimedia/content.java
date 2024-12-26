package com.example.cataloguemultimedia;

import java.util.ArrayList;

public class content
{
    private String title;
    private String description;
    private content_type type;
    private String link_to_image;
    private String link_to_download;
    private ArrayList<Soundtrack> soundtrack_available = new ArrayList<Soundtrack>();

    public content(String title, String description, content_type type, String link_to_image, String link_to_download, ArrayList<Soundtrack> soundtrack_available)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        this.link_to_image = link_to_image;
        this.link_to_download = link_to_download;
        this.soundtrack_available = soundtrack_available;
    }

    public content (String title, String description, content_type type,ArrayList<Soundtrack> soundtrack_available)
    {
        this.title = title;
        this.description = description;
        this.type = type;
        this.soundtrack_available = soundtrack_available;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public content_type getType()
    {
        return type;
    }

    public String getLinkToImage()
    {
        return link_to_image;
    }

    public String getLinkToDownload()
    {
        return link_to_download;
    }

    public ArrayList<Soundtrack> getSoundtrack()
    {
        return soundtrack_available;
    }

    public String getDataJson()
    {
        return "{\n" +
                "  \"title\": \"" + title + "\",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"type\": \"" + type + "\",\n" +
                "  \"link_to_image\": \"" + link_to_image + "\",\n" +
                "  \"link_to_download\": \"" + link_to_download + "\",\n" +
                "  \"soundtrack_available\": \"" + soundtrack_available + "\"\n" +
                "}";
    }
}
