select employees.id,
       name,
       count(educations.id)
FROM Employees
JOIN educations
    On employees.id = educations.id_employee
GROUP BY employees.id,name;