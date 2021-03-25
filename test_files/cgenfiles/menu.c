#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type


//Global Var

int option;



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

void sommaNumeri(){
 int a, b;
printf("Fornisci il primo addendo> ");
scanf("%d", &a);
printf("Fornisci il secondo addendo> ");
scanf("%d", &b);
printf("Somma: %d\n", (a + b));
} 

void moltiplicaNumeri(){
 int a, b, res = 0, i;
printf("Fornisci il primo fattore> ");
scanf("%d", &a);
printf("Fornisci il secondo fattore> ");
scanf("%d", &b);
 i = 0;
while (i < b) {
 res = res + a;

 i = i + 1;

} 
printf("Moltiplicazione: %d\n", (res));
} 

void dividiNaturali(){
 int a, b;
printf("Fornisci il dividendo> ");
scanf("%d", &a);
printf("Fornisci il divisore> ");
scanf("%d", &b);
printf("Rapporto: %d\n", (a / b));
} 

void elevaAPotenza(){
 int a, b, res = 1, i;
printf("Fornisci la base> ");
scanf("%d", &a);
printf("Fornisci l'esponente> ");
scanf("%d", &b);
 i = 0;
while (i < b) {
 res = res * a;

 i = i + 1;

} 
printf("Moltiplicazione: %d\n", (res));
} 

void calcolareSuccessioneFibonacci(){
 int n, i;
 int a, b;
printf("Fornisci quanti numeri di Fibonacci calcolare> ");
scanf("%d", &n);
if (n >= 0) {
printf("Fibonacci di 0 = 0\n");
printf("Fibonacci di 1 = 1\n");
 a = 0;
 b = 1;
 i = 2;
while (i <= n) {
 b = b + a;

 a = b - a;

printf("Fibonacci di %d = %d\n", (i), (b));

 i = i + 1;

} 
} 
} 

void menu(){
printf("Seleziona l'operazione che desideri eseguire\n\n");
printf("[0] sommare due numeri\n");
printf("[1] moltiplicare due numeri\n");
printf("[2] dividere due numeri naturali\n");
printf("[3] elevare a potenza un numero per una base\n");
printf("[4] calcolare la successione di Fibonacci\n");
printf("[5] ...uscire\n\n");
printf("> ");
scanf("%d", &option);
} 

int main (){
menu();
while (option <= 4) {
if (option == 0) {
sommaNumeri();
} 
else if (option == 1) {
moltiplicaNumeri();
}
else if (option == 2) {
dividiNaturali();
}
else if (option == 3) {
elevaAPotenza();
}
else if (option == 4) {
calcolareSuccessioneFibonacci();
}

printf("\n");

menu();
} 
printf("Uscita.\n");
return 0;
} 


