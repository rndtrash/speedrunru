import { flagDictionary } from "./flagDictionary.js";

const players = [
    "SpeedRunNinja",
    "TurboGamer",
    "FastTrack",
    "QuickQuestMaster",
    "ProRacer",
    "LightningBolt",
    "Flash",
    "NinjaWarrior",
    "RocketMan",
    "SamuraiJack",
    "BlazeRunner",
    "PhantomStriker"
];

export const gameRecordsMock = Array.from({ length: 100 }, () => {
    const player_name = players[Math.floor(Math.random() * players.length)];

    const countryCodes = Object.keys(flagDictionary);
    const randomCountryCode = countryCodes[Math.floor(Math.random() * countryCodes.length)];
    const randomFlag = flagDictionary[randomCountryCode];

    const time = Math.floor(Math.random() * 4500000) + 500000;

    const day = Math.floor(Math.random() * 22) + 10;
    const hour = Math.floor(Math.random() * 24);
    const minute = Math.floor(Math.random() * 60);
    const submitted_at = new Date(2021, 6, day, hour, minute).toISOString();

    const run_link = `https://www.youtube.com/watch?v=dQw4w9WgXcQ`;

    return {
        player_name,
        player_country_flag: randomFlag.src,
        player_country_name: randomFlag.alt,
        time: time.toString(),
        submitted_at,
        run_link
    };
});
