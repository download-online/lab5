package ru.jefremov.prog.models;


import java.time.LocalDate;
import java.util.Objects;

public class Ticket implements Comparable<Ticket> {
    private static int nextId = 1;
    private transient final int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле может быть null, Значение поля должно быть больше 0
    private double discount; //Значение поля должно быть больше 0, Максимальное значение поля: 100
    private String comment; //Поле не может быть null
    private TicketType type; //Поле может быть null
    private Event event; //Поле не может быть null

    public Ticket(String name, Coordinates coordinates, Double price, double discount, String comment, TicketType type, Event event) {
        this.id = nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.event = event;
    }

    private Ticket() {
        this.id = nextId++;
        this.creationDate = java.time.LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public String getComment() {
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }



    public void update(Ticket other) {
        if (other==null) {
            throw new IllegalArgumentException("Cannot update ticket based on null");
        }
        this.name = other.name;
        if (!coordinates.identical(other.coordinates)) this.coordinates = other.coordinates;
        this.price = other.price;
        this.discount = other.discount;
        this.comment = other.comment;
        this.type = other.type;
        if (!event.identical(other.event)) this.event = other.event;
    }

    public boolean identical(Ticket other) {
        if (other==null) {
            throw new IllegalArgumentException("Cannot compare ticket with null");
        }
        return (Objects.equals(name, other.name))&&(coordinates==other.coordinates)&&(Objects.equals(price, other.price))&&
                (discount==other.discount)&&(Objects.equals(comment, other.comment))&&(type==other.type)&&(Objects.equals(event,other.event));
    }

    @Override
    public String toString() {
        return "("+creationDate+") "+ type+" ticket " + "[" + name +"]" + " #" + id +
                " to [" + event +"]"+
                " at " + coordinates +
                " for price " + price +
                " with " + discount + "% off ―" +
                " [" + comment + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (event==null || ticket.event==null) {
            if (!(event==null && ticket.event==null)) return false;
        } else if (!event.identical(ticket.event)) return false;
        if (!Objects.equals(name, ticket.name)) return false;
        if (type != ticket.type) return false;
        if (!Objects.equals(price, ticket.price)) return false;
        if (Double.compare(ticket.discount, discount) != 0) return false;
        if (!Objects.equals(creationDate, ticket.creationDate)) return false;
        if (!Objects.equals(coordinates, ticket.coordinates)) return false;
        return Objects.equals(comment, ticket.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event,name,type,price,discount,creationDate,coordinates,comment,id);
    }

    @Override
    public int compareTo(Ticket other) {
        if (other==null) {
            return 1;
        }
        if (event.identical(other.event)) {
            if (name.equals(other.name)) {
                if (type==other.type) {
                    if (price==null) {
                        return (other.price==null ? 0 : -1);
                    } else if (other.price==null) {
                        return 1;
                    } else if (Double.compare(price,other.price)==0) {
                        if (discount==other.discount) {
                            if (creationDate.equals(other.creationDate)) {
                                if (coordinates.equals(other.coordinates)) {
                                    if (comment.equals(other.comment)) {
                                        return id-other.id;
                                    }
                                    return comment.compareTo(other.comment);
                                }
                                return coordinates.compareTo(other.coordinates);
                            }
                            return creationDate.compareTo(other.creationDate);
                        }
                        return Double.compare(discount,other.discount);
                    }
                    return Double.compare(price,other.price);
                }
                if (type==null) {
                    return -1;
                } else {
                    return (other.type==null ? 1 : type.compareTo(other.type));
                }
            }
            return name.compareTo(other.name);
        }
        return event.compareTo(other.event);
    }
}