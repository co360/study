;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-abbr-reader.ss" "lang")((modname bst-dd) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;; bst-dd.rkt


;; PROBLEM:
;;
;; Design a data definition to represent binary search trees. As a reminder,
;; here is one example BST:
;;               10:why
;;      /                   \
;;    3:ilk                42:ily
;;   /     \              /      \
;; 1:abc   4:dcj       27:wit   50:dug
;;           \          /
;;           7:ruf   14:olp
;;
;; at each level:
;;  - all accounts in left sub-tree have account number less than root
;;  - all accounts in right sub-tree have account number greater than root

(define-struct node (key val l r))
;; BST (Binary Search Tree) is one of:
;;  - false
;;  - (make-node Integer String BST BST)
;; interp. false means no BST, or empty BST
;;         key is the node key
;;         val is the node val
;;         l and r are left and right subtrees
;; INVARIANT: for a given node
;;     key is > all keys in its l(eft)  child
;;     key is < all keys in its r(ight) child
;;     the same key never appears twice in the tree
(define BST0 false)
(define BST1 (make-node 1 "abc" false false))
(define BST4 (make-node 4 "dcj" false (make-node 7 "ruf" false false)))
(define BST3 (make-node 3 "ilk" BST1 BST4))
(define BST42 (make-node 42 "ily"
                         (make-node 27 "wit" (make-node 14 "olp" false false) false)
                         (make-node 50 "dug" false false)))
(define BST10 (make-node 10 "why" BST3 BST42))

#;
(define (fn-for-bst t)
  (cond [(false? t) (...)]
        [else
         (... (node-key t)
              (node-val t)
              (fn-for-bst (node-l t))
              (fn-for-bst (node-r t)))]))

;; Template rules used:
;;  - one of: 2 cases
;;  - atomic-distinct: false
;;  - compound: (make-node Integer String BST BST)
;;  - self reference: (node-l t) has type BST
;;  - self reference: (node-r t) has type BST
