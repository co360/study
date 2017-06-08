;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname exercise-242) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 242


; A [Maybe X] is one of:
; - #false
; - X

; [Maybe String]           -> optional String
; [Maybe [List-of String]] -> optional list of String
; [List-of [Maybe String]] -> list of optional String


; String [List-of String] -> [Maybe [List-of String]]
; returns the remainder of los starting with s
; #false otherwise
(check-expect (occurs "a" (list "b" "a" "d" "e")) (list "d" "e"))
(check-expect (occurs "a" (list "b" "c" "d")) #f)

(define (occurs s los)
  (cond [(empty? los) #false]
        [(string=? (first los) s) (rest los)]
        [else (occurs s (rest los))]))
