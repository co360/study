;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname exercise-252) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 252

(require 2htdp/image)


; [List-of Number] -> Number
(define (product l)
  (cond
    [(empty? l) 1]
    [else
     (* (first l)
        (product (rest l)))]))
	
; [List-of Posn] -> Image
(define (image* l)
  (cond
    [(empty? l) emt]
    [else
     (place-dot (first l)
                (image* (rest l)))]))
 
; Posn Image -> Image 
(define (place-dot p img)
  (place-image dot (posn-x p) (posn-y p) img))

; graphical constants:    
(define emt (empty-scene 100 100))
(define dot (circle 3 "solid" "red"))


; [A B] (A B -> B) [List-of A] B -> B
; summarizes the list l with the function f and identity i
(check-expect (fold2 * (list 1 2 3) 1) 6)
(check-expect (fold2 place-dot (list (make-posn 3 5) (make-posn 7 2)) emt)
              (place-dot (make-posn 3 5) (place-dot (make-posn 7 2) emt)))

(define (fold2 f l i)
  (cond [(empty? l) i]
        [else (f (first l)
                 (fold2 f (rest l) i))]))