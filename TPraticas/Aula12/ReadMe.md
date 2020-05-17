## Pergunta 1.1 - SQL Injection ##
### 2) ###
O objetivo desta alínea passava por retornar o departamento do empregado Bob Franco. Assim, a query inserida foi: 

    SELECT department FROM Employees WHERE first_name='Bob' AND last_name='Franco'

![1.2](./imagens/1.2.png)  


### 3) ###
Neste tópico era pretendido alterar o departamento do empregado Tobi Barnett para 'Sales'.

    UPDATE Employees SET department='Sales' WHERE first_name='Tobi' AND last_name='Barnett'
    
![1.3(./imagens/1.3png)  
