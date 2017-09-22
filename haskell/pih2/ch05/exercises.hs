-- 5.1
res = [x | x <- [12,22..1002]]

-- 5.2
grid :: Int -> Int -> [(Int, Int)]
grid x y = [(a, b) | a <- [0..x], b <- [0..y]]

-- 5.3
square :: Int -> [(Int, Int)]
square x = [(a, b) | a <- [0..x], b <- [0..x], a /= b]

-- 5.4
replicate' :: Int -> a -> [a]
replicate' n t = [t | _ <- [1..n]]
