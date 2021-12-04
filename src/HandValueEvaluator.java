import java.util.Arrays;
import java.util.List;

public class HandValueEvaluator {
    private static boolean isMonoStraight(boolean[][] cards, int lowestRank, int suit) {
        for (int r = lowestRank; r < lowestRank + 5; r++)
            if (!cards[suit][r])
                return false;
        return true;
    }
    private static boolean isStraight(int[] ranks, int lowestRank) {
        for (int r = lowestRank; r < lowestRank + 5; r++)
            if (ranks[r] == 0)
                return false;
        return true;
    }

    private interface HandChecker {
        boolean check(boolean[][] cards, int[] ranks, int[] suits);
    }
    private static boolean hasRoyalFlush(boolean[][] cards, int[] ranks, int[] suits) {
        for (int s = 0; s < 4; s++)
            if (isMonoStraight(cards, 10, s))
                return true;
        return false;
    }
    private static boolean hasStraightFlush(boolean[][] cards, int[] ranks, int[] suits) {
        for (int s = 0; s < 4; s++)
            for (int r = 1; r <= 10; r++)
                if (isMonoStraight(cards, r, s))
                    return true;
        return false;
    }
    private static boolean hasFourOfAKind(boolean[][] cards, int[] ranks, int[] suits) {
        return Arrays.stream(ranks).skip(2).anyMatch(r -> r >= 4);
    }
    private static boolean hasFullHouse(boolean[][] cards, int[] ranks, int[] suits) {
        return Arrays.stream(ranks).skip(2).filter(r -> r >= 2).count() >= 2 && Arrays.stream(ranks).skip(2).anyMatch(r -> r >= 3);
    }
    private static boolean hasFlush(boolean[][] cards, int[] ranks, int[] suits) {
        return Arrays.stream(suits).anyMatch(s -> s >= 5);
    }
    private static boolean hasStraight(boolean[][] cards, int[] ranks, int[] suits) {
        for (int r = 1; r <= 10; r++)
            if (isStraight(ranks, r))
                return true;
        return false;
    }
    private static boolean hasThreeOfAKind(boolean[][] cards, int[] ranks, int[] suits) {
        return Arrays.stream(ranks).skip(2).anyMatch(r -> r >= 3);
    }
    private static boolean hasTwoPair(boolean[][] cards, int[] ranks, int[] suits) {
        return Arrays.stream(ranks).skip(2).filter(r -> r >= 2).count() >= 2;
    }
    private static boolean hasOnePair(boolean[][] cards, int[] ranks, int[] suits) {
        return Arrays.stream(ranks).skip(2).anyMatch(r -> r >= 2);
    }

    public static int evaluate(List<Card>hand) {
        boolean[][] cardOcc = new boolean[4][15];
        int[] rankCnt = new int[15];
        int[] suitCnt = new int[4];

        //fill structures
        for (Card card : hand) {
            int rank = card.getValue();
            int suit = card.getSuit().ordinal();

            cardOcc[suit][rank] = true;
            rankCnt[rank]++;
            if (rank == 14)
                rankCnt[1]++;
            suitCnt[suit]++;
        }

        //find best structure
        HandChecker[] checkers = {
                null,
                HandValueEvaluator::hasRoyalFlush,
                HandValueEvaluator::hasStraightFlush,
                HandValueEvaluator::hasFourOfAKind,
                HandValueEvaluator::hasFullHouse,
                HandValueEvaluator::hasFlush,
                HandValueEvaluator::hasStraight,
                HandValueEvaluator::hasThreeOfAKind,
                HandValueEvaluator::hasTwoPair,
                HandValueEvaluator::hasOnePair
        };
        for (int i = 1; i < 10; i++)
            if (checkers[i].check(cardOcc, rankCnt, suitCnt))
                return i;
        return 10;
    }
}
