function copy(int[] src, int sStart, int[] dest, int dStart, int len)
             -> (int[] r)
// starting points in both arrays cannot be negative
requires sStart >= 0
requires dStart >= 0
// Source array must contain enough elements to be copied
requires (sStart + len) <= |src|
// Destination array must have enough space for copied elements
requires (dStart + len) <= |dest|
// Result is same size as dest
ensures |r| == |dest|
// All elements before copied region are same
ensures all { i in 0 .. dStart | dest[i] == r[i] }
// All elements in copied region match src
ensures all { i in 0 .. len | dest[i+dStart] == src[i+sStart] }
// All elements above copied region are same
ensures all { i in (dStart+len) .. |dest| | dest[i] == r[i] }:
    //
    int i = 0    
    int[] odest = dest
    int srcEnd = sStart + len
    //
    while i < len
    where i >= 0 && |dest| == |odest|
    where all { k in 0..dStart | dest[k] == odest[k] }
    where all { k in 0..i | dest[dStart+i] == src[sStart+i] }
    where all { k in (dStart+len) .. |dest| | dest[k] == odest[k] }:
        dest[dStart+i] = src[sStart+i]
        i = i + 1
    //
    return dest