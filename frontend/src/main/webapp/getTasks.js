const tasksP = document.getElementById('tasks')

try {
    const response = await fetch('https://localhost:8443/api/tasks')
    if (!response.ok)
        throw new Error(`Response status: ${response.status}`)
    const json = await response.json();

    tasksP.innerText = json

} catch (err) {
    tasksP.innerText = err.message
}


