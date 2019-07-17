package com.paradoxo.youflix.modelo;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Thumbnail;

public class Video {
    private String id;
    private String titulo;
    private DateTime data;
    private String descricao;

    private String qtdLike;
    private String qtdDeslike;
    private String qtdVisu;
    private String qtdComentario;
    private String duracao;
    private String status;
    private Thumbnail thumbnail;
    private boolean rodape = false;
    private boolean progresso = false;

    public Video() {
    }

    public Video(boolean rodape) {
        this.rodape = rodape;
    }

    public boolean getRodape() {
        return rodape;
    }

    public void setRodape(boolean rodape) {
        this.rodape = rodape;
    }

    public boolean getProgresso() {
        return progresso;
    }

    public void setProgresso(boolean progresso) {
        this.progresso = progresso;
    }

    public Video(String id, String titulo, DateTime data, String id_resposta) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
    }

    public Video(String id, String titulo, DateTime data) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
    }

    public Video(String titulo, String string) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public DateTime getData() {
        return data;
    }

    public void setData(DateTime data) {
        this.data = data;
    }

    public String getQtdLike() {
        return qtdLike;
    }

    public void setQtdLike(String qtdLike) {
        this.qtdLike = qtdLike;
    }

    public String getQtdDeslike() {
        return qtdDeslike;
    }

    public void setQtdDeslike(String qtdDeslike) {
        this.qtdDeslike = qtdDeslike;
    }

    public String getQtdVisu() {
        return qtdVisu;
    }

    public void setQtdVisu(String qtdVisu) {
        this.qtdVisu = qtdVisu;
    }

    public void setQtdComentario(String qtdComentario) {
        this.qtdComentario = qtdComentario;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
