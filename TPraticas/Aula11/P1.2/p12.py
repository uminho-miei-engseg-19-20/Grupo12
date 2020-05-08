import datetime
import re
from calendar import monthrange


def valida_nome(nome):
    """
    Valida nome com até 64 caracteres
    :param nome: nome a ser validado
    :return: True se o nome for válido
             False se o nome não for válido
    """
    if nome.replace(" ", "").isalpha() and 3 <= len(nome) <= 64:
        return True
    else:
        return False


def valida_nif_nic(numero, d):
    """
    Valida NIF ou NIC
    utilizando o algoritmo "módulo 11"
    :param numero: número do documento a ser validado
    :param d: número de dígitos do documento a ser validado
    :return: True se o documento for válido
             False se o documento não for válido
    """
    digitos_nif = d
    if not numero.isdigit() or len(numero) != digitos_nif:
        return False

    soma = sum([int(dig) * (digitos_nif - pos) for pos, dig in enumerate(numero)])
    resto = soma % 11
    if numero[-1] == '0' and resto == 1:
        resto = (soma + 10) % 11
    return resto == 0


def valida_data(data):
    """
    Valida a data de nascimento
    :param data: data de nascimento a ser validada
    :return: True se a data for válida
             False se a data não for válida
    """
    try:
        day, month, year = data.split('/')
        data_nasc = datetime.datetime(int(year), int(month), int(day))
        if datetime.datetime.now() <= data_nasc:
            raise ValueError
    except ValueError:
        return False
    else:
        return True


def valida_valor(valor):
    """
    Valida o valor a ser pago
    :param valor: valor a ser validado
    :return: True se o valor for válido
             False se o valor não for válido
    """
    if re.match(r'^[1-9]\d*([.,]\d{1,2})?$', valor) is not None and len(valor) < 20:
        return True
    else:
        return False


def valida_cartao(numero):
    """
    Valida número de cartão de crédito
    de acordo com o algoritmo de Luhn
    :param numero: número do cartão de crédito a ser validado
    :return: True se o número do cartão for válido
             False se o número do cartão não for válido
    """

    numero = numero.replace(' ', '')
    if 19 < len(numero) < 12 or not numero.isdigit():
        return False
    soma = 0
    parity = len(numero) % 2
    for i, digito in enumerate(int(x) for x in numero):
        if i % 2 == parity:
            digito *= 2
            if digito > 9:
                digito -= 9
        soma += digito
    return soma % 10 == 0


def exp_cartao(data):
    """
    Valida a validade do cartão de crédito
    :param data: data de validade a ser validada
    :return: True se a data for válida
             False se a data não for válida
    """
    try:
        month, year = data.split('/')
        # Número de dias do mês inserido
        weekday, day_count = monthrange(int(year), int(month))

        validade = datetime.datetime(int(year), int(month), day_count)
        if datetime.datetime.now() >= validade:
            raise ValueError
    except ValueError:
        return False
    else:
        return True


def valida_cvc(cvc):
    """
    Valida o CVV/CVC
    :param cvc: CVV/CVC a ser validado
    :return: True se o CVV/CVC for válido
             False se o CVV/CVC não for válido
    """
    if not cvc.isdigit() or len(cvc) > 4 or len(cvc) < 3:
        return False
    else:
        return True


def verify(func, txt):
    """
    Verifica se o valor digitado é válido de acordo com a função passada
    :param func: função que valida o input inserido
    :param txt: tipo de input a ser inserido
    :return: o valor inserido, quando validado
    """
    while True:
        valor = str(input(txt))
        if 'NIC' in txt:
            val = func(valor, 8)
        elif 'NIF' in txt:
            val = func(valor, 9)
        else:
            val = func(valor)

        if val:
            # print('Válido', nif)
            return valor
        else:
            print('Valor inválido.')


# nome_validado = verify(valida_nome, 'Nome: ')
# print(nome_validado)

# data_nasc_validada = verify(valida_data, 'Insira a data de nascimento no formato DD/MM/AAAA:  ')
# print(data_nasc_validada)

# nif_validado = verify(valida_nif_nic, 'NIF (apenas números):  ')
# print(nif_validado)

# valor_validado = verify(valida_valor, 'Valor a pagar: ').replace(" ", "")
# print(valor_validado)

# nic_validado = verify(valida_nif_nic, 'NIC (apenas números): ')
# print(nic_validado)

# cartao_validado = verify(valida_cartao, 'Número do cartão: ')
# print(cartao_validado)

# cvc_validado = verify(valida_cvc, 'CVC/CVV: ')
# print(cvc_validado)

# expdate_validada = verify(exp_cartao, 'Insira a validade do Cartão de crédito no formato MM/AAAA:  ')
# print(expdate_validada)

