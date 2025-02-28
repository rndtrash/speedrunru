export const speedrunMockData = Array.from({ length: 50 }, () => {
    const games = [
        { game: 'MiSide', icon: '/assets/gameIcons/miside.png' },
        { game: 'Risk of Rain 2', icon: '/assets/gameIcons/riskofrain2.png' },
        { game: 'Hollow Knight', icon: '/assets/gameIcons/hollowknight.png' },
    ];
    const gameChoice = games[Math.floor(Math.random() * games.length)];

    const nicknames = [
        'Speedster',
        'Lightning',
        'Flash',
        'Rocket',
        'Ninja',
        'Samurai',
        'LegendX',
        'Turbo',
        'Blaze',
        'Phantom',
    ];
    const user = nicknames[Math.floor(Math.random() * nicknames.length)] +
        Math.floor(Math.random() * 1000);

    const place = Math.floor(Math.random() * 100) + 1;

    const time = Math.floor(Math.random() * 50000000) + 10000;

    const randomDay = Math.floor(Math.random() * 31) + 1;
    const randomHour = Math.floor(Math.random() * 24);
    const randomMinute = Math.floor(Math.random() * 60);
    const date = new Date(2023, 7, randomDay, randomHour, randomMinute).toISOString();

    return {
        game: gameChoice.game,
        icon: gameChoice.icon,
        place,
        user,
        time,
        date,
    };
});
