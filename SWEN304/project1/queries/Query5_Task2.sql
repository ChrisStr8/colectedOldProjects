SELECT DISTINCT RobberId, Nickname
FROM Robbers, Banks
WHERE (RobberId, BankName, City) IN (SELECT RobberId, BankName, City FROM HasAccounts)
   AND (RobberId, BankName, City) NOT IN (SELECT RobberId, BankName, City FROM Accomplices);