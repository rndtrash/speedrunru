import React, { useState, useEffect } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    Box,
    Typography,
    InputAdornment
} from '@mui/material';
import { urlRegex } from "../utils/regex.js";

function TimeInputFields({ hours, minutes, seconds, millis, onChange, onErrorChange }) {
    const [errors, setErrors] = useState({ minutes: false, seconds: false, millis: false, hours: false });

    useEffect(() => {
        if (onErrorChange) {
            onErrorChange(errors);
        }
    }, [errors, onErrorChange]);

    const handleHoursChange = (e) => {
        let value = e.target.value;
        if (value !== '' && parseInt(value) < 0) {
            value = "0";
            setErrors((prev) => ({ ...prev, hours: true }));
        } else {
            setErrors((prev) => ({ ...prev, hours: false }));
        }
        onChange({ hours: value, minutes, seconds, millis });
    };

    const handleMinutesChange = (e) => {
        let value = e.target.value;
        if (value !== '' && parseInt(value) > 59) {
            value = "59";
            setErrors((prev) => ({ ...prev, minutes: true }));
        } else {
            setErrors((prev) => ({ ...prev, minutes: false }));
        }
        onChange({ hours, minutes: value, seconds, millis });
    };

    const handleSecondsChange = (e) => {
        let value = e.target.value;
        if (value !== '' && parseInt(value) > 59) {
            value = "59";
            setErrors((prev) => ({ ...prev, seconds: true }));
        } else {
            setErrors((prev) => ({ ...prev, seconds: false }));
        }
        onChange({ hours, minutes, seconds: value, millis });
    };

    const handleMillisChange = (e) => {
        let value = e.target.value;
        if (value !== '' && parseInt(value) > 999) {
            value = "999";
            setErrors((prev) => ({ ...prev, millis: true }));
        } else {
            setErrors((prev) => ({ ...prev, millis: false }));
        }
        onChange({ hours, minutes, seconds, millis: value });
    };

    return (
        <Box
            sx={{
                fontFamily: 'Nunito Sans',
                fontWeight: 400,
                fontSize: '14px',
                lineHeight: '100%',
                letterSpacing: '0%'
            }}
        >
            <TextField
                variant="standard"
                value={hours}
                onChange={handleHoursChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0 }}
                InputProps={{ endAdornment: <InputAdornment position="end">ч</InputAdornment> }}
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
                error={errors.hours}
                helperText={errors.hours ? "Часы не могут быть отрицательными" : ""}
            />
            <TextField
                variant="standard"
                value={minutes}
                onChange={handleMinutesChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0, max: 59 }}
                InputProps={{ endAdornment: <InputAdornment position="end">м</InputAdornment> }}
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
            />
            <TextField
                variant="standard"
                value={seconds}
                onChange={handleSecondsChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0, max: 59 }}
                InputProps={{ endAdornment: <InputAdornment position="end">с</InputAdornment> }}
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
            />
            <TextField
                variant="standard"
                value={millis}
                onChange={handleMillisChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0, max: 999 }}
                InputProps={{ endAdornment: <InputAdornment position="end">мс</InputAdornment> }}
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
            />
        </Box>
    );
}

export default function SpeedRunSendModal({ open, onClose }) {
    const [videoUrl, setVideoUrl] = useState('');
    const [urlError, setUrlError] = useState(false);
    const [time, setTime] = useState({ hours: '', minutes: '', seconds: '', millis: '' });
    const [timeInputErrors, setTimeInputErrors] = useState({ minutes: false, seconds: false, millis: false, hours: false });
    const [timeError, setTimeError] = useState(false);

    const handleUrlChange = (e) => {
        setVideoUrl(e.target.value);
        setUrlError(!urlRegex.test(e.target.value));
    };

    const handleTimeChange = (newTime) => {
        setTime(newTime);
    };

    const handleErrorChange = (errors) => {
        setTimeInputErrors(errors);
    };

    const handleSubmit = () => {
        const h = parseInt(time.hours) || 0;
        const m = parseInt(time.minutes) || 0;
        const s = parseInt(time.seconds) || 0;
        const ms = parseInt(time.millis) || 0;
        if ((h === 0 && m === 0 && s === 0 && ms === 0) && (videoUrl === '' || urlError)) {
            setTimeError(true);
            return;
        }
        if ((videoUrl === '' || urlError) || timeInputErrors.minutes || timeInputErrors.seconds || timeInputErrors.millis || timeInputErrors.hours) {
            setTimeError(true);
            return;
        }
        setTimeError(false);
        const totalMs = (h * 3600000) + (m * 60000) + (s * 1000) + ms;
        if (totalMs < 0) {
            setTimeError(true);
            setTimeInputErrors({ ...timeInputErrors, general: true });
            return;
        }
        console.log('Видео URL:', videoUrl);
        console.log('Время в мс:', totalMs);
        onClose();
    };

    const hasTimeFieldError = timeInputErrors.minutes || timeInputErrors.seconds || timeInputErrors.millis || timeInputErrors.hours;
    const timeErrorMessage = hasTimeFieldError
        ? 'Проверьте корректность значений времени (минуты и секунды: максимум 59, миллисекунды: максимум 999, часы не могут быть отрицательными).'
        : timeInputErrors.general
            ? 'Время не должно быть отрицательным.'
            : 'Необходимо заполнить ссылку на видео и хотя бы одно поле времени.';

    return (
        <Dialog open={open} onClose={onClose} fullWidth PaperProps={{ sx: {
                fontFamily: 'Nunito Sans',
                fontWeight: 400,
                fontSize: '14px',
                lineHeight: '100%',
                letterSpacing: '0%'
            } }}>
            <DialogTitle
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
            >
                Отправить запись
                <Box
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
                >
                    <img src="/assets/mascots/bunnyMascotSendSpeedRun.png" alt="Bunny Mascot" style={{ maxWidth: '80px', height: 'auto' }} />
                </Box>
            </DialogTitle>
            <DialogContent
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
            >
                <Typography
                    variant="h6"
                    gutterBottom
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
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
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
                />
                <Typography
                    variant="h6"
                    gutterBottom
                    sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}
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
                <TimeInputFields
                    hours={time.hours}
                    minutes={time.minutes}
                    seconds={time.seconds}
                    millis={time.millis}
                    onChange={handleTimeChange}
                    onErrorChange={handleErrorChange}
                />
                {timeError && (
                    <Typography variant="body2" color="error" sx={{
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '14px',
                        lineHeight: '100%',
                        letterSpacing: '0%'
                    }}>
                        {timeErrorMessage}
                    </Typography>
                )}
            </DialogContent>
            <DialogActions
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}
            >
                <Button variant="contained" onClick={handleSubmit} sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 400,
                    fontSize: '14px',
                    lineHeight: '100%',
                    letterSpacing: '0%'
                }}>
                    Отправить
                </Button>
            </DialogActions>
        </Dialog>
    );
}
