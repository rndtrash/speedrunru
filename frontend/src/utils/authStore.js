export function getStoredUsers() {
    const usersJSON = localStorage.getItem('registeredUsers');
    return usersJSON ? JSON.parse(usersJSON) : [];
}

export function saveUsers(users) {
    localStorage.setItem('registeredUsers', JSON.stringify(users));
}

export function addUser(user) {
    const users = getStoredUsers();
    users.push(user);
    saveUsers(users);
}

export function findUser({ username, email }) {
    const users = getStoredUsers();
    return users.find((user) => user.username === username || user.email === email);
}

export function findUserByUsername(username) {
    const users = getStoredUsers();
    return users.find((user) => user.username === username);
}

export function setCurrentUser(user) {
    localStorage.setItem('currentUser', JSON.stringify(user));
}

export function getCurrentUser() {
    const userJSON = localStorage.getItem('currentUser');
    return userJSON ? JSON.parse(userJSON) : null;
}

export function clearCurrentUser() {
    localStorage.removeItem('currentUser');
}

export function setAuthToken(token) {
    localStorage.setItem('authToken', token);
    const expiry = Date.now() + 60 * 60 * 1000; // 1 час
    localStorage.setItem('authExpiry', expiry.toString());
}

export function getAuthToken() {
    const token = localStorage.getItem('authToken');
    const expiry = localStorage.getItem('authExpiry');
    if (token && expiry) {
        if (Date.now() > parseInt(expiry)) {
            clearAuthToken();
            return null;
        }
        return token;
    }
    return null;
}

export function clearAuthToken() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('authExpiry');
}

export function isAuthorized() {
    return getAuthToken() !== null;
}
