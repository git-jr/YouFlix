package com.paradoxo.youflix.modelo;

import java.util.ArrayList;
import java.util.List;

public class PaginaVideo {
    private List<String> tokenPagina = new ArrayList<>();     // Token numero de pagina que podemos pegar com uma busca pode ser usado para avança re trocar as paginas de vídeo (para voltar usar "atual menos anterior")
    private List<Video> videos = new ArrayList<>();
    private String idCanal;
    private String pgToken = null; // Se isso não for nullo deverá ser usado como tpken na página na pesquisa
    private String nextPageToken; // Token que pode ser usada para carregar a PRÓXIMA página de playlists
    private String prevPageToken; // Token que pode ser usada para carregar a página ANTERIOR de playlists
    private String idPlayLiit;
    private int numeroVideos; // Vai ser usado para saber se o botão "ver mais" será mostrado


    public PaginaVideo(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public PaginaVideo() {

    }

    public String getIdPlayLiit() {
        return idPlayLiit;
    }

    public int getNumeroVideos() {
        return numeroVideos;
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

    public String getPrevPageToken() {
        return prevPageToken;
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
}
