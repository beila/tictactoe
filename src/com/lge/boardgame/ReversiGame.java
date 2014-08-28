package com.lge.boardgame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ReversiGame extends BoardGame {
    public ReversiGame(int size, int winning, Player... players) {
        super(size, winning, players);
    }

    @Override
    public boolean end() {
        return IntStream.range(0, players.length).mapToObj(this::nextMoves).allMatch(List::isEmpty);
    }

    @Override
    public List<Point> nextMoves() {
        return nextMoves(nextTurn());
    }

    private List<Point> nextMoves(int nextTurn) {
        List<Point> result = new ArrayList<>();
        final Point directions[] = { new Point(1, 0), new Point(0, 1),
                new Point(-1, 0), new Point(0, -1),
                new Point(1, 1), new Point(1, -1),
                new Point(-1, 1), new Point(-1, -1) };
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                int cell = data[x][y];
                if (cell != nextTurn)
                    continue;
                for (Point dir : directions) {
                    Point point = new Point(x + dir.x, y + dir.y);
                    if (!outOfBoard(point) && !cellEmpty(point)) {
                        result.add(point);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void init() {
        super.init();
        int centerX = getSize() / 2;
        int centerY = getSize() / 2;
        move(new Point(centerX, centerY));
        move(new Point(centerX+1, centerY));
        move(new Point(centerX+1, centerY+1));
        move(new Point(centerX, centerY+1));
    }

    @Override
    public void tryMove(Point p) {
        // TODO
        super.tryMove(p);
    }

    @Override
    public int winner() {
        Stream<long[]> histogram = IntStream.range(0, players.length).mapToObj((player) ->
                        new long[]{player,
                                Arrays.stream(data).flatMapToInt(Arrays::stream).filter((cell) -> cell == player).count()}
        );
        long maxCount = histogram.mapToLong((l) -> l[1]).max().getAsLong();
        LongStream winners = histogram.filter((longs) -> longs[1] == maxCount).mapToLong((l) -> l[0]);
        return winners.count() != 1? -1: winners.iterator().next().intValue();
    }
}
