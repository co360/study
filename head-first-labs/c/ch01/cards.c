/*
 * Program to evaluate face values.
 * Released under the Vegas Public License.
 * (c)2014 The College Blackjack Team.
 */
#include <stdio.h>
#include <stdlib.h>

int main() {
  char card_name[3];
  int count = 0;

  while (card_name[0] != 'X') {
    puts("Enter the card_name: ");
    scanf("%2s", card_name);
    int val = 0;
    /* if (card_name[0] == 'K') { */
    /*   val = 10; */
    /* } else if (card_name[0] == 'Q') { */
    /*   val = 10; */
    /* } else if (card_name[0] == 'J') { */
    /*   val = 10; */
    /* } else if (card_name[0] == 'A') { */
    /*   val = 11; */
    /* } else { */
    /*   val = atoi(card_name); */
    /* } */

    switch (card_name[0]) {
    case 'K':
    case 'Q':
    case 'J': {
      val =  10;
      break;
    }
    case 'A': {
      val = 11;
      break;
    }
    case 'X': {
      continue;
      break;
    }
    default:
      val =  atoi(card_name);
      if (val < 1 || val > 10) {
        puts("I don't understand that value!");
        continue;
      }
    }

    if (val > 2 && val < 7) {
      /* Check if the value is 3 to 6 */
      puts("Count has gone up");
      count++;
    } else if (val == 10) {
      /* Otherwise check if the card was 10, J, Q or K */
      puts("Count has gone down");
      count--;
    }
    printf("Current count: %i\n", count);
    /* printf("The card value is: %i\n", val); */
  }
  return 0;
}
