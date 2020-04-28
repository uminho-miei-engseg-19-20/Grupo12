#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const int MAX_SIZE = 2048;
const int MIN_SIZE = 0;

void vulneravel (char *origem, size_t tamanho) {
        size_t tamanho_real;
        char *destino;
	//if (tamanho < MIN_SIZE) {
	//	printf("bla");
	//	tamanho = 1;
	//}
        if (tamanho < MAX_SIZE && tamanho > MIN_SIZE) {
                tamanho_real = tamanho - 1; // Não copiar \0 de origem para destino
                destino = (char *) malloc(tamanho_real);
                memcpy(destino, origem, tamanho_real);
        }

}

int main() {
	vulneravel("aaa", 0);
}
