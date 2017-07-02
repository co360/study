;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname exercise-264) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 264


; A [NEList-of X] is one of:
; - (cons X '())
; - (cons X [NEList-of X])
; interpretation non-empty lists of X


; Nelon -> Number
; determines the largest 
; number on l
(define (sup l)
  (cond [(empty? (rest l)) (first l)]
        [else
         (local ((define smallest-in-rest (sup (rest l))))
           (if (> (first l) smallest-in-rest)
               (first l)
               smallest-in-rest))]))

(= (sup (list 2 1 3)) 3)