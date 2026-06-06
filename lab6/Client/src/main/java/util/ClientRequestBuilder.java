package util;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import enums.Color;
import enums.Commands;
import enums.UnitOfMeasure;
import exceptions.IncorrectInputException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClientRequestBuilder {

    private final JsonObject request = new JsonObject();

    private final JsonObject product = new JsonObject(); {
        product.add("owner", JsonNull.INSTANCE);
    }
    private final JsonObject person = new JsonObject();
    private final JsonObject coordinates = new JsonObject();

    private final JsonObject parameter = new JsonObject();

    public ClientRequestBuilder(Commands command) {
        request.addProperty("command", command.getName());
    }

    //formater
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    //region Coordinates setters
    public ClientRequestBuilder setX(String input) throws IncorrectInputException {
        try {
            int x = NumbParser.parseInt(input);
            if (x <= -645) throw new ArithmeticException();
            coordinates.addProperty("x", x);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("integer.condition, positive.condition");
        }
        return this;
    }

    public ClientRequestBuilder setY(String input) throws IncorrectInputException {
        try {
            int y = NumbParser.parseInt(input);
            coordinates.addProperty("y", y);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("integer.condition");
        }
        return this;
    }//endregion

    //region Person setters
    public ClientRequestBuilder setPersonName(String input) throws IncorrectInputException {
        if (input == null || input.isEmpty()) throw new IncorrectInputException("not.empty.string.condition");
        else person.addProperty("name", input);
        return this;
    }

    public ClientRequestBuilder setBirthday(String input) throws IncorrectInputException {
        if (input == null || input.equals("Null") || input.equals("Nl")) {
            person.add("birthday", JsonNull.INSTANCE); // Сложный объект Gson (JsonNull) -> через .add()
        } else {
            try {
                // Валидируем строку, чтобы убедиться, что формат верный
                LocalDateTime.parse(input, formatter);

                // Записываем строку в JSON -> через .addProperty()
                person.addProperty("birthday", input);

            } catch (DateTimeParseException e) {
                throw new IncorrectInputException("date.condition");
            }
        }
        return this;
    }

    public ClientRequestBuilder setHeight(String input) throws IncorrectInputException {
        try {
            float height = NumbParser.parseFloat(input);
            if (height <= 0.0) throw new NumberFormatException();
            person.addProperty("height", height);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("positive.condition");
        }
        return this;
    }

    public ClientRequestBuilder setPassportID(String input) {
        if (input.equals("Null") || input.equals("Nl")) person.add("passportID", JsonNull.INSTANCE);
        else person.addProperty("passportID", input);
        return this;
    }

    public ClientRequestBuilder setHairColor(String input) throws IncorrectInputException {
        if (input.equals("Null") || input.equals("Nl")) person.add("hairColor", JsonNull.INSTANCE);
        else person.addProperty("hairColor", Color.getColor(input).name());
        return this;
    }//endregion

    //region Product setters
    public ClientRequestBuilder setName(String input) throws IncorrectInputException {
        if (input == null || input.isEmpty()) throw new IncorrectInputException("not.empty.string.condition");
        else product.addProperty("name", input);
        return this;
    }

    public ClientRequestBuilder setPrice(String input) throws IncorrectInputException {
        if (input.equalsIgnoreCase("Null") || input.equalsIgnoreCase("Nl")) product.add("price", JsonNull.INSTANCE);
        else {
            try {
                float price = NumbParser.parseFloat(input);
                if (price <= 0.0) throw new NumberFormatException();
                product.addProperty("price", price);
            } catch (ArithmeticException | NumberFormatException e) {
                throw new IncorrectInputException("null, positive.condition");
            }
        } return this;
    }

    public ClientRequestBuilder setPartNumber(String input) throws IncorrectInputException {
        if (input.equals("Null") || input.equals("Nl")) product.add("partNumber", JsonNull.INSTANCE);
        if (input.isEmpty()) throw new IncorrectInputException("null, not.empty.string.condition");
        else product.addProperty("partNumber", input);
        return this;
    }

    public ClientRequestBuilder setManufactureCost(String input) throws IncorrectInputException {
        try {
            product.addProperty("manufactureCost", NumbParser.parseFloat(input));
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("number.condition");
        } return this;
    }

    public ClientRequestBuilder setUnitOfMeasure(String input) throws IncorrectInputException {
        product.addProperty("unitOfMeasure", UnitOfMeasure.getUnit(input).name());
        return this;
    }//endregion

    //region Product helpers
    public ClientRequestBuilder setOwner() {
        product.add("owner", person);
        return this;
    }

    public ClientRequestBuilder setCoordinates() {
        product.add("coordinates", coordinates);
        return this;
    }//endregion

    //region Parameter setters
    public ClientRequestBuilder setIntParameter(String input) throws IncorrectInputException{
        try {
            int integer = NumbParser.parseInt(input);
            if (integer < 0) throw new ArithmeticException();
            parameter.addProperty("parameter", integer);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("integer.condition, positive.condition");
        } return this;
    }

    public ClientRequestBuilder setFloatParameter(String input) throws IncorrectInputException{
        try {
            float floater = NumbParser.parseFloat(input);
            if (floater < 0) throw new ArithmeticException();
            parameter.addProperty("parameter", floater);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("number.condition, positive.condition");
        } return this;
    }//endregion

    //region Builders
    public JsonObject buildSimpleRequest() {
        request.add("parameter", parameter);
        return request;
    }

    public JsonObject buildRequest() {
        request.add("parameter", parameter);
        request.add("product", product);
        return request;
    }
    //endregion
}
