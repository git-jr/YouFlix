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
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.VideoListResponse;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.modelo.PaginaPlayList;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.PlayList;
import com.paradoxo.youflix.modelo.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YTinfo {

    private final JsonFactory jsonFactory = new JacksonFactory();
    private final HttpTransport httpTransport = new NetHttpTransport();

    private static String channelUpload, chave, channelId;
    private static final long ITEM_POR_PG = 10;
    private static String pacote = "default";
    private static String sha1 = "default";
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

    public Video carregarTodasInformacoesVideo(String idVideo) {
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
                com.google.api.services.youtube.model.Video itens = busca.getItems().get(0);
                video.setId(itens.getId());
                video.setTitulo(itens.getSnippet().getTitle());
                video.setQtdVisu(itens.getStatistics().getViewCount().toString());
                video.setQtdLike(itens.getStatistics().getLikeCount().toString());
                video.setQtdDeslike(itens.getStatistics().getDislikeCount().toString());
                video.setQtdComentario(itens.getStatistics().getCommentCount().toString());
                video.setData(itens.getSnippet().getPublishedAt());
                video.setDescricao(itens.getSnippet().getDescription());
                video.setStatus(itens.getStatus().getPrivacyStatus());

                video.setDuracao(formatarDataVideo(itens.getContentDetails().getDuration()));

                ThumbnailDetails thumbnails = itens.getSnippet().getThumbnails();
                if (thumbnails.get("standard") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("standard"));
                } else if (thumbnails.get("maxers") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("maxers"));
                } else if (thumbnails.get("default") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("default"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }

    private Video carregarTituloEThumbanilVideo(String idVideo) {
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

            if (conteudoVideo != null) {
                com.google.api.services.youtube.model.Video itens = busca.getItems().get(0);
                video.setId(itens.getId());
                video.setTitulo(itens.getSnippet().getTitle());

                ThumbnailDetails thumbnails = itens.getSnippet().getThumbnails();
                if (thumbnails.get("standard") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("standard"));
                } else if (thumbnails.get("maxers") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("maxers"));
                } else if (thumbnails.get("default") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("default"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }

    public Canal carregarBannerCanal() {
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
                    // Esse canal não existe
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

        if (listaVideos != null) {
            for (SearchResult lv : listaVideos) {
                Video video = new Video();
                video.setId(lv.getId().getVideoId());
                video.setTitulo(lv.getSnippet().getTitle());

                ThumbnailDetails thumbnails = lv.getSnippet().getThumbnails();
                if (thumbnails.get("standard") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("standard"));
                } else if (thumbnails.get("maxers") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("maxers"));
                } else if (thumbnails.get("default") != null) {
                    video.setThumbnail((Thumbnail) thumbnails.get("default"));
                }


                paginaVideo.setVideos(video);
            }
        }
        return paginaVideo;
    }

    public PaginaPlayList listarPlayListsDoCanal(PaginaPlayList paginaPlayList) throws IOException {
        youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                request.getHeaders().set("X-Android-Package", pacote);
                request.getHeaders().set("X-Android-Cert", sha1);
            }
        }).setApplicationName(pacote).build();

        YouTube.Playlists.List query = youtube.playlists().list("id,snippet");
        query.setFields("items(id,snippet/title)");
        query.setChannelId(channelId);
        query.setMaxResults(ITEM_POR_PG);
        query.setKey(chave);

        PlaylistListResponse busca = query.execute();
        List<Playlist> listaPlays = busca.getItems();

        if (listaPlays != null) {
            for (Playlist p : listaPlays) {
                PlayList playList = new PlayList();
                playList.setId(p.getId());
                playList.setNome(p.getSnippet().getTitle());
                paginaPlayList.setPlayLists(playList);
            }
        }

        return paginaPlayList;
    }

    private PaginaVideo carregarVideosDoCanalParcialmente(PaginaVideo paginaVideo, boolean carregandoProximaPagina) throws IOException {
        youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                request.getHeaders().set("X-Android-Package", pacote);
                request.getHeaders().set("X-Android-Cert", sha1);
            }
        }).setApplicationName(pacote).build();

        YouTube.PlaylistItems.List query = youtube.playlistItems().list("id,snippet");
        query.setMaxResults(ITEM_POR_PG);
        query.setKey(chave);

        if (paginaVideo.getIdPlayLiit() != null) {
            // Caso exista um id, significa que é uma consulta por Playlist específica
            query.setPlaylistId(paginaVideo.getIdPlayLiit());
        } else {
            query.setPlaylistId(channelUpload);
            // Caso contrário vamos listar todos os uploads mais recntes do canal
        }


        if (carregandoProximaPagina) {
            query.setPageToken(paginaVideo.getNextPageToken());
        }

        PlaylistItemListResponse busca = query.execute();
        List<PlaylistItem> listaVideos = busca.getItems();

        paginaVideo.setNumeroVideos(busca.getPageInfo().getTotalResults());

        if (listaVideos != null) {
            paginaVideo.setNextPageToken(busca.getNextPageToken());
            paginaVideo.setPrevPageToken(busca.getPrevPageToken());

            for (PlaylistItem lv : listaVideos) {
                Video video = new Video();
                video.setId(lv.getSnippet().getResourceId().getVideoId());
                paginaVideo.setVideos(video);
            }
        }
        return paginaVideo;
    }

    public PaginaVideo listarVideosDoCanal(PaginaVideo paginaVideo, boolean recarregando) throws IOException {
        List<Video> videosCanal = carregarVideosDoCanalParcialmente(paginaVideo, recarregando).getVideos();

        List<Video> videosComInfo = new ArrayList<>();
        for (Video vC : videosCanal) {
            if (vC.getId() == null) {
                break;
                // Para o caso de vídeos e playlists privadas
            }

            videosComInfo.add(carregarTituloEThumbanilVideo(vC.getId()));

            if (videosComInfo.get(videosComInfo.size() - 1).getId() == null) {
                videosComInfo.remove(videosComInfo.size() - 1);
                // Se por algum motivo houver algum vídeo nulo (como no caso da listagem de vídeos privados em playlists) ele é removido
            }
        }

        paginaVideo.setVideos(videosComInfo);

        return paginaVideo;
    }


    private String formatarDataVideo(String duracaoNormal) {
        String duracaoFormatada = duracaoNormal.replace("PT", "")
                .replace("H", ":")
                .replace("M", ":")
                .replace("S", "");

        if (duracaoFormatada.indexOf(":") == duracaoFormatada.length() - 1) { // Ajuste para exbição de durações com zeros no fim
            return (" " + duracaoFormatada + "00 ");
        } else {
            return (" " + duracaoFormatada + " ");
        }

        // Converte o formato da duração do vídeo de algo como "PT10H9M8S" para algo como "04:20"
    }

    private String getPrefString(String nomeShared) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(nomeShared, "");
    }

}
