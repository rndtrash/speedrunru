import React, { useEffect, useState, useMemo } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
    Box,
    Container,
    Typography,
    Button,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    Paper,
} from '@mui/material';
import { gameInfoMock } from '../utils/gameInfoMock';
import { gameRecordsMock } from '../utils/gameRecordsMock';
import { isAuthorized } from '../utils/authStore';
import AuthModal from './AuthModal';

function formatTime(ms) {
    if (ms <= 0) return '0:00:00';
    const hours = Math.floor(ms / 3600000);
    const minutes = Math.floor((ms % 3600000) / 60000);
    const seconds = Math.floor((ms % 60000) / 1000);
    return `${hours.toString().padStart(1, '0')}:${minutes
        .toString()
        .padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
}

function formatDate(dateStr) {
    return new Date(dateStr).toLocaleDateString('ru-RU');
}

function formatReleaseDate(dateStr) {
    const date = new Date(dateStr);
    return `${String(date.getDate()).padStart(2, '0')}.${String(
        date.getMonth() + 1
    ).padStart(2, '0')}.${date.getFullYear()}`;
}

export default function GameInfo() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [gameInfo, setGameInfo] = useState(null);
    const [activeCategory, setActiveCategory] = useState(null);
    const [records, setRecords] = useState([]);
    const [sortField, setSortField] = useState(null);
    const [sortOrder, setSortOrder] = useState('asc');
    const authorized = isAuthorized();

    const [openAuthModal, setOpenAuthModal] = useState(false);

    useEffect(() => {
        const timeoutId = setTimeout(() => {
            if (!gameInfo) navigate('/error?code=GameNotFound');
        }, 30000);
        return () => clearTimeout(timeoutId);
    }, [gameInfo, navigate]);

    useEffect(() => {
        try {
            const game = gameInfoMock.find((g) => g.id === parseInt(id, 10));
            if (!game) navigate('/error?code=GameNotFound');
            else setGameInfo(game);
        } catch (err) {
            navigate('/error?code=UnexpectedError');
        }
    }, [id, navigate]);

    useEffect(() => {
        if (gameInfo?.categories?.length)
            setActiveCategory(gameInfo.categories[0].id);
    }, [gameInfo]);

    useEffect(() => {
        if (activeCategory) {
            const categoryName = activeCategory === 1 ? 'Any%' : 'Все боссы';
            setRecords([]);
            setTimeout(() => {
                const filteredRecords = gameRecordsMock.filter(
                    (record) => record.category === categoryName
                );
                setRecords(filteredRecords);
            }, 500);
        }
    }, [activeCategory]);

    const handleSort = (field) => {
        let order = 'asc';
        if (sortField === field && sortOrder === 'asc') order = 'desc';
        setSortField(field);
        setSortOrder(order);
    };

    const sortedRecords = useMemo(() => {
        if (!sortField) return records;
        return [...records].sort((a, b) => {
            let aField = a[sortField];
            let bField = b[sortField];
            if (sortField === 'submitted_at') {
                aField = new Date(aField);
                bField = new Date(bField);
            }
            if (typeof aField === 'string') {
                aField = aField.toLowerCase();
                bField = bField.toLowerCase();
            }
            if (aField > bField) return sortOrder === 'asc' ? 1 : -1;
            if (aField < bField) return sortOrder === 'asc' ? -1 : 1;
            return 0;
        });
    }, [records, sortField, sortOrder]);

    if (!gameInfo) {
        return (
            <Container sx={{ mt: 5, textAlign: 'center' }}>
                <Typography variant="h5">Загрузка информации об игре...</Typography>
            </Container>
        );
    }

    const handleSendRecord = () => {
        if (!authorized) {
            setOpenAuthModal(true);
        } else {
            // на будущее
            alert('Открывается форма отправки записи ');
        }
    };

    return (
        <Container
            sx={{
                mt: 5,
                padding: '25px 35px',
                backgroundColor: '#ffffff',
                borderRadius: '24px',
            }}
        >
            <Box sx={{ position: 'relative', mb: 3 }}>
                <Box
                    component="img"
                    src={gameInfo.icon}
                    alt={gameInfo.name}
                    sx={{
                        float: 'left',
                        mr: 2,
                        mb: 1,
                        width: 120,
                        height: 120,
                        borderRadius: 2,
                        objectFit: 'contain',
                    }}
                />
                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'space-between',
                        alignItems: 'center',
                    }}
                >
                    <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>
                        {gameInfo.name}
                    </Typography>
                    <Button
                        onClick={handleSendRecord}
                        sx={{
                            textTransform: 'none',
                            borderRadius: '20px',
                            backgroundColor: '#6C67EC',
                            color: '#fff !important',
                            padding: '8px 16px',
                            '&:hover': {
                                backgroundColor: '#5749D0',
                                color: '#fff',
                            },
                        }}
                    >
                        Отправить запись
                    </Button>
                </Box>
                <Typography variant="body1" sx={{ mb: 1 }}>
                    Дата выпуска: {formatReleaseDate(gameInfo.releaseDate)}
                </Typography>
                <Typography variant="body1">{gameInfo.description}</Typography>
            </Box>
            <Box sx={{ display: 'flex', gap: '22px', mb: 1 }}>
                {gameInfo.categories.map((cat) => {
                    const isActive = activeCategory === cat.id;
                    return (
                        <Button
                            key={cat.id}
                            variant={isActive ? 'contained' : 'outlined'}
                            onClick={() => setActiveCategory(cat.id)}
                            sx={{
                                height: '28px',
                                padding: '4.5px 28px',
                                borderTopLeftRadius: '24px',
                                borderTopRightRadius: '24px',
                                backgroundColor: isActive ? '#DCFC6A' : undefined,
                                color: isActive ? '#000000' : undefined,
                                transition: 'box-shadow 0.3s, opacity 0.3s',
                                '&:hover': {
                                    backgroundColor: isActive ? '#DCFC6A' : undefined,
                                    color: isActive ? '#000000' : undefined,
                                    boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.2)',
                                    opacity: 0.9,
                                },
                            }}
                        >
                            {cat.name}
                        </Button>
                    );
                })}
            </Box>
            <Box sx={{ border: '1px solid #DCDBE0', width: '100%', mb: 3 }} />
            <Typography variant="h6" sx={{ mb: 2 }}>
                Описание категории
            </Typography>
            <Typography variant="body1" sx={{ mb: 3 }}>
                Здесь можно написать дополнительную информацию о выбранной категории.
            </Typography>
            <Typography variant="h6" sx={{ mb: 2 }}>
                Рейтинги игроков
            </Typography>
            <Paper
                sx={{
                    border: '1px solid #E6E6E6',
                    borderTopRightRadius: '10px',
                    borderTopLeftRadius: '10px',
                }}
            >
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell
                                onClick={() => handleSort('player')}
                                sx={{ cursor: 'pointer' }}
                            >
                                <Typography variant="body2">Игрок</Typography>
                            </TableCell>
                            <TableCell
                                onClick={() => handleSort('time')}
                                sx={{ cursor: 'pointer' }}
                            >
                                <Typography variant="body2">
                                    Время{' '}
                                    {sortField === 'time'
                                        ? sortOrder === 'asc'
                                            ? '↑'
                                            : '↓'
                                        : ''}
                                </Typography>
                            </TableCell>
                            <TableCell
                                onClick={() => handleSort('submitted_at')}
                                sx={{ cursor: 'pointer' }}
                            >
                                <Typography variant="body2">
                                    Дата{' '}
                                    {sortField === 'submitted_at'
                                        ? sortOrder === 'asc'
                                            ? '↑'
                                            : '↓'
                                        : ''}
                                </Typography>
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {sortedRecords.map((record, idx) => (
                            <TableRow key={idx}>
                                <TableCell>
                                    <Typography variant="body3">{record.player}</Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography variant="body3">
                                        {formatTime(record.time)}
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography variant="body3">
                                        {formatDate(record.submitted_at)}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>

            <AuthModal
                open={openAuthModal}
                onClose={() => setOpenAuthModal(false)}
            />
        </Container>
    );
}
