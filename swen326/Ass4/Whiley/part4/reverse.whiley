function reverse(int[] xs) -> (int[] ys)
requires |xs| > 1
ensures |xs| == |ys|
ensures all {k in 0..|xs| | xs[k] == ys[|ys| - (k+1)]}:
    ys = [0; |xs|]
    int i = 0
    int j = |xs|
    //
    while i < |ys| && j > 0
    where |ys| == |xs|
    where i >= 0 && i <= |ys| 
    where j >= 0 && j <= |xs|
    where all {k in 0..i | ys[k] == xs[|xs|-(k+1)]}
    :
        j = j - 1
        ys[i] = xs[j]
        i = i + 1
    //
    return ys