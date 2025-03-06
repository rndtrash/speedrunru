import {gameInfoMock} from './gameInfoMock.js';
import {flagDictionary} from "./flagDictionary.js";

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
    const gameChoice = gameInfoMock[Math.floor(Math.random() * gameInfoMock.length)];

    const countryCodes = Object.keys(flagDictionary);
    const randomCountryCode = countryCodes[Math.floor(Math.random() * countryCodes.length)];
    const randomFlag = flagDictionary[randomCountryCode];
    const user = nicknames[Math.floor(Math.random() * nicknames.length)] + Math.floor(Math.random() * 1000);

    const place = Math.floor(Math.random() * 100) + 1;
    const time = Math.floor(Math.random() * 50000000) + 10000;
    const randomDay = Math.floor(Math.random() * 31) + 1;
    const randomHour = Math.floor(Math.random() * 24);
    const randomMinute = Math.floor(Math.random() * 60);
    const randomMiliseconds = Math.floor(Math.random() * 1000);
    const date = new Date(2023, 7, randomDay, randomHour, randomMinute, randomMiliseconds).toISOString();

    return {
        id: gameChoice.id,
        game: gameChoice.name,
        icon: gameChoice.icon,
        place,
        user,
        time,
        date,
        country: randomCountryCode,
        flag: randomFlag.src,
        flagAlt: randomFlag.alt
    };
});
