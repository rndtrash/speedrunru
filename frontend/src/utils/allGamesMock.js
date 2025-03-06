import { gameInfoMock } from './gameInfoMock';

export const allGamesMock = Array.from({ length: 10000 }, (_, index) => {
    const baseGame = gameInfoMock[index % gameInfoMock.length];
    return {
        name: baseGame.name,
        description: baseGame.description,
        game_data: baseGame.releaseDate,
        image: baseGame.icon,
        gameId: baseGame.id
    };
});
