export async function registerUser({username,email,password}){
    const response = await fetch('/api/auth/register',{
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username,email,password})
    })
    const data = await response.json()
    if (!response.ok) {
        throw new Error(data.error || "Ошибка при регистрации")
    }
    return data
}

export async function loginUser ({username,password}){
    const response = await fetch('/api/auth/login',{
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username,password})
    })
    const data = await response.json()
    if (!response.ok) {
        throw new Error(data.error || "Ошибка при авторизации")
    }
    return data
}