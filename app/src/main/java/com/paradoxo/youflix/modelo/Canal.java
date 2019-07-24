package com.paradoxo.youflix.modelo;

import com.google.api.services.youtube.model.Thumbnail;

public class Canal {
    private String id;
    private String nome;
    private Thumbnail avatar;
    private String banner;
    private String descricacao;

    private String qtdInscrito;
    private String qtdView;
    private String idChannelUpload;

    public Canal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setIdChannelUpload(String idChannelUpload) {
        this.idChannelUpload = idChannelUpload;
    }

    public String getIdChannelUpload() {
        return idChannelUpload;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Thumbnail getAvatar() {
        return avatar;
    }

    public void setAvatar(Thumbnail avatar) {
        this.avatar = avatar;
    }

    public String getDescricacao() {
        return descricacao;
    }

    public void setDescricacao(String descricacao) {
        this.descricacao = descricacao;
    }

    public String getQtdInscrito() {
        return qtdInscrito;
    }

    public void setQtdInscrito(String qtdInscrito) {
        this.qtdInscrito = qtdInscrito;
    }

    public String getQtdView() {
        return qtdView;
    }

    public void setQtdView(String qtdView) {
        this.qtdView = qtdView;
    }

}
