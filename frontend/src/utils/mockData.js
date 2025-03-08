import {gameInfoMock} from './gameInfoMock.js';
import {flagDictionary} from "./flagDictionary.js";

const nicknames = [
    "Speedster", "Lightning", "Flash", "Rocket", "Ninja", "Samurai", "LegendX", "Turbo", "Blaze", "Phantom",
    "Vortex", "Cyclone", "Whirlwind", "Comet", "Meteor", "Astro", "Quantum", "Eclipse", "Nova", "Streak",
    "Thunderbolt", "Hurricane", "Tornado", "Tempest", "Raptor", "Falcon", "Predator", "Hunter", "Shadow", "Specter",
    "Ghost", "Reaper", "Viper", "Cobra", "Venom", "Scorpion", "Lynx", "Panther", "Tiger", "Jaguar",
    "Cheetah", "Stallion", "Mustang", "Charger", "Bullet", "Maverick", "Outlaw", "Rebel", "Bandit", "Raider",
    "Renegade", "Vandal", "Cyclops", "Titan", "Colossus", "Goliath", "Hercules", "Brute", "Barbarian", "Gladiator",
    "Spartan", "Centurion", "Knight", "Paladin", "Crusader", "Sentinel", "Guardian", "Protector", "MaverickX", "Inferno",
    "Ember", "Pyro", "Scorch", "Flare", "Sizzle", "Burn", "Kindle", "Fuego", "Incendium", "Radiant",
    "Solar", "Helios", "BlazeX", "Scorcher", "Wildfire", "Spark", "Flashfire", "Combustion", "Ignite", "Pyromaniac",
    "RagingFire", "Emberstorm", "Flame", "Sear", "Smolder", "Cinder", "Ash", "Char", "Soot", "Infernal",
    "NovaStrike", "Starburst", "Cosmos", "Nebula", "Galaxy", "Universe", "Meteorite", "AstroX", "Pulsar", "Orbit",
    "Satellite", "SolarFlare", "EclipseX", "Gravity", "BlackHole", "Stardust", "Celestial", "CometTail", "Zenith", "Apex",
    "Pinnacle", "Summit", "Crest", "Peak", "Vertex", "Acme", "Paragon", "Elite", "Vanguard", "Frontline",
    "Trailblazer", "Pioneer", "Explorer", "Nomad", "Wanderer", "Drifter", "Roamer", "Voyager", "Seeker", "Adventurer",
    "MaverickStorm", "RebelSoul", "WildHeart", "IronFist", "SteelForce", "Titanium", "Ironclad", "SteelBlade", "Carbon", "Alloy",
    "Chrome", "Rusty", "Electric", "Voltaic", "Dynamo", "Surge", "Shock", "Jolt", "Voltage", "Ampere",
    "Electron", "Proton", "Neutron", "Ion", "Plasma", "Fusion", "Reactor", "Magnet", "Magnetic", "Circuit",
    "Byte", "Bit", "QuantumX", "Cyber", "Digital", "Pixel", "Glitch", "Matrix", "Code", "Binary",
    "Data", "Algorithm", "Network", "Virus", "Trojan", "Hacker", "Encryptor", "Firewall", "CyberKnight", "Techno",
    "Droid", "Android", "Bot", "Synth", "Neuron", "Neural", "Circuitry", "Nano", "Micro", "Macro"
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
