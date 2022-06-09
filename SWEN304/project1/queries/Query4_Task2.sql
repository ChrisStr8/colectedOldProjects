SELECT Banks.BankName, Banks.City
FROM Banks
   LEFT JOIN Robberies
       ON (Banks.BankName, Banks.City) = (Robberies.BankName, Robberies.City)
WHERE (Robberies.BankName, Robberies.City) IS NULL;