## Pergunta 1.1 ##

## Pergunta 1.2 ##
Após analisar e testar os programas RootExploit.c e 0-simple.c, foi encontrada vulnerabilidade **Stack Buffer Overflow**, devido ao fato do *input* recebido pela função `gets` não efetua uma verificação de tamanho.

No programa RootExploit, duas variáveis são declaradas logo no início:
`pass`: inteiro, inicializada com o valor 0 e de 4 bytes;
`buff`: array de caracteres de 4 bytes.

Os valores são colocados na *stack* que cresce dos endereços mais altos da memória para os mais baixos, porém a escrita é feita ao contrário e a indexação é dos endereços mais baixos para os mais altos, sendo assim, no programa RootExploit, os 4 bytes da variável `pass` são alocados primeiro e em seguida, são alocados os 4 bytes da variável `buff`. 

Neste programa, ao inserir 5 caracteres no *input* (variável `buff`), esse quinto caracter irá ultrapassar o espaço de memória reservado para a variável e será escrito na variável `pass` que está alocada logo após. Se o valor desse caracter for diferente de 0, o programa reconhecerá a `pass` como verdadeira e validará a condição onde só deveria ser validade se o *input* correspondesse à *password* correta.

Já no programa 0-simple.c, as variáveis são:
`control`: inteiro declarada mas nao inicializada;
`buffer`: array de 64 bytes.

Diferente do  RootExploit.c, para explorar o *Stack Buffer Overflow* neste programa é necessário inserir um *input* com no mínimo 65 bytes (77 caracteres) para transbordar o espaço de memória reservado para a variável `buffer`. Assim como no programa anterior, se o byte extra for diferente de zero, o programar escreverá o valor deste byte no endereço de memória da variável `control`, a validará e o programa retornará "YOU WIN!!!"
## Pergunta 1.3 ##
## Pergunta 1.4 ##
## Pergunta 1.5 ##
## Pergunta 1.6 ##
