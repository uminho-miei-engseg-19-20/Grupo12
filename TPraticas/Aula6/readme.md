## Pergunta 1.1 ##   
(*Privacy and Data Protection by Design – from policy to engineering*)  

As oito estratégias de *privacy design* foram separada em duas categorias: **Estratégias Orientadas aos dados** e **Estratégias Orientadas aos processos**.  
&nbsp;

**Estratégias Orientadas ao dado:**  

**Minimise:**  
Considerada a estratégia de design de privacidade mais básica, *Minimise* estabelece que a quantidade de dados pessoais processados deve ser a mínima possível. Ao aplicar esta estratégia, assegura-se que os dados pessoais processados são realmente necessários e se não há uma alternativa menos invasiva de se alcançar os mesmos resultados. Os padrões de design comuns que são implementados à esta estratégia são *"select before you collect"* e *"anonymisation and use pseudonyms"*.  

**Hide:**  
A estratégia *Hide* estabelece que todos os dados pessoais e as relações entre eles devem ser mantidas "escondidas", pois assim, os dados não podem ser facilmente violados. Esta estratégia não especifica diretamente que dados devem ser escondidos, pois isso depende do contexto específico no qual a estratégia é aplicada. Alguns dos padrões de design que pertencem a estratégia *Hide* podem ser o uso de cifragem dos dados e o mix de redes para esconder os padrões de tráfego, além dos *"anonymisation and use pseudonyms"* citados no *Minimise.  

**Separate:**  
A estratégia *Separate* estabelece que dados pessoais devem ser armazenados e processados de maneira distribuída e, sempre que possível, localmente. As base de dados devem ser divididas e deve ser muito difícil fazer conexões entre as tabelas e linhas dessas bases. Separandos os dados, garante-se que um perfil completo de um indivíduo não será criado facilmente. Não há padrões de design conhecidos para esta estratégia.  

**Aggregate:**  
Esta estratégia determina que os dados pessoais devem ser processados com um nível alto de agregação e a menor quantidade de detalhes possível, onde os dados devem ser agregados por grupos de atributos ou grupos de indivíduos, restringindo a quantidade de detalhes de dados pessoais utilizados. Quanto mais generalizados são as informações armazenadas, menor são as chances de se atribuir estas informações à um indivíduo específico. Os padrões de design que pertencem à esta estratégia são *aggregation over time*, *dynamic location granularity*, *k-anonymity*, *differential privacy* e outras técnicas de anonimização.  
&nbsp;

**Estratégias Orientadas ao processo:**  

**Inform:**  
A estratégia *inform* corresponde à noção mais importante de transparência e recomenda que sempre que os titulares dos dados utilizem um sistema, eles devem ser comunicados sobre que dados pessoais estão a ser processados, além de terem acesso aos seus direitos de acesso aos dados. Os titulares também devem ser informados sobre terceiros com os quais as suas informações são compartilhadas. Um possível padrão de design para essa categoria é a *Platform for Privacy Preferences* e *Data Breach Notifications*.  

**Control:**  
Esta estratégia, que é uma contrapartida à estratégia *inform*, estabelece que os titulares dos dados devem ter a capacidade de controlar quais das suas irformações serão processadas. Ou seja, os titulares devem poder ver, atualizar e até mesmo, apagar os seus dados armazenados. *User centric identity managemant* e *end-to-end encryption* são padrões de design associados à essa estratégia.  

**Enforce:**  
A estratégia *enforce* afirma que deve existir uma política de privacidade compatível com os requisitos legais e que esta política deve ser devidamente aplicada e que mecanismos de proteção de dados devem existir para previnir violações da política de privacidade. Além disso, deve ser estabelecida uma estrutura de gestão apropriada para garantir que a política seja cumprida. Controlo de acesso e políticas de direitos de privacidade são exemplos de padrões de design associados à essa estratégia.  

**Demonstrate:**  
A última estratégia, *demostrate*, define que um controlador de dados deve seja capaz de demonstrar conformidade com as políticas de privacidade e com todos os requisitos legais aplicáveis. Esta estratégia é um passo a frente da *enforce*, já que requer que o controlador prove que realmente está no comando. O controlador deve também ser capaz de demonstrar como a política de privacidade é implementada dentro do sistema e em caso de problemas, ele deverá ser capaz de determinar a extensão de possíveis violações da privacidade. Exemplos de padrões de design que implementam esta estratégia são *privacy managemant systems* e o uso de auditoria e *logging*.  
&nbsp;

## Pergunta 1.2 ##  

**Suppliers of services and goods**  

Este caso de uso descreve a aquisição de produtos e serviços necessários para a operação diária e para venda de mercadorias de uma PME de varejo que oferece produtos por meio de uma loja virtual. Esses procedimentos podem incluir, em certos casos, o processamento de dados pessoais, como por exemplo, informações de contacto de funcionários das empresas fornecedoras e informações financeiras de pessoas que têm contrato direto com a PME. A operação de processamento desses dados tem suporte de um sistema de TI conectado ao sistema ERP (*Enterprise Resource Planning*) e ao sistema de contabilidade.  

Dentro dos dados pessoais processados estão os nomes das empresas e detalhes de contacto, informações financeiras como número de conta bancária e número de identificação fiscal, além de fotos e credenciais de acesso dos funcionários. Os pagamentos são realizados através de uso de serviços bancários remotos, os *invoices* e faturas de entregas são carregados em lotes durante a noite através de uma plataforma offline e as comunicações administrativas com os fornecedores são feitas por meio de serviço de email comum.  

Tal como concluído na avaliação de risco, o probabilidade geral de acontecer algum caso de risco, é baixa, com isto as medidas de mitigação propostas são as seguintes:  

* Documentar todos os cargos da organização e o nivel de acesso destes na infrastutura.
* Documentar os niveis de acesso de terceiros aos dados.
* Em caso de alteração dos niveis de acesso de um dado cargo, esta mudança deve ser igualmente documentada.
* Ter uma politica de acesso a dados restrita, e apenas permitir cargos com maior responsabilidade maior liberdade de acesso.
* Ter garantias de segurança, em termos de infrastrutura, não são em termos de estrutura de rede e de sitemas, mas também cada utilizador deve ter a sua conta pessoal, e utilizar uma palavra-chave forte.
* Ter documentado um plano de ação em caso de possíveis incidentes.
* Ter documentado um plano de reestruturação no caso de incidentes.

De notar que estas medidas são baseadas no anexo A.1 do documento, com isto outras medidas de mitigação poderiam ser aplicadas, mas estas achamos serem as mais adequadas tendo em conta o anexo anteriormente referido.


