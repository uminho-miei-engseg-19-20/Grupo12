# Exercício 1 - Assinaturas cegas (Blind signatures) baseadas no Elliptic Curve Discrete Logarithm Problem (ECDLP)
## Pergunta 1.1
Para a realização da pergunta 1.1, foi primeiro gerados o par de chaves e o certificado utilizando o openssl através dos comandos listados na Experiência 1.1. Feito isto, os códigos disponibilizados foram alterados da seguinte forma:

[init-app.py](./init-app.py)    
[AJAX](./ajax.pdf)

# Exercício 2 - Protocolo SSL/TLS
## Pergunta 2.1
Para a pergunta 2, foi-nos pedido que escolhessemos dois sites de empresas não bancárias e não portuguesas cotadas na Euronext. Depois de escolhidosera foi realizado um SSL Server test a cada um dos websites, usando o site SSL labs.

##### i. Resultados
As empresas escolhidas foram AkzoNobel e AJAX, os resultados dos testes encontram-se no repositório:

[AkzoNobel](./akzo.pdf)    
[AJAX](./ajax.pdf)

##### ii. Análise do resultado do SSL Server test relativo ao site com pior rating
Neste caso, o website com pior rating foi o do AJAX. O site do AJAX teve uma excelente classificação A, mas não uma classificação A+, ou seja perfeita, como no outro caso analisado.

Como é possível analisar nos ficheiros em anexo, o site não usa a versão mais recente do TLS 1.3 e permite o uso da sua versão mais antiga a 1.0,  permite o uso de Cipher suites  que são consideradas fracas, por várias razões sendo a principal pelo o uso do mudo CBC, porém permite utilizar outras Cipher suites mais seguras.

##### iii. ROBOT na secção de detalhe do protocolo
O ROBOT attack, é uma vulnerabilidade, que afecta servidores web, que estão configurados a usar o algoritmo de encriptação RSA como troca de chaves de sessão, ao ser explorada esta vulnerabilidade permitia a um atacante recuperar a chave de sessão usada para uma ou mais sessões e desincriptar as comunicações entre ele e o servidor.
Esta vulnerabilidade apenas afeta cifras TLS que usem a encriptação RSA, porém nas ligações mais recentes de TLS usam o Curvas elipticas, para a troca de chaves, e apenas precisam do RSA para as assinaturas, com isto concluimos que esta vulnerabilidade apenas poderia afetar o site do AJAX, embora este também aceite curvas Elipticas, por isso à partida esta vulnerabilidade seria ultrapssada.

