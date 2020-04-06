## Pergunta 1.1 ##

**1.**  
**1.1. Improper Restriction of Operations within the Bounds of a Memory Buffer**  

Essa classe de vulnerabilidade se aproveita do fato de algumas linguagens permitirem o endereçamento direto dos locais da memória e não garantirem automaticamente que esses locais sejam válidos para o buffer de memória que está sendo referenciado, podendo isto possibilitar que operações de leitura ou gravação sejam executadas em locais de memória que podem estar associados a outras variáveis, estruturas de dados ou dados internos do programa. Como resultado, um atacante poderá ser capaz de executar cógidos arbitrários, alterar o controle de fluxo, ter acesso à informações sensíveis ou causar a falha do sistema.

Por permitir o endereçamento direto de memória, as linguagens **C** e **C++** são as mais afetadas por essa *Weakness*.  

Se o atacante conseguir executar códigos ou comandos não autorizados e modificar a memória, comprometeria a **Integridade**, **Confidencialidade** e a **Disponibilidade** do sistema. Caso o atacante consiga ler a memória e causar um ataque DoS, comprometeria a **Disponibilidade** e a **Confidencialidade** do sistema
e se caso o atacante tiver sucesso em ler a memória, ele poderia ter acesso à informações sensíveis, comprometendo a **Confidencialidade** do sistema.  


**1.2. Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')**  

Essa classe de vulnerabilidade, também conhecida como XSS, ocorre quando:

1. Dados não confiáveis são inseridos em uma aplicação web, tipicamente através de uma *web request*;
2. A aplicação web gera, dinamicamente, uma página web que contém dados confiáveis;
3. Durante a geração da página, a aplicação não impede que os dados contenham conteúdos executáveis por um navegador web, como JavaScript, tags ou atributos HTML, *mouse events*, Flash, ActiveX, etc;
4. Uma vítima acessa a página por meio de um navegador que contenha scritps mal-intencionados injetados usando dados não confiáveis;
5. Como o script vem de uma página enviada pelo servidor, o navegador da vítima executa o script mal-intencionado no contexto do domínio do servidor.
6. Isso acaba por violar efetivamente a política de *"same-origin"* do navegador, que afirma que os scripts de um domínio não devem capazes de acessar recursos ou executar códigos em um domínio diferente.


Segundo o site do CWE, existem três tipos principais de XSS:


* ***Reflected XSS* (ou *Non-Persistent*):**  
O servidor ler os dados diretamente do *request* HTTP e os reflete de volta em um *response* HTTP. Esse tipo de XSS ocorre quando um atacante induz a vítima a fornecer conteúdos maliciosos para uma aplicação Web vulnerável, que é refletido de volta à vítima e executado pelo navegador.

* ***Stored XSS* (ou *Persistent*):**  
A aplicação armazena dados maliciosos em base de dados, fóruns, log de visitantes ou em qualquer outra forma de armazenamento confiável do lado do servidor. Posteriormente, esses dados maliciosos são lidos de volta pela aplicação e incluídos no conteúdo dinâmico.


* ***DOM-Based XSS*:**  
No *DOM-based XSS*, o cliente realiza uma injeção de XSS dentro da página, diferentemente dos outros tipos de XSS onde o servidor realiza a injeção. 


Uma vez que o script malicioso é injetado, o atacante pode realizar várias atividades maliciosas como, transferir informações privadas ou enviar *requests* maliciosos para o site se passando pela vítima. O invasor pode também, realizar ataques de *Phishing* que seriam usados para emular websites confiáveis e induzir a vítima à digitar suas credenciais, permitindo que o atacante comprometa a conta da vítima no website em questão. Finalmente, o script pode explorar alguma vulnerabilidade do navegador e possivelmente tomar o controle do computador da vítima.  

A prevalência dessa classe de vulnerabilidades independe de linguagens, mas é mais predominante em tecnologias *Web Based*.  

Caso o atacante tenha sucesso em ultrapassar o mecanismo de proteção e ter acesso à dados de aplicação, esse ataque comprometeria o **controlo de acesso** e a **Confidencialidade** do sistema. Se o atacante conseguir executar códigos ou comandos não autorizados, comprometeria a **Integridade**, **Confidencialidade** e a **Disponibilidade** do sistema. Caso o atacante consiga além de executar códigos ou comandos, ultrapassar o mecanismo de proteção e ter acesso à dados de aplicação, comprometeria a **Confidencialidade**, a **Integridade**, a **Disponibilidade** e o **controlo de acesso** do sistema.  

**1.3. Improper Input Validation**  
Quando o software não realiza a validação do *input* apropriadamente, um atacate pode criar um *input* malicioso de uma forma não esperada pelo resto da aplicação, resultando em um fluxo de controle alterado, controle arbitrário de algum recurso ou até mesmo a execução de algum código malicioso.
&nbsp;
A prevalência dessa classe de vulnerabilidades independe de linguagens.  

**2.**  
**Incorrect Permission Assignment for Critical Resource**  

Essa classe de vulnerabilidade ocorre quando um recurso recebe uma configuração de permissões que fornece acesso à uma gama de atores maior que o necessário, podendo levar à uma exposição de informações sensíveis ou à modificação desse recurso por partes não autorizadas. Isso é especialmente perigoso quando o recurso está relacionado à configurações do programa, execução ou à dados pessoais confidenciais.  

Essa classe de vulnerabilidades é aplicável independentemente de linguagens e tecnologias.  


## Pergunta 1.2 ##  
O limite superior de bugs que se pode encontrar por 1000 linhas de código é 50 e o limite inferior é 5.
Assim, no facebook o número máximo de bugs que podemos encontrar é de 3100000 e o mínimo será 310000. Quanto ao software de automóveis o limite superior de bugs será de 5000000 e o limite inferior de 500000. No Linux 3.1 o número de bugs máximo é de 750000 e o mínimo de 75000. Por fim, nos serviços de internet da Google o limite superior de bugs esperado é de 100000000 e o inferior de 10000000. (A fazer o numero de vulnerabilidades)
## Pergunta 1.3 ##  
#### Vulnerabilidades de Projecto ####
**CWE-73: External Control of File Name or Path**: O software permite, que o input do utilizador, possa controlar ou influenciar ficheiros usados em operações do sistema ficheiros, isto permite que o atacante aceda ou modifique ficheiros dos sistema ou outros ficheiros crucais à aplicação.Este tipo de erro, ocorre quando duas situações se sucedem:
1. Um atacante consegue especificar um caminho utilizado numa operação do sistema de ficheiros.
2. Após o primeiro passo, o atacante ganha capacidades que antes não era permitidas.
O progama pode permitir assim ao atacante alterar um ficheiro especifico, ou correr uma configuração controlada por este.
Existem diferentes formas de mitigação, a menos aconselhalável, mas provavelmente a mais simples, se possível em certos cenários, o caminho utilizado ser estático e não dinâmico. Outra solução pode passar por correr este código numa ambiente controlado com restrições entre o processo e o sistema operativo, de forma a que não permita aceder a ficheiros fora do directório, outra estratégia passa por estratégias de validação de input, no qual o input só é aceite se corresponder determinadas regras, entre outras. 

**CWE-91: XML Injection (aka Blind XPath Injection)**: Isto acontece quando o software, não controla propriamente caracteres especiais que são usados em XML, isto permite que atacantes modifiquem a sintaxe, e o conteúdo do documento XML antes de este ser processado pelo sistema final, isto pode permitir ao atacante adicionar novos conteúdos ao xml ou modificar a syntax do xml. Medidas de mitigação passam por utilização de estratégias de validação de input no qual podem apenas é aceite inputs com determinadas regras impostas.
#### Vulnerabilidades de Codificação ####
**CWE-211: Externally-Generated Error Message Containing Sensitive Information**: A aplicação efetua uma operação que origina uma expção que causa uma mensagem de erro, ou um diagnóstico do sistema que não é diretamente controlado pela aplicação, o que pode levar à divulgação de informação sensível por parte do sistema. O atacante pode utilizar esta informação sensível por parte da aplicação para efetaur eventuais ataques. Esta vulnerabilidade pode ser mitigada de várias formas, o sistema onde esta foi implemetnada deve ser configuardo de forma a que prevenir que erros como este sejam gerados, o ambiente não deve permitir que mensagens deste tipo sejam demonstradas no produto final,  devem ser também usadas expções de forma a controlar eventuais erros como este.

**Exemplo 2: CWE-112: Missing XML Validation**: O software aceita a utilização de xml de uma fonte não seguras, e não valida o esquema deste, isto permite a que atacantes possam utilizar codigo malicioso, sem este ser verificado. A medida de mitigação para este tipo de  situação é bastante simples, passa por validar sempre o esquema de XML seja através de XML Schema or DTD.

#### Vulnerabilidade operacional ####
**CWE-7: J2EE Misconfiguration: Missing Custom Error Page**: Isto acontece quando não é configurada a página de Erro Default de uma aplicação Web, e está expõe informação sensível. Um atacante pode usar a informação exposta pela página não tratada e usar em proveito para procurar vulnerabildiades no sistema. A medida de mitigação é simples de aplicar, basta definir as tratar dos erros na implementação, e nas configurações da aplicação esta deve definir as informações a serem expostas pela página de erro, de modo a garantir que não expõe informação sensível.

**CWE-319: Cleartext Transmission of Sensitive Information**: Esta vulnerabilidade ocorre quando o softoware transmite informação dados sensíveis em texto limpo nos canais de comunicação, estes canais podem ter atacantes a "escutar" as comunicações e se a informação que é passada nestes não for protegida, o risco de um possível ataque é cada vez maior.
As medidas de mitigação desta vulnerabilidade passam por, desde encriptar com um bom algoritmo todos os dados antes de estes serem transmitidos, configurar os servidores para usar canais encriptados, que podem usar protocolos como ssl ou tls entre outros.


## Pergunta 1.4 ##
A diferença entre uma vulnerabilidade de dia-0 e outra vulnerabilidade de codificação é que as de dia-0 apenas são conhecidas por um grupo/meio restrito e não por toda a comunidade de segurança informática (equipas competentes e com bons conhecimentos de segurança). Até que esta vulnerabilidade seja do conhecimento da comunidade de segurança informática, os atacantes podem explorá-la afetando, por exemplo, o programa, os dados ou uma rede. Este tipo de ataques é denominado de ataques dia-zero. 

