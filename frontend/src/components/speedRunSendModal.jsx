import React, { useState } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    Box,
    Typography
} from '@mui/material';
import { urlRegex } from "../utils/regex.js";

const SpeedRunSendModal = ({ open, onClose }) => {
    const [videoUrl, setVideoUrl] = useState('');
    const [urlError, setUrlError] = useState(false);
    const [timeValue, setTimeValue] = useState('');

    const handleUrlChange = (event) => {
        const value = event.target.value;
        setVideoUrl(value);
        setUrlError(!urlRegex.test(value));
    };

    const handleTimeChange = (event) => {
        setTimeValue(event.target.value);
    };

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            PaperProps={{
                sx: { maxWidth: '750px' }
            }}
        >
            <DialogTitle
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 800,
                    fontSize: '34px',
                    lineHeight: '100%',
                    letterSpacing: '0%',
                    display: 'flex',
                    flexDirection: 'row-reverse',
                    gap: '10px',
                    alignItems: 'center',
                }}
            >
                Отправить запись
                <Box sx={{ textAlign: 'center', mb: 2 }}>
                    <img
                        src="/assets/mascots/bunnyMascotSendSpeedRun.png"
                        alt="Bunny Mascot"
                        style={{ maxWidth: '80px', height: 'auto' }}
                    />
                </Box>
            </DialogTitle>

            <DialogContent>
                <Typography
                    variant="h6"
                    gutterBottom
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 800,
                        fontSize: '24px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
                >
                    Видео
                </Typography>

                <Typography
                    variant="body2"
                    gutterBottom
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
                >
                    Прикрепите ссылку на видео с прохождением игры для проверки рекорда модератором
                </Typography>

                <TextField
                    margin="normal"
                    fullWidth
                    placeholder="URL видео на RUTUBE"
                    variant="outlined"
                    value={videoUrl}
                    onChange={handleUrlChange}
                    error={urlError}
                    helperText={urlError ? 'Некорректный формат ссылки' : ''}
                    sx={{
                        '& .MuiOutlinedInput-root': {
                            borderRadius: '20px'
                        },
                        '& .MuiInputBase-input': {
                            color: '#000000',
                            fontFamily: 'Nunito Sans',
                            fontWeight: 400,
                            fontSize: '14px',
                            lineHeight: '100%',
                            letterSpacing: '0%'
                        },
                        '& .MuiInputBase-input::placeholder': {
                            color: '#888888',
                            fontFamily: 'Nunito Sans',
                            fontWeight: 400,
                            fontSize: '14px',
                            lineHeight: '100%',
                            letterSpacing: '0%'
                        }
                    }}
                />

                <Typography
                    variant="h6"
                    sx={{
                        mt: 2,
                        fontFamily: 'Nunito Sans',
                        fontWeight: 800,
                        fontSize: '24px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
                    gutterBottom
                >
                    Время
                </Typography>

                <Typography
                    variant="body2"
                    gutterBottom
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
                >
                    Время определяет место забега в таблице рекордов
                </Typography>

                <TextField
                    margin="normal"
                    fullWidth
                    placeholder="ч.   мин.   сек.   мс."
                    variant="outlined"
                    value={timeValue}
                    onChange={handleTimeChange}
                    sx={{
                        '& .MuiOutlinedInput-root': {
                            borderRadius: '20px'
                        },
                        '& .MuiInputBase-input': {
                            color: '#000000',
                            fontFamily: 'Nunito Sans',
                            fontWeight: 400,
                            fontSize: '14px',
                            lineHeight: '100%',
                            letterSpacing: '0%',
                        },
                        '& .MuiInputBase-input::placeholder': {
                            color: '#888888',
                            fontFamily: 'Nunito Sans',
                            fontWeight: 400,
                            fontSize: '14px',
                            lineHeight: '100%',
                            letterSpacing: '0%'
                        }
                    }}
                />
            </DialogContent>

            <DialogActions>
                <Button
                    variant="contained"
                    onClick={onClose}
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 800,
                        fontSize: '16px'
                    }}
                >
                    Отправить
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default SpeedRunSendModal;
