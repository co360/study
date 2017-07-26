;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname fold-dir) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; fold-dir.rkt
;; Abstraction P8 - Fold Directory
;; Design an abstract fold function for Dir and some functions that
;; uses this abstract function.

(require 2htdp/image)


;; In this exercise you will be need to remember the following DDs for
;; an image organizer.


;; =================
;; Data definitions:

(define-struct dir (name sub-dirs images))
;; Dir is (make-dir String ListOfDir ListOfImage)
;; interp. An directory in the organizer, with a name, a list
;;         of sub-dirs and a list of images.

;; ListOfDir is one of:
;;  - empty
;;  - (cons Dir ListOfDir)
;; interp. A list of directories, this represents the sub-directories of
;;         a directory.

;; ListOfImage is one of:
;;  - empty
;;  - (cons Image ListOfImage)
;; interp. a list of images, this represents the sub-images of a directory.
;; NOTE: Image is a primitive type, but ListOfImage is not.

(define I1 (square 10 "solid" "red"))
(define I2 (square 12 "solid" "green"))
(define I3 (rectangle 13 14 "solid" "blue"))
(define D4 (make-dir "D4" empty (list I1 I2)))
(define D5 (make-dir "D5" empty (list I3)))
(define D6 (make-dir "D6" (list D4 D5) empty))

(define (fn-for-dir d)
  (local ((define (fn-for-dir d)
            (... (dir-name d)
                 (fn-for-lod (dir-sub-dirs d))
                 (fn-for-loi (dir-images d))))
          (define (fn-for-lod lod)
            (cond [(empty? lod) (...)]
                  [else
                   (... (fn-for-dir (first lod))
                        (fn-for-lod (rest lod)))]))
          (define (fn-for-loi loi)
            (cond [(empty? loi) (...)]
                  [else
                   (... (first loi)
                        (fn-for-loi (rest loi)))])))
    (fn-for-dir d)))


;; =================
;; Functions:

;; PROBLEM A:
;;
;; Design an abstract fold function for Dir called fold-dir.


;; PROBLEM B:
;;
;; Design a function that consumes a Dir and produces the number of
;; images in the directory and its sub-directories. Use the fold-dir
;; abstract function.


;; PROBLEM C:
;;
;; Design a function that consumes a Dir and a String. The function
;; looks in dir and all its sub-directories for a directory with the
;; given name. If it finds such a directory it should produce true, if
;; not it should produce false. Use the fold-dir abstract function.


;; PROBLEM D:
;;
;; Is fold-dir really the best way to code the function from part C?
;; Why or why not?