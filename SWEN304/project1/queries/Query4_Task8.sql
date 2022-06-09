SELECT Description, RobberId, Nickname
FROM Skills, Robbers
WHERE (RobberId, SkillId) IN (SELECT RobberId, SkillId FROM HasSkills)
ORDER BY Description;