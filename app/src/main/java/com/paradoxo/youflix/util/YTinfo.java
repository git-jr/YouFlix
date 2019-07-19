package com.paradoxo.youflix.util;

import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.VideoListResponse;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.modelo.Video;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Locale;

public class YTinfo {

    private final JsonFactory jsonFactory = new JacksonFactory();
    private final HttpTransport httpTransport = new NetHttpTransport();

    private static String sha1 = "default";
    private static String chave = "AIzaSyCcLnjFEqUlSfne5cDMAFxGcdZIYCh_0cY";//"AIzaSyAbGlNFbel8iAGoDPPNZZfVhM0Y02VFvcY";
    private static String channelId = "UCYETxCTVSpVOc1TP5KP0pMg"; // Canal Paradoxo: "UCB1knfRXbQLlmz2HrbdHO7Q";
    private static String pacote = "default";
    private YouTube youtube;

    public Video infoVideo(String idVideo) {
        // Classe cópia da YTlist só que estamos fazendo não instanciável
        Video video = new Video();
        try {
            youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.getHeaders().set("X-Android-Package", pacote);
                    request.getHeaders().set("X-Android-Cert", sha1);
                }
            }).setApplicationName(pacote).build();

            YouTube.Videos.List search = youtube.videos().list("id,snippet,status,statistics,contentDetails");
            search.setId(idVideo);
            search.setKey(chave);

            VideoListResponse busca = search.execute();
            List<com.google.api.services.youtube.model.Video> conteudoVideo = busca.getItems();

            if (conteudoVideo != null) {
                video.setId(busca.getItems().get(0).getId());
                video.setTitulo(busca.getItems().get(0).getSnippet().getTitle());
                video.setQtdVisu(busca.getItems().get(0).getStatistics().getViewCount().toString());
                video.setQtdLike(busca.getItems().get(0).getStatistics().getLikeCount().toString());
                video.setQtdDeslike(busca.getItems().get(0).getStatistics().getDislikeCount().toString());
                video.setQtdComentario(busca.getItems().get(0).getStatistics().getCommentCount().toString());
                video.setData(busca.getItems().get(0).getSnippet().getPublishedAt());
                video.setDescricao(busca.getItems().get(0).getSnippet().getDescription());
                video.setStatus(busca.getItems().get(0).getStatus().getPrivacyStatus());


                // Para conveter o formato da duração do vídeo que está em algo como "PT10H9M8S"
                String duracao = busca.getItems().get(0).getContentDetails().getDuration().replace("PT", "")
                        .replace("H", ":")
                        .replace("M", ":")
                        .replace("S", "");

                if (duracao.indexOf(":") == duracao.length() - 1) { // Ajuste para exbição de durações com zeros no fim
                    video.setDuracao(" " + duracao + "00 ");
                } else {
                    video.setDuracao(" " + duracao + " ");
                }

                video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("maxres"));

                DateTime dataDoVideo = new DateTime(video.getData().getValue() + video.getData().getTimeZoneShift());

                Cronos cronos = new Cronos();
                cronos.tempo_depois(dataDoVideo);
            }
        } catch (Exception e) {
            Log.e("TAG", "Erro ao carregar vídeo");
            e.printStackTrace();
        }
        return video;
    }

    public Canal infoCanal(String idCanal) {
        Canal canal = new Canal();
        try {
            youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.getHeaders().set("X-Android-Package", pacote);
                    request.getHeaders().set("X-Android-Cert", sha1);
                }
            }).setApplicationName(pacote).build();

            YouTube.Channels.List search = youtube.channels().list("id,snippet,statistics,BrandingSettings");

            if (idCanal != null) {
                search.setId(idCanal);
            } else {
                search.setId(channelId);
            }
            search.setKey(chave);

            ChannelListResponse busca = search.execute();
            List<Channel> conteudoVideo = busca.getItems();

            if (conteudoVideo != null) {
                canal.setId(busca.getItems().get(0).getId());
                canal.setNome(busca.getItems().get(0).getSnippet().getTitle());
                canal.setDescricacao(busca.getItems().get(0).getSnippet().getDescription());
                canal.setAvatar(busca.getItems().get(0).getSnippet().getThumbnails().getHigh());
                canal.setBanner(busca.getItems().get(0).getBrandingSettings().getImage().getBannerTvHighImageUrl());


                // Formata as quantidade de views e inscritos com "pontos" (.) então 170085 e 11605063 viram 170.085 e 11.605.063
                String qtdViews = String.format(new Locale("pt"), "%,d", busca.getItems().get(0).getStatistics().getViewCount());
                String qtdSubs = String.format(new Locale("pt"), "%,d", busca.getItems().get(0).getStatistics().getSubscriberCount());

                canal.setQtdInscrito(qtdSubs);
                canal.setQtdView(qtdViews);
            }
        } catch (Exception e) {
            Log.e("TAG", "Erro ao carregar dados do canal");
            e.printStackTrace();
        }
        return canal;
    }

    public Canal carregarBanner() {
        Canal canal = new Canal();
        try {
            youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.getHeaders().set("X-Android-Package", pacote);
                    request.getHeaders().set("X-Android-Cert", sha1);
                }
            }).setApplicationName(pacote).build();

            YouTube.Channels.List search = youtube.channels().list("BrandingSettings");
            search.setFields("items(brandingSettings/image(bannerTvHighImageUrl))");
            search.setId(channelId);
            search.setKey(chave);

            ChannelListResponse busca = search.execute();
            List<Channel> conteudoVideo = busca.getItems();

            if (conteudoVideo != null) {
                canal.setBanner(busca.getItems().get(0).getBrandingSettings().getImage().getBannerTvHighImageUrl());

            }
        } catch (Exception e) {
            Log.e("TAG", "Erro ao carregar dados do canal");
            e.printStackTrace();
        }
        return canal;
    }


}
