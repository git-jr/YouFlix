package com.paradoxo.youflix.modelo;

import java.util.ArrayList;
import java.util.List;

public class PaginaPlayList {
    private List<PlayList> playLists = new ArrayList<>();
    private String nextPageToken; // Token que pode ser usada para carregar a PRÓXIMA página de playlists
    private String prevPageToken; // Token que pode ser usada para carregar a página ANTERIOR de playlists
    private int numeroPlays; // Vai ser usado para saber se o botão "ver mais" será mostrado

    public PaginaPlayList(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public PaginaPlayList() {

    }

    public int getNumeroPlays() {
        return numeroPlays;
    }

    public void setNumeroPlays(int numeroPlays) {
        this.numeroPlays = numeroPlays;
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(PlayList playLists) {
        this.playLists.add(playLists);
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
