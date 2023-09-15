package ru.jefremov.prog.managers;

import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.models.Event;
import ru.jefremov.prog.models.Ticket;
import ru.jefremov.prog.models.validation.AbstractTicketValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Класс для хранилища, обеспечивающего ограниченный доступ к коллекции.
 * Обеспечивает корректность хранящихся данных и предоставляет удобные средства для управления коллекцией.
 */
public class Storage {
    private LinkedHashSet<Ticket> collection;
    private final AbstractTicketValidator ticketValidator;
    private final Administrator administrator;
    private LocalDate initDate;

    /**
     * Конструктор для хранилища
     * @param administrator администратор
     * @param ticketValidator валидатор билетов
     */
    public Storage(Administrator administrator, AbstractTicketValidator ticketValidator) {
        if (administrator==null) throw new IllegalArgumentException("Administrator must not be null");
        this.administrator = administrator;
        if (ticketValidator==null) throw new IllegalArgumentException("Ticket validator must not be null");
        this.ticketValidator = ticketValidator;
        this.collection = new LinkedHashSet<>();
        initDate = LocalDate.now();
    }

    /**
     * Геттер для копии хранящейся коллекции.
     * @return копия коллекции
     */
    public LinkedHashSet<Ticket> getCollectionCopy() {
        return new LinkedHashSet<>(collection);
    }

    /**
     * Геттер для администратора
     * @return администратор
     */
    public Administrator getAdministrator() {
        return administrator;
    }

    /**
     * Загрузить коллекцию в хранилище. Отвергает неподходящие коллекции.
     * @param collection коллекция
     * @return подошла ли она для загрузки
     */
    public boolean loadCollection(LinkedHashSet<Ticket> collection) {
        if (collection==null) {
            throw new IllegalArgumentException("collection must not be null");
        }
        if (checkCollection(collection)) {
            this.collection = new LinkedHashSet<>(collection);
            initDate = LocalDate.now();
            return true;
        }
        return false;
    }

    /**
     * Проверяет коллекцию на соответствие требованиям валидатора элементов.
     * @param collection коллекция
     * @return прошла ли коллекция проверку, или нет
     */
    public boolean checkCollection(LinkedHashSet<Ticket> collection) {
        if (collection==null) {
            return false;
        }
        for (Ticket ticket:
             collection) {
            if (!ticketValidator.checkTicket(ticket)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Выявляет проблемы в коллекции при её проверке
     * @param collection коллекция
     * @return Комментарий, если есть проблемы, или null, если коллекция успешно прошла проверку.
     */
    public String reviewCollection(LinkedHashSet<Ticket> collection) {
        if (collection==null) {
            return null;
        }
        int c = 0;
        for (Ticket ticket:
                collection) {
            String result = ticketValidator.reviewTicket(ticket);
            if (!(result==null)) {
                if (ticket==null) return "Null ticket at "+c+" position";
                return "At ticket #"+ticket.getId()+": "+result;
            }
            c+=1;
        }
        return null;
    }

    /**
     * Добавление билета. Отвергает неподходящие элементы.
     * @param ticket билет
     * @return Подошёл ли он для добавления, или нет.
     */
    public boolean addTicket(Ticket ticket) {
        if (ticketValidator.checkTicket(ticket)) {
            return collection.add(ticket);
        }
        return false;
    }

    /**
     * Получение билета по id
     * @param id id
     * @return билет с соответствующим идентификатором
     */
    private Ticket getById(int id) {
        for (Ticket ticket:
             collection) {
            if (ticket.getId()==id) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Проверка, присутствует ли в коллекции билет с нужным id
     * @param id id
     * @return результат проверки
     */
    public boolean hasId(int id) {
        return (getById(id)!=null);
    }

    /**
     * Обновляет билет с нужным id, копируя поля у другого билета. Другой билет может быть отвергнут.
     * @param id id
     * @param other другой билет
     * @return Состоялось ли обновление (подошёл ли другой билет, или нет)
     */
    public boolean updateById(int id, Ticket other) {
        Ticket ticket = getById(id);
        if (ticket==null) {
            return false;
        }
        if (ticketValidator.checkTicket(other)) {
            collection.remove(ticket);
            ticket.update(other);
            return collection.add(ticket);
        }
        return false;
    }

    /**
     * Удаляет билет по id
     * @param id id
     * @return изменилась ли коллекция
     */
    public boolean removeById(int id) {
        Ticket ticket = getById(id);
        if (ticket==null) {
            return false;
        }
        return collection.remove(ticket);
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Выводит содержимое коллекции
     * @return есть ли в коллекции элементы
     */
    public boolean show() {
        if (collection.isEmpty()) return false;
        for (Ticket ticket:
             collection) {
            Printer.println(ticket);
        }
        return true;
    }

    /**
     * Получение размера коллекции
     * @return размер коллекции
     */
    public int size() {
        return collection.size();
    }

    /**
     * Выводит информацию о коллекции.
     */
    public void printInfo() {
        Printer.println(collection.getClass().getName()+" collection with "+size()+" elements.");
        Printer.println("Initialisation date: "+ initDate);
        Printer.println("The collection is linked to a file: "+ administrator.collectionFile);
    }

    /**
     * Добавляет билет, если он меньше всех элементов в коллекции и проходит проверку.
     * @param ticket билет
     * @return добавлен ли билет (прошёл ли он проверку)
     */
    public boolean addIfMin(Ticket ticket) {
        if (collection.isEmpty() || Collections.min(collection).compareTo(ticket)>0) {
            return addTicket(ticket);
        }
        return false;
    }

    /**
     * Удаляет все элементы в коллекции, меньшие, чем заданный
     * @param ticket заданный билет
     * @return количество удалённых элементов
     */
    public int removeLower(Ticket ticket) {
        int sizeBefore = collection.size();
        collection.removeIf(ticket1 -> ticket1.compareTo(ticket)<0);
        int sizeAfter = collection.size();
        return sizeAfter-sizeBefore;
    }

    /**
     * Выводит элементы коллекции, чей комментарий начинается с заданной подстроки
     * @param comment заданная подстрока
     * @return подошла ли строка, если да, то вывелся ли хотя бы один элемент
     */
    public boolean printFilterStartsWithComment(String comment) {
        if (!ticketValidator.checkComment(comment)) return false;
        boolean appliedAny = false;
        for (Ticket ticket:
             collection) {
            if (ticket.getComment()!=null && ticket.getComment().startsWith(comment)) {
                Printer.println(ticket);
                appliedAny = true;
            }
        }
        return appliedAny;
    }

    /**
     * Выводит все элементы коллекции, чьё событие меньше, чем заданное
     * @param event заданное событие
     * @return подошло ли событие, и если да, то вывелся ли хотя бы один элемент.
     */
    public boolean printFilterLessThanEvent(Event event) {
        if (!ticketValidator.checkEvent(event)) return false;
        boolean appliedAny = false;
        for (Ticket ticket:
                collection) {
            if (ticket.getEvent().compareTo(event)<0) {
                Printer.println(ticket);
                appliedAny = true;
            }
        }
        return appliedAny;
    }

    /**
     * Выводит коллекцию в порядке убывания
     * @return есть ли в коллекции элементы.
     */
    public boolean printDescending() {
        if (collection.isEmpty()) return false;
        var list = new ArrayList<>(collection);
        list.sort(null);
        Collections.reverse(list);
        list.forEach(Printer::println);
        return true;
    }
}
