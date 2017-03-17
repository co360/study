;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname exercise-25) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; Exercise 25

(require 2htdp/image)

(define (image-classify img)
  (cond
    [(> (image-height img) (image-width img))
     "tall"]
    [(= (image-height img) (image-width img))
     "square"]
    [(< (image-height img) (image-width img))
     "wide"]))

(image-classify (circle 3 "solid" "red"))
