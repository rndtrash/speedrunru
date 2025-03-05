import React from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';
import Login from './Login';
import Registration from './Registration';
import GamesLatest from './GamesLatest.jsx';
import GameInfo from './GameInfo';
import ErrorPage from './ErrorPage';
import { Box, Grow } from '@mui/material';
import Games from "./Games.jsx";

export default function Index() {
    const location = useLocation();

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            <Header />

            <Box
                component="main"
                sx={{
                    flex: 1,
                    display: 'flex',
                    justifyContent: 'center',
                }}
            >
                <Box sx={{ width: '100%', maxWidth: '1600px', marginTop: '70px' }}>
                    <Routes location={location} key={location.pathname}>
                        <Route
                            path="/"
                            element={
                                <Grow in timeout={1000}>
                                    <Box>
                                        <GamesLatest />
                                    </Box>
                                </Grow>
                            }
                        />
                        <Route
                            path="/games/:id"
                            element={
                                <Grow in timeout={1000}>
                                    <Box>
                                        <GameInfo />
                                    </Box>
                                </Grow>
                            }
                        />
                        <Route
                            path="/games"
                            element={
                            <Grow in timeout={1000}>
                                <Box>
                                    <Games/>
                                </Box>
                            </Grow>
                            }
                        />
                        <Route
                            path="/login"
                            element={
                                <Grow in timeout={1000}>
                                    <Box>
                                        <Login />
                                    </Box>
                                </Grow>
                            }
                        />
                        <Route
                            path="/registration"
                            element={
                                <Grow in timeout={1000}>
                                    <Box>
                                        <Registration />
                                    </Box>
                                </Grow>
                            }
                        />
                        <Route
                            path="*"
                            element={
                                <Grow in timeout={1000}>
                                    <Box>
                                        <ErrorPage errorCode="404" />
                                    </Box>
                                </Grow>
                            }
                        />
                    </Routes>
                </Box>
            </Box>

            <Footer />
        </Box>
    );
}
