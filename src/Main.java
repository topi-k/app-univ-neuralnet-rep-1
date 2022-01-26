import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {

            /* データの宣言（入力パターンと教師信号の組）*/
            int[] x1 = {1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1};
            int[] x2 = {1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0};
            int[] x3 = {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1};
            int[] x4 = {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1};
            int[] x5 = {0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0};
            int[] x6 = {0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0};
            int[] x7 = {0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1};
            int[] t = {1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0};

            /* シナプス結合荷重の初期値設定 / 学習率 / 閾値 */
            double init_synapse_load = 0.52;
            double learning_rate = 0.5;
            double threshold = 0.56;

            File file = new File("./data"+learning_rate+".csv");
            FileWriter filewriter = new FileWriter(file);

            double w1, w2, w3, w4, w5, w6, w7;
            w1 = w2 = w3 = w4 = w5 = w6 = w7 = init_synapse_load;

            int z, total_diff, calc_cnt = 0;
            double temp, diff;

            while (true) {
                total_diff = 0;
                for (int i = 0; i < t.length; i++) {
                    /* 出力値（生）を計算する */
                    temp = (x1[i] * w1 + x2[i] * w2 + x3[i] * w3 + x4[i] * w4 + x5[i] * w5 + x6[i] * w6 + x7[i] * w7) - threshold;
                    /* 求められた出力値からニューロンが発火したかを判定する */
                    if (temp > 0) {
                        z = 1;
                    } else {
                        z = 0;
                    }

                    /* 教師信号との誤差を求めて0以外であれば要修正 */
                    diff = t[i] - z;
                    total_diff += Math.abs(diff);
                    calc_cnt++;

                    if (diff != 0) {
                        /* 修正が必要な場合は結合荷重を修正する */
                        w1 = w1 + (learning_rate * diff * x1[i]);
                        w2 = w2 + (learning_rate * diff * x2[i]);
                        w3 = w3 + (learning_rate * diff * x3[i]);
                        w4 = w4 + (learning_rate * diff * x4[i]);
                        w5 = w5 + (learning_rate * diff * x5[i]);
                        w6 = w6 + (learning_rate * diff * x6[i]);
                        w7 = w7 + (learning_rate * diff * x7[i]);
                        threshold = threshold - (learning_rate * diff);
                        /* 修正時のみシナプス結合荷重を表示(学習過程の表示) */
                        System.out.printf("[%d] Th:%f w1: %f w2: %f w3: %f w4: %f w5: %f w6: %f w7: %f \n", calc_cnt, threshold, w1, w2, w3, w4, w5, w6, w7);
                    }
                }
                filewriter.write(calc_cnt + "," + threshold + "," + w1 + "," + w2 + "," + w3 + "," + w4 + "," + w5 + "," + w6 + "," + w7 + "\n");
                if (total_diff == 0) break;
            }
            filewriter.close();
            System.out.printf("\n ==== Learning completed ==== \n");
            System.out.printf("計算回数: %d\n閾値:%f\n w1: %f\n w2: %f\n w3: %f\n w4: %f\n w5: %f\n w6: %f\n w7: %f\n \n", calc_cnt, threshold, w1, w2, w3, w4, w5, w6, w7);

        } catch (IOException e) {
            System.out.printf("\n ==== ERROR ==== \n");
            System.out.println(e);
        }
    }
}
