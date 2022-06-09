final int Jan = 1
final int Feb = 2
final int Mar = 3
final int Apr = 4
final int May = 5
final int Jun = 6
final int Jul = 7
final int Aug = 8
final int Sep = 9
final int Oct = 10
final int Nov = 11
final int Dec = 12

function getDate() -> (int day, int month, int year)
ensures ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day <= 31) ||
        ((month == 4 || month == 6 || month == 9 || month == 11) && day <= 30) ||
        (month == 2 && day <= 28)
:
    return 17, Sep, 2015
