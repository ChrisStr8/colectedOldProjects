SELECT BankName, City
FROM Banks
WHERE (BankName, City) NOT IN
   (SELECT BankName, City
   FROM Robberies
   WHERE (BankName, City, EXTRACT(YEAR FROM Date)) IN
       (SELECT BankName, City, EXTRACT(YEAR FROM PlannedDate)
       FROM Plans)
   );