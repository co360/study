-- 6.1
fac :: Int -> Int
fac n
  | n < 0 = 1
  | n == 0 = 1
  | otherwise = n * fac(n-1)
