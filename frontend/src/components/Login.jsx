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
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { passwordRegex, usernameRegex } from '../utils/regex.js';
import { setAuthToken, findUserByUsername, setCurrentUser } from '../utils/authStore';

function Login() {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [usernameTouched, setUsernameTouched] = useState(false);
    const [usernameFocused, setUsernameFocused] = useState(false);
    const [password, setPassword] = useState('');
    const [passwordTouched, setPasswordTouched] = useState(false);
    const [passwordFocused, setPasswordFocused] = useState(false);
    const [showPassword, setShowPassword] = useState(false);
    const [formError, setFormError] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);

    const isUsernameValid = usernameRegex.test(username);
    const isPasswordValid = passwordRegex.test(password);
    const isFormValid = username !== '' && password !== '';

    const togglePasswordVisibility = () => {
        setShowPassword((prev) => !prev);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!isFormValid) return;
        setIsSubmitting(true);
        setFormError('');
        try {
            // лже JWT
            const token = 'MOCK_JWT_' + Math.random().toString(36).substr(2);
            const user = findUserByUsername(username);
            if (!user) {
                throw new Error('Пользователь не найден. Зарегистрируйтесь.');
            }
            setAuthToken(token);
            setCurrentUser(user);
            console.log('Token:', token);
            navigate('/');
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
                    Вход
                </Typography>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: '22px' }}>
                    <TextField
                        variant="outlined"
                        fullWidth
                        placeholder="Введите имя пользователя"
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
                            '& .MuiOutlinedInput-input': { color: '#000000' },
                            '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                            '& .MuiOutlinedInput-input::placeholder': { color: '#838488' },
                        }}
                    />
                    <TextField
                        type={showPassword ? 'text' : 'password'}
                        variant="outlined"
                        fullWidth
                        placeholder="Введите пароль"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        onFocus={() => setPasswordFocused(true)}
                        onBlur={() => {
                            setPasswordFocused(false);
                            setPasswordTouched(true);
                        }}
                        error={
                            shouldShowError(passwordTouched, passwordFocused, password, isPasswordValid)
                        }
                        helperText={
                            shouldShowError(passwordTouched, passwordFocused, password, isPasswordValid)
                                ? 'Минимум 8 символов, минимум 1 цифра и 1 буква'
                                : ''
                        }
                        sx={{
                            '& .MuiInputLabel-root': { color: '#838488' },
                            '& .MuiOutlinedInput-input': { color: '#000000' },
                            '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                            '& .MuiOutlinedInput-input::placeholder': { color: '#838488' },
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
                    Войти
                </Button>
                <Typography variant="body1" sx={{ textAlign: 'center' }}>
                    Нет аккаунта?{' '}
                    <Link
                        component={RouterLink}
                        to="/Registration"
                        underline="hover"
                        sx={{ color: '#6C67EC' }}
                    >
                        Зарегистрироваться
                    </Link>
                </Typography>
            </Box>
        </Box>
    );
}

export default Login;
