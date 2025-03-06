import React, { useEffect, useState } from 'react';
import {
    Box,
    Container,
    Typography,
    Grid,
} from '@mui/material';
import { Link } from 'react-router-dom';
import { speedrunMockData } from "../utils/mockData.js";

function getTrophyIcon(place) {
    switch (place) {
        case 1:
            return '/assets/trophy/goldTrophy.png';
        case 2:
            return '/assets/trophy/silverTrophy.png';
        case 3:
            return '/assets/trophy/bronzeTrophy.png';
        default:
            return null;
    }
}

function placeToString(place) {
    switch (place) {
        case 1:
            return '1-е место';
        case 2:
            return '2-е место';
        case 3:
            return '3-е место';
        default:
            return `${place}-е место`;
    }
}

function formatTime(ms) {
    const hours = Math.floor(ms / 3600000);
    const minutes = Math.floor((ms % 3600000) / 60000);
    const seconds = Math.floor((ms % 60000) / 1000);
    const milliseconds = ms % 1000;

    let result = '';
    if (hours > 0) {
        result += `${hours}ч `;
    }
    if (minutes > 0 || hours > 0) {
        result += `${minutes}мин `;
    }
    result += `${seconds}с ${milliseconds}мс`;
    return result.trim();
}

function formatDate(dateStr) {
    const date = new Date(dateStr);
    const options = {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    return date.toLocaleString('ru-RU', options);
}

export default function GamesLatest() {
    const [records, setRecords] = useState([]);

    useEffect(() => {
        const mockFetchRecords = async () => {
            await new Promise((resolve) => setTimeout(resolve, 500));
            setRecords(speedrunMockData);
        };

        mockFetchRecords();
    }, []);

    return (
        <Box
            sx={{
                fontFamily: 'Nunito Sans, sans-serif',
                display: 'flex',
                flexDirection: 'column',
                alignItems: "center",
                justifyContent: "center"
            }}
        >
            <Box
                sx={{
                    position: 'relative',
                    backgroundImage: 'url("/assets/banner.png")',
                    backgroundPosition: 'center',
                    backgroundSize: 'contain',
                    backgroundRepeat: 'no-repeat',
                    height: { xs: '100px', lg: '331px' },
                    maxWidth: "1200px",
                    width: "100%",
                    mb: 4,
                }}
            />

            <Container
                sx={{
                    maxWidth: '750px',
                    minWidth: '400px',
                    background: "#ffffff",
                    borderRadius: "24px",
                    padding: "25px 35px"
                }}
            >
                <Typography variant="h5" sx={{ mb: 2 }}>
                    Последние прохождения
                </Typography>

                <Grid
                    container
                    spacing={2}
                    sx={{
                        flexDirection: { xs: 'column', lg: 'row' },
                    }}
                >
                    {records.map((record, index) => {
                        const trophyIcon = getTrophyIcon(record.place);

                        return (
                            <Grid item xs={12} xl={6} key={index}>
                                <Box
                                    component={Link}
                                    to={`/Games/${record.id}`}
                                    sx={{
                                        display: 'flex',
                                        alignItems: 'center',
                                        overflow: 'hidden',
                                        p: 1,
                                        border: '1px solid #DCDBE0',
                                        borderRadius: '24px',
                                        height: '128px',
                                        width: '100%',
                                        textDecoration: 'none',
                                        color: 'inherit',
                                    }}
                                >
                                    <Box
                                        component="img"
                                        src={record.icon}
                                        alt={record.game}
                                        sx={{
                                            maxWidth: '103px',
                                            maxHeight: '120px',
                                            width: '100%',
                                            height: '100%',
                                            borderRadius: '24px',
                                            objectFit: 'cover',
                                            flexShrink: 0,
                                            ml: 2,
                                            mt: 2,
                                            mr: 2,
                                            mb: 2,
                                        }}
                                    />

                                    <Box
                                        sx={{
                                            ml: 2,
                                            flex: 1,
                                            display: 'flex',
                                            flexDirection: 'column',
                                            gap: "16px",
                                            height: '100%',
                                        }}
                                    >
                                        <Typography
                                            variant="h6"
                                            sx={{
                                                fontWeight: 700,
                                                fontSize: '24px',
                                                lineHeight: '32.74px',
                                                color: '#000000',
                                            }}
                                        >
                                            {record.game}
                                        </Typography>

                                        <Box
                                            sx={{
                                                display: 'flex',
                                                justifyContent: "space-between"
                                            }}
                                        >
                                            <Box>
                                                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                                    {trophyIcon && (
                                                        <Box
                                                            component="img"
                                                            src={trophyIcon}
                                                            alt="trophy"
                                                            sx={{ width: '20px', height: '20px' }}
                                                        />
                                                    )}
                                                    <Typography
                                                        variant="body1"
                                                        sx={{
                                                            fontWeight: 700,
                                                            fontSize: '16px',
                                                            lineHeight: '21.82px',
                                                            ml: trophyIcon ? 0.5 : 0,
                                                        }}
                                                    >
                                                        {placeToString(record.place)}
                                                    </Typography>
                                                </Box>

                                                {record.time > 0 && (
                                                    <Typography
                                                        variant="body1"
                                                        sx={{
                                                            fontWeight: 400,
                                                            fontSize: '14px',
                                                            lineHeight: '19.1px',
                                                            mt: 1,
                                                        }}
                                                    >
                                                        {formatTime(record.time)}
                                                    </Typography>
                                                )}
                                            </Box>

                                            <Box sx={{ textAlign: 'right', mr: 2 }}>
                                                {/* Отображаем флаг слева от ника */}
                                                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                                                    <Box
                                                        component="img"
                                                        src={record.flag}
                                                        alt={record.flagAlt}
                                                        sx={{ width: 24, height: 24 }}
                                                    />
                                                    <Typography
                                                        variant="body1"
                                                        sx={{
                                                            fontWeight: 700,
                                                            fontSize: '16px',
                                                            lineHeight: '21.82px',
                                                        }}
                                                    >
                                                        {record.user}
                                                    </Typography>
                                                </Box>
                                                <Typography
                                                    variant="body1"
                                                    sx={{
                                                        fontWeight: 400,
                                                        fontSize: '14px',
                                                        lineHeight: '19.1px',
                                                        mt: 1,
                                                    }}
                                                >
                                                    {formatDate(record.date)}
                                                </Typography>
                                            </Box>
                                        </Box>
                                    </Box>
                                </Box>
                            </Grid>
                        );
                    })}
                </Grid>
            </Container>
        </Box>
    );
}
