import java.util.*;

public class VeryBigInteger implements Comparable<VeryBigInteger> {
    private LinkedList<Byte> value;

    private VeryBigInteger() {
        value = new LinkedList<>();
    }

    public VeryBigInteger(String val) {
        if (!val.matches("[0-9]+")) {
            throw new IllegalArgumentException("Число задано неверно");
        }
        //разделение строки на массив строк по одной букве
        String[] digits = val.split("");
        value = new LinkedList<>();
        //преобразование в коллекцию Byte
        for (int i = 0; i < digits.length; i++) {
            value.addFirst(new Byte(digits[i]));
        }
        removeZeros();
    }

    public VeryBigInteger(int val) {
        this(Integer.toString(val));
    }

    public void add(VeryBigInteger other) {
        //iterator - объект, помогающий перебирать элементы коллекции. При этом listIterator расширяет его возможности,
        // помогая в том числе изменять и удалять элементы
        //iterator в каждый момент указывает на какой-то элемент коллекции. Метод next - перемещение на следующий,
        //hasNext - проверка, есть ли этот следующий вообще
        ListIterator<Byte> iterator1 = value.listIterator();
        Iterator<Byte> iterator2 = other.value.iterator();
        boolean perenos = false;
        /**
         * Переменная next нужна, т.к. когда iterator1 достигает конца, метод hasNext возвращает в первый раз false,
         * но на следующих итерациях почему-то true и вылетает ошибка
         */
        boolean next = true;
        while (iterator2.hasNext()) {
            byte b2 = iterator2.next();
            if (perenos) {
                b2++;
                perenos = false;
            }
            if (!iterator1.hasNext()) {
                next = false;
            }
            if (next) {
                byte b = (byte) (iterator1.next() + b2);
                if (b > 9) {
                    perenos = true;
                    b %= 10;
                }
                iterator1.set(b);
            } else {
                if (b2 > 9) {
                    perenos = true;
                    b2 %= 10;
                }
                value.add(b2);
            }
        }
        if (perenos) {
            value.addLast((byte) 1);
        }
    }

    public void minus(VeryBigInteger other) {
        if (this.compareTo(other) < 0) {
            throw new IllegalArgumentException("Не может получиться отрицательный результат");
        }
        ListIterator<Byte> iterator1 = value.listIterator();
        Iterator<Byte> iterator2 = other.value.iterator();
        boolean perenos = false;
        /**
         * тут iterator1 всегда будет иметь next, пока iterator2 имеет next, т.к. this >= other
         */
        while (iterator2.hasNext()) {
            byte b2 = iterator2.next();
            if (perenos) {
                b2++;
                perenos = false;
            }
            byte b = (byte) (iterator1.next() - b2);
            if (b < 0) {
                perenos = true;
                b += 10;
            }
            iterator1.set(b);
        }
        if (perenos) {
            byte b = iterator1.next();
            iterator1.set((byte)(b - 1));
        }
        removeZeros();
    }

    //по правилам умножения в столбик
    public void multiply(VeryBigInteger other) {
        VeryBigInteger[] sums = new VeryBigInteger[other.value.size()];
        Iterator<Byte> iterator2 = other.value.iterator();
        int i = 0;
        while (iterator2.hasNext()) {
            int perenos = 0;
            byte b2 = iterator2.next();
            sums[i] = new VeryBigInteger();
            ListIterator<Byte> iterator1 = value.listIterator();
            while (iterator1.hasNext()) {
                byte b = (byte)(iterator1.next() * b2);
                b += perenos;
                perenos = b / 10;
                b %= 10;
                sums[i].value.add(b);
            }
            if (perenos != 0) {
                sums[i].value.add((byte)perenos);
            }
            for (int j = 0; j < i; j++) {
                sums[i].value.addFirst((byte)0);
            }
            i++;
        }
        value = sums[0].value;
        for (int j = 1; j < sums.length; j++) {
            add(sums[j]);
        }
        removeZeros();
    }

    //по правилам деления в столбик
    public void divide(VeryBigInteger other) {
        if (other.compareTo(new VeryBigInteger(0)) == 0) {
            throw new ArithmeticException("Нелья делить на 0");
        }
        VeryBigInteger tmp = new VeryBigInteger();
        LinkedList<Byte> newValue = new LinkedList<>();
        Iterator<Byte> iterator = value.descendingIterator();
        tmp.value.addFirst(iterator.next());
        while (iterator.hasNext()) {
            while (iterator.hasNext() && tmp.compareTo(other) < 0) {
                tmp.value.addFirst(iterator.next());
                if (tmp.compareTo(other) < 0) {
                    newValue.addFirst((byte) 0);
                }
            }
            byte b = 0;
            while (tmp.compareTo(other) >= 0) {
                tmp.minus(other);
                b++;
            }
            if (b != 0) {
                newValue.addFirst(b);
            }
        }
        value = newValue;
        removeZeros();
    }

    //то же деление в столбик, только записывается остаток
    public void remainder(VeryBigInteger other) {
        if (other.compareTo(new VeryBigInteger(0)) == 0) {
            throw new ArithmeticException("Нелья делить на 0");
        }
        VeryBigInteger tmp = new VeryBigInteger();
        Iterator<Byte> iterator = value.descendingIterator();
        tmp.value.addFirst(iterator.next());
        while (iterator.hasNext()) {
            while (iterator.hasNext() && tmp.compareTo(other) < 0) {
                tmp.value.addFirst(iterator.next());
            }
            while (tmp.compareTo(other) >= 0) {
                tmp.minus(other);
            }
        }
        value = tmp.value;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Byte> iterator = value.descendingIterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
        }
        return stringBuilder.toString();
    }

    //-1 если меньше, 1, если больше, 0, если равны
    @Override
    public int compareTo(VeryBigInteger o) {
        if (value.size() > o.value.size()) {
            return 1;
        }

        if (value.size() < o.value.size()) {
            return -1;
        }

        for (int i = value.size() - 1; i >= 0; i--) {
            if (value.get(i) > o.value.get(i)) {
                return 1;
            }
            if (value.get(i) < o.value.get(i)) {
                return -1;
            }
        }
        return 0;
    }

    //удаляет ведущие нули у числа. 0000021 -> 21
    private void removeZeros() {
        Iterator<Byte> iterator = value.descendingIterator();
        while (iterator.hasNext() && iterator.next() == 0 && iterator.hasNext()) {
            iterator.remove();
        }
    }
}
