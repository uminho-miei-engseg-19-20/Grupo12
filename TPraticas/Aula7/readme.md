## Pergunta 1.1 ##

**1.**  
**1.1. CWE-119: Improper Restriction of Operations within the Bounds of a Memory Buffer**  

Essa classe de vulnerabilidade se aproveita do fato de algumas linguagens permitirem o endereçamento direto dos locais da memória e não garantirem automaticamente que esses locais sejam válidos para o buffer de memória que está sendo referenciado, podendo isto possibilitar que operações de leitura ou gravação sejam executadas em locais de memória que podem estar associados a outras variáveis, estruturas de dados ou dados internos do programa. Como resultado, um atacante poderá ser capaz de executar cógidos arbitrários, alterar o controle de fluxo, ter acesso à informações sensíveis ou causar a falha do sistema.

Por permitir o endereçamento direto de memória, as linguagens **C** e **C++** são as mais afetadas por essa *Weakness*.  

Se o atacante conseguir executar códigos ou comandos não autorizados, causar um *buffer overflow* e modificar a memória, comprometeria a **Integridade**, **Confidencialidade** e a **Disponibilidade** do sistema. Caso o atacante consiga ler a memória e causar um ataque DoS, comprometeria a **Disponibilidade** e a **Confidencialidade** do sistema
e se caso o atacante tiver sucesso em ler a memória, ele poderia ter acesso à informações sensíveis, comprometendo a **Confidencialidade** do sistema.  


**1.2. CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')**  

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

**1.3. CWE-20: Improper Input Validation**  
Quando o software não realiza a validação do *input* apropriadamente, um atacate pode criar um *input* malicioso de uma forma não esperada pelo resto da aplicação, resultando em um fluxo de controle alterado, controle arbitrário de algum recurso ou até mesmo a execução de algum código malicioso.
&nbsp;
A prevalência dessa classe de vulnerabilidades independe de linguagens.  

Uma consequência comum dessa classe de vulnerabilidade é um ataque DoS, que comprometeria a **Disponibilidade** do sistema. Caso o atacante possa ler a memória e ficheiros ou directórios, a **Confidencialidade** do sistema seria afetada. Caso um atacante usasse um *input* malicioso para modificar dados ou alterar o fluxo de controle, ele comprometeria a **Integridade**, a **Confidencialidade** e a **Disponibilidade** do sistema.  


**2.**  
**CWE-732: Incorrect Permission Assignment for Critical Resource**  

Essa classe de vulnerabilidade ocorre quando um recurso recebe uma configuração de permissões que fornece acesso à uma gama de atores maior que o necessário, podendo levar à uma exposição de informações sensíveis ou à modificação desse recurso por partes não autorizadas. Isso é especialmente perigoso quando o recurso está relacionado à configurações do programa, execução ou à dados pessoais confidenciais.  

Essa classe de vulnerabilidades é aplicável independentemente de linguagens e tecnologias.  

Um atacante pode ser capaz de ler informações sensíveis como credenciais ou informações de configuração armazenadas em um ficheiro e comprometer a **Confidencialidade** do sistema. Através dessa *weakness*, um atacante também pode modificar propriedades críticas para ganhar privilégios e comprometer o **controlo de acesso** do sistema. Além disso, um invasor pode ser capaz de destruir ou corromper dados sensíveis, apagar registos de uma base de dados, comprometendo a **Integridade** do sistema.  

A vulnerabilidade **CVE-2020-8768** é a CVE mais recente da classe **CWE-732** e foi encontrada em alguns controladores da empresa Phoenix Contact. Nesses dispositivos vulneráveis existe um mecanismo inseguro para acesso de leitura e gravação à configuração do controlador. Esse mecanismo pode ser descoberto examinando um link no website do dispositivo. Essa vulnerabilidade, segundo a base de dados do NIST, é considerada crítica, não requer privilégios, pode ser explorada remotamente e não necessita de intereção do usuário para ser explorada. Além disso, ao ser explorada, essa falha compromete completamente a **Integridade** e a **Disponibilidade** do sistema.  


## Pergunta 1.2 ##  
O limite superior de bugs que se pode encontrar por 1000 linhas de código é 50 e o limite inferior é 5.
Assim, no facebook o número máximo de bugs que podemos encontrar é de 3100000 e o mínimo será 310000. Quanto ao software de automóveis o limite superior de bugs será de 5000000 e o limite inferior de 500000. No Linux 3.1 o número de bugs máximo é de 750000 e o mínimo de 75000. Por fim, nos serviços de internet da Google o limite superior de bugs esperado é de 100000000 e o inferior de 10000000. (A fazer o numero de vulnerabilidades)
## Pergunta 1.3 ##  
## Pergunta 1.4 ##
A diferença entre uma vulnerabilidade de dia-0 e outra vulnerabilidade de codificação é que as de dia-0 apenas são conhecidas por um grupo/meio restrito e não por toda a comunidade de segurança informática (equipas competentes e com bons conhecimentos de segurança). Até que esta vulnerabilidade seja do conhecimento da comunidade de segurança informática, os atacantes podem explorá-la afetando, por exemplo, o programa, os dados ou uma rede. Este tipo de ataques é denominado de ataques dia-zero. 

