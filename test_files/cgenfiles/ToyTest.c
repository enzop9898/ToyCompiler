#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type

struct str_moltiplicazione{
int par1;
};

struct str_differenza{
int par1;
};

struct str_divisione{
int par1;
};

struct str_somma{
int par1;
};


//Global Var




char* concat(char* first, char* second) {
    int i = 0, j = 0; 
    char* str3 = malloc(512 * sizeof(char));
    // Insert the first string in the new string 
    while (first[i] != '\0') { 
        str3[j] = first[i]; 
        i++; 
        j++; 
    }
  
    // Insert the second string in the new string 
    i = 0; 
    while (second[i] != '\0') { 
        str3[j] = second[i]; 
        i++; 
        j++; 
    } 
    str3[j] = '\0'; 
    return str3;
}


//Program Fuctions

struct str_somma somma(int n, int m){
	struct str_somma toReturn;

 int result = 0;
 result = n + m;
 toReturn.par1 = result;
return toReturn;
} 

struct str_differenza differenza(int n, int m){
	struct str_differenza toReturn;

 int result = 0;
 char x[512] = "ciao a tutti";
 result = n - m;
 toReturn.par1 = result;
return toReturn;
} 

struct str_moltiplicazione moltiplicazione(int n, int m){
	struct str_moltiplicazione toReturn;

 int result = 0;
 result = n * m;
 toReturn.par1 = result;
return toReturn;
} 

struct str_divisione divisione(int n, int m){
	struct str_divisione toReturn;

 int result = 0;
 result = n / m;
 toReturn.par1 = result;
return toReturn;
} 

int main (){
 int s, d, m, di, first, secondo, scelta = 0;
printf("Menù\n");

printf("Scegliere l'operazione aritmetica che si vuole eseguire\n");

printf("1. Somma\n");

printf("2. Sottrazione\n");

printf("3. Moltiplicazione\n");

printf("4. Divisione\n");

printf("5. EXIT");

printf("\n Prego effettuare la scelta");

scanf("%d", &scelta);

printf("Dammi il prim operando");

scanf("%d", &first);

printf("Dammi il secondo operando");

scanf("%d", &secondo);

while (scelta != 5) {
if (scelta == 1) {
struct str_somma strTmp_somma_1_1 = somma(first, secondo);
s = strTmp_somma_1_1.par1;
printf("%d", (s));
} 
else if (scelta == 2) {
struct str_differenza strTmp_differenza_1_2 = differenza(first, secondo);
d = strTmp_differenza_1_2.par1;
printf("%d", (d));
}
else if (scelta == 3) {
struct str_moltiplicazione strTmp_moltiplicazione_1_3 = moltiplicazione(first, secondo);
m = strTmp_moltiplicazione_1_3.par1;
printf("%d", (m));
}
else if (scelta == 4) {
struct str_divisione strTmp_divisione_1_4 = divisione(first, secondo);
di = strTmp_divisione_1_4.par1;
printf("%d", (di));
}

printf("Menù\n");

printf("Scegliere l'operazione aritmetica che si vuole eseguire\n");

printf("1. Somma\n");

printf("2. Sottrazione\n");

printf("3. Moltiplicazione\n");

printf("4. Divisione\n");

printf("5. EXIT");

printf("\n Prego effettuare la scelta");

scanf("%d", &scelta);

printf("Dammi il prim operando");

scanf("%d", &first);

printf("Dammi il secondo operando");

scanf("%d", &secondo);

} 
return 0;
} 


