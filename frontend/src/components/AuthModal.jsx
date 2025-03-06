import React from 'react';
import { Modal, Box, Typography, Button, IconButton } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import CloseIcon from '@mui/icons-material/Close';

const AuthModal = ({ open, onClose }) => {
    const navigate = useNavigate();

    const handleLogin = () => {
        onClose();
        navigate('/login');
    };

    const handleRegister = () => {
        onClose();
        navigate('/Registration');
    };

    return (
        <Modal
            open={open}
            onClose={onClose}
            aria-labelledby="auth-modal-title"
            aria-describedby="auth-modal-description"
        >
            <Box
                sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: 428,
                    height: 225,
                    bgcolor: 'white',
                    borderRadius: '32px',
                    boxShadow: 24,
                    p: 4,
                    outline: 'none',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                }}
            >
                <IconButton
                    onClick={onClose}
                    sx={{
                        position: 'absolute',
                        top: 8,
                        right: 8,
                    }}
                >
                    <CloseIcon />
                </IconButton>

                <Typography
                    id="auth-modal-title"
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 700,
                        fontSize: '18px',
                        textAlign: 'center',
                        mb: 3,
                        px: 2,
                    }}
                >
                    Отправить запись могут только авторизованные пользователи.
                </Typography>

                <Box
                    sx={{
                        display: 'flex',
                        gap: 2,
                    }}
                >
                    <Button
                        variant="contained"
                        onClick={handleLogin}
                        sx={{
                            width: 120,
                            height: 41,
                            backgroundColor: '#DCFC6A',
                            borderRadius: '24px',
                            fontFamily:"Nunito Sans",
                            fontWeight: 400,
                            fontSize: "14px",
                            lineHeight: "100%",
                            letterSpacing: "0%",
                            textTransform: 'none',
                            color: "#000000",
                            '&:hover': {
                                backgroundColor: '#DCFC6A',
                            },
                        }}
                    >
                        Войти
                    </Button>
                    <Button
                        variant="contained"
                        onClick={handleRegister}
                        sx={{
                            width: 168,
                            height: 41,
                            backgroundColor: '#6C67EC',
                            borderRadius: '24px',
                            fontFamily:"Nunito Sans",
                            fontWeight: 400,
                            fontSize: "14px",
                            lineHeight: "100%",
                            letterSpacing: "0%",
                            textTransform: 'none',
                            color: '#FFFFFF',
                            '&:hover': {
                                backgroundColor: '#5D58DD',
                            },
                        }}
                    >
                        Зарегистрироваться
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default AuthModal;
