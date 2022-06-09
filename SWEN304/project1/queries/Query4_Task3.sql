SELECT BankName, City FROM HasAccounts, Robbers
WHERE Robbers.Nickname = 'Al Capone'
 AND HasAccounts.RobberId = Robbers.RobberId;
