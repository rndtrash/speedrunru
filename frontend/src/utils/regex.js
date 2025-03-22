export const usernameRegex = /^(?=.*[a-zA-Z0-9а-яА-Я])[a-zA-Zа-яА-Я0-9_-]{3,20}$/;
export const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
export const passwordRegex = /^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$/;
export const urlRegex = /^https?:\/\/(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}(?:\/\S*)?$/;
