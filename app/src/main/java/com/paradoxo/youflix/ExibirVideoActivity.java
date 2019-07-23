package com.paradoxo.youflix;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.paradoxo.youflix.modelo.Video;
import com.paradoxo.youflix.util.YTinfo;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ExibirVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final int COD_ERRO = 1000;
    private static String API_KEY;
    private String idVideo;
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_video);

        configurarToolbar();
        configurarBotaoPlay();
        configurarPlayerYouTube();


        LoadDadosVideo loadDadosVideo = new LoadDadosVideo();
        loadDadosVideo.execute();



    }

    private void configurarToolbar() {
        ((androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configurarBotaoPlay() {
        findViewById(R.id.botaoPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirVideo();
            }
        });
    }

    public void exibirVideo() {
        inicializarVideo();
        findViewById(R.id.layoutPreview).setVisibility(View.GONE);
        youTubeView.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        Log.e("id click", String.valueOf(featureId));
        return true;
    }

    private void configurarPlayerYouTube() {
        API_KEY = getString(R.string.api_key);
        youTubeView = findViewById(R.id.youtubePlayer);

        Intent intent = getIntent();
        idVideo = intent.getStringExtra("idVideo");
        //idVideo = "wxThqXhBu68"; // Id de um vídeo de teste
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean foiRestaurado) {
        if (!foiRestaurado) youTubePlayer.loadVideo(idVideo);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, COD_ERRO).show();
        } else {
            String erro = getString(R.string.erro_carregar_player) + youTubeInitializationResult.toString();
            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == COD_ERRO) inicializarVideo();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        colocarVideoEmTelaCheia();
    }

    private void colocarVideoEmTelaCheia() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        // Esconde a appBar
    }

    private void inicializarVideo() {
        youTubeView.initialize(API_KEY, this);
    }


    @SuppressLint("StaticFieldLeak")
    public class LoadDadosVideo extends AsyncTask<Void, Void, Video> {
        @Override
        protected Video doInBackground(Void... voids) {
            YTinfo yTinfo = new YTinfo();

            return yTinfo.infoVideo(idVideo);
        }

        @Override
        protected void onPostExecute(Video video) {
            super.onPostExecute(video);
            if (video.getTitulo() != null) {
                preencherdadosVideoEmTela(video);
            } else {
                Log.e("erro", "Dados do vídeo não carregados");
            }
        }
    }

    private void preencherdadosVideoEmTela(Video video) {
        Log.e("tag", video.getTitulo());
        Log.e("tag", String.valueOf(video.getData()));

        TextView textViewTitulo = findViewById(R.id.txt_titulo);
        TextView textViewDescricao = findViewById(R.id.txt_descricao);

        textViewTitulo.setText(video.getTitulo());
        textViewDescricao.setText(video.getDescricao());

        TextView visu = findViewById(R.id.numero_visu);
        TextView like = findViewById(R.id.numero_like);
        TextView deslike = findViewById(R.id.numero_deslike);
        TextView tempo_atras = findViewById(R.id.tempo_atras);

        DateTime dtVideo = new DateTime(video.getData().getValue() + video.getData().getTimeZoneShift());
        DateTimeFormatter dtFormatada = DateTimeFormat.forPattern("dd-MM-yyyy  HH:mm");

        visu.setText(video.getQtdVisu());
        like.setText(video.getQtdLike());
        deslike.setText(video.getQtdDeslike());

        Picasso.with(this).load(video.getThumbnail().getUrl()).into((ImageView) findViewById(R.id.thumbnailImageView));

        tempo_atras.setText(dtFormatada.print(dtVideo));
    }
}
