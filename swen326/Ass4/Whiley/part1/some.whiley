function f(int[] items) -> (int r)
requires |items| > 0
ensures some { i in 0..|items| | items[i] == r }:
    int item = items[0]
    return item