SELECT BankName, City, Date
FROM Robberies
WHERE (BankName, City, Date) IN
     (SELECT BankName, City, Date FROM Accomplices WHERE Share = (SELECT MAX(share) FROM Accomplices));
--could simplify to only the select statement in the where section but that 
--would be selecting from Accomplices not Robberies
