public class StatsAccumulatorImpl implements StatsAccumulator {

    private int maxValue = Integer.MIN_VALUE;
    private int minValue = Integer.MAX_VALUE;
    private int countOfValues = 0;
    private double sumOfValues = 0;

    @Override
    public void add(int value) {
        countOfValues++;
        sumOfValues += value;
        if (maxValue < value)
            maxValue = value;
        if (minValue > value)
            minValue = value;
    }

    @Override
    public int getMin() {
        if (countOfValues > 0) {
            return minValue;
        }
        return 0;
    }

    @Override
    public int getMax() {
        if (countOfValues > 0) {
            return maxValue;
        }
        return 0;
    }

    @Override
    public int getCount() {
        return countOfValues;
    }

    @Override
    public Double getAvg() {
        return sumOfValues / countOfValues;
    }
}
