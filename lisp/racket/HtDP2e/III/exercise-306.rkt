;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname exercise-306) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 306

(require 2htdp/abstraction)


; Number -> [List-of Number]
; creates the list (list 0 ... (- n 1)) for any natural number n
(check-expect (f1 5) (list 0 1 2 3 4))

(define (f1 n)
  (for/list ([x n])
    x))

; Number -> [List-of Number]
; creates the list (list 1 ... n) for any natural number n
(check-expect (f2 5) (list 1 2 3 4 5))

(define (f2 n)
  (for/list ([x n])
    (add1 x)))

; Number -> [List-of Number]
; creates the list (list 1 1/2 ... 1/n) for any natural number n
(check-expect (f3 5) (list 1/1 1/2 1/3 1/4 1/5))

(define (f3 n)
  (for/list ([x n])
    (/ 1 (add1 x))))

; Number -> [List-of Number]
; creates the list of the first n even numbers
(check-expect (f4 5) (list 0 2 4 6 8))

(define (f4 n)
  (for/list ([x n])
    (* x 2)))

; Number -> [List-of [List-of Number]]
; creates a diagonal square of 0s and 1s
(check-expect (f5 3) (list
                      (list 1 0 0)
                      (list 0 1 0)
                      (list 0 0 1)))

(define (f5 n)
  (local ((define (numbers n s)
            (cond [(zero? n) empty]
                  [else
                   (cons (if (= n s) 1 0)
                         (numbers (sub1 n) s))])))
    (for/list ([x n])
      (numbers n (- n x)))))

; [Number -> X] Number -> [List-of X]
; tabulates a f function n times
(check-expect (tabulate add1 5) (list 6 5 4 3 2 1))
(check-expect (tabulate number->string 5) (list "5" "4" "3" "2" "1" "0"))

(define (tabulate f n)
  (reverse (for/list ([x (add1 n)])
             (f x))))