export const gameRecordsMock = Array.from({ length: 100 }, () => {
    const categories = ["Any%", "Все боссы"];
    const category = categories[Math.floor(Math.random() * categories.length)];

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
    const player = players[Math.floor(Math.random() * players.length)];

    const platforms = ["PC", "PS4", "Android", "IOS", "Switch"];
    const platform = platforms[Math.floor(Math.random() * platforms.length)];

    const time = Math.floor(Math.random() * 4500000) + 500000;

    const day = Math.floor(Math.random() * 22) + 10;
    const hour = Math.floor(Math.random() * 24);
    const minute = Math.floor(Math.random() * 60);
    const submitted_at = new Date(2021, 6, day, hour, minute).toISOString();

    return {
        player,
        platform,
        time,
        submitted_at,
        category
    };
});
