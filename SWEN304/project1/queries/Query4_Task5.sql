SELECT Robbers.RobberId, Nickname, earnings
FROM Robbers, (SELECT RobberId, SUM(Share) as earnings FROM Accomplices GROUP BY RobberId) as etable
WHERE Robbers.RobberId = etable.RobberId
 AND earnings > 30000
ORDER BY earnings DESC;