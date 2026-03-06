
public class Main {

    static class TaxiCar {
        String color = "Yellow";
        void accept(Visitor e) {
            e.visit(this);
        } 
    }

    static class FormulaCar {
        String color = "Blue";
        void accept(Visitor e) {
            e.visit(this);
        }        
    }

    static interface Visitor {
        void visit(FormulaCar car);
        void visit(TaxiCar car);
    }

    static class CullMetr implements Visitor {
        @Override
        public void visit(FormulaCar car) {
            System.out.println("Cool!");
        }

        @Override
        public void visit(TaxiCar car) {
            System.out.println("Not cool!");
        }
    } 
    public static void main(String[] args) {

        abstract class Hello {
            abstract void lola();
        }

        Hello anonim = new Hello() {
            @Override
            void lola() {
                System.out.println("Lola!");
            }
        };

        anonim.lola();




        class Pokemon {
            String name;
            Pokemon(String name) {
                this.name = name;
            }

            String hello() {
                return "Hello";
            }
        }

        Visitor anonims = new Visitor() {
            @Override
            public void visit(FormulaCar car) {
                System.out.println("Cool!");
            }

            @Override
            public void visit(TaxiCar car) {
                System.out.println("Not cool!");
            } 
        };

        Pokemon pok = new Pokemon("Pika");
        System.out.println(pok.hello());


        TaxiCar taxi = new TaxiCar();
        FormulaCar car = new FormulaCar();

        taxi.accept(anonims);
        taxi.accept(new CullMetr());
        car.accept(new CullMetr());


    }
}