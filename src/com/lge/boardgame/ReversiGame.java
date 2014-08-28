package com.lge.boardgame;

import java.awt.Point;
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

    private List<Point> nextMoves(int me) {
        List<Point> myPoints = points(me);
        List<Point> result = new ArrayList<>();
        final Point directions[] = { new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1),
                new Point(1, 1), new Point(1, -1), new Point(-1, -1), new Point(-1, 1) };
        for (Point myPoint: myPoints) {
            for (Point dir: directions) {
                Point neighbor = new Point(myPoint.x + dir.x, myPoint.y + dir.y);
                if (cellEmpty(neighbor) || outOfBoard(neighbor) || me == get(neighbor.x, neighbor.y)) {
                    continue;
                }
                Point stoppedPoint = stretchUntilDifferent(neighbor, dir);
                if (!outOfBoard(stoppedPoint) && cellEmpty(stoppedPoint)) {
                    result.add(stoppedPoint);
                }
            }
        }
        return result;
    }

    private Point stretchUntilDifferent(Point start, Point dir) {
        //TODO
        return null;
    }

    private List<Point> points(int me) {
        List<Point> points = new ArrayList<>();
        for(int x = 0; x < data.length; ++x) {
            for(int y = 0; y < data[0].length; ++y) {
                if (data[x][y] == me) {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    @Override
    public void init() {
        super.init();
        int centerX = getSize() / 2;
        int centerY = getSize() / 2;
        move(new Point(centerX, centerY));
        move(new Point(centerX-1, centerY));
        move(new Point(centerX-1, centerY-1));
        move(new Point(centerX, centerY-1));
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
