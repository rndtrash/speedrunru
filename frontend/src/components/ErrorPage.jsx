import React from 'react';
import { Box, Container, Typography } from '@mui/material';

export default function ErrorPage({ errorCode = 'Ошибка' }) {
    return (
        <Container
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                textAlign: 'center',
                fontFamily: 'Nunito Sans, sans-serif',
            }}
        >
            <Box
                component="img"
                src="/assets/mascots/pageErrorMascot.png"
                alt="Page Error Mascot"
                sx={{
                    maxWidth: '100%',
                    mb: 4,
                }}
            />
            <Typography
                sx={{
                    fontWeight: 800,
                    fontSize: '43px',
                    lineHeight: '58.65px',
                    letterSpacing: '0%',
                    color: '#000000',
                }}
            >
                Уупс, что-то пошло не так. {errorCode}
            </Typography>
        </Container>
    );
}
