public class Lab1 {

    public static void main(String[] args) {

        short[] w = new short[] {4, 6, 8, 10, 12, 14, 16, 18}; //Создание массива с натуральными числами
        double[] x = new double[14]; //Создание массива чисел типа дабл
        double[][] n = new double[8][14]; //Создание двумерного массива типа дабл

        for (var i = 0; i < x.length; i++) { //Заполнение массива чисел типа дабл в диапазоне -9.0 -- 6.0
            x[i] = -9.0 + Math.random()*15;
        }

        for (var i = 0; i < w.length; i++) { //Заполнение двумерного массива
            switch (w[i]) {//Вместо if else использую свитч
                case 16 ->{
                    for (var j = 0; j < x.length; j++) {
                        n[i][j] = W16(x[j]);
                    }
                }
                case 6, 10, 12, 18 -> {
                    for (var j = 0; j < x.length; j++) {
                        n[i][j] = WSecondCond(x[j]);
                    }
                }
                default -> {
                    for (var j = 0; j < x.length; j++) {
                        n[i][j] = WOthers(x[j]);
                    }
                }
            }
        }
        Result(n);
    }

    public static double W16(double x) { //Создание метода при w = 16
        return Math.pow((Math.tan((Math.pow(x, 1.0-x))))/0.5, 3.0);
    }

    public static double WSecondCond(double x) { //Создение метода согласно второму условию w
        return Math.pow(Math.tan(Math.cbrt(x)), (Math.log(Math.abs(x)))/2.0);
    }

    public static double WOthers(double x) {//Создание метода для остальных значений w
        return Math.tan(Math.pow(Math.tan(x), Math.pow(x*(2.0/3.0 + x), 3.0)));          
    }

    public static void Result(double[][] n) {//Вывод в формате матрицы
        for (int i = 0; i < n.length; i++) {
            for (var j : n[i]) {
                System.out.printf(" %8.4f ", j);
            }
            System.out.println();
        }
    }  
}