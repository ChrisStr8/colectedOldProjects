SELECT RobberId, Nickname, Description
FROM Robbers, Skills
WHERE (RobberId, SkillId, 1) IN (SELECT RobberId, SkillId, Preference FROM HasSkills)
   AND RobberId IN (SELECT RobberId FROM HasSkills WHERE Preference > 1)