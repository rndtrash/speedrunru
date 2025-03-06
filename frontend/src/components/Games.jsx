import React, { useState } from 'react';
import { Box, Typography, Grid, Card, CardActionArea, Pagination } from '@mui/material';
import { Link } from 'react-router-dom';
import { allGamesMock } from '../utils/allGamesMock';

const Games = () => {
    const [page, setPage] = useState(1);
    const itemsPerPage = 30;

    const handleChange = (event, value) => {
        setPage(value);
    };

    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentGames = allGamesMock.slice(startIndex, endIndex);

    return (
        <Box sx={{ padding: "24px", backgroundColor: "#ffffff", borderRadius: "12px" }}>
            <Typography
                sx={{
                    fontFamily: 'Nunito Sans',
                    fontWeight: 800,
                    fontSize: '24px',
                    lineHeight: '32.74px',
                    letterSpacing: '0%',
                    mb: "20px",
                    ml: 4,
                }}
            >
                Игры
            </Typography>

            <Grid container sx={{ columnGap: '32px', rowGap: '18px' }}>
                {currentGames.map((game) => {
                    let fontSize;
                    switch (true) {
                        case (game.name.length > 16):
                            fontSize = "12px";
                            break;
                        case (game.name.length > 12):
                            fontSize = "14px";
                            break;
                        case (game.name.length > 10):
                            fontSize = "16px";
                            break;
                        default:
                            fontSize = "16px";
                    }
                    return (
                        <Grid item key={game.gameId}>
                            <Card
                                sx={{
                                    width: 119,
                                    borderRadius: '16px',
                                    overflow: 'hidden',
                                }}
                            >
                                <CardActionArea
                                    component={Link}
                                    to={`/Games/${game.gameId}`}
                                    sx={{ height: '100%', display: "flex !important", flexDirection: "column" }}
                                >
                                    <Box
                                        component="img"
                                        src={game.image}
                                        alt={game.name}
                                        sx={{
                                            width: 119,
                                            height: 139,
                                            borderTopLeftRadius: '16px',
                                            borderTopRightRadius: '16px',
                                            objectFit: 'cover',
                                        }}
                                    />
                                    <Box
                                        sx={{
                                            width: 119,
                                            height: 37,
                                            background: "#6C67EC",
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: 'center',
                                            borderBottomLeftRadius: '16px',
                                            borderBottomRightRadius: '16px',
                                            p: 0.5,
                                        }}
                                    >
                                        <Typography
                                            sx={{
                                                fontFamily: 'Nunito Sans',
                                                fontWeight: 700,
                                                fontSize: fontSize,
                                                lineHeight: '13.82px',
                                                letterSpacing: '0%',
                                                color: '#FFFFFF',
                                                textAlign: 'center',
                                                display: '-webkit-box',
                                                WebkitLineClamp: 2,
                                                WebkitBoxOrient: 'vertical',
                                                overflow: 'hidden',
                                            }}
                                        >
                                            {game.name}
                                        </Typography>
                                    </Box>
                                </CardActionArea>
                            </Card>
                        </Grid>
                    );
                })}
            </Grid>

            <Box sx={{ display: 'flex', justifyContent: 'center', mt: 3 }}>
                <Pagination
                    count={Math.max(Math.ceil(allGamesMock.length / itemsPerPage),1)}
                    page={page}
                    onChange={handleChange}
                    color="primary"
                />
            </Box>
        </Box>
    );
};

export default Games;
