function insert(int[] items, int pos, int item) -> (int[] ys)
requires pos <= |items|
requires pos >= 0
ensures |ys| == |items| + 1
ensures ys[pos] == item
ensures all {k in 0..pos | items[k] == ys[k]}
ensures all {k in pos+1..|ys| | items[k-1] == ys[k]}
:
    ys = [0; |items| + 1]
    ys[pos] = item
    int i = 0
    //
    while i < |ys|
    where i >= 0 && i <= |ys|
    where i <= |items|+1:
        if i < pos:
            ys[i] = items[i]
        else if i == pos:
            ys[i] = item
        else if i > pos && i-1 < |items|:
            ys[i] = items[i-1]
        i = i + 1
	//
    return ys
