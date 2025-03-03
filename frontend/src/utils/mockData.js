import { gameInfoMock } from './gameInfoMock.js';
// МММ мок на моке

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

export const speedrunMockData = Array.from({ length: 10 }, () => {
    const gameChoice =
        gameInfoMock[Math.floor(Math.random() * gameInfoMock.length)];

    const user =
        nicknames[Math.floor(Math.random() * nicknames.length)] +
        Math.floor(Math.random() * 1000);

    const place = Math.floor(Math.random() * 100) + 1;

    const time = Math.floor(Math.random() * 50000000) + 10000;

    const randomDay = Math.floor(Math.random() * 31) + 1;
    const randomHour = Math.floor(Math.random() * 24);
    const randomMinute = Math.floor(Math.random() * 60);
    const date = new Date(2023, 7, randomDay, randomHour, randomMinute).toISOString();

    return {
        id: gameChoice.id,
        game: gameChoice.name,
        icon: gameChoice.icon,
        place,
        user,
        time,
        date,
    };
});
