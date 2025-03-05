import React from 'react';
import { Box, Typography, Grid, Card, CardActionArea } from '@mui/material';
import { Link } from 'react-router-dom';
import { allGamesMock } from '../utils/allGamesMock';

const Games = () => {
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
                {allGamesMock.map((game) => {
                    let fontSize = game.name.length > 12 ? '14px' : '16px';
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
                                    to={`/games/${game.gameId}`}
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
        </Box>
    );
};

export default Games;
