#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Structure for return type


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

void printPeg(int peg){
if (peg == 1) {
printf("left");
} 
else if (peg == 2) {
printf("center");
}
else {
printf("right");
}
} 

void hanoi(int n, int fromPeg, int usingPeg, int toPeg){
if (n != 0) {
hanoi(n - 1, fromPeg, toPeg, usingPeg);
printf("Move disk from ");
printPeg(fromPeg);
printf(" peg to ");
printPeg(toPeg);
printf(" peg.\n");
hanoi(n - 1, usingPeg, fromPeg, toPeg);
} 
} 

int main (){
 int n = 0;
printf("How many pegs? ");
scanf("%d", &n);
hanoi(n, 1, 2, 3);
return 0;
} 


