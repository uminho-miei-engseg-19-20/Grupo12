 ## Pergunta 1.1 ##
 #### 1. Existem pelo menos dois tipos de vulnerabilidades estudadas na aula teórica de "Validação de Input" que podem ser exploradas. Identifique-as. ####
As duas vulnerabildades presentes neste código são relativas a utilização da função `system` e ao fato do programa não controlar os tipos de metacaracteres que podem ser aceites.  
A função `system` utilizada executa a variável `buf` como argumento, com as variáveis de ambiente do processo-pai, sendo assim, um atacante poderia utilizar esta função para correr comandos não autorizados.  
Com relação à validação dos metacaracteres, um atacante poderia explorar esta falha através de uma injeção de separadores de pastas, conhecido como *path traversal attack* e então, com a ajuda do *autocomplete*, tomar conhecimento de toda estrutura de diretórios do sistema.


#### 2. Forneça o código/passos/linha de comando que permitem explorar cada uma das vulnerabilidades identificadas na linha anterior. ####
Para explorar a vulnerabilidade referente à função `system`, poderíamos recorrer ao código:

`./filetype nome_do_ficheiro; comando_executado_pelo_atacante`

Onde o atacante poderia utilizar o programa para correr comandos não autorizados.

Já para explorar a vulnerabilidade referente à não validação dos metacaracteres inseridos, o atacante poderia ter acesso aos diretórios, por exemplo, através do comando:

`./filetype /etc/passwd`

Além disto, o invasor poderia utilizar o *autocomplete* (através da tecla TAB) e ter acesso aos nomes dos ficheiros e diretórios de toda a estrutura.

#### 3. O que aconteceria se o seu programa tivesse permissões setuid root? ####
Caso o programa vulnerável tenha permissões setuid root, o atacante poderia facilmente correr comandos para ter acesso à informações sensíveis, como por exemplo, através do comando: `./filetype nome_do_ficheiro; cat /etc/passwd` ou `./filetype nome_do_ficheiro; chmod 777 nome_do_ficheiro`.

 ## Pergunta 1.2 ##
 
Para a realização deste trabalho, optamos pelo Python porque para garantir que todos os inputs são *strings*, utilizamos a função `str()` porém, a função `input()` do Python já recebe os valores inseridos como *string*.

As validações dos inputs foram feitas da seguinte forma:

+ **nome** (`valida_nome()`): Verifica se o valor é composto apenas por letras com o método `isalpha()` e limita o tamanho da string (de 3 a 64 caracteres).

+ **número de identificação fiscal (NIF) e número de identificação de cidadão (NIC)** (`valida_nif_nic()`): Verifica se o valor é composto apenas por digitos e verifica também se o tamanho é o correto (9 dígitos para o NIF e 8 para o NIC). Feito isto, implementamos o algoritmo módulo 11 para validar o dígito verificador.

+ **data de nascimento** (`valida_data()`): Primeiramente, dividimos a data inserida e caso o *input* não for válido, a função irá retornar falso. Além disso, a função utiliza o médoto `datetime` do Python para verificar se a data inserida é válida e confere se a data é menor que a data atual.

+ **valor a pagar**(`valida_valor()`): Utiliza expressões regulares para validar o *input* além de verificar o tamanho máximo.

+ **numero de cartão de crédito** (`valida_cartao()`): Primeiramente a função verifica se o tamanho do número inserido é válido e se o *input* é composto apenas por números. Feito isto, valida o número do cartão de crédito utilizando o algoritmo de Luhn.

+ **validade do cartão** (`exp_cartao()`): Funciona de maneira semelhante à função `valida_data()` porém, utilizamos o método `monthrange()` do módulo `calendar` para verificar a quantidade de dias do mês inserido.

+ **CVC/CVV** (`valida_cvc()`): Verifica se o valor inserido é composto apenas por números e se o tamanho é 3 ou 4.

A função `vefiry()` é utilizada para chamar as funções citadas acima em um ciclo que só termina se os valores inseridos forem validados. 
