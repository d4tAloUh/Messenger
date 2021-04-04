package messenger.backend.seeds;

public class Tuple {
    public final Integer x;
    public final Integer y;

    public Tuple(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tuple)) {
            return false;
        }

        Tuple other_ = (Tuple) other;

        // this may cause NPE if nulls are valid values for x or y. The logic may be improved to handle nulls properly, if needed.
        return (other_.x.equals(this.x) && other_.y.equals(this.y)) || (other_.y.equals(this.x) && other_.x.equals(this.y));
    }

    @Override
    public int hashCode() {
        int a = 0, b = 0;
        if (x > y) {
            a = x;
            b = y;
        } else {
            a = y;
            b = x;
        }
        int result = a; result = 31 * result + b;
        return result;

    }
}