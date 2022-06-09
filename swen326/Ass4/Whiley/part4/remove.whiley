function remove(int[] items, int index) -> (int[] r)
requires |items| > 0
requires index >= 0
requires index < |items|
ensures |items| == |r| + 1
ensures all { k in 0..index | items[k] == r[k] }
ensures all { k in index..|r| | items[k+1] == r[k] }:
    //
    int[] nitems = [0; |items|-1]    
    int i = 0
    //
    while i < index 
    where i >= 0 && i <= index
    where |nitems| == |items|-1
    where all { k in 0..i | items[k] == nitems[k] }:
        nitems[i] = items[i]
        i = i + 1
    //
    while i < |nitems| 
    where i >= index && |nitems| == |items|-1
    where all { k in 0..index | items[k] == nitems[k] }
    where all { k in index..i | nitems[k] == items[k+1] }:
        nitems[i] = items[i+1]
        i = i + 1
    //
    return nitems