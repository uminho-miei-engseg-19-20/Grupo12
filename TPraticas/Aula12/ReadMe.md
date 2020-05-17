## Pergunta 1.1 - SQL Injection ##
### 2) ###
Retornar o departamento do empregado Bob Franco. 

    SELECT department FROM Employees WHERE first_name='Bob' AND last_name='Franco'

![1.2](./imagens/1.2.png)  


### 3) ###
Alterar o departamento do empregado Tobi Barnett para 'Sales'.

    UPDATE Employees SET department='Sales' WHERE first_name='Tobi' AND last_name='Barnett'
    
![1.3](./imagens/1.3.png)


### 4) ###
Adicionar a coluna "phone" na tabela "employees"

    ALTER TABLE Employees ADD phone varchar(20)
    
![1.4](./imagens/1.4.png)


### 5) ###
Conceder a um grupo de utilizadores o direito de alterar tabelas

    GRANT ALTER TABLE TO 'UnauthorizedUser'
    
![1.5](./imagens/1.5.png)


### 9) ###
Obter todos os utilizadores da tabela onde os campos foram preenchidos por:
+ Smith'
+ or
+ '1'='1

    SELECT * FROM user_data WHERE first_name="John" and last_name='Smith' or '1'='1'
    
![1.9](./imagens/1.9.png)



