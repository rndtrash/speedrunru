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

function formatTime(ms) {
    if (ms <= 0) return '0:00:00';
    const hours = Math.floor(ms / 3600000);
    const minutes = Math.floor((ms % 3600000) / 60000);
    const seconds = Math.floor((ms % 60000) / 1000);
    return `${hours.toString().padStart(1, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
}

function formatDate(dateStr) {
    return new Date(dateStr).toLocaleDateString('ru-RU');
}

function formatReleaseDate(dateStr) {
    const date = new Date(dateStr);
    return `${String(date.getDate()).padStart(2, '0')}.${String(date.getMonth() + 1).padStart(2, '0')}.${date.getFullYear()}`;
}

export default function GameInfo() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [gameInfo, setGameInfo] = useState(null);
    const [activeCategory, setActiveCategory] = useState(null);
    const [records, setRecords] = useState([]);
    const [sortField, setSortField] = useState(null);
    const [sortOrder, setSortOrder] = useState('asc');

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
        if (gameInfo?.categories?.length) setActiveCategory(gameInfo.categories[0].id);
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

    return (
        <Container sx={{ mt: 5 }}>
            <Box sx={{ display: 'flex', alignItems: 'flex-start', gap: 3, mb: 3 }}>
                <Box component="img" src={gameInfo.icon} alt={gameInfo.name} sx={{ width: 120, height: 120, borderRadius: 2, objectFit: 'cover' }} />
                <Box sx={{ flexGrow: 1 }}>
                    <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                        <Box>
                            <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 1 }}>{gameInfo.name}</Typography>
                            <Typography variant="body1">Дата выпуска: {formatReleaseDate(gameInfo.releaseDate)}</Typography>
                        </Box>
                        <Button variant="contained" color="primary" sx={{ height: 40 }}>Отправить запись</Button>
                    </Box>
                    <Typography variant="body1">{gameInfo.description}</Typography>
                </Box>
            </Box>
            <Box sx={{ display: 'flex', gap: 2, mb: 3 }}>
                {gameInfo.categories.map((cat) => (
                    <Button key={cat.id} variant={activeCategory === cat.id ? 'contained' : 'outlined'} onClick={() => setActiveCategory(cat.id)}>
                        {cat.name}
                    </Button>
                ))}
            </Box>
            <Typography variant="h6" sx={{ mb: 2 }}>Описание категории</Typography>
            <Typography variant="body1" sx={{ mb: 3 }}>Здесь можно написать дополнительную информацию о выбранной категории.</Typography>
            <Typography variant="h6" sx={{ mb: 2 }}>Рейтинги игроков</Typography>
            <Paper>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Игрок</TableCell>
                            <TableCell>Платформа</TableCell>
                            <TableCell onClick={() => handleSort('time')} sx={{ cursor: 'pointer' }}>
                                Время {sortField === 'time' ? (sortOrder === 'asc' ? '↑' : '↓') : ''}
                            </TableCell>
                            <TableCell onClick={() => handleSort('submitted_at')} sx={{ cursor: 'pointer' }}>
                                Дата {sortField === 'submitted_at' ? (sortOrder === 'asc' ? '↑' : '↓') : ''}
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {sortedRecords.map((record, idx) => (
                            <TableRow key={idx}>
                                <TableCell>{record.player}</TableCell>
                                <TableCell>{record.platform}</TableCell>
                                <TableCell>{formatTime(record.time)}</TableCell>
                                <TableCell>{formatDate(record.submitted_at)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>
        </Container>
    );
}
