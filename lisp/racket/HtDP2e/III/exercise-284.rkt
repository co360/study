;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname exercise-284) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 284


((lambda (x) x) (lambda (x) x))
(((lambda (x) x)
  (lambda (x) x))
 10)

((lambda (x) (x x)) (lambda (x) x))
(((lambda (x) (x x))
  (lambda (x) x))
 10)

; Indirect self-reference
#;
((lambda (x) (x x))
 (lambda (x) (x x)))