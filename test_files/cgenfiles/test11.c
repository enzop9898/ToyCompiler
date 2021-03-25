#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type

struct str_x{
char par1[512];
char par2[512];
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

struct str_x x(int a, int b, int c){
	struct str_x toReturn;

 toReturn.par1 = "inizio";
 toReturn.par2 = "fine";
return toReturn;
} 

int main (){
 int n = 1, k = 1;
 float a, b;
 char str1[512], str2[512];
struct str_x strTmp_x_1_1 = x(1.5, k, n + k);
 a = n * k;
str1 = strTmp_x_1_1.par1;
str2 = strTmp_x_1_1.par2;
 b = 7;
printf("%s%s", (str1), (str2));
return 0;
} 


