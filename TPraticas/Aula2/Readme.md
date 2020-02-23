**Pergunta 1.1**  
Tendo em conta as variáveis introduzidas:  
`head -c XX /dev/random | openssl enc -baseYY`  
XX vai definir o tamanho do número aleatório a ser gerado, e YY vai definir a base com que este será apresentado.

Nos sistemas operativos Unix, /dev/random e /dev/urandom são ficheiros especiais que servem como geradores de números pseudo aleatórios, baseando-se na entropia obtida atráves de ruído captado por dispositivos do sistema.
A diferença entre ambos baseia-se no facto de que o /dev/random não gera números aleatórios quando não encontra entropia suficiente, ao contrário do urandom que gera sempre um número "aleatório" independetemente da entropia.

Isto deve-se ao facto de o gerador conter uma estimativa dos números bits de ruído que devem estar dentro da entropia, e por vezes os valores obtidos podem estar abaixo da estimativa, e no caso do /dev/random este bloqueia, porém o /dev/urandom não bloqueia e utiliza os bits obtidos para gerar mais bits pseudo-aleatórios, o que significa que este irá devolver sempre um valor pseudo aleatório quado chamado, embora por vezes com menor entropia ou seja menos aleatório que o /dev/random.
As suas funcionalidades podem ser verificadas na seguinte imagem:
<img src="./images/random_uradom.png"  width="400" height="500">

Ambos os ficheiros utilizam o algoritmo de Yarrow ou um aloritmo baseado no Yarrow porém a sua implementação dependerá da distribuição de Unix.

Com base nisto, a terceira opção e a quarta opção:  
`head -c 1024 /dev/random | openssl enc -base64`  
`head -c 1024 /dev/urandom | openssl enc -base64`

São as opções corretas, caso pretendamos um número pseudo-aleatório com 1024 bytes, representado em base64, porém tal como mencionado anterior a terceira opção será a que dá mais garantias de aleatoridade.



**Pergunta 1.2**  
A olho nú os resultados obtidos anteriormente, e os novos resultados aparentam ser os mesmos, não parece haver grande diferença, porém com base nas referências propostas pelo docente, podemos observar que existem algumas diferenças significativas, a principal deve-se ao facto que num sistema que não aplica o algoritmo de HAVEGE, e que não tenha entropia sufeciente, o /dev/random irá bloquear, tal como explicado na pergunta anterior, porém com este algoritmo, o /dev/random já não irá bloquear, porque terá entropia sufeciente.

Isto acontece porque, este algoritmo tem a particularidade de recolher entropia de eventos de hardware, e mudança de estados destes, permitindo aumentar a fonte de entropia, o que permitirá ter um gerador de números aleátorios mais efeciente, e permite também que em certos computadores, como determinados servidores sem  operações I/O apenas fornecendo serviços, nestes casos a entropia normalmente será baixa, mas com este algoritmo a entropia será aumentada significativamente, permitindo que o gerador /dev/random/ seja usado sem bloquear.



**Pergunta 2.1**  
**A)** Após uma análise e execução dos programas concluimos que para efetuar a divisão do segredo em 8 partes com quorom de 5 para reconstruir o segredo devemos passar ambos os valores como argumento.

Para executar o código foi necessário, primeiramente, gerar  o par de chaves utilizando o Openssl, através do comando: `openssl genrsa -aes128 -out mykey.pem 1024`. Feito isto, corremos o comando: `python createSharedSecret-app.py 8 5 123 mykey.pem`, onde os parâmetros 8, 5, 123 e mykey.pem representam a quantidade de partes que queremos dividir o segredo, o número de partes necessárias para reconstruir o segredo, o identificador do segredo e a chave privada, respectivamente.

A resposta do programa foi:

```
$python createSharedSecret-app.py 8 5 123 mykey.pem  
Private key passphrase: 1234  
Secret: Agora temos um segredo extremamente confidencial  
Component: 1  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjEtNGExZGVjMjVlOGZiNWM2OWI3OTY2ZmEzYjFiOWUzOWI1ZjVjOTU3NzY5N2VjZmQ3NmM4ZjQ0NmE2NTI2MWY4MjFkMDM3ZTMxNTFjM2UwZGFmMzRkOGE4OGUxNzUwNTFhIiwgIjEyMyIsIDUsIDgsICI4YjczOTI5YjU1ZDgyOTlkNDU1NjNiY2M0YTE1MzhiNmNkYjU4NWI1ZDI4MGRmMjg4YTc2YTZlNjk3ZTFmZDgyIl19.EYQW1WH35Q1VmYznPeM4zwAJjJ3eHu1ANDZ1p6Qer4uE0JUy4P27quX1vASBIJrb673MRk4UVpJxcCUq_j7FlgEnLI7Oo6gyZbPDmU0RhDnNs2ssBOnA9b-eco86r6S13zW1_pEIYrF6uCs7sIL5L-vgZGzX35g_IIAwFGP2x4k  
Component: 2  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjItY2RiMzIxZGYwOWYwMDU2ZGRmMGIwOWUxMzNiY2ViNDgxZjA5Y2IzY2I0OGI0YWQ3NDg3MTY0Zjg0M2Y0YzNmMThlOTI4OTlkMjU1NGFmODI3ODJiZjVhMDVlYjhlOTdmIiwgIjEyMyIsIDUsIDgsICJjMmQ3NzIwYzIyODU0OTkyNjY0ZWRiOGQyM2Y5ZjFiMzZiMjcyMzhiMGY2MTIwZWQ4MWQwZGIxZDhlNWZhOWY4Il19.ijDnE4NQ1I_K2mP3y7j7UL4DCr9HLzGLjnEHp_1EmNQjT906FHcecpuS7I6SO2gRfBVu3_1YrTNcCMGWQt0T-63hj9ks_5hPxZT8yWoGfmxkY5ZApQtbcc8CzkRDry1dj643zAVFmvUSWsa_diLJceaIOy78mba4nqBjTiCfoZY  
Component: 3  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjMtMzIwMDFhMGMxODUxNDE3ZDg0MDEwYWZlNmZjZTNiZTQyOTUwYWZkYjczZGMwMWFkZTRlOGZmZTY5NzNmNTNiMDdhNGI4NmZmMzNmMGU4MTJlYzg2MTRjZDdlYzI4OGUwIiwgIjEyMyIsIDUsIDgsICIyMDU2MDBkN2VkMTM0ODI0Y2U0YmU4NmFhODliMGI4YmM0NjlmZDg2NWFhNTkyYTc3YjFiNDZjYWM1MWY4OGU0Il19.VPWIYXwtioCtcQtExWl4OQ1LVUPLZKiIqOfeWBaFxkjNSBsbG4C3iYd3tloyuYqU4vWumzelcMdGPI65DbsxWARTGGEh2PVqKZkWcL9AU-JZhgFYR92KEgDY5atFXRmSNUVXfXEV_gkU0G5mFFLDFCPRFl-MqcyH9P02KElJB4U  
Component: 4  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjQtZTQzYTVkMTk0NjY4ZDg3YjNjYWM2Yjk1Nzk1ODk1YTVlMzNlZmM1ZGI1NTE0MjljNTM1MzJjNGE1M2YyOTE5Y2FkN2VlYmUxMTQyOWI5ZjM0ZDE0YzRmZDQ5OWNkY2IxIiwgIjEyMyIsIDUsIDgsICI1NWZlOWNlNjE5NzQ4NGJmNWFlNDNhNmNhMDQ5MTcwOWY2NDdhYWMyNzVjOGNiMTFmMGE0OTlmMDlhNjI3MWE1Il19.AEHe9eztYh0_zew-qbcnWNFpFk9idig-RXC_QTehhAX3rOYv_004uAQwNqPv-XtHK5vbCRm4CcHC7C1CXR1tIiXLfJpl6tiiVARDrQqzQ8WV_iImDaLdrXTQjnVHnvP5wapFub8749B6_48Fz-R3v4sPmip-dwJkxdKAVHuNczs  
Component: 5  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjUtNThmM2YyNzBhNDc3ODgyMDk1NDE1M2I1MDJkOTc0OGY5MjBlODZiMTY2NjQzOWRiZGQ5YmVjNzVkNjc2MGE3NTBiYTNhM2JhOWQ1Mjc2M2I5ZWMzNTFmMTJiZDA1NTc2IiwgIjEyMyIsIDUsIDgsICJkYWE4NWY0MDk5OWIyYmI4YTgxZGVhMDBiYjMzZmExZWY4MzJkYWRjNTkxNTg1M2U1ZDhhMGMyZjJhODgyNjIxIl19.cOVYJDGSuF9dCbvQ4ujKatRPrmgJwuy6BddHIiT93aG9KD8rAMcTKF0OTmsfd2Bl5F6GvFgX0MfI-TXTGblh_TnZW13dKRbSpZGvm04-IbsCRDjJggNWLtMcXXVBmxNwLqwNqVGD367OoHU6CkHkjLESGI3HBV8lmV-ANkqOXII  
Component: 6  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjYtYzFiNjA3YTIwYjUwM2ZlMGZmNDFhZTA1ZGUxMTA2ZjViMjU0MWE4NTQyNmYxOWUwNjNlMmRmOGUyYWMxNGQ5OGU1NzBmZjFlNzgwOGZiNGVlYWY3NjNlZjY2MmUyZTIiLCAiMTIzIiwgNSwgOCwgIjIxZmQ0NjVlNGRjYWI0NTllY2NkMzU3MDdiOGViMjczMjg4NzUzYjI0NjNkMDdmYjUxZDk4NzA0OWI3N2FmZTMiXX0.O0AmHGbu1Lrs8qZgIPxJo0bxdNoCkoirJt9BwhDPx4Pmv2pNJUtFUHj8ZFiFmV0l0u0bNK-zTimaaNkQfk__5DeOLGQfKnwVdIV32ogG5JoM_bHoNdls6J9bvJWf1w277xqhfNSskx04r7ar_i_GRbtW2_9QrTi-LFsDI2HRSdk  
Component: 7  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjctODBmYmFjOWI4NzRmZjU3YjI0Zjk0ODBmN2IxMjVjZGY0NDE1M2NmODJiNDQ1MjBjODg0M2M5YTBhM2YxZDE0YjQ1NWQwN2RiNGI4YjlhZDk1MjI5NWI1MGRlZDdlZjU0IiwgIjEyMyIsIDUsIDgsICI5NGVhMDg1MzU1MzBkODk2ZjA0ZTEwMTFkNDllOWU0YTViZGJjOTcxNTNjODY1ZDg5MzlkY2ViOGI3YmExYmIxIl19.LWC_hcpBPPjhLahdYkAExiYLhMXUn0LHPQ1QlS_zMuPM959hLEfTzXLckLz5iG1P_itdmN169ZpLz0mmqCzXpYiGfO2ZMCTMx3-2udzUCLqztKejNmhEvfh4plPhTpCgY5Fe83cO39nBwUDrA-NQh2en54xt6gJXlVSNNRJt5Eo  
Component: 8  
eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjgtNDIzYzViMzg4MjZkZmJkNjQyODU5MWFlZWEyMzA5NDUzMjljYTUzYTc4MDAyMTQ5NTc0NTgzNzdhZDFmMmEyYzU2OWZkOGI5NjMwZDRkZDNlNmIzOTk2NTdmMzA1ZWYwIiwgIjEyMyIsIDUsIDgsICJkMzE3N2M2MmM1NzY0NjU3NzQ3Njc3NWU2ZGYwY2YxNDdlMGNkY2M1MzhiNTBiNmI1MjQ4NGYxMmQwNzY0OWNiIl19.Pm3Lz9x7GIFXumGS9zRrxQOEzVSZVxE5sc-Xbw-G9lLEWg5w6Sns38S9MIfNrYsu4UMNoshYUcFs1y1Se1fRKRJ2LsnyHXEET-PC5jjCnSmttA-qlUmmNq9sCkv7UyvtxoPLg4jKGK0QDtHIvEDAMtYMpb-6fy3heQw27aPkvqo
```
Após a execução do programa, foi gerado o certificado necessário para recuperar o segredo, através do comando `openssl req -key mykey.pem -new -x509 -days 365 -out mykey.crt`.


**Pergunta 2.1B)**
No recoverSecretFromAllComponents-app.py são necessárias todas as partes para voltar a reconstruir o segredo, enquanto que recoverSecretFromComponents-app.py apenas são necessárias algumas partes para reconstruir o segredo. Caso o segredo guardado seja de extrema importância, torna-se necessário a utilização do recoverSecretFromAllComponents-app.py, onde todos os componentes necessitam de disponibilizar as suas partes e de seguida o segredo é recuperado, como explicado anteriormente. Já o recoverSecretFromComponents-app.py, é uma forma de assegurar que o segredo será reconstruído, mesmo que alguns dos componentes perca sua chave.

Primeiramente, executamos o script recoverSecretFromComponents-app.py utilizando os componentes 1, 3, 5, 7 e 8 para recuperar o segredo, o número de partes que serão utilizadas, o identificador do segredo e o certificado gerado anteriormente. 

A resposta do programa foi:

```
$python recoverSecretFromComponents-app.py 5 123 mykey.crt 
Component 1: eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjEtNGExZGVjMjVlOGZiNWM2OWI3OTY2ZmEzYjFiOWUzOWI1ZjVjOTU3NzY5N2VjZmQ3NmM4ZjQ0NmE2NTI2MWY4MjFkMDM3ZTMxNTFjM2UwZGFmMzRkOGE4OGUxNzUwNTFhIiwgIjEyMyIsIDUsIDgsICI4YjczOTI5YjU1ZDgyOTlkNDU1NjNiY2M0YTE1MzhiNmNkYjU4NWI1ZDI4MGRmMjg4YTc2YTZlNjk3ZTFmZDgyIl19.EYQW1WH35Q1VmYznPeM4zwAJjJ3eHu1ANDZ1p6Qer4uE0JUy4P27quX1vASBIJrb673MRk4UVpJxcCUq_j7FlgEnLI7Oo6gyZbPDmU0RhDnNs2ssBOnA9b-eco86r6S13zW1_pEIYrF6uCs7sIL5L-vgZGzX35g_IIAwFGP2x4k  
Component 2: eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjMtMzIwMDFhMGMxODUxNDE3ZDg0MDEwYWZlNmZjZTNiZTQyOTUwYWZkYjczZGMwMWFkZTRlOGZmZTY5NzNmNTNiMDdhNGI4NmZmMzNmMGU4MTJlYzg2MTRjZDdlYzI4OGUwIiwgIjEyMyIsIDUsIDgsICIyMDU2MDBkN2VkMTM0ODI0Y2U0YmU4NmFhODliMGI4YmM0NjlmZDg2NWFhNTkyYTc3YjFiNDZjYWM1MWY4OGU0Il19.VPWIYXwtioCtcQtExWl4OQ1LVUPLZKiIqOfeWBaFxkjNSBsbG4C3iYd3tloyuYqU4vWumzelcMdGPI65DbsxWARTGGEh2PVqKZkWcL9AU-JZhgFYR92KEgDY5atFXRmSNUVXfXEV_gkU0G5mFFLDFCPRFl-MqcyH9P02KElJB4U  
Component 3: eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjUtNThmM2YyNzBhNDc3ODgyMDk1NDE1M2I1MDJkOTc0OGY5MjBlODZiMTY2NjQzOWRiZGQ5YmVjNzVkNjc2MGE3NTBiYTNhM2JhOWQ1Mjc2M2I5ZWMzNTFmMTJiZDA1NTc2IiwgIjEyMyIsIDUsIDgsICJkYWE4NWY0MDk5OWIyYmI4YTgxZGVhMDBiYjMzZmExZWY4MzJkYWRjNTkxNTg1M2U1ZDhhMGMyZjJhODgyNjIxIl19.cOVYJDGSuF9dCbvQ4ujKatRPrmgJwuy6BddHIiT93aG9KD8rAMcTKF0OTmsfd2Bl5F6GvFgX0MfI-TXTGblh_TnZW13dKRbSpZGvm04-IbsCRDjJggNWLtMcXXVBmxNwLqwNqVGD367OoHU6CkHkjLESGI3HBV8lmV-ANkqOXII  
Component 4: eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjctODBmYmFjOWI4NzRmZjU3YjI0Zjk0ODBmN2IxMjVjZGY0NDE1M2NmODJiNDQ1MjBjODg0M2M5YTBhM2YxZDE0YjQ1NWQwN2RiNGI4YjlhZDk1MjI5NWI1MGRlZDdlZjU0IiwgIjEyMyIsIDUsIDgsICI5NGVhMDg1MzU1MzBkODk2ZjA0ZTEwMTFkNDllOWU0YTViZGJjOTcxNTNjODY1ZDg5MzlkY2ViOGI3YmExYmIxIl19.LWC_hcpBPPjhLahdYkAExiYLhMXUn0LHPQ1QlS_zMuPM959hLEfTzXLckLz5iG1P_itdmN169ZpLz0mmqCzXpYiGfO2ZMCTMx3-2udzUCLqztKejNmhEvfh4plPhTpCgY5Fe83cO39nBwUDrA-NQh2en54xt6gJXlVSNNRJt5Eo  
Component 5: eyJhbGciOiAiUlMyNTYifQ.eyJvYmplY3QiOiBbIjgtNDIzYzViMzg4MjZkZmJkNjQyODU5MWFlZWEyMzA5NDUzMjljYTUzYTc4MDAyMTQ5NTc0NTgzNzdhZDFmMmEyYzU2OWZkOGI5NjMwZDRkZDNlNmIzOTk2NTdmMzA1ZWYwIiwgIjEyMyIsIDUsIDgsICJkMzE3N2M2MmM1NzY0NjU3NzQ3Njc3NWU2ZGYwY2YxNDdlMGNkY2M1MzhiNTBiNmI1MjQ4NGYxMmQwNzY0OWNiIl19.Pm3Lz9x7GIFXumGS9zRrxQOEzVSZVxE5sc-Xbw-G9lLEWg5w6Sns38S9MIfNrYsu4UMNoshYUcFs1y1Se1fRKRJ2LsnyHXEET-PC5jjCnSmttA-qlUmmNq9sCkv7UyvtxoPLg4jKGK0QDtHIvEDAMtYMpb-6fy3heQw27aPkvqo
Recovered secret: Agora temos um segredo extremamente confidencial
```
