# Exercício 1 - Assinaturas cegas (Blind signatures) baseadas no Elliptic Curve Discrete Logarithm Problem (ECDLP)
## Pergunta 1.1
Para a realização da pergunta 1.1, foi primeiro gerados o par de chaves e o certificado utilizando o openssl através dos comandos listados na Experiência 1.1. Feito isto, os códigos disponibilizados foram alterados da seguinte forma:



- **Assinante:**  
  - [init-app.py](./E01/init-app.py):  
    Pode ser usado de duas formas:

    1 - `init-app.py`:  
  
    Devolve o R' (*pRDashComponents*)  

    2 - `init-app.py -init`:  
  
    Inicializa as componentes *InitComponents* e *pRDashComponents*, devolve o R' (*pRDashComponents*) e guarda-as no ficheiro [signerFile.txt](./E01/signerFile.txt)


  - [blindSignature-app.py](./E01/blindSignature-app.py):

    `blindSignature-app.py -key <chave privada> -bmsg <Blind message>`

    Recebe a *chave privada* e a *blind message* como parâmetro, lê o *initComponent* do ficheiro [signerFile.txt](./E01/signerFile.txt) e devolve o s (*blind signature*).  



- **Requerente:**  
  - [ofusca-app.py](./E01/ofusca-app.py):   

    `ofusca-app.py -msg <mensagem a assinar> -RDash <pRDashComponents>`  

    Recebe a *mensagem a ser assinada* e o *PRDashComponent*, guarda *blind components* e *pRComponents* no ficheiro [requesterFile.txt](./E01/requesterFile.txt) e devolve o m' (*blind message*).


  - [deofusca-app.py](./E01/deofusca-app.py):  

    `desofusca-app.py -s <Blind Signature> -RDash <pRDashComponents>`  

    Recebe a *blind signature* e *pRDashComponents*, lê o *blindComponents* do ficheiro [requesterFile.txt](./E01/requesterFile.txt) e devolte s' (*signature*).  



- **Verificador:**  
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


# Exercício 3 - Protocolo SSH
## Pergunta P3.1
Nesta pergunta pretende-se utilizar o ssh-audit para efetuar testes aos sites fornecidos. As empresas escolhidas para a realização destes testes foram a AIRBUS e a ........... .
#### 1. Resultados do ssh-audit


- **AIRBUS:**
```
# general
(gen) banner: SSH-2.0-Cisco-1.25
(gen) software: Cisco IOS/PIX sshd 1.25
(gen) compatibility: OpenSSH 3.9-6.6, Dropbear SSH 0.53+
(gen) compression: disabled

# key exchange algorithms
(kex) diffie-hellman-group-exchange-sha1  -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [warn] using weak hashing algorithm
                                          `- [info] available since OpenSSH 2.3.0
(kex) diffie-hellman-group14-sha1         -- [warn] using weak hashing algorithm
                                          `- [info] available since OpenSSH 3.9, Dropbear SSH 0.53
(kex) diffie-hellman-group1-sha1          -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [fail] disabled (in client) since OpenSSH 7.0, logjam attack
                                          `- [warn] using small 1024-bit modulus
                                          `- [warn] using weak hashing algorithm
                                          `- [info] available since OpenSSH 2.3.0, Dropbear SSH 0.28

# host-key algorithms
(key) ssh-rsa                             -- [info] available since OpenSSH 2.5.0, Dropbear SSH 0.28

# encryption algorithms (ciphers)
(enc) aes128-ctr                          -- [info] available since OpenSSH 3.7, Dropbear SSH 0.52
(enc) aes192-ctr                          -- [info] available since OpenSSH 3.7
(enc) aes256-ctr                          -- [info] available since OpenSSH 3.7, Dropbear SSH 0.52
(enc) aes128-cbc                          -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [warn] using weak cipher mode
                                          `- [info] available since OpenSSH 2.3.0, Dropbear SSH 0.28
(enc) 3des-cbc                            -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [warn] using weak cipher
                                          `- [warn] using weak cipher mode
                                          `- [warn] using small 64-bit block size
                                          `- [info] available since OpenSSH 1.2.2, Dropbear SSH 0.28
(enc) aes192-cbc                          -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [warn] using weak cipher mode
                                          `- [info] available since OpenSSH 2.3.0
(enc) aes256-cbc                          -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [warn] using weak cipher mode
                                          `- [info] available since OpenSSH 2.3.0, Dropbear SSH 0.47

# message authentication code algorithms
(mac) hmac-sha1                           -- [warn] using encrypt-and-MAC mode
                                          `- [warn] using weak hashing algorithm
                                          `- [info] available since OpenSSH 2.1.0, Dropbear SSH 0.28
(mac) hmac-sha1-96                        -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                          `- [warn] disabled (in client) since OpenSSH 7.2, legacy algorithm
                                          `- [warn] using encrypt-and-MAC mode
                                          `- [warn] using weak hashing algorithm
                                          `- [info] available since OpenSSH 2.5.0, Dropbear SSH 0.47

# algorithm recommendations (for OpenSSH 3.9)
(rec) -diffie-hellman-group1-sha1         -- kex algorithm to remove 
(rec) -diffie-hellman-group-exchange-sha1 -- kex algorithm to remove 
(rec) -aes192-cbc                         -- enc algorithm to remove 
(rec) -aes128-cbc                         -- enc algorithm to remove 
(rec) -3des-cbc                           -- enc algorithm to remove 
(rec) -aes256-cbc                         -- enc algorithm to remove 
(rec) -hmac-sha1-96                       -- mac algorithm to remove 



```

- **ACCOR:**
```
# general
(gen) banner: SSH-2.0-lancom
(gen) compatibility: OpenSSH 7.2+ (some functionality from 6.6), Dropbear SSH 2013.62+ (some functionality from 0.52)
(gen) compression: enabled (zlib)

# key exchange algorithms
(kex) diffie-hellman-group14-sha1           -- [warn] using weak hashing algorithm
                                            `- [info] available since OpenSSH 3.9, Dropbear SSH 0.53
(kex) diffie-hellman-group1-sha1            -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [fail] disabled (in client) since OpenSSH 7.0, logjam attack
                                            `- [warn] using small 1024-bit modulus
                                            `- [warn] using weak hashing algorithm
                                            `- [info] available since OpenSSH 2.3.0, Dropbear SSH 0.28
(kex) diffie-hellman-group-exchange-sha1    -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [warn] using weak hashing algorithm
                                            `- [info] available since OpenSSH 2.3.0
(kex) diffie-hellman-group-exchange-sha256  -- [warn] using custom size modulus (possibly weak)
                                            `- [info] available since OpenSSH 4.4
(kex) ecdh-sha2-nistp256                    -- [fail] using weak elliptic curves
                                            `- [info] available since OpenSSH 5.7, Dropbear SSH 2013.62
(kex) ecdh-sha2-nistp384                    -- [fail] using weak elliptic curves
                                            `- [info] available since OpenSSH 5.7, Dropbear SSH 2013.62
(kex) ecdh-sha2-nistp521                    -- [fail] using weak elliptic curves
                                            `- [info] available since OpenSSH 5.7, Dropbear SSH 2013.62
(kex) curve25519-sha256@libssh.org          -- [info] available since OpenSSH 6.5, Dropbear SSH 2013.62
(kex) ext-info-s                            -- [warn] unknown algorithm

# host-key algorithms
(key) ecdsa-sha2-nistp256                   -- [fail] using weak elliptic curves
                                            `- [warn] using weak random number generator could reveal the key
                                            `- [info] available since OpenSSH 5.7, Dropbear SSH 2013.62
(key) ssh-dss                               -- [fail] removed (in server) and disabled (in client) since OpenSSH 7.0, weak algorithm
                                            `- [warn] using small 1024-bit modulus
                                            `- [warn] using weak random number generator could reveal the key
                                            `- [info] available since OpenSSH 2.1.0, Dropbear SSH 0.28
(key) rsa-sha2-512                          -- [info] available since OpenSSH 7.2
(key) rsa-sha2-256                          -- [info] available since OpenSSH 7.2
(key) ssh-rsa                               -- [info] available since OpenSSH 2.5.0, Dropbear SSH 0.28

# encryption algorithms (ciphers)
(enc) aes256-gcm@openssh.com                -- [info] available since OpenSSH 6.2
(enc) aes128-gcm@openssh.com                -- [info] available since OpenSSH 6.2
(enc) chacha20-poly1305@openssh.com         -- [info] available since OpenSSH 6.5
                                            `- [info] default cipher since OpenSSH 6.9.
(enc) aes256-ctr                            -- [info] available since OpenSSH 3.7, Dropbear SSH 0.52
(enc) aes192-ctr                            -- [info] available since OpenSSH 3.7
(enc) aes128-ctr                            -- [info] available since OpenSSH 3.7, Dropbear SSH 0.52
(enc) aes256-cbc                            -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [warn] using weak cipher mode
                                            `- [info] available since OpenSSH 2.3.0, Dropbear SSH 0.47
(enc) aes192-cbc                            -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [warn] using weak cipher mode
                                            `- [info] available since OpenSSH 2.3.0
(enc) aes128-cbc                            -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [warn] using weak cipher mode
                                            `- [info] available since OpenSSH 2.3.0, Dropbear SSH 0.28
(enc) blowfish-ctr                          -- [warn] unknown algorithm
(enc) blowfish-cbc                          -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [fail] disabled since Dropbear SSH 0.53
                                            `- [warn] disabled (in client) since OpenSSH 7.2, legacy algorithm
                                            `- [warn] using weak cipher mode
                                            `- [warn] using small 64-bit block size
                                            `- [info] available since OpenSSH 1.2.2, Dropbear SSH 0.28
(enc) 3des-ctr                              -- [info] available since Dropbear SSH 0.52
(enc) 3des-cbc                              -- [fail] removed (in server) since OpenSSH 6.7, unsafe algorithm
                                            `- [warn] using weak cipher
                                            `- [warn] using weak cipher mode
                                            `- [warn] using small 64-bit block size
                                            `- [info] available since OpenSSH 1.2.2, Dropbear SSH 0.28

# message authentication code algorithms
(mac) hmac-sha2-512                         -- [warn] using encrypt-and-MAC mode
                                            `- [info] available since OpenSSH 5.9, Dropbear SSH 2013.56
(mac) hmac-sha2-256                         -- [warn] using encrypt-and-MAC mode
                                            `- [info] available since OpenSSH 5.9, Dropbear SSH 2013.56
(mac) hmac-sha1                             -- [warn] using encrypt-and-MAC mode
                                            `- [warn] using weak hashing algorithm
                                            `- [info] available since OpenSSH 2.1.0, Dropbear SSH 0.28

# algorithm recommendations (for OpenSSH 7.2)
(rec) -diffie-hellman-group14-sha1          -- kex algorithm to remove 
(rec) -diffie-hellman-group-exchange-sha1   -- kex algorithm to remove 
(rec) -diffie-hellman-group1-sha1           -- kex algorithm to remove 
(rec) -ecdh-sha2-nistp256                   -- kex algorithm to remove 
(rec) -ecdh-sha2-nistp521                   -- kex algorithm to remove 
(rec) -ecdh-sha2-nistp384                   -- kex algorithm to remove 
(rec) -ecdsa-sha2-nistp256                  -- key algorithm to remove 
(rec) -ssh-dss                              -- key algorithm to remove 
(rec) +ssh-ed25519                          -- key algorithm to append 
(rec) -aes192-cbc                           -- enc algorithm to remove 
(rec) -aes128-cbc                           -- enc algorithm to remove 
(rec) -blowfish-cbc                         -- enc algorithm to remove 
(rec) -3des-cbc                             -- enc algorithm to remove 
(rec) -aes256-cbc                           -- enc algorithm to remove 
(rec) -hmac-sha2-512                        -- mac algorithm to remove 
(rec) -hmac-sha1                            -- mac algorithm to remove 
(rec) -hmac-sha2-256                        -- mac algorithm to remove 
(rec) +hmac-sha2-256-etm@openssh.com        -- mac algorithm to append 
(rec) +hmac-sha2-512-etm@openssh.com        -- mac algorithm to append 
(rec) +umac-128-etm@openssh.com             -- mac algorithm to append 


```
#### 2. Software e versão utilizada pelos servidores ssh
AIRBUS: Software: Cisco IOS/PIX sshd
        Versão: 1.25
        
ACCOR: Software: OpenSSH
       Versão: 7.2+


