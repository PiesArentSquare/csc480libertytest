const tasksP = document.getElementById('tasks')

try {
    const response = await fetch('http://localhost:8080/api/tasks')
    if (!response.ok)
        throw new Error(`Response status: ${response.status}`)
    const json = await response.json();
    tasksP.innerText = JSON.stringify(json)
} catch (err) {
    tasksP.innerText = err.message
}


