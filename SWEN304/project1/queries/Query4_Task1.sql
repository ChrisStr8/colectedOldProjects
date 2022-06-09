SELECT Robbers.RobberId, Nickname, Age, Description FROM
   (SELECT RobberId, Description FROM
       Skills INNER JOIN HasSkills
           ON Skills.SkillId = HasSkills.SkillId) as skills
   INNER JOIN Robbers
       ON Robbers.RobberId = skills.RobberId
WHERE Age >= 20 AND Age <= 40;