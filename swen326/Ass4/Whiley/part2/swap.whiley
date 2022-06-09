function swap(int x, int y) -> (int a, int b)
ensures x == b && y == a:
    return y, x
