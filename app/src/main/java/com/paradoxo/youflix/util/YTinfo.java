package com.paradoxo.youflix.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.VideoListResponse;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.modelo.PaginaPlayList;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.PlayList;
import com.paradoxo.youflix.modelo.Video;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YTinfo {

    private final JsonFactory jsonFactory = new JacksonFactory();
    private final HttpTransport httpTransport = new NetHttpTransport();

    private static final long item_por_pg = 2;
    private static String sha1 = "default";
    private static String channelUpload = "UUYETxCTVSpVOc1TP5KP0pMg";// Canal Paradoxo: "UUB1knfRXbQLlmz2HrbdHO7Q";
    private static String chave; // = "AIzaSyCcLnjFEqUlSfne5cDMAFxGcdZIYCh_0cY";//"AIzaSyAbGlNFbel8iAGoDPPNZZfVhM0Y02VFvcY";
    private static String channelId; // = "UCYETxCTVSpVOc1TP5KP0pMg"; // Canal Paradoxo: "UCB1knfRXbQLlmz2HrbdHO7Q";
    private static String pacote = "default";
    private YouTube youtube;
    private Context context;

    public YTinfo(Context context) {
        this.context = context;
        carregarDadosRequsicao();

    }

    private void carregarDadosRequsicao() {
        channelUpload = getPrefString("channelUpload");
        channelId = getPrefString("idChannel");
        chave = getPrefString("apiKey");
    }

    public Video infoVideo(String idVideo) {
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


                String thumb;
                try {
                    thumb = ((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("standard")).getUrl(); // Só pra testar um possivel erro casso não exitsa uma thumb desse tamanho
                    video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("standard"));
                } catch (Exception e) { // Pro caso da miniatura ser de um vídeo privado, pegamos a miniatura padrão já que não existe uma de maxima qualidade
                    try {
                        thumb = ((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("maxres")).getUrl(); // Só pra testar um possivel erro casso não exitsa uma thumb desse tamanho
                        video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("maxres"));
                    } catch (Exception ex) {
                        // Se os formatos de miniatura de vídeo acima não existirem vamos pegar o padrão mesmo
                        video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("default"));
                    }
                }

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

    public Canal verificarCanal(String apiKey, String idCanal) {
        Canal canal = new Canal();
        try {
            youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.getHeaders().set("X-Android-Package", pacote);
                    request.getHeaders().set("X-Android-Cert", sha1);
                }
            }).setApplicationName(pacote).build();

            YouTube.Channels.List search = youtube.channels().list("snippet, contentDetails");
            search.setFields("items(id,contentDetails/relatedPlaylists/uploads)");
            search.setForUsername(idCanal);


            search.setKey(apiKey);

            ChannelListResponse busca = search.execute();

            List<Channel> items = busca.getItems();

            if (!items.isEmpty() && !items.get(0).getId().isEmpty()) {
                canal.setId(items.get(0).getId());
                canal.setIdChannelUpload(items.get(0).getContentDetails().getRelatedPlaylists().getUploads());

            } else {
                search.setId(idCanal);
                search.setForUsername(null);
                // O usuário pode já estar inserido a url do canal no formato certo

                busca.clear();
                items.clear();
                busca = search.execute();
                items = busca.getItems();


                if (!items.isEmpty() && !items.get(0).getId().isEmpty()) {
                    canal.setId(items.get(0).getId());
                    canal.setIdChannelUpload(items.get(0).getContentDetails().getRelatedPlaylists().getUploads());
                } else {
                    canal.setId(null);
                    // Esse canal provavelmente não existe
                }


            }

        } catch (Exception e) {
            Log.e("TAG", "Erro ao carregar dados do canal");
            e.printStackTrace();
            return null;

        }
        return canal;
    }

    public PaginaVideo buscarVideosCanal(PaginaVideo paginaVideo, String parametroDeBusca) throws IOException {
        youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                request.getHeaders().set("X-Android-Package", pacote);
                request.getHeaders().set("X-Android-Cert", sha1);
            }
        }).setApplicationName(pacote).build();

        YouTube.Search.List query = youtube.search().list("id,snippet");
        query.setFields("items(id/videoId,snippet/title,snippet/thumbnails)");
        query.setMaxResults((long) 5);
        query.setKey(chave);
        query.setChannelId(channelId);
        query.setQ(parametroDeBusca);
        query.setType("video");

        SearchListResponse busca = query.execute();
        List<SearchResult> listaVideos = busca.getItems();


        if (listaVideos != null) { // Se existir resultados:
            for (SearchResult lv : listaVideos) {
                Video video = new Video();
                video.setId(lv.getId().getVideoId());
                video.setTitulo(lv.getSnippet().getTitle());

                String thumb;
                try {
                    thumb = ((Thumbnail) lv.getSnippet().getThumbnails().get("standard")).getUrl(); // Só pra testar um possivel erro casso não exitsa uma thumb desse tamanho
                    video.setThumbnail((Thumbnail) lv.getSnippet().getThumbnails().get("standard"));
                } catch (Exception e) { // Pro caso da miniatura ser de um vídeo privado, pegamos a miniatura padrão já que não existe uma de maxima qualidade
                    try {
                        thumb = ((Thumbnail) lv.getSnippet().getThumbnails().get("maxres")).getUrl(); // Só pra testar um possivel erro casso não exitsa uma thumb desse tamanho
                        video.setThumbnail((Thumbnail) lv.getSnippet().getThumbnails().get("maxres"));
                    } catch (Exception ex) {
                        // Se os formatos de miniatura de vídeo acima não existirem vamos pegar o padrão mesmo
                        video.setThumbnail((Thumbnail) lv.getSnippet().getThumbnails().get("default"));
                    }
                }

                paginaVideo.setVideos(video);
            }
        }
        return paginaVideo;
    }

    // Apenas Thumbanil e id
    private Video infoVideoSimples(String idVideo) {
        Video video = new Video();
        try {
            youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.getHeaders().set("X-Android-Package", pacote);
                    request.getHeaders().set("X-Android-Cert", sha1);
                }
            }).setApplicationName(pacote).build();

            YouTube.Videos.List query = youtube.videos().list("id,snippet");
            query.setFields("items(id,snippet/title,snippet/thumbnails)");
            query.setId(idVideo);
            query.setKey(chave);

            VideoListResponse busca = query.execute();
            List<com.google.api.services.youtube.model.Video> conteudoVideo = busca.getItems();

            if (conteudoVideo != null) { // Se existir resultados:
                video.setId(busca.getItems().get(0).getId());
                video.setTitulo(busca.getItems().get(0).getSnippet().getTitle());

                String thumb;
                try {
                    thumb = ((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("standard")).getUrl(); // Só pra testar um possivel erro casso não exitsa uma thumb desse tamanho
                    video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("standard"));
                } catch (Exception e) { // Pro caso da miniatura ser de um vídeo privado, pegamos a miniatura padrão já que não existe uma de maxima qualidade
                    try {
                        thumb = ((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("maxres")).getUrl(); // Só pra testar um possivel erro casso não exitsa uma thumb desse tamanho
                        video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("maxres"));
                    } catch (Exception ex) {
                        // Se os formatos de miniatura de vídeo acima não existirem vamos pegar o padrão mesmo
                        video.setThumbnail((Thumbnail) busca.getItems().get(0).getSnippet().getThumbnails().get("default"));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }

    public PaginaPlayList listaPlayLists(PaginaPlayList paginaPlayList) throws IOException {
        youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                request.getHeaders().set("X-Android-Package", pacote);
                request.getHeaders().set("X-Android-Cert", sha1);
            }
        }).setApplicationName(pacote).build();

        YouTube.Playlists.List query = youtube.playlists().list("id,snippet");
        query.setFields("items(id,snippet/title)");
        query.setChannelId(channelId); // Usando o ID de uploads do canal, que é obtido da mesma maneira que o id do canal através da própria  API em algum list
        query.setMaxResults(item_por_pg);
        query.setKey(chave);

        PlaylistListResponse busca = query.execute();

        List<Playlist> listaPlays = busca.getItems();

        if (listaPlays != null) { // Se existir resultados:
            for (Playlist p : listaPlays) {
                PlayList playList = new PlayList();
                playList.setId(p.getId());
                playList.setNome(p.getSnippet().getTitle());
                paginaPlayList.setPlayLists(playList); // Isso adiciona um item de playlist ao LIST de paginaPlayList
            }
        } else {
            Log.e("TAG", "ERRO AO CARREGAR PLAYLIST");
        }
        return paginaPlayList;
    }

    // Traz um obejto que contém uma lista dos IDs de vídeo daquela página e a identificação da próxima página
    private PaginaVideo listaPgCanal(PaginaVideo paginaVideo, boolean recarregando) throws IOException {
        youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                request.getHeaders().set("X-Android-Package", pacote);
                request.getHeaders().set("X-Android-Cert", sha1);
            }
        }).setApplicationName(pacote).build();

        YouTube.PlaylistItems.List query = youtube.playlistItems().list("id,snippet");
        query.setMaxResults(item_por_pg);
        query.setKey(chave);

        if (paginaVideo.getIdPlayLiit() != null) {  // Caso exista um id, significa que é uma consulta por Playlist específica
            query.setPlaylistId(paginaVideo.getIdPlayLiit());
        } else { // Caso contrário vamos listar todos uploads do canal por padrão
            query.setPlaylistId(channelUpload); // Aqui estamos usando o o ID de uploads do canal, que é obteido das mesma maneira que o id do canal
        }


        if (recarregando) { // Caso sim, significa que a proxima página está sendo carregada
            query.setPageToken(paginaVideo.getNextPageToken());
        }

        PlaylistItemListResponse busca = query.execute();

        List<PlaylistItem> listaVideos = busca.getItems();

        paginaVideo.setNumeroVideos(busca.getPageInfo().getTotalResults());


        if (listaVideos != null) { // Se existir resultados:
            paginaVideo.setNextPageToken(busca.getNextPageToken());  // Para carregar mais playlists precisamos ir para próxima página de buscas e para isso precisamos ter o proximo token armazenado sempre
            paginaVideo.setPrevPageToken(busca.getPrevPageToken()); // Também estamos armazenando o token da página anterior

            for (PlaylistItem lv : listaVideos) {
                Video video = new Video();
                video.setId(lv.getSnippet().getResourceId().getVideoId()); // Só queremos o id mesmo por hora
                paginaVideo.setVideos(video);
            }
        }
        return paginaVideo;
    }

    public PaginaVideo listaVideos(PaginaVideo paginaVideo, boolean recarregando) throws IOException {
        List<Video> videosCanal = listaPgCanal(paginaVideo, recarregando).getVideos();

        List<Video> videosComInfo = new ArrayList<>();
        for (Video vC : videosCanal) {

            if (vC.getId() == null) { // Para o caso de vídeos e playlists privadas
                break; // Sai do ciclo e vai para o proximo
            }

            videosComInfo.add(infoVideoSimples(vC.getId()));

            if (videosComInfo.get(videosComInfo.size() - 1).getId() == null) { // Se por algum motivo houver algum vídeo nulo (como no caso da listagem de playlist) ele é removido
                videosComInfo.remove(videosComInfo.size() - 1);
            }
        }

        paginaVideo.setVideos(videosComInfo);

        return paginaVideo;
    }

    private String getPrefString(String nomeShared) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(nomeShared, "");
    }

}
