import React, { useState } from 'react';
import {
    Box,
    TextField,
    Button,
    Typography,
    Link,
    IconButton,
    InputAdornment,
} from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { registerUser } from '../api/auth';
import { emailRegex, passwordRegex, usernameRegex } from "../utils/regex.js";

function Registration() {
    const [username, setUsername] = useState('');
    const [usernameTouched, setUsernameTouched] = useState(false);
    const [usernameFocused, setUsernameFocused] = useState(false);
    const [email, setEmail] = useState('');
    const [emailTouched, setEmailTouched] = useState(false);
    const [emailFocused, setEmailFocused] = useState(false);
    const [password, setPassword] = useState('');
    const [passwordTouched, setPasswordTouched] = useState(false);
    const [passwordFocused, setPasswordFocused] = useState(false);
    const [confirmPassword, setConfirmPassword] = useState('');
    const [confirmPasswordTouched, setConfirmPasswordTouched] = useState(false);
    const [confirmPasswordFocused, setConfirmPasswordFocused] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [formError, setFormError] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const isUsernameValid = usernameRegex.test(username);
    const isEmailValid = emailRegex.test(email);
    const isPasswordValid = passwordRegex.test(password);
    const doPasswordsMatch = password === confirmPassword;
    const isFormValid =
        isUsernameValid &&
        isEmailValid &&
        isPasswordValid &&
        doPasswordsMatch &&
        username !== '' &&
        email !== '' &&
        password !== '' &&
        confirmPassword !== '';
    const togglePasswordVisibility = () => {
        setShowPassword((prev) => !prev);
    };
    const toggleConfirmPasswordVisibility = () => {
        setShowConfirmPassword((prev) => !prev);
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!isFormValid) return;
        setIsSubmitting(true);
        setFormError('');
        try {
            const data = await registerUser({ username, email, password });
            console.log('Token:', data.token);
            //todo сделать сохранение пользователя и его данных или перенаправить на страницу авторизации
        } catch (error) {
            setFormError(error.message);
        } finally {
            setIsSubmitting(false);
        }
    };
    const shouldShowError = (touched, focused, value, isValid) =>
        touched && !focused && value !== '' && !isValid;
    return (
        <Box
            sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                p: 2,
            }}
        >
            <Box
                component="form"
                onSubmit={handleSubmit}
                sx={{
                    position: 'relative',
                    backgroundColor: '#fff',
                    p: 4,
                    borderRadius: 2,
                    boxShadow: '0 4px 10px rgba(0,0,0,0.1)',
                    width: '100%',
                    maxWidth: '400px',
                }}
            >
                <Box
                    component="img"
                    src="/assets/mascots/sloth.png"
                    alt="sloth"
                    sx={{
                        position: 'absolute',
                        left: '30px',
                        top: '50%',
                        transform: 'translate(-100%, -50%)',
                        zIndex: '-1',
                    }}
                />
                <Box
                    component="img"
                    src="/assets/mascots/bunny.png"
                    alt="bunny"
                    sx={{
                        position: 'absolute',
                        right: '40px',
                        top: '30%',
                        transform: 'translate(100%, -50%)',
                        zIndex: '-1',
                    }}
                />
                <Typography
                    variant="h4"
                    component="h1"
                    sx={{ mb: 4, textAlign: 'center', fontWeight: 'bold' }}
                >
                    Регистрация
                </Typography>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: '22px' }}>
                    <TextField
                        label="Имя пользователя"
                        variant="outlined"
                        fullWidth
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        onFocus={() => setUsernameFocused(true)}
                        onBlur={() => {
                            setUsernameFocused(false);
                            setUsernameTouched(true);
                        }}
                        error={
                            shouldShowError(usernameTouched, usernameFocused, username, isUsernameValid)
                        }
                        helperText={
                            shouldShowError(usernameTouched, usernameFocused, username, isUsernameValid)
                                ? 'От 3 до 20 символов: латиница, кириллица, цифры, _, -'
                                : ''
                        }
                        sx={{
                            '& .MuiInputLabel-root': { color: '#838488' },
                            '& .MuiInputBase-input': { color: '#000000' },
                            '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                            '& .MuiInputBase-input::placeholder': { color: '#838488' },
                        }}
                    />
                    <TextField
                        label="Email"
                        variant="outlined"
                        fullWidth
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        onFocus={() => setEmailFocused(true)}
                        onBlur={() => {
                            setEmailFocused(false);
                            setEmailTouched(true);
                        }}
                        error={shouldShowError(emailTouched, emailFocused, email, isEmailValid)}
                        helperText={
                            shouldShowError(emailTouched, emailFocused, email, isEmailValid)
                                ? 'Введите корректный email, например name@example.com'
                                : ''
                        }
                        sx={{
                            '& .MuiInputLabel-root': { color: '#838488' },
                            '& .MuiInputBase-input': { color: '#000000' },
                            '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                            '& .MuiInputBase-input::placeholder': { color: '#838488' },
                        }}
                    />
                    <TextField
                        label="Пароль"
                        type={showPassword ? 'text' : 'password'}
                        variant="outlined"
                        fullWidth
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        onFocus={() => setPasswordFocused(true)}
                        onBlur={() => {
                            setPasswordFocused(false);
                            setPasswordTouched(true);
                        }}
                        error={shouldShowError(passwordTouched, passwordFocused, password, isPasswordValid)}
                        helperText={
                            shouldShowError(passwordTouched, passwordFocused, password, isPasswordValid)
                                ? 'Минимум 8 символов, минимум 1 цифра и 1 буква'
                                : ''
                        }
                        sx={{
                            '& .MuiInputLabel-root': { color: '#838488' },
                            '& .MuiInputBase-input': { color: '#000000' },
                            '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                            '& .MuiInputBase-input::placeholder': { color: '#838488' },
                        }}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton onClick={togglePasswordVisibility} edge="end">
                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                    <TextField
                        label="Подтверждение пароля"
                        type={showConfirmPassword ? 'text' : 'password'}
                        variant="outlined"
                        fullWidth
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        onFocus={() => setConfirmPasswordFocused(true)}
                        onBlur={() => {
                            setConfirmPasswordFocused(false);
                            setConfirmPasswordTouched(true);
                        }}
                        error={
                            shouldShowError(
                                confirmPasswordTouched,
                                confirmPasswordFocused,
                                confirmPassword,
                                doPasswordsMatch
                            )
                        }
                        helperText={
                            shouldShowError(
                                confirmPasswordTouched,
                                confirmPasswordFocused,
                                confirmPassword,
                                doPasswordsMatch
                            )
                                ? 'Пароли не совпадают'
                                : ''
                        }
                        sx={{
                            '& .MuiInputLabel-root': { color: '#838488' },
                            '& .MuiInputBase-input': { color: '#000000' },
                            '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                            '& .MuiInputBase-input::placeholder': { color: '#838488' },
                        }}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton onClick={toggleConfirmPasswordVisibility} edge="end">
                                        {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                </Box>
                {formError && (
                    <Typography color="error" sx={{ mt: 1, textAlign: 'center' }}>
                        {formError}
                    </Typography>
                )}
                <Button
                    type="submit"
                    variant="contained"
                    fullWidth
                    disabled={!isFormValid || isSubmitting}
                    sx={{
                        mb: 2,
                        py: 1.5,
                        backgroundColor: '#6C67EC',
                        borderRadius: '20px',
                        opacity: !isFormValid || isSubmitting ? 0.6 : 1,
                        marginTop: '33px',
                    }}
                >
                    Зарегистрироваться
                </Button>
                <Typography variant="body1" sx={{ textAlign: 'center' }}>
                    Уже есть аккаунт?{' '}
                    <Link component={RouterLink} to="/Login" underline="hover" sx={{ color: '#6C67EC' }}>
                        Войти
                    </Link>
                </Typography>
            </Box>
        </Box>
    );
}

export default Registration;
