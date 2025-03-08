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
    InputAdornment,
    FormControl,
    InputLabel,
    Select,
    MenuItem
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
                display: 'flex',
                gap: 2,
                mt: 2,
                border: '1px solid #ccc',
                borderRadius: '20px',
                padding: '8px 16px',
                maxWidth: 400
            }}
        >
            <TextField
                variant="standard"
                value={hours}
                onChange={handleHoursChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0 }}
                InputProps={{ endAdornment: <InputAdornment position="end">ч</InputAdornment> }}
                sx={{ width: '60px', '& .MuiInput-underline:before, & .MuiInput-underline:after': { border: 'none' } }}
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
                sx={{ width: '60px', '& .MuiInput-underline:before, & .MuiInput-underline:after': { border: 'none' } }}
            />
            <TextField
                variant="standard"
                value={seconds}
                onChange={handleSecondsChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0, max: 59 }}
                InputProps={{ endAdornment: <InputAdornment position="end">с</InputAdornment> }}
                sx={{ width: '60px', '& .MuiInput-underline:before, & .MuiInput-underline:after': { border: 'none' } }}
            />
            <TextField
                variant="standard"
                value={millis}
                onChange={handleMillisChange}
                type="number"
                inputProps={{ style: { textAlign: 'right' }, min: 0, max: 999 }}
                InputProps={{ endAdornment: <InputAdornment position="end">мс</InputAdornment> }}
                sx={{ width: '60px', '& .MuiInput-underline:before, & .MuiInput-underline:after': { border: 'none' } }}
            />
        </Box>
    );
}

export default function SpeedRunSendModal({ open, onClose, categories = [], activeCategory = null }) {
    const [videoUrl, setVideoUrl] = useState('');
    const [urlError, setUrlError] = useState(false);
    const [time, setTime] = useState({ hours: '', minutes: '', seconds: '', millis: '' });
    const [timeInputErrors, setTimeInputErrors] = useState({ minutes: false, seconds: false, millis: false, hours: false });
    const [timeError, setTimeError] = useState(false);

    const [selectedCategory, setSelectedCategory] = useState(activeCategory ? activeCategory.id : '');

    useEffect(() => {
        if (activeCategory) {
            setSelectedCategory(activeCategory.id);
        }
    }, [activeCategory]);

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

        if (
            (videoUrl === '' || urlError) ||
            timeInputErrors.minutes ||
            timeInputErrors.seconds ||
            timeInputErrors.millis ||
            timeInputErrors.hours
        ) {
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
        console.log('Выбранная категория ID:', selectedCategory);

        onClose();
    };

    const hasTimeFieldError = timeInputErrors.minutes || timeInputErrors.seconds || timeInputErrors.millis || timeInputErrors.hours;
    const timeErrorMessage = hasTimeFieldError
        ? 'Проверьте корректность значений времени (минуты и секунды: максимум 59, миллисекунды: максимум 999, часы не могут быть отрицательными).'
        : timeInputErrors.general
            ? 'Время не должно быть отрицательным.'
            : 'Необходимо заполнить ссылку на видео и хотя бы одно поле времени.';

    return (
        <Dialog open={open} onClose={onClose} fullWidth PaperProps={{ sx: { maxWidth: '750px' } }}>
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
                    justifyContent: "center"
                }}
            >
                Отправить запись
                <Box sx={{ textAlign: 'center', mb: 2 }}>
                    <img src="/assets/mascots/bunnyMascotSendSpeedRun.png" alt="Bunny Mascot" style={{ maxWidth: '80px', height: 'auto' }} />
                </Box>
            </DialogTitle>

            <DialogContent>
                {/* Блок ввода ссылки на видео */}
                <Typography
                    variant="h6"
                    gutterBottom
                    sx={{ fontFamily: 'Nunito Sans', fontWeight: 800, fontSize: '24px', lineHeight: '100%', letterSpacing: '0%' }}
                >
                    Видео
                </Typography>
                <Typography
                    variant="body2"
                    gutterBottom
                    sx={{ fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px', lineHeight: '100%', letterSpacing: '0%' }}
                >
                    Прикрепите ссылку на видео с прохождением игры для проверки рекорда модератором
                </Typography>
                <TextField
                    margin="normal"
                    fullWidth
                    placeholder="URL видео"
                    variant="outlined"
                    value={videoUrl}
                    onChange={handleUrlChange}
                    error={urlError}
                    helperText={urlError ? 'Некорректный формат ссылки' : ''}
                    sx={{
                        '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                        '& .MuiInputBase-input': { color: '#000000', fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px' },
                        '& .MuiInputBase-input::placeholder': { color: '#888888', fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px' }
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
                    Категория
                </Typography>
                <Typography
                    variant="body2"
                    gutterBottom
                    sx={{ fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px', lineHeight: '100%' }}
                >
                    Выберите нужную категорию из списка
                </Typography>
                <FormControl
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    sx={{
                        '& .MuiOutlinedInput-root': { borderRadius: '20px' },
                        '& .MuiInputBase-input': { fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px', color: '#000000' }
                    }}
                >
                    <InputLabel
                        id="category-select-label"
                        sx={{ fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px' }}
                    >
                        Категория
                    </InputLabel>
                    <Select
                        labelId="category-select-label"
                        label="Категория"
                        value={selectedCategory}
                        onChange={(e) => setSelectedCategory(e.target.value)}
                    >
                        {categories.map((cat) => (
                            <MenuItem
                                key={cat.id}
                                value={cat.id}
                                sx={{ fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px', color: '#000000' }}
                            >
                                {cat.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

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
                    sx={{ fontFamily: 'Nunito Sans', fontWeight: 400, fontSize: '14px' }}
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
                    <Typography variant="body2" color="error" sx={{ mt: 1 }}>
                        {timeErrorMessage}
                    </Typography>
                )}
            </DialogContent>

            <DialogActions>
                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    sx={{ fontFamily: 'Nunito Sans', fontWeight: 800, fontSize: '16px' }}
                >
                    Отправить
                </Button>
            </DialogActions>
        </Dialog>
    );
}
