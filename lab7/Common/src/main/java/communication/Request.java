package communication;

import model.Coordinates;
import model.Person;
import model.Product;

public record Request(Command command, Number parameter, Product product, Person person) {}
