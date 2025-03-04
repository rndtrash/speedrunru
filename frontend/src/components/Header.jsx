import React, { useState, useEffect } from 'react';
import {
    AppBar,
    Toolbar,
    Button,
    Box,
    Tabs,
    Tab,
    TextField,
    useScrollTrigger,
    Slide,
    IconButton,
    Drawer,
    List,
    ListItem,
    ListItemText,
    Divider,
    InputAdornment,
    useMediaQuery,
    useTheme,
    Typography,
} from '@mui/material';
import { Search as SearchIcon, Menu as MenuIcon } from '@mui/icons-material';
import { Link, useLocation } from 'react-router-dom';
import { isAuthorized, getCurrentUser, clearAuthToken, clearCurrentUser } from '../utils/authStore';

function HideOnScroll(props) {
    const { children, window: windowProp } = props;
    const trigger = useScrollTrigger({
        target: windowProp ? windowProp() : (typeof window !== 'undefined' ? window : undefined),
    });
    return (
        <Slide appear={false} direction="down" in={!trigger}>
            {children}
        </Slide>
    );
}

export default function Header(props) {
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));
    const location = useLocation();
    const [tabValue, setTabValue] = useState(null);
    const [mobileOpen, setMobileOpen] = useState(false);

    useEffect(() => {
        if (location.pathname === '/') {
            setTabValue(null);
        }
    }, [location.pathname]);

    const handleTabChange = (event, newValue) => {
        setTabValue(newValue);
    };

    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    };

    const handleLogout = () => {
        clearAuthToken();
        clearCurrentUser();
        window.location.href = '/login';
    };

    const drawer = (
        <Box onClick={handleDrawerToggle} sx={{ width: 250 }}>
            <Box sx={{ p: 2, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <img src="/assets/logos/mainLogo.png" alt="logo" style={{ maxWidth: '150px' }} />
            </Box>
            <Divider />
            <List>
                <ListItem component={Link} to="/">
                    <ListItemText primary="Игры" />
                </ListItem>
                <ListItem component={Link} to="/discussions">
                    <ListItemText primary="Обсуждения" />
                </ListItem>
                <ListItem component={Link} to="/help">
                    <ListItemText primary="Помощь" />
                </ListItem>
            </List>
            <Divider />
            <Box sx={{ p: 2 }}>
                <TextField
                    variant="outlined"
                    size="small"
                    placeholder="Поиск"
                    fullWidth
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <SearchIcon />
                            </InputAdornment>
                        ),
                    }}
                />
            </Box>
            <Divider />
            <Box sx={{ display: 'flex', flexDirection: 'column', p: 2 }}>
                <Button component={Link} to="/login" sx={{ mb: 1, textTransform: 'none' }}>
                    Войти
                </Button>
                <Button component={Link} to="/Registration" variant="contained" sx={{ textTransform: 'none' }}>
                    Зарегистрироваться
                </Button>
            </Box>
        </Box>
    );

    const currentUser = getCurrentUser();
    const authorized = isAuthorized();

    return (
        <React.Fragment>
            <HideOnScroll {...props}>
                <AppBar
                    position="sticky"
                    elevation={0}
                    sx={{
                        bgcolor: '#fff',
                        borderRadius: '24px',
                        margin: '10px',
                        width: '98%',
                    }}
                >
                    <Toolbar>
                        {isMobile && (
                            <IconButton color="inherit" edge="start" onClick={handleDrawerToggle} sx={{ mr: 2 }}>
                                <MenuIcon sx={{ color: '#000000' }} />
                            </IconButton>
                        )}
                        <Box
                            component={Link}
                            to="/"
                            sx={{
                                flexGrow: 1,
                                display: 'flex',
                                alignItems: 'center',
                                textDecoration: 'none',
                            }}
                        >
                            <img src="/assets/logos/mainLogo.png" alt="logo" style={{ height: '40px' }} />
                        </Box>
                        {!isMobile && (
                            <React.Fragment>
                                <Box sx={{ display: 'flex', flexGrow: 1, ml: 2 }}>
                                    <Tabs
                                        value={tabValue}
                                        onChange={handleTabChange}
                                        textColor="inherit"
                                        indicatorColor="transparent"
                                    >
                                        <Tab
                                            component={Link}
                                            to="/Games"
                                            label="Игры"
                                            sx={{
                                                color: '#000000',
                                                bgcolor: tabValue === 0 ? '#DCFC6A' : 'inherit',
                                                fontFamily: 'Nunito Sans, sans-serif',
                                                fontSize: '14px',
                                                fontWeight: 400,
                                                lineHeight: '19.1px',
                                                textTransform: 'none',
                                                borderRadius: '20px',
                                            }}
                                        />
                                        <Tab
                                            component={Link}
                                            to="/discussions"
                                            label="Обсуждения"
                                            sx={{
                                                color: '#000000',
                                                bgcolor: tabValue === 1 ? '#DCFC6A' : 'inherit',
                                                fontFamily: 'Nunito Sans, sans-serif',
                                                fontSize: '14px',
                                                fontWeight: 400,
                                                lineHeight: '19.1px',
                                                textTransform: 'none',
                                                borderRadius: '20px',
                                            }}
                                        />
                                        <Tab
                                            component={Link}
                                            to="/help"
                                            label="Помощь"
                                            sx={{
                                                color: '#000000',
                                                bgcolor: tabValue === 2 ? '#DCFC6A' : 'inherit',
                                                fontFamily: 'Nunito Sans, sans-serif',
                                                fontSize: '14px',
                                                fontWeight: 400,
                                                lineHeight: '19.1px',
                                                textTransform: 'none',
                                                borderRadius: '20px',
                                            }}
                                        />
                                    </Tabs>
                                </Box>
                                <TextField
                                    variant="outlined"
                                    size="small"
                                    placeholder="Поиск"
                                    sx={{
                                        bgcolor: 'white',
                                        mr: '16px',
                                        '& .MuiOutlinedInput-root': {
                                            padding: 0,
                                            height: 36,
                                            borderRadius: '20px',
                                        },
                                        border: '1px solid #DCDBE0',
                                        borderRadius: '20px',
                                    }}
                                    InputProps={{
                                        startAdornment: (
                                            <InputAdornment position="start">
                                                <SearchIcon sx={{ color: 'action.active', fontSize: '20px', marginLeft: '10px' }} />
                                            </InputAdornment>
                                        ),
                                        sx: {
                                            paddingLeft: '8px',
                                        },
                                    }}
                                />
                                {authorized && currentUser ? (
                                    <Box sx={{ display: 'flex', alignItems: 'center', ml: 10 }}>
                                        <img
                                            src="/assets/defaultAvatar.png"
                                            alt="avatar"
                                            style={{ width: '40px', height: '40px', borderRadius: '50%', marginRight: '8px' }}
                                        />
                                        <Typography sx={{ color: '#000000', mr: 1 }}>
                                            {currentUser.username}
                                        </Typography>
                                        <IconButton onClick={handleLogout}>
                                            <img src="/assets/logOutIcon.png" alt="logout" style={{ width: '24px', height: '24px' }} />
                                        </IconButton>
                                    </Box>
                                ) : (
                                    <React.Fragment>
                                        <Box
                                            component={Link}
                                            to="/login"
                                            sx={{
                                                mr: 2,
                                                textDecoration: 'none',
                                                color: '#000000',
                                                textTransform: 'none',
                                            }}
                                        >
                                            Войти
                                        </Box>
                                        <Button
                                            component={Link}
                                            to="/Registration"
                                            sx={{
                                                textTransform: 'none',
                                                borderRadius: '20px',
                                                backgroundColor: '#6C67EC',
                                                color: '#fff',
                                                padding: '8px 16px',
                                                '&:hover': {
                                                    backgroundColor: '#5749D0',
                                                },
                                            }}
                                        >
                                            Зарегистрироваться
                                        </Button>
                                    </React.Fragment>
                                )}
                            </React.Fragment>
                        )}
                    </Toolbar>
                </AppBar>
            </HideOnScroll>
            <nav>
                <Drawer
                    anchor="left"
                    open={mobileOpen}
                    onClose={handleDrawerToggle}
                    ModalProps={{ keepMounted: true }}
                    sx={{
                        display: { xs: 'block', sm: 'none' },
                        '& .MuiDrawer-paper': { boxSizing: 'border-box', width: 250 },
                    }}
                >
                    {drawer}
                </Drawer>
            </nav>
        </React.Fragment>
    );
}
