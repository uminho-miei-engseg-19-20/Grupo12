**Pergunta 1.1**

Para alterar o timestamp para a data de hoje e mudar o dado do Genesis Block, foi utilizado a função Date() e foram alterados os parâmetros da função createGenesisBlock(), conforme o código abaixo:

```
createGenesisBlock(){
    var date_time = new Date();
    var dado = "Bloco inicial da koreCoin";
    return new Block(0, date_time.toLocaleDateString('EN-GB'), dado, "0");
}
```

**Pergunta 1.2**

Para acrescentar um novo bloco, foi utilizada a função addBlock, conforme o código abaixo:

```
koreCoin.addBlock(new Block (4, "01/05/2019", {amount: 100}));
koreCoin.addBlock(new Block (5, "20/06/2019", {amount: 300}));
koreCoin.addBlock(new Block (6, "02/02/2020", {amount: 150}));
```

**Pergunta 2.1**

Para a resolução desta pergunta, primeiramente foi alterado a dificuldade de minerar para 2, como mostra o código abaixo:

```
constructor(){
        this.chain = [this.createGenesisBlock()];
        this.difficulty = 2;
    }
```


**Tempo com dificuldade 2:**
```
real    0m2.321s
user    0m1.558s
sys     0m0.114s
```

**Tempo com dificuldade 3:**
```
real    0m2.153s
user    0m1.931s
sys     0m0.087s
```

**Tempo com dificuldade 4:**
```
real    0m7.056s
user    0m5.994s
sys     0m0.172s
```

**Tempo com dificuldade 5:**
```
real    1m21.364s
user    1m11.258s
sys     0m1.218s
```

Notou-se que quanto maior a dificuldade, maior o tempo necessário para minerar os blocos. Isso se dá como forma de segurança para evitar que os blocos sejam alterados e que o *hash* de toda a cadeia seja recalculado facilmente. Este processo, chamado **Proof of Work**, pretende garantir que o utilizador precise gastar algum poder computacional para criar um novo bloco. Neste caso, é acrescentado um determinado número de zeros no início da *hash* e quanto maior o número de zeros, maior o tempo e o poder computacional necessários para a criação de um novo bloco. O algoritmo utilizado pode ser verificado no extrato de código abaixo:

```
    mineBlock(difficulty){
        while(this.hash.substring(0, difficulty) !== Array(difficulty + 1).join("0")){
            this.nonce++;
            this.hash = this.calculateHash();
        }
        
        console.log("Block mined: " + this.hash);
    }
```

**Pergunta 2.2**

**1)**  
O algoritmo de proof of word utilizado foi implementado na função `proof_of_work(last_proof)`. Este algoritmo simplesmente incrementa um número até que este seja divisível por 9 e seja divisível pelo *proof number* do bloco anterior, que é passado como o parâmetro `last_proof`.


```
def proof_of_work(last_proof):
  # Create a variable that we will use to find
  # our next proof of work
  incrementor = last_proof + 1
  # Keep incrementing the incrementor until
  # it's equal to a number divisible by 9
  # and the proof of work of the previous
  # block in the chain
  while not (incrementor % 9 == 0 and incrementor % last_proof == 0):
    incrementor += 1
  # Once that number is found,
  # we can return it as a proof
  # of our work
  return incrementor
```

**2)**
Este algoritmo não pode ser considerado uma boa opção para minerar, pois além de não ser possível controlar diretamente a dificuldade para minerar um bloco, efetua operações algébricas relativamente simples e por isso, exige pouco poder computacional para a criação de um novo bloco.  

Outro problema encontrado é o fato dos cálculos não são efetuados do lado do cliente, mas sim do lado do servidor, o que possibilita um possível *overloading* do sistema, pois se vários clientes tentarem criar blocos ao mesmo tempo, o servidor poderá não ter capacidade para suportar todos.