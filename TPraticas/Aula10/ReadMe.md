## Pergunta 1.1 ##

#### 1. Qual a vulnerabilidade que existe na função vulneravel() e quais os efeitos da mesma? ####
A vulnerabilidade que existe na função vulneravel() no código overflow.c, é a  vulnerabilidade de Integer Overflow ou Wraparound, pode ser aqui consultada  em maior detalhe https://cwe.mitre.org/data/definitions/190.html.
Esta vulnerabilidade ocorre quando o valor de um inteiro é demasiado grande para o valor origninal alocado, o que pode levar a este valor tornar-se extramamente pequeno ou negativo, isto torna-se uma vulnerabilidade de segurança, quando o software está dependente deste inteiro para fazer certas ações como controlar um ciclo, determinar tamanhos para alocação de memória, entre outros. No caso da função vulneravel(), essa vulnerabilidade ocorre devido ao fato das variáveis `x`e `y` não serem validadas.

#### 2. Complete o main() de modo a demonstrar essa vulnerabilidade. ####

Para demonstrar a vulnerabilidade é necessário que o valor de `x*y` seja superior ao valor máximo permitido a se guardar num `size_t`. O código abaixo exemplifica uma forma de explorar esta falha.

```
int main() {
        char *matrix;
        vulneravel(matrix,100000000,100000000,'a');
}
```

#### 3. Ao executar dá algum erro? Qual? ####
Ao executar o código, ocorreu o erro **segmentation fault** pois não é possível alocar a quantidade de memória pretendida.

## Pergunta 1.2 ##

#### 1. Qual a vulnerabilidade que existe na função vulneravel() e quais os efeitos da mesma? ####

#### 2. Complete o main() de modo a demonstrar essa vulnerabilidade? ####
#### 3. Ao executar dá algum erro? Qual? ####
#### 4. Utilize as várias técnicas de programação defensiva introduzidas na aula teórica para mitigar as vulnerabilidades. ####
#### 4.1 Explique as alterações que fez. ####
