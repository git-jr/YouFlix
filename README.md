# YouFlix
YouFlix é um aplicativo feito para meu portfólio e aprendizado;
O App possui a interface do app da empresa Netflix e lista o conteúdo de um canal do YouTube de acordo com os dados inserido pelo usuário.

<img src="https://miro.medium.com/max/700/1*ku5lArpD2Ou1mhbOYxt4LQ.png" alt="Comparação tela 1" width="600"> 


# Objetivo
Esse é um app meramente para aprendizado e portifólio, **não tem ligação com a empresa Netflix e não visa ser uma cópia do aplicativo da mesma**.
Usei como exemplo nas imagens de comparação o canal Andre Pilli (https://www.youtube.com/OraculosauroVLOG) que se encaixou bem ao layout; 

# Sobre o código em si...
Com esse projeto eu quis aprender e praticar um pouco mais sobre fragments e diferentes estilos para componentes visuais, também tive a oportunidade de voltar a mexer com a APi do YouTube para listar vídeos, playlists e dados do canal em si.
No geral o app foi feito com Android nativo à exceção do uso da biblioteca Picasso para o carregamento de imagens e da biblioteca Joda-Time para formatação de datas da API do YouTube. Não imagino que o código esteja impecável e livre de bugs mas gostei do resultado até agora.


<img src="https://miro.medium.com/max/700/1*2-wXvPEzk85nPPbsdoh1WA.png" alt="Comparação tela 2" width="600">
<img src="https://miro.medium.com/max/700/1*PmTiRQuh7s52TRCJ2YRSmw.png" alt="Comparação tela 3" width="600">
<img src="https://miro.medium.com/max/700/1*cjtUN2Auq2IQ6a03QvThXQ.png" alt="Comparação tela 4" width="600">
<img src="https://miro.medium.com/max/700/1*N1xIQUC7CE1Uw0BrD0_zoA.png" alt="Comparação tela 5" width="600">
<img src="https://miro.medium.com/max/700/1*QzC2f5HNcx6JJbwuawp7LA.png" alt="Comparação tela 6" width="600">
<img src="https://miro.medium.com/max/700/1*54dXmMl9zRAeI1HRKIh1mA.png" alt="Comparação tela 7" width="600">
Não é possível tirar screenshots dentro da guia de login do app da Netflix, então preferi redesenhar o site em forma de app



# Como obter uma API KEY para usar dentro do app
>1 Acesse o link: https://console.developers.google.com/apis/library/youtube.googleapis.com
>2 Faça login com uma conta Google
>3 Concorde com os termos e clique em continuar
<img src="https://cdn-images-1.medium.com/max/800/1*xx8kpPzUHtzKm_GAzxZOwA.png" alt="Passo 3 do tutorial" width="600">


>4 Clique em ativar a API do YouTube aguarde a ativação, se a tela não for trocada, clique novamente em ativar
<img src="https://cdn-images-1.medium.com/max/800/1*1qXRS2rwGrCW4AshazjyVQ.png" alt="Passo 4 do tutorial" width="600">


>5 Na tela que irá aparecer clique em credenciais
<img src="https://cdn-images-1.medium.com/max/800/1*gdSPo68W6NsdXkAiVlD4gA.png" alt="Passo 5 do tutorial" width="600">


>6 Na tela seguinte> CREATE CREDENTIALS> Chave API
<img src="https://cdn-images-1.medium.com/max/800/1*MZ2zBaHEoZHyM-51_OF7TA.png" alt="Passo 6 do tutorial" width="600">


>7 Sua API KEY foi gerada e você pode copiá-la clicando no botão destacado acima.

**Nota:** A Api YouTube possui uma cota de uso de diária de uso para cada chave, se excedida pode fazer o app parar de funcionar corretamente até que seja renovada, para informações sobre cotas da api consulte: https://developers.google.com/youtube/v3/getting-started?hl=pt-br

# Como obter o link de um canal para usar no app
> Abra seu aplicativo do Youtube, pesquise pelo canal que deseja obeter a URL e siga as instruções da imagem abaixo:
<img src="https://cdn-images-1.medium.com/max/800/1*OmEib7WiqVW6Uf-f7Sq8Bw.png" alt="Obter url canal" width="600">
