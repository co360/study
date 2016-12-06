#include <stdio.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
  char *delivery = "";
  int thich = 0;
  int count = 0;
  char ch;

  while ((ch = getopt(argc, argv, "d:t")) != EOF) {
    switch (ch) {
    case 'd': {
      delivery = optarg;
      break;
    }
    case 't': {
      thich = 1;
      break;
    }
    default:
      fprintf(stderr, "Unknown option; '%s'\n", optarg);
      return 1;
    }
  }

  argc -= optind;
  argv += optind;

  if (thich) {
    puts("Thich crust.");
  }
  if (delivery[0]) {
    printf("To be delivered %s.\n", delivery);
  }

  puts("Ingredientes:");
  for (count = 0; count < argc; count++) {
    puts(argv[count]);
  }

  return 0;
}
