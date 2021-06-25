package ru.job4j.srp;

public class StartGenerator {
    public static void main(String[] args) {
        SimpleSequenceGenerator generetor = new SimpleSequenceGenerator(new NumberGenerator<Integer>() {

            @Override
            public Integer generate() {
                return null;
            }

        });

        generetor.generate(5);
        SimpleOutPut su = new SimpleOutPut();
        su.print("Вывод данных");

    }
}
