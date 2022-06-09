function mid(int x,int y, int z) -> (int r)
requires x != y && y != z && z != x
ensures r == x || r == y || r == z
ensures y <= r && r <= z || z <= r && r <= y || //r==x
        x <= r && r <= z || z <= r && r <= x || //r==y
        y <= r && r <= x || x <= r && r <= y    //r==z
:
    if (x > y && x < z || x < y && x > z):
        return x
    else if (y > x && y < z || y < x && y > z):
        return y
    else if (z > y && z < x || x < y && z > x):
        return z
    else:
        return y
