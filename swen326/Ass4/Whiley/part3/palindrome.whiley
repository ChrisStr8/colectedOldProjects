function isPalindrome(int[] chars) -> (bool r)
ensures r <==> all { k in 0..|chars| | chars[k] == chars[|chars|-(k+1)] }:
    //
    int i = 0
    int j = |chars|
    //
    while i < j 
    where i >= 0 && j <= |chars| && i <= j
    where all {k in 0..i | chars[k] == chars[|chars|-(k+1)]}:
        j = j - 1
        if chars[i] != chars[j]:
            return false
        i = i + 1
    //
    return true