;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname exercise-74) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 74

(require 2htdp/image)
(require 2htdp/universe)


; Constants
(define MTS (empty-scene 100 100))
(define DOT (circle 3 "solid" "red"))


; A Posn represents the state of the world.


; Posn -> Posn
; starts the world with (main (make-posn 0 0))
(define (main p0)
  (big-bang p0
            [on-tick x+]
            [on-mouse reset-dot]
            [to-draw scene+dot]))

; Posn -> Posn
; increases the x-coordinate of p by 3
(check-expect (x+ (make-posn 10 0)) (make-posn 13 0))

(define (x+ p)
  (posn-up-x p (+ (posn-x p) 3)))

; Posn Number -> Posn
; produces a posn like p with n in the x field
(check-expect (posn-up-x (make-posn 2 3) 4) (make-posn 4 3))

(define (posn-up-x p n)
  (make-posn n (posn-y p)))

; Posn Number Number MouseEvt -> Posn
; for mouse clicks, (make-posn x y); otherwise p
(check-expect (reset-dot (make-posn 10 20) 29 31 "button-down")
              (make-posn 29 31))
(check-expect (reset-dot (make-posn 10 20) 29 31 "button-up")
              (make-posn 10 20))

(define (reset-dot p x y me)
  (cond
    [(mouse=? me "button-down") (make-posn x y)]
    [else p]))

; Posn -> Image
; adds a red spot to MTS at p
(check-expect (scene+dot (make-posn 10 20))
              (place-image DOT 10 20 MTS))
(check-expect (scene+dot (make-posn 88 73))
              (place-image DOT 88 73 MTS))

(define (scene+dot p)
  (place-image DOT (posn-x p) (posn-y p) MTS))
