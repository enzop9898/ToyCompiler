#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type

struct str_multAddDiff{
int par1;
int par2;
int par3;
};


//Global Var

char nome[512] = "Michele";



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

struct str_multAddDiff multAddDiff(){
	struct str_multAddDiff toReturn;

 int primo, secondo, mul, add, diff;
printf("Inserire il primo argomento:\n");
scanf("%d", &primo);
printf("Inserire il secondo argomento:\n");
scanf("%d", &secondo);
 mul = primo * secondo;
 add = primo + secondo;
 diff = primo - secondo;
 toReturn.par1 = mul;
 toReturn.par2 = add;
 toReturn.par3 = diff;
return toReturn;
} 

void writeNewLines(int n){
while (n > 0) {
printf("\n");

 n = n - 1;

} 
} 

int main (){
 int a, b, c = 0;
struct str_multAddDiff strTmp_multAddDiff_1_1 = multAddDiff();
a = strTmp_multAddDiff_1_1.par1;
b = strTmp_multAddDiff_1_1.par2;
c = strTmp_multAddDiff_1_1.par3;
printf("Ciao %s", (nome));
writeNewLines(2);
printf("I tuoi valori sono:\n%d per la moltiplicazione\n%d per la somma, e \n%d per la differenza", (a), (b), (c));
return 0;
} 


