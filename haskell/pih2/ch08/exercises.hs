-- 8.1
data Nat = Zero | Succ Nat
  deriving (Show, Eq)

add :: Nat -> Nat -> Nat
add Zero n = n
add (Succ m) n = Succ(add m n)

mult :: Nat -> Nat -> Nat
mult Zero n = Zero
mult m Zero = Zero
mult m (Succ n) = if n == Zero then m else mult (add m m) n

-- 8.2
data Tree a = Leaf a | Node (Tree a) a (Tree a)

t :: Tree Int
t = Node (Node (Leaf 1) 3 (Leaf 4)) 5 (Node (Leaf 6) 7 (Leaf 9))

occurs :: Ord a => a -> Tree a -> Bool
occurs x (Leaf y) = x == y
occurs x (Node l y r) = case compare x y of
                          EQ -> True
                          LT -> occurs x l
                          GT -> occurs x r
