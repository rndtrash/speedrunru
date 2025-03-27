import React from 'react';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import './index.css';
import App from './App.jsx';

const theme = createTheme({
    typography: {
        body2: {
            fontFamily: 'Nunito Sans',
            fontWeight: 600,
            fontSize: '16px',
            lineHeight: '21.82px',
            letterSpacing: '0%',
        },
        body3: {
            fontFamily: 'Nunito Sans',
            fontWeight: 400,
            fontSize: '16px',
            lineHeight: '21.82px',
            letterSpacing: '0%',
            color: '#636363',
        },
    },
    components: {
        MuiTypography: {
            variants: [
                {
                    props: { variant: 'body3' },
                    style: {
                        fontFamily: 'Nunito Sans',
                        fontWeight: 400,
                        fontSize: '16px',
                        lineHeight: '21.82px',
                        letterSpacing: '0%',
                        color: '#636363',
                    },
                },
            ],
        },
        MuiCssBaseline: {
            styleOverrides: {
                body: {
                    backgroundColor: '#f9f9f9',
                },
            },
        },
    },
});

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <App />
        </ThemeProvider>
    </StrictMode>
);
