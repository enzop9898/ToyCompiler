#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type

struct str_x{
bool par1;
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

struct str_x x(int a, int b){
	struct str_x toReturn;

 bool c = false;
if (a > b) {
 c = true;
} 
 toReturn.par1 = c;
return toReturn;
} 

int main (){
 int n = 10, k = 8;
 bool c = false;
struct str_x strTmp_x_1_1 = x(n, k);
c = strTmp_x_1_1.par1;
printf("RESULT: %d", (c));
return 0;
} 


