function append(int[] items, int item) -> (int[] rs)
ensures all { k in 0..|items| | rs[k] == items[k] }
ensures rs[|items|] == item:
    //
    int[] nitems = [0; |items| + 1]
    int i = 0
    //
    while i < |items| 
    where i >= 0 && |items| < |nitems| && i <= |items|
    where all {k in 0..i | nitems[k] == items[k]}:
        nitems[i] = items[i]
        i = i + 1
    //
    nitems[i] = item    
    //
    return nitems