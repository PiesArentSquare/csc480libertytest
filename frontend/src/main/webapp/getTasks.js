const tasksP = document.getElementById('tasks')

try {
    const response = await fetch('/api/tasks')
    if (!response.ok)
        throw new Error(`Response status: ${response.status}`)
    // const json = await response.json();
    tasksP.innerText = await response.text()
} catch (err) {
    tasksP.innerText = err.message
}


