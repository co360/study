# HF Security
encrypt.o: encrypt.c
	gcc -c encrypt.c -o encrypt.o

checksum.o: checksum.c
	gcc -c checksum.c -o checksum.o

libhfsecurity.a: encrypt.o checksum.o
	ar -rcs libhfsecurity.a encrypt.o checksum.o

bank_vault: bank_vault.c libhfsecurity.a
	gcc bank_vault.c -I. -L. -lhfsecurity -o bank_vault

# HF Cal
#hfcal.o: hfcal.c
#	gcc -fPIC -I. -c hfcal.c -o hfcal.o

hfcal.o: hfcal_UK.c
	gcc -fPIC -I. -c hfcal_UK.c -o hfcal.o

# libhfcal.a: hfcal.o
# 	ar -rcs libhfcal.a hfcal.o

libhfcal.so: hfcal.o
	gcc -shared hfcal.o -o libhfcal.so

elliptical.o: elliptical.c
	gcc -I. -c elliptical.c -o elliptical.o

elliptical: elliptical.o libhfcal.so
	gcc elliptical.o -L. -lhfcal -o elliptical
