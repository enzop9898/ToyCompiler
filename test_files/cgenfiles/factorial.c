#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type

struct str_factorial{
int par1;
};


//Global Var

int n = 0;



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

struct str_factorial factorial(int n){
	struct str_factorial toReturn;

 int result = 0;
if (n == 0) {
 result = 1;
} 
else {
 result = n * factorial(n - 1).par1;
}
 toReturn.par1 = result;
return toReturn;
} 

int main (){
 int a = ((((true) ? (5) : (0)) >= 0) ? (3) : (1));
printf("%d\n", (a));
printf("%d\n", (((((true) ? (5) : (0)) >= 0) ? (30) : (1))));
printf("Enter n, or >= 10 to exit: ");

scanf("%d", &n);

while (n < 10) {
struct str_factorial strTmp_factorial_1_1 =factorial(n);
printf("Factorial of %d is %d \n", (n), strTmp_factorial_1_1.par1);

printf("Enter n, or >= 10 to exit: ");

scanf("%d", &n);

} 
return 0;
} 


