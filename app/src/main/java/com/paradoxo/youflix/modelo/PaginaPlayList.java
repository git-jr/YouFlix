package com.paradoxo.youflix.modelo;

import java.util.ArrayList;
import java.util.List;

public class PaginaPlayList {
    private List<PlayList> playLists = new ArrayList<>();
    private String nextPageToken;
    private String prevPageToken;
    private int numeroPlays;


    public PaginaPlayList() {

    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(PlayList playLists) {
        this.playLists.add(playLists);
    }

    public int getNumeroPlays() {
        return numeroPlays;
    }

    public void setNumeroPlays(int numeroPlays) {
        this.numeroPlays = numeroPlays;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }
}
