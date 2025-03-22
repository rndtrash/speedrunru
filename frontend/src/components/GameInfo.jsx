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
    TablePagination,
    Tooltip,
} from '@mui/material';
import { gameInfoMock } from '../utils/gameInfoMock';
import { gameRecordsMock } from '../utils/gameRecordsMock';
import { isAuthorized } from '../utils/authStore';
import AuthModal from './AuthModal';
import SpeedRunSendModal from './speedRunSendModal';

function formatTime(ms) {
    if (ms <= 0) return '0:00:00.000';
    const hours = Math.floor(ms / 3600000);
    const minutes = Math.floor((ms % 3600000) / 60000);
    const seconds = Math.floor((ms % 60000) / 1000);
    const milliseconds = ms % 1000;
    return `${hours}:${minutes.toString().padStart(2, '0')}:${seconds
        .toString()
        .padStart(2, '0')}.${milliseconds.toString().padStart(3, '0')}`;
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
    const [page, setPage] = useState(0);
    const rowsPerPage = 10;

    const [openAuthModal, setOpenAuthModal] = useState(false);
    const [openSpeedRunSendModal, setOpenSpeedRunSendModal] = useState(false);

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
            setTimeout(() => {
                const filteredRecords = gameRecordsMock.filter((_, idx) =>
                    activeCategory === 1 ? idx % 2 === 0 : idx % 2 !== 0
                );
                setRecords(filteredRecords);
                setPage(0);
            }, 500);
        }
    }, [activeCategory]);

    const handleSort = (field) => {
        if (sortField !== field) {
            setSortField(field);
            setSortOrder('asc');
        } else {
            if (sortOrder === 'asc') {
                setSortOrder('desc');
            } else if (sortOrder === 'desc') {
                setSortField(null);
                setSortOrder('asc');
            }
        }
        setPage(0);
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

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

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
            setOpenSpeedRunSendModal(true);
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
                                onClick={() => handleSort('player_name')}
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
                        {sortedRecords
                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                            .map((record, idx) => (
                                <TableRow
                                    key={idx}
                                    onClick={() => window.open(record.run_link, '_blank')}
                                    sx={{ cursor: 'pointer' }}
                                >
                                    <TableCell>
                                        <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                            <Tooltip title={record.player_country_name}>
                                                <Box
                                                    component="img"
                                                    src={record.player_country_flag}
                                                    alt={record.player_country_name}
                                                    sx={{
                                                        width: 24,
                                                        height: 24,
                                                        mr: 1,
                                                        borderRadius: '50%',
                                                    }}
                                                />
                                            </Tooltip>
                                            <Typography variant="body3">
                                                {record.player_name}
                                            </Typography>
                                        </Box>
                                    </TableCell>
                                    <TableCell>
                                        <Typography variant="body3">
                                            {formatTime(Number(record.time))}
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
                <TablePagination
                    component="div"
                    count={sortedRecords.length}
                    page={page}
                    onPageChange={handleChangePage}
                    rowsPerPage={rowsPerPage}
                    rowsPerPageOptions={[]}
                    labelDisplayedRows={({ from, to, count }) =>
                        `${from}–${to} из ${count}`
                    }
                />
            </Paper>

            <AuthModal open={openAuthModal} onClose={() => setOpenAuthModal(false)} />

            <SpeedRunSendModal
                open={openSpeedRunSendModal}
                onClose={() => setOpenSpeedRunSendModal(false)}
                categories={gameInfo?.categories || []}
                activeCategory={gameInfo?.categories?.find((cat) => cat.id === activeCategory)}
            />
        </Container>
    );
}
