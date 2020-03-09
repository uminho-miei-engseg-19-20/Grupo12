# Exercício 1 - TOR (The Onion Router)  
## Pergunta 1.1  
Não é possível garantir que o utilizador conseguirá um IP localizado nos EUA ao efetuar o comando `sudo anonsurf start`, pois para garantir o anonimato ao acessar um site utilizando a rede TOR, o utilizador obtém uma lista dos nós da rede Tor fornecida pelo *Onion Router* que atua como *Directory Server* e feito isto, o utilizador escolhe aleatoriamente uma série de nós para chegar ao servidor de destino. Desta forma, o caminho percorrido para acessar o site sempre será definido aleatóriamente e o *Onion Proxy* (OP) não tem nenhum controlo sobre quais são os *Onion Routers* que constituem o circuito.  

## Pergunta 1.2  

**1 -** O site The Hidden Wiki (http://zqktlwi4fecvo6ri.onion/wiki/index.php/Main_Page) foi escolhido para verificar o circuito estabelecido.  

A imagem abaixo mostra que o circuito vai da OP para um OR localizado na Alemanha, passando por outro OR no Canadá e em seguida passa por um OR na França. Logo após, o circuito passa por 3 saltos *relays* até chegar ao site pretendido.

![The Hidden Wiki](./img/the_hidden_wiki.png)
