**Pergunta 1.1**  
(*Privacy and Data Protection by Design – from policy to engineering*)  

As oito estratégias de *privacy design* foram separada em duas categorias: **Estratégias Orientadas aos dados** e **Estratégias Orientadas aos processos**.  


**Estratégias Orientadas ao dado:**  
\\
**Minimise:**  
Considerada a estratégia de design de privacidade mais básica, *Minimise* estabelece que a quantidade de dados pessoais processados deve ser a mínima possível. Ao aplicar esta estratégia, assegura-se que os dados pessoais processados são realmente necessários e se não há uma alternativa menos invasiva de se alcançar os mesmos resultados. Os padrões de design comuns que são implementados à esta estratégia são *"select before you collect"* e *"anonymisation and use pseudonyms"*.  

**Hide:**  
A estratégia *Hide* estabelece que todos os dados pessoais e as relações entre eles devem ser mantidos mantidas "escondidas", pois assim, os dados não podem ser facilmente violados. Esta estratégia não especifica diretamente quais dados devem ser escondidos, pois isso depende do contexto específico no qual a estratégia é aplicada. Alguns dos padrões de design que pertencem a estratégia *Hide* podem ser o uso de cifragem dos dados, o mix de redes, para esconder os padrões de tráfego, além dos *"anonymisation and use pseudonyms"* citados no *Minimise.


**Separate:**  
A estratégia *Separate* estabelece que dados pessoais devem ser armazenados e processados de maneira distribuída e, sempre que possível, localmente. Base de dados devem ser divididas e deve ser muito difícil fazer conexões entre as tabelas e linhas dessas bases. Separandos os dados, garante-se que um perfil completo de um idivíduo não será criado facilmente. Não há padrões de design conhecidos para esta estratégia.

**Aggregate:**  
Esta estratégia determina que os dados pessoais devem ser processados com um nível alto de agregação e a menor quantidade de detalhes possível, onde os dados devem ser agregados por grupos de atributos ou grupos de indivíduos, restringindo a quantidade de detalhes de dados pessoais utilizados. Quanto mais generalizados são as informações armazenadas, menor são as chances de se atribuir estas informações à um indivíduo específico. Os padrões de design que pertencem à esta estratégia são *aggregation over time*, *dynamic location granularity*, *k-anonymity*, *differential privacy* e outras técnicas de anonimização.  

**Estratégias Orientadas ao processo:**  

**Inform:**  
A estratégia *inform* corresponde à noção mais importante de transparência e recomenda que sempre que os titulares dos dados utilizem um sistema, eles devem ser comunicados sobre quais dados pessoais estão sendo processados, além de terem acesso aos seus direitos de acesso aos dados. Os titulares também devem ser informados sobre terceiros com os quais suas informações são compartilhadas. Um possível padrão de design para essa categoria é a *Platform for Privacy Preferences* e *Data Breach Notifications*.


**Control:**  
Esta estratégia, que é uma contrapartida à estratégia *inform*, estabelece que os titulares dos dados devem ter a capacidade de controlar quais das suas irformações serão processadas. Ou seja, os titulares devem poder ver, atualizar e até mesmo, apagar os seus dados armazenados. *User centric identity managemant* e *end-to-end encryption* são padrões de design associados à essa estratégia.


**Enforce:**  
A estratégia *enforce* afirma que deve existir uma política de privacidade compatível com os requisitos legais e que esta política deve ser devidamente aplicada e mecanismos de proteção de dados devem existir para previnir violações da política de privacidade. Além disso, deve ser estabelecida uma estrutura de gestão apropriada para garantir que a política seja cumprida. Controlo de acesso e políticas de direitos de privacidade são exemplos de padrões de design associados à essa estratégia.


**Demonstrate:**  
A última estratégia, *demostrate*, define que um controlador de dados deve seja capaz de demonstrar conformidade com as políticas de privacidade e com todos os requisitos legais aplicáveis. Esta estratégia é um passo a frente da *enforce*, já que requer que o controlador prove que realmente está no comando. O controlador deve também ser capaz de demonstrar como a política de privacidade é implementada dentro do sistema e em caso de problemas, ele deverá ser capaz de determinar a extensão de possíveis violações da privacidade. Exemplos de padrões de design que implementam esta estratégia são *privacy managemant systems* e o uso de auditoria e *logging*.