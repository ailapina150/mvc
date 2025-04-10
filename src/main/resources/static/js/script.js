
    // Функция для удаления элемента образования
    function delEducation(index) {
        const row = document.getElementById('educationRow_' + index);
        if (row) {
            row.remove();
        } else {
            console.error('Элемент Education с индексом ${index} не найден.');
        }
    }
    // Функция для удаления элемента проекта
    function delProject(index) {
        const row = document.getElementById('projectRow_' + index);
        if (row) {
            row.remove();
        } else {
            console.error('Элемент с Project индексом ${index} не найден.');
        }
    }
    // Функция для удаления элемента задачи
    function delTask(index1,index2) {
        const row = document.getElementById('taskRow_' + index1 + '_' + index2);
        if (row) {
            row.remove();
        } else {
            console.error('Элемент сTask индексом ${index} не найден.');
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        const educationContainer = document.getElementById('educationContainer');
        const educationsSize = document.getElementById('educationsSize').value;
        for(let i = 0 ; i < educationsSize; i++)
        educationContainer.appendChild(document.getElementById('educationRow_' + i));

        const projectContainer = document.getElementById('projectContainer');
        const projectsSize = document.getElementById('projectsSize').value;
        for(let i = 0 ; i < projectsSize; i++)
            projectContainer.appendChild(document.getElementById('projectRow_' + i));
    })


    function addEducation() {
        const educationContainer = document.getElementById('educationContainer');

        const nextIndex = educationContainer.childElementCount; // Get the next index
        const newEducation = document.createElement('div');

        newEducation.classList.add('education-elem');
        newEducation.setAttribute('id', `educationRow_${nextIndex}`);
        newEducation.innerHTML = `
        <div id="educationRow_${nextIndex}" class="education-row" >
            <input id="educationId_${nextIndex}" name="educations[${nextIndex}].id"  type="hidden" />
            <label for="yearStart_${nextIndex}">Year Start:</label>
            <input id="yearStart_${nextIndex}" type="number" name="educations[${nextIndex}].yearStart" required>
            <label for="yearEnd_${nextIndex}">Year End:</label>
            <input id="yearEnd_${nextIndex}" type="number" name="educations[${nextIndex}].yearEnd" required>
            <label for="university_${nextIndex}">University:</label>
            <input id="university_${nextIndex}" type="text" name="educations[${nextIndex}].university" required>
            <label for="degree_${nextIndex}">Degree:</label>
            <input id="degree_${nextIndex}" type="text" name="educations[${nextIndex}].degree" required>
            <div class="delete-button">
                <button type="button" class="action" onclick="delEducation(${nextIndex})" >Delete Education</button>
            </div>
         </div>
        `;
        educationContainer.appendChild(newEducation);
    }

    function addProject() {
        const projectContainer = document.getElementById('projectContainer');

        const nextIndex = projectContainer.childElementCount; // Get the next index
        const newProject = document.createElement('div');

        newProject.classList.add('project-elem');
        newProject.setAttribute('id', `projectRow_${nextIndex}`);
        newProject.innerHTML = `
        <div id="projectRow_${nextIndex}" class="project-row" >
<!--            <h3>Project <span th:text ="${nextIndex}+1"/></h3>-->
            <input type = "hidden" id = "projectId_${nextIndex}" name = "projects[${nextIndex}].id" />
            <label for = "project-name_${nextIndex}">Name:</label>
            <input id = "project-name_${nextIndex}" type="text" name = "projects[${nextIndex}].name" required>
            <label for = "description_${nextIndex}">Description:</label>
            <input id = "description_${nextIndex}" type="text" name = "projects[${nextIndex}].description" required>
            <div class ="delete-button">
                 <button type="button" class="action" th:onclick="'delProject(' + ${nextIndex} + ')'">Delete Project</button>
            </div>
         </div>
        `;
        projectContainer.appendChild(newProject);
    }
