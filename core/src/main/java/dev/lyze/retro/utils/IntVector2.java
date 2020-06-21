package dev.lyze.retro.utils;

import com.badlogic.gdx.utils.Pool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntVector2 implements Pool.Poolable {
    private int x, y;

    public IntVector2(IntVector2 vec) {
        x = vec.getX();
        y = vec.getY();
    }

    public IntVector2 set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public IntVector2 set(float x, float y) {
        return set((int) x, (int) y);
    }

    public IntVector2 add(int x, int y) {
        return set(this.x + x, this.y + y);
    }

    @Override
    public void reset() {
        set(0, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntVector2 that = (IntVector2) o;

        if (getX() != that.getX()) return false;
        return getY() == that.getY();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }
}
