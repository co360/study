;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname glue-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; glue.rkt
;; BSL P4
;; Write expressions to operate on strings using primitives.


;; PROBLEM:
;;
;; Write an expression that sticks the strings "Super" "Glue" together
;; into a single string "Super Glue" with a space between the two
;; words.

(string-append "Super" " " "Glue")
