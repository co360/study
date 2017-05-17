;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname quidditch-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; quidditch-starter.rkt


;; PROBLEM:
;;
;; Imagine that you are designing a program that will keep track of
;; your favorite Quidditch teams. (http://iqaquidditch.org/).
;;
;; Design a data definition to represent a list of Quidditch teams. 
;;
;; Information:                     Data:
;;  - UBC                        -> "UBC"
;;  - McGill                     -> "McGill"
;;  - Team Who Must Not Be Named -> "Team Who Must Not Be Named"
;;
;; empty
;; (cons "UBC"
;;       (cons "McGill" empty))


;; ListOfString is one of:
;;  - empty
;;  - (cons String ListOfString)
;; interp. a list of strings
(define LOS1 empty)
(define LOS2 (cons "McGill" empty))
(define LOS3 (cons "UBC" (cons "McGill" empty)))

#;
(define (fn-for-los los)
  (cond [(empty? los) (...)]
        [else
         (... (first los)
              (fn-for-los (rest los)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic distinct: empty
;;  - compound: (cons String ListOfString)


;; PROBLEM:
;;
;; We want to know whether your list of favorite Quidditch teams includes
;; UBC! Design a function that consumes ListOfString and produces true if 
;; the list includes "UBC".

;; ListOfString -> Boolean
;; produce true if los includes "UBC"
(check-expect (contains-ubc? LOS1) false)
(check-expect (contains-ubc? LOS2) false)
(check-expect (contains-ubc? LOS3) true)
(check-expect (contains-ubc? (cons "McGill" (cons "UBC" empty))) true)

; (define (contains-ubc? los) false) ; stub

(define (contains-ubc? los)
  (cond [(empty? los) false]
        [else
         (if (string=? (first los) "UBC")
             true
             (contains-ubc? (rest los)))]))
