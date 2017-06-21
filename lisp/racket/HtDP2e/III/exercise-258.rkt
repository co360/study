;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname exercise-258) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 258

(require 2htdp/image)


; a plain background image 
(define MT (empty-scene 50 50))

; A Polygon is one of:
; - (list Posn Posn Posn)
; - (cons Posn Polygon)
(define P1 (list (make-posn 20 20) (make-posn 30 20) (make-posn 30 30)))


; Image Polygon -> Image 
; adds an image of p to MT
(define (render-polygon img p)
  (local (; Image Posn Posn -> Image 
          ; draws a red line from Posn p to Posn q into im
          (define (render-line im p q)
            (scene+line im (posn-x p) (posn-y p) (posn-x q) (posn-y q) "red"))
          ; Polygon -> Posn
          ; extracts the last item from p
          (define (last p)
            (cond [(empty? (rest (rest (rest p)))) (third p)]
                  [else (last (rest p))]))
          ; Image NELoP -> Image
          ; connects the Posns in p in an image
          (define (connect-dots img p)
            (cond [(empty? (rest p)) MT]
                  [else (render-line (connect-dots img (rest p))
                                     (first p)
                                     (second p))])))
    (render-line (connect-dots img p) (first p) (last p))))
  
(render-polygon MT P1)
