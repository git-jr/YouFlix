package com.paradoxo.youflix.modelo;

import java.util.ArrayList;
import java.util.List;

public class PaginaVideo {
    private List<String> tokenPagina = new ArrayList<>();
    private List<Video> videos = new ArrayList<>();
    private String idCanal;
    private String pgToken = null;
    private String nextPageToken;
    private String prevPageToken;
    private String idPlayLiit;
    private int numeroVideos;

    public PaginaVideo() {

    }

    public String getIdPlayLiit() {
        return idPlayLiit;
    }

    public void setNumeroVideos(int numeroVideos) {
        this.numeroVideos = numeroVideos;
    }

    public void setIdPlayLiit(String idPlayLiit) {
        this.idPlayLiit = idPlayLiit;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public void setVideos(Video video) {
        this.videos.add(video);
    }

    public int getNumeroVideos() {
        return numeroVideos;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }
}
