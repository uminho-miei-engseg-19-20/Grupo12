**Pergunta 1.1**
Tendo em conta as variables introduzidas: 
head -c XX /dev/random | openssl enc -baseYY
XX vai definir o tamanho do número aleatório a ser gerado, e YY vai definir a base com que este será apresentado.
Podemos ultilizar o /dev/random ou o /dev/urandom, a diferença entre ambos baseia-se no facto de que o random não gera números aleatórios quando não encontra entropia suficiente, ao contrário do urandom que gera sempre um número "aleatório" independetemente da entropia.
Nos sistemas operativos Unix, /dev/random e /dev/urandom são ficheiros especiais que servem como geradores de números pseudo aleatórios, baseando-se na entropia obtida atráves de ruído captado por dispositivos do sistema. A principal diferença baseia-se no facto de o /dev/random bloquear quando não tem valores de ruído obtidos.
Isto deve-se ao facto de o gerador conter uma estimativa dos números bits de ruído que devem estar dentro da entropia, e por vezes os valores obtidos podem estar abaixo da estimativa, e no caso do /dev/random este bloqueia, porém o /dev/urandom não bloqueia e utiliza os bits obtidos para gerar mais bits pseudo-aleatórios, o que significa que este irá devolver sempre um valor pseudo aleatório quado chamado, embora por vezes com menor entropia ou seja menos aleatório que o /dev/random.

Ambos os ficheiros utilizam o algoritmo de Yarrow ou um aloritmo baseado no Yarrow porém a sua implementação dependerá da distribuição de Unix.


**Pergunta 2.1A)**
Após uma análise e execução dos programas concluimos que para efetuar a divisão do segredo em 8 partes com quorom de 5 para reconstruir o segredo devemos passar ambos os valores como argumento.
----codigo-----
