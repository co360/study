;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname space-invaders) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; space-invaders.rkt

(require 2htdp/universe)
(require 2htdp/image)


;; Space Invaders

;; =================
;; Constants:

(define WIDTH  300)
(define HEIGHT 500)
(define BACKGROUND (empty-scene WIDTH HEIGHT))

(define INVADER-X-SPEED 1.5)  ;speeds (not velocities) in pixels per tick
(define INVADER-Y-SPEED 1.5)
(define MISSILE-SPEED 10)
(define TANK-SPEED 2)

(define HIT-RANGE 10)

(define INVADE-RATE 100)
(define MISSILE-RATE 100)

(define INVADER
  (overlay/xy (ellipse 10 15 "outline" "blue")              ;cockpit cover
              -5 6
              (ellipse 20 10 "solid"   "blue")))            ;saucer
(define MISSILE (ellipse 5 15 "solid" "red"))
(define TANK
  (overlay/xy (overlay (ellipse 28 8 "solid" "black")       ;tread center
                       (ellipse 30 10 "solid" "green"))     ;tread outline
              5 -14
              (above (rectangle 5 10 "solid" "black")       ;gun
                     (rectangle 20 10 "solid" "black"))))   ;main body

(define TANK-HEIGHT/2 (/ (image-height TANK) 2))
(define INVADER-HEIGHT/2 (/ (image-height TANK) 2))
(define MISSILE-STARTING-Y-POSITION (+ TANK-HEIGHT/2 20))
(define INVADER-STARTING-Y-POSITION (+ INVADER-HEIGHT/2 20))


;; =================
;; Data definitions:

(define-struct game (invaders missiles tank))
;; Game is (make-game (listof Invader) (listof Missile) Tank)
;; interp. the current state of a space invaders game
;;         with the current invaders, missiles and tank position
;; Game constants defined below Missile data definition

#;
(define (fn-for-game g)
  (... (fn-for-loinvader (game-invaders g))
       (fn-for-lom       (game-missiles g))
       (fn-for-tank      (game-tank     g))))

(define-struct invader (x y dx))
;; Invader is (make-invader Number Number Number)
;; interp. the invader is at (x, y) in screen coordinates
;;         the invader along x by dx pixels per clock tick
(define I1 (make-invader 150 100 12))           ;not landed, moving right
(define I2 (make-invader 150 HEIGHT -10))       ;exactly landed, moving left
(define I3 (make-invader 150 (+ HEIGHT 10) 10)) ;> landed, moving right

#;
(define (fn-for-invader i)
  (... (invader-x  i)
       (invader-y  i)
       (invader-dx i)))

(define-struct missile (x y))
;; Missile is (make-missile Number Number)
;; interp. the missile's location is x y in screen coordinates
(define M1 (make-missile 150 300))                               ;not hit U1
(define M2 (make-missile (invader-x I1) (+ (invader-y I1) 10)))  ;exactly hit U1
(define M3 (make-missile (invader-x I1) (+ (invader-y I1)  5)))  ;> hit U1

#;
(define (fn-for-missile m)
  (... (missile-x m)
       (missile-y m)))

(define-struct tank (x dir))
;; Tank is (make-tank Number Integer[-1, 1])
;; interp. the tank location is x, HEIGHT - TANK-HEIGHT in screen coordinates
;;         the tank moves TANK-SPEED pixels per clock tick left if dir -1, right if dir 1
;; INVARIANT: for a given tank:
;;     the x-coordinate is never less than 0 or greater than WIDTH
(define T0 (make-tank (/ WIDTH 2) 1))   ;center going right
(define T1 (make-tank 50 1))            ;going right
(define T2 (make-tank 50 -1))           ;going left

#;
(define (fn-for-tank t)
  (... (tank-x   t)
       (tank-dir t)))

(define G0 (make-game empty empty T0))
(define G1 (make-game empty empty T1))
(define G2 (make-game (list I1) (list M1) T1))
(define G3 (make-game (list I1 I2) (list M1 M2) T1))


;; =================
;; Functions:

;; Game -> Game
;; start the world with (main G0)
;;
(define (main g)
  (big-bang g                        ; Game
            (on-tick   tock)         ; Game -> Game
            (to-draw   render)       ; Game -> Image
            (stop-when game-over?)   ; Game -> Boolean
            (on-key    controller))) ; Game KeyEvent -> Game

;; Game -> Game
;; produce the next game update
(check-random (tock G0)
              (make-game (create-invader (game-invaders G0)) empty (move-tank (game-tank G0))))
(check-random (tock G1)
              (make-game (create-invader (game-invaders G1)) empty (move-tank (game-tank G1))))

;(define (tock g) G0) ; Stub

(define (tock g)
  (make-game (create-invader (move-invaders (do-invader (game-invaders g) (game-missiles g))))
             (clear-missiles (move-missiles (do-missile (game-missiles g) (game-invaders g))))
             (move-tank (game-tank g))))

;; ListofInvader ListofMissile -> ListofInvader
;; remove invaders hit by missiles
(check-expect (do-invader empty empty) empty)
(check-expect (do-invader (list I1) empty) (list I1))
(check-expect (do-invader (list I1 I2) empty) (list I1 I2))
(check-expect (do-invader (list I1 I2) (list M1)) (list I1 I2))
(check-expect (do-invader (list I1 I2) (list M1 M2)) (list I2))

;(define (do-invader loi lom) empty) ; Stub

(define (do-invader loi lom)
  (cond [(empty? loi) empty]
        [else
         (if (hit-invader? (first loi) lom)
             (do-invader (rest loi) lom)
             (cons (first loi)
                   (do-invader (rest loi) lom)))]))

;; Invader ListofMissile -> Boolean
;; produce true if the invader was hit by some missile
(check-expect (hit-invader? I1 empty) false)
(check-expect (hit-invader? I1 (list M1)) false)
(check-expect (hit-invader? I1 (list M1 M2)) true)
(check-expect (hit-invader? I2 (list M1 M2)) false)

;(define (hit-invader? i lom) false) ; Stub

(define (hit-invader? i lom)
  (cond [(empty? lom) false]
        [else
         (or (hit? i (first lom))
             (hit-invader? i (rest lom)))]))

;; Invader Missile -> Boolean
;; produce true if invader is hit by the missile within the HIT-RANGE
(check-expect (hit? I1 M1) false)
(check-expect (hit? I1 M2) true)
(check-expect (hit? I1 M3) true)
(check-expect (hit? I2 M1) false)
(check-expect (hit? I2 M2) false)
(check-expect (hit? I2 M3) false)

;(define (hit? i m) false) ; Stub

(define (hit? i m)
  (and (<= (abs (- (invader-x i)
                   (missile-x m)))
           HIT-RANGE)
       (<= (abs (- (invader-y i)
                   (missile-y m)))
           HIT-RANGE)))

;; ListofInvader -> ListofInvader
;; create a new invader on INVADE-RATE from the first invader on the list
(check-random (create-invader empty)
              (list (make-invader (random WIDTH) INVADER-STARTING-Y-POSITION (- (random 50) 25))))
(check-expect (create-invader (list I1)) (list I1))
(check-random (create-invader (list (make-invader 10 101 10)))
              (list (make-invader (random WIDTH) INVADER-STARTING-Y-POSITION (- (random 50) 25))
                    (make-invader 10 101 10)))

;(define (create-invader loi) empty) ; Stub

(define (create-invader loi)
  (cond [(empty? loi)
         (list (make-invader (random WIDTH) INVADER-STARTING-Y-POSITION (- (random 50) 25)))]
        [else
         (if (> (invader-y (first loi)) INVADE-RATE)
             (cons (make-invader (random WIDTH) INVADER-STARTING-Y-POSITION (- (random 50) 25)) loi)
             loi)]))

;; ListofInvader -> ListofInvader
;; move the invaders from the list
(check-expect (move-invaders empty) empty)
(check-expect (move-invaders (list I1 I2)) (list (move-invader I1) (move-invader I2)))

;(define (move-invaders loi) empty) ; Stub

(define (move-invaders loi)
  (cond [(empty? loi) empty]
        [else
         (cons (move-invader (first loi))
               (move-invaders (rest loi)))]))

;; Invader -> Invader
;; move the invader in INVADER-X-SPEED and INVADER-Y-SPEED
(check-expect (move-invader I1)
              (make-invader (next-x-invader I1) (next-y-invader I1) 12))
(check-expect (move-invader I2)
              (make-invader (next-x-invader I2) (next-y-invader I2) -10))
(check-expect (move-invader (make-invader WIDTH 20 10))
              (make-invader WIDTH (+ 20 INVADER-Y-SPEED) -10))
(check-expect (move-invader (make-invader 0 20 -10))
              (make-invader 0 (+ 20 INVADER-Y-SPEED) 10))

;(define (move-invader i) i) ; Stub

(define (move-invader i)
  (cond [(< (next-x-invader i) 0)
         (make-invader 0 (next-y-invader i) (* (invader-dx i) -1))]
        [(> (next-x-invader i) WIDTH)
         (make-invader WIDTH (next-y-invader i) (* (invader-dx i) -1))]
        [else
         (make-invader (next-x-invader i) (next-y-invader i) (invader-dx i))]))

;; Invader -> Number
;; produce the next point of invader in x
(check-expect (next-x-invader I1) (+ 150 INVADER-X-SPEED 12))
(check-expect (next-x-invader I2) (+ 150 (* INVADER-X-SPEED -1) -10))

;(define (next-x-invader i) 0) ; Stub

(define (next-x-invader i)
  (+ (invader-x i)
     (if (negative? (invader-dx i))
         (* INVADER-X-SPEED -1)
         INVADER-X-SPEED)
     (invader-dx i)))

;; Invader -> Number
;; produce the next point of invader in y
(check-expect (next-y-invader I1) (+ 100 INVADER-Y-SPEED))
(check-expect (next-y-invader I2) (+ HEIGHT INVADER-Y-SPEED))

;(define (next-y-invader i) 0) ; Stub

(define (next-y-invader i)
  (+ (invader-y i) INVADER-Y-SPEED))

;; ListofMissile ListofInvader -> ListofMissile
;; remove the missiles that hit the invaders
(check-expect (do-missile empty empty) empty)
(check-expect (do-missile (list M1) empty) (list M1))
(check-expect (do-missile (list M1) (list I1)) (list M1))
(check-expect (do-missile (list M1) (list I1 I2)) (list M1))
(check-expect (do-missile (list M1 M2) (list I1 I2)) (list M1))
(check-expect (do-missile (list M1 M2) (list I2)) (list M1 M2))

;(define (do-missile loi lom) empty) ; Stub

(define (do-missile lom loi)
  (cond [(empty? lom) empty]
        [else
         (if (hit-missile? (first lom) loi)
             (do-missile (rest lom) loi)
             (cons (first lom)
                   (do-missile (rest lom) loi)))]))

;; Missile ListofInvader -> Boolean
;; produce true if the missile hit some invader
(check-expect (hit-missile? M1 empty) false)
(check-expect (hit-missile? M1 (list I1 I2 I3)) false)
(check-expect (hit-missile? M2 (list I1 I2 I3)) true)
(check-expect (hit-missile? M2 (list I2 I3)) false)
(check-expect (hit-missile? M3 (list I1 I2 I3)) true)
(check-expect (hit-missile? M3 (list I2 I3)) false)

;(define (hit-missile? m loi) false) ; Stub

(define (hit-missile? m loi)
  (cond [(empty? loi) false]
        [else
         (or (hit? (first loi) m)
             (hit-missile? m (rest loi)))]))

;; ListofMissile -> ListofMissile
;; remove missiles that have left the screen
(check-expect (clear-missiles empty) empty)
(check-expect (clear-missiles (list M1)) (list M1))
(check-expect (clear-missiles (list M1 (make-missile 40 -1))) (list M1))

;(define (clear-missiles lom) empty) ; Stub

(define (clear-missiles lom)
  (cond [(empty? lom) empty]
        [else
         (if (< (missile-y (first lom)) 0)
             (clear-missiles (rest lom))
             (cons (first lom)
                   (clear-missiles (rest lom))))]))

;; ListofMissile -> ListofMissile
;; move the missiles from the list
(check-expect (move-missiles empty) empty)
(check-expect (move-missiles (list M1 M2)) (list (move-missile M1) (move-missile M2)))

;(define (move-missiles lom) empty) ; Stub

(define (move-missiles lom)
  (cond [(empty? lom) empty]
        [else
         (cons (move-missile (first lom))
               (move-missiles (rest lom)))]))

;; Missile -> Missile
;; move the missile by MISSILE-SPEED in the y coordinate
(check-expect (move-missile M1) (make-missile 150 (- 300 MISSILE-SPEED)))

;(define (move-missile m) m) ; Stub

(define (move-missile m)
  (make-missile (missile-x m) (- (missile-y m) MISSILE-SPEED)))

;; Tank -> Tank
;; move the tank by TANK-SPEED in the x coordinate
(check-expect (move-tank T0) (make-tank (next-x-tank T0)  1))
(check-expect (move-tank T1) (make-tank (next-x-tank T1)  1))
(check-expect (move-tank T2) (make-tank (next-x-tank T2) -1))
(check-expect (move-tank (make-tank 0 -1)) (make-tank 0 -1))
(check-expect (move-tank (make-tank WIDTH 1)) (make-tank WIDTH 1))

;(define (move-tank t) t) ; Stub

(define (move-tank t)
  (make-tank
   (cond [(< (next-x-tank t) 0) 0]
         [(> (next-x-tank t) WIDTH) WIDTH]
         [else (next-x-tank t)])
   (tank-dir t)))

;; Tank -> Number
;; produce the next point of tank in x
(check-expect (next-x-tank T1) (+ 50 TANK-SPEED))
(check-expect (next-x-tank T2) (- 50 TANK-SPEED))

;(define (next-x-tank t) 0) ; Stub

(define (next-x-tank t)
  (+ (tank-x t) (* (tank-dir t) TANK-SPEED)))

;; Game -> Image
;; render the Game in BACKGROUND
(check-expect (render G0)
              (place-image TANK (/ WIDTH 2) (- HEIGHT TANK-HEIGHT/2) BACKGROUND))
(check-expect (render G1)
              (place-image TANK 50 (- HEIGHT TANK-HEIGHT/2) BACKGROUND))
(check-expect (render G2)
              (place-image
               INVADER 150 100
               (place-image
                MISSILE 150 300
                (place-image TANK 50 (- HEIGHT TANK-HEIGHT/2) BACKGROUND))))
(check-expect (render G3)
              (place-image
               INVADER 150 100
               (place-image
                INVADER 150 HEIGHT
                (place-image
                 MISSILE 150 300
                 (place-image
                  MISSILE 150 110
                  (place-image TANK 50 (- HEIGHT TANK-HEIGHT/2) BACKGROUND))))))

;(define (render g) BACKGROUND) ; Stub

(define (render g)
  (render-invaders (game-invaders g)
                   (render-missiles (game-missiles g)
                                    (render-tank (game-tank g) BACKGROUND))))

;; ListofInvader Image - > Image
;; render the invaders in the image
(check-expect (render-invaders empty BACKGROUND) BACKGROUND)
(check-expect (render-invaders (list I1) BACKGROUND)
              (place-image INVADER 150 100 BACKGROUND))
(check-expect (render-invaders (list I1 I2) BACKGROUND)
              (place-image
               INVADER 150 100
               (place-image
                INVADER 150 HEIGHT
                BACKGROUND)))

;(define (render-invaders loi i) BACKGROUND) ; Stub

(define (render-invaders loi i)
  (cond [(empty? loi) i]
        [else
         (place-image INVADER
                      (invader-x (first loi))
                      (invader-y (first loi))
                      (render-invaders (rest loi) i))]))

;; ListofMissile Image - > Image
;; render the missiles in the image
(check-expect (render-missiles empty BACKGROUND) BACKGROUND)
(check-expect (render-missiles (list M1) BACKGROUND)
              (place-image MISSILE 150 300 BACKGROUND))
(check-expect (render-missiles (list M1 M2) BACKGROUND)
              (place-image
               MISSILE 150 300
               (place-image
                MISSILE 150 110
                BACKGROUND)))

;(define (render-missiles lom i) BACKGROUND) ; Stub

(define (render-missiles lom i)
  (cond [(empty? lom) i]
        [else
         (place-image MISSILE
                      (missile-x (first lom))
                      (missile-y (first lom))
                      (render-missiles (rest lom) i))]))

;; Tank Image - > Image
;; render the tank in the image
(check-expect (render-tank T0 BACKGROUND)
              (place-image TANK (/ WIDTH 2) (- HEIGHT TANK-HEIGHT/2) BACKGROUND))

;(define (render-tank t i) BACKGROUND) ; Stub

(define (render-tank t i)
  (place-image TANK (tank-x t) (- HEIGHT TANK-HEIGHT/2) i))

;; Game -> Boolean
;; produce true when the game is over
(check-expect (game-over? G0) false)
(check-expect (game-over? G1) false)
(check-expect (game-over? G2) false)
(check-expect (game-over? G3) false)
(check-expect (game-over? (make-game (list I1 I2 I3) (list M1 M2) T1)) true)

;(define (game-over? g) false) ; Stub

(define (game-over? s)
  (cond [(empty? (game-invaders s)) false]
        [else
         (any>landed? (game-invaders s))]))

;; ListofInvader -> Boolean
;; produce true when any invader is greater than landed
(check-expect (any>landed? empty) false)
(check-expect (any>landed? (list I1 I2)) false)
(check-expect (any>landed? (list I3 I1 I2)) true)
(check-expect (any>landed? (list I1 I3 I2)) true)
(check-expect (any>landed? (list I1 I2 I3)) true)

;(define (any>landed? invaders) false) ; Stub

(define (any>landed? invaders)
  (cond [(empty? invaders) false]
        [else
         (or (>landed? (first invaders))
             (any>landed? (rest invaders)))]))

;; Invader -> Boolean
;; produce true when the invader-y is greater then HEIGHT
(check-expect (>landed? I1) false)
(check-expect (>landed? I2) false)
(check-expect (>landed? I3) true)

;(define (>landed? invader) false) ; Stub

(define (>landed? invader)
  (> (invader-y invader) HEIGHT))

;; Game KeyEvent -> Game
;; handle the keyboard events:
;;  - left and right arrows: change the tank direction
;;  - space bar: launch missiles
(check-expect (controller G0 "a") G0)
(check-expect (controller (make-game empty empty T2) "left")
              (make-game empty empty T2))
(check-expect (controller (make-game empty empty T1) "left")
              (make-game empty empty T2))
(check-expect (controller (make-game empty empty T1) "right")
              (make-game empty empty T1))
(check-expect (controller (make-game empty empty T2) "right")
              (make-game empty empty T1))
(check-expect (controller G0 " ")
              (make-game empty (list (make-missile (/ WIDTH 2) (- HEIGHT MISSILE-STARTING-Y-POSITION))) (game-tank G0)))
(check-expect (controller G2 " ")
              (make-game (list I1) (list (make-missile 50 (- HEIGHT MISSILE-STARTING-Y-POSITION)) M1) (game-tank G2)))
(check-expect (controller G3 " ")
              (make-game (list I1 I2) (list (make-missile 50 (- HEIGHT MISSILE-STARTING-Y-POSITION)) M1 M2) (game-tank G3)))

;(define (controller g ke) G0) ; Stub

(define (controller g ke)
  (cond [(key=? ke "left")
         (make-game (game-invaders g)
                    (game-missiles g)
                    (change-tank-left (game-tank g)))]
        [(key=? ke "right")
         (make-game (game-invaders g)
                    (game-missiles g)
                    (change-tank-right (game-tank g)))]
        [(key=? ke " ")
         (make-game (game-invaders g)
                    (launch-missile (game-missiles g) (tank-x (game-tank g)))
                    (game-tank g))]
        [else g]))

;; Tank -> Tank
;; change the tank direction to the left (-1)
(check-expect (change-tank-left T0) (make-tank (/ WIDTH 2) -1))
(check-expect (change-tank-left T1) T2)
(check-expect (change-tank-left T2) T2)

;(define (change-tank-left t) t) ; Stub

(define (change-tank-left t)
  (make-tank (tank-x t) -1))

;; Tank -> Tank
;; change the tank direction to the right (1)
(check-expect (change-tank-right T0) T0)
(check-expect (change-tank-right T1) T1)
(check-expect (change-tank-right T2) T1)

;(define (change-tank-right t) t) ; Stub

(define (change-tank-right t)
  (make-tank (tank-x t) 1))

;; ListofMissile Number -> ListofMissile
;; insert a new missile into the list
(check-expect (launch-missile empty 30)
              (list (make-missile 30 (- HEIGHT MISSILE-STARTING-Y-POSITION))))
(check-expect (launch-missile (list M1) 30)
              (list (make-missile 30 (- HEIGHT MISSILE-STARTING-Y-POSITION)) M1))
(check-expect (launch-missile (list (make-missile 30 (- HEIGHT MISSILE-RATE)) M1) 30)
              (list (make-missile 30 (- HEIGHT MISSILE-RATE)) M1))

;(define (launch-missile lom x) lom) ; Stub

(define (launch-missile lom x)
  (cond [(empty? lom)
         (list (make-missile x (- HEIGHT MISSILE-STARTING-Y-POSITION)))]
        [else
         (if (> (- HEIGHT (missile-y (first lom))) MISSILE-RATE)
             (cons (make-missile x (- HEIGHT MISSILE-STARTING-Y-POSITION)) lom)
             lom)]))
