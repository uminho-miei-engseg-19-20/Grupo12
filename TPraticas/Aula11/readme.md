 ## Pergunta 1.1 ##
 #### 1. Existem pelo menos dois tipos de vulnerabilidades estudadas na aula teórica de "Validação de Input" que podem ser exploradas. Identifique-as. ####

#### 2. Forneça o código/passos/linha de comando que permitem explorar cada uma das vulnerabilidades identificadas na linha anterior. ####

#### 3. O que aconteceria se o seu programa tivesse permissões setuid root? ####

 ## Pergunta 1.2 ##
 
Para a realização deste trabalho, optamos pelo Python onde para garantir que todos os *inputs* fossem *strings*, utilizamos a função `str()` porém, a função `input()` do Python já recebe os valores inseridos como *string*.

A validação dos inputs foram feitas da seguinte forma:

+ nome
+ número de identificação fiscal (NIF)
+ número de identificação de cidadão (NIC)
+ data de nascimento
+ valor a pagar
+ numero de cartão de crédito
+ validade do cartão
+ CVC/CVV
