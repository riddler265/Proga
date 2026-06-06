package util;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import communication.Command;

import communication.Request;
import exceptions.IncorrectInputException;
import model.Coordinates;
import model.Person;
import model.Product;
import util.numbparser.NumbParser;

import model.enums.*;
import util.json.JsonManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClientRequestBuilder {

    private final Command command;

    private Number parameter = null;

    private final Product product = new Product();
    private final Person person = new Person();
    private final Coordinates coordinates = new Coordinates();

    private final DateTimeFormatter formatter = JsonManager.getFormatter();

    public ClientRequestBuilder(Command command) {
        this.command = command;
    }


    //region Coordinates setters
    public void setX(String input) throws IncorrectInputException {
        try {
            int x = util.numbparser.NumbParser.parseInt(input);
            if (x <= -645) throw new ArithmeticException();
            coordinates.setX(x);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("integer.condition, positive.condition");
        }
       
    }

    public void setY(String input) throws IncorrectInputException {
        try {
            int y = util.numbparser.NumbParser.parseInt(input);
            coordinates.setY(y);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("integer.condition");
        }
       
    }//endregion

    //region Person setters
    public void setPersonName(String input) throws IncorrectInputException {
        if (input == null || input.isEmpty()) throw new IncorrectInputException("not.empty.string.condition");
        else person.setName(input);
       
    }

    public void setBirthday(String input) throws IncorrectInputException {
        if (input == null || input.equals("Null") || input.equals("Nl")) person.setName(null);
        else {
            try {
                person.setBirthday(LocalDateTime.parse(input, formatter));
            } catch (DateTimeParseException e) {
                throw new IncorrectInputException("date.condition");
            }
        }
       
    }

    public void setHeight(String input) throws IncorrectInputException {
        try {
            float height = util.numbparser.NumbParser.parseFloat(input);
            if (height <= 0.0) throw new NumberFormatException();
            person.setHeight(height);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("positive.condition");
        }
       
    }

    public void setPassportID(String input) {
        if (input.equals("Null") || input.equals("Nl")) person.setPassportID(null);
        else person.setPassportID(input);
       
    }

    public void setHairColor(String input) throws IncorrectInputException {
        if (input.equals("Null") || input.equals("Nl")) person.setHairColor(null);
        else {
            try {
                int id = NumbParser.parseInt(input);
                Color color = Color.getColor(input, id);
                if (color == null) throw new NullPointerException();
                person.setHairColor(color);
            } catch (ArithmeticException | NumberFormatException | NullPointerException e) {
                throw  new IncorrectInputException(Color.getConditionKey());
            }
        }
    }

    public void setPassword(String input) throws IncorrectInputException {
        if (input.equals("Null") || input.equals("Nl")) throw new IncorrectInputException("not.empty.string.condition");
        else person.setPassword(input);
    }
    //endregion

    //region Product setters
    public void setName(String input) throws IncorrectInputException {
        if (input == null || input.isEmpty()) throw new IncorrectInputException("not.empty.string.condition");
        else product.setName(input);
       
    }

    public void setPrice(String input) throws IncorrectInputException {
        if (input.equalsIgnoreCase("Null") || input.equalsIgnoreCase("Nl")) product.setPrice(null);
        else {
            try {
                float price = util.numbparser.NumbParser.parseFloat(input);
                if (price <= 0.0) throw new NumberFormatException();
                product.setPrice(price);
            } catch (ArithmeticException | NumberFormatException e) {
                throw new IncorrectInputException("null, positive.condition");
            }
        }
    }

    public void setPartNumber(String input) throws IncorrectInputException {
        if (input.equals("Null") || input.equals("Nl")) product.setPartNumber(null);
        if (input.isEmpty()) throw new IncorrectInputException("null, not.empty.string.condition");
        else product.setPartNumber(input);
       
    }

    public void setManufactureCost(String input) throws IncorrectInputException {
        try {
            float manufactureCost = NumbParser.parseFloat(input);
            if (manufactureCost < 0.0) throw new ArithmeticException();
            product.setManufactureCost(manufactureCost);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("number.condition");
        }
    }

    public void setUnitOfMeasure(String input) throws IncorrectInputException {
        try {
            int id = NumbParser.parseInt(input);
            UnitOfMeasure unitOfMeasure = UnitOfMeasure.getUnitOfMeasure(input, id);
            if (unitOfMeasure == null) throw new NullPointerException();
            product.setUnitOfMeasure(unitOfMeasure);
        } catch (ArithmeticException | NumberFormatException | NullPointerException e) {
            throw  new IncorrectInputException(UnitOfMeasure.getConditionKey());
        }
       
    }//endregion

    public void setCoordinates() {
        product.setCoordinates(coordinates);
    }

    //region Parameter setters
    public void setIntParameter(String input) throws IncorrectInputException{
        try {
            int integer = util.numbparser.NumbParser.parseInt(input);
            if (integer < 0) throw new ArithmeticException();
            parameter = integer;
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("integer.condition, positive.condition");
        }
    }

    public void setFloatParameter(String input) throws IncorrectInputException{
        try {
            float floater = util.numbparser.NumbParser.parseFloat(input);
            if (floater < 0.0) throw new ArithmeticException();
            parameter = floater;
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("number.condition, positive.condition");
        }
    }//endregion

    //region Builders
    public Request buildRequest() {
        return new Request(command, parameter, product, person);
    }
    //endregion
}
