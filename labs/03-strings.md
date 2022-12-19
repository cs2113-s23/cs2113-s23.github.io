---
layout: default
permalink: lab/3
---

*View the video for this lab on [youtube](https://youtu.be/HnBY40W0vsM)*

# Lab 3: Strings and Command Line Arguments

<!--
* Github Classroom Link: [https://classroom.github.com/a/pR_5ttWK](https://classroom.github.com/a/pR_5ttWK) -->

* Github Classroom Link: [https://classroom.github.com/a/_VLh4SIU](https://classroom.github.com/a/_VLh4SIU)

## Preliminaries


### Test Script

To help you complete the lab, I have provide a test script that will run basic tests against your program. The script is not designed to be comprehensive, and you will graded based on a larger array of tests. To execute the test script, run it from anywhere within the lab directory.

    ./test.sh

### README file

You are required to fill out the README.md file in your repository describing your programming process as well as answer any related lab questions. 

<a id="orgef834d3"></a>

### Compiling your code:

To compile your code, use `gcc` the gnu c compiler using the following command:

    gcc -Wall prog.c -o prog

The `-Wall` option will provide additional warnings, and `prog` is replaced with the program in question. 


## Part 1: Palindrome Checking (30 Points)

For this part of the lab, you will complete a small program to check if the input provided by the user is a palindrome. A palindrome is a string that is the same forward and backwards.

There are two main ways for testing if a string is a palindrome:

-   Iterate from forward to back, and from back to forward, and check that they are the same.

-   Create a copy of the string, in reverse, and check that the copy matches the original.

You will implement both; however, the challenge is working with C strings. The main portion of the program, provided to you, looks like such:

```c
int main(int argc, char * argv[]){

  char str[1024];

  printf("Enter a string:\n");

  scanf("%1023s",str);

  if(check1(str)){
    printf("Palindrome according to check 1\n");
  }else{
    printf("NOT a palindrome according to check 1\n");
  }


  if(check2(str)){
    printf("Palindrome according to check 2\n");
  }else{
    printf("NOT a palindrome according to check 2\n");
  }

}

```

Note that the input is read into the `str` string and may be substantially shorter than the length 1024. It is also passed to the `check1()` and `check2()` functions without a length argument. You should make sure that you use `strlen()` somewhere.

Additionally, to complete `check2()` which requires making a copy of the string; don't forget that C strings must be NULL terminated. If you do not NULL terminate, `strcmp()` will not work.

<div class="requirement">

-   You must complete the `palindrome` program using two checks.

-   `check1()` and `check2()` must use two distinct algorithms:
    -   `check1()` use two iterators, one from the start and one from the end of the string, to check if the front and back are the same
    -   `check2()` copy the string to a new string, in reverse, and compare the two strings using `strcmp()`



Here is some sample output:

    aviv@ubuntu: part1 $ ./palindrome 
    Enter a string:
    racecar
    Palindrome according to check 1
    Palindrome according to check 2
    aviv@ubuntu: part1 $ ./palindrome 
    Enter a string:
    madamimadam
    Palindrome according to check 1
    Palindrome according to check 2
    aviv@ubuntu: part1 $ ./palindrome 
    Enter a string:
    amanaplanacanalpanama
    Palindrome according to check 1
    Palindrome according to check 2
    aviv@ubuntu: part1 $ ./palindrome 
    Enter a string:
    notapalindrome
    NOT a palindrome according to check 1
    NOT a palindrome according to check 2
    aviv@ubuntu: part1 $ 

</div>


# Part 2: Cryptanalysis (70 points)

This part of the lab can be found in the `part2` folder in the repository. 

In this part of the lab, you are going to write two small programs to perform cryptanalysis and cipher text cracking --- this will come in the form of a letter frequency program `freq` and a letter substitution program `sub`. You will use both programs to decrypt a secret message provided to you `cipher.txt` that was encrypted with a substitution cipher, where each letter is replaced with a different letter. 

Perhaps the most famous substitution cipher is the Caeser cipher, attributed to the Roman emperor Julius Caesar, where it was used for communicating with his generals in secret code. The cipher is rather simple, and describes shifting the alphabet by three letters such that `A->C` and `B->D` and `C->E` and so on; decryption requires shifting in the reverse direction. More advanced substitution cipher allow for arbitrary substitutions, not just by a shift, as long as those substitutions are consistent throughout the document. 

Unfortunately, any substitution cipher is trivially broken with some basic tools and analysis because of one fatal flaw. The distribution of letters of the plain text will be identical to that of the cipher text (under the substitution). Consider that English text (if we assume the input, plain-text is English) has a known letter distribution: if we see that `Q` is now the most frequent letter in the cipher text, then it was probably subbed with `S` to get pack to the plain text. 

Your task for this part of the lab is to write two programs: `freq` and `sub`. The `freq` program will take as input a text file and report the alpha letter frequencies of that file (ignoring non-alpha characters), and the `sub` program will print out the text of a file with letter substitutions based on a key-file, containing the substitution rules.  

### `freq`

 *  The frequency program should take one argument, `input.txt`, as a command line argument. It can be run like below:
    ```
    ./freq input.txt
    ```
 * It should output the count of each letter frequency as well as it's percentage, with two decimal points of precision's. For example, you are provided a file called `swift.txt` containing a text from  [Project Gutenberg](https://www.gutenberg.org/files/1342/1342-h/1342-h.htm#link2HCH0001). Running `freq` on that file produces the following output:
 ```
 $ ./freq swift.txt 
A: 42765 (7.74%)
B: 9361 (1.70%)
C: 14136 (2.56%)
D: 22849 (4.14%)
E: 71359 (12.92%)
F: 12376 (2.24%)
G: 10450 (1.89%)
H: 34661 (6.28%)
I: 38880 (7.04%)
J: 968 (0.18%)
K: 3347 (0.61%)
L: 22068 (4.00%)
M: 15056 (2.73%)
N: 38737 (7.01%)
O: 41407 (7.50%)
P: 8733 (1.58%)
Q: 638 (0.12%)
R: 33554 (6.08%)
S: 33875 (6.13%)
T: 48212 (8.73%)
U: 15507 (2.81%)
V: 5842 (1.06%)
W: 12595 (2.28%)
X: 867 (0.16%)
Y: 13052 (2.36%)
Z: 936 (0.17%)
 ```

 * `freq` should ignore all non-alpha characters in the input file, and should count frequencies of lower and upper-case equivalent. That is `a` and `A` both count the same, not for different frequencies. As you can see above, report your frequencies using upper-case. 

### Sub

 * The `sub` program takes two inputs, `key.txt`, and `cipher.txt`, and run with the following command line arguments.
 ```
 ./sub key.txt cipher.txt
 ```

 * The `key.txt` file is organized where each letter `A-Z` matches to a substitution, like `Q:S` (sub `Q` for `S`). As a baseline example, a `key.txt` file that does no-substitutions, would be like below (and is provided in the repo as `key.txt`). However, other `key.txt` files would have actual letter substitutions. 
 ```
 A:A
 B:B
 C:C
 ...
 Y:Y
 Z:Z
 ```
 As part of your analysis, you'll change the right part (after the `:`) in `key.txt` to decrypt the secret message with substitutions. After you decrypt the key by using your `freq` for your analysis, you need to manually update the `key.txt` with your decrypted key . Such like:
 ```
 A:M
 B:F
 C:L
 D:E
 ...
 ```
 
 * To test your substitution program, it should produce the following output when run on the test input and test key.
 
   ```
 $ ./sub test_encrypt_key.txt test_plain.txt
 ... FO CMR TKHMO PKMWOFWMU YMUJH – GQJ WMB SKMP FO MKQJBX GQJ VQK SMKAOC MR GQJ LQJBX MWKQRR OCH WQUX AQQBR QV NMTUMB LHOM; GQJ WMB UFH QB FO QB OCH LKFUUFMBO AMKLUH-RMBXHX LHMWCHR QV RMBOKMTFBJR Y, FBCMUFBT OCH CHMXG RHM YMPQJKR; GQJ WMB RUHHP JBXHK FO LHBHMOC OCH ROMKR SCFWC RCFBH RQ KHXUG QB OCH XHRHKO SQKUX QV EMEKMVQQB; JRH FO OQ RMFU M AFBF KMVO XQSB OCH RUQS CHMYG KFYHK AQOC; SHO FO VQK JRH FB CMBX-OQ-CMBX-WQALMO; SKMP FO KQJBX GQJK CHMX OQ SMKX QVV BQZFQJR VJAHR QK OQ MYQFX OCH TMDH QV OCH KMYHBQJR LJTLUMOOHK LHMRO QV OKMMU (M AFBXLQTTFBTUG ROJPFX MBFAMU, FO MRRJAHR OCMO FV GQJ WMB’O RHH FO, FO WMB’O RHH GQJ – XMVO MR M LJRC, LJO YHKG, YHKG KMYHBQJR); GQJ WMB SMYH GQJK OQSHU FB HAHKTHBWFHR MR M XFROKHRR RFTBMU, MBX QV WQJKRH XKG GQJKRHUV QVV SFOC FO FV FO ROFUU RHHAR OQ LH WUHMB HBQJTC. 
 $ ./sub test_decrypt_key.txt test_cipher.txt
 ... IT HAS GREAT PRACTICAL VALUE – YOU CAN WRAP IT AROUND YOU FOR WARMTH AS YOU BOUND ACROSS THE COLD MOONS OF JAGLAN BETA; YOU CAN LIE ON IT ON THE BRILLIANT MARBLE-SANDED BEACHES OF SANTRAGINUS V, INHALING THE HEADY SEA VAPOURS; YOU CAN SLEEP UNDER IT BENEATH THE STARS WHICH SHINE SO REDLY ON THE DESERT WORLD OF KAKRAFOON; USE IT TO SAIL A MINI RAFT DOWN THE SLOW HEAVY RIVER MOTH; WET IT FOR USE IN HAND-TO-HAND-COMBAT; WRAP IT ROUND YOUR HEAD TO WARD OFF NOXIOUS FUMES OR TO AVOID THE GAZE OF THE RAVENOUS BUGBLATTER BEAST OF TRAAL (A MINDBOGGINGLY STUPID ANIMAL, IT ASSUMES THAT IF YOU CAN’T SEE IT, IT CAN’T SEE YOU – DAFT AS A BUSH, BUT VERY, VERY RAVENOUS); YOU CAN WAVE YOUR TOWEL IN EMERGENCIES AS A DISTRESS SIGNAL, AND OF COURSE DRY YOURSELF OFF WITH IT IF IT STILL SEEMS TO BE CLEAN ENOUGH.
 ```
 
 
 Note, that your `sub` program can both encrypt and decrypt, depending on the key.
 
 
<div class="requirement">

You should complete the following for this part of the lab
1. `freq.c` based on the specifications above
2. `sub.c` based on the specifications above

</div>



