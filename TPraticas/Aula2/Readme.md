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


**Pergunta 2.1**  
**B)** No recoverSecretFromAllComponents-app.py são necessárias todas as partes para voltar a reconstruir o segredo, enquanto que recoverSecretFromComponents-app.py apenas são necessárias algumas partes para reconstruir o segredo. Caso o segredo guardado seja de extrema importância, torna-se necessário a utilização do recoverSecretFromAllComponents-app.py, onde todos os componentes necessitam de disponibilizar as suas partes e de seguida o segredo é recuperado, como explicado anteriormente. Já o recoverSecretFromComponents-app.py, é uma forma de assegurar que o segredo será reconstruído, mesmo que alguns dos componentes perca sua chave.

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

**Pergunta 4.1**  

Após gravar o conteúdo do *Base 64-encoded* do certificado da Buypass da Noruega no ficheiro chamado *cert_buypass.crt*, executamos o comando `openssl x509 -in cert_buypass.crt -text -noout` para obter mais informações a respeito do certificado.

```
$openssl x509 -in cert_buypass.crt -text -noout        
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number: 30 (0x1e)
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: C = NO, O = Buypass AS-983163327, CN = Buypass Class 3 Root CA
        Validity
            Not Before: Sep 25 08:05:19 2012 GMT
            Not After : Sep 25 08:05:19 2032 GMT
        Subject: C = NO, O = Buypass AS-983163327, CN = Buypass Class 3 CA 3
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                RSA Public-Key: (2048 bit)
                Modulus:
                    00:e3:7a:1a:4e:13:b2:80:27:df:d8:32:51:70:54:
                    7e:37:0b:35:aa:b5:14:b9:6a:da:64:aa:b7:b9:78:
                    11:40:0b:41:fc:1f:c3:ff:f7:8d:c2:8f:6f:bd:2b:
                    19:80:f5:33:0e:3a:b2:14:68:2f:68:05:24:fd:e6:
                    0f:91:58:7b:ee:69:00:aa:89:d5:54:5d:15:bf:ea:
                    3b:f1:e7:60:f3:c1:c7:6b:f3:6c:42:60:39:a7:c2:
                    b8:15:fa:c0:43:09:73:43:bc:71:3b:2f:fc:ca:8b:
                    bd:6f:0a:f3:c4:be:ce:26:5c:4d:25:cc:a3:de:57:
                    91:81:9c:63:26:90:89:2e:4f:47:59:b3:01:34:ef:
                    03:f5:e6:f3:5d:68:08:8f:23:64:3d:99:8d:24:3c:
                    2a:10:7c:78:f6:91:34:94:e2:38:af:f1:62:1a:92:
                    bd:4f:54:9f:b8:bc:dc:d0:d2:01:e9:62:65:85:ba:
                    6a:c2:7b:83:60:d4:70:55:83:ca:d1:ef:44:94:2d:
                    bd:de:ad:4f:19:fe:3d:44:e1:0b:e2:25:80:32:5c:
                    ea:b8:89:68:88:58:06:a1:0d:20:3e:8b:b0:12:dd:
                    91:21:88:2a:cc:55:0e:c8:c3:39:9d:f9:eb:c0:f5:
                    5d:a4:47:f5:69:68:e8:78:f4:ef:dd:8d:72:ee:c0:
                    a9:95
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Basic Constraints: critical
                CA:TRUE
            X509v3 Authority Key Identifier: 
                keyid:47:B8:CD:FF:E5:6F:EE:F8:B2:EC:2F:4E:0E:F9:25:B0:8E:3C:6B:C3

            X509v3 Subject Key Identifier: 
                CC:C3:F8:07:B7:9C:6D:7A:4E:F5:A7:2B:1D:05:F9:B3:47:1C:91:D1
            X509v3 Key Usage: critical
                Certificate Sign, CRL Sign
            X509v3 Certificate Policies: 
                Policy: X509v3 Any Policy

            X509v3 CRL Distribution Points: 

                Full Name:
                  URI:http://crl.buypass.no/crl/BPClass3RootCA.crl

    Signature Algorithm: sha256WithRSAEncryption
         09:10:7b:d7:1e:18:e4:e5:52:87:0c:3b:e1:e5:1d:de:68:d8:
         cb:35:7c:0a:f4:54:3a:e5:32:23:91:e7:22:65:c3:b9:df:01:
         ea:ba:61:41:25:7d:1f:23:b6:d8:3a:d8:58:d5:16:50:12:5c:
         9e:7e:19:fd:49:09:4d:0e:23:d8:ac:6c:1f:6e:ff:78:03:54:
         50:ab:c5:87:16:96:9f:50:7a:06:8e:09:a8:ae:01:e8:09:19:
         bf:24:a0:23:b8:5b:c4:e4:d6:1c:d4:45:b6:87:ca:c0:b6:0d:
         9a:a4:d4:57:7b:e4:eb:4d:f2:c7:1f:88:0d:60:3e:be:45:df:
         4b:ef:32:3a:45:04:60:3b:5d:65:99:a5:a6:0b:90:ba:55:d0:
         49:7c:eb:57:fe:00:a3:a6:33:b0:85:bf:51:5f:02:9d:19:79:
         5b:0a:0a:4b:96:fe:5d:84:c7:12:8e:0c:73:ec:d2:56:de:c8:
         f2:58:88:5e:96:de:6f:af:b8:8a:fb:75:ea:7b:59:93:03:ce:
         bd:97:ca:db:ed:e0:d7:1e:96:af:1f:f7:ce:41:89:47:35:60:
         d2:25:00:64:03:a9:db:3e:96:30:4f:2d:05:58:e1:1d:49:3a:
         eb:b1:bd:0d:2f:28:85:ea:c9:c4:cc:cc:a8:30:87:de:ce:3d:
         72:64:93:be:01:39:d9:5d:e2:2c:9e:b2:11:5b:99:0b:d8:19:
         87:1b:41:49:ed:4a:7f:ce:af:3d:b6:b3:61:f1:e6:05:13:6c:
         0f:60:a8:40:33:75:19:35:3d:23:cc:b2:e9:69:b2:50:10:50:
         aa:a1:7d:d7:10:f6:32:00:86:09:cc:66:ef:9c:26:00:3d:eb:
         32:f3:c3:18:f6:06:41:55:30:9f:36:ec:89:af:5a:ab:16:7d:
         81:f9:f0:b0:c0:3d:59:08:e7:20:dc:51:eb:d0:ae:b4:d7:3f:
         af:e0:a5:81:d7:d4:f5:ba:2f:35:70:d5:9d:69:d7:21:53:75:
         31:1f:78:b9:64:92:22:f3:3f:1a:6f:38:fc:13:c9:78:b2:5e:
         52:0f:c8:b2:51:6e:7c:38:5f:9b:49:cc:7e:3f:e4:46:db:e0:
         cf:c3:bb:87:6e:a2:2d:09:89:69:97:ff:98:5e:12:82:78:66:
         d9:fe:62:38:c4:78:a9:5a:eb:9a:9a:76:33:65:c0:f5:4c:9d:
         a0:fb:42:7d:6a:a2:da:be:b0:6b:9e:1b:51:d2:d5:92:da:09:
         9d:b3:91:b8:c8:dc:51:38:1d:6e:40:b6:4f:16:cc:2a:ed:68:
         df:09:42:b7:03:c4:6b:9c:d1:9c:18:f6:82:39:fe:01:02:8b:
         b4:18:08:86:a3:c9:16:c2
```

Os algoritmos utilizados e o tamanho das chaves, como mostra o *output* do comando acima, são:

**Algoritmo de assinatura:** RSA com SHA256

**Algoritmo de geração da chave pública:** RSA

**Tamanho da chave:** 2048bit

