SELECT RobberId, Nickname, (Age - NoYears) as NoYearsNot
FROM Robbers
WHERE NoYears > Age / 2;