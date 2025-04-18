import React, { useState, useEffect, useRef } from 'react';
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
    Typography,
    Paper,
    ListItemButton,
} from '@mui/material';
import { Search as SearchIcon, Menu as MenuIcon } from '@mui/icons-material';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { isAuthorized, getCurrentUser, clearAuthToken, clearCurrentUser } from '../utils/authStore';
import { allGamesMock } from '../utils/allGamesMock';

function HideOnScroll(props) {
    const { children, window: windowProp } = props;
    const trigger = useScrollTrigger({
        target: windowProp ? windowProp() : (typeof window !== 'undefined' ? window : undefined),
        disableHysteresis: true,
        threshold: 20,
    });
    return (
        <Slide appear={false} direction="down" in={!trigger}>
            {children}
        </Slide>
    );
}

export default function Header(props) {
    const isMobile = useMediaQuery('(max-width:1050px)');
    const location = useLocation();
    const navigate = useNavigate();
    const [tabValue, setTabValue] = useState(null);
    const [mobileOpen, setMobileOpen] = useState(false);

    const [searchTerm, setSearchTerm] = useState('');
    const [filteredGames, setFilteredGames] = useState([]);
    const searchBoxRef = useRef(null);

    // Определяем активную вкладку по пути
    useEffect(() => {
        if (location.pathname.startsWith('/Games')) {
            setTabValue(0);
        } else if (location.pathname.startsWith('/discussions')) {
            setTabValue(1);
        } else if (location.pathname.startsWith('/FAQ')) {
            setTabValue(2);
        } else {
            setTabValue(null);
        }
    }, [location.pathname]);

    useEffect(() => {
        if (searchTerm.trim() === '') {
            setFilteredGames([]);
        } else {
            const filtered = allGamesMock.filter(game =>
                game.name.toLowerCase().startsWith(searchTerm.toLowerCase())
            );
            const uniqueGames = [];
            const seenNames = new Set();
            filtered.forEach(game => {
                if (!seenNames.has(game.name)) {
                    seenNames.add(game.name);
                    uniqueGames.push(game);
                }
            });
            setFilteredGames(uniqueGames.slice(0, 10));
        }
    }, [searchTerm]);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (searchBoxRef.current && !searchBoxRef.current.contains(event.target)) {
                setFilteredGames([]);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

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

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleGameSelect = (gameId) => {
        setSearchTerm('');
        setFilteredGames([]);
        navigate(`/games/${gameId}`);
    };

    const drawer = (
        <Box onClick={handleDrawerToggle} sx={{ width: 250 }}>
            <Box sx={{ p: 2, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <img src="/assets/logos/mainLogo.png" alt="logo" style={{ maxWidth: '150px' }} />
            </Box>
            <Divider />
            <List>
                <ListItem component={Link} to="/Games">
                    <ListItemText primary="Игры" />
                </ListItem>
                <ListItem component={Link} to="/discussions">
                    <ListItemText primary="Обсуждения" />
                </ListItem>
                <ListItem component={Link} to="/FAQ">
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
                                            to="/FAQ"
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
                                <Box sx={{ position: 'relative', mr: '16px' }} ref={searchBoxRef}>
                                    <TextField
                                        variant="outlined"
                                        size="small"
                                        placeholder="Поиск"
                                        value={searchTerm}
                                        onChange={handleSearchChange}
                                        sx={{
                                            bgcolor: 'white',
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
                                    {filteredGames.length > 0 && (
                                        <Paper
                                            sx={{
                                                position: 'absolute',
                                                top: '110%',
                                                left: 0,
                                                right: 0,
                                                maxHeight: 300,
                                                overflowY: 'auto',
                                                zIndex: 10,
                                            }}
                                        >
                                            {filteredGames.map(game => (
                                                <ListItemButton key={game.gameId} onClick={() => handleGameSelect(game.gameId)}>
                                                    <img
                                                        src={game.image}
                                                        alt={game.name}
                                                        style={{ width:75, height: 75, marginRight: 8, borderRadius: '4px',objectFit:"contain" }}
                                                    />
                                                    <ListItemText primary={game.name} />
                                                </ListItemButton>
                                            ))}
                                        </Paper>
                                    )}
                                </Box>
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
                        '& .MuiDrawer-paper': { boxSizing: 'border-box', width: 250 },
                    }}
                >
                    {drawer}
                </Drawer>
            </nav>
        </React.Fragment>
    );
}
