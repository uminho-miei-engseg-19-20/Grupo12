# Exercício 1 - Assinaturas cegas (Blind signatures) baseadas no Elliptic Curve Discrete Logarithm Problem (ECDLP)
## Pergunta 1.1
Para a realização da pergunta 1.1, foi primeiro gerados o par de chaves e o certificado utilizando o openssl através dos comandos listados na Experiência 1.1. Feito isto, os códigos disponibilizados foram alterados da seguinte forma:



- Assinante:  
  - [init-app.py](./E01/init-app.py):  
    Pode ser usado de duas formas:

    1 - `init-app.py`:  
  
    Devolve o R' (pRDashComponents)  

    2 - `init-app.py -init`:  
  
    Inicializa as componentes *InitComponents* e *pRDashComponents*, devolve a *pRDashComponents e guarda-as no ficheiro [signerFile.txt](./E01/signerFile.txt)


  - [blindSignature-app.py](./E01/blindSignature-app.py):

    `blindSignature-app.py -key <chave privada> -bmsg <Blind message>`

    Recebe a *chave privada* e a *blind message* como parâmetro, lê o *initComponent* do ficheiro [signerFile.txt](./E01/signerFile.txt) e devolve o *blind signature*.  



- Requerente:  
  - [ofusca-app.py](./E01/ofusca-app.py):   

    `ofusca-app.py -msg <mensagem a assinar> -RDash <pRDashComponents>`  

    Recebe a *mensagem a ser assinada* e o *PRDashComponent*, guarda *blind components* e *pRComponents* no ficheiro [requesterFile.txt](./E01/requesterFile.txt) e devolve a *blind message*.


  - [deofusca-app.py](./E01/deofusca-app.py):  

    `desofusca-app.py -s <Blind Signature> -RDash <pRDashComponents>`  

    Recebe a *blind signature* e *pRDashComponents*, lê o *blindComponents* do ficheiro [requesterFile.txt](./E01/requesterFile.txt) e devolte a *signature*.  



- Verificador:  
  - [verify-app.py](./E01/verify-app.py):  

    `verify-app.py -cert <certificado do assinante> -msg <mensagem original a assinar> -sDash <Signature> -f <ficheiro do requerente>`  

    Recebe o *certificado do assinante*, a *mensagem original a assinar*, a *signature* e o ficheiro [requesterFile.txt](./E01/requesterFile.txt) e imprime *Valid signature* se a assinatura sDash sobre a mensagem *msg* é válida ou *Invalid signature* se a assinatura não for válida.  

Exemplo de utilização:

- **Inicialização:**  
```
$python init-app.py -init
pRDashComponents: 4e5e1dc1104377013d6135edfe14079911f598f2ac38cb35073ddd624ecd076a.7dad36047a378d800435a0eb4b0123100539698f095684724918275c921b837b
initComponents and pRDashComponents initiated and stored in signerFile.txt.
```

- **Ofuscação:**  

```
$python ofusca-app.py -msg mensagem a ser assinada -RDash 4e5e1dc1104377013d6135edfe14079911f598f2ac38cb35073ddd624ecd076a.7dad36047a378d800435a0eb4b0123100539698f095684724918275c921b837b
Output
Blind message: 46156123a69a9ed9a1f26568fda50b32015a54ef7b604c7731045e11d59536f7
Blind components and pRComponents stored in requesterFile.txt.
```

- **Assinatura:**  

```
$python blindSignature-app.py -key key.pem -bmsg 46156123a69a9ed9a1f26568fda50b32015a54ef7b604c7731045e11d59536f7
Input
Passphrase: 
Output
Blind signature: 944f061ba0a200e525af5a5ceeb8bc841b884a9c1c0b6ceebafcedef4e478ee1146083be9ac7b69fb662d58de839064498f28bfb3b45840f0c37a599fc98d01
```

- **desofuscação:**  

```
$python desofusca-app.py -s 944f061ba0a200e525af5a5ceeb8bc841b884a9c1c0b6ceebafcedef4e478ee1146083be9ac7b69fb662d58de839064498f28bfb3b45840f0c37a599fc98d01 -RDash 4e5e1dc1104377013d6135edfe14079911f598f2ac38cb35073ddd624ecd076a.7dad36047a378d800435a0eb4b0123100539698f095684724918275c921b837b
Output
Signature: f77d59b073fa62020e9c80bb7461b8ad12a646ace368510c18ddeb68f4afcd78

```

- **Verificação:**  

```
$python verify-app.py -cert key.crt -msg mensagem a ser assinada -sDash f77d59b073fa62020e9c80bb7461b8ad12a646ace368510c18ddeb68f4afcd78 -f requesterFile.txt
Output
Valid signature

```

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

