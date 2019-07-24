package com.paradoxo.youflix.modelo;

import com.google.api.services.youtube.model.Thumbnail;

public class PlayList {
    private String id;
    private String nome;
    private String descricao;
    private Thumbnail thumbnail;
    private String numeroVideos;
    private boolean rodape = false;
    private boolean progresso = false;

    public PlayList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PlayList(boolean rodape) {
        this.rodape = rodape;
    }

    public boolean getRodape() {
        return rodape;
    }

    public void setRodape(boolean rodape) {
        this.rodape = rodape;
    }

    public void setProgresso(boolean progresso) {
        this.progresso = progresso;
    }

    public boolean getProgresso() {
        return progresso;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNumeroVideos() {
        return numeroVideos;
    }

    public void setNumeroVideos(String numeroVideos) {
        this.numeroVideos = numeroVideos;
    }
}
