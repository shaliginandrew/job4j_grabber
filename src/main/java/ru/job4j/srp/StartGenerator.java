package ru.job4j.srp;

public class StartGenerator {
    public static void main(String[] args) {
        SimpleSequenceGenerator sg = new SimpleSequenceGenerator(new NumberGenerator<Integer>() {

            @Override
            public Integer generate() {
                return null;
            }

        });

        sg.generate(5);

    }
}
