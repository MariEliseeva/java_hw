package spbau.eliseeva.XO.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** The class saves information about statistics and colors. */
public class Information {
    /** Number for the red color*/
    public static final String RED = "#ff0035";

    /** Number for the green color.*/
    public static final String GREEN = "#278c16";

    /** Information about who won whom and how many times.*/
    private static HashMap<String, HashMap<String, Integer>> winStatistic = new HashMap<>();

    /** Information about games ended in a draw.*/
    private static HashMap<String, HashMap<String, Integer>> drawStatistic = new HashMap<>();

    /**
     * Gives information about game statistics.
     * @return arrayList with statistics
     */
    public static ArrayList<String> getStatistics() {
        ArrayList<String> answer = new ArrayList<>();
        for (Map.Entry<String, HashMap<String, Integer>> entry1 : winStatistic.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : entry1.getValue().entrySet()) {
                answer.add(entry1.getKey() + " won " + entry2.getKey() + " " + entry2.getValue() + " times.");
            }
        }
        for (Map.Entry<String, HashMap<String, Integer>> entry1 : drawStatistic.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : entry1.getValue().entrySet()) {
                answer.add(entry1.getKey() + " play in a draw with " + entry2.getKey() + " " + entry2.getValue() + " times.");
            }
        }
        return answer;
    }

    /**
     * Adds game of two players to the statistics.
     * @param nameX name of the one, who won (if not draw)
     * @param nameO name of the other player
     * @param isDraw true if draw
     */
    public static void setStatistic(String nameX, String nameO, boolean isDraw) {
        if (isDraw) {
            updateStatistic(nameX, nameO, drawStatistic);
        } else {
            updateStatistic(nameX, nameO, winStatistic);
        }
    }

    /**
     * Update statistics for draws or for wins.
     * @param nameX name of the one, who won (if not draw)
     * @param nameO ame of the other player
     * @param winStatistic hashMap to update
     */
    private static void updateStatistic(String nameX, String nameO, HashMap<String, HashMap<String, Integer>> winStatistic) {
        if (winStatistic.containsKey(nameX)) {
            if (winStatistic.get(nameX).containsKey(nameO)) {
                winStatistic.get(nameX).put(nameO, winStatistic.get(nameX).get(nameO) + 1);
            } else {
                winStatistic.get(nameX).put(nameO, 1);
            }
        } else {
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put(nameO, 1);
            winStatistic.put(nameX, hashMap);
        }
    }

}
