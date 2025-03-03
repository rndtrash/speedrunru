import React from 'react';
import { Box, Typography } from '@mui/material';

export default function Footer() {
    return (
        <Box
            component="footer"
            sx={{
                bgcolor: 'primary.main',
                color: '#fff',
                p: 2,
                textAlign: 'center',
                mt: 4,
            }}
        >
            <Typography variant="body1">
                © {new Date().getFullYear()} Спидраны. Все права защищены.
            </Typography>
        </Box>
    );
}
